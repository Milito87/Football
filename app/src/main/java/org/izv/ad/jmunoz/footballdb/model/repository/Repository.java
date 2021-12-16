package org.izv.ad.jmunoz.footballdb.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.lifecycle.LiveData;
import org.izv.ad.jmunoz.footballdb.model.room.PlayerDao;
import org.izv.ad.jmunoz.footballdb.model.room.PlayerDatabase;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import java.util.List;

public class Repository {

    private static final String INIT = "init";

    private PlayerDao dao;
    private SharedPreferences preferences;

    public Repository(Context context) {
        PlayerDatabase db = PlayerDatabase.getDatabase(context);
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(!getInit()) {
            clubesPreload();
            setInit();
        }
    }

    /*-----------------------PLAYER-------------------------*/

    public void insert(Player player) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.insert(player);
        });
    }

    public void insert(Player... players) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.insert(players);
        });
    }

    public void update(Player player) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.update(player);
        });
    }

    public void delete(Player player) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.delete(player);
        });
    }

    public void deleteAll() {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.deleteAll();
        });
    }

    public LiveData<List<Player>> getPlayers(){
        return dao.getPlayers();
    }

    public LiveData<Player> getPlayer(long id) {
        return dao.getPlayer(id);
    }

    /*-------------------------CLUB-----------------------------*/

    public void insert(Club club) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.insert(club);
        });
    }

    public void insert(Club... clubes) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
        dao.insert(clubes);
        });
    }

    public void update(Club club) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
        dao.update(club);
        });
    }

    public void delete(Club club) {
        PlayerDatabase.databaseWriteExecutor.execute(()-> {
            dao.delete(club);
        });
    }

    public LiveData<List<Club>> getClubes() {
        return dao.getClubes();
    }

    public LiveData<Club> getClub(long id) {
        return dao.getClub(id);
    }

    /*-------------------PREFERENCES------------------------*/

    public void clubesPreload() {
        String[] clubNames = new String[] {};
        Club[] clubes = new Club[clubNames.length];
        Club club;
        int cont = 0;
        for (String s: clubNames) {
            club = new Club();
            club.name = s;
            clubes[cont] = club;
            cont++;
        }
        insert(clubes);
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }

}
