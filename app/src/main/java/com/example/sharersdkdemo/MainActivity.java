package com.example.sharersdkdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String TAG = "MainActivity";
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        shareButton = findViewById(R.id.share);


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(MainActivity.this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d(TAG, "onSuccess: Shared");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: Cancelled");

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://www.dynamiccreativeminds.com/"))
                            .setQuote("Hello Everyone! This is The text I wanted to share along with The URL Of DCM, So please note that \nText Sharing is possible")
                            .build();
                    shareDialog.show(linkContent);
                } else {
                    Toast.makeText(MainActivity.this, "Failed Sorryyy", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
