package ru.clevertec.check.exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message,String filename) throws IOException {
        super(message);
        try(FileWriter fw = new FileWriter(filename)) {
            fw.write("ERROR\n");
            fw.write("INSUFFICIENT STOCK\n");
        }
    }
    public InsufficientStockException(String message) throws IOException {
        super(message);
        try(FileWriter fw = new FileWriter("result.csv")) {
            fw.write("ERROR\n");
            fw.write("INSUFFICIENT STOCK\n");
        }
    }
}

