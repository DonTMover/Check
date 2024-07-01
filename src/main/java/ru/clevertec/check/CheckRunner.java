package ru.clevertec.check;

import java.util.HashMap;

public class CheckRunner {
    private static Integer discountCard;
    private static Integer balanceDebitCard;
    private static HashMap<Integer,Integer> purchases;
    public static void main(String[] args) {
        for (String arg : args) {
            if(arg.startsWith("discountCard=")){
                String[] str = arg.split("=");
                discountCard = Integer.parseInt(str[1]);
            }
            if(arg.startsWith("balanceDebitCard=")){
                String[] str = arg.split("=");
                balanceDebitCard = Integer.parseInt(str[1]);
            }
            else {
                String[] str = arg.split("-");
                purchases.put(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
            }
        }
    }
}
