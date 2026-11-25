package com.badlogic.gdx.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes8.dex */
public class SharedLibraryLoader {
    public static Architecture architecture;
    public static Architecture.Bitness bitness;

    @Deprecated
    public static boolean is64Bit;

    @Deprecated
    public static boolean isARM;

    @Deprecated
    public static boolean isAndroid;

    @Deprecated
    public static boolean isIos;

    @Deprecated
    public static boolean isLinux;

    @Deprecated
    public static boolean isMac;

    @Deprecated
    public static boolean isWindows;
    private static final HashSet<String> loadedLibraries;
    public static Os os;
    private static final Random random;
    private String nativesJar;

    static {
        bitness = Architecture.Bitness._32;
        architecture = Architecture.x86;
        if (System.getProperty(SystemProperties.OS_NAME).contains("Windows")) {
            os = Os.Windows;
        } else if (System.getProperty(SystemProperties.OS_NAME).contains("Linux")) {
            os = Os.Linux;
        } else if (System.getProperty(SystemProperties.OS_NAME).contains("Mac")) {
            os = Os.MacOsX;
        }
        if (System.getProperty(SystemProperties.OS_ARCH).startsWith("arm") || System.getProperty(SystemProperties.OS_ARCH).startsWith("aarch64")) {
            architecture = Architecture.ARM;
        } else if (System.getProperty(SystemProperties.OS_ARCH).startsWith("riscv")) {
            architecture = Architecture.RISCV;
        } else if (System.getProperty(SystemProperties.OS_ARCH).startsWith("loongarch")) {
            architecture = Architecture.LOONGARCH;
        }
        if (System.getProperty(SystemProperties.OS_ARCH).contains("64") || System.getProperty(SystemProperties.OS_ARCH).startsWith("armv8")) {
            bitness = Architecture.Bitness._64;
        } else if (System.getProperty(SystemProperties.OS_ARCH).contains("128")) {
            bitness = Architecture.Bitness._128;
        }
        boolean z = System.getProperty("moe.platform.name") != null;
        String property = System.getProperty(SystemProperties.JAVA_RUNTIME_NAME);
        if (property != null && property.contains("Android Runtime")) {
            os = Os.Android;
            bitness = Architecture.Bitness._32;
            architecture = Architecture.x86;
        }
        if (z || (os != Os.Android && os != Os.Windows && os != Os.Linux && os != Os.MacOsX)) {
            os = Os.IOS;
            bitness = Architecture.Bitness._32;
            architecture = Architecture.x86;
        }
        isWindows = os == Os.Windows;
        isLinux = os == Os.Linux;
        isMac = os == Os.MacOsX;
        isIos = os == Os.IOS;
        isAndroid = os == Os.Android;
        isARM = architecture == Architecture.ARM;
        is64Bit = bitness == Architecture.Bitness._64;
        loadedLibraries = new HashSet<>();
        random = new Random();
    }

    public SharedLibraryLoader() {
    }

    static String randomUUID() {
        Random random2 = random;
        return new UUID(random2.nextLong(), random2.nextLong()).toString();
    }

    public SharedLibraryLoader(String str) {
        this.nativesJar = str;
    }

    public String crc(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        CRC32 crc32 = new CRC32();
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                crc32.update(bArr, 0, i);
            } catch (Exception unused) {
            } catch (Throwable th) {
                closeQuietly(inputStream);
                throw th;
            }
        }
        closeQuietly(inputStream);
        return Long.toString(crc32.getValue(), 16);
    }

    public String mapLibraryName(String str) {
        return os == Os.Android ? str : os.getLibPrefix() + str + architecture.toSuffix() + bitness.toSuffix() + "." + os.getLibExtension();
    }

    public void load(String str) {
        if (os == Os.IOS) {
            return;
        }
        synchronized (SharedLibraryLoader.class) {
            if (isLoaded(str)) {
                return;
            }
            String strMapLibraryName = mapLibraryName(str);
            try {
                if (os == Os.Android) {
                    System.loadLibrary(strMapLibraryName);
                } else {
                    loadFile(strMapLibraryName);
                }
                setLoaded(str);
            } catch (Throwable th) {
                throw new SharedLibraryLoadRuntimeException("Couldn't load shared library '" + strMapLibraryName + "' for target: " + (os == Os.Android ? "Android" : System.getProperty(SystemProperties.OS_NAME) + ", " + architecture.name() + ", " + bitness.name().substring(1) + "-bit"), th);
            }
        }
    }

    private InputStream readFile(String str) {
        if (this.nativesJar == null) {
            InputStream resourceAsStream = SharedLibraryLoader.class.getResourceAsStream("/" + str);
            if (resourceAsStream != null) {
                return resourceAsStream;
            }
            throw new SharedLibraryLoadRuntimeException("Unable to read file for extraction: " + str);
        }
        try {
            ZipFile zipFile = new ZipFile(this.nativesJar);
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                return zipFile.getInputStream(entry);
            }
            throw new SharedLibraryLoadRuntimeException("Couldn't find '" + str + "' in JAR: " + this.nativesJar);
        } catch (IOException e) {
            throw new SharedLibraryLoadRuntimeException("Error reading '" + str + "' in JAR: " + this.nativesJar, e);
        }
    }

    public File extractFile(String str, String str2) throws IOException {
        try {
            String strCrc = crc(readFile(str));
            if (str2 == null) {
                str2 = strCrc;
            }
            File extractedFile = getExtractedFile(str2, new File(str).getName());
            if (extractedFile == null && (extractedFile = getExtractedFile(randomUUID(), new File(str).getName())) == null) {
                throw new SharedLibraryLoadRuntimeException("Unable to find writable path to extract file. Is the user home directory writable?");
            }
            return extractFile(str, strCrc, extractedFile);
        } catch (RuntimeException e) {
            File file = new File(System.getProperty(SystemProperties.JAVA_LIBRARY_PATH), str);
            if (file.exists()) {
                return file;
            }
            throw e;
        }
    }

    public void extractFileTo(String str, File file) throws Throwable {
        extractFile(str, crc(readFile(str)), new File(file, new File(str).getName()));
    }

    private File getExtractedFile(String str, String str2) {
        File file = new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + str, str2);
        if (canWrite(file)) {
            return file;
        }
        try {
            File fileCreateTempFile = File.createTempFile(str, null);
            if (fileCreateTempFile.delete()) {
                File file2 = new File(fileCreateTempFile, str2);
                if (canWrite(file2)) {
                    return file2;
                }
            }
        } catch (IOException unused) {
        }
        File file3 = new File(System.getProperty("user.home") + "/.libgdx/" + str, str2);
        if (canWrite(file3)) {
            return file3;
        }
        File file4 = new File(".temp/" + str, str2);
        if (canWrite(file4)) {
            return file4;
        }
        if (System.getenv("APP_SANDBOX_CONTAINER_ID") != null) {
            return file;
        }
        return null;
    }

    private boolean canWrite(File file) {
        File parentFile = file.getParentFile();
        if (file.exists()) {
            if (!file.canWrite() || !canExecute(file)) {
                return false;
            }
            file = new File(parentFile, randomUUID().toString());
        } else {
            parentFile.mkdirs();
            if (!parentFile.isDirectory()) {
                return false;
            }
        }
        try {
            new FileOutputStream(file).close();
            if (canExecute(file)) {
                file.delete();
                return true;
            }
            file.delete();
            return false;
        } catch (Throwable unused) {
            file.delete();
            return false;
        }
    }

    private boolean canExecute(File file) {
        try {
            Method method = File.class.getMethod("canExecute", new Class[0]);
            if (((Boolean) method.invoke(file, new Object[0])).booleanValue()) {
                return true;
            }
            File.class.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE).invoke(file, true, false);
            return ((Boolean) method.invoke(file, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    private File extractFile(String str, String str2, File file) throws Throwable {
        String strCrc;
        FileOutputStream fileOutputStream;
        IOException e;
        InputStream file2;
        InputStream inputStream = null;
        if (file.exists()) {
            try {
                strCrc = crc(new FileInputStream(file));
            } catch (FileNotFoundException unused) {
                strCrc = null;
            }
        } else {
            strCrc = null;
        }
        if (strCrc == null || !strCrc.equals(str2)) {
            try {
                file2 = readFile(str);
                try {
                    file.getParentFile().mkdirs();
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        try {
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int i = file2.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, i);
                                th = th;
                                inputStream = file2;
                                closeQuietly(inputStream);
                                closeQuietly(fileOutputStream);
                                throw th;
                            }
                            closeQuietly(file2);
                            closeQuietly(fileOutputStream);
                        } catch (IOException e2) {
                            e = e2;
                            throw new SharedLibraryLoadRuntimeException("Error extracting file: " + str + "\nTo: " + file.getAbsolutePath(), e);
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = null;
                }
            } catch (IOException e4) {
                fileOutputStream = null;
                e = e4;
                file2 = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        }
        return file;
    }

    private void loadFile(String str) {
        String strCrc = crc(readFile(str));
        String name2 = new File(str).getName();
        Throwable thLoadFile = loadFile(str, strCrc, new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + strCrc, name2));
        if (thLoadFile == null) {
            return;
        }
        try {
            File fileCreateTempFile = File.createTempFile(strCrc, null);
            if (fileCreateTempFile.delete() && loadFile(str, strCrc, fileCreateTempFile) == null) {
                return;
            }
        } catch (Throwable unused) {
        }
        if (loadFile(str, strCrc, new File(System.getProperty("user.home") + "/.libgdx/" + strCrc, name2)) == null || loadFile(str, strCrc, new File(".temp/" + strCrc, name2)) == null) {
            return;
        }
        File file = new File(System.getProperty(SystemProperties.JAVA_LIBRARY_PATH), str);
        if (file.exists()) {
            System.load(file.getAbsolutePath());
            return;
        }
        throw new SharedLibraryLoadRuntimeException(thLoadFile);
    }

    private Throwable loadFile(String str, String str2, File file) {
        try {
            System.load(extractFile(str, str2, file).getAbsolutePath());
            return null;
        } catch (Throwable th) {
            return th;
        }
    }

    public static synchronized void setLoaded(String str) {
        loadedLibraries.add(str);
    }

    public static synchronized boolean isLoaded(String str) {
        return loadedLibraries.contains(str);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }
}
