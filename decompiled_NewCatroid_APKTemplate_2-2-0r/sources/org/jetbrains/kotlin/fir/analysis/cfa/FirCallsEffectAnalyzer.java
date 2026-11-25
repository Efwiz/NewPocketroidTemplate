package org.jetbrains.kotlin.fir.analysis.cfa;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.cfa.util.CfgTraverserKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.CfgUtilsKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoCollectorKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.TraverseDirection;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.InitBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.InitBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableAssignmentNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableDeclarationNode;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableCollection;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.CallableId;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0004,-./B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J<\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002J\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u0012*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u0012*\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J*\u0010 \u001a\u0016\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#\u0018\u00010!j\u0004\u0018\u0001`$*\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0016\u0010%\u001a\u00020\f*\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(H\u0002J\f\u0010)\u001a\u00020\f*\u00020*H\u0002J\u0010\u0010+\u001a\u0004\u0018\u00010\u0017*\u0004\u0018\u00010\u001bH\u0002¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "()V", "analyze", "", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "investigate", "", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "requiredRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "referenceToSymbol", "reference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getArgumentCallsEffect", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getParameterCallsEffect", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "index", "", "getParameterCallsEffectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeCallsEffectDeclaration;", "isFunctionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isInPlaceLambda", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "toQualifiedReference", "CapturedLambdaFinder", "IllegalScopeContext", "InvocationDataCollector", "LambdaInvocationInfo", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallsEffectAnalyzer extends FirControlFlowChecker {
    public static final FirCallsEffectAnalyzer INSTANCE = new FirCallsEffectAnalyzer();

    private FirCallsEffectAnalyzer() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker
    public void analyze(ControlFlowGraph graph, DiagnosticReporter reporter, CheckerContext context) {
        KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> parameterCallsEffectDeclaration;
        KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> parameterCallsEffectDeclaration2;
        Intrinsics.checkNotNullParameter(graph, "graph");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<ControlFlowGraph> it = graph.getSubGraphs().iterator();
        while (it.hasNext()) {
            analyze(it.next(), reporter, context);
        }
        FirSession session = context.getSession();
        FirDeclaration declaration = graph.getDeclaration();
        FirFunction firFunction = declaration instanceof FirFunction ? (FirFunction) declaration : null;
        if (firFunction != null && (firFunction instanceof FirContractDescriptionOwner)) {
            FirContractDescriptionOwner firContractDescriptionOwner = (FirContractDescriptionOwner) firFunction;
            List<FirEffectDeclaration> effects = ContractUtilsKt.getEffects(firContractDescriptionOwner.getContractDescription());
            if (effects != null) {
                List<FirEffectDeclaration> list = effects;
                if ((list instanceof Collection) && list.isEmpty()) {
                    return;
                }
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    if (((FirEffectDeclaration) it2.next()).getEffect() instanceof KtCallsEffectDeclaration) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        int i = 0;
                        for (Object obj : firFunction.getValueParameters()) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            FirValueParameter firValueParameter = (FirValueParameter) obj;
                            FirCallsEffectAnalyzer firCallsEffectAnalyzer = INSTANCE;
                            if (firCallsEffectAnalyzer.isFunctionTypeRef(firValueParameter.getReturnTypeRef(), session) && (parameterCallsEffectDeclaration2 = firCallsEffectAnalyzer.getParameterCallsEffectDeclaration(firContractDescriptionOwner.getContractDescription(), i)) != null) {
                                linkedHashMap.put(firValueParameter.getSymbol(), parameterCallsEffectDeclaration2);
                            }
                            i = i2;
                        }
                        FirReceiverParameter receiverParameter = firFunction.getReceiverParameter();
                        if (isFunctionTypeRef(receiverParameter != null ? receiverParameter.getTypeRef() : null, session) && (parameterCallsEffectDeclaration = getParameterCallsEffectDeclaration(firContractDescriptionOwner.getContractDescription(), -1)) != null) {
                            linkedHashMap.put(firFunction.getSymbol(), parameterCallsEffectDeclaration);
                        }
                        if (linkedHashMap.isEmpty()) {
                            return;
                        }
                        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                        graph.traverse(new CapturedLambdaFinder(firFunction), new IllegalScopeContext(linkedHashMap.keySet(), linkedHashMap2));
                        for (Map.Entry entry : linkedHashMap2.entrySet()) {
                            FirBasedSymbol firBasedSymbol = (FirBasedSymbol) entry.getKey();
                            List list2 = (List) entry.getValue();
                            CheckerContext checkerContext = context;
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firContractDescriptionOwner.getContractDescription().getSource(), FirErrors.INSTANCE.getLEAKED_IN_PLACE_LAMBDA(), firBasedSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                            Iterator it3 = list2.iterator();
                            while (it3.hasNext()) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, (KtSourceElement) it3.next(), FirErrors.INSTANCE.getLEAKED_IN_PLACE_LAMBDA(), firBasedSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                            }
                        }
                        TraverseDirection traverseDirection = TraverseDirection.Forward;
                        Set setKeySet = linkedHashMap.keySet();
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        for (Object obj2 : setKeySet) {
                            if (!linkedHashMap2.containsKey((FirBasedSymbol) obj2)) {
                                linkedHashSet.add(obj2);
                            }
                        }
                        Map mapCollectDataForNode = CfgTraverserKt.collectDataForNode(graph, traverseDirection, new InvocationDataCollector(linkedHashSet));
                        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                            FirBasedSymbol<?> firBasedSymbol2 = (FirBasedSymbol) entry2.getKey();
                            KtCallsEffectDeclaration ktCallsEffectDeclaration = (KtCallsEffectDeclaration) entry2.getValue();
                            Iterator<T> it4 = CfgUtilsKt.getPreviousCfgNodes(graph.getExitNode()).iterator();
                            while (it4.hasNext()) {
                                CFGNode cFGNode = (CFGNode) it4.next();
                                EventOccurrencesRange kind = ktCallsEffectDeclaration.getKind();
                                Iterator<E> it5 = ((ImmutableCollection) ((PersistentMap) MapsKt.getValue(mapCollectDataForNode, cFGNode)).values()).iterator();
                                while (it5.hasNext()) {
                                    if (INSTANCE.investigate((LambdaInvocationInfo) it5.next(), firBasedSymbol2, kind, firContractDescriptionOwner, reporter, context)) {
                                        break;
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    private final boolean investigate(LambdaInvocationInfo info, FirBasedSymbol<?> symbol, EventOccurrencesRange requiredRange, FirContractDescriptionOwner function, DiagnosticReporter reporter, CheckerContext context) {
        EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) info.get((Object) symbol);
        if (eventOccurrencesRange == null) {
            eventOccurrencesRange = EventOccurrencesRange.ZERO;
        }
        EventOccurrencesRange eventOccurrencesRange2 = eventOccurrencesRange;
        if (requiredRange.contains(eventOccurrencesRange2)) {
            return false;
        }
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) function.getContractDescription().getSource(), (KtDiagnosticFactory3<FirBasedSymbol<?>, EventOccurrencesRange, EventOccurrencesRange>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getWRONG_INVOCATION_KIND()), symbol, requiredRange, eventOccurrencesRange2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
        return true;
    }

    /* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u001c\u0010\u0005\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006¢\u0006\u0002\u0010\tJ2\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\b2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018H\u0086\bø\u0001\u0000J\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000eJ\u0006\u0010\u001b\u001a\u00020\u0013R\u0018\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R$\u0010\u0005\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$IllegalScopeContext;", "", "functionalTypeSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "leakedSymbols", "", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "(Ljava/util/Set;Ljava/util/Map;)V", "illegalScopeDepth", "", "Ljava/lang/Integer;", "inIllegalScope", "", "getInIllegalScope", "()Z", "scopeDepth", "checkExpressionForLeakedSymbols", "", "fir", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "illegalUsage", "Lkotlin/Function0;", "enterScope", "legal", "exitScope", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IllegalScopeContext {
        private final Set<FirBasedSymbol<?>> functionalTypeSymbols;
        private Integer illegalScopeDepth;
        private final Map<FirBasedSymbol<?>, List<KtSourceElement>> leakedSymbols;
        private int scopeDepth;

        /* JADX WARN: Multi-variable type inference failed */
        public IllegalScopeContext(Set<? extends FirBasedSymbol<?>> functionalTypeSymbols, Map<FirBasedSymbol<?>, List<KtSourceElement>> leakedSymbols) {
            Intrinsics.checkNotNullParameter(functionalTypeSymbols, "functionalTypeSymbols");
            Intrinsics.checkNotNullParameter(leakedSymbols, "leakedSymbols");
            this.functionalTypeSymbols = functionalTypeSymbols;
            this.leakedSymbols = leakedSymbols;
        }

        public final boolean getInIllegalScope() {
            return this.illegalScopeDepth != null;
        }

        public final void enterScope(boolean legal) {
            int i = this.scopeDepth + 1;
            this.scopeDepth = i;
            if (this.illegalScopeDepth != null || legal) {
                return;
            }
            this.illegalScopeDepth = Integer.valueOf(i);
        }

        public final void exitScope() {
            int i = this.scopeDepth;
            Integer num = this.illegalScopeDepth;
            if (num != null && i == num.intValue()) {
                this.illegalScopeDepth = null;
            }
            this.scopeDepth--;
        }

        public static /* synthetic */ void checkExpressionForLeakedSymbols$default(IllegalScopeContext illegalScopeContext, FirExpression firExpression, KtSourceElement ktSourceElement, Function0 illegalUsage, int i, Object obj) {
            if ((i & 2) != 0) {
                ktSourceElement = firExpression != null ? firExpression.getSource() : null;
            }
            if ((i & 4) != 0) {
                illegalUsage = new Function0<Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.cfa.FirCallsEffectAnalyzer$IllegalScopeContext$checkExpressionForLeakedSymbols$1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Boolean invoke() {
                        return false;
                    }
                };
            }
            Intrinsics.checkNotNullParameter(illegalUsage, "illegalUsage");
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(firExpression));
            if (firBasedSymbolReferenceToSymbol == null || !illegalScopeContext.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol)) {
                return;
            }
            if (illegalScopeContext.getInIllegalScope() || ((Boolean) illegalUsage.invoke()).booleanValue()) {
                Map map = illegalScopeContext.leakedSymbols;
                Object obj2 = map.get(firBasedSymbolReferenceToSymbol);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    map.put(firBasedSymbolReferenceToSymbol, obj2);
                }
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj2, ktSourceElement);
            }
        }

        public final void checkExpressionForLeakedSymbols(FirExpression fir, KtSourceElement source, Function0<Boolean> illegalUsage) {
            Intrinsics.checkNotNullParameter(illegalUsage, "illegalUsage");
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(fir));
            if (firBasedSymbolReferenceToSymbol == null || !this.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol)) {
                return;
            }
            if (getInIllegalScope() || illegalUsage.invoke().booleanValue()) {
                Map map = this.leakedSymbols;
                Object obj = map.get(firBasedSymbolReferenceToSymbol);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    map.put(firBasedSymbolReferenceToSymbol, obj);
                }
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
            }
        }
    }

    /* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u001c\u0010\u0015\u001a\u00020\u00022\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0017\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001c2\u0006\u0010\f\u001a\u00020\u0003H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$CapturedLambdaFinder;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "", "Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$IllegalScopeContext;", "rootFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)V", "getRootFunction", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "visitFunctionCallNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "data", "visitFunctionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "visitFunctionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "visitInitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "visitInitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "visitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "visitPropertyInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "visitPropertyInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CapturedLambdaFinder extends ControlFlowGraphVisitor<Unit, IllegalScopeContext> {
        private final FirFunction rootFunction;

        /* JADX INFO: renamed from: visitNode, reason: avoid collision after fix types in other method */
        public void visitNode2(CFGNode<?> node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        public CapturedLambdaFinder(FirFunction rootFunction) {
            Intrinsics.checkNotNullParameter(rootFunction, "rootFunction");
            this.rootFunction = rootFunction;
        }

        public final FirFunction getRootFunction() {
            return this.rootFunction;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionCallNode(FunctionCallNode functionCallNode, IllegalScopeContext illegalScopeContext) {
            visitFunctionCallNode2(functionCallNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionEnterNode(FunctionEnterNode functionEnterNode, IllegalScopeContext illegalScopeContext) {
            visitFunctionEnterNode2(functionEnterNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionExitNode(FunctionExitNode functionExitNode, IllegalScopeContext illegalScopeContext) {
            visitFunctionExitNode2(functionExitNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitInitBlockEnterNode(InitBlockEnterNode initBlockEnterNode, IllegalScopeContext illegalScopeContext) {
            visitInitBlockEnterNode2(initBlockEnterNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitInitBlockExitNode(InitBlockExitNode initBlockExitNode, IllegalScopeContext illegalScopeContext) {
            visitInitBlockExitNode2(initBlockExitNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitNode(CFGNode cFGNode, IllegalScopeContext illegalScopeContext) {
            visitNode2((CFGNode<?>) cFGNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyInitializerEnterNode(PropertyInitializerEnterNode propertyInitializerEnterNode, IllegalScopeContext illegalScopeContext) {
            visitPropertyInitializerEnterNode2(propertyInitializerEnterNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyInitializerExitNode(PropertyInitializerExitNode propertyInitializerExitNode, IllegalScopeContext illegalScopeContext) {
            visitPropertyInitializerExitNode2(propertyInitializerExitNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitVariableAssignmentNode(VariableAssignmentNode variableAssignmentNode, IllegalScopeContext illegalScopeContext) {
            visitVariableAssignmentNode2(variableAssignmentNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Unit visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode, IllegalScopeContext illegalScopeContext) {
            visitVariableDeclarationNode2(variableDeclarationNode, illegalScopeContext);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionEnterNode, reason: avoid collision after fix types in other method */
        public void visitFunctionEnterNode2(FunctionEnterNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.enterScope(node.getFir() == this.rootFunction || FirCallsEffectAnalyzer.INSTANCE.isInPlaceLambda(node.getFir()));
        }

        /* JADX INFO: renamed from: visitFunctionExitNode, reason: avoid collision after fix types in other method */
        public void visitFunctionExitNode2(FunctionExitNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.exitScope();
        }

        /* JADX INFO: renamed from: visitPropertyInitializerEnterNode, reason: avoid collision after fix types in other method */
        public void visitPropertyInitializerEnterNode2(PropertyInitializerEnterNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.enterScope(false);
            FirExpression initializer = node.getFir().getInitializer();
            KtSourceElement source = initializer != null ? initializer.getSource() : null;
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(initializer));
            if (firBasedSymbolReferenceToSymbol != null && data.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol) && data.getInIllegalScope()) {
                Map map = data.leakedSymbols;
                Object obj = map.get(firBasedSymbolReferenceToSymbol);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    map.put(firBasedSymbolReferenceToSymbol, obj);
                }
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
            }
        }

        /* JADX INFO: renamed from: visitPropertyInitializerExitNode, reason: avoid collision after fix types in other method */
        public void visitPropertyInitializerExitNode2(PropertyInitializerExitNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.exitScope();
        }

        /* JADX INFO: renamed from: visitInitBlockEnterNode, reason: avoid collision after fix types in other method */
        public void visitInitBlockEnterNode2(InitBlockEnterNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.enterScope(false);
        }

        /* JADX INFO: renamed from: visitInitBlockExitNode, reason: avoid collision after fix types in other method */
        public void visitInitBlockExitNode2(InitBlockExitNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            data.exitScope();
        }

        /* JADX INFO: renamed from: visitVariableAssignmentNode, reason: avoid collision after fix types in other method */
        public void visitVariableAssignmentNode2(VariableAssignmentNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            FirExpression rValue = node.getFir().getRValue();
            KtSourceElement source = rValue != null ? rValue.getSource() : null;
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(rValue));
            if (firBasedSymbolReferenceToSymbol == null || !data.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol)) {
                return;
            }
            data.getInIllegalScope();
            Map map = data.leakedSymbols;
            Object obj = map.get(firBasedSymbolReferenceToSymbol);
            if (obj == null) {
                obj = (List) new ArrayList();
                map.put(firBasedSymbolReferenceToSymbol, obj);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
        }

        /* JADX INFO: renamed from: visitVariableDeclarationNode, reason: avoid collision after fix types in other method */
        public void visitVariableDeclarationNode2(VariableDeclarationNode node, IllegalScopeContext data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            FirExpression initializer = node.getFir().getInitializer();
            KtSourceElement source = initializer != null ? initializer.getSource() : null;
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(initializer));
            if (firBasedSymbolReferenceToSymbol == null || !data.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol)) {
                return;
            }
            data.getInIllegalScope();
            Map map = data.leakedSymbols;
            Object obj = map.get(firBasedSymbolReferenceToSymbol);
            if (obj == null) {
                obj = (List) new ArrayList();
                map.put(firBasedSymbolReferenceToSymbol, obj);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
        }

        /* JADX WARN: Code duplicated, block: B:30:0x0082  */
        /* JADX WARN: Code duplicated, block: B:32:0x008c  */
        /* JADX INFO: renamed from: visitFunctionCallNode, reason: avoid collision after fix types in other method */
        public void visitFunctionCallNode2(FunctionCallNode node, IllegalScopeContext data) {
            KtSourceElement source;
            Map map;
            Object obj;
            CallableId callableId;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(node.getFir());
            FirFunctionSymbol firFunctionSymbol = resolvedCallableSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedCallableSymbol : null;
            FirResolvedContractDescription resolvedContractDescription = firFunctionSymbol != null ? firFunctionSymbol.getResolvedContractDescription() : null;
            FirExpression explicitReceiver = node.getFir().getExplicitReceiver();
            if (explicitReceiver == null || (source = explicitReceiver.getSource()) == null) {
                source = node.getFir().getSource();
            }
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(node.getFir().getExplicitReceiver()));
            if (firBasedSymbolReferenceToSymbol != null && data.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol)) {
                if (data.getInIllegalScope()) {
                    map = data.leakedSymbols;
                    obj = map.get(firBasedSymbolReferenceToSymbol);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        map.put(firBasedSymbolReferenceToSymbol, obj);
                    }
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
                } else if (firFunctionSymbol == null || (callableId = firFunctionSymbol.getCallableId()) == null || !CallableIdUtilsKt.isInvoke(callableId)) {
                    if ((resolvedContractDescription != null ? FirCallsEffectAnalyzer.INSTANCE.getParameterCallsEffect(resolvedContractDescription, -1) : null) == null) {
                        map = data.leakedSymbols;
                        obj = map.get(firBasedSymbolReferenceToSymbol);
                        if (obj == null) {
                            obj = (List) new ArrayList();
                            map.put(firBasedSymbolReferenceToSymbol, obj);
                        }
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj, source);
                    }
                }
            }
            Iterator<FirExpression> it = node.getFir().getArgumentList().getArguments().iterator();
            while (it.hasNext()) {
                FirExpression next = it.next();
                KtSourceElement source2 = next != null ? next.getSource() : null;
                FirBasedSymbol firBasedSymbolReferenceToSymbol2 = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(next));
                if (firBasedSymbolReferenceToSymbol2 != null && data.functionalTypeSymbols.contains(firBasedSymbolReferenceToSymbol2) && (data.getInIllegalScope() || FirCallsEffectAnalyzer.INSTANCE.getArgumentCallsEffect(node.getFir(), next) == null)) {
                    Map map2 = data.leakedSymbols;
                    Object obj2 = map2.get(firBasedSymbolReferenceToSymbol2);
                    if (obj2 == null) {
                        obj2 = (List) new ArrayList();
                        map2.put(firBasedSymbolReferenceToSymbol2, obj2);
                    }
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull((Collection) obj2, source2);
                }
            }
        }
    }

    /* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u000b2\u0012\u0012\u0004\u0012\u00020\u0000\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001:\u0001\u000bB\u001f\u0012\u0018\b\u0002\u0010\u0003\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R0\u0010\u0007\u001a\u001e\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00000\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lkotlinx/collections/immutable/PersistentMap;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lkotlin/Function1;", "getConstructor", "()Lkotlin/jvm/functions/Function1;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LambdaInvocationInfo extends EventOccurrencesRangeInfo<LambdaInvocationInfo, FirBasedSymbol<?>> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final LambdaInvocationInfo EMPTY = new LambdaInvocationInfo(0 == true ? 1 : 0, 1, 0 == true ? 1 : 0);
        private final Function1<PersistentMap<FirBasedSymbol<?>, ? extends EventOccurrencesRange>, LambdaInvocationInfo> constructor;

        /* JADX WARN: Multi-variable type inference failed */
        public LambdaInvocationInfo() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof FirBasedSymbol) {
                return containsKey((FirBasedSymbol<?>) obj);
            }
            return false;
        }

        public /* bridge */ boolean containsKey(FirBasedSymbol<?> firBasedSymbol) {
            return super.containsKey(firBasedSymbol);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Set entrySet() {
            return getEntries();
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ EventOccurrencesRange get(Object obj) {
            if (obj instanceof FirBasedSymbol) {
                return get((FirBasedSymbol<?>) obj);
            }
            return null;
        }

        public /* bridge */ EventOccurrencesRange get(FirBasedSymbol<?> firBasedSymbol) {
            return (EventOccurrencesRange) super.get(firBasedSymbol);
        }

        public final /* bridge */ EventOccurrencesRange getOrDefault(Object obj, EventOccurrencesRange eventOccurrencesRange) {
            return !(obj instanceof FirBasedSymbol) ? eventOccurrencesRange : getOrDefault((FirBasedSymbol<?>) obj, eventOccurrencesRange);
        }

        public /* bridge */ EventOccurrencesRange getOrDefault(FirBasedSymbol<?> firBasedSymbol, EventOccurrencesRange eventOccurrencesRange) {
            return (EventOccurrencesRange) super.getOrDefault((Object) firBasedSymbol, (Object) eventOccurrencesRange);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Set keySet() {
            return getKeys();
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Collection values() {
            return getValues();
        }

        public /* synthetic */ LambdaInvocationInfo(PersistentMap persistentMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? ExtensionsKt.persistentMapOf(new Pair[0]) : persistentMap);
        }

        /* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final LambdaInvocationInfo getEMPTY() {
                return LambdaInvocationInfo.EMPTY;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LambdaInvocationInfo(PersistentMap<FirBasedSymbol<?>, ? extends EventOccurrencesRange> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
            this.constructor = FirCallsEffectAnalyzer$LambdaInvocationInfo$constructor$1.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
        protected Function1<PersistentMap<FirBasedSymbol<?>, ? extends EventOccurrencesRange>, LambdaInvocationInfo> getConstructor() {
            return this.constructor;
        }
    }

    /* JADX INFO: compiled from: FirCallsEffectAnalyzer.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u001d2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001dB\u0017\u0012\u0010\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004¢\u0006\u0002\u0010\u0006J#\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0001J8\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0016\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\nH\u0016J<\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n*\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002JE\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n*\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u001cH\u0082\bR$\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$InvocationDataCollector;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowGraphVisitor;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/FirCallsEffectAnalyzer$LambdaInvocationInfo;", "functionalTypeSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Ljava/util/Set;)V", "emptyInfo", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/PathAwareLambdaInvocationInfo;", "getEmptyInfo", "()Lkotlinx/collections/immutable/PersistentMap;", "getFunctionalTypeSymbols", "()Ljava/util/Set;", "collectDataForReference", "", "reference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "visitFunctionCallNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "data", "addInvocationInfo", "range", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "checkReference", "rangeGetter", "Lkotlin/Function0;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InvocationDataCollector extends PathAwareControlFlowGraphVisitor<LambdaInvocationInfo> {
        private static final PersistentMap<EdgeLabel, LambdaInvocationInfo> EMPTY_INFO = ExtensionsKt.persistentMapOf(TuplesKt.to(NormalPath.INSTANCE, LambdaInvocationInfo.INSTANCE.getEMPTY()));
        private final Set<FirBasedSymbol<?>> functionalTypeSymbols;

        public final Set<FirBasedSymbol<?>> getFunctionalTypeSymbols() {
            return this.functionalTypeSymbols;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public InvocationDataCollector(Set<? extends FirBasedSymbol<?>> functionalTypeSymbols) {
            Intrinsics.checkNotNullParameter(functionalTypeSymbols, "functionalTypeSymbols");
            this.functionalTypeSymbols = functionalTypeSymbols;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, LambdaInvocationInfo> getEmptyInfo() {
            return EMPTY_INFO;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, LambdaInvocationInfo> visitFunctionCallNode(FunctionCallNode node, PersistentMap<EdgeLabel, LambdaInvocationInfo> data) {
            EventOccurrencesRange parameterCallsEffect;
            CallableId callableId;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            PersistentMap<EdgeLabel, LambdaInvocationInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, (PersistentMap) data);
            FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(node.getFir());
            FirFunctionSymbol firFunctionSymbol = resolvedCallableSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedCallableSymbol : null;
            FirResolvedContractDescription resolvedContractDescription = firFunctionSymbol != null ? firFunctionSymbol.getResolvedContractDescription() : null;
            FirReference qualifiedReference = FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(node.getFir().getExplicitReceiver());
            if (collectDataForReference(qualifiedReference)) {
                if (firFunctionSymbol == null || (callableId = firFunctionSymbol.getCallableId()) == null || !CallableIdUtilsKt.isInvoke(callableId)) {
                    parameterCallsEffect = FirCallsEffectAnalyzer.INSTANCE.getParameterCallsEffect(resolvedContractDescription, -1);
                    if (parameterCallsEffect == null) {
                        parameterCallsEffect = EventOccurrencesRange.UNKNOWN;
                    }
                } else {
                    parameterCallsEffect = EventOccurrencesRange.EXACTLY_ONCE;
                }
                persistentMapVisitNode = addInvocationInfo(persistentMapVisitNode, qualifiedReference, parameterCallsEffect);
            }
            for (FirExpression firExpression : node.getFir().getArgumentList().getArguments()) {
                FirReference qualifiedReference2 = FirCallsEffectAnalyzer.INSTANCE.toQualifiedReference(firExpression);
                if (collectDataForReference(qualifiedReference2)) {
                    EventOccurrencesRange argumentCallsEffect = FirCallsEffectAnalyzer.INSTANCE.getArgumentCallsEffect(node.getFir(), firExpression);
                    if (argumentCallsEffect == null) {
                        argumentCallsEffect = EventOccurrencesRange.ZERO;
                    }
                    persistentMapVisitNode = addInvocationInfo(persistentMapVisitNode, qualifiedReference2, argumentCallsEffect);
                }
            }
            return persistentMapVisitNode;
        }

        private final boolean collectDataForReference(FirReference reference) {
            return reference != null && CollectionsKt.contains(this.functionalTypeSymbols, FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(reference));
        }

        private final PersistentMap<EdgeLabel, LambdaInvocationInfo> addInvocationInfo(PersistentMap<EdgeLabel, LambdaInvocationInfo> persistentMap, FirReference firReference, EventOccurrencesRange eventOccurrencesRange) {
            FirBasedSymbol firBasedSymbolReferenceToSymbol = FirCallsEffectAnalyzer.INSTANCE.referenceToSymbol(firReference);
            return firBasedSymbolReferenceToSymbol != null ? PropertyInitializationInfoCollectorKt.addRange(persistentMap, firBasedSymbolReferenceToSymbol, eventOccurrencesRange) : persistentMap;
        }
    }

    private final boolean isFunctionTypeRef(FirTypeRef firTypeRef, FirSession firSession) {
        ConeKotlinType coneType;
        return (firTypeRef == null || (coneType = FirTypeUtilsKt.getConeType(firTypeRef)) == null || !FunctionalTypeUtilsKt.isSomeFunctionType(coneType, firSession)) ? false : true;
    }

    private final KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> getParameterCallsEffectDeclaration(FirContractDescription firContractDescription, int i) {
        ArrayList arrayList;
        KtEffectDeclaration ktEffectDeclaration;
        Object next;
        List<FirEffectDeclaration> effects;
        if (firContractDescription == null || (effects = ContractUtilsKt.getEffects(firContractDescription)) == null) {
            arrayList = null;
        } else {
            List<FirEffectDeclaration> list = effects;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(((FirEffectDeclaration) it.next()).getEffect());
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            Iterator it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
                KtEffectDeclaration ktEffectDeclaration2 = (KtEffectDeclaration) next;
                if ((ktEffectDeclaration2 instanceof KtCallsEffectDeclaration) && ((KtCallsEffectDeclaration) ktEffectDeclaration2).getValueParameterReference().getParameterIndex() == i) {
                    break;
                }
            }
            ktEffectDeclaration = (KtEffectDeclaration) next;
        } else {
            ktEffectDeclaration = null;
        }
        if (ktEffectDeclaration instanceof KtCallsEffectDeclaration) {
            return (KtCallsEffectDeclaration) ktEffectDeclaration;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EventOccurrencesRange getArgumentCallsEffect(FirFunctionCall firFunctionCall, FirExpression firExpression) {
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firFunctionCall);
        FirFunctionSymbol firFunctionSymbol = resolvedCallableSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedCallableSymbol : null;
        FirResolvedContractDescription resolvedContractDescription = firFunctionSymbol != null ? firFunctionSymbol.getResolvedContractDescription() : null;
        FirArgumentList argumentList = firFunctionCall.getArgumentList();
        FirResolvedArgumentList firResolvedArgumentList = argumentList instanceof FirResolvedArgumentList ? (FirResolvedArgumentList) argumentList : null;
        if (firFunctionSymbol == null || firResolvedArgumentList == null) {
            return null;
        }
        FirValueParameter firValueParameter = firResolvedArgumentList.getMapping().get(firExpression);
        return getParameterCallsEffect(resolvedContractDescription, CollectionsKt.indexOf((List<? extends FirValueParameterSymbol>) firFunctionSymbol.getValueParameterSymbols(), firValueParameter != null ? firValueParameter.getSymbol() : null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EventOccurrencesRange getParameterCallsEffect(FirContractDescription firContractDescription, int i) {
        KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> parameterCallsEffectDeclaration = getParameterCallsEffectDeclaration(firContractDescription, i);
        if (parameterCallsEffectDeclaration != null) {
            return parameterCallsEffectDeclaration.getKind();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isInPlaceLambda(FirFunction firFunction) {
        if (firFunction instanceof FirAnonymousFunction) {
            FirAnonymousFunction firAnonymousFunction = (FirAnonymousFunction) firFunction;
            if (firAnonymousFunction.isLambda() && firAnonymousFunction.getInvocationKind() != null) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirReference toQualifiedReference(FirExpression firExpression) {
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        if (firQualifiedAccessExpression != null) {
            return firQualifiedAccessExpression.getCalleeReference();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirBasedSymbol<?> referenceToSymbol(FirReference reference) {
        if (reference instanceof FirResolvedNamedReference) {
            return ((FirResolvedNamedReference) reference).getResolvedSymbol();
        }
        if (reference instanceof FirThisReference) {
            return ((FirThisReference) reference).getBoundSymbol();
        }
        return null;
    }
}
