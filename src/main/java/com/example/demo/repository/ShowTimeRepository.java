package com.example.demo.repository;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtimes, Integer> {

    List<Showtimes> findShowtimesByMovieId_MovieId(String id);
    List<Showtimes> findAllByMovieId_MovieIdAndRoomId_RoomIdIn(String movieId, Collection<String> roomIds);

    @Query(value = "SELECT s.* " +
            "FROM showtimes s " +
            "JOIN room r ON s.roomId = r.roomId " +
            "JOIN threat t ON r.threatId = t.id " +
            "WHERE s.movieId = :movieId AND t.id = :threatId", nativeQuery = true)
    List<Showtimes> findShowtimesByThreatIdAndMovieId(Integer threatId, String movieId);

    @Query("SELECT DISTINCT m FROM Showtimes s JOIN s.movieId m JOIN s.roomId r  JOIN r.threatId t  WHERE t.id = :threatId")
    List<Movie> findMoviesByThreatId(@Param("threatId") Integer threatId);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.showtimesCollection s JOIN s.roomId r JOIN r.threatId t WHERE t.id = :theaterId AND s.date = :date")
    List<Movie> findMoviesAndShowtimesForDate(@Param("theaterId") Integer theaterId, @Param("date") String date);


    @Query("SELECT  s FROM Showtimes s JOIN s.roomId r JOIN r.threatId t WHERE t.id = :threatId")
    List<Showtimes> findAvailableDatesForTheater(@Param("threatId") Integer threatId);



}
