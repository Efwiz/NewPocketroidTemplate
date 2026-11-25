package org.jetbrains.kotlin.scripting.compiler.plugin.dependencies;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptsCompilationDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0019B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0002\u0010\tJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies;", "", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "sources", "Lorg/jetbrains/kotlin/psi/KtFile;", "sourceDependencies", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies$SourceDependencies;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getClasspath", "()Ljava/util/List;", "getSourceDependencies", "getSources", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "SourceDependencies", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptsCompilationDependencies {
    private final List<File> classpath;
    private final List<SourceDependencies> sourceDependencies;
    private final List<KtFile> sources;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ScriptsCompilationDependencies copy$default(ScriptsCompilationDependencies scriptsCompilationDependencies, List list, List list2, List list3, int i, Object obj) {
        if ((i & 1) != 0) {
            list = scriptsCompilationDependencies.classpath;
        }
        if ((i & 2) != 0) {
            list2 = scriptsCompilationDependencies.sources;
        }
        if ((i & 4) != 0) {
            list3 = scriptsCompilationDependencies.sourceDependencies;
        }
        return scriptsCompilationDependencies.copy(list, list2, list3);
    }

    public final List<File> component1() {
        return this.classpath;
    }

    public final List<KtFile> component2() {
        return this.sources;
    }

    public final List<SourceDependencies> component3() {
        return this.sourceDependencies;
    }

    public final ScriptsCompilationDependencies copy(List<? extends File> classpath, List<? extends KtFile> sources, List<SourceDependencies> sourceDependencies) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(sources, "sources");
        Intrinsics.checkNotNullParameter(sourceDependencies, "sourceDependencies");
        return new ScriptsCompilationDependencies(classpath, sources, sourceDependencies);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptsCompilationDependencies)) {
            return false;
        }
        ScriptsCompilationDependencies scriptsCompilationDependencies = (ScriptsCompilationDependencies) other;
        return Intrinsics.areEqual(this.classpath, scriptsCompilationDependencies.classpath) && Intrinsics.areEqual(this.sources, scriptsCompilationDependencies.sources) && Intrinsics.areEqual(this.sourceDependencies, scriptsCompilationDependencies.sourceDependencies);
    }

    public int hashCode() {
        return (((this.classpath.hashCode() * 31) + this.sources.hashCode()) * 31) + this.sourceDependencies.hashCode();
    }

    public String toString() {
        return "ScriptsCompilationDependencies(classpath=" + this.classpath + ", sources=" + this.sources + ", sourceDependencies=" + this.sourceDependencies + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ScriptsCompilationDependencies(List<? extends File> classpath, List<? extends KtFile> sources, List<SourceDependencies> sourceDependencies) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(sources, "sources");
        Intrinsics.checkNotNullParameter(sourceDependencies, "sourceDependencies");
        this.classpath = classpath;
        this.sources = sources;
        this.sourceDependencies = sourceDependencies;
    }

    public final List<File> getClasspath() {
        return this.classpath;
    }

    public final List<KtFile> getSources() {
        return this.sources;
    }

    public final List<SourceDependencies> getSourceDependencies() {
        return this.sourceDependencies;
    }

    /* JADX INFO: compiled from: ScriptsCompilationDependencies.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00060\u0005HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/dependencies/ScriptsCompilationDependencies$SourceDependencies;", "", "scriptFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "sourceDependencies", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "", "(Lorg/jetbrains/kotlin/psi/KtFile;Lkotlin/script/experimental/api/ResultWithDiagnostics;)V", "getScriptFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getSourceDependencies", "()Lkotlin/script/experimental/api/ResultWithDiagnostics;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class SourceDependencies {
        private final KtFile scriptFile;
        private final ResultWithDiagnostics<List<KtFile>> sourceDependencies;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SourceDependencies copy$default(SourceDependencies sourceDependencies, KtFile ktFile, ResultWithDiagnostics resultWithDiagnostics, int i, Object obj) {
            if ((i & 1) != 0) {
                ktFile = sourceDependencies.scriptFile;
            }
            if ((i & 2) != 0) {
                resultWithDiagnostics = sourceDependencies.sourceDependencies;
            }
            return sourceDependencies.copy(ktFile, resultWithDiagnostics);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KtFile getScriptFile() {
            return this.scriptFile;
        }

        public final ResultWithDiagnostics<List<KtFile>> component2() {
            return this.sourceDependencies;
        }

        public final SourceDependencies copy(KtFile scriptFile, ResultWithDiagnostics<? extends List<? extends KtFile>> sourceDependencies) {
            Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
            Intrinsics.checkNotNullParameter(sourceDependencies, "sourceDependencies");
            return new SourceDependencies(scriptFile, sourceDependencies);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SourceDependencies)) {
                return false;
            }
            SourceDependencies sourceDependencies = (SourceDependencies) other;
            return Intrinsics.areEqual(this.scriptFile, sourceDependencies.scriptFile) && Intrinsics.areEqual(this.sourceDependencies, sourceDependencies.sourceDependencies);
        }

        public int hashCode() {
            return (this.scriptFile.hashCode() * 31) + this.sourceDependencies.hashCode();
        }

        public String toString() {
            return "SourceDependencies(scriptFile=" + this.scriptFile + ", sourceDependencies=" + this.sourceDependencies + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SourceDependencies(KtFile scriptFile, ResultWithDiagnostics<? extends List<? extends KtFile>> sourceDependencies) {
            Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
            Intrinsics.checkNotNullParameter(sourceDependencies, "sourceDependencies");
            this.scriptFile = scriptFile;
            this.sourceDependencies = sourceDependencies;
        }

        public final KtFile getScriptFile() {
            return this.scriptFile;
        }

        public final ResultWithDiagnostics<List<KtFile>> getSourceDependencies() {
            return this.sourceDependencies;
        }
    }
}
