package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: PublishedApiUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PublishedApiUsageChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PublishedApiUsageChecker {
    public static final PublishedApiUsageChecker INSTANCE = new PublishedApiUsageChecker();

    private PublishedApiUsageChecker() {
    }

    public final void check(KtDeclaration declaration, DeclarationDescriptor descriptor, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (!(descriptor instanceof DeclarationDescriptorWithVisibility) || Intrinsics.areEqual(((DeclarationDescriptorWithVisibility) descriptor).getVisibilityImpl(), DescriptorVisibilities.INTERNAL) || (descriptor instanceof PropertyAccessorDescriptor)) {
            return;
        }
        for (KtAnnotationEntry ktAnnotationEntry : declaration.getAnnotationEntries()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null && Intrinsics.areEqual(annotationDescriptor.getFqName(), StandardNames.FqNames.publishedApi)) {
                trace.report(Errors.NON_INTERNAL_PUBLISHED_API.on(ktAnnotationEntry));
            }
        }
    }
}
