package org.jetbrains.kotlin.metadata.serialization;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder;

/* JADX INFO: compiled from: MutableTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0012\b\u0000\u0010\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/metadata/serialization/TableElementWrapper;", "Element", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$Builder;", "", "builder", "(Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$Builder;)V", "getBuilder", "()Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$Builder;", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$Builder;", "bytes", "", "hashCode", "", Namer.EQUALS_METHOD_NAME, "", "other", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TableElementWrapper<Element extends GeneratedMessageLite.Builder<?, Element>> {
    private final Element builder;
    private final byte[] bytes;
    private final int hashCode;

    public TableElementWrapper(Element builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.builder = builder;
        byte[] byteArray = builder.build().toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        this.bytes = byteArray;
        this.hashCode = Arrays.hashCode(byteArray);
    }

    public final Element getBuilder() {
        return this.builder;
    }

    /* JADX INFO: renamed from: hashCode, reason: from getter */
    public int getHashCode() {
        return this.hashCode;
    }

    public boolean equals(Object other) {
        return (other instanceof TableElementWrapper) && Arrays.equals(this.bytes, ((TableElementWrapper) other).bytes);
    }
}
