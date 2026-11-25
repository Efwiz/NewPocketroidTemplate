package org.jetbrains.kotlin.fir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAbstractArgumentList.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\nJ)\u0010\u000b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00060\r2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\u000eJ)\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00060\r2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirAbstractArgumentList;", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "()V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformArguments", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "transformChildren", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractArgumentList extends FirArgumentList {
    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public <D> FirArgumentList transformArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirElement transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }
}
