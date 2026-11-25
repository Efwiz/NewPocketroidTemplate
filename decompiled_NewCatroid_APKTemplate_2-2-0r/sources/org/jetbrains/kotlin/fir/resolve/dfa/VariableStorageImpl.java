package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: VariableStorageImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0012\u001a\u00020\u0000J\u001e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\bJ(\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u000e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0010J\u001a\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0010H\u0016J\"\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020#H\u0002J$\u0010$\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010%\u001a\u0006\u0012\u0002\b\u00030&2\u0006\u0010\u001f\u001a\u00020\u0010H\u0002J\u0016\u0010'\u001a\u0004\u0018\u00010\b2\n\u0010%\u001a\u0006\u0012\u0002\b\u00030&H\u0016J\u0016\u0010(\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0010J\u0018\u0010)\u001a\u0004\u0018\u00010!2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0010J\"\u0010*\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010%\u001a\u0006\u0012\u0002\b\u00030&2\u0006\u0010\u001f\u001a\u00020\u0010J\u001a\u0010+\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0010H\u0016J\u0012\u0010,\u001a\u00020-2\n\u0010%\u001a\u0006\u0012\u0002\b\u00030&J\u001a\u0010.\u001a\u0004\u0018\u00010\u001d*\u0006\u0012\u0002\b\u00030&2\u0006\u0010\u001b\u001a\u00020\u0010H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorage;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "_realVariables", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Identifier;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "counter", "", "realVariables", "", "getRealVariables", "()Ljava/util/Map;", "syntheticVariables", "Lorg/jetbrains/kotlin/fir/FirElement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/SyntheticVariable;", LineReader.CLEAR, "copyRealVariableWithRemapping", "variable", TypedValues.TransitionType.S_FROM, "to", "createReal", "flow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Flow;", "identifier", "originalFir", "stability", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", "createSynthetic", "fir", "get", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "realFir", "", "getIdentifierBySymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getLocalVariable", "getOrCreate", "getOrCreateIfReal", "getOrCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization", "getRealVariableWithoutUnwrappingAlias", "removeRealVariable", "", "getStability", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableStorageImpl extends VariableStorage {
    private final Map<Identifier, RealVariable> _realVariables;
    private int counter;
    private final FirSession session;
    private final Map<FirElement, SyntheticVariable> syntheticVariables;

    public VariableStorageImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.counter = 1;
        this._realVariables = new HashMap();
        this.syntheticVariables = new HashMap();
    }

    public final Map<Identifier, RealVariable> getRealVariables() {
        return this._realVariables;
    }

    public final VariableStorageImpl clear() {
        return new VariableStorageImpl(this.session);
    }

    public final RealVariable getOrCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization(Flow flow, FirBasedSymbol<?> symbol, FirElement fir) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(fir, "fir");
        FirElement firElementUnwrapElement = UtilKt.unwrapElement(fir);
        Identifier identifierBySymbol = getIdentifierBySymbol(flow, symbol, firElementUnwrapElement);
        PropertyStability stability = getStability(symbol, fir);
        if (stability == null) {
            throw new IllegalArgumentException("Stability for initialized variable always should be computable".toString());
        }
        RealVariable realVariable = this._realVariables.get(identifierBySymbol);
        return realVariable == null ? createReal(flow, identifierBySymbol, firElementUnwrapElement, stability) : realVariable;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.VariableStorage
    public RealVariable getRealVariableWithoutUnwrappingAlias(Flow flow, FirElement fir) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(fir, "fir");
        FirElement firElementUnwrapElement = UtilKt.unwrapElement(fir);
        FirBasedSymbol<?> symbol = UtilKt.getSymbol(firElementUnwrapElement);
        if (symbol == null || getStability(symbol, firElementUnwrapElement) == null) {
            return null;
        }
        return this._realVariables.get(getIdentifierBySymbol(flow, symbol, firElementUnwrapElement));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.VariableStorage
    public RealVariable getLocalVariable(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this._realVariables.get(new Identifier(symbol, null, null));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.VariableStorage
    public DataFlowVariable get(Flow flow, FirElement fir) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return get(flow, UtilKt.unwrapElement(fir), false);
    }

    public final DataFlowVariable getOrCreateIfReal(Flow flow, FirElement fir) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return get(flow, UtilKt.unwrapElement(fir), true);
    }

    public final DataFlowVariable getOrCreate(Flow flow, FirElement fir) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(fir, "fir");
        FirElement firElementUnwrapElement = UtilKt.unwrapElement(fir);
        DataFlowVariable dataFlowVariable = get(flow, firElementUnwrapElement, true);
        return dataFlowVariable == null ? createSynthetic(firElementUnwrapElement) : dataFlowVariable;
    }

    public final SyntheticVariable createSynthetic(FirElement fir) {
        Intrinsics.checkNotNullParameter(fir, "fir");
        int i = this.counter;
        this.counter = i + 1;
        SyntheticVariable syntheticVariable = new SyntheticVariable(fir, i);
        this.syntheticVariables.put(fir, syntheticVariable);
        return syntheticVariable;
    }

    private final DataFlowVariable get(Flow flow, FirElement realFir, boolean createReal) {
        PropertyStability stability;
        RealVariable realVariableUnwrapVariable;
        FirBasedSymbol<?> symbol = UtilKt.getSymbol(realFir);
        if (symbol == null || (stability = getStability(symbol, realFir)) == null) {
            return this.syntheticVariables.get(realFir);
        }
        Identifier identifierBySymbol = getIdentifierBySymbol(flow, symbol, realFir);
        RealVariable realVariable = this._realVariables.get(identifierBySymbol);
        if (realVariable == null || (realVariableUnwrapVariable = flow.unwrapVariable(realVariable)) == null) {
            return createReal ? createReal(flow, identifierBySymbol, realFir, stability) : null;
        }
        return realVariableUnwrapVariable;
    }

    public final void removeRealVariable(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this._realVariables.remove(new Identifier(symbol, null, null));
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0040  */
    private final Identifier getIdentifierBySymbol(Flow flow, FirBasedSymbol<?> symbol, FirElement fir) {
        DataFlowVariable orCreate;
        FirExpression extensionReceiver;
        FirExpression dispatchReceiver;
        DataFlowVariable orCreate2 = null;
        FirQualifiedAccessExpression firQualifiedAccessExpression = fir instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) fir : null;
        if (firQualifiedAccessExpression == null) {
            FirVariableAssignment firVariableAssignment = fir instanceof FirVariableAssignment ? (FirVariableAssignment) fir : null;
            FirExpression lValue = firVariableAssignment != null ? firVariableAssignment.getLValue() : null;
            firQualifiedAccessExpression = lValue instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) lValue : null;
        }
        if (firQualifiedAccessExpression == null || (dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver()) == null) {
            orCreate = null;
        } else {
            if (Intrinsics.areEqual(dispatchReceiver, FirNoReceiverExpression.INSTANCE)) {
                dispatchReceiver = null;
            }
            if (dispatchReceiver != null) {
                orCreate = getOrCreate(flow, dispatchReceiver);
            } else {
                orCreate = null;
            }
        }
        if (firQualifiedAccessExpression != null && (extensionReceiver = firQualifiedAccessExpression.getExtensionReceiver()) != null) {
            if (Intrinsics.areEqual(extensionReceiver, FirNoReceiverExpression.INSTANCE)) {
                extensionReceiver = null;
            }
            if (extensionReceiver != null) {
                orCreate2 = getOrCreate(flow, extensionReceiver);
            }
        }
        return new Identifier(symbol, orCreate, orCreate2);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002e  */
    private final RealVariable createReal(Flow flow, Identifier identifier, FirElement originalFir, PropertyStability stability) {
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue;
        boolean z;
        FirExpression explicitReceiver;
        if (originalFir instanceof FirQualifiedAccessExpression) {
            firQualifiedAccessExpressionUnwrapLValue = (FirQualifiedAccessExpression) originalFir;
        } else if (originalFir instanceof FirWhenSubjectExpression) {
            FirExpression subject = ((FirWhenExpression) ((FirWhenSubjectExpression) originalFir).getWhenRef().getValue()).getSubject();
            if (subject instanceof FirQualifiedAccessExpression) {
                firQualifiedAccessExpressionUnwrapLValue = (FirQualifiedAccessExpression) subject;
            } else {
                firQualifiedAccessExpressionUnwrapLValue = null;
            }
        } else if (originalFir instanceof FirVariableAssignment) {
            firQualifiedAccessExpressionUnwrapLValue = FirExpressionUtilKt.unwrapLValue((FirVariableAssignment) originalFir);
        } else {
            firQualifiedAccessExpressionUnwrapLValue = null;
        }
        if (firQualifiedAccessExpressionUnwrapLValue != null) {
            explicitReceiver = firQualifiedAccessExpressionUnwrapLValue.getExplicitReceiver();
            z = firQualifiedAccessExpressionUnwrapLValue.getCalleeReference() instanceof FirThisReference;
        } else {
            z = false;
            explicitReceiver = null;
        }
        DataFlowVariable orCreate = explicitReceiver != null ? getOrCreate(flow, explicitReceiver) : null;
        int i = this.counter;
        this.counter = i + 1;
        RealVariable realVariable = new RealVariable(identifier, z, orCreate, i, stability);
        this._realVariables.put(identifier, realVariable);
        return realVariable;
    }

    public final RealVariable copyRealVariableWithRemapping(RealVariable variable, RealVariable from, RealVariable to) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Identifier identifier = variable.getIdentifier();
        Identifier identifierCopy$default = Identifier.copy$default(identifier, null, Intrinsics.areEqual(identifier.getDispatchReceiver(), from) ? to : identifier.getDispatchReceiver(), Intrinsics.areEqual(identifier.getExtensionReceiver(), from) ? to : identifier.getExtensionReceiver(), 1, null);
        Map<Identifier, RealVariable> map = this._realVariables;
        RealVariable realVariable = map.get(identifierCopy$default);
        if (realVariable == null) {
            boolean isThisReference = variable.getIsThisReference();
            DataFlowVariable explicitReceiverVariable = Intrinsics.areEqual(variable.getExplicitReceiverVariable(), from) ? to : variable.getExplicitReceiverVariable();
            int i = this.counter;
            this.counter = i + 1;
            realVariable = new RealVariable(identifierCopy$default, isThisReference, explicitReceiverVariable, i, variable.getStability());
            map.put(identifierCopy$default, realVariable);
        }
        return realVariable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final PropertyStability getStability(FirBasedSymbol<?> firBasedSymbol, FirElement firElement) {
        FirExpression dispatchReceiver;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        FirClassLikeSymbol<?> symbol;
        if (firElement instanceof FirThisReceiverExpression) {
            return PropertyStability.STABLE_VALUE;
        }
        if (firBasedSymbol instanceof FirAnonymousObjectSymbol) {
            return null;
        }
        if (firBasedSymbol instanceof FirFunctionSymbol ? true : firBasedSymbol instanceof FirClassSymbol ? true : firBasedSymbol instanceof FirBackingFieldSymbol) {
            return PropertyStability.STABLE_VALUE;
        }
        if (!(firBasedSymbol instanceof FirVariableSymbol)) {
            return null;
        }
        boolean z = false;
        if (firBasedSymbol instanceof FirFieldSymbol) {
            Modality modality = ((FirCallableSymbol) firBasedSymbol).getResolvedStatus().getModality();
            if (!(modality == null || modality == Modality.FINAL)) {
                return PropertyStability.MUTABLE_PROPERTY;
            }
        }
        D fir = ((FirVariableSymbol) firBasedSymbol).getFir();
        FirProperty firProperty = fir instanceof FirProperty ? (FirProperty) fir : null;
        if (firProperty == null) {
            return PropertyStability.STABLE_VALUE;
        }
        if (firProperty.getDelegate() != null) {
            return PropertyStability.DELEGATED_PROPERTY;
        }
        if (firProperty.getIsLocal()) {
            return firProperty.getIsVal() ? PropertyStability.STABLE_VALUE : PropertyStability.LOCAL_VAR;
        }
        if (firProperty.getIsVar()) {
            return PropertyStability.MUTABLE_PROPERTY;
        }
        if (firProperty.getReceiverParameter() != null) {
            return PropertyStability.PROPERTY_WITH_GETTER;
        }
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter != null && !(getter instanceof FirDefaultPropertyAccessor)) {
            z = true;
        }
        if (z) {
            return PropertyStability.PROPERTY_WITH_GETTER;
        }
        FirProperty firProperty2 = firProperty;
        if (Intrinsics.areEqual(firProperty2.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
            return PropertyStability.STABLE_VALUE;
        }
        if (firProperty2.getStatus().getModality() != Modality.FINAL) {
            FirElement firElementUnwrapElement = UtilKt.unwrapElement(firElement);
            FirQualifiedAccessExpression firQualifiedAccessExpression = firElementUnwrapElement instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firElementUnwrapElement : null;
            if (firQualifiedAccessExpression != null && (dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver()) != null) {
                FirTypeRef typeRef = dispatchReceiver.getTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, this.session, null, 2, null)) == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), this.session)) == null) {
                    return null;
                }
                FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir();
                if (firClassLikeDeclaration instanceof FirAnonymousObject) {
                    return PropertyStability.STABLE_VALUE;
                }
                if (firClassLikeDeclaration instanceof FirRegularClass) {
                    return firClassLikeDeclaration.getStatus().getModality() == Modality.FINAL ? PropertyStability.STABLE_VALUE : PropertyStability.PROPERTY_WITH_GETTER;
                }
                throw new IllegalStateException("Should not be here: " + firClassLikeDeclaration);
            }
            return null;
        }
        FirProperty firProperty3 = firProperty;
        while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firProperty3)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firProperty3) || (firProperty3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty3) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firProperty3) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firProperty3) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firProperty3 = originalForSubstitutionOverrideAttr;
        }
        FirModuleData moduleData = ((FirProperty) firProperty3).getModuleData();
        FirModuleData moduleData2 = FirModuleDataKt.getModuleData(this.session);
        return Intrinsics.areEqual(moduleData, moduleData2) ? true : moduleData2.getFriendDependencies().contains(moduleData) ? true : moduleData2.getDependsOnDependencies().contains(moduleData) ? PropertyStability.STABLE_VALUE : PropertyStability.ALIEN_PUBLIC_PROPERTY;
    }
}
