package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProviderMerger;
import org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.CompositeKotlinDeclarationProvider;

/* JADX INFO: compiled from: KotlinDeclarationProviderMergerBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H$¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinDeclarationProviderMergerBase;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProviderMerger;", "()V", "mergeDeclarationProviders", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "declarationProviders", "", "mergeToList", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinDeclarationProviderMergerBase extends KotlinDeclarationProviderMerger {
    protected abstract List<KotlinDeclarationProvider> mergeToList(List<? extends KotlinDeclarationProvider> declarationProviders);

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProviderMerger
    public KotlinDeclarationProvider mergeDeclarationProviders(List<? extends KotlinDeclarationProvider> declarationProviders) {
        Intrinsics.checkNotNullParameter(declarationProviders, "declarationProviders");
        return CompositeKotlinDeclarationProvider.INSTANCE.createFlattened(mergeToList(CompositeKotlinDeclarationProvider.INSTANCE.flatten(declarationProviders)));
    }
}
