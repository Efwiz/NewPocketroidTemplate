package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002B\u0011\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrScriptSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindableSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrScriptSymbolImpl extends IrBindableSymbolBase<ScriptDescriptor, IrScript> implements IrScriptSymbol {
    /* JADX WARN: Multi-variable type inference failed */
    public IrScriptSymbolImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ IrScriptSymbolImpl(ScriptDescriptor scriptDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : scriptDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ ClassifierDescriptor getDescriptor() {
        return (ClassifierDescriptor) getDescriptor();
    }

    public IrScriptSymbolImpl(ScriptDescriptor scriptDescriptor) {
        super(scriptDescriptor);
    }
}
