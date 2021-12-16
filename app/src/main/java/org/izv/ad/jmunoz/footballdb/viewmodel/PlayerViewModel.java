package org.izv.ad.jmunoz.footballdb.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import org.izv.ad.jmunoz.footballdb.model.repository.Repository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private Repository repository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insert(Player player) {
        repository.insert(player);
    }

    public void insert(Player... players) {
        repository.insert(players);
    }

    public void update(Player player) {
        repository.update(player);
    }

    public void delete(Player player) {
        repository.delete(player);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Player>> getPlayers() {
        return repository.getPlayers();
    }

    public LiveData<Player> getPlayer(long id) {
        return repository.getPlayer(id);
    }

}
