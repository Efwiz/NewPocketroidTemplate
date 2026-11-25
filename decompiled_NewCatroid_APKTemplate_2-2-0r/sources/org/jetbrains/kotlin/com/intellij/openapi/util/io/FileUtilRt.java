package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.Consumer;

/* JADX INFO: loaded from: classes3.dex */
public class FileUtilRt {

    @Deprecated
    public static final FileFilter ALL_FILES;
    public static final int LARGE_FILE_PREVIEW_SIZE;
    public static final int LARGE_FOR_CONTENT_LOADING;
    public static final int MEGABYTE = 1048576;
    private static final Random RANDOM;
    private static final boolean USE_FILE_CHANNELS;
    private static String ourCanonicalTempPathCache;

    private interface CharComparingStrategy {
        public static final CharComparingStrategy IDENTITY = new CharComparingStrategy() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.CharComparingStrategy.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.CharComparingStrategy
            public boolean charsEqual(char c, char c2) {
                return c == c2;
            }
        };
        public static final CharComparingStrategy CASE_INSENSITIVE = new CharComparingStrategy() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.CharComparingStrategy.2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.CharComparingStrategy
            public boolean charsEqual(char c, char c2) {
                return StringUtilRt.charsEqualIgnoreCase(c, c2);
            }
        };

        boolean charsEqual(char c, char c2);
    }

    public interface RepeatableIOOperation<T, E extends Throwable> {
        T execute(boolean z) throws Throwable;
    }

    protected interface SymlinkResolver {
        boolean isSymlink(CharSequence charSequence);

        String resolveSymlinksAndCanonicalize(String str, char c, boolean z);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 9:
            case 11:
            case 21:
            case 23:
            case 29:
            case 31:
            case 33:
            case 40:
            case 49:
            case 55:
            case 57:
            case 58:
            case 59:
            case 60:
            case 66:
            case 69:
            case 72:
            case 74:
            case 75:
            case 80:
            case 82:
            case 84:
            case 86:
            case 87:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 9:
            case 11:
            case 21:
            case 23:
            case 29:
            case 31:
            case 33:
            case 40:
            case 49:
            case 55:
            case 57:
            case 58:
            case 59:
            case 60:
            case 66:
            case 69:
            case 72:
            case 74:
            case 75:
            case 80:
            case 82:
            case 84:
            case 86:
            case 87:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 5:
            case 78:
            case 79:
            case 90:
            case 97:
                objArr[0] = "path";
                break;
            case 3:
            case 9:
            case 11:
            case 21:
            case 23:
            case 29:
            case 31:
            case 33:
            case 40:
            case 49:
            case 55:
            case 57:
            case 58:
            case 59:
            case 60:
            case 66:
            case 69:
            case 72:
            case 74:
            case 75:
            case 80:
            case 82:
            case 84:
            case 86:
            case 87:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtilRt";
                break;
            case 4:
                objArr[0] = "root";
                break;
            case 6:
            case 7:
                objArr[0] = "result";
                break;
            case 8:
            case 10:
            case 12:
            case 17:
            case 19:
            case 20:
            case 22:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 13:
            case 25:
            case 27:
                objArr[0] = "filePath";
                break;
            case 14:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 15:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 70:
            case 76:
            case 77:
            case 88:
            case 89:
            case 91:
            case 93:
            case 94:
            case 95:
            case 96:
            default:
                objArr[0] = "file";
                break;
            case 16:
            case 30:
            case 32:
                objArr[0] = "name";
                break;
            case 18:
                objArr[0] = "expectedName";
                break;
            case 24:
            case 26:
                objArr[0] = "basePath";
                break;
            case 28:
                objArr[0] = "s";
                break;
            case 34:
            case 35:
            case 37:
            case 39:
            case 41:
            case 42:
            case 44:
            case 46:
            case 48:
            case 51:
            case 53:
                objArr[0] = "prefix";
                break;
            case 36:
            case 38:
            case 43:
            case 45:
            case 47:
            case 50:
            case 52:
                objArr[0] = "dir";
                break;
            case 54:
                objArr[0] = "suffix";
                break;
            case 56:
                objArr[0] = "temp";
                break;
            case 61:
                objArr[0] = "tempPath";
                break;
            case 71:
                objArr[0] = "encoding";
                break;
            case 73:
            case 81:
                objArr[0] = "reader";
                break;
            case 83:
            case 85:
                objArr[0] = "stream";
                break;
            case 92:
                objArr[0] = "ioTask";
                break;
            case 98:
                objArr[0] = "fromFile";
                break;
            case 99:
                objArr[0] = "toFile";
                break;
            case 100:
                objArr[0] = "inputStream";
                break;
            case 101:
                objArr[0] = "outputStream";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "splitPath";
                break;
            case 9:
            case 11:
                objArr[1] = "getExtension";
                break;
            case 21:
                objArr[1] = "toSystemDependentName";
                break;
            case 23:
                objArr[1] = "toSystemIndependentName";
                break;
            case 29:
                objArr[1] = "ensureEnds";
                break;
            case 31:
            case 33:
                objArr[1] = "getNameWithoutExtension";
                break;
            case 40:
                objArr[1] = "createTempDirectory";
                break;
            case 49:
                objArr[1] = "createTempFile";
                break;
            case 55:
                objArr[1] = "calcName";
                break;
            case 57:
                objArr[1] = "normalizeFile";
                break;
            case 58:
                objArr[1] = "getTempDirectory";
                break;
            case 59:
            case 60:
                objArr[1] = "calcCanonicalTempPath";
                break;
            case 66:
                objArr[1] = "loadFile";
                break;
            case 69:
            case 72:
                objArr[1] = "loadFileText";
                break;
            case 74:
            case 75:
                objArr[1] = "loadText";
                break;
            case 80:
            case 82:
                objArr[1] = "loadLines";
                break;
            case 84:
            case 86:
            case 87:
                objArr[1] = "loadBytes";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtilRt";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "splitPath";
                break;
            case 3:
            case 9:
            case 11:
            case 21:
            case 23:
            case 29:
            case 31:
            case 33:
            case 40:
            case 49:
            case 55:
            case 57:
            case 58:
            case 59:
            case 60:
            case 66:
            case 69:
            case 72:
            case 74:
            case 75:
            case 80:
            case 82:
            case 84:
            case 86:
            case 87:
                break;
            case 4:
                objArr[2] = "isFilePathAcceptable";
                break;
            case 5:
            case 6:
                objArr[2] = "processRoot";
                break;
            case 7:
                objArr[2] = "processDots";
                break;
            case 8:
            case 10:
            case 12:
                objArr[2] = "getExtension";
                break;
            case 13:
            case 14:
                objArr[2] = "extensionEquals";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "fileNameEquals";
                break;
            case 19:
            case 20:
                objArr[2] = "toSystemDependentName";
                break;
            case 22:
                objArr[2] = "toSystemIndependentName";
                break;
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[2] = "getRelativePath";
                break;
            case 28:
                objArr[2] = "ensureEnds";
                break;
            case 30:
            case 32:
                objArr[2] = "getNameWithoutExtension";
                break;
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                objArr[2] = "createTempDirectory";
                break;
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                objArr[2] = "createTempFile";
                break;
            case 50:
            case 51:
                objArr[2] = "doCreateTempFile";
                break;
            case 52:
            case 53:
            case 54:
                objArr[2] = "calcName";
                break;
            case 56:
                objArr[2] = "normalizeFile";
                break;
            case 61:
                objArr[2] = "resetCanonicalTempPathCache";
                break;
            case 62:
            case 63:
            case 64:
            case 65:
                objArr[2] = "loadFile";
                break;
            case 67:
            case 68:
            case 70:
            case 71:
                objArr[2] = "loadFileText";
                break;
            case 73:
                objArr[2] = "loadText";
                break;
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
                objArr[2] = "loadLines";
                break;
            case 83:
            case 85:
                objArr[2] = "loadBytes";
                break;
            case 88:
                objArr[2] = "getParentFile";
                break;
            case 89:
                objArr[2] = "delete";
                break;
            case 90:
                objArr[2] = "deleteRecursivelyNIO";
                break;
            case 91:
                objArr[2] = "deleteRecursively";
                break;
            case 92:
                objArr[2] = "doIOOperation";
                break;
            case 93:
                objArr[2] = "deleteFile";
                break;
            case 94:
                objArr[2] = "ensureCanCreateFile";
                break;
            case 95:
                objArr[2] = "createIfNotExists";
                break;
            case 96:
                objArr[2] = "createParentDirs";
                break;
            case 97:
                objArr[2] = "createDirectory";
                break;
            case 98:
            case 99:
            case 100:
            case 101:
                objArr[2] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 102:
                objArr[2] = "fileToUri";
                break;
            default:
                objArr[2] = "isJarOrZip";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 9:
            case 11:
            case 21:
            case 23:
            case 29:
            case 31:
            case 33:
            case 40:
            case 49:
            case 55:
            case 57:
            case 58:
            case 59:
            case 60:
            case 66:
            case 69:
            case 72:
            case 74:
            case 75:
            case 80:
            case 82:
            case 84:
            case 86:
            case 87:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    static {
        int iMax = Math.max(20971520, Math.max(getUserFileSizeLimit(), getUserContentLoadLimit()));
        LARGE_FOR_CONTENT_LOADING = iMax;
        LARGE_FILE_PREVIEW_SIZE = Math.min(getLargeFilePreviewSize(), iMax);
        USE_FILE_CHANNELS = "true".equalsIgnoreCase(System.getProperty("idea.fs.useChannels"));
        RANDOM = new Random();
        ALL_FILES = new FileFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.2
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                return true;
            }
        };
    }

    public static boolean isJarOrZip(File file) {
        if (file == null) {
            $$$reportNull$$$0(0);
        }
        return isJarOrZip(file, true);
    }

    public static boolean isJarOrZip(File file, boolean z) {
        if (file == null) {
            $$$reportNull$$$0(1);
        }
        if (z && file.isDirectory()) {
            return false;
        }
        String path = file.getPath();
        return StringUtilRt.endsWithIgnoreCase(path, ".jar") || StringUtilRt.endsWithIgnoreCase(path, Constants.ZIP_EXTENSION);
    }

    public static List<String> splitPath(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(c, i);
            if (iIndexOf != -1) {
                arrayList.add(str.substring(i, iIndexOf));
                i = iIndexOf + 1;
            } else {
                arrayList.add(str.substring(i));
                return arrayList;
            }
        }
    }

    public static boolean isFilePathAcceptable(File file, FileFilter fileFilter) {
        if (file == null) {
            $$$reportNull$$$0(4);
        }
        if (fileFilter == null) {
            return true;
        }
        while (fileFilter.accept(file)) {
            file = file.getParentFile();
            if (file == null) {
                return true;
            }
        }
        return false;
    }

    private static final class NIOReflect {
        static final boolean IS_AVAILABLE;
        private static Class<?> ourAccessDeniedExceptionClass;
        private static Method ourAttributesIsOtherMethod;
        private static final ThreadLocal<Consumer<Object>> ourCallback = new ThreadLocal<>();
        private static Object ourDeletionVisitor;
        private static Method ourFileToPathMethod;
        private static Method ourFilesDeleteIfExistsMethod;
        private static Method ourFilesWalkMethod;
        private static Class<?> ourNoSuchFileExceptionClass;
        private static Method ourPathToFileMethod;

        static Object toPath(File file) throws IllegalAccessException, InvocationTargetException {
            return ourFileToPathMethod.invoke(file, new Object[0]);
        }

        static void deleteRecursively(Object obj, Consumer<Object> consumer) throws IllegalAccessException, InvocationTargetException {
            try {
                try {
                    ThreadLocal<Consumer<Object>> threadLocal = ourCallback;
                    threadLocal.set(consumer);
                    ourFilesWalkMethod.invoke(null, obj, ourDeletionVisitor);
                    threadLocal.remove();
                } catch (InvocationTargetException e) {
                    if (!ourNoSuchFileExceptionClass.isInstance(e.getCause())) {
                        throw e;
                    }
                    ourCallback.remove();
                }
            } catch (Throwable th) {
                ourCallback.remove();
                throw th;
            }
        }

        static {
            boolean z = false;
            try {
                Class<?> cls = Class.forName("java.nio.file.Path");
                Class<?> cls2 = Class.forName("java.nio.file.FileVisitor");
                Class<?> cls3 = Class.forName("java.nio.file.Files");
                ourNoSuchFileExceptionClass = Class.forName("java.nio.file.NoSuchFileException");
                ourAccessDeniedExceptionClass = Class.forName("java.nio.file.AccessDeniedException");
                ourFileToPathMethod = Class.forName(CommonClassNames.JAVA_IO_FILE).getMethod("toPath", new Class[0]);
                ourPathToFileMethod = cls.getMethod("toFile", new Class[0]);
                ourFilesWalkMethod = cls3.getMethod("walkFileTree", cls, cls2);
                ourAttributesIsOtherMethod = Class.forName("java.nio.file.attribute.BasicFileAttributes").getDeclaredMethod("isOther", new Class[0]);
                ourFilesDeleteIfExistsMethod = cls3.getMethod("deleteIfExists", cls);
                final Object obj = Class.forName("java.nio.file.FileVisitResult").getDeclaredField("CONTINUE").get(null);
                final Object obj2 = Class.forName("java.nio.file.FileVisitResult").getDeclaredField("SKIP_SUBTREE").get(null);
                ourDeletionVisitor = Proxy.newProxyInstance(FileUtilRt.class.getClassLoader(), new Class[]{cls2}, new InvocationHandler() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.NIOReflect.1
                    @Override // java.lang.reflect.InvocationHandler
                    public Object invoke(Object obj3, Method method, Object[] objArr) throws Throwable {
                        boolean zEquals;
                        if (objArr.length == 2) {
                            String name2 = method.getName();
                            Object obj4 = objArr[1];
                            if (obj4 instanceof Throwable) {
                                if (SystemInfoRt.isWindows && "visitFileFailed".equals(name2) && NIOReflect.ourNoSuchFileExceptionClass.isInstance(obj4)) {
                                    performDelete(objArr[0]);
                                } else {
                                    throw ((Throwable) obj4);
                                }
                            } else if ("visitFile".equals(name2) || "postVisitDirectory".equals(name2)) {
                                Consumer consumer = (Consumer) NIOReflect.ourCallback.get();
                                if (consumer != null) {
                                    consumer.consume(objArr[0]);
                                }
                                performDelete(objArr[0]);
                            } else if (SystemInfoRt.isWindows && "preVisitDirectory".equals(name2)) {
                                try {
                                    zEquals = Boolean.TRUE.equals(NIOReflect.ourAttributesIsOtherMethod.invoke(obj4, new Object[0]));
                                } catch (Throwable unused) {
                                    zEquals = false;
                                }
                                if (zEquals) {
                                    performDelete(objArr[0]);
                                    return obj2;
                                }
                            }
                        }
                        return obj;
                    }

                    private void performDelete(Object obj3) throws IOException {
                        for (int i = 10; i > 0; i--) {
                            try {
                                NIOReflect.ourFilesDeleteIfExistsMethod.invoke(null, obj3);
                                return;
                            } catch (IllegalAccessException e) {
                                throw new IllegalStateException(e);
                            } catch (InvocationTargetException e2) {
                                Throwable cause = e2.getCause();
                                if (!(cause instanceof IOException)) {
                                    throw new IllegalStateException(e2);
                                }
                                if (SystemInfoRt.isWindows && i != 1) {
                                    if (NIOReflect.ourAccessDeniedExceptionClass.isInstance(cause)) {
                                        try {
                                            File file = (File) NIOReflect.ourPathToFileMethod.invoke(obj3, new Object[0]);
                                            if (file.delete() || !file.exists()) {
                                                return;
                                            }
                                        } catch (Throwable unused) {
                                        }
                                    }
                                    try {
                                        Thread.sleep(10L);
                                    } catch (InterruptedException unused2) {
                                    }
                                } else {
                                    throw ((IOException) cause);
                                }
                            }
                        }
                    }
                });
                z = true;
            } catch (Throwable unused) {
                FileUtilRt.logger().info("Was not able to detect NIO API");
            }
            IS_AVAILABLE = z;
        }
    }

    public static String toCanonicalPath(String str, char c, boolean z) {
        return toCanonicalPath(str, c, z, null);
    }

    protected static String toCanonicalPath(String str, char c, boolean z, SymlinkResolver symlinkResolver) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (str.charAt(0) == '.') {
            if (str.length() == 1) {
                return "";
            }
            char cCharAt = str.charAt(1);
            if (cCharAt == '/' || cCharAt == c) {
                str = str.substring(2);
            }
        }
        if (c != '/') {
            str = str.replace(c, '/');
        }
        int iIndexOf = -1;
        do {
            iIndexOf = str.indexOf(47, iIndexOf + 1);
            char cCharAt2 = iIndexOf == str.length() - 1 ? (char) 0 : str.charAt(iIndexOf + 1);
            if (cCharAt2 == '.' || cCharAt2 == '/') {
                break;
            }
        } while (iIndexOf != -1);
        if (iIndexOf == -1) {
            if (!z) {
                return str;
            }
            int iProcessRoot = processRoot(str, NullAppendable.INSTANCE);
            int iLastIndexOf = str.lastIndexOf(47);
            return (iLastIndexOf == -1 || iLastIndexOf <= iProcessRoot || iLastIndexOf != str.length() - 1) ? str : str.substring(0, str.length() - 1);
        }
        StringBuilder sb = new StringBuilder(str.length());
        int iProcessRoot2 = processRoot(str, sb);
        int i = 0;
        boolean z2 = true;
        for (int i2 = iProcessRoot2; i2 < str.length(); i2++) {
            char cCharAt3 = str.charAt(i2);
            if (cCharAt3 == '/') {
                if (!z2) {
                    if (!processDots(sb, i, iProcessRoot2, symlinkResolver)) {
                        return symlinkResolver.resolveSymlinksAndCanonicalize(str, c, z);
                    }
                    i = 0;
                }
                z2 = true;
            } else {
                if (cCharAt3 != '.') {
                    while (i > 0) {
                        sb.append('.');
                        i--;
                    }
                    sb.append(cCharAt3);
                } else if (z2 || i > 0) {
                    i++;
                } else {
                    sb.append('.');
                }
                z2 = false;
            }
        }
        if (i > 0 && !processDots(sb, i, iProcessRoot2, symlinkResolver)) {
            return symlinkResolver.resolveSymlinksAndCanonicalize(str, c, z);
        }
        int length = sb.length() - 1;
        if (z && length >= 0 && sb.charAt(length) == '/' && length > iProcessRoot2) {
            sb.deleteCharAt(length);
        }
        return sb.toString();
    }

    private static int processRoot(String str, Appendable appendable) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (appendable == null) {
            $$$reportNull$$$0(6);
        }
        try {
            int i = 2;
            if (SystemInfoRt.isWindows && str.length() > 1 && str.charAt(0) == '/' && str.charAt(1) == '/') {
                appendable.append("//");
                while (i < str.length() && str.charAt(i) == '/') {
                    i++;
                }
                if (i == str.length()) {
                    return i;
                }
                int iIndexOf = str.indexOf(47, i);
                if (iIndexOf < 0) {
                    iIndexOf = str.length();
                }
                appendable.append(str, i, iIndexOf);
                appendable.append('/');
                while (iIndexOf < str.length() && str.charAt(iIndexOf) == '/') {
                    iIndexOf++;
                }
                if (iIndexOf == str.length()) {
                    return iIndexOf;
                }
                int iIndexOf2 = str.indexOf(47, iIndexOf);
                if (iIndexOf2 < 0) {
                    iIndexOf2 = str.length();
                }
                appendable.append(str, iIndexOf, iIndexOf2);
                appendable.append('/');
                return iIndexOf2;
            }
            if (!str.isEmpty() && str.charAt(0) == '/') {
                appendable.append('/');
                return 1;
            }
            if (str.length() <= 2 || str.charAt(1) != ':' || str.charAt(2) != '/') {
                return 0;
            }
            appendable.append(str, 0, 3);
            return 3;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean processDots(StringBuilder sb, int i, int i2, SymlinkResolver symlinkResolver) {
        if (sb == null) {
            $$$reportNull$$$0(7);
        }
        if (i == 2) {
            if (StringUtilRt.endsWith(sb, "/../") || "../".contentEquals(sb)) {
                i2 = -1;
            } else {
                int iLastIndexOf = StringUtilRt.lastIndexOf(sb, '/', i2, sb.length() - 1);
                if (iLastIndexOf >= 0) {
                    i2 = iLastIndexOf + 1;
                } else if (i2 <= 0) {
                    i2 = sb.length() > 0 ? 0 : iLastIndexOf;
                }
            }
            if (i2 >= 0) {
                if (symlinkResolver != null && symlinkResolver.isSymlink(sb)) {
                    return false;
                }
                sb.delete(i2, sb.length());
            } else {
                sb.append("../");
            }
        } else if (i != 1) {
            for (int i3 = 0; i3 < i; i3++) {
                sb.append('.');
            }
            sb.append('/');
        }
        return true;
    }

    public static String getExtension(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf < 0) {
            return "";
        }
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(9);
        }
        return strSubstring;
    }

    public static CharSequence getExtension(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(10);
        }
        CharSequence extension = getExtension(charSequence, "");
        if (extension == null) {
            $$$reportNull$$$0(11);
        }
        return extension;
    }

    public static CharSequence getExtension(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        int iLastIndexOf = StringUtilRt.lastIndexOf(charSequence, '.', 0, charSequence.length());
        return iLastIndexOf < 0 ? str : charSequence.subSequence(iLastIndexOf + 1, charSequence.length());
    }

    public static boolean extensionEquals(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (str2 == null) {
            $$$reportNull$$$0(14);
        }
        int length = str2.length();
        if (length == 0) {
            return str.indexOf(46, Math.max(str.lastIndexOf(47), str.lastIndexOf(92)) + 1) == -1;
        }
        int length2 = str.length() - length;
        return length2 >= 1 && str.charAt(length2 + (-1)) == '.' && str.regionMatches(SystemInfoRt.isFileSystemCaseSensitive ^ true, length2, str2, 0, length);
    }

    public static boolean fileNameEquals(File file, String str) {
        if (file == null) {
            $$$reportNull$$$0(15);
        }
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        return fileNameEquals(file.getName(), str);
    }

    public static boolean fileNameEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(17);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(18);
        }
        return StringUtilRt.equal(charSequence2, charSequence, SystemInfoRt.isFileSystemCaseSensitive);
    }

    public static String toSystemDependentName(String str) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        return toSystemDependentName(str, File.separatorChar);
    }

    public static String toSystemDependentName(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        String strReplace = str.replace('/', c).replace('\\', c);
        if (strReplace == null) {
            $$$reportNull$$$0(21);
        }
        return strReplace;
    }

    public static String toSystemIndependentName(String str) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        String strReplace = str.replace('\\', '/');
        if (strReplace == null) {
            $$$reportNull$$$0(23);
        }
        return strReplace;
    }

    public static String getRelativePath(File file, File file2) {
        if (file == null || file2 == null) {
            return null;
        }
        if (file.equals(file2)) {
            return ".";
        }
        return getRelativePath(file.getAbsolutePath(), file2.getAbsolutePath(), File.separatorChar);
    }

    public static String getRelativePath(String str, String str2, char c) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        if (str2 == null) {
            $$$reportNull$$$0(25);
        }
        return getRelativePath(str, str2, c, SystemInfoRt.isFileSystemCaseSensitive);
    }

    public static String getRelativePath(String str, String str2, char c, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (str2 == null) {
            $$$reportNull$$$0(27);
        }
        String strEnsureEnds = ensureEnds(str, c);
        String strEnsureEnds2 = ensureEnds(str2, c);
        if (z) {
            if (strEnsureEnds.equals(strEnsureEnds2)) {
                return ".";
            }
        } else if (strEnsureEnds.equalsIgnoreCase(strEnsureEnds2)) {
            return ".";
        }
        CharComparingStrategy charComparingStrategy = z ? CharComparingStrategy.IDENTITY : CharComparingStrategy.CASE_INSENSITIVE;
        int i = 0;
        int i2 = 0;
        while (i < str2.length() && i < strEnsureEnds.length() && charComparingStrategy.charsEqual(str2.charAt(i), strEnsureEnds.charAt(i))) {
            if (strEnsureEnds.charAt(i) == c) {
                i2 = i;
            }
            i++;
        }
        if (i == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (i < strEnsureEnds.length()) {
            if (strEnsureEnds.charAt(i) == c) {
                sb.append("..");
                sb.append(c);
            }
            i++;
        }
        sb.append(str2.substring(i2 + 1));
        return sb.toString();
    }

    private static String ensureEnds(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        if (!StringUtilRt.endsWithChar(str, c)) {
            str = str + c;
        }
        if (str == null) {
            $$$reportNull$$$0(29);
        }
        return str;
    }

    public static CharSequence getNameWithoutExtension(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(30);
        }
        int iLastIndexOf = StringUtilRt.lastIndexOf(charSequence, '.', 0, charSequence.length());
        if (iLastIndexOf >= 0) {
            charSequence = charSequence.subSequence(0, iLastIndexOf);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(31);
        }
        return charSequence;
    }

    public static String getNameWithoutExtension(String str) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        String string = getNameWithoutExtension((CharSequence) str).toString();
        if (string == null) {
            $$$reportNull$$$0(33);
        }
        return string;
    }

    public static File createTempDirectory(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        return createTempDirectory(str, str2, true);
    }

    public static File createTempDirectory(String str, String str2, boolean z) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        return createTempDirectory(new File(getTempDirectory()), str, str2, z);
    }

    public static File createTempDirectory(File file, String str, String str2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(36);
        }
        if (str == null) {
            $$$reportNull$$$0(37);
        }
        return createTempDirectory(file, str, str2, true);
    }

    public static File createTempDirectory(File file, String str, String str2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(38);
        }
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        File fileDoCreateTempFile = doCreateTempFile(file, str, str2, true);
        if (z) {
            FilesToDeleteHolder.ourFilesToDelete.add(fileDoCreateTempFile.getPath());
        }
        if (!fileDoCreateTempFile.isDirectory()) {
            throw new IOException("Cannot create a directory: " + fileDoCreateTempFile);
        }
        if (fileDoCreateTempFile == null) {
            $$$reportNull$$$0(40);
        }
        return fileDoCreateTempFile;
    }

    private static class FilesToDeleteHolder {
        private static final Queue<String> ourFilesToDelete = createFilesToDelete();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtilRt$FilesToDeleteHolder", "createFilesToDelete"));
        }

        private static Queue<String> createFilesToDelete() {
            final ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
            Runtime.getRuntime().addShutdownHook(new Thread("FileUtil deleteOnExit") { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.FilesToDeleteHolder.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    while (true) {
                        String str = (String) concurrentLinkedQueue.poll();
                        if (str == null) {
                            return;
                        } else {
                            FileUtilRt.delete(new File(str));
                        }
                    }
                }
            });
            return concurrentLinkedQueue;
        }
    }

    public static File createTempFile(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(41);
        }
        return createTempFile(str, str2, false);
    }

    public static File createTempFile(String str, String str2, boolean z) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        return createTempFile(new File(getTempDirectory()), str, str2, true, z);
    }

    public static File createTempFile(File file, String str, String str2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(43);
        }
        if (str == null) {
            $$$reportNull$$$0(44);
        }
        return createTempFile(file, str, str2, true, true);
    }

    public static File createTempFile(File file, String str, String str2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(45);
        }
        if (str == null) {
            $$$reportNull$$$0(46);
        }
        return createTempFile(file, str, str2, z, true);
    }

    public static File createTempFile(File file, String str, String str2, boolean z, boolean z2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(47);
        }
        if (str == null) {
            $$$reportNull$$$0(48);
        }
        File fileDoCreateTempFile = doCreateTempFile(file, str, str2, false);
        if (z2) {
            fileDoCreateTempFile.deleteOnExit();
        }
        if (!z && !fileDoCreateTempFile.delete() && fileDoCreateTempFile.exists()) {
            throw new IOException("Cannot delete a file: " + fileDoCreateTempFile);
        }
        if (fileDoCreateTempFile == null) {
            $$$reportNull$$$0(49);
        }
        return fileDoCreateTempFile;
    }

    private static File doCreateTempFile(File file, String str, String str2, boolean z) throws IOException {
        File fileCalcName;
        boolean zMkdir;
        if (file == null) {
            $$$reportNull$$$0(50);
        }
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        file.mkdirs();
        if (str.length() < 3) {
            str = (str + "___").substring(0, 3);
        }
        if (str2 == null) {
            str2 = "";
        }
        String name2 = new File(str).getName();
        IOException e = null;
        int i = 10;
        int iNextInt = 0;
        int i2 = 0;
        while (true) {
            try {
                fileCalcName = calcName(file, name2, str2, iNextInt);
                if (z) {
                    try {
                        zMkdir = fileCalcName.mkdir();
                    } catch (IOException e2) {
                        e = e2;
                    }
                } else {
                    zMkdir = fileCalcName.createNewFile();
                }
                if (zMkdir) {
                    return normalizeFile(fileCalcName);
                }
            } catch (IOException e3) {
                e = e3;
                fileCalcName = null;
            }
            i2++;
            if (i2 > i / 2 || i2 > 100) {
                String[] list = file.list();
                int length = list == null ? 0 : list.length;
                int iMax = Math.max(10, length * 10);
                if (i2 > 100) {
                    if (e != null) {
                        throw e;
                    }
                    throw new IOException("Unable to create a temporary file " + fileCalcName + "\nDirectory '" + file + "' list (" + length + " children): " + Arrays.toString(list));
                }
                i = iMax;
            }
            iNextInt++;
            if (iNextInt > 2) {
                iNextInt = RANDOM.nextInt(i) + 2;
            }
        }
    }

    private static File calcName(File file, String str, String str2, int i) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(52);
        }
        if (str == null) {
            $$$reportNull$$$0(53);
        }
        if (str2 == null) {
            $$$reportNull$$$0(54);
        }
        if (i != 0) {
            str = str + i;
        }
        if (str.endsWith(".") && str2.startsWith(".")) {
            str = str.substring(0, str.length() - 1);
        }
        String str3 = str + str2;
        File file2 = new File(file, str3);
        if (str3.equals(file2.getName())) {
            return file2;
        }
        throw new IOException("A generated name is malformed: '" + str3 + "' (" + file2 + Constant.AFTER_QUTO);
    }

    private static File normalizeFile(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(56);
        }
        File canonicalFile = file.getCanonicalFile();
        if (SystemInfoRt.isWindows && canonicalFile.getAbsolutePath().contains(" ")) {
            canonicalFile = file.getAbsoluteFile();
        }
        if (canonicalFile == null) {
            $$$reportNull$$$0(57);
        }
        return canonicalFile;
    }

    public static String getTempDirectory() {
        if (ourCanonicalTempPathCache == null) {
            ourCanonicalTempPathCache = calcCanonicalTempPath();
        }
        String str = ourCanonicalTempPathCache;
        if (str == null) {
            $$$reportNull$$$0(58);
        }
        return str;
    }

    private static String calcCanonicalTempPath() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        try {
            String canonicalPath = file.getCanonicalPath();
            if (!SystemInfoRt.isWindows || !canonicalPath.contains(" ")) {
                if (canonicalPath == null) {
                    $$$reportNull$$$0(59);
                }
                return canonicalPath;
            }
        } catch (IOException unused) {
        }
        String absolutePath = file.getAbsolutePath();
        if (absolutePath == null) {
            $$$reportNull$$$0(60);
        }
        return absolutePath;
    }

    static void resetCanonicalTempPathCache(String str) {
        if (str == null) {
            $$$reportNull$$$0(61);
        }
        ourCanonicalTempPathCache = str;
    }

    public static File generateRandomTemporaryPath() throws IOException {
        File file = new File(getTempDirectory(), UUID.randomUUID().toString());
        for (int i = 0; file.exists() && i < 5; i++) {
            file = new File(getTempDirectory(), UUID.randomUUID().toString());
        }
        if (file.exists()) {
            throw new IOException("Couldn't generate unique random path.");
        }
        return normalizeFile(file);
    }

    public static String loadFile(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(62);
        }
        return loadFile(file, null, false);
    }

    public static String loadFile(File file, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(63);
        }
        return loadFile(file, null, z);
    }

    public static String loadFile(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(64);
        }
        return loadFile(file, str, false);
    }

    public static String loadFile(File file, String str, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(65);
        }
        String str2 = new String(loadFileText(file, str));
        if (z) {
            str2 = StringUtilRt.convertLineSeparators(str2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(66);
        }
        return str2;
    }

    public static char[] loadFileText(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(67);
        }
        return loadFileText(file, (String) null);
    }

    public static char[] loadFileText(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(68);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = str == null ? new InputStreamReader(fileInputStream, Charset.defaultCharset()) : new InputStreamReader(fileInputStream, str);
        try {
            char[] cArrLoadText = loadText(inputStreamReader, (int) file.length());
            inputStreamReader.close();
            if (cArrLoadText == null) {
                $$$reportNull$$$0(69);
            }
            return cArrLoadText;
        } catch (Throwable th) {
            inputStreamReader.close();
            throw th;
        }
    }

    public static char[] loadFileText(File file, Charset charset) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(70);
        }
        if (charset == null) {
            $$$reportNull$$$0(71);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), charset);
        try {
            char[] cArrLoadText = loadText(inputStreamReader, (int) file.length());
            inputStreamReader.close();
            if (cArrLoadText == null) {
                $$$reportNull$$$0(72);
            }
            return cArrLoadText;
        } catch (Throwable th) {
            inputStreamReader.close();
            throw th;
        }
    }

    public static char[] loadText(Reader reader, int i) throws IOException {
        if (reader == null) {
            $$$reportNull$$$0(73);
        }
        char[] cArr = new char[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = reader.read(cArr, i2, i - i2);
            if (i3 <= 0) {
                break;
            }
            i2 += i3;
        }
        if (i2 == i) {
            return cArr;
        }
        char[] cArrCopyOf = Arrays.copyOf(cArr, i2);
        if (cArrCopyOf == null) {
            $$$reportNull$$$0(75);
        }
        return cArrCopyOf;
    }

    public static List<String> loadLines(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(76);
        }
        return loadLines(file.getPath());
    }

    public static List<String> loadLines(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(77);
        }
        return loadLines(file.getPath(), str);
    }

    public static List<String> loadLines(String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(78);
        }
        return loadLines(str, (String) null);
    }

    public static List<String> loadLines(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(79);
        }
        FileInputStream fileInputStream = new FileInputStream(str);
        BufferedReader bufferedReader = new BufferedReader(str2 == null ? new InputStreamReader(fileInputStream, Charset.defaultCharset()) : new InputStreamReader(fileInputStream, str2));
        try {
            List<String> listLoadLines = loadLines(bufferedReader);
            bufferedReader.close();
            if (listLoadLines == null) {
                $$$reportNull$$$0(80);
            }
            return listLoadLines;
        } catch (Throwable th) {
            bufferedReader.close();
            throw th;
        }
    }

    public static List<String> loadLines(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader == null) {
            $$$reportNull$$$0(81);
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return arrayList;
            }
            arrayList.add(line);
        }
    }

    public static byte[] loadBytes(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(83);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (byteArray == null) {
            $$$reportNull$$$0(84);
        }
        return byteArray;
    }

    public static boolean isTooLarge(long j) {
        return j > ((long) LARGE_FOR_CONTENT_LOADING);
    }

    public static byte[] loadBytes(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(85);
        }
        if (i == 0) {
            byte[] bArr = ArrayUtilRt.EMPTY_BYTE_ARRAY;
            if (bArr == null) {
                $$$reportNull$$$0(86);
            }
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr2, i2, i - i2);
            if (i3 <= 0) {
                break;
            }
            i2 += i3;
        }
        return bArr2;
    }

    public static File getParentFile(File file) {
        if (file == null) {
            $$$reportNull$$$0(88);
        }
        int i = 0;
        while (true) {
            file = file.getParentFile();
            if (file == null) {
                return null;
            }
            if (!".".equals(file.getName())) {
                if ("..".equals(file.getName())) {
                    i++;
                } else {
                    if (i <= 0) {
                        return file;
                    }
                    i--;
                }
            }
        }
    }

    public static boolean delete(File file) {
        if (file == null) {
            $$$reportNull$$$0(89);
        }
        if (NIOReflect.IS_AVAILABLE) {
            try {
                deleteRecursivelyNIO(NIOReflect.toPath(file), null);
                return true;
            } catch (IOException unused) {
                return false;
            } catch (Exception e) {
                logger().info(e);
                return false;
            }
        }
        return deleteRecursively(file);
    }

    static void deleteRecursivelyNIO(Object obj, Consumer<Object> consumer) throws IOException {
        if (obj == null) {
            $$$reportNull$$$0(90);
        }
        try {
            NIOReflect.deleteRecursively(obj, consumer);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            if (!(cause instanceof RuntimeException)) {
                throw new IllegalStateException(e2);
            }
            throw ((RuntimeException) cause);
        }
    }

    private static boolean deleteRecursively(File file) {
        if (file == null) {
            $$$reportNull$$$0(91);
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (!deleteRecursively(file2)) {
                    return false;
                }
            }
        }
        return deleteFile(file);
    }

    public static <T, E extends Throwable> T doIOOperation(RepeatableIOOperation<T, E> repeatableIOOperation) throws Throwable {
        if (repeatableIOOperation == null) {
            $$$reportNull$$$0(92);
        }
        int i = 10;
        while (i > 0) {
            T tExecute = repeatableIOOperation.execute(i == 1);
            if (tExecute != null) {
                return tExecute;
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException unused) {
            }
            i--;
        }
        return null;
    }

    protected static boolean deleteFile(final File file) {
        if (file == null) {
            $$$reportNull$$$0(93);
        }
        return Boolean.TRUE.equals((Boolean) doIOOperation(new RepeatableIOOperation<Boolean, RuntimeException>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.RepeatableIOOperation
            public Boolean execute(boolean z) {
                if (file.delete() || !file.exists()) {
                    return Boolean.TRUE;
                }
                if (z) {
                    return Boolean.FALSE;
                }
                return null;
            }
        }));
    }

    public static boolean ensureCanCreateFile(File file) {
        if (file == null) {
            $$$reportNull$$$0(94);
        }
        if (file.exists()) {
            return file.canWrite();
        }
        if (createIfNotExists(file)) {
            return delete(file);
        }
        return false;
    }

    public static boolean createIfNotExists(File file) {
        if (file == null) {
            $$$reportNull$$$0(95);
        }
        if (file.exists()) {
            return true;
        }
        try {
            if (!createParentDirs(file)) {
                return false;
            }
            new FileOutputStream(file).close();
            return true;
        } catch (IOException e) {
            logger().info(e);
            return false;
        }
    }

    public static boolean createParentDirs(File file) {
        if (file == null) {
            $$$reportNull$$$0(96);
        }
        File parentFile = file.getParentFile();
        return parentFile == null || createDirectory(parentFile);
    }

    public static boolean createDirectory(File file) {
        if (file == null) {
            $$$reportNull$$$0(97);
        }
        return file.isDirectory() || file.mkdirs();
    }

    public static void copy(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(98);
        }
        if (file2 == null) {
            $$$reportNull$$$0(99);
        }
        if (ensureCanCreateFile(file2)) {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    copy(fileInputStream, fileOutputStream);
                    fileInputStream.close();
                    fileOutputStream.close();
                    long jLastModified = file.lastModified();
                    if (jLastModified < 0) {
                        logger().warn("Invalid timestamp " + jLastModified + " of '" + file + FormatHelper.QUOTE);
                    } else {
                        if (file2.setLastModified(jLastModified)) {
                            return;
                        }
                        logger().warn("Unable to set timestamp " + jLastModified + " to '" + file2 + FormatHelper.QUOTE);
                    }
                } catch (Throwable th) {
                    fileInputStream.close();
                    throw th;
                }
            } catch (Throwable th2) {
                fileOutputStream.close();
                throw th2;
            }
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(100);
        }
        if (outputStream == null) {
            $$$reportNull$$$0(101);
        }
        if (USE_FILE_CHANNELS && (inputStream instanceof FileInputStream) && (outputStream instanceof FileOutputStream)) {
            FileChannel channel = ((FileInputStream) inputStream).getChannel();
            try {
                FileChannel channel2 = ((FileOutputStream) outputStream).getChannel();
                try {
                    channel.transferTo(0L, Long.MAX_VALUE, channel2);
                    channel2.close();
                    channel.close();
                    return;
                } catch (Throwable th) {
                    channel2.close();
                    throw th;
                }
            } catch (Throwable th2) {
                channel.close();
                throw th2;
            }
        }
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static int getUserFileSizeLimit() {
        return parseKilobyteProperty("idea.max.intellisense.filesize", 2560000);
    }

    public static int getUserContentLoadLimit() {
        return parseKilobyteProperty("idea.max.content.load.filesize", 20971520);
    }

    private static int getLargeFilePreviewSize() {
        return parseKilobyteProperty("idea.max.content.load.large.preview.size", 2560000);
    }

    private static int parseKilobyteProperty(String str, int i) {
        try {
            long j = Integer.parseInt(System.getProperty(str, String.valueOf(i / 1024)));
            if (j < 0) {
                return Integer.MAX_VALUE;
            }
            return (int) Math.min(j * 1024, 2147483647L);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LoggerRt logger() {
        return LoggerRt.getInstance("#com.intellij.openapi.util.io.FileUtilRt");
    }

    public static URI fileToUri(File file) {
        if (file == null) {
            $$$reportNull$$$0(102);
        }
        String absolutePath = file.getAbsolutePath();
        if (File.separatorChar != '/') {
            absolutePath = absolutePath.replace(File.separatorChar, '/');
        }
        if (!absolutePath.startsWith("/")) {
            absolutePath = "/" + absolutePath;
        }
        if (absolutePath.startsWith("//")) {
            absolutePath = "//" + absolutePath;
        }
        try {
            return new URI("file", null, absolutePath, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(absolutePath, e);
        }
    }

    public static int pathHashCode(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        String canonicalPath = toCanonicalPath(str, File.separatorChar, true);
        return SystemInfoRt.isFileSystemCaseSensitive ? canonicalPath.hashCode() : StringUtilRt.stringHashCodeInsensitive(canonicalPath);
    }

    public static boolean filesEqual(File file, File file2) {
        return pathsEqual(file == null ? null : file.getPath(), file2 != null ? file2.getPath() : null);
    }

    public static boolean pathsEqual(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        String canonicalPath = toCanonicalPath(str, File.separatorChar, true);
        String canonicalPath2 = toCanonicalPath(str2, File.separatorChar, true);
        if (SystemInfoRt.isFileSystemCaseSensitive) {
            return canonicalPath.equals(canonicalPath2);
        }
        return canonicalPath.equalsIgnoreCase(canonicalPath2);
    }
}
