package org.jetbrains.kotlin.analysis.project.structure;

import java.nio.file.Path;
import java.util.Collection;
import kotlin.Metadata;

/* JADX INFO: compiled from: KtModule.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&\u0082\u0001\u0003\u0005\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtBinaryModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "getBinaryRoots", "", "Ljava/nio/file/Path;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtBuiltinsModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtLibraryModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSdkModule;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtBinaryModule extends KtModule {
    Collection<Path> getBinaryRoots();
}
