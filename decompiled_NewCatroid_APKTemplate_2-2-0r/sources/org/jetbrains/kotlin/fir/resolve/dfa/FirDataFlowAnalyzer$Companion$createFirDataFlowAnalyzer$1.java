package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.PersistentImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001e\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"org/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer$Companion$createFirDataFlowAnalyzer$1", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "logicSystem", "Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "getLogicSystem", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "receiverStack", "Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "getReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "visibilityChecker", "Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "receiverUpdated", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDataFlowAnalyzer$Companion$createFirDataFlowAnalyzer$1 extends FirDataFlowAnalyzer {
    final /* synthetic */ FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents $components;
    private final LogicSystem logicSystem;
    private final ConeInferenceContext typeContext;
    private final FirVisibilityChecker visibilityChecker;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FirDataFlowAnalyzer$Companion$createFirDataFlowAnalyzer$1(final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents, final DataFlowAnalyzerContext dataFlowAnalyzerContext) {
        super(bodyResolveTransformerComponents, dataFlowAnalyzerContext);
        this.$components = bodyResolveTransformerComponents;
        this.visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(bodyResolveTransformerComponents.getSession());
        this.typeContext = TypeComponentsKt.getTypeContext(bodyResolveTransformerComponents.getSession());
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(bodyResolveTransformerComponents.getSession());
        this.logicSystem = new LogicSystem(typeContext) { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$Companion$createFirDataFlowAnalyzer$1$logicSystem$1
            @Override // org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem
            public VariableStorageImpl getVariableStorage() {
                return dataFlowAnalyzerContext.getVariableStorage();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem
            protected boolean isAcceptableForSmartcast(ConeKotlinType coneKotlinType) {
                Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
                if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinType)) {
                    return false;
                }
                if (coneKotlinType instanceof ConeClassLikeType) {
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, bodyResolveTransformerComponents.getSession(), null, 2, null).getLookupTag(), bodyResolveTransformerComponents.getSession());
                    if (symbol == null) {
                        return false;
                    }
                    E fir = symbol.getFir();
                    FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
                    if (firRegularClass == null) {
                        return true;
                    }
                    return this.visibilityChecker.isClassLikeVisible(firRegularClass, bodyResolveTransformerComponents.getSession(), bodyResolveTransformerComponents.getContext().getFile(), bodyResolveTransformerComponents.getContext().getContainers());
                }
                if (!(coneKotlinType instanceof ConeTypeParameterType)) {
                    if (coneKotlinType instanceof ConeFlexibleType) {
                        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
                        if (!isAcceptableForSmartcast(coneFlexibleType.getLowerBound()) || !isAcceptableForSmartcast(coneFlexibleType.getUpperBound())) {
                            return false;
                        }
                    } else {
                        if (!(coneKotlinType instanceof ConeIntersectionType)) {
                            if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
                                return isAcceptableForSmartcast(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
                            }
                            return false;
                        }
                        Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
                        if (!(intersectedTypes instanceof Collection) || !intersectedTypes.isEmpty()) {
                            Iterator<T> it = intersectedTypes.iterator();
                            while (it.hasNext()) {
                                if (!isAcceptableForSmartcast((ConeKotlinType) it.next())) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer
    public PersistentImplicitReceiverStack getReceiverStack() {
        ImplicitReceiverStack implicitReceiverStack = this.$components.getImplicitReceiverStack();
        Intrinsics.checkNotNull(implicitReceiverStack, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.PersistentImplicitReceiverStack");
        return (PersistentImplicitReceiverStack) implicitReceiverStack;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer
    protected void receiverUpdated(FirBasedSymbol<?> symbol, TypeStatement info) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Integer receiverIndex = getReceiverStack().getReceiverIndex(symbol);
        if (receiverIndex != null) {
            int iIntValue = receiverIndex.intValue();
            getReceiverStack().replaceReceiverType(iIntValue, UtilKt.smartCastedType(info, this.typeContext, getReceiverStack().getOriginalType(iIntValue)));
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer
    protected LogicSystem getLogicSystem() {
        return this.logicSystem;
    }
}
