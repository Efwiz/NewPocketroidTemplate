package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.bridges.FunctionHandle;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BridgesConstruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00000\u0013H\u0016J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\tR\u0014\u0010\n\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/IrBasedFunctionHandle;", "Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isAbstract", "", "()Z", "isDeclaration", "mayBeUsedAsSuperImplementation", "getMayBeUsedAsSuperImplementation", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "getOverridden", "", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrBasedFunctionHandle implements FunctionHandle {
    private final IrSimpleFunction function;
    private final boolean isAbstract;
    private final boolean isDeclaration;
    private final boolean mayBeUsedAsSuperImplementation;

    public static /* synthetic */ IrBasedFunctionHandle copy$default(IrBasedFunctionHandle irBasedFunctionHandle, IrSimpleFunction irSimpleFunction, int i, Object obj) {
        if ((i & 1) != 0) {
            irSimpleFunction = irBasedFunctionHandle.function;
        }
        return irBasedFunctionHandle.copy(irSimpleFunction);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrSimpleFunction getFunction() {
        return this.function;
    }

    public final IrBasedFunctionHandle copy(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new IrBasedFunctionHandle(function);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof IrBasedFunctionHandle) && Intrinsics.areEqual(this.function, ((IrBasedFunctionHandle) other).function);
    }

    public int hashCode() {
        return this.function.hashCode();
    }

    public String toString() {
        return "IrBasedFunctionHandle(function=" + this.function + ')';
    }

    public IrBasedFunctionHandle(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        this.isDeclaration = AdditionalIrUtilsKt.isReal(function) || IrUtilsKt.findInterfaceImplementation(function) != null;
        this.isAbstract = function.getModality() == Modality.ABSTRACT;
        this.mayBeUsedAsSuperImplementation = !IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(function));
    }

    public final IrSimpleFunction getFunction() {
        return this.function;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isDeclaration, reason: from getter */
    public boolean getIsDeclaration() {
        return this.isDeclaration;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isAbstract, reason: from getter */
    public boolean getIsAbstract() {
        return this.isAbstract;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public boolean getMayBeUsedAsSuperImplementation() {
        return this.mayBeUsedAsSuperImplementation;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public List<IrBasedFunctionHandle> getOverridden() {
        List<IrSimpleFunctionSymbol> overriddenSymbols = this.function.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(new IrBasedFunctionHandle(((IrSimpleFunctionSymbol) it.next()).getOwner()));
        }
        return arrayList;
    }
}
