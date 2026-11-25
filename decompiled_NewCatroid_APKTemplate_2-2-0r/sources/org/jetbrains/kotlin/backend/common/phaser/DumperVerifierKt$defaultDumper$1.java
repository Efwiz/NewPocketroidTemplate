package org.jetbrains.kotlin.backend.common.phaser;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: DumperVerifier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DumperVerifierKt$defaultDumper$1 extends FunctionReference implements Function3<ActionState, IrElement, Object, String> {
    public static final DumperVerifierKt$defaultDumper$1 INSTANCE = new DumperVerifierKt$defaultDumper$1();

    DumperVerifierKt$defaultDumper$1() {
        super(3);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "dumpIrElement(Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Object;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "dumpIrElement";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(DumperVerifierKt.class, "ir.backend.common");
    }

    @Override // kotlin.jvm.functions.Function3
    public final String invoke(ActionState p0, IrElement p1, Object obj) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return DumperVerifierKt.dumpIrElement(p0, p1, obj);
    }
}
