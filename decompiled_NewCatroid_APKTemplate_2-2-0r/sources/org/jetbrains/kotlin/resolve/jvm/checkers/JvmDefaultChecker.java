package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.LanguageVersionSettingsProvider;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.util.DeclarationUtilKt;

/* JADX INFO: compiled from: JvmDefaultChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J0\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0002J0\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0014\u0010\u001d\u001a\u00020\u0012*\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u0014H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmDefaultChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/config/JvmTarget;Lcom/intellij/openapi/project/Project;)V", "ideService", "Lorg/jetbrains/kotlin/resolve/LanguageVersionSettingsProvider;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkJvmCompatibilityAnnotations", "", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "checkPossibleClashMember", "inheritedFun", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "actualImplementation", "checkSpecializationInCompatibilityMode", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "performSpecializationCheck", "findPossibleClashMember", "isCompiledToJvmDefaultWithProperMode", "compilationDefaultMode", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultChecker implements DeclarationChecker {
    private final LanguageVersionSettingsProvider ideService;
    private final JvmTarget jvmTarget;

    public JvmDefaultChecker(JvmTarget jvmTarget, Project project) {
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        Intrinsics.checkNotNullParameter(project, "project");
        this.jvmTarget = jvmTarget;
        this.ideService = LanguageVersionSettingsProvider.INSTANCE.getInstance(project);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0067  */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        boolean z;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        JvmDefaultMode jvmDefaultMode = (JvmDefaultMode) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getJvmDefaultMode());
        if (checkJvmCompatibilityAnnotations(descriptor, declaration, context, jvmDefaultMode) || !jvmDefaultMode.isEnabled() || !(descriptor instanceof ClassDescriptor) || DescriptorUtils.isInterface(descriptor) || DescriptorUtils.isAnnotationClass(descriptor)) {
            return;
        }
        if (!jvmDefaultMode.isCompatibility() || JvmAnnotationUtilKt.hasJvmDefaultNoCompatibilityAnnotation(descriptor)) {
            z = false;
        } else {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if ((classDescriptor.getModality() == Modality.OPEN || classDescriptor.getModality() == Modality.ABSTRACT) && !DescriptorUtilsKt.isEffectivelyPrivateApi((DeclarationDescriptorWithVisibility) descriptor)) {
                z = true;
            } else {
                z = false;
            }
        }
        ClassDescriptor classDescriptor2 = (ClassDescriptor) descriptor;
        if (DescriptorUtilsKt.getSuperClassNotAny(classDescriptor2) != null || z) {
            Map<CallableMemberDescriptor, CallableMemberDescriptor> nonPrivateTraitMembersForDelegation = DeclarationUtilKt.getNonPrivateTraitMembersForDelegation(classDescriptor2, true);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<CallableMemberDescriptor, CallableMemberDescriptor> entry : nonPrivateTraitMembersForDelegation.entrySet()) {
                if (isCompiledToJvmDefaultWithProperMode(entry.getValue(), jvmDefaultMode)) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) entry2.getKey();
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) entry2.getValue();
                if ((callableMemberDescriptor2 instanceof FunctionDescriptor) && (callableMemberDescriptor instanceof FunctionDescriptor)) {
                    if (checkSpecializationInCompatibilityMode((FunctionDescriptor) callableMemberDescriptor, (FunctionDescriptor) callableMemberDescriptor2, context, declaration, z)) {
                        checkPossibleClashMember(callableMemberDescriptor, callableMemberDescriptor2, jvmDefaultMode, context, declaration);
                    }
                } else if ((callableMemberDescriptor2 instanceof PropertyDescriptor) && (callableMemberDescriptor instanceof PropertyDescriptor)) {
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableMemberDescriptor2;
                    PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                    PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) callableMemberDescriptor;
                    PropertyGetterDescriptor getter2 = propertyDescriptor2.getGetter();
                    if (getter == null || getter2 == null || !jvmDefaultMode.isCompatibility() || checkSpecializationInCompatibilityMode(getter2, getter, context, declaration, z)) {
                        if (propertyDescriptor.isVar() && propertyDescriptor2.isVar()) {
                            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                            PropertySetterDescriptor setter2 = propertyDescriptor2.getSetter();
                            if (setter == null || setter2 == null || checkSpecializationInCompatibilityMode(setter2, setter, context, declaration, z)) {
                            }
                        }
                        checkPossibleClashMember(callableMemberDescriptor, callableMemberDescriptor2, jvmDefaultMode, context, declaration);
                    }
                }
            }
        }
    }

    private final boolean checkJvmCompatibilityAnnotations(DeclarationDescriptor descriptor, KtDeclaration declaration, DeclarationCheckerContext context, JvmDefaultMode jvmDefaultMode) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(JvmNames.INSTANCE.getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME());
        if (annotationDescriptorMo6752findAnnotation != null) {
            KtAnnotationEntry sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorMo6752findAnnotation);
            KtDeclaration ktDeclaration = sourceFromAnnotation != null ? sourceFromAnnotation : declaration;
            if (!jvmDefaultMode.isEnabled()) {
                context.getTrace().report(ErrorsJvm.JVM_DEFAULT_IN_DECLARATION.on(ktDeclaration, "JvmDefaultWithoutCompatibility"));
                return true;
            }
        }
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation2 = descriptor.getAnnotations().mo6752findAnnotation(JvmAnnotationUtilKt.getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME());
        if (annotationDescriptorMo6752findAnnotation2 == null) {
            return false;
        }
        KtAnnotationEntry sourceFromAnnotation2 = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorMo6752findAnnotation2);
        KtDeclaration ktDeclaration2 = sourceFromAnnotation2 != null ? sourceFromAnnotation2 : declaration;
        if (jvmDefaultMode != JvmDefaultMode.ALL_INCOMPATIBLE) {
            context.getTrace().report(ErrorsJvm.JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION.on(ktDeclaration2));
            return true;
        }
        if (DescriptorUtils.isInterface(descriptor)) {
            return false;
        }
        context.getTrace().report(ErrorsJvm.JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE.on(ktDeclaration2));
        return true;
    }

    private final boolean checkSpecializationInCompatibilityMode(FunctionDescriptor inheritedFun, FunctionDescriptor actualImplementation, DeclarationCheckerContext context, KtDeclaration declaration, boolean performSpecializationCheck) {
        if (performSpecializationCheck && !(actualImplementation instanceof JavaMethodDescriptor)) {
            String strComputeJvmDescriptor = MethodSignatureMappingKt.computeJvmDescriptor(inheritedFun, true, false);
            FunctionDescriptor original = actualImplementation.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            if (!Intrinsics.areEqual(strComputeJvmDescriptor, MethodSignatureMappingKt.computeJvmDescriptor(original, true, false))) {
                context.getTrace().report(ErrorsJvm.EXPLICIT_OVERRIDE_REQUIRED_IN_COMPATIBILITY_MODE.on(declaration, DescriptorUtils.getDirectMember(inheritedFun), DescriptorUtils.getDirectMember(original)));
                return false;
            }
        }
        return true;
    }

    private final void checkPossibleClashMember(CallableMemberDescriptor inheritedFun, CallableMemberDescriptor actualImplementation, JvmDefaultMode jvmDefaultMode, DeclarationCheckerContext context, KtDeclaration declaration) {
        CallableMemberDescriptor callableMemberDescriptorFindPossibleClashMember = findPossibleClashMember(inheritedFun, jvmDefaultMode);
        if (callableMemberDescriptorFindPossibleClashMember != null) {
            context.getTrace().report(ErrorsJvm.EXPLICIT_OVERRIDE_REQUIRED_IN_MIXED_MODE.on(declaration, DescriptorUtils.getDirectMember(actualImplementation), DescriptorUtils.getDirectMember(callableMemberDescriptorFindPossibleClashMember), jvmDefaultMode.getDescription()));
        }
    }

    private final CallableMemberDescriptor findPossibleClashMember(CallableMemberDescriptor inheritedFun, JvmDefaultMode jvmDefaultMode) {
        Object next;
        DeclarationDescriptor containingDeclaration = inheritedFun.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!(containingDeclaration instanceof ClassDescriptor) || DescriptorUtilsKt.getSuperClassNotAny((ClassDescriptor) containingDeclaration) == null) {
            return null;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = inheritedFun.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : overriddenDescriptors) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
            if (!DescriptorUtils.isInterface(callableMemberDescriptor.getContainingModule()) && !DescriptorUtils.isAnnotationClass(callableMemberDescriptor.getContainingModule())) {
                arrayList.add(obj);
            }
        }
        ArrayList<CallableMemberDescriptor> arrayList2 = arrayList;
        Iterator it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) next;
            if (!(callableMemberDescriptor2 instanceof JavaCallableMemberDescriptor)) {
                Intrinsics.checkNotNull(callableMemberDescriptor2);
                CallableMemberDescriptor nonPrivateTraitMembersForDelegation = DeclarationUtilKt.getNonPrivateTraitMembersForDelegation(callableMemberDescriptor2, true);
                if (nonPrivateTraitMembersForDelegation != null && !isCompiledToJvmDefaultWithProperMode(nonPrivateTraitMembersForDelegation, jvmDefaultMode)) {
                    break;
                }
            }
        }
        CallableMemberDescriptor callableMemberDescriptor3 = (CallableMemberDescriptor) next;
        if (callableMemberDescriptor3 != null) {
            return callableMemberDescriptor3;
        }
        for (CallableMemberDescriptor callableMemberDescriptor4 : arrayList2) {
            Intrinsics.checkNotNull(callableMemberDescriptor4);
            CallableMemberDescriptor callableMemberDescriptorFindPossibleClashMember = findPossibleClashMember(callableMemberDescriptor4, jvmDefaultMode);
            if (callableMemberDescriptorFindPossibleClashMember != null) {
                return callableMemberDescriptorFindPossibleClashMember;
            }
        }
        return null;
    }

    private final boolean isCompiledToJvmDefaultWithProperMode(CallableMemberDescriptor callableMemberDescriptor, JvmDefaultMode jvmDefaultMode) {
        return JvmDefaultCheckerKt.isCompiledToJvmDefaultWithProperMode(callableMemberDescriptor, this.ideService, jvmDefaultMode);
    }
}
