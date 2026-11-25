package org.jetbrains.kotlin.com.google.gwt.dev.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsScope;

/* JADX INFO: compiled from: ScopeContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"findOwnNameOrDeclare", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "ident", "", "js.parser"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopeContextKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final JsName findOwnNameOrDeclare(JsScope jsScope, String str) {
        if (jsScope instanceof JsFunctionScope) {
            return ((JsFunctionScope) jsScope).declareNameUnsafe(str);
        }
        JsName jsNameDeclareName = jsScope.declareName(str);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        return jsNameDeclareName;
    }
}
