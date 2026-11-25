package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorTypeImpl;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUIntTypeRef;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirIntegerConstantOperatorScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u0014H\u0016J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\rH\u0016J.\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\r2\u001c\u0010\u001a\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001c\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00190\u001bH\u0016J\u001c\u0010\u001e\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00190\u001fH\u0016J*\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000e2\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\"0\u001bH\u0016J*\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020&2\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\"0\u001bH\u0016J$\u0010'\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\r2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00190\u001fH\u0016J(\u0010(\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\r2\u0016\u0010\u001a\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030)\u0012\u0004\u0012\u00020\u00190\u001fH\u0016J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u000eH\u0002R\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntegerConstantOperatorScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "isUnsigned", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Z)V", "baseScope", "()Z", "mappedFunctions", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getCallableNames", "", "getClassifierNames", "mayContainName", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "wrapIntOperator", "originalSymbol", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIntegerConstantOperatorScope extends FirTypeScope {
    private final FirTypeScope baseScope;
    private final boolean isUnsigned;
    private final Map<Name, FirNamedFunctionSymbol> mappedFunctions;
    private final ScopeSession scopeSession;
    private final FirSession session;

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    /* JADX INFO: renamed from: isUnsigned, reason: from getter */
    public final boolean getIsUnsigned() {
        return this.isUnsigned;
    }

    public FirIntegerConstantOperatorScope(FirSession session, ScopeSession scopeSession, boolean z) {
        FirImplicitUIntTypeRef intType;
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.isUnsigned = z;
        boolean z2 = this.isUnsigned;
        if (z2) {
            intType = this.session.getBuiltinTypes().getUIntType();
        } else if (!z2) {
            intType = this.session.getBuiltinTypes().getIntType();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        FirTypeScope.Empty emptyScope = ScopeUtilsKt.scope(intType.getType(), this.session, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        this.baseScope = emptyScope == null ? FirTypeScope.Empty.INSTANCE : emptyScope;
        this.mappedFunctions = new LinkedHashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        boolean zIsInt;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        boolean z = false;
        boolean z2 = !this.isUnsigned && ConvertibleIntegerOperators.INSTANCE.getUnaryOperatorNames().contains(name2);
        boolean zContains = ConvertibleIntegerOperators.INSTANCE.getBinaryOperatorsNames().contains(name2);
        if (!z2 && !zContains) {
            this.baseScope.processFunctionsByName(name2, processor);
            return;
        }
        if (this.isUnsigned && !ConvertibleIntegerOperators.INSTANCE.getBinaryOperatorsWithSignedArgument().contains(name2)) {
            z = true;
        }
        Map<Name, FirNamedFunctionSymbol> map = this.mappedFunctions;
        FirNamedFunctionSymbol firNamedFunctionSymbolWrapIntOperator = map.get(name2);
        if (firNamedFunctionSymbolWrapIntOperator == null) {
            for (FirNamedFunctionSymbol firNamedFunctionSymbol : FirScopeKt.getFunctions(this.baseScope, name2)) {
                if (z2) {
                    zIsInt = true;
                } else {
                    ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirValueParameter) CollectionsKt.first((List) ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters())).getReturnTypeRef());
                    if (z) {
                        zIsInt = ConeBuiltinTypeUtilsKt.isUInt(coneType);
                    } else {
                        zIsInt = ConeBuiltinTypeUtilsKt.isInt(coneType);
                    }
                }
                if (zIsInt) {
                    firNamedFunctionSymbolWrapIntOperator = wrapIntOperator(firNamedFunctionSymbol);
                    map.put(name2, firNamedFunctionSymbolWrapIntOperator);
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        processor.invoke(firNamedFunctionSymbolWrapIntOperator);
        this.baseScope.processFunctionsByName(name2, processor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirNamedFunctionSymbol wrapIntOperator(FirNamedFunctionSymbol originalSymbol) {
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) originalSymbol.getFir();
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(firSimpleFunction.getSource());
        firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firSimpleFunction));
        firSimpleFunctionBuilder.setModuleData(firSimpleFunction.getModuleData());
        firSimpleFunctionBuilder.setOrigin(firSimpleFunction.getOrigin());
        firSimpleFunctionBuilder.setAttributes(firSimpleFunction.getAttributes().copy());
        firSimpleFunctionBuilder.setStatus(firSimpleFunction.getStatus());
        firSimpleFunctionBuilder.setReturnTypeRef(firSimpleFunction.getReturnTypeRef());
        firSimpleFunctionBuilder.setReceiverParameter(firSimpleFunction.getReceiverParameter());
        firSimpleFunctionBuilder.setDeprecationsProvider(firSimpleFunction.getDeprecationsProvider());
        firSimpleFunctionBuilder.setContainerSource(firSimpleFunction.getContainerSource());
        firSimpleFunctionBuilder.setDispatchReceiverType(firSimpleFunction.getDispatchReceiverType());
        firSimpleFunctionBuilder.getContextReceivers().addAll(firSimpleFunction.getContextReceivers());
        firSimpleFunctionBuilder.getValueParameters().addAll(firSimpleFunction.getValueParameters());
        firSimpleFunctionBuilder.setBody(firSimpleFunction.getBody());
        firSimpleFunctionBuilder.setContractDescription(firSimpleFunction.getContractDescription());
        firSimpleFunctionBuilder.setName(firSimpleFunction.getName());
        firSimpleFunctionBuilder.setSymbol(firSimpleFunction.getSymbol());
        firSimpleFunctionBuilder.getAnnotations().addAll(firSimpleFunction.getAnnotations());
        firSimpleFunctionBuilder.getTypeParameters().addAll(firSimpleFunction.getTypeParameters());
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(originalSymbol.getCallableId()));
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.WrappedIntegerOperator.INSTANCE);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(new ConeIntegerConstantOperatorTypeImpl(this.isUnsigned, ConeNullability.NOT_NULL));
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        FirSimpleFunction firSimpleFunctionMo6810build = firSimpleFunctionBuilder.mo6809build();
        FirIntegerConstantOperatorScopeKt.setOriginalForWrappedIntegerOperator(firSimpleFunctionMo6810build, originalSymbol);
        FirIntegerConstantOperatorScopeKt.setUnsignedWrappedIntegerOperator(firSimpleFunctionMo6810build, Boolean.valueOf(this.isUnsigned));
        return firSimpleFunctionMo6810build.getSymbol();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.baseScope.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.baseScope.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.baseScope.mayContainName(name2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.baseScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }
}
