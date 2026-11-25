package org.jetbrains.kotlin.js.dce;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\r\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\b\u001a\f\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n\u001a&\u0010\u000b\u001a\f\u0012\b\u0012\u00060\rR\u00020\u000e0\f*\f\u0012\b\u0012\u00060\rR\u00020\u000e0\f2\u0006\u0010\u000f\u001a\u00020\u000e\u001a*\u0010\u0010\u001a\u00020\u0011*\u00060\rR\u00020\u000e2\u0010\u0010\u0012\u001a\f\u0012\b\u0012\u00060\rR\u00020\u000e0\u00132\u0006\u0010\u000f\u001a\u00020\u000eH\u0002\u001a\u0012\u0010\u0014\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003\u001a\u0012\u0010\u0016\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003\u001a\u0012\u0010\u0017\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003\u001a\u001a\u0010\u0018\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0007\u001a\u0012\u0010\u001a\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003\u001a\u001a\u0010\u001b\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0007\u001a\u0012\u0010\u001d\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003\u001a\u001a\u0010\u001e\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0007\u001a\u0012\u0010\u001f\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0003¨\u0006 "}, d2 = {"isSpecialFunction", "", "expr", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "specialFunction", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "asString", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "extractLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "extractReachableRoots", "", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "Lorg/jetbrains/kotlin/js/dce/Context;", "context", "extractRootsImpl", "", "target", "", "isAmdDefine", "function", "isDefineInlineFunction", "isDefineModule", "isKotlinFunction", "name", "isObjectDefineProperty", "isObjectFunction", "functionName", "isObjectGetOwnPropertyDescriptor", "isTopLevelFunction", "isWrapFunction", "js.dce"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilKt {
    public static final boolean isObjectDefineProperty(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isObjectFunction(context, function, "defineProperty");
    }

    public static final boolean isObjectGetOwnPropertyDescriptor(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isObjectFunction(context, function, "getOwnPropertyDescriptor");
    }

    public static final boolean isDefineModule(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isKotlinFunction(context, function, "defineModule");
    }

    public static final boolean isDefineInlineFunction(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isKotlinFunction(context, function, Namer.DEFINE_INLINE_FUNCTION) || isSpecialFunction(function, SpecialFunction.DEFINE_INLINE_FUNCTION);
    }

    public static final boolean isWrapFunction(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isKotlinFunction(context, function, "wrapFunction") || isSpecialFunction(function, SpecialFunction.WRAP_FUNCTION);
    }

    public static final boolean isObjectFunction(Context context, JsExpression function, String functionName) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        if (!(function instanceof JsNameRef)) {
            return false;
        }
        JsNameRef jsNameRef = (JsNameRef) function;
        if (!Intrinsics.areEqual(jsNameRef.getIdent(), functionName)) {
            return false;
        }
        JsExpression qualifier = jsNameRef.getQualifier();
        JsNameRef jsNameRef2 = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        if (jsNameRef2 == null) {
            return false;
        }
        JsName name2 = jsNameRef2.getName();
        if ((name2 != null ? context.getNodes().get(name2) : null) != null) {
            return false;
        }
        return Intrinsics.areEqual(jsNameRef2.getIdent(), CommonClassNames.JAVA_LANG_OBJECT_SHORT);
    }

    public static final boolean isKotlinFunction(Context context, JsExpression function, String name2) {
        JsName name3;
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!(function instanceof JsNameRef)) {
            return false;
        }
        JsNameRef jsNameRef = (JsNameRef) function;
        if (!Intrinsics.areEqual(jsNameRef.getIdent(), name2)) {
            return false;
        }
        JsExpression qualifier = jsNameRef.getQualifier();
        JsNameRef jsNameRef2 = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        if (jsNameRef2 == null || (name3 = jsNameRef2.getName()) == null || !context.getNodes().containsKey(name3)) {
            return false;
        }
        String ident = name3.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        String lowerCase = ident.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return Intrinsics.areEqual(lowerCase, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    public static final boolean isSpecialFunction(JsExpression expr, SpecialFunction specialFunction) {
        Intrinsics.checkNotNullParameter(expr, "expr");
        Intrinsics.checkNotNullParameter(specialFunction, "specialFunction");
        if (expr instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) expr;
            if (jsNameRef.getQualifier() == null) {
                JsName name2 = jsNameRef.getName();
                if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) == specialFunction) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isAmdDefine(Context context, JsExpression function) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return isTopLevelFunction(context, function, "define");
    }

    public static final boolean isTopLevelFunction(Context context, JsExpression function, String name2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!(function instanceof JsNameRef)) {
            return false;
        }
        JsNameRef jsNameRef = (JsNameRef) function;
        return jsNameRef.getQualifier() == null && Intrinsics.areEqual(jsNameRef.getIdent(), name2) && !CollectionsKt.contains(context.getNodes().keySet(), jsNameRef.getName());
    }

    public static final JsLocation extractLocation(JsNode jsNode) {
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        if (jsNode instanceof SourceInfoAwareJsNode) {
            Object source = ((SourceInfoAwareJsNode) jsNode).getSource();
            if (source instanceof JsLocation) {
                return (JsLocation) source;
            }
            return null;
        }
        if (!(jsNode instanceof JsExpressionStatement)) {
            return null;
        }
        Object source2 = ((JsExpressionStatement) jsNode).getExpression().getSource();
        if (source2 instanceof JsLocation) {
            return (JsLocation) source2;
        }
        return null;
    }

    public static final String asString(JsLocation jsLocation) {
        Intrinsics.checkNotNullParameter(jsLocation, "<this>");
        String strSubstring = jsLocation.getFile().substring(StringsKt.lastIndexOf$default((CharSequence) jsLocation.getFile(), "/", 0, false, 6, (Object) null) + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring + ':' + (jsLocation.getStartLine() + 1);
    }

    public static final Iterable<Context.Node> extractReachableRoots(Iterable<Context.Node> iterable, Context context) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        context.clearVisited();
        ArrayList arrayList = new ArrayList();
        for (Context.Node node : iterable) {
            if (node.getReachable()) {
                extractRootsImpl(node.getOriginal(), arrayList, context);
            }
        }
        return arrayList;
    }

    private static final void extractRootsImpl(Context.Node node, List<Context.Node> list, Context context) {
        if (context.visit(node.getOriginal())) {
            Context.Node parent = node.getOriginal().getParent();
            if (parent == null) {
                list.add(node.getOriginal());
            } else {
                extractRootsImpl(parent, list, context);
            }
        }
    }
}
