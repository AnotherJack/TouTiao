package cn.edu.cuc.toutiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.HashMap;

import cn.edu.cuc.toutiao.bean.LoginInfo;
import cn.edu.cuc.toutiao.retrofit.ApiService;
import cn.edu.cuc.toutiao.retrofit.ServiceGenerator;
import cn.edu.cuc.toutiao.util.AESUtils;
import cn.edu.cuc.toutiao.util.Base64Decoder;
import cn.edu.cuc.toutiao.util.Base64Encoder;
import cn.edu.cuc.toutiao.util.RSAUtils;
import cn.edu.cuc.toutiao.util.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText phone_et;
    private EditText email_et;
    private EditText name_et;
    private EditText pwd_et;
    private EditText sex_et;
    private EditText university_et;
    private EditText school_et;
    private EditText major_et;
    private EditText education_et;
    private Button register_btn;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://icuc.cuc.edu.cn/proj/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        phone_et = (EditText) findViewById(R.id.phone_et);
        email_et = (EditText) findViewById(R.id.email_et);
        name_et = (EditText) findViewById(R.id.name_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        sex_et = (EditText) findViewById(R.id.sex_et);
        university_et = (EditText) findViewById(R.id.university_et);
        school_et = (EditText) findViewById(R.id.school_et);
        major_et = (EditText) findViewById(R.id.major_et);
        education_et = (EditText) findViewById(R.id.education_et);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
    }

    private void doRegister(){
        String phone = phone_et.getText().toString();
        String email = email_et.getText().toString();
        String name = name_et.getText().toString();
        String pwd = pwd_et.getText().toString();
        String sex = sex_et.getText().toString();
        String university = university_et.getText().toString();
        String school = school_et.getText().toString();
        String major = major_et.getText().toString();
        String education = education_et.getText().toString();

        if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(email)){
            Toast.makeText(this,"手机与邮箱请至少填写一项",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
            return;
        }
        SPUtils spUtils = SPUtils.getInstance();
        String rsa_pulic_key = spUtils.getString("rsa_pulic_key");
        PublicKey rsaPublicKey = RSAUtils.getPublicKey(rsa_pulic_key);
        HashMap<String,String> map = new HashMap<>();
        //未加密的aes key
        final String aesKey =  AESUtils.generateKey();

        String aes = null;
        try {
            byte[] aesBytes = RSAUtils.encryptByPublicKey(aesKey.getBytes(), rsaPublicKey.getEncoded());
            aes = Base64Encoder.encode(aesBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("aes",aes);
        map.put("rsa_type","1");
        map.put("gid",spUtils.getString("gid"));
        map.put("expr","");
        map.put("phone",AESUtils.encrypt(aesKey,phone));
        map.put("email",AESUtils.encrypt(aesKey,email));
        map.put("name",AESUtils.encrypt(aesKey,name));
        map.put("pwd",AESUtils.encrypt(aesKey,pwd));
        map.put("sex",AESUtils.encrypt(aesKey,sex));
        map.put("university",AESUtils.encrypt(aesKey,university));
        map.put("school",AESUtils.encrypt(aesKey,school));
        map.put("major",AESUtils.encrypt(aesKey,major));
        map.put("education",AESUtils.encrypt(aesKey,education));

        Call<String> call = apiService.doRegister(map);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String loginInfoStr = response.body();
//                Gson gson = new Gson();
                Log.d("LoginInfo-----",loginInfoStr);
                loginInfoStr = AESUtils.decrypt(aesKey,loginInfoStr);
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(loginInfoStr,LoginInfo.class);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
