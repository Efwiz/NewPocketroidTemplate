package org.jetbrains.kotlin.incremental;

import java.io.Closeable;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.storage.InMemoryStorageWrapper;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\n\u0010\u001b\u001a\u0004\u0018\u00010\nH\u0004J\b\u0010\u001c\u001a\u00020\u001aH\u0016J\u0018\u0010\u001d\u001a\u00020\u001a2\u000e\u0010\u001e\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0012H\u0016R(\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR(\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\n@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR.\u0010\u0010\u001a\"\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00120\u0011j\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0015X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BaseCompilationTransaction;", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "()V", "value", "Ljava/io/Closeable;", "cachesManager", "getCachesManager", "()Ljava/io/Closeable;", "setCachesManager", "(Ljava/io/Closeable;)V", "", "executionThrowable", "getExecutionThrowable", "()Ljava/lang/Throwable;", "setExecutionThrowable", "(Ljava/lang/Throwable;)V", "inMemoryStorageWrappers", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/incremental/storage/InMemoryStorageWrapper;", "Lkotlin/collections/HashSet;", "isSuccessful", "", "()Z", "setSuccessful", "(Z)V", "checkForExecutionException", "", "closeCachesManager", "markAsSuccessful", "registerInMemoryStorageWrapper", "inMemoryStorageWrapper", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseCompilationTransaction implements CompilationTransaction {
    private Closeable cachesManager;
    private Throwable executionThrowable;
    private final HashSet<InMemoryStorageWrapper<?, ?>> inMemoryStorageWrappers = new HashSet<>();
    private boolean isSuccessful;

    /* JADX INFO: renamed from: isSuccessful, reason: from getter */
    protected final boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    protected final void setSuccessful(boolean z) {
        this.isSuccessful = z;
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void markAsSuccessful() {
        this.isSuccessful = true;
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void registerInMemoryStorageWrapper(InMemoryStorageWrapper<?, ?> inMemoryStorageWrapper) {
        Intrinsics.checkNotNullParameter(inMemoryStorageWrapper, "inMemoryStorageWrapper");
        this.inMemoryStorageWrappers.add(inMemoryStorageWrapper);
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public Closeable getCachesManager() {
        return this.cachesManager;
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void setCachesManager(Closeable closeable) {
        if (this.cachesManager != null) {
            throw new IllegalStateException("cachesManager is already set".toString());
        }
        this.cachesManager = closeable;
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public Throwable getExecutionThrowable() {
        return this.executionThrowable;
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void setExecutionThrowable(Throwable th) {
        if (this.executionThrowable != null) {
            throw new IllegalStateException("executionThrowable is already set".toString());
        }
        this.executionThrowable = th;
    }

    protected final Throwable closeCachesManager() {
        Object objM2898constructorimpl;
        Unit unit;
        try {
            Result.Companion companion = Result.INSTANCE;
            BaseCompilationTransaction baseCompilationTransaction = this;
            if (!this.isSuccessful) {
                Iterator<InMemoryStorageWrapper<?, ?>> it = this.inMemoryStorageWrappers.iterator();
                while (it.hasNext()) {
                    it.next().resetInMemoryChanges();
                }
            }
            Closeable cachesManager = getCachesManager();
            if (cachesManager != null) {
                cachesManager.close();
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            objM2898constructorimpl = Result.m2898constructorimpl(unit);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(objM2898constructorimpl);
        if (thM2901exceptionOrNullimpl == null) {
            return null;
        }
        this.isSuccessful = false;
        CachesManagerCloseException cachesManagerCloseException = new CachesManagerCloseException(thM2901exceptionOrNullimpl);
        Throwable executionThrowable = getExecutionThrowable();
        if (executionThrowable != null) {
            ExceptionsKt.addSuppressed(executionThrowable, cachesManagerCloseException);
        }
        Throwable executionThrowable2 = getExecutionThrowable();
        return executionThrowable2 == null ? cachesManagerCloseException : executionThrowable2;
    }

    protected final void checkForExecutionException() {
        if (getExecutionThrowable() != null) {
            this.isSuccessful = false;
        }
    }
}
