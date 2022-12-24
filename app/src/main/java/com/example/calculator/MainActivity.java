package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ezylang.evalex.BaseException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;

public class MainActivity extends AppCompatActivity {

    //поле для выражения
    EditText editTextExpression;
    // поле для вывода результата
    EditText editTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //найти поля ввода по идентификаторам
        //положить их в соответствующие переменные
        editTextExpression = findViewById(R.id.editTextExpresion);
        editTextResult = findViewById(R.id.editTextResult);
    }

    public void clickNum(View view) {
        //взять текст с нажатой кнопки
        Button pressedButton = (Button) view;
        //и добавить его в выражение
        editTextExpression.setText(  editTextExpression.getText()
                                    +pressedButton.getText().toString());
    }

    public void equal(View view) {
        //убрать курсор редактирования с толей ввода
        view.requestFocus();

        //взять выражение из поля ввода
        String strExpression = editTextExpression.getText().toString();

        //замена процента на выражение
        strExpression = strExpression.replace("%","/100*");


        Expression expression = new Expression(strExpression);

        try {
            //посчитать выражение
            EvaluationValue result = expression.evaluate();
            //вывести результат
            editTextResult.setText(result.getStringValue());
        }
        //если проблема
        catch (BaseException baseException)
        {
            //выделить проблемный символ
            editTextExpression.requestFocus();
            editTextExpression.setSelection(baseException.getStartPosition(),
                                            baseException.getEndPosition());
            //показать сообщение о проблеме
            editTextResult.setText(baseException.getMessage());
        }


    }

    public void backspace(View view) {
        //удалить последний символ
        String expression = editTextExpression.getText().toString();

        editTextExpression.setText(expression.substring(0,expression.length()-1));
    }

    public void clear(View view) {
        //очистить поле для выражения
        editTextExpression.setText("");
    }


    public void clickSqrt(View view) {
        editTextExpression.setText(editTextExpression.getText()
                +"sqrt");
    }

    public void clickFact(View view) {
        editTextExpression.setText(editTextExpression.getText()
                +"fact");
    }
}