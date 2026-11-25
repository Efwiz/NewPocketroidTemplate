package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ExplicitApiMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: ExplicitApiDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J(\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExplicitApiDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkExplicitReturnType", "state", "Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "checkVisibilityModifier", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitApiDeclarationChecker implements DeclarationChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        ExplicitApiMode explicitApiMode = (ExplicitApiMode) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getExplicitApiMode());
        if (explicitApiMode != ExplicitApiMode.DISABLED && (descriptor instanceof DeclarationDescriptorWithVisibility)) {
            if ((descriptor instanceof ClassDescriptor) && ((ClassDescriptor) descriptor).getClassKind() == ClassKind.ENUM_ENTRY) {
                return;
            }
            DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) descriptor;
            if (DescriptorUtilsKt.isEffectivelyPublicApi(declarationDescriptorWithVisibility) || DescriptorUtilsKt.isPublishedApi(descriptor)) {
                checkVisibilityModifier(explicitApiMode, declaration, declarationDescriptorWithVisibility, context);
                checkExplicitReturnType(explicitApiMode, declaration, descriptor, context);
            }
        }
    }

    private final void checkVisibilityModifier(ExplicitApiMode state, KtDeclaration declaration, DeclarationDescriptorWithVisibility descriptor, DeclarationCheckerContext context) {
        DiagnosticFactory0<KtDeclaration> diagnosticFactory0;
        if (KtPsiUtilKt.visibilityModifier(declaration) == null && !INSTANCE.explicitVisibilityIsNotRequired(descriptor)) {
            if (state == ExplicitApiMode.STRICT) {
                diagnosticFactory0 = Errors.NO_EXPLICIT_VISIBILITY_IN_API_MODE;
            } else {
                diagnosticFactory0 = Errors.NO_EXPLICIT_VISIBILITY_IN_API_MODE_WARNING;
            }
            BindingTrace trace = context.getTrace();
            Diagnostic diagnosticOn = diagnosticFactory0.on(declaration);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
        }
    }

    private final void checkExplicitReturnType(ExplicitApiMode state, KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        DiagnosticFactory0<KtNamedDeclaration> diagnosticFactory0;
        if (declaration instanceof KtCallableDeclaration) {
            Companion companion = INSTANCE;
            KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) declaration;
            if (companion.returnTypeCheckIsApplicable(ktCallableDeclaration) && companion.returnTypeRequired(ktCallableDeclaration, descriptor, true, false, false)) {
                if (state == ExplicitApiMode.STRICT) {
                    diagnosticFactory0 = Errors.NO_EXPLICIT_RETURN_TYPE_IN_API_MODE;
                } else {
                    diagnosticFactory0 = Errors.NO_EXPLICIT_RETURN_TYPE_IN_API_MODE_WARNING;
                }
                BindingTrace trace = context.getTrace();
                Diagnostic diagnosticOn = diagnosticFactory0.on(declaration);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
            }
        }
    }

    /* JADX INFO: compiled from: ExplicitApiDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ0\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExplicitApiDeclarationChecker$Companion;", "", "()V", "explicitVisibilityIsNotRequired", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "publicReturnTypeShouldBePresentInApiMode", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "returnTypeCheckIsApplicable", "returnTypeRequired", "checkForPublicApi", "checkForInternal", "checkForPrivate", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean explicitVisibilityIsNotRequired(DeclarationDescriptor descriptor) {
            Collection<? extends CallableDescriptor> overriddenDescriptors;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            ClassConstructorDescriptor classConstructorDescriptor = descriptor instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) descriptor : null;
            if (classConstructorDescriptor != null && classConstructorDescriptor.isPrimary()) {
                return true;
            }
            boolean z = descriptor instanceof PropertyDescriptor;
            if (z) {
                DeclarationDescriptor containingDeclaration = ((PropertyDescriptor) descriptor).getContainingModule();
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                if (classDescriptor != null && classDescriptor.isData()) {
                    return true;
                }
            }
            CallableDescriptor callableDescriptor = descriptor instanceof CallableDescriptor ? (CallableDescriptor) descriptor : null;
            if ((callableDescriptor != null && (overriddenDescriptors = callableDescriptor.getOverriddenDescriptors()) != null && (!overriddenDescriptors.isEmpty())) || (descriptor instanceof PropertyAccessorDescriptor)) {
                return true;
            }
            if (!z) {
                return false;
            }
            DeclarationDescriptor containingDeclaration2 = ((PropertyDescriptor) descriptor).getContainingModule();
            ClassDescriptor classDescriptor2 = containingDeclaration2 instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration2 : null;
            return (classDescriptor2 != null ? classDescriptor2.getClassKind() : null) == ClassKind.ANNOTATION_CLASS;
        }

        public final boolean returnTypeRequired(KtCallableDeclaration element, DeclarationDescriptor descriptor, boolean checkForPublicApi, boolean checkForInternal, boolean checkForPrivate) {
            EffectiveVisibility effectiveVisibilityEffectiveVisibility$default;
            Intrinsics.checkNotNullParameter(element, "element");
            KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(element);
            if (containingClassOrObject != null && containingClassOrObject.isLocal()) {
                return false;
            }
            if ((element instanceof KtFunction) && ((KtFunction) element).isLocal()) {
                return false;
            }
            if ((element instanceof KtProperty) && ((KtProperty) element).isLocal()) {
                return false;
            }
            Visibility visibility = null;
            CallableMemberDescriptor callableMemberDescriptor = descriptor instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) descriptor : null;
            if (callableMemberDescriptor != null && (effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default((DeclarationDescriptorWithVisibility) callableMemberDescriptor, (DescriptorVisibility) null, false, 3, (Object) null)) != null) {
                visibility = effectiveVisibilityEffectiveVisibility$default.toVisibility();
            }
            boolean z = (visibility != null && visibility.getIsPublicAPI()) || (Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE) && DescriptorUtilsKt.isPublishedApi(callableMemberDescriptor));
            if (checkForPublicApi && z) {
                return true;
            }
            if (checkForInternal && Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
                return true;
            }
            return checkForPrivate && Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE);
        }

        public final boolean returnTypeCheckIsApplicable(KtCallableDeclaration element) {
            Intrinsics.checkNotNullParameter(element, "element");
            if ((element.getContainingFile() instanceof KtCodeFragment) || (element instanceof KtFunctionLiteral) || (element instanceof KtConstructor) || element.mo7396getTypeReference() != null) {
                return false;
            }
            return ((element instanceof KtNamedFunction) && ((KtNamedFunction) element).hasBlockBody()) ? false : true;
        }

        public final boolean publicReturnTypeShouldBePresentInApiMode(KtCallableDeclaration element, LanguageVersionSettings languageVersionSettings, DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return languageVersionSettings.getFlag(AnalysisFlags.getExplicitApiMode()) != ExplicitApiMode.DISABLED && returnTypeRequired(element, descriptor, true, false, false);
        }
    }
}
