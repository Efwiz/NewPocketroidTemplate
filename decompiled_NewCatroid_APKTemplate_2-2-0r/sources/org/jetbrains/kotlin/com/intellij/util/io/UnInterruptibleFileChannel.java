package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/* JADX INFO: loaded from: classes3.dex */
public final class UnInterruptibleFileChannel extends FileChannel {
    private final UnInterruptibleFileChannelHandle myFileChannelHandle;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "path";
        } else {
            objArr[0] = "openOptions";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/UnInterruptibleFileChannel";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public UnInterruptibleFileChannel(Path path, OpenOption... openOptionArr) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (openOptionArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myFileChannelHandle = new UnInterruptibleFileChannelHandle(path, openOptionArr);
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.ReadableByteChannel
    public int read(final ByteBuffer byteBuffer) throws IOException {
        return ((Integer) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda9
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.read(byteBuffer));
            }
        })).intValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.ScatteringByteChannel
    public long read(final ByteBuffer[] byteBufferArr, final int i, final int i2) throws IOException {
        return ((Long) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Long.valueOf(fileChannel.read(byteBufferArr, i, i2));
            }
        })).longValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.WritableByteChannel
    public int write(final ByteBuffer byteBuffer) throws IOException {
        return ((Integer) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.write(byteBuffer));
            }
        })).intValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.GatheringByteChannel
    public long write(final ByteBuffer[] byteBufferArr, final int i, final int i2) throws IOException {
        return ((Long) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Long.valueOf(fileChannel.write(byteBufferArr, i, i2));
            }
        })).longValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
    public long position() throws IOException {
        return ((Long) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Long.valueOf(fileChannel.position());
            }
        })).longValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
    public FileChannel position(final long j) throws IOException {
        return (FileChannel) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return fileChannel.position(j);
            }
        });
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
    public long size() throws IOException {
        return ((Long) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Long.valueOf(fileChannel.size());
            }
        })).longValue();
    }

    @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
    public FileChannel truncate(final long j) throws IOException {
        return (FileChannel) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda10
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return fileChannel.truncate(j);
            }
        });
    }

    @Override // java.nio.channels.FileChannel
    public void force(final boolean z) throws IOException {
        this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return UnInterruptibleFileChannel.lambda$force$8(z, fileChannel);
            }
        });
    }

    static /* synthetic */ Object lambda$force$8(boolean z, FileChannel fileChannel) throws IOException {
        fileChannel.force(z);
        return null;
    }

    @Override // java.nio.channels.FileChannel
    public long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.channels.FileChannel
    public long transferFrom(ReadableByteChannel readableByteChannel, long j, long j2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.channels.FileChannel
    public int read(final ByteBuffer byteBuffer, final long j) throws IOException {
        return ((Integer) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.read(byteBuffer, j));
            }
        })).intValue();
    }

    @Override // java.nio.channels.FileChannel
    public int write(final ByteBuffer byteBuffer, final long j) throws IOException {
        return ((Integer) this.myFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannel$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.write(byteBuffer, j));
            }
        })).intValue();
    }

    @Override // java.nio.channels.FileChannel
    public MappedByteBuffer map(FileChannel.MapMode mapMode, long j, long j2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.channels.FileChannel
    public FileLock lock(long j, long j2, boolean z) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.channels.FileChannel
    public FileLock tryLock(long j, long j2, boolean z) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.channels.spi.AbstractInterruptibleChannel
    protected void implCloseChannel() throws Throwable {
        this.myFileChannelHandle.close();
    }
}
