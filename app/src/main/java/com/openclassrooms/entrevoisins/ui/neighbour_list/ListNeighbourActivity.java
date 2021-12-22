package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.openclassrooms.entrevoisins.databinding.ActivityListNeighbourBinding;

public class ListNeighbourActivity extends AppCompatActivity {

    private ActivityListNeighbourBinding b;

    ListNeighbourPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListNeighbourBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setSupportActionBar(b.toolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        b.container.setAdapter(mPagerAdapter);
        b.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(b.tabs));
        b.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(b.container));

    }

    public void addNeighbour(View view) {
        AddNeighbourActivity.navigate(this);
    }

    public void showNeighbour(View view) {
        NeighbourDetailsActivity.navigate(this);
    }
}
