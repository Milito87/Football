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
import org.izv.ad.jmunoz.footballdb.view.adapter.ClubAdapter;
import org.izv.ad.jmunoz.footballdb.viewmodel.ClubViewModel;

public class ClubFragment extends Fragment {

   private RecyclerView recyclerClub;
   private ClubViewModel cvm;
   private ClubAdapter clubAdapter;
   private Button btBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_club, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        //cierra fragment y vuelve al anterior
        btBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

    }

    private void init(View view){

        btBack = view.findViewById(R.id.btClubBack);

        recyclerClub = view.findViewById(R.id.recyclerViewClubes);
        recyclerClub.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));

        cvm = new ViewModelProvider(getActivity()).get(ClubViewModel.class);

        //establece la lista de clubs en el adaptador
        cvm.getClubes().observe(getActivity(), clubs -> {
            clubAdapter = new ClubAdapter(view.getContext());
            clubAdapter.setClubList(clubs);
            recyclerClub.setAdapter(clubAdapter);
        });

    }

}