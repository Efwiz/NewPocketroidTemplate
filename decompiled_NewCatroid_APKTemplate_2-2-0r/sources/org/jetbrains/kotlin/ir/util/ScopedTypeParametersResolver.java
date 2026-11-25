package org.jetbrains.kotlin.ir.util;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;

/* JADX INFO: compiled from: ScopedTypeParametersResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\tH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000e\u001a\u00020\u0006H\u0017R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/ScopedTypeParametersResolver;", "Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "()V", "typeParameterScopes", "Ljava/util/ArrayDeque;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "enterTypeParameterScope", "", "typeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveTypeParameterScope", "resolveScopedTypeParameter", "typeParameterDescriptor", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopedTypeParametersResolver implements TypeParametersResolver {
    private final ArrayDeque<Map<TypeParameterDescriptor, IrTypeParameterSymbol>> typeParameterScopes = new ArrayDeque<>();

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    public void enterTypeParameterScope(IrTypeParametersContainer typeParametersContainer) {
        Intrinsics.checkNotNullParameter(typeParametersContainer, "typeParametersContainer");
        ArrayDeque<Map<TypeParameterDescriptor, IrTypeParameterSymbol>> arrayDeque = this.typeParameterScopes;
        List<IrTypeParameter> typeParameters = typeParametersContainer.getTypeParameters();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
        for (IrTypeParameter irTypeParameter : typeParameters) {
            Pair pair = TuplesKt.to(irTypeParameter.getDescriptor(), irTypeParameter.getSymbol());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        arrayDeque.addFirst(linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    public void leaveTypeParameterScope() {
        this.typeParameterScopes.removeFirst();
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeParametersResolver
    @ObsoleteDescriptorBasedAPI
    public IrTypeParameterSymbol resolveScopedTypeParameter(TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        Iterator<Map<TypeParameterDescriptor, IrTypeParameterSymbol>> it = this.typeParameterScopes.iterator();
        while (it.hasNext()) {
            IrTypeParameterSymbol irTypeParameterSymbol = it.next().get(typeParameterDescriptor);
            if (irTypeParameterSymbol != null) {
                return irTypeParameterSymbol;
            }
        }
        return null;
    }
}
