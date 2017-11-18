package ml.u_finder.u_finder;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Raber on 11/18/2017.
 */

public class MapThread extends Thread {
    private String room;
    private MapActivity activity;
    private Bitmap image;
    private final UWBServer server = new UWBServer("77.172.10.240",8379);
    public MapThread(String room, MapActivity mapActivity){
        this.room = room;
        this.activity = mapActivity;
    }

    public void run(){
        Log.v("Raber", ""+room);
        image = server.getImage(room);

        if (image != null){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    activity.setImage(image);
                }
            });
        }
        else {

        }

    }
}
