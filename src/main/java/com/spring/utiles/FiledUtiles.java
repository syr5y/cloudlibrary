package com.spring.utiles;



import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FiledUtiles {
    public static String readFiled(String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        return IOUtils.toString(in);
    }
    public static void writeFiled(String data,String path) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        IOUtils.write(data,out);
    }
}
