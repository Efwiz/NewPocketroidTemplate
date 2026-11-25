package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
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
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\"\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\u0002\u0010\tJ)\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015RV\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b2\"\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentListImpl;", "Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentList;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "mapping", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/LinkedHashMap;)V", "<set-?>", "getMapping", "()Ljava/util/LinkedHashMap;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "transformArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "D", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedArgumentListImpl extends FirResolvedArgumentList {
    private LinkedHashMap<FirExpression, FirValueParameter> mapping;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public FirResolvedArgumentListImpl(KtSourceElement ktSourceElement, LinkedHashMap<FirExpression, FirValueParameter> mapping) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        this.source = ktSourceElement;
        this.mapping = mapping;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList
    public LinkedHashMap<FirExpression, FirValueParameter> getMapping() {
        return this.mapping;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList, org.jetbrains.kotlin.fir.expressions.FirAbstractArgumentList, org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public <D> FirArgumentList transformArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        LinkedHashMap<FirExpression, FirValueParameter> mapping = getMapping();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapping.size()));
        Iterator<T> it = mapping.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put((FirExpression) FirTransformerUtilKt.transformSingle((FirExpression) entry.getKey(), transformer, data), entry.getValue());
        }
        this.mapping = linkedHashMap;
        return this;
    }
}
