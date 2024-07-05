package ru.clevertec.check;

import ru.clevertec.check.exceptions.BadRequestException;

import java.io.FileWriter;
import java.io.IOException;

public class CheckFilePaths {
    protected static void checkFiles(String PRODUCTS_FILE,String RESULT_FILE) throws IOException {
        if(PRODUCTS_FILE == null || RESULT_FILE != null){
            throw new BadRequestException("Products file not found", RESULT_FILE);
            
        } else if (PRODUCTS_FILE == null || RESULT_FILE == null) {
            throw new BadRequestException("Products file not found");
        } else if (PRODUCTS_FILE != null || RESULT_FILE == null) {
            throw new BadRequestException("Result file not found");
        }
    }
}
