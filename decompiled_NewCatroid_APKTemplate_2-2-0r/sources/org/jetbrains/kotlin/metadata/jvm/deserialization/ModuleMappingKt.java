package org.jetbrains.kotlin.metadata.jvm.deserialization;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.VersionSpecificBehaviorKt;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u001a\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"internalNameOf", "", "packageFqName", "className", "serializeToByteArray", "", "Lorg/jetbrains/kotlin/metadata/jvm/JvmModuleProtoBuf$Module;", "version", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", OverlayablePolicy.NAME_flags, "", "metadata.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleMappingKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String internalNameOf(String str, String str2) {
        return str.length() == 0 ? str2 : StringsKt.replace$default(str, '.', '/', false, 4, (Object) null) + '/' + str2;
    }

    public static final byte[] serializeToByteArray(JvmModuleProtoBuf.Module module, BinaryVersion version, int i) throws IOException {
        Intrinsics.checkNotNullParameter(module, "<this>");
        Intrinsics.checkNotNullParameter(version, "version");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int[] numbers = version.getNumbers();
        dataOutputStream.writeInt(numbers.length);
        for (int i2 : numbers) {
            dataOutputStream.writeInt(i2);
        }
        if (VersionSpecificBehaviorKt.isKotlin1Dot4OrLater(version)) {
            dataOutputStream.writeInt(i);
        }
        module.writeTo(dataOutputStream);
        dataOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }
}
