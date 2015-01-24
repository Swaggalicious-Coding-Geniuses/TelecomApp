package com.swaglords.cse110app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.swaglords.cse110app.R;

public class SignUpActivity extends ActionBarActivity {

    private EditText username;
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
        password = (EditText) findViewById(R.id.signUpPassword);
        passwordRepeat = (EditText) findViewById(R.id.signUpPasswordRepeat);
        firstName = (EditText) findViewById(R.id.signUpFirstName);
        lastName = (EditText) findViewById(R.id.signUpLastName);
        address = (EditText) findViewById(R.id.signUpAddress);

        //Set up Register button click handler
        findViewById(R.id.signUpRegisterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean invalidInformation = false;
                StringBuilder errorMessage =
                        new StringBuilder("Please enter the following fields: ");

                //Check for empty username field
                if(isEmpty(username)){
                    invalidInformation = true;
                    errorMessage.append("username");
                }
                //Check for empty password field
                if(isEmpty(password)){
                    if(invalidInformation){
                        errorMessage.append(", ");
                    }
                    invalidInformation = true;
                    errorMessage.append("password");
                }
                //Check for matching passwords
                if(!isMatching(password, passwordRepeat)){
                        if(invalidInformation) {
                            errorMessage.append(", ");
                        }
                        invalidInformation = true;
                        errorMessage.append("matching passwords");
                }
                //Check for empty firstName field
                if(isEmpty(firstName)){
                    if(invalidInformation){
                        errorMessage.append(", ");
                    }
                    invalidInformation = true;
                    errorMessage.append("first name");
                }
                //Check for empty lastName field
                if(isEmpty(lastName)){
                    if(invalidInformation){
                        errorMessage.append(", ");
                    }
                    invalidInformation = true;
                    errorMessage.append("last name");
                }
                //Check for empty address field
                if(isEmpty(address)){
                    if(invalidInformation){
                        errorMessage.append(", ");
                    }
                    invalidInformation = true;
                    errorMessage.append("address");
                }
                errorMessage.append(".");

                //If there is an error then display the error
                if(invalidInformation){
                    Toast.makeText(SignUpActivity.this, errorMessage.toString()
                            , Toast.LENGTH_LONG).show();
                    return;
                }

                //Set progress dialog
                final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setTitle("Please wait");
                dialog.setMessage("Please wait while you are signed up");
                dialog.show();

                //Set up new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.put("firstName", firstName.getText().toString());
                user.put("lastName", lastName.getText().toString());
                user.put("address", address.getText().toString());

                //Call Parse sign up method
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        dialog.dismiss();
                        if(e != null){
                            //Show error message
                            Toast.makeText(SignUpActivity.this, e.getMessage()
                                    , Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent =
                                    new Intent(SignUpActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
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

    //Helper method to check if an EditText field is empty
    private boolean isEmpty(EditText editText){
        return !(editText.getText().toString().trim().length() > 0);
    }

    //Helper method to check if two EditText fields match
    private boolean isMatching(EditText editText, EditText editText2){
        return (editText.getText().toString().equals
                (editText2.getText().toString()));
    }
}
