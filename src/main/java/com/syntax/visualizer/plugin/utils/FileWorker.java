package com.syntax.visualizer.plugin.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileWorker {
    public static boolean WriteFileFromStream(InputStream source, File destination) {
        try {
            Files.copy(source, destination.toPath());
        } catch (IOException e){
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean UnZip(File source, File destination) {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination.getPath());
        } catch (ZipException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean DeleteFile(File file) {
        if (file.isDirectory()) {
            for (File _file : file.listFiles()) {
                if (!DeleteFile(_file))
                    return false;
            }
        }
        return file.delete();
    }
}
