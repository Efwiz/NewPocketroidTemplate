package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirFunctionReturnChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionReturnChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkHasReturnIfBlock", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionReturnChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirFunctionReturnChecker INSTANCE = new FirFunctionReturnChecker();

    private FirFunctionReturnChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkHasReturnIfBlock(declaration, reporter, context);
    }

    private final void checkHasReturnIfBlock(FirFunction declaration, DiagnosticReporter reporter, CheckerContext context) {
        FirControlFlowGraphReference controlFlowGraphReference;
        ControlFlowGraph controlFlowGraph;
        CFGNode<?> cFGNodePrevious;
        if (((declaration instanceof FirPropertyAccessor) && ((FirPropertyAccessor) declaration).isSetter()) || (declaration instanceof FirConstructor)) {
            return;
        }
        if ((declaration instanceof FirAnonymousFunction) && ((FirAnonymousFunction) declaration).getIsLambda()) {
            return;
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context.getSession());
        if (ConeBuiltinTypeUtilsKt.isUnit(coneKotlinTypeFullyExpandedType) || ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeFullyExpandedType) || (controlFlowGraphReference = declaration.getControlFlowGraphReference()) == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null) {
            return;
        }
        List<CFGNode<?>> previousNodes = controlFlowGraph.getExitNode().getPreviousNodes();
        ListIterator<CFGNode<?>> listIterator = previousNodes.listIterator(previousNodes.size());
        do {
            if (!listIterator.hasPrevious()) {
                cFGNodePrevious = null;
                break;
            }
            cFGNodePrevious = listIterator.previous();
        } while (!(cFGNodePrevious instanceof BlockExitNode));
        CFGNode<?> cFGNode = cFGNodePrevious;
        if (cFGNode == null || cFGNode.getIsDead()) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getNO_RETURN_IN_FUNCTION_WITH_BLOCK_BODY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
