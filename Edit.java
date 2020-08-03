package com.example.bulletin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bulletin.bean.Message;
import com.example.bulletin.mqtt.MqttService;
import com.google.gson.Gson;

public class Edit extends AppCompatActivity  {

    private EditText edit;
    private Button btn;
    private Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edit=(EditText)findViewById(R.id.edit);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String content=edit.getText().toString();
                if(!TextUtils.isEmpty(content)){
                    Message message =new Message("guest",content,true);
                    MqttService.getMyMqtt().pubMsg("1",mGson.toJson(message),0);
                    Toast.makeText(Edit.this,"Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, Edit.class);
        context.startActivity(starter);

    }

}