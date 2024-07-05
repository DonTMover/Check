package ru.clevertec.check.exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
        try(FileWriter fw = new FileWriter("result.csv")) {
            fw.write("ERROR\n");
            fw.write("NOT ENOUGH MONEY\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NotEnoughMoneyException(String message,String filename) {
        super(message);
        try(FileWriter fw = new FileWriter(filename)) {
            fw.write("ERROR\n");
            fw.write("NOT ENOUGH MONEY\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
