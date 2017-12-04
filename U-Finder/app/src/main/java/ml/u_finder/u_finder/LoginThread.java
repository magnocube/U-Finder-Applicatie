package ml.u_finder.u_finder;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LoginThread extends Thread {
    private final UWBServer server = new UWBServer("77.172.10.240", 8379);
    private String user = null;
    private String password = null;
    private LoginActivity activity;

    public LoginThread(String usr, String passwd, LoginActivity loginActivity) {
        this.user = usr;
        this.password = passwd;
        this.activity = loginActivity;
    }

    public void run() {


        if (server.login(user, password)) {
            //Log.v("Raber", ""+login );

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    activity.succesLogin();
                }
            });
        } else {
            //Log.v("Raber", ""+login );
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    activity.loginFail();
                }
            });
        }

    }
}
