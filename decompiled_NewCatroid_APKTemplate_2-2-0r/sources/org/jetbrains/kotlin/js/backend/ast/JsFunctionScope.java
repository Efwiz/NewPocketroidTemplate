package org.jetbrains.kotlin.js.backend.ast;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: jsScopes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsFunctionScope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsDeclarationScope;", "parent", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", MediaTrack.ROLE_DESCRIPTION, "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Ljava/lang/String;)V", "declareNameUnsafe", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "identifier", "hasOwnName", "", "name", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JsFunctionScope extends JsDeclarationScope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsFunctionScope(JsScope parent, String description) {
        super(parent, description, false, 4, null);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(description, "description");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public boolean hasOwnName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return JsDeclarationScope.INSTANCE.getRESERVED_WORDS().contains(name2) || super.hasOwnName(name2);
    }

    public JsName declareNameUnsafe(String identifier) {
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        JsName jsNameDeclareName = super.declareName(identifier);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        return jsNameDeclareName;
    }
}
