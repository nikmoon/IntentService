package innopolis.nikbird.org.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nikbird on 19.07.17.
 */

public class ValutaService extends IntentService {

    private static Handler sHadler;

    public ValutaService() {
        super("ValutaService");
        if (sHadler == null) {
            sHadler = new Handler();
        }
    }

    private static Thread mValutaThread;
    private static volatile boolean mRubMonitoring;
    private static volatile boolean mUsMonitoring;
    private static volatile boolean mEuMonitoring;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null)
            return;

        mRubMonitoring = intent.getBooleanExtra("rub", mRubMonitoring);
        mUsMonitoring = intent.getBooleanExtra("us", mUsMonitoring);
        mEuMonitoring = intent.getBooleanExtra("eu", mEuMonitoring);

        if (mValutaThread == null) {
            mValutaThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final StringBuilder message = new StringBuilder("");
                    while (true) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            throw new Error("ERROR: sleep interrupted!!!");
                        }
                        if (mRubMonitoring) {
                            message.append("rub course is: " + 100);
                        }
                        if (mUsMonitoring) {
                            message.append("\nus course is: " + 100);
                        }
                        if (mEuMonitoring) {
                            message.append("\neu course is: " + 100);
                        }
                        if (!"".equals(message.toString())) {
                            sHadler.post(new Runnable() {
                                private String msg = message.toString();
                                @Override
                                public void run() {
                                    Toast.makeText(ValutaService.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                            message.setLength(0);
                        }
                    }
                }
            });
            mValutaThread.start();
        }
    }
}
