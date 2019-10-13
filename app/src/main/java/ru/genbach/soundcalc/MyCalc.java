package ru.genbach.soundcalc;

import android.content.res.Resources;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCalc {
    private static final String rex1 = "\\/[0\\.0]+$";              //  Регулярное выражение для выявления деления на ноль в конце строки
    private static final String rex2 = "[\\+\\-\\*\\/]$";           //  Регулярное выражение для выявления знака операции в конце строки
    private static final String rex3 = "[\\=]$";                    //  Регулярное выражение для выявления знака '=' в конце строки
    private static Resources res;

    public String name;

    MyCalc(Resources r){
        res = r;
        name = "MyCalc";
    }

    private static String formatRez(String str, boolean anot){    //  Форматирует результат в виде 12 343 567 567 890,035; anot = true, то писать млн, млрд, и т.д.
        final int COUNT_RAZ = 13;
        int n;                                              //  позиция с которой будем парсить строку
        int j=-1;                                           //  Для навигации по млн, млрд, и т.д.
        String s;                                           //  если будет ошибка, то перенести в цикл
        String hvost = "";                                  //  для хранения чисел, после запятой
        StringBuilder rez = new StringBuilder("");
        LinkedList<String> list = new LinkedList<>();
        ArrayList<String> razm = new ArrayList<>(COUNT_RAZ);
        razm.add(" " + res.getString(R.string.str1) + " ");
        razm.add(" " + res.getString(R.string.str2) + " ");
        razm.add(" " + res.getString(R.string.str3) + " ");
        razm.add(" " + res.getString(R.string.str4) + " ");
        razm.add(" " + res.getString(R.string.str5) + " ");
        razm.add(" " + res.getString(R.string.str6) + " ");
        razm.add(" " + res.getString(R.string.str7) + " ");
        razm.add(" " + res.getString(R.string.str8) + " ");
        razm.add(" " + res.getString(R.string.str9) + " ");
        razm.add(" " + res.getString(R.string.str10) + " ");
        razm.add(" " + res.getString(R.string.str11) + " ");
        razm.add(" " + res.getString(R.string.str12) + " ");
        razm.add(" " + res.getString(R.string.str13));
        n = str.lastIndexOf(",");
        if(n == -1){
            n = str.lastIndexOf(".");
        }
        if(n == -1){                                        //  Если в числе нет разделителя, то
            n = str.length();                               //  запоминаем индекс последнего символа
        } else {                                            //  если разделитель есть, то
            hvost = str.substring(n, str.length());         //  сохраняем хвост изначального числа
        }
        if (n >= 3) {                                       //  если больше чем одна триада, то
            while (n >= 3) {
                s = str.substring(n - 3, n);                //  берём триаду с конца строки
                list.add(s);
                n -= 3;
            }
            if(n > 0){
                s = str.substring(0, n);                  //  берём то что осталось
                list.add(s);
            }
        }else{                                              //  если всего одна триада, то
            rez.append(str);                                //  возвращаем тоже, что и получили
        }
        n = list.size();

        if(n > 0){                                          //  если в списке что-то есть, то
            if (n > 1){
                j = n - 2;
            }
            for (int i = 0; i < n; i++) {                   //  собираем
                s = list.getLast();
                rez.append(s);                              //  строку
                list.removeLast();
                if (i < n-1 & !s.equals("-")) {             //  если не последняя часть и это не минус, то
                    if(j >=0 & anot & j < COUNT_RAZ){
                        rez.append(razm.get(j));            //  c размерностью
                        j--;
                    }else {
                        rez.append(" ");                    //  с пробелами
                    }
                } else {
                    j--;
                }
            }
            rez.append(hvost);                              //  не забываем про хвост
        }
        return rez.toString();
    }

    private static String removeDotZero(String str) {            //  Удаляет лишние нули, после разделителя
        int n;
        char ch;
        String rez = "";
        n = str.lastIndexOf(",");
        if(n == -1){
            n = str.lastIndexOf(".");
        }
        if(n == -1) {                                           //  Если в числе нет разделителя, то
            rez = str;                                          //  Возвращаем строку в неизменном состоянии
        }else {                                                 //  Если есть разделитель, то
            for (int i = str.length()-1; i > n-1; i--) {
                ch = str.charAt(i);
                if(ch != '0'){
                    if(Character.isDigit(ch)) {                 //  Если число, то
                        rez = str.substring(0, i + 1);          //  вырезать включая его
                    }else{                                      //  Если не число, то
                        rez = str.substring(0, i);              //  вырезать не включая его
                    }
                    break;
                }
            }
        }
        return rez;
    }

    public static String toCalc(String operS) {        			//  Рассчитать значение по строке с арифметическим значением
        operS = operS.replace(',', '.');        //  Если запятая то меняем её на точку
        BigDecimal result = null;
        Expression expression = new Expression(operS);
        expression.setPrecision(128);                           //  Устанавливаем точность
        result = expression.eval();                             //  Вычисление арифметич выражения, в конце не должно быть '='
        String s = String.format("%.9f",result.doubleValue());
        return formatRez(removeDotZero(s), true);
    }

    public byte isGood(String operS) {          // Вернёт 0 - если строка operS верна, 1 - если в конце operS есть '+' или '-' или '*' или '/', 2 - деление на ноль
        Pattern p = Pattern.compile(rex1);      //  скомпилировали регулярное выражение в представление
        Matcher m = p.matcher(operS);           //  создали поисковик в тексте по шаблону выше
        if (m.find()) {                         //  если удовлетворяет регулярному выражению, то
            return 2;                           //  в конце строки присутствует деление на ноль
        }
        p = Pattern.compile(rex2);              //  скомпилировали регулярное выражение в представление
        m = p.matcher(operS);                   //  создали поисковик в тексте по шаблону выше
        if (m.find()) {                         //  если удовлетворяет регулярному выражению, то
            return 1;                           //  в конце строки присутствует знак операции
        }
        p = Pattern.compile(rex3);              //  скомпилировали регулярное выражение в представление
        m = p.matcher(operS);                   //  создали поисковик в тексте по шаблону выше
        if (m.find()) {                         //  если удовлетворяет регулярному выражению, то
            return 3;                           //  в конце строки присутствует знак '='
        }
        return 0;                               //  строка готова для расчёта
    }

    public static String removeProb(String str) {            //  Удаляет размерности/пробелы из строки
        return str.replaceAll("([ а-я\\.]){4,12}","").replaceAll(" ","");
    }
}
