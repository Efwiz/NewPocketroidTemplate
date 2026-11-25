package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;

/* JADX INFO: compiled from: FirInlineDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"createInlineFunctionBodyContext", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirInlineDeclarationCheckerKt {
    public static final FirInlineDeclarationChecker.InlineFunctionBodyContext createInlineFunctionBodyContext(FirFunction function, FirSession session) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(session, "session");
        List<FirValueParameter> valueParameters = function.getValueParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            FirValueParameter firValueParameter = (FirValueParameter) obj;
            if (!firValueParameter.getIsNoinline()) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
                if (!ConeTypeUtilsKt.isMarkedNullable(coneType) && FunctionalTypeUtilsKt.isNonReflectFunctionType(coneType, session)) {
                    arrayList.add(obj);
                }
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((FirValueParameter) it.next()).getSymbol());
        }
        ArrayList arrayList4 = arrayList3;
        EffectiveVisibility.Local publishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(function);
        if (publishedApiEffectiveVisibility == null) {
            FirDeclarationStatus status = function.getStatus();
            FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
            if (firResolvedDeclarationStatus == null || (publishedApiEffectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
                publishedApiEffectiveVisibility = EffectiveVisibility.Local.INSTANCE;
            }
        }
        return new FirInlineDeclarationChecker.InlineFunctionBodyContext(function, publishedApiEffectiveVisibility, arrayList4, session);
    }
}
