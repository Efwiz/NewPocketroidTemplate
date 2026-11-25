package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IntersectionTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a<\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"transformComponents", "Lorg/jetbrains/kotlin/types/IntersectionTypeConstructor;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "", "transform", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntersectionTypeConstructorKt {
    public static /* synthetic */ IntersectionTypeConstructor transformComponents$default(IntersectionTypeConstructor intersectionTypeConstructor, Function1 predicate, Function1 transform, int i, Object obj) {
        if ((i & 1) != 0) {
            predicate = new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.types.IntersectionTypeConstructorKt.transformComponents.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
        }
        Intrinsics.checkNotNullParameter(intersectionTypeConstructor, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection<KotlinType> collectionMo7438getSupertypes = intersectionTypeConstructor.mo7438getSupertypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
        boolean z = false;
        for (KotlinType kotlinType : collectionMo7438getSupertypes) {
            if (((Boolean) predicate.invoke(kotlinType)).booleanValue()) {
                kotlinType = (KotlinType) transform.invoke(kotlinType);
                z = true;
            }
            arrayList.add(kotlinType);
        }
        ArrayList arrayList2 = arrayList;
        KotlinType kotlinType2 = null;
        if (!z) {
            return null;
        }
        KotlinType alternative = intersectionTypeConstructor.getAlternative();
        if (alternative != null) {
            if (((Boolean) predicate.invoke(alternative)).booleanValue()) {
                alternative = (KotlinType) transform.invoke(alternative);
            }
            kotlinType2 = alternative;
        }
        return new IntersectionTypeConstructor(arrayList2).setAlternative(kotlinType2);
    }

    public static final IntersectionTypeConstructor transformComponents(IntersectionTypeConstructor intersectionTypeConstructor, Function1<? super KotlinType, Boolean> predicate, Function1<? super KotlinType, ? extends KotlinType> transform) {
        Intrinsics.checkNotNullParameter(intersectionTypeConstructor, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection<KotlinType> collectionMo7438getSupertypes = intersectionTypeConstructor.mo7438getSupertypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
        boolean z = false;
        for (KotlinType kotlinTypeInvoke : collectionMo7438getSupertypes) {
            if (predicate.invoke(kotlinTypeInvoke).booleanValue()) {
                kotlinTypeInvoke = transform.invoke(kotlinTypeInvoke);
                z = true;
            }
            arrayList.add(kotlinTypeInvoke);
        }
        ArrayList arrayList2 = arrayList;
        KotlinType kotlinType = null;
        if (!z) {
            return null;
        }
        KotlinType alternative = intersectionTypeConstructor.getAlternative();
        if (alternative != null) {
            if (predicate.invoke(alternative).booleanValue()) {
                alternative = transform.invoke(alternative);
            }
            kotlinType = alternative;
        }
        return new IntersectionTypeConstructor(arrayList2).setAlternative(kotlinType);
    }
}
