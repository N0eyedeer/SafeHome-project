package com.example.sf20;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link m02#newInstance} factory method to
 * create an instance of this fragment.
 */
public class m02 extends Fragment {
    private VideoView mVideoView;
    private Button playBtn,stopBtn,zoomin,zoomout,change;
    MediaController mMediaController;
    private TextView text02;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int flag = 0;



    public m02() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment m02.
     */
    // TODO: Rename and change types and number of parameters
    public static m02 newInstance(String param1, String param2) {
        m02 fragment = new m02();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // mVideoView = (VideoView)find
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_m02, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getVideo g = new getVideo();
        zoomin = (Button) getActivity().findViewById(R.id.button2);
        zoomout = (Button) getActivity().findViewById(R.id.button3);
        change = (Button) getActivity().findViewById(R.id.button4);

        ImageView mImg02 = (ImageView) getActivity().findViewById(R.id.imageView);
        //loading.png
        //创建一个ui线程，根据g中的数据，更新fragment02中的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                g.connect();
                //g.connect2();
               // mImg02.setImageResource(R.drawable.loading);

                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //更新UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(flag == 0)
                                mImg02.setImageBitmap(g.getBitmap());
                            else
                                mImg02.setImageBitmap(g.bitmap2);
                        }
                    });
                }
            }
        }).start();
        //监听按钮，点击后放大图片
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = g.getBitmap();
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                float sx = (float) 2 * w / w;
                float sy = (float) 2 * h / h;
                mImg02.setScaleX(sx);
                mImg02.setScaleY(sy);
            }
        });
        //监听按钮，点击后缩小图片
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = g.getBitmap();
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                float sx = (float) 0.8 * w / w;
                float sy = (float) 0.8 * h / h;
                mImg02.setScaleX(sx);
                mImg02.setScaleY(sy);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    flag = 1;
                }
                else {
                    flag = 0;
                }
            }
        });



    }
}