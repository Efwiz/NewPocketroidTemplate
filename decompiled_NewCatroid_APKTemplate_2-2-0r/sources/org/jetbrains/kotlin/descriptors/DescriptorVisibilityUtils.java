package org.jetbrains.kotlin.descriptors;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: DescriptorVisibilityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J*\u0010\u0010\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J \u0010\u0011\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J \u0010\u0012\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0007R\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DescriptorVisibilityUtils;", "", "()V", "useSpecialRulesForPrivateSealedConstructors", "", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getUseSpecialRulesForPrivateSealedConstructors", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)Z", "findInvisibleMember", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "what", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "isVisible", "isVisibleIgnoringReceiver", "isVisibleWithAnyReceiver", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorVisibilityUtils {
    public static final DescriptorVisibilityUtils INSTANCE = new DescriptorVisibilityUtils();

    private DescriptorVisibilityUtils() {
    }

    @JvmStatic
    public static final DeclarationDescriptorWithVisibility findInvisibleMember(ReceiverValue receiver, DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(what, "what");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return DescriptorVisibilities.findInvisibleMember(receiver, what, from, INSTANCE.getUseSpecialRulesForPrivateSealedConstructors(languageVersionSettings));
    }

    @JvmStatic
    public static final boolean isVisible(ReceiverValue receiver, DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(what, "what");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return DescriptorVisibilities.isVisible(receiver, what, from, INSTANCE.getUseSpecialRulesForPrivateSealedConstructors(languageVersionSettings));
    }

    @JvmStatic
    public static final boolean isVisibleIgnoringReceiver(DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(what, "what");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return DescriptorVisibilities.isVisibleIgnoringReceiver(what, from, INSTANCE.getUseSpecialRulesForPrivateSealedConstructors(languageVersionSettings));
    }

    @JvmStatic
    public static final boolean isVisibleWithAnyReceiver(DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(what, "what");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return DescriptorVisibilities.isVisibleWithAnyReceiver(what, from, INSTANCE.getUseSpecialRulesForPrivateSealedConstructors(languageVersionSettings));
    }

    public final boolean getUseSpecialRulesForPrivateSealedConstructors(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "<this>");
        return (languageVersionSettings.supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage) && languageVersionSettings.supportsFeature(LanguageFeature.UseConsistentRulesForPrivateConstructorsOfSealedClasses)) ? false : true;
    }
}
