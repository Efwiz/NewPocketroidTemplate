package org.jetbrains.kotlin.storage;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: locks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/storage/SimpleLock;", "", "lock", "", "unlock", "Companion", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SimpleLock {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void lock();

    void unlock();

    /* JADX INFO: compiled from: locks.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/storage/SimpleLock$Companion;", "", "()V", "simpleLock", "Lorg/jetbrains/kotlin/storage/DefaultSimpleLock;", "checkCancelled", "Ljava/lang/Runnable;", "interruptedExceptionHandler", "Lkotlin/Function1;", "Ljava/lang/InterruptedException;", "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DefaultSimpleLock simpleLock$default(Companion companion, Runnable runnable, Function1 function1, int i, Object obj) {
            if ((i & 1) != 0) {
                runnable = null;
            }
            if ((i & 2) != 0) {
                function1 = null;
            }
            return companion.simpleLock(runnable, function1);
        }

        public final DefaultSimpleLock simpleLock(Runnable checkCancelled, Function1<? super InterruptedException, Unit> interruptedExceptionHandler) {
            if (checkCancelled != null && interruptedExceptionHandler != null) {
                return new CancellableSimpleLock(checkCancelled, interruptedExceptionHandler);
            }
            return new DefaultSimpleLock(null, 1, null);
        }
    }
}
