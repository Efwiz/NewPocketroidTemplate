package org.eclipse.jgit.internal.storage.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes13.dex */
public abstract class BlockSource implements AutoCloseable {
    public void adviseSequentialRead(long j, long j2) {
    }

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract ByteBuffer read(long j, int i) throws IOException;

    public abstract long size() throws IOException;

    public static BlockSource from(final byte[] bArr) {
        return new BlockSource() { // from class: org.eclipse.jgit.internal.storage.io.BlockSource.1
            @Override // org.eclipse.jgit.internal.storage.io.BlockSource, java.lang.AutoCloseable
            public void close() {
            }

            @Override // org.eclipse.jgit.internal.storage.io.BlockSource
            public ByteBuffer read(long j, int i) {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
                byte[] bArr2 = bArr;
                if (j < bArr2.length) {
                    int i2 = (int) j;
                    byteBufferAllocate.put(bArr, i2, Math.min(i, bArr2.length - i2));
                }
                return byteBufferAllocate;
            }

            @Override // org.eclipse.jgit.internal.storage.io.BlockSource
            public long size() {
                return bArr.length;
            }
        };
    }

    public static BlockSource from(FileInputStream fileInputStream) {
        return from(fileInputStream.getChannel());
    }

    public static BlockSource from(final FileChannel fileChannel) {
        return new BlockSource() { // from class: org.eclipse.jgit.internal.storage.io.BlockSource.2
            @Override // org.eclipse.jgit.internal.storage.io.BlockSource
            public ByteBuffer read(long j, int i) throws IOException {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
                fileChannel.position(j);
                while (fileChannel.read(byteBufferAllocate) > 0 && byteBufferAllocate.position() < i) {
                }
                return byteBufferAllocate;
            }

            @Override // org.eclipse.jgit.internal.storage.io.BlockSource
            public long size() throws IOException {
                return fileChannel.size();
            }

            @Override // org.eclipse.jgit.internal.storage.io.BlockSource, java.lang.AutoCloseable
            public void close() {
                try {
                    fileChannel.close();
                } catch (IOException unused) {
                }
            }
        };
    }
}
