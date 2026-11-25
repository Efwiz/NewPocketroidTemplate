package org.jetbrains.kotlin.js.backend.ast;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: jsScopes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsObjectScope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "parent", MediaTrack.ROLE_DESCRIPTION, "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Ljava/lang/String;)V", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsObjectScope extends JsScope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsObjectScope(JsScope parent, String description) {
        super(parent, description);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(description, "description");
    }
}
