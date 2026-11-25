package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.cfa.FirPropertyInitializationAnalyzerKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfo;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableAssignmentNode;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CanBeValChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/CanBeValChecker;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "()V", "analyze", "", "data", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "ReassignedVariableCollector", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CanBeValChecker extends AbstractFirPropertyInitializationChecker {
    public static final CanBeValChecker INSTANCE = new CanBeValChecker();

    private CanBeValChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker
    public void analyze(PropertyInitializationInfoData data, DiagnosticReporter reporter, CheckerContext context) {
        boolean zCanBeVal;
        int i;
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        ReassignedVariableCollector reassignedVariableCollector = new ReassignedVariableCollector(data);
        data.getGraph().traverse(reassignedVariableCollector);
        Iterator<FirPropertySymbol> it = data.getProperties().iterator();
        while (it.hasNext()) {
            FirPropertySymbol next = it.next();
            KtSourceElement source = next.getSource();
            if (source != null) {
                if (Intrinsics.areEqual(source.getElementType(), KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                    List<LighterASTNode> children = LightTreeUtilsKt.getChildren(source.getLighterASTNode(), source.getTreeStructure());
                    if ((children instanceof Collection) && children.isEmpty()) {
                        i = 0;
                    } else {
                        Iterator<T> it2 = children.iterator();
                        i = 0;
                        while (it2.hasNext()) {
                            if (Intrinsics.areEqual(((LighterASTNode) it2.next()).getTokenType(), KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY) && (i = i + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    Iterator<Integer> it3 = RangesKt.until(0, i).iterator();
                    while (true) {
                        zCanBeVal = true;
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            ((IntIterator) it3).nextInt();
                            if (!it.hasNext() || !reassignedVariableCollector.canBeVal(it.next()) || !zCanBeVal) {
                                zCanBeVal = false;
                            }
                        }
                    }
                } else {
                    zCanBeVal = reassignedVariableCollector.canBeVal(next);
                }
                if (zCanBeVal) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCAN_BE_VAL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }

    /* JADX INFO: compiled from: CanBeValChecker.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u0014\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/CanBeValChecker$ReassignedVariableCollector;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", "data", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "(Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;)V", "getData", "()Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "reassigned", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "canBeVal", "", NativeSymbol.TYPE_NAME, "visitNode", "", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ReassignedVariableCollector extends ControlFlowGraphVisitorVoid {
        private final PropertyInitializationInfoData data;
        private final Set<FirPropertySymbol> reassigned;

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitNode(CFGNode<?> node) {
            Intrinsics.checkNotNullParameter(node, "node");
        }

        public ReassignedVariableCollector(PropertyInitializationInfoData data) {
            Intrinsics.checkNotNullParameter(data, "data");
            this.data = data;
            this.reassigned = new LinkedHashSet();
        }

        public final PropertyInitializationInfoData getData() {
            return this.data;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitVariableAssignmentNode(VariableAssignmentNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(node.getFir());
            if (calleeReference != null) {
                FirPropertySymbol resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null);
                if (resolvedPropertySymbol$default != null && resolvedPropertySymbol$default.isVar()) {
                    KtSourceElement source = resolvedPropertySymbol$default.getSource();
                    if (((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) || !this.data.getProperties().contains(resolvedPropertySymbol$default)) {
                        return;
                    }
                    if (FirPropertyInitializationAnalyzerKt.requiresInitialization(resolvedPropertySymbol$default, this.data.getGraph().getKind() == ControlFlowGraph.Kind.Class)) {
                        Collection collectionValues = this.data.getValue(node).values();
                        if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                            return;
                        }
                        Iterator it = collectionValues.iterator();
                        while (it.hasNext()) {
                            EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) ((PropertyInitializationInfo) it.next()).get((Object) resolvedPropertySymbol$default);
                            if (eventOccurrencesRange == null || !EventOccurrencesRangeKt.canBeRevisited(eventOccurrencesRange)) {
                            }
                        }
                        return;
                    }
                    this.reassigned.add(resolvedPropertySymbol$default);
                }
            }
        }

        public final boolean canBeVal(FirPropertySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (symbol.isVar() && !symbol.getHasDelegate()) {
                KtSourceElement source = symbol.getSource();
                KtSourceElementKind kind = source != null ? source.getKind() : null;
                if (!(kind == null ? true : kind instanceof KtFakeSourceElementKind) && !this.reassigned.contains(symbol)) {
                    return true;
                }
            }
            return false;
        }
    }
}
