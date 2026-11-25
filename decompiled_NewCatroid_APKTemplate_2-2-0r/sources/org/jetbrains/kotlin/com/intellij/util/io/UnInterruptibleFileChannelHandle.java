package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
final class UnInterruptibleFileChannelHandle {
    private static final Logger LOG = Logger.getInstance((Class<?>) UnInterruptibleFileChannelHandle.class);
    private volatile FileChannel myChannel;
    private final Lock myOpenCloseLock;
    private final OpenOption[] myOpenOptions;
    private final Path myPath;

    interface FileChannelIdempotentOperation<T> {
        T execute(FileChannel fileChannel) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "openOptions";
        } else if (i != 2) {
            objArr[0] = "path";
        } else {
            objArr[0] = "operation";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/UnInterruptibleFileChannelHandle";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "executeOperation";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    UnInterruptibleFileChannelHandle(Path path, OpenOption... openOptionArr) throws Throwable {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (openOptionArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myOpenCloseLock = new ReentrantLock();
        this.myPath = path;
        this.myOpenOptions = openOptionArr;
        reopenChannel();
    }

    <T> T executeOperation(FileChannelIdempotentOperation<T> fileChannelIdempotentOperation) throws IOException {
        T tExecute;
        if (fileChannelIdempotentOperation == null) {
            $$$reportNull$$$0(2);
        }
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        boolean z = false;
        while (true) {
            try {
                try {
                    tExecute = fileChannelIdempotentOperation.execute(this.myChannel);
                    break;
                } catch (ClosedChannelException unused) {
                    LOG.warn("Channel " + System.identityHashCode(this.myChannel) + " for " + this.myPath + " closed. Trying to reopen it again");
                    if (Thread.currentThread().isInterrupted()) {
                        Thread.interrupted();
                        z = true;
                    }
                    reopenChannel();
                }
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return tExecute;
    }

    boolean isOpen() {
        return this.myChannel != null;
    }

    void close() throws Throwable {
        ConcurrencyUtil.withLock(this.myOpenCloseLock, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws IOException {
                this.f$0.m6735xb868a44a();
            }
        });
    }

    void reopenChannel() throws Throwable {
        ConcurrencyUtil.withLock(this.myOpenCloseLock, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws IOException {
                this.f$0.m6736x3119161b();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reopenChannel$1$org-jetbrains-kotlin-com-intellij-util-io-UnInterruptibleFileChannelHandle, reason: not valid java name */
    /* synthetic */ void m6736x3119161b() throws IOException {
        FileChannel fileChannelOpen = FileChannel.open(this.myPath, this.myOpenOptions);
        try {
            m6735xb868a44a();
        } catch (IOException unused) {
        }
        this.myChannel = fileChannelOpen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: tryClose, reason: merged with bridge method [inline-methods] */
    public void m6735xb868a44a() throws IOException {
        try {
            FileChannel fileChannel = this.myChannel;
            if (fileChannel != null && fileChannel.isOpen()) {
                fileChannel.close();
            }
        } finally {
            this.myChannel = null;
        }
    }
}
