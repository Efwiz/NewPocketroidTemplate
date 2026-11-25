package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.CliModuleAnnotationsResolver;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ProjectEnvironmentWithCoreEnvironmentEmulation;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/VfsBasedProjectEnvironment;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "localFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "getPackagePartProviderFn", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "initialRoots", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lcom/intellij/openapi/project/Project;Lcom/intellij/openapi/vfs/VirtualFileSystem;Lkotlin/jvm/functions/Function1;Ljava/util/List;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getInitialRoots", "()Ljava/util/List;", "packagePartProviders", "", "Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;", "getPackagePartProviders", "getPackagePartProvider", "fileSearchScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ProjectEnvironmentWithCoreEnvironmentEmulation extends VfsBasedProjectEnvironment {
    private final CompilerConfiguration configuration;
    private final List<JavaRoot> initialRoots;
    private final List<JvmPackagePartProvider> packagePartProviders;

    public final List<JavaRoot> getInitialRoots() {
        return this.initialRoots;
    }

    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProjectEnvironmentWithCoreEnvironmentEmulation(Project project, VirtualFileSystem localFileSystem, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> getPackagePartProviderFn, List<JavaRoot> initialRoots, CompilerConfiguration configuration) {
        super(project, localFileSystem, getPackagePartProviderFn);
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(localFileSystem, "localFileSystem");
        Intrinsics.checkNotNullParameter(getPackagePartProviderFn, "getPackagePartProviderFn");
        Intrinsics.checkNotNullParameter(initialRoots, "initialRoots");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.initialRoots = initialRoots;
        this.configuration = configuration;
        this.packagePartProviders = new ArrayList();
    }

    public final List<JvmPackagePartProvider> getPackagePartProviders() {
        return this.packagePartProviders;
    }

    @Override // org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment, org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public PackagePartProvider getPackagePartProvider(AbstractProjectFileSearchScope fileSearchScope) {
        Intrinsics.checkNotNullParameter(fileSearchScope, "fileSearchScope");
        PackagePartProvider packagePartProvider = super.getPackagePartProvider(fileSearchScope);
        JvmPackagePartProvider jvmPackagePartProvider = packagePartProvider instanceof JvmPackagePartProvider ? (JvmPackagePartProvider) packagePartProvider : null;
        if (jvmPackagePartProvider != null) {
            List<JavaRoot> list = this.initialRoots;
            Object notNull = this.configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
            jvmPackagePartProvider.addRoots(list, (MessageCollector) notNull);
            this.packagePartProviders.add(jvmPackagePartProvider);
            ModuleAnnotationsResolver companion = ModuleAnnotationsResolver.INSTANCE.getInstance(getProject());
            Intrinsics.checkNotNull(companion, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.CliModuleAnnotationsResolver");
            ((CliModuleAnnotationsResolver) companion).addPackagePartProvider(jvmPackagePartProvider);
        }
        return packagePartProvider;
    }
}
