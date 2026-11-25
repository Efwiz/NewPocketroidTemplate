package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolver;
import org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolverFactory;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinStaticAnnotationsResolver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticAnnotationsResolverFactory;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinAnnotationsResolverFactory;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Ljava/util/Collection;)V", "createAnnotationResolver", "Lorg/jetbrains/kotlin/analysis/providers/KotlinAnnotationsResolver;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticAnnotationsResolverFactory implements KotlinAnnotationsResolverFactory {
    private final Collection<KtFile> files;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinStaticAnnotationsResolverFactory(Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        this.files = files;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolverFactory
    public KotlinAnnotationsResolver createAnnotationResolver(GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        return new KotlinStaticAnnotationsResolver(this.files, searchScope);
    }
}
