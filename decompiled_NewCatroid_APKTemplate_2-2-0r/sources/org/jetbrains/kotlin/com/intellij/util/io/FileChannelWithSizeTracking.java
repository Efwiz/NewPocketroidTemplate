package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;

/* JADX INFO: loaded from: classes3.dex */
final class FileChannelWithSizeTracking {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) FileChannelWithSizeTracking.class);
    private static final boolean doAssertions = SystemProperties.getBooleanProperty("idea.do.random.access.wrapper.assertions", false);
    private final UnInterruptibleFileChannelHandle myChannelHandle;
    private final Path myPath;
    private volatile long mySize;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "path", "org/jetbrains/kotlin/com/intellij/util/io/FileChannelWithSizeTracking", "<init>"));
    }

    FileChannelWithSizeTracking(Path path) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        Path parent = path.getParent();
        if (!Files.exists(parent, new LinkOption[0])) {
            Files.createDirectories(parent, new FileAttribute[0]);
        }
        UnInterruptibleFileChannelHandle unInterruptibleFileChannelHandle = new UnInterruptibleFileChannelHandle(path, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        this.myChannelHandle = unInterruptibleFileChannelHandle;
        this.mySize = ((Long) unInterruptibleFileChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.FileChannelWithSizeTracking$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Long.valueOf(fileChannel.size());
            }
        })).longValue();
        this.myPath = path;
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("Inst:" + this + "," + Thread.currentThread() + "," + getClass().getClassLoader());
        }
    }

    long length() throws IOException {
        return this.mySize;
    }

    void write(final long j, final byte[] bArr, final int i, final int i2) throws IOException {
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("write:" + this + "," + Thread.currentThread() + "," + i2 + "," + j);
        }
        this.mySize = Math.max(((long) ((Integer) this.myChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.FileChannelWithSizeTracking$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.write(ByteBuffer.wrap(bArr, i, i2), j));
            }
        })).intValue()) + j, length());
    }

    void read(final long j, final byte[] bArr, final int i, final int i2) throws IOException {
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("read:" + this + "," + Thread.currentThread() + "," + i2 + "," + j);
        }
        this.myChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.FileChannelWithSizeTracking$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return Integer.valueOf(fileChannel.read(ByteBuffer.wrap(bArr, i, i2), j));
            }
        });
    }

    void close() throws Throwable {
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("Closed:" + this + "," + Thread.currentThread());
        }
        force();
        this.myChannelHandle.close();
    }

    public String toString() {
        return this.myPath + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + Integer.toHexString(hashCode());
    }

    private void force() throws IOException {
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("Forcing:" + this + "," + Thread.currentThread());
        }
        this.myChannelHandle.executeOperation(new UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.FileChannelWithSizeTracking$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
            public final Object execute(FileChannel fileChannel) {
                return FileChannelWithSizeTracking.lambda$force$4(fileChannel);
            }
        });
    }

    static /* synthetic */ Object lambda$force$4(FileChannel fileChannel) throws IOException {
        fileChannel.force(true);
        return null;
    }
}
