package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;

/* JADX INFO: compiled from: FirJvmVisibilityConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmVisibilityConverter;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "()V", "convertPlatformVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmVisibilityConverter extends Fir2IrVisibilityConverter {
    public static final FirJvmVisibilityConverter INSTANCE = new FirJvmVisibilityConverter();

    private FirJvmVisibilityConverter() {
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter
    protected DescriptorVisibility convertPlatformVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        if (Intrinsics.areEqual(visibility, JavaVisibilities.PackageVisibility.INSTANCE)) {
            DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
            return PACKAGE_VISIBILITY;
        }
        if (Intrinsics.areEqual(visibility, JavaVisibilities.ProtectedStaticVisibility.INSTANCE)) {
            DescriptorVisibility PROTECTED_STATIC_VISIBILITY = JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PROTECTED_STATIC_VISIBILITY, "PROTECTED_STATIC_VISIBILITY");
            return PROTECTED_STATIC_VISIBILITY;
        }
        if (!Intrinsics.areEqual(visibility, JavaVisibilities.ProtectedAndPackage.INSTANCE)) {
            throw new IllegalStateException(("Unknown visibility: " + visibility).toString());
        }
        DescriptorVisibility PROTECTED_AND_PACKAGE = JavaDescriptorVisibilities.PROTECTED_AND_PACKAGE;
        Intrinsics.checkNotNullExpressionValue(PROTECTED_AND_PACKAGE, "PROTECTED_AND_PACKAGE");
        return PROTECTED_AND_PACKAGE;
    }
}
