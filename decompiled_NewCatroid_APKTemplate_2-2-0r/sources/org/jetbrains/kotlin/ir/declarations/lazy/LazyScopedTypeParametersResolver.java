package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.TypeParametersResolver;

/* JADX INFO: compiled from: LazyScopedTypeParametersResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\b\u0010\u000b\u001a\u00020\tH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/LazyScopedTypeParametersResolver;", "Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;)V", "typeParameterScopes", "Ljava/util/ArrayDeque;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "enterTypeParameterScope", "", "typeParametersContainer", "leaveTypeParameterScope", "resolveScopedTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyScopedTypeParametersResolver implements TypeParametersResolver {
    private final ReferenceSymbolTable symbolTable;
    private final ArrayDeque<IrTypeParametersContainer> typeParameterScopes;

    public LazyScopedTypeParametersResolver(ReferenceSymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.symbolTable = symbolTable;
        this.typeParameterScopes = new ArrayDeque<>();
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    public void enterTypeParameterScope(IrTypeParametersContainer typeParametersContainer) {
        Intrinsics.checkNotNullParameter(typeParametersContainer, "typeParametersContainer");
        this.typeParameterScopes.addFirst(typeParametersContainer);
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    public void leaveTypeParameterScope() {
        this.typeParameterScopes.removeFirst();
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    @ObsoleteDescriptorBasedAPI
    public IrTypeParameterSymbol resolveScopedTypeParameter(TypeParameterDescriptor typeParameterDescriptor) {
        Object next;
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        IrTypeParametersContainer irTypeParametersContainer = (IrTypeParametersContainer) CollectionsKt.firstOrNull(this.typeParameterScopes);
        if (irTypeParametersContainer == null) {
            return null;
        }
        Iterator<T> it = irTypeParametersContainer.getTypeParameters().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrTypeParameter) next).getDescriptor(), typeParameterDescriptor));
        IrTypeParameter irTypeParameter = (IrTypeParameter) next;
        if (irTypeParameter != null) {
            return irTypeParameter.getSymbol();
        }
        return null;
    }
}
