package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: declarationCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/OverloadsAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDeclaration", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverloadsAnnotationChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtAnnotationEntry sourceFromAnnotation;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorFindJvmOverloadsAnnotation = JvmAnnotationUtilKt.findJvmOverloadsAnnotation(descriptor);
        if (annotationDescriptorFindJvmOverloadsAnnotation == null || (sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorFindJvmOverloadsAnnotation)) == null) {
            return;
        }
        checkDeclaration(sourceFromAnnotation, descriptor, context);
    }

    private final void checkDeclaration(KtAnnotationEntry annotationEntry, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ValueParameterDescriptor valueParameterDescriptor;
        BindingTrace trace = context.getTrace();
        if (descriptor instanceof CallableDescriptor) {
            CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
            DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingModule();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if ((classDescriptor != null ? classDescriptor.getKind() : null) == ClassKind.INTERFACE) {
                trace.report(ErrorsJvm.OVERLOADS_INTERFACE.on(annotationEntry));
                return;
            }
            if ((descriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) descriptor).getModality() == Modality.ABSTRACT) {
                trace.report(ErrorsJvm.OVERLOADS_ABSTRACT.on(annotationEntry));
                return;
            }
            if (DescriptorUtils.isLocal(descriptor)) {
                trace.report(ErrorsJvm.OVERLOADS_LOCAL.on(annotationEntry));
                return;
            }
            if (DescriptorUtilsKt.isAnnotationConstructor(descriptor)) {
                trace.report(ErrorsJvm.OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR.on(context.getLanguageVersionSettings(), annotationEntry));
                return;
            }
            if (!callableDescriptor.getVisibilityImpl().isPublicAPI() && !Intrinsics.areEqual(callableDescriptor.getVisibilityImpl(), DescriptorVisibilities.INTERNAL)) {
                trace.report(ErrorsJvm.OVERLOADS_PRIVATE.on(annotationEntry));
                return;
            }
            List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                do {
                    if (it.hasNext()) {
                        valueParameterDescriptor = (ValueParameterDescriptor) it.next();
                        if (valueParameterDescriptor.declaresDefaultValue()) {
                            break;
                        } else {
                            Intrinsics.checkNotNull(valueParameterDescriptor);
                        }
                    }
                } while (!ArgumentsUtilsKt.isActualParameterWithCorrespondingExpectedDefault(valueParameterDescriptor));
                if (descriptor instanceof SimpleFunctionDescriptor) {
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
                    if (InlineClassManglingRulesKt.requiresFunctionNameManglingForParameterTypes(callableMemberDescriptor) || InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(callableMemberDescriptor)) {
                        trace.report(ErrorsJvm.OVERLOADS_ANNOTATION_MANGLED_FUNCTION.on(annotationEntry));
                        return;
                    }
                }
                if ((descriptor instanceof ClassConstructorDescriptor) && InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters((CallableMemberDescriptor) descriptor)) {
                    trace.report(ErrorsJvm.OVERLOADS_ANNOTATION_HIDDEN_CONSTRUCTOR.on(annotationEntry));
                    return;
                }
                return;
            }
            trace.report(ErrorsJvm.OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS.on(annotationEntry));
        }
    }
}
