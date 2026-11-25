package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;

/* JADX INFO: compiled from: Renderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"asRenderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/SmartDescriptorRenderer;", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RenderersKt {
    public static final SmartDescriptorRenderer asRenderer(DescriptorRenderer descriptorRenderer) {
        Intrinsics.checkNotNullParameter(descriptorRenderer, "<this>");
        return new SmartDescriptorRenderer(descriptorRenderer);
    }
}
