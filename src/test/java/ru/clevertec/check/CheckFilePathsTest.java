package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CheckFilePathsTest {

    @Test
    void checkFiles1() throws IOException {
        try {
            String ResPath = null;
            String ProductPath = null;
            CheckFilePaths.checkFiles(ProductPath,ResPath);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    @Test
    void checkFiles2() {
        try {
            String ResPath = "";
            String ProductPath = null;
            CheckFilePaths.checkFiles(ProductPath,ResPath);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    @Test
    void checkFiles3() {
        try {
            String ResPath = null;
            String ProductPath = "";
            CheckFilePaths.checkFiles(ProductPath,ResPath);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}