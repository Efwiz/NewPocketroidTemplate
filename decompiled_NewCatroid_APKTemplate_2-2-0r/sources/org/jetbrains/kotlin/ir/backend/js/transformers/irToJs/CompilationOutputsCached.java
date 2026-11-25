package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsCached;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "jsCodeFile", "Ljava/io/File;", "sourceMapFile", "tsDefinitionsFile", "(Ljava/io/File;Ljava/io/File;Ljava/io/File;)V", "jsProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getJsProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "tsDefinitions", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "getTsDefinitions-J7-OZfo", "()Ljava/lang/String;", "writeJsCode", "", "outputJsFile", "outputJsMapFile", "isUpdateRequired", "", "target", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilationOutputsCached extends CompilationOutputs {
    private final File jsCodeFile;
    private final File sourceMapFile;
    private final File tsDefinitionsFile;

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public JsProgram getJsProgram() {
        return null;
    }

    public CompilationOutputsCached(File jsCodeFile, File file, File file2) {
        Intrinsics.checkNotNullParameter(jsCodeFile, "jsCodeFile");
        this.jsCodeFile = jsCodeFile;
        this.sourceMapFile = file;
        this.tsDefinitionsFile = file2;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    /* JADX INFO: renamed from: getTsDefinitions-J7-OZfo */
    public String getTsDefinitions() {
        File file = this.tsDefinitionsFile;
        if (file != null) {
            return TypeScriptFragment.m7090constructorimpl(FilesKt.readText$default(file, null, 1, null));
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0026  */
    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public void writeJsCode(File outputJsFile, File outputJsMapFile) {
        String strAsSourceMappingUrl;
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        Intrinsics.checkNotNullParameter(outputJsMapFile, "outputJsMapFile");
        File file = this.sourceMapFile;
        if (file != null) {
            if (isUpdateRequired(file, outputJsMapFile)) {
                FilesKt.copyTo$default(file, outputJsMapFile, true, 0, 4, null);
                CompilationOutputsKt.copyModificationTimeFrom(file, outputJsMapFile);
            }
            strAsSourceMappingUrl = CompilationOutputsKt.asSourceMappingUrl(outputJsMapFile);
            if (strAsSourceMappingUrl == null) {
                strAsSourceMappingUrl = "";
            }
        } else {
            strAsSourceMappingUrl = "";
        }
        if (isUpdateRequired(this.jsCodeFile, outputJsFile)) {
            FilesKt.writeText$default(outputJsFile, FilesKt.readText$default(this.jsCodeFile, null, 1, null) + strAsSourceMappingUrl, null, 2, null);
            CompilationOutputsKt.copyModificationTimeFrom(this.jsCodeFile, outputJsFile);
        }
    }

    private final boolean isUpdateRequired(File file, File file2) {
        long jLastModified = file.lastModified();
        long jLastModified2 = file2.lastModified();
        return jLastModified <= 0 || jLastModified2 <= 0 || jLastModified2 > jLastModified;
    }
}
