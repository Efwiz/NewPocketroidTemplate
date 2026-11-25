package org.jetbrains.kotlin.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\u0018\u0010\t\u001a\u00020\u0004*\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractFlexibilityChecker;", "", "()V", "hasDifferentFlexibility", "", "Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "hasDifferentFlexibilityAtDepth", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractFlexibilityChecker {
    public static final AbstractFlexibilityChecker INSTANCE = new AbstractFlexibilityChecker();

    private AbstractFlexibilityChecker() {
    }

    public final boolean hasDifferentFlexibilityAtDepth(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(typeSystemCommonSuperTypesContext, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        if (types.isEmpty()) {
            return false;
        }
        if (hasDifferentFlexibility(typeSystemCommonSuperTypesContext, types)) {
            return true;
        }
        Collection<? extends KotlinTypeMarker> collection = types;
        int iArgumentsCount = typeSystemCommonSuperTypesContext.argumentsCount((KotlinTypeMarker) CollectionsKt.first(collection));
        int i = 0;
        while (i < iArgumentsCount) {
            ArrayList arrayList = new ArrayList();
            for (KotlinTypeMarker kotlinTypeMarker : collection) {
                KotlinTypeMarker type = (typeSystemCommonSuperTypesContext.argumentsCount(kotlinTypeMarker) <= i || typeSystemCommonSuperTypesContext.isStarProjection(typeSystemCommonSuperTypesContext.getArgument(kotlinTypeMarker, i))) ? null : typeSystemCommonSuperTypesContext.getType(typeSystemCommonSuperTypesContext.getArgument(kotlinTypeMarker, i));
                if (type != null) {
                    arrayList.add(type);
                }
            }
            if (hasDifferentFlexibilityAtDepth(typeSystemCommonSuperTypesContext, arrayList)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private final boolean hasDifferentFlexibility(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, Collection<? extends KotlinTypeMarker> collection) {
        Collection<? extends KotlinTypeMarker> collection2 = collection;
        KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) CollectionsKt.first(collection2);
        boolean z = collection2 instanceof Collection;
        if (z && collection2.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (((KotlinTypeMarker) it.next()) != kotlinTypeMarker) {
                if (z && collection2.isEmpty()) {
                    return false;
                }
                Iterator<T> it2 = collection2.iterator();
                while (it2.hasNext()) {
                    if (!typeSystemCommonSuperTypesContext.isFlexible((KotlinTypeMarker) it2.next())) {
                        if (z && collection2.isEmpty()) {
                            return false;
                        }
                        Iterator<T> it3 = collection2.iterator();
                        while (it3.hasNext()) {
                            if (typeSystemCommonSuperTypesContext.isFlexible((KotlinTypeMarker) it3.next())) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
