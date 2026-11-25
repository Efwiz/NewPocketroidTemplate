package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0010\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0001*\u00020\u0003\u001a\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\b\u0012\u0004\u0012\u00020\n0\b\u001a\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u0003*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0003\u001a\u0012\u0010\u000e\u001a\u00020\u000f*\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0005¨\u0006\u0011"}, d2 = {"allFields", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "allSuperInterfaces", "filterVirtualFunctions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getSuperClass", "hasInterfaceSuperClass", "", "wasmSignature", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;", "irBuiltIns", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassInfoKt {
    public static final WasmSignature wasmSignature(IrSimpleFunction irSimpleFunction, IrBuiltIns irBuiltIns) {
        IrType type;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Name name2 = irSimpleFunction.getName();
        IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
        IrType irTypeEraseGenerics = (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null) ? null : TypeTranformerKt.eraseGenerics(type, irBuiltIns);
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(TypeTranformerKt.eraseGenerics(((IrValueParameter) it.next()).getType(), irBuiltIns));
        }
        return new WasmSignature(name2, irTypeEraseGenerics, arrayList, TypeTranformerKt.eraseGenerics(irSimpleFunction.getReturnType(), irBuiltIns), IrUtilsKt.isOverridableOrOverrides(irSimpleFunction));
    }

    private static final void allSuperInterfaces$allSuperInterfacesImpl(IrClass irClass, List<IrClass> list) {
        Iterator<IrType> it = irClass.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            allSuperInterfaces$allSuperInterfacesImpl((IrClass) owner, list);
        }
        if (IrUtilsKt.isInterface(irClass)) {
            list.add(irClass);
        }
    }

    public static final List<IrClass> allSuperInterfaces(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        ArrayList arrayList = new ArrayList();
        allSuperInterfaces$allSuperInterfacesImpl(irClass, arrayList);
        return arrayList;
    }

    public static final IrClass getSuperClass(IrClass irClass, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Object obj = null;
        if (Intrinsics.areEqual(irClass, builtIns.getAnyClass().getOwner())) {
            return null;
        }
        List<IrType> superTypes = irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = IrTypesKt.getClassifierOrFail((IrType) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            arrayList.add((IrClass) owner);
        }
        Iterator it2 = arrayList.iterator();
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it2.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it2.next();
            if (!IrUtilsKt.isInterface((IrClass) next)) {
                if (z) {
                    break;
                }
                z = true;
                obj2 = next;
            }
        }
        IrClass irClass2 = (IrClass) obj;
        return irClass2 == null ? builtIns.getAnyClass().getOwner() : irClass2;
    }

    public static final List<IrField> allFields(IrClass irClass, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        IrClass superClass = getSuperClass(irClass, builtIns);
        List<IrField> listAllFields = superClass != null ? allFields(superClass, builtIns) : null;
        if (listAllFields == null) {
            listAllFields = CollectionsKt.emptyList();
        }
        List<IrField> list = listAllFields;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrField) {
                arrayList.add(obj);
            }
        }
        return CollectionsKt.plus((Collection) list, (Iterable) arrayList);
    }

    public static final boolean hasInterfaceSuperClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Iterator<IrType> it = irClass.getSuperTypes().iterator();
        IrClass irClass2 = null;
        while (it.hasNext()) {
            IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            irClass2 = (IrClass) owner;
            if (IrUtilsKt.isInterface(irClass2)) {
                return true;
            }
        }
        if (irClass2 != null) {
            return hasInterfaceSuperClass(irClass2);
        }
        return false;
    }

    public static final Sequence<IrSimpleFunction> filterVirtualFunctions(Sequence<? extends IrDeclaration> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Sequence sequenceFilter = SequencesKt.filter(sequence, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassInfoKt$filterVirtualFunctions$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSimpleFunction);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.distinct(SequencesKt.filter(SequencesKt.map(SequencesKt.filter(sequenceFilter, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassInfoKt.filterVirtualFunctions.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getDispatchReceiverParameter() != null);
            }
        }), new Function1<IrSimpleFunction, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassInfoKt.filterVirtualFunctions.2
            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrResolveUtilsKt.getRealOverrideTarget(it);
            }
        }), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassInfoKt.filterVirtualFunctions.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrUtilsKt.isOverridableOrOverrides(it));
            }
        }));
    }
}
