package org.jetbrains.kotlin.net.jpountz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public enum Native {
    ;

    private static boolean loaded = false;

    private enum OS {
        WINDOWS("win32", "so"),
        LINUX("linux", "so"),
        MAC("darwin", "dylib"),
        SOLARIS("solaris", "so");

        public final String libExtension;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f926name;

        OS(String str, String str2) {
            this.f926name = str;
            this.libExtension = str2;
        }
    }

    private static String arch() {
        return System.getProperty(SystemProperties.OS_ARCH);
    }

    private static OS os() {
        String property = System.getProperty(SystemProperties.OS_NAME);
        if (property.contains("Linux")) {
            return OS.LINUX;
        }
        if (property.contains("Mac")) {
            return OS.MAC;
        }
        if (property.contains("Windows")) {
            return OS.WINDOWS;
        }
        if (property.contains("Solaris") || property.contains("SunOS")) {
            return OS.SOLARIS;
        }
        throw new UnsupportedOperationException("Unsupported operating system: " + property);
    }

    private static String resourceName() {
        OS os = os();
        return "/" + Native.class.getPackage().getName().replace('.', '/') + "/" + os.f926name + "/" + arch() + "/liblz4-java." + os.libExtension;
    }

    public static synchronized boolean isLoaded() {
        return loaded;
    }

    private static void cleanupOldTempLibs() {
        File[] fileArrListFiles = new File(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath()).listFiles(new FilenameFilter() { // from class: org.jetbrains.kotlin.net.jpountz.util.Native.1
            private final String searchPattern = "liblz4-java-";

            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                return str.startsWith("liblz4-java-") && !str.endsWith(".lck");
            }
        });
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (!new File(file.getAbsolutePath() + ".lck").exists()) {
                    try {
                        file.delete();
                    } catch (SecurityException e) {
                        System.err.println("Failed to delete old temp lib" + e.getMessage());
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.File, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.File, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    public static synchronized void load() {
        Throwable th;
        IOException e;
        ?? CreateTempFile = ".";
        synchronized (Native.class) {
            if (loaded) {
                return;
            }
            cleanupOldTempLibs();
            try {
                System.loadLibrary("lz4-java");
                loaded = true;
            } catch (UnsatisfiedLinkError unused) {
                ?? ResourceName = resourceName();
                InputStream resourceAsStream = Native.class.getResourceAsStream(ResourceName);
                if (resourceAsStream == null) {
                    throw new UnsupportedOperationException("Unsupported OS/arch, cannot find " + ResourceName + ". Please try building from source.");
                }
                try {
                    try {
                        CreateTempFile = File.createTempFile("liblz4-java-", "." + os().libExtension + ".lck");
                        try {
                            File file = new File(CreateTempFile.getAbsolutePath().replaceFirst(".lck$", ""));
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                try {
                                    byte[] bArr = new byte[4096];
                                    while (true) {
                                        int i = resourceAsStream.read(bArr);
                                        if (i != -1) {
                                            fileOutputStream.write(bArr, 0, i);
                                        } else {
                                            fileOutputStream.close();
                                            System.load(file.getAbsolutePath());
                                            loaded = true;
                                            file.deleteOnExit();
                                            CreateTempFile.deleteOnExit();
                                            return;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    try {
                                        throw th2;
                                    } catch (Throwable th3) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable th4) {
                                            th2.addSuppressed(th4);
                                        }
                                        throw th3;
                                    }
                                }
                            } catch (IOException e2) {
                                e = e2;
                                throw new ExceptionInInitializerError("Cannot unpack liblz4-java: " + e);
                            }
                        } catch (IOException e3) {
                            e = e3;
                        } catch (Throwable th5) {
                            ResourceName = 0;
                            th = th5;
                            if (!loaded) {
                                if (ResourceName != 0 && ResourceName.exists() && !ResourceName.delete()) {
                                    throw new ExceptionInInitializerError("Cannot unpack liblz4-java / cannot delete a temporary native library " + ResourceName);
                                }
                                if (CreateTempFile != 0 && CreateTempFile.exists() && !CreateTempFile.delete()) {
                                    throw new ExceptionInInitializerError("Cannot unpack liblz4-java / cannot delete a temporary lock file " + CreateTempFile);
                                }
                            } else {
                                ResourceName.deleteOnExit();
                                CreateTempFile.deleteOnExit();
                            }
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (IOException e4) {
                    e = e4;
                } catch (Throwable th7) {
                    ResourceName = 0;
                    th = th7;
                    CreateTempFile = 0;
                }
            }
        }
    }
}
