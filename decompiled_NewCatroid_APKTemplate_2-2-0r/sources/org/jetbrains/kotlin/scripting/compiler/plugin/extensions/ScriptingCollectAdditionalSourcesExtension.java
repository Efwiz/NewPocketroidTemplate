package org.jetbrains.kotlin.scripting.compiler.plugin.extensions;

import java.io.File;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.extensions.CollectAdditionalSourcesExtension;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependencies;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependenciesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingCollectAdditionalSourcesExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/extensions/ScriptingCollectAdditionalSourcesExtension;", "Lorg/jetbrains/kotlin/extensions/CollectAdditionalSourcesExtension;", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "(Lcom/intellij/mock/MockProject;)V", "getProject", "()Lcom/intellij/mock/MockProject;", "collectAdditionalSourcesAndUpdateConfiguration", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "knownSources", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingCollectAdditionalSourcesExtension implements CollectAdditionalSourcesExtension {
    private final MockProject project;

    public ScriptingCollectAdditionalSourcesExtension(MockProject project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    public final MockProject getProject() {
        return this.project;
    }

    @Override // org.jetbrains.kotlin.extensions.CollectAdditionalSourcesExtension
    public Collection<KtFile> collectAdditionalSourcesAndUpdateConfiguration(Collection<? extends KtFile> knownSources, CompilerConfiguration configuration, Project project) {
        Intrinsics.checkNotNullParameter(knownSources, "knownSources");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        ScriptsCompilationDependencies scriptsCompilationDependenciesCollectScriptsCompilationDependencies$default = ScriptsCompilationDependenciesKt.collectScriptsCompilationDependencies$default(configuration, project, knownSources, null, 8, null);
        List<File> listComponent1 = scriptsCompilationDependenciesCollectScriptsCompilationDependencies$default.component1();
        List<KtFile> listComponent2 = scriptsCompilationDependenciesCollectScriptsCompilationDependencies$default.component2();
        JvmContentRootsKt.addJvmClasspathRoots(configuration, listComponent1);
        return listComponent2;
    }
}
