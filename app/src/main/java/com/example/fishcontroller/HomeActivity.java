package com.example.fishcontroller;

import android.os.Bundle;
import android.widget.ToggleButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private TextView txtConnection, txtTime, txtAccountant, txtCurrent;
    private ToggleButton toggleButton;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências aos TextViews no layout
        txtConnection = findViewById(R.id.textView);
        txtTime = findViewById(R.id.textView2);
        txtAccountant = findViewById(R.id.textView3);
        txtCurrent = findViewById(R.id.textView4);

        // Referência ao botão no layout
        toggleButton = findViewById(R.id.toggle_button);

        // Ação ao clicar no botão
        toggleButton.setOnClickListener(v -> {
            if (toggleButton.isChecked()) {
                sendRequest("http://192.168.100.109/ligar", "LED Ligado!");
            } else {
                sendRequest("http://192.168.100.109/desligar", "LED Desligado!");
            }
        });

        // Atualização das informações do ESP32 a cada 5 segundos
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateInfo();
            }
        }, 0, 500);
    }

    // Método para enviar requisições de controle do LED
    private void sendRequest(String url, String successMessage) {
        new Thread(() -> {
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Erro ao executar ação!", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Erro de conexão!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    // Método para buscar informações da rota /info
    private void updateInfo() {
        new Thread(() -> {
            String url = "http://192.168.100.109/info";
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);

                    String name = jsonObject.getString("name");
                    String time = jsonObject.getString("uptime");
                    String state = jsonObject.getString("state");
                    int countOff = jsonObject.getInt("count_off");

                    runOnUiThread(() -> {
                        txtConnection.setText("Nome: " + name);
                        txtTime.setText("Tempo de funcionamento: " + time);
                        txtCurrent.setText("Estado do pino D2: " + state);
                        txtAccountant.setText("Pino D2 desligado: " + countOff + " vezes");
                    });
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
