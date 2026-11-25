package org.jetbrains.kotlin.incremental.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/PathStringDescriptor;", "Lorg/jetbrains/kotlin/com/intellij/util/io/EnumeratorStringDescriptor;", "()V", "getHashCode", "", "path", "", "isEqual", "", "val1", "val2", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PathStringDescriptor extends EnumeratorStringDescriptor {
    public static final PathStringDescriptor INSTANCE = new PathStringDescriptor();

    private PathStringDescriptor() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor, org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor, org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public int getHashCode(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (StringUtil.isEmpty(path)) {
            return 0;
        }
        return FileUtil.toCanonicalPath(path).hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor, org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public boolean isEqual(String val1, String val2) {
        Intrinsics.checkNotNullParameter(val1, "val1");
        if (Intrinsics.areEqual(val1, val2)) {
            return true;
        }
        if (val2 == null) {
            return false;
        }
        return Intrinsics.areEqual(FileUtil.toCanonicalPath(val1), FileUtil.toCanonicalPath(val2));
    }
}
