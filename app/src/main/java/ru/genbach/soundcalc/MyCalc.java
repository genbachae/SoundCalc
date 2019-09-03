package ru.genbach.soundcalc;

public class MyCalc {
    private int len_vir;    //  хранит длину последней переданной строки с выражением
    private float rez;      //  хранит последний рассчитанный результат
    private char[] oper = {'/', '*', '-', '+', '='};    //  массив операций которые есть в канкуляторе

    public int getPosOper(String virStr){      //  вернёт позицию первого операнда что будет найден, должен быть приватным
        char ch;
        for (int i = 0; i < virStr.length(); i++) {
            ch = virStr.charAt(i);
            if (!Character.isDigit(ch)) {       //  Если найдено первое вхождение операнда, то
                return i;                       //  вернуть его позицию в строке.
            }
        }
    }

    public float toCalc(String virStr) {
        String vir;                         //  Выражение которое будет парситься
        int new_len = virStr.length();      //  Длина строки с выражением

        if (new_len > len_vir & len_vir != 0 & rez != 0.0) {             //  Если длина переданного выражения больше предыдущего и ранее был рассчитан результат, то

        }


    }

}
