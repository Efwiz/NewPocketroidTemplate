package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerformanceCounter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000 \n2\u00020\u0001:\u0001\nB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0014¢\u0006\u0002\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/ReenterableCounter;", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "name", "", "(Ljava/lang/String;)V", "countTime", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Companion", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ReenterableCounter extends PerformanceCounter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ThreadLocal<Set<ReenterableCounter>> enteredCounters = new ThreadLocal<>();

    /* JADX INFO: compiled from: PerformanceCounter.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0006H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/util/ReenterableCounter$Companion;", "", "()V", "enteredCounters", "Ljava/lang/ThreadLocal;", "", "Lorg/jetbrains/kotlin/util/ReenterableCounter;", "enterCounter", "", "counter", "leaveCounter", "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean enterCounter(ReenterableCounter counter) {
            PerformanceCounter.Companion companion = PerformanceCounter.INSTANCE;
            ThreadLocal threadLocal = ReenterableCounter.enteredCounters;
            Object obj = threadLocal.get();
            if (obj == null) {
                obj = (Set) new HashSet();
                threadLocal.set(obj);
            }
            return ((Set) obj).add(counter);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void leaveCounter(ReenterableCounter counter) {
            Set set = (Set) ReenterableCounter.enteredCounters.get();
            if (set != null) {
                set.remove(counter);
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReenterableCounter(String name2) {
        super(name2);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.util.PerformanceCounter
    protected <T> T countTime(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long jCurrentTime = PerformanceCounter.INSTANCE.currentTime();
        boolean zEnterCounter = INSTANCE.enterCounter(this);
        try {
            T tInvoke = block.invoke();
            if (zEnterCounter) {
                incrementTime(PerformanceCounter.INSTANCE.currentTime() - jCurrentTime);
            }
            return tInvoke;
        } finally {
            if (zEnterCounter) {
                incrementTime(PerformanceCounter.INSTANCE.currentTime() - jCurrentTime);
                INSTANCE.leaveCounter(this);
            }
        }
    }
}
