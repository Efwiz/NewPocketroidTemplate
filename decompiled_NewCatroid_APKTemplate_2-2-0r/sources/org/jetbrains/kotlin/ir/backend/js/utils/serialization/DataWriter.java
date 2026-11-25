package org.jetbrains.kotlin.ir.backend.js.utils.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsIrAstSerializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J7\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0004\b\u0000\u0010\n2\b\u0010\u000b\u001a\u0004\u0018\u0001H\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u000e0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\"\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013H\u0086\bø\u0001\u0000J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0012J\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cJ4\u0010\u001d\u001a\u00020\u000e\"\u0004\b\u0000\u0010\n2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\n0\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u000e0\rH\u0086\bø\u0001\u0000J\u000e\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0019J\u000e\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020(R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/serialization/DataWriter;", "", "()V", "data", "Ljava/io/ByteArrayOutputStream;", "getData", "()Ljava/io/ByteArrayOutputStream;", "output", "Ljava/io/DataOutputStream;", "ifNotNull", ExifInterface.GPS_DIRECTION_TRUE, "t", "write", "Lkotlin/Function1;", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ifTrue", "condition", "", "Lkotlin/Function0;", "saveTo", "writeBoolean", "boolean", "writeByte", "byte", "", "writeByteArray", "byteArray", "", "writeCollection", "collection", "", "writeItem", "writeDouble", "double", "", "writeInt", "int", "writeString", "string", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DataWriter {
    private final ByteArrayOutputStream data;
    private final DataOutputStream output;

    public DataWriter() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.data = byteArrayOutputStream;
        this.output = new DataOutputStream(byteArrayOutputStream);
    }

    public final ByteArrayOutputStream getData() {
        return this.data;
    }

    public final void saveTo(DataOutputStream output) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        this.data.writeTo(output);
    }

    public final void writeByte(int i) throws IOException {
        if ((i & (-128)) != 0) {
            throw new IllegalStateException(("Byte out of bounds: " + i).toString());
        }
        this.output.writeByte(i);
    }

    public final void writeByteArray(byte[] byteArray) throws IOException {
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        this.output.writeInt(byteArray.length);
        this.output.write(byteArray);
    }

    public final void writeString(String string) throws IOException {
        Intrinsics.checkNotNullParameter(string, "string");
        byte[] bytes = string.getBytes(ConstantsKt.getSerializationCharset());
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        writeByteArray(bytes);
    }

    public final void writeBoolean(boolean z) throws IOException {
        this.output.writeBoolean(z);
    }

    public final void writeInt(int i) throws IOException {
        this.output.writeInt(i);
    }

    public final void writeDouble(double d) throws IOException {
        this.output.writeDouble(d);
    }

    public final <T> void writeCollection(Collection<? extends T> collection, Function1<? super T, Unit> writeItem) throws IOException {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Intrinsics.checkNotNullParameter(writeItem, "writeItem");
        this.output.writeInt(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            writeItem.invoke(it.next());
        }
    }

    public final <T> T ifNotNull(T t, Function1<? super T, Unit> write) throws IOException {
        Intrinsics.checkNotNullParameter(write, "write");
        this.output.writeBoolean(t != null);
        if (t != null) {
            write.invoke(t);
        }
        return t;
    }

    public final void ifTrue(boolean condition, Function0<Unit> write) throws IOException {
        Intrinsics.checkNotNullParameter(write, "write");
        this.output.writeBoolean(condition);
        if (condition) {
            write.invoke();
        }
    }
}
