package com.example.realkepstone.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.realkepstone.MainActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.data.Food;
import com.example.realkepstone.data.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OrderFragment extends Fragment {

    ArrayList<Food> OrderList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);





        return root;

    }
}
