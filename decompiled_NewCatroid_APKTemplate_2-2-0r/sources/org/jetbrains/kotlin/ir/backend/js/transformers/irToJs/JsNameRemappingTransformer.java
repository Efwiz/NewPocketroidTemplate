package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;

/* JADX INFO: compiled from: FunctionWithJsFuncAnnotationInliner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u0005*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsNameRemappingTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "replacements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Ljava/util/Map;)V", "replacement", "getReplacement", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "visit", "", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsNameRemappingTransformer extends JsVisitorWithContextImpl {
    private final Map<JsName, JsExpression> replacements;

    /* JADX WARN: Multi-variable type inference failed */
    public JsNameRemappingTransformer(Map<JsName, ? extends JsExpression> replacements) {
        Intrinsics.checkNotNullParameter(replacements, "replacements");
        this.replacements = replacements;
    }

    private final JsExpression getReplacement(JsName jsName) {
        return this.replacements.get(jsName);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsNameRef nameRef, JsContext<JsNode> ctx) {
        JsName name2;
        JsExpression replacement;
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        super.visit(nameRef, ctx);
        if (nameRef.getQualifier() != null || (name2 = nameRef.getName()) == null || (replacement = getReplacement(name2)) == null) {
            return true;
        }
        ctx.replaceMe(replacement);
        return false;
    }
}
