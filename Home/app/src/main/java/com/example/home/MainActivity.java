package com.example.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;
    Button onBtnClick, stopBtnClick;
    TextView statusTextView;
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onBtnClick = (Button) findViewById(R.id.buttonOn);
        stopBtnClick = (Button) findViewById(R.id.idStopBtn);
        statusTextView = (TextView) findViewById(R.id.idTextView);

        onBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTextView.setText("Mở");
                Log.d(TAG, "onCreate: on button clicked");
                if(pahoMqttClient.mqttAndroidClient.isConnected()){
//                    sendMsgControlToServer((Constants.MQTT_MSG_ON));
                    Log.d(TAG, "srever connected");
                }
                else {
                    Log.d(TAG, "srever not connected");
                    //Connect to Broker
                    client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID, Constants.MQTT_CLIENT_UN, Constants.MQTT_CLIENT_PW);
                    //Set Mqtt Message Callback
                    mqttCallback();
                }
            }
        });

        stopBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTextView.setText("Tắt");
                Log.d(TAG, "onCreate: off button clicked");
                if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
                    //Disconnect and Reconnect to  Broker
                    sendMsgControlToServer(Constants.MQTT_MSG_OFF);
                }
                else {
                    //Connect to Broker
                    client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID, Constants.MQTT_CLIENT_UN, Constants.MQTT_CLIENT_PW);
                    //Set Mqtt Message Callback
                    mqttCallback();
                }
            }
        });

        //Create listener for MQTT messages.
//        mqttCallback();

        //Create Timer to report MQTT connection status every 1 second
//        myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                ScheduleTasks();
//            }
//
//        }, 0, 1000);

    }

    private void ScheduleTasks()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(RunScheduledTasks);
    }

    private Runnable RunScheduledTasks = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.

            //Check MQTT Connection Status
            TextView tvMessage  = (TextView) findViewById(R.id.idTextView);
            String msg_new="";

            if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
                msg_new = "Connected\r\n";
                tvMessage.setTextColor(0xFF00FF00); //Green if connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            else {
                msg_new = "Disconnected\r\n";
                tvMessage.setTextColor(0xFFFF0000); //Red if not connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            tvMessage.setText(msg_new);
        }
    };

    protected void mqttCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //msg("Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                TextView tvMessage = (TextView) findViewById(R.id.idTextView);
                if(topic.equals("mycustomtopic1")) {
                    //Add custom message handling here (if topic = "mycustomtopic1")
                }
                else if(topic.equals("mycustomtopic2")) {
                    //Add custom message handling here (if topic = "mycustomtopic2")
                }
                else {
                    String msg = "topic: " + topic + "\r\nMessage: " + message.toString() + "\r\n";
                    tvMessage.append( msg);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    protected void sendMsgControlToServer(String msg){
        if (!msg.isEmpty()) {
            try {
                pahoMqttClient.publishMessage( client, msg, 1, Constants.MQTT_TOPIC_CONTROL);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPahoMqttClient(PahoMqttClient pahoMqttClient) {
        this.pahoMqttClient = pahoMqttClient;
    }
}
