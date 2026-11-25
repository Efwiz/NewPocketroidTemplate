package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$1 extends FunctionReference implements Function1<AbiRendererImpl.RenderedDeclaration<?>, Integer> {
    AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "orderByDeclarationKind(Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;)I";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "orderByDeclarationKind";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AbiRendererImpl.RenderedDeclarationContainerKind.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Integer invoke(AbiRendererImpl.RenderedDeclaration<?> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Integer.valueOf(((AbiRendererImpl.RenderedDeclarationContainerKind) this.receiver).orderByDeclarationKind(p0));
    }
}
