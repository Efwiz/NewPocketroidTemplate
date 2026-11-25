package org.jetbrains.kotlin.js.inline.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.context.NamingContext;
import org.jetbrains.kotlin.js.inline.util.rewriters.LabelNameRefreshingVisitor;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: namingUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u0016\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012¨\u0006\u0013"}, d2 = {"aliasArgumentsIfNeeded", "", "context", "Lorg/jetbrains/kotlin/js/inline/context/NamingContext;", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "parameters", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", FirebaseAnalytics.Param.SOURCE, "", "refreshLabelNames", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "renameLocalNames", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NamingUtilsKt {
    public static final void aliasArgumentsIfNeeded(NamingContext context, List<? extends JsExpression> arguments, List<JsParameter> parameters, Object obj) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        if (arguments.size() > parameters.size()) {
            throw new IllegalArgumentException(("arguments.size (" + arguments.size() + ") should be less or equal to parameters.size (" + parameters.size() + ')').toString());
        }
        ArrayList arrayList = new ArrayList();
        for (Pair pair : CollectionsKt.zip(arguments, parameters)) {
            JsExpression jsExpression = (JsExpression) pair.component1();
            JsParameter jsParameter = (JsParameter) pair.component2();
            if (JsAstUtils.isUndefinedExpression(jsExpression)) {
                arrayList.add(jsParameter);
            } else {
                JsName name2 = jsParameter.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(name2.getIdent());
                Intrinsics.checkNotNull(jsNameDeclareTemporaryName);
                MetadataProperties.setStaticRef(jsNameDeclareTemporaryName, jsExpression);
                context.newVar(jsNameDeclareTemporaryName, jsExpression.deepCopy(), obj);
                JsNameRef jsNameRefMakeRef = jsNameDeclareTemporaryName.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                jsNameRefMakeRef.setSource(jsExpression.getSource());
                context.replaceName(name2, jsNameRefMakeRef);
            }
        }
        CollectionsKt.addAll(arrayList, parameters.subList(arguments.size(), parameters.size()));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            JsName name3 = ((JsParameter) it.next()).getName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName(name3.getIdent());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            jsNameDeclareTemporaryName2.copyMetadataFrom(name3);
            NamingContext.newVar$default(context, jsNameDeclareTemporaryName2, null, obj, 2, null);
            JsNameRef jsNameRefMakeRef2 = jsNameDeclareTemporaryName2.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
            context.replaceName(name3, jsNameRefMakeRef2);
        }
    }

    public static final void renameLocalNames(NamingContext context, JsFunction function) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(function, "function");
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        for (JsName jsName : CollectUtilsKt.collectDefinedNames(body)) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(jsName.getIdent());
            Intrinsics.checkNotNull(jsNameDeclareTemporaryName);
            MetadataProperties.setStaticRef(jsNameDeclareTemporaryName, MetadataProperties.getStaticRef(jsName));
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "apply(...)");
            JsNameRef jsNameRefMakeRef = jsNameDeclareTemporaryName.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            context.replaceName(jsName, jsNameRefMakeRef);
        }
    }

    public static final JsNode refreshLabelNames(JsNode node, JsScope scope) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (!(scope instanceof JsFunctionScope)) {
            throw new AssertionError("JsFunction is expected to have JsFunctionScope");
        }
        JsNode jsNodeAccept = new LabelNameRefreshingVisitor((JsFunctionScope) scope).accept(node);
        Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
        return jsNodeAccept;
    }
}
