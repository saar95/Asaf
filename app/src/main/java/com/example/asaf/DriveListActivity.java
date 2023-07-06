package com.example.asaf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;

public class DriveListActivity extends AppCompatActivity implements RecycleViewIterface, SearchView.OnQueryTextListener {
    private ArrayList<DriveModel> filteredMovieModels = new ArrayList<>();
    ArrayList<DriveModel> DriveList = DriveModel.getInstance().getDriveList();

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_list);

        //SearchView searchView = findViewById(R.id.searchView);
        //searchView.setOnQueryTextListener(this);
        RecyclerView rv = findViewById(R.id.mRcycleView);

        adapter = new Adapter(this, DriveList,this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
//        ArrayList<DriveModel> movies = filteredMovieModels.isEmpty() ? DriveList : filteredMovieModels;
//
//        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
//        intent.putExtra("movieName", movies.get(position).getMovieName());
//        intent.putExtra("movieGenre", movies.get(position).getMovieGenre());
//        intent.putExtra("movieYear", movies.get(position).getMovieYear());
//        intent.putExtra("movieImage", movies.get(position).getImage());
//        intent.putExtra("movieDesc", movies.get(position).getDescription());
//        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Handle search query submission
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Handle search query text changes
        // Update the RecyclerView based on the search query
        //filter(newText);
        return true;
    }

//    private void filter(String text) {
//        filteredMovieModels.clear();
//        for (DriveModel movie : DriveList) {
//            if (movie.getMovieName().toLowerCase().contains(text.toLowerCase())) {
//                filteredMovieModels.add(movie);
//            }
//        }
//        adapter.filterList(filteredMovieModels);
//    }

}