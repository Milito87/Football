package org.izv.ad.jmunoz.footballdb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.izv.ad.jmunoz.footballdb.view.adapter.viewholder.ClubViewHolder;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubViewHolder>{

    public List<Club> clubList;
    private Context context;

    public ClubAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {

        holder.club = clubList.get(position);
        holder.tvName.setText(holder.club.name);
        holder.start.setProgress(holder.club.star);
        holder.tvDescr.setText(holder.club.description);
        Picasso.get().load(holder.club.url).into(holder.iClub);

    }

    //devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        if(clubList == null) {
            return 0;
        }
        return clubList.size();
    }

    //establece la lista a usar en el adaptador
    public void setClubList(List<Club> listClub) {

        this.clubList = listClub;
        notifyDataSetChanged();
    }

}
