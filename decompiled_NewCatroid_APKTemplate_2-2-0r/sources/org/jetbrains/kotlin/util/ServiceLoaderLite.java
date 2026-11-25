package org.jetbrains.kotlin.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ServiceLoaderLite.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u0002J&\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fJ)\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0086\bJ\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010\u0011\u001a\u00020\u00042\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0002J*\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r2\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\r0\b2\u0006\u0010\u0013\u001a\u00020\u0014J8\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\u0015\"\u0004\b\u0000\u0010\r2\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\r0\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\u0006\u0010\u0013\u001a\u00020\u0016J#\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0014H\u0086\bJ1\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\n\b\u0000\u0010\r\u0018\u0001*\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\u0006\u0010\u0013\u001a\u00020\u0016H\u0086\bJ\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\t\u001a\u00020\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/util/ServiceLoaderLite;", "", "()V", "SERVICE_DIRECTORY_LOCATION", "", "findImplementations", "", "service", "Ljava/lang/Class;", "file", "Ljava/io/File;", "files", "", "Service", "findImplementationsInDirectory", "classId", "findImplementationsInJar", "getClassIdentifier", "loadImplementations", "classLoader", "Ljava/net/URLClassLoader;", "", "Ljava/lang/ClassLoader;", "parseLine", Constant.LINE, "parseLines", "lines", "Lkotlin/sequences/Sequence;", "ServiceLoadingException", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ServiceLoaderLite {
    public static final ServiceLoaderLite INSTANCE = new ServiceLoaderLite();

    private ServiceLoaderLite() {
    }

    /* JADX INFO: compiled from: ServiceLoaderLite.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/util/ServiceLoaderLite$ServiceLoadingException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "file", "Ljava/io/File;", "cause", "", "(Ljava/io/File;Ljava/lang/Throwable;)V", "getFile", "()Ljava/io/File;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ServiceLoadingException extends RuntimeException {
        private final File file;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ServiceLoadingException(File file, Throwable cause) {
            super("Error loading services from " + file, cause);
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.file = file;
        }

        public final File getFile() {
            return this.file;
        }
    }

    public final <Service> List<Service> loadImplementations(Class<? extends Service> service, URLClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        URL[] uRLs = classLoader.getURLs();
        Intrinsics.checkNotNullExpressionValue(uRLs, "getURLs(...)");
        URL[] urlArr = uRLs;
        ArrayList arrayList = new ArrayList(urlArr.length);
        for (URL url : urlArr) {
            try {
                arrayList.add(Paths.get(url.toURI()).toFile());
            } catch (UnsupportedOperationException unused) {
                throw new IllegalArgumentException("Only local URLs are supported, got " + url.getProtocol());
            } catch (FileSystemNotFoundException unused2) {
                throw new IllegalArgumentException("Only local URLs are supported, got " + url.getProtocol());
            }
        }
        return loadImplementations(service, arrayList, classLoader);
    }

    public final <Service> List<Service> loadImplementations(Class<? extends Service> service, List<? extends File> files, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = findImplementations(service, files).iterator();
        while (it.hasNext()) {
            arrayList.add(service.cast(Class.forName(it.next(), false, classLoader).newInstance()));
        }
        return arrayList;
    }

    public final /* synthetic */ <Service> Set<String> findImplementations(List<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.reifiedOperationMarker(4, "Service");
        return findImplementations(Object.class, files);
    }

    public final /* synthetic */ <Service> List<Service> loadImplementations(URLClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.reifiedOperationMarker(4, "Service");
        return loadImplementations(Object.class, classLoader);
    }

    public final /* synthetic */ <Service> List<Service> loadImplementations(List<? extends File> files, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.reifiedOperationMarker(4, "Service");
        return loadImplementations(Object.class, files, classLoader);
    }

    public final Set<String> findImplementations(Class<?> service, List<? extends File> files) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(files, "files");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, INSTANCE.findImplementations(service, (File) it.next()));
        }
        return linkedHashSet;
    }

    private final Set<String> findImplementations(Class<?> service, File file) {
        String classIdentifier = getClassIdentifier(service);
        if (file.isDirectory()) {
            return findImplementationsInDirectory(classIdentifier, file);
        }
        if (file.isFile()) {
            String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            if (Intrinsics.areEqual(lowerCase, "jar")) {
                return findImplementationsInJar(classIdentifier, file);
            }
        }
        return SetsKt.emptySet();
    }

    private final Set<String> findImplementationsInDirectory(String classId, File file) {
        File file2 = new File(file, "META-INF/services/" + classId);
        if (!file2.isFile()) {
            file2 = null;
        }
        if (file2 == null) {
            return SetsKt.emptySet();
        }
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(file2), Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                Set<String> lines = INSTANCE.parseLines(file, TextStreamsKt.lineSequence(bufferedReader));
                CloseableKt.closeFinally(bufferedReader, null);
                return lines;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (IOError e) {
            throw new ServiceLoadingException(file, e);
        }
    }

    private final Set<String> findImplementationsInJar(String classId, File file) {
        ZipFile zipFile = new ZipFile(file);
        try {
            ZipFile zipFile2 = zipFile;
            ZipEntry entry = zipFile2.getEntry("META-INF/services/" + classId);
            if (entry == null) {
                Set<String> setEmptySet = SetsKt.emptySet();
                CloseableKt.closeFinally(zipFile, null);
                return setEmptySet;
            }
            Intrinsics.checkNotNull(entry);
            InputStream inputStream = zipFile2.getInputStream(entry);
            try {
                InputStream inputStream2 = inputStream;
                Intrinsics.checkNotNull(inputStream2);
                Reader inputStreamReader = new InputStreamReader(inputStream2, Charsets.UTF_8);
                BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
                BufferedReader bufferedReader2 = bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, 8192);
                try {
                    Set<String> lines = INSTANCE.parseLines(file, TextStreamsKt.lineSequence(bufferedReader2));
                    CloseableKt.closeFinally(bufferedReader2, null);
                    CloseableKt.closeFinally(inputStream, null);
                    CloseableKt.closeFinally(zipFile, null);
                    return lines;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(bufferedReader2, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(inputStream, th3);
                    throw th4;
                }
            }
        } catch (Throwable th5) {
            try {
                throw th5;
            } catch (Throwable th6) {
                CloseableKt.closeFinally(zipFile, th5);
                throw th6;
            }
        }
    }

    private final Set<String> parseLines(File file, Sequence<String> lines) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<String> it = lines.iterator();
        while (it.hasNext()) {
            String line = INSTANCE.parseLine(file, it.next());
            if (line != null) {
                linkedHashSet.add(line);
            }
        }
        return linkedHashSet;
    }

    private final String parseLine(File file, String line) {
        boolean zIsJavaIdentifierStart;
        String string = StringsKt.trim((CharSequence) StringsKt.substringBefore$default(line, '#', (String) null, 2, (Object) null)).toString();
        if (string.length() <= 0) {
            string = null;
        }
        if (string == null) {
            return null;
        }
        String str = string;
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            int i3 = i2 + 1;
            if (i2 == 0) {
                zIsJavaIdentifierStart = Character.isJavaIdentifierStart(cCharAt);
            } else {
                zIsJavaIdentifierStart = Character.isJavaIdentifierPart(cCharAt) || cCharAt == '.';
            }
            if (!zIsJavaIdentifierStart) {
                throw new ServiceLoadingException(file, new RuntimeException("Invalid Java identifier: " + line));
            }
            i++;
            i2 = i3;
        }
        return string;
    }

    private final String getClassIdentifier(Class<?> service) {
        String name2 = service.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }
}
