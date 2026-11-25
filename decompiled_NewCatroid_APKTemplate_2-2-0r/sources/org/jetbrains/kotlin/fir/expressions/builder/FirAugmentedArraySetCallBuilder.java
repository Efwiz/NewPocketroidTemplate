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
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.impl.FirAugmentedArraySetCallImpl;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.impl.FirStubReference;

/* JADX INFO: compiled from: FirAugmentedArraySetCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010)\u001a\u00020*H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u000b\"\u0004\b(\u0010\r¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAugmentedArraySetCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "arrayAccessSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getArrayAccessSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setArrayAccessSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "lhsGetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "getLhsGetCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setLhsGetCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "setOperation", "(Lorg/jetbrains/kotlin/fir/expressions/FirOperation;)V", "rhs", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getRhs", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setRhs", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", FirebaseAnalytics.Param.SOURCE, "getSource", "setSource", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAugmentedArraySetCallBuilder implements FirAnnotationContainerBuilder {
    private KtSourceElement arrayAccessSource;
    public FirFunctionCall lhsGetCall;
    public FirOperation operation;
    public FirExpression rhs;
    private KtSourceElement source;
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirReference calleeReference = FirStubReference.INSTANCE;

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

    public final FirFunctionCall getLhsGetCall() {
        FirFunctionCall firFunctionCall = this.lhsGetCall;
        if (firFunctionCall != null) {
            return firFunctionCall;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lhsGetCall");
        return null;
    }

    public final void setLhsGetCall(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<set-?>");
        this.lhsGetCall = firFunctionCall;
    }

    public final FirExpression getRhs() {
        FirExpression firExpression = this.rhs;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rhs");
        return null;
    }

    public final void setRhs(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rhs = firExpression;
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

    public final FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public final void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    public final KtSourceElement getArrayAccessSource() {
        return this.arrayAccessSource;
    }

    public final void setArrayAccessSource(KtSourceElement ktSourceElement) {
        this.arrayAccessSource = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirAugmentedArraySetCall build() {
        return new FirAugmentedArraySetCallImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getLhsGetCall(), getRhs(), getOperation(), this.calleeReference, this.arrayAccessSource, null);
    }
}
