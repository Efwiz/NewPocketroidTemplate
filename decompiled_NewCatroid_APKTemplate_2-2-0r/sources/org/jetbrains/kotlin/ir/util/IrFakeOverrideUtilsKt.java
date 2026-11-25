package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: IrFakeOverrideUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\\\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\b\b\u0000\u0010\u000e*\u00020\u000f\"\u000e\b\u0001\u0010\r*\b\u0012\u0004\u0012\u0002H\u000e0\u0010*\b\u0012\u0004\u0012\u0002H\r0\u00112\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u00132\u0014\b\u0002\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u0013\u001a\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00150\f*\b\u0012\u0004\u0012\u00020\u00150\u0011\u001a[\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\b\b\u0000\u0010\u000e*\u00020\u000f\"\u000e\b\u0001\u0010\r*\b\u0012\u0004\u0012\u0002H\u000e0\u0010*\u0002H\r2\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u00132\u0014\b\u0002\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u0013¢\u0006\u0002\u0010\u0017\u001aK\u0010\u0018\u001a\u0004\u0018\u0001H\r\"\b\b\u0000\u0010\u000e*\u00020\u000f\"\u000e\b\u0001\u0010\r*\b\u0012\u0004\u0012\u0002H\u000e0\u0010*\u0002H\r2\b\b\u0002\u0010\u0019\u001a\u00020\u00012\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u0013¢\u0006\u0002\u0010\u001a\u001aK\u0010\u001b\u001a\u0004\u0018\u0001H\r\"\b\b\u0000\u0010\u000e*\u00020\u000f\"\u000e\b\u0001\u0010\r*\b\u0012\u0004\u0012\u0002H\u000e0\u0010*\u0002H\r2\b\b\u0002\u0010\u0019\u001a\u00020\u00012\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00010\u0013¢\u0006\u0002\u0010\u001a\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\u0005\u001a\u00020\t*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n¨\u0006\u001c"}, d2 = {"isFakeOverride", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isReal", "target", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getTarget", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "collectAndFilterRealOverrides", "", ExifInterface.GPS_DIRECTION_TRUE, ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "", "toSkip", "Lkotlin/Function1;", "filter", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "collectRealOverrides", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Set;", "resolveFakeOverride", "allowAbstract", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;ZLkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "resolveFakeOverrideOrNull", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrFakeOverrideUtilsKt {
    public static final boolean isReal(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return !isFakeOverride(irDeclaration);
    }

    public static final boolean isFakeOverride(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrSimpleFunction) {
            return ((IrSimpleFunction) irDeclaration).getIsFakeOverride();
        }
        if (irDeclaration instanceof IrProperty) {
            return ((IrProperty) irDeclaration).getIsFakeOverride();
        }
        return false;
    }

    public static final IrSimpleFunction getTarget(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        if (irSimpleFunction.getModality() == Modality.ABSTRACT) {
            return irSimpleFunction;
        }
        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) resolveFakeOverride$default(irSimpleFunction, false, null, 3, null);
        if (irSimpleFunction2 != null) {
            return irSimpleFunction2;
        }
        throw new IllegalStateException(("Could not resolveFakeOverride() for " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final IrFunction getTarget(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            return getTarget((IrSimpleFunction) irFunction);
        }
        if (irFunction instanceof IrConstructor) {
            return irFunction;
        }
        throw new IllegalStateException(irFunction.toString());
    }

    public static /* synthetic */ Set collectRealOverrides$default(IrOverridableDeclaration irOverridableDeclaration, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectRealOverrides.1
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        if ((i & 2) != 0) {
            function2 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectRealOverrides.2
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        return collectRealOverrides(irOverridableDeclaration, function1, function2);
    }

    public static final <S extends IrSymbol, T extends IrOverridableDeclaration<S>> Set<T> collectRealOverrides(T t, Function1<? super T, Boolean> toSkip, Function1<? super T, Boolean> filter) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(toSkip, "toSkip");
        Intrinsics.checkNotNullParameter(filter, "filter");
        if (isReal(t) && !toSkip.invoke(t).booleanValue()) {
            return SetsKt.setOf(t);
        }
        List<S> overriddenSymbols = t.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectRealOverrides$lambda$0");
            arrayList.add((IrOverridableDeclaration) owner);
        }
        return collectAndFilterRealOverrides(arrayList, toSkip, filter);
    }

    public static /* synthetic */ Set collectAndFilterRealOverrides$default(Collection collection, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectAndFilterRealOverrides.1
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        if ((i & 2) != 0) {
            function2 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectAndFilterRealOverrides.2
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        return collectAndFilterRealOverrides(collection, function1, function2);
    }

    public static final <S extends IrSymbol, T extends IrOverridableDeclaration<S>> Set<T> collectAndFilterRealOverrides(Collection<? extends T> collection, Function1<? super T, Boolean> toSkip, Function1<? super T, Boolean> filter) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(toSkip, "toSkip");
        Intrinsics.checkNotNullParameter(filter, "filter");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            collectAndFilterRealOverrides$collectRealOverrides(linkedHashSet, filter, toSkip, linkedHashMap, (IrOverridableDeclaration) it.next());
        }
        linkedHashSet.clear();
        Iterator it2 = MapsKt.toList(linkedHashMap).iterator();
        while (it2.hasNext()) {
            collectAndFilterRealOverrides$excludeRepeated(linkedHashSet, linkedHashMap, (IrOverridableDeclaration) ((Pair) it2.next()).getSecond());
        }
        return CollectionsKt.toSet(linkedHashMap.values());
    }

    private static final <T extends IrOverridableDeclaration<S>, S extends IrSymbol> Object collectAndFilterRealOverrides$toKey(T t) {
        IdSignature signature = t.getSymbol().getSignature();
        return signature == null ? t : signature;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T extends IrOverridableDeclaration<S>, S extends IrSymbol> void collectAndFilterRealOverrides$collectRealOverrides(Set<T> set, Function1<? super T, Boolean> function1, Function1<? super T, Boolean> function2, Map<Object, T> map, T t) {
        if (!set.add(t) || function1.invoke(t).booleanValue()) {
            return;
        }
        if (isReal(t) && !function2.invoke(t).booleanValue()) {
            map.put(collectAndFilterRealOverrides$toKey(t), t);
            return;
        }
        Iterator it = t.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectAndFilterRealOverrides$collectRealOverrides$lambda$1");
            collectAndFilterRealOverrides$collectRealOverrides(set, function1, function2, map, (IrOverridableDeclaration) owner);
        }
    }

    private static final <T extends IrOverridableDeclaration<S>, S extends IrSymbol> void collectAndFilterRealOverrides$excludeRepeated(Set<T> set, Map<Object, T> map, T t) {
        if (set.add(t)) {
            Iterator<T> it = t.getOverriddenSymbols().iterator();
            while (it.hasNext()) {
                IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.collectAndFilterRealOverrides$excludeRepeated$lambda$3");
                IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) owner;
                map.remove(collectAndFilterRealOverrides$toKey(irOverridableDeclaration));
                collectAndFilterRealOverrides$excludeRepeated(set, map, irOverridableDeclaration);
            }
        }
    }

    public static final Set<IrOverridableMember> collectAndFilterRealOverrides(Collection<? extends IrOverridableMember> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Collection<? extends IrOverridableMember> collection2 = collection;
        Collection<? extends IrOverridableMember> collection3 = collection2;
        if (!collection3.isEmpty()) {
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                if (!(((IrOverridableMember) it.next()) instanceof IrSimpleFunction)) {
                    if (!collection3.isEmpty()) {
                        Iterator<T> it2 = collection2.iterator();
                        while (it2.hasNext()) {
                            if (!(((IrOverridableMember) it2.next()) instanceof IrProperty)) {
                                StringBuilder sb = new StringBuilder("all members should be of the same kind, got ");
                                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
                                Iterator<T> it3 = collection2.iterator();
                                while (it3.hasNext()) {
                                    arrayList.add(RenderIrElementKt.render$default((IrOverridableMember) it3.next(), (DumpIrTreeOptions) null, 1, (Object) null));
                                }
                                throw new IllegalStateException(sb.append(arrayList).toString().toString());
                            }
                        }
                    }
                    return collectAndFilterRealOverrides$default(collection, null, null, 3, null);
                }
            }
        }
        return collectAndFilterRealOverrides$default(collection, null, null, 3, null);
    }

    public static /* synthetic */ IrOverridableDeclaration resolveFakeOverride$default(IrOverridableDeclaration irOverridableDeclaration, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            function1 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.resolveFakeOverride.1
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        return resolveFakeOverride(irOverridableDeclaration, z, function1);
    }

    public static final <S extends IrSymbol, T extends IrOverridableDeclaration<S>> T resolveFakeOverride(T t, boolean z, Function1<? super T, Boolean> toSkip) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(toSkip, "toSkip");
        T t2 = (T) resolveFakeOverrideOrNull(t, z, toSkip);
        if (z && t2 == null) {
            throw new IllegalStateException(("No real overrides for " + RenderIrElementKt.render$default(t, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return t2;
    }

    public static /* synthetic */ IrOverridableDeclaration resolveFakeOverrideOrNull$default(IrOverridableDeclaration irOverridableDeclaration, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            function1 = new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.resolveFakeOverrideOrNull.1
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrOverridableDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return false;
                }
            };
        }
        return resolveFakeOverrideOrNull(irOverridableDeclaration, z, function1);
    }

    public static final <S extends IrSymbol, T extends IrOverridableDeclaration<S>> T resolveFakeOverrideOrNull(T t, boolean z, Function1<? super T, Boolean> toSkip) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(toSkip, "toSkip");
        if (!t.getIsFakeOverride() && !toSkip.invoke(t).booleanValue()) {
            return t;
        }
        Object obj = null;
        if (z) {
            return (T) CollectionsKt.firstOrNull(collectRealOverrides$default(t, toSkip, null, 2, null));
        }
        Set setCollectRealOverrides = collectRealOverrides(t, toSkip, new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt.resolveFakeOverrideOrNull.2
            /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrOverridableDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getModality() == Modality.ABSTRACT);
            }
        });
        Iterator it = setCollectRealOverrides.iterator();
        boolean z2 = false;
        Object obj2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z2) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it.next();
            IrDeclarationParent parent = ((IrOverridableDeclaration) next).getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (!(irClass != null && IrUtilsKt.isInterface(irClass))) {
                if (z2) {
                    break;
                }
                obj2 = next;
                z2 = true;
            }
        }
        T t2 = (T) obj;
        return t2 == null ? (T) CollectionsKt.firstOrNull(setCollectRealOverrides) : t2;
    }
}
