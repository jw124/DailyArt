package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner numberSpinner;
    private Spinner frequencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        numberSpinner = (Spinner) findViewById(R.id.number_spinner);
        ArrayAdapter<CharSequence> numberAdapter = ArrayAdapter.createFromResource(this, R.array.number_array, android.R.layout.simple_spinner_item);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(this);

        frequencySpinner = (Spinner) findViewById(R.id.frequency_spinner);
        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(this, R.array.frequency_array, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(this);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.prompt_radio:
                if (checked) {
                    Log.d("Radio", "Clicked prompt");
                    break;
                }
            case R.id.critiques_radio:
                if (checked){
                    Log.d("Radio", "Clicked critiques");
                    break;
                }
        }
    }

    public void onSaveClicked(View view) {
        CharSequence toastContent = "Changes saved.";
        Toast toast = Toast.makeText(getApplicationContext(), toastContent, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.d("Number spinner", "Selected " + parent.getItemAtPosition(pos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}