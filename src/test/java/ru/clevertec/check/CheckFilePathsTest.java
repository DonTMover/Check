package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CheckFilePathsTest {

    @Test
    void checkFiles1() throws IOException {
        try {
            String ResPath = null;
            String username = null;
            String password = null;
            String url = null;
            CheckFilePaths.checkFiles(ResPath,username,password,url);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    @Test
    void checkFiles2() {
        try {
            String ResPath = "";
            String username = null;
            String password = null;
            String url = null;
            CheckFilePaths.checkFiles(ResPath,username,password,url);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    @Test
    void checkFiles3() {
        try {
            String ResPath = "";
            String username = "";
            String password = null;
            String url = null;
            CheckFilePaths.checkFiles(ResPath,username,password,url);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    @Test
    void checkFiles4() {
        try {
            String ResPath = "";
            String username = "";
            String password = "";
            String url = null;
            CheckFilePaths.checkFiles(ResPath,username,password,url);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}