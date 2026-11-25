package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.utils.AnnotationsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmAnyRef;
import org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmExternRef;
import org.jetbrains.kotlin.wasm.ir.WasmF32;
import org.jetbrains.kotlin.wasm.ir.WasmF64;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmI64;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmOp;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullExternrefType;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullType;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullrefType;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmUnreachableType;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: DeclarationGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0016\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005\u001a\u0010\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f*\u00020\u0011\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0011¨\u0006\u0014"}, d2 = {"generateConstExpression", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "body", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "context", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "generateDefaultInitializerForType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "g", "getEffectiveValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isExported", "", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationGeneratorKt {
    public static final void generateDefaultInitializerForType(WasmType type, WasmExpressionBuilder g) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(g, "g");
        SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("Default initializer, usually don't require location");
        if (Intrinsics.areEqual(type, WasmI32.INSTANCE)) {
            g.buildConstI32(0, sourceLocationNoLocation);
            return;
        }
        if (Intrinsics.areEqual(type, WasmI64.INSTANCE)) {
            g.buildConstI64(0L, sourceLocationNoLocation);
            return;
        }
        if (Intrinsics.areEqual(type, WasmF32.INSTANCE)) {
            g.buildConstF32(0.0f, sourceLocationNoLocation);
            return;
        }
        if (Intrinsics.areEqual(type, WasmF64.INSTANCE)) {
            g.buildConstF64(0.0d, sourceLocationNoLocation);
            return;
        }
        if (type instanceof WasmRefNullType) {
            g.buildRefNull(((WasmRefNullType) type).getHeapType(), sourceLocationNoLocation);
            return;
        }
        if (type instanceof WasmRefNullrefType) {
            g.buildRefNull(WasmHeapType.Simple.None.INSTANCE, sourceLocationNoLocation);
            return;
        }
        if (type instanceof WasmRefNullExternrefType) {
            g.buildRefNull(WasmHeapType.Simple.NoExtern.INSTANCE, sourceLocationNoLocation);
            return;
        }
        if (type instanceof WasmAnyRef) {
            g.buildRefNull(WasmHeapType.Simple.Any.INSTANCE, sourceLocationNoLocation);
        } else if (type instanceof WasmExternRef) {
            g.buildRefNull(WasmHeapType.Simple.Extern.INSTANCE, sourceLocationNoLocation);
        } else {
            if (!Intrinsics.areEqual(type, WasmUnreachableType.INSTANCE)) {
                throw new IllegalStateException(("Unknown value type " + type.getName()).toString());
            }
            throw new IllegalStateException("Unreachable type can't be initialized".toString());
        }
    }

    public static final List<IrValueParameter> getEffectiveValueParameters(IrFunction irFunction) {
        IrValueParameter thisReceiver;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrConstructor) {
            thisReceiver = IrUtilsKt.getParentAsClass(irFunction).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
        } else {
            thisReceiver = null;
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{thisReceiver, irFunction.getDispatchReceiverParameter(), irFunction.getExtensionReceiverParameter()}), (Iterable) irFunction.getValueParameters());
    }

    public static final boolean isExported(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return AnnotationUtilsKt.isJsExport(irFunction) || AnnotationsKt.getWasmExportNameIfWasmExport(irFunction) != null;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004b  */
    /* JADX WARN: Code duplicated, block: B:15:0x004e  */
    public static final void generateConstExpression(IrConst<?> expression, WasmExpressionBuilder body, WasmModuleCodegenContext context, SourceLocation location) {
        Boolean boolValueOf;
        boolean zIsExternal;
        WasmType wasmType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(location, "location");
        IrConstKind<?> kind = expression.getKind();
        if (kind instanceof IrConstKind.Null) {
            IrClass irClass = IrTypesKt.getClass(expression.getType());
            if (irClass == null) {
                IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(expression.getType());
                if (erasedUpperBound != null) {
                    zIsExternal = erasedUpperBound.getIsExternal();
                } else {
                    boolValueOf = null;
                }
                if (Intrinsics.areEqual((Object) boolValueOf, (Object) true)) {
                    wasmType = WasmRefNullExternrefType.INSTANCE;
                } else {
                    wasmType = WasmRefNullrefType.INSTANCE;
                }
                body.buildInstr(WasmOp.REF_NULL, location, new WasmImmediate.HeapType(wasmType));
                return;
            }
            zIsExternal = irClass.getIsExternal();
            boolValueOf = Boolean.valueOf(zIsExternal);
            if (Intrinsics.areEqual((Object) boolValueOf, (Object) true)) {
                wasmType = WasmRefNullExternrefType.INSTANCE;
            } else {
                wasmType = WasmRefNullrefType.INSTANCE;
            }
            body.buildInstr(WasmOp.REF_NULL, location, new WasmImmediate.HeapType(wasmType));
            return;
        }
        if (kind instanceof IrConstKind.Boolean) {
            body.buildConstI32(((IrConstKind.Boolean) kind).valueOf(expression).booleanValue() ? 1 : 0, location);
            return;
        }
        if (kind instanceof IrConstKind.Byte) {
            body.buildConstI32(((IrConstKind.Byte) kind).valueOf(expression).byteValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Short) {
            body.buildConstI32(((IrConstKind.Short) kind).valueOf(expression).shortValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Int) {
            body.buildConstI32(((IrConstKind.Int) kind).valueOf(expression).intValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Long) {
            body.buildConstI64(((IrConstKind.Long) kind).valueOf(expression).longValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Char) {
            body.buildConstI32(((IrConstKind.Char) kind).valueOf(expression).charValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Float) {
            body.buildConstF32(((IrConstKind.Float) kind).valueOf(expression).floatValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.Double) {
            body.buildConstF64(((IrConstKind.Double) kind).valueOf(expression).doubleValue(), location);
            return;
        }
        if (kind instanceof IrConstKind.String) {
            String strValueOf = ((IrConstKind.String) kind).valueOf(expression);
            Pair<WasmSymbol<Integer>, WasmSymbol<Integer>> pairReferenceStringLiteralAddressAndId = context.referenceStringLiteralAddressAndId(strValueOf);
            WasmSymbol<Integer> wasmSymbolComponent1 = pairReferenceStringLiteralAddressAndId.component1();
            WasmSymbol<Integer> wasmSymbolComponent2 = pairReferenceStringLiteralAddressAndId.component2();
            body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("const string: \"" + strValueOf + '\"'));
            body.buildConstI32Symbol(wasmSymbolComponent2, location);
            body.buildConstI32Symbol(wasmSymbolComponent1, location);
            body.buildConstI32(strValueOf.length(), location);
            body.buildCall(context.referenceFunction(context.getBackendContext().getWasmSymbols().getStringGetLiteral()), location);
            body.commentGroupEnd();
            return;
        }
        throw new IllegalStateException("Unknown constant kind".toString());
    }
}
