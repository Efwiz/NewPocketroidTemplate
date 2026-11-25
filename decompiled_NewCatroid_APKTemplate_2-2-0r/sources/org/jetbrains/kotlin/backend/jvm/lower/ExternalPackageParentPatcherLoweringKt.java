package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: ExternalPackageParentPatcherLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0002H\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"externalPackageParentPatcherPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getExternalPackageParentPatcherPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "createLowering", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExternalPackageParentPatcherLoweringKt {
    private static final NamedCompilerPhase<CommonBackendContext, IrModuleFragment> externalPackageParentPatcherPhase = PhaseBuildersKt.makeIrModulePhase(ExternalPackageParentPatcherLoweringKt$externalPackageParentPatcherPhase$1.INSTANCE, "ExternalPackageParentPatcherLowering", "Replace parent from package fragment to FileKt class for top-level callables (K2 only)", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<CommonBackendContext, IrModuleFragment> getExternalPackageParentPatcherPhase() {
        return externalPackageParentPatcherPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FileLoweringPass createLowering(CommonBackendContext commonBackendContext) {
        if (!(commonBackendContext instanceof JvmBackendContext)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Boolean bool = (Boolean) commonBackendContext.getConfiguration().get(CommonConfigurationKeys.USE_FIR);
        if (Intrinsics.areEqual((Object) bool, (Object) true)) {
            return new ExternalPackageParentPatcherLowering((JvmBackendContext) commonBackendContext);
        }
        if (Intrinsics.areEqual((Object) bool, (Object) false) || bool == null) {
            return FileLoweringPass.Empty.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
