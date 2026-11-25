package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer;
import org.jetbrains.kotlin.library.encodings.WobblyTF8;
import org.jetbrains.kotlin.library.impl.IrArrayMemoryReader;
import org.jetbrains.kotlin.library.impl.IrFileReadersKt;
import org.jetbrains.kotlin.library.impl.IrMemoryStringWriter;

/* JADX INFO: compiled from: JsIrFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrFileMetadata;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$FileBackendSpecificMetadata;", "exportedNames", "", "", "(Ljava/util/List;)V", "getExportedNames", "()Ljava/util/List;", "toByteArray", "", "Companion", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrFileMetadata implements IrFileSerializer.FileBackendSpecificMetadata {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<String> exportedNames;

    public JsIrFileMetadata(List<String> exportedNames) {
        Intrinsics.checkNotNullParameter(exportedNames, "exportedNames");
        this.exportedNames = exportedNames;
    }

    public final List<String> getExportedNames() {
        return this.exportedNames;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.FileBackendSpecificMetadata
    public byte[] toByteArray() {
        return new IrMemoryStringWriter(this.exportedNames).writeIntoMemory();
    }

    /* JADX INFO: compiled from: JsIrFileMetadata.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrFileMetadata$Companion;", "", "()V", "fromByteArray", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrFileMetadata;", "data", "", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JsIrFileMetadata fromByteArray(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            byte[][] array = IrFileReadersKt.toArray(new IrArrayMemoryReader(data));
            WobblyTF8 wobblyTF8 = WobblyTF8.INSTANCE;
            ArrayList arrayList = new ArrayList(array.length);
            for (byte[] bArr : array) {
                arrayList.add(wobblyTF8.decode(bArr));
            }
            return new JsIrFileMetadata(arrayList);
        }
    }
}
