package org.izv.ad.jmunoz.footballdb.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.view.MainActivity;
import org.izv.ad.jmunoz.footballdb.view.adapter.PlayerAdapter;
import org.izv.ad.jmunoz.footballdb.viewmodel.ClubViewModel;
import org.izv.ad.jmunoz.footballdb.viewmodel.PlayerViewModel;

public class InfoFragment extends Fragment {

    private RecyclerView recyclerPlayer;
    private ClubViewModel cvm;
    private PlayerViewModel pvm;
    private PlayerAdapter playerAdapter;
    private Button btAdd, btClub;
    public Bundle b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();

        init(view);

        //va a PlayerFragment
        btAdd.setOnClickListener(v -> {
            //para evitar cargas  de informacion
            MainActivity.player_id = (long) -1;
            NavHostFragment.findNavController(InfoFragment.this).navigate(R.id.toInfoPlayer);
        });

        btClub.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.toInfoClub);
        });

    }

    private void init(View view){

        if(MainActivity.player_id == null){
            MainActivity.player_id = (long) -1;
        }

        if(b != null){
            MainActivity.player_id = b.getLong("edit");
        }

        if(MainActivity.player_id > 1){
            NavHostFragment.findNavController(this).navigate(R.id.toInfoPlayer);
        }


        btAdd = view.findViewById(R.id.btAddInfo);
        btClub = view.findViewById(R.id.btInfoClub);

        recyclerPlayer = view.findViewById(R.id.recyclerViewInfo);
        recyclerPlayer.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));

        cvm = new ViewModelProvider(getActivity()).get(ClubViewModel.class);
        pvm = new ViewModelProvider(getActivity()).get(PlayerViewModel.class);

        //establece la lista de Player y Club en el adaptador
        pvm.getPlayers().observe(getActivity(), players -> {
            cvm.getClubes().observe(getActivity(), clubs -> {
                playerAdapter = new PlayerAdapter(view.getContext());
                playerAdapter.setPlayerList(players);
                playerAdapter.setClubList(clubs);
                recyclerPlayer.setAdapter(playerAdapter);
            });
        });
        //crea la lista de club
        createLeague();
    }

    //crea y devuelve un club
    private Club createClub(int id, String name, String url, String descr, int start) {
        Club club = new Club();
        club.id = id;
        club.name = name;
        club.url = url;
        club.description = descr;
        club.star = start;

        return club;
    }

    //crea lista de clubes
    private void createLeague(){

            cvm.getClubes().observe(getActivity(), clubs -> {
                if(clubs.size() <= 0) {
                    cvm.insert(createClub(0, getString(R.string.fcb_name), getString(R.string.fcb_url), getString(R.string.fcb_descr), 5));
                    cvm.insert(createClub(1, getString(R.string.rma_name), getString(R.string.rma_url), getString(R.string.rma_descr), 4));
                    cvm.insert(createClub(2, getString(R.string.atm_name), getString(R.string.atm_url), getString(R.string.atm_descr), 4));
                    cvm.insert(createClub(3, getString(R.string.val_name), getString(R.string.val_url), getString(R.string.val_descr), 3));
                    cvm.insert(createClub(4, getString(R.string.sev_name), getString(R.string.sev_url), getString(R.string.sev_descr), 4));
                    cvm.insert(createClub(5, getString(R.string.bet_name), getString(R.string.bet_url), getString(R.string.bet_descr), 3));
                }
            });

    }

    }

