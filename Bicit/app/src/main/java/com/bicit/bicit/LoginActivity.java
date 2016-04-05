package com.bicit.bicit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public EditText txtUser;
    public EditText txtPassword;
    public Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.txtUser = (EditText) findViewById(R.id.txtUser);
        this.txtPassword = (EditText) findViewById(R.id.txtPassword);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void onClick(View v ){
        String user = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        if(user.equals("") && password.equals("")){
            Intent intent = new Intent(LoginActivity.this,Maproute.class);
            startActivity(intent);
        }
    }


}
