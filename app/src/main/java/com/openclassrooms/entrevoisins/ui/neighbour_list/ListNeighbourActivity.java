package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityListNeighbourBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListNeighbourActivity extends AppCompatActivity {

    private ActivityListNeighbourBinding b;

/*    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;*/

    ListNeighbourPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListNeighbourBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        //ButterKnife.bind(this);

        setSupportActionBar(b.toolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        b.container.setAdapter(mPagerAdapter);
        b.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(b.tabs));
        b.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(b.container));

    }

    //@OnClick(R.id.add_neighbour)
    public void addNeighbour(View view) {
        AddNeighbourActivity.navigate(this);
    }
}
