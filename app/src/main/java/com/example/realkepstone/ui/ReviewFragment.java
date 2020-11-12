package com.example.realkepstone.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realkepstone.R;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realkepstone.adapter.ReviewAdapter;
import com.example.realkepstone.data.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewFragment extends Fragment {


    ImageButton review = null;
    ImageButton back = null;
    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    Context contex;
    TextView textView;
    ArrayList<Food> OrderList;
    SharedPreferences count;
    SharedPreferences Kor;
    SharedPreferences Eng;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_review, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView3);


        adapter = new ReviewAdapter();
        recyclerView.setAdapter(adapter);


        return rootView;
    }

}