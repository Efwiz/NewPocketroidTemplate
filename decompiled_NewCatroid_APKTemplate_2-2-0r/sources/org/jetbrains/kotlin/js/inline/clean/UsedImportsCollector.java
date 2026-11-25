package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.CoroutineMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: removeUnusedImports.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/UsedImportsCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "usedImports", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Ljava/util/Set;)V", "pseudoImports", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "getPseudoImports", "()Ljava/util/List;", "getUsedImports", "()Ljava/util/Set;", "visit", "", "x", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UsedImportsCollector extends RecursiveJsVisitor {
    private final List<JsVars.JsVar> pseudoImports;
    private final Set<JsName> usedImports;

    public UsedImportsCollector(Set<JsName> usedImports) {
        Intrinsics.checkNotNullParameter(usedImports, "usedImports");
        this.usedImports = usedImports;
        this.pseudoImports = new ArrayList();
    }

    public final Set<JsName> getUsedImports() {
        return this.usedImports;
    }

    public final List<JsVars.JsVar> getPseudoImports() {
        return this.pseudoImports;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(JsVars.JsVar x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsName name2 = x.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (MetadataProperties.getImported(name2)) {
            this.pseudoImports.add(x);
        } else {
            super.visit(x);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef nameRef) {
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        JsName name2 = nameRef.getName();
        if (name2 != null && MetadataProperties.getImported(name2)) {
            this.usedImports.add(name2);
        }
        super.visitNameRef(nameRef);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(x);
        if (coroutineMetadata != null) {
            accept(coroutineMetadata.getSuspendObjectRef());
            accept(coroutineMetadata.getBaseClassRef());
        }
        super.visitFunction(x);
    }
}
