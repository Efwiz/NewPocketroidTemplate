package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;

/* JADX INFO: loaded from: classes3.dex */
public final class DirectBufferWrapper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ByteOrder ourNativeByteOrder = ByteOrder.nativeOrder();
    private volatile ByteBuffer myBuffer;
    private volatile boolean myDirty;
    private final PagedFileStorage myFile;
    private final int myLength;
    private final long myPosition;
    private final boolean myReadOnly;
    private volatile boolean myReleased;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/util/io/DirectBufferWrapper", "<init>"));
    }

    DirectBufferWrapper(PagedFileStorage pagedFileStorage, long j, int i, boolean z) throws IOException {
        if (pagedFileStorage == null) {
            $$$reportNull$$$0(0);
        }
        this.myReleased = false;
        this.myFile = pagedFileStorage;
        this.myPosition = j;
        this.myLength = i;
        this.myReadOnly = z;
        this.myBuffer = DirectByteBufferAllocator.allocate(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.DirectBufferWrapper$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6718xbd88abb();
            }
        });
    }

    private void markDirty() throws IOException {
        if (this.myReadOnly) {
            throw new IOException("Read-only byte buffer can't be modified. File: " + this.myFile);
        }
        if (this.myDirty) {
            return;
        }
        this.myDirty = true;
        this.myFile.markDirty();
    }

    boolean isDirty() {
        return this.myDirty;
    }

    public ByteBuffer copy() {
        try {
            return DirectByteBufferAllocator.allocate(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.DirectBufferWrapper$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return this.f$0.m6715x8703e5eb();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: lambda$copy$1$org-jetbrains-kotlin-com-intellij-util-io-DirectBufferWrapper, reason: not valid java name */
    /* synthetic */ ByteBuffer m6715x8703e5eb() throws IOException {
        ByteBuffer byteBufferDuplicate = this.myBuffer.duplicate();
        byteBufferDuplicate.order(this.myBuffer.order());
        return byteBufferDuplicate;
    }

    public byte get(int i) {
        return this.myBuffer.get(i);
    }

    public long getLong(int i) {
        return this.myBuffer.getLong(i);
    }

    public ByteBuffer putLong(int i, long j) throws IOException {
        markDirty();
        return this.myBuffer.putLong(i, j);
    }

    public int getInt(int i) {
        return this.myBuffer.getInt(i);
    }

    public ByteBuffer putInt(int i, int i2) throws IOException {
        markDirty();
        return this.myBuffer.putInt(i, i2);
    }

    public void position(int i) {
        this.myBuffer.position(i);
    }

    public void put(ByteBuffer byteBuffer) throws IOException {
        markDirty();
        this.myBuffer.put(byteBuffer);
    }

    public void readToArray(byte[] bArr, int i, int i2, int i3) throws IllegalArgumentException {
        synchronized (this.myBuffer) {
            this.myBuffer.position(i2);
            this.myBuffer.get(bArr, i, i3);
        }
    }

    public void putFromArray(byte[] bArr, int i, int i2, int i3) throws IOException, IllegalArgumentException {
        markDirty();
        synchronized (this.myBuffer) {
            this.myBuffer.position(i2);
            this.myBuffer.put(bArr, i, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: create, reason: merged with bridge method [inline-methods] */
    public ByteBuffer m6718xbd88abb() throws IOException {
        final ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(this.myLength);
        return (ByteBuffer) this.myFile.useChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.DirectBufferWrapper$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
            public final Object process(FileChannel fileChannel) {
                return this.f$0.m6716xa09fe873(byteBufferAllocateDirect, fileChannel);
            }
        }, this.myReadOnly);
    }

    /* JADX INFO: renamed from: lambda$create$2$org-jetbrains-kotlin-com-intellij-util-io-DirectBufferWrapper, reason: not valid java name */
    /* synthetic */ ByteBuffer m6716xa09fe873(ByteBuffer byteBuffer, FileChannel fileChannel) throws IOException {
        fileChannel.read(byteBuffer, this.myPosition);
        return byteBuffer;
    }

    void release() throws IOException {
        if (isDirty()) {
            force();
        }
        if (this.myBuffer != null) {
            ByteBufferUtil.cleanBuffer(this.myBuffer);
            this.myBuffer = null;
            this.myReleased = true;
        }
    }

    boolean isReleased() {
        return this.myReleased;
    }

    void force() throws IOException {
        if (isReleased() || !isDirty()) {
            return;
        }
        final ByteBuffer byteBuffer = this.myBuffer;
        byteBuffer.rewind();
        this.myFile.useChannel(new OpenChannelsCache.ChannelProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.DirectBufferWrapper$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache.ChannelProcessor
            public final Object process(FileChannel fileChannel) {
                return this.f$0.m6717x3ce64409(byteBuffer, fileChannel);
            }
        }, this.myReadOnly);
        this.myDirty = false;
    }

    /* JADX INFO: renamed from: lambda$force$3$org-jetbrains-kotlin-com-intellij-util-io-DirectBufferWrapper, reason: not valid java name */
    /* synthetic */ Object m6717x3ce64409(ByteBuffer byteBuffer, FileChannel fileChannel) throws IOException {
        fileChannel.write(byteBuffer, this.myPosition);
        return null;
    }

    int getLength() {
        return this.myLength;
    }

    public String toString() {
        return "Buffer for " + this.myFile + ", offset:" + this.myPosition + ", size: " + this.myLength;
    }

    public void useNativeByteOrder() {
        ByteOrder byteOrderOrder = this.myBuffer.order();
        ByteOrder byteOrder = ourNativeByteOrder;
        if (byteOrderOrder != byteOrder) {
            this.myBuffer.order(byteOrder);
        }
    }

    public static DirectBufferWrapper readWriteDirect(PagedFileStorage pagedFileStorage, long j, int i) throws IOException {
        return new DirectBufferWrapper(pagedFileStorage, j, i, false);
    }

    public static DirectBufferWrapper readOnlyDirect(PagedFileStorage pagedFileStorage, long j, int i) throws IOException {
        return new DirectBufferWrapper(pagedFileStorage, j, i, true);
    }
}
