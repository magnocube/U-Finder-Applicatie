package ml.u_finder.u_finder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {
    private Button button_logout;
    private Button button_locatie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_logout = (Button) findViewById(R.id.Logout);
        button_locatie = (Button) findViewById(R.id.locatie);


        //logout button
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                MenuActivity.this.finish();
            }
        });

        //locatie
        button_locatie.setOnClickListener(new View.OnClickListener() {
            // Drawable buttonshape_pressed = getResources().getDrawable(R.drawable.buttonshape_pressed);

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MapActivity.class));

            }
        });


    }
}
