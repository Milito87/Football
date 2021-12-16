package org.izv.ad.jmunoz.footballdb.view.adapter.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Club;
import org.izv.ad.jmunoz.footballdb.view.fragment.PlayerFragment;

public class ClubViewHolder extends RecyclerView.ViewHolder{

    public Club club;
    public ImageView iClub;
    public TextView tvName, tvDescr;
    public RatingBar start;

    public ClubViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvClubItemName);
        tvDescr = itemView.findViewById(R.id.tvClubItemDescr);
        iClub = itemView.findViewById(R.id.iClubItemLogo);
        start = itemView.findViewById(R.id.ratingStartItemCLub);

        //guarda un objeto de la clase club
        itemView.setOnClickListener(v -> {

            Intent intent = new Intent(itemView.getContext(), PlayerFragment.class);
            intent.putExtra("club", club);
            itemView.getContext().startActivity(intent);
        });

    }
}
