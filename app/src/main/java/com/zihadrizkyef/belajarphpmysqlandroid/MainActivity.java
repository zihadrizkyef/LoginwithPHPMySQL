package com.zihadrizkyef.belajarphpmysqlandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /*
    * TODO :
    *   -PHP echo ubah ke json
    *   -TextView selalu "success"
    */
    EditText etUsername, etPassword;
    TextView tvStatus, tvRole, tvMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        tvStatus = (TextView)findViewById(R.id.tvLoginStatus);
        tvRole = (TextView)findViewById(R.id.tvLoginRole);
        tvMethod = (TextView)findViewById(R.id.tvLoginMethod);
    }

    public void btnLoginGET(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        tvMethod.setText("Login Method : GET");
        new SigninActivity(this, tvStatus, tvRole, 0).execute(username, password);
    }

    public void btnLoginPOST(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        tvMethod.setText("Login Method : POST");
        new SigninActivity(this, tvStatus, tvRole, 1).execute(username, password);
    }
}
