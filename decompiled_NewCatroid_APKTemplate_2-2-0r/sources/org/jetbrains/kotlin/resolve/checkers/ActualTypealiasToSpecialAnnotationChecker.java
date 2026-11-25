package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.calls.mpp.ActualTypealiasToSpecialAnnotationUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: ActualTypealiasToSpecialAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ActualTypealiasToSpecialAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActualTypealiasToSpecialAnnotationChecker implements DeclarationChecker {
    public static final ActualTypealiasToSpecialAnnotationChecker INSTANCE = new ActualTypealiasToSpecialAnnotationChecker();

    private ActualTypealiasToSpecialAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ClassDescriptor classDescriptor;
        ClassId classId;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (declaration instanceof KtTypeAlias) && (descriptor instanceof TypeAliasDescriptor)) {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) descriptor;
            if (typeAliasDescriptor.isActual() && (classDescriptor = typeAliasDescriptor.getClassDescriptor()) != null && classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS && (classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor)) != null && ActualTypealiasToSpecialAnnotationUtils.INSTANCE.isAnnotationProhibitedInActualTypeAlias(classId)) {
                context.getTrace().report(Errors.ACTUAL_TYPEALIAS_TO_SPECIAL_ANNOTATION.on(declaration, classId));
            }
        }
    }
}
