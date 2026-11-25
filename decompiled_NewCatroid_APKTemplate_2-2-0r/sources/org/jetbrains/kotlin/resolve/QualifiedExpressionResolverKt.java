package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;

/* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¨\u0006\t"}, d2 = {"isVisible", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "shouldBeVisibleFrom", "position", "Lorg/jetbrains/kotlin/resolve/QualifierPosition;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifiedExpressionResolverKt {
    public static final boolean isVisible(DeclarationDescriptor descriptor, DeclarationDescriptor declarationDescriptor, QualifierPosition position, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (!(descriptor instanceof DeclarationDescriptorWithVisibility) || declarationDescriptor == null) {
            return true;
        }
        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) descriptor;
        DescriptorVisibility visibility = declarationDescriptorWithVisibility.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        if (position == QualifierPosition.IMPORT) {
            if (DescriptorVisibilities.isPrivate(visibility)) {
                return DescriptorVisibilities.inSameFile(descriptor, declarationDescriptor);
            }
            if (!visibility.mo7471mustCheckInImports()) {
                return true;
            }
        }
        return DescriptorVisibilityUtils.isVisibleIgnoringReceiver(declarationDescriptorWithVisibility, declarationDescriptor, languageVersionSettings);
    }
}
