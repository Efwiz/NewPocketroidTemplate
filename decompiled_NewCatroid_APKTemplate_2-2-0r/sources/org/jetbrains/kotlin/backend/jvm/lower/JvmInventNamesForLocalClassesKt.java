package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLowerKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: JvmInventNamesForLocalClasses.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"-\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"-\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"inventNamesForInlinedLocalClassesPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getInventNamesForInlinedLocalClassesPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "inventNamesForLocalClassesPhase", "getInventNamesForLocalClassesPhase", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmInventNamesForLocalClassesKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> inventNamesForInlinedLocalClassesPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> inventNamesForLocalClassesPhase;

    static {
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInventNamesForLocalClassesKt$inventNamesForLocalClassesPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new JvmInventNamesForLocalClasses(context, false, 2, null);
            }
        }, "InventNamesForLocalClasses", "Invent names for local classes and anonymous objects", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(MainMethodGenerationLoweringKt.getMainMethodGenerationPhase()), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        inventNamesForLocalClassesPhase = namedCompilerPhaseMakeIrFilePhase;
        inventNamesForInlinedLocalClassesPhase = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmInventNamesForLocalClassesKt$inventNamesForInlinedLocalClassesPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return !JvmLowerKt.irInlinerIsEnabled(context) ? FileLoweringPass.Empty.INSTANCE : new JvmInventNamesForInlinedAnonymousObjects(context);
            }
        }, "InventNamesForInlinedLocalClasses", "Invent names for INLINED local classes and anonymous objects", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeIrFilePhase, RemoveDuplicatedInlinedLocalClassesLoweringKt.getRemoveDuplicatedInlinedLocalClasses()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInventNamesForLocalClassesPhase() {
        return inventNamesForLocalClassesPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInventNamesForInlinedLocalClassesPhase() {
        return inventNamesForInlinedLocalClassesPhase;
    }
}
