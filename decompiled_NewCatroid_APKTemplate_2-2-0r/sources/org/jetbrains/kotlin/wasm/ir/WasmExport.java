package org.jetbrains.kotlin.wasm.ir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0005\u0013\u0014\u0015\u0016\u0017B'\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f\u0082\u0001\u0005\u0018\u0019\u001a\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "", "name", "", "field", Namer.METADATA_CLASS_KIND, "", "keyword", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;BLjava/lang/String;)V", "getField", "()Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "getKeyword", "()Ljava/lang/String;", "getKind", "()B", "getName", "Function", "Global", "Memory", "Table", "Tag", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Global;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Memory;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Table;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Tag;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmExport<T extends WasmNamedModuleField> {
    private final T field;
    private final String keyword;
    private final byte kind;
    private final String name;

    public /* synthetic */ WasmExport(String str, WasmNamedModuleField wasmNamedModuleField, byte b, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, wasmNamedModuleField, b, str2);
    }

    private WasmExport(String str, T t, byte b, String str2) {
        this.name = str;
        this.field = t;
        this.kind = b;
        this.keyword = str2;
    }

    public final String getName() {
        return this.name;
    }

    public final T getField() {
        return this.field;
    }

    public final byte getKind() {
        return this.kind;
    }

    public final String getKeyword() {
        return this.keyword;
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "name", "", "field", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;)V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends WasmExport<WasmFunction> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Function(String name2, WasmFunction field) {
            super(name2, field, (byte) 0, "func", null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(field, "field");
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Table;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "name", "", "field", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmTable;)V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Table extends WasmExport<WasmTable> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Table(String name2, WasmTable field) {
            super(name2, field, (byte) 1, "table", null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(field, "field");
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Memory;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "name", "", "field", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;)V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Memory extends WasmExport<WasmMemory> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Memory(String name2, WasmMemory field) {
            super(name2, field, (byte) 2, "memory", null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(field, "field");
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Global;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "name", "", "field", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;)V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Global extends WasmExport<WasmGlobal> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Global(String name2, WasmGlobal field) {
            super(name2, field, (byte) 3, "global", null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(field, "field");
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExport$Tag;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "name", "", "field", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmTag;)V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Tag extends WasmExport<WasmTag> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Tag(String name2, WasmTag field) {
            super(name2, field, (byte) 4, "tag", null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(field, "field");
        }
    }
}
