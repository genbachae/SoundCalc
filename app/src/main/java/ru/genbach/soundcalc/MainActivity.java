package ru.genbach.soundcalc;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener {
    private TextView textView1, textView2;
    private Button iBCE, iBdelit, iBmnojit, iBbackspace, iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8, iB9;
    private Button iBminus, iBplus, iBtochka, iBinvertirovat, iBravno;
    Resources res;
    final int MAX_SYMBOL = 10;                  // не более этого количества цифр
    final int MAX_FULL = 90;                   //  Общее количество символов не должно превышать это кол.-во
    private static final String TAG = "SoundCalc";
    MyCalc myCalc;
    SoundPool sp;                               //  Для звуков
    int[] arrSoundId = new int[10];             //  Для идентификаторов звуков
    final int MAX_STREAMS = 5;                  //  Максимальное кол.-во играемых звуков


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSound();
        res = getResources();
        myCalc = new MyCalc(getResources());                                      //  Экземпляр класса для расчёта
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        setZero();
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
        iBravno.setOnClickListener(this);                       //  Обработкой нажатия занимается этот класс
        iBdelit.setOnClickListener(this);                       //  Обработкой нажатия занимается этот класс
        iBmnojit.setOnClickListener(this);                      //  Обработкой нажатия занимается этот класс
        setColorButtons();
    }

    void initSound(){
        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        arrSoundId[0] = sp.load(MainActivity.this, R.raw.zero, 1);
        arrSoundId[1] = sp.load(MainActivity.this, R.raw.one, 1);
        arrSoundId[2] = sp.load(MainActivity.this, R.raw.two, 1);
        arrSoundId[3] = sp.load(MainActivity.this, R.raw.three, 1);
        arrSoundId[4] = sp.load(MainActivity.this, R.raw.four, 1);
        arrSoundId[5] = sp.load(MainActivity.this, R.raw.five, 1);
        arrSoundId[6] = sp.load(MainActivity.this, R.raw.six, 1);
        arrSoundId[7] = sp.load(MainActivity.this, R.raw.seven, 1);
        arrSoundId[8] = sp.load(MainActivity.this, R.raw.eight, 1);
        arrSoundId[9] = sp.load(MainActivity.this, R.raw.nine, 1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {      //  Сохраняем данные до поворота
        outState.putString("KEY1",textView1.getText().toString());
        outState.putString("KEY2",textView2.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {     //  Восстанавливаем данные после поворота
        super.onRestoreInstanceState(savedInstanceState);
        textView1.setText((String)savedInstanceState.get("KEY1"));
        textView2.setText((String)savedInstanceState.get("KEY2"));
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.d(TAG, "onLoadComplete, sampleId = " + sampleId + ", status = " + status);
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
        int m, i = 0, digit_m = 0;              //  i - для подсчёта итераций!
        char c;                             //  символ из строки
        boolean rez = true;

        try {                                   //  начало блока отслеживания ошибок
            s = e.getText().toString();
            m = s.length();                     //  кол.-во символов в строке
/*            if(m == MAX_SYMBOL){
                i = 0;
            }*/
            if(MAX_FULL <= m){
                //showMess("Превышено максимальное общее число [" + MAX_FULL + "] введённых символов");
                showMess(res.getString(R.string.max11) + MAX_FULL + res.getString(R.string.max12));
                return false;
            }
            while(m > 0 & i < MAX_SYMBOL){
                c = s.charAt(m-1);
                if (Character.isDigit(c)) {     //  если число, то
                    digit_m++;                  //  подсчитываем количество цифр идущих подряд
                    if (digit_m == MAX_SYMBOL){ //  счётчик чисел идущих подряд достиг предела!
                        rez = false;
                        m = 1;
                        //  showMess("Превышено максимальное число [" + MAX_SYMBOL + "] цифр");
                        showMess(res.getString(R.string.max21) + MAX_SYMBOL + res.getString(R.string.max22));
                    }
                }else {                         //  если символ, то
                    digit_m = 0;                //  обнуляем счётчик чисел идущих подряд

                }
                m--;                            //  переходим к следующему символу
                i++;
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
        int posOp = getPosOper(s);              //  Получаем позицию последнего оператора
        int posTchk = s.lastIndexOf('.');   //  Позиция последней точки
        int posZap = s.lastIndexOf(',');    //  позиция последней запятой
        m = s.length();                         //  Кол.-во символов.
        pos = s.charAt(m - 1);                    //  Получить последний символ из строки
        if ((pos == '-' & m == 1) | m == 0) {   //  Если единственный символ минус или нет символов то выходим из процедуры
            return;
        }

/*        if (Character.isDigit(pos)){                            //  если последний символ это число
            if(!Character.isDigit(ch)){                         //  хотят добавить НЕ число
                if(posOp > posTchk & posOp > posZap | posOp == -1) {
                    s = s + ch;
                } else {
                    showMess("Точка/Запятая уже есть!");
                }
            }
        } else {                                                //  последний символ НЕ число
            if(!Character.isDigit(ch)){                         //  хотят добавить НЕ число
                if(posOp > posTchk & posOp > posZap | posOp == -1) {
                    s = s.substring(0, m - 1) + ch;                 //  меняем его в строке
                } else {
                    showMess("Точка/Запятая уже есть!");
                }
            }
        }*/
//      Останов!!!

        if (pos == '/' | pos == '*' | pos == '-' | pos == '+' | pos == '=' | pos == '.') {     //  если ранее был уже введён символ операции или точки, то
            if(ch != '.') {                                                                     //  знак операции не меняем на точку!
                s = s.substring(0, m - 1) + ch;                                                 //  меняем его в строке
            }
        } else {                                                                //  если нет символа операции или точки, то
            if((s.indexOf(".") != -1 & ch == '.') | (s.indexOf(",") != -1 & ch == '.')) {                  //  если хочет добавить ещё одну точку/запятую, то
                if(posOp > posTchk & posOp > posZap | (posOp == -1 & posTchk == -1 & posZap == -1)) {
                    s = s + ch;
                }
            }else {                                                             //  если хочет добавить что-то другое, то
                s = s + ch;
            }
        }

        textView1.setText(s);

    }
    private void calcMy(String s){
        switch(myCalc.isGood(s)){
            case 0:
                textView2.setText(myCalc.toCalc(s));
                break;
            case 1:
                s = s.substring(0,s.length()-1);            //  Отбрасываем знак операции в конце строки
                textView2.setText(myCalc.toCalc(s));
                break;
            case 2:
                //  showMess("Деление на 0 недопустимая операция! Продолжайте вводить цифры отличные от нуля.");
                s = getString(R.string.s_err);
                textView2.setText(s);
                //  Log.d(TAG,"Попытка деления на 0. Результат = ꝏ");
                break;
            case 3:
                s = s.substring(0,s.length()-1);            //  Отбрасываем знак '=' в конце строки
                s = myCalc.toCalc(s);
                textView2.setText(s);
                textView1.setText(myCalc.removeProb(s));       //  перед присваиванием выбросить все пробелы и размерности!!!
                //  protectV = true;                                //  установить запрет на ввод чисел
                break;
        }
    }
    
    private boolean kontrolZero(TextView e){                     //  Чтобы ноль не размножался! Вставлять до добавления ещё одного нуля.
        String s;
        boolean rez = true;                                     //  По умолчанию ввод '0' разрешён
        int m;                                                  //  Для длины строки
        char ch, ch2;
        boolean b_zero = false;                                 //  По умолчанию считать кол.-во нулей после точки не нужно
        int i_zero = 0;                                         //  Для подсчёта кол.-ва нулей

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
                        i_zero++;
                        if (ch2 == '+' | ch2 == '-' | ch2 == '/' | ch2 == '*' | i_zero == MAX_SYMBOL-1){  //  а предпоследний это символ операции или введено уже много 0 после точки, то
                            rez = false;                              //  устанавливаем запрет на добавление ещё одного нуля
                            break;                                    //  выйти  из цикла
                        }
                    }else if(ch == '.' | Character.isDigit(ch)){      //    если точка или число, то
                        rez = true;                                   //    разрешаем ввод нулей
                        break;
                    }
                    m--;                                             // смещаемся в начало строки
                }
            }
        } catch (Exception e1) {
            Log.d(TAG,"Ошибка в kontrolZero(TextView e)", e1);
        }
        return rez;
    }

    void getSound(int id){                                          //  Проигрывает звук по полученному индексу
        sp.play(arrSoundId[id], 1, 1, 1, 0, 1);
    }
    boolean errNot(){
        String s = getString(R.string.s_err);
        String s2 = textView2.getText().toString();
        if(!s.equalsIgnoreCase(s2)) {          //  Если ошибки нет, то
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iB0:
                if (kontrolZero(textView1)){                    //  Нельзя вводить больше одного нуля
                    clickNum('0');
                    getSound(0);
                }
                break;
            case R.id.iB1:
                clickNum('1');
                getSound(1);
                break;
            case R.id.iB2:
                clickNum('2');
                getSound(2);
                break;
            case R.id.iB3:
                clickNum('3');
                getSound(3);
                break;
            case R.id.iB4:
                clickNum('4');
                getSound(4);
                break;
            case R.id.iB5:
                clickNum('5');
                getSound(5);
                break;
            case R.id.iB6:
                clickNum('6');
                getSound(6);
                break;
            case R.id.iB7:
                clickNum('7');
                getSound(7);
                break;
            case R.id.iB8:
                clickNum('8');
                getSound(8);
                break;
            case R.id.iB9:
                clickNum('9');
                getSound(9);
                break;
            case R.id.iBdelit:
                if(errNot()) {                       //  Если ошибки нет, то
                    clickOper('/');
                }
                break;
            case R.id.iBmnojit:
                if(errNot()) {                       //  Если ошибки нет, то
                    clickOper('*');
                }
                break;
            case R.id.iBplus:
                if(errNot()) {                       //  Если ошибки нет, то
                    clickOper('+');
                }
                break;
            case R.id.iBminus:
                if(errNot()) {                       //  Если ошибки нет, то
                    clickOper('-');
                }
                break;
            case R.id.iBtochka:
                clickOper('.');
                break;
            case R.id.iBravno:
                if(errNot()) {                       //  Если ошибки нет, то
                    clickOper('=');
                }
                break;
        }
        optimizStr(textView1);
        //  Начало блока рассчитывающего выражение
        String s = textView1.getText().toString();
        calcMy(s);
        //  Конец блока рассчитывающего выражение
    }
    private void setZero(){
        textView1.setText("0");
        textView2.setText("0");
    }
    
    public void clickBackspace(View view){  // Обработчик кнопки удаления последнего введённого символа
        String s;
        int m;
        try {                               // Начало блока обработки ошибок
            s = textView1.getText().toString();
            m = s.length();
            if (m > 1){                     // Если набрано хотя-бы два числа, то
                s = s.substring(0,m-1);     // Вырезать символы начиная с 0 и до предпоследнего
                textView1.setText(s);       // Присвоить
                calcMy(s);                  //  Пересчёт арифметич. выражения
            } else {                        // Если одно число и менее, то
                setZero();
            }                               // Конец блока обработки ошибок
        } catch (Exception e) {
            Log.d(TAG,"Ошибка в кнопке clickBackspace",e);
        }
    }

    public void clickCE(View view){
        setZero();
    }

    public int getPosOper(String s){            //  Возвратит индекс последнего введенного символа операции
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(s.lastIndexOf("+"));
        pos.add(s.lastIndexOf("*"));
        pos.add(s.lastIndexOf("-"));
        pos.add(s.lastIndexOf("/"));
        int p = Collections.max(pos);
        return p;
    }

    private int findRaz(String s){              //  Находит и возвращает позицию разделителя в строке s
        int p = s.indexOf('.');
        if (p == -1){
            p = s.indexOf(',');
        }
        return p;
    }

    public void clickInvert(View view){    //   обработчик кнопки "+/-"
        String s = textView1.getText().toString();
        s = s.replace(',','.');
        char ch = s.charAt(s.length()-1);
        int p = getPosOper(s);
        if(p == -1 | p == 0){                    //  символов операций в строке небыло
            if(findRaz(s) > -1) {
                double v = Double.parseDouble(s);
                v *= -1.0;
                s = Double.toString(v);
                char ch2 = s.charAt(s.length()-1);
                if(ch == ch2) {                 //  если в результате смены знака последний символ не изменился, то
                    textView1.setText(s);
                }else{                          //  если изменился, то
                    textView1.setText(s.substring(0,s.length()-2));
                }
            } else {                            //  в s число типа int
                int v = Integer.parseInt(s);
                v *= -1;
                s = Integer.toString(v);
                textView1.setText(s);
            }
        }                               //  последний символ операции в строке неразумно менять на знак!
    }
}
