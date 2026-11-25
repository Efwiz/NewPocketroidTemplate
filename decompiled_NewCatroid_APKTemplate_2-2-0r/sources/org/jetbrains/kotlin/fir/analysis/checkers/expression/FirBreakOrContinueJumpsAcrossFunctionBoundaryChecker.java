package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.InlineStatus;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;

/* JADX INFO: compiled from: FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLoopJumpChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker extends FirExpressionChecker<FirLoopJump> {
    public static final FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker INSTANCE = new FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker();

    private FirBreakOrContinueJumpsAcrossFunctionBoundaryChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirLoopJump expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        check$findPathAndCheck(expression, new ArrayDeque(), reporter, context, context.get$languageVersionSettings().supportsFeature(LanguageFeature.BreakContinueInInlineLambdas), ((FirLoop) expression.getTarget().getLabeledElement()).getBlock());
    }

    private static final boolean check$findPathAndCheck$findPathAndCheckWithAddingErrorElement(ArrayDeque<FirElement> arrayDeque, FirLoopJump firLoopJump, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, boolean z, FirElement firElement, FirElement firElement2) {
        arrayDeque.addLast(firElement);
        boolean zCheck$findPathAndCheck = check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, firElement2);
        arrayDeque.removeLast();
        return zCheck$findPathAndCheck;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0075  */
    private static final boolean check$findPathAndCheck(FirLoopJump firLoopJump, ArrayDeque<FirElement> arrayDeque, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, boolean z, FirElement firElement) {
        if (firElement == null) {
            return false;
        }
        if (Intrinsics.areEqual(firElement, firLoopJump)) {
            if (!arrayDeque.isEmpty()) {
                ArrayDeque<FirElement> arrayDeque2 = arrayDeque;
                if (!(arrayDeque2 instanceof Collection) || !arrayDeque2.isEmpty()) {
                    for (FirElement firElement2 : arrayDeque2) {
                        if (firElement2 instanceof FirAnonymousFunction) {
                            if (((FirAnonymousFunction) firElement2).getInlineStatus() != InlineStatus.Inline) {
                                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firLoopJump.getSource(), FirErrors.INSTANCE.getBREAK_OR_CONTINUE_JUMPS_ACROSS_FUNCTION_BOUNDARY(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            }
                        } else if (!(firElement2 instanceof FirAnonymousFunctionExpression) || ((FirAnonymousFunctionExpression) firElement2).getAnonymousFunction().getInlineStatus() != InlineStatus.Inline) {
                            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firLoopJump.getSource(), FirErrors.INSTANCE.getBREAK_OR_CONTINUE_JUMPS_ACROSS_FUNCTION_BOUNDARY(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                    if (!z) {
                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firLoopJump.getSource(), FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.BreakContinueInInlineLambdas, checkerContext.get$languageVersionSettings()), checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                } else if (!z) {
                    KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firLoopJump.getSource(), FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.BreakContinueInInlineLambdas, checkerContext.get$languageVersionSettings()), checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
            return true;
        }
        if (firElement instanceof FirBlock) {
            Iterator<FirStatement> it = ((FirBlock) firElement).getStatements().iterator();
            while (it.hasNext()) {
                if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, it.next())) {
                    return true;
                }
            }
        } else {
            if (firElement instanceof FirLoop) {
                FirLoop firLoop = (FirLoop) firElement;
                return check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, firLoop.getCondition()) || check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, firLoop.getBlock());
            }
            if (firElement instanceof FirWhenExpression) {
                Iterator<FirWhenBranch> it2 = ((FirWhenExpression) firElement).getBranches().iterator();
                while (it2.hasNext()) {
                    if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, it2.next().getResult())) {
                        return true;
                    }
                }
            } else {
                if (firElement instanceof FirVariable) {
                    return check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, ((FirVariable) firElement).getInitializer());
                }
                if (firElement instanceof FirWrappedExpression) {
                    return check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, ((FirWrappedExpression) firElement).getExpression());
                }
                if (firElement instanceof FirNoReceiverExpression) {
                    return false;
                }
                if (firElement instanceof FirFunctionCall) {
                    FirFunctionCall firFunctionCall = (FirFunctionCall) firElement;
                    if (!check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, firFunctionCall.getExtensionReceiver()) && !check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, firFunctionCall.getDispatchReceiver())) {
                        List<FirExpression> arguments = ((FirCall) firElement).getArgumentList().getArguments();
                        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                            Iterator<T> it3 = arguments.iterator();
                            while (it3.hasNext()) {
                                if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, (FirElement) it3.next())) {
                                }
                            }
                        }
                    }
                    return true;
                }
                if (firElement instanceof FirCall) {
                    Iterator<FirExpression> it4 = ((FirCall) firElement).getArgumentList().getArguments().iterator();
                    while (it4.hasNext()) {
                        if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, it4.next())) {
                            return true;
                        }
                    }
                } else if (firElement instanceof FirClass) {
                    arrayDeque.addLast(firElement);
                    Iterator<FirDeclaration> it5 = ((FirClass) firElement).getDeclarations().iterator();
                    while (it5.hasNext()) {
                        if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, it5.next())) {
                            arrayDeque.removeLast();
                            return true;
                        }
                    }
                    arrayDeque.removeLast();
                } else if (firElement instanceof FirFunction) {
                    if (check$findPathAndCheck$findPathAndCheckWithAddingErrorElement(arrayDeque, firLoopJump, diagnosticReporter, checkerContext, z, firElement, ((FirFunction) firElement).getBody())) {
                        return true;
                    }
                    if (firElement instanceof FirConstructor) {
                        FirDelegatedConstructorCall delegatedConstructor = ((FirConstructor) firElement).getDelegatedConstructor();
                        FirArgumentList argumentList = delegatedConstructor != null ? delegatedConstructor.getArgumentList() : null;
                        if (argumentList != null) {
                            arrayDeque.addLast(firElement);
                            Iterator<FirExpression> it6 = argumentList.getArguments().iterator();
                            while (it6.hasNext()) {
                                if (check$findPathAndCheck(firLoopJump, arrayDeque, diagnosticReporter, checkerContext, z, it6.next())) {
                                    arrayDeque.removeLast();
                                    return true;
                                }
                            }
                            arrayDeque.removeLast();
                        }
                    }
                } else {
                    if (firElement instanceof FirAnonymousInitializer) {
                        return check$findPathAndCheck$findPathAndCheckWithAddingErrorElement(arrayDeque, firLoopJump, diagnosticReporter, checkerContext, z, firElement, ((FirAnonymousInitializer) firElement).getBody());
                    }
                    if (firElement instanceof FirAnonymousObjectExpression) {
                        return check$findPathAndCheck$findPathAndCheckWithAddingErrorElement(arrayDeque, firLoopJump, diagnosticReporter, checkerContext, z, firElement, ((FirAnonymousObjectExpression) firElement).getAnonymousObject());
                    }
                    if (firElement instanceof FirAnonymousFunctionExpression) {
                        return check$findPathAndCheck$findPathAndCheckWithAddingErrorElement(arrayDeque, firLoopJump, diagnosticReporter, checkerContext, z, firElement, ((FirAnonymousFunctionExpression) firElement).getAnonymousFunction());
                    }
                }
            }
        }
        return false;
    }
}
