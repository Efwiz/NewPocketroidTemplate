package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.incremental.storage.InlineFunctionOrAccessorExternalizer;
import org.jetbrains.kotlin.incremental.storage.LongExternalizer;
import org.jetbrains.kotlin.incremental.storage.MapExternalizer;
import org.jetbrains.kotlin.incremental.storage.NullableValueExternalizer;
import org.jetbrains.kotlin.incremental.storage.StringExternalizer;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ExtraInfoExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", CompilerOptions.INFO, "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExtraInfoExternalizer implements DataExternalizer<KotlinClassInfo.ExtraInfo> {
    public static final ExtraInfoExternalizer INSTANCE = new ExtraInfoExternalizer();

    private ExtraInfoExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, KotlinClassInfo.ExtraInfo info) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(info, "info");
        new NullableValueExternalizer(LongExternalizer.INSTANCE).save(output, info.getClassSnapshotExcludingMembers());
        new MapExternalizer(StringExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null).save(output, info.getConstantSnapshots());
        new MapExternalizer(InlineFunctionOrAccessorExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null).save(output, info.getInlineFunctionOrAccessorSnapshots());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public KotlinClassInfo.ExtraInfo read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new KotlinClassInfo.ExtraInfo((Long) new NullableValueExternalizer(LongExternalizer.INSTANCE).read(input), new MapExternalizer(StringExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null).read(input), new MapExternalizer(InlineFunctionOrAccessorExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null).read(input));
    }
}
