package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityNeighbourDetailsBinding;

public class NeighbourDetailsActivity extends AppCompatActivity {

    private ActivityNeighbourDetailsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
    }

    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, NeighbourDetailsActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}