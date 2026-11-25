package org.jetbrains.kotlin.js.inline.util.rewriters;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.CoroutineMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: NameReplacingVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u001c\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u001e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00102\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0016J\u001c\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00122\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u001c\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00132\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u001c\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/rewriters/NameReplacingVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "replaceMap", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Ljava/util/Map;)V", "applyToNamedNode", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/HasName;", "endVisit", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "visit", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameReplacingVisitor extends JsVisitorWithContextImpl {
    private final Map<JsName, JsExpression> replaceMap;

    /* JADX WARN: Multi-variable type inference failed */
    public NameReplacingVisitor(Map<JsName, ? extends JsExpression> replaceMap) {
        Intrinsics.checkNotNullParameter(replaceMap, "replaceMap");
        this.replaceMap = replaceMap;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsNameRef x, JsContext<JsNode> ctx) {
        JsExpression jsExpression;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (x.getQualifier() == null && (jsExpression = this.replaceMap.get(x.getName())) != null) {
            if (jsExpression instanceof JsNameRef) {
                applyToNamedNode(x);
                return;
            }
            JsExpression jsExpressionDeepCopy = jsExpression.deepCopy();
            Intrinsics.checkNotNullExpressionValue(jsExpressionDeepCopy, "deepCopy(...)");
            if (x.getSource() != null) {
                jsExpressionDeepCopy.setSource(x.getSource());
            }
            ctx.replaceMe(accept(jsExpressionDeepCopy));
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsVars.JsVar x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        applyToNamedNode(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsLabel x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        applyToNamedNode(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        applyToNamedNode(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsParameter x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        applyToNamedNode(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(x);
        if (coroutineMetadata != null) {
            JsNode jsNodeAccept = accept(coroutineMetadata.getBaseClassRef().deepCopy());
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
            JsExpression jsExpression = (JsExpression) jsNodeAccept;
            JsNode jsNodeAccept2 = accept(coroutineMetadata.getSuspendObjectRef().deepCopy());
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept2, "accept(...)");
            MetadataProperties.setCoroutineMetadata(x, coroutineMetadata.copy((1855 & 1) != 0 ? coroutineMetadata.doResumeName : null, (1855 & 2) != 0 ? coroutineMetadata.stateName : null, (1855 & 4) != 0 ? coroutineMetadata.exceptionStateName : null, (1855 & 8) != 0 ? coroutineMetadata.finallyPathName : null, (1855 & 16) != 0 ? coroutineMetadata.resultName : null, (1855 & 32) != 0 ? coroutineMetadata.exceptionName : null, (1855 & 64) != 0 ? coroutineMetadata.baseClassRef : jsExpression, (1855 & 128) != 0 ? coroutineMetadata.suspendObjectRef : (JsExpression) jsNodeAccept2, (1855 & 256) != 0 ? coroutineMetadata.hasController : false, (1855 & 512) != 0 ? coroutineMetadata.hasReceiver : false, (1855 & 1024) != 0 ? coroutineMetadata.psiElement : null));
        }
        return super.visit(x, ctx);
    }

    private final void applyToNamedNode(HasName x) {
        while (true) {
            JsNode jsNode = (JsExpression) this.replaceMap.get(x.getName());
            if (!(jsNode instanceof HasName)) {
                return;
            } else {
                x.setName(((HasName) jsNode).getName());
            }
        }
    }
}
