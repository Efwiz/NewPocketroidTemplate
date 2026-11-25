package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;

/* JADX INFO: compiled from: Fir2IrVisibilityConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H$J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "", "()V", "convertPlatformVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "convertToDescriptorVisibility", "Default", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Fir2IrVisibilityConverter {
    protected abstract DescriptorVisibility convertPlatformVisibility(Visibility visibility);

    /* JADX INFO: compiled from: Fir2IrVisibilityConverter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter$Default;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "()V", "convertPlatformVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends Fir2IrVisibilityConverter {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter
        protected DescriptorVisibility convertPlatformVisibility(Visibility visibility) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            throw new IllegalStateException(("Unknown visibility: " + visibility).toString());
        }
    }

    public final DescriptorVisibility convertToDescriptorVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE)) {
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            return PRIVATE;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) {
            DescriptorVisibility PRIVATE_TO_THIS = DescriptorVisibilities.PRIVATE_TO_THIS;
            Intrinsics.checkNotNullExpressionValue(PRIVATE_TO_THIS, "PRIVATE_TO_THIS");
            return PRIVATE_TO_THIS;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE)) {
            DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
            Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
            return PROTECTED;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
            Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
            return INTERNAL;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE)) {
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            return PUBLIC;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Local.INSTANCE)) {
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            return LOCAL;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.InvisibleFake.INSTANCE)) {
            DescriptorVisibility INVISIBLE_FAKE = DescriptorVisibilities.INVISIBLE_FAKE;
            Intrinsics.checkNotNullExpressionValue(INVISIBLE_FAKE, "INVISIBLE_FAKE");
            return INVISIBLE_FAKE;
        }
        if (!Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE)) {
            return convertPlatformVisibility(visibility);
        }
        DescriptorVisibility UNKNOWN = DescriptorVisibilities.UNKNOWN;
        Intrinsics.checkNotNullExpressionValue(UNKNOWN, "UNKNOWN");
        return UNKNOWN;
    }
}
