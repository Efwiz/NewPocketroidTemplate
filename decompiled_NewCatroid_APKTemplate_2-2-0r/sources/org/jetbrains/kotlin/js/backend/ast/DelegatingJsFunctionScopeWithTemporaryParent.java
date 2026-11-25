package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: jsScopes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0018\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000e\u001a\u00020\tH\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\tH\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/DelegatingJsFunctionScopeWithTemporaryParent;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;", "delegatingScope", "parent", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;Lorg/jetbrains/kotlin/js/backend/ast/JsScope;)V", "declareFreshName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "suggestedName", "", "declareName", "identifier", "declareNameUnsafe", "enterLabel", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "outputName", "exitLabel", "", "findLabel", "findOwnName", "ident", "hasOwnName", "", "name", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatingJsFunctionScopeWithTemporaryParent extends JsFunctionScope {
    private final JsFunctionScope delegatingScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegatingJsFunctionScopeWithTemporaryParent(JsFunctionScope delegatingScope, JsScope parent) {
        super(parent, "<delegating scope to delegatingScope>");
        Intrinsics.checkNotNullParameter(delegatingScope, "delegatingScope");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.delegatingScope = delegatingScope;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsFunctionScope, org.jetbrains.kotlin.js.backend.ast.JsScope
    public boolean hasOwnName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegatingScope.hasOwnName(name2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    protected JsName findOwnName(String ident) {
        Intrinsics.checkNotNullParameter(ident, "ident");
        return this.delegatingScope.findOwnName(ident);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsFunctionScope
    public JsName declareNameUnsafe(String identifier) {
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        return this.delegatingScope.declareNameUnsafe(identifier);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public JsName declareName(String identifier) {
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        JsName jsNameDeclareName = this.delegatingScope.declareName(identifier);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        return jsNameDeclareName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public JsName declareFreshName(String suggestedName) {
        Intrinsics.checkNotNullParameter(suggestedName, "suggestedName");
        JsName jsNameDeclareFreshName = this.delegatingScope.declareFreshName(suggestedName);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareFreshName, "declareFreshName(...)");
        return jsNameDeclareFreshName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope
    public JsName enterLabel(String label, String outputName) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(outputName, "outputName");
        return this.delegatingScope.enterLabel(label, outputName);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope
    public void exitLabel() {
        this.delegatingScope.exitLabel();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope
    public JsName findLabel(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        return this.delegatingScope.findLabel(label);
    }
}
