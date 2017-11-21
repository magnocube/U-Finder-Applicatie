package ml.u_finder.u_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class MapActivity extends Activity {
    private Button mapButton;
    private ImageView kaart;
    private MapThread thread;

    private ProgressDialog progressDialog;
    private Bitmap stockMap;
    private Canvas canvas;
    private int[] colors ;
    private Paint paint;

    private Bitmap bitmapMutable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        colors = new int[4];
        colors[0] = Color.GREEN;
        colors[1] = Color.BLUE;
        colors[2] = Color.WHITE;
        colors[3] = Color.RED;

        mapButton = (Button) findViewById(R.id.GetKaart);
        kaart = (ImageView) findViewById(R.id.Kaart);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Loading map");



        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                thread = new MapThread("room1", MapActivity.this);
                thread.start();
            }
        });
    }

    public void setImage(Bitmap image){
        stockMap = image;
        bitmapMutable = stockMap.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(bitmapMutable);

        kaart.setImageBitmap(bitmapMutable);


        if (image.getWidth() > image.getHeight()){
            kaart.setRotation(90);
        }

        progressDialog.cancel();
    }

    public void track(int x, int y, int i){

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(colors[i]);


        canvas.drawCircle(x, y, 15, paint);
        kaart.setAdjustViewBounds(true);
        kaart.setImageBitmap(bitmapMutable);
    }

    public void repeat(){
        setImage(stockMap);
    }


}
