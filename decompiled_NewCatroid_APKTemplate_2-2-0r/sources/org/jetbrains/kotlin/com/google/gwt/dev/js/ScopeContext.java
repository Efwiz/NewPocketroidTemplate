package org.jetbrains.kotlin.com.google.gwt.dev.js;

import java.util.NoSuchElementException;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsCatchScope;
import org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ScopeContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000eJ\u0018\u0010\u0014\u001a\n \u0015*\u0004\u0018\u00010\u00030\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u0006\u0010\u0019\u001a\u00020\u0017J\u0010\u0010\u001a\u001a\n \u0015*\u0004\u0018\u00010\u00030\u0003H\u0002J\u000e\u0010\u001b\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00122\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u001d\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\u000eR\u0014\u0010\u0005\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/com/google/gwt/dev/js/ScopeContext;", "", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;)V", "currentScope", "getCurrentScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "rootScope", "scopes", "Ljava/util/Stack;", "enterCatch", "Lorg/jetbrains/kotlin/js/backend/ast/JsCatch;", "ident", "", "enterFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "enterLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "outputName", "enterScope", JvmProtoBufUtil.PLATFORM_TYPE_ID, "exitCatch", "", "exitFunction", "exitLabel", "exitScope", "globalNameFor", "labelFor", "localNameFor", "referenceFor", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopeContext {
    private final JsScope rootScope;
    private final Stack<JsScope> scopes;

    public ScopeContext(JsScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        for (JsScope jsScope : SequencesKt.generateSequence(scope, new Function1<JsScope, JsScope>() { // from class: org.jetbrains.kotlin.com.google.gwt.dev.js.ScopeContext$rootScope$1
            @Override // kotlin.jvm.functions.Function1
            public final JsScope invoke(JsScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParent();
            }
        })) {
            if (jsScope instanceof JsRootScope) {
                this.rootScope = jsScope;
                Stack<JsScope> stack = new Stack<>();
                this.scopes = stack;
                stack.push(scope);
                return;
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final JsFunction enterFunction() {
        JsFunction jsFunction = new JsFunction(getCurrentScope(), "<js function>");
        JsFunctionScope scope = jsFunction.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        enterScope(scope);
        return jsFunction;
    }

    public final void exitFunction() {
        boolean z = getCurrentScope() instanceof JsDeclarationScope;
        exitScope();
    }

    public final JsCatch enterCatch(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        JsCatch jsCatch = new JsCatch(getCurrentScope(), ident);
        JsScope scope = jsCatch.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        enterScope(scope);
        return jsCatch;
    }

    public final void exitCatch() {
        boolean z = getCurrentScope() instanceof JsCatchScope;
        exitScope();
    }

    public final JsName enterLabel(String ident, String outputName) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        Intrinsics.checkNotNullParameter(outputName, "outputName");
        JsScope currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope");
        return ((JsDeclarationScope) currentScope).enterLabel(ident, outputName);
    }

    public final void exitLabel() {
        JsScope currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope");
        ((JsDeclarationScope) currentScope).exitLabel();
    }

    public final JsName labelFor(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        JsScope currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope");
        return ((JsDeclarationScope) currentScope).findLabel(ident);
    }

    public final JsName globalNameFor(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        JsName jsNameFindName = getCurrentScope().findName(ident);
        if (jsNameFindName != null) {
            return jsNameFindName;
        }
        JsName jsNameDeclareName = this.rootScope.declareName(ident);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        return jsNameDeclareName;
    }

    public final JsName localNameFor(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        return ScopeContextKt.findOwnNameOrDeclare(getCurrentScope(), ident);
    }

    public final JsNameRef referenceFor(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        return new JsNameRef(ident);
    }

    private final JsScope enterScope(JsScope scope) {
        return this.scopes.push(scope);
    }

    private final JsScope exitScope() {
        return this.scopes.pop();
    }

    private final JsScope getCurrentScope() {
        JsScope jsScopePeek = this.scopes.peek();
        Intrinsics.checkNotNullExpressionValue(jsScopePeek, "peek(...)");
        return jsScopePeek;
    }
}
