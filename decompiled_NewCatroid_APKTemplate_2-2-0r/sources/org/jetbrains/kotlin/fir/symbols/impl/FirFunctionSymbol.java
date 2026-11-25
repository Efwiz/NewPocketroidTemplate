package org.jetbrains.kotlin.fir.symbols.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u000f\b\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u0082\u0001\u0003\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "getResolvedContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "resolvedControlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getResolvedControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "valueParameterSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "getValueParameterSymbols", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionWithoutNameSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunctionSymbol<D extends FirFunction> extends FirCallableSymbol<D> implements FunctionSymbolMarker {
    private final CallableId callableId;

    public /* synthetic */ FirFunctionSymbol(CallableId callableId, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableId);
    }

    private FirFunctionSymbol(CallableId callableId) {
        this.callableId = callableId;
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
    public CallableId getCallableId() {
        return this.callableId;
    }

    public final List<FirValueParameterSymbol> getValueParameterSymbols() {
        List<FirValueParameter> valueParameters = getFir().getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirValueParameter) it.next()).getSymbol());
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirResolvedContractDescription getResolvedContractDescription() {
        FirContractDescription contractDescription;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.CONTRACTS);
        if (this instanceof FirNamedFunctionSymbol) {
            contractDescription = ((FirSimpleFunction) ((FirNamedFunctionSymbol) this).getFir()).getContractDescription();
        } else {
            contractDescription = this instanceof FirPropertyAccessorSymbol ? ((FirPropertyAccessor) ((FirPropertyAccessorSymbol) this).getFir()).getContractDescription() : null;
        }
        if (contractDescription instanceof FirResolvedContractDescription) {
            return (FirResolvedContractDescription) contractDescription;
        }
        return null;
    }

    public final FirControlFlowGraphReference getResolvedControlFlowGraphReference() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.BODY_RESOLVE);
        return getFir().getControlFlowGraphReference();
    }
}
