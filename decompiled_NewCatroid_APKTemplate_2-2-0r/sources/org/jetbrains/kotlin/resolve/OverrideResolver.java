package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.SmartHashSet;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryWithPsiElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.rendering.DeclarationWithDiagnosticComponents;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.IndexedParametersSubstitution;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerImpl;

/* JADX INFO: compiled from: OverrideResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 22\u00020\u0001:\u0007,-./012B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J \u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u0018H\u0002J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u0018H\u0002J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0018H\u0002J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0018H\u0002J\u0018\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010(\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0018H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "overridesBackwardCompatibilityHelper", "Lorg/jetbrains/kotlin/resolve/OverridesBackwardCompatibilityHelper;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "platformSpecificDiagnosticComponents", "Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/OverridesBackwardCompatibilityHelper;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;)V", "check", "", "c", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "checkNameAndDefaultForDeclaredParameter", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "multipleDefaultsInSuper", "", "checkNameAndDefaultForFakeOverrideParameter", "containingFunction", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "checkOverrideForComponentFunction", "componentFunction", "checkOverrideForCopyFunction", "copyFunction", "checkOverrideForMember", "declared", "checkOverrides", "checkOverridesForParameters", "checkOverridesInAClass", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "klass", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "checkParameterOverridesForAllClasses", "checkVisibility", "checkVisibilityForMember", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "memberDescriptor", "CheckInheritedSignaturesReportStrategy", "CheckOverrideReportForDeclaredMemberStrategy", "CheckOverrideReportStrategy", "CollectErrorInformationForInheritedMembersStrategy", "CollectMissingImplementationsStrategy", "CollectWarningInformationForInheritedMembersStrategy", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverrideResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;
    private final OverridesBackwardCompatibilityHelper overridesBackwardCompatibilityHelper;
    private final PlatformSpecificDiagnosticComponents platformSpecificDiagnosticComponents;
    private final BindingTrace trace;

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\bb\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckInheritedSignaturesReportStrategy;", "", "abstractBaseClassMemberNotImplemented", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "abstractInvisibleMember", "abstractMemberNotImplemented", "abstractMemberWithMoreSpecificType", "abstractMember", "concreteMember", "conflictingInterfaceMemberNotImplemented", "multipleImplementationsMemberNotImplemented", "typeMismatchOnInheritance", "descriptor1", "descriptor2", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface CheckInheritedSignaturesReportStrategy {
        void abstractBaseClassMemberNotImplemented(CallableMemberDescriptor descriptor);

        void abstractInvisibleMember(CallableMemberDescriptor descriptor);

        void abstractMemberNotImplemented(CallableMemberDescriptor descriptor);

        void abstractMemberWithMoreSpecificType(CallableMemberDescriptor abstractMember, CallableMemberDescriptor concreteMember);

        void conflictingInterfaceMemberNotImplemented(CallableMemberDescriptor descriptor);

        void multipleImplementationsMemberNotImplemented(CallableMemberDescriptor descriptor);

        void typeMismatchOnInheritance(CallableMemberDescriptor descriptor1, CallableMemberDescriptor descriptor2);
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bb\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportForDeclaredMemberStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportStrategy;", "cannotOverrideInvisibleMember", "", "overriding", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "invisibleOverridden", "nothingToOverride", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface CheckOverrideReportForDeclaredMemberStrategy extends CheckOverrideReportStrategy {
        void cannotOverrideInvisibleMember(CallableMemberDescriptor overriding, CallableMemberDescriptor invisibleOverridden);

        void nothingToOverride(CallableMemberDescriptor overriding);
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bb\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportStrategy;", "", "overridingFinalMember", "", "overriding", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "overridden", "returnTypeMismatchOnOverride", "varOverriddenByVal", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface CheckOverrideReportStrategy {
        void overridingFinalMember(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden);

        void returnTypeMismatchOnOverride(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden);

        void varOverriddenByVal(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden);
    }

    public OverrideResolver(BindingTrace trace, OverridesBackwardCompatibilityHelper overridesBackwardCompatibilityHelper, LanguageVersionSettings languageVersionSettings, KotlinTypeRefiner kotlinTypeRefiner, PlatformSpecificDiagnosticComponents platformSpecificDiagnosticComponents) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(overridesBackwardCompatibilityHelper, "overridesBackwardCompatibilityHelper");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(platformSpecificDiagnosticComponents, "platformSpecificDiagnosticComponents");
        this.trace = trace;
        this.overridesBackwardCompatibilityHelper = overridesBackwardCompatibilityHelper;
        this.languageVersionSettings = languageVersionSettings;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.platformSpecificDiagnosticComponents = platformSpecificDiagnosticComponents;
    }

    public final void check(TopDownAnalysisContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        checkVisibility(c);
        checkOverrides(c);
        checkParameterOverridesForAllClasses(c);
    }

    private final void checkOverrides(TopDownAnalysisContext c) {
        Map<KtClassOrObject, ClassDescriptorWithResolutionScopes> declaredClasses = c.getDeclaredClasses();
        Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
        for (Map.Entry<KtClassOrObject, ClassDescriptorWithResolutionScopes> entry : declaredClasses.entrySet()) {
            KtClassOrObject key = entry.getKey();
            ClassDescriptorWithResolutionScopes value = entry.getValue();
            Intrinsics.checkNotNull(value);
            Intrinsics.checkNotNull(key);
            checkOverridesInAClass(value, key);
        }
    }

    private final void checkOverridesInAClass(ClassDescriptorWithResolutionScopes classDescriptor, KtClassOrObject klass) {
        for (CallableMemberDescriptor callableMemberDescriptor : classDescriptor.getDeclaredCallableMembers()) {
            Intrinsics.checkNotNull(callableMemberDescriptor);
            checkOverrideForMember(callableMemberDescriptor);
        }
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = classDescriptor;
        CollectErrorInformationForInheritedMembersStrategy collectErrorInformationForInheritedMembersStrategy = new CollectErrorInformationForInheritedMembersStrategy(this, klass, classDescriptorWithResolutionScopes);
        INSTANCE.checkInheritedAndDelegatedSignatures(classDescriptorWithResolutionScopes, collectErrorInformationForInheritedMembersStrategy, collectErrorInformationForInheritedMembersStrategy, this.kotlinTypeRefiner);
        collectErrorInformationForInheritedMembersStrategy.doReportErrors();
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectMissingImplementationsStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckInheritedSignaturesReportStrategy;", "()V", "shouldImplement", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getShouldImplement", "()Ljava/util/LinkedHashSet;", "abstractBaseClassMemberNotImplemented", "", "descriptor", "abstractInvisibleMember", "abstractMemberNotImplemented", "abstractMemberWithMoreSpecificType", "abstractMember", "concreteMember", "conflictingInterfaceMemberNotImplemented", "multipleImplementationsMemberNotImplemented", "typeMismatchOnInheritance", "descriptor1", "descriptor2", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CollectMissingImplementationsStrategy implements CheckInheritedSignaturesReportStrategy {
        private final LinkedHashSet<CallableMemberDescriptor> shouldImplement = new LinkedHashSet<>();

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractBaseClassMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractInvisibleMember(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void typeMismatchOnInheritance(CallableMemberDescriptor descriptor1, CallableMemberDescriptor descriptor2) {
            Intrinsics.checkNotNullParameter(descriptor1, "descriptor1");
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor2");
        }

        public final LinkedHashSet<CallableMemberDescriptor> getShouldImplement() {
            return this.shouldImplement;
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.shouldImplement.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void multipleImplementationsMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.shouldImplement.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void conflictingInterfaceMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (descriptor.getModality() == Modality.ABSTRACT) {
                this.shouldImplement.add(descriptor);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractMemberWithMoreSpecificType(CallableMemberDescriptor abstractMember, CallableMemberDescriptor concreteMember) {
            Intrinsics.checkNotNullParameter(abstractMember, "abstractMember");
            Intrinsics.checkNotNullParameter(concreteMember, "concreteMember");
            this.shouldImplement.add(abstractMember);
        }
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0013\b\u0016\u0012\n\u0010\u0003\u001a\u00060\u0001R\u00020\u0002¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectWarningInformationForInheritedMembersStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectErrorInformationForInheritedMembersStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver;", "delegateStrategy", "(Lorg/jetbrains/kotlin/resolve/OverrideResolver;Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectErrorInformationForInheritedMembersStrategy;)V", "klass", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/resolve/OverrideResolver;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "doReportErrors", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CollectWarningInformationForInheritedMembersStrategy extends CollectErrorInformationForInheritedMembersStrategy {
        final /* synthetic */ OverrideResolver this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CollectWarningInformationForInheritedMembersStrategy(OverrideResolver overrideResolver, KtClassOrObject klass, ClassDescriptor classDescriptor) {
            super(overrideResolver, klass, classDescriptor);
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            this.this$0 = overrideResolver;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public CollectWarningInformationForInheritedMembersStrategy(OverrideResolver overrideResolver, CollectErrorInformationForInheritedMembersStrategy delegateStrategy) {
            this(overrideResolver, delegateStrategy.getKlass(), delegateStrategy.getClassDescriptor());
            Intrinsics.checkNotNullParameter(delegateStrategy, "delegateStrategy");
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CollectErrorInformationForInheritedMembersStrategy
        public void doReportErrors() {
            boolean zClassCanHaveAbstractFakeOverride = DescriptorUtils.classCanHaveAbstractFakeOverride(getClassDescriptor());
            if (!getAbstractInBaseClassNoImpl().isEmpty() && !zClassCanHaveAbstractFakeOverride) {
                if (this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.AbstractClassMemberNotImplementedWithIntermediateAbstractClass)) {
                    this.this$0.trace.report(Errors.ABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED.on(getKlass(), getKlass(), (CallableMemberDescriptor) CollectionsKt.first(getAbstractInBaseClassNoImpl())));
                } else {
                    this.this$0.trace.report(Errors.ABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED_WARNING.on(getKlass(), getKlass(), (CallableMemberDescriptor) CollectionsKt.first(getAbstractInBaseClassNoImpl())));
                }
            }
            if (getConflictingInterfaceMembers().isEmpty()) {
                return;
            }
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.first(getConflictingInterfaceMembers());
            if (this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.AbstractClassMemberNotImplementedWithIntermediateAbstractClass)) {
                this.this$0.trace.report(Errors.MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED.on(getKlass(), getKlass(), callableMemberDescriptor));
            } else {
                this.this$0.trace.report(Errors.MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED_WARNING.on(getKlass(), getKlass(), callableMemberDescriptor));
            }
        }
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\nH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\nH\u0016J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\nH\u0016J\u0018\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\nH\u0016J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\nH\u0016J\b\u0010$\u001a\u00020\u001cH\u0016J\u0010\u0010%\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\nH\u0016J\u0018\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0016JD\u0010)\u001a\u00020\u001c2\u0018\u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0+2\u0010\u0010,\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010-\u001a\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0002J2\u0010.\u001a\u00020\u001c2\u0018\u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0+2\u0006\u0010/\u001a\u00020\n2\u0006\u00100\u001a\u00020\nH\u0002J\u0018\u00101\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0016J\n\u00102\u001a\u000603R\u000204J\u0018\u00105\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\n2\u0006\u00100\u001a\u00020\nH\u0016J\u0018\u00106\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0016R$\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u001e\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001a0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectErrorInformationForInheritedMembersStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckInheritedSignaturesReportStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportStrategy;", "klass", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/resolve/OverrideResolver;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "abstractInBaseClassNoImpl", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lkotlin/collections/LinkedHashSet;", "getAbstractInBaseClassNoImpl", "()Ljava/util/LinkedHashSet;", "abstractInvisibleSuper", "abstractNoImpl", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "conflictingInterfaceMembers", "getConflictingInterfaceMembers", "conflictingReturnTypes", "getKlass", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "multipleImplementations", "onceErrorsReported", "Lorg/jetbrains/kotlin/com/intellij/util/containers/SmartHashSet;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "abstractBaseClassMemberNotImplemented", "", "descriptor", "abstractInvisibleMember", "abstractMemberNotImplemented", "abstractMemberWithMoreSpecificType", "abstractMember", "concreteMember", "conflictingInterfaceMemberNotImplemented", "doReportErrors", "multipleImplementationsMemberNotImplemented", "overridingFinalMember", "overriding", "overridden", "reportDelegationProblemIfRequired", "diagnosticFactory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "relevantDiagnosticFromInheritance", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "reportInheritanceConflictIfRequired", "descriptor1", "descriptor2", "returnTypeMismatchOnOverride", "toDeprecationStrategy", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CollectWarningInformationForInheritedMembersStrategy;", "Lorg/jetbrains/kotlin/resolve/OverrideResolver;", "typeMismatchOnInheritance", "varOverriddenByVal", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private class CollectErrorInformationForInheritedMembersStrategy implements CheckInheritedSignaturesReportStrategy, CheckOverrideReportStrategy {
        private final LinkedHashSet<CallableMemberDescriptor> abstractInBaseClassNoImpl;
        private final LinkedHashSet<CallableMemberDescriptor> abstractInvisibleSuper;
        private final LinkedHashSet<CallableMemberDescriptor> abstractNoImpl;
        private final ClassDescriptor classDescriptor;
        private final LinkedHashSet<CallableMemberDescriptor> conflictingInterfaceMembers;
        private final LinkedHashSet<CallableMemberDescriptor> conflictingReturnTypes;
        private final KtClassOrObject klass;
        private final LinkedHashSet<CallableMemberDescriptor> multipleImplementations;
        private final SmartHashSet<DiagnosticFactoryWithPsiElement<?, ?>> onceErrorsReported;
        final /* synthetic */ OverrideResolver this$0;

        public CollectErrorInformationForInheritedMembersStrategy(OverrideResolver overrideResolver, KtClassOrObject klass, ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            this.this$0 = overrideResolver;
            this.klass = klass;
            this.classDescriptor = classDescriptor;
            this.abstractNoImpl = new LinkedHashSet<>();
            this.abstractInBaseClassNoImpl = new LinkedHashSet<>();
            this.abstractInvisibleSuper = new LinkedHashSet<>();
            this.multipleImplementations = new LinkedHashSet<>();
            this.conflictingInterfaceMembers = new LinkedHashSet<>();
            this.conflictingReturnTypes = new LinkedHashSet<>();
            this.onceErrorsReported = new SmartHashSet<>();
        }

        public final KtClassOrObject getKlass() {
            return this.klass;
        }

        public final ClassDescriptor getClassDescriptor() {
            return this.classDescriptor;
        }

        protected final LinkedHashSet<CallableMemberDescriptor> getAbstractInBaseClassNoImpl() {
            return this.abstractInBaseClassNoImpl;
        }

        protected final LinkedHashSet<CallableMemberDescriptor> getConflictingInterfaceMembers() {
            return this.conflictingInterfaceMembers;
        }

        public final CollectWarningInformationForInheritedMembersStrategy toDeprecationStrategy() {
            return new CollectWarningInformationForInheritedMembersStrategy(this.this$0, this);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.abstractNoImpl.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractBaseClassMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.abstractInBaseClassNoImpl.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void multipleImplementationsMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.multipleImplementations.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void conflictingInterfaceMemberNotImplemented(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.conflictingInterfaceMembers.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void typeMismatchOnInheritance(CallableMemberDescriptor descriptor1, CallableMemberDescriptor descriptor2) {
            Intrinsics.checkNotNullParameter(descriptor1, "descriptor1");
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor2");
            this.conflictingReturnTypes.add(descriptor1);
            this.conflictingReturnTypes.add(descriptor2);
            if ((descriptor1 instanceof PropertyDescriptor) && (descriptor2 instanceof PropertyDescriptor)) {
                if (((PropertyDescriptor) descriptor1).isVar() || ((PropertyDescriptor) descriptor2).isVar()) {
                    DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> VAR_TYPE_MISMATCH_ON_INHERITANCE = Errors.VAR_TYPE_MISMATCH_ON_INHERITANCE;
                    Intrinsics.checkNotNullExpressionValue(VAR_TYPE_MISMATCH_ON_INHERITANCE, "VAR_TYPE_MISMATCH_ON_INHERITANCE");
                    reportInheritanceConflictIfRequired(VAR_TYPE_MISMATCH_ON_INHERITANCE, descriptor1, descriptor2);
                    return;
                } else {
                    DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> PROPERTY_TYPE_MISMATCH_ON_INHERITANCE = Errors.PROPERTY_TYPE_MISMATCH_ON_INHERITANCE;
                    Intrinsics.checkNotNullExpressionValue(PROPERTY_TYPE_MISMATCH_ON_INHERITANCE, "PROPERTY_TYPE_MISMATCH_ON_INHERITANCE");
                    reportInheritanceConflictIfRequired(PROPERTY_TYPE_MISMATCH_ON_INHERITANCE, descriptor1, descriptor2);
                    return;
                }
            }
            DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> RETURN_TYPE_MISMATCH_ON_INHERITANCE = Errors.RETURN_TYPE_MISMATCH_ON_INHERITANCE;
            Intrinsics.checkNotNullExpressionValue(RETURN_TYPE_MISMATCH_ON_INHERITANCE, "RETURN_TYPE_MISMATCH_ON_INHERITANCE");
            reportInheritanceConflictIfRequired(RETURN_TYPE_MISMATCH_ON_INHERITANCE, descriptor1, descriptor2);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractInvisibleMember(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.abstractInvisibleSuper.add(descriptor);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckInheritedSignaturesReportStrategy
        public void abstractMemberWithMoreSpecificType(CallableMemberDescriptor abstractMember, CallableMemberDescriptor concreteMember) {
            Intrinsics.checkNotNullParameter(abstractMember, "abstractMember");
            Intrinsics.checkNotNullParameter(concreteMember, "concreteMember");
            typeMismatchOnInheritance(abstractMember, concreteMember);
        }

        private final void reportInheritanceConflictIfRequired(DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> diagnosticFactory, CallableMemberDescriptor descriptor1, CallableMemberDescriptor descriptor2) {
            if (this.onceErrorsReported.contains(diagnosticFactory)) {
                return;
            }
            this.onceErrorsReported.add(diagnosticFactory);
            this.this$0.trace.report(diagnosticFactory.on(this.klass, descriptor1, descriptor2));
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
        public void overridingFinalMember(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
            Intrinsics.checkNotNullParameter(overriding, "overriding");
            Intrinsics.checkNotNullParameter(overridden, "overridden");
            DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> OVERRIDING_FINAL_MEMBER_BY_DELEGATION = Errors.OVERRIDING_FINAL_MEMBER_BY_DELEGATION;
            Intrinsics.checkNotNullExpressionValue(OVERRIDING_FINAL_MEMBER_BY_DELEGATION, "OVERRIDING_FINAL_MEMBER_BY_DELEGATION");
            reportDelegationProblemIfRequired(OVERRIDING_FINAL_MEMBER_BY_DELEGATION, null, overriding, overridden);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
        public void returnTypeMismatchOnOverride(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
            Pair pair;
            Intrinsics.checkNotNullParameter(overriding, "overriding");
            Intrinsics.checkNotNullParameter(overridden, "overridden");
            if (overridden instanceof PropertyDescriptor) {
                pair = TuplesKt.to(Errors.PROPERTY_TYPE_MISMATCH_BY_DELEGATION, Errors.PROPERTY_TYPE_MISMATCH_ON_INHERITANCE);
            } else {
                pair = TuplesKt.to(Errors.RETURN_TYPE_MISMATCH_BY_DELEGATION, Errors.RETURN_TYPE_MISMATCH_ON_INHERITANCE);
            }
            DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> diagnosticFactory2 = (DiagnosticFactory2) pair.component1();
            DiagnosticFactory2 diagnosticFactory3 = (DiagnosticFactory2) pair.component2();
            Intrinsics.checkNotNull(diagnosticFactory2);
            reportDelegationProblemIfRequired(diagnosticFactory2, diagnosticFactory3, overriding, overridden);
        }

        @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
        public void varOverriddenByVal(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
            Intrinsics.checkNotNullParameter(overriding, "overriding");
            Intrinsics.checkNotNullParameter(overridden, "overridden");
            DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION = Errors.VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION;
            Intrinsics.checkNotNullExpressionValue(VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION, "VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION");
            reportDelegationProblemIfRequired(VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION, null, overriding, overridden);
        }

        private final void reportDelegationProblemIfRequired(DiagnosticFactory2<KtClassOrObject, CallableMemberDescriptor, CallableMemberDescriptor> diagnosticFactory, DiagnosticFactoryWithPsiElement<?, ?> relevantDiagnosticFromInheritance, CallableMemberDescriptor delegate, CallableMemberDescriptor overridden) {
            delegate.getKind();
            CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.DELEGATION;
            if (this.onceErrorsReported.contains(diagnosticFactory)) {
                return;
            }
            if (relevantDiagnosticFromInheritance == null || !this.onceErrorsReported.contains(relevantDiagnosticFromInheritance)) {
                this.onceErrorsReported.add(diagnosticFactory);
                this.this$0.trace.report(diagnosticFactory.on(this.klass, delegate, overridden));
            }
        }

        public void doReportErrors() {
            boolean zClassCanHaveAbstractFakeOverride = DescriptorUtils.classCanHaveAbstractFakeOverride(this.classDescriptor);
            if (!this.abstractInBaseClassNoImpl.isEmpty() && !zClassCanHaveAbstractFakeOverride) {
                BindingTrace bindingTrace = this.this$0.trace;
                DiagnosticFactory2<KtClassOrObject, KtClassOrObject, CallableMemberDescriptor> diagnosticFactory2 = Errors.ABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED;
                KtClassOrObject ktClassOrObject = this.klass;
                bindingTrace.report(diagnosticFactory2.on(ktClassOrObject, ktClassOrObject, (CallableMemberDescriptor) CollectionsKt.first(this.abstractInBaseClassNoImpl)));
            } else if (!this.abstractNoImpl.isEmpty() && !zClassCanHaveAbstractFakeOverride) {
                BindingTrace bindingTrace2 = this.this$0.trace;
                DiagnosticFactory2<KtClassOrObject, KtClassOrObject, CallableMemberDescriptor> diagnosticFactory3 = Errors.ABSTRACT_MEMBER_NOT_IMPLEMENTED;
                KtClassOrObject ktClassOrObject2 = this.klass;
                bindingTrace2.report(diagnosticFactory3.on(ktClassOrObject2, ktClassOrObject2, (CallableMemberDescriptor) CollectionsKt.first(this.abstractNoImpl)));
            }
            if (!this.abstractInvisibleSuper.isEmpty() && !zClassCanHaveAbstractFakeOverride) {
                this.this$0.trace.report(Errors.INVISIBLE_ABSTRACT_MEMBER_FROM_SUPER.on(this.this$0.languageVersionSettings, this.klass, this.classDescriptor, this.abstractInvisibleSuper));
            }
            this.conflictingInterfaceMembers.removeAll(this.conflictingReturnTypes);
            this.multipleImplementations.removeAll(this.conflictingReturnTypes);
            if (!this.conflictingInterfaceMembers.isEmpty()) {
                BindingTrace bindingTrace3 = this.this$0.trace;
                DiagnosticFactory2<KtClassOrObject, KtClassOrObject, CallableMemberDescriptor> diagnosticFactory4 = Errors.MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED;
                KtClassOrObject ktClassOrObject3 = this.klass;
                bindingTrace3.report(diagnosticFactory4.on(ktClassOrObject3, ktClassOrObject3, (CallableMemberDescriptor) CollectionsKt.first(this.conflictingInterfaceMembers)));
                return;
            }
            if (this.multipleImplementations.isEmpty()) {
                return;
            }
            BindingTrace bindingTrace4 = this.this$0.trace;
            DiagnosticFactory2<KtClassOrObject, KtClassOrObject, CallableMemberDescriptor> diagnosticFactory5 = Errors.MANY_IMPL_MEMBER_NOT_IMPLEMENTED;
            KtClassOrObject ktClassOrObject4 = this.klass;
            bindingTrace4.report(diagnosticFactory5.on(ktClassOrObject4, ktClassOrObject4, (CallableMemberDescriptor) CollectionsKt.first(this.multipleImplementations)));
        }
    }

    private final void checkOverrideForMember(final CallableMemberDescriptor declared) {
        if (declared.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED) {
            DataClassDescriptorResolver dataClassDescriptorResolver = DataClassDescriptorResolver.INSTANCE;
            Name name2 = declared.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (dataClassDescriptorResolver.isComponentLike(name2)) {
                checkOverrideForComponentFunction(declared);
                return;
            } else {
                if (Intrinsics.areEqual(declared.getShortName(), DataClassDescriptorResolver.INSTANCE.getCOPY_METHOD_NAME())) {
                    checkOverrideForCopyFunction(declared);
                    return;
                }
                return;
            }
        }
        if (declared.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            return;
        }
        final KtNamedDeclaration ktNamedDeclaration = (KtNamedDeclaration) DescriptorToSourceUtils.descriptorToDeclaration(declared);
        if (ktNamedDeclaration == null) {
            throw new IllegalStateException("declared descriptor is not resolved to declaration: " + declared);
        }
        KtModifierList modifierList = ktNamedDeclaration.getModifierList();
        boolean z = modifierList != null && modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = declared.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        if (!z) {
            if (overriddenDescriptors.isEmpty() || this.overridesBackwardCompatibilityHelper.overrideCanBeOmitted(declared)) {
                return;
            }
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.first(overriddenDescriptors);
            this.trace.report(Errors.VIRTUAL_MEMBER_HIDDEN.on(ktNamedDeclaration, declared, callableMemberDescriptor, callableMemberDescriptor.getContainingDeclaration()));
            return;
        }
        INSTANCE.checkOverridesForMemberMarkedOverride(declared, this.kotlinTypeRefiner, new CheckOverrideReportForDeclaredMemberStrategy() { // from class: org.jetbrains.kotlin.resolve.OverrideResolver.checkOverrideForMember.1
            private boolean finalOverriddenError;
            private boolean kindMismatchError;
            private boolean typeMismatchError;

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void overridingFinalMember(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (this.finalOverriddenError) {
                    return;
                }
                this.finalOverriddenError = true;
                OverrideResolver.this.trace.report(Errors.OVERRIDING_FINAL_MEMBER.on(ktNamedDeclaration, overridden, overridden.getContainingDeclaration()));
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void returnTypeMismatchOnOverride(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (this.typeMismatchError) {
                    return;
                }
                this.typeMismatchError = true;
                boolean z2 = overridden instanceof PropertyDescriptor;
                if (z2 && ((PropertyDescriptor) overridden).isVar()) {
                    OverrideResolver.this.trace.report(Errors.VAR_TYPE_MISMATCH_ON_OVERRIDE.on(ktNamedDeclaration, declared, overridden));
                } else if (z2 && !((PropertyDescriptor) overridden).isVar()) {
                    OverrideResolver.this.trace.report(Errors.PROPERTY_TYPE_MISMATCH_ON_OVERRIDE.on(ktNamedDeclaration, declared, overridden));
                } else {
                    OverrideResolver.this.trace.report(Errors.RETURN_TYPE_MISMATCH_ON_OVERRIDE.on(ktNamedDeclaration, declared, new DeclarationWithDiagnosticComponents(overridden, OverrideResolver.this.platformSpecificDiagnosticComponents)));
                }
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void varOverriddenByVal(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (this.kindMismatchError) {
                    return;
                }
                this.kindMismatchError = true;
                BindingTrace bindingTrace = OverrideResolver.this.trace;
                DiagnosticFactory2<KtNamedDeclaration, PropertyDescriptor, PropertyDescriptor> diagnosticFactory2 = Errors.VAR_OVERRIDDEN_BY_VAL;
                KtNamedDeclaration ktNamedDeclaration2 = ktNamedDeclaration;
                CallableMemberDescriptor callableMemberDescriptor2 = declared;
                Intrinsics.checkNotNull(callableMemberDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                bindingTrace.report(diagnosticFactory2.on(ktNamedDeclaration2, (PropertyDescriptor) callableMemberDescriptor2, (PropertyDescriptor) overridden));
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportForDeclaredMemberStrategy
            public void cannotOverrideInvisibleMember(CallableMemberDescriptor overriding, CallableMemberDescriptor invisibleOverridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(invisibleOverridden, "invisibleOverridden");
                OverrideResolver.this.trace.report(Errors.CANNOT_OVERRIDE_INVISIBLE_MEMBER.on(ktNamedDeclaration, declared, invisibleOverridden));
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportForDeclaredMemberStrategy
            public void nothingToOverride(CallableMemberDescriptor overriding) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                OverrideResolver.this.trace.report(Errors.NOTHING_TO_OVERRIDE.on(ktNamedDeclaration, declared));
            }
        }, this.languageVersionSettings);
    }

    private final void checkOverrideForComponentFunction(final CallableMemberDescriptor componentFunction) {
        Companion companion = INSTANCE;
        DeclarationDescriptor containingDeclaration = componentFunction.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        final PsiElement psiElementFindDataModifierForDataClass = companion.findDataModifierForDataClass(containingDeclaration);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = componentFunction.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        companion.checkOverridesForMember(componentFunction, overriddenDescriptors, new CheckOverrideReportStrategy() { // from class: org.jetbrains.kotlin.resolve.OverrideResolver.checkOverrideForComponentFunction.1
            private boolean overrideConflict;

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void overridingFinalMember(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (this.overrideConflict) {
                    return;
                }
                this.overrideConflict = true;
                OverrideResolver.this.trace.report(Errors.DATA_CLASS_OVERRIDE_CONFLICT.on(psiElementFindDataModifierForDataClass, componentFunction, overridden.getContainingDeclaration()));
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void returnTypeMismatchOnOverride(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (this.overrideConflict) {
                    return;
                }
                this.overrideConflict = true;
                OverrideResolver.this.trace.report(Errors.DATA_CLASS_OVERRIDE_CONFLICT.on(psiElementFindDataModifierForDataClass, componentFunction, overridden.getContainingDeclaration()));
            }

            @Override // org.jetbrains.kotlin.resolve.OverrideResolver.CheckOverrideReportStrategy
            public void varOverriddenByVal(CallableMemberDescriptor overriding, CallableMemberDescriptor overridden) {
                Intrinsics.checkNotNullParameter(overriding, "overriding");
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                throw new IllegalStateException("Component functions are not properties");
            }
        }, this.kotlinTypeRefiner);
    }

    private final void checkOverrideForCopyFunction(CallableMemberDescriptor copyFunction) {
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = copyFunction.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.firstOrNull(overriddenDescriptors);
        if (callableMemberDescriptor != null) {
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            Companion companion = INSTANCE;
            DeclarationDescriptor containingDeclaration2 = copyFunction.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            this.trace.report(Errors.DATA_CLASS_OVERRIDE_DEFAULT_VALUES.on(this.languageVersionSettings, companion.findDataModifierForDataClass(containingDeclaration2), copyFunction, containingDeclaration));
        }
    }

    private final void checkParameterOverridesForAllClasses(TopDownAnalysisContext c) {
        Iterator<ClassDescriptorWithResolutionScopes> it = c.getDeclaredClasses().values().iterator();
        while (it.hasNext()) {
            for (DeclarationDescriptor declarationDescriptor : DescriptorUtils.getAllDescriptors(it.next().getDefaultType().getMemberScope())) {
                if (declarationDescriptor instanceof CallableMemberDescriptor) {
                    checkOverridesForParameters((CallableMemberDescriptor) declarationDescriptor);
                }
            }
        }
    }

    private final void checkOverridesForParameters(CallableMemberDescriptor declared) {
        KtModifierListOwner ktModifierListOwner;
        boolean z = declared.getKind() == CallableMemberDescriptor.Kind.DECLARATION;
        if (!z || (ktModifierListOwner = (KtModifierListOwner) DescriptorToSourceUtils.descriptorToDeclaration(declared)) == null || ktModifierListOwner.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
            for (ValueParameterDescriptor valueParameterDescriptor : declared.getValueParameters()) {
                Iterator<ValueParameterDescriptor> it = valueParameterDescriptor.getOverriddenDescriptors().iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (it.next().declaresDefaultValue()) {
                        i++;
                    }
                }
                boolean z2 = i > 1;
                if (z) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    checkNameAndDefaultForDeclaredParameter(valueParameterDescriptor, z2);
                } else {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    checkNameAndDefaultForFakeOverrideParameter(declared, valueParameterDescriptor, z2);
                }
            }
        }
    }

    private final void checkNameAndDefaultForDeclaredParameter(ValueParameterDescriptor descriptor, boolean multipleDefaultsInSuper) {
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
        KtParameter ktParameter = psiElementDescriptorToDeclaration instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration : null;
        if (ktParameter == null) {
            throw new IllegalStateException(("Declaration not found for parameter: " + descriptor).toString());
        }
        if (descriptor.declaresDefaultValue()) {
            this.trace.report(Errors.DEFAULT_VALUE_NOT_ALLOWED_IN_OVERRIDE.on(ktParameter));
        }
        if (multipleDefaultsInSuper) {
            this.trace.report(Errors.MULTIPLE_DEFAULTS_INHERITED_FROM_SUPERTYPES.on(ktParameter, descriptor));
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getOverriddenDescriptors()) {
            if (INSTANCE.shouldReportParameterNameOverrideWarning(descriptor, valueParameterDescriptor)) {
                BindingTrace bindingTrace = this.trace;
                DeclarationDescriptor containingDeclaration = valueParameterDescriptor.getContainingDeclaration().getContainingDeclaration();
                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                bindingTrace.report(Errors.PARAMETER_NAME_CHANGED_ON_OVERRIDE.on(ktParameter, (ClassDescriptor) containingDeclaration, valueParameterDescriptor));
            }
        }
    }

    private final void checkNameAndDefaultForFakeOverrideParameter(CallableMemberDescriptor containingFunction, ValueParameterDescriptor descriptor, boolean multipleDefaultsInSuper) {
        DeclarationDescriptor containingDeclaration = containingFunction.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        KtClassOrObject ktClassOrObject = (KtClassOrObject) DescriptorToSourceUtils.descriptorToDeclaration(containingDeclaration);
        if (ktClassOrObject == null) {
            throw new IllegalStateException(("Declaration not found for class: " + containingDeclaration).toString());
        }
        if (multipleDefaultsInSuper) {
            this.trace.report(Errors.MULTIPLE_DEFAULTS_INHERITED_FROM_SUPERTYPES_WHEN_NO_EXPLICIT_OVERRIDE.on(ktClassOrObject, descriptor));
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getOverriddenDescriptors()) {
            if (INSTANCE.shouldReportParameterNameOverrideWarning(descriptor, valueParameterDescriptor)) {
                this.trace.report(Errors.DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES.on(ktClassOrObject, containingFunction.getOverriddenDescriptors(), Integer.valueOf(valueParameterDescriptor.getIndex() + 1)));
            }
        }
    }

    private final void checkVisibility(TopDownAnalysisContext c) {
        Map<KtCallableDeclaration, CallableMemberDescriptor> members = c.getMembers();
        Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
        for (Map.Entry<KtCallableDeclaration, CallableMemberDescriptor> entry : members.entrySet()) {
            KtCallableDeclaration key = entry.getKey();
            CallableMemberDescriptor value = entry.getValue();
            Intrinsics.checkNotNull(key);
            Intrinsics.checkNotNull(value);
            checkVisibilityForMember(key, value);
            if ((key instanceof KtProperty) && (value instanceof PropertyDescriptor)) {
                KtPropertyAccessor setter = ((KtProperty) key).getSetter();
                PropertySetterDescriptor setter2 = ((PropertyDescriptor) value).getSetter();
                if (setter != null && setter2 != null) {
                    checkVisibilityForMember(setter, setter2);
                }
            }
        }
    }

    private final void checkVisibilityForMember(KtDeclaration declaration, CallableMemberDescriptor memberDescriptor) {
        DescriptorVisibility visibility = memberDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        for (CallableMemberDescriptor callableMemberDescriptor : memberDescriptor.getOverriddenDescriptors()) {
            Integer numCompare = DescriptorVisibilities.compare(visibility, callableMemberDescriptor.getVisibility());
            if (numCompare == null) {
                this.trace.report(Errors.CANNOT_CHANGE_ACCESS_PRIVILEGE.on(declaration, callableMemberDescriptor.getVisibility(), callableMemberDescriptor, callableMemberDescriptor.getContainingDeclaration()));
                return;
            } else if (numCompare.intValue() < 0) {
                this.trace.report(Errors.CANNOT_WEAKEN_ACCESS_PRIVILEGE.on(declaration, callableMemberDescriptor.getVisibility(), callableMemberDescriptor, callableMemberDescriptor.getContainingDeclaration()));
                return;
            }
        }
    }

    /* JADX INFO: compiled from: OverrideResolver.kt */
    @Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J.\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002J&\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0002J8\u0010\u0014\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u001aH\u0002J.\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J(\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0018\u0010#\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0018H\u0002J,\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00060&2\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002J(\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00160*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012H\u0002J\u001a\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00040-2\u0006\u0010.\u001a\u00020/J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0002J*\u00104\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0002J\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0006\u0010\r\u001a\u00020\u000eJ6\u00107\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0018\u00108\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00160*2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016H\u0002J8\u0010:\u001a\u00020\u00182\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0012\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00160\u00122\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016H\u0002J \u0010=\u001a\u00020\u00182\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?H\u0002J\u001c\u0010C\u001a\u00020\u00042\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010.\u001a\u00020/J\u0016\u0010E\u001a\u00020\u00182\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020GJ\u0012\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016*\u00020\u0006H\u0002¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverrideResolver$Companion;", "", "()V", "checkInheritedAndDelegatedSignatures", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "reportingStrategy", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckInheritedSignaturesReportStrategy;", "overrideReportStrategyForDelegates", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportStrategy;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "inheritedReportStrategy", "checkInheritedDescriptorsGroup", "overriddenDescriptors", "", "checkInvisibleFakeOverride", "checkMissingOverridesByJava8Restrictions", "relevantDirectlyOverridden", "", "onlyBaseClassMembers", "", "overriddenInterfaceMembers", "", "checkOverridesForMember", "memberDescriptor", "reportError", "checkOverridesForMemberMarkedOverride", "declared", "Lorg/jetbrains/kotlin/resolve/OverrideResolver$CheckOverrideReportForDeclaredMemberStrategy;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkPropertyKind", "isVar", "collectAbstractMethodsWithMoreSpecificReturnType", "", "abstractOverridden", "implementation", "collectOverriddenDeclarations", "", "directOverriddenDescriptors", "createCannotInferVisibilityReporter", "Lkotlin/Function1;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "findDataModifierForDataClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "dataClass", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "findInvisibleOverriddenDescriptor", "declaringClass", "getMissingImplementations", "getRelevantDirectlyOverridden", "overriddenByParent", "allFilteredOverriddenDeclarations", "isRelevant", "declarationSet", "allDeclarationSets", "isReturnTypeOkForOverride", "superDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "subDescriptor", "prepareTypeSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resolveUnknownVisibilities", "descriptors", "shouldReportParameterNameOverrideWarning", "parameterFromSubclass", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameterFromSuperclass", "computeRelevantDirectlyOverridden", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void resolveUnknownVisibilities(Collection<? extends CallableMemberDescriptor> descriptors, BindingTrace trace) {
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Iterator<? extends CallableMemberDescriptor> it = descriptors.iterator();
            while (it.hasNext()) {
                OverridingUtil.resolveUnknownVisibilityForMember(it.next(), createCannotInferVisibilityReporter(trace));
            }
        }

        public final Function1<CallableMemberDescriptor, Unit> createCannotInferVisibilityReporter(final BindingTrace trace) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            return new Function1<CallableMemberDescriptor, Unit>() { // from class: org.jetbrains.kotlin.resolve.OverrideResolver$Companion$createCannotInferVisibilityReporter$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(CallableMemberDescriptor callableMemberDescriptor) {
                    invoke2(callableMemberDescriptor);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Code duplicated, block: B:12:0x002f  */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(CallableMemberDescriptor descriptor) {
                    ClassDescriptor classDescriptor;
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    if (descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE || descriptor.getKind() == CallableMemberDescriptor.Kind.DELEGATION) {
                        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(descriptor);
                        if (containingClass == null) {
                            throw new AssertionError("Class member expected: " + descriptor);
                        }
                        classDescriptor = containingClass;
                    } else if (descriptor instanceof PropertyAccessorDescriptor) {
                        PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) descriptor;
                        if (propertyAccessorDescriptor.isDefault()) {
                            PropertyDescriptor correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty();
                            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                            classDescriptor = correspondingProperty;
                        } else {
                            classDescriptor = descriptor;
                        }
                    } else {
                        classDescriptor = descriptor;
                    }
                    PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(classDescriptor);
                    if (psiElementDescriptorToDeclaration instanceof KtDeclaration) {
                        trace.report(Errors.CANNOT_INFER_VISIBILITY.on(psiElementDescriptorToDeclaration, descriptor));
                    }
                }
            };
        }

        public final Set<CallableMemberDescriptor> getMissingImplementations(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            CollectMissingImplementationsStrategy collectMissingImplementationsStrategy = new CollectMissingImplementationsStrategy();
            checkInheritedAndDelegatedSignatures(classDescriptor, collectMissingImplementationsStrategy, (CheckOverrideReportStrategy) null, KotlinTypeRefiner.Default.INSTANCE);
            return collectMissingImplementationsStrategy.getShouldImplement();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkInheritedAndDelegatedSignatures(ClassDescriptor classDescriptor, CheckInheritedSignaturesReportStrategy inheritedReportStrategy, CheckOverrideReportStrategy overrideReportStrategyForDelegates, KotlinTypeRefiner kotlinTypeRefiner) {
            for (DeclarationDescriptor declarationDescriptor : DescriptorUtils.getAllDescriptors(classDescriptor.getDefaultType().getMemberScope())) {
                if (declarationDescriptor instanceof CallableMemberDescriptor) {
                    checkInheritedAndDelegatedSignatures((CallableMemberDescriptor) declarationDescriptor, inheritedReportStrategy, overrideReportStrategyForDelegates, kotlinTypeRefiner);
                }
            }
        }

        private final Set<CallableMemberDescriptor> computeRelevantDirectlyOverridden(CallableMemberDescriptor callableMemberDescriptor) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (overriddenDescriptors.isEmpty()) {
                return SetsKt.emptySet();
            }
            Map<CallableMemberDescriptor, Set<CallableMemberDescriptor>> mapCollectOverriddenDeclarations = collectOverriddenDeclarations(overriddenDescriptors);
            List listFlatten = ContainerUtil.flatten(mapCollectOverriddenDeclarations.values());
            Intrinsics.checkNotNullExpressionValue(listFlatten, "flatten(...)");
            Set<? extends CallableMemberDescriptor> setFilterOutOverridden = OverridingUtil.filterOutOverridden(Sets.newLinkedHashSet(listFlatten));
            Intrinsics.checkNotNullExpressionValue(setFilterOutOverridden, "filterOutOverridden(...)");
            return getRelevantDirectlyOverridden(mapCollectOverriddenDeclarations, setFilterOutOverridden);
        }

        private final void checkInheritedAndDelegatedSignatures(CallableMemberDescriptor descriptor, CheckInheritedSignaturesReportStrategy reportingStrategy, CheckOverrideReportStrategy overrideReportStrategyForDelegates, KotlinTypeRefiner kotlinTypeRefiner) {
            CallableMemberDescriptor.Kind kind = descriptor.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (kind == CallableMemberDescriptor.Kind.FAKE_OVERRIDE || kind == CallableMemberDescriptor.Kind.DELEGATION) {
                Set<CallableMemberDescriptor> setComputeRelevantDirectlyOverridden = computeRelevantDirectlyOverridden(descriptor);
                if (setComputeRelevantDirectlyOverridden.isEmpty()) {
                    return;
                }
                if (descriptor.getVisibility() == DescriptorVisibilities.INVISIBLE_FAKE) {
                    checkInvisibleFakeOverride(descriptor, setComputeRelevantDirectlyOverridden, reportingStrategy);
                    return;
                }
                Set<CallableMemberDescriptor> set = setComputeRelevantDirectlyOverridden;
                checkInheritedDescriptorsGroup(descriptor, set, reportingStrategy, kotlinTypeRefiner);
                if (kind == CallableMemberDescriptor.Kind.DELEGATION && overrideReportStrategyForDelegates != null) {
                    checkOverridesForMember(descriptor, set, overrideReportStrategyForDelegates, kotlinTypeRefiner);
                }
                if (kind != CallableMemberDescriptor.Kind.DELEGATION) {
                    checkMissingOverridesByJava8Restrictions$default(this, setComputeRelevantDirectlyOverridden, reportingStrategy, false, null, 12, null);
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : setComputeRelevantDirectlyOverridden) {
                    if (!CallResolverUtilKt.isOrOverridesSynthesized((CallableMemberDescriptor) obj)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj2 : arrayList) {
                    if (((CallableMemberDescriptor) obj2).getModality() != Modality.ABSTRACT) {
                        arrayList2.add(obj2);
                    } else {
                        arrayList3.add(obj2);
                    }
                }
                Pair pair = new Pair(arrayList2, arrayList3);
                List list = (List) pair.component1();
                List<? extends CallableMemberDescriptor> list2 = (List) pair.component2();
                int size = list.size();
                if (size == 0) {
                    if (kind != CallableMemberDescriptor.Kind.DELEGATION) {
                        Iterator<T> it = list2.iterator();
                        while (it.hasNext()) {
                            reportingStrategy.abstractMemberNotImplemented((CallableMemberDescriptor) it.next());
                        }
                        return;
                    }
                    return;
                }
                if (size == 1) {
                    if (kind != CallableMemberDescriptor.Kind.DELEGATION) {
                        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.first(list);
                        Iterator<T> it2 = collectAbstractMethodsWithMoreSpecificReturnType(list2, callableMemberDescriptor, kotlinTypeRefiner).iterator();
                        while (it2.hasNext()) {
                            reportingStrategy.abstractMemberWithMoreSpecificType((CallableMemberDescriptor) it2.next(), callableMemberDescriptor);
                        }
                        return;
                    }
                    return;
                }
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    reportingStrategy.multipleImplementationsMemberNotImplemented((CallableMemberDescriptor) it3.next());
                }
            }
        }

        private final void checkInvisibleFakeOverride(CallableMemberDescriptor descriptor, Collection<? extends CallableMemberDescriptor> overriddenDescriptors, CheckInheritedSignaturesReportStrategy reportingStrategy) {
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if ((classDescriptor != null ? classDescriptor.getModality() : null) == Modality.ABSTRACT) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : overriddenDescriptors) {
                if (((CallableMemberDescriptor) obj).getModality() == Modality.ABSTRACT) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.size() != overriddenDescriptors.size()) {
                return;
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                reportingStrategy.abstractInvisibleMember((CallableMemberDescriptor) it.next());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void checkMissingOverridesByJava8Restrictions$default(Companion companion, Set set, CheckInheritedSignaturesReportStrategy checkInheritedSignaturesReportStrategy, boolean z, List list, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            if ((i & 8) != 0) {
                list = new SmartList();
            }
            companion.checkMissingOverridesByJava8Restrictions(set, checkInheritedSignaturesReportStrategy, z, list);
        }

        private final void checkMissingOverridesByJava8Restrictions(Set<? extends CallableMemberDescriptor> relevantDirectlyOverridden, CheckInheritedSignaturesReportStrategy reportingStrategy, boolean onlyBaseClassMembers, List<CallableMemberDescriptor> overriddenInterfaceMembers) {
            boolean z = false;
            boolean z2 = false;
            CallableMemberDescriptor callableMemberDescriptor = null;
            CallableMemberDescriptor callableMemberDescriptor2 = null;
            CallableMemberDescriptor callableMemberDescriptor3 = null;
            for (CallableMemberDescriptor callableMemberDescriptor4 : relevantDirectlyOverridden) {
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor4.getContainingDeclaration();
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                if (classDescriptor != null) {
                    if (classDescriptor.getKind() == ClassKind.CLASS) {
                        if (callableMemberDescriptor4.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && !classDescriptor.get$isExpect()) {
                            callableMemberDescriptor3 = callableMemberDescriptor4;
                        }
                        if (callableMemberDescriptor4.getModality() == Modality.ABSTRACT) {
                            callableMemberDescriptor = callableMemberDescriptor4;
                        } else {
                            callableMemberDescriptor2 = callableMemberDescriptor4;
                        }
                        z = true;
                    } else if (classDescriptor.getKind() == ClassKind.INTERFACE) {
                        overriddenInterfaceMembers.add(callableMemberDescriptor4);
                        if (callableMemberDescriptor4.getModality() != Modality.ABSTRACT) {
                            z2 = true;
                        }
                    }
                }
            }
            if (callableMemberDescriptor != null && callableMemberDescriptor2 == null) {
                reportingStrategy.abstractBaseClassMemberNotImplemented(callableMemberDescriptor);
                return;
            }
            if (!onlyBaseClassMembers && !z && z2 && overriddenInterfaceMembers.size() > 1) {
                Iterator<CallableMemberDescriptor> it = overriddenInterfaceMembers.iterator();
                while (it.hasNext()) {
                    reportingStrategy.conflictingInterfaceMemberNotImplemented(it.next());
                }
            } else if (callableMemberDescriptor3 != null) {
                if (reportingStrategy instanceof CollectErrorInformationForInheritedMembersStrategy) {
                    reportingStrategy = ((CollectErrorInformationForInheritedMembersStrategy) reportingStrategy).toDeprecationStrategy();
                }
                checkMissingOverridesByJava8Restrictions(computeRelevantDirectlyOverridden(callableMemberDescriptor3), reportingStrategy, overriddenInterfaceMembers.isEmpty(), overriddenInterfaceMembers);
                if (reportingStrategy instanceof CollectWarningInformationForInheritedMembersStrategy) {
                    ((CollectWarningInformationForInheritedMembersStrategy) reportingStrategy).doReportErrors();
                }
            }
        }

        private final List<CallableMemberDescriptor> collectAbstractMethodsWithMoreSpecificReturnType(List<? extends CallableMemberDescriptor> abstractOverridden, CallableMemberDescriptor implementation, KotlinTypeRefiner kotlinTypeRefiner) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : abstractOverridden) {
                if (!OverrideResolver.INSTANCE.isReturnTypeOkForOverride((CallableMemberDescriptor) obj, implementation, kotlinTypeRefiner)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        private final Set<CallableMemberDescriptor> getRelevantDirectlyOverridden(Map<CallableMemberDescriptor, Set<CallableMemberDescriptor>> overriddenByParent, Set<? extends CallableMemberDescriptor> allFilteredOverriddenDeclarations) {
            Iterator<Map.Entry<CallableMemberDescriptor, Set<CallableMemberDescriptor>>> it = overriddenByParent.entrySet().iterator();
            while (it.hasNext()) {
                if (!isRelevant(it.next().getValue(), overriddenByParent.values(), allFilteredOverriddenDeclarations)) {
                    it.remove();
                }
            }
            return overriddenByParent.keySet();
        }

        private final boolean isRelevant(Set<? extends CallableMemberDescriptor> declarationSet, Collection<? extends Set<? extends CallableMemberDescriptor>> allDeclarationSets, Set<? extends CallableMemberDescriptor> allFilteredOverriddenDeclarations) {
            for (Set<? extends CallableMemberDescriptor> set : allDeclarationSets) {
                if (set != declarationSet) {
                    Set<? extends CallableMemberDescriptor> set2 = declarationSet;
                    if (set.containsAll(set2) || Collections.disjoint(allFilteredOverriddenDeclarations, set2)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private final Map<CallableMemberDescriptor, Set<CallableMemberDescriptor>> collectOverriddenDeclarations(Collection<? extends CallableMemberDescriptor> directOverriddenDescriptors) {
            LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
            for (CallableMemberDescriptor callableMemberDescriptor : directOverriddenDescriptors) {
                Set<CallableMemberDescriptor> overriddenDeclarations = OverridingUtil.getOverriddenDeclarations(callableMemberDescriptor);
                Intrinsics.checkNotNullExpressionValue(overriddenDeclarations, "getOverriddenDeclarations(...)");
                Set setFilterOutOverridden = OverridingUtil.filterOutOverridden(overriddenDeclarations);
                Intrinsics.checkNotNullExpressionValue(setFilterOutOverridden, "filterOutOverridden(...)");
                Intrinsics.checkNotNull(linkedHashMapNewLinkedHashMap);
                linkedHashMapNewLinkedHashMap.put(callableMemberDescriptor, new LinkedHashSet(setFilterOutOverridden));
            }
            Intrinsics.checkNotNull(linkedHashMapNewLinkedHashMap);
            return linkedHashMapNewLinkedHashMap;
        }

        private final void checkInheritedDescriptorsGroup(CallableMemberDescriptor descriptor, Collection<? extends CallableMemberDescriptor> overriddenDescriptors, CheckInheritedSignaturesReportStrategy reportingStrategy, KotlinTypeRefiner kotlinTypeRefiner) {
            if (overriddenDescriptors.size() <= 1) {
                return;
            }
            for (CallableMemberDescriptor callableMemberDescriptor : overriddenDescriptors) {
                if ((descriptor instanceof PropertyDescriptor) && !(callableMemberDescriptor instanceof PropertyDescriptor)) {
                    throw new IllegalArgumentException((callableMemberDescriptor + " is not a property").toString());
                }
                if (!isReturnTypeOkForOverride(callableMemberDescriptor, descriptor, kotlinTypeRefiner)) {
                    reportingStrategy.typeMismatchOnInheritance(descriptor, callableMemberDescriptor);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkOverridesForMemberMarkedOverride(CallableMemberDescriptor declared, KotlinTypeRefiner kotlinTypeRefiner, CheckOverrideReportForDeclaredMemberStrategy reportError, LanguageVersionSettings languageVersionSettings) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = declared.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            checkOverridesForMember(declared, overriddenDescriptors, reportError, kotlinTypeRefiner);
            if (overriddenDescriptors.isEmpty()) {
                DeclarationDescriptor containingDeclaration = declared.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                if (classDescriptor == null) {
                    throw new IllegalStateException(("Overrides may only be resolved in a class, but " + declared + " comes from " + containingDeclaration).toString());
                }
                CallableMemberDescriptor callableMemberDescriptorFindInvisibleOverriddenDescriptor = findInvisibleOverriddenDescriptor(declared, classDescriptor, kotlinTypeRefiner, languageVersionSettings);
                if (callableMemberDescriptorFindInvisibleOverriddenDescriptor != null) {
                    reportError.cannotOverrideInvisibleMember(declared, callableMemberDescriptorFindInvisibleOverriddenDescriptor);
                } else {
                    reportError.nothingToOverride(declared);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkOverridesForMember(CallableMemberDescriptor memberDescriptor, Collection<? extends CallableMemberDescriptor> overriddenDescriptors, CheckOverrideReportStrategy reportError, KotlinTypeRefiner kotlinTypeRefiner) {
            for (CallableMemberDescriptor callableMemberDescriptor : overriddenDescriptors) {
                if (callableMemberDescriptor.getModality() == Modality.FINAL) {
                    reportError.overridingFinalMember(memberDescriptor, callableMemberDescriptor);
                }
                if (!isReturnTypeOkForOverride(callableMemberDescriptor, memberDescriptor, kotlinTypeRefiner)) {
                    if ((memberDescriptor instanceof PropertyDescriptor) && !(callableMemberDescriptor instanceof PropertyDescriptor)) {
                        throw new IllegalArgumentException((callableMemberDescriptor + " is overridden by property " + memberDescriptor).toString());
                    }
                    reportError.returnTypeMismatchOnOverride(memberDescriptor, callableMemberDescriptor);
                }
                if (checkPropertyKind(callableMemberDescriptor, true) && checkPropertyKind(memberDescriptor, false)) {
                    reportError.varOverriddenByVal(memberDescriptor, callableMemberDescriptor);
                }
            }
        }

        private final boolean isReturnTypeOkForOverride(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, KotlinTypeRefiner kotlinTypeRefiner) {
            TypeSubstitutor typeSubstitutorPrepareTypeSubstitutor = prepareTypeSubstitutor(superDescriptor, subDescriptor);
            if (typeSubstitutorPrepareTypeSubstitutor == null) {
                return false;
            }
            KotlinType returnType = superDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            KotlinType returnType2 = subDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            KotlinType kotlinTypeSubstitute = typeSubstitutorPrepareTypeSubstitutor.substitute(returnType, Variance.OUT_VARIANCE);
            Intrinsics.checkNotNull(kotlinTypeSubstitute);
            NewKotlinTypeCheckerImpl newKotlinTypeCheckerImpl = new NewKotlinTypeCheckerImpl(kotlinTypeRefiner, null, 2, null);
            if ((superDescriptor instanceof PropertyDescriptor) && ((PropertyDescriptor) superDescriptor).isVar()) {
                return newKotlinTypeCheckerImpl.equalTypes(returnType2, kotlinTypeSubstitute);
            }
            return newKotlinTypeCheckerImpl.isSubtypeOf(returnType2, kotlinTypeSubstitute);
        }

        private final TypeSubstitutor prepareTypeSubstitutor(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor) {
            List<TypeParameterDescriptor> typeParameters = superDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            List<TypeParameterDescriptor> typeParameters2 = subDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            if (typeParameters2.size() != typeParameters.size()) {
                return null;
            }
            ArrayList arrayList = new ArrayList(typeParameters2.size());
            int size = typeParameters.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(new TypeProjectionImpl(typeParameters2.get(i).getDefaultType()));
            }
            return new IndexedParametersSubstitution(typeParameters, arrayList).buildSubstitutor();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PsiElement findDataModifierForDataClass(DeclarationDescriptor dataClass) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) DescriptorToSourceUtils.getSourceFromDescriptor(dataClass);
            if ((ktClassOrObject != null ? ktClassOrObject.getModifierList() : null) != null) {
                KtModifierList modifierList = ktClassOrObject.getModifierList();
                Intrinsics.checkNotNull(modifierList);
                PsiElement modifier = modifierList.getModifier(KtTokens.DATA_KEYWORD);
                if (modifier != null) {
                    return modifier;
                }
            }
            throw new IllegalStateException("No data modifier is found for data class " + dataClass);
        }

        private final CallableMemberDescriptor findInvisibleOverriddenDescriptor(CallableMemberDescriptor declared, ClassDescriptor declaringClass, KotlinTypeRefiner kotlinTypeRefiner, LanguageVersionSettings languageVersionSettings) {
            for (KotlinType kotlinType : kotlinTypeRefiner.refineSupertypes(declaringClass)) {
                LinkedHashSet<CallableMemberDescriptor> linkedHashSet = new LinkedHashSet();
                MemberScope memberScope = kotlinType.getMemberScope();
                Name name2 = declared.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                linkedHashSet.addAll(memberScope.getContributedFunctions(name2, NoLookupLocation.WHEN_CHECK_OVERRIDES));
                MemberScope memberScope2 = kotlinType.getMemberScope();
                Name name3 = declared.getShortName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                linkedHashSet.addAll(memberScope2.getContributedVariables(name3, NoLookupLocation.WHEN_CHECK_OVERRIDES));
                for (CallableMemberDescriptor callableMemberDescriptor : linkedHashSet) {
                    if (OverridingUtil.DEFAULT.isOverridableBy(callableMemberDescriptor, declared, null).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                        if (OverridingUtil.isVisibleForOverride(declared, callableMemberDescriptor, DescriptorVisibilityUtils.INSTANCE.getUseSpecialRulesForPrivateSealedConstructors(languageVersionSettings))) {
                            throw new IllegalStateException("Descriptor " + callableMemberDescriptor + " is overridable by " + declared + " and visible but does not appear in its getOverriddenDescriptors()");
                        }
                        return callableMemberDescriptor;
                    }
                }
            }
            return null;
        }

        public final boolean shouldReportParameterNameOverrideWarning(ValueParameterDescriptor parameterFromSubclass, ValueParameterDescriptor parameterFromSuperclass) {
            Intrinsics.checkNotNullParameter(parameterFromSubclass, "parameterFromSubclass");
            Intrinsics.checkNotNullParameter(parameterFromSuperclass, "parameterFromSuperclass");
            return parameterFromSubclass.getContainingDeclaration().hasStableParameterNames() && parameterFromSuperclass.getContainingDeclaration().hasStableParameterNames() && !Intrinsics.areEqual(parameterFromSuperclass.getShortName(), parameterFromSubclass.getShortName());
        }

        private final boolean checkPropertyKind(CallableMemberDescriptor descriptor, boolean isVar) {
            return (descriptor instanceof PropertyDescriptor) && ((PropertyDescriptor) descriptor).isVar() == isVar;
        }
    }
}
