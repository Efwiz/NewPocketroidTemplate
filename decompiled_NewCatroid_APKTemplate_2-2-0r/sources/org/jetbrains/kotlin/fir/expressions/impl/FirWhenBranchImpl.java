package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirWhenBranchImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u0013\u001a\u00020\u0014\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001aJ)\u0010\u001b\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00162\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00160\u001d2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00162\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00160\u001d2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001eJ)\u0010 \u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00162\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00160\u001d2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001eJ)\u0010!\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00162\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00160\u001d2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirWhenBranchImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "result", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "getCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setCondition", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getResult", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setResult", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirWhenBranchImpl;", "transformCondition", "transformOtherChildren", "transformResult", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenBranchImpl extends FirWhenBranch {
    private FirExpression condition;
    private FirBlock result;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public <D> FirWhenBranchImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public /* bridge */ /* synthetic */ FirWhenBranch transformCondition(FirTransformer firTransformer, Object obj) {
        return transformCondition((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public /* bridge */ /* synthetic */ FirWhenBranch transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public /* bridge */ /* synthetic */ FirWhenBranch transformResult(FirTransformer firTransformer, Object obj) {
        return transformResult((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public FirExpression getCondition() {
        return this.condition;
    }

    public void setCondition(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.condition = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public FirBlock getResult() {
        return this.result;
    }

    public void setResult(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<set-?>");
        this.result = firBlock;
    }

    public FirWhenBranchImpl(KtSourceElement ktSourceElement, FirExpression condition, FirBlock result) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(result, "result");
        this.source = ktSourceElement;
        this.condition = condition;
        this.result = result;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getCondition().accept(visitor, data);
        getResult().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirWhenBranchImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformCondition((FirTransformer) transformer, (Object) data);
        transformResult((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public <D> FirWhenBranchImpl transformCondition(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCondition((FirExpression) getCondition().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenBranch
    public <D> FirWhenBranchImpl transformResult(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setResult((FirBlock) getResult().transform(transformer, data));
        return this;
    }
}
