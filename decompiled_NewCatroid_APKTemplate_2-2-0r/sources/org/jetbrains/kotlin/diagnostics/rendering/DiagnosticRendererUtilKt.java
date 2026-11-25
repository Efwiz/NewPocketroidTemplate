package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;

/* JADX INFO: compiled from: DiagnosticRendererUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004¨\u0006\u0005"}, d2 = {"renderKind", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "renderKindWithName", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticRendererUtilKt {
    public static final String renderKindWithName(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        return DescriptorRenderer.INSTANCE.getClassifierKindPrefix(classifierDescriptorWithTypeParameters) + " '" + classifierDescriptorWithTypeParameters.getName() + '\'';
    }

    public static final String renderKind(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return DescriptorRenderer.INSTANCE.getClassifierKindPrefix(classDescriptor);
    }
}
