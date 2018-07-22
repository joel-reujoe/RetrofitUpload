package com.example.joel.retrofitupload;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joel.retrofitupload.API.API;
import com.example.joel.retrofitupload.API.RestClient;
import com.example.joel.retrofitupload.response.UploadResponse;

import java.io.FileNotFoundException;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
public class UploadImage extends AppCompatActivity {

    Button select;
    Button upload;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        select=findViewById(R.id.select);
        upload=findViewById(R.id.upload);
        image=findViewById(R.id.imageView);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,103);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).client(okClient).addConverterFactory(GsonConverterFactory.create()).build();
                API api=RestClient.client.create(API.class);
                Call<UploadResponse> responseCall=api.test();
                responseCall.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        UploadResponse uploadResponse=response.body();
                        Toast.makeText(getApplicationContext(),uploadResponse.getData(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        Bitmap bitmap;
        Uri uri;
        if(resultCode==RESULT_OK)
        {
            if(requestCode==103)
            {
                uri=data.getData();
                try {
                    bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
