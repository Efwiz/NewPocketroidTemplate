package org.jetbrains.kotlin.com.intellij.util.lang;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.ProtectionDomain;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassPath {
    private static final AtomicLong classDefineTotalTime;
    private static final Measurer classLoading;
    private static final Collection<Map.Entry<String, Path>> loadedClasses;
    static final boolean logLoadingInfo;
    static final boolean recordLoadingInfo;
    static final boolean recordLoadingTime;
    private static final Measurer resourceLoading;
    private volatile boolean allUrlsWereProcessed;
    private final ClasspathCache cache;
    private final CachePoolImpl cachePool;
    private final Predicate<? super Path> cachingCondition;
    final boolean errorOnMissingJar;
    private final List<Path> files;
    private final Set<Path> filesWithProtectionDomain;
    final boolean isClassPathIndexEnabled;
    private final AtomicInteger lastLoaderProcessed;
    private final Map<Path, Loader> loaderMap;
    private final List<Loader> loaders;
    final boolean lockJars;
    private final Function<Path, ResourceFile> resourceFileFactory;
    private final boolean useCache;

    interface ClassDataConsumer {
        Class<?> consumeClassData(String str, byte[] bArr, Loader loader, ProtectionDomain protectionDomain) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 18) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 18) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "filesWithProtectionDomain";
                break;
            case 2:
                objArr[0] = "configuration";
                break;
            case 3:
                objArr[0] = UncompressedFiles.NAME_paths;
                break;
            case 4:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassPath";
                break;
            case 5:
            default:
                objArr[0] = "files";
                break;
            case 6:
                objArr[0] = "newList";
                break;
            case 7:
            case 10:
                objArr[0] = "className";
                break;
            case 8:
                objArr[0] = "classDataConsumer";
                break;
            case 9:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 11:
                objArr[0] = "classConsumer";
                break;
            case 12:
            case 21:
            case 25:
                objArr[0] = "loader";
                break;
            case 13:
                objArr[0] = "resourceName";
                break;
            case 14:
                objArr[0] = "name";
                break;
            case 15:
                objArr[0] = "dir";
                break;
            case 16:
                objArr[0] = "fileNameFilter";
                break;
            case 17:
                objArr[0] = "consumer";
                break;
            case 19:
            case 20:
            case 22:
            case 23:
                objArr[0] = "file";
                break;
            case 24:
                objArr[0] = "manifestAttributes";
                break;
        }
        if (i == 4) {
            objArr[1] = "getLoadingStats";
        } else if (i != 18) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassPath";
        } else {
            objArr[1] = "getBaseUrls";
        }
        switch (i) {
            case 3:
                objArr[2] = "reset";
                break;
            case 4:
            case 18:
                break;
            case 5:
                objArr[2] = "addFiles";
                break;
            case 6:
                objArr[2] = "appendFiles";
                break;
            case 7:
            case 8:
                objArr[2] = "findClass";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "findClassInLoader";
                break;
            case 13:
                objArr[2] = "findResource";
                break;
            case 14:
                objArr[2] = "getResources";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "processResources";
                break;
            case 19:
                objArr[2] = "createLoader";
                break;
            case 20:
            case 21:
                objArr[2] = "initLoaderCache";
                break;
            case 22:
                objArr[2] = "getManifestData";
                break;
            case 23:
            case 24:
                objArr[2] = "cacheManifestData";
                break;
            case 25:
                objArr[2] = "loadManifestClasspath";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 18) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        boolean z = Boolean.getBoolean("idea.record.classpath.info");
        recordLoadingInfo = z;
        recordLoadingTime = z || Boolean.getBoolean("idea.record.classloading.stats");
        boolean z2 = Boolean.getBoolean("idea.log.classpath.info");
        logLoadingInfo = z2;
        classLoading = new Measurer();
        resourceLoading = new Measurer();
        classDefineTotalTime = new AtomicLong();
        loadedClasses = z ? new ConcurrentLinkedQueue() : null;
        if (z2) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.ClassPath$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    System.out.println("Classloading requests: " + ClassPath.class.getClassLoader() + ", class=" + ClassPath.classLoading + ", resource=" + ClassPath.resourceLoading);
                }
            }, "Shutdown hook for tracing classloading information"));
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x000f A[Catch: all -> 0x0022, LOOP:0: B:7:0x000d->B:8:0x000f, LOOP_END, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0007, B:8:0x000f, B:9:0x001d), top: B:15:0x0004 }] */
    synchronized void addFiles(List<Path> list) {
        int size;
        if (list == null) {
            $$$reportNull$$$0(5);
            for (size = list.size() - 1; size >= 0; size--) {
                this.files.add(list.get(size));
            }
            this.allUrlsWereProcessed = false;
        } else {
            while (size >= 0) {
                this.files.add(list.get(size));
            }
            this.allUrlsWereProcessed = false;
        }
        throw th;
    }

    public Class<?> findClass(String str, ClassDataConsumer classDataConsumer) throws IOException {
        Class<?> clsFindClassInLoader;
        Class<?> clsFindClassInLoader2;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (classDataConsumer == null) {
            $$$reportNull$$$0(8);
        }
        long jStartTiming = classLoading.startTiming();
        try {
            String str2 = str.replace('.', '/') + ".class";
            int i = 0;
            if (this.useCache) {
                Loader[] classLoadersByName = this.cache.getClassLoadersByName(str2);
                if (classLoadersByName != null) {
                    int length = classLoadersByName.length;
                    while (i < length) {
                        Loader loader = classLoadersByName[i];
                        if (loader.containsName(str2) && (clsFindClassInLoader2 = findClassInLoader(str2, str, classDataConsumer, loader)) != null) {
                            return clsFindClassInLoader2;
                        }
                        i++;
                    }
                }
                if (this.allUrlsWereProcessed) {
                    return null;
                }
                i = this.lastLoaderProcessed.get();
            }
            while (true) {
                int i2 = i + 1;
                Loader loader2 = getLoader(i);
                if (loader2 == null) {
                    return null;
                }
                if ((!this.useCache || loader2.containsName(str2)) && (clsFindClassInLoader = findClassInLoader(str2, str, classDataConsumer, loader2)) != null) {
                    return clsFindClassInLoader;
                }
                i = i2;
            }
        } finally {
            classLoading.record(jStartTiming, str);
        }
    }

    private static Class<?> findClassInLoader(String str, String str2, ClassDataConsumer classDataConsumer, Loader loader) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        if (classDataConsumer == null) {
            $$$reportNull$$$0(11);
        }
        if (loader == null) {
            $$$reportNull$$$0(12);
        }
        Class<?> clsFindClass = loader.findClass(str, str2, classDataConsumer);
        if (clsFindClass == null) {
            return null;
        }
        Collection<Map.Entry<String, Path>> collection = loadedClasses;
        if (collection != null) {
            collection.add(new AbstractMap.SimpleImmutableEntry(str, loader.path));
        }
        return clsFindClass;
    }

    public Resource findResource(String str) {
        Resource resource;
        Resource resource2;
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        long jStartTiming = resourceLoading.startTiming();
        try {
            int i = 0;
            if (this.useCache) {
                Loader[] loadersByName = this.cache.getLoadersByName(str);
                if (loadersByName != null) {
                    int length = loadersByName.length;
                    while (i < length) {
                        Loader loader = loadersByName[i];
                        if (loader.containsName(str) && (resource2 = loader.getResource(str)) != null) {
                            Collection<Map.Entry<String, Path>> collection = loadedClasses;
                            if (collection != null) {
                                collection.add(new AbstractMap.SimpleImmutableEntry(str, loader.path));
                            }
                            resourceLoading.record(jStartTiming, str);
                            return resource2;
                        }
                        i++;
                    }
                }
                if (!this.allUrlsWereProcessed) {
                    i = this.lastLoaderProcessed.get();
                } else {
                    resourceLoading.record(jStartTiming, str);
                    return null;
                }
            }
            while (true) {
                int i2 = i + 1;
                Loader loader2 = getLoader(i);
                if (loader2 == null) {
                    resourceLoading.record(jStartTiming, str);
                    return null;
                }
                if ((!this.useCache || loader2.containsName(str)) && (resource = loader2.getResource(str)) != null) {
                    Collection<Map.Entry<String, Path>> collection2 = loadedClasses;
                    if (collection2 != null) {
                        collection2.add(new AbstractMap.SimpleImmutableEntry(str, loader2.path));
                    }
                    resourceLoading.record(jStartTiming, str);
                    return resource;
                }
                i = i2;
            }
        } catch (Throwable th) {
            resourceLoading.record(jStartTiming, str);
            throw th;
        }
    }

    public Enumeration<URL> getResources(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        if (this.useCache && this.allUrlsWereProcessed) {
            Loader[] loadersByName = this.cache.getLoadersByName(str);
            return (loadersByName == null || loadersByName.length == 0) ? Collections.emptyEnumeration() : new ResourceEnumeration(str, loadersByName);
        }
        return new UncachedResourceEnumeration(str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Loader getLoader(int i) {
        return i < this.lastLoaderProcessed.get() ? this.loaders.get(i) : getLoaderSlowPath(i);
    }

    private synchronized Loader getLoaderSlowPath(int i) {
        while (this.loaders.size() < i + 1) {
            int size = this.files.size();
            if (size == 0) {
                if (this.useCache) {
                    this.allUrlsWereProcessed = true;
                }
                return null;
            }
            Path pathRemove = this.files.remove(size - 1);
            if (!this.loaderMap.containsKey(pathRemove)) {
                try {
                    Loader loaderCreateLoader = createLoader(pathRemove);
                    if (loaderCreateLoader != null) {
                        if (this.useCache) {
                            initLoaderCache(pathRemove, loaderCreateLoader);
                        }
                        this.loaders.add(loaderCreateLoader);
                        this.loaderMap.put(pathRemove, loaderCreateLoader);
                        this.lastLoaderProcessed.incrementAndGet();
                    }
                } catch (IOException e) {
                    LoggerRt.getInstance((Class<?>) ClassPath.class).info("path: " + pathRemove, e);
                }
            }
        }
        return this.loaders.get(i);
    }

    private Loader createLoader(Path path) throws IOException {
        ResourceFile resourceFileApply;
        String[] strArrLoadManifestClasspath;
        if (path == null) {
            $$$reportNull$$$0(19);
        }
        JarLoader jarLoader = null;
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            if (attributes.isDirectory()) {
                return new FileLoader(path, this.isClassPathIndexEnabled);
            }
            if (!attributes.isRegularFile()) {
                return null;
            }
            if (this.filesWithProtectionDomain.contains(path)) {
                jarLoader = new SecureJarLoader(path, this);
            } else {
                Function<Path, ResourceFile> function = this.resourceFileFactory;
                if (function == null) {
                    resourceFileApply = new JdkZipResourceFile(path, this.lockJars, false);
                } else {
                    resourceFileApply = function.apply(path);
                }
                jarLoader = new JarLoader(path, this, resourceFileApply);
            }
            String string = path.toString();
            if (string.startsWith(ModuleXmlParser.CLASSPATH, string.lastIndexOf(File.separatorChar) + 1) && (strArrLoadManifestClasspath = loadManifestClasspath(jarLoader)) != null) {
                long jNanoTime = logLoadingInfo ? System.nanoTime() : 0L;
                ArrayList arrayList = new ArrayList(strArrLoadManifestClasspath.length);
                for (String str : strArrLoadManifestClasspath) {
                    try {
                        arrayList.add(Paths.get(UrlClassLoader.urlToFilePath(str), new String[0]));
                    } catch (Exception e) {
                        LoggerRt.getInstance((Class<?>) ClassPath.class).warn("file: " + path + " / " + str, e);
                    }
                }
                addFiles(arrayList);
                if (logLoadingInfo) {
                    System.out.println("Loaded all " + strArrLoadManifestClasspath.length + " files " + ((System.nanoTime() - jNanoTime) / PackingOptions.SEGMENT_LIMIT) + LanguageCodeUtil.MS);
                }
            }
            return jarLoader;
        } catch (NoSuchFileException unused) {
        }
    }

    private void initLoaderCache(Path path, Loader loader) throws IOException {
        Predicate<? super Path> predicate;
        if (path == null) {
            $$$reportNull$$$0(20);
        }
        if (loader == null) {
            $$$reportNull$$$0(21);
        }
        CachePoolImpl cachePoolImpl = this.cachePool;
        ClasspathCache.LoaderData loaderDataBuildData = cachePoolImpl == null ? null : cachePoolImpl.loaderIndexCache.get(path);
        if (loaderDataBuildData == null) {
            loaderDataBuildData = loader.buildData();
            if (this.cachePool != null && (predicate = this.cachingCondition) != null && predicate.test(path)) {
                loaderDataBuildData = loaderDataBuildData instanceof ClasspathCache.LoaderData ? (ClasspathCache.LoaderData) loaderDataBuildData : ((ClasspathCache.LoaderDataBuilder) loaderDataBuildData).build();
                this.cachePool.loaderIndexCache.put(path, (ClasspathCache.LoaderData) loaderDataBuildData);
            }
        }
        this.cache.applyLoaderData(loaderDataBuildData, loader);
        if (this.files.isEmpty()) {
            this.allUrlsWereProcessed = true;
        }
    }

    Map<Loader.Attribute, String> getManifestData(Path path) {
        CachePoolImpl cachePoolImpl;
        if (path == null) {
            $$$reportNull$$$0(22);
        }
        if (!this.useCache || (cachePoolImpl = this.cachePool) == null) {
            return null;
        }
        return cachePoolImpl.getManifestData(path);
    }

    void cacheManifestData(Path path, Map<Loader.Attribute, String> map) {
        Predicate<? super Path> predicate;
        if (path == null) {
            $$$reportNull$$$0(23);
        }
        if (map == null) {
            $$$reportNull$$$0(24);
        }
        if (!this.useCache || this.cachePool == null || (predicate = this.cachingCondition) == null || !predicate.test(path)) {
            return;
        }
        this.cachePool.cacheManifestData(path, map);
    }

    private static final class ResourceEnumeration implements Enumeration<URL> {
        private int index;
        private final Loader[] loaders;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f724name;
        private Resource resource;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "name", "org/jetbrains/kotlin/com/intellij/util/lang/ClassPath$ResourceEnumeration", "<init>"));
        }

        ResourceEnumeration(String str, Loader[] loaderArr) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.f724name = str;
            this.loaders = loaderArr;
        }

        private boolean next() {
            if (this.resource != null) {
                return true;
            }
            long jStartTiming = ClassPath.resourceLoading.startTiming();
            while (true) {
                try {
                    int i = this.index;
                    Loader[] loaderArr = this.loaders;
                    if (i >= loaderArr.length) {
                        ClassPath.resourceLoading.record(jStartTiming, this.f724name);
                        return false;
                    }
                    this.index = i + 1;
                    Loader loader = loaderArr[i];
                    if (!loader.containsName(this.f724name)) {
                        this.resource = null;
                    } else {
                        Resource resource = loader.getResource(this.f724name);
                        this.resource = resource;
                        if (resource != null) {
                            ClassPath.resourceLoading.record(jStartTiming, this.f724name);
                            return true;
                        }
                    }
                } catch (Throwable th) {
                    ClassPath.resourceLoading.record(jStartTiming, this.f724name);
                    throw th;
                }
            }
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return next();
        }

        @Override // java.util.Enumeration
        public URL nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            Resource resource = this.resource;
            this.resource = null;
            return resource.getURL();
        }
    }

    private static final class UncachedResourceEnumeration implements Enumeration<URL> {
        private final ClassPath classPath;
        private int index;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f725name;
        private Resource resource;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "name";
            } else {
                objArr[0] = "classPath";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassPath$UncachedResourceEnumeration";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        UncachedResourceEnumeration(String str, ClassPath classPath) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (classPath == null) {
                $$$reportNull$$$0(1);
            }
            this.f725name = str;
            this.classPath = classPath;
        }

        private boolean next() {
            if (this.resource != null) {
                return true;
            }
            long jStartTiming = ClassPath.resourceLoading.startTiming();
            while (true) {
                try {
                    ClassPath classPath = this.classPath;
                    int i = this.index;
                    this.index = i + 1;
                    Loader loader = classPath.getLoader(i);
                    if (loader != null) {
                        if (!this.classPath.useCache || loader.containsName(this.f725name)) {
                            Resource resource = loader.getResource(this.f725name);
                            this.resource = resource;
                            if (resource != null) {
                                ClassPath.resourceLoading.record(jStartTiming, this.f725name);
                                return true;
                            }
                        }
                    } else {
                        ClassPath.resourceLoading.record(jStartTiming, this.f725name);
                        return false;
                    }
                } catch (Throwable th) {
                    ClassPath.resourceLoading.record(jStartTiming, this.f725name);
                    throw th;
                }
            }
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return next();
        }

        @Override // java.util.Enumeration
        public URL nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            Resource resource = this.resource;
            this.resource = null;
            return resource.getURL();
        }
    }

    private static String[] loadManifestClasspath(JarLoader jarLoader) {
        if (jarLoader == null) {
            $$$reportNull$$$0(25);
        }
        try {
            String classPathManifestAttribute = jarLoader.getClassPathManifestAttribute();
            if (classPathManifestAttribute == null) {
                return null;
            }
            String[] strArrSplit = classPathManifestAttribute.split(" ");
            if (strArrSplit.length <= 0 || !strArrSplit[0].startsWith("file:")) {
                return null;
            }
            return strArrSplit;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Measurer {
        private final ThreadLocal<Boolean> doingTiming;
        private final AtomicInteger requestCounter;
        private final AtomicLong timeCounter;

        private Measurer() {
            this.timeCounter = new AtomicLong();
            this.requestCounter = new AtomicInteger();
            this.doingTiming = new ThreadLocal<>();
        }

        long startTiming() {
            if (!ClassPath.recordLoadingTime || this.doingTiming.get() != null) {
                return -1L;
            }
            this.doingTiming.set(Boolean.TRUE);
            return System.nanoTime();
        }

        void record(long j, String str) {
            if (j == -1) {
                return;
            }
            this.doingTiming.set(null);
            long jNanoTime = System.nanoTime() - j;
            long jAddAndGet = this.timeCounter.addAndGet(jNanoTime);
            int iIncrementAndGet = this.requestCounter.incrementAndGet();
            if (ClassPath.logLoadingInfo) {
                if (jNanoTime > 3000000) {
                    System.out.println(TimeUnit.NANOSECONDS.toMillis(jNanoTime) + " ms for " + str);
                }
                if (iIncrementAndGet % 10000 == 0) {
                    System.out.println(ClassPath.class.getClassLoader() + ", requests: " + iIncrementAndGet + ", time:" + TimeUnit.NANOSECONDS.toMillis(jAddAndGet) + LanguageCodeUtil.MS);
                }
            }
        }

        public String toString() {
            return "Measurer(time=" + TimeUnit.NANOSECONDS.toMillis(this.timeCounter.get()) + "ms, requests=" + this.requestCounter + ')';
        }
    }
}
