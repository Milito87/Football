package org.izv.ad.jmunoz.footballdb.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import org.izv.ad.jmunoz.footballdb.R;
import org.izv.ad.jmunoz.footballdb.model.entity.Player;
import org.izv.ad.jmunoz.footballdb.view.MainActivity;
import org.izv.ad.jmunoz.footballdb.viewmodel.ClubViewModel;
import org.izv.ad.jmunoz.footballdb.viewmodel.PlayerViewModel;
import java.io.IOException;
import java.util.Calendar;

public class PlayerFragment extends Fragment {

    private static final int SELECT_IMAGE = 200;

    private ImageView iPlayer, iClub;
    private TextInputEditText etName, etDate;
    private RatingBar start;
    private Button btBack, btClean, btAdd, btPos;
    private EditText url;
    private DatePickerDialog data;

    private PlayerViewModel pvm;
    private ClubViewModel cvm;
    private boolean existPlayer;
    private  int image_id;
    private AlertDialog.Builder alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        //cierra el fragment y vuelve al anterior
        btBack.setOnClickListener(v -> {
            MainActivity.player_id = (long) -1;
            NavHostFragment.findNavController(this).popBackStack();
        });

        //borra Player o limpia los campos
        btClean.setOnClickListener(v -> {
            if(btClean.getText().toString().compareTo(getString(R.string.delete)) == 0) {
               pvm.getPlayers().observe(getActivity(), players -> {
                   for(int i=0;i<players.size();i++){
                       if(MainActivity.player_id==players.get(i).getId()){
                           pvm.delete(players.get(i));
                           NavHostFragment.findNavController(this).popBackStack();
                           MainActivity.player_id = (long) -1;

                       }
                   }
               });
            }
            else{
                clean();
            }
        });

        btAdd.setOnClickListener(v -> {
            if(validate(v)){
                addPlayer();
                NavHostFragment.findNavController(this).popBackStack();
            }
            else{
                Snackbar.make(view,getString(R.string.not_validated), Snackbar.LENGTH_SHORT).show();
            }
        });

        btPos.setOnClickListener(v -> {
            loadPos();
        });

        etDate.setOnClickListener(v -> {
                getDate(v);
        });

        iClub.setOnClickListener(v -> {
            if(image_id > 5 || image_id < 0){
                image_id=0;
            }
            setClubImage(image_id);
            image_id++;
        });

        iPlayer.setOnClickListener(v -> {

            url.setHint(getString(R.string.put_url));
               alert = new AlertDialog.Builder(v.getContext());
               alert.setIcon(R.drawable.default_player).setView(url).setTitle(getString(R.string.load_img)).setNeutralButton(getString(R.string.cancel), (dialog, which) -> {
                   //close dialog
               }).setPositiveButton(getString(R.string.search_gallery), (dialog, which) -> {
                    loadFromGallery();
               }).setNegativeButton(getString(R.string.load_url), (dialog, which) -> {
                   Picasso.get().load(url.getText().toString()).into(iPlayer);
                   iPlayer.setContentDescription(getString(R.string.img_player));
               }).show();
        });

    }

    private void init(View view){

        iPlayer = view.findViewById(R.id.iPlayerAdd);
        iClub = view.findViewById(R.id.iClubAdd);
        image_id = -1;
        etName = view.findViewById(R.id.etPlayerAddName);
        etDate = view.findViewById(R.id.etPlayerAddDate);
        start = view.findViewById(R.id.ratingStartAdd);
        url = new EditText(view.getContext());

        btBack = view.findViewById(R.id.btAddBack);
        btClean = view.findViewById(R.id.btAddClean);
        btAdd = view.findViewById(R.id.btAddPlayer);
        btPos = view.findViewById(R.id.btPos);
        btPos.setText("");

        cvm = new ViewModelProvider(getActivity()).get(ClubViewModel.class);
        pvm = new ViewModelProvider(getActivity()).get(PlayerViewModel.class);

        if(MainActivity.player_id > -1) {
            loadPlayer(MainActivity.player_id, view);
            image_id =Integer.parseInt(String.valueOf(MainActivity.player_id));
            btClean.setText(getString(R.string.delete));
            btAdd.setText(getString(R.string.update));
        }
        else{
            btClean.setText(getString(R.string.clean));
            btAdd.setText(getString(R.string.add));
        }

    }

    //limpia y pone pordefeco los campos de Player
    private void clean(){

        iPlayer.setImageResource(R.drawable.default_player);
        iClub.setImageResource(R.drawable.default_logo);

        etName.setText("");
        etDate.setText("");

        start.setNumStars(0);

    }

    //carga los datos del Player indicado
    private void loadPlayer(long id, View view){

        pvm.getPlayers().observe(getActivity(), players -> {

                int i;
                for (i = 0; i < players.size(); i++) {
                    if (id == players.get(i).getId()) {
                        break;
                    }
                }
                etName.setText(players.get(i).name);
                etDate.setText(players.get(i).date);
                Picasso.get().load(players.get(i).url).into(iPlayer);
                iPlayer.setContentDescription(getString(R.string.img_player));
                loadLogoFromClubDB(i, view);
                start.setProgress(players.get(i).star);
                btPos.setText(players.get(i).position);
                url.setText(players.get(i).url);

        });

    }

    //establece la posicion del Player
    private void loadPos(){
        switch(btPos.getText().toString()){
            case "":
            case "STRIKER":
                btPos.setText(getString(R.string.goalkeeper));
                btPos.setBackgroundColor(getResources().getColor(R.color.yellow_900));
                break;
            case "GOALKEEPER":
                btPos.setText(getString(R.string.defense));
                btPos.setBackgroundColor(getResources().getColor(R.color.blue_900));
                break;
            case "DEFENSE":
                btPos.setText(getString(R.string.midfield));
                btPos.setBackgroundColor(getResources().getColor(R.color.green_900));
                break;
            case "MIDFIELD":
                btPos.setText(getString(R.string.striker));
                btPos.setBackgroundColor(getResources().getColor(R.color.red_900));
                break;
        }
    }

    //comprueba campos y existencia de Player para validar añadirlo
    private boolean validate(View view){

        if(etName.getText().toString().isEmpty() ||
                iPlayer.getContentDescription().toString().compareTo(getString(R.string.img_default)) == 0 ||
                etDate.getText().toString().isEmpty() ||
                start.getProgress() < 1 || image_id < 0 ||
                btPos.getText().toString().compareTo("") == 0){
            return false;
        }
        else{
            if(isExist(view)){
                return false;
            }
            else{
                return true;
            }

        }

    }

    //lanza DataPicker y establece la fecha elegida en el EditText
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDate(View view){

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        data = new DatePickerDialog(view.getContext(), (view1, year1, month1, dayOfMonth) ->
                etDate.setText(dayOfMonth + "/" + (month1+1) + "/" + year1), day, month , year);
        data.show();
    }

    //añade un Player
    private void addPlayer(){
        Player p = new Player();

        p.name = etName.getText().toString();
        p.club = image_id-1;
        p.date = etDate.getText().toString();
        p.url = url.getText().toString();
        p.position = btPos.getText().toString();
        p.star = start.getProgress();
        if(MainActivity.player_id == -1){
            pvm.insert(p);
        }
        else{
            p.id = MainActivity.player_id;
            pvm.update(p);
            MainActivity.player_id = (long) -1;
        }


    }

    //comprueba si existe Player
    private boolean isExist(View view){

        pvm.getPlayers().observe(getActivity(), players -> {
            for(int i=0;i< players.size();i++) {
                if (players.get(i).name.compareTo(etName.getText().toString()) == 0) {
                    existPlayer = true;
                }
                else{
                    existPlayer =false;
                }
            }

        });
        return existPlayer;
    }

    //establece la imagen del club indicado
    private void setClubImage(int i){

        cvm.getClubes().observe(getActivity(), clubs -> {
            Picasso.get().load(clubs.get(i).url).into(iClub);
        });

    }

    //abre la galeria del telefono
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {

            }
        }
    }

    //carga imagen desde la galeria
    private void loadFromGallery(){

        Intent intent = new Intent();
        intent.setType(getString(R.string.image_path));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),SELECT_IMAGE);

    }

    //carga el logo desde la basedatos de clubs
    private void loadLogoFromClubDB(int image, View view){

        pvm.getPlayers().observe((LifecycleOwner) view.getContext(), players -> {
            cvm.getClubes().observe((LifecycleOwner) view.getContext(), clubs -> {

                Picasso.get().load(clubs.get((int) players.get(image).club).url).into(iClub);

                });
        });
    }
}
