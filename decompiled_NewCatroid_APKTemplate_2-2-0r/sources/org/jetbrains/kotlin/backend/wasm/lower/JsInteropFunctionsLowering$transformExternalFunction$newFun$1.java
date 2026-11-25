package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsInteropFunctionsLowering$transformExternalFunction$newFun$1 extends FunctionReference implements Function3<IrFunction, IrFunction, Map<IrValueParameter, ? extends IrValueParameter>, Unit> {
    JsInteropFunctionsLowering$transformExternalFunction$newFun$1(Object obj) {
        super(3, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "remapMultiFieldValueClassStructure(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Ljava/util/Map;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "remapMultiFieldValueClassStructure";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(WasmBackendContext.class);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction, IrFunction irFunction2, Map<IrValueParameter, ? extends IrValueParameter> map) {
        invoke2(irFunction, irFunction2, map);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrFunction p0, IrFunction p1, Map<IrValueParameter, ? extends IrValueParameter> map) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        ((WasmBackendContext) this.receiver).remapMultiFieldValueClassStructure(p0, p1, map);
    }
}
