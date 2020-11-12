package com.example.realkepstone.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realkepstone.MainActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.adapter.RecyclerAdapter;
import com.example.realkepstone.data.Food;
import com.example.realkepstone.data.FoodAfter;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    public ArrayList<Food> listData = new ArrayList<>();
    public ArrayList<Food> trash = new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(Food data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    public void deleteItem(Food data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.remove(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageButton order;
        private TextView textView1;
        private TextView textView2;
        private ImageView imageView1;
        private Food data;
        private int position;
        private RelativeLayout linear;

        ItemViewHolder(View itemView) {
            super(itemView);

            order = itemView.findViewById(R.id.order);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView1 = itemView.findViewById(R.id.imageView1);
            linear=itemView.findViewById(R.id.Linear);
        }

        void onBind(Food data, int position) {
            this.data = data;
            this.position = position;

            textView1.setText(data.getKor());
            textView2.setText(data.getEng());
            imageView1.setImageResource(data.getResId());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            imageView1.setOnClickListener(this);
            order.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.textView1:
                    break;
                case R.id.textView2:
                    break;
                case R.id.imageView:
                    break;
                case R.id.order:
                    trash.add(data);
                    deleteItem(data);
                    selectedItems.delete(position);
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    // 클릭된 position 저장
                    prePosition = position;
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemChanged(position);

                    break;
            }
        }


        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(100);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    linear.getLayoutParams().height = value;
                    linear.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    linear.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
}