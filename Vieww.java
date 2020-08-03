package com.example.bulletin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulletin.bean.Message;
import com.example.bulletin.mqtt.MqttListener;
import com.example.bulletin.mqtt.MqttService;
import com.example.bulletin.mqtt.MyMqtt;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Vieww extends AppCompatActivity {

    private EditText edit;
    private Gson mGson=new Gson();
    private List<Message> mMessageList=new ArrayList<>();
    public MqttListener mMqttListener=new MqttListener() {
        @Override
        public void onConnected() {
            MqttService.getMyMqtt().subTopic("1",0);
            Toast.makeText(Vieww.this,"Connected", Toast.LENGTH_SHORT).show();


            tex.setText(mMessageList.toString());
        }

        @Override
        public void onFail() {
                Toast.makeText(Vieww.this,"Connect fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost() {
                Toast.makeText(Vieww.this,"Connect lost",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceive(String message) {
            Toast.makeText(Vieww.this,"Receive",Toast.LENGTH_SHORT).show();

            Message msg=mGson.fromJson(message, Message.class);
        /*    if(msg.getClientID().equals(MyMqtt.getClientID())){
                msg.setReceive(false);
            }*/
            mMessageList.clear();
            mMessageList.add(msg);


        }

        @Override
        public void onSendSucc() {

        }
    };
    private TextView tex;
    private Button btn1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        tex=(TextView)findViewById(R.id.message);
        edit=(EditText)findViewById(R.id.messageE);
        btn1=(Button)findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String content=edit.getText().toString();
                if(!TextUtils.isEmpty(content)){
                    Message message =new Message("guest",content,true);
                    MqttService.getMyMqtt().pubMsg("1",mGson.toJson(message),0);

                }

            }
        });


        initMqtt();



        //initListView();
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, Vieww.class);
        context.startActivity(starter);

    }

    private void initMqtt(){
        MqttService.start(this);
        MqttService.addMqttListener(mMqttListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MqttService.removeMqttListener(mMqttListener);
        MqttService.stop(this);
    }
    /*  private ListView listView;
    private ArrayAdapter<Message> arrayAdapter;
    private void initListView(){
        listView=(ListView)findViewById(R.id.list);
        setContentView(R.layout.activity_view);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.activity_list_item,mMessageList);
        listView.setAdapter(arrayAdapter);

    }*/

}