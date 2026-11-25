package org.jetbrains.kotlin.wasm.ir.convertors;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.Unit;
import kotlin.UnsignedKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.wasm.ir.OperatorsKt;
import org.jetbrains.kotlin.wasm.ir.UtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmAnyRef;
import org.jetbrains.kotlin.wasm.ir.WasmArrayDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmData;
import org.jetbrains.kotlin.wasm.ir.WasmDataMode;
import org.jetbrains.kotlin.wasm.ir.WasmElement;
import org.jetbrains.kotlin.wasm.ir.WasmEqRef;
import org.jetbrains.kotlin.wasm.ir.WasmExport;
import org.jetbrains.kotlin.wasm.ir.WasmExternRef;
import org.jetbrains.kotlin.wasm.ir.WasmF32;
import org.jetbrains.kotlin.wasm.ir.WasmF64;
import org.jetbrains.kotlin.wasm.ir.WasmFuncRef;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmFunctionType;
import org.jetbrains.kotlin.wasm.ir.WasmGlobal;
import org.jetbrains.kotlin.wasm.ir.WasmI16;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmI64;
import org.jetbrains.kotlin.wasm.ir.WasmI8;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmImmediateKind;
import org.jetbrains.kotlin.wasm.ir.WasmImportDescriptor;
import org.jetbrains.kotlin.wasm.ir.WasmInstr;
import org.jetbrains.kotlin.wasm.ir.WasmInstrWithoutLocation;
import org.jetbrains.kotlin.wasm.ir.WasmLimits;
import org.jetbrains.kotlin.wasm.ir.WasmLocal;
import org.jetbrains.kotlin.wasm.ir.WasmMemory;
import org.jetbrains.kotlin.wasm.ir.WasmModule;
import org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField;
import org.jetbrains.kotlin.wasm.ir.WasmOp;
import org.jetbrains.kotlin.wasm.ir.WasmStructDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmTable;
import org.jetbrains.kotlin.wasm.ir.WasmTag;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmTypeDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmV128;

/* JADX INFO: compiled from: WasmBinaryToIR.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ò\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J7\u0010J\u001a\u0002HK\"\u0004\b\u0000\u0010K2\f\u0010L\u001a\b\u0012\u0004\u0012\u0002HK0M2\f\u0010N\u001a\b\u0012\u0004\u0012\u0002HK0M2\u0006\u0010O\u001a\u00020PH\u0002¢\u0006\u0002\u0010QJ\u0010\u0010R\u001a\u00020\u001a2\u0006\u0010O\u001a\u00020PH\u0002J,\u0010S\u001a\u00020T2!\u0010U\u001a\u001d\u0012\u0013\u0012\u00110F¢\u0006\f\bW\u0012\b\bX\u0012\u0004\b\b(O\u0012\u0004\u0012\u00020T0VH\u0082\bJ\u0010\u0010Y\u001a\u00020<2\u0006\u0010O\u001a\u00020PH\u0002J\u0010\u0010Z\u001a\u00020&2\u0006\u0010O\u001a\u00020PH\u0002J8\u0010[\u001a\b\u0012\u0004\u0012\u0002HK0M\"\u0004\b\u0000\u0010K2!\u0010U\u001a\u001d\u0012\u0013\u0012\u00110F¢\u0006\f\bW\u0012\b\bX\u0012\u0004\b\b(O\u0012\u0004\u0012\u0002HK0VH\u0082\bJ\u0010\u0010\\\u001a\u00020.2\u0006\u0010O\u001a\u00020PH\u0002J\u0006\u0010]\u001a\u00020^J\b\u0010_\u001a\u00020`H\u0002J\u000e\u0010a\u001a\b\u0012\u0004\u0012\u00020b0\fH\u0002J&\u0010a\u001a\u00020T2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020b0\f2\u000e\b\u0002\u0010d\u001a\b\u0012\u0004\u0012\u00020e0MH\u0002J\u0016\u0010f\u001a\u00020b2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020e0MH\u0002J\b\u0010g\u001a\u00020hH\u0002J\b\u0010i\u001a\u00020\nH\u0002J\u0006\u0010j\u001a\u00020kJ\u0014\u0010l\u001a\u0002042\n\b\u0002\u0010m\u001a\u0004\u0018\u00010nH\u0002J\b\u0010o\u001a\u00020#H\u0002J\b\u0010p\u001a\u00020\nH\u0002J\u0010\u0010q\u001a\u00020\n2\u0006\u0010r\u001a\u00020\tH\u0002J\u0010\u0010s\u001a\u0002012\u0006\u0010O\u001a\u00020PH\u0002J\u0010\u0010t\u001a\u0002042\u0006\u0010O\u001a\u00020PH\u0002J\f\u0010u\u001a\u00020P*\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u001b\u0010\u001c\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d0\f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000fR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000fR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\f¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u000fR\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\f¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u000fR\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\f¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u000fR\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020&0\f¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u000fR\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\f¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u000fR\u0017\u00100\u001a\b\u0012\u0004\u0012\u0002010\f¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u000fR\u0017\u00103\u001a\b\u0012\u0004\u0012\u0002040\f¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u000fR\u0017\u00106\u001a\b\u0012\u0004\u0012\u0002070\f¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u000fR\u0017\u00109\u001a\b\u0012\u0004\u0012\u00020.0\f¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u000fR\u001c\u0010;\u001a\u0004\u0018\u00010<X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u0017\u0010A\u001a\b\u0012\u0004\u0012\u0002010\f¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u000fR\u0017\u0010C\u001a\b\u0012\u0004\u0012\u0002040\f¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u000fR\u001c\u0010E\u001a\u00020FX\u0086Dø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010I\u001a\u0004\bG\u0010H\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006v"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinaryToIR;", "", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader;", "(Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader;)V", "getB", "()Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader;", "codeToSimpleValueType", "", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "data", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "getData", "()Ljava/util/List;", "dataCount", "", "getDataCount", "()Z", "setDataCount", "(Z)V", "definedFunctions", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "getDefinedFunctions", PathTree.NAME_elements, "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "getElements", PsiKeyword.EXPORTS, "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "getExports", "functionTypes", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "getFunctionTypes", "gcTypes", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "getGcTypes", "globals", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "getGlobals", "importedFunctions", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "getImportedFunctions", "importedGlobals", "getImportedGlobals", "importedMemories", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "getImportedMemories", "importedTables", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "getImportedTables", "importedTags", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "getImportedTags", "importsInOrder", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "getImportsInOrder", "memory", "getMemory", "startFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "getStartFunction", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "setStartFunction", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;)V", "table", "getTable", "tags", "getTags", "validVersion", "Lkotlin/UInt;", "getValidVersion-pVg5ArA", "()I", "I", "byIdx", ExifInterface.GPS_DIRECTION_TRUE, "l1", "", "l2", "index", "", "(Ljava/util/List;Ljava/util/List;I)Ljava/lang/Object;", "elemByIdx", "forEachVectorElement", "", "block", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "funByIdx", "globalByIdx", "mapVector", "memoryByIdx", "parseModule", "Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "readBlockType", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", "readExpression", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "instructions", "locals", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "readInstruction", "readLimits", "Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "readRefType", "readString", "", "readTag", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "readTypeDeclaration", "readValueType", "readValueTypeImpl", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "tableByIdx", "tagByIdx", "readVarUInt32AsInt", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmBinaryToIR {
    private final MyByteReader b;
    private final Map<Byte, WasmType> codeToSimpleValueType;
    private final List<WasmData> data;
    private boolean dataCount;
    private final List<WasmFunction.Defined> definedFunctions;
    private final List<WasmElement> elements;
    private final List<WasmExport<?>> exports;
    private final List<WasmFunctionType> functionTypes;
    private final List<WasmTypeDeclaration> gcTypes;
    private final List<WasmGlobal> globals;
    private final List<WasmFunction.Imported> importedFunctions;
    private final List<WasmGlobal> importedGlobals;
    private final List<WasmMemory> importedMemories;
    private final List<WasmTable> importedTables;
    private final List<WasmTag> importedTags;
    private final List<WasmNamedModuleField> importsInOrder;
    private final List<WasmMemory> memory;
    private WasmFunction startFunction;
    private final List<WasmTable> table;
    private final List<WasmTag> tags;
    private final int validVersion;

    /* JADX INFO: compiled from: WasmBinaryToIR.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[WasmOp.values().length];
            try {
                iArr[WasmOp.END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WasmOp.BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WasmOp.LOOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WasmOp.IF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[WasmImmediateKind.values().length];
            try {
                iArr2[WasmImmediateKind.CONST_U8.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[WasmImmediateKind.CONST_I32.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[WasmImmediateKind.CONST_I64.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[WasmImmediateKind.CONST_F32.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[WasmImmediateKind.CONST_F64.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[WasmImmediateKind.MEM_ARG.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[WasmImmediateKind.BLOCK_TYPE.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[WasmImmediateKind.FUNC_IDX.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[WasmImmediateKind.LOCAL_IDX.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[WasmImmediateKind.GLOBAL_IDX.ordinal()] = 10;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[WasmImmediateKind.TYPE_IDX.ordinal()] = 11;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[WasmImmediateKind.MEMORY_IDX.ordinal()] = 12;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[WasmImmediateKind.DATA_IDX.ordinal()] = 13;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[WasmImmediateKind.TABLE_IDX.ordinal()] = 14;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr2[WasmImmediateKind.LABEL_IDX.ordinal()] = 15;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[WasmImmediateKind.TAG_IDX.ordinal()] = 16;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[WasmImmediateKind.LABEL_IDX_VECTOR.ordinal()] = 17;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[WasmImmediateKind.ELEM_IDX.ordinal()] = 18;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[WasmImmediateKind.VAL_TYPE_VECTOR.ordinal()] = 19;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[WasmImmediateKind.STRUCT_TYPE_IDX.ordinal()] = 20;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[WasmImmediateKind.STRUCT_FIELD_IDX.ordinal()] = 21;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[WasmImmediateKind.TYPE_IMM.ordinal()] = 22;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[WasmImmediateKind.HEAP_TYPE.ordinal()] = 23;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[WasmImmediateKind.LOCAL_DEFS.ordinal()] = 24;
            } catch (NoSuchFieldError unused28) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public WasmBinaryToIR(MyByteReader b) {
        Intrinsics.checkNotNullParameter(b, "b");
        this.b = b;
        this.validVersion = 1;
        this.functionTypes = new ArrayList();
        this.gcTypes = new ArrayList();
        this.importsInOrder = new ArrayList();
        this.importedFunctions = new ArrayList();
        this.importedMemories = new ArrayList();
        this.importedTables = new ArrayList();
        this.importedGlobals = new ArrayList();
        this.importedTags = new ArrayList();
        this.definedFunctions = new ArrayList();
        this.table = new ArrayList();
        this.memory = new ArrayList();
        this.globals = new ArrayList();
        this.exports = new ArrayList();
        this.elements = new ArrayList();
        this.data = new ArrayList();
        this.dataCount = true;
        this.tags = new ArrayList();
        List listListOf = CollectionsKt.listOf((Object[]) new WasmType[]{WasmI32.INSTANCE, WasmI64.INSTANCE, WasmF32.INSTANCE, WasmF64.INSTANCE, WasmV128.INSTANCE, WasmI8.INSTANCE, WasmI16.INSTANCE, WasmFuncRef.INSTANCE, WasmAnyRef.INSTANCE, WasmExternRef.INSTANCE, WasmEqRef.INSTANCE});
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listListOf, 10)), 16));
        for (Object obj : listListOf) {
            linkedHashMap.put(Byte.valueOf(((WasmType) obj).getCode()), obj);
        }
        this.codeToSimpleValueType = linkedHashMap;
    }

    public final MyByteReader getB() {
        return this.b;
    }

    /* JADX INFO: renamed from: getValidVersion-pVg5ArA, reason: not valid java name and from getter */
    public final int getValidVersion() {
        return this.validVersion;
    }

    public final List<WasmFunctionType> getFunctionTypes() {
        return this.functionTypes;
    }

    public final List<WasmTypeDeclaration> getGcTypes() {
        return this.gcTypes;
    }

    public final List<WasmNamedModuleField> getImportsInOrder() {
        return this.importsInOrder;
    }

    public final List<WasmFunction.Imported> getImportedFunctions() {
        return this.importedFunctions;
    }

    public final List<WasmMemory> getImportedMemories() {
        return this.importedMemories;
    }

    public final List<WasmTable> getImportedTables() {
        return this.importedTables;
    }

    public final List<WasmGlobal> getImportedGlobals() {
        return this.importedGlobals;
    }

    public final List<WasmTag> getImportedTags() {
        return this.importedTags;
    }

    public final List<WasmFunction.Defined> getDefinedFunctions() {
        return this.definedFunctions;
    }

    public final List<WasmTable> getTable() {
        return this.table;
    }

    public final List<WasmMemory> getMemory() {
        return this.memory;
    }

    public final List<WasmGlobal> getGlobals() {
        return this.globals;
    }

    public final List<WasmExport<?>> getExports() {
        return this.exports;
    }

    public final WasmFunction getStartFunction() {
        return this.startFunction;
    }

    public final void setStartFunction(WasmFunction wasmFunction) {
        this.startFunction = wasmFunction;
    }

    public final List<WasmElement> getElements() {
        return this.elements;
    }

    public final List<WasmData> getData() {
        return this.data;
    }

    public final boolean getDataCount() {
        return this.dataCount;
    }

    public final void setDataCount(boolean z) {
        this.dataCount = z;
    }

    public final List<WasmTag> getTags() {
        return this.tags;
    }

    private final <T> T byIdx(List<? extends T> l1, List<? extends T> l2, int index) {
        if (index < l1.size()) {
            return l1.get(index);
        }
        return l2.get(index - l1.size());
    }

    private final WasmFunction funByIdx(int index) {
        return (WasmFunction) byIdx(this.importedFunctions, this.definedFunctions, index);
    }

    private final WasmMemory memoryByIdx(int index) {
        return (WasmMemory) byIdx(this.importedMemories, this.memory, index);
    }

    private final WasmElement elemByIdx(int index) {
        return this.elements.get(index);
    }

    private final WasmTable tableByIdx(int index) {
        return (WasmTable) byIdx(this.importedTables, this.table, index);
    }

    private final WasmGlobal globalByIdx(int index) {
        return (WasmGlobal) byIdx(this.importedGlobals, this.globals, index);
    }

    private final WasmTag tagByIdx(int index) {
        return (WasmTag) byIdx(this.importedTags, this.tags, index);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:169:0x0606 A[LOOP:0: B:180:0x0019->B:169:0x0606, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:191:0x0623 A[SYNTHETIC] */
    public final WasmModule parseModule() throws IOException {
        int i;
        WasmExport<?> function;
        WasmElement.Mode.Active active;
        WasmFuncRef valueType;
        WasmTable.Value expression;
        WasmDataMode active2;
        if (this.b.m7501readUInt32pVg5ArA() != 1836278016) {
            throw new IllegalStateException("InvalidMagicNumber".toString());
        }
        if (this.b.m7501readUInt32pVg5ArA() != this.validVersion) {
            throw new IllegalStateException("InvalidVersion(version.toLong(), listOf(validVersion.toLong()))".toString());
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            try {
                short varUInt7 = this.b.readVarUInt7();
                if (varUInt7 > 12) {
                    throw new IllegalStateException("InvalidSectionId(sectionId)".toString());
                }
                if (varUInt7 != 12 && i3 != 12 && varUInt7 != 0 && varUInt7 <= i3) {
                    throw new IllegalArgumentException(("Section ID " + ((int) varUInt7) + " came after " + i3).toString());
                }
                int iMax = Math.max((int) varUInt7, i3);
                int varUInt32AsInt = readVarUInt32AsInt(this.b);
                MyByteReader myByteReader = this.b;
                String str = "Wasm section " + ((int) varUInt7) + " of size " + varUInt32AsInt;
                long offset = myByteReader.getOffset() + ((long) varUInt32AsInt);
                myByteReader.getSizeLimits().add(new MyByteReader.SizeLimit(offset, str));
                myByteReader.setCurrentMaxSize(offset);
                byte b = 3;
                switch (varUInt7) {
                    case 0:
                        i = iMax;
                        this.b.readBytes(Integer.valueOf(varUInt32AsInt));
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 1:
                        i = iMax;
                        int i4 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i5 = 0; UnsignedKt.uintCompare(i5, i4) < 0; i5++) {
                            WasmTypeDeclaration typeDeclaration = readTypeDeclaration();
                            if (typeDeclaration instanceof WasmFunctionType) {
                                this.functionTypes.add(typeDeclaration);
                            } else if (typeDeclaration instanceof WasmStructDeclaration) {
                                this.gcTypes.add(typeDeclaration);
                            } else {
                                boolean z = typeDeclaration instanceof WasmArrayDeclaration;
                            }
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 2:
                        i = iMax;
                        int i6 = this.b.m7504readVarUInt32pVg5ArA();
                        int i7 = 0;
                        while (UnsignedKt.uintCompare(i7, i6) < 0) {
                            WasmImportDescriptor wasmImportDescriptor = new WasmImportDescriptor(readString(), readString());
                            byte b2 = this.b.readByte();
                            if (b2 == 0) {
                                WasmFunctionType wasmFunctionType = this.functionTypes.get(readVarUInt32AsInt(this.b));
                                List<WasmFunction.Imported> list = this.importedFunctions;
                                WasmFunction.Imported imported = new WasmFunction.Imported("", new WasmSymbol(wasmFunctionType), wasmImportDescriptor);
                                this.importsInOrder.add(imported);
                                list.add(imported);
                                Unit unit = Unit.INSTANCE;
                            } else if (b2 == 1) {
                                WasmType refType = readRefType();
                                WasmLimits limits = readLimits();
                                List<WasmTable> list2 = this.importedTables;
                                WasmTable wasmTable = new WasmTable(limits, refType, wasmImportDescriptor);
                                this.importsInOrder.add(wasmTable);
                                list2.add(wasmTable);
                                Unit unit2 = Unit.INSTANCE;
                            } else if (b2 == 2) {
                                WasmLimits limits2 = readLimits();
                                List<WasmMemory> list3 = this.importedMemories;
                                WasmMemory wasmMemory = new WasmMemory(limits2, wasmImportDescriptor);
                                this.importsInOrder.add(wasmMemory);
                                list3.add(wasmMemory);
                                Unit unit3 = Unit.INSTANCE;
                            } else if (b2 == b) {
                                List<WasmGlobal> list4 = this.importedGlobals;
                                WasmGlobal wasmGlobal = new WasmGlobal("", readValueType(), this.b.readVarUInt1(), CollectionsKt.emptyList(), wasmImportDescriptor);
                                this.importsInOrder.add(wasmGlobal);
                                list4.add(wasmGlobal);
                                Unit unit4 = Unit.INSTANCE;
                            } else if (b2 == 4) {
                                WasmTag tag = readTag(wasmImportDescriptor);
                                this.importedTags.add(tag);
                                this.importsInOrder.add(tag);
                                Unit unit5 = Unit.INSTANCE;
                            } else {
                                throw new IllegalStateException(("Unsupported import kind " + ((int) b2)).toString());
                            }
                            i7++;
                            b = 3;
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 3:
                        i = iMax;
                        int i8 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i9 = 0; UnsignedKt.uintCompare(i9, i8) < 0; i9++) {
                            WasmFunctionType wasmFunctionType2 = this.functionTypes.get(readVarUInt32AsInt(this.b));
                            List<WasmFunction.Defined> list5 = this.definedFunctions;
                            WasmSymbol wasmSymbol = new WasmSymbol(wasmFunctionType2);
                            List<WasmType> parameterTypes = wasmFunctionType2.getParameterTypes();
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameterTypes, 10));
                            int i10 = 0;
                            for (Object obj : parameterTypes) {
                                int i11 = i10 + 1;
                                if (i10 < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                arrayList.add(new WasmLocal(i10, "", (WasmType) obj, true));
                                i10 = i11;
                            }
                            list5.add(new WasmFunction.Defined("", wasmSymbol, CollectionsKt.toMutableList((Collection) arrayList), null, 8, null));
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 4:
                        i = iMax;
                        int i12 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i13 = 0; UnsignedKt.uintCompare(i13, i12) < 0; i13++) {
                            this.table.add(new WasmTable(readLimits(), readRefType(), null, 4, null));
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 5:
                        i = iMax;
                        int i14 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i15 = 0; UnsignedKt.uintCompare(i15, i14) < 0; i15++) {
                            this.memory.add(new WasmMemory(readLimits(), null, 2, null));
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 6:
                        i = iMax;
                        int i16 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i17 = 0; UnsignedKt.uintCompare(i17, i16) < 0; i17++) {
                            ArrayList arrayList2 = new ArrayList();
                            this.globals.add(new WasmGlobal("", readValueType(), this.b.readVarUInt1(), arrayList2, null, 16, null));
                            readExpression$default(this, arrayList2, null, 2, null);
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 7:
                        i = iMax;
                        int i18 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i19 = 0; UnsignedKt.uintCompare(i19, i18) < 0; i19++) {
                            String string = readString();
                            byte b3 = this.b.readByte();
                            int varUInt32AsInt2 = readVarUInt32AsInt(this.b);
                            List<WasmExport<?>> list6 = this.exports;
                            if (b3 == 0) {
                                function = new WasmExport.Function(string, funByIdx(varUInt32AsInt2));
                            } else if (b3 == 1) {
                                function = new WasmExport.Table(string, tableByIdx(varUInt32AsInt2));
                            } else if (b3 == 2) {
                                function = new WasmExport.Memory(string, memoryByIdx(varUInt32AsInt2));
                            } else if (b3 == 3) {
                                function = new WasmExport.Global(string, globalByIdx(varUInt32AsInt2));
                            } else if (b3 == 4) {
                                function = new WasmExport.Tag(string, tagByIdx(varUInt32AsInt2));
                            } else {
                                throw new IllegalStateException(("Invalid export kind " + ((int) b3)).toString());
                            }
                            list6.add(function);
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 8:
                        i = iMax;
                        if (this.startFunction != null) {
                            throw new IllegalArgumentException("Start function is already defined".toString());
                        }
                        this.startFunction = funByIdx(readVarUInt32AsInt(this.b));
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 9:
                        i = iMax;
                        int i20 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i21 = 0; UnsignedKt.uintCompare(i21, i20) < 0; i21++) {
                            int i22 = this.b.m7500readUBytew2LRezQ() & 255;
                            switch (i22) {
                                case 0:
                                case 4:
                                    active = new WasmElement.Mode.Active(tableByIdx(0), readExpression());
                                    break;
                                case 1:
                                case 5:
                                    active = WasmElement.Mode.Passive.INSTANCE;
                                    break;
                                case 2:
                                case 6:
                                    active = new WasmElement.Mode.Active(tableByIdx(this.b.m7504readVarUInt32pVg5ArA()), readExpression());
                                    break;
                                case 3:
                                case 7:
                                    active = WasmElement.Mode.Declarative.INSTANCE;
                                    break;
                                default:
                                    throw new IllegalStateException(("Invalid element first byte " + i22).toString());
                            }
                            if (i22 < 5) {
                                if (1 <= i22 && i22 < 4 && this.b.readByte() != 0) {
                                    throw new IllegalArgumentException("Failed requirement.".toString());
                                }
                                valueType = WasmFuncRef.INSTANCE;
                            } else {
                                valueType = readValueType();
                            }
                            UIntRange uIntRange = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
                            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange, 10));
                            Iterator<UInt> it = uIntRange.iterator();
                            while (it.hasNext()) {
                                it.next().getData();
                                if (i22 < 4) {
                                    expression = new WasmTable.Value.Function(funByIdx(readVarUInt32AsInt(this.b)));
                                } else {
                                    ArrayList arrayList4 = new ArrayList();
                                    readExpression$default(this, arrayList4, null, 2, null);
                                    expression = new WasmTable.Value.Expression(arrayList4);
                                }
                                arrayList3.add(expression);
                            }
                            this.elements.add(new WasmElement(valueType, arrayList3, active));
                        }
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 10:
                        int i23 = this.b.m7504readVarUInt32pVg5ArA();
                        int i24 = i2;
                        while (UnsignedKt.uintCompare(i24, i23) < 0) {
                            WasmFunction.Defined defined = this.definedFunctions.get(i24);
                            int varUInt32AsInt3 = readVarUInt32AsInt(this.b);
                            MyByteReader myByteReader2 = this.b;
                            long offset2 = ((long) varUInt32AsInt3) + myByteReader2.getOffset();
                            myByteReader2.getSizeLimits().add(new MyByteReader.SizeLimit(offset2, "function body size"));
                            myByteReader2.setCurrentMaxSize(offset2);
                            UIntRange uIntRange2 = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
                            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange2, 10));
                            Iterator<UInt> it2 = uIntRange2.iterator();
                            while (it2.hasNext()) {
                                it2.next().getData();
                                int varUInt32AsInt4 = readVarUInt32AsInt(this.b);
                                WasmType valueType2 = readValueType();
                                WasmLocal wasmLocal = (WasmLocal) CollectionsKt.lastOrNull((List) defined.getLocals());
                                int id2 = wasmLocal != null ? wasmLocal.getId() + 1 : 0;
                                Iterator<UInt> it3 = it2;
                                int i25 = 0;
                                while (i25 < varUInt32AsInt4) {
                                    defined.getLocals().add(new WasmLocal(id2 + i25, "", valueType2, false));
                                    i25++;
                                    iMax = iMax;
                                    i23 = i23;
                                    varUInt32AsInt4 = varUInt32AsInt4;
                                    id2 = id2;
                                }
                                arrayList5.add(Unit.INSTANCE);
                                it2 = it3;
                            }
                            int i26 = iMax;
                            int i27 = i23;
                            readExpression(defined.getInstructions(), defined.getLocals());
                            if (myByteReader2.getOffset() != myByteReader2.getCurrentMaxSize()) {
                                throw new IllegalArgumentException(("Ending size-limited block \"function body size\". We haven't read all " + varUInt32AsInt3 + " bytes.").toString());
                            }
                            CollectionsKt.removeLast(myByteReader2.getSizeLimits());
                            myByteReader2.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader2.getSizeLimits())).getMaxSize());
                            i24++;
                            iMax = i26;
                            i23 = i27;
                        }
                        i = iMax;
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 11:
                        int i28 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i29 = i2; UnsignedKt.uintCompare(i29, i28) < 0; i29++) {
                            byte b4 = this.b.readByte();
                            if (b4 == 0) {
                                active2 = new WasmDataMode.Active(i2, readExpression());
                            } else if (b4 == 1) {
                                active2 = WasmDataMode.Passive.INSTANCE;
                            } else if (b4 == 2) {
                                active2 = new WasmDataMode.Active(readVarUInt32AsInt(this.b), readExpression());
                            } else {
                                throw new IllegalStateException(("Unsupported data mode " + ((int) b4)).toString());
                            }
                            this.data.add(new WasmData(active2, this.b.readBytes(Integer.valueOf(readVarUInt32AsInt(this.b)))));
                        }
                        i = iMax;
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 12:
                        this.b.m7504readVarUInt32pVg5ArA();
                        this.dataCount = true;
                        i = iMax;
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    case 13:
                        int i30 = this.b.m7504readVarUInt32pVg5ArA();
                        for (int i31 = i2; UnsignedKt.uintCompare(i31, i30) < 0; i31++) {
                            this.tags.add(readTag$default(this, null, 1, null));
                        }
                        i = iMax;
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                    default:
                        i = iMax;
                        if (myByteReader.getOffset() != myByteReader.getCurrentMaxSize()) {
                            throw new IllegalArgumentException(("Ending size-limited block \"" + str + "\". We haven't read all " + varUInt32AsInt + " bytes.").toString());
                        }
                        CollectionsKt.removeLast(myByteReader.getSizeLimits());
                        myByteReader.setCurrentMaxSize(((MyByteReader.SizeLimit) CollectionsKt.last((List) myByteReader.getSizeLimits())).getMaxSize());
                        i3 = i;
                        i2 = 0;
                        break;
                        break;
                }
            } catch (Throwable unused) {
                WasmModule wasmModule = new WasmModule(this.functionTypes, this.gcTypes, this.importsInOrder, this.importedFunctions, this.importedMemories, this.importedTables, this.importedGlobals, this.importedTags, this.definedFunctions, this.table, this.memory, this.globals, this.exports, this.elements, this.tags, this.startFunction, this.data, this.dataCount);
                UtilsKt.calculateIds(wasmModule);
                return wasmModule;
            }
        }
    }

    private final WasmLimits readLimits() {
        return new WasmLimits(this.b.m7504readVarUInt32pVg5ArA(), this.b.readVarUInt1() ? UInt.m2987boximpl(this.b.m7504readVarUInt32pVg5ArA()) : null, null);
    }

    static /* synthetic */ WasmTag readTag$default(WasmBinaryToIR wasmBinaryToIR, WasmImportDescriptor wasmImportDescriptor, int i, Object obj) {
        if ((i & 1) != 0) {
            wasmImportDescriptor = null;
        }
        return wasmBinaryToIR.readTag(wasmImportDescriptor);
    }

    private final WasmTag readTag(WasmImportDescriptor importPair) {
        if (this.b.readByte() != 0) {
            throw new IllegalStateException("as per spec".toString());
        }
        return new WasmTag(this.functionTypes.get(readVarUInt32AsInt(this.b)), importPair);
    }

    private final List<WasmInstr> readExpression() {
        ArrayList arrayList = new ArrayList();
        readExpression$default(this, arrayList, null, 2, null);
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void readExpression$default(WasmBinaryToIR wasmBinaryToIR, List list, List list2, int i, Object obj) {
        if ((i & 2) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        wasmBinaryToIR.readExpression(list, list2);
    }

    private final void readExpression(List<WasmInstr> instructions, List<WasmLocal> locals) {
        int i = 0;
        while (i >= 0) {
            WasmInstr instruction = readInstruction(locals);
            int i2 = WhenMappings.$EnumSwitchMapping$0[instruction.getOperator().ordinal()];
            if (i2 != 1) {
                if (i2 == 2 || i2 == 3 || i2 == 4) {
                    i++;
                }
            } else if (i == 0) {
                return;
            } else {
                i--;
            }
            instructions.add(instruction);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private final WasmInstr readInstruction(List<WasmLocal> locals) {
        WasmImmediate constU8;
        int iM2916constructorimpl = UByte.m2916constructorimpl(this.b.readByte()) & 255;
        if (WasmBinaryToIRKt.getTwoByteOpcodes().contains(Integer.valueOf(iM2916constructorimpl))) {
            iM2916constructorimpl = (iM2916constructorimpl << 8) + (UByte.m2916constructorimpl(this.b.readByte()) & 255);
        }
        WasmOp wasmOp = OperatorsKt.getOpcodesToOp().get(Integer.valueOf(iM2916constructorimpl));
        if (wasmOp == null) {
            StringBuilder sb = new StringBuilder("Wrong opcode 0x");
            String string = Integer.toString(iM2916constructorimpl, CharsKt.checkRadix(16));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            throw new IllegalStateException(sb.append(string).toString().toString());
        }
        List<WasmImmediateKind> immediates = wasmOp.getImmediates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(immediates, 10));
        Iterator<T> it = immediates.iterator();
        while (it.hasNext()) {
            switch (WhenMappings.$EnumSwitchMapping$1[((WasmImmediateKind) it.next()).ordinal()]) {
                case 1:
                    constU8 = new WasmImmediate.ConstU8(this.b.m7500readUBytew2LRezQ(), null);
                    break;
                case 2:
                    constU8 = new WasmImmediate.ConstI32(this.b.readVarInt32());
                    break;
                case 3:
                    constU8 = new WasmImmediate.ConstI64(this.b.readVarInt64());
                    break;
                case 4:
                    constU8 = new WasmImmediate.ConstF32(this.b.m7501readUInt32pVg5ArA(), null);
                    break;
                case 5:
                    constU8 = new WasmImmediate.ConstF64(this.b.m7502readUInt64sVKNKU(), null);
                    break;
                case 6:
                    constU8 = new WasmImmediate.MemArg(this.b.m7504readVarUInt32pVg5ArA(), this.b.m7504readVarUInt32pVg5ArA(), null);
                    break;
                case 7:
                    constU8 = readBlockType();
                    break;
                case 8:
                    constU8 = new WasmImmediate.FuncIdx(funByIdx(readVarUInt32AsInt(this.b)));
                    break;
                case 9:
                    constU8 = new WasmImmediate.LocalIdx(locals.get(readVarUInt32AsInt(this.b)));
                    break;
                case 10:
                    constU8 = new WasmImmediate.GlobalIdx(globalByIdx(readVarUInt32AsInt(this.b)));
                    break;
                case 11:
                    constU8 = new WasmImmediate.TypeIdx(this.functionTypes.get(readVarUInt32AsInt(this.b)));
                    break;
                case 12:
                    constU8 = new WasmImmediate.MemoryIdx(readVarUInt32AsInt(this.b));
                    break;
                case 13:
                    constU8 = new WasmImmediate.DataIdx(readVarUInt32AsInt(this.b));
                    break;
                case 14:
                    constU8 = new WasmImmediate.TableIdx(readVarUInt32AsInt(this.b));
                    break;
                case 15:
                    constU8 = new WasmImmediate.LabelIdx(readVarUInt32AsInt(this.b));
                    break;
                case 16:
                    constU8 = new WasmImmediate.TagIdx(readVarUInt32AsInt(this.b));
                    break;
                case 17:
                    UIntRange uIntRange = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange, 10));
                    Iterator<UInt> it2 = uIntRange.iterator();
                    while (it2.hasNext()) {
                        it2.next().getData();
                        arrayList2.add(Integer.valueOf(readVarUInt32AsInt(this.b)));
                    }
                    constU8 = new WasmImmediate.LabelIdxVector(arrayList2);
                    break;
                case 18:
                    constU8 = new WasmImmediate.ElemIdx(elemByIdx(readVarUInt32AsInt(this.b)));
                    break;
                case 19:
                    UIntRange uIntRange2 = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange2, 10));
                    Iterator<UInt> it3 = uIntRange2.iterator();
                    while (it3.hasNext()) {
                        it3.next().getData();
                        arrayList3.add(readValueType());
                    }
                    constU8 = new WasmImmediate.ValTypeVector(arrayList3);
                    break;
                case 20:
                    throw new NotImplementedError(null, 1, null);
                case 21:
                    throw new NotImplementedError(null, 1, null);
                case 22:
                    throw new NotImplementedError(null, 1, null);
                case 23:
                    constU8 = new WasmImmediate.HeapType(readRefType());
                    break;
                case 24:
                    throw new NotImplementedError(null, 1, null);
                default:
                    throw new NoWhenBranchMatchedException();
            }
            arrayList.add(constU8);
        }
        return new WasmInstrWithoutLocation(wasmOp, arrayList);
    }

    private final WasmTypeDeclaration readTypeDeclaration() {
        if (this.b.readVarInt7() != -32) {
            throw new NotImplementedError(null, 1, null);
        }
        UIntRange uIntRange = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange, 10));
        Iterator<UInt> it = uIntRange.iterator();
        while (it.hasNext()) {
            it.next().getData();
            arrayList.add(readValueType());
        }
        ArrayList arrayList2 = arrayList;
        UIntRange uIntRange2 = URangesKt.m4166untilJ1ME1BU(0, this.b.m7504readVarUInt32pVg5ArA());
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(uIntRange2, 10));
        Iterator<UInt> it2 = uIntRange2.iterator();
        while (it2.hasNext()) {
            it2.next().getData();
            arrayList3.add(readValueType());
        }
        return new WasmFunctionType(arrayList2, arrayList3);
    }

    private final WasmType readValueType() {
        return readValueTypeImpl(this.b.readVarInt7());
    }

    private final WasmImmediate.BlockType readBlockType() {
        long varInt64 = this.b.readVarInt64();
        if (varInt64 >= 0) {
            return new WasmImmediate.BlockType.Function(this.functionTypes.get((int) varInt64));
        }
        if (varInt64 == -64) {
            return new WasmImmediate.BlockType.Value(null);
        }
        return new WasmImmediate.BlockType.Value(readValueTypeImpl((byte) varInt64));
    }

    private final WasmType readRefType() throws IOException {
        byte b = this.b.readByte();
        if (b == 111) {
            return WasmExternRef.INSTANCE;
        }
        if (b == 112) {
            return WasmFuncRef.INSTANCE;
        }
        StringBuilder sb = new StringBuilder("Unsupported heap type ");
        String string = Integer.toString(b, CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        throw new IllegalStateException(sb.append(string).toString().toString());
    }

    private final WasmType readValueTypeImpl(byte code) {
        WasmType wasmType = this.codeToSimpleValueType.get(Byte.valueOf(code));
        if (wasmType != null) {
            return wasmType;
        }
        StringBuilder sb = new StringBuilder("InvalidType 0x");
        String string = Integer.toString(code, CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        throw new IllegalStateException(sb.append(string).toString().toString());
    }

    private final int readVarUInt32AsInt(MyByteReader myByteReader) {
        return myByteReader.m7504readVarUInt32pVg5ArA();
    }

    public final String readString() {
        String string = Charsets.UTF_8.newDecoder().decode(ByteBuffer.wrap(this.b.readBytes(Integer.valueOf(readVarUInt32AsInt(this.b))))).toString();
        Intrinsics.checkNotNullExpressionValue(string, "let(...)");
        return string;
    }
}
