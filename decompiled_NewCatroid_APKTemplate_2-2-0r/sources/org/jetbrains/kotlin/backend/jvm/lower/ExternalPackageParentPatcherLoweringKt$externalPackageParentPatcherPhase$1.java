package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;

/* JADX INFO: compiled from: ExternalPackageParentPatcherLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ExternalPackageParentPatcherLoweringKt$externalPackageParentPatcherPhase$1 extends FunctionReference implements Function1<CommonBackendContext, FileLoweringPass> {
    public static final ExternalPackageParentPatcherLoweringKt$externalPackageParentPatcherPhase$1 INSTANCE = new ExternalPackageParentPatcherLoweringKt$externalPackageParentPatcherPhase$1();

    ExternalPackageParentPatcherLoweringKt$externalPackageParentPatcherPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createLowering(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "createLowering";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(ExternalPackageParentPatcherLoweringKt.class, "backend.jvm.lower");
    }

    @Override // kotlin.jvm.functions.Function1
    public final FileLoweringPass invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ExternalPackageParentPatcherLoweringKt.createLowering(p0);
    }
}
