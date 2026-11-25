package org.jetbrains.kotlin.analysis.providers.topics;

import kotlin.Metadata;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;

/* JADX INFO: compiled from: KotlinModuleOutOfBlockModificationListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/topics/KotlinModuleOutOfBlockModificationListener;", "", "onModification", "", "module", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinModuleOutOfBlockModificationListener {
    void onModification(KtModule module);
}
