package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;

/* JADX INFO: compiled from: jsAstUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u0006J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rH\u0002J\u001c\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0006J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\"\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\r2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\rJ\u0018\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006J\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0006J\u0016\u0010,\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010-\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010.\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010/\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u00062\u0006\u00100\u001a\u000201¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsAstUtils;", "", "()V", "addAssign", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "left", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "right", "and", "op1", "op2", "assignment", "assignmentToThisField", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "fieldName", "", "deBlockIfPossible", "statement", "decomposeAssignment", "Lkotlin/Pair;", "expr", "div", "equality", "arg1", "arg2", "greaterThan", "greaterThanEq", "inequality", "lessThan", "lessThanEq", "mod", "mul", "newJsIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "ifExpression", "thenStatement", "elseStatement", "newVar", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "not", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "expression", "or", "subtract", "sum", "typeOfIs", "string", "Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsAstUtils {
    public static final JsAstUtils INSTANCE = new JsAstUtils();

    private JsAstUtils() {
    }

    private final JsStatement deBlockIfPossible(JsStatement statement) {
        if (!(statement instanceof JsBlock)) {
            return statement;
        }
        JsBlock jsBlock = (JsBlock) statement;
        if (jsBlock.getStatements().size() != 1) {
            return statement;
        }
        JsStatement jsStatement = jsBlock.getStatements().get(0);
        Intrinsics.checkNotNull(jsStatement);
        return jsStatement;
    }

    public static /* synthetic */ JsIf newJsIf$default(JsAstUtils jsAstUtils, JsExpression jsExpression, JsStatement jsStatement, JsStatement jsStatement2, int i, Object obj) {
        if ((i & 4) != 0) {
            jsStatement2 = null;
        }
        return jsAstUtils.newJsIf(jsExpression, jsStatement, jsStatement2);
    }

    public final JsIf newJsIf(JsExpression ifExpression, JsStatement thenStatement, JsStatement elseStatement) {
        Intrinsics.checkNotNullParameter(ifExpression, "ifExpression");
        Intrinsics.checkNotNullParameter(thenStatement, "thenStatement");
        return new JsIf(ifExpression, deBlockIfPossible(thenStatement), elseStatement != null ? INSTANCE.deBlockIfPossible(elseStatement) : null);
    }

    public final JsBinaryOperation and(JsExpression op1, JsExpression op2) {
        Intrinsics.checkNotNullParameter(op1, "op1");
        Intrinsics.checkNotNullParameter(op2, "op2");
        return new JsBinaryOperation(JsBinaryOperator.AND, op1, op2);
    }

    public final JsBinaryOperation or(JsExpression op1, JsExpression op2) {
        Intrinsics.checkNotNullParameter(op1, "op1");
        Intrinsics.checkNotNullParameter(op2, "op2");
        return new JsBinaryOperation(JsBinaryOperator.OR, op1, op2);
    }

    public final JsBinaryOperation equality(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.REF_EQ, arg1, arg2);
    }

    public final JsBinaryOperation inequality(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.REF_NEQ, arg1, arg2);
    }

    public final JsBinaryOperation lessThanEq(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.LTE, arg1, arg2);
    }

    public final JsBinaryOperation lessThan(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.LT, arg1, arg2);
    }

    public final JsBinaryOperation greaterThan(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.GT, arg1, arg2);
    }

    public final JsBinaryOperation greaterThanEq(JsExpression arg1, JsExpression arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        return new JsBinaryOperation(JsBinaryOperator.GTE, arg1, arg2);
    }

    public final JsBinaryOperation assignment(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.ASG, left, right);
    }

    public final JsStatement assignmentToThisField(String fieldName, JsExpression right) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(right, "right");
        JsStatement jsStatementMakeStmt = assignment(new JsNameRef(fieldName, new JsThisRef()), right).source(right.getSource()).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    public final Pair<JsExpression, JsExpression> decomposeAssignment(JsExpression expr) {
        Intrinsics.checkNotNullParameter(expr, "expr");
        if (!(expr instanceof JsBinaryOperation)) {
            return null;
        }
        JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) expr;
        if (jsBinaryOperation.getOperator() != JsBinaryOperator.ASG) {
            return null;
        }
        return new Pair<>(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
    }

    public final JsBinaryOperation sum(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.ADD, left, right);
    }

    public final JsBinaryOperation addAssign(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.ASG_ADD, left, right);
    }

    public final JsBinaryOperation subtract(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.SUB, left, right);
    }

    public final JsBinaryOperation mul(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.MUL, left, right);
    }

    public final JsBinaryOperation div(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.DIV, left, right);
    }

    public final JsBinaryOperation mod(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.MOD, left, right);
    }

    public final JsPrefixOperation not(JsExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new JsPrefixOperation(JsUnaryOperator.NOT, expression);
    }

    public final JsBinaryOperation typeOfIs(JsExpression expression, JsStringLiteral string) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(string, "string");
        return equality(new JsPrefixOperation(JsUnaryOperator.TYPEOF, expression), string);
    }

    public final JsVars newVar(JsName name2, JsExpression expr) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new JsVars(new JsVars.JsVar(name2, expr));
    }
}
