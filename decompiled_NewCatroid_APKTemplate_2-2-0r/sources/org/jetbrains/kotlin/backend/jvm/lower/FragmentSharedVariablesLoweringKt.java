package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: FragmentSharedVariablesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"3\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"fragmentSharedVariablesLowering", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getFragmentSharedVariablesLowering$annotations", "()V", "getFragmentSharedVariablesLowering", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FragmentSharedVariablesLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> fragmentSharedVariablesLowering = PhaseBuildersKt.makeIrModulePhase(FragmentSharedVariablesLoweringKt$fragmentSharedVariablesLowering$1.INSTANCE, "FragmentSharedVariablesLowering", "Promotes captured variables that are modified by the fragment to shared variables", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static /* synthetic */ void getFragmentSharedVariablesLowering$annotations() {
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getFragmentSharedVariablesLowering() {
        return fragmentSharedVariablesLowering;
    }
}
