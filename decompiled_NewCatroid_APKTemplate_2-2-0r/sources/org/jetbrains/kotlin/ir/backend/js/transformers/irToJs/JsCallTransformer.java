package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;

/* JADX INFO: compiled from: JsCallTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsCallTransformer;", "", "jsOrJsFuncCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;)V", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "generateExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "generateStatement", "getJsStatements", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCallTransformer {
    private final JsGenerationContext context;
    private final IrCall jsOrJsFuncCall;
    private final List<JsStatement> statements;

    public JsCallTransformer(IrCall jsOrJsFuncCall, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(jsOrJsFuncCall, "jsOrJsFuncCall");
        Intrinsics.checkNotNullParameter(context, "context");
        this.jsOrJsFuncCall = jsOrJsFuncCall;
        this.context = context;
        this.statements = getJsStatements();
    }

    public final JsStatement generateStatement() {
        if (this.statements.isEmpty()) {
            return JsEmpty.INSTANCE;
        }
        List mutableList = CollectionsKt.toMutableList((Collection) this.statements);
        Object objLast = CollectionsKt.last((List<? extends Object>) mutableList);
        JsReturn jsReturn = objLast instanceof JsReturn ? (JsReturn) objLast : null;
        JsExpression expression = jsReturn != null ? jsReturn.getExpression() : null;
        if (expression != null) {
            Intrinsics.checkNotNull(expression);
            if ((expression instanceof JsPrefixOperation) && ((JsPrefixOperation) expression).getOperator() == JsUnaryOperator.VOID) {
                CollectionsKt.removeLastOrNull(mutableList);
            } else {
                int lastIndex = CollectionsKt.getLastIndex(mutableList);
                JsStatement jsStatementMakeStmt = expression.makeStmt();
                Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                mutableList.set(lastIndex, jsStatementMakeStmt);
            }
        }
        int size = mutableList.size();
        if (size == 0) {
            return JsEmpty.INSTANCE;
        }
        if (size == 1) {
            return (JsStatement) JsAstUtilsKt.withSource$default((JsNode) CollectionsKt.single(mutableList), this.jsOrJsFuncCall, this.context, null, null, 12, null);
        }
        return new JsCompositeBlock((List<? extends JsStatement>) mutableList);
    }

    public final JsExpression generateExpression() {
        JsExpression expression;
        JsStatement jsStatementPrevious;
        JsInvocation jsInvocation;
        if (this.statements.isEmpty()) {
            return new JsPrefixOperation(JsUnaryOperator.VOID, new JsIntLiteral(3));
        }
        List<JsStatement> list = this.statements;
        ListIterator<JsStatement> listIterator = list.listIterator(list.size());
        while (true) {
            expression = null;
            if (!listIterator.hasPrevious()) {
                jsStatementPrevious = null;
                break;
            }
            jsStatementPrevious = listIterator.previous();
            JsStatement jsStatement = jsStatementPrevious;
            if (!(jsStatement instanceof JsSingleLineComment) && !(jsStatement instanceof JsMultiLineComment)) {
                break;
            }
        }
        JsStatement jsStatement2 = jsStatementPrevious;
        boolean z = jsStatement2 instanceof JsReturn;
        if (z) {
            expression = ((JsReturn) jsStatement2).getExpression();
        } else if (jsStatement2 instanceof JsExpressionStatement) {
            expression = ((JsExpressionStatement) jsStatement2).getExpression();
        }
        if (this.statements.size() == 1 && expression != null) {
            return (JsExpression) JsAstUtilsKt.withSource$default(expression, this.jsOrJsFuncCall, this.context, null, null, 12, null);
        }
        List mutableList = CollectionsKt.toMutableList((Collection) this.statements);
        if (!z) {
            if (jsStatement2 instanceof JsExpressionStatement) {
                mutableList.set(CollectionsKt.getLastIndex(this.statements), new JsReturn(((JsExpressionStatement) jsStatement2).getExpression()));
            } else {
                mutableList.add(new JsReturn(new JsPrefixOperation(JsUnaryOperator.VOID, new JsIntLiteral(3))));
            }
        }
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock((List<JsStatement>) mutableList), "");
        IrFunction currentFunction = this.context.getCurrentFunction();
        if (currentFunction != null && !currentFunction.getIsInline() && (currentFunction.getDispatchReceiverParameter() != null || (currentFunction instanceof IrConstructor))) {
            jsInvocation = new JsInvocation(new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), jsFunction), new JsThisRef());
        } else {
            jsInvocation = new JsInvocation(jsFunction, new JsExpression[0]);
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(jsInvocation, this.jsOrJsFuncCall, this.context, null, null, 12, null);
    }

    private final List<JsStatement> getJsStatements() {
        if (this.context.checkIfJsCode(this.jsOrJsFuncCall.getSymbol())) {
            IrExpression valueArgument = this.jsOrJsFuncCall.getValueArgument(0);
            if (valueArgument != null) {
                List<JsStatement> listTranslateJsCodeIntoStatementList = JsCodeKt.translateJsCodeIntoStatementList(valueArgument, this.context.getStaticContext().getBackendContext(), this.context.getCurrentFile().getFileEntry());
                if (listTranslateJsCodeIntoStatementList != null) {
                    return listTranslateJsCodeIntoStatementList;
                }
                CompilationExceptionKt.compilationException("Cannot compute js code", this.jsOrJsFuncCall);
                throw null;
            }
            CompilationExceptionKt.compilationException("JsCode is expected", this.jsOrJsFuncCall);
            throw null;
        }
        if (this.context.checkIfHasAssociatedJsCode(this.jsOrJsFuncCall.getSymbol())) {
            return new FunctionWithJsFuncAnnotationInliner(this.jsOrJsFuncCall, this.context).generateResultStatement();
        }
        CompilationExceptionKt.compilationException("`js` function call or function with @JsFunc annotation expected", this.jsOrJsFuncCall);
        throw null;
    }
}
