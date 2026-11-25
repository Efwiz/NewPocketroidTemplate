package kotlin.script.experimental.jvm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.jvm.impl.PathUtilKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmClassLoaderUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\u001a2\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00012\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0000\u001a2\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0000\u001a2\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001b2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0000\u001a2\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001a\u001a\u00020\u001b2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0000\u001a\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0001H\u0000\u001a\f\u0010#\u001a\u00020\u0001*\u00020$H\u0002\u001aE\u0010%\u001a\u00020\u0011*\u00020&2\u0006\u0010\u0014\u001a\u00020\u00012\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010(\"\u00020\u00012\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00110\u0016¢\u0006\u0002\u0010)\u001a\f\u0010*\u001a\u00020\u0001*\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0014\u0010\b\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u0014\u0010\n\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0014\u0010\f\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007\"\u0014\u0010\u000e\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007¨\u0006+"}, d2 = {"pathElementPattern", "", "getPathElementPattern", "()Ljava/lang/String;", "pathSeparatorChars", "", "getPathSeparatorChars", "()[C", "pathSeparatorPattern", "getPathSeparatorPattern", "patternCharsToEscape", "getPatternCharsToEscape", "specialPatternChars", "getSpecialPatternChars", "wildcardChars", "getWildcardChars", "forAllMatchingFilesInDirectory", "", "baseDir", "Ljava/io/File;", "namePattern", "body", "Lkotlin/Function2;", "Ljava/io/InputStream;", "forAllMatchingFilesInJar", "jarFile", "nameRegex", "Lkotlin/text/Regex;", "forAllMatchingFilesInJarFile", "Ljava/util/jar/JarFile;", "forAllMatchingFilesInJarStream", "jarInputStream", "Ljava/util/jar/JarInputStream;", "namePatternToRegex", "pattern", "escape", "", "forAllMatchingFiles", "Ljava/lang/ClassLoader;", "keyResourcePaths", "", "(Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/String;Lkotlin/jvm/functions/Function2;)V", "toUniversalSeparator", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmClassLoaderUtilKt {
    private static final String pathElementPattern;
    private static final char[] pathSeparatorChars;
    private static final String pathSeparatorPattern;
    private static final char[] patternCharsToEscape;
    private static final char[] specialPatternChars;
    private static final char[] wildcardChars;

    public static final void forAllMatchingFiles(ClassLoader classLoader, String namePattern, String[] keyResourcePaths, Function2<? super String, ? super InputStream, Unit> body) {
        Intrinsics.checkNotNullParameter(classLoader, "<this>");
        Intrinsics.checkNotNullParameter(namePattern, "namePattern");
        Intrinsics.checkNotNullParameter(keyResourcePaths, "keyResourcePaths");
        Intrinsics.checkNotNullParameter(body, "body");
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Regex regexNamePatternToRegex = namePatternToRegex(namePattern);
        if (keyResourcePaths.length == 0) {
            keyResourcePaths = new String[]{"", "META-INF/MANIFEST.MF"};
        }
        forAllMatchingFiles$iterateResources(classLoader, hashSet2, hashSet, namePattern, body, regexNamePatternToRegex, keyResourcePaths);
    }

    private static final void forAllMatchingFiles$iterateResources(ClassLoader classLoader, HashSet<URL> hashSet, HashSet<File> hashSet2, String str, Function2<? super String, ? super InputStream, Unit> function2, Regex regex, String[] strArr) throws IOException {
        for (String str2 : strArr) {
            ClassLoaderResourceRootFIlePathCalculator classLoaderResourceRootFIlePathCalculator = new ClassLoaderResourceRootFIlePathCalculator(str2);
            Enumeration<URL> resources = classLoader.getResources(str2);
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
            Iterator it = CollectionsKt.iterator(resources);
            while (it.hasNext()) {
                URL url = (URL) it.next();
                JarFile jarFile = null;
                if (Intrinsics.areEqual(url.getProtocol(), "jar")) {
                    URLConnection uRLConnectionOpenConnection = url.openConnection();
                    JarURLConnection jarURLConnection = uRLConnectionOpenConnection instanceof JarURLConnection ? (JarURLConnection) uRLConnectionOpenConnection : null;
                    URL jarFileURL = jarURLConnection != null ? jarURLConnection.getJarFileURL() : null;
                    if (jarFileURL != null && !hashSet.contains(jarFileURL)) {
                        hashSet.add(jarFileURL);
                        try {
                            jarFile = jarURLConnection.getJarFile();
                        } catch (IOException unused) {
                        }
                        if (jarFile != null) {
                            forAllMatchingFilesInJarFile(jarFile, regex, function2);
                        }
                    }
                } else {
                    Intrinsics.checkNotNull(url);
                    File fileOrNull = PathUtilKt.toFileOrNull(url);
                    File fileInvoke = fileOrNull != null ? classLoaderResourceRootFIlePathCalculator.invoke(fileOrNull) : null;
                    if (fileInvoke != null && fileInvoke.isDirectory() && !hashSet2.contains(fileInvoke)) {
                        hashSet2.add(fileInvoke);
                        forAllMatchingFilesInDirectory(fileInvoke, str, function2);
                    }
                }
            }
        }
    }

    static {
        char[] charArray = "*?".toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
        wildcardChars = charArray;
        char[] charArray2 = ".*?+()[]^${}|".toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray2, "toCharArray(...)");
        int length = charArray.length;
        for (int i = 0; i < length && ArraysKt.contains(charArray2, charArray[i]); i++) {
        }
        patternCharsToEscape = charArray2;
        char[] charArray3 = (File.separatorChar == '/' ? "/" : "/" + File.separator).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray3, "toCharArray(...)");
        pathSeparatorChars = charArray3;
        pathElementPattern = File.separatorChar == '/' ? "[^/]*" : "[^/" + escape(File.separatorChar) + "]*";
        pathSeparatorPattern = File.separatorChar != '/' ? "[/" + escape(File.separatorChar) + ']' : "/";
        specialPatternChars = ArraysKt.plus(charArray2, charArray3);
    }

    public static final char[] getWildcardChars() {
        return wildcardChars;
    }

    public static final char[] getPatternCharsToEscape() {
        return patternCharsToEscape;
    }

    private static final String escape(char c) {
        return ((c == '\\' || ArraysKt.contains(patternCharsToEscape, c)) ? "\\" : "") + c;
    }

    public static final char[] getPathSeparatorChars() {
        return pathSeparatorChars;
    }

    public static final String getPathElementPattern() {
        return pathElementPattern;
    }

    public static final String getPathSeparatorPattern() {
        return pathSeparatorPattern;
    }

    public static final char[] getSpecialPatternChars() {
        return specialPatternChars;
    }

    public static final String toUniversalSeparator(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return File.separatorChar == '/' ? str : StringsKt.replace$default(str, File.separatorChar, '/', false, 4, (Object) null);
    }

    public static final void forAllMatchingFilesInDirectory(File baseDir, String namePattern, Function2<? super String, ? super InputStream, Unit> body) {
        final File fileResolve;
        Intrinsics.checkNotNullParameter(baseDir, "baseDir");
        Intrinsics.checkNotNullParameter(namePattern, "namePattern");
        Intrinsics.checkNotNullParameter(body, "body");
        String str = namePattern;
        int iIndexOfAny$default = StringsKt.indexOfAny$default((CharSequence) str, wildcardChars, 0, false, 6, (Object) null);
        if (iIndexOfAny$default < 0) {
            File fileResolve2 = FilesKt.resolve(baseDir, namePattern);
            if (!fileResolve2.exists() || !fileResolve2.isFile()) {
                fileResolve2 = null;
            }
            if (fileResolve2 != null) {
                String path = FilesKt.relativeToOrSelf(fileResolve2, baseDir).getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                body.invoke(toUniversalSeparator(path), new FileInputStream(fileResolve2));
                return;
            }
            return;
        }
        int iLastIndexOfAny$default = StringsKt.lastIndexOfAny$default((CharSequence) str, pathSeparatorChars, iIndexOfAny$default, false, 4, (Object) null);
        if (iLastIndexOfAny$default <= 0) {
            fileResolve = baseDir;
        } else {
            String strSubstring = namePattern.substring(0, iLastIndexOfAny$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            fileResolve = FilesKt.resolve(baseDir, strSubstring);
        }
        if (fileResolve.exists() && fileResolve.isDirectory()) {
            String strSubstring2 = namePattern.substring(iLastIndexOfAny$default + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            final Regex regexNamePatternToRegex = namePatternToRegex(strSubstring2);
            for (File file : SequencesKt.filter(FilesKt.walkTopDown(fileResolve), new Function1<File, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClassLoaderUtilKt.forAllMatchingFilesInDirectory.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Regex regex = regexNamePatternToRegex;
                    String path2 = FilesKt.relativeToOrSelf(it, fileResolve).getPath();
                    Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
                    return Boolean.valueOf(regex.matches(path2));
                }
            })) {
                String path2 = FilesKt.relativeToOrSelf(file, baseDir).getPath();
                Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
                body.invoke(toUniversalSeparator(path2), new FileInputStream(file));
            }
        }
    }

    public static final void forAllMatchingFilesInJarStream(JarInputStream jarInputStream, Regex nameRegex, Function2<? super String, ? super InputStream, Unit> body) throws IOException {
        JarEntry nextJarEntry;
        Intrinsics.checkNotNullParameter(jarInputStream, "jarInputStream");
        Intrinsics.checkNotNullParameter(nameRegex, "nameRegex");
        Intrinsics.checkNotNullParameter(body, "body");
        do {
            nextJarEntry = jarInputStream.getNextJarEntry();
            if (nextJarEntry != null) {
                try {
                    if (!nextJarEntry.isDirectory()) {
                        String name2 = nextJarEntry.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        if (nameRegex.matches(name2)) {
                            String name3 = nextJarEntry.getName();
                            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                            body.invoke(name3, jarInputStream);
                        }
                    }
                    jarInputStream.closeEntry();
                } catch (Throwable th) {
                    jarInputStream.closeEntry();
                    throw th;
                }
            }
        } while (nextJarEntry != null);
    }

    public static final void forAllMatchingFilesInJar(File jarFile, Regex nameRegex, Function2<? super String, ? super InputStream, Unit> body) {
        Intrinsics.checkNotNullParameter(jarFile, "jarFile");
        Intrinsics.checkNotNullParameter(nameRegex, "nameRegex");
        Intrinsics.checkNotNullParameter(body, "body");
        JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarFile));
        try {
            forAllMatchingFilesInJarStream(jarInputStream, nameRegex, body);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(jarInputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(jarInputStream, th);
                throw th2;
            }
        }
    }

    public static final void forAllMatchingFilesInJarFile(JarFile jarFile, Regex nameRegex, Function2<? super String, ? super InputStream, Unit> body) throws IOException {
        Intrinsics.checkNotNullParameter(jarFile, "jarFile");
        Intrinsics.checkNotNullParameter(nameRegex, "nameRegex");
        Intrinsics.checkNotNullParameter(body, "body");
        Enumeration<JarEntry> enumerationEntries = jarFile.entries();
        Intrinsics.checkNotNullExpressionValue(enumerationEntries, "entries(...)");
        for (JarEntry jarEntry : SequencesKt.asSequence(CollectionsKt.iterator(enumerationEntries))) {
            if (!jarEntry.isDirectory()) {
                String name2 = jarEntry.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (nameRegex.matches(name2)) {
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    try {
                        InputStream inputStream2 = inputStream;
                        String name3 = jarEntry.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        Intrinsics.checkNotNull(inputStream2);
                        body.invoke(name3, inputStream2);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(inputStream, th);
                            throw th2;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public static final Regex namePatternToRegex(String pattern) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < pattern.length()) {
            int iIndexOfAny$default = StringsKt.indexOfAny$default((CharSequence) pattern, specialPatternChars, i, false, 4, (Object) null);
            if (iIndexOfAny$default < 0) {
                iIndexOfAny$default = pattern.length();
            }
            String strSubstring = pattern.substring(i, iIndexOfAny$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            sb.append(strSubstring);
            i = iIndexOfAny$default + 1;
            if (iIndexOfAny$default >= pattern.length()) {
                break;
            }
            if (ArraysKt.contains(pathSeparatorChars, pattern.charAt(iIndexOfAny$default))) {
                sb.append(pathSeparatorPattern);
            } else if (pattern.charAt(iIndexOfAny$default) == '?') {
                sb.append('.');
            } else if (pattern.charAt(iIndexOfAny$default) == '*' && i < pattern.length() && pattern.charAt(i) == '*') {
                sb.append(".*");
                i = iIndexOfAny$default + 2;
            } else if (pattern.charAt(iIndexOfAny$default) == '*') {
                sb.append(pathElementPattern);
            } else {
                sb.append('\\');
                sb.append(pattern.charAt(iIndexOfAny$default));
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new Regex(string);
    }
}
