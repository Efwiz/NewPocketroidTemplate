package com.koushikdutta.async.util;

import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
public class FileUtility {
    public static boolean deleteDirectory(File file) {
        File[] fileArrListFiles;
        if (file.exists() && (fileArrListFiles = file.listFiles()) != null) {
            for (int i = 0; i < fileArrListFiles.length; i++) {
                if (fileArrListFiles[i].isDirectory()) {
                    deleteDirectory(fileArrListFiles[i]);
                } else {
                    fileArrListFiles[i].delete();
                }
            }
        }
        return file.delete();
    }
}
