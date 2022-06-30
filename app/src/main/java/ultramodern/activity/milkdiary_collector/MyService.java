package ultramodern.activity.milkdiary_collector;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

@SuppressWarnings("ALL")
public class MyService extends Service {
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside","onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    public boolean isOnline(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }


    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate,1*1000- SystemClock.elapsedRealtime()%1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("Check Internet");
            broadcastIntent.putExtra("online_status",""+isOnline(MyService.this));
            sendBroadcast(broadcastIntent);
        }
    };

        }
