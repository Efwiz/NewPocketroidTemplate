package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtTypeAlias;

/* JADX INFO: compiled from: ExpectActualClassifiersAreInBetaChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExpectActualClassifiersAreInBetaChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualClassifiersAreInBetaChecker implements DeclarationChecker {
    public static final ExpectActualClassifiersAreInBetaChecker INSTANCE = new ExpectActualClassifiersAreInBetaChecker();

    private ExpectActualClassifiersAreInBetaChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiPlatformProjects) && !CommonConfigurationKeysKt.getAreExpectActualClassesStable(context.getLanguageVersionSettings())) {
            if ((descriptor instanceof TypeAliasDescriptor) || (descriptor instanceof ClassDescriptor)) {
                if (!(declaration instanceof KtClassOrObject) && !(declaration instanceof KtTypeAlias)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (!(declaration instanceof KtClassLikeDeclaration)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (!(descriptor instanceof ClassifierDescriptorWithTypeParameters)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = (ClassifierDescriptorWithTypeParameters) descriptor;
                if (classifierDescriptorWithTypeParameters.get$isExpect() || classifierDescriptorWithTypeParameters.isActual()) {
                    context.getTrace().report(Errors.EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING.on(declaration));
                }
            }
        }
    }
}
