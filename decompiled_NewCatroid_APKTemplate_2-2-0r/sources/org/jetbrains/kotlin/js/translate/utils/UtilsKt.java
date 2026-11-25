package org.jetbrains.kotlin.js.translate.utils;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CommonCoroutineCodegenUtilKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.CoroutineMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.context.UsageTracker;
import org.jetbrains.kotlin.js.translate.expression.ExpressionVisitor;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TypeOfFIFKt;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentUnmapped;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a&\u0010\n\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f\u001a@\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001\u001a\u0018\u0010\u001c\u001a\u00020\u000f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0016\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0016\u0010!\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u001a\"\u0010&\u001a\u00020'*\u00020\u00182\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020+\u001a\u001a\u0010,\u001a\u00020\b*\u00020\u00182\u0006\u0010-\u001a\u00020\u00142\u0006\u0010.\u001a\u00020\u000f\u001a\u001a\u0010,\u001a\u00020\b*\u00020\u00182\u0006\u0010\t\u001a\u00020\b2\u0006\u0010.\u001a\u00020\u000f\u001a\"\u0010/\u001a\u00020\u0006*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u00142\u0006\u00100\u001a\u00020\u000f\u001a\u0016\u00101\u001a\u00020\u000f*\u00020\u00182\n\u00102\u001a\u0006\u0012\u0002\b\u00030#\u001a\"\u00103\u001a\u00020'*\u0002042\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010-\u001a\u00020\u00142\u0006\u00105\u001a\u00020\u001a\u001a\u0014\u00106\u001a\u0004\u0018\u00010 *\u00020\u00182\u0006\u0010.\u001a\u000207\u001a\u0012\u00108\u001a\u00020 *\u00020\u00182\u0006\u0010.\u001a\u000207\u001a\u0014\u00109\u001a\u0004\u0018\u00010:*\u00020\u00182\u0006\u0010.\u001a\u000207\u001a\n\u0010;\u001a\u00020\u001a*\u00020\u0014\u001a\n\u0010<\u001a\u00020\u001a*\u00020\u0014\u001a\n\u0010=\u001a\u00020 *\u00020 \u001aH\u0010>\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H@0\u0005\u0012\u0004\u0012\u0002HA0?0\u0005\"\u0004\b\u0000\u0010@\"\u0004\b\u0001\u0010A*\b\u0012\u0004\u0012\u0002H@0\u00052\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HA0C\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006D"}, d2 = {"finalElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getFinalElement", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;", "createPrototypeStatements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "superName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "name", "definePackageAlias", "", "varName", "tag", "parentRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "generateDelegateCall", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "fromDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "toDescriptor", "thisObject", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "detectDefaultParameters", "", FirebaseAnalytics.Param.SOURCE, "getReferenceToJsClass", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getReferenceToJsClassOrArray", "resolveAccessorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "suspendPropertyDescriptor", "addAccessorsToPrototype", "", "containingClass", "propertyDescriptor", "literal", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "addFunctionButNotExport", "descriptor", "expression", "addFunctionToPrototype", "function", "createCoroutineResult", "resolvedCall", "fillCoroutineMetadata", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "hasController", "getPrecisePrimitiveType", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getPrecisePrimitiveTypeNotNull", "getPrimitiveNumericComparisonInfo", "Lorg/jetbrains/kotlin/resolve/checkers/PrimitiveNumericComparisonInfo;", "hasOrInheritsParametersWithDefaultValue", "hasOwnParametersWithDefaultValue", "refineType", "splitToRanges", "Lkotlin/Pair;", ExifInterface.GPS_DIRECTION_TRUE, ExifInterface.LATITUDE_SOUTH, "classifier", "Lkotlin/Function1;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    private static final JsName generateDelegateCall$getNameForFunctionWithPossibleDefaultParam(FunctionDescriptor functionDescriptor, boolean z, TranslationContext translationContext) {
        JsName nameForDescriptor;
        if (z && hasOrInheritsParametersWithDefaultValue(functionDescriptor)) {
            nameForDescriptor = translationContext.scope().declareName(translationContext.getNameForDescriptor(functionDescriptor).getIdent() + "$default");
        } else {
            nameForDescriptor = translationContext.getNameForDescriptor(functionDescriptor);
        }
        Intrinsics.checkNotNull(nameForDescriptor);
        return nameForDescriptor;
    }

    public static final JsStatement generateDelegateCall(ClassDescriptor classDescriptor, FunctionDescriptor fromDescriptor, FunctionDescriptor toDescriptor, JsExpression thisObject, TranslationContext context, boolean z, PsiElement psiElement) {
        List<ValueParameterDescriptor> valueParameters;
        JsInvocation jsInvocation;
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(fromDescriptor, "fromDescriptor");
        Intrinsics.checkNotNullParameter(toDescriptor, "toDescriptor");
        Intrinsics.checkNotNullParameter(thisObject, "thisObject");
        Intrinsics.checkNotNullParameter(context, "context");
        JsNameRef jsNameRef = new JsNameRef(generateDelegateCall$getNameForFunctionWithPossibleDefaultParam(toDescriptor, z, context), thisObject);
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        if (DescriptorUtils.isExtension(fromDescriptor)) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            smartList.add(new JsParameter(jsNameDeclareTemporaryName));
            smartList2.add(new JsNameRef(jsNameDeclareTemporaryName));
        }
        if (fromDescriptor.isSuspend()) {
            List<ValueParameterDescriptor> valueParameters2 = fromDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            ValueParameterDescriptor continuationParameterDescriptor = context.getContinuationParameterDescriptor();
            Intrinsics.checkNotNull(continuationParameterDescriptor);
            valueParameters = CollectionsKt.plus((Collection<? extends ValueParameterDescriptor>) valueParameters2, continuationParameterDescriptor);
        } else {
            valueParameters = fromDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        }
        Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
        while (it.hasNext()) {
            String strAsString = it.next().getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName(strAsString);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            smartList.add(new JsParameter(jsNameDeclareTemporaryName2));
            smartList2.add(new JsNameRef(jsNameDeclareTemporaryName2));
        }
        FunctionIntrinsic functionIntrinsic = context.intrinsics().getFunctionIntrinsic(toDescriptor, context);
        if (functionIntrinsic instanceof FunctionIntrinsicWithReceiverComputed) {
            jsInvocation = ((FunctionIntrinsicWithReceiverComputed) functionIntrinsic).apply(thisObject, smartList2, context);
        } else {
            jsInvocation = new JsInvocation(jsNameRef, smartList2);
        }
        jsInvocation.setSource(psiElement);
        JsFunction jsFunctionSimpleReturnFunction = TranslationUtils.simpleReturnFunction(context.scope(), jsInvocation);
        Intrinsics.checkNotNullExpressionValue(jsFunctionSimpleReturnFunction, "simpleReturnFunction(...)");
        jsFunctionSimpleReturnFunction.setSource(psiElement);
        JsBlock body = jsFunctionSimpleReturnFunction.getBody();
        PsiElement finalElement = psiElement != null ? getFinalElement(psiElement) : null;
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        jsFunctionSimpleReturnFunction.getParameters().addAll(smartList);
        JsFunction jsFunction = jsFunctionSimpleReturnFunction;
        if (MetadataProperties.isSuspend(jsFunction)) {
            fillCoroutineMetadata(jsFunctionSimpleReturnFunction, context, fromDescriptor, false);
        }
        JsName jsNameGenerateDelegateCall$getNameForFunctionWithPossibleDefaultParam = generateDelegateCall$getNameForFunctionWithPossibleDefaultParam(fromDescriptor, z, context);
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(context.getInnerReference(classDescriptor));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(new JsNameRef(jsNameGenerateDelegateCall$getNameForFunctionWithPossibleDefaultParam, jsExpressionPrototypeOf), jsFunction).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    public static final <T, S> List<Pair<List<T>, S>> splitToRanges(List<? extends T> list, Function1<? super T, ? extends S> classifier) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        if (list.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        int i = 0;
        S sInvoke = classifier.invoke(list.get(0));
        ArrayList arrayList = new ArrayList();
        for (IndexedValue indexedValue : SequencesKt.drop(SequencesKt.withIndex(CollectionsKt.asSequence(list)), 1)) {
            int index = indexedValue.getIndex();
            S sInvoke2 = classifier.invoke((Object) indexedValue.component2());
            if (!Intrinsics.areEqual(sInvoke2, sInvoke)) {
                arrayList.add(new Pair(list.subList(i, index), sInvoke));
                sInvoke = sInvoke2;
                i = index;
            }
        }
        arrayList.add(new Pair(list.subList(i, list.size()), sInvoke));
        return arrayList;
    }

    public static final JsExpression getReferenceToJsClass(KotlinType type, TranslationContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        JsExpression referenceToJsClassOrArray = getReferenceToJsClassOrArray(type, context);
        MetadataProperties.setKType(referenceToJsClassOrArray, TypeOfFIFKt.createKType(context, type));
        return referenceToJsClassOrArray;
    }

    public static final JsExpression getReferenceToJsClassOrArray(KotlinType type, TranslationContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 == null) {
            Collection<KotlinType> collectionMo7438getSupertypes = type.getConstructor().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            Collection<KotlinType> collection = collectionMo7438getSupertypes;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(getReferenceToJsClass(((KotlinType) it.next()).getConstructor().getThis$0(), context));
            }
            return new JsArrayLiteral(arrayList);
        }
        return getReferenceToJsClass(this$0, context);
    }

    public static final JsExpression getReferenceToJsClass(ClassifierDescriptor classifierDescriptor, TranslationContext context) {
        JsNameRef jsNameRefCaptureTypeIfNeedAndGetCapturedName;
        Intrinsics.checkNotNullParameter(context, "context");
        if (classifierDescriptor instanceof ClassDescriptor) {
            jsNameRefCaptureTypeIfNeedAndGetCapturedName = ReferenceTranslator.translateAsTypeReference((ClassDescriptor) classifierDescriptor, context);
        } else if (classifierDescriptor instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) classifierDescriptor;
            typeParameterDescriptor.isReified();
            UsageTracker usageTracker = context.usageTracker();
            if (usageTracker != null) {
                usageTracker.used(classifierDescriptor);
            }
            jsNameRefCaptureTypeIfNeedAndGetCapturedName = context.captureTypeIfNeedAndGetCapturedName(typeParameterDescriptor);
            if (jsNameRefCaptureTypeIfNeedAndGetCapturedName == null) {
                jsNameRefCaptureTypeIfNeedAndGetCapturedName = context.getNameForDescriptor(classifierDescriptor).makeRef();
            }
        } else {
            throw new IllegalStateException("Can't get reference for " + classifierDescriptor);
        }
        Intrinsics.checkNotNull(jsNameRefCaptureTypeIfNeedAndGetCapturedName);
        MetadataProperties.setPrimitiveKClass(jsNameRefCaptureTypeIfNeedAndGetCapturedName, ExpressionVisitor.getPrimitiveClass(context, classifierDescriptor));
        return jsNameRefCaptureTypeIfNeedAndGetCapturedName;
    }

    public static final JsStatement addFunctionToPrototype(TranslationContext translationContext, ClassDescriptor classDescriptor, FunctionDescriptor descriptor, JsExpression function) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(function, "function");
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(translationContext.getInnerReference(classDescriptor));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(new JsNameRef(translationContext.getNameForDescriptor(descriptor), jsExpressionPrototypeOf), function).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    public static final void addAccessorsToPrototype(TranslationContext translationContext, ClassDescriptor containingClass, PropertyDescriptor propertyDescriptor, JsObjectLiteral literal) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(literal, "literal");
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(translationContext.getInnerReference(containingClass));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        JsName nameForDescriptor = translationContext.getNameForDescriptor(propertyDescriptor);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        JsInvocation jsInvocationDefineProperty = JsAstUtils.defineProperty(jsExpressionPrototypeOf, nameForDescriptor.getIdent(), literal);
        Intrinsics.checkNotNullExpressionValue(jsInvocationDefineProperty, "defineProperty(...)");
        translationContext.addDeclarationStatement(jsInvocationDefineProperty.makeStmt());
    }

    public static final void fillCoroutineMetadata(JsFunction jsFunction, TranslationContext context, FunctionDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JsExpression jsExpressionTranslateGet = CallTranslator.INSTANCE.translateGet(context, resolveAccessorCall((PropertyDescriptor) CollectionsKt.first(context.getCurrentModule().getPackage(StandardNames.COROUTINES_INTRINSICS_PACKAGE_FQ_NAME).getMemberScope().getContributedVariables(CommonCoroutineCodegenUtilKt.getCOROUTINE_SUSPENDED_NAME(), NoLookupLocation.FROM_BACKEND)), context), null);
        JsName nameForDescriptor = context.getNameForDescriptor(TranslationUtils.getCoroutineDoResumeFunction(context));
        JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(TranslationUtils.getCoroutineBaseClass(context), context);
        JsName jsNameFillCoroutineMetadata$getCoroutinePropertyName = fillCoroutineMetadata$getCoroutinePropertyName(context, "state");
        JsName jsNameFillCoroutineMetadata$getCoroutinePropertyName2 = fillCoroutineMetadata$getCoroutinePropertyName(context, "exceptionState");
        JsName jsNameFillCoroutineMetadata$getCoroutinePropertyName3 = fillCoroutineMetadata$getCoroutinePropertyName(context, "finallyPath");
        JsName jsNameFillCoroutineMetadata$getCoroutinePropertyName4 = fillCoroutineMetadata$getCoroutinePropertyName(context, "result");
        JsName jsNameFillCoroutineMetadata$getCoroutinePropertyName5 = fillCoroutineMetadata$getCoroutinePropertyName(context, K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION);
        boolean z2 = descriptor.mo6753getDispatchReceiverParameter() != null;
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        Intrinsics.checkNotNull(nameForDescriptor);
        Intrinsics.checkNotNull(jsExpressionTranslateAsTypeReference);
        MetadataProperties.setCoroutineMetadata(jsFunction, new CoroutineMetadata(nameForDescriptor, jsNameFillCoroutineMetadata$getCoroutinePropertyName, jsNameFillCoroutineMetadata$getCoroutinePropertyName2, jsNameFillCoroutineMetadata$getCoroutinePropertyName3, jsNameFillCoroutineMetadata$getCoroutinePropertyName4, jsNameFillCoroutineMetadata$getCoroutinePropertyName5, jsExpressionTranslateAsTypeReference, jsExpressionTranslateGet, z, z2, psi));
    }

    private static final JsName fillCoroutineMetadata$getCoroutinePropertyName(TranslationContext translationContext, String str) {
        JsName nameForDescriptor = translationContext.getNameForDescriptor(TranslationUtils.getCoroutineProperty(translationContext, str));
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        return nameForDescriptor;
    }

    private static final ResolvedCall<PropertyDescriptor> resolveAccessorCall(final PropertyDescriptor propertyDescriptor, final TranslationContext translationContext) {
        return new ResolvedCall<PropertyDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.utils.UtilsKt.resolveAccessorCall.1
            public Void getDispatchReceiver() {
                return null;
            }

            public Void getExtensionReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public KotlinType getSmartCastDispatchReceiverType() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public /* bridge */ /* synthetic */ ReceiverValue getDispatchReceiver() {
                return (ReceiverValue) getDispatchReceiver();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public /* bridge */ /* synthetic */ ReceiverValue getExtensionReceiver() {
                return (ReceiverValue) getExtensionReceiver();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ResolutionStatus getStatus() {
                return ResolutionStatus.SUCCESS;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public Call getCall() {
                final TranslationContext translationContext2 = translationContext;
                return new Call() { // from class: org.jetbrains.kotlin.js.translate.utils.UtilsKt$resolveAccessorCall$1$getCall$1
                    @Override // org.jetbrains.kotlin.psi.Call
                    public ASTNode getCallOperationNode() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public KtExpression getCalleeExpression() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public ReceiverValue getDispatchReceiver() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public Receiver getExplicitReceiver() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public KtTypeArgumentList getTypeArgumentList() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public KtValueArgumentList getValueArgumentList() {
                        return null;
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public List<ValueArgument> getValueArguments() {
                        return CollectionsKt.emptyList();
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public List<LambdaArgument> getFunctionLiteralArguments() {
                        return CollectionsKt.emptyList();
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public List<KtTypeProjection> getTypeArguments() {
                        return CollectionsKt.emptyList();
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public KtElement getCallElement() {
                        Project project = translationContext2.getConfig().getProject();
                        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                        return new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createExpression("COROUTINE_SUSPENDED");
                    }

                    @Override // org.jetbrains.kotlin.psi.Call
                    public Call.CallType getCallType() {
                        return Call.CallType.DEFAULT;
                    }
                };
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            /* JADX INFO: renamed from: getCandidateDescriptor, reason: from getter */
            public PropertyDescriptor get$suspendPropertyDescriptor() {
                return propertyDescriptor;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public PropertyDescriptor getResultingDescriptor() {
                return propertyDescriptor;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public List<ReceiverValue> getContextReceivers() {
                return CollectionsKt.emptyList();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ExplicitReceiverKind getExplicitReceiverKind() {
                return ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
                return new LinkedHashMap();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public List<ResolvedValueArgument> getValueArgumentsByIndex() {
                return new ArrayList();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ArgumentUnmapped getArgumentMapping(ValueArgument valueArgument) {
                Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
                return ArgumentUnmapped.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
                return new LinkedHashMap();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public DataFlowInfoForArguments getDataFlowInfoForArguments() {
                throw new IllegalStateException();
            }
        };
    }

    public static final JsStatement definePackageAlias(String name2, JsName varName, String tag, JsExpression parentRef) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(varName, "varName");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(parentRef, "parentRef");
        JsNameRef jsNameRef = new JsNameRef(name2, parentRef);
        JsBinaryOperation jsBinaryOperationOr = JsAstUtils.or(jsNameRef, JsAstUtils.assignment(jsNameRef.deepCopy(), new JsObjectLiteral(false)));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationOr, "or(...)");
        JsVars jsVarsNewVar = JsAstUtils.newVar(varName, jsBinaryOperationOr);
        Intrinsics.checkNotNull(jsVarsNewVar);
        MetadataProperties.setExportedPackage(jsVarsNewVar, tag);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
        return jsVarsNewVar;
    }

    public static final PsiElement getFinalElement(PsiElement psiElement) {
        KtBlockExpression bodyExpression;
        PsiElement rBrace;
        PsiElement rBrace2;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtFunctionLiteral) {
            rBrace = ((KtFunctionLiteral) psiElement).getRBrace();
            if (rBrace == null) {
                return psiElement;
            }
        } else {
            if (psiElement instanceof KtDeclarationWithBody) {
                KtDeclarationWithBody ktDeclarationWithBody = (KtDeclarationWithBody) psiElement;
                KtBlockExpression bodyBlockExpression = ktDeclarationWithBody.getBodyBlockExpression();
                if (bodyBlockExpression != null && (rBrace2 = bodyBlockExpression.getRBrace()) != null) {
                    return rBrace2;
                }
                KtExpression bodyExpression2 = ktDeclarationWithBody.getBodyExpression();
                return bodyExpression2 != null ? bodyExpression2 : psiElement;
            }
            if (!(psiElement instanceof KtLambdaExpression) || (bodyExpression = ((KtLambdaExpression) psiElement).getBodyExpression()) == null || (rBrace = bodyExpression.getRBrace()) == null) {
                return psiElement;
            }
        }
        return rBrace;
    }

    public static final JsName addFunctionButNotExport(TranslationContext translationContext, FunctionDescriptor descriptor, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(expression, "expression");
        JsName innerNameForDescriptor = translationContext.getInnerNameForDescriptor(descriptor);
        Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
        return addFunctionButNotExport(translationContext, innerNameForDescriptor, expression);
    }

    public static final JsName addFunctionButNotExport(TranslationContext translationContext, JsName name2, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof JsFunction) {
            ((JsFunction) expression).setName(name2);
            translationContext.addDeclarationStatement(expression.makeStmt());
        } else {
            translationContext.addDeclarationStatement(JsAstUtils.newVar(name2, expression));
        }
        return name2;
    }

    public static final List<JsStatement> createPrototypeStatements(JsName superName, JsName name2) {
        Intrinsics.checkNotNullParameter(superName, "superName");
        Intrinsics.checkNotNullParameter(name2, "name");
        JsNameRef jsNameRefMakeRef = superName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(jsNameRefMakeRef);
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        JsInvocation jsInvocation = new JsInvocation(new JsNameRef(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, CommonClassNames.JAVA_LANG_OBJECT_SHORT), jsExpressionPrototypeOf);
        JsNameRef jsNameRefMakeRef2 = name2.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
        JsExpression jsExpressionPrototypeOf2 = JsAstUtils.prototypeOf(jsNameRefMakeRef2);
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf2, "prototypeOf(...)");
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(jsExpressionPrototypeOf2, jsInvocation).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        JsStatement jsStatementMakeStmt2 = JsAstUtils.assignment(new JsNameRef(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, jsExpressionPrototypeOf2.deepCopy()), jsNameRefMakeRef2.deepCopy()).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt2, "makeStmt(...)");
        return CollectionsKt.listOf((Object[]) new JsStatement[]{jsStatementMakeStmt, jsStatementMakeStmt2});
    }

    public static final JsExpression createCoroutineResult(TranslationContext translationContext, ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        JsNameRef jsNameRef = new JsNameRef("$$coroutineResult$$", TranslationUtils.translateContinuationArgument(translationContext).source((Object) callElement));
        JsNameRef jsNameRef2 = jsNameRef;
        MetadataProperties.setSideEffects(jsNameRef2, SideEffectKind.DEPENDS_ON_STATE);
        jsNameRef.setSource(callElement);
        MetadataProperties.setCoroutineResult(jsNameRef, true);
        MetadataProperties.setSynthetic(jsNameRef2, true);
        return jsNameRef;
    }

    public static final KotlinType refineType(KotlinType kotlinType) {
        Object next;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Set<KotlinType> allSupertypes = TypeUtils.getAllSupertypes(kotlinType);
        Intrinsics.checkNotNullExpressionValue(allSupertypes, "getAllSupertypes(...)");
        Iterator<T> it = allSupertypes.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType((KotlinType) next));
        KotlinType kotlinType2 = (KotlinType) next;
        return kotlinType2 == null ? kotlinType : kotlinType2;
    }

    public static final KotlinType getPrecisePrimitiveType(TranslationContext translationContext, KtExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        BindingContext bindingContext = translationContext.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        KotlinType type = bindingContext.getType(expression);
        if (type == null) {
            return null;
        }
        return refineType(type);
    }

    public static final KotlinType getPrecisePrimitiveTypeNotNull(TranslationContext translationContext, KtExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        KotlinType precisePrimitiveType = getPrecisePrimitiveType(translationContext, expression);
        if (precisePrimitiveType != null) {
            return precisePrimitiveType;
        }
        throw new IllegalStateException("Type must be not null for " + expression);
    }

    public static final PrimitiveNumericComparisonInfo getPrimitiveNumericComparisonInfo(TranslationContext translationContext, KtExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        CompilerConfiguration configuration = translationContext.getConfig().getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        if (CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.ProperIeee754Comparisons)) {
            return (PrimitiveNumericComparisonInfo) translationContext.bindingContext().get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, expression);
        }
        return null;
    }

    public static final boolean hasOrInheritsParametersWithDefaultValue(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(functionDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.js.translate.utils.UtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return UtilsKt.hasOrInheritsParametersWithDefaultValue$lambda$7((FunctionDescriptor) obj);
            }
        }, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.utils.UtilsKt.hasOrInheritsParametersWithDefaultValue.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor functionDescriptor2) {
                Intrinsics.checkNotNull(functionDescriptor2);
                return Boolean.valueOf(UtilsKt.hasOwnParametersWithDefaultValue(functionDescriptor2));
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable hasOrInheritsParametersWithDefaultValue$lambda$7(FunctionDescriptor functionDescriptor) {
        Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((FunctionDescriptor) it.next()).getOriginal());
        }
        return arrayList;
    }

    public static final boolean hasOwnParametersWithDefaultValue(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getOriginal().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            if (!valueParameterDescriptor.declaresDefaultValue()) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                if (ArgumentsUtilsKt.isActualParameterWithCorrespondingExpectedDefault(valueParameterDescriptor)) {
                }
            }
            return true;
        }
        return false;
    }
}
