package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.JvmFieldApplicabilityProblem;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmFieldApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0014\u0010\u000f\u001a\u00020\f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\f\u0010\u0013\u001a\u00020\f*\u00020\u0014H\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmFieldApplicabilityChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isInterfaceCompanionWithPublicJvmFieldProperties", "", "companionObject", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "hasBackingField", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "hasCustomAccessor", "Lorg/jetbrains/kotlin/psi/KtProperty;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmFieldApplicabilityChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        JvmFieldApplicabilityProblem jvmFieldApplicabilityProblem;
        DiagnosticFactory1<KtAnnotationEntry, String> diagnosticFactory1;
        Annotations annotations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof PropertyDescriptor) {
            boolean z = declaration instanceof KtProperty;
            if (z || (declaration instanceof KtParameter)) {
                AnnotationDescriptor annotationDescriptorFindJvmFieldAnnotation = JvmAnnotationUtilKt.findJvmFieldAnnotation(descriptor);
                if (annotationDescriptorFindJvmFieldAnnotation == null) {
                    FieldDescriptor delegateField = ((PropertyDescriptor) descriptor).getDelegateField();
                    annotationDescriptorFindJvmFieldAnnotation = (delegateField == null || (annotations = delegateField.getAnnotations()) == null) ? null : annotations.mo6752findAnnotation(JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME);
                    if (annotationDescriptorFindJvmFieldAnnotation == null) {
                        return;
                    }
                }
                if (z && ((KtProperty) declaration).hasDelegate()) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.DELEGATE;
                } else {
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    if (!hasBackingField(propertyDescriptor, context2)) {
                        return;
                    }
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
                    if (ModalityUtilsKt.isOverridable(callableMemberDescriptor)) {
                        jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.NOT_FINAL;
                    } else if (DescriptorVisibilities.isPrivate(propertyDescriptor.getVisibility())) {
                        jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.PRIVATE;
                    } else if (z && hasCustomAccessor((KtProperty) declaration)) {
                        jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.CUSTOM_ACCESSOR;
                    } else {
                        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                        if (!overriddenDescriptors.isEmpty()) {
                            jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.OVERRIDES;
                        } else if (propertyDescriptor.isLateInit()) {
                            jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.LATEINIT;
                        } else if (propertyDescriptor.isConst()) {
                            jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.CONST;
                        } else if (DeclarationCheckersKt.isInsideCompanionObjectOfInterface(callableMemberDescriptor)) {
                            if (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JvmFieldInInterface)) {
                                jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.INSIDE_COMPANION_OF_INTERFACE;
                            } else {
                                DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
                                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                                if (isInterfaceCompanionWithPublicJvmFieldProperties((ClassDescriptor) containingDeclaration)) {
                                    return;
                                } else {
                                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.NOT_PUBLIC_VAL_WITH_JVMFIELD;
                                }
                            }
                        } else if (DescriptorUtils.isTopLevelDeclaration(descriptor) && JvmFileClassUtilKt.isInsideJvmMultifileClassFile(declaration)) {
                            jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.TOP_LEVEL_PROPERTY_OF_MULTIFILE_FACADE;
                        } else {
                            KotlinType returnType = propertyDescriptor.getReturnType();
                            if (returnType == null || !InlineClassManglingRulesKt.isValueClassThatRequiresMangling(returnType)) {
                                return;
                            } else {
                                jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.RETURN_TYPE_IS_VALUE_CLASS;
                            }
                        }
                    }
                }
                KtAnnotationEntry sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorFindJvmFieldAnnotation);
                if (sourceFromAnnotation == null) {
                    return;
                }
                if ((declaration instanceof KtParameter) && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor)) {
                    diagnosticFactory1 = ErrorsJvm.INAPPLICABLE_JVM_FIELD_WARNING;
                } else {
                    diagnosticFactory1 = ErrorsJvm.INAPPLICABLE_JVM_FIELD;
                }
                context.getTrace().report(diagnosticFactory1.on(sourceFromAnnotation, jvmFieldApplicabilityProblem.getErrorMessage()));
            }
        }
    }

    private final boolean isInterfaceCompanionWithPublicJvmFieldProperties(ClassDescriptor companionObject) {
        for (DeclarationDescriptor declarationDescriptor : companionObject.getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.VARIABLES, MemberScope.INSTANCE.getALL_NAME_FILTER())) {
            if (declarationDescriptor instanceof PropertyDescriptor) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
                if (!Intrinsics.areEqual(propertyDescriptor.getVisibility(), DescriptorVisibilities.PUBLIC) || propertyDescriptor.isVar() || propertyDescriptor.getModality() != Modality.FINAL || !DescriptorsJvmAbiUtil.hasJvmFieldAnnotation((CallableMemberDescriptor) declarationDescriptor)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean hasCustomAccessor(KtProperty ktProperty) {
        return (ktProperty.getGetter() == null && ktProperty.getSetter() == null) ? false : true;
    }

    private final boolean hasBackingField(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        Boolean bool = (Boolean) bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
        if (bool == null) {
            bool = false;
        }
        return bool.booleanValue();
    }
}
