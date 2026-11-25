package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: IfNullExpressionsFusionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"-\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"ifNullExpressionsFusionPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getIfNullExpressionsFusionPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IfNullExpressionsFusionLoweringKt {
    private static final NamedCompilerPhase<CommonBackendContext, IrFile> ifNullExpressionsFusionPhase = PhaseBuildersKt.makeIrFilePhase(IfNullExpressionsFusionLoweringKt$ifNullExpressionsFusionPhase$1.INSTANCE, "IfNullExpressionsFusionLowering", "Simplify '?.' and '?:' operator chains", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<CommonBackendContext, IrFile> getIfNullExpressionsFusionPhase() {
        return ifNullExpressionsFusionPhase;
    }
}
