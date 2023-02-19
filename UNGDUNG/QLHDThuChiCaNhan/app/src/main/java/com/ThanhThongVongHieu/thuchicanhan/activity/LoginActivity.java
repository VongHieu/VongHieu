package com.ThanhThongVongHieu.thuchicanhan.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.ThanhThongVongHieu.thuchicanhan.database.DaoTaiKhoan;
import com.ThanhThongVongHieu.thuchicanhan.mdel.TaikhoanMatKhau;
import com.ThanhThongVongHieu.thuchicanhan.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnLog;
    private ImageView login;
    EditText edtTaiKhoan, edtMatKhau;
    TextView txtReg;
    CheckBox cbLuuThongTin;
    DaoTaiKhoan tkDao;
    TaikhoanMatKhau checkLogin;
    LinearLayout linearLayout;
    Animation animation;
    private int requestCode;
    private int resultCode;
    @Nullable
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout = findViewById(R.id.linearLayoutlogin);
        init();
        animation = AnimationUtils.loadAnimation(this, R.anim.dangnhap_dangky_animation);
        linearLayout.startAnimation(animation);
        layThongTin();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,options);
                Intent  intent =googleSignInClient.getSignInIntent();
                startActivityForResult(intent,9);
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean xetTk = false;
                tkDao = new DaoTaiKhoan(LoginActivity.this);
                String tenTK = edtTaiKhoan.getText().toString();
                String mk = edtMatKhau.getText().toString();
                checkLogin = tkDao.getUserByUserName(tenTK);

                if (checkLogin.getTenTaiKhoan().matches(tenTK) && checkLogin.getMatKhau().matches(mk)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    luuThongTin();
                    startActivity(new Intent(LoginActivity.this, ContainerActivity.class));
                    overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);

                } else {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void luuThongTin() {
        SharedPreferences sharedPreferences = getSharedPreferences("sinhvien", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String ten = edtTaiKhoan.getText().toString();
        String pass = edtMatKhau.getText().toString();
        boolean check = cbLuuThongTin.isChecked();
        if (!check) {
            editor.clear();
        } else {
            editor.putString("tennguoidung", ten);
            editor.putString("matkhau", pass);
            editor.putBoolean("checkstatus", check);
        }
        editor.commit();
    }

    private void layThongTin() {
        SharedPreferences sharedPreferences = getSharedPreferences("sinhvien", MODE_PRIVATE);

        boolean check = sharedPreferences.getBoolean("checkstatus", false);
        if (check) {
            String tenNguoiDung = sharedPreferences.getString("tennguoidung", "");
            String matKhau = sharedPreferences.getString("matkhau", "");
            edtTaiKhoan.setText(tenNguoiDung);
            edtMatKhau.setText(matKhau);
        } else {
            edtTaiKhoan.setText("");
            edtMatKhau.setText("");
        }
        cbLuuThongTin.setChecked(check);
    }

    private void init() {
        edtTaiKhoan = findViewById(R.id.edtUserName);
        edtMatKhau = findViewById(R.id.edtPassword);
        btnLog = findViewById(R.id.btnLog);
        cbLuuThongTin = findViewById(R.id.cbLuuThongTin);
        login = findViewById(R.id.imglogingg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            String tk = data.getStringExtra("taikhoan");
            String mk = data.getStringExtra("matkhau");
            edtTaiKhoan.setText(tk);
            edtMatKhau.setText(mk);
        }
        if(requestCode==9){
            Task<GoogleSignInAccount> listTk = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(listTk);
        }
    }

    private void handleSignIn(Task<GoogleSignInAccount> listTk) {
        GoogleSignInAccount account = listTk.getResult();
        if (account!= null){
            startActivity(new Intent(LoginActivity.this, ContainerActivity.class));
            overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickReg(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(i, 999);
        overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
    }
}


