package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmIrExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmLocal;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullType;
import org.jetbrains.kotlin.wasm.ir.WasmType;

/* JADX INFO: compiled from: WasmFunctionCodegenContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\"J\u001e\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u001aJ\u0016\u00101\u001a\u00020+2\u0006\u00102\u001a\u00020\u001d2\u0006\u00100\u001a\u00020\u001aJ\u000e\u00103\u001a\u00020#2\u0006\u00104\u001a\u00020\u001aJ\u000e\u00103\u001a\u00020#2\u0006\u00105\u001a\u00020%J\u000e\u00103\u001a\u00020#2\u0006\u0010,\u001a\u00020\"J\u0016\u00106\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0019J\u000e\u00107\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u001dR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014RB\u0010\u0015\u001a6\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017\u0012\u0004\u0012\u00020\u001a0\u0016j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001a0\u0016j\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001e\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010!\u001a\u001e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0\u0016j\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010$\u001a\u001e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020#0\u0016j\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020#`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010&\u001a\u00020'*\u00020%8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmFunctionCodegenContext;", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "wasmFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "backendContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "context", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "bodyGen", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "getBodyGen", "()Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "getIrFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "loopLevels", "Ljava/util/LinkedHashMap;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/LoopLabelType;", "", "Lkotlin/collections/LinkedHashMap;", "nonLocalReturnLevels", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "tagIdx", "getTagIdx", "()I", "wasmLocals", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "wasmSyntheticLocals", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/SyntheticLocalType;", "wasmType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "getWasmType", "(Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/SyntheticLocalType;)Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "defineLocal", "", "irValueDeclaration", "defineLoopLevel", "irLoop", "labelType", FirebaseAnalytics.Param.LEVEL, "defineNonLocalReturnLevel", "block", "referenceLocal", "index", ModuleXmlParser.TYPE, "referenceLoopLevel", "referenceNonLocalReturnLevel", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmFunctionCodegenContext {
    private final WasmBackendContext backendContext;
    private final WasmExpressionBuilder bodyGen;
    private final WasmModuleCodegenContext context;
    private final IrFunction irFunction;
    private final LinkedHashMap<Pair<IrLoop, LoopLabelType>, Integer> loopLevels;
    private final LinkedHashMap<IrReturnableBlockSymbol, Integer> nonLocalReturnLevels;
    private final WasmFunction.Defined wasmFunction;
    private final LinkedHashMap<IrValueSymbol, WasmLocal> wasmLocals;
    private final LinkedHashMap<SyntheticLocalType, WasmLocal> wasmSyntheticLocals;

    /* JADX INFO: compiled from: WasmFunctionCodegenContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SyntheticLocalType.values().length];
            try {
                iArr[SyntheticLocalType.IS_INTERFACE_PARAMETER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SyntheticLocalType.TMP_FOR_BR_ON_CAST_EMULATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SyntheticLocalType.TABLE_SWITCH_SELECTOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final int getTagIdx() {
        return 0;
    }

    public WasmFunctionCodegenContext(IrFunction irFunction, WasmFunction.Defined wasmFunction, WasmBackendContext backendContext, WasmModuleCodegenContext context) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(wasmFunction, "wasmFunction");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(context, "context");
        this.irFunction = irFunction;
        this.wasmFunction = wasmFunction;
        this.backendContext = backendContext;
        this.context = context;
        this.bodyGen = new WasmIrExpressionBuilder(wasmFunction.getInstructions());
        this.wasmLocals = new LinkedHashMap<>();
        this.wasmSyntheticLocals = new LinkedHashMap<>();
        this.loopLevels = new LinkedHashMap<>();
        this.nonLocalReturnLevels = new LinkedHashMap<>();
    }

    public final IrFunction getIrFunction() {
        return this.irFunction;
    }

    public final WasmBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final WasmModuleCodegenContext getContext() {
        return this.context;
    }

    public final WasmExpressionBuilder getBodyGen() {
        return this.bodyGen;
    }

    public final void defineLocal(IrValueSymbol irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "irValueDeclaration");
        this.wasmLocals.containsKey(irValueDeclaration);
        IrValueDeclaration owner = irValueDeclaration.getOwner();
        int size = this.wasmFunction.getLocals().size();
        String strAsString = owner.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        WasmLocal wasmLocal = new WasmLocal(size, strAsString, owner instanceof IrValueParameter ? this.context.transformValueParameterType((IrValueParameter) owner) : this.context.transformType(owner.getType()), irValueDeclaration instanceof IrValueParameterSymbol);
        this.wasmLocals.put(irValueDeclaration, wasmLocal);
        this.wasmFunction.getLocals().add(wasmLocal);
    }

    public final WasmLocal referenceLocal(IrValueSymbol irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "irValueDeclaration");
        return (WasmLocal) MapsKt.getValue(this.wasmLocals, irValueDeclaration);
    }

    public final WasmLocal referenceLocal(int index) {
        return this.wasmFunction.getLocals().get(index);
    }

    private final WasmType getWasmType(SyntheticLocalType syntheticLocalType) {
        int i = WhenMappings.$EnumSwitchMapping$0[syntheticLocalType.ordinal()];
        if (i == 1) {
            return new WasmRefNullType(new WasmHeapType.Type(this.context.referenceGcType(this.backendContext.getIrBuiltIns().getAnyClass())));
        }
        if (i == 2) {
            return new WasmRefNullType(WasmHeapType.Simple.Any.INSTANCE);
        }
        if (i == 3) {
            return WasmI32.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final WasmLocal referenceLocal(SyntheticLocalType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        LinkedHashMap<SyntheticLocalType, WasmLocal> linkedHashMap = this.wasmSyntheticLocals;
        WasmLocal wasmLocal = linkedHashMap.get(type);
        if (wasmLocal == null) {
            wasmLocal = new WasmLocal(this.wasmFunction.getLocals().size(), type.name(), getWasmType(type), false);
            this.wasmFunction.getLocals().add(wasmLocal);
            linkedHashMap.put(type, wasmLocal);
        }
        return wasmLocal;
    }

    public final void defineNonLocalReturnLevel(IrReturnableBlockSymbol block, int level) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.nonLocalReturnLevels.put(block, Integer.valueOf(level));
    }

    public final int referenceNonLocalReturnLevel(IrReturnableBlockSymbol block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return ((Number) MapsKt.getValue(this.nonLocalReturnLevels, block)).intValue();
    }

    public final void defineLoopLevel(IrLoop irLoop, LoopLabelType labelType, int level) {
        Intrinsics.checkNotNullParameter(irLoop, "irLoop");
        Intrinsics.checkNotNullParameter(labelType, "labelType");
        Pair<IrLoop, LoopLabelType> pair = new Pair<>(irLoop, labelType);
        this.loopLevels.containsKey(pair);
        this.loopLevels.put(pair, Integer.valueOf(level));
    }

    public final int referenceLoopLevel(IrLoop irLoop, LoopLabelType labelType) {
        Intrinsics.checkNotNullParameter(irLoop, "irLoop");
        Intrinsics.checkNotNullParameter(labelType, "labelType");
        return ((Number) MapsKt.getValue(this.loopLevels, new Pair(irLoop, labelType))).intValue();
    }
}
