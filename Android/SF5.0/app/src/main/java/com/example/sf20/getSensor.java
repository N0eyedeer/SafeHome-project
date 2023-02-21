package com.example.sf20;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getSensor {
    public String yan_wu = "0";
    public String yu_di = "0";
    public String shi_du = "0";
    public String wen_du = "0";
    public String yudi = "0";
    public String ren = "0";
    public String zhen = "0";
    public String huo = "0";
    public String yan = "0";
    private void parseJSONWITHJSONObject(String jsonData) {
            //yan_wu=2174&yu_di=1&shi_du=41.00&wen_du=22.60&yudi=0&ren=1&zhen=0&huo=0&yan=0
            //分离yan_wu，yu_di，shi_du，wen_du，yudi，ren，zhen，huo，yan
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
            yan_wu = str1[1];
            yu_di = str2[1];
            shi_du = str3[1];
            wen_du = str4[1];
            yudi = str5[1];
            ren = str6[1];
            zhen = str7[1];
            huo = str8[1];
            yan = str9[1];

    }

    /*生成随机数赋给edit_userName*/
    public void run(){
        /*请求JSON数据*/
        String url = "http://47.115.226.124/data/";
        sendRequestWithOkHttp(url);
    }
    public void sendRequestWithOkHttp(String url) {
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

}
