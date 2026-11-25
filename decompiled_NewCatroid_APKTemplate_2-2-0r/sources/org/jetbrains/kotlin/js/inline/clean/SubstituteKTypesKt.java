package org.jetbrains.kotlin.js.inline.clean;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
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

/* JADX INFO: compiled from: substituteKTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\f\u0010\u0007\u001a\u00020\u0004*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0004*\u00020\u0001H\u0002¨\u0006\n"}, d2 = {"getTransitiveKType", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "e", "substituteKTypes", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "checkDoesNotCreateRecursiveKType", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "checkNoInvocationsWithRecursiveKType", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SubstituteKTypesKt {
    public static final void substituteKTypes(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.SubstituteKTypesKt$substituteKTypes$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation invocation, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                SubstituteKTypesKt.checkDoesNotCreateRecursiveKType(invocation);
                JsExpression qualifier = invocation.getQualifier();
                JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
                if (jsNameRef == null) {
                    return;
                }
                JsName name2 = jsNameRef.getName();
                if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) != SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE) {
                    return;
                }
                List<JsExpression> arguments = invocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                JsExpression jsExpression = (JsExpression) CollectionsKt.first((List) arguments);
                Intrinsics.checkNotNull(jsExpression);
                JsExpression transitiveKType = SubstituteKTypesKt.getTransitiveKType(jsExpression);
                if (transitiveKType != null) {
                    SubstituteKTypesKt.checkNoInvocationsWithRecursiveKType(transitiveKType);
                    ctx.replaceMe(transitiveKType);
                }
            }
        }.accept(root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression getTransitiveKType(JsExpression jsExpression) {
        if (MetadataProperties.getKType(jsExpression) != null) {
            return MetadataProperties.getKType(jsExpression);
        }
        if (!(jsExpression instanceof JsNameRef)) {
            return null;
        }
        JsName name2 = ((JsNameRef) jsExpression).getName();
        JsNode staticRef = name2 != null ? MetadataProperties.getStaticRef(name2) : null;
        JsExpression jsExpression2 = staticRef instanceof JsExpression ? (JsExpression) staticRef : null;
        if (jsExpression2 == null) {
            return null;
        }
        return getTransitiveKType(jsExpression2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkNoInvocationsWithRecursiveKType(JsExpression jsExpression) {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.SubstituteKTypesKt$checkNoInvocationsWithRecursiveKType$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation invocation, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                SubstituteKTypesKt.checkDoesNotCreateRecursiveKType(invocation);
            }
        }.accept(jsExpression);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkDoesNotCreateRecursiveKType(JsInvocation jsInvocation) {
        if (MetadataProperties.getKTypeWithRecursion(jsInvocation)) {
            throw new NotImplementedError("An operation is not implemented: Non-reified type parameters with recursive bounds are not supported yet");
        }
    }
}
