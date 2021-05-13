package com.syntax.visualizer.plugin.utils;

import com.syntax.visualizer.plugin.globals.ProjectLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileWorker {
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public static boolean UnZip(InputStream source, File destinationDir) {
        try(ZipInputStream zis = new ZipInputStream(source)) {
            ZipEntry zipEntry;
            final byte[] buffer = new byte[1024];
            while ((zipEntry = zis.getNextEntry()) != null) {
                final File newFile = newFile(destinationDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory: " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    final FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zis.closeEntry();
        } catch (IOException e) {
            ProjectLogger.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static boolean DeleteFile(File file) {
        if (file.isDirectory()) {
            for (File _file : Objects.requireNonNull(file.listFiles())) {
                if (!DeleteFile(_file))
                    return false;
            }
        }
        return file.delete();
    }

    public static boolean DeleteIfExist(File file) {
        return !file.exists() || FileWorker.DeleteFile(file);
    }
}
