package ml.u_finder.u_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
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
    private int[] colors ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        colors = new int[4];
        colors[0] = Color.GREEN;
        colors[1] = Color.BLUE;
        colors[2] = Color.WHITE;
        colors[3] = Color.RED;

        Button btn = (Button) findViewById(R.id.btn);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] plants = new String[]{
                "Test1",
                "Test2"
        };
        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantsList.add("Apple");
                spinnerArrayAdapter.notifyDataSetChanged();
            }
        });


        kaart = (ImageView) findViewById(R.id.Kaart);

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Loading map");
        progressDialog.show();
        thread = new MapThread("room1", MapActivity.this);
        thread.start();
    }

    public void setImage(Bitmap image){
        stockMap = image;
        bitmapMutable = stockMap.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(bitmapMutable);

        kaart.setImageBitmap(bitmapMutable);


        if (!(image.getWidth() > image.getHeight())){
            kaart.setRotation(-90);
        }

        progressDialog.cancel();
    }

    public void track(int x, int y, String name, int i){

        try {

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setTextSize(kaart.getWidth()/18);

            int deelWaarde = (kaart.getWidth()*kaart.getHeight())/15;
            int radius = (kaart.getWidth()*kaart.getHeight())/deelWaarde;

            canvas.drawCircle(x, y, radius, paint);

            canvas.drawText(name, (x - radius) , (y - radius), paint);
            kaart.setAdjustViewBounds(true);
            kaart.setImageBitmap(bitmapMutable);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.v("Raber", "failed to track");
        }
    }

    public void repeat(){
        setImage(stockMap);
    }


    public void onPause()
    {
     super.onPause();
     thread.setTracking(false);
    }

}
