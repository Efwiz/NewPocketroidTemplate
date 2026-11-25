package org.jetbrains.kotlin.constant;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: EvaluatedConstTracker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J.\u0010\u0003\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\tH&J,\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u0004H&¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "", "()V", "load", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "start", "", "end", "file", "", "", "Lkotlin/Pair;", "save", "", "constant", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class EvaluatedConstTracker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract Map<Pair<Integer, Integer>, ConstantValue<?>> load(String file);

    public abstract ConstantValue<?> load(int start, int end, String file);

    public abstract void save(int start, int end, String file, ConstantValue<?> constant);

    /* JADX INFO: compiled from: EvaluatedConstTracker.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EvaluatedConstTracker create() {
            return new DefaultEvaluatedConstTracker();
        }
    }
}
