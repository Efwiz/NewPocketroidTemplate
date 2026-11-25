package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.impl.FirAssignmentOperatorStatementImpl;

/* JADX INFO: compiled from: FirAssignmentOperatorStatementBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\rR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAssignmentOperatorStatementBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "leftArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getLeftArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setLeftArgument", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "setOperation", "(Lorg/jetbrains/kotlin/fir/expressions/FirOperation;)V", "rightArgument", "getRightArgument", "setRightArgument", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAssignmentOperatorStatementBuilder implements FirAnnotationContainerBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    public FirExpression leftArgument;
    public FirOperation operation;
    public FirExpression rightArgument;
    private KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final FirOperation getOperation() {
        FirOperation firOperation = this.operation;
        if (firOperation != null) {
            return firOperation;
        }
        Intrinsics.throwUninitializedPropertyAccessException("operation");
        return null;
    }

    public final void setOperation(FirOperation firOperation) {
        Intrinsics.checkNotNullParameter(firOperation, "<set-?>");
        this.operation = firOperation;
    }

    public final FirExpression getLeftArgument() {
        FirExpression firExpression = this.leftArgument;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("leftArgument");
        return null;
    }

    public final void setLeftArgument(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.leftArgument = firExpression;
    }

    public final FirExpression getRightArgument() {
        FirExpression firExpression = this.rightArgument;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rightArgument");
        return null;
    }

    public final void setRightArgument(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rightArgument = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirAssignmentOperatorStatement build() {
        return new FirAssignmentOperatorStatementImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getOperation(), getLeftArgument(), getRightArgument(), null);
    }
}
