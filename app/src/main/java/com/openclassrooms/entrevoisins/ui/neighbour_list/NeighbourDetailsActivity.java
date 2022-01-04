package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.databinding.ActivityNeighbourDetailsBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.text.Normalizer;

public class NeighbourDetailsActivity extends AppCompatActivity {

    private ActivityNeighbourDetailsBinding b;
    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private static boolean showFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        // Cache le titre de l'actionbar
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        getInfo();  // Récupère les informations du voisin sélectionné
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    // Démarre l'activité en chargeant le bon voisin
    public static void navigate(FragmentActivity activity, int position, boolean favorites) {
        Intent intent = new Intent(activity, NeighbourDetailsActivity.class);
        intent.putExtra("position", position);
        showFavorites = favorites;
        ActivityCompat.startActivity(activity, intent, null);
    }

    // Charge les informations du voisin sélectionné
    @SuppressLint("SetTextI18n")
    private void getInfo() {
        int position = getIntent().getIntExtra("position", -1); // Récupère la position de l'item cliqué
        mApiService = DI.getNeighbourApiService();
        if (showFavorites)
            mNeighbour = mApiService.getFavoriteNeighbours().get(position);
        else
            mNeighbour = mApiService.getNeighbours().get(position);

        // Enlève les accents dans le nom pour la fausse url
        String normalizedName = Normalizer.normalize(mNeighbour.getName(), Normalizer.Form.NFD);
        String urlName = normalizedName.replaceAll("\\p{M}", "").toLowerCase();

        // Récupération des informations sur le voisin récupéré
        Glide.with(b.neighbourDetailsAvatar).load(mNeighbour.getAvatarUrl()).into(b.neighbourDetailsAvatar);
        b.neighbourDetailsAvatarName.setText(mNeighbour.getName());
        b.cardInfoName.setText(mNeighbour.getName());
        b.cardInfoLocation.setText(mNeighbour.getAddress());
        b.cardInfoPhone.setText(mNeighbour.getPhoneNumber());
        b.cardInfoUrl.setText("www.facebook.fr/" + urlName);
        b.cardAboutContent.setText(mNeighbour.getAboutMe());
        b.fabFavorite.setSelected(mNeighbour.isFavorite());
    }

    // Ajoute/supprime le voisin des favoris
    public void switchFavorite(View view) {
        if (b.fabFavorite.isSelected()) {   // Si le bouton est activé,
            mApiService.deleteFavoriteNeighbour(mNeighbour);    // on retire des favoris
            b.fabFavorite.setSelected(false);                   // et passe le bouton à désactivé.
        } else {    // Si le bouton est désactivé,
            mApiService.addFavoriteNeighbour(mNeighbour);   // on ajoute le voisin des favoris
            b.fabFavorite.setSelected(true);                // et passe le bouton à activé.
        }
    }

}