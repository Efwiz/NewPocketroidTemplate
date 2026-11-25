package org.jetbrains.kotlin.platform;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;

/* JADX INFO: compiled from: PlatformSpecificDiagnosticComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "isNullabilityAnnotation", "", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PlatformSpecificDiagnosticComponents extends PlatformSpecificExtension<PlatformSpecificDiagnosticComponents> {
    boolean isNullabilityAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor containingDeclaration);

    /* JADX INFO: compiled from: PlatformSpecificDiagnosticComponents.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents$Default;", "Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "()V", "isNullabilityAnnotation", "", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements PlatformSpecificDiagnosticComponents {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents
        public boolean isNullabilityAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor containingDeclaration) {
            Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            return false;
        }

        private Default() {
        }
    }
}
