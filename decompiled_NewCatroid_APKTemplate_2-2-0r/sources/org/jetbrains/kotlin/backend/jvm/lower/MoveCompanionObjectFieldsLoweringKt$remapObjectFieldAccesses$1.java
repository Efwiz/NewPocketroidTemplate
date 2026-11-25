package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;

/* JADX INFO: compiled from: MoveCompanionObjectFieldsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class MoveCompanionObjectFieldsLoweringKt$remapObjectFieldAccesses$1 extends FunctionReference implements Function1<JvmBackendContext, RemapObjectFieldAccesses> {
    public static final MoveCompanionObjectFieldsLoweringKt$remapObjectFieldAccesses$1 INSTANCE = new MoveCompanionObjectFieldsLoweringKt$remapObjectFieldAccesses$1();

    MoveCompanionObjectFieldsLoweringKt$remapObjectFieldAccesses$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(RemapObjectFieldAccesses.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final RemapObjectFieldAccesses invoke(JvmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new RemapObjectFieldAccesses(p0);
    }
}
