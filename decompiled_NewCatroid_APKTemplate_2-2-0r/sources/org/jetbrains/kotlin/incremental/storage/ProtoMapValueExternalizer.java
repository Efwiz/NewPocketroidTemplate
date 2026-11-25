package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValueExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtoMapValueExternalizer implements DataExternalizer<ProtoMapValue> {
    public static final ProtoMapValueExternalizer INSTANCE = new ProtoMapValueExternalizer();

    private ProtoMapValueExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, ProtoMapValue value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        output.writeBoolean(value.isPackageFacade());
        output.writeInt(value.getBytes().length);
        output.write(value.getBytes());
        output.writeInt(value.getStrings().length);
        for (String str : value.getStrings()) {
            output.writeUTF(str);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public ProtoMapValue read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        boolean z = input.readBoolean();
        int i = input.readInt();
        byte[] bArr = new byte[i];
        input.readFully(bArr, 0, i);
        int i2 = input.readInt();
        String[] strArr = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            String utf = input.readUTF();
            Intrinsics.checkNotNullExpressionValue(utf, "readUTF(...)");
            strArr[i3] = utf;
        }
        return new ProtoMapValue(z, bArr, strArr);
    }
}
