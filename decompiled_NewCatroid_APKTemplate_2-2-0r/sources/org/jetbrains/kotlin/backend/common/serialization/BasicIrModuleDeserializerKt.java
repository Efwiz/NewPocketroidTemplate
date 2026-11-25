package org.jetbrains.kotlin.backend.common.serialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.protobuf.CodedInputStream;

/* JADX INFO: compiled from: BasicIrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t"}, d2 = {"codedInputStream", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "", "getCodedInputStream", "([B)Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "findModuleDeserializerForTopLevelId", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "idSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BasicIrModuleDeserializerKt {
    public static final IrModuleDeserializer findModuleDeserializerForTopLevelId(IrModuleDeserializer irModuleDeserializer, IdSignature idSignature) {
        Object next;
        Intrinsics.checkNotNullParameter(irModuleDeserializer, "<this>");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        if (irModuleDeserializer.contains(idSignature)) {
            return irModuleDeserializer;
        }
        Iterator<T> it = irModuleDeserializer.getModuleDependencies().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((IrModuleDeserializer) next).contains(idSignature)) {
                return (IrModuleDeserializer) next;
            }
        }
        next = null;
        return (IrModuleDeserializer) next;
    }

    public static final CodedInputStream getCodedInputStream(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr);
        codedInputStreamNewInstance.setRecursionLimit(65535);
        Intrinsics.checkNotNull(codedInputStreamNewInstance);
        return codedInputStreamNewInstance;
    }
}
