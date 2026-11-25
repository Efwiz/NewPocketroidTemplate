package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirResolvedArgumentList.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00150\u00172\u0006\u0010\u0018\u001a\u0002H\u0015H\u0016¢\u0006\u0002\u0010\u0019J)\u0010\u001a\u001a\u00020\u001b\"\u0004\b\u0000\u0010\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00150\u001d2\u0006\u0010\u0018\u001a\u0002H\u0015H&¢\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\u00020 \"\u0004\b\u0000\u0010\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00150\u001d2\u0006\u0010\u0018\u001a\u0002H\u0015H\u0016¢\u0006\u0002\u0010!R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R.\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n`\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAbstractArgumentList;", "()V", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArguments", "()Ljava/util/List;", "mapping", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "getMapping", "()Ljava/util/LinkedHashMap;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "transformChildren", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolvedArgumentList extends FirAbstractArgumentList {
    public abstract LinkedHashMap<FirExpression, FirValueParameter> getMapping();

    @Override // org.jetbrains.kotlin.fir.expressions.FirArgumentList, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public abstract <D> FirArgumentList transformArguments(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public List<FirExpression> getArguments() {
        Set<FirExpression> setKeySet = getMapping().keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        return CollectionsKt.toList(setKeySet);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<FirExpression> it = getArguments().iterator();
        while (it.hasNext()) {
            it.next().accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirElement transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformArguments(transformer, data);
        return this;
    }
}
