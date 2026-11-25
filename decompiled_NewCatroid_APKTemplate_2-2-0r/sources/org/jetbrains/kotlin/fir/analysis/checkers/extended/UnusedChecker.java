package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.cfa.util.CfgTraverserKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.cfa.util.TraverseDirection;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.QualifiedAccessNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableAssignmentNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableDeclarationNode;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableCollection;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: UnusedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0004\u000f\u0010\u0011\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "()V", "isLoopIterator", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)Z", "analyze", "", "data", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "CfaVisitor", "ValueWritesWithoutReading", "VariableStatus", "VariableStatusInfo", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnusedChecker extends AbstractFirPropertyInitializationChecker {
    public static final UnusedChecker INSTANCE = new UnusedChecker();

    private UnusedChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker
    public void analyze(PropertyInitializationInfoData data, DiagnosticReporter reporter, CheckerContext context) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        data.getGraph().traverse(new CfaVisitor(new ValueWritesWithoutReading(context.getSession(), data.getProperties()).getData(data.getGraph()), reporter, context));
    }

    /* JADX INFO: compiled from: UnusedChecker.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B=\u0012&\u0010\u0002\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b0\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0014\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001aH\u0016R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR1\u0010\u0002\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$CfaVisitor;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", "data", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/PathAwareVariableStatusInfo;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)V", "getContext", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "getData", "()Ljava/util/Map;", "getReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "visitNode", "", "node", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CfaVisitor extends ControlFlowGraphVisitorVoid {
        private final CheckerContext context;
        private final Map<CFGNode<?>, PersistentMap<EdgeLabel, VariableStatusInfo>> data;
        private final DiagnosticReporter reporter;

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitNode(CFGNode<?> node) {
            Intrinsics.checkNotNullParameter(node, "node");
        }

        public final Map<CFGNode<?>, PersistentMap<EdgeLabel, VariableStatusInfo>> getData() {
            return this.data;
        }

        public final DiagnosticReporter getReporter() {
            return this.reporter;
        }

        public final CheckerContext getContext() {
            return this.context;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public CfaVisitor(Map<CFGNode<?>, ? extends PersistentMap<EdgeLabel, VariableStatusInfo>> data, DiagnosticReporter reporter, CheckerContext context) {
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            Intrinsics.checkNotNullParameter(context, "context");
            this.data = data;
            this.reporter = reporter;
            this.context = context;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitVariableAssignmentNode(VariableAssignmentNode node) {
            FirPropertySymbol resolvedPropertySymbol$default;
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMap;
            Intrinsics.checkNotNullParameter(node, "node");
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(node.getFir());
            if (calleeReference == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null || (persistentMap = this.data.get(node)) == null) {
                return;
            }
            Iterator<E> it = ((ImmutableCollection) persistentMap.values()).iterator();
            while (it.hasNext()) {
                VariableStatus variableStatus = (VariableStatus) ((VariableStatusInfo) it.next()).get((Object) resolvedPropertySymbol$default);
                if (variableStatus != null && variableStatus == VariableStatus.ONLY_WRITTEN_NEVER_READ) {
                    KtDiagnosticReportHelpersKt.reportOn$default(this.reporter, node.getFir().getLValue().getSource(), FirErrors.INSTANCE.getASSIGNED_VALUE_IS_NEVER_READ(), this.context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitVariableDeclarationNode(VariableDeclarationNode node) {
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMap;
            Object next;
            Intrinsics.checkNotNullParameter(node, "node");
            FirPropertySymbol symbol = node.getFir().getSymbol();
            if (node.getFir().getSource() == null || UnusedChecker.INSTANCE.isLoopIterator(symbol) || (persistentMap = this.data.get(node)) == null) {
                return;
            }
            Collection collectionValues = persistentMap.values();
            ArrayList arrayList = new ArrayList();
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                VariableStatus variableStatus = (VariableStatus) ((VariableStatusInfo) it.next()).get((Object) symbol);
                if (variableStatus != null) {
                    arrayList.add(variableStatus);
                }
            }
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                next = it2.next();
                while (it2.hasNext()) {
                    next = ((VariableStatus) next).merge((VariableStatus) it2.next());
                }
            } else {
                next = null;
            }
            VariableStatus variableStatus2 = (VariableStatus) next;
            if (variableStatus2 != null) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.BODY_RESOLVE);
                FirProperty firProperty = (FirProperty) symbol.getFir();
                KtSourceElement source = firProperty.getSource();
                if (Intrinsics.areEqual(source != null ? source.getElementType() : null, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                    return;
                }
                if (variableStatus2 == VariableStatus.UNUSED) {
                    FirExpression initializer = node.getFir().getInitializer();
                    FirFunctionCall firFunctionCall = initializer instanceof FirFunctionCall ? (FirFunctionCall) initializer : null;
                    if ((firFunctionCall == null || !FirHelpersKt.isIterator(firFunctionCall)) && !Intrinsics.areEqual((Object) ClassMembersKt.isCatchParameter(node.getFir()), (Object) true)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(this.reporter, source, FirErrors.INSTANCE.getUNUSED_VARIABLE(), this.context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
                if (variableStatus2.getIsRedundantInit()) {
                    FirExpression initializer2 = firProperty.getInitializer();
                    KtDiagnosticReportHelpersKt.reportOn$default(this.reporter, initializer2 != null ? initializer2.getSource() : null, FirErrors.INSTANCE.getVARIABLE_INITIALIZER_IS_REDUNDANT(), this.context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else if (variableStatus2 == VariableStatus.ONLY_WRITTEN_NEVER_READ) {
                    KtDiagnosticReportHelpersKt.reportOn$default(this.reporter, source, FirErrors.INSTANCE.getVARIABLE_NEVER_READ(), this.context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }

    /* JADX INFO: compiled from: UnusedChecker.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\u00002\b\u0010\r\u001a\u0004\u0018\u00010\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatus;", "", "priority", "", "(Ljava/lang/String;II)V", "isRead", "", "()Z", "setRead", "(Z)V", "isRedundantInit", "setRedundantInit", "merge", "variableUseState", "READ", "WRITTEN_AFTER_READ", "ONLY_WRITTEN_NEVER_READ", "UNUSED", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum VariableStatus {
        READ(3),
        WRITTEN_AFTER_READ(2),
        ONLY_WRITTEN_NEVER_READ(1),
        UNUSED(0);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private boolean isRead;
        private boolean isRedundantInit;
        private final int priority;

        public static EnumEntries<VariableStatus> getEntries() {
            return $ENTRIES;
        }

        VariableStatus(int i) {
            this.priority = i;
        }

        /* JADX INFO: renamed from: isRead, reason: from getter */
        public final boolean getIsRead() {
            return this.isRead;
        }

        public final void setRead(boolean z) {
            this.isRead = z;
        }

        /* JADX INFO: renamed from: isRedundantInit, reason: from getter */
        public final boolean getIsRedundantInit() {
            return this.isRedundantInit;
        }

        public final void setRedundantInit(boolean z) {
            this.isRedundantInit = z;
        }

        public final VariableStatus merge(VariableStatus variableUseState) {
            VariableStatus variableStatus = (variableUseState == null || this.priority > variableUseState.priority) ? this : variableUseState;
            boolean z = false;
            variableStatus.isRead = this.isRead || (variableUseState != null && variableUseState.isRead);
            if (this.isRedundantInit && variableUseState != null && variableUseState.isRedundantInit) {
                z = true;
            }
            variableStatus.isRedundantInit = z;
            return variableStatus;
        }
    }

    /* JADX INFO: compiled from: UnusedChecker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000e2\u0014\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000eB\u001b\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0000H\u0016J\u0010\u0010\r\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0000H\u0016R,\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0004\u0012\u00020\u00000\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatus;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "(Lkotlinx/collections/immutable/PersistentMap;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lkotlin/Function1;", "getConstructor", "()Lkotlin/jvm/functions/Function1;", "merge", "other", "plus", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VariableStatusInfo extends ControlFlowInfo<VariableStatusInfo, FirPropertySymbol, VariableStatus> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final VariableStatusInfo EMPTY = new VariableStatusInfo(0 == true ? 1 : 0, 1, 0 == true ? 1 : 0);
        private final Function1<PersistentMap<FirPropertySymbol, ? extends VariableStatus>, VariableStatusInfo> constructor;

        /* JADX WARN: Multi-variable type inference failed */
        public VariableStatusInfo() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof FirPropertySymbol) {
                return containsKey((FirPropertySymbol) obj);
            }
            return false;
        }

        public /* bridge */ boolean containsKey(FirPropertySymbol firPropertySymbol) {
            return super.containsKey(firPropertySymbol);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ boolean containsValue(Object obj) {
            if (obj instanceof VariableStatus) {
                return containsValue((VariableStatus) obj);
            }
            return false;
        }

        public /* bridge */ boolean containsValue(VariableStatus variableStatus) {
            return super.containsValue(variableStatus);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Set entrySet() {
            return getEntries();
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            if (obj instanceof FirPropertySymbol) {
                return get((FirPropertySymbol) obj);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public final /* bridge */ VariableStatus get(Object obj) {
            if (obj instanceof FirPropertySymbol) {
                return get((FirPropertySymbol) obj);
            }
            return null;
        }

        public /* bridge */ VariableStatus get(FirPropertySymbol firPropertySymbol) {
            return (VariableStatus) super.get(firPropertySymbol);
        }

        @Override // java.util.Map
        public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
            return !(obj instanceof FirPropertySymbol) ? obj2 : getOrDefault((FirPropertySymbol) obj, (VariableStatus) obj2);
        }

        public final /* bridge */ VariableStatus getOrDefault(Object obj, VariableStatus variableStatus) {
            return !(obj instanceof FirPropertySymbol) ? variableStatus : getOrDefault((FirPropertySymbol) obj, variableStatus);
        }

        public /* bridge */ VariableStatus getOrDefault(FirPropertySymbol firPropertySymbol, VariableStatus variableStatus) {
            return (VariableStatus) super.getOrDefault((Object) firPropertySymbol, (Object) variableStatus);
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Set keySet() {
            return getKeys();
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
        public /* bridge */ /* synthetic */ Collection values() {
            return getValues();
        }

        public /* synthetic */ VariableStatusInfo(PersistentMap persistentMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? ExtensionsKt.persistentMapOf(new Pair[0]) : persistentMap);
        }

        /* JADX INFO: compiled from: UnusedChecker.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final VariableStatusInfo getEMPTY() {
                return VariableStatusInfo.EMPTY;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public VariableStatusInfo(PersistentMap<FirPropertySymbol, ? extends VariableStatus> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
            this.constructor = UnusedChecker$VariableStatusInfo$constructor$1.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
        protected Function1<PersistentMap<FirPropertySymbol, ? extends VariableStatus>, VariableStatusInfo> getConstructor() {
            return this.constructor;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
        public VariableStatusInfo merge(VariableStatusInfo other) {
            Intrinsics.checkNotNullParameter(other, "other");
            VariableStatusInfo variableStatusInfo = this;
            for (FirPropertySymbol firPropertySymbol : CollectionsKt.union(keySet(), other.keySet())) {
                VariableStatus variableStatus = (VariableStatus) get((Object) firPropertySymbol);
                if (variableStatus == null) {
                    variableStatus = VariableStatus.UNUSED;
                }
                VariableStatus variableStatus2 = (VariableStatus) other.get((Object) firPropertySymbol);
                if (variableStatus2 == null) {
                    variableStatus2 = VariableStatus.UNUSED;
                }
                variableStatusInfo = (VariableStatusInfo) variableStatusInfo.put(firPropertySymbol, variableStatus.merge(variableStatus2));
            }
            return variableStatusInfo;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
        public VariableStatusInfo plus(VariableStatusInfo other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return merge(other);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: UnusedChecker.kt */
    @Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000 02\b\u0012\u0004\u0012\u00020\u00020\u0001:\u00010B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ.\u0010\u000f\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f0\u00102\u0006\u0010\u0012\u001a\u00020\u0013Ja\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0016\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0017\"\u00020\u00072\u0016\u0010\u0018\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019H\u0002¢\u0006\u0002\u0010\u001bJ8\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J8\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0006\u0010!\u001a\u00020\"2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\fH\u0016J<\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\n\u0010!\u001a\u0006\u0012\u0002\b\u00030\u00112\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\fH\u0016J8\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0006\u0010!\u001a\u00020&2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\fH\u0016JI\u0010'\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020)0\u0017\"\u00020)H\u0002¢\u0006\u0002\u0010*J8\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0006\u0010!\u001a\u00020,2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\fH\u0016J8\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\u0006\u0010!\u001a\u00020.2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\fH\u0016J8\u0010/\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f*\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f2\n\u0010!\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0002R$\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u0002`\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$ValueWritesWithoutReading;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowGraphVisitor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatusInfo;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "localProperties", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/Set;)V", "emptyInfo", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/PathAwareVariableStatusInfo;", "getEmptyInfo", "()Lkotlinx/collections/immutable/PersistentMap;", "getData", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", ConfigConstants.CONFIG_KEY_UPDATE, "pathAwareInfo", "symbols", "", "updater", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnusedChecker$VariableStatus;", "(Lkotlinx/collections/immutable/PersistentMap;[Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function1;)Lkotlinx/collections/immutable/PersistentMap;", "visitAnnotation", "dataForNode", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "visitFunctionCallNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "data", "visitNode", "visitQualifiedAccessNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "visitQualifiedAccesses", "qualifiedAccesses", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lkotlinx/collections/immutable/PersistentMap;[Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;)Lkotlinx/collections/immutable/PersistentMap;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "withAnnotationsFrom", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ValueWritesWithoutReading extends PathAwareControlFlowGraphVisitor<VariableStatusInfo> {
        private static final PersistentMap<EdgeLabel, VariableStatusInfo> EMPTY_INFO = ExtensionsKt.persistentMapOf(TuplesKt.to(NormalPath.INSTANCE, VariableStatusInfo.INSTANCE.getEMPTY()));
        private final Set<FirPropertySymbol> localProperties;
        private final FirSession session;

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor, org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public /* bridge */ /* synthetic */ Object visitNode(CFGNode cFGNode, Object obj) {
            return visitNode((CFGNode<?>) cFGNode, (PersistentMap<EdgeLabel, VariableStatusInfo>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ValueWritesWithoutReading(FirSession session, Set<? extends FirPropertySymbol> localProperties) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(localProperties, "localProperties");
            this.session = session;
            this.localProperties = localProperties;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> getEmptyInfo() {
            return EMPTY_INFO;
        }

        public final Map<CFGNode<?>, PersistentMap<EdgeLabel, VariableStatusInfo>> getData(ControlFlowGraph graph) {
            Intrinsics.checkNotNullParameter(graph, "graph");
            return CfgTraverserKt.collectDataForNode(graph, TraverseDirection.Backward, this);
        }

        private final PersistentMap<EdgeLabel, VariableStatusInfo> withAnnotationsFrom(PersistentMap<EdgeLabel, VariableStatusInfo> persistentMap, CFGNode<?> cFGNode) {
            List<FirAnnotation> annotations;
            FirElement fir = cFGNode.getFir();
            FirAnnotationContainer firAnnotationContainer = fir instanceof FirAnnotationContainer ? (FirAnnotationContainer) fir : null;
            if (firAnnotationContainer == null || (annotations = firAnnotationContainer.getAnnotations()) == null) {
                return persistentMap;
            }
            Iterator<T> it = annotations.iterator();
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMapVisitAnnotation = persistentMap;
            while (it.hasNext()) {
                persistentMapVisitAnnotation = visitAnnotation(persistentMapVisitAnnotation, (FirAnnotation) it.next());
            }
            return persistentMapVisitAnnotation == null ? persistentMap : persistentMapVisitAnnotation;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> visitNode(CFGNode<?> node, PersistentMap<EdgeLabel, VariableStatusInfo> data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            return withAnnotationsFrom(super.visitNode(node, (PersistentMap) data), node);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> visitVariableDeclarationNode(final VariableDeclarationNode node, PersistentMap<EdgeLabel, VariableStatusInfo> data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, data);
            KtSourceElement source = node.getFir().getSource();
            return (source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind ? persistentMapVisitNode : update(persistentMapVisitNode, new FirPropertySymbol[]{node.getFir().getSymbol()}, new Function1<VariableStatus, VariableStatus>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.UnusedChecker$ValueWritesWithoutReading$visitVariableDeclarationNode$1

                /* JADX INFO: compiled from: UnusedChecker.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                public /* synthetic */ class WhenMappings {
                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                    static {
                        int[] iArr = new int[UnusedChecker.VariableStatus.values().length];
                        try {
                            iArr[UnusedChecker.VariableStatus.ONLY_WRITTEN_NEVER_READ.ordinal()] = 1;
                        } catch (NoSuchFieldError unused) {
                        }
                        try {
                            iArr[UnusedChecker.VariableStatus.WRITTEN_AFTER_READ.ordinal()] = 2;
                        } catch (NoSuchFieldError unused2) {
                        }
                        try {
                            iArr[UnusedChecker.VariableStatus.READ.ordinal()] = 3;
                        } catch (NoSuchFieldError unused3) {
                        }
                        $EnumSwitchMapping$0 = iArr;
                    }
                }

                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final UnusedChecker.VariableStatus invoke(UnusedChecker.VariableStatus variableStatus) {
                    int i = variableStatus == null ? -1 : WhenMappings.$EnumSwitchMapping$0[variableStatus.ordinal()];
                    if (i == -1) {
                        return UnusedChecker.VariableStatus.UNUSED;
                    }
                    if (i == 1 || i == 2) {
                        if (node.getFir().getInitializer() != null && variableStatus.getIsRead()) {
                            variableStatus.setRedundantInit(true);
                            return variableStatus;
                        }
                        if (node.getFir().getInitializer() != null) {
                            return UnusedChecker.VariableStatus.ONLY_WRITTEN_NEVER_READ;
                        }
                    } else if (i == 3) {
                        return UnusedChecker.VariableStatus.READ;
                    }
                    return null;
                }
            });
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> visitVariableAssignmentNode(VariableAssignmentNode node, PersistentMap<EdgeLabel, VariableStatusInfo> data) {
            final FirPropertySymbol resolvedPropertySymbol$default;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, data);
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(node.getFir());
            return (calleeReference == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null) ? persistentMapVisitNode : update(persistentMapVisitNode, new FirPropertySymbol[]{resolvedPropertySymbol$default}, new Function1<VariableStatus, VariableStatus>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.UnusedChecker$ValueWritesWithoutReading$visitVariableAssignmentNode$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final UnusedChecker.VariableStatus invoke(UnusedChecker.VariableStatus variableStatus) {
                    UnusedChecker.VariableStatus variableStatusMerge;
                    if (!this.this$0.localProperties.contains(resolvedPropertySymbol$default)) {
                        variableStatusMerge = null;
                    } else if (variableStatus == UnusedChecker.VariableStatus.READ) {
                        variableStatusMerge = UnusedChecker.VariableStatus.WRITTEN_AFTER_READ;
                    } else if (variableStatus == UnusedChecker.VariableStatus.WRITTEN_AFTER_READ) {
                        variableStatusMerge = UnusedChecker.VariableStatus.ONLY_WRITTEN_NEVER_READ;
                    } else {
                        variableStatusMerge = UnusedChecker.VariableStatus.ONLY_WRITTEN_NEVER_READ.merge(variableStatus == null ? UnusedChecker.VariableStatus.UNUSED : variableStatus);
                    }
                    if (variableStatusMerge == null) {
                        return null;
                    }
                    variableStatusMerge.setRead(variableStatus != null ? variableStatus.getIsRead() : false);
                    return variableStatusMerge;
                }
            });
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> visitQualifiedAccessNode(QualifiedAccessNode node, PersistentMap<EdgeLabel, VariableStatusInfo> data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            return visitQualifiedAccesses(visitNode((CFGNode<?>) node, data), node.getFir());
        }

        private final PersistentMap<EdgeLabel, VariableStatusInfo> visitAnnotation(PersistentMap<EdgeLabel, VariableStatusInfo> dataForNode, FirAnnotation annotation) {
            if (!(annotation instanceof FirAnnotationCall)) {
                return dataForNode;
            }
            List<FirExpression> arguments = ((FirAnnotationCall) annotation).getArgumentList().getArguments();
            ArrayList arrayList = new ArrayList();
            for (FirExpression firExpression : arguments) {
                FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
                if (firQualifiedAccessExpression != null) {
                    arrayList.add(firQualifiedAccessExpression);
                }
            }
            FirQualifiedAccessExpression[] firQualifiedAccessExpressionArr = (FirQualifiedAccessExpression[]) arrayList.toArray(new FirQualifiedAccessExpression[0]);
            return visitQualifiedAccesses(dataForNode, (FirQualifiedAccessExpression[]) Arrays.copyOf(firQualifiedAccessExpressionArr, firQualifiedAccessExpressionArr.length));
        }

        private static final FirPropertySymbol visitQualifiedAccesses$retrieveSymbol(ValueWritesWithoutReading valueWritesWithoutReading, FirQualifiedAccessExpression firQualifiedAccessExpression) {
            FirPropertySymbol resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
            if (resolvedPropertySymbol$default == null || !valueWritesWithoutReading.localProperties.contains(resolvedPropertySymbol$default)) {
                return null;
            }
            return resolvedPropertySymbol$default;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
        public PersistentMap<EdgeLabel, VariableStatusInfo> visitFunctionCallNode(FunctionCallNode node, PersistentMap<EdgeLabel, VariableStatusInfo> data) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(data, "data");
            PersistentMap<EdgeLabel, VariableStatusInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, data);
            FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(node.getFir().getCalleeReference());
            if (resolved == null) {
                return persistentMapVisitNode;
            }
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            Object obj = null;
            FirFunctionSymbol firFunctionSymbol = resolvedSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedSymbol : null;
            if (firFunctionSymbol == null) {
                return persistentMapVisitNode;
            }
            if (Intrinsics.areEqual(firFunctionSymbol.getCallableId().getCallableName().getIdentifier(), "invoke")) {
                for (Object obj2 : this.localProperties) {
                    FirPropertySymbol firPropertySymbol = (FirPropertySymbol) obj2;
                    if (Intrinsics.areEqual(firPropertySymbol.getName(), resolved.getName()) && FunctionalTypeUtilsKt.isBasicFunctionType(FirTypeUtilsKt.getConeType(firPropertySymbol.getResolvedReturnTypeRef()), this.session)) {
                        obj = obj2;
                        break;
                    }
                }
                obj = (FirPropertySymbol) obj;
            }
            if (obj == null) {
                return persistentMapVisitNode;
            }
            final VariableStatus variableStatus = VariableStatus.READ;
            variableStatus.setRead(true);
            return update(persistentMapVisitNode, new FirPropertySymbol[]{obj}, new Function1<VariableStatus, VariableStatus>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.UnusedChecker$ValueWritesWithoutReading$visitFunctionCallNode$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final UnusedChecker.VariableStatus invoke(UnusedChecker.VariableStatus variableStatus2) {
                    return variableStatus;
                }
            });
        }

        private final PersistentMap<EdgeLabel, VariableStatusInfo> visitQualifiedAccesses(PersistentMap<EdgeLabel, VariableStatusInfo> persistentMap, FirQualifiedAccessExpression... firQualifiedAccessExpressionArr) {
            ArrayList arrayList = new ArrayList();
            for (FirQualifiedAccessExpression firQualifiedAccessExpression : firQualifiedAccessExpressionArr) {
                FirPropertySymbol firPropertySymbolVisitQualifiedAccesses$retrieveSymbol = visitQualifiedAccesses$retrieveSymbol(this, firQualifiedAccessExpression);
                if (firPropertySymbolVisitQualifiedAccesses$retrieveSymbol != null) {
                    arrayList.add(firPropertySymbolVisitQualifiedAccesses$retrieveSymbol);
                }
            }
            FirPropertySymbol[] firPropertySymbolArr = (FirPropertySymbol[]) arrayList.toArray(new FirPropertySymbol[0]);
            final VariableStatus variableStatus = VariableStatus.READ;
            variableStatus.setRead(true);
            return update(persistentMap, (FirPropertySymbol[]) Arrays.copyOf(firPropertySymbolArr, firPropertySymbolArr.length), new Function1<VariableStatus, VariableStatus>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.UnusedChecker$ValueWritesWithoutReading$visitQualifiedAccesses$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final UnusedChecker.VariableStatus invoke(UnusedChecker.VariableStatus variableStatus2) {
                    return variableStatus;
                }
            });
        }

        private final PersistentMap<EdgeLabel, VariableStatusInfo> update(PersistentMap<EdgeLabel, VariableStatusInfo> persistentMap, FirPropertySymbol[] firPropertySymbolArr, Function1<? super VariableStatus, ? extends VariableStatus> function1) {
            PersistentMap.Builder<EdgeLabel, VariableStatusInfo> builder = persistentMap.builder();
            PersistentMap.Builder<EdgeLabel, VariableStatusInfo> builder2 = builder;
            for (Map.Entry<EdgeLabel, VariableStatusInfo> entry : persistentMap.entrySet()) {
                EdgeLabel key = entry.getKey();
                VariableStatusInfo value = entry.getValue();
                for (FirPropertySymbol firPropertySymbol : firPropertySymbolArr) {
                    VariableStatus variableStatusInvoke = function1.invoke(value.get((Object) firPropertySymbol));
                    if (variableStatusInvoke != null) {
                        builder2.put(key, value.put(firPropertySymbol, variableStatusInvoke));
                    }
                }
            }
            return builder.build();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isLoopIterator(FirPropertySymbol firPropertySymbol) {
        KtSourceElement source;
        FirExpression initializer = ((FirProperty) firPropertySymbol.getFir()).getInitializer();
        return Intrinsics.areEqual((initializer == null || (source = initializer.getSource()) == null) ? null : source.getKind(), KtFakeSourceElementKind.DesugaredForLoop.INSTANCE);
    }
}
