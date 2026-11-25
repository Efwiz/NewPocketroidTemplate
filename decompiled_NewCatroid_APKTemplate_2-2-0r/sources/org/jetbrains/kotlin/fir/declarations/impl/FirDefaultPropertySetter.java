package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirDefaultSetterValueParameterBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;

/* JADX INFO: compiled from: FirDefaultPropertyAccessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001Bq\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertySetter;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "propertyTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "propertyAccessorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "parameterAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultPropertySetter extends FirDefaultPropertyAccessor {
    public /* synthetic */ FirDefaultPropertySetter(KtSourceElement ktSourceElement, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirTypeRef firTypeRef, Visibility visibility, FirPropertySymbol firPropertySymbol, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol firPropertyAccessorSymbol, List list, FirResolvePhase firResolvePhase, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firModuleData, firDeclarationOrigin, firTypeRef, visibility, firPropertySymbol, (i & 64) != 0 ? Modality.FINAL : modality, (i & 128) != 0 ? null : effectiveVisibility, (i & 256) != 0 ? new FirPropertyAccessorSymbol() : firPropertyAccessorSymbol, (i & 512) != 0 ? CollectionsKt.emptyList() : list, (i & 1024) != 0 ? FirResolvePhase.RAW_FIR : firResolvePhase);
    }

    public FirDefaultPropertySetter(KtSourceElement ktSourceElement, FirModuleData moduleData, FirDeclarationOrigin origin, FirTypeRef propertyTypeRef, Visibility visibility, FirPropertySymbol propertySymbol, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol propertyAccessorSymbol, List<? extends FirAnnotation> parameterAnnotations, FirResolvePhase resolvePhase) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(propertyTypeRef, "propertyTypeRef");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(propertyAccessorSymbol, "propertyAccessorSymbol");
        Intrinsics.checkNotNullParameter(parameterAnnotations, "parameterAnnotations");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        FirImplicitUnitTypeRef firImplicitUnitTypeRef = new FirImplicitUnitTypeRef(ktSourceElement);
        FirValueParameter[] firValueParameterArr = new FirValueParameter[1];
        FirDefaultSetterValueParameterBuilder firDefaultSetterValueParameterBuilder = new FirDefaultSetterValueParameterBuilder();
        firDefaultSetterValueParameterBuilder.setResolvePhase(resolvePhase);
        firDefaultSetterValueParameterBuilder.setSource(ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.DefaultAccessor.INSTANCE, 0, 0, 6, null) : null);
        firDefaultSetterValueParameterBuilder.setContainingFunctionSymbol(propertyAccessorSymbol);
        firDefaultSetterValueParameterBuilder.setModuleData(moduleData);
        firDefaultSetterValueParameterBuilder.setOrigin(origin);
        firDefaultSetterValueParameterBuilder.setReturnTypeRef(propertyTypeRef);
        firDefaultSetterValueParameterBuilder.setSymbol(new FirValueParameterSymbol(StandardNames.DEFAULT_VALUE_PARAMETER));
        CollectionsKt.addAll(firDefaultSetterValueParameterBuilder.getAnnotations(), parameterAnnotations);
        Unit unit = Unit.INSTANCE;
        firValueParameterArr[0] = firDefaultSetterValueParameterBuilder.mo6809build();
        super(ktSourceElement, moduleData, origin, firImplicitUnitTypeRef, CollectionsKt.mutableListOf(firValueParameterArr), propertySymbol, false, visibility, modality, effectiveVisibility, propertyAccessorSymbol, resolvePhase);
    }
}
