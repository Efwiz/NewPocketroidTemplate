package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.renderer.FirCallNoArgumentsRenderer;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;

/* JADX INFO: compiled from: CFGNodeRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0004\u001a\u00020\u0005*\u0006\u0012\u0002\b\u00030\u0006\u001a\f\u0010\u0007\u001a\u00020\u0005*\u00020\bH\u0002\"\u0014\u0010\u0000\u001a\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006\t"}, d2 = {"CfgRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getCfgRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "render", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CFGNodeRendererKt {
    public static final String render(CFGNode<?> cFGNode) {
        String string;
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        StringBuilder sb = new StringBuilder();
        if (cFGNode instanceof FunctionEnterNode) {
            string = "Enter function " + cFGNode.getOwner().getName();
        } else if (cFGNode instanceof FunctionExitNode) {
            string = "Exit function " + cFGNode.getOwner().getName();
        } else if (cFGNode instanceof LocalFunctionDeclarationNode) {
            string = "Local function declaration";
        } else if (cFGNode instanceof BlockEnterNode) {
            string = "Enter block";
        } else if (cFGNode instanceof BlockExitNode) {
            string = "Exit block";
        } else if (cFGNode instanceof WhenEnterNode) {
            string = "Enter when";
        } else if (cFGNode instanceof WhenBranchConditionEnterNode) {
            string = "Enter when branch condition ".concat(((WhenBranchConditionEnterNode) cFGNode).getFir().getCondition() instanceof FirElseIfTrueCondition ? "\"else\"" : "");
        } else if (cFGNode instanceof WhenBranchConditionExitNode) {
            string = "Exit when branch condition";
        } else if (cFGNode instanceof WhenBranchResultEnterNode) {
            string = "Enter when branch result";
        } else if (cFGNode instanceof WhenBranchResultExitNode) {
            string = "Exit when branch result";
        } else if (cFGNode instanceof WhenSyntheticElseBranchNode) {
            string = "Synthetic else branch";
        } else if (cFGNode instanceof WhenExitNode) {
            string = "Exit when";
        } else if (cFGNode instanceof LoopEnterNode) {
            string = "Enter " + type(((LoopEnterNode) cFGNode).getFir()) + " loop";
        } else if (cFGNode instanceof LoopBlockEnterNode) {
            string = "Enter loop block";
        } else if (cFGNode instanceof LoopBlockExitNode) {
            string = "Exit loop block";
        } else if (cFGNode instanceof LoopConditionEnterNode) {
            string = "Enter loop condition";
        } else if (cFGNode instanceof LoopConditionExitNode) {
            string = "Exit loop condition";
        } else if (cFGNode instanceof LoopExitNode) {
            string = "Exit " + type(((LoopExitNode) cFGNode).getFir()) + " loop";
        } else {
            if (cFGNode instanceof QualifiedAccessNode) {
                string = "Access variable " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((QualifiedAccessNode) cFGNode).getFir().getCalleeReference(), false, 2, null);
            } else if (cFGNode instanceof ResolvedQualifierNode) {
                string = "Access qualifier " + ((ResolvedQualifierNode) cFGNode).getFir().getClassId();
            } else if (cFGNode instanceof ComparisonExpressionNode) {
                string = "Comparison " + ((ComparisonExpressionNode) cFGNode).getFir().getOperation().getOperator();
            } else if (cFGNode instanceof TypeOperatorCallNode) {
                string = "Type operator: \"" + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((TypeOperatorCallNode) cFGNode).getFir(), false, 2, null) + '\"';
            } else if (cFGNode instanceof SmartCastExpressionExitNode) {
                string = "Smart cast: \"" + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((SmartCastExpressionExitNode) cFGNode).getFir(), false, 2, null) + '\"';
            } else if (cFGNode instanceof EqualityOperatorCallNode) {
                string = "Equality operator " + ((EqualityOperatorCallNode) cFGNode).getFir().getOperation().getOperator();
            } else if (cFGNode instanceof JumpNode) {
                string = "Jump: " + UtilsKt.render(((JumpNode) cFGNode).getFir());
            } else if (cFGNode instanceof StubNode) {
                string = "Stub";
            } else if (cFGNode instanceof CheckNotNullCallNode) {
                string = "Check not null: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((CheckNotNullCallNode) cFGNode).getFir(), false, 2, null);
            } else if (cFGNode instanceof ConstExpressionNode) {
                string = "Const: " + UtilsKt.render(((ConstExpressionNode) cFGNode).getFir());
            } else if (cFGNode instanceof VariableDeclarationNode) {
                string = "Variable declaration: " + getCfgRenderer().renderAsCallableDeclarationString(((VariableDeclarationNode) cFGNode).getFir());
            } else if (cFGNode instanceof VariableAssignmentNode) {
                StringBuilder sb2 = new StringBuilder("Assignment: ");
                FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(((VariableAssignmentNode) cFGNode).getFir());
                string = sb2.append(calleeReference != null ? FirRenderer.renderElementAsString$default(getCfgRenderer(), calleeReference, false, 2, null) : null).toString();
            } else if (cFGNode instanceof FunctionCallNode) {
                string = "Function call: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((FunctionCallNode) cFGNode).getFir(), false, 2, null);
            } else if (cFGNode instanceof DelegatedConstructorCallNode) {
                string = "Delegated constructor call: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((DelegatedConstructorCallNode) cFGNode).getFir(), false, 2, null);
            } else if (cFGNode instanceof StringConcatenationCallNode) {
                string = "String concatenation call: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((StringConcatenationCallNode) cFGNode).getFir(), false, 2, null);
            } else if (cFGNode instanceof ThrowExceptionNode) {
                string = "Throw: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((ThrowExceptionNode) cFGNode).getFir(), false, 2, null);
            } else if (cFGNode instanceof TryExpressionEnterNode) {
                string = "Try expression enter";
            } else if (cFGNode instanceof TryMainBlockEnterNode) {
                string = "Try main block enter";
            } else if (cFGNode instanceof TryMainBlockExitNode) {
                string = "Try main block exit";
            } else if (cFGNode instanceof CatchClauseEnterNode) {
                string = "Catch enter";
            } else if (cFGNode instanceof CatchClauseExitNode) {
                string = "Catch exit";
            } else if (cFGNode instanceof FinallyBlockEnterNode) {
                string = "Enter finally";
            } else if (cFGNode instanceof FinallyBlockExitNode) {
                string = "Exit finally";
            } else if (cFGNode instanceof TryExpressionExitNode) {
                string = "Try expression exit";
            } else if (cFGNode instanceof BinaryAndEnterNode) {
                string = "Enter &&";
            } else if (cFGNode instanceof BinaryAndExitLeftOperandNode) {
                string = "Exit left part of &&";
            } else if (cFGNode instanceof BinaryAndEnterRightOperandNode) {
                string = "Enter right part of &&";
            } else if (cFGNode instanceof BinaryAndExitNode) {
                string = "Exit &&";
            } else if (cFGNode instanceof BinaryOrEnterNode) {
                string = "Enter ||";
            } else if (cFGNode instanceof BinaryOrExitLeftOperandNode) {
                string = "Exit left part of ||";
            } else if (cFGNode instanceof BinaryOrEnterRightOperandNode) {
                string = "Enter right part of ||";
            } else if (cFGNode instanceof BinaryOrExitNode) {
                string = "Exit ||";
            } else if (cFGNode instanceof PropertyInitializerEnterNode) {
                string = "Enter property";
            } else if (cFGNode instanceof PropertyInitializerExitNode) {
                string = "Exit property";
            } else if (cFGNode instanceof DelegateExpressionExitNode) {
                string = "Exit property delegate";
            } else if (cFGNode instanceof FieldInitializerEnterNode) {
                string = "Enter field";
            } else if (cFGNode instanceof FieldInitializerExitNode) {
                string = "Exit field";
            } else if (cFGNode instanceof InitBlockEnterNode) {
                string = "Enter init block";
            } else if (cFGNode instanceof InitBlockExitNode) {
                string = "Exit init block";
            } else if (cFGNode instanceof EnterSafeCallNode) {
                string = "Enter safe call";
            } else if (cFGNode instanceof ExitSafeCallNode) {
                string = "Exit safe call";
            } else if (cFGNode instanceof WhenSubjectExpressionExitNode) {
                string = "Exit $subj";
            } else if (cFGNode instanceof SplitPostponedLambdasNode) {
                string = "Postponed enter to lambda";
            } else if (cFGNode instanceof PostponedLambdaExitNode) {
                string = "Postponed exit from lambda";
            } else if (cFGNode instanceof MergePostponedLambdaExitsNode) {
                string = "Merge postponed lambda exits";
            } else if (cFGNode instanceof AnonymousFunctionExpressionNode) {
                string = "Exit anonymous function expression";
            } else if (cFGNode instanceof ClassEnterNode) {
                string = "Enter class " + cFGNode.getOwner().getName();
            } else if (cFGNode instanceof ClassExitNode) {
                string = "Exit class " + cFGNode.getOwner().getName();
            } else if (cFGNode instanceof LocalClassExitNode) {
                string = "Local class declaration";
            } else if (cFGNode instanceof AnonymousObjectEnterNode) {
                string = "Enter anonymous object";
            } else if (cFGNode instanceof AnonymousObjectExpressionExitNode) {
                string = "Exit anonymous object expression";
            } else if (cFGNode instanceof ScriptEnterNode) {
                string = "Enter class " + ((ScriptEnterNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof ScriptExitNode) {
                string = "Exit class " + ((ScriptExitNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof CodeFragmentEnterNode) {
                string = "Enter code fragment";
            } else if (cFGNode instanceof CodeFragmentExitNode) {
                string = "Exit code fragment";
            } else if (cFGNode instanceof FakeExpressionEnterNode) {
                string = "Enter fake expression";
            } else if (cFGNode instanceof EnterValueParameterNode) {
                string = "Enter default value of " + ((EnterValueParameterNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof EnterDefaultArgumentsNode) {
                string = "Enter default value of " + ((EnterDefaultArgumentsNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof ExitDefaultArgumentsNode) {
                string = "Exit default value of " + ((ExitDefaultArgumentsNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof ExitValueParameterNode) {
                string = "Exit default value of " + ((ExitValueParameterNode) cFGNode).getFir().getName();
            } else if (cFGNode instanceof ElvisLhsExitNode) {
                string = "Exit lhs of ?:";
            } else if (cFGNode instanceof ElvisLhsIsNotNullNode) {
                string = "Lhs of ?: is not null";
            } else if (cFGNode instanceof ElvisRhsEnterNode) {
                string = "Enter rhs of ?:";
            } else if (cFGNode instanceof ElvisExitNode) {
                string = "Exit ?:";
            } else if (cFGNode instanceof CallableReferenceNode) {
                string = "Callable reference: " + FirRenderer.renderElementAsString$default(getCfgRenderer(), ((CallableReferenceNode) cFGNode).getFir(), false, 2, null);
            } else {
                if (!(cFGNode instanceof GetClassCallNode)) {
                    if (cFGNode instanceof AbstractBinaryExitNode) {
                        throw new IllegalStateException();
                    }
                    throw new NoWhenBranchMatchedException();
                }
                string = "::class call";
            }
        }
        sb.append(string);
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return string2;
    }

    private static final FirRenderer getCfgRenderer() {
        return new FirRenderer(null, null, null, new FirCallNoArgumentsRenderer(), null, null, null, null, null, null, null, null, null, null, null, null, null, 131061, null);
    }

    private static final String type(FirLoop firLoop) {
        if (firLoop instanceof FirWhileLoop) {
            return PsiKeyword.WHILE;
        }
        if (firLoop instanceof FirDoWhileLoop) {
            return "do-while";
        }
        throw new IllegalArgumentException();
    }
}
