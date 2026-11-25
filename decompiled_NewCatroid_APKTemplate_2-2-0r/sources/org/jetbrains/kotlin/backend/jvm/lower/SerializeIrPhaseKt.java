package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLowerKt;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: SerializeIrPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"serializeIrPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getSerializeIrPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SerializeIrPhaseKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> serializeIrPhase = PhaseBuildersKt.makeCustomPhase(new Function2<JvmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.SerializeIrPhaseKt$serializeIrPhase$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(JvmBackendContext jvmBackendContext, IrModuleFragment irModuleFragment) {
            invoke2(jvmBackendContext, irModuleFragment);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(JvmBackendContext context, IrModuleFragment irModule) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(irModule, "irModule");
            LowerKt.lower(new SerializeIrPhase(context), irModule);
        }
    }, "SerializeIr", "If specified by compiler options, save serialized IR in class annotations", (504 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(JvmLowerKt.getExpectDeclarationsRemovingPhase()), (504 & 16) != 0 ? SetsKt.emptySet() : null, (504 & 32) != 0 ? SetsKt.emptySet() : null, (504 & 64) != 0 ? SetsKt.emptySet() : null, (504 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null, (504 & 256) != 0 ? 1 : 0);

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getSerializeIrPhase() {
        return serializeIrPhase;
    }
}
