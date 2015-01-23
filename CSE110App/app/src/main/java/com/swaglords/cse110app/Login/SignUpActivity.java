package com.swaglords.cse110app.Login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.swaglords.cse110app.R;

public class SignUpActivity extends ActionBarActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordRepeat;
    private EditText firstName;
    private EditText lastName;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Set up the sign up page
        username = (EditText) findViewById(R.id.signUpUsername);
        email = (EditText) findViewById(R.id.signUpEmail);
        password = (EditText) findViewById(R.id.signUpPassword);
        passwordRepeat = (EditText) findViewById(R.id.signUpPasswordRepeat);
        firstName = (EditText) findViewById(R.id.signUpFirstName);
        lastName = (EditText) findViewById(R.id.signUpLastName);
        address = (EditText) findViewById(R.id.signUpAddress);

        //Set up Register button click handler

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
