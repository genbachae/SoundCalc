package ru.genbach.soundcalc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView1, textView2;
    private Button iBCE, iBdelit, iBmnojit, iBbackspace, iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8, iB9;
    private Button iBminus, iBplus, iBtochka, iBinvertirovat, iBravno;
    final int MAX_SYMBOL = 10;                  // не более этого количества цифр
    final int MAX_FULL = 300;                   //  Общее количество символов не должно превышать это кол.-во
    private static final String TAG = "SoundCalc";
    MyCalc myCalc;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCalc = new MyCalc();                                      //  Экземпляр класса для расчёта
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        iBCE = (Button)findViewById(R.id.iBCE);
        iBdelit = (Button)findViewById(R.id.iBdelit);
        iBmnojit = (Button)findViewById(R.id.iBmnojit);
        iBbackspace = (Button)findViewById(R.id.iBbackspace);
        iB0 = (Button)findViewById(R.id.iB0);
        iB1 = (Button)findViewById(R.id.iB1);
        iB2 = (Button)findViewById(R.id.iB2);
        iB3 = (Button)findViewById(R.id.iB3);
        iB4 = (Button)findViewById(R.id.iB4);
        iB5 = (Button)findViewById(R.id.iB5);
        iB6 = (Button)findViewById(R.id.iB6);
        iB7 = (Button)findViewById(R.id.iB7);
        iB8 = (Button)findViewById(R.id.iB8);
        iB9 = (Button)findViewById(R.id.iB9);
        iBminus = (Button)findViewById(R.id.iBminus);
        iBplus = (Button)findViewById(R.id.iBplus);
        iBtochka = (Button)findViewById(R.id.iBtochka);
        iBinvertirovat = (Button)findViewById(R.id.iBinvertirovat);
        iBravno = (Button)findViewById(R.id.iBravno);

        iB0.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB1.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB2.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB3.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB4.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB5.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB6.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB7.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB8.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс
        iB9.setOnClickListener(this);                           //  Обработкой нажатия занимается этот класс

        iBminus.setOnClickListener(this);                       //  Обработкой нажатия занимается этот класс
        iBplus.setOnClickListener(this);                        //  Обработкой нажатия занимается этот класс
        iBtochka.setOnClickListener(this);                      //  Обработкой нажатия занимается этот класс
        iBinvertirovat.setOnClickListener(this);                //  Обработкой нажатия занимается этот класс
        iBravno.setOnClickListener(this);                       //  Обработкой нажатия занимается этот класс
        iBdelit.setOnClickListener(this);                       //  Обработкой нажатия занимается этот класс
        iBmnojit.setOnClickListener(this);                      //  Обработкой нажатия занимается этот класс

        setColorButtons();
    }

    public void optimizStr(TextView e){                         //  Оптимизировать строку(Преобразовывать "012.." в "12..")
        String s, s2;
        int m;
        char ch;
        char ch2;
        boolean b;

        s = e.getText().toString();
        m = s.length();                                         //  кол.-во символов в строке
        if (m == 2){                                             //  минимум 2 символа
            ch = s.charAt(m-2);                                 //  предпоследний символ
            ch2 = s.charAt(m-1);                                //  последний символ
            if (ch == '0'){                            //  если предпоследний символ 0,
                if (Character.isDigit(ch2)){                    //  а последний число, то
                    if (m > 2){
                        s2 = s.substring(0, m - 2) + ch2;             //  пересобрать строку без 0
                    } else{
                        s2 = String.valueOf(ch2);
                    }
                    e.setText(s2);
                }
            }
        }
    }    

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setColorButtons(){ //  Установить чёрный цвета фона всем кнопкам
        Drawable d = MainActivity.this.getResources().getDrawable(android.R.color.black);
        iBCE.setBackground(d);
        iBdelit.setBackground(d);
        iBmnojit.setBackground(d);
        iBbackspace.setBackground(d);
        iB0.setBackground(d);
        iB1.setBackground(d);
        iB2.setBackground(d);
        iB3.setBackground(d);
        iB4.setBackground(d);
        iB5.setBackground(d);
        iB6.setBackground(d);
        iB7.setBackground(d);
        iB8.setBackground(d);
        iB9.setBackground(d);
        iBminus.setBackground(d);
        iBplus.setBackground(d);
        iBtochka.setBackground(d);
        iBinvertirovat.setBackground(d);
        iBravno.setBackground(d);
    }

    private void showMess(String mess){
        Toast.makeText(MainActivity.this, mess, Toast.LENGTH_SHORT).show();
    }

    public boolean proverka(TextView e){    //  Если в текстовом поле меньше MAX_SYMBOL цифр подряд, то возвращ. true иначе false
        String s;                           //  строка
        int m, digit_m = 0;
        char c;                             //  символ из строки
        boolean rez = true;

        try {                                   //  начало блока отслеживания ошибок
            s = e.getText().toString();
            m = s.length();                     //  кол.-во символов в строке
            if(MAX_FULL <= s.length()){
                showMess("Превышено максимальное число [" + MAX_FULL + "] введённых символов");
                return false;
            }
            while(m > 0){
                c = s.charAt(m-1);
                if (Character.isDigit(c)) {     //  если число, то
                    digit_m++;                  //  подсчитываем количество цифр идущих подряд
                    if (digit_m == MAX_SYMBOL){ //  счётчик чисел идущих подряд достиг предела!
                        rez = false;
                        m = 1;
                        showMess("Превышено максимальное число [" + MAX_SYMBOL + "] цифр");
                    }
                }else {                         //  если символ, то
                    digit_m = 0;                //  обнуляем счётчик чисел идущих подряд
                }
                m--;                            //  переходим к следующему символу
            }
        } catch (Exception e1) {
            Log.d(TAG,"Ошибка в функции proverka(textView e)", e1);
        }
        return rez;
    }


    private void clickNum (char c){
        String s;
        if(proverka(textView1)){                // Если число введенных цифр подряд не превышает разумное количество, то
            s = textView1.getText().toString();
            s = s + c;
            textView1.setText(s);
        }
    }

    private void clickOper(char ch) {
        String s;
        char pos;                               //  Последний символ
        int m;
        s = textView1.getText().toString();
        m = s.length();                         //  Кол.-во символов.
        pos = s.charAt(m - 1);                    //  Получить последний символ из строки
        if ((pos == '-' & m == 1) | m == 0) {   //  Если единственный символ минус или нет символов то выходим из процедуры
            return;
        }
        if (pos == '/' | pos == '*' | pos == '-' | pos == '+' | pos == '=' | pos == '.') {    //  если ранее был уже введён символ операции или точки, то
            s = s.substring(0, m - 1) + ch;                                     //  меняем его в строке
        } else {                                                                //  если нет символа операции или точки, то
            s = s + ch;                                                         //  добавляем в конец строки
        }
        textView1.setText(s);

    }
    
    public boolean kontrolZero(TextView e){                     //  Чтобы ноль не размножался! Вставлять до добавления ещё одного нуля.
        String s;
        boolean rez = true;                                     //  По умолчанию ввод '0' разрешён
        int m;                                                  //  Для длины строки
        char ch, ch2;

        try {
            s = e.getText().toString();
            m = s.length();                                         //  кол.-во символов в строке
            if (m == 1){                                            //  если в строке всего один символ
                ch = s.charAt(m-1);
                if (ch == '0'){                                      //  и это ноль
                    rez = false;                                     //  устанавливаем запрет на добавление ещё одного нуля
                }
            } else {                                                 //  Если 2 и более символа, то
                while(m > 1){
                    ch = s.charAt(m-1);                              //  последний символ
                    ch2 = s.charAt(m-2);                             //  предпоследний символ
                    if (ch == '0'){                                             //  если последний символ ноль
                        if (ch2 == '+' | ch2 == '-' | ch2 == '/' | ch2 == '*'){  //  а предпоследний это символ операции, то
                            rez = false;                              //  устанавливаем запрет на добавление ещё одного нуля
                            break;                                    //  выйти  из цикла
                        }
                    }
                    m--;                                             // смещаемся в начало строки
                }
            }
        } catch (Exception e1) {
            Log.d(TAG,"Ошибка в kontrolZero(TextView e)", e1);
        }
        return rez;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iB0:
/*                String s;
                s =  textView1.getText().toString();*/
                if (kontrolZero(textView1)){                    //  Нельзя вводить больше одного нуля
                    clickNum('0');
                }
                break;
            case R.id.iB1:
                clickNum('1');

                break;
            case R.id.iB2:
                clickNum('2');

                break;
            case R.id.iB3:
                clickNum('3');

                break;
            case R.id.iB4:
                clickNum('4');

                break;
            case R.id.iB5:
                clickNum('5');
                break;
            case R.id.iB6:
                clickNum('6');
                break;
            case R.id.iB7:
                clickNum('7');
                break;
            case R.id.iB8:
                clickNum('8');
                break;
            case R.id.iB9:
                clickNum('9');
                break;
            case R.id.iBdelit:
                clickOper('/');
                break;
            case R.id.iBmnojit:
                clickOper('*');
                break;
            case R.id.iBplus:
                clickOper('+');
                break;
            case R.id.iBminus:
                clickOper('-');
                break;
            case R.id.iBtochka:
                clickOper('.');
                break;
            case R.id.iBravno:
                clickOper('=');
                break;
        }
        optimizStr(textView1);
        String s = textView1.getText().toString();
        switch(myCalc.isGood(s)){
            case 0:
   //             try {
                try {
                    textView2.setText(myCalc.toCalc(s));
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
/*                } catch (ScriptException e) {
                    Log.d(TAG,"Ошибка в textView2.setText(myCalc.toCalc(s));", e);
                }*/
                break;
            case 2:
                showMess("Деление на 0 недопустимая операция! Продолжайте вводить цифры отличные от нуля.");
                break;
        }
    }
    
    public void clickBackspace(View view){  // Обработчик кнопки удаления последнего введённого символа
        String s;
        int m;
        try {                               // Начало блока обработки ошибок
            s = textView1.getText().toString();
            m = s.length();
            if (m > 0){                     // Если набрано хотя-бы одно число, то
                s = s.substring(0,m-1);     // Вырезать символы начиная с 0 и до предпоследнего
                textView1.setText(s);       // Присвоить эдиту
            } else {                        // Если одно число и менее, то
                switch (m) {
                    case 0:
                        break;
                    case 1:
                        s = "";
                        break;
                }
            }                               // Конец блока обработки ошибок
        } catch (Exception e) {
            Log.d(TAG,"Ошибка в кнопке clickBackspace",e);
        }
    }

    public void clickCE(View view){
        textView1.setText("");
    }
}
