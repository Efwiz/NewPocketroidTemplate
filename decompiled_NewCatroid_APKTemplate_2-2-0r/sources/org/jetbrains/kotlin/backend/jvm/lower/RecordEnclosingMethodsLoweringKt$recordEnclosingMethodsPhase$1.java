package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;

/* JADX INFO: compiled from: RecordEnclosingMethodsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class RecordEnclosingMethodsLoweringKt$recordEnclosingMethodsPhase$1 extends FunctionReference implements Function1<JvmBackendContext, RecordEnclosingMethodsLowering> {
    public static final RecordEnclosingMethodsLoweringKt$recordEnclosingMethodsPhase$1 INSTANCE = new RecordEnclosingMethodsLoweringKt$recordEnclosingMethodsPhase$1();

    RecordEnclosingMethodsLoweringKt$recordEnclosingMethodsPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(RecordEnclosingMethodsLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final RecordEnclosingMethodsLowering invoke(JvmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new RecordEnclosingMethodsLowering(p0);
    }
}
