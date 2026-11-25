package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides;
import org.jetbrains.kotlin.resolve.calls.results.PlatformOverloadsSpecificityComparator;

/* JADX INFO: compiled from: JvmPlatformOverloadsSpecificityComparator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformOverloadsSpecificityComparator;", "Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "isMoreSpecificShape", "", "specific", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "general", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformOverloadsSpecificityComparator implements PlatformOverloadsSpecificityComparator {
    private final LanguageVersionSettings languageVersionSettings;

    public JvmPlatformOverloadsSpecificityComparator(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.PlatformOverloadsSpecificityComparator
    public boolean isMoreSpecificShape(CallableDescriptor specific, CallableDescriptor general) {
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        if (!(specific instanceof PropertyDescriptor) || !(general instanceof PropertyDescriptor)) {
            return false;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) specific;
        if (propertyDescriptor.mo6753getDispatchReceiverParameter() == null) {
            return false;
        }
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) general;
        return propertyDescriptor2.mo6753getDispatchReceiverParameter() != null && (propertyDescriptor.getPackageFragment() instanceof ClassDescriptor) && DescriptorEquivalenceForOverrides.areEquivalent$default(DescriptorEquivalenceForOverrides.INSTANCE, propertyDescriptor.getPackageFragment(), propertyDescriptor2.getPackageFragment(), true, false, 8, null) && this.languageVersionSettings.supportsFeature(LanguageFeature.PreferJavaFieldOverload) && JavaDescriptorUtilKt.isJavaField(propertyDescriptor) && !JavaDescriptorUtilKt.isJavaField(propertyDescriptor2);
    }
}
