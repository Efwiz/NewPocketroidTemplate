package org.jetbrains.kotlin.js.translate.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.inline.util.rewriters.NameReplacingVisitor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.context.UsageTracker;
import org.jetbrains.kotlin.js.translate.context.UsageTrackerKt;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: compiled from: LiteralFunctionTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001a\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u0005\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/LiteralFunctionTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "fillCoroutineMetadata", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl$WithDestructuringDeclaration;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LiteralFunctionTranslator extends AbstractTranslator {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiteralFunctionTranslator(TranslationContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final JsExpression translate(KtDeclarationWithBody declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        PsiElement finalElement = UtilsKt.getFinalElement(declaration);
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        FunctionDescriptor functionDescriptor = BindingUtils.getFunctionDescriptor(translationContextContext.bindingContext(), declaration);
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        JsFunction functionObject = translationContextContext.getFunctionObject(functionDescriptor);
        Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
        TranslationContext translationContextNewFunctionBodyWithUsageTracker = translationContextContext.newFunctionBodyWithUsageTracker(functionObject, functionDescriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextNewFunctionBodyWithUsageTracker, "newFunctionBodyWithUsageTracker(...)");
        List<JsParameter> parameters = functionObject.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        TranslationContext translationContextTranslateAndAliasParameters = FunctionTranslatorKt.translateAndAliasParameters(translationContextNewFunctionBodyWithUsageTracker, functionDescriptor, parameters);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
            if (valueParameterDescriptor instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
                functionObject.getBody().getStatements().add(translate((ValueParameterDescriptorImpl.WithDestructuringDeclaration) valueParameterDescriptor, translationContextTranslateAndAliasParameters));
            }
        }
        List<JsStatement> statements = functionObject.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> defaultValueForArguments = FunctionBodyTranslator.setDefaultValueForArguments(functionDescriptor, translationContextTranslateAndAliasParameters);
        Intrinsics.checkNotNullExpressionValue(defaultValueForArguments, "setDefaultValueForArguments(...)");
        CollectionsKt.addAll(statements, defaultValueForArguments);
        List<JsStatement> statements2 = functionObject.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        statements2.add(FunctionBodyTranslator.translateFunctionBody(functionDescriptor, declaration, translationContextTranslateAndAliasParameters));
        MetadataProperties.setFunctionDescriptor(functionObject, functionDescriptor);
        functionObject.setSource(declaration);
        functionObject.getBody().setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        UsageTracker usageTracker = translationContextTranslateAndAliasParameters.usageTracker();
        Intrinsics.checkNotNull(usageTracker);
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        JsName innerNameForDescriptor = translationContextContext.getInnerNameForDescriptor(functionDescriptor2);
        Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
        if (UsageTrackerKt.hasCapturedExceptContaining(usageTracker)) {
            JsFunction jsFunctionSimpleReturnFunction = TranslationUtils.simpleReturnFunction(translationContextContext.scope(), functionObject.source((Object) declaration));
            Intrinsics.checkNotNullExpressionValue(jsFunctionSimpleReturnFunction, "simpleReturnFunction(...)");
            MetadataProperties.setLocal(jsFunctionSimpleReturnFunction, true);
            if (usageTracker.getCapturedDescriptors().contains(functionDescriptor) && !DescriptorUtilsKt.isCoroutineLambda(functionDescriptor2)) {
                functionObject.setName(UsageTrackerKt.getNameForCapturedDescriptor(usageTracker, functionDescriptor2));
            }
            MetadataProperties.setStaticRef(innerNameForDescriptor, jsFunctionSimpleReturnFunction);
            fillCoroutineMetadata(jsFunctionSimpleReturnFunction, translationContextContext, functionDescriptor);
            jsFunctionSimpleReturnFunction.setSource(declaration);
            return LiteralFunctionTranslatorKt.withCapturedParameters(jsFunctionSimpleReturnFunction, translationContextTranslateAndAliasParameters, innerNameForDescriptor, translationContextContext, declaration);
        }
        if (usageTracker.getCapturedDescriptors().contains(functionDescriptor)) {
            JsName nameForCapturedDescriptor = UsageTrackerKt.getNameForCapturedDescriptor(usageTracker, functionDescriptor2);
            Intrinsics.checkNotNull(nameForCapturedDescriptor);
            JsName innerNameForDescriptor2 = translationContextContext.getInnerNameForDescriptor(functionDescriptor2);
            Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor2, "getInnerNameForDescriptor(...)");
            new NameReplacingVisitor(MapsKt.mapOf(TuplesKt.to(nameForCapturedDescriptor, JsAstUtils.pureFqn(innerNameForDescriptor2, (JsExpression) null)))).accept(functionObject);
        }
        MetadataProperties.setLocal(functionObject, true);
        LiteralFunctionTranslatorKt.addFunctionDeclaration(translationContextContext, innerNameForDescriptor, functionObject, declaration);
        fillCoroutineMetadata(functionObject, translationContextContext, functionDescriptor);
        MetadataProperties.setStaticRef(innerNameForDescriptor, functionObject);
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(innerNameForDescriptor, (JsExpression) null);
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        return jsNameRefPureFqn;
    }

    public final void fillCoroutineMetadata(JsFunction jsFunction, TranslationContext context, FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.isSuspend()) {
            UtilsKt.fillCoroutineMetadata(jsFunction, context, descriptor, descriptor.getExtensionReceiverParameter() != null);
            MetadataProperties.setForceStateMachine(jsFunction, true);
        }
    }

    public final JsVars translate(ValueParameterDescriptorImpl.WithDestructuringDeclaration withDestructuringDeclaration, TranslationContext context) {
        KtDestructuringDeclaration destructuringDeclaration;
        Intrinsics.checkNotNullParameter(withDestructuringDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ValueParameterDescriptorImpl.WithDestructuringDeclaration withDestructuringDeclaration2 = withDestructuringDeclaration;
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(withDestructuringDeclaration2);
        KtParameter ktParameter = psiElementDescriptorToDeclaration instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration : null;
        if (ktParameter == null || (destructuringDeclaration = ktParameter.getDestructuringDeclaration()) == null) {
            throw new IllegalStateException(("Destructuring declaration for descriptor " + withDestructuringDeclaration + " not found").toString());
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(withDestructuringDeclaration2, context);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        JsVars jsVarsTranslate = DestructuringDeclarationTranslator.translate(destructuringDeclaration, jsExpressionTranslateAsValueReference, context);
        Intrinsics.checkNotNullExpressionValue(jsVarsTranslate, "translate(...)");
        return jsVarsTranslate;
    }
}
