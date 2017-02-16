package com.example.sakif.basiccalculator;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View ;
import android.view.View.OnClickListener;
public class calulator extends AppCompatActivity {
    public TextView txtResult;
    public String inStr = "0" ;
    public char lastOperatpor = ' ';
    public double result = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calulator);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText("0");

        BtnListener listener = new BtnListener() ;
        ((Button) findViewById(R.id.btn0)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn1)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn2)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn3)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn4)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn5)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn6)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn7)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn8)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btn9)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnadd)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnsub)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnmulti)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btndiv)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btndot)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnC)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnequal)).setOnClickListener(listener);
    }
    private class BtnListener implements View.OnClickListener {
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.btn0:case R.id.btn1:case R.id.btn2:case R.id.btn3:case R.id.btn4:case R.id.btn5:case R.id.btn6:case R.id.btn7:case R.id.btn8:case R.id.btn9:
                    String inNum = ((Button) view).getText().toString();
                    if (inStr.equals("0")){
                        inStr = inNum;
                    }
                    else{
                        inStr += inNum;
                    }
                    txtResult.setText(inStr);
                    if(lastOperatpor == '='){
                        result = '0';
                        lastOperatpor = ' ';
                    }
                    break;
                case R.id.btnadd:
                    calculate();
                    lastOperatpor = '+';
                    break;
                case R.id.btnsub:
                    calculate();
                    lastOperatpor = '-';
                    break;
                case R.id.btnmulti:
                    calculate();
                    lastOperatpor = '*';
                    break;
                case R.id.btndiv:
                    calculate();
                    lastOperatpor = '/';
                    break;
                case R.id.btnequal:
                    calculate();
                    lastOperatpor = '=';
                    break;
                case R.id.btnC:
                    calculate();
                    lastOperatpor = ' ';
                    break;
                
            }

        }
    }

    private void calculate() {
        double infinal = Double.parseDouble(inStr);
        inStr = "0";
        if (lastOperatpor == ' ') {
            result = infinal;
        } else if (lastOperatpor == '+') {
            result += infinal;
        } else if (lastOperatpor == '-') {
            result -= infinal;
        } else if (lastOperatpor == '*') {
            result *= infinal;
        } else if (lastOperatpor == '/') {
            result /= infinal;
        } else if (lastOperatpor == '=') {
            //result = infinal;
        }
        txtResult.setText(String.valueOf(result));

    }
}
