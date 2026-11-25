package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.util.UrlUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public class UrlClassLoader extends ClassLoader implements ClassPath.ClassDataConsumer {
    protected final ClassPath.ClassDataConsumer classDataConsumer;
    private final ClassLoadingLocks<String> classLoadingLocks;
    protected final ClassPath classPath;
    private final List<Path> files;
    private final boolean isBootstrapResourcesAllowed;
    private static final boolean isParallelCapable = registerAsParallelCapable();
    private static final ClassLoader appClassLoader = UrlClassLoader.class.getClassLoader();
    private static final ThreadLocal<Boolean> skipFindingResource = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 9 || i == 35 || i == 18 || i == 19 || i == 30 || i == 31) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 9 || i == 35 || i == 18 || i == 19 || i == 30 || i == 31) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 9:
            case 18:
            case 19:
            case 30:
            case 31:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/UrlClassLoader";
                break;
            case 5:
            case 6:
            case 8:
                objArr[0] = "parent";
                break;
            case 7:
                objArr[0] = "classLoader";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "builder";
                break;
            case 14:
            case 17:
                objArr[0] = "files";
                break;
            case 15:
                objArr[0] = "classPath";
                break;
            case 16:
            case 34:
                objArr[0] = "url";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 29:
            case 32:
                objArr[0] = "name";
                break;
            case 26:
                objArr[0] = "dir";
                break;
            case 27:
                objArr[0] = "fileNameFilter";
                break;
            case 28:
                objArr[0] = "consumer";
                break;
            case 33:
                objArr[0] = "path";
                break;
            default:
                objArr[0] = "jar";
                break;
        }
        if (i == 1) {
            objArr[1] = "getClassPath";
        } else if (i == 2) {
            objArr[1] = "getLoadedClasses";
        } else if (i == 3) {
            objArr[1] = "getBaseUrls";
        } else if (i == 4) {
            objArr[1] = "getLoadingStats";
        } else if (i == 9) {
            objArr[1] = "createDefaultBuilderForJdk";
        } else if (i == 35) {
            objArr[1] = "urlToFilePath";
        } else if (i == 18) {
            objArr[1] = "getUrls";
        } else if (i == 19) {
            objArr[1] = "getFiles";
        } else if (i == 30) {
            objArr[1] = "findResources";
        } else if (i != 31) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/UrlClassLoader";
        } else {
            objArr[1] = "getClassLoadingLock";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 9:
            case 18:
            case 19:
            case 30:
            case 31:
            case 35:
                break;
            case 5:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "<init>";
                break;
            case 6:
            case 7:
                objArr[2] = "registerInClassLoaderValueMap";
                break;
            case 8:
                objArr[2] = "createDefaultBuilderForJdk";
                break;
            case 16:
                objArr[2] = "addURL";
                break;
            case 17:
                objArr[2] = "addFiles";
                break;
            case 20:
                objArr[2] = "findClass";
                break;
            case 21:
                objArr[2] = "isByteBufferSupported";
                break;
            case 22:
            case 23:
                objArr[2] = "consumeClassData";
                break;
            case 24:
                objArr[2] = "findResource";
                break;
            case 25:
                objArr[2] = "getResourceAsStream";
                break;
            case 26:
            case 27:
            case 28:
                objArr[2] = "processResources";
                break;
            case 29:
                objArr[2] = "findResources";
                break;
            case 32:
                objArr[2] = "loadClassInsideSelf";
                break;
            case 33:
                objArr[2] = "toCanonicalPath";
                break;
            case 34:
                objArr[2] = "urlToFilePath";
                break;
            default:
                objArr[2] = "appendToClassPathForInstrumentation";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 9 && i != 35 && i != 18 && i != 19 && i != 30 && i != 31) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected ProtectionDomain getProtectionDomain() {
        return null;
    }

    public final List<URL> getUrls() {
        ArrayList arrayList = new ArrayList();
        Iterator<Path> it = this.files.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(it.next().toUri().toURL());
            } catch (MalformedURLException unused) {
            }
        }
        return arrayList;
    }

    public final boolean hasLoadedClass(String str) {
        Class<?> clsFindLoadedClass = findLoadedClass(str);
        return clsFindLoadedClass != null && clsFindLoadedClass.getClassLoader() == this;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String str) throws ClassNotFoundException {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        if (str.startsWith("org.jetbrains.kotlin.com.intellij.util.lang.")) {
            return appClassLoader.loadClass(str);
        }
        try {
            Class<?> clsFindClass = this.classPath.findClass(str, this.classDataConsumer);
            if (clsFindClass != null) {
                return clsFindClass;
            }
            throw new ClassNotFoundException(str);
        } catch (IOException e) {
            throw new ClassNotFoundException(str, e);
        }
    }

    private void definePackageIfNeeded(String str, Loader loader) throws IOException {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            return;
        }
        String strSubstring = str.substring(0, iLastIndexOf);
        if (isPackageDefined(strSubstring)) {
            return;
        }
        try {
            Map<Loader.Attribute, String> attributes = loader.getAttributes();
            if (attributes == null || attributes.isEmpty()) {
                definePackage(strSubstring, null, null, null, null, null, null, null);
            } else {
                definePackage(strSubstring, attributes.get(Loader.Attribute.SPEC_TITLE), attributes.get(Loader.Attribute.SPEC_VERSION), attributes.get(Loader.Attribute.SPEC_VENDOR), attributes.get(Loader.Attribute.IMPL_TITLE), attributes.get(Loader.Attribute.IMPL_VERSION), attributes.get(Loader.Attribute.IMPL_VENDOR), null);
            }
        } catch (IllegalArgumentException unused) {
        }
    }

    protected boolean isPackageDefined(String str) {
        return getPackage(str) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.ClassPath.ClassDataConsumer
    public Class<?> consumeClassData(String str, byte[] bArr, Loader loader, ProtectionDomain protectionDomain) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        definePackageIfNeeded(str, loader);
        int length = bArr.length;
        if (protectionDomain == null) {
            protectionDomain = getProtectionDomain();
        }
        return super.defineClass(str, bArr, 0, length, protectionDomain);
    }

    @Override // java.lang.ClassLoader
    public URL findResource(String str) {
        Resource resourceDoFindResource;
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        if (skipFindingResource.get() == null && (resourceDoFindResource = doFindResource(str)) != null) {
            return resourceDoFindResource.getURL();
        }
        return null;
    }

    @Override // java.lang.ClassLoader
    public InputStream getResourceAsStream(String str) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        Resource resourceDoFindResource = doFindResource(str);
        if (resourceDoFindResource != null) {
            try {
                return resourceDoFindResource.getInputStream();
            } catch (IOException e) {
                logError("Cannot load resource " + str, e);
                return null;
            }
        }
        if (this.isBootstrapResourcesAllowed) {
            ThreadLocal<Boolean> threadLocal = skipFindingResource;
            threadLocal.set(Boolean.TRUE);
            try {
                URL resource = super.getResource(str);
                if (resource != null) {
                    try {
                        InputStream inputStreamOpenStream = resource.openStream();
                        threadLocal.set(null);
                        return inputStreamOpenStream;
                    } catch (IOException unused) {
                    }
                }
                skipFindingResource.set(null);
            } catch (Throwable th) {
                skipFindingResource.set(null);
                throw th;
            }
        }
        return null;
    }

    private Resource doFindResource(String str) {
        String canonicalPath = toCanonicalPath(str);
        Resource resourceFindResource = this.classPath.findResource(canonicalPath);
        if (resourceFindResource == null && canonicalPath.startsWith("/") && this.classPath.findResource(canonicalPath.substring(1)) != null) {
            logError("Calling `ClassLoader#getResource` with leading slash doesn't work; strip", new IllegalArgumentException(str));
        }
        return resourceFindResource;
    }

    @Override // java.lang.ClassLoader
    protected Enumeration<URL> findResources(String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(29);
        }
        Enumeration<URL> resources = this.classPath.getResources(str);
        if (resources == null) {
            $$$reportNull$$$0(30);
        }
        return resources;
    }

    protected final Object getClassLoadingLock(String str) {
        ClassLoadingLocks<String> classLoadingLocks = this.classLoadingLocks;
        Object orCreateLock = classLoadingLocks == null ? this : classLoadingLocks.getOrCreateLock(str);
        if (orCreateLock == null) {
            $$$reportNull$$$0(31);
        }
        return orCreateLock;
    }

    protected static String toCanonicalPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(33);
        }
        if (str.isEmpty()) {
            return str;
        }
        if (str.charAt(0) == '.') {
            if (str.length() == 1) {
                return "";
            }
            if (str.charAt(1) == '/') {
                str = str.substring(2);
            }
        }
        int iIndexOf = -1;
        do {
            iIndexOf = str.indexOf(47, iIndexOf + 1);
            char cCharAt = iIndexOf == str.length() - 1 ? (char) 0 : str.charAt(iIndexOf + 1);
            if (cCharAt == '.' || cCharAt == '/') {
                break;
            }
        } while (iIndexOf != -1);
        if (iIndexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        int iProcessRoot = processRoot(str, sb);
        int i = 0;
        boolean z = true;
        for (int i2 = iProcessRoot; i2 < str.length(); i2++) {
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 == '/') {
                if (!z) {
                    processDots(sb, i, iProcessRoot);
                    i = 0;
                }
                z = true;
            } else {
                if (cCharAt2 != '.') {
                    while (i > 0) {
                        sb.append('.');
                        i--;
                    }
                    sb.append(cCharAt2);
                } else if (z || i > 0) {
                    i++;
                } else {
                    sb.append('.');
                }
                z = false;
            }
        }
        if (i > 0) {
            processDots(sb, i, iProcessRoot);
        }
        return sb.toString();
    }

    private static void processDots(StringBuilder sb, int i, int i2) {
        if (i != 2) {
            if (i != 1) {
                for (int i3 = 0; i3 < i; i3++) {
                    sb.append('.');
                }
                sb.append('/');
                return;
            }
            return;
        }
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
            sb.delete(i2, sb.length());
        } else {
            sb.append("../");
        }
    }

    private static int processRoot(String str, StringBuilder sb) {
        if (!str.isEmpty() && str.charAt(0) == '/') {
            sb.append('/');
            return 1;
        }
        if (str.length() <= 2 || str.charAt(1) != ':' || str.charAt(2) != '/') {
            return 0;
        }
        sb.append((CharSequence) str, 0, 3);
        return 3;
    }

    private void logError(String str, Throwable th) {
        try {
            Class<?> cls = Class.forName("org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger", false, this);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            (void) lookup.findVirtual(cls, "error", MethodType.methodType(Void.TYPE, String.class, Throwable.class)).bindTo((Object) lookup.findStatic(cls, "getInstance", MethodType.methodType(cls, (Class<?>) Class.class)).invoke(getClass())).invokeExact(str, th);
        } catch (Throwable th2) {
            th2.addSuppressed(th);
            th2.printStackTrace(System.err);
        }
    }

    public static String urlToFilePath(String str) {
        int i;
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        int length = str.startsWith("file:") ? "file:".length() : 0;
        int iIndexOf = str.indexOf("!/");
        if (str.charAt(length) == '/' && str.length() > (i = length + 2) && str.charAt(i) == ':') {
            length++;
        }
        if (iIndexOf < 0) {
            iIndexOf = str.length();
        }
        String string = UrlUtilRt.unescapePercentSequences(str, length, iIndexOf).toString();
        if (string == null) {
            $$$reportNull$$$0(35);
        }
        return string;
    }
}
