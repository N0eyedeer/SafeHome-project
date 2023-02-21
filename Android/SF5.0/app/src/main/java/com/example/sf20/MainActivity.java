package com.example.sf20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText edit_userName;
    private EditText edit_password;
    private Button buttonLogin;
    public static getSensor s;
    private CheckBox remember;
    private String edit_userNameValue,edit_passwordValue;
    private SharedPreferences sp;
    //双击返回键退出
    private long mExitTime = System.currentTimeMillis();  //为当前系统时间，单位：毫秒
    private void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();

            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - mExitTime < 800) {
            MainActivity.this.finish();   //关闭本活动页面
        }
        else{
            toast("Press the back key again to exit!");
            mExitTime = System.currentTimeMillis();   //这里赋值最关键，别忘记
        }
    }
    //双击返回键退出
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        remember = (CheckBox) findViewById(R.id.cb_1);
        buttonLogin = (Button) findViewById(R.id.btn_login);
        edit_userName=(EditText) findViewById(R.id.et_1);//delete
        edit_password=(EditText) findViewById(R.id.et_2);//delete

        if(sp.getBoolean("ISCHECK",false)){
            remember.setChecked(true);
            edit_userName.setText(sp.getString("USER_NAME",""));
            edit_password.setText(sp.getString("PASSWORD",""));
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_userNameValue = edit_userName.getText().toString();
                edit_passwordValue = edit_password.getText().toString();
                if (edit_userNameValue.equals("Hex")&&edit_passwordValue.equals("666")){
                    Toast.makeText(MainActivity.this, "Success！", Toast.LENGTH_SHORT).show();
                    if (remember.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME",edit_userNameValue);
                        editor.putString("PASSWORD",edit_passwordValue);
                        editor.commit();
                    }
                    //跳转界面
                    s = new getSensor();
                    s.run();
                    Intent intent = new Intent(MainActivity.this,FragAct.class);
                    MainActivity.this.startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Wrong id or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(remember.isChecked()){
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK",true).commit();
                }
                else{
                    System.out.println("记住密码未选中");
                    sp.edit().putBoolean("ISCHECK",false).commit();
                }
            }
        });
        /*buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextUerName=findViewById(R.id.et_1);
                final EditText editTextPassword=findViewById(R.id.et_2);
                String userName = editTextUerName.getText().toString();
                boolean name = userName.equals("Hex");
                String password = editTextPassword.getText().toString();
                boolean pass = password.equals("666");

                if (TextUtils.isEmpty(editTextUerName.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (name&&pass) {
                    Intent intent = new Intent(MainActivity.this, AlphaActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                }}});*/
    }
}
