package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;

/* JADX INFO: compiled from: ImplicitsExtensionsResolutionFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0007J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "", "getScopesWithInfo", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeWithImplicitsExtensionsResolutionInfo;", "scopes", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "Default", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ImplicitsExtensionsResolutionFilter {
    Sequence<ScopeWithImplicitsExtensionsResolutionInfo> getScopesWithInfo(Sequence<? extends HierarchicalScope> scopes);

    /* JADX INFO: compiled from: ImplicitsExtensionsResolutionFilter.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter$Default;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "()V", "getScopesWithInfo", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeWithImplicitsExtensionsResolutionInfo;", "scopes", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements ImplicitsExtensionsResolutionFilter {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter
        public Sequence<ScopeWithImplicitsExtensionsResolutionInfo> getScopesWithInfo(Sequence<? extends HierarchicalScope> scopes) {
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            return SequencesKt.map(scopes, new Function1<HierarchicalScope, ScopeWithImplicitsExtensionsResolutionInfo>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter$Default$getScopesWithInfo$1
                @Override // kotlin.jvm.functions.Function1
                public final ScopeWithImplicitsExtensionsResolutionInfo invoke(HierarchicalScope scope) {
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return new ScopeWithImplicitsExtensionsResolutionInfo(scope, true);
                }
            });
        }
    }
}
