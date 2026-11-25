package org.jetbrains.kotlin.ir.backend.js;

import com.google.android.gms.cast.MediaTrack;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u001c\b\u0002\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\b\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00070\u0006\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\bR\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R<\u0010\r\u001a*\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000ej\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f`\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/BodyLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/Lowering;", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Lkotlin/jvm/functions/Function1;)V", "modulePhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getModulePhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "bodyLowering", "context", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BodyLowering extends Lowering {
    private final Function1<JsIrBackendContext, BodyLoweringPass> factory;
    private final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> modulePhase;

    public /* synthetic */ BodyLowering(String str, String str2, Set set, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? SetsKt.emptySet() : set, function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BodyLowering(String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super JsIrBackendContext, ?, ?>> prerequisite, Function1<? super JsIrBackendContext, ? extends BodyLoweringPass> factory) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.factory = factory;
        this.modulePhase = JsLoweringPhasesKt.makeJsModulePhase(factory, name2, description, prerequisite);
    }

    public final BodyLoweringPass bodyLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return this.factory.invoke(context);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.Lowering
    public NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> getModulePhase() {
        return this.modulePhase;
    }
}
