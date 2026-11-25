package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;

/* JADX INFO: compiled from: PropertyReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class PropertyReferenceLoweringKt$propertyReferencePhase$1 extends FunctionReference implements Function1<JvmBackendContext, PropertyReferenceLowering> {
    public static final PropertyReferenceLoweringKt$propertyReferencePhase$1 INSTANCE = new PropertyReferenceLoweringKt$propertyReferencePhase$1();

    PropertyReferenceLoweringKt$propertyReferencePhase$1() {
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
        return Reflection.getOrCreateKotlinClass(PropertyReferenceLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PropertyReferenceLowering invoke(JvmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new PropertyReferenceLowering(p0);
    }
}
