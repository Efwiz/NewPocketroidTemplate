package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.PlatformAnnotationFeaturesSupport;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: JvmPlatformAnnotationFeaturesSupport.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformAnnotationFeaturesSupport;", "Lorg/jetbrains/kotlin/resolve/PlatformAnnotationFeaturesSupport;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "isRepeatableAnnotationClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformAnnotationFeaturesSupport implements PlatformAnnotationFeaturesSupport {
    private final LanguageVersionSettings languageVersionSettings;

    public JvmPlatformAnnotationFeaturesSupport(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformAnnotationFeaturesSupport
    public boolean isRepeatableAnnotationClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
            throw new IllegalStateException(descriptor.toString());
        }
        Annotations annotations = descriptor.getAnnotations();
        FqName REPEATABLE_ANNOTATION = JvmAnnotationNames.REPEATABLE_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(REPEATABLE_ANNOTATION, "REPEATABLE_ANNOTATION");
        if (annotations.hasAnnotation(REPEATABLE_ANNOTATION)) {
            return this.languageVersionSettings.supportsFeature(LanguageFeature.RepeatableAnnotations) || (DescriptorUtilsKt.getAnnotationRetention(descriptor) == KotlinRetention.SOURCE && (descriptor instanceof JavaClassDescriptor));
        }
        return false;
    }
}
