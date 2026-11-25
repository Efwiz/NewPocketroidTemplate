package org.jetbrains.kotlin.fir.plugin;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: SimpleFunctionBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BE\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\u0002\u0010\u0010J\b\u0010\u0012\u001a\u00020\u0002H\u0016J \u0010\u0013\u001a\u00020\u00142\u0018\u0010\u0015\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f0\fJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u000fR\"\u0010\u0011\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/SimpleFunctionBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "returnTypeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/name/CallableId;Lkotlin/jvm/functions/Function1;)V", "extensionReceiverTypeProvider", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "extensionReceiverType", "", "typeProvider", ModuleXmlParser.TYPE, "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleFunctionBuildingContext extends FunctionBuildingContext<FirSimpleFunction> {
    private Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> extensionReceiverTypeProvider;
    private final Function1<List<? extends FirTypeParameter>, ConeKotlinType> returnTypeProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SimpleFunctionBuildingContext(FirSession session, GeneratedDeclarationKey key, FirClassSymbol<?> firClassSymbol, CallableId callableId, Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> returnTypeProvider) {
        super(callableId, session, key, firClassSymbol, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        this.returnTypeProvider = returnTypeProvider;
    }

    public final void extensionReceiverType(final ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        extensionReceiverType(new Function1<List<? extends FirTypeParameter>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContext.extensionReceiverType.1
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
        if (this.extensionReceiverTypeProvider != null) {
            throw new IllegalArgumentException("Extension receiver type is already initialized".toString());
        }
        this.extensionReceiverTypeProvider = typeProvider;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    public FirSimpleFunction build() {
        ConeKotlinType coneKotlinTypeInvoke;
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firSimpleFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(getKey()));
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(getCallableId()));
        firSimpleFunctionBuilder.setName(getCallableId().getCallableName());
        firSimpleFunctionBuilder.setStatus(generateStatus());
        FirClassSymbol<?> owner = getOwner();
        firSimpleFunctionBuilder.setDispatchReceiverType(owner != null ? ScopeUtilsKt.defaultType(owner) : null);
        List<DeclarationBuildingContext.TypeParameterData> typeParameters = getTypeParameters();
        List<FirTypeParameter> typeParameters2 = firSimpleFunctionBuilder.getTypeParameters();
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            typeParameters2.add(generateTypeParameter((DeclarationBuildingContext.TypeParameterData) it.next(), firSimpleFunctionBuilder.getSymbol()));
        }
        initTypeParameterBounds(firSimpleFunctionBuilder.getTypeParameters(), firSimpleFunctionBuilder.getTypeParameters());
        produceContextReceiversTo(firSimpleFunctionBuilder.getContextReceivers(), firSimpleFunctionBuilder.getTypeParameters());
        List<FunctionBuildingContext.ValueParameterData> valueParameters = getValueParameters();
        List<FirValueParameter> valueParameters2 = firSimpleFunctionBuilder.getValueParameters();
        Iterator<T> it2 = valueParameters.iterator();
        while (it2.hasNext()) {
            valueParameters2.add(generateValueParameter((FunctionBuildingContext.ValueParameterData) it2.next(), firSimpleFunctionBuilder.getSymbol(), firSimpleFunctionBuilder.getTypeParameters()));
        }
        firSimpleFunctionBuilder.setReturnTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(this.returnTypeProvider.invoke(firSimpleFunctionBuilder.getTypeParameters()), null, null, 3, null));
        Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> function1 = this.extensionReceiverTypeProvider;
        if (function1 != null && (coneKotlinTypeInvoke = function1.invoke(firSimpleFunctionBuilder.getTypeParameters())) != null) {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinTypeInvoke, null, null, 3, null));
            firSimpleFunctionBuilder.setReceiverParameter(firReceiverParameterBuilder.mo6809build());
        }
        return firSimpleFunctionBuilder.mo6809build();
    }
}
