package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.incremental.storage.ClassIdExternalizer;
import org.jetbrains.kotlin.incremental.storage.IntExternalizer;
import org.jetbrains.kotlin.incremental.storage.ListExternalizer;
import org.jetbrains.kotlin.incremental.storage.NullableValueExternalizer;
import org.jetbrains.kotlin.incremental.storage.StringExternalizer;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassInfoExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", CompilerOptions.INFO, "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassInfoExternalizer implements DataExternalizer<KotlinClassInfo> {
    public static final KotlinClassInfoExternalizer INSTANCE = new KotlinClassInfoExternalizer();

    private KotlinClassInfoExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, KotlinClassInfo info) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(info, "info");
        ClassIdExternalizer.INSTANCE.save(output, info.getClassId());
        IntExternalizer.INSTANCE.save(output, info.getClassKind().getId());
        new ListExternalizer(StringExternalizer.INSTANCE).save(output, ArraysKt.toList(info.getClassHeaderData()));
        new ListExternalizer(StringExternalizer.INSTANCE).save(output, ArraysKt.toList(info.getClassHeaderStrings()));
        new NullableValueExternalizer(StringExternalizer.INSTANCE).save(output, info.getMultifileClassName());
        ExtraInfoExternalizer.INSTANCE.save(output, info.getExtraInfo());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public KotlinClassInfo read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new KotlinClassInfo(ClassIdExternalizerWithInterning.INSTANCE.read(input), KotlinClassHeader.Kind.INSTANCE.getById(IntExternalizer.INSTANCE.read(input).intValue()), (String[]) new ListExternalizer(StringExternalizer.INSTANCE).read(input).toArray(new String[0]), (String[]) new ListExternalizer(StringExternalizer.INSTANCE).read(input).toArray(new String[0]), (String) new NullableValueExternalizer(StringExternalizer.INSTANCE).read(input), ExtraInfoExternalizer.INSTANCE.read(input));
    }
}
