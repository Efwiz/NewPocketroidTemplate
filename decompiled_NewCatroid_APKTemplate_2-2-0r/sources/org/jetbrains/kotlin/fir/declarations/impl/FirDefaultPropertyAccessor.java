package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDefaultPropertyAccessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 '2\u00020\u0001:\u0001'Bs\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bR(\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0016\u0010#\u001a\u0004\u0018\u00010$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirPropertyAccessorImpl;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "propertyTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "isGetter", "", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;ZLorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "body", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDefaultPropertyAccessor extends FirPropertyAccessorImpl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.fir.declarations.impl.FirPropertyAccessorImpl, org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public final FirBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.impl.FirPropertyAccessorImpl
    public final void setBody(FirBlock firBlock) {
    }

    public /* synthetic */ FirDefaultPropertyAccessor(KtSourceElement ktSourceElement, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirTypeRef firTypeRef, List list, FirPropertySymbol firPropertySymbol, boolean z, Visibility visibility, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol firPropertyAccessorSymbol, FirResolvePhase firResolvePhase, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firModuleData, firDeclarationOrigin, firTypeRef, list, firPropertySymbol, z, visibility, (i & 256) != 0 ? Modality.FINAL : modality, (i & 512) != 0 ? null : effectiveVisibility, firPropertyAccessorSymbol, firResolvePhase);
    }

    public FirDefaultPropertyAccessor(KtSourceElement ktSourceElement, FirModuleData moduleData, FirDeclarationOrigin origin, FirTypeRef propertyTypeRef, List<FirValueParameter> valueParameters, FirPropertySymbol propertySymbol, boolean z, Visibility visibility, Modality modality, EffectiveVisibility effectiveVisibility, FirPropertyAccessorSymbol symbol, FirResolvePhase resolvePhase) {
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl;
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(propertyTypeRef, "propertyTypeRef");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        FirDeclarationAttributes firDeclarationAttributes = new FirDeclarationAttributes();
        if (effectiveVisibility == null) {
            firResolvedDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, modality);
        } else {
            firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, modality, effectiveVisibility);
        }
        super(ktSourceElement, resolvePhase, moduleData, origin, firDeclarationAttributes, firResolvedDeclarationStatusImpl, propertyTypeRef, UnresolvedDeprecationProvider.INSTANCE, null, null, MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI(), valueParameters, null, FirEmptyContractDescription.INSTANCE, symbol, propertySymbol, z, MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI(), new ArrayList(), null);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.impl.FirPropertyAccessorImpl, org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return getPropertySymbol().getDispatchReceiverType();
    }

    /* JADX INFO: compiled from: FirDefaultPropertyAccessor.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JP\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor$Companion;", "", "()V", "createGetterOrSetter", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "propertyTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "isGetter", "", "parameterAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirDefaultPropertyAccessor createGetterOrSetter(KtSourceElement source, FirModuleData moduleData, FirDeclarationOrigin origin, FirTypeRef propertyTypeRef, Visibility visibility, FirPropertySymbol propertySymbol, boolean isGetter, List<? extends FirAnnotation> parameterAnnotations) {
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(propertyTypeRef, "propertyTypeRef");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
            Intrinsics.checkNotNullParameter(parameterAnnotations, "parameterAnnotations");
            if (isGetter) {
                return new FirDefaultPropertyGetter(source, moduleData, origin, propertyTypeRef, visibility, propertySymbol, Modality.FINAL, null, null, null, 896, null);
            }
            return new FirDefaultPropertySetter(source, moduleData, origin, propertyTypeRef, visibility, propertySymbol, Modality.FINAL, null, null, parameterAnnotations, null, 1408, null);
        }
    }
}
