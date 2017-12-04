package ml.u_finder.u_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private TextView user;
    private TextView passwd;
    private TextView ErrorField;
    private Button loginButton;
    private LoginThread thread;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (TextView) findViewById(R.id.gebruiksnaam);
        passwd = (TextView) findViewById(R.id.wachtwoord);
        ErrorField = (TextView) findViewById(R.id.ErrorField);
        loginButton = (Button) findViewById(R.id.LoginButton);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Loading");


        /*

        passwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (user.getText().toString().equals(userName) && passwd.getText().toString().equals(password)) {
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        ErrorField.setVisibility(View.INVISIBLE);
                    } else if (user.getText().toString().equals("admin") && passwd.getText().toString().equals("admin")) {
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        ErrorField.setVisibility(View.INVISIBLE);
                    } else {
                        user.setText("");
                        passwd.setText("");
                        ErrorField.setVisibility(View.VISIBLE);
                        user.requestFocus();
                    }

                    handled = true;
                }
                return handled;
            }
        });
        */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                thread = new LoginThread(user.getText().toString(), passwd.getText().toString(), LoginActivity.this);
                thread.start();

            }
        });


    }

    public void succesLogin() {
        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        this.finish();

        progressDialog.cancel();
    }


    public void loginFail() {
        ErrorField.setVisibility(View.VISIBLE);
        user.setText("");
        user.requestFocus();
        passwd.setText("");
        progressDialog.cancel();
    }


}













