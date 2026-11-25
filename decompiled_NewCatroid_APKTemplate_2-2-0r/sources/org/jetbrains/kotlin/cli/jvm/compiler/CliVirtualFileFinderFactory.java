package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinder;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory;

/* JADX INFO: compiled from: CliVirtualFileFinderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliVirtualFileFinderFactory;", "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinderFactory;", "index", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "enableSearchInCtSym", "", "(Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;Z)V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliVirtualFileFinderFactory implements VirtualFileFinderFactory {
    private final boolean enableSearchInCtSym;
    private final JvmDependenciesIndex index;

    public CliVirtualFileFinderFactory(JvmDependenciesIndex index, boolean z) {
        Intrinsics.checkNotNullParameter(index, "index");
        this.index = index;
        this.enableSearchInCtSym = z;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory
    public VirtualFileFinder create(GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new CliVirtualFileFinder(this.index, scope, this.enableSearchInCtSym);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory
    public VirtualFileFinder create(Project project, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(module, "module");
        JvmDependenciesIndex jvmDependenciesIndex = this.index;
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(project);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        return new CliVirtualFileFinder(jvmDependenciesIndex, globalSearchScopeAllScope, this.enableSearchInCtSym);
    }
}
