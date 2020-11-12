package com.example.realkepstone.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.realkepstone.MainActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.data.FoodAfter;
import com.example.realkepstone.server.ApiInterface;
import com.example.realkepstone.server.HttpClient;
import com.example.realkepstone.ui.ResultFragment;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuGalleryFragment extends Fragment {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    public static final int PICK_IMAGE = 100;
    private TextView text;
    private ImageView menu;
    private ImageView ganpan;
    Response<FoodAfter> response;
    Context context;
    ApiInterface apiInterface;
    /* @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         getParentFragmentManager().setFragmentResultListener("requestKey", this, new androidx.fragment.app.FragmentResultListener() {
             @Override
             public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                 String result = bundle.getString("bundleKey");
                 assert result != null;
             }
         });
     }*/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menugallery, container, false);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(1000, TimeUnit.MINUTES)
                .readTimeout(1000000, TimeUnit.SECONDS)
                .writeTimeout(1000000, TimeUnit.SECONDS).build();






        apiInterface = HttpClient.getRetrofit().create( ApiInterface.class );
        imageview = (ImageView) root.findViewById(R.id.imageView);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            android.net.Uri selectedImage = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                imageview.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }


            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            MainActivity activity = (MainActivity) getActivity();
            String filePath=activity.getRealPathFromURI(selectedImage);
            File file = new File(filePath);
            Log.d("TedPark", filePath);


            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-file"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("image/jpeg"), "file");

//            Log.d("THIS", data.getData().getPath());
            retrofit2.Call<FoodAfter> req = apiInterface.postImage(body, name);
            req.enqueue(new Callback<FoodAfter>() {
                @Override
                public void onResponse(Call<FoodAfter> call, Response<FoodAfter> response) {
                    if(response.code()==201) {
                        response.body(); // have your all data
                       /* List<String> id = response.body().getFoodKorName();
                        List<String> engName = response.body().getFoodEngName();
                        List<String> descreption = response.body().getFoodDescription();
                        List<List<String>> ingredients = response.body().getFoodIngredients();
                        List<List<String>> allergy = response.body().getFoodAllergy();
                        text.setText(id+"+"+engName+"+"+descreption);*/
                        //Put the value
                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                        ResultFragment mfragment=new ResultFragment();

                        Bundle bundle=new Bundle();
                        //  bundle.putSerializable("json", response.body());

                        bundle.putSerializable("json", response.body());

                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transaction.replace(R.id.Main_Frame, mfragment,"not");
                        transaction.commit();


                        //  MainActivity activity = (MainActivity) getActivity();
                        // activity.setFrag(4);
                    }
                    Log.d("TedPark", String.valueOf(response.code()));
                }
                @Override
                public void onFailure(Call<FoodAfter> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("TedPark", "ㄹ");
                    Toast.makeText(getContext().getApplicationContext(), "네트워크를 확인해주세요.", Toast.LENGTH_LONG).show();

                }
            });

        }

    }

}