package org.jetbrains.kotlin.fir.expressions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;

/* JADX INFO: compiled from: ExhaustivenessStatus.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "", "()V", "ExhaustiveAsNothing", "NotExhaustive", "ProperlyExhaustive", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$ExhaustiveAsNothing;", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$NotExhaustive;", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$ProperlyExhaustive;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExhaustivenessStatus {
    public /* synthetic */ ExhaustivenessStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ExhaustivenessStatus() {
    }

    /* JADX INFO: compiled from: ExhaustivenessStatus.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$ProperlyExhaustive;", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProperlyExhaustive extends ExhaustivenessStatus {
        public static final ProperlyExhaustive INSTANCE = new ProperlyExhaustive();

        private ProperlyExhaustive() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ExhaustivenessStatus.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$ExhaustiveAsNothing;", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExhaustiveAsNothing extends ExhaustivenessStatus {
        public static final ExhaustiveAsNothing INSTANCE = new ExhaustiveAsNothing();

        private ExhaustiveAsNothing() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ExhaustivenessStatus.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$NotExhaustive;", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "reasons", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "(Ljava/util/List;)V", "getReasons", "()Ljava/util/List;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotExhaustive extends ExhaustivenessStatus {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final NotExhaustive NO_ELSE_BRANCH = new NotExhaustive(CollectionsKt.listOf(WhenMissingCase.Unknown.INSTANCE));
        private final List<WhenMissingCase> reasons;

        /* JADX INFO: compiled from: ExhaustivenessStatus.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$NotExhaustive$Companion;", "", "()V", "NO_ELSE_BRANCH", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$NotExhaustive;", "getNO_ELSE_BRANCH", "()Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus$NotExhaustive;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final NotExhaustive getNO_ELSE_BRANCH() {
                return NotExhaustive.NO_ELSE_BRANCH;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public NotExhaustive(List<? extends WhenMissingCase> reasons) {
            super(null);
            Intrinsics.checkNotNullParameter(reasons, "reasons");
            this.reasons = reasons;
        }

        public final List<WhenMissingCase> getReasons() {
            return this.reasons;
        }
    }
}
