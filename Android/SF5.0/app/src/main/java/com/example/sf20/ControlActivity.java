package com.example.sf20;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControlActivity extends AppCompatActivity {

    TextView mtem;
    TextView mhum;
    TextView msmo;
    TextView mcus;
    int flag=0,aflag=0;
    private SensorManager mSensorManager= null;
    private ThermometerView thermometerTv;
    private Sensor mSensor = null;
    private float x, y, z;
    Handler h = null;
    Vibrator vibrator;
    int state_receive=1;
    public String warning_ren;
    public String warning_zhen;
    public String warning_huo;


    RecyclerView mRecyclerView;
    List<News> mNewsList = new ArrayList<>();
    MyAdapter mMyAdapter=new MyAdapter();;
    public static class News {
        public String tem; // temperature
        public String hum; //humidity
        public String smo; // temperature
        public String cus; //humidity

    }
    SensorEventListener lsn = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            x = event.values[SensorManager.DATA_X];
            y = event.values[SensorManager.DATA_Y];
            z = event.values[SensorManager.DATA_Z];
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
    };
    @Override
    public void onResume(){
        //3.注册SensorEventListener，调用registerListener()方法来注册,第三个参数是检测的灵敏精确度,
        mSensorManager.registerListener(lsn, mSensor, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }
    @Override
    public void onPause(){
        mSensorManager.unregisterListener(lsn);
        super.onPause();
    }
    void onezero(String exp1,String exp2,String exp3,int state){
        int exp=0;

        vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);

        if ((/*Objects.equals(exp1, "1") ||*/ Objects.equals(exp2, "1") || Objects.equals(exp3, "1"))&&state_receive==1){//有报警、可接收就振动
            if(Objects.equals(exp3, "1"))
            //add the warning message to m03
                m03.mNewsList.add(new m03.News("   Warning!","There are vibrations!Please check the environment!"));
            if(Objects.equals(exp2, "1"))
                m03.mNewsList.add(new m03.News("   Warning!","There are smoke!Please check the environment!"));


            long[] pattern = {100, 200, 100, 200}; // 100ms后开始震动200ms，然后再停止100ms，再震动200ms
            Log.e(TAG, flag+"warning！！");
            vibrator.vibrate(pattern, 0);
            flag=1;//aflag=1;
            if(aflag==0){
                //1创建对象
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//2设置属性
                builder.setTitle("Hint");
                builder.setMessage("Warning!!");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (flag==1){//正在震动
                            Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "done!@#$%^%$#@!@#$$#！");
                            vibrator.cancel();
                            state_receive=0;//接收状态0
                            flag=0;
                            aflag=0;
                        }

                    }
                });

//3显示
                builder.show();
                aflag=1;
            }


        }
        else if((/*Objects.equals(exp1, "0") &&*/ Objects.equals(exp2, "0") && Objects.equals(exp3, "0"))){
            //TipHelper.Vibrate();
            state_receive=1;
            Log.e(TAG, flag+"reset！");

        }
        else {
        }
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(ControlActivity.this, R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            mtem.setText(news.tem);
            mhum.setText(news.hum);
            msmo.setText(news.smo);
            mcus.setText(news.cus);
        }


        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {


        /*解析JSON数据*/
        /*{"yan_wu": 225, "yu_di": 1, "shi_du": 14, "wen_du": 56, "yudi": 0, "ren": 0, "zhen": 1, "huo": 0, "yan": 0}*/
        private void parseJSONWITHJSONObject(String jsonData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //更改UI；
                        String[] str = jsonData.split("&");
                        String[] str1 = str[0].split("=");
                        String[] str2 = str[1].split("=");
                        String[] str3 = str[2].split("=");
                        String[] str4 = str[3].split("=");
                        String[] str5 = str[4].split("=");
                        String[] str6 = str[5].split("=");
                        String[] str7 = str[6].split("=");
                        String[] str8 = str[7].split("=");
                        String[] str9 = str[8].split("=");
                        String wen_du = str4[1];
                        mtem.setText(wen_du+"℃");
                        String shi_du = str3[1];
                        mhum.setText(shi_du+"%RH");
                        String yan_wu = str1[1];
                        msmo.setText(yan_wu+"PM");
                        warning_ren = str6[1];
                        warning_zhen = str7[1];
                        warning_huo = str8[1];
                        onezero(warning_ren,warning_huo,warning_zhen,state_receive);

                }
            });
        }
        /*生成随机数赋给edit_userName*/
        private void random(){
            /*请求JSON数据*/
            String url = "http://47.115.226.124/data/";
            sendRequestWithOkHttp(url);
        }
        private void sendRequestWithOkHttp(String url) {
            /*每隔0.5s请求一次*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(500);
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            parseJSONWITHJSONObject(responseData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            random();
            mtem = itemView.findViewById(R.id.textView);
            mhum = itemView.findViewById(R.id.textView2);
            msmo = itemView.findViewById(R.id.textView3);
            mcus = itemView.findViewById(R.id.textView4);


        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        mRecyclerView = findViewById(R.id.recyclerview);
        //mMyAdapter =
        mRecyclerView.setAdapter(mMyAdapter);
        //mMyAdapter.onBindViewHolder(mMyAdapter,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setItemViewCacheSize(1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        //mImageView设置为res/drawable/floor_plan_design.png
        //拿到加速重力感应的Sensor对象
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        thermometerTv = findViewById(R.id.tv_thermometer);
        News news1 = new News();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //更改UI；
                for (int i = 0; i < 1; i++) {
                    News news = new News();
                    news.tem = "Loading";
                    news.hum = "Loading";
                    news.smo="Loading";
                    news.cus="Loading";
                    mNewsList.add(news);
                }
            }
        });

        final Handler handler = new Handler();
        thermometerTv = findViewById(R.id.tv_thermometer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //s.wen_du to float
                        thermometerTv.setValueAndStartAnim(Float.parseFloat(MainActivity.s.wen_du));
                        handler.postDelayed(this, 100);
                    }
                });
            }
        }).start();


        }
    }
