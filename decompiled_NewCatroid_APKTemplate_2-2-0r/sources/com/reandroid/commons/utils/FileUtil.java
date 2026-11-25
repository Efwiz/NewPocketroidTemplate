package com.reandroid.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes10.dex */
public class FileUtil implements ShutdownHook.ShutdownListener {
    private static final String EXT_SMALI_FILE = ".smali";
    private static FileUtil INS = new FileUtil();
    private static long current_pid;
    private static File mGlobalTmpDir;
    private static String tmp_dir_root_name;

    private FileUtil() {
    }

    static {
        ShutdownHook.INS.addListener(INS);
    }

    @Override // com.reandroid.commons.utils.ShutdownHook.ShutdownListener
    public void onShutdown() {
        clearTmp();
    }

    private static void clearTmp() {
        deleteDir(getTmpDir());
    }

    private static void deleteDir(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles == null) {
                    deleteEmptyDirectories(file);
                    return;
                }
                for (File file2 : fileArrListFiles) {
                    deleteDir(file2);
                }
                deleteEmptyDirectories(file);
            }
        }
    }

    public static File getTmpDir() {
        File file = new File(new File(getGlobalTmpDir(), getTmpDirRootName()), String.valueOf(getCurrentPid()));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File getGlobalTmpDir() {
        File file = mGlobalTmpDir;
        if (file != null) {
            return file;
        }
        File file2 = new File(File.separator + Constants.TMP_DIRECTORY_NAME);
        if (checkDirWritableDir(file2)) {
            mGlobalTmpDir = file2;
            return file2;
        }
        File file3 = new File(Constants.TMP_DIRECTORY_NAME);
        checkDirWritableDir(file3);
        mGlobalTmpDir = file3;
        return file3;
    }

    public static void setTmpDirRootName(String str) {
        tmp_dir_root_name = str;
    }

    private static String getTmpDirRootName() {
        String str = tmp_dir_root_name;
        return str != null ? str : "reandroid";
    }

    private static long getCurrentPid() {
        long j = current_pid;
        if (j != 0) {
            return j;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        current_pid = jCurrentTimeMillis;
        return jCurrentTimeMillis;
    }

    private static boolean checkDirWritableDir(File file) {
        if (!file.exists() && !file.mkdirs()) {
            return false;
        }
        File file2 = new File(file, "test55557");
        file2.deleteOnExit();
        try {
            file2.createNewFile();
            if (!file2.isFile()) {
                return false;
            }
            file2.delete();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static File getNumberedUniqueFile(File file) {
        String strSubstring;
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            return file;
        }
        String name2 = file.getName();
        int iLastIndexOf = name2.lastIndexOf(".");
        if (iLastIndexOf <= 0) {
            strSubstring = "";
        } else {
            strSubstring = name2.substring(iLastIndexOf);
            name2 = name2.substring(0, iLastIndexOf);
        }
        String strRemoveLastNum = removeLastNum(name2);
        File parentFile = file.getParentFile();
        File file2 = new File(parentFile, buildNumberFileName(strRemoveLastNum, 1, strSubstring));
        for (int i = 1; file2.exists() && i < Integer.MAX_VALUE; i++) {
            file2 = new File(parentFile, buildNumberFileName(strRemoveLastNum, i, strSubstring));
        }
        return file2;
    }

    public static File getNextNumberedFile(File file) {
        String strSubstring;
        if (file == null) {
            return null;
        }
        String name2 = file.getName();
        int iLastIndexOf = name2.lastIndexOf(".");
        if (iLastIndexOf <= 0) {
            strSubstring = "";
        } else {
            strSubstring = name2.substring(iLastIndexOf);
            name2 = name2.substring(0, iLastIndexOf);
        }
        int lastNum = getLastNum(name2) + 1;
        return new File(file.getParentFile(), buildNumberFileName(removeLastNum(name2), lastNum, strSubstring));
    }

    public static File getLastNumberedFile(File file) {
        String strSubstring;
        int lastNum;
        if (file == null) {
            return null;
        }
        if (!file.isFile()) {
            return file;
        }
        String name2 = file.getName();
        int iLastIndexOf = name2.lastIndexOf(".");
        if (iLastIndexOf <= 0) {
            strSubstring = "";
        } else {
            strSubstring = name2.substring(iLastIndexOf);
            name2 = name2.substring(0, iLastIndexOf);
        }
        File[] fileArrListFiles = file.getParentFile().listFiles();
        if (fileArrListFiles == null) {
            return file;
        }
        int lastNum2 = getLastNum(name2) + 1;
        String strRemoveLastNum = removeLastNum(name2);
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                String name3 = file2.getName();
                if (name3.startsWith(strRemoveLastNum) && name3.endsWith(strSubstring) && (lastNum = getLastNum(file2)) > lastNum2) {
                    file = file2;
                    lastNum2 = lastNum;
                }
            }
        }
        return file;
    }

    private static int getLastNum(File file) {
        String name2 = file.getName();
        int iLastIndexOf = name2.lastIndexOf(".");
        if (iLastIndexOf > 0) {
            name2 = name2.substring(0, iLastIndexOf);
        }
        Matcher matcher = Pattern.compile("^.+_([0-9]{1,6})$").matcher(name2);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private static String buildNumberFileName(String str, int i, String str2) {
        String strValueOf;
        if (i < 10000) {
            strValueOf = String.format("%04d", Integer.valueOf(i));
        } else {
            strValueOf = String.valueOf(i);
        }
        return str + "_" + strValueOf + str2;
    }

    private static String removeLastNum(String str) {
        Matcher matcher = Pattern.compile("^.+(_[0-9]{1,6})$").matcher(str);
        if (!matcher.find()) {
            return str;
        }
        return str.substring(0, str.length() - matcher.group(1).length());
    }

    private static int getLastNum(String str) {
        Matcher matcher = Pattern.compile("^.+_([0-9]{1,6})$").matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public static void deleteEmptyDirectories(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            file.delete();
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                deleteEmptyDirectories(file2);
            }
        }
        File[] fileArrListFiles2 = file.listFiles();
        if (fileArrListFiles2 == null || fileArrListFiles2.length == 0) {
            file.delete();
        }
    }

    public static void moveFile(File file, File file2) throws IOException {
        copyFile(file, file2);
        file.delete();
        deleteEmptyDirectories(file.getParentFile());
    }

    public static void copyFile(File file, File file2) throws IOException {
        if (file2.isFile()) {
            throw new IOException("Destination exists: " + file2.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new IOException("Source file does NOT exist: " + file.getAbsolutePath());
        }
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2, false);
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int i = fileInputStream.read(bArr, 0, 1024);
                if (i > 0) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            } catch (IOException e) {
                file2.delete();
                throw e;
            }
        }
    }

    public static int countLines(File file) throws IOException {
        if (file == null) {
            throw new IOException("file == null");
        }
        int i = 0;
        if (!file.isFile()) {
            return 0;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (bufferedReader.readLine() != null) {
            i++;
        }
        bufferedReader.close();
        return i;
    }
}
