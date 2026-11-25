package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryAndExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryOrExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CatchClauseEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CatchClauseExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.Edge;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FinallyBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FinallyBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.JumpNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.TryMainBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.TryMainBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenBranchResultExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenExitNode;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirTailrecFunctionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0002H\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTailrecFunctionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "hasMoreFollowingInstructions", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "tailrecFunction", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTailrecFunctionChecker extends FirDeclarationChecker<FirSimpleFunction> {
    public static final FirTailrecFunctionChecker INSTANCE = new FirTailrecFunctionChecker();

    private FirTailrecFunctionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(final FirSimpleFunction declaration, final CheckerContext context, final DiagnosticReporter reporter) {
        ControlFlowGraph controlFlowGraph;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirSimpleFunction firSimpleFunction = declaration;
        if (firSimpleFunction.getStatus().isTailRec()) {
            if (!DeclarationUtilsKt.isEffectivelyFinal(firSimpleFunction, context) && !Intrinsics.areEqual(firSimpleFunction.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getTAILREC_ON_VIRTUAL_MEMBER_ERROR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            FirControlFlowGraphReference controlFlowGraphReference = declaration.getControlFlowGraphReference();
            if (controlFlowGraphReference == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null) {
                return;
            }
            final Ref.IntRef intRef = new Ref.IntRef();
            final Ref.IntRef intRef2 = new Ref.IntRef();
            final Ref.IntRef intRef3 = new Ref.IntRef();
            final Ref.IntRef intRef4 = new Ref.IntRef();
            controlFlowGraph.traverse(new ControlFlowGraphVisitorVoid() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTailrecFunctionChecker.check.1
                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitNode(CFGNode<?> node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitTryMainBlockEnterNode(TryMainBlockEnterNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef.element++;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitTryMainBlockExitNode(TryMainBlockExitNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef.element--;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitCatchClauseEnterNode(CatchClauseEnterNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef2.element++;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitCatchClauseExitNode(CatchClauseExitNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef2.element--;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitFinallyBlockEnterNode(FinallyBlockEnterNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef3.element++;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitFinallyBlockExitNode(FinallyBlockExitNode node) {
                    Intrinsics.checkNotNullParameter(node, "node");
                    intRef3.element--;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
                public void visitFunctionCallNode(FunctionCallNode node) {
                    ClassKind classKind;
                    Intrinsics.checkNotNullParameter(node, "node");
                    FirFunctionCall fir = node.getFir();
                    FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(fir.getCalleeReference(), false, 1, null);
                    FirNamedFunctionSymbol firNamedFunctionSymbol = resolvedCallableSymbol$default instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) resolvedCallableSymbol$default : null;
                    if (firNamedFunctionSymbol != null && Intrinsics.areEqual(firNamedFunctionSymbol, declaration.getSymbol())) {
                        if (fir.getArgumentList().getArguments().size() != firNamedFunctionSymbol.getValueParameterSymbols().size() && firNamedFunctionSymbol.getRawStatus().isOverride()) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, fir.getSource(), FirErrors.INSTANCE.getNON_TAIL_RECURSIVE_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            return;
                        }
                        FirExpression dispatchReceiver = fir.getDispatchReceiver();
                        ConeSimpleKotlinType dispatchReceiverType = declaration.getDispatchReceiverType();
                        FirClassifierSymbol<?> symbol = dispatchReceiverType != null ? TypeUtilsKt.toSymbol(dispatchReceiverType, context.getSession()) : null;
                        FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
                        if (!(dispatchReceiver instanceof FirNoReceiverExpression) && ((!(dispatchReceiver instanceof FirThisReceiverExpression) || !Intrinsics.areEqual(((FirThisReceiverExpression) dispatchReceiver).getCalleeReference().getBoundSymbol(), firClassSymbol)) && (firClassSymbol == null || (classKind = firClassSymbol.getClassKind()) == null || !classKind.isSingleton()))) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, fir.getSource(), FirErrors.INSTANCE.getNON_TAIL_RECURSIVE_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            return;
                        }
                        if (intRef.element <= 0 && intRef2.element <= 0 && intRef3.element <= 0) {
                            if (FirTailrecFunctionChecker.INSTANCE.hasMoreFollowingInstructions(node, declaration)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, fir.getSource(), FirErrors.INSTANCE.getNON_TAIL_RECURSIVE_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                return;
                            } else {
                                if (node.getIsDead()) {
                                    return;
                                }
                                intRef4.element++;
                                return;
                            }
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, fir.getSource(), FirErrors.INSTANCE.getTAIL_RECURSION_IN_TRY_IS_NOT_SUPPORTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            });
            if (intRef4.element == 0) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getNO_TAIL_CALLS_FOUND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasMoreFollowingInstructions(CFGNode<?> cFGNode, FirSimpleFunction firSimpleFunction) {
        for (CFGNode<?> cFGNode2 : cFGNode.getFollowingNodes()) {
            Edge edgeEdgeTo = cFGNode.edgeTo(cFGNode2);
            if (edgeEdgeTo.getKind().getUsedInCfa() && !edgeEdgeTo.getKind().getIsDead()) {
                if (edgeEdgeTo.getKind().getIsBack()) {
                    return true;
                }
                if (cFGNode2 instanceof FunctionExitNode) {
                    return !Intrinsics.areEqual(((FunctionExitNode) cFGNode2).getFir(), firSimpleFunction);
                }
                if (!(cFGNode2 instanceof JumpNode ? true : cFGNode2 instanceof BinaryAndExitNode ? true : cFGNode2 instanceof BinaryOrExitNode ? true : cFGNode2 instanceof WhenBranchResultExitNode ? true : cFGNode2 instanceof WhenExitNode ? true : cFGNode2 instanceof BlockExitNode)) {
                    return true;
                }
                boolean zHasMoreFollowingInstructions = hasMoreFollowingInstructions(cFGNode2, firSimpleFunction);
                if (zHasMoreFollowingInstructions) {
                    return zHasMoreFollowingInstructions;
                }
            }
        }
        return false;
    }
}
