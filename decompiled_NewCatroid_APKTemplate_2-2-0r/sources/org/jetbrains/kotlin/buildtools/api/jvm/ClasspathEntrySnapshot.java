package org.jetbrains.kotlin.buildtools.api.jvm;

import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.buildtools.api.ExperimentalBuildToolsApi;

/* JADX INFO: compiled from: ClasspathEntrySnapshot.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/jvm/ClasspathEntrySnapshot;", "", "classSnapshots", "", "", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClassSnapshot;", "getClassSnapshots", "()Ljava/util/Map;", "saveSnapshot", "", "path", "Ljava/io/File;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface ClasspathEntrySnapshot {
    Map<String, ClassSnapshot> getClassSnapshots();

    void saveSnapshot(File path);
}
