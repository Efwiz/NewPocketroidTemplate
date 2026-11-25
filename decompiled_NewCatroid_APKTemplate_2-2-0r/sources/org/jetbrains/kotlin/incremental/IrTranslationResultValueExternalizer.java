package org.jetbrains.kotlin.incremental;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.js.IrTranslationResultValue;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\f\u0010\f\u001a\u00020\r*\u00020\u0006H\u0002J\u000e\u0010\u000e\u001a\u0004\u0018\u00010\r*\u00020\u0006H\u0002J\u0014\u0010\u000f\u001a\u00020\b*\u00020\n2\u0006\u0010\u0010\u001a\u00020\rH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IrTranslationResultValueExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "readArray", "", "readArrayOrNull", "writeArray", "array", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrTranslationResultValueExternalizer implements DataExternalizer<IrTranslationResultValue> {
    public static final IrTranslationResultValueExternalizer INSTANCE = new IrTranslationResultValueExternalizer();

    private IrTranslationResultValueExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, IrTranslationResultValue value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        writeArray(output, value.getFileData());
        writeArray(output, value.getTypes());
        writeArray(output, value.getSignatures());
        writeArray(output, value.getStrings());
        writeArray(output, value.getDeclarations());
        writeArray(output, value.getBodies());
        writeArray(output, value.getFqn());
        writeArray(output, value.getFileMetadata());
        byte[] debugInfo = value.getDebugInfo();
        if (debugInfo != null) {
            INSTANCE.writeArray(output, debugInfo);
        }
    }

    private final void writeArray(DataOutput dataOutput, byte[] bArr) throws IOException {
        dataOutput.writeInt(bArr.length);
        dataOutput.write(bArr);
    }

    private final byte[] readArray(DataInput dataInput) throws IOException {
        byte[] bArr = new byte[dataInput.readInt()];
        dataInput.readFully(bArr);
        return bArr;
    }

    private final byte[] readArrayOrNull(DataInput dataInput) {
        try {
            byte[] bArr = new byte[dataInput.readInt()];
            dataInput.readFully(bArr);
            return bArr;
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public IrTranslationResultValue read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new IrTranslationResultValue(readArray(input), readArray(input), readArray(input), readArray(input), readArray(input), readArray(input), readArray(input), readArray(input), readArrayOrNull(input));
    }
}
