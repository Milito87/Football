package org.izv.ad.jmunoz.footballdb.model.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class, Club.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {

    public abstract PlayerDao getDao();

    private static volatile PlayerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //variable que podra ejecutar simultaneamente el num de hilos indicados
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PlayerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            synchronized (PlayerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlayerDatabase.class, "player_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
