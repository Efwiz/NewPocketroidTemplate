package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public final class RandomAccessDataFile implements Forceable, Closeable {
    private static final Logger LOG = Logger.getInstance((Class<?>) RandomAccessDataFile.class);
    private static final OpenChannelsCache ourCache = new OpenChannelsCache(10);
    private static final AtomicInteger ourFilesCount = new AtomicInteger();
    private static final ThreadLocal<byte[]> ourTypedIOBuffer = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.RandomAccessDataFile$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return RandomAccessDataFile.lambda$static$0();
        }
    });
    public static long totalReadBytes;
    public static int totalReads;
    public static long totalWriteBytes;
    public static int totalWrites;
    private final int myCount;
    private final Path myFile;
    private volatile boolean myIsDirty;
    private volatile boolean myIsDisposed;
    private final PagePool myPool;
    private volatile long mySize;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "pool";
        } else if (i == 2) {
            objArr[0] = "name";
        } else if (i != 3) {
            objArr[0] = "file";
        } else {
            objArr[0] = "channelConsumer";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/RandomAccessDataFile";
        if (i == 2) {
            objArr[2] = "ensureNonNegative";
        } else if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "useFileChannel";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ byte[] lambda$static$0() {
        return new byte[8];
    }

    private <T> T useFileChannel(OpenChannelsCache.ChannelProcessor<T> channelProcessor) throws IOException {
        if (channelProcessor == null) {
            $$$reportNull$$$0(3);
        }
        return (T) ourCache.useChannel(this.myFile, channelProcessor, false);
    }

    public long physicalLength() {
        assertNotDisposed();
        try {
            return ((Long) useFileChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.RandomAccessDataFile$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
                public final Object process(FileChannel fileChannel) {
                    return Long.valueOf(fileChannel.size());
                }
            })).longValue();
        } catch (IOException unused) {
            return 0L;
        }
    }

    public void dispose() {
        if (this.myIsDisposed) {
            return;
        }
        this.myPool.flushPages(this);
        try {
            ourCache.closeChannel(this.myFile);
            this.myIsDisposed = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        dispose();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() {
        assertNotDisposed();
        if (isDirty()) {
            this.myPool.flushPages(this);
            this.myIsDirty = false;
        }
    }

    public boolean isDirty() {
        assertNotDisposed();
        return this.myIsDirty;
    }

    private void assertNotDisposed() {
        if (this.myIsDisposed) {
            LOG.error("storage file is disposed: " + this.myFile);
        }
    }

    void loadPage(final Page page) {
        assertNotDisposed();
        try {
            final ByteBuffer buf = page.getBuf();
            useFileChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.RandomAccessDataFile$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
                public final Object process(FileChannel fileChannel) {
                    return Integer.valueOf(fileChannel.read(ByteBuffer.wrap(buf.array(), 0, Page.PAGE_SIZE), page.getOffset()));
                }
            });
            totalReads++;
            totalReadBytes += (long) Page.PAGE_SIZE;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void flushPage(Page page, int i, int i2) {
        assertNotDisposed();
        try {
            flush(page.getBuf(), page.getOffset() + ((long) i), i, i2 - i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void flush(final ByteBuffer byteBuffer, final long j, final int i, int i2) throws IOException {
        if (((long) i2) + j > this.mySize) {
            i2 = (int) (this.mySize - j);
        }
        final int i3 = i2;
        useFileChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.RandomAccessDataFile$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
            public final Object process(FileChannel fileChannel) {
                return this.f$0.m6731xd809bf15(byteBuffer, i, i3, j, fileChannel);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$flush$3$org-jetbrains-kotlin-com-intellij-util-io-RandomAccessDataFile, reason: not valid java name */
    /* synthetic */ Integer m6731xd809bf15(ByteBuffer byteBuffer, int i, int i2, long j, FileChannel fileChannel) throws IOException {
        int iWrite = fileChannel.write(ByteBuffer.wrap(byteBuffer.array(), i, i2), j);
        totalWrites++;
        totalWriteBytes += (long) i2;
        return Integer.valueOf(iWrite);
    }

    public int hashCode() {
        return this.myCount;
    }

    public String toString() {
        return "RandomAccessFile[" + this.myFile + ", dirty=" + this.myIsDirty + "]";
    }
}
