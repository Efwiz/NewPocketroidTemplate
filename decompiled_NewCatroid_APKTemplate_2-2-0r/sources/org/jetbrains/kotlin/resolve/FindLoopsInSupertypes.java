package org.jetbrains.kotlin.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: findLoopsInSupertypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006H\u0002¨\u0006\u0007"}, d2 = {"isReachable", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "to", "neighbors", "Lorg/jetbrains/kotlin/utils/DFS$Neighbors;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FindLoopsInSupertypes {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isReachable(TypeConstructor typeConstructor, final TypeConstructor typeConstructor2, DFS.Neighbors<TypeConstructor> neighbors) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        DFS.dfs(CollectionsKt.listOf(typeConstructor), neighbors, new DFS.VisitedWithSet(), new DFS.AbstractNodeHandler<TypeConstructor, Unit>() { // from class: org.jetbrains.kotlin.resolve.FindLoopsInSupertypes.isReachable.1
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7421result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7421result();
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(TypeConstructor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (!Intrinsics.areEqual(current, typeConstructor2)) {
                    return true;
                }
                booleanRef.element = true;
                return false;
            }
        });
        return booleanRef.element;
    }
}
