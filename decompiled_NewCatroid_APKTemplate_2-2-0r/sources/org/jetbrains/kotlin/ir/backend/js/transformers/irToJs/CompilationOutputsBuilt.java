package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "rawJsCode", "", "sourceMap", "tsDefinitions", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "jsProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getJsProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getTsDefinitions-J7-OZfo", "()Ljava/lang/String;", "Ljava/lang/String;", "writeJsCode", "", "outputJsFile", "Ljava/io/File;", "outputJsMapFile", "writeJsCodeIntoModuleCache", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuiltForCache;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilationOutputsBuilt extends CompilationOutputs {
    private final JsProgram jsProgram;
    private final String rawJsCode;
    private final String sourceMap;
    private final String tsDefinitions;

    public /* synthetic */ CompilationOutputsBuilt(String str, String str2, String str3, JsProgram jsProgram, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, jsProgram);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    /* JADX INFO: renamed from: getTsDefinitions-J7-OZfo, reason: from getter */
    public String getTsDefinitions() {
        return this.tsDefinitions;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public JsProgram getJsProgram() {
        return this.jsProgram;
    }

    private CompilationOutputsBuilt(String rawJsCode, String str, String str2, JsProgram jsProgram) {
        Intrinsics.checkNotNullParameter(rawJsCode, "rawJsCode");
        this.rawJsCode = rawJsCode;
        this.sourceMap = str;
        this.tsDefinitions = str2;
        this.jsProgram = jsProgram;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0019  */
    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public void writeJsCode(File outputJsFile, File outputJsMapFile) {
        String strAsSourceMappingUrl;
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        Intrinsics.checkNotNullParameter(outputJsMapFile, "outputJsMapFile");
        String str = this.sourceMap;
        if (str != null) {
            FilesKt.writeText$default(outputJsMapFile, str, null, 2, null);
            strAsSourceMappingUrl = CompilationOutputsKt.asSourceMappingUrl(outputJsMapFile);
            if (strAsSourceMappingUrl == null) {
                strAsSourceMappingUrl = "";
            }
        } else {
            strAsSourceMappingUrl = "";
        }
        FilesKt.writeText$default(outputJsFile, this.rawJsCode + strAsSourceMappingUrl, null, 2, null);
    }

    public final CompilationOutputsBuiltForCache writeJsCodeIntoModuleCache(File outputJsFile, File outputJsMapFile) {
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        String str = this.sourceMap;
        if (str != null && outputJsMapFile != null) {
            FilesKt.writeText$default(outputJsMapFile, str, null, 2, null);
        }
        FilesKt.writeText$default(outputJsFile, this.rawJsCode, null, 2, null);
        return new CompilationOutputsBuiltForCache(outputJsFile, outputJsMapFile, this);
    }
}
