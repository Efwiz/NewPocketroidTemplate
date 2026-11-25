package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$visitVararg$segments$1", f = "WasmVarargExpressionLowering.kt", i = {0, 0, 0, 1, 1}, l = {214, 217, 222}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "currentElements", "tempVar", "$this$sequence", "currentElements"}, s = {"L$0", "L$1", "L$3", "L$0", "L$1"})
final class WasmVarargExpressionLowering$visitVararg$segments$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super WasmVarargExpressionLowering.VarargSegmentBuilder>, Continuation<? super Unit>, Object> {
    final /* synthetic */ List<IrVariable> $elementVars;
    final /* synthetic */ IrVararg $irVararg;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ WasmVarargExpressionLowering this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    WasmVarargExpressionLowering$visitVararg$segments$1(IrVararg irVararg, List<? extends IrVariable> list, WasmVarargExpressionLowering wasmVarargExpressionLowering, Continuation<? super WasmVarargExpressionLowering$visitVararg$segments$1> continuation) {
        super(2, continuation);
        this.$irVararg = irVararg;
        this.$elementVars = list;
        this.this$0 = wasmVarargExpressionLowering;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        WasmVarargExpressionLowering$visitVararg$segments$1 wasmVarargExpressionLowering$visitVararg$segments$1 = new WasmVarargExpressionLowering$visitVararg$segments$1(this.$irVararg, this.$elementVars, this.this$0, continuation);
        wasmVarargExpressionLowering$visitVararg$segments$1.L$0 = obj;
        return wasmVarargExpressionLowering$visitVararg$segments$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super WasmVarargExpressionLowering.VarargSegmentBuilder> sequenceScope, Continuation<? super Unit> continuation) {
        return ((WasmVarargExpressionLowering$visitVararg$segments$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0071  */
    /* JADX WARN: Code duplicated, block: B:23:0x0098  */
    /* JADX WARN: Code duplicated, block: B:25:0x00bd A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:26:0x00be  */
    /* JADX WARN: Code duplicated, block: B:30:0x00e4 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:33:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:35:0x0110 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:38:0x008b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x00e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x0087 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:0x008f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x006b A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00e2 -> B:11:0x002e). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:40:0x0087
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            Method dump skipped, instruction units count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$visitVararg$segments$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
