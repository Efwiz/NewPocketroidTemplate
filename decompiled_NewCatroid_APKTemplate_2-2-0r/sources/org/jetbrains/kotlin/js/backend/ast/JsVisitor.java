package org.jetbrains.kotlin.js.backend.ast;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: JsVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000à\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u0004\"\n\b\u0000\u0010\u0005*\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u000f\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000bJ\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u00042\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0014J\u0010\u00106\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\u00042\u0006\u0010K\u001a\u00020LH\u0016J\u0010\u0010M\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020NH\u0016J\u0010\u0010O\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020\u00042\u0006\u00101\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020\u00042\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020WH\u0016J\u0010\u0010X\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020YH\u0016J\u0010\u0010Z\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020[H\u0016J\u0010\u0010\\\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020]H\u0016J\u0010\u0010^\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020_H\u0016J\u0010\u0010`\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020aH\u0016J\u0010\u0010b\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020cH\u0016J\u0010\u0010d\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020eH\u0016J\u0010\u0010f\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020gH\u0016J\u0010\u0010h\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u00020\u00042\u0006\u00101\u001a\u00020kH\u0016J\u0010\u0010l\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020mH\u0016J\u0010\u0010n\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020oH\u0016J\u0010\u0010p\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020qH\u0016J\u0010\u0010r\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020sH\u0016J\u0010\u0010t\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020uH\u0016J\u0010\u0010v\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020yH\u0016¨\u0006z"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "", "()V", "accept", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)V", "acceptList", "collection", "", "acceptLvalue", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "acceptWithInsertRemove", "visit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsSwitch;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "visitArray", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "visitArrayAccess", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "visitBinaryExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "visitBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "visitBoolean", "Lorg/jetbrains/kotlin/js/backend/ast/JsBooleanLiteral;", "visitBreak", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "visitCase", "Lorg/jetbrains/kotlin/js/backend/ast/JsCase;", "visitCatch", "Lorg/jetbrains/kotlin/js/backend/ast/JsCatch;", "visitClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsClass;", "visitConditional", "Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;", "visitContinue", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "visitDebugger", "Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;", "visitDefault", "Lorg/jetbrains/kotlin/js/backend/ast/JsDefault;", "visitDoWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "visitDocComment", "comment", "Lorg/jetbrains/kotlin/js/backend/ast/JsDocComment;", "visitDouble", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoubleLiteral;", "visitElement", "visitEmpty", "Lorg/jetbrains/kotlin/js/backend/ast/JsEmpty;", "visitExport", "export", "Lorg/jetbrains/kotlin/js/backend/ast/JsExport;", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitFor", "Lorg/jetbrains/kotlin/js/backend/ast/JsFor;", "visitForIn", "Lorg/jetbrains/kotlin/js/backend/ast/JsForIn;", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "visitImport", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/js/backend/ast/JsImport;", "visitInt", "Lorg/jetbrains/kotlin/js/backend/ast/JsIntLiteral;", "visitInvocation", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "visitLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "visitLoop", "Lorg/jetbrains/kotlin/js/backend/ast/JsLoop;", "visitMultiLineComment", "Lorg/jetbrains/kotlin/js/backend/ast/JsMultiLineComment;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "visitNew", "Lorg/jetbrains/kotlin/js/backend/ast/JsNew;", "visitNull", "Lorg/jetbrains/kotlin/js/backend/ast/JsNullLiteral;", "visitObjectLiteral", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "visitParameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "visitPostfixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPostfixOperation;", "visitPrefixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "visitProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "visitPropertyInitializer", "Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;", "visitRegExp", "Lorg/jetbrains/kotlin/js/backend/ast/JsRegExp;", "visitReturn", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "visitSingleLineComment", "Lorg/jetbrains/kotlin/js/backend/ast/JsSingleLineComment;", "visitString", "Lorg/jetbrains/kotlin/js/backend/ast/JsStringLiteral;", "visitSuper", "Lorg/jetbrains/kotlin/js/backend/ast/JsSuperRef;", "visitThis", "Lorg/jetbrains/kotlin/js/backend/ast/JsThisRef;", "visitThrow", "Lorg/jetbrains/kotlin/js/backend/ast/JsThrow;", "visitTry", "Lorg/jetbrains/kotlin/js/backend/ast/JsTry;", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "visitWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsVisitor {
    protected void visitElement(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
    }

    public <T extends JsNode> void accept(T node) {
        if (node != null) {
            node.accept(this);
        }
    }

    public final <T extends JsNode> void acceptList(List<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            accept(it.next());
        }
    }

    public final void acceptLvalue(JsExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        accept(expression);
    }

    public final <T extends JsNode> void acceptWithInsertRemove(List<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            accept(it.next());
        }
    }

    public void visitArrayAccess(JsArrayAccess x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitArray(JsArrayLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitBinaryExpression(JsBinaryOperation x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitBlock(JsBlock x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitBoolean(JsBooleanLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitBreak(JsBreak x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitCase(JsCase x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitCatch(JsCatch x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitClass(JsClass x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitConditional(JsConditional x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitContinue(JsContinue x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitDebugger(JsDebugger x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitDefault(JsDefault x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitDoWhile(JsDoWhile x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitLoop(x);
    }

    public void visitEmpty(JsEmpty x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitExpressionStatement(JsExpressionStatement x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitFor(JsFor x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitLoop(x);
    }

    public void visitForIn(JsForIn x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitLoop(x);
    }

    public void visitFunction(JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitIf(JsIf x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitInvocation(JsInvocation invocation) {
        Intrinsics.checkNotNullParameter(invocation, "invocation");
        visitElement(invocation);
    }

    public void visitLabel(JsLabel x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitLoop(JsLoop x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitNameRef(JsNameRef nameRef) {
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        visitElement(nameRef);
    }

    public void visitNew(JsNew x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitNull(JsNullLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitInt(JsIntLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitDouble(JsDoubleLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitObjectLiteral(JsObjectLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitParameter(JsParameter x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitPostfixOperation(JsPostfixOperation x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitPrefixOperation(JsPrefixOperation x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitProgram(JsProgram x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitPropertyInitializer(JsPropertyInitializer x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitRegExp(JsRegExp x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitReturn(JsReturn x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitString(JsStringLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visit(JsSwitch x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitThis(JsThisRef x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitSuper(JsSuperRef x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitThrow(JsThrow x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitTry(JsTry x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visit(JsVars.JsVar x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitVars(JsVars x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitElement(x);
    }

    public void visitWhile(JsWhile x) {
        Intrinsics.checkNotNullParameter(x, "x");
        visitLoop(x);
    }

    public void visitDocComment(JsDocComment comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        visitElement(comment);
    }

    public void visitSingleLineComment(JsSingleLineComment comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        visitElement(comment);
    }

    public void visitMultiLineComment(JsMultiLineComment comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        visitElement(comment);
    }

    public void visitExport(JsExport export) {
        Intrinsics.checkNotNullParameter(export, "export");
        visitElement(export);
    }

    public void visitImport(JsImport jsImport) {
        Intrinsics.checkNotNullParameter(jsImport, "import");
        visitElement(jsImport);
    }
}
