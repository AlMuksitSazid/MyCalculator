package com.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    TextView myCalculator;
    EditText firstNumber, secondNumber;
    Button reset, sum, sub, mul, div;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);

        myCalculator = (TextView) findViewById(R.id.mycalculator);
        firstNumber = (EditText) findViewById(R.id.firstnumber);
        secondNumber = (EditText) findViewById(R.id.secondnumber);
        reset = (Button) findViewById(R.id.Reset);
        sum = (Button) findViewById(R.id.Sum);
        sub = (Button) findViewById(R.id.Sub);
        mul = (Button) findViewById(R.id.Mul);
        div = (Button) findViewById(R.id.Div);

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myCalculator.setText("MY CALCULATOR");
                firstNumber.setText("");
                secondNumber.setText("");
                Toasty.info(MainActivity.this, "Reset done", Toast.LENGTH_SHORT).show();
            }
        });

        sum.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double sum = n1+n2;
                    myCalculator.setText(String.valueOf(sum));

                    Toasty.success(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double sub = n1-n2;
                    myCalculator.setText(String.valueOf(sub));

                    Toasty.success(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        mul.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double mul = n1*n2;
                    myCalculator.setText(String.valueOf(mul));

                    Toasty.success(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        div.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    try {
                        double div = n1/n2;
                        myCalculator.setText(String.valueOf(div));
                        if(n2!=0)
                            Toasty.success(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }catch (ArithmeticException e){
                        myCalculator.setText("Math Error");
                        Toasty.error(MainActivity.this, "Exception: "+e, Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        firstNumber.addTextChangedListener(resetText);
        secondNumber.addTextChangedListener(resetText);
    }
    TextWatcher resetText = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String s1 = firstNumber.getText().toString().trim();
            String s2 = secondNumber.getText().toString().trim();
            reset.setEnabled(!s1.isEmpty() || !s2.isEmpty());
        }
        public void afterTextChanged(Editable s) {

        }
    };
}