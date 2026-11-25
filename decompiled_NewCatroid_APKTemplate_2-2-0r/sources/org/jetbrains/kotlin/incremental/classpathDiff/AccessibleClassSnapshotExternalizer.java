package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.storage.DelegateDataExternalizer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J \u0010\u0004\u001a\n \u0005*\u0004\u0018\u00010\u00020\u00022\r\b\u0001\u0010\u0006\u001a\u00070\u0007¢\u0006\u0002\b\bH\u0096\u0001J(\u0010\t\u001a\u00020\n2\r\b\u0001\u0010\u0006\u001a\u00070\u000b¢\u0006\u0002\b\b2\u000e\u0010\f\u001a\n \u0005*\u0004\u0018\u00010\u00020\u0002H\u0096\u0001¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshotExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "()V", "read", JvmProtoBufUtil.PLATFORM_TYPE_ID, "p0", "Ljava/io/DataInput;", "Lorg/jetbrains/annotations/NotNull;", "save", "", "Ljava/io/DataOutput;", "p1", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessibleClassSnapshotExternalizer implements DataExternalizer<AccessibleClassSnapshot> {
    public static final AccessibleClassSnapshotExternalizer INSTANCE = new AccessibleClassSnapshotExternalizer();
    private final /* synthetic */ DelegateDataExternalizer<AccessibleClassSnapshot> $$delegate_0 = new DelegateDataExternalizer<>(CollectionsKt.listOf((Object[]) new Class[]{KotlinClassSnapshot.class, JavaClassSnapshot.class}), CollectionsKt.listOf((Object[]) new DataExternalizer[]{KotlinClassSnapshotExternalizer.INSTANCE, JavaClassSnapshotExternalizer.INSTANCE}));

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public AccessibleClassSnapshot read(DataInput p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_0.read(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput p0, AccessibleClassSnapshot p1) throws IOException {
        Intrinsics.checkNotNullParameter(p0, "p0");
        DelegateDataExternalizer<AccessibleClassSnapshot> delegateDataExternalizer = this.$$delegate_0;
        Intrinsics.checkNotNull(p1);
        delegateDataExternalizer.save(p0, p1);
    }

    private AccessibleClassSnapshotExternalizer() {
    }
}
