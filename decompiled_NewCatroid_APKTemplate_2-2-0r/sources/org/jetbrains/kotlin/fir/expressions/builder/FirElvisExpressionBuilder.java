package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirElvisExpressionImpl;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.impl.FirStubReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirElvisExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010&\u001a\u00020'H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR*\u0010 \u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001f8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b!\u0010\u0003\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirElvisExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "lhs", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getLhs", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setLhs", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "rhs", "getRhs", "setRhs", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeRef", "getTypeRef$annotations", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirElvisExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirReference calleeReference = FirStubReference.INSTANCE;
    public FirExpression lhs;
    public FirExpression rhs;
    private KtSourceElement source;

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirElvisExpressionBuilder")
    public static /* synthetic */ void getTypeRef$annotations() {
    }

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

    public final FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public final void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    public final FirExpression getLhs() {
        FirExpression firExpression = this.lhs;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lhs");
        return null;
    }

    public final void setLhs(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.lhs = firExpression;
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

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirElvisExpression build() {
        return new FirElvisExpressionImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), this.calleeReference, getLhs(), getRhs(), null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ FirTypeRef getTypeRef() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }
}
