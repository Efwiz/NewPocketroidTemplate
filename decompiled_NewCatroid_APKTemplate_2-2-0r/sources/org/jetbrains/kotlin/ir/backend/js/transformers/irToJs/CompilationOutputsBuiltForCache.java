package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuiltForCache;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "jsCodeFile", "Ljava/io/File;", "sourceMapFile", "outputBuilt", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "(Ljava/io/File;Ljava/io/File;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;)V", "jsProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getJsProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "tsDefinitions", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "getTsDefinitions-J7-OZfo", "()Ljava/lang/String;", "writeJsCode", "", "outputJsFile", "outputJsMapFile", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilationOutputsBuiltForCache extends CompilationOutputs {
    private final File jsCodeFile;
    private final CompilationOutputsBuilt outputBuilt;
    private final File sourceMapFile;

    public CompilationOutputsBuiltForCache(File jsCodeFile, File file, CompilationOutputsBuilt outputBuilt) {
        Intrinsics.checkNotNullParameter(jsCodeFile, "jsCodeFile");
        Intrinsics.checkNotNullParameter(outputBuilt, "outputBuilt");
        this.jsCodeFile = jsCodeFile;
        this.sourceMapFile = file;
        this.outputBuilt = outputBuilt;
        setDependencies(outputBuilt.getDependencies());
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    /* JADX INFO: renamed from: getTsDefinitions-J7-OZfo */
    public String getTsDefinitions() {
        return this.outputBuilt.getTsDefinitions();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public JsProgram getJsProgram() {
        return this.outputBuilt.getJsProgram();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public void writeJsCode(File outputJsFile, File outputJsMapFile) {
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        Intrinsics.checkNotNullParameter(outputJsMapFile, "outputJsMapFile");
        this.outputBuilt.writeJsCode(outputJsFile, outputJsMapFile);
        CompilationOutputsKt.copyModificationTimeFrom(this.jsCodeFile, outputJsFile);
        File file = this.sourceMapFile;
        if (file != null) {
            CompilationOutputsKt.copyModificationTimeFrom(file, outputJsMapFile);
        }
    }
}
