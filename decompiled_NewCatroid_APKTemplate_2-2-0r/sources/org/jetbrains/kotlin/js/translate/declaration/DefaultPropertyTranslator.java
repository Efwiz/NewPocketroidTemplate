package org.jetbrains.kotlin.js.translate.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: PropertyTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J&\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001c\u0010\u001b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/DefaultPropertyTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "delegateReference", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "getDelegateReference", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "generateDefaultGetterFunction", "", "getterDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "generateDefaultSetterFunction", "setterDescriptor", "generateDelegatedGetterFunction", "delegatedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "translateHost", "accessorDescriptor", "newDeclarationIfNecessary", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultPropertyTranslator extends AbstractTranslator {
    private final JsExpression delegateReference;
    private final VariableDescriptorWithAccessors descriptor;

    public final VariableDescriptorWithAccessors getDescriptor() {
        return this.descriptor;
    }

    public final JsExpression getDelegateReference() {
        return this.delegateReference;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultPropertyTranslator(VariableDescriptorWithAccessors descriptor, TranslationContext context, JsExpression delegateReference) {
        super(context);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(delegateReference, "delegateReference");
        this.descriptor = descriptor;
        this.delegateReference = delegateReference;
    }

    public final void generateDefaultGetterFunction(VariableAccessorDescriptor getterDescriptor, JsFunction function) {
        Intrinsics.checkNotNullParameter(getterDescriptor, "getterDescriptor");
        Intrinsics.checkNotNullParameter(function, "function");
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) bindingContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, getterDescriptor);
        if (resolvedCall != null) {
            generateDelegatedGetterFunction(getterDescriptor, resolvedCall, function);
            return;
        }
        DescriptorUtilsKt.isExtension(this.descriptor);
        boolean z = this.descriptor instanceof PropertyDescriptor;
        TranslationContext translationContextContext = context();
        VariableDescriptorWithAccessors variableDescriptorWithAccessors = this.descriptor;
        Intrinsics.checkNotNull(variableDescriptorWithAccessors, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        JsNameRef jsNameRefBackingFieldReference = TranslationUtils.backingFieldReference(translationContextContext, (PropertyDescriptor) variableDescriptorWithAccessors);
        Intrinsics.checkNotNullExpressionValue(jsNameRefBackingFieldReference, "backingFieldReference(...)");
        JsNameRef jsNameRef = jsNameRefBackingFieldReference;
        if (getterDescriptor instanceof PropertyAccessorDescriptor) {
            PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) getterDescriptor;
            if (propertyAccessorDescriptor.getCorrespondingProperty().isLateInit()) {
                JsExpression referenceToIntrinsic = context().getReferenceToIntrinsic(Namer.THROW_UNINITIALIZED_PROPERTY_ACCESS_EXCEPTION);
                Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
                List<JsStatement> statements = function.getBody().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(new JsIf(new JsBinaryOperation(JsBinaryOperator.EQ, jsNameRef, new JsNullLiteral()), new JsReturn(new JsInvocation(referenceToIntrinsic, new JsStringLiteral(propertyAccessorDescriptor.getCorrespondingProperty().getShortName().asString())))));
            }
        }
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), jsNameRef, TranslationUtils.getReturnTypeForCoercion(getterDescriptor));
        Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
        List<JsStatement> statements2 = function.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        JsReturn jsReturn = new JsReturn(jsExpressionCoerce);
        SourceElement source = ((PropertyDescriptor) this.descriptor).getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        jsReturn.setSource(PsiSourceElementKt.getPsi(source));
        statements2.add(jsReturn);
    }

    private final void generateDelegatedGetterFunction(VariableAccessorDescriptor getterDescriptor, ResolvedCall<FunctionDescriptor> delegatedCall, JsFunction function) {
        JsExpression jsExpressionTranslateHost = translateHost(getterDescriptor, function);
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        JsExpression jsExpressionTranslate = CallTranslator.translate(PropertyTranslatorKt.contextWithPropertyMetadataCreationIntrinsified(newDeclarationIfNecessary(translationContextContext, getterDescriptor, function), delegatedCall, this.descriptor, jsExpressionTranslateHost), delegatedCall, this.delegateReference);
        SourceElement source = getterDescriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        jsExpressionTranslate.setSource(PsiSourceElementKt.getPsi(source));
        AstUtilsKt.addStatement(function, new JsReturn(jsExpressionTranslate));
    }

    public final void generateDefaultSetterFunction(VariableAccessorDescriptor setterDescriptor, JsFunction function) {
        Intrinsics.checkNotNullParameter(setterDescriptor, "setterDescriptor");
        Intrinsics.checkNotNullParameter(function, "function");
        setterDescriptor.getValueParameters().size();
        String strAsString = setterDescriptor.getCorrespondingVariable().getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JsName name2 = AstUtilsKt.addParameter$default(function, strAsString, null, 2, null).getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        TranslationContext translationContextInnerContextWithAliased = context().innerContextWithAliased(setterDescriptor.getValueParameters().get(0), name2.makeRef());
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithAliased, "innerContextWithAliased(...)");
        ResolvedCall resolvedCall = (ResolvedCall) bindingContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, setterDescriptor);
        if (resolvedCall != null) {
            JsExpression jsExpressionTranslate = CallTranslator.translate(PropertyTranslatorKt.contextWithPropertyMetadataCreationIntrinsified(newDeclarationIfNecessary(translationContextInnerContextWithAliased, setterDescriptor, function), resolvedCall, this.descriptor, translateHost(setterDescriptor, function)), resolvedCall, this.delegateReference);
            SourceElement source = setterDescriptor.getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            jsExpressionTranslate.setSource(PsiSourceElementKt.getPsi(source));
            JsStatement jsStatementMakeStmt = jsExpressionTranslate.makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            AstUtilsKt.addStatement(function, jsStatementMakeStmt);
            return;
        }
        DescriptorUtilsKt.isExtension(this.descriptor);
        boolean z = this.descriptor instanceof PropertyDescriptor;
        JsNameRef jsNameRefMakeRef = name2.makeRef();
        Intrinsics.checkNotNull(jsNameRefMakeRef);
        JsNameRef jsNameRef = jsNameRefMakeRef;
        MetadataProperties.setType(jsNameRef, TranslationUtils.getReturnTypeForCoercion(setterDescriptor.getCorrespondingVariable()));
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "apply(...)");
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), jsNameRef, TranslationUtils.getReturnTypeForCoercion(setterDescriptor.getCorrespondingVariable(), true));
        Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
        VariableDescriptorWithAccessors variableDescriptorWithAccessors = this.descriptor;
        Intrinsics.checkNotNull(variableDescriptorWithAccessors, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        JsExpression jsExpressionAssignmentToBackingField = TranslationUtils.assignmentToBackingField(translationContextInnerContextWithAliased, (PropertyDescriptor) variableDescriptorWithAccessors, jsExpressionCoerce);
        Intrinsics.checkNotNullExpressionValue(jsExpressionAssignmentToBackingField, "assignmentToBackingField(...)");
        SourceElement source2 = ((PropertyDescriptor) this.descriptor).getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source2, "getSource(...)");
        jsExpressionAssignmentToBackingField.setSource(PsiSourceElementKt.getPsi(source2));
        JsStatement jsStatementMakeStmt2 = jsExpressionAssignmentToBackingField.makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt2, "makeStmt(...)");
        AstUtilsKt.addStatement(function, jsStatementMakeStmt2);
    }

    private final TranslationContext newDeclarationIfNecessary(TranslationContext translationContext, VariableAccessorDescriptor variableAccessorDescriptor, JsFunction jsFunction) {
        if (!(variableAccessorDescriptor.getCorrespondingVariable() instanceof LocalVariableDescriptor)) {
            TranslationContext translationContextNewDeclaration = translationContext.newDeclaration(variableAccessorDescriptor);
            Intrinsics.checkNotNull(translationContextNewDeclaration);
            return translationContextNewDeclaration;
        }
        TranslationContext translationContextInnerBlock = translationContext.innerBlock(jsFunction.getBody());
        Intrinsics.checkNotNull(translationContextInnerBlock);
        return translationContextInnerBlock;
    }

    private final JsExpression translateHost(VariableAccessorDescriptor accessorDescriptor, JsFunction function) {
        if (DescriptorUtilsKt.isExtension(accessorDescriptor)) {
            String receiverParameterName = Namer.getReceiverParameterName();
            Intrinsics.checkNotNullExpressionValue(receiverParameterName, "getReceiverParameterName(...)");
            JsNameRef jsNameRefMakeRef = AstUtilsKt.addParameter(function, receiverParameterName, 0).getName().makeRef();
            Intrinsics.checkNotNull(jsNameRefMakeRef);
            return jsNameRefMakeRef;
        }
        return new JsThisRef();
    }
}
