package com.example.speechtotext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtSpeechInput, bulb1, bulb2, bulb3;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bulb1 = findViewById(R.id.bulbOne);
        bulb2 = findViewById(R.id.bulbTwo);
        bulb3 = findViewById(R.id.bulbThree);
        txtSpeechInput = findViewById(R.id.txtSpeechInput);
        btnSpeak = findViewById(R.id.btnSpeak);

        // hide the action bar
//        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    if (result.get(0).equals("on first bulb") || result.get(0).equals("on 1st bulb") || result.get(0).contains("on first")) {
                        bulb1.setBackgroundColor(Color.RED);
                        if (result.get(0).equals("on second bulb") || result.get(0).equals("on 2nd bulb") || result.get(0).contains("on second") || result.get(0).contains("on 2nd")) {
                            bulb2.setBackgroundColor(Color.RED);
                            if (result.get(0).equals("on third bulb") || result.get(0).equals("on 3rd bulb") || result.get(0).contains("on 3rd") || result.get(0).contains("on third")) {
                                bulb3.setBackgroundColor(Color.RED);
                            }
                        }
                    } else if (result.get(0).equals("on second bulb") || result.get(0).equals("on 2nd bulb") || result.get(0).contains("on second") || result.get(0).contains("on 2nd")) {
                        bulb2.setBackgroundColor(Color.RED);
                    } else if (result.get(0).equals("on third bulb") || result.get(0).equals("on 3rd bulb") || result.get(0).contains("on 3rd") || result.get(0).contains("on third")) {
                        bulb3.setBackgroundColor(Color.RED);
                    } else if (result.get(0).equals("of first bulb") || result.get(0).equals("of 1st bulb") || result.get(0).contains("of first") || result.get(0).contains("of 1st")) {
                        bulb1.setBackgroundColor(Color.TRANSPARENT);
                    } else if (result.get(0).equals("of second bulb") || result.get(0).equals("of 2nd bulb") || result.get(0).contains("of 2nd")|| result.get(0).contains("of second")) {
                        bulb2.setBackgroundColor(Color.TRANSPARENT);
                    } else if (result.get(0).equals("of third bulb") || result.get(0).equals("of 3rd bulb") || result.get(0).contains("of 3rd") || result.get(0).contains("of third")) {
                        bulb3.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        Toast.makeText(this, "this bulb is not available", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void batterystatus(View view) {
        startActivity(new Intent(MainActivity.this,BatteryStatus.class));
    }
}