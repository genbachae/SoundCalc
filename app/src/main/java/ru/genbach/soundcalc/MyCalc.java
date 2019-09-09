package ru.genbach.soundcalc;

import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCalc {
    private static final String rex1 = "\\/[0\\.0]+$";              //  Регулярное выражение для выявления деления на ноль в конце строки
    private static final String rex2 = "[\\+\\-\\*\\/]$";           //  Регулярное выражение для выявления знака операции в конце строки
    public String name;

    MyCalc(){
        name = "MyCalc";
    }

    public String toCalc(String operS) throws ScriptException {        //  Рассчитать значение по строке с арифметическим значением
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = engine.eval(operS);
        return result.toString();
    }

    public byte isGood(String operS) {  // Вернёт 0 - если строка operS верна, 1 - если в конце operS есть '+' или '-' или '*' или '/', 2 - деление на ноль
        Pattern p = Pattern.compile(rex1);      //  скомпилировали регулярное выражение в представление
        Matcher m = p.matcher(operS);           //  создали поисковик в тексте по шаблону выше
        if (m.find()) {                         //  если удовлетворяет регулярному выражению, то
            return 2;                           //  2 - в конце строки присутствует деление на ноль
        }
        p = Pattern.compile(rex2);              //  скомпилировали регулярное выражение в представление
        m = p.matcher(operS);                   //  создали поисковик в тексте по шаблону выше
        if (m.find()) {                         //  если удовлетворяет регулярному выражению, то
            return 1;                           //  1 - в конце строки присутствует знак операции
        }
        return 0;                               //  строка готова для расчёта
    }

/*    private int len_vir;    //  хранит длину последней переданной строки с выражением
    private float rez;      //  хранит последний рассчитанный результат
    private char[] oper = {'/', '*', '-', '+', '='};    //  массив операций которые есть в канкуляторе*/

/*    public ArrayList getPosOper(String virStr){      //  вернёт позицию первого операнда что будет найден, должен быть приватным
        char ch;
        ArrayList arrOper = new ArrayList();        //  массив с номерами позиций в которых находяться операнды

        for (int i = 0; i < virStr.length(); i++) {
            ch = virStr.charAt(i);
            if (!Character.isDigit(ch)) {       //  Если найдено первое вхождение операнда, то
                arrOper.add(i);                       //  занести его позицию в список
            }
        }
        return arrOper;
    }*/

/*    public float toCalc(String virStr) {
        String vir;                         //  Выражение которое будет парситься
        int new_len = virStr.length();      //  Длина строки с выражением

        if (new_len > len_vir & len_vir != 0 & rez != 0.0) {             //  Если длина переданного выражения больше предыдущего и ранее был рассчитан результат, то

        }


    }*/

}
