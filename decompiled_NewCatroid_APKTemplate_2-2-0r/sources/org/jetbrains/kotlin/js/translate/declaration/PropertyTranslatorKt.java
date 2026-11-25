package org.jetbrains.kotlin.js.translate.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;

/* JADX INFO: compiled from: PropertyTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b\u001a4\u0010\u000b\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\r\u001a(\u0010\u000f\u001a\u00020\b*\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015\u001a\n\u0010\u0016\u001a\u00020\u0017*\u00020\n\u001a\n\u0010\u0018\u001a\u00020\u0017*\u00020\n\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u0015*\u00020\b2\u0006\u0010\u001a\u001a\u00020\n¨\u0006\u001b"}, d2 = {"translateAccessors", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "result", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "declaration", "Lorg/jetbrains/kotlin/psi/KtProperty;", "addGetterAndSetter", "generateGetter", "Lkotlin/Function0;", "generateSetter", "contextWithPropertyMetadataCreationIntrinsified", "delegatedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "property", "host", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "hasCustomGetter", "", "hasCustomSetter", "translateDelegateOrInitializerExpression", "expression", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertyTranslatorKt {
    public static final void translateAccessors(VariableDescriptorWithAccessors descriptor, KtProperty ktProperty, List<JsPropertyInitializer> result, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            if (propertyDescriptor.getModality() == Modality.ABSTRACT || JsDescriptorUtils.isSimpleFinalProperty(propertyDescriptor)) {
                return;
            }
        }
        new PropertyTranslator(descriptor, ktProperty, context).translate(result);
    }

    public static final void translateAccessors(VariableDescriptorWithAccessors descriptor, List<JsPropertyInitializer> result, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(context, "context");
        translateAccessors(descriptor, null, result, context);
    }

    public static final void addGetterAndSetter(List<JsPropertyInitializer> list, VariableDescriptorWithAccessors descriptor, Function0<? extends JsPropertyInitializer> generateGetter, Function0<? extends JsPropertyInitializer> generateSetter) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(generateGetter, "generateGetter");
        Intrinsics.checkNotNullParameter(generateSetter, "generateSetter");
        list.add(generateGetter.invoke());
        if (descriptor.isVar()) {
            list.add(generateSetter.invoke());
        }
    }

    public static final JsExpression translateDelegateOrInitializerExpression(TranslationContext translationContext, KtProperty expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(translationContext.bindingContext(), expression);
        Intrinsics.checkNotNull(descriptorForElement, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors");
        VariableDescriptorWithAccessors variableDescriptorWithAccessors = (VariableDescriptorWithAccessors) descriptorForElement;
        KtExpression delegateExpressionOrInitializer = expression.getDelegateExpressionOrInitializer();
        if (delegateExpressionOrInitializer == null) {
            return null;
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(delegateExpressionOrInitializer, translationContext);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
        ResolvedCall resolvedCall = (ResolvedCall) translationContext.bindingContext().get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, variableDescriptorWithAccessors);
        if (resolvedCall != null) {
            return CallTranslator.translate(contextWithPropertyMetadataCreationIntrinsified(translationContext, resolvedCall, variableDescriptorWithAccessors, new JsThisRef()), resolvedCall, jsExpressionTranslateAsExpression);
        }
        return TranslationUtils.coerce(translationContext, jsExpressionTranslateAsExpression, variableDescriptorWithAccessors.getType());
    }

    public static final TranslationContext contextWithPropertyMetadataCreationIntrinsified(TranslationContext translationContext, ResolvedCall<FunctionDescriptor> delegatedCall, VariableDescriptorWithAccessors property, JsExpression host) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(delegatedCall, "delegatedCall");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(host, "host");
        List<ResolvedValueArgument> valueArgumentsByIndex = delegatedCall.getValueArgumentsByIndex();
        Intrinsics.checkNotNull(valueArgumentsByIndex);
        ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex.get(0);
        Intrinsics.checkNotNull(resolvedValueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument");
        ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
        Intrinsics.checkNotNull(valueArgument);
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        List<ResolvedValueArgument> valueArgumentsByIndex2 = delegatedCall.getValueArgumentsByIndex();
        Intrinsics.checkNotNull(valueArgumentsByIndex2);
        ResolvedValueArgument resolvedValueArgument2 = valueArgumentsByIndex2.get(1);
        Intrinsics.checkNotNull(resolvedValueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument");
        ValueArgument valueArgument2 = ((ExpressionValueArgument) resolvedValueArgument2).getValueArgument();
        Intrinsics.checkNotNull(valueArgument2);
        KtExpression argumentExpression2 = valueArgument2.getArgumentExpression();
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(translationContext.getVariableForPropertyMetadata(property), (JsExpression) null);
        Intrinsics.checkNotNull(jsNameRefPureFqn);
        MetadataProperties.setSynthetic(jsNameRefPureFqn, true);
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "apply(...)");
        TranslationContext translationContextInnerContextWithAliasesForExpressions = translationContext.innerContextWithAliasesForExpressions(MapsKt.mapOf(TuplesKt.to(argumentExpression, host), TuplesKt.to(argumentExpression2, jsNameRefPureFqn)));
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithAliasesForExpressions, "innerContextWithAliasesForExpressions(...)");
        return translationContextInnerContextWithAliasesForExpressions;
    }

    public static final boolean hasCustomGetter(KtProperty ktProperty) {
        Intrinsics.checkNotNullParameter(ktProperty, "<this>");
        KtPropertyAccessor getter = ktProperty.getGetter();
        if (getter != null) {
            return getter.hasBody();
        }
        return false;
    }

    public static final boolean hasCustomSetter(KtProperty ktProperty) {
        Intrinsics.checkNotNullParameter(ktProperty, "<this>");
        KtPropertyAccessor setter = ktProperty.getSetter();
        if (setter != null) {
            return setter.hasBody();
        }
        return false;
    }
}
