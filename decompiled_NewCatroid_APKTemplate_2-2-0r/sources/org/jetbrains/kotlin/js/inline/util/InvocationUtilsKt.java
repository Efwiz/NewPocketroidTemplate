package org.jetbrains.kotlin.js.inline.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.utils.InlineUtils;

/* JADX INFO: compiled from: invocationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0006\u001a\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\n\u001a\u00020\u0006\u001a\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0010"}, d2 = {"transitiveStaticRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getTransitiveStaticRef", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getCallerQualifier", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "getCallerQualifierImpl", "getSimpleIdent", "", "call", "getSimpleName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "hasCallerQualifier", "", "isCallInvocation", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InvocationUtilsKt {
    public static final JsName getSimpleName(JsInvocation call) {
        Intrinsics.checkNotNullParameter(call, "call");
        JsExpression qualifier = call.getQualifier();
        Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
        JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        if (jsNameRef != null) {
            return jsNameRef.getName();
        }
        return null;
    }

    public static final String getSimpleIdent(JsInvocation call) {
        JsName name2;
        Intrinsics.checkNotNullParameter(call, "call");
        JsNode qualifier = call.getQualifier();
        while (qualifier != null) {
            if (qualifier instanceof JsInvocation) {
                JsInvocation jsInvocation = (JsInvocation) qualifier;
                JsNode qualifier2 = jsInvocation.getQualifier();
                if (isCallInvocation(jsInvocation)) {
                    JsNameRef jsNameRef = qualifier2 instanceof JsNameRef ? (JsNameRef) qualifier2 : null;
                    qualifier = jsNameRef != null ? jsNameRef.getQualifier() : null;
                } else {
                    qualifier = qualifier2;
                }
            } else {
                if (!(qualifier instanceof HasName) || (name2 = ((HasName) qualifier).getName()) == null) {
                    return null;
                }
                return name2.getIdent();
            }
        }
        return null;
    }

    public static final boolean isCallInvocation(JsInvocation invocation) {
        Intrinsics.checkNotNullParameter(invocation, "invocation");
        JsExpression qualifier = invocation.getQualifier();
        JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        List<JsExpression> arguments = invocation.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        JsName name2 = InlineUtils.getName(jsNameRef);
        if ((name2 != null ? MetadataProperties.getDescriptor(name2) : null) != null) {
            return false;
        }
        return (!Intrinsics.areEqual(jsNameRef != null ? jsNameRef.getIdent() : null, "call") || arguments.isEmpty() || jsNameRef.getQualifier() == null) ? false : true;
    }

    public static final boolean hasCallerQualifier(JsInvocation invocation) {
        Intrinsics.checkNotNullParameter(invocation, "invocation");
        return getCallerQualifierImpl(invocation) != null;
    }

    public static final JsExpression getCallerQualifier(JsInvocation invocation) {
        Intrinsics.checkNotNullParameter(invocation, "invocation");
        JsExpression callerQualifierImpl = getCallerQualifierImpl(invocation);
        if (callerQualifierImpl != null) {
            return callerQualifierImpl;
        }
        throw new AssertionError("must check hasQualifier() before calling getQualifier");
    }

    private static final JsExpression getCallerQualifierImpl(JsInvocation jsInvocation) {
        JsExpression qualifier = jsInvocation.getQualifier();
        JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        if (jsNameRef != null) {
            return jsNameRef.getQualifier();
        }
        return null;
    }

    public static final JsExpression getTransitiveStaticRef(JsExpression jsExpression) {
        JsExpression jsExpression2;
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        while (true) {
            if (jsExpression instanceof JsNameRef) {
                JsName name2 = ((JsNameRef) jsExpression).getName();
                JsNode staticRef = name2 != null ? MetadataProperties.getStaticRef(name2) : null;
                jsExpression2 = staticRef instanceof JsExpression ? (JsExpression) staticRef : null;
                if (jsExpression2 == null) {
                    break;
                }
                jsExpression = jsExpression2;
            } else {
                if (!(jsExpression instanceof JsInvocation)) {
                    break;
                }
                JsName simpleName = getSimpleName((JsInvocation) jsExpression);
                JsNode staticRef2 = simpleName != null ? MetadataProperties.getStaticRef(simpleName) : null;
                jsExpression2 = staticRef2 instanceof JsExpression ? (JsExpression) staticRef2 : null;
                if (jsExpression2 == null) {
                    break;
                }
                jsExpression = jsExpression2;
            }
        }
        return jsExpression;
    }
}
