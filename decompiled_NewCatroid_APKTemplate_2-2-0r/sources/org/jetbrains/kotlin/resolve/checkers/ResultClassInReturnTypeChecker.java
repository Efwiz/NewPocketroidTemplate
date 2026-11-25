package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ResultClassInReturnTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ResultClassInReturnTypeChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isForbiddenReturnType", "", "returnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "declarationDescriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResultClassInReturnTypeChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KotlinType returnType;
        KtTypeReference nameIdentifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
        if (((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getAllowResultReturnType())).booleanValue()) {
            return;
        }
        if (((languageVersionSettings.getFeatureSupport(LanguageFeature.InlineClasses) == LanguageFeature.State.ENABLED || languageVersionSettings.supportsFeature(LanguageFeature.JvmInlineValueClasses)) && languageVersionSettings.supportsFeature(LanguageFeature.AllowResultInReturnType)) || languageVersionSettings.supportsFeature(LanguageFeature.AllowNullOperatorsForResultAndResultReturnTypeByDefault) || !(declaration instanceof KtCallableDeclaration) || !(descriptor instanceof CallableMemberDescriptor) || (returnType = ((CallableMemberDescriptor) descriptor).getReturnType()) == null || !isForbiddenReturnType(returnType, declaration, descriptor)) {
            return;
        }
        KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) declaration;
        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktCallableDeclaration.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference != null) {
            nameIdentifier = ktTypeReferenceMo7396getTypeReference;
        } else {
            nameIdentifier = ktCallableDeclaration.mo7392getNameIdentifier();
            if (nameIdentifier == null) {
                return;
            }
        }
        BindingTrace trace = context.getTrace();
        Diagnostic diagnosticOn = Errors.RESULT_CLASS_IN_RETURN_TYPE.on(nameIdentifier);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
    }

    private final boolean isForbiddenReturnType(KotlinType returnType, KtDeclaration declaration, DeclarationDescriptor declarationDescriptor) {
        KtPropertyAccessor getter;
        if (!ResultClassInReturnTypeCheckerKt.isResultType(returnType)) {
            return false;
        }
        if (!(declarationDescriptor instanceof PropertyDescriptor) && !(declarationDescriptor instanceof PropertyGetterDescriptor)) {
            return true;
        }
        if ((declaration instanceof KtProperty) && (getter = ((KtProperty) declaration).getGetter()) != null && getter.hasBody()) {
            return true;
        }
        DescriptorVisibility visibilityImpl = ((DeclarationDescriptorWithVisibility) declarationDescriptor).getVisibilityImpl();
        Intrinsics.checkNotNullExpressionValue(visibilityImpl, "getVisibility(...)");
        return (DescriptorVisibilities.isPrivate(visibilityImpl) || Intrinsics.areEqual(visibilityImpl, DescriptorVisibilities.LOCAL)) ? false : true;
    }
}
