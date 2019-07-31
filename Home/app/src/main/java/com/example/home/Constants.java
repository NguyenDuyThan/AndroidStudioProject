package com.example.home;

public class Constants {

//    public static final String MQTT_BROKER_URL = "app_startup_placeholder";
//    public static final String MQTT_CLIENT_UN = "app_startup_placeholder";
//    public static final String MQTT_CLIENT_PW = "app_startup_placeholder";
//    public static final String CLIENT_ID = "app_startup_placeholder";

    public static final String MQTT_BROKER_URL = "tcp://ece.ddns.net:1883";
    public static final String MQTT_CLIENT_UN = "iotmqttcenter";
    public static final String MQTT_CLIENT_PW = "Fhsj5@GDhjw@TH2fdh";
    public static final String CLIENT_ID = "mqtt000001";
    public static final String MQTT_TOPIC_CONTROL = MQTT_CLIENT_UN + "/feeds/onoff";
    public static final String MQTT_MSG_ON = "{CONTROL:ON}";
    public static final String MQTT_MSG_OFF = "{CONTROL:OFF}";


    public static final String CONNECTED = "Connected";
    public static final String NOTCONNECTED = "Not Connected";

}

