package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: JvmPropertyVsFieldAmbiguityCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\f*\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000fH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmPropertyVsFieldAmbiguityCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "selfOrBaseForFakeOverride", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "predicate", "Lkotlin/Function1;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPropertyVsFieldAmbiguityCallChecker implements CallChecker {
    public static final JvmPropertyVsFieldAmbiguityCallChecker INSTANCE = new JvmPropertyVsFieldAmbiguityCallChecker();

    private JvmPropertyVsFieldAmbiguityCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        DiagnosticFactory2<PsiElement, String, String> diagnosticFactory2;
        String strAsString;
        FqName fqNameSafe;
        String strAsString2;
        FqName fqNameSafe2;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.PreferJavaFieldOverload)) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            PropertyDescriptor propertyDescriptor = resultingDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) resultingDescriptor : null;
            if (propertyDescriptor != null && JavaDescriptorUtilKt.isJavaField(propertyDescriptor)) {
                DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                if (classDescriptor == null) {
                    return;
                }
                CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride(propertyDescriptor);
                Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorUnwrapFakeOverride, "unwrapFakeOverride(...)");
                DeclarationDescriptor containingDeclaration2 = ((PropertyDescriptor) callableMemberDescriptorUnwrapFakeOverride).getContainingModule();
                ClassDescriptor classDescriptor2 = containingDeclaration2 instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration2 : null;
                if (classDescriptor2 == classDescriptor) {
                    return;
                }
                MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
                Name name2 = propertyDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                for (PropertyDescriptor propertyDescriptor2 : unsubstitutedMemberScope.getContributedVariables(name2, NoLookupLocation.FOR_ALREADY_TRACKED)) {
                    if (propertyDescriptor2 != propertyDescriptor) {
                        CallableMemberDescriptor callableMemberDescriptorUnwrapSubstitutionOverride = DescriptorUtils.unwrapSubstitutionOverride(propertyDescriptor2);
                        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorUnwrapSubstitutionOverride, "unwrapSubstitutionOverride(...)");
                        PropertyDescriptor propertyDescriptor3 = (PropertyDescriptor) callableMemberDescriptorUnwrapSubstitutionOverride;
                        DeclarationDescriptor containingDeclaration3 = propertyDescriptor3.getContainingModule();
                        ClassDescriptor classDescriptor3 = containingDeclaration3 instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration3 : null;
                        JvmPropertyVsFieldAmbiguityCallChecker jvmPropertyVsFieldAmbiguityCallChecker = INSTANCE;
                        boolean zSelfOrBaseForFakeOverride = jvmPropertyVsFieldAmbiguityCallChecker.selfOrBaseForFakeOverride(propertyDescriptor3, new Function1<PropertyDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmPropertyVsFieldAmbiguityCallChecker$check$1$hasLateInit$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(PropertyDescriptor it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return Boolean.valueOf(it.isLateInit());
                            }
                        });
                        boolean zSelfOrBaseForFakeOverride2 = jvmPropertyVsFieldAmbiguityCallChecker.selfOrBaseForFakeOverride(propertyDescriptor3, new Function1<PropertyDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmPropertyVsFieldAmbiguityCallChecker$check$1$hasCustomGetter$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(PropertyDescriptor it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                PropertyGetterDescriptor getter = it.getGetter();
                                boolean z = false;
                                if (getter != null && !getter.isDefault()) {
                                    z = true;
                                }
                                return Boolean.valueOf(z);
                            }
                        });
                        boolean zSelfOrBaseForFakeOverride3 = jvmPropertyVsFieldAmbiguityCallChecker.selfOrBaseForFakeOverride(propertyDescriptor3, new Function1<PropertyDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmPropertyVsFieldAmbiguityCallChecker$check$1$hasCustomSetter$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(PropertyDescriptor it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                PropertySetterDescriptor setter = it.getSetter();
                                boolean z = false;
                                if (setter != null && !setter.isDefault()) {
                                    z = true;
                                }
                                return Boolean.valueOf(z);
                            }
                        });
                        String strComputeJvmDescriptorWithoutName = MethodSignatureMappingKt.computeJvmDescriptorWithoutName(propertyDescriptor);
                        String strComputeJvmDescriptorWithoutName2 = MethodSignatureMappingKt.computeJvmDescriptorWithoutName(propertyDescriptor2);
                        if (zSelfOrBaseForFakeOverride || zSelfOrBaseForFakeOverride2 || zSelfOrBaseForFakeOverride3 || propertyDescriptor3.getModality() != Modality.FINAL || !Intrinsics.areEqual(strComputeJvmDescriptorWithoutName, strComputeJvmDescriptorWithoutName2)) {
                            if (classDescriptor2 == null || classDescriptor3 == null || !DescriptorUtils.isSubclass(classDescriptor2, classDescriptor3)) {
                                if (DescriptorVisibilities.isVisible(resolvedCall.getDispatchReceiver(), propertyDescriptor2, context.getScope().getOwnerDescriptor(), false)) {
                                    if (zSelfOrBaseForFakeOverride2) {
                                        diagnosticFactory2 = ErrorsJvm.BASE_CLASS_FIELD_SHADOWS_DERIVED_CLASS_PROPERTY;
                                    } else if (zSelfOrBaseForFakeOverride || zSelfOrBaseForFakeOverride3) {
                                        diagnosticFactory2 = ErrorsJvm.BACKING_FIELD_ACCESSED_DUE_TO_PROPERTY_FIELD_CONFLICT;
                                    } else {
                                        diagnosticFactory2 = propertyDescriptor3.getModality() != Modality.FINAL ? ErrorsJvm.BASE_CLASS_FIELD_MAY_SHADOW_DERIVED_CLASS_PROPERTY : ErrorsJvm.BASE_CLASS_FIELD_WITH_DIFFERENT_SIGNATURE_THAN_DERIVED_CLASS_PROPERTY;
                                    }
                                    BindingTrace trace = context.getTrace();
                                    String str = "unknown class";
                                    if (classDescriptor2 == null || (fqNameSafe2 = DescriptorUtilsKt.getFqNameSafe(classDescriptor2)) == null || (strAsString = fqNameSafe2.asString()) == null) {
                                        strAsString = "unknown class";
                                    }
                                    if (classDescriptor3 != null && (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(classDescriptor3)) != null && (strAsString2 = fqNameSafe.asString()) != null) {
                                        str = strAsString2;
                                    }
                                    trace.report(diagnosticFactory2.on(reportOn, strAsString, str));
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private final boolean selfOrBaseForFakeOverride(PropertyDescriptor propertyDescriptor, Function1<? super PropertyDescriptor, Boolean> function1) {
        if (function1.invoke(propertyDescriptor).booleanValue()) {
            return true;
        }
        if (propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return false;
        }
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        if (!collection.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor2 : collection) {
                JvmPropertyVsFieldAmbiguityCallChecker jvmPropertyVsFieldAmbiguityCallChecker = INSTANCE;
                Intrinsics.checkNotNull(propertyDescriptor2);
                if (jvmPropertyVsFieldAmbiguityCallChecker.selfOrBaseForFakeOverride(propertyDescriptor2, function1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
