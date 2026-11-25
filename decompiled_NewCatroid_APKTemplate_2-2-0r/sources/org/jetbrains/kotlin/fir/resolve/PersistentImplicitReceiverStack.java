package org.jetbrains.kotlin.fir.resolve;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.util.PersistentSetMultimap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: PersistentImplicitReceiverStack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0004BW\b\u0002\u0012\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0006\u0012\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\b\u0012\u0016\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\r0\u000b\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006¢\u0006\u0002\u0010\u0010J(\u0010\u0014\u001a\u00020\u00002\b\u0010\u0015\u001a\u0004\u0018\u00010\t2\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\tJ\u0018\u0010\u0018\u001a\u00020\u00002\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u001aJ\u0018\u0010\u001b\u001a\u00020\u00002\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001c0\u001aJ\u0012\u0010\u001d\u001a\u00020\u00002\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u001cJ\u0006\u0010\u001e\u001a\u00020\u0000J\u0019\u0010\u001f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010 H\u0096\u0002J\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\rJ\u0019\u0010#\u001a\u0004\u0018\u00010\r2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\f¢\u0006\u0002\u0010%J\u0013\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030'H\u0096\u0002J\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\"\u0010(\u001a\u0004\u0018\u00010)2\u0016\u0010*\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0003\u0012\u0004\u0012\u00020,0+H\u0016J\u0012\u0010-\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u001aH\u0016J\u0016\u0010.\u001a\u00020/2\u0006\u0010\"\u001a\u00020\r2\u0006\u00100\u001a\u00020\u000fJB\u00101\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\b*\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\t2\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002R\u001e\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "()V", "stack", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "receiversPerLabel", "Lorg/jetbrains/kotlin/fir/util/PersistentSetMultimap;", "Lorg/jetbrains/kotlin/name/Name;", "indexesPerSymbol", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "originalTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lkotlinx/collections/immutable/PersistentList;Lorg/jetbrains/kotlin/fir/util/PersistentSetMultimap;Lkotlinx/collections/immutable/PersistentMap;Lkotlinx/collections/immutable/PersistentList;)V", "size", "getSize", "()I", ProductAction.ACTION_ADD, "name", "value", "aliasLabel", "addAll", "receivers", "", "addAllContextReceivers", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "addContextReceiver", "createSnapshot", "get", "", "getOriginalType", "index", "getReceiverIndex", NativeSymbol.TYPE_NAME, "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Ljava/lang/Integer;", "iterator", "", "lastDispatchReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitDispatchReceiverValue;", "lookupCondition", "Lkotlin/Function1;", "", "receiversAsReversed", "replaceReceiverType", "", ModuleXmlParser.TYPE, "putIfNameIsNotNull", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PersistentImplicitReceiverStack extends ImplicitReceiverStack implements Iterable<ImplicitReceiverValue<?>>, KMappedMarker {
    private final PersistentMap<FirBasedSymbol<?>, Integer> indexesPerSymbol;
    private final PersistentList<ConeKotlinType> originalTypes;
    private final PersistentSetMultimap<Name, ImplicitReceiverValue<?>> receiversPerLabel;
    private final PersistentList<ImplicitReceiverValue<?>> stack;

    /* JADX WARN: Multi-variable type inference failed */
    private PersistentImplicitReceiverStack(PersistentList<? extends ImplicitReceiverValue<?>> persistentList, PersistentSetMultimap<Name, ImplicitReceiverValue<?>> persistentSetMultimap, PersistentMap<FirBasedSymbol<?>, Integer> persistentMap, PersistentList<? extends ConeKotlinType> persistentList2) {
        this.stack = persistentList;
        this.receiversPerLabel = persistentSetMultimap;
        this.indexesPerSymbol = persistentMap;
        this.originalTypes = persistentList2;
    }

    public final int getSize() {
        return this.stack.size();
    }

    public PersistentImplicitReceiverStack() {
        this(ExtensionsKt.persistentListOf(), new PersistentSetMultimap(), ExtensionsKt.persistentMapOf(new Pair[0]), ExtensionsKt.persistentListOf());
    }

    public final PersistentImplicitReceiverStack addAll(List<? extends ImplicitReceiverValue<?>> receivers) {
        Intrinsics.checkNotNullParameter(receivers, "receivers");
        Iterator<T> it = receivers.iterator();
        PersistentImplicitReceiverStack persistentImplicitReceiverStackAdd$default = this;
        while (it.hasNext()) {
            persistentImplicitReceiverStackAdd$default = add$default(persistentImplicitReceiverStackAdd$default, null, (ImplicitReceiverValue) it.next(), null, 4, null);
        }
        return persistentImplicitReceiverStackAdd$default;
    }

    public final PersistentImplicitReceiverStack addAllContextReceivers(List<? extends ContextReceiverValue<?>> receivers) {
        Intrinsics.checkNotNullParameter(receivers, "receivers");
        Iterator<T> it = receivers.iterator();
        PersistentImplicitReceiverStack persistentImplicitReceiverStackAddContextReceiver = this;
        while (it.hasNext()) {
            persistentImplicitReceiverStackAddContextReceiver = persistentImplicitReceiverStackAddContextReceiver.addContextReceiver((ContextReceiverValue) it.next());
        }
        return persistentImplicitReceiverStackAddContextReceiver;
    }

    public static /* synthetic */ PersistentImplicitReceiverStack add$default(PersistentImplicitReceiverStack persistentImplicitReceiverStack, Name name2, ImplicitReceiverValue implicitReceiverValue, Name name3, int i, Object obj) {
        if ((i & 4) != 0) {
            name3 = null;
        }
        return persistentImplicitReceiverStack.add(name2, implicitReceiverValue, name3);
    }

    public final PersistentImplicitReceiverStack add(Name name2, ImplicitReceiverValue<?> value, Name aliasLabel) {
        Intrinsics.checkNotNullParameter(value, "value");
        PersistentList<ImplicitReceiverValue<?>> persistentListAdd = this.stack.add(value);
        return new PersistentImplicitReceiverStack(persistentListAdd, putIfNameIsNotNull(putIfNameIsNotNull(this.receiversPerLabel, name2, value), aliasLabel, value), this.indexesPerSymbol.put(value.getBoundSymbol(), Integer.valueOf(persistentListAdd.size() - 1)), this.originalTypes.add(value.getOriginalType()));
    }

    private final PersistentSetMultimap<Name, ImplicitReceiverValue<?>> putIfNameIsNotNull(PersistentSetMultimap<Name, ImplicitReceiverValue<?>> persistentSetMultimap, Name name2, ImplicitReceiverValue<?> implicitReceiverValue) {
        return name2 != null ? persistentSetMultimap.put(name2, implicitReceiverValue) : persistentSetMultimap;
    }

    public final PersistentImplicitReceiverStack addContextReceiver(ContextReceiverValue<?> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Name labelName = value.getLabelName();
        if (labelName == null) {
            return this;
        }
        return new PersistentImplicitReceiverStack(this.stack, this.receiversPerLabel.put(labelName, value), this.indexesPerSymbol, this.originalTypes);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack
    public ImplicitReceiverValue<?> get(String name2) {
        if (name2 == null) {
            return (ImplicitReceiverValue) CollectionsKt.lastOrNull((List) this.stack);
        }
        PersistentSetMultimap<Name, ImplicitReceiverValue<?>> persistentSetMultimap = this.receiversPerLabel;
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return (ImplicitReceiverValue) CollectionsKt.lastOrNull(persistentSetMultimap.get(nameIdentifier));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack
    public ImplicitDispatchReceiverValue lastDispatchReceiver() {
        PersistentList<ImplicitReceiverValue<?>> persistentList = this.stack;
        ArrayList arrayList = new ArrayList();
        for (ImplicitReceiverValue<?> implicitReceiverValue : persistentList) {
            if (implicitReceiverValue instanceof ImplicitDispatchReceiverValue) {
                arrayList.add(implicitReceiverValue);
            }
        }
        return (ImplicitDispatchReceiverValue) CollectionsKt.lastOrNull((List) arrayList);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack
    public ImplicitDispatchReceiverValue lastDispatchReceiver(Function1<? super ImplicitReceiverValue<?>, Boolean> lookupCondition) {
        Object objPrevious;
        Intrinsics.checkNotNullParameter(lookupCondition, "lookupCondition");
        PersistentList<ImplicitReceiverValue<?>> persistentList = this.stack;
        ArrayList arrayList = new ArrayList();
        for (ImplicitReceiverValue<?> implicitReceiverValue : persistentList) {
            if (implicitReceiverValue instanceof ImplicitDispatchReceiverValue) {
                arrayList.add(implicitReceiverValue);
            }
        }
        ArrayList arrayList2 = arrayList;
        ListIterator listIterator = arrayList2.listIterator(arrayList2.size());
        while (listIterator.hasPrevious()) {
            objPrevious = listIterator.previous();
            if (lookupCondition.invoke(objPrevious).booleanValue()) {
                return (ImplicitDispatchReceiverValue) objPrevious;
            }
        }
        objPrevious = null;
        return (ImplicitDispatchReceiverValue) objPrevious;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack
    public List<ImplicitReceiverValue<?>> receiversAsReversed() {
        return CollectionsKt.asReversed(this.stack);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack, java.lang.Iterable
    public Iterator<ImplicitReceiverValue<?>> iterator() {
        return this.stack.iterator();
    }

    public final Integer getReceiverIndex(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (Integer) this.indexesPerSymbol.get(symbol);
    }

    public final ConeKotlinType getOriginalType(int index) {
        return (ConeKotlinType) this.originalTypes.get(index);
    }

    public final void replaceReceiverType(int index, ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (index >= 0) {
            this.stack.size();
        }
        ((ImplicitReceiverValue) this.stack.get(index)).updateTypeFromSmartcast(type);
    }

    public final PersistentImplicitReceiverStack createSnapshot() {
        PersistentList<ImplicitReceiverValue<?>> persistentList = this.stack;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(persistentList, 10));
        Iterator<ImplicitReceiverValue<?>> it = persistentList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().createSnapshot());
        }
        return new PersistentImplicitReceiverStack(ExtensionsKt.toPersistentList(arrayList), this.receiversPerLabel, this.indexesPerSymbol, this.originalTypes);
    }
}
