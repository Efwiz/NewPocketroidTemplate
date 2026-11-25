package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;

/* JADX INFO: compiled from: ScopedTypeParametersResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH'¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "", "enterTypeParameterScope", "", "typeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveTypeParameterScope", "resolveScopedTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeParametersResolver {
    void enterTypeParameterScope(IrTypeParametersContainer typeParametersContainer);

    void leaveTypeParameterScope();

    @ObsoleteDescriptorBasedAPI
    IrTypeParameterSymbol resolveScopedTypeParameter(TypeParameterDescriptor typeParameterDescriptor);
}
