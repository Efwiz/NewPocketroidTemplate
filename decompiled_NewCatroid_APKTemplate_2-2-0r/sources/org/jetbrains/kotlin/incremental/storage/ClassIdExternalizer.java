package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ClassIdExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/name/ClassId;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "classId", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassIdExternalizer implements DataExternalizer<ClassId> {
    public static final ClassIdExternalizer INSTANCE = new ClassIdExternalizer();

    private ClassIdExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, ClassId classId) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqNameExternalizer fqNameExternalizer = FqNameExternalizer.INSTANCE;
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        fqNameExternalizer.save(output, packageFqName);
        FqNameExternalizer fqNameExternalizer2 = FqNameExternalizer.INSTANCE;
        FqName relativeClassName = classId.getRelativeClassName();
        Intrinsics.checkNotNullExpressionValue(relativeClassName, "getRelativeClassName(...)");
        fqNameExternalizer2.save(output, relativeClassName);
        output.writeBoolean(classId.isLocal());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public ClassId read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new ClassId(FqNameExternalizer.INSTANCE.read(input), FqNameExternalizer.INSTANCE.read(input), input.readBoolean());
    }
}
