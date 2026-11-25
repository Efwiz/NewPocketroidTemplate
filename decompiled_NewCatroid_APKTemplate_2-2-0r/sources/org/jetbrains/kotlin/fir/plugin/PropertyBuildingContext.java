package org.jetbrains.kotlin.fir.plugin;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyBodyResolveState;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: PropertyBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BU\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f0\f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011¢\u0006\u0002\u0010\u0013J\b\u0010\u0018\u001a\u00020\u0002H\u0016J \u0010\u0019\u001a\u00020\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\r\u0012\u0004\u0012\u00020\u000f0\fJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u000fJ\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u0017R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0014\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\r\u0012\u0004\u0012\u00020\u000f\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/PropertyBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "returnTypeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isVal", "", "hasBackingField", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/name/CallableId;Lkotlin/jvm/functions/Function1;ZZ)V", "extensionReceiverTypeProvider", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "setterVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "extensionReceiverType", "", "typeProvider", ModuleXmlParser.TYPE, "setter", "visibility", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyBuildingContext extends DeclarationBuildingContext<FirProperty> {
    private final CallableId callableId;
    private Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> extensionReceiverTypeProvider;
    private final boolean hasBackingField;
    private final boolean isVal;
    private final Function1<List<? extends FirTypeParameterRef>, ConeKotlinType> returnTypeProvider;
    private Visibility setterVisibility;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PropertyBuildingContext(FirSession session, GeneratedDeclarationKey key, FirClassSymbol<?> firClassSymbol, CallableId callableId, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> returnTypeProvider, boolean z, boolean z2) {
        super(session, key, firClassSymbol, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        this.callableId = callableId;
        this.returnTypeProvider = returnTypeProvider;
        this.isVal = z;
        this.hasBackingField = z2;
    }

    public final void extensionReceiverType(final ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        extensionReceiverType(new Function1<List<? extends FirTypeParameter>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.PropertyBuildingContext.extensionReceiverType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return type;
            }
        });
    }

    public final void extensionReceiverType(Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> typeProvider) {
        Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
        this.extensionReceiverTypeProvider = typeProvider;
    }

    public final void setter(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        this.setterVisibility = visibility;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    public FirProperty build() {
        ConeKotlinType coneKotlinTypeInvoke;
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firPropertyBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
        firPropertyBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(getKey()));
        firPropertyBuilder.setSymbol(new FirPropertySymbol(this.callableId));
        firPropertyBuilder.setName(this.callableId.getCallableName());
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImplGenerateStatus = generateStatus();
        firPropertyBuilder.setStatus(firResolvedDeclarationStatusImplGenerateStatus);
        FirClassSymbol<?> owner = getOwner();
        firPropertyBuilder.setDispatchReceiverType(owner != null ? ScopeUtilsKt.defaultType(owner) : null);
        List<DeclarationBuildingContext.TypeParameterData> typeParameters = getTypeParameters();
        List<FirTypeParameter> typeParameters2 = firPropertyBuilder.getTypeParameters();
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            typeParameters2.add(generateTypeParameter((DeclarationBuildingContext.TypeParameterData) it.next(), firPropertyBuilder.getSymbol()));
        }
        initTypeParameterBounds(firPropertyBuilder.getTypeParameters(), firPropertyBuilder.getTypeParameters());
        firPropertyBuilder.setReturnTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(this.returnTypeProvider.invoke(firPropertyBuilder.getTypeParameters()), null, null, 3, null));
        Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> function1 = this.extensionReceiverTypeProvider;
        if (function1 != null && (coneKotlinTypeInvoke = function1.invoke(firPropertyBuilder.getTypeParameters())) != null) {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinTypeInvoke, null, null, 3, null));
            firPropertyBuilder.setReceiverParameter(firReceiverParameterBuilder.mo6809build());
        }
        produceContextReceiversTo(firPropertyBuilder.getContextReceivers(), firPropertyBuilder.getTypeParameters());
        firPropertyBuilder.setVar(!this.isVal);
        firPropertyBuilder.setGetter(new FirDefaultPropertyGetter(null, FirModuleDataKt.getModuleData(getSession()), FirDeclarationOriginKt.getOrigin(getKey()), firPropertyBuilder.getReturnTypeRef(), firPropertyBuilder.getStatus().getVisibility(), firPropertyBuilder.getSymbol(), Modality.FINAL, firResolvedDeclarationStatusImplGenerateStatus.getEffectiveVisibility(), null, FirResolvePhase.BODY_RESOLVE, 256, null));
        if (firPropertyBuilder.isVar()) {
            FirModuleData moduleData = FirModuleDataKt.getModuleData(getSession());
            FirDeclarationOrigin origin = FirDeclarationOriginKt.getOrigin(getKey());
            FirTypeRef returnTypeRef = firPropertyBuilder.getReturnTypeRef();
            Visibility visibility = this.setterVisibility;
            if (visibility == null) {
                visibility = firPropertyBuilder.getStatus().getVisibility();
            }
            firPropertyBuilder.setSetter(new FirDefaultPropertySetter(null, moduleData, origin, returnTypeRef, visibility, firPropertyBuilder.getSymbol(), Modality.FINAL, firResolvedDeclarationStatusImplGenerateStatus.getEffectiveVisibility(), null, null, FirResolvePhase.BODY_RESOLVE, 768, null));
        } else if (this.setterVisibility != null) {
            throw new IllegalArgumentException("isVar = false but setterVisibility is specified. Did you forget to set isVar = true?".toString());
        }
        if (this.hasBackingField) {
            firPropertyBuilder.setBackingField(new FirDefaultPropertyBackingField(FirModuleDataKt.getModuleData(getSession()), FirDeclarationOriginKt.getOrigin(getKey()), null, new ArrayList(), firPropertyBuilder.getReturnTypeRef(), firPropertyBuilder.isVar(), firPropertyBuilder.getSymbol(), firPropertyBuilder.getStatus(), FirResolvePhase.BODY_RESOLVE));
        }
        firPropertyBuilder.setLocal(false);
        firPropertyBuilder.setBodyResolveState(FirPropertyBodyResolveState.EVERYTHING_RESOLVED);
        return firPropertyBuilder.mo6809build();
    }
}
