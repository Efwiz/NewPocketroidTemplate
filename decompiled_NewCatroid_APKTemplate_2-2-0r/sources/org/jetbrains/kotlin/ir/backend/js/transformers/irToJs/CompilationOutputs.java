package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarationsKt;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: CompilationOutputs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u001cJ\u001c\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00142\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00140!J\u0016\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020%J6\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u00042\u0006\u0010\u001f\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)2\u0006\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020%H\u0016J\u0018\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u0014H&R,\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00000\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0014*\u00020\u00148DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u0014*\u00020\u00148DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0018\u0010\u0019\u001a\u00020\u0014*\u00020\u00148DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "", "()V", "dependencies", "", "Lkotlin/Pair;", "", "getDependencies", "()Ljava/util/Collection;", "setDependencies", "(Ljava/util/Collection;)V", "jsProgram", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getJsProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "tsDefinitions", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "getTsDefinitions-J7-OZfo", "()Ljava/lang/String;", "dtsForJsFile", "Ljava/io/File;", "getDtsForJsFile", "(Ljava/io/File;)Ljava/io/File;", "mapForJsFile", "getMapForJsFile", "normalizedAbsoluteFile", "getNormalizedAbsoluteFile", "createWrittenFilesContainer", "", "deleteNonWrittenFiles", "", ModuleXmlParser.OUTPUT_DIR, "writtenFiles", "", "getFullTsDefinition", "moduleName", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "writeAll", "outputName", "genDTS", "", "writeJsCode", "outputJsFile", "outputJsMapFile", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CompilationOutputs {
    private Collection<? extends Pair<String, ? extends CompilationOutputs>> dependencies = CollectionsKt.emptyList();

    public abstract JsProgram getJsProgram();

    /* JADX INFO: renamed from: getTsDefinitions-J7-OZfo, reason: not valid java name */
    public abstract String mo7197getTsDefinitionsJ7OZfo();

    public abstract void writeJsCode(File outputJsFile, File outputJsMapFile);

    public final Collection<Pair<String, CompilationOutputs>> getDependencies() {
        return this.dependencies;
    }

    public final void setDependencies(Collection<? extends Pair<String, ? extends CompilationOutputs>> collection) {
        Intrinsics.checkNotNullParameter(collection, "<set-?>");
        this.dependencies = collection;
    }

    public final Set<File> createWrittenFilesContainer() {
        return new LinkedHashSet(((this.dependencies.size() + 1) * 2) + 1);
    }

    public Collection<File> writeAll(File outputDir, String outputName, boolean genDTS, String moduleName, ModuleKind moduleKind) throws IOException {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(outputName, "outputName");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Set<File> setCreateWrittenFilesContainer = createWrittenFilesContainer();
        Iterator<T> it = this.dependencies.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            writeAll$writeAsJsFile(FilesKt.resolve(outputDir, ((String) pair.component1()) + CompilationOutputsKt.getExtension(moduleKind)), this, setCreateWrittenFilesContainer, (CompilationOutputs) pair.component2());
        }
        File fileResolve = FilesKt.resolve(outputDir, outputName + CompilationOutputsKt.getExtension(moduleKind));
        writeAll$writeAsJsFile(fileResolve, this, setCreateWrittenFilesContainer, this);
        if (genDTS) {
            File dtsForJsFile = getDtsForJsFile(fileResolve);
            FilesKt.writeText$default(dtsForJsFile, getFullTsDefinition(moduleName, moduleKind), null, 2, null);
            setCreateWrittenFilesContainer.add(dtsForJsFile);
        }
        deleteNonWrittenFiles(outputDir, setCreateWrittenFilesContainer);
        return setCreateWrittenFilesContainer;
    }

    private static final void writeAll$writeAsJsFile(File file, CompilationOutputs compilationOutputs, Set<File> set, CompilationOutputs compilationOutputs2) {
        file.getParentFile().mkdirs();
        File mapForJsFile = compilationOutputs.getMapForJsFile(file);
        File normalizedAbsoluteFile = compilationOutputs.getNormalizedAbsoluteFile(file);
        compilationOutputs2.writeJsCode(normalizedAbsoluteFile, mapForJsFile);
        Set<File> set2 = set;
        set2.add(normalizedAbsoluteFile);
        set2.add(mapForJsFile);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs$deleteNonWrittenFiles$3, reason: invalid class name */
    /* JADX INFO: compiled from: CompilationOutputs.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<File, Unit> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "deleteNonWrittenFiles$delete(Ljava/io/File;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "delete";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(File file) {
            invoke2(file);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(File p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            p0.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final File deleteNonWrittenFiles$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (File) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean deleteNonWrittenFiles$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteNonWrittenFiles$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void deleteNonWrittenFiles(final File outputDir, final Set<? extends File> writtenFiles) throws IOException {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(writtenFiles, "writtenFiles");
        Stream<Path> streamWalk = Files.walk(outputDir.toPath(), new FileVisitOption[0]);
        final AnonymousClass1 anonymousClass1 = new Function1<Path, File>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs.deleteNonWrittenFiles.1
            @Override // kotlin.jvm.functions.Function1
            public final File invoke(Path path) {
                return path.toFile();
            }
        };
        Stream<R> map = streamWalk.map(new Function() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompilationOutputs.deleteNonWrittenFiles$lambda$2(anonymousClass1, obj);
            }
        });
        final Function1<File, Boolean> function1 = new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs.deleteNonWrittenFiles.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File file) {
                return Boolean.valueOf((Intrinsics.areEqual(file, outputDir) || writtenFiles.contains(file)) ? false : true);
            }
        };
        Stream streamFilter = map.filter(new Predicate() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CompilationOutputs.deleteNonWrittenFiles$lambda$3(function1, obj);
            }
        });
        final AnonymousClass3 anonymousClass3 = AnonymousClass3.INSTANCE;
        streamFilter.forEach(new Consumer() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CompilationOutputs.deleteNonWrittenFiles$lambda$4(anonymousClass3, obj);
            }
        });
    }

    public final String getFullTsDefinition(String moduleName, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Collection<? extends Pair<String, ? extends CompilationOutputs>> collection = this.dependencies;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String strMo7197getTsDefinitionsJ7OZfo = ((CompilationOutputs) ((Pair) it.next()).getSecond()).mo7197getTsDefinitionsJ7OZfo();
            TypeScriptFragment typeScriptFragmentM7089boximpl = strMo7197getTsDefinitionsJ7OZfo != null ? TypeScriptFragment.m7089boximpl(strMo7197getTsDefinitionsJ7OZfo) : null;
            if (typeScriptFragmentM7089boximpl != null) {
                arrayList.add(typeScriptFragmentM7089boximpl);
            }
        }
        ArrayList arrayList2 = arrayList;
        String strMo7197getTsDefinitionsJ7OZfo2 = mo7197getTsDefinitionsJ7OZfo();
        return ExportModelToTsDeclarationsKt.toTypeScript(CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull(strMo7197getTsDefinitionsJ7OZfo2 != null ? TypeScriptFragment.m7089boximpl(strMo7197getTsDefinitionsJ7OZfo2) : null)), moduleName, moduleKind);
    }

    protected final File getNormalizedAbsoluteFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        File absoluteFile = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        return FilesKt.normalize(absoluteFile);
    }

    protected final File getMapForJsFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return getNormalizedAbsoluteFile(FilesKt.resolveSibling(file, file.getName() + ".map"));
    }

    protected final File getDtsForJsFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return getNormalizedAbsoluteFile(FilesKt.resolveSibling(file, FilesKt.getNameWithoutExtension(file) + ".d.ts"));
    }
}
