package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: VfsBasedProjectEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u0002\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0003*\u00020\u0001¨\u0006\b"}, d2 = {"asPsiSearchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "toAbstractProjectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "toAbstractProjectFileSearchScope", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VfsBasedProjectEnvironmentKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final GlobalSearchScope asPsiSearchScope(AbstractProjectFileSearchScope abstractProjectFileSearchScope) {
        if (abstractProjectFileSearchScope == AbstractProjectFileSearchScope.EMPTY.INSTANCE) {
            return GlobalSearchScope.EMPTY_SCOPE;
        }
        if (abstractProjectFileSearchScope == AbstractProjectFileSearchScope.ANY.INSTANCE) {
            return GlobalSearchScope.notScope(GlobalSearchScope.EMPTY_SCOPE);
        }
        Intrinsics.checkNotNull(abstractProjectFileSearchScope, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.PsiBasedProjectFileSearchScope");
        return ((PsiBasedProjectFileSearchScope) abstractProjectFileSearchScope).getPsiSearchScope();
    }

    public static final AbstractProjectEnvironment toAbstractProjectEnvironment(final KotlinCoreEnvironment kotlinCoreEnvironment) {
        Intrinsics.checkNotNullParameter(kotlinCoreEnvironment, "<this>");
        Project project = kotlinCoreEnvironment.getProject();
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        Intrinsics.checkNotNullExpressionValue(fileSystem, "getFileSystem(...)");
        return new VfsBasedProjectEnvironment(project, fileSystem, new Function1<GlobalSearchScope, PackagePartProvider>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironmentKt.toAbstractProjectEnvironment.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PackagePartProvider invoke(GlobalSearchScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return kotlinCoreEnvironment.createPackagePartProvider(it);
            }
        });
    }

    public static final AbstractProjectFileSearchScope toAbstractProjectFileSearchScope(GlobalSearchScope globalSearchScope) {
        Intrinsics.checkNotNullParameter(globalSearchScope, "<this>");
        return new PsiBasedProjectFileSearchScope(globalSearchScope);
    }
}
