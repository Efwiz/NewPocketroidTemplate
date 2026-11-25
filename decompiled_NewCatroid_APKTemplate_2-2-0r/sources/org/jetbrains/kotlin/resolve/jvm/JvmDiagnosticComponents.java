package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.AnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.JvmAnnotationNamesKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents;

/* JADX INFO: compiled from: JvmDiagnosticComponents.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmDiagnosticComponents;", "Lorg/jetbrains/kotlin/platform/PlatformSpecificDiagnosticComponents;", "typeQualifierResolver", "Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "(Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;)V", "isNullabilityAnnotation", "", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDiagnosticComponents implements PlatformSpecificDiagnosticComponents {
    private final AnnotationTypeQualifierResolver typeQualifierResolver;

    public JvmDiagnosticComponents(AnnotationTypeQualifierResolver typeQualifierResolver) {
        Intrinsics.checkNotNullParameter(typeQualifierResolver, "typeQualifierResolver");
        this.typeQualifierResolver = typeQualifierResolver;
    }

    @Override // org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents
    public boolean isNullabilityAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor containingDeclaration) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        if (!(containingDeclaration instanceof JavaCallableMemberDescriptor)) {
            return false;
        }
        FqName fqName = annotationDescriptor.getFqName();
        return (fqName != null && JvmAnnotationNamesKt.getNULLABILITY_ANNOTATIONS().contains(fqName)) || this.typeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor) != null;
    }
}
