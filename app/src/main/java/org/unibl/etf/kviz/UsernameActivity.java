package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class UsernameActivity extends AppCompatActivity {

    Button saveUserNameBtn;
    public static String PREFS_USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        saveUserNameBtn = findViewById(R.id.save_username_button);
        saveUserNameBtn.setOnClickListener(v->{
            EditText usernameView = findViewById(R.id.editTextUseraname);
            String username = usernameView.getText().toString();
            if(username.length()>0) {
                SharedPreferences preferences = getSharedPreferences(PREFS_USERNAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PREFS_USERNAME, username);
                editor.apply();
                editor.commit();
                startActivity(new Intent(this,MainActivity.class));
            }
        });
    }
}