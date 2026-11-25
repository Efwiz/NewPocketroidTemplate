package com.reandroid.archive;

import com.reandroid.archive.writer.ApkFileWriter;
import com.reandroid.archive.writer.ZipAligner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class ZipAlign {
    public static void alignApk(File file) throws IOException {
        if (!file.isFile()) {
            throw new FileNotFoundException("No such file: " + file);
        }
        File tmpFile = toTmpFile(file);
        try {
            alignApk(file, tmpFile);
            file.delete();
            tmpFile.renameTo(file);
        } catch (IOException e) {
            tmpFile.delete();
            throw e;
        }
    }

    public static void alignApk(File file, File file2) throws IOException {
        align(file, file2, ZipAligner.apkAligner());
    }

    public static void align(File file, File file2, int i) throws IOException {
        ZipAligner zipAligner = new ZipAligner();
        zipAligner.setDefaultAlignment(i);
        align(file, file2, zipAligner);
    }

    public static void align(File file, File file2, ZipAligner zipAligner) throws IOException {
        if (file.equals(file2)) {
            throw new IOException("Input and output are equal: " + file);
        }
        ApkFileWriter apkFileWriter = new ApkFileWriter(file2, new ArchiveFile(file).getInputSources());
        apkFileWriter.setZipAligner(zipAligner);
        apkFileWriter.write();
    }

    private static File toTmpFile(File file) {
        String str = file.getName() + ".align.tmp";
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return new File(str);
        }
        return new File(parentFile, str);
    }
}
