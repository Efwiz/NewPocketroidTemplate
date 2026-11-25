package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.backend.ast.JsName;

/* JADX INFO: compiled from: resolveTemporaryNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/Scope;", "", "()V", "children", "", "getChildren", "()Ljava/util/Set;", "declaredNames", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getDeclaredNames", "usedNames", "getUsedNames", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Scope {
    private final Set<JsName> declaredNames = new LinkedHashSet();
    private final Set<JsName> usedNames = new LinkedHashSet();
    private final Set<Scope> children = new LinkedHashSet();

    public final Set<JsName> getDeclaredNames() {
        return this.declaredNames;
    }

    public final Set<JsName> getUsedNames() {
        return this.usedNames;
    }

    public final Set<Scope> getChildren() {
        return this.children;
    }
}
