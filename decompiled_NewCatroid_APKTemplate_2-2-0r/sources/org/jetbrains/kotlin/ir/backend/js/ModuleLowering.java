package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012.\u0010\u0004\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\t¢\u0006\u0002\u0010\nR<\u0010\u0004\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ModuleLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/Lowering;", "name", "", "modulePhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;)V", "getModulePhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleLowering extends Lowering {
    private final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> modulePhase;

    @Override // org.jetbrains.kotlin.ir.backend.js.Lowering
    public NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> getModulePhase() {
        return this.modulePhase;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ModuleLowering(String name2, NamedCompilerPhase<? super JsIrBackendContext, Iterable<IrModuleFragment>> modulePhase) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(modulePhase, "modulePhase");
        this.modulePhase = modulePhase;
    }
}
