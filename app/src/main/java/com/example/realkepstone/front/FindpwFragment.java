package com.example.realkepstone.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realkepstone.FrontActivity;
import com.example.realkepstone.R;
import com.example.realkepstone.server.ApiInterface;
import com.example.realkepstone.server.FindIdData;
import com.example.realkepstone.server.FindpwData;
import com.example.realkepstone.server.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindpwFragment extends Fragment {
    ApiInterface api;

    EditText email;
    EditText id;

    ImageButton send=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findpw, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        id = (EditText) root.findViewById(R.id.id);
        email = (EditText) root.findViewById(R.id.email);
        send = (ImageButton) root.findViewById(R.id.send);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail= email.getText().toString();
                String userId= id.getText().toString();

                requestPost(userId, userEmail);
            }
        });

        return root;
    }

    public void requestPost(String userId, String userEmail) {
        FindpwData findpwData = new FindpwData(userId, userEmail);
        Call<FindpwData> call = api.requestPw( findpwData );

/*
- 없는 이메일: JsonResponse({"message": "INVALID_EMAIL"}, status=401)
- 해당 메일로 메일 전송 성공:
   JsonResponse({"message": "SEND_MAIL_SUCCESS"}, status=200)

 */
        call.enqueue( new Callback<FindpwData>() {
            @Override
            public void onResponse(Call<FindpwData> call, Response<FindpwData> response) {
                if(response.code()==200){
                    Toast.makeText(getContext().getApplicationContext(), "해당 이메일 주소로 임시 비밀번호가 발송되었습니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_LONG).show();
                    FrontActivity activity = (FrontActivity) getActivity();
                    activity.fromfindId();

                }else if(response.code()==401){
                    Toast.makeText(getContext().getApplicationContext(), "존재하지 않는 아이디거나 이메일이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                else if(response.code()==402){
                    Toast.makeText(getContext().getApplicationContext(), "비밀번호가 비밀번호 재입력과 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext().getApplicationContext(), "올바르지 않은 정보입니다..", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FindpwData> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "네트워크 연결을 확인해주세요.", Toast.LENGTH_LONG).show();
            }
        } );
    }

}
