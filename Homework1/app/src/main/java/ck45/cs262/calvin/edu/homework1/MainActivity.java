package ck45.cs262.calvin.edu.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText value1EditText;
    private EditText value2EditText;
    private TextView returnTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value1EditText = (EditText) findViewById(R.id.value1EditText);
        value2EditText = (EditText) findViewById(R.id.value2EditText);
        returnTextView = (TextView) findViewById(R.id.returnTextView);

    }

    public void calculate(View view) {
        Spinner spinner_main = (Spinner) findViewById(R.id.spinner);
        String selectedOperator = (String) spinner_main.getSelectedItem();

        Float value1 = Float.parseFloat(value1EditText.getText().toString());
        Float value2 = Float.parseFloat(value2EditText.getText().toString());

        if(selectedOperator.trim().equals("+")) {
            float result = value1 + value2;
            returnTextView.setText(Float.toString(result));
        }else if(selectedOperator.trim().equals("-")) {
            float result = value1 - value2;
            returnTextView.setText(Float.toString(result));
        }else if(selectedOperator.trim().equals("*")) {
            float result = value1 * value2;
            returnTextView.setText(Float.toString(result));
        }else if(selectedOperator.trim().equals("/")) {
            float result = value1 / value2;
            returnTextView.setText(Float.toString(result));
        }else if(selectedOperator.trim().equals("%")) {
            float result = value1 % value2;
            returnTextView.setText(Float.toString(result));
        }else {
            returnTextView.setText("");
        }
    }
}
