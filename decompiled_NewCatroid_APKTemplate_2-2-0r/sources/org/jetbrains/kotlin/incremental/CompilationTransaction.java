package org.jetbrains.kotlin.incremental;

import java.io.Closeable;
import java.nio.file.Path;
import kotlin.Metadata;
import org.jetbrains.kotlin.incremental.storage.InMemoryStorageWrapper;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u000eH&J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0018\u0010\u0013\u001a\u00020\u000e2\u000e\u0010\u0014\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0015H&R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "Ljava/io/Closeable;", "cachesManager", "getCachesManager", "()Ljava/io/Closeable;", "setCachesManager", "(Ljava/io/Closeable;)V", "executionThrowable", "", "getExecutionThrowable", "()Ljava/lang/Throwable;", "setExecutionThrowable", "(Ljava/lang/Throwable;)V", "deleteFile", "", "outputFile", "Ljava/nio/file/Path;", "markAsSuccessful", "registerAddedOrChangedFile", "registerInMemoryStorageWrapper", "inMemoryStorageWrapper", "Lorg/jetbrains/kotlin/incremental/storage/InMemoryStorageWrapper;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompilationTransaction extends Closeable {
    void deleteFile(Path outputFile);

    Closeable getCachesManager();

    Throwable getExecutionThrowable();

    void markAsSuccessful();

    void registerAddedOrChangedFile(Path outputFile);

    void registerInMemoryStorageWrapper(InMemoryStorageWrapper<?, ?> inMemoryStorageWrapper);

    void setCachesManager(Closeable closeable);

    void setExecutionThrowable(Throwable th);
}
