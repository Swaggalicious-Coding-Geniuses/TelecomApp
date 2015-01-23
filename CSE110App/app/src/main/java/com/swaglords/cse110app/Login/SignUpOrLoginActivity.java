package com.swaglords.cse110app.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.swaglords.cse110app.R;

public class SignUpOrLoginActivity extends ActionBarActivity {
	private Context appContext;
	private DisplayMetrics displayInfo;
	private EditText username, password;
	private Button login, register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_or_login);

		appContext = SignUpOrLoginActivity.this;
		displayInfo = getResources().getDisplayMetrics();
		Parse.initialize(this, "ktmIrppN0aJb1J2kI8o2jRiAJJPSEAnYSxQZp9iF", "wSJeYJfZWko5zq5Cl2ofYGrWFIKmJSjuhVYAyo1n");

		//Initialize elements
		username = (EditText) findViewById(R.id.signInUserName);
		password = (EditText) findViewById(R.id.signInPassword);
		login = (Button) findViewById(R.id.loginButton);
		register = (Button) findViewById(R.id.signInRegisterButton);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void  onClick (View view) {

				// Show a progress dialog
				final AlertDialog.Builder progressBuilder = new AlertDialog.Builder(appContext);
				progressBuilder.setTitle("Logging in...");
				int padding = (int) (20 * displayInfo.density);
				ProgressBar bar = new ProgressBar(appContext);
				bar.setPadding(0, padding, 0, padding);
				progressBuilder.setView(bar);

				final AlertDialog progressDialog = progressBuilder.create();
				progressDialog.show();

				//Start logging into the Parse database
				ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback()
				{
					@Override
					public void done(ParseUser parseUser, ParseException e)
					{
						// Get rid of the login dialog
						progressDialog.dismiss();

						if (e != null) {
							// Prepare an alert dialog to notify the user of an error
							final AlertDialog.Builder loginFailedDialog = new AlertDialog.Builder(appContext);
							loginFailedDialog.setTitle(R.string.login_failed);
							loginFailedDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
								}
							});

							// Show the dialog with the appropriate message
							if (isEmpty(username) || isEmpty(password)) {
								if (!isEmpty(password)) // If the password is filled, the username isn't
									loginFailedDialog.setMessage(R.string.login_failed_message_no_username);
								else if (!isEmpty(username)) // If the username is filled, the password isn't
									loginFailedDialog.setMessage(R.string.login_failed_message_no_password);
								else // Otherwise neither are filled
									loginFailedDialog.setMessage(R.string.login_failed_message_no_input);

								loginFailedDialog.show();
							}
							else {
								loginFailedDialog.setMessage(R.string.login_failed_message);
								loginFailedDialog.show();
							}
						}
						else {
							// Start HomeActivity
							Intent intent = new Intent(appContext, HomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
					}
				});
			}
		});

		//Set onClickListener to link to SignUpActivity
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =
						new Intent(SignUpOrLoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sign_up_or_login, menu);
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

	private boolean isEmpty(EditText editText) {
		return (editText.getText().toString().trim().length() <= 0);
	}
}
