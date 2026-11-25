package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;

/* JADX INFO: compiled from: AnnotationsWhitelistDescriptorRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u001f\b\u0002\u0010\u0003\u001a\u0019\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"withAnnotationsWhitelist", "Lorg/jetbrains/kotlin/diagnostics/rendering/AnnotationsWhitelistDescriptorRenderer;", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "toParameterRenderer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lkotlin/ExtensionFunctionType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsWhitelistDescriptorRendererKt {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.diagnostics.rendering.AnnotationsWhitelistDescriptorRendererKt$withAnnotationsWhitelist$1, reason: invalid class name */
    /* JADX INFO: compiled from: AnnotationsWhitelistDescriptorRenderer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<DescriptorRenderer, SmartDescriptorRenderer> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "asRenderer(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;)Lorg/jetbrains/kotlin/diagnostics/rendering/SmartDescriptorRenderer;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "asRenderer";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(RenderersKt.class, "frontend");
        }

        @Override // kotlin.jvm.functions.Function1
        public final SmartDescriptorRenderer invoke(DescriptorRenderer p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return RenderersKt.asRenderer(p0);
        }
    }

    public static /* synthetic */ AnnotationsWhitelistDescriptorRenderer withAnnotationsWhitelist$default(DescriptorRenderer descriptorRenderer, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = AnonymousClass1.INSTANCE;
        }
        return withAnnotationsWhitelist(descriptorRenderer, function1);
    }

    public static final AnnotationsWhitelistDescriptorRenderer withAnnotationsWhitelist(DescriptorRenderer descriptorRenderer, Function1<? super DescriptorRenderer, ? extends DiagnosticParameterRenderer<? super DeclarationDescriptor>> toParameterRenderer) {
        Intrinsics.checkNotNullParameter(descriptorRenderer, "<this>");
        Intrinsics.checkNotNullParameter(toParameterRenderer, "toParameterRenderer");
        return new AnnotationsWhitelistDescriptorRenderer(descriptorRenderer, toParameterRenderer);
    }
}
