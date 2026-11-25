package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmExport;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmFunctionType;
import org.jetbrains.kotlin.wasm.ir.WasmGlobal;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmTypeDeclaration;

/* JADX INFO: compiled from: WasmModuleCodegenContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0018\u001a\u00020\u00192\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bJ\u0016\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u000e\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u001eJ\u0016\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%J\u0016\u0010&\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0011J\u0016\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,J\u0016\u0010-\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010.\u001a\u00020/J\u0016\u00100\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%J\u0016\u00101\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u00102\u001a\u000203J\u0016\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u0002062\u0006\u00102\u001a\u000203J\u0016\u00107\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u00102\u001a\u000203J\u0016\u00108\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%J\u0016\u00109\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020;J\u000e\u0010<\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u000bJ\u000e\u0010=\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010?\u001a\u00020@J\u000e\u0010A\u001a\u00020B2\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020%0\u00102\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010#\u001a\u00020\u000bJ&\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0018\u0010F\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020I0H\u0012\u0004\u0012\u00020J0GJ\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020,0\u00102\u0006\u0010)\u001a\u00020*J\u0014\u0010L\u001a\b\u0012\u0004\u0012\u00020/0\u00102\u0006\u0010)\u001a\u00020*J\u0014\u0010M\u001a\b\u0012\u0004\u0012\u00020%0\u00102\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010N\u001a\b\u0012\u0004\u0012\u0002030\u00102\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010O\u001a\b\u0012\u0004\u0012\u0002030\u00102\u0006\u00105\u001a\u000206J\u0014\u0010P\u001a\b\u0012\u0004\u0012\u0002030\u00102\u0006\u0010#\u001a\u00020\u000bJ*\u0010Q\u001a\b\u0012\u0004\u0012\u00020%0\u00102\u0006\u0010#\u001a\u00020\u000b2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020%0SH\u0002J&\u0010T\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100G2\u0006\u0010U\u001a\u00020\u001eJ\u0014\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010#\u001a\u00020\u000bJ\u0014\u0010W\u001a\b\u0012\u0004\u0012\u00020%0\u00102\u0006\u0010#\u001a\u00020\u000bJ\u0016\u0010X\u001a\u00020\u00192\u0006\u0010+\u001a\u00020,2\u0006\u0010Y\u001a\u00020\u001eJ\u0010\u0010Z\u001a\u0004\u0018\u00010J2\u0006\u0010[\u001a\u00020\\J\u000e\u0010]\u001a\u00020J2\u0006\u0010[\u001a\u00020\\J\u000e\u0010^\u001a\u00020J2\u0006\u0010[\u001a\u00020\\J\u0010\u0010_\u001a\u0004\u0018\u00010J2\u0006\u0010[\u001a\u00020\\J\u000e\u0010`\u001a\u00020J2\u0006\u0010[\u001a\u00020\\J\u000e\u0010a\u001a\u00020J2\u0006\u0010b\u001a\u00020cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "", "backendContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "wasmFragment", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "classMetadataCache", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ClassMetadata;", "interfaceMetadataCache", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/InterfaceMetadata;", "scratchMemAddr", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "", "getScratchMemAddr", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "stringPoolSize", "getStringPoolSize", "typeTransformer", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmTypeTransformer;", "addExport", "", "wasmExport", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "addJsFun", "importName", "", "jsCode", "addJsModuleImport", "module", "defineClassITableGcType", "irClass", "wasmType", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "defineClassITableInterfaceSlot", "slot", "defineFunction", "irFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "wasmFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "defineFunctionType", "wasmFunctionType", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "defineGcType", "defineGlobalClassITable", "wasmGlobal", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "defineGlobalField", "irField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "defineGlobalVTable", "defineVTableGcType", "generateTypeInfo", "typeInfo", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "getClassMetadata", "getInterfaceMetadata", "getStructFieldRef", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "isAlreadyDefinedClassITableGcType", "", "referenceClassITableGcType", "referenceClassITableInterfaceSlot", "referenceConstantArray", "resource", "Lkotlin/Pair;", "", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "referenceFunction", "referenceFunctionType", "referenceGcType", "referenceGlobalClassITable", "referenceGlobalField", "referenceGlobalVTable", "referenceNonNothingType", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableAndDefinable;", "referenceStringLiteralAddressAndId", "string", "referenceTypeId", "referenceVTableGcType", "registerInitFunction", "priority", "transformBlockResultType", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "transformBoxedType", "transformFieldType", "transformResultType", "transformType", "transformValueParameterType", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmModuleCodegenContext {
    private final WasmBackendContext backendContext;
    private final Map<IrClassSymbol, ClassMetadata> classMetadataCache;
    private final Map<IrClassSymbol, InterfaceMetadata> interfaceMetadataCache;
    private final WasmTypeTransformer typeTransformer;
    private final WasmCompiledModuleFragment wasmFragment;

    public WasmModuleCodegenContext(WasmBackendContext backendContext, WasmCompiledModuleFragment wasmFragment) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(wasmFragment, "wasmFragment");
        this.backendContext = backendContext;
        this.wasmFragment = wasmFragment;
        this.typeTransformer = new WasmTypeTransformer(this, backendContext.getIrBuiltIns());
        this.classMetadataCache = new LinkedHashMap();
        this.interfaceMetadataCache = new LinkedHashMap();
    }

    public final WasmBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final WasmSymbol<Integer> getScratchMemAddr() {
        return this.wasmFragment.getScratchMemAddr();
    }

    public final WasmSymbol<Integer> getStringPoolSize() {
        return this.wasmFragment.getStringPoolSize();
    }

    public final WasmType transformType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return this.typeTransformer.toWasmValueType(irType);
    }

    public final WasmType transformFieldType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return this.typeTransformer.toWasmFieldType(irType);
    }

    public final WasmType transformBoxedType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return this.typeTransformer.toBoxedInlineClassType(irType);
    }

    public final WasmType transformValueParameterType(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        WasmTypeTransformer wasmTypeTransformer = this.typeTransformer;
        if (wasmTypeTransformer.getContext().backendContext.getInlineClassesUtils().shouldValueParameterBeBoxed(irValueParameter)) {
            return wasmTypeTransformer.toBoxedInlineClassType(irValueParameter.getType());
        }
        return wasmTypeTransformer.toWasmValueType(irValueParameter.getType());
    }

    public final WasmType transformResultType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return this.typeTransformer.toWasmResultType(irType);
    }

    public final WasmType transformBlockResultType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return this.typeTransformer.toWasmBlockResultType(irType);
    }

    public final Pair<WasmSymbol<Integer>, WasmSymbol<Integer>> referenceStringLiteralAddressAndId(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return TuplesKt.to(this.wasmFragment.getStringLiteralAddress().reference(string), this.wasmFragment.getStringLiteralPoolId().reference(string));
    }

    public final WasmSymbol<Integer> referenceConstantArray(Pair<? extends List<Long>, ? extends WasmType> resource) {
        Intrinsics.checkNotNullParameter(resource, "resource");
        return this.wasmFragment.getConstantArrayDataSegmentId().reference(resource);
    }

    public final void generateTypeInfo(IrClassSymbol irClass, ConstantDataElement typeInfo) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(typeInfo, "typeInfo");
        this.wasmFragment.getTypeInfo().define(irClass, typeInfo);
    }

    public final void registerInitFunction(WasmFunction wasmFunction, String priority) {
        Intrinsics.checkNotNullParameter(wasmFunction, "wasmFunction");
        Intrinsics.checkNotNullParameter(priority, "priority");
        this.wasmFragment.getInitFunctions().add(new WasmCompiledModuleFragment.FunWithPriority(wasmFunction, priority));
    }

    public final void addExport(WasmExport<?> wasmExport) {
        Intrinsics.checkNotNullParameter(wasmExport, "wasmExport");
        this.wasmFragment.getExports().add(wasmExport);
    }

    public final void defineFunction(IrFunctionSymbol irFunction, WasmFunction wasmFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(wasmFunction, "wasmFunction");
        this.wasmFragment.getFunctions().define(irFunction, wasmFunction);
    }

    public final void defineGlobalField(IrFieldSymbol irField, WasmGlobal wasmGlobal) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        Intrinsics.checkNotNullParameter(wasmGlobal, "wasmGlobal");
        this.wasmFragment.getGlobalFields().define(irField, wasmGlobal);
    }

    public final void defineGlobalVTable(IrClassSymbol irClass, WasmGlobal wasmGlobal) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(wasmGlobal, "wasmGlobal");
        this.wasmFragment.getGlobalVTables().define(irClass, wasmGlobal);
    }

    public final void defineGlobalClassITable(IrClassSymbol irClass, WasmGlobal wasmGlobal) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(wasmGlobal, "wasmGlobal");
        this.wasmFragment.getGlobalClassITables().define(irClass, wasmGlobal);
    }

    public final void defineGcType(IrClassSymbol irClass, WasmTypeDeclaration wasmType) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(wasmType, "wasmType");
        this.wasmFragment.getGcTypes().define(irClass, wasmType);
    }

    public final void defineVTableGcType(IrClassSymbol irClass, WasmTypeDeclaration wasmType) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(wasmType, "wasmType");
        this.wasmFragment.getVTableGcTypes().define(irClass, wasmType);
    }

    public final void defineFunctionType(IrFunctionSymbol irFunction, WasmFunctionType wasmFunctionType) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(wasmFunctionType, "wasmFunctionType");
        this.wasmFragment.getFunctionTypes().define(irFunction, wasmFunctionType);
    }

    public final ClassMetadata getClassMetadata(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Map<IrClassSymbol, ClassMetadata> map = this.classMetadataCache;
        ClassMetadata classMetadata = map.get(irClass);
        if (classMetadata == null) {
            IrClass superClass = ClassInfoKt.getSuperClass(irClass.getOwner(), this.backendContext.getIrBuiltIns());
            ClassMetadata classMetadata2 = new ClassMetadata(irClass.getOwner(), superClass != null ? getClassMetadata(superClass.getSymbol()) : null, this.backendContext.getIrBuiltIns());
            map.put(irClass, classMetadata2);
            classMetadata = classMetadata2;
        }
        return classMetadata;
    }

    public final InterfaceMetadata getInterfaceMetadata(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Map<IrClassSymbol, InterfaceMetadata> map = this.interfaceMetadataCache;
        InterfaceMetadata interfaceMetadata = map.get(irClass);
        if (interfaceMetadata == null) {
            interfaceMetadata = new InterfaceMetadata(irClass.getOwner(), this.backendContext.getIrBuiltIns());
            map.put(irClass, interfaceMetadata);
        }
        return interfaceMetadata;
    }

    public final WasmSymbol<WasmFunction> referenceFunction(IrFunctionSymbol irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        return this.wasmFragment.getFunctions().reference(irFunction);
    }

    public final WasmSymbol<WasmGlobal> referenceGlobalField(IrFieldSymbol irField) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        return this.wasmFragment.getGlobalFields().reference(irField);
    }

    public final WasmSymbol<WasmGlobal> referenceGlobalVTable(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.wasmFragment.getGlobalVTables().reference(irClass);
    }

    public final WasmSymbol<WasmGlobal> referenceGlobalClassITable(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.wasmFragment.getGlobalClassITables().reference(irClass);
    }

    private final WasmSymbol<WasmTypeDeclaration> referenceNonNothingType(IrClassSymbol irClass, WasmCompiledModuleFragment.ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> from) {
        if (IrTypePredicatesKt.isNothing(IrTypesKt.getDefaultType(irClass))) {
            throw new IllegalArgumentException("Can't reference Nothing type".toString());
        }
        return from.reference(irClass);
    }

    public final WasmSymbol<WasmTypeDeclaration> referenceGcType(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return referenceNonNothingType(irClass, this.wasmFragment.getGcTypes());
    }

    public final WasmSymbol<WasmTypeDeclaration> referenceVTableGcType(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return referenceNonNothingType(irClass, this.wasmFragment.getVTableGcTypes());
    }

    public final WasmSymbol<WasmTypeDeclaration> referenceClassITableGcType(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return referenceNonNothingType(irClass, this.wasmFragment.getClassITableGcType());
    }

    public final void defineClassITableGcType(IrClassSymbol irClass, WasmTypeDeclaration wasmType) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(wasmType, "wasmType");
        this.wasmFragment.getClassITableGcType().define(irClass, wasmType);
    }

    public final boolean isAlreadyDefinedClassITableGcType(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.wasmFragment.getClassITableGcType().getDefined().keySet().contains(irClass);
    }

    public final WasmSymbol<Integer> referenceClassITableInterfaceSlot(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (IrTypePredicatesKt.isNothing(IrTypesKt.getDefaultType(irClass))) {
            throw new IllegalArgumentException("Can't reference Nothing type".toString());
        }
        return this.wasmFragment.getClassITableInterfaceSlot().reference(irClass);
    }

    public final void defineClassITableInterfaceSlot(IrClassSymbol irClass, int slot) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.wasmFragment.getClassITableInterfaceSlot().define(irClass, Integer.valueOf(slot));
    }

    public final WasmSymbol<WasmFunctionType> referenceFunctionType(IrFunctionSymbol irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        return this.wasmFragment.getFunctionTypes().reference(irFunction);
    }

    public final WasmSymbol<Integer> referenceTypeId(IrClassSymbol irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.wasmFragment.getTypeIds().reference(irClass);
    }

    public final WasmSymbol<Integer> getStructFieldRef(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return new WasmSymbol<>(Integer.valueOf(getClassMetadata(IrUtilsKt.getParentAsClass(field).getSymbol()).getFields().indexOf(field) + 2));
    }

    public final void addJsFun(String importName, String jsCode) {
        Intrinsics.checkNotNullParameter(importName, "importName");
        Intrinsics.checkNotNullParameter(jsCode, "jsCode");
        this.wasmFragment.getJsFuns().add(new WasmCompiledModuleFragment.JsCodeSnippet(importName, jsCode));
    }

    public final void addJsModuleImport(String module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.wasmFragment.getJsModuleImports().add(module);
    }
}
