package com.bicit.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bicit.bicit.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    public Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        //Se obtinene el perfil actual
        Profile perfilActual = Profile.getCurrentProfile();

        //Se revisa si no es nulo
        //Si no es nulo se procede a llamar la siguiente actividad
        //Ya que se inicio sesion anteriormente
        if(AccessToken.getCurrentAccessToken() != null){
            llamarPrincipal();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Se inicia la actividad principal
                llamarPrincipal();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v ){

        Intent intent = new Intent(LoginActivity.this,EventosListar.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void llamarPrincipal(){
        Intent intent = new Intent(LoginActivity.this,EventosListar.class);
        startActivity(intent);
        finish();
    }

}
