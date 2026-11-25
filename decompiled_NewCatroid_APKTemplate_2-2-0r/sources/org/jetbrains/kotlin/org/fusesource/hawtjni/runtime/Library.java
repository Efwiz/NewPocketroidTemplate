package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import com.beust.jcommander.Parameters;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class Library {
    static final String SLASH = System.getProperty(SystemProperties.FILE_SEPARATOR);
    private final ClassLoader classLoader;
    private boolean loaded;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f927name;
    private final String version;

    public Library(String str) {
        this(str, null, null);
    }

    public Library(String str, Class<?> cls) {
        this(str, version(cls), cls.getClassLoader());
    }

    public Library(String str, String str2) {
        this(str, str2, null);
    }

    public Library(String str, String str2, ClassLoader classLoader) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.f927name = str;
        this.version = str2;
        this.classLoader = classLoader;
    }

    private static String version(Class<?> cls) {
        try {
            return cls.getPackage().getImplementationVersion();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getOperatingSystem() {
        String strTrim = System.getProperty(SystemProperties.OS_NAME).toLowerCase().trim();
        if (strTrim.startsWith("linux")) {
            return "linux";
        }
        if (strTrim.startsWith("mac os x")) {
            return "osx";
        }
        if (strTrim.startsWith("win")) {
            return "windows";
        }
        return strTrim.replaceAll("\\W+", "_");
    }

    public static String getPlatform() {
        return getOperatingSystem() + getBitModel();
    }

    public static int getBitModel() {
        String property = System.getProperty("sun.arch.data.model");
        if (property == null) {
            property = System.getProperty("com.ibm.vm.bitmode");
        }
        if (property != null) {
            return Integer.parseInt(property);
        }
        return -1;
    }

    public synchronized void load() {
        if (this.loaded) {
            return;
        }
        doLoad();
        this.loaded = true;
    }

    private void doLoad() {
        String property = System.getProperty("library." + this.f927name + ".version");
        if (property == null) {
            property = this.version;
        }
        String str = property;
        ArrayList<Throwable> arrayList = new ArrayList<>();
        String[] specificSearchDirs = getSpecificSearchDirs();
        String map = map(this.f927name);
        String map2 = str == null ? null : map(this.f927name + Parameters.DEFAULT_OPTION_PREFIXES + str);
        String property2 = System.getProperty("library." + this.f927name + ".path");
        if (property2 != null) {
            for (String str2 : specificSearchDirs) {
                if ((str != null && load(arrayList, file(property2, str2, map2))) || load(arrayList, file(property2, str2, map))) {
                    return;
                }
            }
        }
        if (str == null || !load(arrayList, this.f927name + getBitModel() + Parameters.DEFAULT_OPTION_PREFIXES + str)) {
            if ((str == null || !load(arrayList, this.f927name + Parameters.DEFAULT_OPTION_PREFIXES + str)) && !load(arrayList, this.f927name)) {
                if (this.classLoader != null) {
                    String str3 = str != null ? map2 : map;
                    int i = 0;
                    for (int length = specificSearchDirs.length; i < length; length = length) {
                        String str4 = specificSearchDirs[i];
                        if (str != null && extractAndLoad(arrayList, property2, str4, map2, str3)) {
                            return;
                        }
                        if (extractAndLoad(arrayList, property2, str4, map, str3)) {
                            return;
                        }
                        i++;
                    }
                }
                UnsatisfiedLinkError unsatisfiedLinkError = new UnsatisfiedLinkError("Could not load library. Reasons: " + arrayList.toString());
                try {
                    Method method = Throwable.class.getMethod("addSuppressed", Throwable.class);
                    Iterator<Throwable> it = arrayList.iterator();
                    while (it.hasNext()) {
                        method.invoke(unsatisfiedLinkError, it.next());
                    }
                    throw unsatisfiedLinkError;
                } catch (Throwable unused) {
                    throw unsatisfiedLinkError;
                }
            }
        }
    }

    @Deprecated
    public final String getArchSpecifcResourcePath() {
        return getArchSpecificResourcePath();
    }

    public final String getArchSpecificResourcePath() {
        return "META-INF/native/" + getPlatform() + "/" + System.getProperty(SystemProperties.OS_ARCH) + "/" + map(this.f927name);
    }

    @Deprecated
    public final String getOperatingSystemSpecifcResourcePath() {
        return getOperatingSystemSpecificResourcePath();
    }

    public final String getOperatingSystemSpecificResourcePath() {
        return getPlatformSpecificResourcePath(getOperatingSystem());
    }

    @Deprecated
    public final String getPlatformSpecifcResourcePath() {
        return getPlatformSpecificResourcePath();
    }

    public final String getPlatformSpecificResourcePath() {
        return getPlatformSpecificResourcePath(getPlatform());
    }

    @Deprecated
    public final String getPlatformSpecifcResourcePath(String str) {
        return getPlatformSpecificResourcePath(str);
    }

    public final String getPlatformSpecificResourcePath(String str) {
        return "META-INF/native/" + str + "/" + map(this.f927name);
    }

    @Deprecated
    public final String getResorucePath() {
        return getResourcePath();
    }

    public final String getResourcePath() {
        return "META-INF/native/" + map(this.f927name);
    }

    public final String getLibraryFileName() {
        return map(this.f927name);
    }

    public final String[] getSpecificSearchDirs() {
        return new String[]{getPlatform() + "/" + System.getProperty(SystemProperties.OS_ARCH), getPlatform(), getOperatingSystem()};
    }

    private boolean extractAndLoad(ArrayList<Throwable> arrayList, String str, String str2, String str3, String str4) {
        File fileExtract;
        URL resource = this.classLoader.getResource("META-INF/native/" + (str2 == null ? "" : str2 + '/') + str3);
        if (resource != null) {
            int iLastIndexOf = str4.lastIndexOf(46);
            String str5 = str4.substring(0, iLastIndexOf) + Parameters.DEFAULT_OPTION_PREFIXES;
            String strSubstring = str4.substring(iLastIndexOf);
            File[] fileArr = new File[3];
            fileArr[0] = str != null ? file(str) : null;
            fileArr[1] = file(System.getProperty("java.io.tmpdir"));
            fileArr[2] = file(System.getProperty("user.home"), ".hawtjni", this.f927name);
            for (File file : Arrays.asList(fileArr)) {
                if (file != null && (fileExtract = extract(arrayList, resource, str5, strSubstring, file)) != null && load(arrayList, fileExtract)) {
                    return true;
                }
            }
        }
        return false;
    }

    private File file(String... strArr) {
        File file = null;
        for (String str : strArr) {
            if (file == null) {
                file = new File(str);
            } else if (str != null) {
                file = new File(file, str);
            }
        }
        return file;
    }

    private String map(String str) {
        String strMapLibraryName = System.mapLibraryName(str);
        return strMapLibraryName.endsWith(".dylib") ? strMapLibraryName.substring(0, strMapLibraryName.length() - ".dylib".length()) + ".jnilib" : strMapLibraryName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private File extract(ArrayList<Throwable> arrayList, URL url, String str, String str2, File file) {
        InputStream inputStreamOpenStream;
        IOException iOException;
        Closeable closeable;
        FileOutputStream fileOutputStream;
        File absoluteFile = file.getAbsoluteFile();
        if (!absoluteFile.exists() && !absoluteFile.mkdirs()) {
            arrayList.add(new IOException("Unable to create directory: " + absoluteFile));
            return null;
        }
        try {
            try {
                File fileCreateTempFile = File.createTempFile(str, str2, absoluteFile);
                try {
                    inputStreamOpenStream = url.openStream();
                    if (inputStreamOpenStream != null) {
                        try {
                            byte[] bArr = new byte[4096];
                            fileOutputStream = new FileOutputStream(fileCreateTempFile);
                            while (true) {
                                try {
                                    int i = inputStreamOpenStream.read(bArr);
                                    if (i == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, i);
                                } catch (Throwable th) {
                                    th = th;
                                    closeable = fileOutputStream;
                                    close(closeable);
                                    close(inputStreamOpenStream);
                                    throw th;
                                }
                            }
                            chmod755(fileCreateTempFile);
                            fileOutputStream = fileOutputStream;
                        } catch (Throwable th2) {
                            th = th2;
                            closeable = null;
                            close(closeable);
                            close(inputStreamOpenStream);
                            throw th;
                        }
                    } else {
                        fileOutputStream = null;
                    }
                    fileCreateTempFile.deleteOnExit();
                    close(fileOutputStream);
                    close(inputStreamOpenStream);
                    return fileCreateTempFile;
                } catch (Throwable th3) {
                    th = th3;
                    inputStreamOpenStream = null;
                    closeable = inputStreamOpenStream;
                    close(closeable);
                    close(inputStreamOpenStream);
                    throw th;
                }
            } catch (Throwable th4) {
                if (str != 0) {
                    str.delete();
                    iOException = new IOException("Unable to extract library from " + url + " to " + ((Object) str));
                } else {
                    iOException = new IOException("Unable to create temporary file in " + absoluteFile);
                }
                iOException.initCause(th4);
                arrayList.add(iOException);
                return null;
            }
        } catch (Throwable th5) {
            th = th5;
            inputStreamOpenStream = null;
        }
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    private void chmod755(File file) {
        if (getPlatform().startsWith("windows")) {
            return;
        }
        try {
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                Object objInvoke = classLoader.loadClass("java.nio.file.attribute.PosixFilePermissions").getMethod("fromString", String.class).invoke(null, "rwxr-xr-x");
                Object objInvoke2 = file.getClass().getMethod("toPath", new Class[0]).invoke(file, new Object[0]);
                classLoader.loadClass("java.nio.file.Files").getMethod("setPosixFilePermissions", classLoader.loadClass("java.nio.file.Path"), Set.class).invoke(null, objInvoke2, objInvoke);
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            Runtime.getRuntime().exec(new String[]{"chmod", "755", file.getCanonicalPath()}).waitFor();
        }
    }

    private boolean load(ArrayList<Throwable> arrayList, File file) {
        try {
            System.load(file.getPath());
            return true;
        } catch (UnsatisfiedLinkError e) {
            LinkageError linkageError = new LinkageError("Unable to load library from " + file);
            linkageError.initCause(e);
            arrayList.add(linkageError);
            return false;
        }
    }

    private boolean load(ArrayList<Throwable> arrayList, String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (UnsatisfiedLinkError e) {
            LinkageError linkageError = new LinkageError("Unable to load library " + str);
            linkageError.initCause(e);
            arrayList.add(linkageError);
            return false;
        }
    }
}
