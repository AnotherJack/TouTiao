package cn.edu.cuc.toutiao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.RegisterActivity;
import cn.edu.cuc.toutiao.util.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView username;

    private String uid;
    private String gid;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        username = rootView.findViewById(R.id.username);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initId();
        if(uid.equals("")){
            //没登录
            username.setText("登录／注册");
            username.setClickable(true);
            username.setOnClickListener(this);
        }else {
            //登录了
            username.setText("username");
            username.setClickable(false);
        }
    }

    private void initId(){
        SPUtils spUtils = SPUtils.getInstance();
        uid = spUtils.getString("uid", "");
        gid = spUtils.getString("gid", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.username:
                showLoginDialog();
                break;
        }
    }

    private void showLoginDialog(){
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_login);
        EditText username_et = window.findViewById(R.id.username_et);
        EditText password_et = window.findViewById(R.id.password_et);
        Button login_btn = window.findViewById(R.id.login_btn);
        Button register_btn = window.findViewById(R.id.register_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
