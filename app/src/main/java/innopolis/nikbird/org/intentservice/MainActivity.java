package innopolis.nikbird.org.intentservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Monitoring(View view) {
        if (view == null)
            return;

        String currency = null;
        boolean flag = false;

        switch (view.getId()) {
            case R.id.btnStartRub:
                flag = true;
            case R.id.btnStopRub:
                currency = "rub";
                break;
            case R.id.btnStartUs:
                flag = true;
            case R.id.btnStopUs:
                currency = "us";
                break;
            case R.id.btnStartEu:
                flag = true;
            case R.id.btnStopEu:
                currency = "eu";
                break;
        }
        if (currency != null) {
            sendMessageToService(currency, flag);
        }

    }

    private void sendMessageToService(String currency, boolean flag) {
        Intent intent = new Intent(this, ValutaService.class);
        intent.putExtra(currency, flag);
        startService(intent);
    }
}
