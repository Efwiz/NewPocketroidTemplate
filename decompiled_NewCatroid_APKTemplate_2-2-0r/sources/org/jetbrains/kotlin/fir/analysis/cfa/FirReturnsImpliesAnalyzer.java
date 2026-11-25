package org.jetbrains.kotlin.fir.analysis.cfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.BuiltinTypes;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.cfa.util.CfgUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.ContractsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.DataFlowInfo;
import org.jetbrains.kotlin.fir.resolve.dfa.DataFlowVariable;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.Flow;
import org.jetbrains.kotlin.fir.resolve.dfa.Identifier;
import org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem;
import org.jetbrains.kotlin.fir.resolve.dfa.MutableFlow;
import org.jetbrains.kotlin.fir.resolve.dfa.Operation;
import org.jetbrains.kotlin.fir.resolve.dfa.OperationStatement;
import org.jetbrains.kotlin.fir.resolve.dfa.PersistentFlow;
import org.jetbrains.kotlin.fir.resolve.dfa.PropertyStability;
import org.jetbrains.kotlin.fir.resolve.dfa.RealVariable;
import org.jetbrains.kotlin.fir.resolve.dfa.TypeStatement;
import org.jetbrains.kotlin.fir.resolve.dfa.UtilKt;
import org.jetbrains.kotlin.fir.resolve.dfa.VariableStorage;
import org.jetbrains.kotlin.fir.resolve.dfa.VariableStorageImpl;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.JumpNode;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirReturnsImpliesAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0016J(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002Jd\u0010\u0016\u001a\u00020\u00172\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u00192\u0016\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001c0\u001bj\u0002`\u001d2\u0016\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001c0\u001fj\u0002` 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J\u001c\u0010%\u001a\u00020\u0017*\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\tH\u0002J.\u0010*\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190+*\u0006\u0012\u0002\b\u00030\u00192\u0012\b\u0002\u0010,\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190-H\u0002J\u0016\u0010.\u001a\u00020\u0017*\u00020&2\b\u0010/\u001a\u0004\u0018\u000100H\u0002R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirReturnsImpliesAnalyzer;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "()V", "containingProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "getContainingProperty", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "correspondingParameterType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCorrespondingParameterType", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "analyze", "", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "logicSystem", "Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "isWrongConditionOnNode", "", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/KtConditionalEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeConditionalEffectDeclaration;", "effect", "Lorg/jetbrains/kotlin/contracts/description/KtReturnsEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeReturnsEffectDeclaration;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "dataFlowInfo", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", "canBeTrueFor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ModuleXmlParser.TYPE, "collectBranchExits", "", "nodes", "", "isTrueFor", "value", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReturnsImpliesAnalyzer extends FirControlFlowChecker {
    public static final FirReturnsImpliesAnalyzer INSTANCE = new FirReturnsImpliesAnalyzer();

    /* JADX INFO: compiled from: FirReturnsImpliesAnalyzer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Operation.values().length];
            try {
                iArr[Operation.EqTrue.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Operation.EqFalse.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Operation.EqNull.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Operation.NotEqNull.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirReturnsImpliesAnalyzer() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker
    public void analyze(ControlFlowGraph graph, DiagnosticReporter reporter, CheckerContext context) {
        Intrinsics.checkNotNullParameter(graph, "graph");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
        analyze(graph, reporter, context, new LogicSystem(typeContext) { // from class: org.jetbrains.kotlin.fir.analysis.cfa.FirReturnsImpliesAnalyzer$analyze$logicSystem$1
            @Override // org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem
            public VariableStorageImpl getVariableStorage() {
                throw new IllegalStateException("shouldn't be called");
            }
        });
    }

    private final void analyze(ControlFlowGraph graph, DiagnosticReporter reporter, CheckerContext context, LogicSystem logicSystem) {
        List<FirEffectDeclaration> effects;
        FirControlFlowGraphReference controlFlowGraphReference;
        DataFlowInfo dataFlowInfo;
        Iterator<ControlFlowGraph> it = graph.getSubGraphs().iterator();
        while (it.hasNext()) {
            analyze(it.next(), reporter, context);
        }
        FirDeclaration declaration = graph.getDeclaration();
        FirFunction firFunction = declaration instanceof FirFunction ? (FirFunction) declaration : null;
        if (firFunction != null && (firFunction instanceof FirContractDescriptionOwner)) {
            FirContractDescriptionOwner firContractDescriptionOwner = (FirContractDescriptionOwner) firFunction;
            if (firContractDescriptionOwner.getContractDescription().getSource() == null || (effects = ContractUtilsKt.getEffects(firContractDescriptionOwner.getContractDescription())) == null || (controlFlowGraphReference = firFunction.getControlFlowGraphReference()) == null || (dataFlowInfo = FirControlFlowGraphReferenceImplKt.getDataFlowInfo(controlFlowGraphReference)) == null) {
                return;
            }
            Iterator<FirEffectDeclaration> it2 = effects.iterator();
            while (it2.hasNext()) {
                KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect = it2.next().getEffect();
                KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktConditionalEffectDeclaration = effect instanceof KtConditionalEffectDeclaration ? (KtConditionalEffectDeclaration) effect : null;
                if (ktConditionalEffectDeclaration != null) {
                    KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect2 = ktConditionalEffectDeclaration.getEffect();
                    KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktReturnsEffectDeclaration = effect2 instanceof KtReturnsEffectDeclaration ? (KtReturnsEffectDeclaration) effect2 : null;
                    if (ktReturnsEffectDeclaration != null) {
                        List<CFGNode<?>> previousCfgNodes = CfgUtilsKt.getPreviousCfgNodes(graph.getExitNode());
                        if (!(previousCfgNodes instanceof Collection) || !previousCfgNodes.isEmpty()) {
                            Iterator<T> it3 = previousCfgNodes.iterator();
                            while (true) {
                                if (!it3.hasNext()) {
                                    break;
                                } else if (INSTANCE.isWrongConditionOnNode((CFGNode) it3.next(), ktConditionalEffectDeclaration, ktReturnsEffectDeclaration, firFunction, logicSystem, dataFlowInfo, context)) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firContractDescriptionOwner.getContractDescription().getSource(), FirErrors.INSTANCE.getWRONG_IMPLIES_CONDITION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                    break;
                                }
                            }
                        } else {
                            break;
                            break;
                        }
                        firFunction = firFunction;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, org.jetbrains.kotlin.fir.resolve.dfa.PersistentFlow] */
    /* JADX WARN: Type inference failed for: r1v19, types: [T, org.jetbrains.kotlin.fir.resolve.dfa.PersistentFlow] */
    private final boolean isWrongConditionOnNode(CFGNode<?> node, KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic> effectDeclaration, KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect, FirFunction function, final LogicSystem logicSystem, DataFlowInfo dataFlowInfo, CheckerContext context) {
        boolean zIsSubtypeOf;
        FirFunctionSymbol<? extends FirFunction> symbol;
        FirProperty containingProperty;
        FirTypeRef typeRef;
        BuiltinTypes builtinTypes = context.getSession().getBuiltinTypes();
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
        boolean z = false;
        boolean z2 = (node instanceof JumpNode) && (((JumpNode) node).getFir() instanceof FirReturnExpression);
        FirExpression fir = node.getFir();
        if (z2) {
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirReturnExpression");
            fir = ((FirReturnExpression) fir).getResult();
        }
        FirExpression firExpression = fir instanceof FirExpression ? (FirExpression) fir : null;
        ConeKotlinType coneType = (firExpression == null || (typeRef = firExpression.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
        if (Intrinsics.areEqual(coneType, builtinTypes.getNothingType().getType())) {
            return false;
        }
        if (z2 && (fir instanceof FirWhenExpression)) {
            List listCollectBranchExits$default = collectBranchExits$default(this, node, null, 1, null);
            if ((listCollectBranchExits$default instanceof Collection) && listCollectBranchExits$default.isEmpty()) {
                return false;
            }
            Iterator it = listCollectBranchExits$default.iterator();
            while (it.hasNext()) {
                if (INSTANCE.isWrongConditionOnNode((CFGNode) it.next(), effectDeclaration, effect, function, logicSystem, dataFlowInfo, context)) {
                    return true;
                }
            }
            return false;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = node.getFlow();
        Operation operation = ContractsKt.toOperation(effect.getValue());
        if (operation != null) {
            if (fir instanceof FirConstExpression) {
                if (!isTrueFor(operation, ((FirConstExpression) fir).getValue())) {
                    return false;
                }
            } else {
                if (coneType != null && !canBeTrueFor(operation, context.getSession(), coneType)) {
                    return false;
                }
                VariableStorage variableStorage = dataFlowInfo.getVariableStorage();
                Intrinsics.checkNotNull(variableStorage, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.VariableStorageImpl");
                DataFlowVariable orCreateIfReal = ((VariableStorageImpl) variableStorage).getOrCreateIfReal((Flow) objectRef.element, fir);
                if (orCreateIfReal != null) {
                    Map<RealVariable, TypeStatement> mapApproveOperationStatement = logicSystem.approveOperationStatement((PersistentFlow) objectRef.element, new OperationStatement(orCreateIfReal, operation));
                    if (!mapApproveOperationStatement.isEmpty()) {
                        MutableFlow mutableFlowFork = ((PersistentFlow) objectRef.element).fork();
                        logicSystem.addTypeStatements(mutableFlowFork, mapApproveOperationStatement);
                        objectRef.element = mutableFlowFork.freeze();
                    }
                }
            }
        }
        Set<RealVariable> knownVariables = ((PersistentFlow) objectRef.element).getKnownVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(knownVariables, 10)), 16));
        for (Object obj : knownVariables) {
            linkedHashMap.put(((RealVariable) obj).getIdentifier(), obj);
        }
        int size = function.getValueParameters().size() + 1;
        RealVariable[] realVariableArr = new RealVariable[size];
        int i = 0;
        while (i < size) {
            if (i > 0) {
                symbol = function.getValueParameters().get(i - 1).getSymbol();
            } else {
                FirPropertySymbol symbol2 = (!(function.getSymbol() instanceof FirPropertyAccessorSymbol) || (containingProperty = INSTANCE.getContainingProperty(context)) == null) ? null : containingProperty.getSymbol();
                if (symbol2 != null) {
                    symbol = symbol2;
                } else {
                    symbol = function.getSymbol();
                }
            }
            Identifier identifier = new Identifier(symbol, null, null);
            RealVariable realVariable = (RealVariable) linkedHashMap.get(identifier);
            if (realVariable == null) {
                realVariable = new RealVariable(identifier, i == 0, null, i, PropertyStability.STABLE_VALUE);
            }
            realVariableArr[i] = realVariable;
            i++;
        }
        Map<RealVariable, TypeStatement> mapApproveContractStatement = ContractsKt.approveContractStatement(logicSystem, effectDeclaration.getCondition(), realVariableArr, null, new Function1<OperationStatement, Map<RealVariable, ? extends TypeStatement>>() { // from class: org.jetbrains.kotlin.fir.analysis.cfa.FirReturnsImpliesAnalyzer$isWrongConditionOnNode$conditionStatements$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Map<RealVariable, TypeStatement> invoke(OperationStatement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return logicSystem.approveOperationStatement(objectRef.element, it2);
            }
        });
        if (mapApproveContractStatement == null) {
            return true;
        }
        Collection<TypeStatement> collectionValues = mapApproveContractStatement.values();
        if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
            z = true;
        } else {
            for (TypeStatement typeStatement : collectionValues) {
                ConeKotlinType correspondingParameterType = INSTANCE.getCorrespondingParameterType(typeStatement.getVariable().getIdentifier().getSymbol());
                if (correspondingParameterType == null) {
                    zIsSubtypeOf = true;
                } else {
                    ConeInferenceContext coneInferenceContext = typeContext;
                    zIsSubtypeOf = TypeUtilsKt.isSubtypeOf(UtilKt.smartCastedType(((PersistentFlow) objectRef.element).getTypeStatement(typeStatement.getVariable()), coneInferenceContext, correspondingParameterType), typeContext, UtilKt.smartCastedType(typeStatement, coneInferenceContext, correspondingParameterType));
                }
                if (!zIsSubtypeOf) {
                }
            }
            z = true;
        }
        return !z;
    }

    private final boolean canBeTrueFor(Operation operation, FirSession firSession, ConeKotlinType coneKotlinType) {
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 1 || i == 2) {
            return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(firSession), (KotlinTypeMarker) firSession.getBuiltinTypes().getBooleanType().getType(), (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null);
        }
        if (i == 3) {
            return FirTypeUtilsKt.getCanBeNull(coneKotlinType);
        }
        if (i == 4) {
            return !ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinType);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean isTrueFor(Operation operation, Object obj) {
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 1) {
            return Intrinsics.areEqual(obj, (Object) true);
        }
        if (i == 2) {
            return Intrinsics.areEqual(obj, (Object) false);
        }
        if (i != 3) {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            if (obj != null) {
                return true;
            }
        } else if (obj == null) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List collectBranchExits$default(FirReturnsImpliesAnalyzer firReturnsImpliesAnalyzer, CFGNode cFGNode, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = new ArrayList();
        }
        return firReturnsImpliesAnalyzer.collectBranchExits(cFGNode, list);
    }

    private final List<CFGNode<?>> collectBranchExits(CFGNode<?> cFGNode, List<CFGNode<?>> list) {
        if (cFGNode instanceof BlockExitNode) {
            CollectionsKt.addAll(list, CfgUtilsKt.getPreviousCfgNodes(cFGNode));
        } else {
            Iterator<T> it = CfgUtilsKt.getPreviousCfgNodes(cFGNode).iterator();
            while (it.hasNext()) {
                INSTANCE.collectBranchExits((CFGNode) it.next(), list);
            }
        }
        return list;
    }

    private final FirProperty getContainingProperty(CheckerContext checkerContext) {
        FirDeclaration firDeclarationPrevious;
        List<FirDeclaration> containingDeclarations = checkerContext.getContainingDeclarations();
        ListIterator<FirDeclaration> listIterator = containingDeclarations.listIterator(containingDeclarations.size());
        do {
            if (!listIterator.hasPrevious()) {
                firDeclarationPrevious = null;
                break;
            }
            firDeclarationPrevious = listIterator.previous();
        } while (!(firDeclarationPrevious instanceof FirProperty));
        if (firDeclarationPrevious instanceof FirProperty) {
            return (FirProperty) firDeclarationPrevious;
        }
        return null;
    }

    private final ConeKotlinType getCorrespondingParameterType(FirBasedSymbol<?> firBasedSymbol) {
        FirResolvedTypeRef resolvedReceiverTypeRef;
        if (firBasedSymbol instanceof FirValueParameterSymbol) {
            return ((FirValueParameterSymbol) firBasedSymbol).getResolvedReturnType();
        }
        if (!(firBasedSymbol instanceof FirCallableSymbol) || (resolvedReceiverTypeRef = ((FirCallableSymbol) firBasedSymbol).getResolvedReceiverTypeRef()) == null) {
            return null;
        }
        return FirTypeUtilsKt.getConeType(resolvedReceiverTypeRef);
    }
}
