package org.jetbrains.kotlin.wasm.ir.convertors;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.wasm.ir.WasmAnyRef;
import org.jetbrains.kotlin.wasm.ir.WasmArrayDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmData;
import org.jetbrains.kotlin.wasm.ir.WasmDataMode;
import org.jetbrains.kotlin.wasm.ir.WasmElement;
import org.jetbrains.kotlin.wasm.ir.WasmExport;
import org.jetbrains.kotlin.wasm.ir.WasmExternRef;
import org.jetbrains.kotlin.wasm.ir.WasmFuncRef;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmFunctionType;
import org.jetbrains.kotlin.wasm.ir.WasmGlobal;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmImportDescriptor;
import org.jetbrains.kotlin.wasm.ir.WasmInstr;
import org.jetbrains.kotlin.wasm.ir.WasmLimits;
import org.jetbrains.kotlin.wasm.ir.WasmLocal;
import org.jetbrains.kotlin.wasm.ir.WasmMemory;
import org.jetbrains.kotlin.wasm.ir.WasmModule;
import org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField;
import org.jetbrains.kotlin.wasm.ir.WasmOp;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullType;
import org.jetbrains.kotlin.wasm.ir.WasmRefType;
import org.jetbrains.kotlin.wasm.ir.WasmStructDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmStructFieldDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmSymbolReadOnly;
import org.jetbrains.kotlin.wasm.ir.WasmTable;
import org.jetbrains.kotlin.wasm.ir.WasmTag;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmTypeDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmUnreachableType;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaDouble;

/* JADX INFO: compiled from: WasmIrToText.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u0014\u001a\u00020\u00042\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020 J\u000e\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u00042\u0006\u00100\u001a\u000201H\u0002J\u000e\u00102\u001a\u00020\u00042\u0006\u00100\u001a\u000201J\u0010\u00103\u001a\u00020\u00042\u0006\u00104\u001a\u000205H\u0002J\u000e\u00106\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u000207J\u0014\u00106\u001a\u00020\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020708J\u0018\u00109\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0004\b:\u0010\bJ\u000e\u0010;\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020<J\u000e\u0010=\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020>J\u0010\u0010?\u001a\u00020\u00042\u0006\u0010@\u001a\u00020AH\u0002J\u0010\u0010B\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010C\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020DH\u0002J\u0010\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020GH\u0002J\u0010\u0010H\u001a\u00020\u00042\u0006\u0010I\u001a\u00020JH\u0002J\u000e\u0010K\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020>J\u0010\u0010L\u001a\u00020\u00042\u0006\u0010M\u001a\u00020NH\u0002J\u000e\u0010O\u001a\u00020\u00042\u0006\u0010P\u001a\u00020QJ\u0010\u0010R\u001a\u00020S2\u0006\u0010%\u001a\u00020TH\u0002J\u0010\u0010U\u001a\u00020S2\u0006\u0010%\u001a\u00020VH\u0002J!\u0010W\u001a\u00020\u00042\b\u0010X\u001a\u0004\u0018\u00010Y2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00040[H\u0082\bJ\u0010\u0010\\\u001a\u00020\u00042\u0006\u0010]\u001a\u00020SH\u0002J\f\u0010^\u001a\u00020\u0004*\u00020_H\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006`"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmIrToText;", "Lorg/jetbrains/kotlin/wasm/ir/convertors/SExpressionBuilder;", "()V", "appendAlign", "", "value", "Lkotlin/UInt;", "appendAlign-WZ4Q5Ns", "(I)V", "appendArrayTypeDeclaration", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmArrayDeclaration;", "appendBlockType", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", "appendData", "wasmData", "Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "appendDefinedFunction", "function", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "appendExport", "export", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "appendFieldType", "field", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructFieldDeclaration;", "appendFunctionTypeDeclaration", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "appendGlobal", "global", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "appendHeapType", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "appendIdxIfNotZero", "id", "", "appendImmediate", "x", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "appendImportedFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "appendInstr", "wasmInstr", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "appendLimits", "limits", "Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "appendLocal", "local", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "appendLocalReference", "appendMemory", "memory", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "appendModuleFieldReference", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "appendOffset", "appendOffset-WZ4Q5Ns", "appendRefType", "Lorg/jetbrains/kotlin/wasm/ir/WasmRefType;", "appendReferencedType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "appendStartFunction", "startFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "appendStructField", "appendStructTypeDeclaration", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructDeclaration;", "appendTable", "table", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "appendTag", "wasmTag", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "appendType", "appendWasmElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "appendWasmModule", "module", "Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "f32Str", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF32;", "f64Str", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF64;", "maybeSubType", "superType", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "body", "Lkotlin/Function0;", "toWatString", "s", "appendImportPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmIrToText extends SExpressionBuilder {

    /* JADX INFO: compiled from: WasmIrToText.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WasmOp.values().length];
            try {
                iArr[WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WasmOp.PSEUDO_COMMENT_GROUP_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WasmOp.PSEUDO_COMMENT_GROUP_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: appendOffset-WZ4Q5Ns, reason: not valid java name */
    public final void m7537appendOffsetWZ4Q5Ns(int value) {
        if (value != 0) {
            appendElement("offset=" + ((Object) UInt.m3039toStringimpl(value)));
        }
    }

    /* JADX INFO: renamed from: appendAlign-WZ4Q5Ns, reason: not valid java name */
    public final void m7536appendAlignWZ4Q5Ns(int value) {
        long j = 1;
        for (int i = 0; i < value; i++) {
            j *= (long) 2;
        }
        if (j != 0) {
            appendElement("align=" + j);
        }
    }

    private final void appendInstr(WasmInstr wasmInstr) {
        WasmOp operator = wasmInstr.getOperator();
        if (operator.getOpcode() == 65535) {
            int i = WhenMappings.$EnumSwitchMapping$0[operator.ordinal()];
            if (i == 1) {
                String strAppendInstr$commentText = appendInstr$commentText(wasmInstr);
                if (SequencesKt.count(StringsKt.lineSequence(strAppendInstr$commentText)) >= 2) {
                    throw new IllegalArgumentException("Comments for single instruction should be in one line".toString());
                }
                getStringBuilder().append("  ;; ");
                getStringBuilder().append(strAppendInstr$commentText);
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    newLine();
                    return;
                }
                throw new IllegalStateException(("Unknown pseudo op " + operator).toString());
            }
            newLine();
            for (String str : StringsKt.lines(appendInstr$commentText(wasmInstr))) {
                newLine();
                getStringBuilder().append(";; ");
                getStringBuilder().append(str);
            }
            return;
        }
        if (operator == WasmOp.END || operator == WasmOp.ELSE || operator == WasmOp.CATCH) {
            setIndent(getIndent() - 1);
        }
        newLine();
        getStringBuilder().append(wasmInstr.getOperator().getMnemonic());
        if (operator == WasmOp.BLOCK || operator == WasmOp.LOOP || operator == WasmOp.IF || operator == WasmOp.ELSE || operator == WasmOp.CATCH || operator == WasmOp.TRY) {
            setIndent(getIndent() + 1);
        }
        if (SetsKt.setOf((Object[]) new WasmOp[]{WasmOp.CALL_INDIRECT, WasmOp.TABLE_INIT}).contains(wasmInstr.getOperator())) {
            Iterator it = CollectionsKt.reversed(wasmInstr.getImmediates()).iterator();
            while (it.hasNext()) {
                appendImmediate((WasmImmediate) it.next());
            }
        } else {
            Iterator<T> it2 = wasmInstr.getImmediates().iterator();
            while (it2.hasNext()) {
                appendImmediate((WasmImmediate) it2.next());
            }
        }
    }

    private static final String appendInstr$commentText(WasmInstr wasmInstr) {
        Object objSingle = CollectionsKt.single((List<? extends Object>) wasmInstr.getImmediates());
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.wasm.ir.WasmImmediate.ConstString");
        return ((WasmImmediate.ConstString) objSingle).getValue();
    }

    private final void appendImmediate(WasmImmediate x) {
        if (x instanceof WasmImmediate.ConstU8) {
            String lowerCase = UByte.m2960toStringimpl(((WasmImmediate.ConstU8) x).getValue()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            appendElement(lowerCase);
            return;
        }
        if (x instanceof WasmImmediate.ConstI32) {
            String lowerCase2 = String.valueOf(((WasmImmediate.ConstI32) x).getValue()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            appendElement(lowerCase2);
            return;
        }
        if (x instanceof WasmImmediate.ConstI64) {
            String lowerCase3 = String.valueOf(((WasmImmediate.ConstI64) x).getValue()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
            appendElement(lowerCase3);
            return;
        }
        if (x instanceof WasmImmediate.ConstF32) {
            String lowerCase4 = f32Str((WasmImmediate.ConstF32) x).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
            appendElement(lowerCase4);
            return;
        }
        if (x instanceof WasmImmediate.ConstF64) {
            String lowerCase5 = f64Str((WasmImmediate.ConstF64) x).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase5, "toLowerCase(...)");
            appendElement(lowerCase5);
            return;
        }
        if (x instanceof WasmImmediate.SymbolI32) {
            appendElement(String.valueOf(((WasmImmediate.SymbolI32) x).getValue().getOwner().intValue()));
            return;
        }
        if (x instanceof WasmImmediate.MemArg) {
            WasmImmediate.MemArg memArg = (WasmImmediate.MemArg) x;
            m7537appendOffsetWZ4Q5Ns(memArg.getOffset());
            m7536appendAlignWZ4Q5Ns(memArg.getAlign());
            return;
        }
        if (x instanceof WasmImmediate.BlockType) {
            appendBlockType((WasmImmediate.BlockType) x);
            return;
        }
        if (x instanceof WasmImmediate.FuncIdx) {
            appendModuleFieldReference(((WasmImmediate.FuncIdx) x).getValue().getOwner());
            return;
        }
        if (x instanceof WasmImmediate.LocalIdx) {
            appendLocalReference(((WasmImmediate.LocalIdx) x).getValue().getOwner());
            return;
        }
        if (x instanceof WasmImmediate.GlobalIdx) {
            appendModuleFieldReference(((WasmImmediate.GlobalIdx) x).getValue().getOwner());
            return;
        }
        if (x instanceof WasmImmediate.TypeIdx) {
            WasmIrToText wasmIrToText = this;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (type");
            appendModuleFieldReference(((WasmImmediate.TypeIdx) x).getValue().getOwner());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            return;
        }
        if (x instanceof WasmImmediate.MemoryIdx) {
            appendIdxIfNotZero(((WasmImmediate.MemoryIdx) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.DataIdx) {
            appendElement(((WasmImmediate.DataIdx) x).getValue().toString());
            return;
        }
        if (x instanceof WasmImmediate.TableIdx) {
            appendElement(((WasmImmediate.TableIdx) x).getValue().toString());
            return;
        }
        if (x instanceof WasmImmediate.LabelIdx) {
            appendElement(String.valueOf(((WasmImmediate.LabelIdx) x).getValue()));
            return;
        }
        if (x instanceof WasmImmediate.TagIdx) {
            appendElement(String.valueOf(((WasmImmediate.TagIdx) x).getValue()));
            return;
        }
        if (!(x instanceof WasmImmediate.LabelIdxVector)) {
            if (x instanceof WasmImmediate.ElemIdx) {
                Integer id2 = ((WasmImmediate.ElemIdx) x).getValue().getId();
                Intrinsics.checkNotNull(id2);
                appendElement(String.valueOf(id2.intValue()));
                return;
            }
            if (x instanceof WasmImmediate.ValTypeVector) {
                WasmIrToText wasmIrToText2 = this;
                ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(" (result");
                Iterator<T> it = ((WasmImmediate.ValTypeVector) x).getValue().iterator();
                while (it.hasNext()) {
                    appendType((WasmType) it.next());
                }
                ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(Constant.AFTER_QUTO);
                return;
            }
            if (x instanceof WasmImmediate.GcType) {
                appendModuleFieldReference(((WasmImmediate.GcType) x).getValue().getOwner());
                return;
            }
            if (x instanceof WasmImmediate.StructFieldIdx) {
                appendElement(String.valueOf(((WasmImmediate.StructFieldIdx) x).getValue().getOwner().intValue()));
                return;
            } else if (x instanceof WasmImmediate.HeapType) {
                appendHeapType(((WasmImmediate.HeapType) x).getValue());
                return;
            } else {
                if (x instanceof WasmImmediate.ConstString) {
                    throw new IllegalStateException("Pseudo immediate".toString());
                }
                return;
            }
        }
        Iterator<T> it2 = ((WasmImmediate.LabelIdxVector) x).getValue().iterator();
        while (it2.hasNext()) {
            appendElement(String.valueOf(((Number) it2.next()).intValue()));
        }
    }

    private final String f32Str(WasmImmediate.ConstF32 x) {
        String str;
        int rawBits = x.getRawBits();
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        float fIntBitsToFloat = Float.intBitsToFloat(rawBits);
        if (!Float.isNaN(fIntBitsToFloat)) {
            if (fIntBitsToFloat == Float.POSITIVE_INFINITY) {
                return LuaDouble.JSTR_POSINF;
            }
            if (fIntBitsToFloat == Float.NEGATIVE_INFINITY) {
                return LuaDouble.JSTR_NEGINF;
            }
            return String.valueOf(fIntBitsToFloat);
        }
        if ((Integer.MIN_VALUE & rawBits) == 0) {
            str = "";
        } else {
            str = Parameters.DEFAULT_OPTION_PREFIXES;
        }
        if (rawBits != 2143289344) {
            int i = rawBits & Lua.MASK_NOT_B;
            StringBuilder sbAppend = new StringBuilder().append(str).append("nan:0x");
            String string = Integer.toString(i, CharsKt.checkRadix(16));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return sbAppend.append(string).toString();
        }
        return str.concat(LuaDouble.JSTR_NAN);
    }

    private final String f64Str(WasmImmediate.ConstF64 x) {
        String str;
        long rawBits = x.getRawBits();
        DoubleCompanionObject doubleCompanionObject = DoubleCompanionObject.INSTANCE;
        double dLongBitsToDouble = Double.longBitsToDouble(rawBits);
        if (!Double.isNaN(dLongBitsToDouble)) {
            if (dLongBitsToDouble == Double.POSITIVE_INFINITY) {
                return LuaDouble.JSTR_POSINF;
            }
            if (dLongBitsToDouble == Double.NEGATIVE_INFINITY) {
                return LuaDouble.JSTR_NEGINF;
            }
            return String.valueOf(dLongBitsToDouble);
        }
        if ((Long.MIN_VALUE & rawBits) == 0) {
            str = "";
        } else {
            str = Parameters.DEFAULT_OPTION_PREFIXES;
        }
        if (rawBits != 9221120237041090560L) {
            StringBuilder sbAppend = new StringBuilder().append(str).append("nan:0x");
            String string = Long.toString(rawBits & 4503599627370495L, CharsKt.checkRadix(16));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return sbAppend.append(string).toString();
        }
        return str.concat(LuaDouble.JSTR_NAN);
    }

    public final void appendBlockType(WasmImmediate.BlockType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof WasmImmediate.BlockType.Value) {
            WasmImmediate.BlockType.Value value = (WasmImmediate.BlockType.Value) type;
            if (value.getType() == null || (value.getType() instanceof WasmUnreachableType)) {
                return;
            }
            WasmIrToText wasmIrToText = this;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (result");
            appendType(value.getType());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            return;
        }
        if (type instanceof WasmImmediate.BlockType.Function) {
            WasmImmediate.BlockType.Function function = (WasmImmediate.BlockType.Function) type;
            List<WasmType> parameterTypes = function.getType().getParameterTypes();
            List<WasmType> resultTypes = function.getType().getResultTypes();
            if (!parameterTypes.isEmpty()) {
                WasmIrToText wasmIrToText2 = this;
                ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(" (param");
                Iterator<T> it = parameterTypes.iterator();
                while (it.hasNext()) {
                    appendType((WasmType) it.next());
                }
                ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(Constant.AFTER_QUTO);
            }
            if (resultTypes.isEmpty()) {
                return;
            }
            WasmIrToText wasmIrToText3 = this;
            ((SExpressionBuilder) wasmIrToText3).stringBuilder.append(" (result");
            Iterator<T> it2 = resultTypes.iterator();
            while (it2.hasNext()) {
                appendType((WasmType) it2.next());
            }
            ((SExpressionBuilder) wasmIrToText3).stringBuilder.append(Constant.AFTER_QUTO);
        }
    }

    public final void appendRefType(WasmRefType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        WasmHeapType heapType = type.getHeapType();
        if (heapType instanceof WasmHeapType.Simple) {
            appendElement(((WasmHeapType.Simple) type.getHeapType()).getName() + "ref");
        } else if (heapType instanceof WasmHeapType.Type) {
            WasmIrToText wasmIrToText = this;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (ref");
            appendHeapType(type.getHeapType());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        }
    }

    public final void appendWasmModule(WasmModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(module");
        ((SExpressionBuilder) wasmIrToText).indent++;
        Iterator<T> it = module.getFunctionTypes().iterator();
        while (it.hasNext()) {
            appendFunctionTypeDeclaration((WasmFunctionType) it.next());
        }
        if (!module.getRecGroupTypes().isEmpty()) {
            wasmIrToText.newLine();
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(rec");
            ((SExpressionBuilder) wasmIrToText).indent++;
            for (WasmTypeDeclaration wasmTypeDeclaration : module.getRecGroupTypes()) {
                if (wasmTypeDeclaration instanceof WasmStructDeclaration) {
                    appendStructTypeDeclaration((WasmStructDeclaration) wasmTypeDeclaration);
                } else if (wasmTypeDeclaration instanceof WasmArrayDeclaration) {
                    appendArrayTypeDeclaration((WasmArrayDeclaration) wasmTypeDeclaration);
                } else if (wasmTypeDeclaration instanceof WasmFunctionType) {
                    appendFunctionTypeDeclaration((WasmFunctionType) wasmTypeDeclaration);
                }
            }
            ((SExpressionBuilder) wasmIrToText).indent--;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        }
        for (WasmNamedModuleField wasmNamedModuleField : module.getImportsInOrder()) {
            if (wasmNamedModuleField instanceof WasmFunction.Imported) {
                appendImportedFunction((WasmFunction.Imported) wasmNamedModuleField);
            } else if (wasmNamedModuleField instanceof WasmMemory) {
                appendMemory((WasmMemory) wasmNamedModuleField);
            } else if (wasmNamedModuleField instanceof WasmTable) {
                appendTable((WasmTable) wasmNamedModuleField);
            } else if (wasmNamedModuleField instanceof WasmGlobal) {
                appendGlobal((WasmGlobal) wasmNamedModuleField);
            } else {
                if (!(wasmNamedModuleField instanceof WasmTag)) {
                    throw new IllegalStateException(("Unknown import kind " + Reflection.getOrCreateKotlinClass(wasmNamedModuleField.getClass())).toString());
                }
                appendTag((WasmTag) wasmNamedModuleField);
            }
        }
        Iterator<T> it2 = module.getDefinedFunctions().iterator();
        while (it2.hasNext()) {
            appendDefinedFunction((WasmFunction.Defined) it2.next());
        }
        Iterator<T> it3 = module.getTables().iterator();
        while (it3.hasNext()) {
            appendTable((WasmTable) it3.next());
        }
        Iterator<T> it4 = module.getMemories().iterator();
        while (it4.hasNext()) {
            appendMemory((WasmMemory) it4.next());
        }
        Iterator<T> it5 = module.getGlobals().iterator();
        while (it5.hasNext()) {
            appendGlobal((WasmGlobal) it5.next());
        }
        Iterator<T> it6 = module.getExports().iterator();
        while (it6.hasNext()) {
            appendExport((WasmExport) it6.next());
        }
        Iterator<T> it7 = module.getElements().iterator();
        while (it7.hasNext()) {
            appendWasmElement((WasmElement) it7.next());
        }
        WasmFunction startFunction = module.getStartFunction();
        if (startFunction != null) {
            appendStartFunction(startFunction);
        }
        Iterator<T> it8 = module.getData().iterator();
        while (it8.hasNext()) {
            appendData((WasmData) it8.next());
        }
        Iterator<T> it9 = module.getTags().iterator();
        while (it9.hasNext()) {
            appendTag((WasmTag) it9.next());
        }
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendFunctionTypeDeclaration(WasmFunctionType type) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(type");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(type);
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (func");
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (param");
        Iterator<T> it = type.getParameterTypes().iterator();
        while (it.hasNext()) {
            appendType((WasmType) it.next());
        }
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        if (!type.getResultTypes().isEmpty()) {
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (result");
            Iterator<T> it2 = type.getResultTypes().iterator();
            while (it2.hasNext()) {
                appendType((WasmType) it2.next());
            }
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        }
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendStructTypeDeclaration(WasmStructDeclaration type) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(type");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(type);
        WasmSymbolReadOnly<WasmTypeDeclaration> superType = type.getSuperType();
        WasmTypeDeclaration owner = superType != null ? superType.getOwner() : null;
        if (owner != null) {
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (sub");
            appendModuleFieldReference(owner);
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (struct");
            Iterator<T> it = type.getFields().iterator();
            while (it.hasNext()) {
                appendStructField((WasmStructFieldDeclaration) it.next());
            }
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        } else {
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (struct");
            Iterator<T> it2 = type.getFields().iterator();
            while (it2.hasNext()) {
                appendStructField((WasmStructFieldDeclaration) it2.next());
            }
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        }
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendArrayTypeDeclaration(WasmArrayDeclaration type) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(type");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(type);
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (array");
        appendFieldType(type.getField());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendImportedFunction(WasmFunction.Imported function) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(func");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(function);
        appendImportPair(function.getImportPair());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (type");
        appendModuleFieldReference(function.getType());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendImportPair(WasmImportDescriptor wasmImportDescriptor) {
        WasmIrToText wasmIrToText = this;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (import");
        toWatString(wasmImportDescriptor.getModuleName());
        toWatString(wasmImportDescriptor.getDeclarationName());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendDefinedFunction(WasmFunction.Defined function) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(func");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(function);
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (type");
        appendModuleFieldReference(function.getType());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        for (WasmLocal wasmLocal : function.getLocals()) {
            if (wasmLocal.getIsParameter()) {
                appendLocal(wasmLocal);
            }
        }
        if (!function.getType().getOwner().getResultTypes().isEmpty()) {
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (result");
            Iterator<T> it = function.getType().getOwner().getResultTypes().iterator();
            while (it.hasNext()) {
                appendType((WasmType) it.next());
            }
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        }
        for (WasmLocal wasmLocal2 : function.getLocals()) {
            if (!wasmLocal2.getIsParameter()) {
                appendLocal(wasmLocal2);
            }
        }
        Iterator<T> it2 = function.getInstructions().iterator();
        while (it2.hasNext()) {
            appendInstr((WasmInstr) it2.next());
        }
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendTable(WasmTable table) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(table");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(table);
        WasmImportDescriptor importPair = table.getImportPair();
        if (importPair != null) {
            appendImportPair(importPair);
        }
        appendLimits(table.getLimits());
        appendType(table.getElementType());
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendMemory(WasmMemory memory) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(memory");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(memory);
        WasmImportDescriptor importPair = memory.getImportPair();
        if (importPair != null) {
            appendImportPair(importPair);
        }
        appendLimits(memory.getLimits());
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendLimits(WasmLimits limits) {
        appendElement(Integer.toUnsignedString(limits.m7498getMinSizepVg5ArA()));
        UInt uIntM7497getMaxSize0hXNFcg = limits.m7497getMaxSize0hXNFcg();
        if (uIntM7497getMaxSize0hXNFcg != null) {
            appendElement(Integer.toUnsignedString(uIntM7497getMaxSize0hXNFcg.getData()));
        }
    }

    private final void appendGlobal(WasmGlobal global) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(global");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(global);
        WasmImportDescriptor importPair = global.getImportPair();
        if (importPair != null) {
            appendImportPair(importPair);
        }
        if (global.getIsMutable()) {
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (mut");
            appendType(global.getType());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        } else {
            appendType(global.getType());
        }
        Iterator<T> it = global.getInit().iterator();
        while (it.hasNext()) {
            appendInstr((WasmInstr) it.next());
        }
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendExport(WasmExport<?> export) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(export");
        ((SExpressionBuilder) wasmIrToText).indent++;
        toWatString(export.getName());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (" + export.getKeyword());
        appendModuleFieldReference(export.getField());
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0107  */
    /* JADX WARN: Code duplicated, block: B:39:0x0111 A[LOOP:0: B:35:0x0101->B:39:0x0111, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:45:0x0121 A[SYNTHETIC] */
    private final void appendWasmElement(WasmElement element) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(elem");
        ((SExpressionBuilder) wasmIrToText).indent++;
        WasmElement.Mode mode = element.getMode();
        if (!Intrinsics.areEqual(mode, WasmElement.Mode.Passive.INSTANCE)) {
            if (mode instanceof WasmElement.Mode.Active) {
                WasmElement.Mode.Active active = (WasmElement.Mode.Active) mode;
                Integer id2 = active.getTable().getId();
                if (id2 == null || id2.intValue() != 0) {
                    ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (table");
                    appendModuleFieldReference(active.getTable());
                    ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
                }
                ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (");
                appendInstr((WasmInstr) CollectionsKt.single((List) active.getOffset()));
                ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            } else if (Intrinsics.areEqual(mode, WasmElement.Mode.Declarative.INSTANCE)) {
                appendElement("declare");
            }
        }
        List<WasmTable.Value> values = element.getValues();
        if ((values instanceof Collection) && values.isEmpty()) {
            appendElement("func");
            for (WasmTable.Value value : element.getValues()) {
                if (value instanceof WasmTable.Value.Function) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                appendModuleFieldReference(((WasmTable.Value.Function) value).getFunction().getOwner());
            }
        } else {
            Iterator<T> it = values.iterator();
            while (it.hasNext()) {
                if (!(((WasmTable.Value) it.next()) instanceof WasmTable.Value.Function)) {
                    appendType(element.getType());
                    for (WasmTable.Value value2 : element.getValues()) {
                        if (value2 instanceof WasmTable.Value.Expression) {
                            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (item");
                            appendInstr((WasmInstr) CollectionsKt.single((List) ((WasmTable.Value.Expression) value2).getExpr()));
                            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
                        } else {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                    }
                }
            }
            appendElement("func");
            while (r7.hasNext()) {
                if (value instanceof WasmTable.Value.Function) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                appendModuleFieldReference(((WasmTable.Value.Function) value).getFunction().getOwner());
            }
        }
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendStartFunction(WasmFunction startFunction) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(start");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(startFunction);
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendData(WasmData wasmData) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(data");
        ((SExpressionBuilder) wasmIrToText).indent++;
        WasmDataMode mode = wasmData.getMode();
        if (mode instanceof WasmDataMode.Active) {
            WasmDataMode.Active active = (WasmDataMode.Active) mode;
            if (active.getMemoryIdx() != 0) {
                ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (memory");
                appendElement(String.valueOf(active.getMemoryIdx()));
                ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            }
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (");
            appendInstr((WasmInstr) CollectionsKt.single((List) active.getOffset()));
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        } else {
            Intrinsics.areEqual(mode, WasmDataMode.Passive.INSTANCE);
        }
        appendElement(WasmIrToTextKt.toWatData(wasmData.getBytes()));
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendTag(WasmTag wasmTag) {
        WasmIrToText wasmIrToText = this;
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(tag");
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendModuleFieldReference(wasmTag);
        WasmImportDescriptor importPair = wasmTag.getImportPair();
        if (importPair != null) {
            appendImportPair(importPair);
        }
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (param");
        Iterator<T> it = wasmTag.getType().getParameterTypes().iterator();
        while (it.hasNext()) {
            appendType((WasmType) it.next());
        }
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
        wasmTag.getType().getResultTypes().isEmpty();
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendLocal(WasmLocal local) {
        WasmIrToText wasmIrToText = this;
        String str = local.getIsParameter() ? "param" : "local";
        wasmIrToText.newLine();
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append("(".concat(str));
        ((SExpressionBuilder) wasmIrToText).indent++;
        appendLocalReference(local);
        appendType(local.getType());
        ((SExpressionBuilder) wasmIrToText).indent--;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    public final void appendHeapType(WasmHeapType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof WasmHeapType.Simple) {
            appendElement(((WasmHeapType.Simple) type).getName());
        } else if (type instanceof WasmHeapType.Type) {
            appendModuleFieldReference(((WasmHeapType.Type) type).getType().getOwner());
        }
    }

    public final void appendReferencedType(WasmType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof WasmFuncRef) {
            appendElement("func");
        } else if (type instanceof WasmAnyRef) {
            appendElement("any");
        } else {
            if (!(type instanceof WasmExternRef)) {
                throw new NotImplementedError(null, 1, null);
            }
            appendElement("extern");
        }
    }

    public final void appendType(WasmType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof WasmRefType) {
            WasmIrToText wasmIrToText = this;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (ref");
            appendHeapType(((WasmRefType) type).getHeapType());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            return;
        }
        if (type instanceof WasmRefNullType) {
            WasmIrToText wasmIrToText2 = this;
            ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(" (ref null");
            appendHeapType(((WasmRefNullType) type).getHeapType());
            ((SExpressionBuilder) wasmIrToText2).stringBuilder.append(Constant.AFTER_QUTO);
            return;
        }
        if (Intrinsics.areEqual(type, WasmUnreachableType.INSTANCE)) {
            return;
        }
        appendElement(type.getName());
    }

    private final void appendStructField(WasmStructFieldDeclaration field) {
        WasmIrToText wasmIrToText = this;
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (field");
        appendFieldType(field);
        ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
    }

    private final void appendFieldType(WasmStructFieldDeclaration field) {
        if (field.getIsMutable()) {
            WasmIrToText wasmIrToText = this;
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(" (mut");
            appendType(field.getType());
            ((SExpressionBuilder) wasmIrToText).stringBuilder.append(Constant.AFTER_QUTO);
            return;
        }
        appendType(field.getType());
    }

    public final void appendLocalReference(WasmLocal local) {
        Intrinsics.checkNotNullParameter(local, "local");
        appendElement("$" + local.getId() + '_' + WasmIrToTextKt.sanitizeWatIdentifier(local.getName()));
    }

    public final void appendIdxIfNotZero(int id2) {
        if (id2 != 0) {
            appendElement(String.valueOf(id2));
        }
    }

    public final void appendModuleFieldReference(WasmSymbolReadOnly<? extends WasmNamedModuleField> field) {
        Intrinsics.checkNotNullParameter(field, "field");
        appendModuleFieldReference(field.getOwner());
    }

    public final void appendModuleFieldReference(WasmNamedModuleField field) {
        String str;
        Intrinsics.checkNotNullParameter(field, "field");
        Integer id2 = field.getId();
        if (id2 == null) {
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(field.getClass()) + StringUtil.SPACE + field.getName() + " ID is unlinked").toString());
        }
        int iIntValue = id2.intValue();
        if (field instanceof WasmData) {
            str = "data";
        } else if (field instanceof WasmFunction) {
            str = "fun";
        } else if (field instanceof WasmMemory) {
            str = "mem";
        } else if (field instanceof WasmTable) {
            str = "table";
        } else if (field instanceof WasmElement) {
            str = "elem";
        } else if (field instanceof WasmGlobal) {
            str = "g";
        } else if (field instanceof WasmTypeDeclaration) {
            str = ModuleXmlParser.TYPE;
        } else {
            if (!(field instanceof WasmTag)) {
                throw new NoWhenBranchMatchedException();
            }
            str = "tag";
        }
        appendElement("$" + WasmIrToTextKt.sanitizeWatIdentifier(field.getName()) + "___" + str + '_' + iIntValue);
    }

    private final void toWatString(String s) {
        String str = s;
        for (int i = 0; i < str.length(); i++) {
            if (!WasmIrToTextKt.isValidWatIdentifierChar(str.charAt(i))) {
                StringBuilder stringBuilder = getStringBuilder();
                byte[] bytes = s.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                stringBuilder.append(WasmIrToTextKt.toWatData(bytes));
                return;
            }
        }
        getStringBuilder().append(" \"");
        getStringBuilder().append(s);
        getStringBuilder().append('\"');
    }
}
