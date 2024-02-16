package com.example.doma;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;
    private TexttoSpeech ttsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textview1);
        Button btnBluetoothSettings = findViewById(R.id.btnBluetoothSettings);


        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intent);


        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);

        audioHelper = new AudioHelper(this);
        ttsManager = new TexttoSpeech(this);


        if (audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
            textView.setText("Monitorando as notificações");
            btnBluetoothSettings.setVisibility(View.GONE);

            ttsManager.speak("Monitorando as notificações");

        } else {
            textView.setText("Nenhum Bluetooth conectado");
            btnBluetoothSettings.setVisibility(View.VISIBLE);

            btnBluetoothSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    audioHelper.showBluetoothSettings();
                }
            });
        }

        // Falar o texto usando o TTSManager
        ttsManager.speak("Não há dispositivos Bluetooth conectado. Para conectar, basta pressionar o botão localizado no centro da tela.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsManager.shutdown();
    }
}


