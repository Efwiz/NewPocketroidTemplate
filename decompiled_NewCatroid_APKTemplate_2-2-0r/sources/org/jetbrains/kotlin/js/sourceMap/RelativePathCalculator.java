package org.jetbrains.kotlin.js.sourceMap;

import java.io.File;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: RelativePathCalculator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0003J\u0012\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006*\u00020\u0003H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/RelativePathCalculator;", "", "baseDir", "Ljava/io/File;", "(Ljava/io/File;)V", "baseDirPath", "", "calculateRelativePathTo", "", "file", "getAllParents", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RelativePathCalculator {
    private final List<File> baseDirPath;

    public RelativePathCalculator(File baseDir) {
        Intrinsics.checkNotNullParameter(baseDir, "baseDir");
        this.baseDirPath = getAllParents(baseDir);
    }

    private final List<File> getAllParents(File file) {
        File absoluteFile = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        return CollectionsKt.asReversed(SequencesKt.toList(SequencesKt.generateSequence(FilesKt.normalize(absoluteFile), new Function1<File, File>() { // from class: org.jetbrains.kotlin.js.sourceMap.RelativePathCalculator.getAllParents.1
            @Override // kotlin.jvm.functions.Function1
            public final File invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParentFile();
            }
        })));
    }

    public final String calculateRelativePathTo(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        List<File> allParents = getAllParents(file);
        int i = 0;
        if (!Intrinsics.areEqual(this.baseDirPath.get(0), allParents.get(0))) {
            return null;
        }
        List<Pair> listZip = CollectionsKt.zip(this.baseDirPath, allParents);
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                if (Intrinsics.areEqual((File) pair.component1(), (File) pair.component2()) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int size = this.baseDirPath.size();
        for (int i2 = i; i2 < size; i2++) {
            sb.append("../");
        }
        int size2 = allParents.size();
        while (i < size2) {
            sb.append(allParents.get(i).getName()).append('/');
            i++;
        }
        sb.setLength(StringsKt.getLastIndex(sb));
        return sb.toString();
    }
}
