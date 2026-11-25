package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleContent;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.PlatformAnalysisParameters;
import org.jetbrains.kotlin.analyzer.ResolverForModule;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;

/* JADX INFO: compiled from: JvmResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001Bg\u0012\u0016\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0003\u0012\u001e\b\u0002\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\n\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\u0003¢\u0006\u0002\u0010\u000eR\u001f\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R!\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R'\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformParameters;", "Lorg/jetbrains/kotlin/analyzer/PlatformAnalysisParameters;", "packagePartProviderFactory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "moduleByJavaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "resolverForReferencedModule", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "useBuiltinsProviderForModule", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "getModuleByJavaClass", "()Lkotlin/jvm/functions/Function1;", "getPackagePartProviderFactory", "getResolverForReferencedModule", "()Lkotlin/jvm/functions/Function2;", "getUseBuiltinsProviderForModule", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformParameters implements PlatformAnalysisParameters {
    private final Function1<JavaClass, ModuleInfo> moduleByJavaClass;
    private final Function1<ModuleContent<?>, PackagePartProvider> packagePartProviderFactory;
    private final Function2<ModuleInfo, ModuleInfo, ResolverForModule> resolverForReferencedModule;
    private final Function1<ModuleInfo, Boolean> useBuiltinsProviderForModule;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmPlatformParameters(Function1<? super ModuleContent<?>, ? extends PackagePartProvider> packagePartProviderFactory, Function1<? super JavaClass, ? extends ModuleInfo> moduleByJavaClass, Function2<? super ModuleInfo, ? super ModuleInfo, ResolverForModule> function2, Function1<? super ModuleInfo, Boolean> useBuiltinsProviderForModule) {
        Intrinsics.checkNotNullParameter(packagePartProviderFactory, "packagePartProviderFactory");
        Intrinsics.checkNotNullParameter(moduleByJavaClass, "moduleByJavaClass");
        Intrinsics.checkNotNullParameter(useBuiltinsProviderForModule, "useBuiltinsProviderForModule");
        this.packagePartProviderFactory = packagePartProviderFactory;
        this.moduleByJavaClass = moduleByJavaClass;
        this.resolverForReferencedModule = function2;
        this.useBuiltinsProviderForModule = useBuiltinsProviderForModule;
    }

    public /* synthetic */ JvmPlatformParameters(Function1 function1, Function1 function2, Function2 function3, Function1 function4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, function2, (i & 4) != 0 ? null : function3, function4);
    }

    public final Function1<ModuleContent<?>, PackagePartProvider> getPackagePartProviderFactory() {
        return this.packagePartProviderFactory;
    }

    public final Function1<JavaClass, ModuleInfo> getModuleByJavaClass() {
        return this.moduleByJavaClass;
    }

    public final Function2<ModuleInfo, ModuleInfo, ResolverForModule> getResolverForReferencedModule() {
        return this.resolverForReferencedModule;
    }

    public final Function1<ModuleInfo, Boolean> getUseBuiltinsProviderForModule() {
        return this.useBuiltinsProviderForModule;
    }
}
