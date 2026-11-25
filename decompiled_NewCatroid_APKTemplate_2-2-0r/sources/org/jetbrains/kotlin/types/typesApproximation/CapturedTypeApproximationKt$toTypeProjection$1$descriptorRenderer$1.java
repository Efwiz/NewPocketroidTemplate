package org.jetbrains.kotlin.types.typesApproximation;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.renderer.ClassifierNamePolicy;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;

/* JADX INFO: compiled from: CapturedTypeApproximation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1 extends Lambda implements Function1<DescriptorRendererOptions, Unit> {
    public static final CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1 INSTANCE = new CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1();

    CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
        invoke2(descriptorRendererOptions);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(DescriptorRendererOptions withOptions) {
        Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
        withOptions.setClassifierNamePolicy(ClassifierNamePolicy.FULLY_QUALIFIED.INSTANCE);
    }
}
