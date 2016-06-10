package com.bicit.manejadorArchivos;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import org.apache.http.HttpClientConnection;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;


import java.io.File;
import java.io.IOException;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by Juan Pablo Gaviria on 9/06/2016.
 */
public class UploaderArchivo extends AsyncTask<String,String,String> {

    ProgressDialog pDialog;
    private String fileName;
    private Context context;

    public UploaderArchivo(String fileName, Context context){
        this.fileName = fileName;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        File archivoKml = new File(fileName);

        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httpPost = new HttpPost("http://bicit.eu5.org/upload.php");
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody archivo = new FileBody(archivoKml);
        mpEntity.addPart("uploadedfile", archivo);
        httpPost.setEntity(mpEntity);
        try{
            HttpResponse response = httpClient.execute(httpPost);
            httpClient.getConnectionManager().shutdown();

            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    protected void onPreExecute() {
        System.out.println("Subiendo");
        super.onPreExecute();
    }
    protected void onPostExecute(String result) {
        System.out.println("Subido");
        super.onPostExecute(result);
    }

}
