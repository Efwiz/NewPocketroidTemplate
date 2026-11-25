package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.storage.ListExternalizer;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassMemberLevelSnapshotExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassMemberLevelSnapshot;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "snapshot", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassMemberLevelSnapshotExternalizer implements DataExternalizer<JavaClassMemberLevelSnapshot> {
    public static final JavaClassMemberLevelSnapshotExternalizer INSTANCE = new JavaClassMemberLevelSnapshotExternalizer();

    private JavaClassMemberLevelSnapshotExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, JavaClassMemberLevelSnapshot snapshot) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        JavaElementSnapshotExternalizer.INSTANCE.save(output, snapshot.getClassAbiExcludingMembers());
        new ListExternalizer(JavaElementSnapshotExternalizer.INSTANCE).save(output, snapshot.getFieldsAbi());
        new ListExternalizer(JavaElementSnapshotExternalizer.INSTANCE).save(output, snapshot.getMethodsAbi());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public JavaClassMemberLevelSnapshot read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new JavaClassMemberLevelSnapshot(JavaElementSnapshotExternalizer.INSTANCE.read(input), (List) new ListExternalizer(JavaElementSnapshotExternalizer.INSTANCE).read(input), (List) new ListExternalizer(JavaElementSnapshotExternalizer.INSTANCE).read(input));
    }
}
