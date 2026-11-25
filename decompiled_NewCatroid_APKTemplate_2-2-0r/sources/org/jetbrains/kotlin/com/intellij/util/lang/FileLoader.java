package org.jetbrains.kotlin.com.intellij.util.lang;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.time.DurationKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.io.DirectByteBufferPool;

/* JADX INFO: loaded from: classes3.dex */
final class FileLoader extends Loader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean isClassPathIndexEnabled;
    private final int rootDirAbsolutePathLength;
    private static final EnumSet<StandardOpenOption> READ_OPTIONS = EnumSet.of(StandardOpenOption.READ);
    private static final EnumSet<StandardOpenOption> WRITE_OPTIONS = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    private static final AtomicInteger totalLoaders = new AtomicInteger();
    private static final AtomicLong totalScanning = new AtomicLong();
    private static final AtomicLong totalSaving = new AtomicLong();
    private static final AtomicLong totalReading = new AtomicLong();
    private static final Boolean doFsActivityLogging = false;
    private static final BlockingDeque<Map.Entry<ClasspathCache.LoaderData, Path>> loaderDataToSave = new LinkedBlockingDeque();
    private static final AtomicBoolean isSaveThreadStarted = new AtomicBoolean();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "dir";
                break;
            case 2:
                objArr[0] = "fileNameFilter";
                break;
            case 3:
                objArr[0] = "consumer";
                break;
            case 4:
                objArr[0] = "startDir";
                break;
            case 5:
                objArr[0] = "context";
                break;
            case 6:
                objArr[0] = "absFilePath";
                break;
            case 7:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/FileLoader";
                break;
            case 8:
                objArr[0] = "name";
                break;
            case 9:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 10:
                objArr[0] = "data";
                break;
            case 11:
                objArr[0] = "indexFile";
                break;
            default:
                objArr[0] = "path";
                break;
        }
        if (i == 7) {
            objArr[1] = "getRelativeResourcePath";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/FileLoader";
        } else {
            objArr[1] = "buildData";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "processResources";
                break;
            case 4:
            case 5:
                objArr[2] = "buildPackageCache";
                break;
            case 6:
                objArr[2] = "getRelativeResourcePath";
                break;
            case 7:
            case 12:
                break;
            case 8:
                objArr[2] = "getResource";
                break;
            case 9:
                objArr[2] = "findClass";
                break;
            case 10:
            case 11:
                objArr[2] = "saveToIndex";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    public Map<Loader.Attribute, String> getAttributes() throws IOException {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FileLoader(Path path, boolean z) {
        super(path);
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        this.rootDirAbsolutePathLength = path.toString().length();
        this.isClassPathIndexEnabled = z;
    }

    private void buildPackageCache(Path path, ClasspathCache.LoaderDataBuilder loaderDataBuilder) {
        if (path == null) {
            $$$reportNull$$$0(4);
        }
        if (loaderDataBuilder == null) {
            $$$reportNull$$$0(5);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(path);
        while (true) {
            Path path2 = (Path) arrayDeque.pollFirst();
            if (path2 == null) {
                return;
            }
            try {
                try {
                    DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(path2);
                    try {
                        boolean z = false;
                        boolean z2 = false;
                        for (Path path3 : directoryStreamNewDirectoryStream) {
                            String strReplace = path.relativize(path3).toString().replace(File.separatorChar, '/');
                            if (strReplace.endsWith(".class")) {
                                loaderDataBuilder.andClassName(strReplace);
                                z = true;
                            } else {
                                loaderDataBuilder.addResourceName(strReplace, strReplace.length());
                                if (!strReplace.endsWith(Constants.SVG_FILE_EXTENSION) && !strReplace.endsWith(Constants.DEFAULT_IMAGE_EXTENSION) && !strReplace.endsWith(".xml")) {
                                    arrayDeque.addLast(path3);
                                }
                                z2 = true;
                            }
                        }
                        if (z || z2) {
                            String relativeResourcePath = getRelativeResourcePath(path2.toString());
                            if (z) {
                                loaderDataBuilder.addClassPackage(relativeResourcePath);
                            }
                            if (z2) {
                                loaderDataBuilder.addResourcePackage(relativeResourcePath);
                            }
                        }
                        if (directoryStreamNewDirectoryStream != null) {
                            directoryStreamNewDirectoryStream.close();
                        }
                    } catch (Throwable th) {
                        if (directoryStreamNewDirectoryStream != null) {
                            try {
                                directoryStreamNewDirectoryStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (NoSuchFileException | NotDirectoryException unused) {
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    private String getRelativeResourcePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        String strReplace = str.substring(this.rootDirAbsolutePathLength).replace(File.separatorChar, '/');
        if (strReplace.startsWith("/")) {
            strReplace = strReplace.substring(1);
        }
        if (strReplace == null) {
            $$$reportNull$$$0(7);
        }
        return strReplace;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    Resource getResource(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        Path pathResolve = this.path.resolve(str);
        if (Files.exists(pathResolve, new LinkOption[0])) {
            return new FileResource(pathResolve);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    Class<?> findClass(String str, String str2, ClassPath.ClassDataConsumer classDataConsumer) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        try {
            return classDataConsumer.consumeClassData(str2, Files.readAllBytes(this.path.resolve(str)), this, null);
        } catch (NoSuchFileException unused) {
            return null;
        }
    }

    private static ClasspathCache.LoaderData readFromIndex(Path path) throws IOException {
        long jNanoTime = System.nanoTime();
        try {
            try {
                SeekableByteChannel seekableByteChannelNewByteChannel = Files.newByteChannel(path, READ_OPTIONS, new FileAttribute[0]);
                try {
                    ByteBuffer byteBufferAllocate = DirectByteBufferPool.DEFAULT_POOL.allocate((int) seekableByteChannelNewByteChannel.size());
                    do {
                        try {
                            seekableByteChannelNewByteChannel.read(byteBufferAllocate);
                        } catch (Throwable th) {
                            DirectByteBufferPool.DEFAULT_POOL.release(byteBufferAllocate);
                            throw th;
                        }
                    } while (byteBufferAllocate.hasRemaining());
                    byteBufferAllocate.flip();
                    byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
                    if (byteBufferAllocate.getShort() == 23) {
                        int[] iArr = new int[byteBufferAllocate.getInt()];
                        int[] iArr2 = new int[byteBufferAllocate.getInt()];
                        IntBuffer intBufferAsIntBuffer = byteBufferAllocate.asIntBuffer();
                        intBufferAsIntBuffer.get(iArr);
                        intBufferAsIntBuffer.get(iArr2);
                        byteBufferAllocate.position(byteBufferAllocate.position() + (intBufferAsIntBuffer.position() * 4));
                        ClasspathCache.LoaderData loaderData = new ClasspathCache.LoaderData(iArr2, iArr, new ClasspathCache.NameFilter(byteBufferAllocate));
                        DirectByteBufferPool.DEFAULT_POOL.release(byteBufferAllocate);
                        if (seekableByteChannelNewByteChannel != null) {
                            seekableByteChannelNewByteChannel.close();
                        }
                        totalReading.addAndGet(System.nanoTime() - jNanoTime);
                        return loaderData;
                    }
                    DirectByteBufferPool.DEFAULT_POOL.release(byteBufferAllocate);
                    if (seekableByteChannelNewByteChannel != null) {
                        seekableByteChannelNewByteChannel.close();
                    }
                    Files.deleteIfExists(path);
                    totalReading.addAndGet(System.nanoTime() - jNanoTime);
                    return null;
                } catch (Throwable th2) {
                    if (seekableByteChannelNewByteChannel != null) {
                        try {
                            seekableByteChannelNewByteChannel.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                if (0 == 0) {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException unused) {
                    }
                }
                totalReading.addAndGet(System.nanoTime() - jNanoTime);
                throw th4;
            }
        } catch (NoSuchFileException | IOException unused2) {
        } catch (Exception e) {
            LoggerRt.getInstance((Class<?>) FileLoader.class).warn("Cannot read classpath index (version=-1, module=" + path.getParent().getFileName() + Constant.AFTER_QUTO, e);
            if (0 == 0) {
            }
            totalReading.addAndGet(System.nanoTime() - jNanoTime);
            return null;
        }
    }

    private static void saveToIndex(ClasspathCache.LoaderData loaderData, Path path) throws IOException {
        if (loaderData == null) {
            $$$reportNull$$$0(10);
        }
        if (path == null) {
            $$$reportNull$$$0(11);
        }
        long jNanoTime = System.nanoTime();
        SeekableByteChannel seekableByteChannelNewByteChannel = null;
        try {
            ByteBuffer byteBufferAllocate = DirectByteBufferPool.DEFAULT_POOL.allocate(loaderData.sizeInBytes() + 2);
            try {
                byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
                byteBufferAllocate.putShort((short) 23);
                loaderData.save(byteBufferAllocate);
                byteBufferAllocate.flip();
                seekableByteChannelNewByteChannel = Files.newByteChannel(path, WRITE_OPTIONS, new FileAttribute[0]);
                do {
                    seekableByteChannelNewByteChannel.write(byteBufferAllocate);
                } while (byteBufferAllocate.hasRemaining());
                DirectByteBufferPool.DEFAULT_POOL.release(byteBufferAllocate);
                if (seekableByteChannelNewByteChannel != null) {
                    try {
                        seekableByteChannelNewByteChannel.close();
                    } catch (Exception e) {
                        LoggerRt.getInstance((Class<?>) FileLoader.class).warn(e);
                    }
                }
                totalSaving.addAndGet(System.nanoTime() - jNanoTime);
            } catch (Throwable th) {
                DirectByteBufferPool.DEFAULT_POOL.release(byteBufferAllocate);
                throw th;
            }
        } catch (Throwable th2) {
            if (seekableByteChannelNewByteChannel != null) {
                try {
                    seekableByteChannelNewByteChannel.close();
                } catch (Exception e2) {
                    if (0 != 0) {
                        LoggerRt.getInstance((Class<?>) FileLoader.class).warn(e2);
                    }
                }
            }
            if (0 == 0) {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException unused) {
                }
            }
            totalSaving.addAndGet(System.nanoTime() - jNanoTime);
            throw th2;
        }
    }

    private Path getIndexFileFile() {
        return this.path.resolve("classpath.index");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    public ClasspathCache.IndexRegistrar buildData() {
        long jAddAndGet;
        Path indexFileFile = this.isClassPathIndexEnabled ? getIndexFileFile() : null;
        ClasspathCache.LoaderData fromIndex = indexFileFile != null ? readFromIndex(indexFileFile) : null;
        int iIncrementAndGet = totalLoaders.incrementAndGet();
        if (fromIndex == null) {
            long jNanoTime = System.nanoTime();
            ClasspathCache.LoaderDataBuilder loaderDataBuilder = new ClasspathCache.LoaderDataBuilder(true);
            buildPackageCache(this.path, loaderDataBuilder);
            fromIndex = loaderDataBuilder.build();
            long jNanoTime2 = System.nanoTime() - jNanoTime;
            jAddAndGet = totalScanning.addAndGet(jNanoTime2);
            if (doFsActivityLogging.booleanValue()) {
                System.out.println("Scanned: " + this.path + " for " + (jNanoTime2 / ((long) DurationKt.NANOS_IN_MILLIS)) + LanguageCodeUtil.MS);
            }
            if (indexFileFile != null) {
                loaderDataToSave.addLast(new AbstractMap.SimpleImmutableEntry(fromIndex, indexFileFile));
                startCacheSavingIfNeeded();
            }
        } else {
            jAddAndGet = totalScanning.get();
        }
        if (doFsActivityLogging.booleanValue()) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder("Scanning: ");
            long j = DurationKt.NANOS_IN_MILLIS;
            printStream.println(sb.append(jAddAndGet / j).append("ms, loading: ").append(totalReading.get() / j).append("ms for ").append(iIncrementAndGet).append(" loaders").toString());
        }
        if (fromIndex == null) {
            $$$reportNull$$$0(12);
        }
        return fromIndex;
    }

    private static void startCacheSavingIfNeeded() {
        if (isSaveThreadStarted.compareAndSet(false, true)) {
            Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.FileLoader$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    return FileLoader.lambda$startCacheSavingIfNeeded$0(runnable);
                }
            }).schedule(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.FileLoader$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FileLoader.lambda$startCacheSavingIfNeeded$1();
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    static /* synthetic */ Thread lambda$startCacheSavingIfNeeded$0(Runnable runnable) {
        Thread thread = new Thread(runnable, "Save classpath indexes for file loader");
        thread.setDaemon(true);
        thread.setPriority(1);
        return thread;
    }

    static /* synthetic */ void lambda$startCacheSavingIfNeeded$1() {
        while (true) {
            try {
                Map.Entry<ClasspathCache.LoaderData, Path> entryTakeFirst = loaderDataToSave.takeFirst();
                Path value = entryTakeFirst.getValue();
                Path pathResolve = value.getParent().resolve("classpath.index.tmp");
                try {
                    saveToIndex(entryTakeFirst.getKey(), pathResolve);
                    try {
                        Files.move(pathResolve, value, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                    } catch (AtomicMoveNotSupportedException unused) {
                        Files.move(pathResolve, value, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    LoggerRt.getInstance((Class<?>) FileLoader.class).warn("Cannot save classpath index for module " + value.getParent().getFileName(), e);
                }
            } catch (InterruptedException unused2) {
                return;
            }
        }
    }

    private static final class FileResource implements Resource {
        private final Path file;
        private URL url;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
            if (i == 1 || i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/FileLoader$FileResource";
            } else {
                objArr[0] = "file";
            }
            if (i == 1) {
                objArr[1] = "getURL";
            } else if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/FileLoader$FileResource";
            } else {
                objArr[1] = "getBytes";
            }
            if (i != 1 && i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        FileResource(Path path) {
            if (path == null) {
                $$$reportNull$$$0(0);
            }
            this.file = path;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.Resource
        public URL getURL() {
            URL url = this.url;
            if (url == null) {
                try {
                    url = this.file.toUri().toURL();
                    this.url = url;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (url == null) {
                $$$reportNull$$$0(1);
            }
            return url;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.Resource
        public InputStream getInputStream() throws IOException {
            return new BufferedInputStream(Files.newInputStream(this.file, new OpenOption[0]), 32000);
        }

        public String toString() {
            return this.file.toString();
        }
    }

    public String toString() {
        return "FileLoader(path=" + this.path + ')';
    }
}
