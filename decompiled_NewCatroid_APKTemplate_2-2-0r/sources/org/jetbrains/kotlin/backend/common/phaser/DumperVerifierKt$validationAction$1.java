package org.jetbrains.kotlin.backend.common.phaser;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: DumperVerifier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DumperVerifierKt$validationAction$1 extends FunctionReference implements Function2<CommonBackendContext, IrElement, Unit> {
    public static final DumperVerifierKt$validationAction$1 INSTANCE = new DumperVerifierKt$validationAction$1();

    DumperVerifierKt$validationAction$1() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "validationAction$validationCallback(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/IrElement;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "validationCallback";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(CommonBackendContext commonBackendContext, IrElement irElement) {
        invoke2(commonBackendContext, irElement);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(CommonBackendContext p0, IrElement p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        DumperVerifierKt.validationCallback$default(p0, p1, false, 4, null);
    }
}
