package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirResolvedArgumentList.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012&\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007`\b¢\u0006\u0002\u0010\tJ$\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bH\u0002J)\u0010\u0017\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001a2\u0006\u0010\u001b\u001a\u0002H\u0018H\u0016¢\u0006\u0002\u0010\u001cR.\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005j\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007`\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rRV\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b2\"\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentListForErrorCall;", "Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentList;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "_mapping", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/LinkedHashMap;)V", "arguments", "", "getArguments", "()Ljava/util/List;", "<set-?>", "mapping", "getMapping", "()Ljava/util/LinkedHashMap;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "computeMapping", "transformArguments", "D", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentListForErrorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedArgumentListForErrorCall extends FirResolvedArgumentList {
    private LinkedHashMap<FirExpression, FirValueParameter> _mapping;
    private LinkedHashMap<FirExpression, FirValueParameter> mapping;
    private KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public /* bridge */ /* synthetic */ FirArgumentList transformArguments(FirTransformer firTransformer, Object obj) {
        return transformArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public FirResolvedArgumentListForErrorCall(KtSourceElement ktSourceElement, LinkedHashMap<FirExpression, FirValueParameter> _mapping) {
        Intrinsics.checkNotNullParameter(_mapping, "_mapping");
        this.source = ktSourceElement;
        this._mapping = _mapping;
        this.mapping = computeMapping();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList
    public LinkedHashMap<FirExpression, FirValueParameter> getMapping() {
        return this.mapping;
    }

    private final LinkedHashMap<FirExpression, FirValueParameter> computeMapping() {
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap = this._mapping;
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap2 = new LinkedHashMap<>();
        for (Map.Entry<FirExpression, FirValueParameter> entry : linkedHashMap.entrySet()) {
            if (entry.getValue() != null) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap2;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public List<FirExpression> getArguments() {
        Set<FirExpression> setKeySet = this._mapping.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        return CollectionsKt.toList(setKeySet);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public <D> FirResolvedArgumentListForErrorCall transformArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap = this._mapping;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap.size()));
        Iterator<T> it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap2.put((FirExpression) FirTransformerUtilKt.transformSingle((FirExpression) entry.getKey(), transformer, data), entry.getValue());
        }
        this._mapping = linkedHashMap2;
        this.mapping = computeMapping();
        return this;
    }
}
