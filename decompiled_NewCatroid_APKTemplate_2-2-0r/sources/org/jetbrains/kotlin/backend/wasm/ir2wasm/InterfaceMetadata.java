package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: ClassInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/InterfaceMetadata;", "", "iFace", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getIFace", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "methods", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/VirtualMethodMetadata;", "getMethods", "()Ljava/util/List;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InterfaceMetadata {
    private final IrClass iFace;
    private final List<VirtualMethodMetadata> methods;

    public InterfaceMetadata(IrClass iFace, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(iFace, "iFace");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.iFace = iFace;
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(iFace.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.InterfaceMetadata$special$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSimpleFunction);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Sequence<IrSimpleFunction> sequenceFilter2 = SequencesKt.filter(sequenceFilter, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.InterfaceMetadata$methods$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it.getIsFakeOverride() || Intrinsics.areEqual(it.getVisibility(), DescriptorVisibilities.PRIVATE) || it.getModality() == Modality.FINAL) ? false : true);
            }
        });
        ArrayList arrayList = new ArrayList();
        for (IrSimpleFunction irSimpleFunction : sequenceFilter2) {
            arrayList.add(new VirtualMethodMetadata(irSimpleFunction, ClassInfoKt.wasmSignature(irSimpleFunction, irBuiltIns)));
        }
        this.methods = arrayList;
    }

    public final IrClass getIFace() {
        return this.iFace;
    }

    public final List<VirtualMethodMetadata> getMethods() {
        return this.methods;
    }
}
