package org.izv.ad.jmunoz.footballdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.model.repository.Repository;

import java.util.List;

public class ClubViewModel extends AndroidViewModel {

    private Repository repository;

    public ClubViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insert(Club club) {
        repository.insert(club);
    }

    public void insert(Club... clubes) {
        repository.insert(clubes);
    }

    public void update(Club club) {
        repository.update(club);
    }

    public void delete(Club club) {
        repository.delete(club);
    }

    public LiveData<List<Club>> getClubes() {
        return repository.getClubes();
    }

    public LiveData<Club> getClub(long id) {
        return repository.getClub(id);
    }

    public void clubesPreload() {
        repository.clubesPreload();
    }

    public void setInit() {
        repository.setInit();
    }

    public boolean getInit() {
        return repository.getInit();
    }
}
