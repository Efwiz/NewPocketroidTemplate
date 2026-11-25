package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DeclarationReturnTypeSanitizer;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.WrappedTypeFactory;

/* JADX INFO: compiled from: JvmDeclarationReturnTypeSanitizer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmDeclarationReturnTypeSanitizer;", "Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;", "()V", "sanitizeReturnType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "inferred", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDeclarationReturnTypeSanitizer implements DeclarationReturnTypeSanitizer {
    public static final JvmDeclarationReturnTypeSanitizer INSTANCE = new JvmDeclarationReturnTypeSanitizer();

    private JvmDeclarationReturnTypeSanitizer() {
    }

    @Override // org.jetbrains.kotlin.resolve.DeclarationReturnTypeSanitizer
    public UnwrappedType sanitizeReturnType(UnwrappedType inferred, WrappedTypeFactory wrappedTypeFactory, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(inferred, "inferred");
        Intrinsics.checkNotNullParameter(wrappedTypeFactory, "wrappedTypeFactory");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return languageVersionSettings.supportsFeature(LanguageFeature.StrictJavaNullabilityAssertions) ? inferred.replaceAttributes(TypeAttributesKt.replaceAnnotations(inferred.getAttributes(), new FilteredAnnotations(inferred.getAnnotations(), languageVersionSettings.supportsFeature(LanguageFeature.NewInference), new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.JvmDeclarationReturnTypeSanitizer.sanitizeReturnType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it, JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION));
            }
        }))) : inferred;
    }
}
