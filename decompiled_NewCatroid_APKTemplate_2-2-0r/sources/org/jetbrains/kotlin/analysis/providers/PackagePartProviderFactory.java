package org.jetbrains.kotlin.analysis.providers;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;

/* JADX INFO: compiled from: PackagePartProviderFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/PackagePartProviderFactory;", "", "()V", "createPackagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PackagePartProviderFactory {
    public abstract PackagePartProvider createPackagePartProvider(GlobalSearchScope scope);
}
