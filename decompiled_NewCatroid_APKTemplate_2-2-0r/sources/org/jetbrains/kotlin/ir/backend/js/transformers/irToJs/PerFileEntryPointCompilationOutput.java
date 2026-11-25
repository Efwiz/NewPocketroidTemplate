package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarationsKt;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/PerFileEntryPointCompilationOutput;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "()V", "jsProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getJsProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "tsDefinitions", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "getTsDefinitions-J7-OZfo", "()Ljava/lang/String;", "Ljava/lang/String;", "writeAll", "", "Ljava/io/File;", ModuleXmlParser.OUTPUT_DIR, "outputName", "", "genDTS", "", "moduleName", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "writeJsCode", "", "outputJsFile", "outputJsMapFile", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PerFileEntryPointCompilationOutput extends CompilationOutputs {
    private final JsProgram jsProgram;
    private final String tsDefinitions;

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public void writeJsCode(File outputJsFile, File outputJsMapFile) {
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        Intrinsics.checkNotNullParameter(outputJsMapFile, "outputJsMapFile");
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

    @Override // org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs
    public Collection<File> writeAll(File outputDir, String outputName, boolean genDTS, String moduleName, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(outputName, "outputName");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Set<File> setCreateWrittenFilesContainer = createWrittenFilesContainer();
        Iterator<T> it = getDependencies().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            String str = (String) pair.component1();
            CompilationOutputs compilationOutputs = (CompilationOutputs) pair.component2();
            File fileResolve = FilesKt.resolve(outputDir, str + CompilationOutputsKt.getExtension(moduleKind));
            fileResolve.getParentFile().mkdirs();
            File mapForJsFile = getMapForJsFile(fileResolve);
            File normalizedAbsoluteFile = getNormalizedAbsoluteFile(fileResolve);
            File dtsForJsFile = getDtsForJsFile(normalizedAbsoluteFile);
            compilationOutputs.writeJsCode(normalizedAbsoluteFile, mapForJsFile);
            Set<File> set = setCreateWrittenFilesContainer;
            set.add(normalizedAbsoluteFile);
            set.add(mapForJsFile);
            String tsDefinitions = compilationOutputs.getTsDefinitions();
            TypeScriptFragment typeScriptFragmentM7089boximpl = tsDefinitions != null ? TypeScriptFragment.m7089boximpl(tsDefinitions) : null;
            if (typeScriptFragmentM7089boximpl != null) {
                typeScriptFragmentM7089boximpl.m7095unboximpl();
            }
            if (!genDTS) {
                typeScriptFragmentM7089boximpl = null;
            }
            String strM7095unboximpl = typeScriptFragmentM7089boximpl != null ? typeScriptFragmentM7089boximpl.m7095unboximpl() : null;
            if (strM7095unboximpl != null) {
                FilesKt.writeText$default(dtsForJsFile, ExportModelToTsDeclarationsKt.toTypeScript(CollectionsKt.listOf(TypeScriptFragment.m7089boximpl((strM7095unboximpl != null ? TypeScriptFragment.m7089boximpl(strM7095unboximpl) : null).m7095unboximpl())), str, moduleKind), null, 2, null);
                set.add(dtsForJsFile);
            }
        }
        deleteNonWrittenFiles(outputDir, setCreateWrittenFilesContainer);
        return setCreateWrittenFilesContainer;
    }
}
