package org.jetbrains.kotlin.fir.declarations;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.PersistentImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ImplicitReceiverUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B=\b\u0002\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0010\u0010\b\u001a\f\u0012\u0004\u0012\u00020\t0\u0004j\u0002`\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\fJ\u001c\u0010\u0013\u001a\u00020\u00002\u0014\u0010\u0014\u001a\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00160\u0015j\u0002`\u0017J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\tJ\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00002\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015H\u0002J\u0010\u0010\u001f\u001a\u00020\u00002\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u001a\u0010 \u001a\u00020\u00002\b\u0010!\u001a\u0004\u0018\u00010\u001c2\b\u0010\"\u001a\u0004\u0018\u00010\u001cJ\u0014\u0010#\u001a\u00020\u00002\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015J(\u0010%\u001a\u00020\u00002\b\u0010&\u001a\u0004\u0018\u00010'2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010'J\u0006\u0010+\u001a\u00020\u0000J\u000e\u0010,\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\tR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\b\u001a\f\u0012\u0004\u0012\u00020\t0\u0004j\u0002`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "", "()V", "towerDataElements", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "localScopes", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirLocalScopes;", "nonLocalTowerDataElements", "(Lkotlinx/collections/immutable/PersistentList;Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;Lkotlinx/collections/immutable/PersistentList;Lkotlinx/collections/immutable/PersistentList;)V", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "getLocalScopes", "()Lkotlinx/collections/immutable/PersistentList;", "getNonLocalTowerDataElements", "getTowerDataElements", "addContextReceiverGroup", "contextReceiverGroup", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "addLocalScope", "localScope", "addNonLocalScope", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "addNonLocalScopeElements", PathTree.NAME_elements, "addNonLocalScopeIfNotNull", "addNonLocalScopesIfNotNull", "scope1", "scope2", "addNonLocalTowerDataElements", "newElements", "addReceiver", "name", "Lorg/jetbrains/kotlin/name/Name;", "implicitReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "additionalLabName", "createSnapshot", "setLastLocalScope", "newLastScope", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTowerDataContext {
    private final PersistentImplicitReceiverStack implicitReceiverStack;
    private final PersistentList<FirLocalScope> localScopes;
    private final PersistentList<FirTowerDataElement> nonLocalTowerDataElements;
    private final PersistentList<FirTowerDataElement> towerDataElements;

    private FirTowerDataContext(PersistentList<FirTowerDataElement> persistentList, PersistentImplicitReceiverStack persistentImplicitReceiverStack, PersistentList<FirLocalScope> persistentList2, PersistentList<FirTowerDataElement> persistentList3) {
        this.towerDataElements = persistentList;
        this.implicitReceiverStack = persistentImplicitReceiverStack;
        this.localScopes = persistentList2;
        this.nonLocalTowerDataElements = persistentList3;
    }

    public final PersistentList<FirTowerDataElement> getTowerDataElements() {
        return this.towerDataElements;
    }

    public final PersistentImplicitReceiverStack getImplicitReceiverStack() {
        return this.implicitReceiverStack;
    }

    public final PersistentList<FirLocalScope> getLocalScopes() {
        return this.localScopes;
    }

    public final PersistentList<FirTowerDataElement> getNonLocalTowerDataElements() {
        return this.nonLocalTowerDataElements;
    }

    public FirTowerDataContext() {
        this(ExtensionsKt.persistentListOf(), new PersistentImplicitReceiverStack(), ExtensionsKt.persistentListOf(), ExtensionsKt.persistentListOf());
    }

    public final FirTowerDataContext setLastLocalScope(FirLocalScope newLastScope) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(newLastScope, "newLastScope");
        FirLocalScope firLocalScope = (FirLocalScope) CollectionsKt.last((List) this.localScopes);
        PersistentList<FirTowerDataElement> persistentList = this.towerDataElements;
        ListIterator<FirTowerDataElement> listIterator = persistentList.listIterator(persistentList.size());
        while (listIterator.hasPrevious()) {
            if (listIterator.previous().getScope() == firLocalScope) {
                iNextIndex = listIterator.nextIndex();
                PersistentList<FirTowerDataElement> persistentList2 = this.towerDataElements.set(iNextIndex, ImplicitReceiverUtilsKt.asTowerDataElement(newLastScope, true));
                PersistentImplicitReceiverStack persistentImplicitReceiverStack = this.implicitReceiverStack;
                PersistentList<FirLocalScope> persistentList3 = this.localScopes;
                return new FirTowerDataContext(persistentList2, persistentImplicitReceiverStack, persistentList3.set(CollectionsKt.getLastIndex(persistentList3), newLastScope), this.nonLocalTowerDataElements);
            }
        }
        iNextIndex = -1;
        PersistentList<FirTowerDataElement> persistentList4 = this.towerDataElements.set(iNextIndex, ImplicitReceiverUtilsKt.asTowerDataElement(newLastScope, true));
        PersistentImplicitReceiverStack persistentImplicitReceiverStack2 = this.implicitReceiverStack;
        PersistentList<FirLocalScope> persistentList5 = this.localScopes;
        return new FirTowerDataContext(persistentList4, persistentImplicitReceiverStack2, persistentList5.set(CollectionsKt.getLastIndex(persistentList5), newLastScope), this.nonLocalTowerDataElements);
    }

    public final FirTowerDataContext addNonLocalTowerDataElements(List<FirTowerDataElement> newElements) {
        Intrinsics.checkNotNullParameter(newElements, "newElements");
        List<FirTowerDataElement> list = newElements;
        PersistentList<FirTowerDataElement> persistentListAddAll = this.towerDataElements.addAll((Collection<? extends FirTowerDataElement>) list);
        PersistentImplicitReceiverStack persistentImplicitReceiverStack = this.implicitReceiverStack;
        List<FirTowerDataElement> list2 = newElements;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            ImplicitReceiverValue<?> implicitReceiver = ((FirTowerDataElement) it.next()).getImplicitReceiver();
            if (implicitReceiver != null) {
                arrayList.add(implicitReceiver);
            }
        }
        PersistentImplicitReceiverStack persistentImplicitReceiverStackAddAll = persistentImplicitReceiverStack.addAll(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            List<ContextReceiverValue<?>> contextReceiverGroup = ((FirTowerDataElement) it2.next()).getContextReceiverGroup();
            if (contextReceiverGroup == null) {
                contextReceiverGroup = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList2, contextReceiverGroup);
        }
        return new FirTowerDataContext(persistentListAddAll, persistentImplicitReceiverStackAddAll.addAllContextReceivers(arrayList2), this.localScopes, this.nonLocalTowerDataElements.addAll((Collection<? extends FirTowerDataElement>) list));
    }

    public final FirTowerDataContext addLocalScope(FirLocalScope localScope) {
        Intrinsics.checkNotNullParameter(localScope, "localScope");
        return new FirTowerDataContext(this.towerDataElements.add(ImplicitReceiverUtilsKt.asTowerDataElement(localScope, true)), this.implicitReceiverStack, this.localScopes.add(localScope), this.nonLocalTowerDataElements);
    }

    public static /* synthetic */ FirTowerDataContext addReceiver$default(FirTowerDataContext firTowerDataContext, Name name2, ImplicitReceiverValue implicitReceiverValue, Name name3, int i, Object obj) {
        if ((i & 4) != 0) {
            name3 = null;
        }
        return firTowerDataContext.addReceiver(name2, implicitReceiverValue, name3);
    }

    public final FirTowerDataContext addReceiver(Name name2, ImplicitReceiverValue<?> implicitReceiverValue, Name additionalLabName) {
        Intrinsics.checkNotNullParameter(implicitReceiverValue, "implicitReceiverValue");
        FirTowerDataElement firTowerDataElementAsTowerDataElement = ImplicitReceiverUtilsKt.asTowerDataElement(implicitReceiverValue);
        return new FirTowerDataContext(this.towerDataElements.add(firTowerDataElementAsTowerDataElement), this.implicitReceiverStack.add(name2, implicitReceiverValue, additionalLabName), this.localScopes, this.nonLocalTowerDataElements.add(firTowerDataElementAsTowerDataElement));
    }

    public final FirTowerDataContext addContextReceiverGroup(List<? extends ContextReceiverValue<?>> contextReceiverGroup) {
        Intrinsics.checkNotNullParameter(contextReceiverGroup, "contextReceiverGroup");
        if (contextReceiverGroup.isEmpty()) {
            return this;
        }
        FirTowerDataElement firTowerDataElementAsTowerDataElement = ImplicitReceiverUtilsKt.asTowerDataElement(contextReceiverGroup);
        PersistentList<FirTowerDataElement> persistentListAdd = this.towerDataElements.add(firTowerDataElementAsTowerDataElement);
        PersistentImplicitReceiverStack persistentImplicitReceiverStackAddContextReceiver = this.implicitReceiverStack;
        Iterator<T> it = contextReceiverGroup.iterator();
        while (it.hasNext()) {
            persistentImplicitReceiverStackAddContextReceiver = persistentImplicitReceiverStackAddContextReceiver.addContextReceiver((ContextReceiverValue) it.next());
        }
        return new FirTowerDataContext(persistentListAdd, persistentImplicitReceiverStackAddContextReceiver, this.localScopes, this.nonLocalTowerDataElements.add(firTowerDataElementAsTowerDataElement));
    }

    public final FirTowerDataContext addNonLocalScopeIfNotNull(FirScope scope) {
        return scope == null ? this : addNonLocalScope(scope);
    }

    public final FirTowerDataContext addNonLocalScopesIfNotNull(FirScope scope1, FirScope scope2) {
        if (scope1 != null) {
            return scope2 != null ? addNonLocalScopeElements(CollectionsKt.listOf((Object[]) new FirTowerDataElement[]{ImplicitReceiverUtilsKt.asTowerDataElement(scope1, false), ImplicitReceiverUtilsKt.asTowerDataElement(scope2, false)})) : addNonLocalScope(scope1);
        }
        return scope2 != null ? addNonLocalScope(scope2) : this;
    }

    public final FirTowerDataContext addNonLocalScope(FirScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        FirTowerDataElement firTowerDataElementAsTowerDataElement = ImplicitReceiverUtilsKt.asTowerDataElement(scope, false);
        return new FirTowerDataContext(this.towerDataElements.add(firTowerDataElementAsTowerDataElement), this.implicitReceiverStack, this.localScopes, this.nonLocalTowerDataElements.add(firTowerDataElementAsTowerDataElement));
    }

    private final FirTowerDataContext addNonLocalScopeElements(List<FirTowerDataElement> elements) {
        List<FirTowerDataElement> list = elements;
        return new FirTowerDataContext(this.towerDataElements.addAll((Collection<? extends FirTowerDataElement>) list), this.implicitReceiverStack, this.localScopes, this.nonLocalTowerDataElements.addAll((Collection<? extends FirTowerDataElement>) list));
    }

    public final FirTowerDataContext createSnapshot() {
        PersistentList<FirTowerDataElement> persistentList = this.towerDataElements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(persistentList, 10));
        Iterator<FirTowerDataElement> it = persistentList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().createSnapshot());
        }
        PersistentList persistentList2 = ExtensionsKt.toPersistentList(arrayList);
        PersistentImplicitReceiverStack persistentImplicitReceiverStackCreateSnapshot = this.implicitReceiverStack.createSnapshot();
        PersistentList persistentList3 = ExtensionsKt.toPersistentList(this.localScopes);
        PersistentList<FirTowerDataElement> persistentList4 = this.nonLocalTowerDataElements;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(persistentList4, 10));
        Iterator<FirTowerDataElement> it2 = persistentList4.iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().createSnapshot());
        }
        return new FirTowerDataContext(persistentList2, persistentImplicitReceiverStackCreateSnapshot, persistentList3, ExtensionsKt.toPersistentList(arrayList2));
    }
}
