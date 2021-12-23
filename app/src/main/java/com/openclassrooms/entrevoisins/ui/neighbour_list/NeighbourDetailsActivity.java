package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.databinding.ActivityNeighbourDetailsBinding;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;

import java.text.Normalizer;

public class NeighbourDetailsActivity extends AppCompatActivity {

    private ActivityNeighbourDetailsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        getInfo();
    }

    public static void navigate(FragmentActivity activity, int position) {
        Intent intent = new Intent(activity, NeighbourDetailsActivity.class);
        intent.putExtra("position", position);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @SuppressLint("SetTextI18n")
    private void getInfo() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        Neighbour neighbour = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(position);

        // Enlève les accents dans le nom pour la fausse url
        String normalizedName = Normalizer.normalize(neighbour.getName(), Normalizer.Form.NFD);
        String urlName = normalizedName.replaceAll("\\p{M}", "").toLowerCase();

        // Récupération des informations sur le voisin récupéré
        Glide.with(b.neighbourDetailsAvatar).load(neighbour.getAvatarUrl()).into(b.neighbourDetailsAvatar);
        b.neighbourDetailsAvatarName.setText(neighbour.getName());
        b.cardInfoName.setText(neighbour.getName());
        b.cardInfoLocation.setText(neighbour.getAddress());
        b.cardInfoPhone.setText(neighbour.getPhoneNumber());
        b.cardInfoUrl.setText("www.facebook.fr/"+urlName);
        b.cardAboutContent.setText(neighbour.getAboutMe());
    }
}