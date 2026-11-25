package org.jetbrains.kotlin.build;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.idea.KotlinFileType;

/* JADX INFO: compiled from: sourcesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"DEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS", "", "", "getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS", "()Ljava/util/List;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourcesUtilsKt {
    private static final List<String> DEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS = CollectionsKt.listOf((Object[]) new String[]{KotlinFileType.EXTENSION, "kts"});

    public static final List<String> getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS() {
        return DEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS;
    }
}
