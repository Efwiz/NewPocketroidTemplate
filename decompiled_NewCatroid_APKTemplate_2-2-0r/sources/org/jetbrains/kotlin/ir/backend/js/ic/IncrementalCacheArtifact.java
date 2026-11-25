package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IncrementalCacheArtifact.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00070\u0010J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "", "artifactsDir", "Ljava/io/File;", "forceRebuildJs", "", "srcCacheActions", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "externalModuleName", "", "(Ljava/io/File;ZLjava/util/List;Ljava/lang/String;)V", "buildModuleArtifactAndCommitCache", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "moduleName", "rebuiltFileFragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "getSourceFiles", "Ljava/util/HashSet;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCacheArtifact {
    private final File artifactsDir;
    private final String externalModuleName;
    private final boolean forceRebuildJs;
    private final List<SourceFileCacheArtifact> srcCacheActions;

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalCacheArtifact(File artifactsDir, boolean z, List<? extends SourceFileCacheArtifact> srcCacheActions, String str) {
        Intrinsics.checkNotNullParameter(artifactsDir, "artifactsDir");
        Intrinsics.checkNotNullParameter(srcCacheActions, "srcCacheActions");
        this.artifactsDir = artifactsDir;
        this.forceRebuildJs = z;
        this.srcCacheActions = srcCacheActions;
        this.externalModuleName = str;
    }

    public final HashSet<KotlinSourceFile> getSourceFiles() {
        List<SourceFileCacheArtifact> list = this.srcCacheActions;
        List<SourceFileCacheArtifact> list2 = list;
        HashSet<KotlinSourceFile> hashSetNewHashSetWithExpectedSize = CollectionsKt.newHashSetWithExpectedSize(list.size());
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            hashSetNewHashSetWithExpectedSize.add(KotlinSourceFile.m7163boximpl(((SourceFileCacheArtifact) it.next()).getSrcFile()));
        }
        return hashSetNewHashSetWithExpectedSize;
    }

    public final ModuleArtifact buildModuleArtifactAndCommitCache(String moduleName, Map<KotlinSourceFile, ? extends List<JsIrProgramFragment>> rebuiltFileFragments) throws Exception {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(rebuiltFileFragments, "rebuiltFileFragments");
        List<SourceFileCacheArtifact> list = this.srcCacheActions;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        for (SourceFileCacheArtifact sourceFileCacheArtifact : list) {
            List<JsIrProgramFragment> listEmptyList = rebuiltFileFragments.get(KotlinSourceFile.m7163boximpl(sourceFileCacheArtifact.getSrcFile()));
            if (listEmptyList != null) {
                sourceFileCacheArtifact.commitBinaryAst(listEmptyList);
            }
            sourceFileCacheArtifact.commitMetadata();
            String srcFile = sourceFileCacheArtifact.getSrcFile();
            if (listEmptyList == null) {
                listEmptyList = kotlin.collections.CollectionsKt.emptyList();
            }
            arrayList.add(new SrcFileArtifact(srcFile, listEmptyList, sourceFileCacheArtifact.getBinaryAstFile()));
        }
        return new ModuleArtifact(moduleName, arrayList, this.artifactsDir, this.forceRebuildJs, this.externalModuleName);
    }
}
