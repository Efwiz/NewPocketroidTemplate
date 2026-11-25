package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;

/* JADX INFO: compiled from: DeclarationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"getEffectiveModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationsCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:13:0x0022  */
    public static final Modality getEffectiveModality(PropertyDescriptor propertyDescriptor) {
        boolean z;
        if (propertyDescriptor.getModality() == Modality.OPEN) {
            DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if ((classDescriptor != null ? classDescriptor.getModality() : null) == Modality.FINAL) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (z) {
            return Modality.FINAL;
        }
        if (z) {
            throw new NoWhenBranchMatchedException();
        }
        Modality modality = propertyDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        return modality;
    }

    public static final Modality getEffectiveModality(PropertyDescriptor propertyDescriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        boolean zSupportsFeature = languageVersionSettings.supportsFeature(LanguageFeature.TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck);
        if (zSupportsFeature) {
            return getEffectiveModality(propertyDescriptor);
        }
        if (zSupportsFeature) {
            throw new NoWhenBranchMatchedException();
        }
        Modality modality = propertyDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        return modality;
    }
}
