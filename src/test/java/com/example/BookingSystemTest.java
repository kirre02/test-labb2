package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingSystemTest {

    @Mock
    TimeProvider timeProvider;

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    BookingSystem bookingSystem;

    @Nested
    @DisplayName("Book room tests")
    class BookRoomTests {
        @Test
        @DisplayName("Throws error for null parameter")
        void throwsErrorForNullParameter() {
            assertThatThrownBy(() -> bookingSystem.bookRoom(null, LocalDateTime.MIN, LocalDateTime.MAX))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
        }

        @Test
        @DisplayName("Throws error if startTime is before current time")
        void throwsErrorIfStartTimeIsBeforeCurrentTime() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MAX);

            assertThatThrownBy(() -> bookingSystem.bookRoom("1", LocalDateTime.MIN, LocalDateTime.MAX))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Kan inte boka tid i dåtid");
        }

        @Test
        @DisplayName("Throws error if endTime is before startTime")
        void throwsErrorIfEndTimeIsBeforeStartTime() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MAX);

            assertThatThrownBy(() -> bookingSystem.bookRoom("1", LocalDateTime.MAX, LocalDateTime.MIN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Sluttid måste vara efter starttid");
        }

        @Test
        @DisplayName("Throws error for non existent room")
        void throwsErrorForNonExistentRoom() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MIN);
            when(roomRepository.findById("2")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookingSystem.bookRoom("2", LocalDateTime.MIN, LocalDateTime.MAX))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Rummet existerar inte");
        }

        @Test
        @DisplayName("Returns false for non available room")
        void returnsFalseForNonAvailableRoom() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MIN);
            Room nonAvailableRoom = new Room("3", "Rum tre");
            nonAvailableRoom.addBooking(new Booking("1", "3", LocalDateTime.MIN, LocalDateTime.MAX));
            when(roomRepository.findById("3")).thenReturn(Optional.of(nonAvailableRoom));

            assertThat(bookingSystem.bookRoom("3", LocalDateTime.MIN, LocalDateTime.MAX)).isFalse();
        }

        @Test
        @DisplayName("Returns true for available room")
        void returnsTrueForAvailableRoom() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MIN);
            when(roomRepository.findById("1")).thenReturn(Optional.of(new Room("1", "Rum ett")));

            assertThat(bookingSystem.bookRoom("1", LocalDateTime.MIN, LocalDateTime.MAX)).isTrue();
        }
    }

    @Nested
    @DisplayName("Get available rooms tests")
    class GetAvailableRoomsTests {
        @Test
        @DisplayName("Throws error for null parameter")
        void throwsErrorForNullParameter() {
            assertThatThrownBy(() -> bookingSystem.getAvailableRooms(LocalDateTime.MIN, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Måste ange både start- och sluttid");
        }

        @Test
        @DisplayName("Throws error if endTime is before startTime")
        void throwsErrorIfEndTimeIsBeforeStartTime() {
            assertThatThrownBy(() -> bookingSystem.getAvailableRooms(LocalDateTime.MAX, LocalDateTime.MIN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Sluttid måste vara efter starttid");
        }

        @Test
        @DisplayName("Taken room is not part of the available rooms")
        void takenRoomIsNotPartOfAvailableRooms() {
            Room takenRoom = new Room("1", "Rum ett");
            takenRoom.addBooking(new Booking("1", "1", LocalDateTime.MIN, LocalDateTime.MAX));
            Room availableRoom = new Room("2", "Rum två");

            when(roomRepository.findAll()).thenReturn(List.of(availableRoom, takenRoom));

            assertThat(bookingSystem.getAvailableRooms(LocalDateTime.MIN, LocalDateTime.MAX)).isEqualTo(List.of(availableRoom));
        }

        @Test
        @DisplayName("Get all available rooms")
        void getAllAvailableRooms() {
            Room roomOne = new Room("1", "Rum ett");
            Room roomTwo = new Room("2", "Rum två");

            when(roomRepository.findAll()).thenReturn(List.of(roomOne, roomTwo));

            assertThat(bookingSystem.getAvailableRooms(LocalDateTime.MIN, LocalDateTime.MAX)).isEqualTo(List.of(roomOne, roomTwo));
        }
    }

    @Nested
    @DisplayName("Cancel booking tests")
    class CancelBookingTests {
        @Test
        @DisplayName("Throws exception for null parameter")
        void throwsExceptionForNullParameter() {
            assertThatThrownBy(() -> bookingSystem.cancelBooking(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Boknings-id kan inte vara null");
        }

        @Test
        @DisplayName("Returns false for unused booking id")
        void returnsFalseForUnusedBookingId() {
            assertThat(bookingSystem.cancelBooking("1")).isFalse();
        }

        @Test
        @DisplayName("Throws exception if start time is before current time")
        void throwsExceptionIfStartTimeIsBeforeCurrentTime() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MAX);
            Room bookedRoom = new Room("1", "Rum ett");
            bookedRoom.addBooking(new Booking("1", "1", LocalDateTime.MIN, LocalDateTime.MAX));
            when(roomRepository.findAll()).thenReturn(List.of(bookedRoom));

            assertThatThrownBy(() -> bookingSystem.cancelBooking("1"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Kan inte avboka påbörjad eller avslutad bokning");
        }

        @Test
        @DisplayName("Returns true for successful canceling")
        void returnsTrueForSuccessfulCanceling() {
            when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.MIN);
            Room bookedRoom = new Room("1", "Rum ett");
            bookedRoom.addBooking(new Booking("1", "1", LocalDateTime.MIN, LocalDateTime.MAX));
            when(roomRepository.findAll()).thenReturn(List.of(bookedRoom));

            assertThat(bookingSystem.cancelBooking("1")).isTrue();
        }
    }
}