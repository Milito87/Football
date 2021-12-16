package org.izv.ad.jmunoz.footballdb.view.adapter.viewholder;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import org.izv.ad.jmunoz.footballdb.view.MainActivity;
import org.izv.ad.jmunoz.footballdb.view.fragment.InfoFragment;
import org.izv.ad.jmunoz.footballdb.viewmodel.PlayerViewModel;

import java.util.List;

public class PlayerViewHolder extends RecyclerView.ViewHolder{

    public Player player;
    public Club club;
    public ImageView iPlayer, iClub;
    public RatingBar start;
    public Button btPos;
    public TextView tvName, tvDate;
    public AlertDialog.Builder alert;
    private PlayerViewModel pvm;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvPlayerItemName);
        btPos = itemView.findViewById(R.id.btItemPos);
        iPlayer = itemView.findViewById(R.id.iPlayerItem);
        iClub= itemView.findViewById(R.id.iClubItem);
        start = itemView.findViewById(R.id.ratingStart);
        tvDate = itemView.findViewById(R.id.tvPlayerItemDate);


        itemView.setOnClickListener(v -> {
            pvm = new ViewModelProvider((ViewModelStoreOwner) itemView.getContext()).get(PlayerViewModel.class);

            alert = new AlertDialog.Builder(v.getContext());                                    //guarda la ID del objeto Player para editarlo
            alert.setTitle(tvName.getText()).setIcon(R.drawable.default_player).setPositiveButton("EDIT", (dialog, which) -> {
                //asignamos ID a editar
                MainActivity.player_id = player.id;
                Intent i = new Intent(itemView.getContext(), MainActivity.class);
                Bundle b = new Bundle();
                b.putLong("edit", player.id);
                i.putExtras(b);
                itemView.getContext().startActivity(i);

                //elimina el objeto Player indicado
            }).setNegativeButton("DELETE", (dialog, which) -> {

                pvm.getPlayers().observe((LifecycleOwner) itemView.getContext(), players -> {
                    pvm.delete(player);
                });
            }).show();
        });


    }
}
