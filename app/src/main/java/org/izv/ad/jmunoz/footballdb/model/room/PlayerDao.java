package org.izv.ad.jmunoz.footballdb.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import java.util.List;

@androidx.room.Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Player player);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Club club);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(Player... players);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(Club... clubes);

    @Update
    int update(Player player);

    @Update
    int update(Club club);

    @Delete
    int delete(Player player);

    @Delete
    int delete(Club club);

    @Query("delete from player")
    int deleteAll();

    @Query("select * from player order by name asc")
    LiveData<List<Player>> getPlayers();

    @Query("select * from club")
    LiveData<List<Club>> getClubes();

    @Query("select * from player where id = :id")
    LiveData<Player> getPlayer(long id);

    @Query("select * from club where id = :id")
    LiveData<Club> getClub(long id);

}
