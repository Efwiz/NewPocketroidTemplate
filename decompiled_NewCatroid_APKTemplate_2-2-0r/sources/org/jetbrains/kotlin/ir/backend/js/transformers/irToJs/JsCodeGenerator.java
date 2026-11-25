package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsCodeGenerator;", "", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgram;", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "mainModuleName", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "sourceMapsInfo", "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgram;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;)V", "generateJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "relativeRequirePath", "", "outJsProgram", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCodeGenerator {
    private final JsGenerationGranularity granularity;
    private final String mainModuleName;
    private final ModuleKind moduleKind;
    private final JsIrProgram program;
    private final SourceMapsInfo sourceMapsInfo;

    public JsCodeGenerator(JsIrProgram program, JsGenerationGranularity granularity, String mainModuleName, ModuleKind moduleKind, SourceMapsInfo sourceMapsInfo) {
        Intrinsics.checkNotNullParameter(program, "program");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        this.program = program;
        this.granularity = granularity;
        this.mainModuleName = mainModuleName;
        this.moduleKind = moduleKind;
        this.sourceMapsInfo = sourceMapsInfo;
    }

    public final CompilationOutputsBuilt generateJsCode(boolean relativeRequirePath, boolean outJsProgram) {
        return IrModuleToJsTransformerKt.generateWrappedModuleBody(this.granularity, this.mainModuleName, this.moduleKind, this.program, this.sourceMapsInfo, relativeRequirePath, outJsProgram);
    }
}
