package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: LocalClassesNavigation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\u0018\u00002\u00020\u0001B/\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0007J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\t2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002R-\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "", "parentForClass", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "parentClassForFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Ljava/util/Map;Ljava/util/Map;)V", "designationMap", "", "getDesignationMap", "()Ljava/util/Map;", "designationMap$delegate", "Lkotlin/Lazy;", "getParentForClass", "pathForCallable", "callableMemberDeclaration", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalClassesNavigationInfo {

    /* JADX INFO: renamed from: designationMap$delegate, reason: from kotlin metadata */
    private final Lazy designationMap;
    private final Map<FirCallableDeclaration, FirClassLikeDeclaration> parentClassForFunction;
    private final Map<FirClassLikeDeclaration, FirClassLikeDeclaration> parentForClass;

    /* JADX WARN: Multi-variable type inference failed */
    public LocalClassesNavigationInfo(Map<FirClassLikeDeclaration, ? extends FirClassLikeDeclaration> parentForClass, Map<FirCallableDeclaration, ? extends FirClassLikeDeclaration> parentClassForFunction) {
        Intrinsics.checkNotNullParameter(parentForClass, "parentForClass");
        Intrinsics.checkNotNullParameter(parentClassForFunction, "parentClassForFunction");
        this.parentForClass = parentForClass;
        this.parentClassForFunction = parentClassForFunction;
        this.designationMap = LazyKt.lazy(new Function0<Map<FirCallableDeclaration, ? extends List<? extends FirClassLikeDeclaration>>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo$designationMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FirCallableDeclaration, ? extends List<? extends FirClassLikeDeclaration>> invoke() {
                Set setKeySet = this.this$0.parentClassForFunction.keySet();
                final LocalClassesNavigationInfo localClassesNavigationInfo = this.this$0;
                return CollectionsKt.keysToMap(setKeySet, new Function1<FirCallableDeclaration, List<? extends FirClassLikeDeclaration>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo$designationMap$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final List<FirClassLikeDeclaration> invoke(FirCallableDeclaration it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return localClassesNavigationInfo.pathForCallable(it);
                    }
                });
            }
        });
    }

    public final Map<FirClassLikeDeclaration, FirClassLikeDeclaration> getParentForClass() {
        return this.parentForClass;
    }

    public final Map<FirCallableDeclaration, List<FirClassLikeDeclaration>> getDesignationMap() {
        return (Map) this.designationMap.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirClassLikeDeclaration> pathForCallable(FirCallableDeclaration callableMemberDeclaration) {
        ArrayList arrayList = new ArrayList();
        FirClassLikeDeclaration firClassLikeDeclaration = this.parentClassForFunction.get(callableMemberDeclaration);
        while (firClassLikeDeclaration != null) {
            arrayList.add(firClassLikeDeclaration);
            firClassLikeDeclaration = this.parentForClass.get(firClassLikeDeclaration);
        }
        return kotlin.collections.CollectionsKt.asReversedMutable(arrayList);
    }
}
