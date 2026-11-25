package org.jf.util.io;

import com.beust.jcommander.Parameters;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes6.dex */
public class Files {
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        String str = System.currentTimeMillis() + Parameters.DEFAULT_OPTION_PREFIXES;
        for (int i = 0; i < 10000; i++) {
            File file2 = new File(file, str + i);
            if (file2.mkdir()) {
                return file2;
            }
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + str + "0 to " + str + "9999)");
    }

    public static String readContent(File file) throws IOException {
        byte[] byteArray = ByteStreams.toByteArray(new FileInputStream(file));
        return new String(byteArray, 0, byteArray.length, StandardCharsets.UTF_8);
    }

    public static String getNameWithoutExtension(String str) {
        String name2 = new File(str).getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        return iLastIndexOf == -1 ? name2 : name2.substring(0, iLastIndexOf);
    }

    public static String getNameWithoutExtension(File file) {
        String name2 = file.getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        return iLastIndexOf == -1 ? name2 : name2.substring(0, iLastIndexOf);
    }
}
