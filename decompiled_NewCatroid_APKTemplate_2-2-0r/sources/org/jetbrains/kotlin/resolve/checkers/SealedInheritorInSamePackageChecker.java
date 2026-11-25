package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: SealedInheritorInSamePackageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SealedInheritorInSamePackageChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SealedInheritorInSamePackageChecker implements DeclarationChecker {
    public static final SealedInheritorInSamePackageChecker INSTANCE = new SealedInheritorInSamePackageChecker();

    private SealedInheritorInSamePackageChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        FqName fqNameContainingPackage;
        UnwrappedType unwrappedTypeUnwrap;
        ClassifierDescriptor this$0;
        FqName fqNameContainingPackage2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage) && (descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject) && (fqNameContainingPackage = DescriptorUtilKt.containingPackage(descriptor)) != null) {
            Iterator<KtSuperTypeListEntry> it = ((KtClassOrObject) declaration).getSuperTypeListEntries().iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = it.next().getTypeReference();
                if (typeReference != null) {
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    KotlinType abbreviatedTypeOrType = BindingContextUtilsKt.getAbbreviatedTypeOrType(typeReference, context2);
                    if (abbreviatedTypeOrType != null && (unwrappedTypeUnwrap = abbreviatedTypeOrType.unwrap()) != null && (this$0 = unwrappedTypeUnwrap.getConstructor().getThis$0()) != null) {
                        ClassifierDescriptor classifierDescriptor = this$0;
                        if (DescriptorUtilKt.isSealed(classifierDescriptor) && (fqNameContainingPackage2 = DescriptorUtilKt.containingPackage(classifierDescriptor)) != null && !Intrinsics.areEqual(fqNameContainingPackage, fqNameContainingPackage2)) {
                            context.getTrace().report(Errors.SEALED_INHERITOR_IN_DIFFERENT_PACKAGE.on(typeReference, fqNameContainingPackage, fqNameContainingPackage2));
                        }
                    }
                }
            }
        }
    }
}
