package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinPackageProvider;
import org.jetbrains.kotlin.analysis.providers.KotlinPackageProviderFactory;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinStaticPackageProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticPackageProviderFactory;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinPackageProviderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lcom/intellij/openapi/project/Project;Ljava/util/Collection;)V", "createPackageProvider", "Lorg/jetbrains/kotlin/analysis/providers/KotlinPackageProvider;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticPackageProviderFactory extends KotlinPackageProviderFactory {
    private final Collection<KtFile> files;
    private final Project project;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinStaticPackageProviderFactory(Project project, Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        this.project = project;
        this.files = files;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinPackageProviderFactory
    public KotlinPackageProvider createPackageProvider(GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        return new KotlinStaticPackageProvider(this.project, searchScope, this.files);
    }
}
