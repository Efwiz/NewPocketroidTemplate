package org.jetbrains.kotlin.resolve.calls.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.psi.KtLambdaExpression;

/* JADX INFO: compiled from: builderInferenceUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/util/BuilderLambdaLabelingInfo;", "", "builderLambda", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "(Lorg/jetbrains/kotlin/psi/KtLambdaExpression;)V", "getBuilderLambda", "()Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "toString", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderLambdaLabelingInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final BuilderLambdaLabelingInfo EMPTY = new BuilderLambdaLabelingInfo(null);
    private final KtLambdaExpression builderLambda;

    /* JADX INFO: compiled from: builderInferenceUtil.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/util/BuilderLambdaLabelingInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/calls/util/BuilderLambdaLabelingInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/calls/util/BuilderLambdaLabelingInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BuilderLambdaLabelingInfo getEMPTY() {
            return BuilderLambdaLabelingInfo.EMPTY;
        }
    }

    public BuilderLambdaLabelingInfo(KtLambdaExpression ktLambdaExpression) {
        this.builderLambda = ktLambdaExpression;
    }

    public final KtLambdaExpression getBuilderLambda() {
        return this.builderLambda;
    }

    public String toString() {
        return String.valueOf(this.builderLambda);
    }
}
