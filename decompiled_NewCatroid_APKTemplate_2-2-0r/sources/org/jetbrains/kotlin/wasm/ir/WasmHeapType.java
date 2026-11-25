package org.jetbrains.kotlin.wasm.ir;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.billthefarmer.mididriver.MidiConstants;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "", "()V", "Simple", "Type", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Type;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmHeapType {
    public /* synthetic */ WasmHeapType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: Types.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Type;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;)V", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Type extends WasmHeapType {
        private final WasmSymbolReadOnly<WasmTypeDeclaration> type;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Type copy$default(Type type, WasmSymbolReadOnly wasmSymbolReadOnly, int i, Object obj) {
            if ((i & 1) != 0) {
                wasmSymbolReadOnly = type.type;
            }
            return type.copy(wasmSymbolReadOnly);
        }

        public final WasmSymbolReadOnly<WasmTypeDeclaration> component1() {
            return this.type;
        }

        public final Type copy(WasmSymbolReadOnly<? extends WasmTypeDeclaration> type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new Type(type);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Type) && Intrinsics.areEqual(this.type, ((Type) other).type);
        }

        public int hashCode() {
            return this.type.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Type(WasmSymbolReadOnly<? extends WasmTypeDeclaration> type) {
            super(null);
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
        }

        public final WasmSymbolReadOnly<WasmTypeDeclaration> getType() {
            return this.type;
        }

        public String toString() {
            return "Type:" + this.type;
        }
    }

    private WasmHeapType() {
    }

    /* JADX INFO: compiled from: Types.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\f\r\u000e\u000f\u0010\u0011\u0012B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0007\u0013\u0014\u0015\u0016\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "name", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "(Ljava/lang/String;B)V", "getCode", "()B", "getName", "()Ljava/lang/String;", "toString", "Any", "Eq", "Extern", "Func", "NoExtern", "None", "Struct", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Any;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Eq;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Extern;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Func;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$NoExtern;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$None;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Struct;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Simple extends WasmHeapType {
        private final byte code;
        private final String name;

        public /* synthetic */ Simple(String str, byte b, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, b);
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Func;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Func extends Simple {
            public static final Func INSTANCE = new Func();

            private Func() {
                super("func", (byte) -16, null);
            }
        }

        private Simple(String str, byte b) {
            super(null);
            this.name = str;
            this.code = b;
        }

        public final byte getCode() {
            return this.code;
        }

        public final String getName() {
            return this.name;
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Extern;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Extern extends Simple {
            public static final Extern INSTANCE = new Extern();

            private Extern() {
                super("extern", ByteSourceJsonBootstrapper.UTF8_BOM_1, null);
            }
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Any;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Any extends Simple {
            public static final Any INSTANCE = new Any();

            private Any() {
                super("any", (byte) -18, null);
            }
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Eq;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Eq extends Simple {
            public static final Eq INSTANCE = new Eq();

            private Eq() {
                super("eq", (byte) -19, null);
            }
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$Struct;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Struct extends Simple {
            public static final Struct INSTANCE = new Struct();

            private Struct() {
                super("struct", (byte) -21, null);
            }
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$None;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class None extends Simple {
            public static final None INSTANCE = new None();

            private None() {
                super("none", MidiConstants.MIDI_TIME_CODE, null);
            }
        }

        /* JADX INFO: compiled from: Types.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple$NoExtern;", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType$Simple;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NoExtern extends Simple {
            public static final NoExtern INSTANCE = new NoExtern();

            private NoExtern() {
                super("noextern", MidiConstants.SONG_POSITION_POBYTEER, null);
            }
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder("Simple:").append(this.name).append('(');
            String string = Integer.toString(this.code, CharsKt.checkRadix(16));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return sbAppend.append(string).append(')').toString();
        }
    }
}
