package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirTowerResolveTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R5\u0010\u0007\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\bj\u0002`\u000b0\t0\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR+\u0010\u0010\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\t0\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000f\u001a\u0004\b\u0012\u0010\rR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR'\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\t0\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000f\u001a\u0004\b\u0019\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "towerDataContext", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;)V", "contextReceiverGroups", "", "Lkotlin/collections/IndexedValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "getContextReceiverGroups", "()Ljava/util/List;", "contextReceiverGroups$delegate", "Lkotlin/Lazy;", "implicitReceivers", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "getImplicitReceivers", "implicitReceivers$delegate", "nonLocalTowerDataElements", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "getNonLocalTowerDataElements", "reversedFilteredLocalScopes", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "getReversedFilteredLocalScopes", "reversedFilteredLocalScopes$delegate", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerDataElementsForName {

    /* JADX INFO: renamed from: contextReceiverGroups$delegate, reason: from kotlin metadata */
    private final Lazy contextReceiverGroups;

    /* JADX INFO: renamed from: implicitReceivers$delegate, reason: from kotlin metadata */
    private final Lazy implicitReceivers;
    private final List<FirTowerDataElement> nonLocalTowerDataElements;

    /* JADX INFO: renamed from: reversedFilteredLocalScopes$delegate, reason: from kotlin metadata */
    private final Lazy reversedFilteredLocalScopes;

    public TowerDataElementsForName(final Name name2, final FirTowerDataContext towerDataContext) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(towerDataContext, "towerDataContext");
        this.nonLocalTowerDataElements = CollectionsKt.asReversed(towerDataContext.getNonLocalTowerDataElements());
        this.reversedFilteredLocalScopes = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends IndexedValue<? extends FirLocalScope>>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerDataElementsForName$reversedFilteredLocalScopes$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IndexedValue<? extends FirLocalScope>> invoke() {
                FirTowerDataContext firTowerDataContext = towerDataContext;
                Name name3 = name2;
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                PersistentList<FirLocalScope> localScopes = firTowerDataContext.getLocalScopes();
                int lastIndex = CollectionsKt.getLastIndex(localScopes);
                for (int i = lastIndex; -1 < i; i--) {
                    FirLocalScope firLocalScope = (FirLocalScope) localScopes.get(i);
                    if (firLocalScope.mayContainName(name3)) {
                        listCreateListBuilder.add(new IndexedValue(lastIndex - i, firLocalScope));
                    }
                }
                return CollectionsKt.build(listCreateListBuilder);
            }
        });
        this.implicitReceivers = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends IndexedValue<? extends ImplicitReceiverValue<?>>>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerDataElementsForName$implicitReceivers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IndexedValue<? extends ImplicitReceiverValue<?>>> invoke() {
                List<FirTowerDataElement> nonLocalTowerDataElements = this.this$0.getNonLocalTowerDataElements();
                ArrayList arrayList = new ArrayList();
                int i = 0;
                for (Object obj : nonLocalTowerDataElements) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ImplicitReceiverValue<?> implicitReceiver = ((FirTowerDataElement) obj).getImplicitReceiver();
                    IndexedValue indexedValue = implicitReceiver != null ? new IndexedValue(i, implicitReceiver) : null;
                    if (indexedValue != null) {
                        arrayList.add(indexedValue);
                    }
                    i = i2;
                }
                return arrayList;
            }
        });
        this.contextReceiverGroups = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends IndexedValue<? extends List<? extends ContextReceiverValue<?>>>>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerDataElementsForName$contextReceiverGroups$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IndexedValue<? extends List<? extends ContextReceiverValue<?>>>> invoke() {
                List<FirTowerDataElement> nonLocalTowerDataElements = this.this$0.getNonLocalTowerDataElements();
                ArrayList arrayList = new ArrayList();
                int i = 0;
                for (Object obj : nonLocalTowerDataElements) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    List<ContextReceiverValue<?>> contextReceiverGroup = ((FirTowerDataElement) obj).getContextReceiverGroup();
                    IndexedValue indexedValue = contextReceiverGroup != null ? new IndexedValue(i, contextReceiverGroup) : null;
                    if (indexedValue != null) {
                        arrayList.add(indexedValue);
                    }
                    i = i2;
                }
                return arrayList;
            }
        });
    }

    public final List<FirTowerDataElement> getNonLocalTowerDataElements() {
        return this.nonLocalTowerDataElements;
    }

    public final List<IndexedValue<FirLocalScope>> getReversedFilteredLocalScopes() {
        return (List) this.reversedFilteredLocalScopes.getValue();
    }

    public final List<IndexedValue<ImplicitReceiverValue<?>>> getImplicitReceivers() {
        return (List) this.implicitReceivers.getValue();
    }

    public final List<IndexedValue<List<ContextReceiverValue<?>>>> getContextReceiverGroups() {
        return (List) this.contextReceiverGroups.getValue();
    }
}
