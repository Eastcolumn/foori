package com.example.realkepstone.front;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.realkepstone.FrontActivity;
import com.example.realkepstone.MainActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.server.ApiInterface;
import com.example.realkepstone.server.HttpClient;
import com.example.realkepstone.server.ReqLoginData;
import com.example.realkepstone.server.ResLoginData;
import com.example.realkepstone.ui.ResultFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    ImageButton login = null;
    ImageButton join = null;
    ImageButton without = null;
    ImageButton findid = null;
    ImageButton findpw = null;
    FrontActivity frontActivity;

    private static String TAG = "MainActivity";
        ApiInterface api;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        EditText mEmailView;
        EditText mPasswordView;

        mEmailView = (EditText) root.findViewById(R.id.id);

        api = HttpClient.getRetrofit().create( ApiInterface.class );


        mPasswordView = (EditText) root.findViewById(R.id.pw);

        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }

        });
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });


        login = (ImageButton) root.findViewById(R.id.login);
        join = (ImageButton) root.findViewById(R.id.join);
        without = (ImageButton) root.findViewById(R.id.without);
        findid = (ImageButton) root.findViewById(R.id.findId);
        findpw = (ImageButton) root.findViewById(R.id.findPw);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrontActivity activity = (FrontActivity) getActivity();
                activity.loginfragmentChange();
            }
        });
        without.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrontActivity activity = (FrontActivity) getActivity();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrontActivity activity = (FrontActivity) getActivity();
                activity.tofindId();
            }
        });
        findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrontActivity activity = (FrontActivity) getActivity();
                activity.tofindPw();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FrontActivity activity = (FrontActivity) getActivity();

              //  activity.requestPostLogin(userID,userPW);
                String id, pw;
                id = mEmailView.getText().toString();
                pw= mPasswordView.getText().toString();
                requestPost(id, pw);
            }
        });

        return root;
    }
    /*
 - 아이디 없음: JsonResponse({"message": "INVALID_ID"}, status=401)
- 비밀번호 틀림: JsonResponse({"message": "INVALID_PASSWORD"}, status=402)
- 계정 비활성화 상태 (is_active가 0인 경우)
  : JsonResponse({"message": "NOT_ACTIVATE_ACCOUNT"}, status=402)
- 로그인 성공: JsonResponse({'access_token': token}, status=200)
     */
    public void requestPost(String id, String pw) {
        ReqLoginData reqLoginData = new ReqLoginData( id, pw);
        Call<ResLoginData> call = api.requestPostLogin( reqLoginData );

        // 비동기로 백그라운드 쓰레드로 동작
        call.enqueue( new Callback<ResLoginData>() {
            // 통신성공 후 텍스트뷰에 결과값 출력
            @Override
            public void onResponse(Call<ResLoginData> call, Response<ResLoginData> response) {
                if(response.code()==200){
                    int user_no=response.body().getUser_no();


                    Bundle bundle = new Bundle();
                    bundle.putInt("user_no", user_no);
                    Log.d("loginuserno", String.valueOf(user_no));

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("user_no",user_no);

                    startActivity(intent);
                }
                else if(response.code()==201){

                    int user_no=response.body().getUser_no();

                    Log.d("loginuserno", String.valueOf(user_no));
                    Log.d("sex!", String.valueOf(response.code()));

                    Bundle bundle = new Bundle();
                    bundle.putInt("user_no", user_no);
                    FrontActivity activity = (FrontActivity) getActivity();
                    activity.joinfragmentChange(bundle);


                }


                else if(response.code()==401){
                    Toast.makeText(getContext().getApplicationContext(), "없는 아이디입니다.", Toast.LENGTH_LONG).show();
                }
                else if(response.code()==402){
                    Toast.makeText(getContext().getApplicationContext(), "계정이 비활성 상태이거나 비밀번호가 틀립니다.", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getContext().getApplicationContext(), "잘못된 아이디나 비밀번호입니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResLoginData> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "네트워크 연결을 확인해주세요.", Toast.LENGTH_LONG).show();


            }
        } );
    }



}