package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
final class JdkZipResourceFile implements ResourceFile {
    private static final Object lock = new Object();
    private final File file;
    private final boolean isSecureLoader;
    private final boolean lockJars;
    private volatile SoftReference<ZipFile> zipFileSoftReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stream";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile";
                break;
            case 7:
                objArr[0] = "file";
                break;
            case 8:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 9:
                objArr[0] = "dir";
                break;
            case 10:
                objArr[0] = "filter";
                break;
            case 11:
                objArr[0] = "consumer";
                break;
            case 12:
                objArr[0] = "name";
                break;
            case 13:
                objArr[0] = "jarLoader";
                break;
            default:
                objArr[0] = "path";
                break;
        }
        if (i == 2) {
            objArr[1] = "loadBytes";
        } else if (i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "getZipFile";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile";
        } else {
            objArr[1] = "buildClassPathCacheData";
        }
        switch (i) {
            case 1:
                objArr[2] = "loadBytes";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 14:
                break;
            case 7:
                objArr[2] = "createZipFile";
                break;
            case 8:
                objArr[2] = "findClass";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "processResources";
                break;
            case 12:
            case 13:
                objArr[2] = "getResource";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    JdkZipResourceFile(Path path, boolean z, boolean z2) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        this.lockJars = z;
        this.file = path.toFile();
        this.isSecureLoader = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] loadBytes(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(1);
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 <= 0) {
                break;
            }
            i2 += i3;
        }
        return bArr;
    }

    ZipFile getZipFile() throws IOException {
        if (!this.lockJars) {
            ZipFile zipFileCreateZipFile = createZipFile(this.file);
            if (zipFileCreateZipFile == null) {
                $$$reportNull$$$0(3);
            }
            return zipFileCreateZipFile;
        }
        SoftReference<ZipFile> softReference = this.zipFileSoftReference;
        ZipFile zipFile = null;
        ZipFile zipFile2 = softReference == null ? null : softReference.get();
        if (zipFile2 != null) {
            if (zipFile2 == null) {
                $$$reportNull$$$0(4);
            }
            return zipFile2;
        }
        synchronized (lock) {
            SoftReference<ZipFile> softReference2 = this.zipFileSoftReference;
            if (softReference2 != null) {
                zipFile = softReference2.get();
            }
            if (zipFile != null) {
                if (zipFile == null) {
                    $$$reportNull$$$0(5);
                }
                return zipFile;
            }
            ZipFile zipFileCreateZipFile2 = createZipFile(this.file);
            this.zipFileSoftReference = new SoftReference<>(zipFileCreateZipFile2);
            if (zipFileCreateZipFile2 == null) {
                $$$reportNull$$$0(6);
            }
            return zipFileCreateZipFile2;
        }
    }

    private ZipFile createZipFile(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(7);
        }
        return this.isSecureLoader ? new JarFile(file) : new ZipFile(file);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.ResourceFile
    public Class<?> findClass(String str, String str2, JarLoader jarLoader, ClassPath.ClassDataConsumer classDataConsumer) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        ZipFile zipFile = getZipFile();
        try {
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                InputStream inputStream = zipFile.getInputStream(entry);
                try {
                    byte[] bArrLoadBytes = loadBytes(inputStream, (int) entry.getSize());
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    Class<?> clsConsumeClassData = classDataConsumer.consumeClassData(str2, bArrLoadBytes, jarLoader, jarLoader instanceof SecureJarLoader ? ((SecureJarLoader) jarLoader).getProtectionDomain((JarEntry) entry, new URL(jarLoader.url, entry.getName())) : null);
                    if (!this.lockJars) {
                        zipFile.close();
                    }
                    return clsConsumeClassData;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (!this.lockJars) {
                zipFile.close();
            }
            return null;
        } catch (Throwable th3) {
            if (!this.lockJars) {
                zipFile.close();
            }
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.ResourceFile
    public Resource getResource(String str, JarLoader jarLoader) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (jarLoader == null) {
            $$$reportNull$$$0(13);
        }
        ZipFile zipFile = getZipFile();
        try {
            ZipEntry entry = zipFile.getEntry(str);
            if (entry == null) {
                return null;
            }
            return this.isSecureLoader ? new SecureJarResource(jarLoader.url, (JarEntry) entry, (SecureJarLoader) jarLoader) : new ZipFileResource(jarLoader.url, entry, this);
        } finally {
            if (!this.lockJars) {
                zipFile.close();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.ResourceFile
    public Attributes loadManifestAttributes() throws IOException {
        ZipFile zipFile = getZipFile();
        try {
            ZipEntry entry = zipFile.getEntry("META-INF/MANIFEST.MF");
            if (entry == null) {
                if (!this.lockJars) {
                    zipFile.close();
                }
                return null;
            }
            try {
                InputStream inputStream = zipFile.getInputStream(entry);
                try {
                    Attributes mainAttributes = new Manifest(inputStream).getMainAttributes();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (!this.lockJars) {
                        zipFile.close();
                    }
                    return mainAttributes;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Exception unused) {
                if (!this.lockJars) {
                    zipFile.close();
                }
                return null;
            }
        } catch (Throwable th3) {
            if (!this.lockJars) {
                zipFile.close();
            }
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.ResourceFile
    public ClasspathCache.IndexRegistrar buildClassPathCacheData() throws IOException {
        ZipFile zipFile = getZipFile();
        try {
            ClasspathCache.LoaderDataBuilder loaderDataBuilder = new ClasspathCache.LoaderDataBuilder(true);
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                String name2 = enumerationEntries.nextElement().getName();
                if (name2.endsWith(".class")) {
                    loaderDataBuilder.addClassPackageFromName(name2);
                    loaderDataBuilder.andClassName(name2);
                } else {
                    loaderDataBuilder.addResourcePackageFromName(name2);
                    loaderDataBuilder.addResourceName(name2, name2.endsWith("/") ? name2.length() - 1 : name2.length());
                }
            }
            return loaderDataBuilder;
        } finally {
            if (!this.lockJars) {
                zipFile.close();
            }
        }
    }

    private static class ZipFileResource implements Resource {
        protected final URL baseUrl;
        protected final ZipEntry entry;
        protected final JdkZipResourceFile file;
        private URL url;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "entry";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile$ZipFileResource";
            } else {
                objArr[0] = "baseUrl";
            }
            if (i == 3) {
                objArr[1] = "getURL";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile$ZipFileResource";
            } else {
                objArr[1] = "getBytes";
            }
            if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private ZipFileResource(URL url, ZipEntry zipEntry, JdkZipResourceFile jdkZipResourceFile) {
            if (url == null) {
                $$$reportNull$$$0(0);
            }
            if (zipEntry == null) {
                $$$reportNull$$$0(1);
            }
            if (jdkZipResourceFile == null) {
                $$$reportNull$$$0(2);
            }
            this.baseUrl = url;
            this.entry = zipEntry;
            this.file = jdkZipResourceFile;
        }

        public String toString() {
            return this.url.toString();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.Resource
        public URL getURL() {
            URL url = this.url;
            if (url == null) {
                try {
                    url = new URL(this.baseUrl, this.entry.getName());
                    this.url = url;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (url == null) {
                $$$reportNull$$$0(3);
            }
            return url;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.Resource
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(getBytes());
        }

        public byte[] getBytes() throws IOException {
            ZipFile zipFile = this.file.getZipFile();
            try {
                InputStream inputStream = zipFile.getInputStream(this.entry);
                try {
                    byte[] bArrLoadBytes = JdkZipResourceFile.loadBytes(inputStream, (int) this.entry.getSize());
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (!this.file.lockJars) {
                        zipFile.close();
                    }
                    if (bArrLoadBytes == null) {
                        $$$reportNull$$$0(4);
                    }
                    return bArrLoadBytes;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (!this.file.lockJars) {
                    zipFile.close();
                }
                throw th3;
            }
        }
    }

    private static final class SecureJarResource extends ZipFileResource {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "entry";
            } else if (i == 2) {
                objArr[0] = "jarLoader";
            } else if (i != 3) {
                objArr[0] = "baseUrl";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile$SecureJarResource";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/JdkZipResourceFile$SecureJarResource";
            } else {
                objArr[1] = "getBytes";
            }
            if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SecureJarResource(URL url, JarEntry jarEntry, SecureJarLoader secureJarLoader) {
            super(url, jarEntry, (JdkZipResourceFile) secureJarLoader.zipFile);
            if (url == null) {
                $$$reportNull$$$0(0);
            }
            if (jarEntry == null) {
                $$$reportNull$$$0(1);
            }
            if (secureJarLoader == null) {
                $$$reportNull$$$0(2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.JdkZipResourceFile.ZipFileResource
        public byte[] getBytes() throws IOException {
            ZipFile zipFile = this.file.getZipFile();
            try {
                InputStream inputStream = zipFile.getInputStream(this.entry);
                try {
                    byte[] bArrLoadBytes = JdkZipResourceFile.loadBytes(inputStream, (int) this.entry.getSize());
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (!this.file.lockJars) {
                        zipFile.close();
                    }
                    if (bArrLoadBytes == null) {
                        $$$reportNull$$$0(3);
                    }
                    return bArrLoadBytes;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (!this.file.lockJars) {
                    zipFile.close();
                }
                throw th3;
            }
        }
    }
}
