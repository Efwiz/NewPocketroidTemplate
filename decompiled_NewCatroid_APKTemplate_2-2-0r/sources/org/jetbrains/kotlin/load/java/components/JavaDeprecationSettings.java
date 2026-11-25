package org.jetbrains.kotlin.load.java.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationSettings;

/* JADX INFO: compiled from: JavaDeprecationSettings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaDeprecationSettings;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationSettings;", "()V", "propagatedToOverrides", "", "deprecationAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaDeprecationSettings implements DeprecationSettings {
    public static final JavaDeprecationSettings INSTANCE = new JavaDeprecationSettings();

    private JavaDeprecationSettings() {
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationSettings
    public boolean propagatedToOverrides(AnnotationDescriptor deprecationAnnotation) {
        Intrinsics.checkNotNullParameter(deprecationAnnotation, "deprecationAnnotation");
        return !(deprecationAnnotation instanceof JavaDeprecatedAnnotationDescriptor);
    }
}
