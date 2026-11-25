package org.jetbrains.kotlin.js.inline.util.rewriters;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;

/* JADX INFO: compiled from: LabelNameRefreshingVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00122\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00132\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00182\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00132\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/rewriters/LabelNameRefreshingVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "functionScope", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;)V", "getFunctionScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;", "substitutions", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Ljava/util/ArrayDeque;", "endVisit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "getSubstitution", "name", "visit", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LabelNameRefreshingVisitor extends JsVisitorWithContextImpl {
    private final JsFunctionScope functionScope;
    private final Map<JsName, ArrayDeque<JsName>> substitutions;

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return false;
    }

    public LabelNameRefreshingVisitor(JsFunctionScope functionScope) {
        Intrinsics.checkNotNullParameter(functionScope, "functionScope");
        this.functionScope = functionScope;
        this.substitutions = new LinkedHashMap();
    }

    public final JsFunctionScope getFunctionScope() {
        return this.functionScope;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsBreak x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsNameRef label = x.getLabel();
        JsName name2 = label != null ? label.getName() : null;
        if (name2 != null) {
            ctx.replaceMe(new JsBreak(getSubstitution(name2).makeRef()).source(x.getSource()));
        }
        super.endVisit(x, (JsContext) ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsContinue x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsNameRef label = x.getLabel();
        JsName name2 = label != null ? label.getName() : null;
        if (name2 != null) {
            ctx.replaceMe(new JsContinue(getSubstitution(name2).makeRef()).source(x.getSource()));
        }
        super.endVisit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsLabel x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsName name2 = x.getName();
        JsFunctionScope jsFunctionScope = this.functionScope;
        String ident = name2.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        String ident2 = name2.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
        JsName jsNameEnterLabel = jsFunctionScope.enterLabel(ident, ident2);
        Map<JsName, ArrayDeque<JsName>> map = this.substitutions;
        Intrinsics.checkNotNull(name2);
        ArrayDeque<JsName> arrayDeque = map.get(name2);
        if (arrayDeque == null) {
            arrayDeque = new ArrayDeque<>();
            map.put(name2, arrayDeque);
        }
        arrayDeque.push(jsNameEnterLabel);
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsLabel x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        ArrayDeque<JsName> arrayDeque = this.substitutions.get(x.getName());
        Intrinsics.checkNotNull(arrayDeque);
        JsLabel jsLabel = new JsLabel(arrayDeque.pop(), x.getStatement());
        jsLabel.copyMetadataFrom(x);
        ctx.replaceMe(jsLabel);
        this.functionScope.exitLabel();
        super.endVisit(x, ctx);
    }

    private final JsName getSubstitution(JsName name2) {
        ArrayDeque<JsName> arrayDeque = this.substitutions.get(name2);
        JsName jsNamePeek = arrayDeque != null ? arrayDeque.peek() : null;
        return jsNamePeek == null ? name2 : jsNamePeek;
    }
}
