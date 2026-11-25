package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\nHÆ\u0003JC\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiverType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "valueParametersType", "", "returnType", "isVirtual", "", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/ir/types/IrType;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrType;Z)V", "getExtensionReceiverType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getReturnType", "getValueParametersType", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WasmSignature {
    private final IrType extensionReceiverType;
    private final boolean isVirtual;
    private final Name name;
    private final IrType returnType;
    private final List<IrType> valueParametersType;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ WasmSignature copy$default(WasmSignature wasmSignature, Name name2, IrType irType, List list, IrType irType2, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            name2 = wasmSignature.name;
        }
        if ((i & 2) != 0) {
            irType = wasmSignature.extensionReceiverType;
        }
        IrType irType3 = irType;
        if ((i & 4) != 0) {
            list = wasmSignature.valueParametersType;
        }
        List list2 = list;
        if ((i & 8) != 0) {
            irType2 = wasmSignature.returnType;
        }
        IrType irType4 = irType2;
        if ((i & 16) != 0) {
            z = wasmSignature.isVirtual;
        }
        return wasmSignature.copy(name2, irType3, list2, irType4, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrType getExtensionReceiverType() {
        return this.extensionReceiverType;
    }

    public final List<IrType> component3() {
        return this.valueParametersType;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final IrType getReturnType() {
        return this.returnType;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsVirtual() {
        return this.isVirtual;
    }

    public final WasmSignature copy(Name name2, IrType extensionReceiverType, List<? extends IrType> valueParametersType, IrType returnType, boolean isVirtual) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(valueParametersType, "valueParametersType");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return new WasmSignature(name2, extensionReceiverType, valueParametersType, returnType, isVirtual);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WasmSignature)) {
            return false;
        }
        WasmSignature wasmSignature = (WasmSignature) other;
        return Intrinsics.areEqual(this.name, wasmSignature.name) && Intrinsics.areEqual(this.extensionReceiverType, wasmSignature.extensionReceiverType) && Intrinsics.areEqual(this.valueParametersType, wasmSignature.valueParametersType) && Intrinsics.areEqual(this.returnType, wasmSignature.returnType) && this.isVirtual == wasmSignature.isVirtual;
    }

    public int hashCode() {
        int iHashCode = this.name.hashCode() * 31;
        IrType irType = this.extensionReceiverType;
        return ((((((iHashCode + (irType == null ? 0 : irType.hashCode())) * 31) + this.valueParametersType.hashCode()) * 31) + this.returnType.hashCode()) * 31) + Boolean.hashCode(this.isVirtual);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WasmSignature(Name name2, IrType irType, List<? extends IrType> valueParametersType, IrType returnType, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(valueParametersType, "valueParametersType");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.name = name2;
        this.extensionReceiverType = irType;
        this.valueParametersType = valueParametersType;
        this.returnType = returnType;
        this.isVirtual = z;
    }

    public final Name getName() {
        return this.name;
    }

    public final IrType getExtensionReceiverType() {
        return this.extensionReceiverType;
    }

    public final List<IrType> getValueParametersType() {
        return this.valueParametersType;
    }

    public final IrType getReturnType() {
        return this.returnType;
    }

    public final boolean isVirtual() {
        return this.isVirtual;
    }

    public String toString() {
        String str;
        IrType irType = this.extensionReceiverType;
        if (irType == null || (str = "(er: " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null) + ") ") == null) {
            str = "";
        }
        return "[" + (this.isVirtual ? "" : "(non-virtual) ") + str + this.name + '(' + CollectionsKt.joinToString$default(this.valueParametersType, ", ", null, null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmSignature$toString$parameters$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return RenderIrElementKt.render$default(it, (DumpIrTreeOptions) null, 1, (Object) null);
            }
        }, 30, null) + ") -> " + RenderIrElementKt.render$default(this.returnType, (DumpIrTreeOptions) null, 1, (Object) null) + ']';
    }
}
