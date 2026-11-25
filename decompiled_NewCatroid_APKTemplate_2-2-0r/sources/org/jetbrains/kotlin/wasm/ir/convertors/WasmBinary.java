package org.jetbrains.kotlin.wasm.ir.convertors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.UShort;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: WasmIrToBinary.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u00020\bX\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u00020\bX\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinary;", "", "()V", "ARRAY_TYPE", "", "EMPTY_TYPE_FOR_BLOCK", "FUNC_TYPE", "MAGIC", "Lkotlin/UInt;", "I", "REC_GROUP", "STRUCT_TYPE", "SUB_FINAL_TYPE", "SUB_TYPE", "VERSION", "Section", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WasmBinary {
    public static final byte ARRAY_TYPE = -34;
    public static final byte EMPTY_TYPE_FOR_BLOCK = -64;
    public static final byte FUNC_TYPE = -32;
    public static final WasmBinary INSTANCE = new WasmBinary();
    public static final int MAGIC = 1836278016;
    public static final byte REC_GROUP = -50;
    public static final byte STRUCT_TYPE = -33;
    public static final byte SUB_FINAL_TYPE = -49;
    public static final byte SUB_TYPE = -48;
    public static final int VERSION = 1;

    private WasmBinary() {
    }

    /* JADX INFO: compiled from: WasmIrToBinary.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000fHÖ\u0001¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u0013HÖ\u0001¢\u0006\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinary$Section;", "", "id", "Lkotlin/UShort;", "constructor-impl", "(S)S", "getId-Mh2AYeg", "()S", ExifInterface.LATITUDE_SOUTH, Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(SLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(S)I", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "Companion", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @JvmInline
    public static final class Section {
        private final short id;

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final short CUSTOM = m7512constructorimpl(0);
        private static final short TYPE = m7512constructorimpl(1);
        private static final short IMPORT = m7512constructorimpl(2);
        private static final short FUNCTION = m7512constructorimpl(3);
        private static final short TABLE = m7512constructorimpl(4);
        private static final short MEMORY = m7512constructorimpl(5);
        private static final short GLOBAL = m7512constructorimpl(6);
        private static final short EXPORT = m7512constructorimpl(7);
        private static final short START = m7512constructorimpl(8);
        private static final short ELEMENT = m7512constructorimpl(9);
        private static final short CODE = m7512constructorimpl(10);
        private static final short DATA = m7512constructorimpl(11);
        private static final short DATA_COUNT = m7512constructorimpl(12);
        private static final short TAG = m7512constructorimpl(13);

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Section m7511boximpl(short s) {
            return new Section(s);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static short m7512constructorimpl(short s) {
            return s;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m7513equalsimpl(short s, Object obj) {
            return (obj instanceof Section) && s == ((Section) obj).m7518unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m7514equalsimpl0(short s, short s2) {
            return UShort.m3186equalsimpl0(s, s2);
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m7515hashCodeimpl(short s) {
            return UShort.m3191hashCodeimpl(s);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m7516toStringimpl(short s) {
            return "Section(id=" + ((Object) UShort.m3223toStringimpl(s)) + ')';
        }

        public boolean equals(Object obj) {
            return m7513equalsimpl(this.id, obj);
        }

        public int hashCode() {
            return m7515hashCodeimpl(this.id);
        }

        public String toString() {
            return m7516toStringimpl(this.id);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ short m7518unboximpl() {
            return this.id;
        }

        /* JADX INFO: compiled from: WasmIrToBinary.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000b\u0010\u0006R\u0019\u0010\f\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\r\u0010\u0006R\u0019\u0010\u000e\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000f\u0010\u0006R\u0019\u0010\u0010\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0011\u0010\u0006R\u0019\u0010\u0012\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0013\u0010\u0006R\u0019\u0010\u0014\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0015\u0010\u0006R\u0019\u0010\u0016\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0017\u0010\u0006R\u0019\u0010\u0018\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0019\u0010\u0006R\u0019\u0010\u001a\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u001b\u0010\u0006R\u0019\u0010\u001c\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u001d\u0010\u0006R\u0019\u0010\u001e\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u001f\u0010\u0006R\u0019\u0010 \u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b!\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinary$Section$Companion;", "", "()V", "CODE", "Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinary$Section;", "getCODE-Esw0DI4", "()S", ExifInterface.LATITUDE_SOUTH, "CUSTOM", "getCUSTOM-Esw0DI4", "DATA", "getDATA-Esw0DI4", "DATA_COUNT", "getDATA_COUNT-Esw0DI4", "ELEMENT", "getELEMENT-Esw0DI4", "EXPORT", "getEXPORT-Esw0DI4", "FUNCTION", "getFUNCTION-Esw0DI4", "GLOBAL", "getGLOBAL-Esw0DI4", "IMPORT", "getIMPORT-Esw0DI4", "MEMORY", "getMEMORY-Esw0DI4", "START", "getSTART-Esw0DI4", "TABLE", "getTABLE-Esw0DI4", "TAG", "getTAG-Esw0DI4", "TYPE", "getTYPE-Esw0DI4", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: renamed from: getCUSTOM-Esw0DI4, reason: not valid java name */
            public final short m7520getCUSTOMEsw0DI4() {
                return Section.CUSTOM;
            }

            /* JADX INFO: renamed from: getTYPE-Esw0DI4, reason: not valid java name */
            public final short m7532getTYPEEsw0DI4() {
                return Section.TYPE;
            }

            /* JADX INFO: renamed from: getIMPORT-Esw0DI4, reason: not valid java name */
            public final short m7527getIMPORTEsw0DI4() {
                return Section.IMPORT;
            }

            /* JADX INFO: renamed from: getFUNCTION-Esw0DI4, reason: not valid java name */
            public final short m7525getFUNCTIONEsw0DI4() {
                return Section.FUNCTION;
            }

            /* JADX INFO: renamed from: getTABLE-Esw0DI4, reason: not valid java name */
            public final short m7530getTABLEEsw0DI4() {
                return Section.TABLE;
            }

            /* JADX INFO: renamed from: getMEMORY-Esw0DI4, reason: not valid java name */
            public final short m7528getMEMORYEsw0DI4() {
                return Section.MEMORY;
            }

            /* JADX INFO: renamed from: getGLOBAL-Esw0DI4, reason: not valid java name */
            public final short m7526getGLOBALEsw0DI4() {
                return Section.GLOBAL;
            }

            /* JADX INFO: renamed from: getEXPORT-Esw0DI4, reason: not valid java name */
            public final short m7524getEXPORTEsw0DI4() {
                return Section.EXPORT;
            }

            /* JADX INFO: renamed from: getSTART-Esw0DI4, reason: not valid java name */
            public final short m7529getSTARTEsw0DI4() {
                return Section.START;
            }

            /* JADX INFO: renamed from: getELEMENT-Esw0DI4, reason: not valid java name */
            public final short m7523getELEMENTEsw0DI4() {
                return Section.ELEMENT;
            }

            /* JADX INFO: renamed from: getCODE-Esw0DI4, reason: not valid java name */
            public final short m7519getCODEEsw0DI4() {
                return Section.CODE;
            }

            /* JADX INFO: renamed from: getDATA-Esw0DI4, reason: not valid java name */
            public final short m7521getDATAEsw0DI4() {
                return Section.DATA;
            }

            /* JADX INFO: renamed from: getDATA_COUNT-Esw0DI4, reason: not valid java name */
            public final short m7522getDATA_COUNTEsw0DI4() {
                return Section.DATA_COUNT;
            }

            /* JADX INFO: renamed from: getTAG-Esw0DI4, reason: not valid java name */
            public final short m7531getTAGEsw0DI4() {
                return Section.TAG;
            }
        }

        private /* synthetic */ Section(short s) {
            this.id = s;
        }

        /* JADX INFO: renamed from: getId-Mh2AYeg, reason: not valid java name and from getter */
        public final short getId() {
            return this.id;
        }
    }
}
