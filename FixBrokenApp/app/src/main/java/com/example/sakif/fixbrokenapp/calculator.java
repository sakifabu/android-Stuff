package com.example.sakif.fixbrokenapp;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class calculator extends AppCompatActivity {

    public TextView txtResult;
    public String inStr = "0" ;
    public char lastOperatpor = ' ';
    public double result = 0 ;
    private double inNum1 =0;
    private double radians =0;
    private double MemoryStore =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText("0");

        BtnListener listener = new BtnListener() ;
        ((Button) findViewById(R.id.btnMC)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnMR)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnMS)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnMadd)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnMsub)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnBK)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnCE)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnC)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnRoot)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnLn)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnSin)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnCos)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnTan)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnLog)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnPercent)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnInverse)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnMod)).setOnClickListener(listener);
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
        ((Button) findViewById(R.id.btnequal)).setOnClickListener(listener);
    }
    private class BtnListener implements View.OnClickListener {
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.btn0:case R.id.btn1:case R.id.btn2:case R.id.btn3:case R.id.btn4:
                case R.id.btn5:case R.id.btn6:case R.id.btn7:case R.id.btn8:case R.id.btn9:
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
                case R.id.btnPercent:
                    calculate();
                    lastOperatpor = '%';
                    break;
                case R.id.btnMod:
                    calculate();
                    lastOperatpor = 'M';
                    break;
                case R.id.btnRoot:
                    inNum1 = Double.parseDouble(inStr);
                    txtResult.setText(String.valueOf(Math.sqrt(inNum1)));
                    break;
                case R.id.btnLn:
                    inNum1 = Double.parseDouble(inStr);
                    txtResult.setText(String.valueOf(Math.log(inNum1)));
                    break;
                case R.id.btnLog:
                    inNum1 = Double.parseDouble(inStr);
                    txtResult.setText(String.valueOf(Math.log10(inNum1)));
                    break;
                case R.id.btnInverse:
                    inNum1 = Double.parseDouble(inStr);
                    txtResult.setText(String.valueOf(1/inNum1));
                    break;
                case R.id.btnSin:
                    inNum1 = Double.parseDouble(inStr);
                    radians = Math.toRadians(inNum1);
                    txtResult.setText(String.valueOf(Math.sin(radians)));
                case R.id.btnCos:
                    inNum1 = Double.parseDouble(inStr);
                    radians = Math.toRadians(inNum1);
                    txtResult.setText(String.valueOf(Math.cos(radians)));
                case R.id.btnTan:
                    inNum1 = Double.parseDouble(inStr);
                    radians = Math.toRadians(inNum1);
                    txtResult.setText(String.valueOf(Math.tan(radians)));
                case R.id.btnMC:
                    MemoryStore=0;
                    break;
                case R.id.btnMS:
                    MemoryStore=Double.parseDouble(inStr);
                    break;
                case R.id.btnMR:
                    txtResult.setText(String.valueOf(MemoryStore));
                    break;
                case R.id.btnMsub:
                    inNum1 =Double.parseDouble(inStr);
                    MemoryStore -= inNum1;
                    break;
                case R.id.btnMadd:
                    inNum1=Double.parseDouble(inStr);
                    MemoryStore += inNum1;
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
        }else if (lastOperatpor == '%') {
            result += (inNum1*result)/100;
        }else if (lastOperatpor == 'M') {
            result %= inNum1;
        }
        txtResult.setText(String.valueOf(result));

    }
}