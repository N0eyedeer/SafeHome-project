package com.example.sf20;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FragAct extends AppCompatActivity implements View.OnClickListener{
    private final Fragment fragment01 = new m01();
    private final Fragment fragment02 = new m02();
    private final Fragment fragment03 = new m03();
    private final Fragment fragment04 = new m04();
    private FragmentManager fm;

    private LinearLayout mTab01;
    private LinearLayout mTab02;
    private LinearLayout mTab03;
    private LinearLayout mTab04;

    private ImageButton mImg01;
    private ImageButton mImg02;
    private ImageButton mImg03;
    private ImageButton mImg04;

    /*public void onSavedInstanceState(Bundle outState){
        //手动保存
        outState.putBoolean(ARG_IS_HIDDEN, isHidden());
        super.onSaveInstanceState(outState);
    }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();

            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("记住密码已选中");
            Log.e(TAG, "onSaveInstanceState: 恢复相关状态！！");
            setContentView(R.layout.activity_frag);
            initView();
            initFragment();
            selectFragment(0);
            initEvent();


        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);



        /*selectFragment(2);
        selectFragment(3);
        selectFragment(1);*/

    }
    private void initFragment(){
        fm =getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.add(R.id.id_content,fragment01);
        transaction.add(R.id.id_content,fragment02);
        transaction.add(R.id.id_content,fragment03);
        transaction.add(R.id.id_content,fragment04);




        transaction.commit();
    }
    private void initView(){
        mTab01=(LinearLayout)findViewById(R.id.id_tab_weixin);
        mTab02=(LinearLayout)findViewById(R.id.id_tab_frd);
        mTab03=(LinearLayout)findViewById(R.id.id_tab_contact);
        mTab04=(LinearLayout)findViewById(R.id.id_tab_settings);


        mImg01=(ImageButton)findViewById(R.id.id_tab_weixin_img);
        mImg02=(ImageButton)findViewById(R.id.id_tab_frd_img);
        mImg03=(ImageButton)findViewById(R.id.id_tab_contact_img);
        mImg04=(ImageButton)findViewById(R.id.id_tab_settings_img);
    }
    private void hideFragment(FragmentTransaction transaction){

        transaction.hide(fragment02);
        transaction.hide(fragment01);
        transaction.hide(fragment03);
        transaction.hide(fragment04);
    }
    private void selectFragment(int i){
        FragmentTransaction transaction=fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:

                transaction.show(fragment01);
                mImg01.setImageResource(R.drawable.house1);
                break;
            case 1:
                transaction.show(fragment02);
                mImg02.setImageResource(R.drawable.circles1);
                break;
            case 2:
                transaction.show(fragment03);
                mImg03.setImageResource(R.drawable.light1);
                break;
            case 3:
                transaction.show(fragment04);
                mImg04.setImageResource(R.drawable.user1);
                break;
            default:
                break;
        }

        transaction.commit();
    }

    public void onClick(View v) {
        resetImgs();
        switch(v.getId()){
            case R.id.id_tab_weixin:
                selectFragment(0);
                break;
            case R.id.id_tab_frd:
                selectFragment(1);
                break;
            case R.id.id_tab_contact:
                selectFragment(2);
                break;
            case R.id.id_tab_settings:
                selectFragment(3);
                break;
            default:
                break;
        }
    }
    private void resetImgs(){
        mImg01.setImageResource(R.drawable.house);
        mImg02.setImageResource(R.drawable.circles);
        mImg03.setImageResource(R.drawable.light);
        mImg04.setImageResource(R.drawable.user);
    }
    private void initEvent(){

        mTab01.setOnClickListener((View.OnClickListener) this);
        mTab02.setOnClickListener((View.OnClickListener) this);

        mTab03.setOnClickListener((View.OnClickListener) this);
        mTab04.setOnClickListener((View.OnClickListener) this);
    }

}