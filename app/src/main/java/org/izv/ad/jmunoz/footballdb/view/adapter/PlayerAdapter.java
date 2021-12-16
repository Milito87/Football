package org.izv.ad.jmunoz.footballdb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.view.adapter.viewholder.PlayerViewHolder;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder>{

    private List<Player> playerList;
    private List<Club> clubList;
    private Context context;

    public PlayerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        holder.player = playerList.get(position);
        holder.club = clubList.get((int) holder.player.club);
        Picasso.get().load(holder.player.url).into(holder.iPlayer);
        Picasso.get().load(holder.club.url).into(holder.iClub);
        holder.tvName.setText(holder.player.name);
        holder.tvDate.setText(holder.player.date); ;
        holder.start.setProgress(holder.player.star); ;
        holder.btPos.setText(holder.player.position); ;

    }

    //devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        if(playerList == null) {
            return 0;
        }
        return playerList.size();
    }

    //establece la lista de Player a usar en el adaptador
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        notifyDataSetChanged();
    }
    //establece la lista de Club a usar en el adaptador
    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
        notifyDataSetChanged();
    }

}
