package org.jetbrains.kotlin.js.translate.expression;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.context.InlineFunctionContext;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;

/* JADX INFO: compiled from: FunctionTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001a\u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006*\u00020\u0002H\u0002\u001a&\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002\u001a \u0010\u0011\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\f\u001a\u00020\u00022\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u001a\u001a\u0010\u0016\u001a\u00020\n*\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a\u001a\"\u0010\u001b\u001a\u00020\u000e*\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u001d"}, d2 = {"requiresExtensionReceiverParameter", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getRequiresExtensionReceiverParameter", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Z", "getCorrectTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "reportInlineFunction", "", "Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumer;", "descriptor", "translatedFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "sourceInfo", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "translateAndAliasParameters", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "targetList", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "translateFunction", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "wrapWithInlineMetadata", "outerContext", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionTranslatorKt {
    public static final TranslationContext translateAndAliasParameters(TranslationContext translationContext, FunctionDescriptor descriptor, List<JsParameter> targetList) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(targetList, "targetList");
        Map<DeclarationDescriptor, JsExpression> linkedHashMap = new LinkedHashMap<>();
        for (TypeParameterDescriptor typeParameterDescriptor : getCorrectTypeParameters(descriptor)) {
            if (typeParameterDescriptor.isReified()) {
                JsName nameForDescriptor = translationContext.getNameForDescriptor(typeParameterDescriptor);
                Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
                List<JsParameter> list = targetList;
                list.add(new JsParameter(nameForDescriptor));
                String strIsInstanceSuggestedName = Namer.isInstanceSuggestedName(typeParameterDescriptor);
                Intrinsics.checkNotNullExpressionValue(strIsInstanceSuggestedName, "isInstanceSuggestedName(...)");
                JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(strIsInstanceSuggestedName);
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
                list.add(new JsParameter(jsNameDeclareTemporaryName));
                Intrinsics.checkNotNull(typeParameterDescriptor);
                JsExpression jsExpressionMakeRef = jsNameDeclareTemporaryName.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsExpressionMakeRef, "makeRef(...)");
                linkedHashMap.put(typeParameterDescriptor, jsExpressionMakeRef);
            }
        }
        if (getRequiresExtensionReceiverParameter(descriptor)) {
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            JsExpression jsExpressionMakeRef2 = jsNameDeclareTemporaryName2.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsExpressionMakeRef2, "makeRef(...)");
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            MetadataProperties.setType(jsExpressionMakeRef2, extensionReceiverParameter.getType());
            DeclarationDescriptor extensionReceiverParameter2 = descriptor.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter2);
            linkedHashMap.put(extensionReceiverParameter2, jsExpressionMakeRef2);
            targetList.add(new JsParameter(jsNameDeclareTemporaryName2));
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
            ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
            JsName nameForDescriptor2 = translationContext.getNameForDescriptor(valueParameterDescriptor2);
            Intrinsics.checkNotNullExpressionValue(nameForDescriptor2, "getNameForDescriptor(...)");
            JsName jsNameDeclareTemporaryName3 = JsScope.declareTemporaryName(nameForDescriptor2.getIdent());
            Intrinsics.checkNotNull(jsNameDeclareTemporaryName3);
            MetadataProperties.setDescriptor(jsNameDeclareTemporaryName3, valueParameterDescriptor2);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName3, "also(...)");
            JsExpression jsExpressionPureFqn = JsAstUtils.pureFqn(jsNameDeclareTemporaryName3, (JsExpression) null);
            Intrinsics.checkNotNullExpressionValue(jsExpressionPureFqn, "pureFqn(...)");
            MetadataProperties.setType(jsExpressionPureFqn, valueParameterDescriptor.getType());
            Intrinsics.checkNotNull(valueParameterDescriptor);
            linkedHashMap.put(valueParameterDescriptor, jsExpressionPureFqn);
            JsParameter jsParameter = new JsParameter(jsNameDeclareTemporaryName3);
            MetadataProperties.setHasDefaultValue(jsParameter, ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor));
            targetList.add(jsParameter);
        }
        ValueParameterDescriptor continuationParameterDescriptor = translationContext.getContinuationParameterDescriptor();
        if (continuationParameterDescriptor != null) {
            targetList.add(new JsParameter(translationContext.getNameForDescriptor(continuationParameterDescriptor)));
            JsExpression jsExpressionStateMachineReceiver = JsAstUtils.stateMachineReceiver();
            Intrinsics.checkNotNullExpressionValue(jsExpressionStateMachineReceiver, "stateMachineReceiver(...)");
            linkedHashMap.put(continuationParameterDescriptor, jsExpressionStateMachineReceiver);
        }
        TranslationContext translationContextInnerContextWithDescriptorsAliased = translationContext.innerContextWithDescriptorsAliased(linkedHashMap);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithDescriptorsAliased, "innerContextWithDescriptorsAliased(...)");
        return translationContextInnerContextWithDescriptorsAliased;
    }

    private static final List<TypeParameterDescriptor> getCorrectTypeParameters(FunctionDescriptor functionDescriptor) {
        List<TypeParameterDescriptor> typeParameters;
        PropertyDescriptor correspondingProperty;
        PropertyAccessorDescriptor propertyAccessorDescriptor = functionDescriptor instanceof PropertyAccessorDescriptor ? (PropertyAccessorDescriptor) functionDescriptor : null;
        if (propertyAccessorDescriptor == null || (correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty()) == null || (typeParameters = correspondingProperty.getTypeParameters()) == null) {
            typeParameters = functionDescriptor.getTypeParameters();
        }
        Intrinsics.checkNotNull(typeParameters);
        return typeParameters;
    }

    private static final boolean getRequiresExtensionReceiverParameter(FunctionDescriptor functionDescriptor) {
        return DescriptorUtils.isExtension(functionDescriptor);
    }

    public static final void translateFunction(TranslationContext translationContext, KtDeclarationWithBody declaration, JsFunction function) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(function, "function");
        FunctionDescriptor functionDescriptor = BindingUtils.getFunctionDescriptor(translationContext.bindingContext(), declaration);
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        if (declaration.hasBody()) {
            JsBlock jsBlockTranslateFunctionBody = FunctionBodyTranslator.translateFunctionBody(functionDescriptor, declaration, translationContext);
            Intrinsics.checkNotNullExpressionValue(jsBlockTranslateFunctionBody, "translateFunctionBody(...)");
            List<JsStatement> statements = function.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> statements2 = jsBlockTranslateFunctionBody.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
        }
        MetadataProperties.setFunctionDescriptor(function, functionDescriptor);
    }

    public static final JsExpression wrapWithInlineMetadata(TranslationContext translationContext, TranslationContext outerContext, JsFunction function, FunctionDescriptor descriptor) {
        JsExpression jsExpressionWrapFunction;
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        if (descriptor.isInline()) {
            JsConfig config = translationContext.getConfig();
            Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
            if (DescriptorUtilsKt.shouldBeExported(descriptor, config)) {
                jsExpressionWrapFunction = InlineMetadata.INSTANCE.compose(function, descriptor, translationContext).functionWithMetadata(outerContext, psi);
            } else {
                InlineFunctionContext inlineFunctionContext = translationContext.getInlineFunctionContext();
                Intrinsics.checkNotNull(inlineFunctionContext);
                List<JsStatement> statements = inlineFunctionContext.getImportBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                List<JsStatement> statements2 = inlineFunctionContext.getPrototypeBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                List listPlus = CollectionsKt.plus((Collection) statements, (Iterable) statements2);
                List<JsStatement> statements3 = inlineFunctionContext.getDeclarationsBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                jsExpressionWrapFunction = InlineMetadata.INSTANCE.wrapFunction(outerContext, new FunctionWithWrapper(function, new JsBlock((List<JsStatement>) CollectionsKt.plus((Collection<? extends JsReturn>) CollectionsKt.plus((Collection) listPlus, (Iterable) statements3), new JsReturn(function)))), psi);
            }
            IncrementalResultsConsumer incrementalResultsConsumer = (IncrementalResultsConsumer) translationContext.getConfig().getConfiguration().get(JSConfigurationKeys.INCREMENTAL_RESULTS_CONSUMER);
            if (incrementalResultsConsumer == null) {
                return jsExpressionWrapFunction;
            }
            reportInlineFunction(incrementalResultsConsumer, descriptor, jsExpressionWrapFunction, psi);
            return jsExpressionWrapFunction;
        }
        return function;
    }

    private static final void reportInlineFunction(IncrementalResultsConsumer incrementalResultsConsumer, FunctionDescriptor functionDescriptor, JsExpression jsExpression, PsiElement psiElement) {
        PsiFile psiFile;
        String strAsString;
        int i;
        int iIntValue;
        ASTNode node;
        SourceFile containingFile = functionDescriptor.getSourceElement().getContainingFile();
        Integer numValueOf = null;
        PsiSourceFile psiSourceFile = containingFile instanceof PsiSourceFile ? (PsiSourceFile) containingFile : null;
        if (psiSourceFile == null || (psiFile = psiSourceFile.getPsiFile()) == null) {
            return;
        }
        File fileVirtualToIoFile = VfsUtilCore.virtualToIoFile(psiFile.getVirtualFile());
        Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile, "virtualToIoFile(...)");
        DescriptorVisibility visibility = functionDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        if (EffectiveVisibilityUtilsKt.effectiveVisibility(visibility, (DeclarationDescriptor) functionDescriptor2, true).getPrivateApi()) {
            return;
        }
        if (functionDescriptor instanceof PropertyGetterDescriptor) {
            StringBuilder sb = new StringBuilder("<get>");
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) functionDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            strAsString = sb.append(org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.getFqNameSafe(correspondingProperty).asString()).toString();
        } else if (functionDescriptor instanceof PropertySetterDescriptor) {
            StringBuilder sb2 = new StringBuilder("<set>");
            PropertyDescriptor correspondingProperty2 = ((PropertySetterDescriptor) functionDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty2, "getCorrespondingProperty(...)");
            strAsString = sb2.append(org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.getFqNameSafe(correspondingProperty2).asString()).toString();
        } else {
            strAsString = org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.getFqNameSafe(functionDescriptor2).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        String str = strAsString;
        if (psiElement != null && (node = psiElement.getNode()) != null) {
            numValueOf = Integer.valueOf(node.getStartOffset());
        }
        Document document = psiFile.getViewProvider().getDocument();
        if (numValueOf == null || document == null) {
            i = -1;
            iIntValue = -1;
        } else {
            int lineNumber = document.getLineNumber(numValueOf.intValue());
            i = lineNumber;
            iIntValue = numValueOf.intValue() - document.getLineStartOffset(lineNumber);
        }
        incrementalResultsConsumer.processInlineFunction(fileVirtualToIoFile, str, jsExpression, i, iIntValue);
    }
}
