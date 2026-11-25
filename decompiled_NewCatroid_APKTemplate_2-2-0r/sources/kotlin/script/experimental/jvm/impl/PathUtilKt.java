package kotlin.script.experimental.jvm.impl;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: pathUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0001H\u0002\u001a\u0012\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f\u001a\u001e\u0010\r\u001a\u0004\u0018\u00010\u00012\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u000f\u001a\u00020\u0001H\u0000\u001a\u001e\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0001H\u0002\u001a\u0014\u0010\u0013\u001a\u0004\u0018\u00010\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f\u001a\u0018\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u000e\u0010\u0018\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000\u001a\u000e\u0010\u0019\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"FILE_PROTOCOL", "", "JAR_PROTOCOL", "JAR_SEPARATOR", "SCHEME_SEPARATOR", "extractRoot", "resourceURL", "Ljava/net/URL;", "resourcePath", "getResourcePathForClass", "Ljava/io/File;", "aClass", "Ljava/lang/Class;", "getResourceRoot", "context", "path", "splitJarUrl", "Lkotlin/Pair;", "url", "tryGetResourcePathForClass", "tryGetResourcePathForClassByName", "name", "classLoader", "Ljava/lang/ClassLoader;", "toContainingJarOrNull", "toFileOrNull", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PathUtilKt {
    private static final String FILE_PROTOCOL = "file";
    private static final String JAR_PROTOCOL = "jar";
    private static final String JAR_SEPARATOR = "!/";
    private static final String SCHEME_SEPARATOR = "://";

    public static final String getResourceRoot(Class<?> context, String path) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        URL resource = context.getResource(path);
        if (resource == null) {
            String strSubstring = path.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            resource = ClassLoader.getSystemResource(strSubstring);
        }
        if (resource != null) {
            return extractRoot(resource, path);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x008e  */
    private static final String extractRoot(URL url, String str) throws IOException {
        String canonicalPath;
        String str2 = str;
        if (!StringsKt.startsWith$default((CharSequence) str2, '/', false, 2, (Object) null) || StringsKt.startsWith$default((CharSequence) str2, '\\', false, 2, (Object) null)) {
            return null;
        }
        String protocol = url.getProtocol();
        if (Intrinsics.areEqual(protocol, "file")) {
            File fileOrNull = toFileOrNull(url);
            Intrinsics.checkNotNull(fileOrNull);
            String path = fileOrNull.getPath();
            Intrinsics.checkNotNull(path);
            if (StringsKt.endsWith(StringsKt.replace$default(path, '\\', '/', false, 4, (Object) null), StringsKt.replace$default(str, '\\', '/', false, 4, (Object) null), true)) {
                canonicalPath = path.substring(0, path.length() - str.length());
                Intrinsics.checkNotNullExpressionValue(canonicalPath, "substring(...)");
            } else {
                canonicalPath = null;
            }
        } else if (Intrinsics.areEqual(protocol, "jar")) {
            String file = url.getFile();
            Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
            Pair<String, String> pairSplitJarUrl = splitJarUrl(file);
            if ((pairSplitJarUrl != null ? pairSplitJarUrl.getFirst() : null) != null) {
                canonicalPath = new File(pairSplitJarUrl.getFirst()).getCanonicalPath();
            } else {
                canonicalPath = null;
            }
        } else {
            canonicalPath = null;
        }
        if (canonicalPath != null) {
            return StringsKt.trimEnd(canonicalPath, File.separatorChar);
        }
        return null;
    }

    private static final Pair<String, String> splitJarUrl(String str) {
        Integer numValueOf = Integer.valueOf(StringsKt.indexOf$default((CharSequence) str, JAR_SEPARATOR, 0, false, 6, (Object) null));
        if (numValueOf.intValue() < 0) {
            numValueOf = null;
        }
        if (numValueOf == null) {
            return null;
        }
        int iIntValue = numValueOf.intValue();
        String strSubstring = str.substring(iIntValue + 2);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String strSubstring2 = str.substring(0, iIntValue);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        if (StringsKt.startsWith$default(strSubstring2, "jar:", false, 2, (Object) null)) {
            strSubstring2 = strSubstring2.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        }
        if (StringsKt.startsWith$default(strSubstring2, "file", false, 2, (Object) null)) {
            try {
                File fileOrNull = toFileOrNull(new URL(strSubstring2));
                Intrinsics.checkNotNull(fileOrNull);
                String path = fileOrNull.getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                strSubstring2 = StringsKt.replace$default(path, '\\', '/', false, 4, (Object) null);
            } catch (Exception unused) {
                strSubstring2 = strSubstring2.substring(4);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                if (StringsKt.startsWith$default(strSubstring2, SCHEME_SEPARATOR, false, 2, (Object) null)) {
                    strSubstring2 = strSubstring2.substring(3);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                } else if (StringsKt.startsWith$default((CharSequence) strSubstring2, ':', false, 2, (Object) null)) {
                    strSubstring2 = strSubstring2.substring(1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                }
            }
        }
        return new Pair<>(strSubstring2, strSubstring);
    }

    public static final File tryGetResourcePathForClass(Class<?> aClass) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        StringBuilder sb = new StringBuilder("/");
        String name2 = aClass.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String resourceRoot = getResourceRoot(aClass, sb.append(StringsKt.replace$default(name2, '.', '/', false, 4, (Object) null)).append(".class").toString());
        if (resourceRoot != null) {
            return new File(resourceRoot).getAbsoluteFile();
        }
        return null;
    }

    public static final File getResourcePathForClass(Class<?> aClass) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        File fileTryGetResourcePathForClass = tryGetResourcePathForClass(aClass);
        if (fileTryGetResourcePathForClass != null) {
            return fileTryGetResourcePathForClass;
        }
        throw new IllegalStateException("Resource for class: " + aClass.getName() + " not found");
    }

    public static final File tryGetResourcePathForClassByName(String name2, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        try {
            Class<?> clsLoadClass = classLoader.loadClass(name2);
            if (clsLoadClass != null) {
                return tryGetResourcePathForClass(clsLoadClass);
            }
            return null;
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            return null;
        }
    }

    public static final File toFileOrNull(URL url) {
        File file;
        Intrinsics.checkNotNullParameter(url, "<this>");
        try {
            file = new File(url.toURI());
        } catch (IllegalArgumentException | URISyntaxException unused) {
            file = null;
        }
        if (file == null) {
            return Intrinsics.areEqual(url.getProtocol(), "file") ? new File(url.getFile()) : null;
        }
        return file;
    }

    public static final File toContainingJarOrNull(URL url) throws IOException {
        URL jarFileURL;
        Intrinsics.checkNotNullParameter(url, "<this>");
        if (!Intrinsics.areEqual(url.getProtocol(), "jar")) {
            return null;
        }
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        JarURLConnection jarURLConnection = uRLConnectionOpenConnection instanceof JarURLConnection ? (JarURLConnection) uRLConnectionOpenConnection : null;
        if (jarURLConnection == null || (jarFileURL = jarURLConnection.getJarFileURL()) == null) {
            return null;
        }
        return toFileOrNull(jarFileURL);
    }
}
