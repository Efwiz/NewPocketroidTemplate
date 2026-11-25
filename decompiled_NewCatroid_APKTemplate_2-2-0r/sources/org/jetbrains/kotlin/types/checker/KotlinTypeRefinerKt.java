package org.jetbrains.kotlin.types.checker;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: KotlinTypeRefiner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\u001a \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t*\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rH\u0007\"(\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00018\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"REFINER_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/types/checker/Ref;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport;", "getREFINER_CAPABILITY$annotations", "()V", "getREFINER_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "refineTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", SpecTypePair.NAME_types, "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeRefinerKt {
    private static final ModuleCapability<Ref<TypeRefinementSupport>> REFINER_CAPABILITY = new ModuleCapability<>("KotlinTypeRefiner");

    public static /* synthetic */ void getREFINER_CAPABILITY$annotations() {
    }

    public static final ModuleCapability<Ref<TypeRefinementSupport>> getREFINER_CAPABILITY() {
        return REFINER_CAPABILITY;
    }

    public static final List<KotlinType> refineTypes(KotlinTypeRefiner kotlinTypeRefiner, Iterable<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(types, 10));
        Iterator<? extends KotlinType> it = types.iterator();
        while (it.hasNext()) {
            arrayList.add(kotlinTypeRefiner.refineType((KotlinTypeMarker) it.next()));
        }
        return arrayList;
    }
}
