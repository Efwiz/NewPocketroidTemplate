package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: MultiFieldValueClassAnnotationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MultiFieldValueClassAnnotationsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "report", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultiFieldValueClassAnnotationsChecker implements DeclarationChecker {
    public static final MultiFieldValueClassAnnotationsChecker INSTANCE = new MultiFieldValueClassAnnotationsChecker();

    private MultiFieldValueClassAnnotationsChecker() {
    }

    private final void report(DeclarationCheckerContext context, String name2, KotlinType type, KtAnnotationEntry annotationEntry) {
        if (InlineClassesUtilsKt.needsMfvcFlattening(type)) {
            context.getTrace().report(Errors.ANNOTATION_ON_ILLEGAL_MULTI_FIELD_VALUE_CLASS_TYPED_TARGET.on(annotationEntry, name2));
        }
    }

    private static final void check$report(DeclarationCheckerContext declarationCheckerContext, String str, KotlinType kotlinType, Annotations annotations) {
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            MultiFieldValueClassAnnotationsChecker multiFieldValueClassAnnotationsChecker = INSTANCE;
            PsiElement psi = PsiSourceElementKt.getPsi(annotationDescriptor.getSource());
            Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotationEntry");
            multiFieldValueClassAnnotationsChecker.report(declarationCheckerContext, str, kotlinType, (KtAnnotationEntry) psi);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        List<ValueParameterDescriptor> valueParameters;
        ValueParameterDescriptor valueParameterDescriptor;
        KotlinType returnType;
        KtExpression delegateExpression;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            FieldDescriptor backingField = propertyDescriptor.getBackingField();
            if (backingField != null) {
                KotlinType type = propertyDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                check$report(context, "fields", type, backingField.getAnnotations());
            }
            KotlinType type2 = null;
            KtProperty ktProperty = declaration instanceof KtProperty ? (KtProperty) declaration : null;
            if (ktProperty != null && (delegateExpression = ktProperty.getDelegateExpression()) != null) {
                BindingContext context2 = context.getTrace().getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                type2 = CallUtilKt.getType(delegateExpression, context2);
            }
            FieldDescriptor delegateField = propertyDescriptor.getDelegateField();
            if (delegateField != null && type2 != null) {
                check$report(context, "delegate fields", type2, delegateField.getAnnotations());
            }
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            if (getter != null) {
                List<ReceiverParameterDescriptor> contextReceiverParameters = getter.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                if (contextReceiverParameters.isEmpty() && getter.getExtensionReceiverParameter() == null && (returnType = getter.getReturnType()) != null) {
                    Intrinsics.checkNotNull(returnType);
                    check$report(context, "getters", returnType, getter.getAnnotations());
                }
            }
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (setter != null && (valueParameters = setter.getValueParameters()) != null && (valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.single((List) valueParameters)) != null) {
                KotlinType type3 = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                check$report(context, "parameters", type3, valueParameterDescriptor.getAnnotations());
            }
            ReceiverParameterDescriptor extensionReceiverParameter = propertyDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                KotlinType type4 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
                check$report(context, "receivers", type4, extensionReceiverParameter.getAnnotations());
            }
            List<ReceiverParameterDescriptor> contextReceiverParameters2 = propertyDescriptor.getContextReceiverParameters();
            Intrinsics.checkNotNullExpressionValue(contextReceiverParameters2, "getContextReceiverParameters(...)");
            for (ReceiverParameterDescriptor receiverParameterDescriptor : contextReceiverParameters2) {
                KotlinType type5 = receiverParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type5, "getType(...)");
                check$report(context, "receivers", type5, receiverParameterDescriptor.getAnnotations());
            }
            return;
        }
        if (descriptor instanceof PropertyAccessorDescriptor) {
            return;
        }
        if (descriptor instanceof LocalVariableDescriptor) {
            KotlinType type6 = ((LocalVariableDescriptor) descriptor).getType();
            Intrinsics.checkNotNullExpressionValue(type6, "getType(...)");
            check$report(context, "variables", type6, descriptor.getAnnotations());
            return;
        }
        if (descriptor instanceof CallableDescriptor) {
            CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
            ReceiverParameterDescriptor extensionReceiverParameter2 = callableDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter2 != null) {
                KotlinType type7 = extensionReceiverParameter2.getType();
                Intrinsics.checkNotNullExpressionValue(type7, "getType(...)");
                check$report(context, "receivers", type7, extensionReceiverParameter2.getAnnotations());
            }
            List<ReceiverParameterDescriptor> contextReceiverParameters3 = callableDescriptor.getContextReceiverParameters();
            Intrinsics.checkNotNullExpressionValue(contextReceiverParameters3, "getContextReceiverParameters(...)");
            for (ReceiverParameterDescriptor receiverParameterDescriptor2 : contextReceiverParameters3) {
                KotlinType type8 = receiverParameterDescriptor2.getType();
                Intrinsics.checkNotNullExpressionValue(type8, "getType(...)");
                check$report(context, "receivers", type8, receiverParameterDescriptor2.getAnnotations());
            }
            List<ValueParameterDescriptor> valueParameters2 = callableDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            for (ValueParameterDescriptor valueParameterDescriptor2 : valueParameters2) {
                KotlinType type9 = valueParameterDescriptor2.getType();
                Intrinsics.checkNotNullExpressionValue(type9, "getType(...)");
                check$report(context, "parameters", type9, valueParameterDescriptor2.getAnnotations());
            }
        }
    }
}
