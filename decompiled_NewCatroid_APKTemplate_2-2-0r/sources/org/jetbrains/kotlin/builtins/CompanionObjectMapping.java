package org.jetbrains.kotlin.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: CompanionObjectMapping.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/builtins/CompanionObjectMapping;", "", "()V", "classIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassIds", "()Ljava/util/Set;", "allClassesWithIntrinsicCompanions", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompanionObjectMapping {
    public static final CompanionObjectMapping INSTANCE = new CompanionObjectMapping();
    private static final Set<ClassId> classIds;

    private CompanionObjectMapping() {
    }

    public final Set<ClassId> getClassIds() {
        return classIds;
    }

    static {
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(StandardNames.getPrimitiveFqName((PrimitiveType) it.next()));
        }
        FqName safe = StandardNames.FqNames.string.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        List listPlus = CollectionsKt.plus((Collection<? extends FqName>) arrayList, safe);
        FqName safe2 = StandardNames.FqNames._boolean.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe2, "toSafe(...)");
        List listPlus2 = CollectionsKt.plus((Collection<? extends FqName>) listPlus, safe2);
        FqName safe3 = StandardNames.FqNames._enum.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe3, "toSafe(...)");
        List listPlus3 = CollectionsKt.plus((Collection<? extends FqName>) listPlus2, safe3);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it2 = listPlus3.iterator();
        while (it2.hasNext()) {
            linkedHashSet.add(ClassId.topLevel((FqName) it2.next()));
        }
        classIds = linkedHashSet;
    }

    public final Set<ClassId> allClassesWithIntrinsicCompanions() {
        return classIds;
    }
}
