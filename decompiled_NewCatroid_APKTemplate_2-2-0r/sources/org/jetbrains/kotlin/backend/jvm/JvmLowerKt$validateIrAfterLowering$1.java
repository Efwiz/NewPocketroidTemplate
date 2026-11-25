package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.lower.IrValidationKt;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JvmLowerKt$validateIrAfterLowering$1 extends FunctionReference implements Function2<JvmBackendContext, IrModuleFragment, Unit> {
    public static final JvmLowerKt$validateIrAfterLowering$1 INSTANCE = new JvmLowerKt$validateIrAfterLowering$1();

    JvmLowerKt$validateIrAfterLowering$1() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "validateIr(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "validateIr";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(IrValidationKt.class, "backend.jvm.lower");
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(JvmBackendContext jvmBackendContext, IrModuleFragment irModuleFragment) {
        invoke2(jvmBackendContext, irModuleFragment);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(JvmBackendContext p0, IrModuleFragment p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        IrValidationKt.validateIr(p0, p1);
    }
}
