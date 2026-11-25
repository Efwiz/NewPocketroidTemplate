package org.jetbrains.kotlin.fir.expressions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentListForErrorCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentListImpl;

/* JADX INFO: compiled from: FirArgumentUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0014\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004\u001a\u0016\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005\u001a6\u0010\n\u001a\u00020\u000b2\"\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\fj\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u001a\u000e\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0005¨\u0006\u0012"}, d2 = {"buildArgumentListForErrorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "original", "mapping", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "buildBinaryArgumentList", "left", "right", "buildResolvedArgumentList", "Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedArgumentList;", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "buildUnaryArgumentList", "argument", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirArgumentUtilKt {
    public static /* synthetic */ FirResolvedArgumentList buildResolvedArgumentList$default(LinkedHashMap linkedHashMap, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 2) != 0) {
            ktSourceElement = null;
        }
        return buildResolvedArgumentList(linkedHashMap, ktSourceElement);
    }

    public static final FirResolvedArgumentList buildResolvedArgumentList(LinkedHashMap<FirExpression, FirValueParameter> mapping, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        return new FirResolvedArgumentListImpl(ktSourceElement, mapping);
    }

    public static final FirArgumentList buildArgumentListForErrorCall(FirArgumentList original, Map<FirExpression, ? extends FirValueParameter> mapping) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        KtSourceElement source = original.getSource();
        List<FirExpression> arguments = original.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        for (FirExpression firExpression : arguments) {
            arrayList.add(TuplesKt.to(firExpression, mapping.get(firExpression)));
        }
        return new FirResolvedArgumentListForErrorCall(source, (LinkedHashMap) MapsKt.toMap(arrayList, new LinkedHashMap()));
    }

    public static final FirArgumentList buildUnaryArgumentList(FirExpression argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(argument);
        return firArgumentListBuilder.build();
    }

    public static final FirArgumentList buildBinaryArgumentList(FirExpression left, FirExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(left);
        firArgumentListBuilder.getArguments().add(right);
        return firArgumentListBuilder.build();
    }
}
