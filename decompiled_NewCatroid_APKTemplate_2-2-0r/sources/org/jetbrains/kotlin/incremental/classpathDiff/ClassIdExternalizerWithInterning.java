package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.storage.ClassIdExternalizer;
import org.jetbrains.kotlin.incremental.storage.FqNameExternalizer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\u0007\u001a\u00020\b2\r\b\u0001\u0010\t\u001a\u00070\n¢\u0006\u0002\b\u000b2\u000e\u0010\f\u001a\n \r*\u0004\u0018\u00010\u00020\u0002H\u0096\u0001¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassIdExternalizerWithInterning;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/name/ClassId;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "p0", "Ljava/io/DataOutput;", "Lorg/jetbrains/annotations/NotNull;", "p1", JvmProtoBufUtil.PLATFORM_TYPE_ID, "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassIdExternalizerWithInterning implements DataExternalizer<ClassId> {
    public static final ClassIdExternalizerWithInterning INSTANCE = new ClassIdExternalizerWithInterning();
    private final /* synthetic */ ClassIdExternalizer $$delegate_0 = ClassIdExternalizer.INSTANCE;

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput p0, ClassId p1) throws IOException {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ClassIdExternalizer classIdExternalizer = this.$$delegate_0;
        Intrinsics.checkNotNull(p1);
        classIdExternalizer.save(p0, p1);
    }

    private ClassIdExternalizerWithInterning() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public ClassId read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new ClassId(FqNameExternalizerWithInterning.INSTANCE.read(input), FqNameExternalizer.INSTANCE.read(input), input.readBoolean());
    }
}
