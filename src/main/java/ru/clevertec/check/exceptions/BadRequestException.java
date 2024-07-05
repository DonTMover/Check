package ru.clevertec.check.exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, String filename) throws IOException {
        super(message);
        try(FileWriter fw = new FileWriter(filename)) {
            fw.write("ERROR\n");
            fw.write("BAD REQUEST\n");
        }

    }

    public BadRequestException(String message) throws IOException {
        super(message);
        try(FileWriter fw = new FileWriter("result.csv")) {
            fw.write("ERROR\n");
            fw.write("BAD REQUEST\n");
        }
    }
}
