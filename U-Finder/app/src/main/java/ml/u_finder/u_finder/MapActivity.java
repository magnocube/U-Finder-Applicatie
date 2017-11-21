package ml.u_finder.u_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





public class MapActivity extends Activity {
    private ImageView kaart;
    private MapThread thread;
    private ProgressDialog progressDialog;
    private Canvas canvas;
    private Bitmap bitmapMutable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


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
        bitmapMutable = image.copy(Bitmap.Config.ARGB_8888, true);



        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        canvas = new Canvas(bitmapMutable);
        canvas.drawCircle(159, 120, 15, paint);
        kaart.setAdjustViewBounds(true);
        kaart.setImageBitmap(bitmapMutable);


        if (image.getWidth() > image.getHeight()){
            kaart.setRotation(90);
        }

        progressDialog.cancel();
    }
}
