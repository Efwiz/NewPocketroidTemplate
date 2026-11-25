package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDefaultPropertyAccessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001Ba\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyGetter;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "propertyTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultPropertyGetter extends FirDefaultPropertyAccessor {
    public /* synthetic */ FirDefaultPropertyGetter(KtSourceElement ktSourceElement, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirTypeRef firTypeRef, Visibility visibility, FirPropertySymbol firPropertySymbol, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol firPropertyAccessorSymbol, FirResolvePhase firResolvePhase, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firModuleData, firDeclarationOrigin, firTypeRef, visibility, firPropertySymbol, (i & 64) != 0 ? Modality.FINAL : modality, (i & 128) != 0 ? null : effectiveVisibility, (i & 256) != 0 ? new FirPropertyAccessorSymbol() : firPropertyAccessorSymbol, (i & 512) != 0 ? FirResolvePhase.RAW_FIR : firResolvePhase);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDefaultPropertyGetter(KtSourceElement ktSourceElement, FirModuleData moduleData, FirDeclarationOrigin origin, FirTypeRef propertyTypeRef, Visibility visibility, FirPropertySymbol propertySymbol, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol symbol, FirResolvePhase resolvePhase) {
        super(ktSourceElement, moduleData, origin, propertyTypeRef, new ArrayList(), propertySymbol, true, visibility, modality, effectiveVisibility, symbol, resolvePhase);
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(propertyTypeRef, "propertyTypeRef");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
    }
}
