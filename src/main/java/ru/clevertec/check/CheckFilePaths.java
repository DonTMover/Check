package ru.clevertec.check;

import ru.clevertec.check.exceptions.BadRequestException;

import java.io.IOException;

public class CheckFilePaths {
    protected static void checkFiles(String RESULT_FILE,String datasourceUsername,String datasourcePassword,String datasourceUrl) throws IOException {
        if( RESULT_FILE == null) {
            throw new BadRequestException("Result_File file not found", RESULT_FILE);
        } else if (datasourceUsername == null) {
            throw new BadRequestException("datasource_username not found");
        } else if (datasourcePassword == null) {
            throw new BadRequestException("datasource_password not found");
        }else if (datasourceUrl == null) {
            throw new BadRequestException("datasource_url not found");
        }
    }
}
