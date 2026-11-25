package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R:\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0003\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/Lowering;", "", "name", "", "(Ljava/lang/String;)V", "modulePhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getModulePhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "getName", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/ir/backend/js/BodyLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/DeclarationLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/ModuleLowering;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Lowering {
    private final String name;

    public /* synthetic */ Lowering(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public abstract NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> getModulePhase();

    private Lowering(String str) {
        this.name = str;
    }

    public final String getName() {
        return this.name;
    }
}
