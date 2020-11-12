package com.example.realkepstone.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realkepstone.MainActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.adapter.RecyclerAdapter;
import com.example.realkepstone.data.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;
    private ArrayList<Food> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        int user_no = 0;

        Bundle bundle = getArguments();

        if (bundle != null) {
            user_no = bundle.getInt("user_no");
        }

        Log.d("값", String.valueOf(user_no));

        getData();
        return rootView;
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("김치찌개", "삼겹살", "소주", "족발", "보쌈", "카레", "제육 볶음", "돈까스", "햄버거");
        List<String> listContent = Arrays.asList(
                "배추, 돼지고기, 고추, 콩",
                "돼지고기",
                "알코올",
                "돼지고기, 간장, 된장, 마늘",
                "돼지고기, 간장",
                "강황, 양파, 감자, 당근, 돼지고기",
                "돼지고기, 고추장, 간장, 양파",
                "돼지고기, 밀가루",
                "밀가루, 양상추, 소고기, 토마토, 양파, 특별한 소스까지"
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.kimchi,
                R.drawable.sam,
                R.drawable.soju,
                R.drawable.jok,
                R.drawable.bossan,
                R.drawable.care,
                R.drawable.je,
                R.drawable.care,
                R.drawable.care


        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Food food = new Food();
            food.setKor(listTitle.get(i));
            food.setEng(listContent.get(i));
            food.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(food);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}