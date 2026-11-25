package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClassInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ClassMetadata;", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "superClass", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ClassMetadata;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "fields", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getFields", "()Ljava/util/List;", Namer.METADATA_SUPERTYPES, "getInterfaces", "getKlass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSuperClass", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ClassMetadata;", "virtualMethods", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/VirtualMethodMetadata;", "getVirtualMethods", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassMetadata {
    private final List<IrField> fields;
    private final List<IrClass> interfaces;
    private final IrClass klass;
    private final ClassMetadata superClass;
    private final List<VirtualMethodMetadata> virtualMethods;

    public ClassMetadata(IrClass klass, ClassMetadata classMetadata, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.klass = klass;
        this.superClass = classMetadata;
        List<IrField> list = classMetadata != null ? classMetadata.fields : null;
        List<IrField> listEmptyList = list == null ? CollectionsKt.emptyList() : list;
        List<IrDeclaration> declarations = klass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrField) {
                arrayList.add(obj);
            }
        }
        this.fields = CollectionsKt.plus((Collection) listEmptyList, (Iterable) arrayList);
        this.interfaces = ClassInfoKt.allSuperInterfaces(this.klass);
        Sequence<IrSimpleFunction> sequenceFilterVirtualFunctions = ClassInfoKt.filterVirtualFunctions(CollectionsKt.asSequence(this.klass.getDeclarations()));
        ArrayList arrayList2 = new ArrayList();
        for (IrSimpleFunction irSimpleFunction : sequenceFilterVirtualFunctions) {
            arrayList2.add(new VirtualMethodMetadata(irSimpleFunction, ClassInfoKt.wasmSignature(irSimpleFunction, irBuiltIns)));
        }
        ArrayList arrayList3 = arrayList2;
        ClassMetadata classMetadata2 = this.superClass;
        List<VirtualMethodMetadata> list2 = classMetadata2 != null ? classMetadata2.virtualMethods : null;
        List<VirtualMethodMetadata> list3 = list2;
        if (list3 != null && !list3.isEmpty()) {
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = arrayList3;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList5, 10)), 16));
            for (Object obj2 : arrayList5) {
                linkedHashMap.put(((VirtualMethodMetadata) obj2).getSignature(), obj2);
            }
            List<VirtualMethodMetadata> list4 = list2;
            for (VirtualMethodMetadata virtualMethodMetadata : list4) {
                ArrayList arrayList6 = arrayList4;
                VirtualMethodMetadata virtualMethodMetadata2 = (VirtualMethodMetadata) linkedHashMap.get(virtualMethodMetadata.getSignature());
                if (virtualMethodMetadata2 != null) {
                    virtualMethodMetadata = virtualMethodMetadata2;
                }
                arrayList6.add(virtualMethodMetadata);
            }
            ArrayList arrayList7 = arrayList4;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = list4.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(((VirtualMethodMetadata) it.next()).getSignature());
            }
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            for (Object obj3 : arrayList5) {
                if (!linkedHashSet2.contains(((VirtualMethodMetadata) obj3).getSignature())) {
                    arrayList7.add(obj3);
                }
            }
            arrayList3 = arrayList4;
        }
        this.virtualMethods = arrayList3;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (VirtualMethodMetadata virtualMethodMetadata3 : arrayList3) {
            WasmSignature signature = virtualMethodMetadata3.getSignature();
            Object obj4 = linkedHashMap2.get(signature);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap2.put(signature, obj4);
            }
            ((List) obj4).add(virtualMethodMetadata3.getFunction());
        }
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            WasmSignature wasmSignature = (WasmSignature) entry.getKey();
            List list5 = (List) entry.getValue();
            if (list5.size() > 1) {
                throw new IllegalStateException(("Class " + IrUtilsKt.getFqNameWhenAvailable(this.klass) + " has " + list5.size() + " methods with the same signature " + wasmSignature + "\n " + CollectionsKt.joinToString$default(list5, null, null, null, 0, null, new Function1<IrSimpleFunction, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassMetadata$funcList$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(IrSimpleFunction it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return " ---- " + IrUtilsKt.getFqNameWhenAvailable(it2) + " \n";
                    }
                }, 31, null)).toString());
            }
        }
    }

    public final IrClass getKlass() {
        return this.klass;
    }

    public final ClassMetadata getSuperClass() {
        return this.superClass;
    }

    public final List<IrField> getFields() {
        return this.fields;
    }

    public final List<IrClass> getInterfaces() {
        return this.interfaces;
    }

    public final List<VirtualMethodMetadata> getVirtualMethods() {
        return this.virtualMethods;
    }
}
