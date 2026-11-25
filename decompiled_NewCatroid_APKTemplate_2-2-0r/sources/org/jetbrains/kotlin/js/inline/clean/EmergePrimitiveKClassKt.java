package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;

/* JADX INFO: compiled from: emergePrimitiveKClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"emergePrimitiveKClass", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EmergePrimitiveKClassKt {
    public static final void emergePrimitiveKClass(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.EmergePrimitiveKClassKt$emergePrimitiveKClass$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation invocation, JsContext<? super JsNode> ctx) {
                JsExpression primitiveKClass;
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsExpression qualifier = invocation.getQualifier();
                JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
                if (jsNameRef == null) {
                    return;
                }
                JsName name2 = jsNameRef.getName();
                if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) != SpecialFunction.GET_KCLASS) {
                    return;
                }
                List<JsExpression> arguments = invocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) arguments);
                JsNameRef jsNameRef2 = objFirstOrNull instanceof JsNameRef ? (JsNameRef) objFirstOrNull : null;
                if (jsNameRef2 == null || (primitiveKClass = MetadataProperties.getPrimitiveKClass(jsNameRef2)) == null) {
                    return;
                }
                ctx.replaceMe(primitiveKClass);
            }
        }.accept(root);
    }
}
