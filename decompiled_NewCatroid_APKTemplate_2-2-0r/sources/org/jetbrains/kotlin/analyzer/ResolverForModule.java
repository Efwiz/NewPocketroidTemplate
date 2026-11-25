package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "componentProvider", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "(Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;Lorg/jetbrains/kotlin/container/ComponentProvider;)V", "getComponentProvider", "()Lorg/jetbrains/kotlin/container/ComponentProvider;", "getPackageFragmentProvider", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolverForModule {
    private final ComponentProvider componentProvider;
    private final PackageFragmentProvider packageFragmentProvider;

    public ResolverForModule(PackageFragmentProvider packageFragmentProvider, ComponentProvider componentProvider) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        Intrinsics.checkNotNullParameter(componentProvider, "componentProvider");
        this.packageFragmentProvider = packageFragmentProvider;
        this.componentProvider = componentProvider;
    }

    public final PackageFragmentProvider getPackageFragmentProvider() {
        return this.packageFragmentProvider;
    }

    public final ComponentProvider getComponentProvider() {
        return this.componentProvider;
    }
}
