package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class ScriptLoweringKt$addAnonymousInitializer$1 extends Lambda implements Function1<IrFunctionBuilder, Unit> {
    public static final ScriptLoweringKt$addAnonymousInitializer$1 INSTANCE = new ScriptLoweringKt$addAnonymousInitializer$1();

    public ScriptLoweringKt$addAnonymousInitializer$1() {
        super(1);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrFunctionBuilder irFunctionBuilder) {
        Intrinsics.checkNotNullParameter(irFunctionBuilder, "$this$null");
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
        invoke2(irFunctionBuilder);
        return Unit.INSTANCE;
    }
}
