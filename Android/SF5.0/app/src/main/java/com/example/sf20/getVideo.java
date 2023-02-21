package com.example.sf20;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class getVideo {
    private Bitmap bitmap;
    public Bitmap bitmap2;
    public void setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
    }
    public Bitmap getBitmap(){
        return this.bitmap;
    }
    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                InputStream inStream = null;
                int count = 0;
                try {
                    socket = new Socket("43.142.29.23", 8888);
                    //socket = new Socket("10.0.2.2", 8888);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    inStream = socket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (true) {
                    try {
                        byte[] bytes = new byte[1024];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        while ((len = inStream.read(bytes)) != -1) {
                            baos.write(bytes, 0, len);
                            System.out.println("get1024");
                            if (len < 1024) {
                                if ((len < 2) || bytes[len - 2] == (byte) 0xff && bytes[len - 1] == (byte) 0xd9) {
                                    System.out.println("get the end");
                                    break;
                                }
                            }
                        }
                        byte[] data = baos.toByteArray();
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                    System.out.println(count++);
                }
            }
        }).start();
        System.out.println("end");
    }
    public void connect2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                InputStream inStream = null;
                int count = 0;
                try {
                    socket = new Socket("47.115.226.124", 8080);
                    //socket = new Socket("10.0.2.2", 8888);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    inStream = socket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (true) {
                    try {
                        byte[] bytes = new byte[1024];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        while ((len = inStream.read(bytes)) != -1) {
                            baos.write(bytes, 0, len);
                            System.out.println("get1024");
                            if (len < 1024) {
                                if ((len < 2) || bytes[len - 2] == (byte) 0xff && bytes[len - 1] == (byte) 0xd9) {
                                    System.out.println("get the end");
                                    break;
                                }
                            }
                        }
                        byte[] data = baos.toByteArray();
                        bitmap2 = BitmapFactory.decodeByteArray(data, 0, data.length);

                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                    System.out.println(count++);
                }
            }
        }).start();
        System.out.println("end");
    }

}