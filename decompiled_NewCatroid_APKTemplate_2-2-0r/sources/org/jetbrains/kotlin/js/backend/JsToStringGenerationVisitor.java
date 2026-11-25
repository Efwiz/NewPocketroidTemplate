package org.jetbrains.kotlin.js.backend;

import gnu.trove.THashSet;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
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
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsComment;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsDocComment;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExport;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsNumberLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsOperator;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsRegExp;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.util.TextOutput;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes5.dex */
public class JsToStringGenerationVisitor extends JsVisitor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Set<JsBlock> globalBlocks;
    protected boolean insideComments;
    private boolean lineBreakAfterBlock;
    protected boolean needSemi;
    protected final TextOutput p;
    private final List<Object> sourceInfoStack;
    private final SourceLocationConsumer sourceLocationConsumer;
    private static final char[] CHARS_BREAK = PsiKeyword.BREAK.toCharArray();
    private static final char[] CHARS_CASE = PsiKeyword.CASE.toCharArray();
    private static final char[] CHARS_CATCH = PsiKeyword.CATCH.toCharArray();
    private static final char[] CHARS_CLASS = "class".toCharArray();
    private static final char[] CHARS_CONSTRUCTOR = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME.toCharArray();
    private static final char[] CHARS_CONTINUE = PsiKeyword.CONTINUE.toCharArray();
    private static final char[] CHARS_DEBUGGER = "debugger".toCharArray();
    private static final char[] CHARS_DEFAULT = "default".toCharArray();
    private static final char[] CHARS_DO = PsiKeyword.DO.toCharArray();
    private static final char[] CHARS_ELSE = PsiKeyword.ELSE.toCharArray();
    private static final char[] CHARS_EXTENDS = PsiKeyword.EXTENDS.toCharArray();
    private static final char[] CHARS_FALSE = "false".toCharArray();
    private static final char[] CHARS_FINALLY = PsiKeyword.FINALLY.toCharArray();
    private static final char[] CHARS_FOR = PsiKeyword.FOR.toCharArray();
    private static final char[] CHARS_FUNCTION = "function".toCharArray();
    private static final char[] CHARS_STATIC = "static".toCharArray();
    private static final char[] CHARS_GET = "get".toCharArray();
    private static final char[] CHARS_SET = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.toCharArray();
    private static final char[] CHARS_IF = PsiKeyword.IF.toCharArray();
    private static final char[] CHARS_IN = "in".toCharArray();
    private static final char[] CHARS_NEW = PsiKeyword.NEW.toCharArray();
    private static final char[] CHARS_NULL = "null".toCharArray();
    private static final char[] CHARS_RETURN = "return".toCharArray();
    private static final char[] CHARS_SWITCH = PsiKeyword.SWITCH.toCharArray();
    private static final char[] CHARS_THIS = "this".toCharArray();
    private static final char[] CHARS_SUPER = PsiKeyword.SUPER.toCharArray();
    private static final char[] CHARS_THROW = PsiKeyword.THROW.toCharArray();
    private static final char[] CHARS_TRUE = "true".toCharArray();
    private static final char[] CHARS_TRY = PsiKeyword.TRY.toCharArray();
    private static final char[] CHARS_VAR = PsiKeyword.VAR.toCharArray();
    private static final char[] CHARS_WHILE = PsiKeyword.WHILE.toCharArray();
    private static final char[] HEX_DIGITS = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', Util.C_BYTE, Util.C_CHAR, Util.C_DOUBLE, 'E', Util.C_FLOAT};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "sourceLocationConsumer";
                break;
            case 2:
            default:
                objArr[0] = "out";
                break;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
                objArr[0] = "x";
                break;
            case 5:
                objArr[0] = "binaryOperation";
                break;
            case 25:
                objArr[0] = "statement";
                break;
            case 26:
                objArr[0] = "invocation";
                break;
            case 28:
            case 29:
                objArr[0] = "nameRef";
                break;
            case 34:
                objArr[0] = "objectLiteral";
                break;
            case 47:
                objArr[0] = PsiKeyword.VAR;
                break;
            case 48:
                objArr[0] = "vars";
                break;
            case 49:
            case 50:
            case 51:
                objArr[0] = "comment";
                break;
            case 52:
                objArr[0] = "export";
                break;
            case 53:
                objArr[0] = "jsImport";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/js/backend/JsToStringGenerationVisitor";
        switch (i) {
            case 3:
                objArr[2] = "visitArrayAccess";
                break;
            case 4:
                objArr[2] = "visitArray";
                break;
            case 5:
                objArr[2] = "visitBinaryExpression";
                break;
            case 6:
                objArr[2] = "visitBlock";
                break;
            case 7:
                objArr[2] = "visitBoolean";
                break;
            case 8:
                objArr[2] = "visitBreak";
                break;
            case 9:
                objArr[2] = "visitContinue";
                break;
            case 10:
                objArr[2] = "visitCase";
                break;
            case 11:
                objArr[2] = "visitCatch";
                break;
            case 12:
                objArr[2] = "visitConditional";
                break;
            case 13:
                objArr[2] = "visitDebugger";
                break;
            case 14:
                objArr[2] = "visitDefault";
                break;
            case 15:
                objArr[2] = "visitWhile";
                break;
            case 16:
                objArr[2] = "visitDoWhile";
                break;
            case 17:
                objArr[2] = "visitEmpty";
                break;
            case 18:
                objArr[2] = "visitExpressionStatement";
                break;
            case 19:
                objArr[2] = "visitFor";
                break;
            case 20:
                objArr[2] = "visitForIn";
                break;
            case 21:
                objArr[2] = "visitFunction";
                break;
            case 22:
                objArr[2] = "printFunction";
                break;
            case 23:
                objArr[2] = "visitClass";
                break;
            case 24:
                objArr[2] = "visitIf";
                break;
            case 25:
                objArr[2] = "isIfWithoutElse";
                break;
            case 26:
                objArr[2] = "visitInvocation";
                break;
            case 27:
                objArr[2] = "visitLabel";
                break;
            case 28:
            case 29:
                objArr[2] = "visitNameRef";
                break;
            case 30:
                objArr[2] = "visitNew";
                break;
            case 31:
                objArr[2] = "visitNull";
                break;
            case 32:
                objArr[2] = "visitInt";
                break;
            case 33:
                objArr[2] = "visitDouble";
                break;
            case 34:
                objArr[2] = "visitObjectLiteral";
                break;
            case 35:
                objArr[2] = "visitParameter";
                break;
            case 36:
                objArr[2] = "visitPostfixOperation";
                break;
            case 37:
                objArr[2] = "visitPrefixOperation";
                break;
            case 38:
                objArr[2] = "visitProgram";
                break;
            case 39:
                objArr[2] = "visitRegExp";
                break;
            case 40:
                objArr[2] = "visitReturn";
                break;
            case 41:
                objArr[2] = "visitString";
                break;
            case 42:
            case 47:
                objArr[2] = "visit";
                break;
            case 43:
                objArr[2] = "visitThis";
                break;
            case 44:
                objArr[2] = "visitSuper";
                break;
            case 45:
                objArr[2] = "visitThrow";
                break;
            case 46:
                objArr[2] = "visitTry";
                break;
            case 48:
                objArr[2] = "visitVars";
                break;
            case 49:
                objArr[2] = "visitSingleLineComment";
                break;
            case 50:
                objArr[2] = "visitMultiLineComment";
                break;
            case 51:
                objArr[2] = "visitDocComment";
                break;
            case 52:
                objArr[2] = "visitExport";
                break;
            case 53:
                objArr[2] = "visitImport";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitEmpty(JsEmpty jsEmpty) {
        if (jsEmpty == null) {
            $$$reportNull$$$0(17);
        }
    }

    public static CharSequence javaScriptString(String str) {
        return javaScriptString(str, false);
    }

    public static CharSequence javaScriptString(CharSequence charSequence, boolean z) {
        int i;
        int i2;
        int length = charSequence.length();
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char cCharAt = charSequence.charAt(i5);
            if (cCharAt == '\"') {
                i3++;
            } else if (cCharAt == '\'') {
                i4++;
            }
        }
        StringBuilder sb = new StringBuilder(length + 16);
        char c = (i3 < i4 || z) ? '\"' : '\'';
        sb.append(c);
        for (int i6 = 0; i6 < length; i6++) {
            char cCharAt2 = charSequence.charAt(i6);
            if (' ' > cCharAt2 || cCharAt2 > '~' || cCharAt2 == c || cCharAt2 == '\\') {
                if (cCharAt2 == '\f') {
                    i = 102;
                } else if (cCharAt2 == '\r') {
                    i = 114;
                } else if (cCharAt2 == '\"') {
                    i = 34;
                } else if (cCharAt2 == '\'') {
                    i = 39;
                } else if (cCharAt2 != '\\') {
                    switch (cCharAt2) {
                        case '\b':
                            i = 98;
                            break;
                        case '\t':
                            i = 116;
                            break;
                        case '\n':
                            i = 110;
                            break;
                        default:
                            i = -1;
                            break;
                    }
                } else {
                    i = 92;
                }
                if (i >= 0) {
                    sb.append('\\');
                    sb.append((char) i);
                } else {
                    if (cCharAt2 < 256) {
                        sb.append("\\x");
                        i2 = 2;
                    } else {
                        sb.append("\\u");
                        i2 = 4;
                    }
                    for (int i7 = (i2 - 1) * 4; i7 >= 0; i7 -= 4) {
                        sb.append(HEX_DIGITS[(cCharAt2 >> i7) & 15]);
                    }
                }
            } else {
                sb.append(cCharAt2);
            }
        }
        sb.append(c);
        escapeClosingTags(sb);
        return sb;
    }

    private static void escapeClosingTags(StringBuilder sb) {
        if (sb == null) {
            return;
        }
        int iIndexOf = 0;
        while (true) {
            iIndexOf = sb.indexOf("</", iIndexOf);
            if (iIndexOf == -1) {
                return;
            } else {
                sb.insert(iIndexOf + 1, '\\');
            }
        }
    }

    public JsToStringGenerationVisitor(TextOutput textOutput, SourceLocationConsumer sourceLocationConsumer) {
        if (textOutput == null) {
            $$$reportNull$$$0(0);
        }
        if (sourceLocationConsumer == null) {
            $$$reportNull$$$0(1);
        }
        this.sourceInfoStack = new ArrayList();
        this.insideComments = false;
        this.needSemi = true;
        this.lineBreakAfterBlock = true;
        this.globalBlocks = new THashSet();
        this.p = textOutput;
        this.sourceLocationConsumer = sourceLocationConsumer;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsToStringGenerationVisitor(TextOutput textOutput) {
        this(textOutput, NoOpSourceLocationConsumer.INSTANCE);
        if (textOutput == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArrayAccess(JsArrayAccess jsArrayAccess) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(3);
        }
        printCommentsBeforeNode(jsArrayAccess);
        pushSourceInfo(jsArrayAccess.getSource());
        printPair(jsArrayAccess, jsArrayAccess.getArrayExpression());
        leftSquare();
        accept(jsArrayAccess.getIndexExpression());
        rightSquare();
        printCommentsAfterNode(jsArrayAccess);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArray(JsArrayLiteral jsArrayLiteral) {
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(4);
        }
        printCommentsBeforeNode(jsArrayLiteral);
        pushSourceInfo(jsArrayLiteral.getSource());
        leftSquare();
        printExpressions(jsArrayLiteral.getExpressions());
        rightSquare();
        printCommentsAfterNode(jsArrayLiteral);
        popSourceInfo();
    }

    private void printExpressions(List<JsExpression> list) {
        boolean z = false;
        for (JsExpression jsExpression : list) {
            z = sepCommaOptSpace(z) && !(jsExpression instanceof JsDocComment);
            boolean zParenPushIfCommaExpression = parenPushIfCommaExpression(jsExpression);
            accept(jsExpression);
            if (zParenPushIfCommaExpression) {
                rightParen();
            }
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBinaryExpression(JsBinaryOperation jsBinaryOperation) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(5);
        }
        printCommentsBeforeNode(jsBinaryOperation);
        pushSourceInfo(jsBinaryOperation.getSource());
        JsBinaryOperator operator = jsBinaryOperation.getOperator();
        JsExpression arg1 = jsBinaryOperation.getArg1();
        boolean zParenPush = true;
        boolean zParenPush2 = parenPush(jsBinaryOperation, arg1, !operator.isLeftAssociative());
        accept(arg1);
        if (operator.isKeyword()) {
            _parenPopOrSpace(jsBinaryOperation, arg1, !operator.isLeftAssociative());
        } else if (operator != JsBinaryOperator.COMMA) {
            if (zParenPush2) {
                rightParen();
            }
            spaceOpt();
        }
        this.p.print(operator.getSymbol());
        JsExpression arg2 = jsBinaryOperation.getArg2();
        if (operator == JsBinaryOperator.COMMA) {
            spaceOpt();
            zParenPush = false;
        } else if ((arg2 instanceof JsBinaryOperation) && ((JsBinaryOperation) arg2).getOperator() == JsBinaryOperator.AND) {
            spaceOpt();
            leftParen();
        } else if (spaceCalc(operator, arg2)) {
            zParenPush = _parenPushOrSpace(jsBinaryOperation, arg2, operator.isLeftAssociative());
        } else {
            spaceOpt();
            zParenPush = parenPush(jsBinaryOperation, arg2, operator.isLeftAssociative());
        }
        accept(arg2);
        if (zParenPush) {
            rightParen();
        }
        printCommentsAfterNode(jsBinaryOperation);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBlock(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(6);
        }
        printJsBlock(jsBlock, true, null);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBoolean(JsBooleanLiteral jsBooleanLiteral) {
        if (jsBooleanLiteral == null) {
            $$$reportNull$$$0(7);
        }
        pushSourceInfo(jsBooleanLiteral.getSource());
        printCommentsBeforeNode(jsBooleanLiteral);
        if (jsBooleanLiteral.getValue()) {
            this.p.print(CHARS_TRUE);
        } else {
            this.p.print(CHARS_FALSE);
        }
        printCommentsAfterNode(jsBooleanLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBreak(JsBreak jsBreak) {
        if (jsBreak == null) {
            $$$reportNull$$$0(8);
        }
        pushSourceInfo(jsBreak.getSource());
        printCommentsBeforeNode(jsBreak);
        this.p.print(CHARS_BREAK);
        continueOrBreakLabel(jsBreak);
        printCommentsAfterNode(jsBreak);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitContinue(JsContinue jsContinue) {
        if (jsContinue == null) {
            $$$reportNull$$$0(9);
        }
        pushSourceInfo(jsContinue.getSource());
        printCommentsBeforeNode(jsContinue);
        this.p.print(CHARS_CONTINUE);
        continueOrBreakLabel(jsContinue);
        printCommentsAfterNode(jsContinue);
        popSourceInfo();
    }

    private void continueOrBreakLabel(JsContinue jsContinue) {
        JsNameRef label = jsContinue.getLabel();
        if (label != null) {
            space();
            this.p.print(label.getIdent());
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitCase(JsCase jsCase) {
        if (jsCase == null) {
            $$$reportNull$$$0(10);
        }
        pushSourceInfo(jsCase.getSource());
        printCommentsBeforeNode(jsCase);
        this.p.print(CHARS_CASE);
        space();
        accept(jsCase.getCaseExpression());
        _colon();
        printCommentsAfterNode(jsCase);
        popSourceInfo();
        newlineOpt();
        this.sourceLocationConsumer.pushSourceInfo(null);
        printSwitchMemberStatements(jsCase);
        this.sourceLocationConsumer.popSourceInfo();
    }

    private void printSwitchMemberStatements(JsSwitchMember jsSwitchMember) {
        this.p.indentIn();
        for (JsStatement jsStatement : jsSwitchMember.getStatements()) {
            this.needSemi = true;
            accept(jsStatement);
            if (this.needSemi) {
                semi();
            }
            newlineOpt();
        }
        this.p.indentOut();
        this.needSemi = false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitCatch(JsCatch jsCatch) {
        if (jsCatch == null) {
            $$$reportNull$$$0(11);
        }
        printCommentsBeforeNode(jsCatch);
        pushSourceInfo(jsCatch.getSource());
        spaceOpt();
        this.p.print(CHARS_CATCH);
        spaceOpt();
        leftParen();
        nameDef(jsCatch.getParameter().getName());
        JsExpression condition = jsCatch.getCondition();
        if (condition != null) {
            space();
            _if();
            space();
            accept(condition);
        }
        rightParen();
        spaceOpt();
        popSourceInfo();
        printCommentsAfterNode(jsCatch);
        this.sourceLocationConsumer.pushSourceInfo(null);
        accept(jsCatch.getBody());
        this.sourceLocationConsumer.popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitConditional(JsConditional jsConditional) {
        if (jsConditional == null) {
            $$$reportNull$$$0(12);
        }
        pushSourceInfo(jsConditional.getSource());
        printCommentsBeforeNode(jsConditional);
        printPair(jsConditional, jsConditional.getTestExpression(), true);
        spaceOpt();
        this.p.print(MangleConstant.Q_MARK);
        spaceOpt();
        printPair(jsConditional, jsConditional.getThenExpression());
        spaceOpt();
        _colon();
        spaceOpt();
        printPair(jsConditional, jsConditional.getElseExpression());
        printCommentsAfterNode(jsConditional);
        popSourceInfo();
    }

    private void printPair(JsExpression jsExpression, JsExpression jsExpression2, boolean z) {
        boolean zParenCalc = parenCalc(jsExpression, jsExpression2, z);
        if (zParenCalc) {
            leftParen();
        }
        accept(jsExpression2);
        if (zParenCalc) {
            rightParen();
        }
    }

    private void printPair(JsExpression jsExpression, JsExpression jsExpression2) {
        printPair(jsExpression, jsExpression2, false);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDebugger(JsDebugger jsDebugger) {
        if (jsDebugger == null) {
            $$$reportNull$$$0(13);
        }
        pushSourceInfo(jsDebugger.getSource());
        printCommentsBeforeNode(jsDebugger);
        this.p.print(CHARS_DEBUGGER);
        printCommentsAfterNode(jsDebugger);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDefault(JsDefault jsDefault) {
        if (jsDefault == null) {
            $$$reportNull$$$0(14);
        }
        pushSourceInfo(jsDefault.getSource());
        printCommentsBeforeNode(jsDefault);
        this.p.print(CHARS_DEFAULT);
        _colon();
        printCommentsAfterNode(jsDefault);
        popSourceInfo();
        newlineOpt();
        this.sourceLocationConsumer.pushSourceInfo(null);
        printSwitchMemberStatements(jsDefault);
        this.sourceLocationConsumer.popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitWhile(JsWhile jsWhile) {
        if (jsWhile == null) {
            $$$reportNull$$$0(15);
        }
        pushSourceInfo(jsWhile.getSource());
        printCommentsBeforeNode(jsWhile);
        _while();
        spaceOpt();
        leftParen();
        accept(jsWhile.getCondition());
        rightParen();
        printCommentsAfterNode(jsWhile);
        popSourceInfo();
        JsStatement jsStatementMaterialize = materialize(jsWhile.getBody());
        nestedPush(jsStatementMaterialize);
        this.sourceLocationConsumer.pushSourceInfo(null);
        accept(jsStatementMaterialize);
        this.sourceLocationConsumer.popSourceInfo();
        nestedPop(jsStatementMaterialize);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDoWhile(JsDoWhile jsDoWhile) {
        if (jsDoWhile == null) {
            $$$reportNull$$$0(16);
        }
        this.sourceLocationConsumer.pushSourceInfo(null);
        printCommentsBeforeNode(jsDoWhile);
        this.p.print(CHARS_DO);
        JsStatement jsStatementMaterialize = materialize(jsDoWhile.getBody());
        nestedPush(jsStatementMaterialize);
        accept(jsStatementMaterialize);
        this.sourceLocationConsumer.popSourceInfo();
        nestedPop(jsStatementMaterialize);
        pushSourceInfo(jsDoWhile.getCondition().getSource());
        if (this.needSemi) {
            semi();
            newlineOpt();
        } else {
            spaceOpt();
            this.needSemi = true;
        }
        _while();
        spaceOpt();
        leftParen();
        accept(jsDoWhile.getCondition());
        rightParen();
        printCommentsAfterNode(jsDoWhile);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitExpressionStatement(JsExpressionStatement jsExpressionStatement) {
        if (jsExpressionStatement == null) {
            $$$reportNull$$$0(18);
        }
        Object source = jsExpressionStatement.getSource();
        if (source == null && !(jsExpressionStatement.getExpression() instanceof JsFunction)) {
            source = jsExpressionStatement.getExpression().getSource();
        }
        pushSourceInfo(source);
        printCommentsBeforeNode(jsExpressionStatement);
        boolean zExec = JsFirstExpressionVisitor.exec(jsExpressionStatement);
        if (zExec) {
            leftParen();
        }
        accept(jsExpressionStatement.getExpression());
        if (zExec) {
            rightParen();
        }
        printCommentsAfterNode(jsExpressionStatement);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFor(JsFor jsFor) {
        if (jsFor == null) {
            $$$reportNull$$$0(19);
        }
        pushSourceInfo(jsFor.getSource());
        printCommentsBeforeNode(jsFor);
        _for();
        spaceOpt();
        leftParen();
        if (jsFor.getInitExpression() != null) {
            accept(jsFor.getInitExpression());
        } else if (jsFor.getInitVars() != null) {
            accept(jsFor.getInitVars());
        }
        semi();
        if (jsFor.getCondition() != null) {
            spaceOpt();
            accept(jsFor.getCondition());
        }
        semi();
        if (jsFor.getIncrementExpression() != null) {
            spaceOpt();
            accept(jsFor.getIncrementExpression());
        }
        rightParen();
        printCommentsAfterNode(jsFor);
        popSourceInfo();
        JsStatement jsStatementMaterialize = materialize(jsFor.getBody());
        nestedPush(jsStatementMaterialize);
        if (jsStatementMaterialize != null) {
            this.sourceLocationConsumer.pushSourceInfo(null);
            accept(jsStatementMaterialize);
            this.sourceLocationConsumer.popSourceInfo();
        }
        nestedPop(jsStatementMaterialize);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitForIn(JsForIn jsForIn) {
        if (jsForIn == null) {
            $$$reportNull$$$0(20);
        }
        pushSourceInfo(jsForIn.getSource());
        printCommentsBeforeNode(jsForIn);
        _for();
        spaceOpt();
        leftParen();
        if (jsForIn.getIterVarName() != null) {
            var();
            space();
            nameDef(jsForIn.getIterVarName());
            if (jsForIn.getIterExpression() != null) {
                spaceOpt();
                assignment();
                spaceOpt();
                accept(jsForIn.getIterExpression());
            }
        } else {
            accept(jsForIn.getIterExpression());
        }
        space();
        this.p.print(CHARS_IN);
        space();
        accept(jsForIn.getObjectExpression());
        rightParen();
        printCommentsAfterNode(jsForIn);
        popSourceInfo();
        JsStatement jsStatementMaterialize = materialize(jsForIn.getBody());
        nestedPush(jsStatementMaterialize);
        this.sourceLocationConsumer.pushSourceInfo(null);
        accept(jsStatementMaterialize);
        this.sourceLocationConsumer.popSourceInfo();
        nestedPop(jsStatementMaterialize);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(21);
        }
        printCommentsBeforeNode(jsFunction);
        this.p.print(CHARS_FUNCTION);
        space();
        printFunction(jsFunction);
        printCommentsAfterNode(jsFunction);
    }

    private void printFunction(JsFunction jsFunction) {
        if (jsFunction == null) {
            $$$reportNull$$$0(22);
        }
        if (jsFunction.isStatic()) {
            this.p.print(CHARS_STATIC);
            space();
        }
        if (jsFunction.isGetter()) {
            this.p.print(CHARS_GET);
            space();
        } else if (jsFunction.isSetter()) {
            this.p.print(CHARS_SET);
            space();
        }
        if (jsFunction.getName() != null) {
            nameOf(jsFunction);
        }
        pushSourceInfo(jsFunction.getSource());
        leftParen();
        this.sourceLocationConsumer.pushSourceInfo(null);
        boolean zSepCommaOptSpace = false;
        for (JsParameter jsParameter : jsFunction.getParameters()) {
            zSepCommaOptSpace = sepCommaOptSpace(zSepCommaOptSpace);
            accept(jsParameter);
        }
        this.sourceLocationConsumer.popSourceInfo();
        rightParen();
        space();
        this.lineBreakAfterBlock = false;
        this.sourceLocationConsumer.pushSourceInfo(null);
        printJsBlock(jsFunction.getBody(), true, jsFunction.getBody().getSource());
        this.sourceLocationConsumer.popSourceInfo();
        popSourceInfo();
        this.needSemi = true;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitClass(JsClass jsClass) {
        if (jsClass == null) {
            $$$reportNull$$$0(23);
        }
        pushSourceInfo(jsClass.getSource());
        printCommentsBeforeNode(jsClass);
        this.p.print(CHARS_CLASS);
        space();
        if (jsClass.getName() != null) {
            nameOf(jsClass);
        }
        if (jsClass.getBaseClass() != null) {
            space();
            this.p.print(CHARS_EXTENDS);
            space();
            accept(jsClass.getBaseClass());
        }
        space();
        if (jsClass.getConstructor() == null && jsClass.getMembers().isEmpty()) {
            this.p.print("{}");
            newline();
        } else {
            blockOpen();
            if (jsClass.getConstructor() != null) {
                this.p.print(CHARS_CONSTRUCTOR);
                jsClass.getConstructor().setName(null);
                printFunction(jsClass.getConstructor());
                newline();
            }
            Iterator<JsFunction> it = jsClass.getMembers().iterator();
            while (it.hasNext()) {
                printFunction(it.next());
                newline();
            }
            blockClose();
        }
        this.needSemi = false;
        printCommentsAfterNode(jsClass);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitIf(JsIf jsIf) {
        if (jsIf == null) {
            $$$reportNull$$$0(24);
        }
        printCommentsBeforeNode(jsIf);
        pushSourceInfo(jsIf.getSource());
        _if();
        spaceOpt();
        leftParen();
        accept(jsIf.getIfExpression());
        rightParen();
        popSourceInfo();
        printCommentsAfterNode(jsIf);
        JsStatement thenStatement = jsIf.getThenStatement();
        JsStatement elseStatement = jsIf.getElseStatement();
        if (elseStatement != null && isIfWithoutElse(thenStatement)) {
            thenStatement = new JsBlock(thenStatement);
        }
        nestedPush(thenStatement);
        if ((thenStatement instanceof JsBlock) && elseStatement != null) {
            this.lineBreakAfterBlock = false;
        }
        this.sourceLocationConsumer.pushSourceInfo(null);
        accept(materialize(thenStatement));
        this.sourceLocationConsumer.popSourceInfo();
        nestedPop(thenStatement);
        if (elseStatement != null) {
            if (this.needSemi) {
                semi();
                newlineOpt();
            } else {
                spaceOpt();
                this.needSemi = true;
            }
            this.p.print(CHARS_ELSE);
            boolean z = elseStatement instanceof JsIf;
            if (!z) {
                nestedPush(elseStatement);
            } else {
                space();
            }
            this.sourceLocationConsumer.pushSourceInfo(null);
            accept(materialize(elseStatement));
            this.sourceLocationConsumer.popSourceInfo();
            if (z) {
                return;
            }
            nestedPop(elseStatement);
        }
    }

    private static boolean isIfWithoutElse(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(25);
        }
        while (jsStatement instanceof JsIf) {
            JsIf jsIf = (JsIf) jsStatement;
            if (jsIf.getElseStatement() == null) {
                return true;
            }
            jsStatement = jsIf.getElseStatement();
        }
        return false;
    }

    private static JsStatement materialize(JsStatement jsStatement) {
        return jsStatement instanceof JsCompositeBlock ? new JsBlock(jsStatement) : jsStatement;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInvocation(JsInvocation jsInvocation) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(26);
        }
        pushSourceInfo(jsInvocation.getSource());
        printCommentsBeforeNode(jsInvocation);
        printPair(jsInvocation, jsInvocation.getQualifier());
        leftParen();
        printExpressions(jsInvocation.getArguments());
        rightParen();
        printCommentsAfterNode(jsInvocation);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitLabel(JsLabel jsLabel) {
        if (jsLabel == null) {
            $$$reportNull$$$0(27);
        }
        nameOf(jsLabel);
        _colon();
        spaceOpt();
        this.sourceLocationConsumer.pushSourceInfo(null);
        accept(jsLabel.getStatement());
        this.sourceLocationConsumer.popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef jsNameRef) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(28);
        }
        visitNameRef(jsNameRef, true);
    }

    public void visitNameRef(JsNameRef jsNameRef, boolean z) {
        boolean zParenCalc;
        if (jsNameRef == null) {
            $$$reportNull$$$0(29);
        }
        printCommentsBeforeNode(jsNameRef);
        this.p.maybeIndent();
        JsExpression qualifier = jsNameRef.getQualifier();
        if (qualifier != null && z) {
            if (qualifier instanceof JsLiteral.JsValueLiteral) {
                zParenCalc = qualifier instanceof JsNumberLiteral;
            } else {
                zParenCalc = parenCalc(jsNameRef, qualifier, false);
            }
            if (zParenCalc) {
                leftParen();
            }
            accept(qualifier);
            if (zParenCalc) {
                rightParen();
            }
            this.p.print('.');
        }
        pushSourceInfo(jsNameRef.getSource());
        this.p.print(jsNameRef.getIdent());
        popSourceInfo();
        printCommentsAfterNode(jsNameRef);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNew(JsNew jsNew) {
        if (jsNew == null) {
            $$$reportNull$$$0(30);
        }
        pushSourceInfo(jsNew.getSource());
        printCommentsBeforeNode(jsNew);
        this.p.print(CHARS_NEW);
        space();
        JsExpression constructorExpression = jsNew.getConstructorExpression();
        boolean zExec = JsConstructExpressionVisitor.exec(constructorExpression);
        if (zExec) {
            leftParen();
        }
        accept(constructorExpression);
        if (zExec) {
            rightParen();
        }
        leftParen();
        printExpressions(jsNew.getArguments());
        rightParen();
        printCommentsAfterNode(jsNew);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNull(JsNullLiteral jsNullLiteral) {
        if (jsNullLiteral == null) {
            $$$reportNull$$$0(31);
        }
        pushSourceInfo(jsNullLiteral.getSource());
        printCommentsBeforeNode(jsNullLiteral);
        this.p.print(CHARS_NULL);
        printCommentsAfterNode(jsNullLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInt(JsIntLiteral jsIntLiteral) {
        if (jsIntLiteral == null) {
            $$$reportNull$$$0(32);
        }
        pushSourceInfo(jsIntLiteral.getSource());
        printCommentsBeforeNode(jsIntLiteral);
        this.p.print(jsIntLiteral.value);
        printCommentsAfterNode(jsIntLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDouble(JsDoubleLiteral jsDoubleLiteral) {
        if (jsDoubleLiteral == null) {
            $$$reportNull$$$0(33);
        }
        pushSourceInfo(jsDoubleLiteral.getSource());
        printCommentsBeforeNode(jsDoubleLiteral);
        this.p.print(jsDoubleLiteral.value);
        printCommentsAfterNode(jsDoubleLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitObjectLiteral(JsObjectLiteral jsObjectLiteral) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(34);
        }
        pushSourceInfo(jsObjectLiteral.getSource());
        printCommentsBeforeNode(jsObjectLiteral);
        this.p.print('{');
        if (jsObjectLiteral.isMultiline()) {
            this.p.indentIn();
        }
        boolean z = false;
        for (JsPropertyInitializer jsPropertyInitializer : jsObjectLiteral.getPropertyInitializers()) {
            if (z) {
                this.p.print(',');
            }
            if (jsObjectLiteral.isMultiline()) {
                newlineOpt();
            } else if (z) {
                spaceOpt();
            }
            pushSourceInfo(jsPropertyInitializer.getSource());
            JsExpression labelExpr = jsPropertyInitializer.getLabelExpr();
            if (labelExpr instanceof JsStringLiteral) {
                JsStringLiteral jsStringLiteral = (JsStringLiteral) labelExpr;
                String value = jsStringLiteral.getValue();
                if (IdentifierPolicyKt.isValidES5Identifier(value)) {
                    labelExpr = (JsExpression) new JsNameRef(value).withMetadataFrom(jsStringLiteral);
                }
            }
            if (labelExpr instanceof JsNameRef) {
                visitNameRef((JsNameRef) labelExpr, false);
            } else {
                accept(labelExpr);
            }
            _colon();
            space();
            JsExpression valueExpr = jsPropertyInitializer.getValueExpr();
            boolean zParenPushIfCommaExpression = parenPushIfCommaExpression(valueExpr);
            accept(valueExpr);
            if (zParenPushIfCommaExpression) {
                rightParen();
            }
            popSourceInfo();
            z = true;
        }
        if (jsObjectLiteral.isMultiline()) {
            this.p.indentOut();
            newlineOpt();
        }
        this.p.print('}');
        printCommentsAfterNode(jsObjectLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitParameter(JsParameter jsParameter) {
        if (jsParameter == null) {
            $$$reportNull$$$0(35);
        }
        pushSourceInfo(jsParameter.getSource());
        nameOf(jsParameter);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPostfixOperation(JsPostfixOperation jsPostfixOperation) {
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(36);
        }
        pushSourceInfo(jsPostfixOperation.getSource());
        printCommentsBeforeNode(jsPostfixOperation);
        JsUnaryOperator operator = jsPostfixOperation.getOperator();
        printPair(jsPostfixOperation, jsPostfixOperation.getArg());
        this.p.print(operator.getSymbol());
        printCommentsAfterNode(jsPostfixOperation);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPrefixOperation(JsPrefixOperation jsPrefixOperation) {
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(37);
        }
        pushSourceInfo(jsPrefixOperation.getSource());
        printCommentsBeforeNode(jsPrefixOperation);
        JsUnaryOperator operator = jsPrefixOperation.getOperator();
        this.p.print(operator.getSymbol());
        JsExpression arg = jsPrefixOperation.getArg();
        if (spaceCalc(operator, arg)) {
            space();
        }
        printPair(jsPrefixOperation, arg);
        printCommentsAfterNode(jsPrefixOperation);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitProgram(JsProgram jsProgram) {
        if (jsProgram == null) {
            $$$reportNull$$$0(38);
        }
        jsProgram.acceptChildren(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitRegExp(JsRegExp jsRegExp) {
        if (jsRegExp == null) {
            $$$reportNull$$$0(39);
        }
        pushSourceInfo(jsRegExp.getSource());
        printCommentsBeforeNode(jsRegExp);
        slash();
        this.p.print(jsRegExp.getPattern());
        slash();
        String flags = jsRegExp.getFlags();
        if (flags != null) {
            this.p.print(flags);
        }
        printCommentsAfterNode(jsRegExp);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitReturn(JsReturn jsReturn) {
        if (jsReturn == null) {
            $$$reportNull$$$0(40);
        }
        pushSourceInfo(jsReturn.getSource());
        printCommentsBeforeNode(jsReturn);
        this.p.print(CHARS_RETURN);
        JsExpression expression = jsReturn.getExpression();
        if (expression != null) {
            space();
            accept(expression);
        }
        printCommentsAfterNode(jsReturn);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitString(JsStringLiteral jsStringLiteral) {
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(41);
        }
        pushSourceInfo(jsStringLiteral.getSource());
        printCommentsBeforeNode(jsStringLiteral);
        this.p.print(javaScriptString(jsStringLiteral.getValue()));
        printCommentsAfterNode(jsStringLiteral);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(JsSwitch jsSwitch) {
        if (jsSwitch == null) {
            $$$reportNull$$$0(42);
        }
        pushSourceInfo(jsSwitch.getSource());
        printCommentsBeforeNode(jsSwitch);
        this.p.print(CHARS_SWITCH);
        spaceOpt();
        leftParen();
        accept(jsSwitch.getExpression());
        rightParen();
        printCommentsAfterNode(jsSwitch);
        popSourceInfo();
        this.sourceLocationConsumer.pushSourceInfo(null);
        spaceOpt();
        blockOpen();
        acceptList(jsSwitch.getCases());
        blockClose();
        this.sourceLocationConsumer.popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitThis(JsThisRef jsThisRef) {
        if (jsThisRef == null) {
            $$$reportNull$$$0(43);
        }
        pushSourceInfo(jsThisRef.getSource());
        printCommentsBeforeNode(jsThisRef);
        this.p.print(CHARS_THIS);
        printCommentsAfterNode(jsThisRef);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitSuper(JsSuperRef jsSuperRef) {
        if (jsSuperRef == null) {
            $$$reportNull$$$0(44);
        }
        pushSourceInfo(jsSuperRef.getSource());
        printCommentsBeforeNode(jsSuperRef);
        this.p.print(CHARS_SUPER);
        printCommentsAfterNode(jsSuperRef);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitThrow(JsThrow jsThrow) {
        if (jsThrow == null) {
            $$$reportNull$$$0(45);
        }
        pushSourceInfo(jsThrow.getSource());
        printCommentsBeforeNode(jsThrow);
        this.p.print(CHARS_THROW);
        space();
        accept(jsThrow.getExpression());
        printCommentsAfterNode(jsThrow);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitTry(JsTry jsTry) {
        if (jsTry == null) {
            $$$reportNull$$$0(46);
        }
        printCommentsBeforeNode(jsTry);
        pushSourceInfo(jsTry.getSource());
        this.p.print(CHARS_TRY);
        spaceOpt();
        this.lineBreakAfterBlock = false;
        popSourceInfo();
        accept(jsTry.getTryBlock());
        acceptList(jsTry.getCatches());
        JsBlock finallyBlock = jsTry.getFinallyBlock();
        if (finallyBlock != null) {
            this.p.print(CHARS_FINALLY);
            spaceOpt();
            accept(finallyBlock);
        }
        printCommentsAfterNode(jsTry);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(JsVars.JsVar jsVar) {
        if (jsVar == null) {
            $$$reportNull$$$0(47);
        }
        pushSourceInfo(jsVar.getSource());
        printCommentsBeforeNode(jsVar);
        nameOf(jsVar);
        JsExpression initExpression = jsVar.getInitExpression();
        if (initExpression != null) {
            spaceOpt();
            assignment();
            spaceOpt();
            boolean zParenPushIfCommaExpression = parenPushIfCommaExpression(initExpression);
            accept(initExpression);
            if (zParenPushIfCommaExpression) {
                rightParen();
            }
        }
        printCommentsAfterNode(jsVar);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitVars(JsVars jsVars) {
        if (jsVars == null) {
            $$$reportNull$$$0(48);
        }
        pushSourceInfo(jsVars.getSource());
        printCommentsBeforeNode(jsVars);
        var();
        space();
        boolean z = false;
        for (JsVars.JsVar jsVar : jsVars) {
            if (z) {
                if (jsVars.isMultiline()) {
                    newlineOpt();
                }
                this.p.print(',');
                spaceOpt();
            } else {
                z = true;
            }
            accept(jsVar);
        }
        printCommentsAfterNode(jsVars);
        popSourceInfo();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitSingleLineComment(JsSingleLineComment jsSingleLineComment) {
        if (jsSingleLineComment == null) {
            $$$reportNull$$$0(49);
        }
        if (this.needSemi && this.insideComments) {
            semi();
            space();
        }
        this.p.print("//");
        this.p.print(jsSingleLineComment.getText());
        newline();
        this.needSemi = false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitMultiLineComment(JsMultiLineComment jsMultiLineComment) {
        if (jsMultiLineComment == null) {
            $$$reportNull$$$0(50);
        }
        List<String> listLines = StringsKt.lines(jsMultiLineComment.getText());
        this.p.print(RefSpec.WILDCARD_SUFFIX);
        this.p.print(listLines.get(0).trim());
        for (int i = 1; i < listLines.size(); i++) {
            newline();
            this.p.print(listLines.get(i).trim());
        }
        this.p.print("*/");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDocComment(JsDocComment jsDocComment) {
        if (jsDocComment == null) {
            $$$reportNull$$$0(51);
        }
        boolean z = false;
        boolean z2 = jsDocComment.getTags().size() == 1;
        if (!z2) {
            newlineOpt();
        }
        this.p.print("/**");
        if (z2) {
            space();
        } else {
            newline();
        }
        for (Map.Entry<String, Object> entry : jsDocComment.getTags().entrySet()) {
            if (z) {
                newline();
                this.p.print(StringUtil.SPACE);
                this.p.print('*');
            } else {
                z = true;
            }
            this.p.print('@');
            this.p.print(entry.getKey());
            Object value = entry.getValue();
            if (value != null) {
                space();
                if (value instanceof CharSequence) {
                    this.p.print((CharSequence) value);
                } else {
                    visitNameRef((JsNameRef) value);
                }
            }
            if (!z2) {
                newline();
            }
        }
        if (z2) {
            space();
        } else {
            newlineOpt();
        }
        this.p.print('*');
        this.p.print('/');
        if (z2) {
            spaceOpt();
        }
    }

    private void newlineOpt() {
        if (this.p.isCompact()) {
            return;
        }
        newline();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitExport(JsExport jsExport) {
        if (jsExport == null) {
            $$$reportNull$$$0(52);
        }
        this.p.print("export");
        space();
        JsExport.Subject subject = jsExport.getSubject();
        if (subject instanceof JsExport.Subject.All) {
            this.p.print("*");
        } else if (subject instanceof JsExport.Subject.Elements) {
            blockOpen();
            for (JsExport.Element element : ((JsExport.Subject.Elements) subject).getElements()) {
                visitNameRef(element.getName());
                JsName alias = element.getAlias();
                if (alias != null) {
                    this.p.print(" as ");
                    nameDef(alias);
                }
                this.p.print(',');
                this.p.newline();
            }
            this.p.indentOut();
            this.p.print('}');
        }
        if (jsExport.getFromModule() != null) {
            this.p.print(" from ");
            this.p.print(javaScriptString(jsExport.getFromModule()));
        }
        this.needSemi = true;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitImport(JsImport jsImport) {
        if (jsImport == null) {
            $$$reportNull$$$0(53);
        }
        JsImport.Target target = jsImport.getTarget();
        this.p.print("import ");
        if (target instanceof JsImport.Target.Default) {
            visitNameRef(((JsImport.Target.Default) target).getName());
        } else if (target instanceof JsImport.Target.All) {
            this.p.print("* as ");
            visitNameRef(((JsImport.Target.All) target).getAlias());
        } else if (target instanceof JsImport.Target.Elements) {
            List<JsImport.Element> elements = ((JsImport.Target.Elements) target).getElements();
            this.p.print("{");
            boolean z = elements.size() > 1;
            this.p.indentIn();
            if (z) {
                newlineOpt();
            } else {
                space();
            }
            for (JsImport.Element element : elements) {
                nameDef(element.getName());
                JsNameRef alias = element.getAlias();
                if (alias != null) {
                    this.p.print(" as ");
                    visitNameRef(alias);
                }
                if (z) {
                    this.p.print(',');
                    newlineOpt();
                } else {
                    space();
                }
            }
            this.p.indentOut();
            this.p.print("}");
        }
        this.p.print(" from ");
        this.p.print(javaScriptString(jsImport.getModule()));
    }

    private void newline() {
        this.p.newline();
        this.sourceLocationConsumer.newLine();
    }

    private void pushSourceInfo(Object obj) {
        this.p.maybeIndent();
        this.sourceInfoStack.add(obj);
        if (obj != null) {
            this.sourceLocationConsumer.pushSourceInfo(obj);
        }
    }

    private void printCommentsBeforeNode(JsNode jsNode) {
        printComments(jsNode.getCommentsBeforeNode(), false);
    }

    private void printCommentsAfterNode(JsNode jsNode) {
        printComments(jsNode.getCommentsAfterNode(), true);
    }

    private void printComments(List<JsComment> list, boolean z) {
        if (list == null) {
            return;
        }
        boolean z2 = this.needSemi;
        this.needSemi = z;
        this.insideComments = true;
        Iterator<JsComment> it = list.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
        this.insideComments = false;
        if (z) {
            return;
        }
        this.needSemi = z2;
    }

    private void popSourceInfo() {
        if (this.sourceInfoStack.isEmpty()) {
            return;
        }
        List<Object> list = this.sourceInfoStack;
        if (list.remove(list.size() - 1) != null) {
            this.sourceLocationConsumer.popSourceInfo();
        }
    }

    private void printJsBlock(JsBlock jsBlock, boolean z, Object obj) {
        boolean z2;
        if (!this.lineBreakAfterBlock) {
            this.lineBreakAfterBlock = true;
            z = false;
        }
        printCommentsBeforeNode(jsBlock);
        boolean zIsTransparent = jsBlock.isTransparent();
        if (!zIsTransparent) {
            this.sourceLocationConsumer.pushSourceInfo(jsBlock.getSource());
            blockOpen();
            this.sourceLocationConsumer.popSourceInfo();
        }
        this.sourceLocationConsumer.pushSourceInfo(null);
        Iterator<JsStatement> it = jsBlock.getStatements().iterator();
        while (it.hasNext()) {
            boolean z3 = jsBlock.isTransparent() || this.globalBlocks.contains(jsBlock);
            JsStatement next = it.next();
            if (!(next instanceof JsEmpty)) {
                this.needSemi = true;
                if (z3 && (next instanceof JsBlock)) {
                    this.globalBlocks.add((JsBlock) next);
                    z2 = true;
                } else {
                    z2 = false;
                }
                accept(next);
                if (z2) {
                    this.globalBlocks.remove(next);
                }
                if (this.needSemi) {
                    boolean z4 = (next instanceof JsExpressionStatement) && (((JsExpressionStatement) next).getExpression() instanceof JsFunction);
                    boolean z5 = (it.hasNext() || zIsTransparent || JsRequiresSemiVisitor.exec(next)) ? false : true;
                    if (!z4) {
                        if (z5) {
                            this.p.printOpt(';');
                        } else {
                            semi();
                        }
                        newlineOpt();
                    } else if (z5) {
                        newlineOpt();
                    } else {
                        newline();
                    }
                }
            }
        }
        if (!zIsTransparent) {
            this.p.indentOut();
            this.sourceLocationConsumer.popSourceInfo();
            Object closingBraceSource = jsBlock.getClosingBraceSource();
            if (closingBraceSource != null) {
                obj = closingBraceSource;
            }
            if (obj != null) {
                pushSourceInfo(obj);
            }
            this.p.print('}');
            if (obj != null) {
                popSourceInfo();
            }
            if (z) {
                newlineOpt();
            }
        } else {
            this.sourceLocationConsumer.popSourceInfo();
        }
        this.needSemi = false;
        printCommentsAfterNode(jsBlock);
    }

    private void assignment() {
        this.p.print('=');
    }

    private void blockClose() {
        this.p.indentOut();
        this.p.print('}');
        newlineOpt();
    }

    private void blockOpen() {
        this.p.print('{');
        this.p.indentIn();
        newlineOpt();
    }

    private void _colon() {
        this.p.print(':');
    }

    private void _for() {
        this.p.print(CHARS_FOR);
    }

    private void _if() {
        this.p.print(CHARS_IF);
    }

    private void leftParen() {
        this.p.print('(');
    }

    private void leftSquare() {
        this.p.print('[');
    }

    private void nameDef(JsName jsName) {
        this.p.print(jsName.getIdent());
    }

    private void nameOf(HasName hasName) {
        nameDef(hasName.getName());
    }

    private boolean nestedPop(JsStatement jsStatement) {
        boolean z = jsStatement instanceof JsBlock;
        boolean z2 = !z;
        if (!z) {
            this.p.indentOut();
        }
        return z2;
    }

    private boolean nestedPush(JsStatement jsStatement) {
        boolean z = jsStatement instanceof JsBlock;
        boolean z2 = !z;
        if (!z) {
            newlineOpt();
            this.p.indentIn();
        } else {
            spaceOpt();
        }
        return z2;
    }

    private static boolean parenCalc(JsExpression jsExpression, JsExpression jsExpression2, boolean z) {
        int iExec = JsPrecedenceVisitor.exec(jsExpression);
        int iExec2 = JsPrecedenceVisitor.exec(jsExpression2);
        return iExec > iExec2 || (iExec == iExec2 && z);
    }

    private boolean _parenPopOrSpace(JsExpression jsExpression, JsExpression jsExpression2, boolean z) {
        boolean zParenCalc = parenCalc(jsExpression, jsExpression2, z);
        if (zParenCalc) {
            rightParen();
        } else {
            space();
        }
        return zParenCalc;
    }

    private boolean parenPush(JsExpression jsExpression, JsExpression jsExpression2, boolean z) {
        boolean zParenCalc = parenCalc(jsExpression, jsExpression2, z);
        if (zParenCalc) {
            leftParen();
        }
        return zParenCalc;
    }

    private boolean parenPushIfCommaExpression(JsExpression jsExpression) {
        boolean z = (jsExpression instanceof JsBinaryOperation) && ((JsBinaryOperation) jsExpression).getOperator() == JsBinaryOperator.COMMA;
        if (z) {
            leftParen();
        }
        return z;
    }

    private boolean _parenPushOrSpace(JsExpression jsExpression, JsExpression jsExpression2, boolean z) {
        boolean zParenCalc = parenCalc(jsExpression, jsExpression2, z);
        if (zParenCalc) {
            leftParen();
        } else {
            space();
        }
        return zParenCalc;
    }

    private void rightParen() {
        this.p.print(')');
    }

    private void rightSquare() {
        this.p.print(']');
    }

    private void semi() {
        this.p.print(';');
    }

    private boolean sepCommaOptSpace(boolean z) {
        if (!z) {
            return true;
        }
        this.p.print(',');
        spaceOpt();
        return true;
    }

    private void slash() {
        this.p.print('/');
    }

    private void space() {
        this.p.print(StringUtil.SPACE);
    }

    private static boolean spaceCalc(JsOperator jsOperator, JsExpression jsExpression) {
        if (jsOperator.isKeyword()) {
            return true;
        }
        if (jsExpression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
            return jsBinaryOperation.getOperator().getPrecedence() > jsOperator.getPrecedence() && spaceCalc(jsOperator, jsBinaryOperation.getArg1());
        }
        if (jsExpression instanceof JsPrefixOperation) {
            JsUnaryOperator operator = ((JsPrefixOperation) jsExpression).getOperator();
            if ((jsOperator == JsBinaryOperator.SUB || jsOperator == JsUnaryOperator.NEG) && (operator == JsUnaryOperator.DEC || operator == JsUnaryOperator.NEG)) {
                return true;
            }
            return jsOperator == JsBinaryOperator.ADD && operator == JsUnaryOperator.INC;
        }
        if (!(jsExpression instanceof JsNumberLiteral) || (jsOperator != JsBinaryOperator.SUB && jsOperator != JsUnaryOperator.NEG)) {
            return false;
        }
        if (jsExpression instanceof JsIntLiteral) {
            return ((JsIntLiteral) jsExpression).value < 0;
        }
        return ((JsDoubleLiteral) jsExpression).value < 0.0d;
    }

    private void spaceOpt() {
        this.p.printOpt(StringUtil.SPACE);
    }

    private void var() {
        this.p.print(CHARS_VAR);
    }

    private void _while() {
        this.p.print(CHARS_WHILE);
    }
}
