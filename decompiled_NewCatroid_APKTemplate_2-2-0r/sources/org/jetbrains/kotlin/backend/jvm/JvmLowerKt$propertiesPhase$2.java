package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.lower.PropertiesLowering;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JvmLowerKt$propertiesPhase$2 extends FunctionReference implements Function1<IrFile, Unit> {
    JvmLowerKt$propertiesPhase$2(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "checkNoProperties(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "checkNoProperties";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(PropertiesLowering.Companion.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IrFile irFile) {
        invoke2(irFile);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrFile p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((PropertiesLowering.Companion) this.receiver).checkNoProperties(p0);
    }
}
