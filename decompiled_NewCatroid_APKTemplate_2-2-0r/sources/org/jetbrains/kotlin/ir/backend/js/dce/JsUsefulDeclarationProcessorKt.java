package org.jetbrains.kotlin.ir.backend.js.dce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: JsUsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002¨\u0006\u0004"}, d2 = {"filterDescendantsOf", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "bases", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsUsefulDeclarationProcessorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<IrClass> filterDescendantsOf(Collection<? extends IrClass> collection, Collection<? extends IrClass> collection2) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        CollectionsKt.addAll(hashSet2, collection2);
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (filterDescendantsOf$overridesAnyBase(hashSet2, hashSet, (IrClass) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private static final boolean filterDescendantsOf$overridesAnyBase(HashSet<IrClass> hashSet, HashSet<IrClass> hashSet2, IrClass irClass) {
        IrClass owner;
        if (hashSet.contains(irClass)) {
            return true;
        }
        if (hashSet2.contains(irClass)) {
            return false;
        }
        hashSet2.add(irClass);
        Iterator<T> it = irClass.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
            IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
            if (irClassSymbol != null && (owner = irClassSymbol.getOwner()) != null && filterDescendantsOf$overridesAnyBase(hashSet, hashSet2, owner)) {
                hashSet.add(owner);
                return true;
            }
        }
        return false;
    }
}
