package org.jetbrains.kotlin.wasm.ir.convertors;

import com.android.dx.rop.code.RegisterSpec;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.UInt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.wasm.ir.WasmArrayDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmData;
import org.jetbrains.kotlin.wasm.ir.WasmDataMode;
import org.jetbrains.kotlin.wasm.ir.WasmElement;
import org.jetbrains.kotlin.wasm.ir.WasmExport;
import org.jetbrains.kotlin.wasm.ir.WasmFuncRef;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmFunctionType;
import org.jetbrains.kotlin.wasm.ir.WasmGlobal;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmInstr;
import org.jetbrains.kotlin.wasm.ir.WasmInstrWithLocation;
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
import org.jetbrains.kotlin.wasm.ir.source.location.Box;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocationMapping;

/* JADX INFO: compiled from: WasmIrToBinary.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020 2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010,\u001a\u00020 2\u0006\u0010-\u001a\u00020.H\u0002J\u0014\u0010/\u001a\u00020 2\n\u00100\u001a\u0006\u0012\u0002\b\u000301H\u0002J\u0016\u00102\u001a\u00020 2\f\u00103\u001a\b\u0012\u0004\u0012\u00020504H\u0002J\u0010\u00106\u001a\u00020 2\u0006\u00107\u001a\u000208H\u0002J\u0010\u00109\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001cH\u0002J\u0010\u0010:\u001a\u00020 2\u0006\u0010;\u001a\u00020<H\u0002J\u000e\u0010=\u001a\u00020 2\u0006\u0010!\u001a\u00020>J\u0010\u0010?\u001a\u00020 2\u0006\u0010@\u001a\u00020AH\u0002J\u0010\u0010B\u001a\u00020 2\u0006\u0010&\u001a\u00020CH\u0002J\u0010\u0010D\u001a\u00020 2\u0006\u0010E\u001a\u000205H\u0002J\u0010\u0010F\u001a\u00020 2\u0006\u0010G\u001a\u00020HH\u0002J\u000e\u0010I\u001a\u00020 2\u0006\u0010J\u001a\u00020KJ\u0010\u0010L\u001a\u00020 2\u0006\u0010M\u001a\u00020NH\u0002J\u000e\u0010O\u001a\u00020 2\u0006\u00107\u001a\u00020PJ(\u0010Q\u001a\u00020 2\u0006\u0010R\u001a\u00020S2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020 0UH\u0002ø\u0001\u0000¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020 2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0010\u0010[\u001a\u00020 2\u0006\u0010!\u001a\u00020\\H\u0002J\u0010\u0010]\u001a\u00020 2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020 2\u0006\u0010a\u001a\u00020bH\u0002J\u0016\u0010c\u001a\u00020 2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020'0eH\u0002J\u000e\u0010f\u001a\u00020 2\u0006\u0010!\u001a\u00020gJ\u0010\u0010h\u001a\u00020 2\u0006\u0010i\u001a\u00020\u001bH\u0002J\u0006\u0010j\u001a\u00020 J\u0016\u0010k\u001a\u00020 2\f\u0010l\u001a\b\u0012\u0004\u0012\u00020 0UH\u0002J\u0014\u0010m\u001a\u00020 *\u00020\u00102\u0006\u0010n\u001a\u00020\u0007H\u0002J\u0012\u0010o\u001a\u00020 *\u00020\u00102\u0006\u0010p\u001a\u00020\u001bR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u001a\u001a\u00020\u001b*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006q"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmIrToBinary;", "", "outputStream", "Ljava/io/OutputStream;", "module", "Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "moduleName", "", "emitNameSection", "", "sourceMapFileName", "sourceLocationMappings", "", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocationMapping;", "(Ljava/io/OutputStream;Lorg/jetbrains/kotlin/wasm/ir/WasmModule;Ljava/lang/String;ZLjava/lang/String;Ljava/util/List;)V", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteWriter;", "getEmitNameSection", "()Z", "getModule", "()Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "getModuleName", "()Ljava/lang/String;", "offsets", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/wasm/ir/source/location/Box;", "index", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "getIndex", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;)I", "appendArrayTypeDeclaration", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmArrayDeclaration;", "appendBlockType", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", "appendCode", "function", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "appendData", "wasmData", "Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "appendDefinedFunction", "appendElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "appendExport", "export", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "appendExpr", "expr", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "appendFiledType", "field", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructFieldDeclaration;", "appendFunctionTypeDeclaration", "appendGlobal", "c", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "appendHeapType", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "appendImmediate", "x", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "appendImportedFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "appendInstr", "instr", "appendLimits", "limits", "Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "appendLocalReference", "local", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "appendMemory", "memory", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "appendModuleFieldReference", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "appendSection", "section", "Lorg/jetbrains/kotlin/wasm/ir/convertors/WasmBinary$Section;", FirebaseAnalytics.Param.CONTENT, "Lkotlin/Function0;", "appendSection-Sr9dMcE", "(SLkotlin/jvm/functions/Function0;)V", "appendStartFunction", "startFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "appendStructTypeDeclaration", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructDeclaration;", "appendTable", "table", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "appendTag", "t", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "appendTextSection", "definedFunctions", "", "appendType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "appendVectorSize", "size", "appendWasmModule", "withVarUInt32PayloadSizePrepended", "fn", "writeString", "str", "writeVarUInt32", RegisterSpec.PREFIX, "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmIrToBinary {
    private ByteWriter b;
    private final boolean emitNameSection;
    private final WasmModule module;
    private final String moduleName;
    private PersistentList<Box> offsets;
    private final List<SourceLocationMapping> sourceLocationMappings;
    private final String sourceMapFileName;

    public WasmIrToBinary(OutputStream outputStream, WasmModule module, String moduleName, boolean z, String str, List<SourceLocationMapping> list) {
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        this.module = module;
        this.moduleName = moduleName;
        this.emitNameSection = z;
        this.sourceMapFileName = str;
        this.sourceLocationMappings = list;
        this.b = new ByteWriter.OutputStream(outputStream);
        this.offsets = ExtensionsKt.persistentListOf();
    }

    public /* synthetic */ WasmIrToBinary(OutputStream outputStream, WasmModule wasmModule, String str, boolean z, String str2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(outputStream, wasmModule, str, z, (i & 16) != 0 ? null : str2, (i & 32) != 0 ? null : list);
    }

    public final WasmModule getModule() {
        return this.module;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final boolean getEmitNameSection() {
        return this.emitNameSection;
    }

    public final void appendWasmModule() {
        this.b.m7507writeUInt32WZ4Q5Ns(WasmBinary.MAGIC);
        this.b.m7507writeUInt32WZ4Q5Ns(1);
        final WasmModule wasmModule = this.module;
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7532getTYPEEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.appendVectorSize(wasmModule.getFunctionTypes().size() + (!wasmModule.getRecGroupTypes().isEmpty() ? 1 : 0));
                List<WasmFunctionType> functionTypes = wasmModule.getFunctionTypes();
                WasmIrToBinary wasmIrToBinary = this;
                Iterator<T> it = functionTypes.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendFunctionTypeDeclaration((WasmFunctionType) it.next());
                }
                if (wasmModule.getRecGroupTypes().isEmpty()) {
                    return;
                }
                this.b.writeVarInt7(WasmBinary.REC_GROUP);
                this.appendVectorSize(wasmModule.getRecGroupTypes().size());
                List<WasmTypeDeclaration> recGroupTypes = wasmModule.getRecGroupTypes();
                WasmIrToBinary wasmIrToBinary2 = this;
                for (WasmTypeDeclaration wasmTypeDeclaration : recGroupTypes) {
                    if (wasmTypeDeclaration instanceof WasmStructDeclaration) {
                        wasmIrToBinary2.appendStructTypeDeclaration((WasmStructDeclaration) wasmTypeDeclaration);
                    } else if (wasmTypeDeclaration instanceof WasmArrayDeclaration) {
                        wasmIrToBinary2.appendArrayTypeDeclaration((WasmArrayDeclaration) wasmTypeDeclaration);
                    } else if (wasmTypeDeclaration instanceof WasmFunctionType) {
                        wasmIrToBinary2.appendFunctionTypeDeclaration((WasmFunctionType) wasmTypeDeclaration);
                    }
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7527getIMPORTEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getImportsInOrder().size());
                List<WasmNamedModuleField> importsInOrder = wasmModule.getImportsInOrder();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                for (WasmNamedModuleField wasmNamedModuleField : importsInOrder) {
                    if (wasmNamedModuleField instanceof WasmFunction.Imported) {
                        wasmIrToBinary.appendImportedFunction((WasmFunction.Imported) wasmNamedModuleField);
                    } else if (wasmNamedModuleField instanceof WasmMemory) {
                        wasmIrToBinary.appendMemory((WasmMemory) wasmNamedModuleField);
                    } else if (wasmNamedModuleField instanceof WasmTable) {
                        wasmIrToBinary.appendTable((WasmTable) wasmNamedModuleField);
                    } else if (wasmNamedModuleField instanceof WasmGlobal) {
                        wasmIrToBinary.appendGlobal((WasmGlobal) wasmNamedModuleField);
                    } else if (wasmNamedModuleField instanceof WasmTag) {
                        wasmIrToBinary.appendTag((WasmTag) wasmNamedModuleField);
                    } else {
                        throw new IllegalStateException(("Unknown import kind " + Reflection.getOrCreateKotlinClass(wasmNamedModuleField.getClass())).toString());
                    }
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7525getFUNCTIONEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getDefinedFunctions().size());
                List<WasmFunction.Defined> definedFunctions = wasmModule.getDefinedFunctions();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = definedFunctions.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendDefinedFunction((WasmFunction.Defined) it.next());
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7530getTABLEEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getTables().size());
                List<WasmTable> tables = wasmModule.getTables();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = tables.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendTable((WasmTable) it.next());
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7528getMEMORYEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getMemories().size());
                List<WasmMemory> memories = wasmModule.getMemories();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = memories.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendMemory((WasmMemory) it.next());
                }
            }
        });
        if (!wasmModule.getTags().isEmpty()) {
            m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7531getTAGEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.appendVectorSize(wasmModule.getTags().size());
                    List<WasmTag> tags = wasmModule.getTags();
                    WasmIrToBinary wasmIrToBinary = this.this$0;
                    Iterator<T> it = tags.iterator();
                    while (it.hasNext()) {
                        wasmIrToBinary.appendTag((WasmTag) it.next());
                    }
                }
            });
        }
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7526getGLOBALEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getGlobals().size());
                List<WasmGlobal> globals = wasmModule.getGlobals();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = globals.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendGlobal((WasmGlobal) it.next());
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7524getEXPORTEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getExports().size());
                List<WasmExport<?>> exports = wasmModule.getExports();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = exports.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendExport((WasmExport) it.next());
                }
            }
        });
        if (wasmModule.getStartFunction() != null) {
            m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7529getSTARTEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$9
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.appendStartFunction(wasmModule.getStartFunction());
                }
            });
        }
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7523getELEMENTEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$10
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getElements().size());
                List<WasmElement> elements = wasmModule.getElements();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = elements.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendElement((WasmElement) it.next());
                }
            }
        });
        if (wasmModule.getDataCount()) {
            m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7522getDATA_COUNTEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$11
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.b.m7509writeVarUInt32WZ4Q5Ns(UInt.m2993constructorimpl(wasmModule.getData().size()));
                }
            });
        }
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7519getCODEEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$12
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getDefinedFunctions().size());
                List<WasmFunction.Defined> definedFunctions = wasmModule.getDefinedFunctions();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = definedFunctions.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendCode((WasmFunction.Defined) it.next());
                }
            }
        });
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7521getDATAEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$13
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.appendVectorSize(wasmModule.getData().size());
                List<WasmData> data = wasmModule.getData();
                WasmIrToBinary wasmIrToBinary = this.this$0;
                Iterator<T> it = data.iterator();
                while (it.hasNext()) {
                    wasmIrToBinary.appendData((WasmData) it.next());
                }
            }
        });
        if (this.emitNameSection) {
            appendTextSection(wasmModule.getDefinedFunctions());
        }
        if (this.sourceMapFileName != null) {
            m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7520getCUSTOMEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendWasmModule$1$14
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    WasmIrToBinary wasmIrToBinary = this.this$0;
                    wasmIrToBinary.writeString(wasmIrToBinary.b, "sourceMappingURL");
                    WasmIrToBinary wasmIrToBinary2 = this.this$0;
                    wasmIrToBinary2.writeString(wasmIrToBinary2.b, this.this$0.sourceMapFileName);
                }
            });
        }
    }

    private final void appendTextSection(final List<WasmFunction.Defined> definedFunctions) {
        m7535appendSectionSr9dMcE(WasmBinary.Section.INSTANCE.m7520getCUSTOMEsw0DI4(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                WasmIrToBinary wasmIrToBinary = WasmIrToBinary.this;
                wasmIrToBinary.writeString(wasmIrToBinary.b, "name");
                WasmIrToBinary wasmIrToBinary2 = WasmIrToBinary.this;
                short sM7520getCUSTOMEsw0DI4 = WasmBinary.Section.INSTANCE.m7520getCUSTOMEsw0DI4();
                final WasmIrToBinary wasmIrToBinary3 = WasmIrToBinary.this;
                wasmIrToBinary2.m7535appendSectionSr9dMcE(sM7520getCUSTOMEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        WasmIrToBinary wasmIrToBinary4 = wasmIrToBinary3;
                        wasmIrToBinary4.writeString(wasmIrToBinary4.b, wasmIrToBinary3.getModuleName());
                    }
                });
                WasmIrToBinary wasmIrToBinary4 = WasmIrToBinary.this;
                short sM7532getTYPEEsw0DI4 = WasmBinary.Section.INSTANCE.m7532getTYPEEsw0DI4();
                final WasmIrToBinary wasmIrToBinary5 = WasmIrToBinary.this;
                final List<WasmFunction.Defined> list = definedFunctions;
                wasmIrToBinary4.m7535appendSectionSr9dMcE(sM7532getTYPEEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        wasmIrToBinary5.appendVectorSize(list.size());
                        List<WasmFunction.Defined> list2 = list;
                        WasmIrToBinary wasmIrToBinary6 = wasmIrToBinary5;
                        for (WasmFunction.Defined defined : list2) {
                            wasmIrToBinary6.appendModuleFieldReference(defined);
                            wasmIrToBinary6.writeString(wasmIrToBinary6.b, defined.getName());
                        }
                    }
                });
                WasmIrToBinary wasmIrToBinary6 = WasmIrToBinary.this;
                short sM7527getIMPORTEsw0DI4 = WasmBinary.Section.INSTANCE.m7527getIMPORTEsw0DI4();
                final WasmIrToBinary wasmIrToBinary7 = WasmIrToBinary.this;
                final List<WasmFunction.Defined> list2 = definedFunctions;
                wasmIrToBinary6.m7535appendSectionSr9dMcE(sM7527getIMPORTEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        wasmIrToBinary7.appendVectorSize(list2.size());
                        List<WasmFunction.Defined> list3 = list2;
                        WasmIrToBinary wasmIrToBinary8 = wasmIrToBinary7;
                        for (WasmFunction.Defined defined : list3) {
                            wasmIrToBinary8.appendModuleFieldReference(defined);
                            wasmIrToBinary8.appendVectorSize(defined.getLocals().size());
                            for (WasmLocal wasmLocal : defined.getLocals()) {
                                wasmIrToBinary8.writeVarUInt32(wasmIrToBinary8.b, wasmLocal.getId());
                                wasmIrToBinary8.writeString(wasmIrToBinary8.b, wasmLocal.getName());
                            }
                        }
                    }
                });
                WasmIrToBinary wasmIrToBinary8 = WasmIrToBinary.this;
                short sM7530getTABLEEsw0DI4 = WasmBinary.Section.INSTANCE.m7530getTABLEEsw0DI4();
                final WasmIrToBinary wasmIrToBinary9 = WasmIrToBinary.this;
                wasmIrToBinary8.m7535appendSectionSr9dMcE(sM7530getTABLEEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.4
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        WasmIrToBinary wasmIrToBinary10 = wasmIrToBinary9;
                        wasmIrToBinary10.appendVectorSize(wasmIrToBinary10.getModule().getRecGroupTypes().size());
                        List<WasmTypeDeclaration> recGroupTypes = wasmIrToBinary9.getModule().getRecGroupTypes();
                        WasmIrToBinary wasmIrToBinary11 = wasmIrToBinary9;
                        for (WasmTypeDeclaration wasmTypeDeclaration : recGroupTypes) {
                            wasmIrToBinary11.appendModuleFieldReference(wasmTypeDeclaration);
                            wasmIrToBinary11.writeString(wasmIrToBinary11.b, wasmTypeDeclaration.getName());
                        }
                    }
                });
                WasmIrToBinary wasmIrToBinary10 = WasmIrToBinary.this;
                short sM7524getEXPORTEsw0DI4 = WasmBinary.Section.INSTANCE.m7524getEXPORTEsw0DI4();
                final WasmIrToBinary wasmIrToBinary11 = WasmIrToBinary.this;
                wasmIrToBinary10.m7535appendSectionSr9dMcE(sM7524getEXPORTEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.5
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        WasmIrToBinary wasmIrToBinary12 = wasmIrToBinary11;
                        wasmIrToBinary12.appendVectorSize(wasmIrToBinary12.getModule().getGlobals().size());
                        List<WasmGlobal> globals = wasmIrToBinary11.getModule().getGlobals();
                        WasmIrToBinary wasmIrToBinary13 = wasmIrToBinary11;
                        for (WasmGlobal wasmGlobal : globals) {
                            wasmIrToBinary13.appendModuleFieldReference(wasmGlobal);
                            wasmIrToBinary13.writeString(wasmIrToBinary13.b, wasmGlobal.getName());
                        }
                    }
                });
                WasmIrToBinary wasmIrToBinary12 = WasmIrToBinary.this;
                short sM7519getCODEEsw0DI4 = WasmBinary.Section.INSTANCE.m7519getCODEEsw0DI4();
                final WasmIrToBinary wasmIrToBinary13 = WasmIrToBinary.this;
                wasmIrToBinary12.m7535appendSectionSr9dMcE(sM7519getCODEEsw0DI4, new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendTextSection.1.6
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        List<WasmTypeDeclaration> recGroupTypes = wasmIrToBinary13.getModule().getRecGroupTypes();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : recGroupTypes) {
                            if (obj instanceof WasmStructDeclaration) {
                                arrayList.add(obj);
                            }
                        }
                        ArrayList<WasmStructDeclaration> arrayList2 = arrayList;
                        wasmIrToBinary13.appendVectorSize(arrayList2.size());
                        WasmIrToBinary wasmIrToBinary14 = wasmIrToBinary13;
                        for (WasmStructDeclaration wasmStructDeclaration : arrayList2) {
                            wasmIrToBinary14.appendModuleFieldReference(wasmStructDeclaration);
                            wasmIrToBinary14.appendVectorSize(wasmStructDeclaration.getFields().size());
                            int i = 0;
                            for (Object obj2 : wasmStructDeclaration.getFields()) {
                                int i2 = i + 1;
                                if (i < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                wasmIrToBinary14.writeVarUInt32(wasmIrToBinary14.b, i);
                                wasmIrToBinary14.writeString(wasmIrToBinary14.b, ((WasmStructFieldDeclaration) obj2).getName());
                                i = i2;
                            }
                        }
                    }
                });
            }
        });
    }

    private final void appendInstr(WasmInstr instr) {
        List<SourceLocationMapping> list;
        SourceLocation location = instr.getLocation();
        if (location != null && (list = this.sourceLocationMappings) != null) {
            list.add(new SourceLocationMapping(this.offsets.add(new Box(this.b.getWritten())), location));
        }
        int opcode = instr.getOperator().getOpcode();
        if (opcode == 65535) {
            return;
        }
        if (opcode > 255) {
            this.b.writeByte((byte) (opcode >>> 8));
            this.b.writeByte((byte) (opcode & 255));
        } else {
            this.b.writeByte((byte) opcode);
        }
        Iterator<T> it = instr.getImmediates().iterator();
        while (it.hasNext()) {
            appendImmediate((WasmImmediate) it.next());
        }
    }

    private final void appendImmediate(WasmImmediate x) {
        if (x instanceof WasmImmediate.ConstU8) {
            this.b.m7506writeUByte7apg3OU(((WasmImmediate.ConstU8) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.ConstI32) {
            this.b.writeVarInt32(((WasmImmediate.ConstI32) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.ConstI64) {
            this.b.writeVarInt64(((WasmImmediate.ConstI64) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.ConstF32) {
            this.b.m7507writeUInt32WZ4Q5Ns(((WasmImmediate.ConstF32) x).getRawBits());
            return;
        }
        if (x instanceof WasmImmediate.ConstF64) {
            this.b.m7508writeUInt64VKZWuLQ(((WasmImmediate.ConstF64) x).getRawBits());
            return;
        }
        if (x instanceof WasmImmediate.SymbolI32) {
            this.b.writeVarInt32(((WasmImmediate.SymbolI32) x).getValue().getOwner().intValue());
            return;
        }
        if (x instanceof WasmImmediate.MemArg) {
            WasmImmediate.MemArg memArg = (WasmImmediate.MemArg) x;
            this.b.m7509writeVarUInt32WZ4Q5Ns(memArg.getAlign());
            this.b.m7509writeVarUInt32WZ4Q5Ns(memArg.getOffset());
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
            appendModuleFieldReference(((WasmImmediate.TypeIdx) x).getValue().getOwner());
            return;
        }
        if (x instanceof WasmImmediate.MemoryIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.MemoryIdx) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.DataIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.DataIdx) x).getValue().getOwner().intValue());
            return;
        }
        if (x instanceof WasmImmediate.TableIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.TableIdx) x).getValue().getOwner().intValue());
            return;
        }
        if (x instanceof WasmImmediate.LabelIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.LabelIdx) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.TagIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.TagIdx) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.LabelIdxVector) {
            WasmImmediate.LabelIdxVector labelIdxVector = (WasmImmediate.LabelIdxVector) x;
            writeVarUInt32(this.b, labelIdxVector.getValue().size());
            Iterator<Integer> it = labelIdxVector.getValue().iterator();
            while (it.hasNext()) {
                writeVarUInt32(this.b, it.next().intValue());
            }
            return;
        }
        if (x instanceof WasmImmediate.ElemIdx) {
            appendModuleFieldReference(((WasmImmediate.ElemIdx) x).getValue());
            return;
        }
        if (x instanceof WasmImmediate.ValTypeVector) {
            WasmImmediate.ValTypeVector valTypeVector = (WasmImmediate.ValTypeVector) x;
            writeVarUInt32(this.b, valTypeVector.getValue().size());
            Iterator<WasmType> it2 = valTypeVector.getValue().iterator();
            while (it2.hasNext()) {
                appendType(it2.next());
            }
            return;
        }
        if (x instanceof WasmImmediate.GcType) {
            appendModuleFieldReference(((WasmImmediate.GcType) x).getValue().getOwner());
            return;
        }
        if (x instanceof WasmImmediate.StructFieldIdx) {
            writeVarUInt32(this.b, ((WasmImmediate.StructFieldIdx) x).getValue().getOwner().intValue());
        } else if (x instanceof WasmImmediate.HeapType) {
            appendHeapType(((WasmImmediate.HeapType) x).getValue());
        } else if (x instanceof WasmImmediate.ConstString) {
            throw new IllegalStateException("Instructions with pseudo immediates should be skipped".toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: appendSection-Sr9dMcE, reason: not valid java name */
    public final void m7535appendSectionSr9dMcE(short section, final Function0<Unit> content) {
        this.b.m7510writeVarUInt7xj2QHRw(section);
        withVarUInt32PayloadSizePrepended(new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary$appendSection$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                content.invoke();
            }
        });
    }

    private final void withVarUInt32PayloadSizePrepended(Function0<Unit> fn) {
        Box box = new Box(-1);
        PersistentList<Box> persistentList = this.offsets;
        this.offsets = persistentList.add(box);
        ByteWriter byteWriter = this.b;
        ByteWriter byteWriterCreateTemp = byteWriter.createTemp();
        this.b = byteWriterCreateTemp;
        fn.invoke();
        this.b = byteWriter;
        writeVarUInt32(byteWriter, byteWriterCreateTemp.getWritten());
        box.setValue(this.b.getWritten());
        this.offsets = persistentList;
        this.b.write(byteWriterCreateTemp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendVectorSize(int size) {
        writeVarUInt32(this.b, size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendFunctionTypeDeclaration(WasmFunctionType type) {
        this.b.writeVarInt7((byte) -32);
        writeVarUInt32(this.b, type.getParameterTypes().size());
        Iterator<T> it = type.getParameterTypes().iterator();
        while (it.hasNext()) {
            appendType((WasmType) it.next());
        }
        writeVarUInt32(this.b, type.getResultTypes().size());
        Iterator<T> it2 = type.getResultTypes().iterator();
        while (it2.hasNext()) {
            appendType((WasmType) it2.next());
        }
    }

    private final void appendBlockType(WasmImmediate.BlockType type) {
        if (type instanceof WasmImmediate.BlockType.Function) {
            appendModuleFieldReference(((WasmImmediate.BlockType.Function) type).getType());
            return;
        }
        if (type instanceof WasmImmediate.BlockType.Value) {
            WasmImmediate.BlockType.Value value = (WasmImmediate.BlockType.Value) type;
            if (value.getType() == null) {
                this.b.writeVarInt7((byte) -64);
            } else {
                appendType(value.getType());
            }
        }
    }

    private final void appendFiledType(WasmStructFieldDeclaration field) {
        appendType(field.getType());
        this.b.writeVarUInt1(field.getIsMutable());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendStructTypeDeclaration(WasmStructDeclaration type) {
        this.b.writeVarInt7((byte) -48);
        WasmSymbolReadOnly<WasmTypeDeclaration> superType = type.getSuperType();
        if (superType != null) {
            appendVectorSize(1);
            appendModuleFieldReference(superType.getOwner());
        } else {
            appendVectorSize(0);
        }
        this.b.writeVarInt7(WasmBinary.STRUCT_TYPE);
        writeVarUInt32(this.b, type.getFields().size());
        Iterator<T> it = type.getFields().iterator();
        while (it.hasNext()) {
            appendFiledType((WasmStructFieldDeclaration) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendArrayTypeDeclaration(WasmArrayDeclaration type) {
        this.b.writeVarInt7(WasmBinary.ARRAY_TYPE);
        appendFiledType(type.getField());
    }

    public final int getIndex(WasmFunctionType wasmFunctionType) {
        Intrinsics.checkNotNullParameter(wasmFunctionType, "<this>");
        Integer id2 = wasmFunctionType.getId();
        Intrinsics.checkNotNull(id2);
        return id2.intValue();
    }

    private final void appendLimits(WasmLimits limits) {
        this.b.writeVarUInt1(limits.m7497getMaxSize0hXNFcg() != null);
        this.b.m7509writeVarUInt32WZ4Q5Ns(limits.m7498getMinSizepVg5ArA());
        if (limits.m7497getMaxSize0hXNFcg() != null) {
            this.b.m7509writeVarUInt32WZ4Q5Ns(limits.m7497getMaxSize0hXNFcg().getData());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendImportedFunction(WasmFunction.Imported function) {
        writeString(this.b, function.getImportPair().getModuleName());
        writeString(this.b, function.getImportPair().getDeclarationName());
        this.b.writeByte((byte) 0);
        writeVarUInt32(this.b, getIndex(function.getType().getOwner()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendDefinedFunction(WasmFunction.Defined function) {
        writeVarUInt32(this.b, getIndex(function.getType().getOwner()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendTable(WasmTable table) {
        if (table.getImportPair() != null) {
            writeString(this.b, table.getImportPair().getModuleName());
            writeString(this.b, table.getImportPair().getDeclarationName());
            this.b.writeByte((byte) 1);
        }
        appendType(table.getElementType());
        appendLimits(table.getLimits());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendMemory(WasmMemory memory) {
        if (memory.getImportPair() != null) {
            writeString(this.b, memory.getImportPair().getModuleName());
            writeString(this.b, memory.getImportPair().getDeclarationName());
            this.b.writeByte((byte) 2);
        }
        appendLimits(memory.getLimits());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendGlobal(WasmGlobal c) {
        if (c.getImportPair() != null) {
            writeString(this.b, c.getImportPair().getModuleName());
            writeString(this.b, c.getImportPair().getDeclarationName());
            this.b.writeByte((byte) 3);
            appendType(c.getType());
            this.b.writeVarUInt1(c.getIsMutable());
            return;
        }
        appendType(c.getType());
        this.b.writeVarUInt1(c.getIsMutable());
        appendExpr(c.getInit());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendTag(WasmTag t) {
        if (t.getImportPair() != null) {
            writeString(this.b, t.getImportPair().getModuleName());
            writeString(this.b, t.getImportPair().getDeclarationName());
            this.b.writeByte((byte) 4);
        } else {
            this.b.writeByte((byte) 0);
            t.getType().getId();
            ByteWriter byteWriter = this.b;
            Integer id2 = t.getType().getId();
            Intrinsics.checkNotNull(id2);
            writeVarUInt32(byteWriter, id2.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendExport(WasmExport<?> export) {
        writeString(this.b, export.getName());
        this.b.writeByte(export.getKind());
        appendModuleFieldReference(export.getField());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendStartFunction(WasmFunction startFunction) {
        appendModuleFieldReference(startFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:15:0x0037  */
    public final void appendElement(WasmElement element) {
        boolean z;
        ArrayList arrayList;
        List<WasmTable.Value> values = element.getValues();
        if (!(values instanceof Collection) || !values.isEmpty()) {
            Iterator<T> it = values.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!(((WasmTable.Value) it.next()) instanceof WasmTable.Value.Function)) {
                    }
                } else if (Intrinsics.areEqual(element.getType(), WasmFuncRef.INSTANCE)) {
                    z = true;
                }
                z = false;
            }
        } else if (Intrinsics.areEqual(element.getType(), WasmFuncRef.INSTANCE)) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            List<WasmTable.Value> values2 = element.getValues();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(values2, 10));
            for (WasmTable.Value value : values2) {
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.wasm.ir.WasmTable.Value.Function");
                Integer id2 = ((WasmTable.Value.Function) value).getFunction().getOwner().getId();
                Intrinsics.checkNotNull(id2);
                arrayList2.add(Integer.valueOf(id2.intValue()));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        WasmElement.Mode mode = element.getMode();
        if (Intrinsics.areEqual(mode, WasmElement.Mode.Passive.INSTANCE)) {
            this.b.writeByte(z ? (byte) 1 : (byte) 5);
            appendElement$writeTypeOrKind(z, this, element);
            appendElement$writeElements(this, element, arrayList);
            return;
        }
        if (mode instanceof WasmElement.Mode.Active) {
            WasmElement.Mode.Active active = (WasmElement.Mode.Active) mode;
            Integer id3 = active.getTable().getId();
            Intrinsics.checkNotNull(id3);
            if (id3.intValue() == 0 && z) {
                this.b.writeByte((byte) 0);
                appendExpr(active.getOffset());
            } else if (z) {
                this.b.writeByte((byte) 2);
                appendModuleFieldReference(active.getTable());
                appendExpr(active.getOffset());
                appendElement$writeTypeOrKind(z, this, element);
            } else {
                this.b.writeByte((byte) 6);
                appendModuleFieldReference(active.getTable());
                appendExpr(active.getOffset());
                appendElement$writeTypeOrKind(z, this, element);
            }
            appendElement$writeElements(this, element, arrayList);
            return;
        }
        if (Intrinsics.areEqual(mode, WasmElement.Mode.Declarative.INSTANCE)) {
            this.b.writeByte(z ? (byte) 3 : (byte) 7);
            appendElement$writeTypeOrKind(z, this, element);
            appendElement$writeElements(this, element, arrayList);
        }
    }

    private static final void appendElement$writeElements(WasmIrToBinary wasmIrToBinary, WasmElement wasmElement, List<Integer> list) {
        wasmIrToBinary.appendVectorSize(wasmElement.getValues().size());
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                wasmIrToBinary.writeVarUInt32(wasmIrToBinary.b, ((Number) it.next()).intValue());
            }
            return;
        }
        for (WasmTable.Value value : wasmElement.getValues()) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.wasm.ir.WasmTable.Value.Expression");
            wasmIrToBinary.appendExpr(((WasmTable.Value.Expression) value).getExpr());
        }
    }

    private static final void appendElement$writeTypeOrKind(boolean z, WasmIrToBinary wasmIrToBinary, WasmElement wasmElement) {
        if (z) {
            wasmIrToBinary.b.writeByte((byte) 0);
        } else {
            wasmIrToBinary.appendType(wasmElement.getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendCode(final WasmFunction.Defined function) {
        withVarUInt32PayloadSizePrepended(new Function0<Unit>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary.appendCode.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                WasmIrToBinary wasmIrToBinary = WasmIrToBinary.this;
                ByteWriter byteWriter = wasmIrToBinary.b;
                List<WasmLocal> locals = function.getLocals();
                int i = 0;
                if (!(locals instanceof Collection) || !locals.isEmpty()) {
                    Iterator<T> it = locals.iterator();
                    while (it.hasNext()) {
                        if (!((WasmLocal) it.next()).getIsParameter() && (i = i + 1) < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                wasmIrToBinary.writeVarUInt32(byteWriter, i);
                List<WasmLocal> locals2 = function.getLocals();
                WasmIrToBinary wasmIrToBinary2 = WasmIrToBinary.this;
                for (WasmLocal wasmLocal : locals2) {
                    if (!wasmLocal.getIsParameter()) {
                        wasmIrToBinary2.b.m7509writeVarUInt32WZ4Q5Ns(1);
                        wasmIrToBinary2.appendType(wasmLocal.getType());
                    }
                }
                WasmIrToBinary.this.appendExpr(function.getInstructions());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendData(WasmData wasmData) {
        WasmDataMode mode = wasmData.getMode();
        if (mode instanceof WasmDataMode.Active) {
            WasmDataMode.Active active = (WasmDataMode.Active) mode;
            if (active.getMemoryIdx() == 0) {
                this.b.writeByte((byte) 0);
            } else {
                this.b.writeByte((byte) 2);
                writeVarUInt32(this.b, active.getMemoryIdx());
            }
            appendExpr(active.getOffset());
        } else if (Intrinsics.areEqual(mode, WasmDataMode.Passive.INSTANCE)) {
            this.b.writeByte((byte) 1);
        }
        writeVarUInt32(this.b, wasmData.getBytes().length);
        this.b.writeBytes(wasmData.getBytes());
    }

    public final void appendHeapType(WasmHeapType type) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof WasmHeapType.Simple) {
            iIntValue = ((WasmHeapType.Simple) type).getCode();
        } else {
            if (!(type instanceof WasmHeapType.Type)) {
                throw new NoWhenBranchMatchedException();
            }
            Integer id2 = ((WasmHeapType.Type) type).getType().getOwner().getId();
            Intrinsics.checkNotNull(id2);
            iIntValue = id2.intValue();
        }
        this.b.writeVarInt32(iIntValue);
    }

    public final void appendType(WasmType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.b.writeVarInt7(type.getCode());
        if (type instanceof WasmRefType) {
            appendHeapType(((WasmRefType) type).getHeapType());
        }
        if (type instanceof WasmRefNullType) {
            appendHeapType(((WasmRefNullType) type).getHeapType());
        }
    }

    public final void appendLocalReference(WasmLocal local) {
        Intrinsics.checkNotNullParameter(local, "local");
        writeVarUInt32(this.b, local.getId());
    }

    public final void appendModuleFieldReference(WasmNamedModuleField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        Integer id2 = field.getId();
        if (id2 == null) {
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(field.getClass()) + StringUtil.SPACE + field.getName() + " ID is unlinked").toString());
        }
        writeVarUInt32(this.b, id2.intValue());
    }

    public final void writeVarUInt32(ByteWriter byteWriter, int i) {
        Intrinsics.checkNotNullParameter(byteWriter, "<this>");
        byteWriter.m7509writeVarUInt32WZ4Q5Ns(UInt.m2993constructorimpl(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeString(ByteWriter byteWriter, String str) {
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        writeVarUInt32(byteWriter, bytes.length);
        byteWriter.writeBytes(bytes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendExpr(Iterable<? extends WasmInstr> expr) {
        Iterator<? extends WasmInstr> it = expr.iterator();
        while (it.hasNext()) {
            appendInstr(it.next());
        }
        appendInstr(new WasmInstrWithLocation(WasmOp.END, SourceLocation.INSTANCE.NoLocation("End of instruction list")));
    }
}
