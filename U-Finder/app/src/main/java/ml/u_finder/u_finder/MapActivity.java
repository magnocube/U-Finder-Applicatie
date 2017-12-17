package ml.u_finder.u_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapActivity extends Activity {
    private ImageView kaart;
    private MapThread thread;
    private ProgressDialog progressDialog;
    private Canvas canvas;
    private Bitmap stockMap;
    private Paint paint;
    private Bitmap bitmapMutable;
    private String[] Users;
    private Spinner dropdown;
    private Button trackButton;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dropdown = (Spinner) findViewById(R.id.spinner);


        kaart = (ImageView) findViewById(R.id.Kaart);
        trackButton = (Button) findViewById(R.id.track);

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Loading map");
        progressDialog.show();
        thread = new MapThread("room1", MapActivity.this);
        thread.start();

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.setPerson(getSelectedSpinner());
            }
        });


    }

    public void setImage(Bitmap image) {
        stockMap = image;
        bitmapMutable = stockMap.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(bitmapMutable);

        kaart.setImageBitmap(bitmapMutable);


        if ((image.getWidth() > image.getHeight())) {
            kaart.setRotation(90);
        }

        progressDialog.cancel();
    }

    public void track(int x, int y, String name) {

        try {

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setTextSize(kaart.getWidth() / 18);

            int deelWaarde = (kaart.getWidth() * kaart.getHeight()) / 15;
            int radius = (kaart.getWidth() * kaart.getHeight()) / deelWaarde;

            canvas.drawCircle(x, y, radius, paint);

            canvas.drawText(name, (x - radius), (y - radius), paint);
            kaart.setAdjustViewBounds(true);
            kaart.setImageBitmap(bitmapMutable);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Raber", "failed to track");
        }
    }


    public void repeat() {
        setImage(stockMap);
    }


    public void onPause() {
        super.onPause();
        thread.setTracking(false);
    }

    public void FillUsers(String usr, int i) {

        Users[i + 1] = usr;

    }

    public void SetIndex(int index) {
        Users = new String[index + 1];
        Users[0] = "Iedereen";

    }

    public void CreateSpinner() {
        adapter = new ArrayAdapter<String>(this, R.layout.spinnertheme, Users);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_theme);
        dropdown.setAdapter(adapter);
    }

    public String getSelectedSpinner() {

        try {
            return dropdown.getSelectedItem().toString();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.v("Raber", "No data in dropdown box");

        }

        return "";
    }

}
