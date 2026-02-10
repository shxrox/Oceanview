package com.oceanview.repository;
import com.oceanview.model.Room;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface RoomRepository {
    List<Room> findAvailableRooms(Date checkIn, Date checkOut) throws SQLException;
    Room findById(int id) throws SQLException;
}