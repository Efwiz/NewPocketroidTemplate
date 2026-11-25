package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PerformByIrFileKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService;
import org.jetbrains.kotlin.backend.common.phaser.PhaserState;
import org.jetbrains.kotlin.backend.common.phaser.SameTypeCompilerPhase;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: JvmPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\u001a,\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002\"*\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"0\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"-\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u00048F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\r"}, d2 = {"generateAdditionalClassesPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "jvmCodegenPhases", "getJvmCodegenPhases", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "jvmPhases", "getJvmPhases", "codegenPhase", "generateMultifileFacade", "", "backend.jvm.entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmPhasesKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> generateAdditionalClassesPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> jvmCodegenPhases;

    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> codegenPhase(final boolean z) {
        String str = z ? "MultifileFacades" : "Regular";
        return PerformByIrFileKt.performByIrFile("CodegenByIrFile".concat(str), "Code generation by IrFile".concat(z ? ", multifile facades" : ", regular files"), false, CollectionsKt.listOf(PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmPhasesKt.codegenPhase.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new FileCodegen(context, z);
            }
        }, "Codegen".concat(str), "Code generation", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null)));
    }

    static {
        NamedCompilerPhase<JvmBackendContext, IrModuleFragment> namedCompilerPhase = new NamedCompilerPhase<>("GenerateAdditionalClasses", "Generate additional classes that were requested during codegen", null, new SameTypeCompilerPhase<JvmBackendContext, IrModuleFragment>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmPhasesKt$generateAdditionalClassesPhase$1
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
                return invoke(phaseConfigurationService, (PhaserState<IrModuleFragment>) phaserState, (JvmBackendContext) loggingContext, (IrModuleFragment) obj);
            }

            public IrModuleFragment invoke(PhaseConfigurationService phaseConfig, PhaserState<IrModuleFragment> phaserState, JvmBackendContext context, IrModuleFragment input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(input, "input");
                context.getEnumEntriesIntrinsicMappingsCache().generateMappingsClasses();
                return input;
            }
        }, null, null, null, null, 0, 500, null);
        generateAdditionalClassesPhase = namedCompilerPhase;
        jvmCodegenPhases = new NamedCompilerPhase<>("Codegen", "Code generation", null, PhaseBuildersKt.then(PhaseBuildersKt.then(codegenPhase(true), codegenPhase(false)), namedCompilerPhase), null, null, null, null, 1, 244, null);
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getJvmCodegenPhases() {
        return jvmCodegenPhases;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getJvmPhases() {
        return JvmLowerKt.getJvmLoweringPhases();
    }
}
