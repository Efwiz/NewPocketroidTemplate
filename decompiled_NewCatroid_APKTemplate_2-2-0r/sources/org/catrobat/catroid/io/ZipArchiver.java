package org.catrobat.catroid.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/ZipArchiver.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ZipArchiver {
    private static final int COMPRESSION_LEVEL = 0;
    private static final String DIRECTORY_LEVEL_UP = "../";

    public void zip(File archive, File[] files) throws IOException {
        archive.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(archive);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        try {
            zipOutputStream.setLevel(0);
            writeZipEntriesToStream(zipOutputStream, Arrays.asList(files), "");
        } finally {
            zipOutputStream.close();
            fileOutputStream.close();
        }
    }

    private void writeZipEntriesToStream(ZipOutputStream zipOutputStream, List<File> files, String parentDir) throws IOException {
        for (File file : files) {
            if (!file.exists()) {
                throw new FileNotFoundException("File: " + file.getAbsolutePath() + " does NOT exist.");
            }
            if (file.isDirectory()) {
                writeZipEntriesToStream(zipOutputStream, Arrays.asList(file.listFiles()), parentDir + file.getName() + "/");
            } else {
                zipOutputStream.putNextEntry(new ZipEntry(parentDir + file.getName()));
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i == -1) {
                                break;
                            } else {
                                zipOutputStream.write(bArr, 0, i);
                            }
                            zipOutputStream.closeEntry();
                            throw th;
                        }
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    zipOutputStream.closeEntry();
                    throw th3;
                }
            }
        }
    }

    public void unzip(File archive, File dstDir) throws IOException {
        unzip(new FileInputStream(archive), dstDir);
    }

    public void unzip(InputStream is, File dstDir) throws IOException {
        createDirIfNecessary(dstDir);
        ZipInputStream zipInputStream = new ZipInputStream(is);
        while (true) {
            try {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    if (!nextEntry.getName().contains(DIRECTORY_LEVEL_UP)) {
                        if (nextEntry.isDirectory()) {
                            createDirIfNecessary(new File(dstDir, nextEntry.getName()));
                        } else {
                            File file = new File(dstDir, nextEntry.getName());
                            file.getParentFile().mkdirs();
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                byte[] bArr = new byte[8192];
                                while (true) {
                                    int i = zipInputStream.read(bArr);
                                    if (i == -1) {
                                        break;
                                    } else {
                                        fileOutputStream.write(bArr, 0, i);
                                    }
                                    try {
                                        zipInputStream.close();
                                    } catch (Throwable th) {
                                        th.addSuppressed(th);
                                    }
                                    throw th;
                                }
                                fileOutputStream.close();
                            } catch (Throwable th2) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                                throw th2;
                            }
                        }
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            } catch (Throwable th4) {
                zipInputStream.close();
                throw th4;
            }
        }
    }

    private void createDirIfNecessary(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdir()) {
            throw new IOException("Could NOT create Dir: " + dir.getAbsolutePath());
        }
    }
}
