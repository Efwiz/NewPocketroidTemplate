package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;

/* JADX INFO: compiled from: InlineCache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineCache;", "", "()V", "classBytes", "Lorg/jetbrains/kotlin/com/intellij/util/containers/SLRUMap;", "", "", "getClassBytes", "()Lcom/intellij/util/containers/SLRUMap;", "methodNodeById", "Lorg/jetbrains/kotlin/codegen/inline/MethodId;", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "getMethodNodeById", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineCache {
    private final SLRUMap<String, byte[]> classBytes = new SLRUMap<>(30, 20);
    private final SLRUMap<MethodId, SMAPAndMethodNode> methodNodeById = new SLRUMap<>(60, 50);

    public final SLRUMap<String, byte[]> getClassBytes() {
        return this.classBytes;
    }

    public final SLRUMap<MethodId, SMAPAndMethodNode> getMethodNodeById() {
        return this.methodNodeById;
    }
}
