package ru.clevertec.check.exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
        try(FileWriter fw = new FileWriter("result.csv")) {
            fw.write("ERROR\n");
            fw.write("INTERNAL SERVER ERROR\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InternalServerErrorException(String message, String filename) {
        super(message);
        try(FileWriter fw = new FileWriter(filename)) {
            fw.write("ERROR\n");
            fw.write("INTERNAL SERVER ERROR\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
