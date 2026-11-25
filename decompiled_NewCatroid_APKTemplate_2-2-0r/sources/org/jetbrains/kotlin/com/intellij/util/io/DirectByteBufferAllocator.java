package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;

/* JADX INFO: loaded from: classes3.dex */
final class DirectByteBufferAllocator {
    private static final ExecutorService ourAllocator;

    static {
        ourAllocator = (SystemInfoRt.isLinux && Boolean.parseBoolean(System.getProperty("idea.limit.paged.storage.allocators", "true"))) ? ConcurrencyUtil.newSingleThreadExecutor("DirectBufferWrapper allocation thread") : null;
    }

    static ByteBuffer allocate(final ThrowableComputable<? extends ByteBuffer, ? extends IOException> throwableComputable) throws IOException {
        ExecutorService executorService = ourAllocator;
        if (executorService != null) {
            try {
                Objects.requireNonNull(throwableComputable);
                return (ByteBuffer) executorService.submit(new Callable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.DirectByteBufferAllocator$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return (ByteBuffer) throwableComputable.compute();
                    }
                }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                if (cause instanceof IOException) {
                    throw ((IOException) cause);
                }
                if (cause instanceof OutOfMemoryError) {
                    throw ((OutOfMemoryError) cause);
                }
                throw new RuntimeException(e2);
            }
        }
        return throwableComputable.compute();
    }
}
