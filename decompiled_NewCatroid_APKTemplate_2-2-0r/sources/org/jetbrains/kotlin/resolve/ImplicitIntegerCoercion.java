package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ImplicitIntegerCoercion.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\f\u0010\u000f\u001a\u00020\u0007*\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ImplicitIntegerCoercion;", "", "()V", "IMPLICIT_INTEGER_COERCION_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "MODULE_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "getMODULE_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "isEnabledFor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "hasImplicitIntegerCoercionAnnotation", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitIntegerCoercion {
    public static final ImplicitIntegerCoercion INSTANCE = new ImplicitIntegerCoercion();
    private static final ModuleCapability<Boolean> MODULE_CAPABILITY = new ModuleCapability<>("ImplicitIntegerCoercion");
    private static final FqName IMPLICIT_INTEGER_COERCION_ANNOTATION_FQ_NAME = new FqName("kotlin.internal.ImplicitIntegerCoercion");

    private ImplicitIntegerCoercion() {
    }

    public final ModuleCapability<Boolean> getMODULE_CAPABILITY() {
        return MODULE_CAPABILITY;
    }

    public final boolean isEnabledFor(DeclarationDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        ModuleDescriptor containingModuleOrNull;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (hasImplicitIntegerCoercionAnnotation(descriptor)) {
            return true;
        }
        return languageVersionSettings.supportsFeature(LanguageFeature.ImplicitSignedToUnsignedIntegerConversion) && (containingModuleOrNull = DescriptorUtils.getContainingModuleOrNull(descriptor)) != null && ImplicitIntegerCoercionKt.hasImplicitIntegerCoercionCapability(containingModuleOrNull);
    }

    private final boolean hasImplicitIntegerCoercionAnnotation(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(IMPLICIT_INTEGER_COERCION_ANNOTATION_FQ_NAME) != null;
    }
}
