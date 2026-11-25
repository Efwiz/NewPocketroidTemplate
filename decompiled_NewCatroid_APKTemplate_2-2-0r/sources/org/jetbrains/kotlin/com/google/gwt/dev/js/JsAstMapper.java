package org.jetbrains.kotlin.com.google.gwt.dev.js;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.kotlin.com.google.gwt.dev.js.parserExceptions.JsParserException;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Comment;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Node;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.TokenStream;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsComment;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsRegExp;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode;

/* JADX INFO: loaded from: classes3.dex */
public class JsAstMapper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String fileName;
    private final ScopeContext scopeContext;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
        } else if (i != 2) {
            objArr[0] = "scope";
        } else {
            objArr[0] = "statementWithLabel";
        }
        objArr[1] = "org/jetbrains/kotlin/com/google/gwt/dev/js/JsAstMapper";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "getTargetLabel";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public JsAstMapper(JsScope jsScope, String str) {
        if (jsScope == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.scopeContext = new ScopeContext(jsScope);
        this.fileName = str;
    }

    private static JsParserException createParserException(String str, Node node) {
        return new JsParserException("Parser encountered internal error: " + str, node.getPosition());
    }

    private JsNode map(Node node) throws JsParserException {
        return withLocation(mapWithComments(node), node);
    }

    private JsNode mapWithComments(Node node) throws JsParserException {
        JsNode jsNodeMapWithoutLocation = mapWithoutLocation(node);
        if (jsNodeMapWithoutLocation != null) {
            jsNodeMapWithoutLocation.setCommentsBeforeNode(mapComments(node.getCommentsBeforeNode()));
            jsNodeMapWithoutLocation.setCommentsAfterNode(mapComments(node.getCommentsAfterNode()));
        }
        return jsNodeMapWithoutLocation;
    }

    private JsNode mapWithoutLocation(Node node) throws JsParserException {
        int type = node.getType();
        if (type == 30) {
            return mapNew(node);
        }
        if (type == 31) {
            return mapDeleteProp(node);
        }
        if (type != 61) {
            if (type == 62) {
                return mapThrowStatement(node);
            }
            if (type == 109) {
                return mapPrimary(node);
            }
            if (type == 110) {
                return mapFunction(node);
            }
            switch (type) {
                case 5:
                    return mapReturn(node);
                case 39:
                    return mapGetProp(node);
                case 40:
                    return mapSetProp(node);
                case 41:
                    return mapGetElem(node);
                case 42:
                    return mapSetElem(node);
                case 43:
                    return mapCall(node);
                case 44:
                    break;
                case 45:
                    return mapDoubleNumber(node);
                case 46:
                    return new JsStringLiteral(node.getString());
                case 56:
                    return mapRegExp(node);
                case 75:
                    return mapTryStatement(node);
                case 113:
                    return mapIfStatement(node);
                case 115:
                    return mapSwitchStatement(node);
                case 140:
                    return mapExpressionStatement(node);
                case 146:
                    return mapDebuggerStatement(node);
                case 147:
                    JsBlock jsBlock = new JsBlock();
                    mapStatements(jsBlock.getStatements(), node);
                    return jsBlock;
                case 148:
                    return mapIntNumber(node);
                default:
                    switch (type) {
                        case 10:
                            return mapBinaryOperation(JsBinaryOperator.ASG, node);
                        case 11:
                            return mapBinaryOperation(JsBinaryOperator.BIT_OR, node);
                        case 12:
                            return mapBinaryOperation(JsBinaryOperator.BIT_XOR, node);
                        case 13:
                            return mapBinaryOperation(JsBinaryOperator.BIT_AND, node);
                        default:
                            switch (type) {
                                case 23:
                                    return mapBinaryOperation(JsBinaryOperator.ADD, node);
                                case 24:
                                    return mapBinaryOperation(JsBinaryOperator.SUB, node);
                                case 25:
                                    return mapBinaryOperation(JsBinaryOperator.MUL, node);
                                case 26:
                                    return mapBinaryOperation(JsBinaryOperator.DIV, node);
                                case 27:
                                    return mapBinaryOperation(JsBinaryOperator.MOD, node);
                                default:
                                    switch (type) {
                                        case 96:
                                            return mapBinaryOperation(JsBinaryOperator.COMMA, node);
                                        case 97:
                                            return mapAssignmentVariant(node);
                                        case 98:
                                            return mapConditional(node);
                                        default:
                                            switch (type) {
                                                case 100:
                                                    return mapBinaryOperation(JsBinaryOperator.OR, node);
                                                case 101:
                                                    return mapBinaryOperation(JsBinaryOperator.AND, node);
                                                case 102:
                                                    return mapEqualityVariant(node);
                                                case 103:
                                                    return mapRelationalVariant(node);
                                                case 104:
                                                    return mapShiftVariant(node);
                                                case 105:
                                                    return mapUnaryVariant(node);
                                                case 106:
                                                    return mapIncDecFixity(JsUnaryOperator.INC, node);
                                                case 107:
                                                    return mapIncDecFixity(JsUnaryOperator.DEC, node);
                                                default:
                                                    switch (type) {
                                                        case 118:
                                                            return mapDoOrWhileStatement(true, node);
                                                        case 119:
                                                            return mapDoOrWhileStatement(false, node);
                                                        case 120:
                                                            return mapForStatement(node);
                                                        case 121:
                                                            return mapBreak(node);
                                                        case 122:
                                                            return mapContinue(node);
                                                        case 123:
                                                            return mapVar(node);
                                                        case 124:
                                                            return mapWithStatement(node);
                                                        default:
                                                            switch (type) {
                                                                case 132:
                                                                    return null;
                                                                case 133:
                                                                    return mapBlock(node);
                                                                case 134:
                                                                    return mapArrayLit(node);
                                                                case 135:
                                                                    return mapObjectLit(node);
                                                                case 136:
                                                                    return mapLabel(node);
                                                                default:
                                                                    throw createParserException("Unexpected top-level token type: " + node.getType(), node);
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
        return this.scopeContext.globalNameFor(node.getString()).makeRef();
    }

    private JsArrayLiteral mapArrayLit(Node node) throws JsParserException {
        JsArrayLiteral jsArrayLiteral = new JsArrayLiteral();
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            jsArrayLiteral.getExpressions().add(mapExpression(firstChild));
        }
        return jsArrayLiteral;
    }

    private JsNameRef mapAsPropertyNameRef(Node node) throws JsParserException {
        JsNode map = map(node);
        if (map instanceof JsStringLiteral) {
            return this.scopeContext.referenceFor(((JsStringLiteral) map).getValue());
        }
        throw createParserException("Expecting a name reference", node);
    }

    private JsExpression mapAssignmentVariant(Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation == 128) {
            return mapBinaryOperation(JsBinaryOperator.ASG, node);
        }
        switch (operation) {
            case 11:
                return mapBinaryOperation(JsBinaryOperator.ASG_BIT_OR, node);
            case 12:
                return mapBinaryOperation(JsBinaryOperator.ASG_BIT_XOR, node);
            case 13:
                return mapBinaryOperation(JsBinaryOperator.ASG_BIT_AND, node);
            default:
                switch (operation) {
                    case 20:
                        return mapBinaryOperation(JsBinaryOperator.ASG_SHL, node);
                    case 21:
                        return mapBinaryOperation(JsBinaryOperator.ASG_SHR, node);
                    case 22:
                        return mapBinaryOperation(JsBinaryOperator.ASG_SHRU, node);
                    case 23:
                        return mapBinaryOperation(JsBinaryOperator.ASG_ADD, node);
                    case 24:
                        return mapBinaryOperation(JsBinaryOperator.ASG_SUB, node);
                    case 25:
                        return mapBinaryOperation(JsBinaryOperator.ASG_MUL, node);
                    case 26:
                        return mapBinaryOperation(JsBinaryOperator.ASG_DIV, node);
                    case 27:
                        return mapBinaryOperation(JsBinaryOperator.ASG_MOD, node);
                    default:
                        throw createParserException("Unknown assignment operator variant: " + node.getOperation(), node);
                }
        }
    }

    private JsExpression mapBinaryOperation(JsBinaryOperator jsBinaryOperator, Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        return new JsBinaryOperation(jsBinaryOperator, mapExpression(firstChild), mapExpression(firstChild.getNext()));
    }

    private JsBlock mapBlock(Node node) throws JsParserException {
        JsBlock jsBlock = new JsBlock();
        mapStatements(jsBlock.getStatements(), node);
        return jsBlock;
    }

    private JsBreak mapBreak(Node node) {
        return new JsBreak(getTargetLabel(node));
    }

    private JsNameRef getTargetLabel(Node node) {
        if (node == null) {
            $$$reportNull$$$0(2);
        }
        int type = node.getType();
        if (type != 121 && type != 122) {
            throw new AssertionError("Unexpected node type with label: " + TokenStream.tokenToName(node.getType()));
        }
        Node firstChild = node.getFirstChild();
        if (firstChild == null) {
            return null;
        }
        return this.scopeContext.labelFor(firstChild.getString()).makeRef();
    }

    private JsInvocation mapCall(Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        JsExpression jsExpressionMapExpression = mapExpression(firstChild);
        SmartList smartList = new SmartList();
        for (Node next = firstChild.getNext(); next != null; next = next.getNext()) {
            smartList.add(mapExpression(next));
        }
        return new JsInvocation(jsExpressionMapExpression, smartList);
    }

    private JsExpression mapConditional(Node node) throws JsParserException {
        JsConditional jsConditional = new JsConditional();
        Node firstChild = node.getFirstChild();
        jsConditional.setTestExpression(mapExpression(firstChild));
        Node next = firstChild.getNext();
        jsConditional.setThenExpression(mapExpression(next));
        jsConditional.setElseExpression(mapExpression(next.getNext()));
        return jsConditional;
    }

    private JsContinue mapContinue(Node node) {
        return new JsContinue(getTargetLabel(node));
    }

    private JsStatement mapDebuggerStatement(Node node) {
        return new JsDebugger();
    }

    private JsExpression mapDeleteProp(Node node) throws JsParserException {
        JsExpression jsExpressionMapExpression = mapExpression(node.getFirstChild());
        if (jsExpressionMapExpression instanceof JsNameRef) {
            return new JsPrefixOperation(JsUnaryOperator.DELETE, jsExpressionMapExpression);
        }
        if (jsExpressionMapExpression instanceof JsArrayAccess) {
            return new JsPrefixOperation(JsUnaryOperator.DELETE, jsExpressionMapExpression);
        }
        return new JsNullLiteral();
    }

    private JsStatement mapDoOrWhileStatement(boolean z, Node node) throws JsParserException {
        Node next;
        Node next2;
        if (z) {
            next = node.getFirstChild();
            next2 = node.getFirstChild().getNext();
        } else {
            Node firstChild = node.getFirstChild();
            next = node.getFirstChild().getNext();
            next2 = firstChild;
        }
        JsExpression jsExpressionMapExpression = mapExpression(next);
        JsStatement jsStatementMapStatement = mapStatement(next2);
        if (z) {
            return new JsWhile(jsExpressionMapExpression, jsStatementMapStatement);
        }
        return new JsDoWhile(jsExpressionMapExpression, jsStatementMapStatement);
    }

    private JsExpression mapEqualityVariant(Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation == 53) {
            return mapBinaryOperation(JsBinaryOperator.REF_EQ, node);
        }
        if (operation != 54) {
            switch (operation) {
                case 14:
                    return mapBinaryOperation(JsBinaryOperator.EQ, node);
                case 15:
                    return mapBinaryOperation(JsBinaryOperator.NEQ, node);
                case 16:
                    return mapBinaryOperation(JsBinaryOperator.LT, node);
                case 17:
                    return mapBinaryOperation(JsBinaryOperator.LTE, node);
                case 18:
                    return mapBinaryOperation(JsBinaryOperator.GT, node);
                case 19:
                    return mapBinaryOperation(JsBinaryOperator.GTE, node);
                default:
                    throw createParserException("Unknown equality operator variant: " + node.getOperation(), node);
            }
        }
        return mapBinaryOperation(JsBinaryOperator.REF_NEQ, node);
    }

    public JsExpression mapExpression(Node node) throws JsParserException {
        JsNode map = map(node);
        if (map instanceof JsExpression) {
            return (JsExpression) map;
        }
        throw createParserException("Expecting an expression", node);
    }

    private JsStatement mapExpressionStatement(Node node) throws JsParserException {
        return mapExpression(node.getFirstChild()).makeStmt();
    }

    private JsStatement mapForStatement(Node node) throws JsParserException {
        JsFor jsFor;
        JsForIn jsForIn;
        Node firstChild = node.getFirstChild();
        Node next = firstChild.getNext();
        Node next2 = next.getNext();
        Node next3 = next2.getNext();
        if (next3 == null) {
            Node firstChild2 = node.getFirstChild();
            Node next4 = firstChild2.getNext();
            Node next5 = next4.getNext();
            if (firstChild2.getType() == 123) {
                Node firstChild3 = firstChild2.getFirstChild();
                jsForIn = new JsForIn(this.scopeContext.localNameFor(firstChild3.getString()));
                Node firstChild4 = firstChild3.getFirstChild();
                if (firstChild4 != null) {
                    jsForIn.setIterExpression(mapOptionalExpression(firstChild4));
                }
            } else {
                jsForIn = new JsForIn();
                jsForIn.setIterExpression(mapExpression(firstChild2));
            }
            jsForIn.setObjectExpression(mapExpression(next4));
            JsStatement jsStatementMapStatement = mapStatement(next5);
            if (jsStatementMapStatement != null) {
                jsForIn.setBody(jsStatementMapStatement);
            } else {
                jsForIn.setBody(JsEmpty.INSTANCE);
            }
            return jsForIn;
        }
        JsNode map = map(firstChild);
        JsExpression jsExpressionMapOptionalExpression = mapOptionalExpression(next);
        JsExpression jsExpressionMapOptionalExpression2 = mapOptionalExpression(next2);
        if (map instanceof JsVars) {
            jsFor = new JsFor((JsVars) map, jsExpressionMapOptionalExpression, jsExpressionMapOptionalExpression2);
        } else {
            jsFor = new JsFor((JsExpression) map, jsExpressionMapOptionalExpression, jsExpressionMapOptionalExpression2);
        }
        JsStatement jsStatementMapStatement2 = mapStatement(next3);
        if (jsStatementMapStatement2 != null) {
            jsFor.setBody(jsStatementMapStatement2);
        } else {
            jsFor.setBody(JsEmpty.INSTANCE);
        }
        return jsFor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JsFunction mapFunction(Node node) throws JsParserException {
        node.getType();
        Node firstChild = node.getFirstChild();
        Node next = node.getFirstChild().getNext().getNext();
        String string = firstChild.getString();
        JsName jsNameLocalNameFor = (string == null || string.length() <= 0) ? null : this.scopeContext.localNameFor(string);
        JsFunction jsFunctionEnterFunction = this.scopeContext.enterFunction();
        jsFunctionEnterFunction.setName(jsNameLocalNameFor);
        for (Node firstChild2 = node.getFirstChild().getNext().getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNext()) {
            jsFunctionEnterFunction.getParameters().add(withLocation(new JsParameter(this.scopeContext.localNameFor(firstChild2.getString())), firstChild2));
        }
        jsFunctionEnterFunction.setBody(mapBlock(next));
        this.scopeContext.exitFunction();
        return jsFunctionEnterFunction;
    }

    private JsArrayAccess mapGetElem(Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        return new JsArrayAccess(mapExpression(firstChild), mapExpression(firstChild.getNext()));
    }

    private JsNameRef mapGetProp(Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        Node next = firstChild.getNext();
        JsExpression jsExpressionMapExpression = mapExpression(firstChild);
        JsNameRef jsNameRefMapAsPropertyNameRef = mapAsPropertyNameRef(next);
        jsNameRefMapAsPropertyNameRef.setQualifier(jsExpressionMapExpression);
        return jsNameRefMapAsPropertyNameRef;
    }

    private JsIf mapIfStatement(Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        Node next = node.getFirstChild().getNext();
        Node next2 = node.getFirstChild().getNext().getNext();
        JsIf jsIf = new JsIf(mapExpression(firstChild), mapStatement(next));
        if (next2 != null) {
            jsIf.setElseStatement(mapStatement(next2));
        }
        return jsIf;
    }

    private JsExpression mapIncDecFixity(JsUnaryOperator jsUnaryOperator, Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation == 130) {
            return mapPrefixOperation(jsUnaryOperator, node);
        }
        if (operation == 131) {
            return mapPostfixOperation(jsUnaryOperator, node);
        }
        throw createParserException("Unknown prefix/postfix variant: " + node.getOperation(), node);
    }

    private JsLabel mapLabel(Node node) throws JsParserException {
        String string = node.getFirstChild().getString();
        JsName jsNameEnterLabel = this.scopeContext.enterLabel(string, string);
        Node next = node.getFirstChild().getNext();
        JsLabel jsLabel = new JsLabel(jsNameEnterLabel);
        jsLabel.setStatement(mapStatement(next));
        this.scopeContext.exitLabel();
        return jsLabel;
    }

    private JsNew mapNew(Node node) throws JsParserException {
        Node firstChild = node.getFirstChild();
        JsNew jsNew = new JsNew(mapExpression(firstChild));
        List<JsExpression> arguments = jsNew.getArguments();
        for (Node next = firstChild.getNext(); next != null; next = next.getNext()) {
            arguments.add(mapExpression(next));
        }
        return jsNew;
    }

    private static JsExpression mapIntNumber(Node node) {
        double d = node.getDouble();
        if (d <= 2.147483647E9d && d >= -2.147483648E9d) {
            return new JsIntLiteral((int) node.getDouble());
        }
        return new JsDoubleLiteral(node.getDouble());
    }

    private static JsExpression mapDoubleNumber(Node node) {
        return new JsDoubleLiteral(node.getDouble());
    }

    private JsExpression mapObjectLit(Node node) throws JsParserException {
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral();
        Node firstChild = node.getFirstChild();
        while (firstChild != null) {
            JsExpression jsExpressionMapExpression = mapExpression(firstChild);
            Node next = firstChild.getNext();
            if (next == null) {
                throw createParserException("Expected an init expression for: " + jsExpressionMapExpression, node);
            }
            jsObjectLiteral.getPropertyInitializers().add(new JsPropertyInitializer(jsExpressionMapExpression, mapExpression(next)));
            firstChild = next.getNext();
        }
        return jsObjectLiteral;
    }

    private JsExpression mapOptionalExpression(Node node) throws JsParserException {
        JsNode map = map(node);
        if (map == null) {
            return null;
        }
        if (map instanceof JsExpression) {
            return (JsExpression) map;
        }
        throw createParserException("Expecting an expression or null", node);
    }

    private JsExpression mapPostfixOperation(JsUnaryOperator jsUnaryOperator, Node node) throws JsParserException {
        return new JsPostfixOperation(jsUnaryOperator, mapExpression(node.getFirstChild()));
    }

    private JsExpression mapPrefixOperation(JsUnaryOperator jsUnaryOperator, Node node) throws JsParserException {
        return new JsPrefixOperation(jsUnaryOperator, mapExpression(node.getFirstChild()));
    }

    private static JsExpression mapPrimary(Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation != 74) {
            switch (operation) {
                case 49:
                    return new JsNullLiteral();
                case 50:
                    return new JsThisRef();
                case 51:
                    return new JsBooleanLiteral(false);
                case 52:
                    return new JsBooleanLiteral(true);
                default:
                    throw createParserException("Unknown primary: " + node.getOperation(), node);
            }
        }
        return new JsNameRef("undefined");
    }

    private JsNode mapRegExp(Node node) {
        JsRegExp jsRegExp = new JsRegExp();
        Node firstChild = node.getFirstChild();
        jsRegExp.setPattern(firstChild.getString());
        Node next = firstChild.getNext();
        if (next != null) {
            jsRegExp.setFlags(next.getString());
        }
        return jsRegExp;
    }

    private JsExpression mapRelationalVariant(Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation == 63) {
            return mapBinaryOperation(JsBinaryOperator.INOP, node);
        }
        if (operation != 64) {
            switch (operation) {
                case 16:
                    return mapBinaryOperation(JsBinaryOperator.LT, node);
                case 17:
                    return mapBinaryOperation(JsBinaryOperator.LTE, node);
                case 18:
                    return mapBinaryOperation(JsBinaryOperator.GT, node);
                case 19:
                    return mapBinaryOperation(JsBinaryOperator.GTE, node);
                default:
                    throw createParserException("Unknown relational operator variant: " + node.getOperation(), node);
            }
        }
        return mapBinaryOperation(JsBinaryOperator.INSTANCEOF, node);
    }

    private JsReturn mapReturn(Node node) throws JsParserException {
        JsReturn jsReturn = new JsReturn();
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            jsReturn.setExpression(mapExpression(firstChild));
        }
        return jsReturn;
    }

    private JsExpression mapSetElem(Node node) throws JsParserException {
        return new JsBinaryOperation(JsBinaryOperator.ASG, mapGetElem(node), mapExpression(node.getFirstChild().getNext().getNext()));
    }

    private JsExpression mapSetProp(Node node) throws JsParserException {
        return new JsBinaryOperation(JsBinaryOperator.ASG, mapGetProp(node), mapExpression(node.getFirstChild().getNext().getNext()));
    }

    private JsExpression mapShiftVariant(Node node) throws JsParserException {
        switch (node.getOperation()) {
            case 20:
                return mapBinaryOperation(JsBinaryOperator.SHL, node);
            case 21:
                return mapBinaryOperation(JsBinaryOperator.SHR, node);
            case 22:
                return mapBinaryOperation(JsBinaryOperator.SHRU, node);
            default:
                throw createParserException("Unknown equality operator variant: " + node.getOperation(), node);
        }
    }

    private JsStatement mapStatement(Node node) throws JsParserException {
        JsNode map = map(node);
        if (map != null) {
            if (map instanceof JsStatement) {
                return (JsStatement) map;
            }
            if (map instanceof JsExpression) {
                return ((JsExpression) map).makeStmt();
            }
            throw createParserException("Expecting a statement", node);
        }
        return JsEmpty.INSTANCE;
    }

    private void mapStatements(List<JsStatement> list, Node node) throws JsParserException {
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            JsStatement jsStatementMapStatement = mapStatement(firstChild);
            if (jsStatementMapStatement != null) {
                list.add(jsStatementMapStatement);
            }
        }
    }

    public List<JsStatement> mapStatements(Node node) throws JsParserException {
        ArrayList arrayList = new ArrayList();
        mapStatements(arrayList, node);
        return arrayList;
    }

    private JsSwitch mapSwitchStatement(Node node) throws JsParserException {
        JsSwitch jsSwitch = new JsSwitch();
        Node firstChild = node.getFirstChild();
        jsSwitch.setExpression(mapExpression(firstChild));
        for (Node next = firstChild.getNext(); next != null; next = next.getNext()) {
            if (next.getType() == 116) {
                JsCase jsCase = new JsCase();
                Node firstChild2 = next.getFirstChild();
                jsCase.setCaseExpression(mapExpression(firstChild2));
                mapStatements(jsCase.getStatements(), firstChild2.getNext());
                jsSwitch.getCases().add(jsCase);
            } else {
                JsDefault jsDefault = new JsDefault();
                mapStatements(jsDefault.getStatements(), next.getFirstChild());
                jsSwitch.getCases().add(jsDefault);
            }
        }
        return jsSwitch;
    }

    private JsThrow mapThrowStatement(Node node) throws JsParserException {
        return new JsThrow(mapExpression(node.getFirstChild()));
    }

    private JsTry mapTryStatement(Node node) throws JsParserException {
        JsTry jsTry = new JsTry();
        Node firstChild = node.getFirstChild();
        jsTry.setTryBlock(mapBlock(firstChild));
        Node next = firstChild.getNext();
        Node firstChild2 = next.getFirstChild();
        while (firstChild2 != null) {
            Node firstChild3 = firstChild2.getFirstChild();
            JsCatch jsCatchEnterCatch = this.scopeContext.enterCatch(firstChild3.getString());
            firstChild2 = firstChild2.getNext();
            Node next2 = firstChild3.getNext();
            JsExpression jsExpressionMapExpression = mapExpression(next2);
            jsCatchEnterCatch.setCondition(jsExpressionMapExpression);
            if (firstChild2 == null && (jsExpressionMapExpression instanceof JsBooleanLiteral) && ((JsBooleanLiteral) jsExpressionMapExpression).getValue()) {
                jsCatchEnterCatch.setCondition(null);
            }
            jsCatchEnterCatch.setBody(mapBlock(next2.getNext()));
            jsTry.getCatches().add(jsCatchEnterCatch);
            this.scopeContext.exitCatch();
        }
        Node next3 = next.getNext();
        if (next3 != null) {
            jsTry.setFinallyBlock(mapBlock(next3));
        }
        return jsTry;
    }

    private JsExpression mapUnaryVariant(Node node) throws JsParserException {
        int operation = node.getOperation();
        if (operation == 23) {
            return mapPrefixOperation(JsUnaryOperator.POS, node);
        }
        if (operation == 24) {
            Node firstChild = node.getFirstChild();
            if (firstChild.getType() == 148) {
                double d = -firstChild.getDouble();
                if (d >= -2.147483648E9d) {
                    return new JsIntLiteral((int) d);
                }
            }
            return mapPrefixOperation(JsUnaryOperator.NEG, node);
        }
        if (operation == 28) {
            return mapPrefixOperation(JsUnaryOperator.BIT_NOT, node);
        }
        if (operation == 32) {
            return mapPrefixOperation(JsUnaryOperator.TYPEOF, node);
        }
        if (operation == 129) {
            return mapPrefixOperation(JsUnaryOperator.NOT, node);
        }
        if (operation == 132) {
            return mapPrefixOperation(JsUnaryOperator.VOID, node);
        }
        throw createParserException("Unknown unary operator variant: " + node.getOperation(), node);
    }

    private JsVars mapVar(Node node) throws JsParserException {
        JsVars jsVars = new JsVars();
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            JsVars.JsVar jsVar = (JsVars.JsVar) withLocation(new JsVars.JsVar(this.scopeContext.localNameFor(firstChild.getString())), firstChild);
            Node firstChild2 = firstChild.getFirstChild();
            if (firstChild2 != null) {
                jsVar.setInitExpression(mapExpression(firstChild2));
            }
            jsVars.add(jsVar);
        }
        return jsVars;
    }

    private JsNode mapWithStatement(Node node) throws JsParserException {
        throw createParserException("Internal error: unexpected token 'with'", node);
    }

    private <T extends JsNode> T withLocation(T t, Node node) {
        JsName name2;
        String string = null;
        if (t == null) {
            return null;
        }
        CodePosition position = node.getPosition();
        int type = node.getType();
        if (type == 39) {
            position = node.getLastChild().getPosition();
        } else if (type == 110) {
            Node firstChild = node.getFirstChild();
            while (firstChild != null && firstChild.getType() != 94) {
                firstChild = firstChild.getNext();
            }
            if (firstChild != null && firstChild.getPosition() != null) {
                position = firstChild.getPosition();
            }
        }
        if (position != null) {
            if (((t instanceof JsFunction) || (t instanceof JsVars.JsVar) || (t instanceof JsParameter)) && (name2 = ((HasName) t).getName()) != null) {
                string = name2.toString();
            }
            JsLocation jsLocation = new JsLocation(this.fileName, position.getLine(), position.getOffset(), string);
            if (t instanceof SourceInfoAwareJsNode) {
                t.setSource(jsLocation);
            } else if (t instanceof JsExpressionStatement) {
                JsExpression expression = ((JsExpressionStatement) t).getExpression();
                if (expression.getSource() == null) {
                    expression.setSource(jsLocation);
                }
            }
        }
        return t;
    }

    private List<JsComment> mapComments(Comment comment) {
        if (comment == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        while (comment != null) {
            String text = comment.getText();
            linkedList.add(comment.getIsMultiLine() ? new JsMultiLineComment(text) : new JsSingleLineComment(text));
            comment = comment.getNext();
        }
        return linkedList;
    }
}
