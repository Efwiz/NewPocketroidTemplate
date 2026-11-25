package org.jetbrains.kotlin.incremental;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/NonRecoverableCompilationTransaction;", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "Lorg/jetbrains/kotlin/incremental/BaseCompilationTransaction;", "()V", "close", "", "deleteFile", "outputFile", "Ljava/nio/file/Path;", "registerAddedOrChangedFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NonRecoverableCompilationTransaction extends BaseCompilationTransaction implements CompilationTransaction {
    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void registerAddedOrChangedFile(Path outputFile) {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void deleteFile(Path outputFile) throws IOException {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        if (Files.exists(outputFile, new LinkOption[0])) {
            Files.delete(outputFile);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        checkForExecutionException();
        Throwable thCloseCachesManager = closeCachesManager();
        if (thCloseCachesManager != null) {
            throw thCloseCachesManager;
        }
    }
}
