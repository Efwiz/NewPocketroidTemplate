package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u0004H&J\b\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0006\r\u000e\u000f\u0010\u0011\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "", "()V", "sizeInBytes", "", "getSizeInBytes", "()I", ArchiveStreamFactory.DUMP, "", OutputKeys.INDENT, "startAddress", "toBytes", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataCharArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataCharField;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntField;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntegerArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataStruct;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstantDataElement {
    public /* synthetic */ ConstantDataElement(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String dump(String indent, int startAddress);

    public abstract int getSizeInBytes();

    public abstract byte[] toBytes();

    private ConstantDataElement() {
    }

    public static /* synthetic */ String dump$default(ConstantDataElement constantDataElement, String str, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: dump");
        }
        if ((i2 & 1) != 0) {
            str = "";
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return constantDataElement.dump(str, i);
    }
}
