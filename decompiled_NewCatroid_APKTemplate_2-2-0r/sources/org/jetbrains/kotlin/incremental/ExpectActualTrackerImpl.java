package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;

/* JADX INFO: compiled from: ExpectActualTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H\u0016R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\n0\t8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ExpectActualTrackerImpl;", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "()V", "expectToActual", "Ljava/util/HashMap;", "Ljava/io/File;", "", "Lkotlin/collections/HashMap;", "expectToActualMap", "", "", "getExpectToActualMap", "()Ljava/util/Map;", "report", "", "expectedFile", "actualFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualTrackerImpl implements ExpectActualTracker {
    private final HashMap<File, Set<File>> expectToActual = new HashMap<>();

    public final Map<File, Set<File>> getExpectToActualMap() {
        return this.expectToActual;
    }

    @Override // org.jetbrains.kotlin.incremental.components.ExpectActualTracker
    public void report(File expectedFile, File actualFile) {
        Intrinsics.checkNotNullParameter(expectedFile, "expectedFile");
        Intrinsics.checkNotNullParameter(actualFile, "actualFile");
        HashMap<File, Set<File>> map = this.expectToActual;
        HashSet hashSet = map.get(expectedFile);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(expectedFile, hashSet);
        }
        hashSet.add(actualFile);
    }
}
