package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClientUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/FileAgeComparator;", "Ljava/util/Comparator;", "Ljava/io/File;", "Lkotlin/Comparator;", "()V", "compare", "", "left", "right", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileAgeComparator implements Comparator<File> {
    @Override // java.util.Comparator
    public int compare(File left, File right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        long jLastModified = left.lastModified();
        long jLastModified2 = right.lastModified();
        if (jLastModified == 0 || jLastModified2 == 0) {
            return 0;
        }
        if (jLastModified > jLastModified2) {
            return -1;
        }
        if (jLastModified < jLastModified2) {
            return 1;
        }
        return ComparisonsKt.compareValues(FilesKt.normalize(left).getAbsolutePath(), FilesKt.normalize(right).getAbsolutePath());
    }
}
