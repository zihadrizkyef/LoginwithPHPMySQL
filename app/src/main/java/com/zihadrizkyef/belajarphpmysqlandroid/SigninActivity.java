package com.zihadrizkyef.belajarphpmysqlandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by zihadrizkyef on 29/09/16.
 */

public class SigninActivity extends AsyncTask<String, Void, String> {
    private ProgressDialog progressDialog;
    private Context context;
    private TextView tvStatus, tvRole;
    private int byGetOrPost = 0; //flag 0 means get and 1 means post. (By default it is get)

    boolean success;
    int id;
    String name;

    public SigninActivity(Context context, TextView tvStatus, TextView tvRole, int byGetOrPost) {
        this.context = context;
        this.tvStatus = tvStatus;
        this.tvRole = tvRole;
        this.byGetOrPost = byGetOrPost;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "", "Loading...");
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        //change the link below with your own link
        String link = "http://www.google.com/android-login/login.php?username="+username+"&password="+password;

        if (byGetOrPost == 0) { //by Get method
            try {
                URL url = new URL(link);
                HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
                urlCon.setReadTimeout(15000);
                urlCon.setConnectTimeout(15000);
                getResponse(urlCon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")
                        +"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
                urlCon.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(urlCon.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();

                getResponse(urlCon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (success) {
            tvStatus.setText("Login Status : success");
            tvRole.setText("Login Role : id 0"+id+"|name "+name);
        } else {
            tvStatus.setText("Login Status : fail");
            tvRole.setText("Login Role : null");
        }
    }

    void getResponse(HttpURLConnection urlCon) {
        try {
            int resCode = urlCon.getResponseCode();
            if (resCode == HTTP_OK) {
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String jsonString = br.readLine();
                System.out.println("WebResponse : "+jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                success = jsonObject.getBoolean("success");
                if (success) {
                    id = jsonObject.getInt("id");
                    name = jsonObject.getString("name");
                }

                is.close();
                isr.close();
                br.close();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
