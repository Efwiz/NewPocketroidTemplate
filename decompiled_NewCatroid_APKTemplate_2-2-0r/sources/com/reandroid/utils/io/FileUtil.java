package com.reandroid.utils.io;

import com.beust.jcommander.Parameters;
import com.reandroid.arsc.ARSCLib;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes11.dex */
public class FileUtil {
    private static String def_prefix;
    private static final Map<String, File> TEMP_DIRS = new HashMap();
    private static final String[] FILE_SIZE_UNITS = {" bytes", " Kb", " Mb", " Gb", " Tb", " Pb"};

    public static File toTmpName(File file) {
        File parentFile = file.getParentFile();
        String str = file.getName() + ".tmp";
        if (parentFile == null) {
            return new File(str);
        }
        return new File(parentFile, str);
    }

    @Deprecated
    public static void writeUtf8(File file, String str) throws IOException {
        IOUtil.writeUtf8(str, file);
    }

    public static String combineFilePath(String str, String str2) {
        return combinePath(File.separatorChar, str, str2);
    }

    public static String combineUnixPath(String str, String str2) {
        return combinePath('/', str, str2);
    }

    public static String combinePath(char c, String str, String str2) {
        if (StringsUtil.isEmpty(str)) {
            return str2;
        }
        if (StringsUtil.isEmpty(str2)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 1);
        sb.append(str);
        if (str.charAt(str.length() - 1) != c) {
            sb.append(c);
        }
        sb.append(str2);
        return sb.toString();
    }

    public static String shortPath(File file, int i) {
        File file2 = file;
        while (i > 0) {
            File parentFile = file2.getParentFile();
            if (parentFile == null) {
                break;
            }
            i--;
            file2 = parentFile;
        }
        if (file == file2) {
            return file.getName();
        }
        return file.getAbsolutePath().substring(file2.getAbsolutePath().length() + 1);
    }

    public static String getParent(String str) {
        if (StringsUtil.isEmpty(str)) {
            return StringsUtil.EMPTY;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = str.lastIndexOf(92);
        }
        if (iLastIndexOf <= 0) {
            return StringsUtil.EMPTY;
        }
        return str.substring(0, iLastIndexOf + 1);
    }

    public static String getFileName(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = str.lastIndexOf(92);
        }
        return iLastIndexOf >= 0 ? str.substring(iLastIndexOf + 1) : str;
    }

    public static String getNameWoExtension(File file) {
        return getNameWoExtensionForSimpleName(file.getName());
    }

    public static String getNameWoExtension(String str) {
        return getNameWoExtensionForSimpleName(getFileName(str));
    }

    private static String getNameWoExtensionForSimpleName(String str) {
        if (str.endsWith(".9.png")) {
            return str.substring(0, str.length() - ".9.png".length());
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf < 0 ? str : str.substring(0, iLastIndexOf);
    }

    public static String getExtension(File file) {
        return getExtensionForSimpleName(file.getName());
    }

    public static String getExtension(String str) {
        return getExtensionForSimpleName(getFileName(str));
    }

    private static String getExtensionForSimpleName(String str) {
        if (str.endsWith(".9.png")) {
            return ".9.png";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf < 0) {
            return StringsUtil.EMPTY;
        }
        return str.substring(iLastIndexOf);
    }

    public static String toReadableFileSize(long j) {
        if (j < 0) {
            return Long.toString(j);
        }
        String[] strArr = FILE_SIZE_UNITS;
        String str = "";
        int i = 0;
        long j2 = 0;
        while (i < strArr.length) {
            long j3 = i == 0 ? 1024L : 1000L;
            str = strArr[i];
            long j4 = j / j3;
            if (j4 == 0) {
                break;
            }
            j2 = j - (j3 * j4);
            i++;
            j = j4;
        }
        if (j2 == 0) {
            return j + str;
        }
        return j + "." + j2 + str;
    }

    public static InputStream inputStream(File file) throws IOException {
        if (!file.isFile()) {
            throw new FileNotFoundException("No such file: " + file);
        }
        return new FileInputStream(file);
    }

    public static OutputStream outputStream(File file) throws IOException {
        ensureParentDirectory(file);
        return new FileOutputStream(file);
    }

    public static void ensureParentDirectory(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null || parentFile.exists()) {
            return;
        }
        parentFile.mkdirs();
    }

    public static void createNewFile(File file) throws IOException {
        ensureParentDirectory(file);
        if (file.isFile()) {
            file.delete();
        }
        file.createNewFile();
    }

    public static void deleteDirectory(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                file.delete();
                return;
            }
            for (File file2 : fileArrListFiles) {
                deleteDirectory(file2);
            }
            file.delete();
        }
    }

    public static void deleteEmptyDirectory(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            file.delete();
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (!file2.isDirectory()) {
                return;
            }
            deleteEmptyDirectory(file2);
        }
        deleteIfEmptyDirectory(file);
    }

    private static void deleteIfEmptyDirectory(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            file.delete();
        }
    }

    public static File getTempDir() {
        return getTempDir(null);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0019 A[Catch: all -> 0x002e, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x0019, B:14:0x002c, B:11:0x0020, B:13:0x0026), top: B:19:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:11:0x0020 A[Catch: all -> 0x002e, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x0019, B:14:0x002c, B:11:0x0020, B:13:0x0026), top: B:19:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x0026 A[Catch: all -> 0x002e, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x0019, B:14:0x002c, B:11:0x0020, B:13:0x0026), top: B:19:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:8:0x0013 A[Catch: all -> 0x002e, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x0019, B:14:0x002c, B:11:0x0020, B:13:0x0026), top: B:19:0x0005 }] */
    public static File getTempDir(String str) {
        Map<String, File> map;
        File writableTempDir;
        synchronized (FileUtil.class) {
            if (str == null) {
                str = getDefRootName();
                map = TEMP_DIRS;
                writableTempDir = map.get(str);
                if (writableTempDir == null) {
                    writableTempDir = getWritableTempDir(str);
                    if (writableTempDir != null) {
                        writableTempDir.deleteOnExit();
                        map.put(str, writableTempDir);
                    }
                } else if (!writableTempDir.exists()) {
                    writableTempDir.mkdir();
                    writableTempDir.deleteOnExit();
                }
            } else {
                map = TEMP_DIRS;
                writableTempDir = map.get(str);
                if (writableTempDir == null) {
                    writableTempDir = getWritableTempDir(str);
                    if (writableTempDir != null) {
                        writableTempDir.deleteOnExit();
                        map.put(str, writableTempDir);
                    }
                } else if (!writableTempDir.exists()) {
                    writableTempDir.mkdir();
                    writableTempDir.deleteOnExit();
                }
            }
            throw th;
        }
        return writableTempDir;
    }

    private static File getWritableTempDir(String str) {
        File writableTempDir = getWritableTempDir(System.getProperty("java.io.tmpdir", null), str);
        if (writableTempDir == null) {
            writableTempDir = getWritableTempDir(System.getProperty("user.home", null), str);
        }
        if (writableTempDir != null) {
            return writableTempDir;
        }
        File file = new File(new File(Constants.TMP_DIRECTORY_NAME).getAbsolutePath());
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            file = parentFile;
        }
        return getWritableTempDir(file.getAbsolutePath(), str);
    }

    private static File getWritableTempDir(String str, String str2) {
        if (str == null) {
            return null;
        }
        return getWritableTempDir(new File(str), str2);
    }

    private static File getWritableTempDir(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.isDirectory() && !file2.mkdirs()) {
            return null;
        }
        String str2 = "test_" + System.currentTimeMillis() + Parameters.DEFAULT_OPTION_PREFIXES;
        for (int i = 0; i < 9999; i++) {
            File file3 = new File(file2, str2 + i);
            if (!file3.exists()) {
                try {
                    if (!file3.createNewFile() || !file3.delete()) {
                        break;
                    }
                    return file2;
                } catch (IOException unused) {
                }
            }
        }
        return null;
    }

    public static void setDefaultTempPrefix(String str) {
        synchronized (FileUtil.class) {
            if (ObjectsUtil.equals(str, def_prefix)) {
                return;
            }
            String str2 = def_prefix;
            if (str2 != null) {
                TEMP_DIRS.remove(str2);
            }
            def_prefix = str;
        }
    }

    private static String getDefRootName() {
        if (def_prefix == null) {
            def_prefix = "tmp_" + ARSCLib.getName() + Parameters.DEFAULT_OPTION_PREFIXES + ARSCLib.getVersion();
        }
        return def_prefix;
    }
}
