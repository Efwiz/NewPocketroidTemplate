package org.jetbrains.kotlin.js.translate.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.context.UsageTracker;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;

/* JADX INFO: compiled from: LiteralFunctionTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0002\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0007H\u0002\u001a \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001a\u001e\u0010\u0015\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0007H\u0002\u001a&\u0010\u0017\u001a\u00020\u0001*\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002\u001a\u000e\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u001dH\u0002\u001a*\u0010\u001e\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\"¨\u0006#"}, d2 = {"declareAliasInsideFunction", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getTemporaryNamesInScope", "", "suggested", "isLocalInlineDeclaration", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "moveCapturedLocalInside", "Lorg/jetbrains/kotlin/js/translate/expression/CapturedArgsParams;", "capturingFunction", "capturedName", "localFunAlias", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "addDeclaration", "value", "addFunctionDeclaration", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", FirebaseAnalytics.Param.SOURCE, "", "getStaticRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/HasName;", "withCapturedParameters", "context", "functionName", "invokingContext", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LiteralFunctionTranslatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void addFunctionDeclaration(TranslationContext translationContext, JsName jsName, JsFunction jsFunction, Object obj) {
        JsFunction jsFunctionWrapFunction;
        if (translationContext.isPublicInlineFunction()) {
            jsFunctionWrapFunction = InlineMetadata.INSTANCE.wrapFunction(translationContext, new FunctionWithWrapper(jsFunction, null), obj);
        } else {
            jsFunctionWrapFunction = jsFunction;
        }
        UtilsKt.addFunctionButNotExport(translationContext, jsName, jsFunctionWrapFunction);
    }

    public static final JsExpression withCapturedParameters(JsFunction jsFunction, TranslationContext context, JsName functionName, TranslationContext invokingContext, KtDeclaration source) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        Intrinsics.checkNotNullParameter(invokingContext, "invokingContext");
        Intrinsics.checkNotNullParameter(source, "source");
        addFunctionDeclaration(invokingContext, functionName, jsFunction, source);
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(functionName, (JsExpression) null);
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        JsInvocation jsInvocation = new JsInvocation(jsNameRefPureFqn, new JsExpression[0]);
        MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
        List<JsExpression> arguments = jsInvocation.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        UsageTracker usageTracker = context.usageTracker();
        Intrinsics.checkNotNull(usageTracker);
        for (Map.Entry<DeclarationDescriptor, JsName> entry : usageTracker.getCapturedDescriptorToJsName().entrySet()) {
            DeclarationDescriptor key = entry.getKey();
            JsName value = entry.getValue();
            if (!Intrinsics.areEqual(key, usageTracker.getContainingDescriptor()) || DescriptorUtilsKt.isCoroutineLambda(key)) {
                JsExpression argumentForClosureConstructor = invokingContext.getArgumentForClosureConstructor(key);
                Intrinsics.checkNotNullExpressionValue(argumentForClosureConstructor, "getArgumentForClosureConstructor(...)");
                List<JsExpression> listListOf = CollectionsKt.listOf(argumentForClosureConstructor);
                List<JsParameter> listListOf2 = CollectionsKt.listOf(new JsParameter(value));
                if (key instanceof TypeParameterDescriptor) {
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) key;
                    if (typeParameterDescriptor.isReified()) {
                        listListOf = CollectionsKt.plus((Collection) CollectionsKt.listOf(invokingContext.getCapturedTypeName(typeParameterDescriptor).makeRef()), (Iterable) listListOf);
                        listListOf2 = CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsParameter(context.getCapturedTypeName(typeParameterDescriptor))), (Iterable) listListOf2);
                    }
                }
                if ((key instanceof CallableDescriptor) && isLocalInlineDeclaration((CallableDescriptor) key)) {
                    JsNameRef jsNameRef = argumentForClosureConstructor instanceof JsNameRef ? (JsNameRef) argumentForClosureConstructor : null;
                    JsNode staticRef = jsNameRef != null ? getStaticRef(jsNameRef) : null;
                    JsExpression jsExpression = staticRef instanceof JsExpression ? (JsExpression) staticRef : null;
                    if (jsExpression != null) {
                        CapturedArgsParams capturedArgsParamsMoveCapturedLocalInside = moveCapturedLocalInside(jsFunction, value, jsExpression);
                        listListOf = capturedArgsParamsMoveCapturedLocalInside.component1();
                        listListOf2 = capturedArgsParamsMoveCapturedLocalInside.component2();
                    }
                }
                parameters.addAll(listListOf2);
                arguments.addAll(listListOf);
            }
        }
        return jsInvocation;
    }

    private static final CapturedArgsParams moveCapturedLocalInside(JsFunction jsFunction, JsName jsName, JsExpression jsExpression) {
        if (jsExpression instanceof JsNameRef) {
            declareAliasInsideFunction(jsFunction, jsName, jsExpression);
            return new CapturedArgsParams(null, null, 3, null);
        }
        if (jsExpression instanceof JsInvocation) {
            return moveCapturedLocalInside(jsFunction, jsName, (JsInvocation) jsExpression);
        }
        throw new AssertionError("Local function reference has wrong alias " + jsExpression);
    }

    private static final CapturedArgsParams moveCapturedLocalInside(JsFunction jsFunction, JsName jsName, JsInvocation jsInvocation) {
        List<JsExpression> arguments = jsInvocation.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        List<JsName> temporaryNamesInScope = getTemporaryNamesInScope(arguments);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(temporaryNamesInScope, 10));
        Iterator<T> it = temporaryNamesInScope.iterator();
        while (it.hasNext()) {
            arrayList.add(((JsName) it.next()).makeRef());
        }
        declareAliasInsideFunction(jsFunction, jsName, new JsInvocation(jsInvocation.getQualifier(), arrayList));
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(temporaryNamesInScope, 10));
        Iterator<T> it2 = temporaryNamesInScope.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new JsParameter((JsName) it2.next()));
        }
        return new CapturedArgsParams(arguments, arrayList2);
    }

    private static final void declareAliasInsideFunction(JsFunction jsFunction, JsName jsName, JsExpression jsExpression) {
        MetadataProperties.setStaticRef(jsName, jsExpression);
        JsFunction innerFunction = FunctionUtilsKt.getInnerFunction(jsFunction);
        if (innerFunction != null) {
            addDeclaration(innerFunction, jsName, jsExpression);
        }
    }

    private static final List<JsName> getTemporaryNamesInScope(List<? extends JsExpression> list) {
        ArrayList arrayList = new ArrayList();
        for (JsExpression jsExpression : list) {
            if (!(jsExpression instanceof JsNameRef)) {
                throw new AssertionError("Expected suggestion to be JsNameRef");
            }
            String ident = ((JsNameRef) jsExpression).getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(ident);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            arrayList.add(jsNameDeclareTemporaryName);
        }
        return arrayList;
    }

    private static final void addDeclaration(JsFunction jsFunction, JsName jsName, JsExpression jsExpression) {
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsName, jsExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "newVar(...)");
        jsFunction.getBody().getStatements().add(0, jsVarsNewVar);
    }

    private static final JsNode getStaticRef(HasName hasName) {
        JsName name2 = hasName.getName();
        if (name2 != null) {
            return MetadataProperties.getStaticRef(name2);
        }
        return null;
    }

    private static final boolean isLocalInlineDeclaration(CallableDescriptor callableDescriptor) {
        return (callableDescriptor instanceof FunctionDescriptor) && Intrinsics.areEqual(callableDescriptor.getVisibilityImpl(), DescriptorVisibilities.LOCAL) && InlineUtil.isInline(callableDescriptor);
    }
}
