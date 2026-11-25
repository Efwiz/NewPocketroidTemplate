package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableNotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
public class PagedFileStorage implements Forceable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile boolean isDirty;
    private final Path myFile;
    private final Object myInputStreamLock;
    private final PagedFileStorageCache myLastAccessedBufferCache;
    private final boolean myNativeBytesOrder;
    protected final int myPageSize;
    private final boolean myReadOnly;
    private volatile long mySize;
    private int myStorageIndex;
    private final StorageLockContext myStorageLockContext;
    protected final boolean myValuesAreBufferAligned;
    private static final Logger LOG = Logger.getInstance((Class<?>) PagedFileStorage.class);
    private static final OpenChannelsCache CHANNELS_CACHE = new OpenChannelsCache(200);
    public static final int BUFFER_SIZE = FilePageCache.BUFFER_SIZE;
    private static final ThreadLocal<byte[]> ourTypedIOBuffer = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage$$ExternalSyntheticLambda4
        @Override // java.util.function.Supplier
        public final Object get() {
            return PagedFileStorage.lambda$static$0();
        }
    });
    private static final StorageLockContext ourDefaultContext = new StorageLockContext(true, false);
    public static final ThreadLocal<StorageLockContext> THREAD_LOCAL_STORAGE_LOCK_CONTEXT = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PagedFileStorage";
                break;
            case 3:
                objArr[0] = "consumer";
                break;
            case 6:
                objArr[0] = "processor";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 1) {
            objArr[1] = "getStorageLockContext";
        } else if (i == 2) {
            objArr[1] = "getFile";
        } else if (i == 4 || i == 5) {
            objArr[1] = "readInputStream";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PagedFileStorage";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                break;
            case 3:
                objArr[2] = "readInputStream";
                break;
            case 6:
                objArr[2] = "useChannel";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ byte[] lambda$static$0() {
        return new byte[8];
    }

    public PagedFileStorage(Path path, StorageLockContext storageLockContext, int i, boolean z, boolean z2) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        this.myLastAccessedBufferCache = new PagedFileStorageCache();
        this.myInputStreamLock = new Object();
        this.mySize = -1L;
        this.myFile = path;
        this.myReadOnly = PersistentHashMapValueStorage.CreationTimeOptions.READONLY.get() == Boolean.TRUE;
        StorageLockContext storageLockContext2 = THREAD_LOCAL_STORAGE_LOCK_CONTEXT.get();
        if (storageLockContext2 != null) {
            if (storageLockContext != null && storageLockContext != storageLockContext2) {
                throw new IllegalStateException();
            }
            storageLockContext = storageLockContext2;
        }
        storageLockContext = storageLockContext == null ? ourDefaultContext : storageLockContext;
        this.myStorageLockContext = storageLockContext;
        this.myPageSize = Math.max(i <= 0 ? BUFFER_SIZE : i, Page.PAGE_SIZE);
        this.myValuesAreBufferAligned = z;
        this.myStorageIndex = storageLockContext.getBufferCache().registerPagedFileStorage(this);
        this.myNativeBytesOrder = z2;
    }

    public int getPageSize() {
        return this.myPageSize;
    }

    public void lockRead() {
        this.myStorageLockContext.lockRead();
    }

    public void unlockRead() {
        this.myStorageLockContext.unlockRead();
    }

    public void lockWrite() {
        this.myStorageLockContext.lockWrite();
    }

    public void unlockWrite() {
        this.myStorageLockContext.unlockWrite();
    }

    public StorageLockContext getStorageLockContext() {
        StorageLockContext storageLockContext = this.myStorageLockContext;
        if (storageLockContext == null) {
            $$$reportNull$$$0(1);
        }
        return storageLockContext;
    }

    Path getFile() {
        Path path = this.myFile;
        if (path == null) {
            $$$reportNull$$$0(2);
        }
        return path;
    }

    public <R> R readInputStream(final ThrowableNotNullFunction<? super InputStream, R, ? extends IOException> throwableNotNullFunction) throws IOException {
        R r;
        if (throwableNotNullFunction == null) {
            $$$reportNull$$$0(3);
        }
        synchronized (this.myInputStreamLock) {
            try {
                r = (R) useChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage$$ExternalSyntheticLambda3
                    @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
                    public final Object process(FileChannel fileChannel) {
                        return PagedFileStorage.lambda$readInputStream$1(throwableNotNullFunction, fileChannel);
                    }
                }, true);
            } catch (NoSuchFileException unused) {
                R rFun = throwableNotNullFunction.fun(new ByteArrayInputStream(ArrayUtil.EMPTY_BYTE_ARRAY));
                if (rFun == null) {
                    $$$reportNull$$$0(5);
                }
                return rFun;
            }
        }
        if (r == null) {
            $$$reportNull$$$0(4);
        }
        return r;
    }

    static /* synthetic */ Object lambda$readInputStream$1(ThrowableNotNullFunction throwableNotNullFunction, FileChannel fileChannel) throws IOException {
        fileChannel.position(0L);
        return throwableNotNullFunction.fun(Channels.newInputStream(fileChannel));
    }

    <R> R useChannel(OpenChannelsCache.ChannelProcessor<R> channelProcessor, boolean z) throws IOException {
        if (channelProcessor == null) {
            $$$reportNull$$$0(6);
        }
        if (this.myStorageLockContext.useChannelCache()) {
            return (R) CHANNELS_CACHE.useChannel(this.myFile, channelProcessor, z);
        }
        OpenChannelsCache.ChannelDescriptor channelDescriptor = new OpenChannelsCache.ChannelDescriptor(this.myFile, z);
        try {
            R rProcess = channelProcessor.process(channelDescriptor.getChannel());
            channelDescriptor.close();
            return rProcess;
        } catch (Throwable th) {
            try {
                channelDescriptor.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void putInt(long j, int i) throws IOException {
        if (this.myValuesAreBufferAligned) {
            int i2 = this.myPageSize;
            getBuffer(j / ((long) i2)).putInt((int) (j % ((long) i2)), i);
            return;
        }
        Bits.putInt(getThreadLocalTypedIOBuffer(), 0, i);
        put(j, getThreadLocalTypedIOBuffer(), 0, 4);
    }

    public int getInt(long j) throws IOException {
        if (this.myValuesAreBufferAligned) {
            int i = this.myPageSize;
            return getReadOnlyBuffer(j / ((long) i)).getInt((int) (j % ((long) i)));
        }
        get(j, getThreadLocalTypedIOBuffer(), 0, 4);
        return Bits.getInt(getThreadLocalTypedIOBuffer(), 0);
    }

    public int getOffsetInPage(long j) {
        return (int) (j % ((long) this.myPageSize));
    }

    public DirectBufferWrapper getByteBuffer(long j, boolean z) throws IOException {
        return getBufferWrapper(j / ((long) this.myPageSize), z);
    }

    public void putLong(long j, long j2) throws IOException {
        if (this.myValuesAreBufferAligned) {
            int i = this.myPageSize;
            getBuffer(j / ((long) i)).putLong((int) (j % ((long) i)), j2);
            return;
        }
        Bits.putLong(getThreadLocalTypedIOBuffer(), 0, j2);
        put(j, getThreadLocalTypedIOBuffer(), 0, 8);
    }

    public long getLong(long j) throws IOException {
        if (this.myValuesAreBufferAligned) {
            int i = this.myPageSize;
            return getReadOnlyBuffer(j / ((long) i)).getLong((int) (j % ((long) i)));
        }
        get(j, getThreadLocalTypedIOBuffer(), 0, 8);
        return Bits.getLong(getThreadLocalTypedIOBuffer(), 0);
    }

    public byte get(long j) throws IOException {
        int i = this.myPageSize;
        return getReadOnlyBuffer(j / ((long) i)).get((int) (j % ((long) i)));
    }

    public void get(long j, byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int i3 = this.myPageSize;
            long j2 = j / ((long) i3);
            int i4 = (int) (j % ((long) i3));
            int iMin = Math.min(i2, i3 - i4);
            getReadOnlyBuffer(j2).readToArray(bArr, i, i4, iMin);
            i2 -= iMin;
            i += iMin;
            j += (long) iMin;
        }
    }

    public void put(long j, byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int i3 = this.myPageSize;
            long j2 = j / ((long) i3);
            int i4 = (int) (j % ((long) i3));
            int iMin = Math.min(i2, i3 - i4);
            getBuffer(j2).putFromArray(bArr, i, i4, iMin);
            i2 -= iMin;
            i += iMin;
            j += (long) iMin;
        }
    }

    public void close() throws IOException {
        SmartList smartList = new SmartList();
        ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws Exception {
                this.f$0.m6720xd71ec63f();
            }
        }));
        ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws Exception {
                this.f$0.m6721xdd22919e();
            }
        }));
        ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws Exception {
                this.f$0.m6722xe3265cfd();
            }
        }));
        if (!smartList.isEmpty()) {
            throw new IOException(new CompoundRuntimeException(smartList));
        }
    }

    /* JADX INFO: renamed from: lambda$close$3$org-jetbrains-kotlin-com-intellij-util-io-PagedFileStorage, reason: not valid java name */
    /* synthetic */ void m6721xdd22919e() throws Exception {
        unmapAll();
        this.myStorageLockContext.getBufferCache().removeStorage(this.myStorageIndex);
        this.myStorageIndex = -1;
    }

    /* JADX INFO: renamed from: lambda$close$4$org-jetbrains-kotlin-com-intellij-util-io-PagedFileStorage, reason: not valid java name */
    /* synthetic */ void m6722xe3265cfd() throws Exception {
        CHANNELS_CACHE.closeChannel(this.myFile);
    }

    private void unmapAll() {
        this.myStorageLockContext.getBufferCache().unmapBuffersForOwner(this.myStorageIndex, this.myStorageLockContext);
        this.myLastAccessedBufferCache.clear();
    }

    public void resize(long j) throws IOException {
        long size;
        if (Files.exists(this.myFile, new LinkOption[0])) {
            size = Files.size(this.myFile);
        } else {
            Files.createDirectories(this.myFile.getParent(), new FileAttribute[0]);
            size = 0;
        }
        if (size == j && size == length()) {
            return;
        }
        long jCurrentTimeMillis = IOStatistics.DEBUG ? System.currentTimeMillis() : 0L;
        this.myStorageLockContext.getBufferCache().invalidateBuffer(this.myStorageIndex | ((int) (size / ((long) this.myPageSize))));
        long jCurrentTimeMillis2 = IOStatistics.DEBUG ? System.currentTimeMillis() : 0L;
        resizeFile(j);
        long j2 = j - size;
        if (j2 > 0) {
            fillWithZeros(size, j2);
        }
        if (IOStatistics.DEBUG) {
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            long j3 = jCurrentTimeMillis3 - jCurrentTimeMillis;
            if (j3 > 100) {
                IOStatistics.dump("Resized " + this.myFile + " from " + size + " to " + j + " for " + j3 + ", unmap all:" + (jCurrentTimeMillis3 - jCurrentTimeMillis2));
            }
        }
    }

    private void resizeFile(long j) throws IOException {
        this.mySize = -1L;
        RandomAccessFile randomAccessFile = new RandomAccessFile(this.myFile.toFile(), "rw");
        try {
            randomAccessFile.setLength(j);
            randomAccessFile.close();
            this.mySize = j;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void fillWithZeros(long j, long j2) throws IOException {
        byte[] bArr = new byte[8192];
        Arrays.fill(bArr, (byte) 0);
        while (j2 > 0) {
            int iMin = Math.min((int) j2, 8192);
            put(j, bArr, 0, iMin);
            long j3 = iMin;
            j2 -= j3;
            j += j3;
        }
    }

    public final long length() {
        long size = this.mySize;
        if (size != -1) {
            return size;
        }
        if (Files.exists(this.myFile, new LinkOption[0])) {
            try {
                size = Files.size(this.myFile);
                this.mySize = size;
                return size;
            } catch (IOException e) {
                LOG.error((Throwable) e);
                return size;
            }
        }
        this.mySize = 0L;
        return 0L;
    }

    private DirectBufferWrapper getBuffer(long j) throws IOException {
        return getBufferWrapper(j, true);
    }

    private DirectBufferWrapper getReadOnlyBuffer(long j) throws IOException {
        return getBufferWrapper(j, false);
    }

    private DirectBufferWrapper getBufferWrapper(long j, boolean z) throws IOException {
        DirectBufferWrapper pageFromCache = this.myLastAccessedBufferCache.getPageFromCache(j, this.myStorageLockContext.getBufferCache().getMappingChangeCount());
        if (this.myReadOnly && z) {
            throw new IOException("Read-only storage can't be modified");
        }
        if (pageFromCache != null) {
            if (z) {
                markDirty();
            }
            return pageFromCache;
        }
        if (this.myStorageIndex == -1) {
            throw new IOException("storage is already closed; path " + this.myFile);
        }
        DirectBufferWrapper directBufferWrapper = this.myStorageLockContext.getBufferCache().get(Integer.valueOf(this.myStorageIndex | ((int) j)), !z, this.myReadOnly);
        if (z) {
            markDirty();
        }
        if (this.myNativeBytesOrder) {
            directBufferWrapper.useNativeByteOrder();
        }
        this.myLastAccessedBufferCache.updateCache(j, directBufferWrapper, this.myStorageLockContext.getBufferCache().getMappingChangeCount());
        return directBufferWrapper;
    }

    void markDirty() {
        if (this.isDirty) {
            return;
        }
        this.isDirty = true;
    }

    private static byte[] getThreadLocalTypedIOBuffer() {
        return ourTypedIOBuffer.get();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force, reason: merged with bridge method [inline-methods] */
    public void m6720xd71ec63f() throws IOException {
        long jCurrentTimeMillis = IOStatistics.DEBUG ? System.currentTimeMillis() : 0L;
        if (this.isDirty) {
            this.myStorageLockContext.getBufferCache().flushBuffersForOwner(this.myStorageIndex, this.myStorageLockContext);
            this.isDirty = false;
        }
        if (IOStatistics.DEBUG) {
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            if (jCurrentTimeMillis2 > 100) {
                IOStatistics.dump("Flushed " + this.myFile + " for " + jCurrentTimeMillis2);
            }
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }
}
