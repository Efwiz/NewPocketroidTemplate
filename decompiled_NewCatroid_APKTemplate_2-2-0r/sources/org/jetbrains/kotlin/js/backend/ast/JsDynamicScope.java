package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: jsScopes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsDynamicScope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "()V", "doCreateName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "name", "", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDynamicScope extends JsScope {
    public static final JsDynamicScope INSTANCE = new JsDynamicScope();

    private JsDynamicScope() {
        super(null, "Scope for dynamic declarations");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    protected JsName doCreateName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new JsName(name2, false);
    }
}
