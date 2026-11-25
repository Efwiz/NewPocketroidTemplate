package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.wasm.lower.GenerateMainFunctionCallsKt;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$addMainFunctionCallsLowering$1 extends FunctionReference implements Function2<WasmBackendContext, IrModuleFragment, Unit> {
    public static final WasmLoweringPhasesKt$addMainFunctionCallsLowering$1 INSTANCE = new WasmLoweringPhasesKt$addMainFunctionCallsLowering$1();

    WasmLoweringPhasesKt$addMainFunctionCallsLowering$1() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "generateMainFunctionCalls(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "generateMainFunctionCalls";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(GenerateMainFunctionCallsKt.class, "backend.wasm");
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
        invoke2(wasmBackendContext, irModuleFragment);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(WasmBackendContext p0, IrModuleFragment p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        GenerateMainFunctionCallsKt.generateMainFunctionCalls(p0, p1);
    }
}
