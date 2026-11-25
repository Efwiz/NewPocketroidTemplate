package org.jetbrains.kotlin.incremental;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/TranslationResultValueExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TranslationResultValueExternalizer implements DataExternalizer<TranslationResultValue> {
    public static final TranslationResultValueExternalizer INSTANCE = new TranslationResultValueExternalizer();

    private TranslationResultValueExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, TranslationResultValue value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        output.writeInt(value.getMetadata().length);
        output.write(value.getMetadata());
        output.writeInt(value.getBinaryAst().length);
        output.write(value.getBinaryAst());
        output.writeInt(value.getInlineData().length);
        output.write(value.getInlineData());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public TranslationResultValue read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        byte[] bArr = new byte[input.readInt()];
        input.readFully(bArr);
        byte[] bArr2 = new byte[input.readInt()];
        input.readFully(bArr2);
        byte[] bArr3 = new byte[input.readInt()];
        input.readFully(bArr3);
        return new TranslationResultValue(bArr, bArr2, bArr3);
    }
}
