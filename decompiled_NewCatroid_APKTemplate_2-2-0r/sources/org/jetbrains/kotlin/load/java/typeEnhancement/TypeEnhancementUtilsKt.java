package org.jetbrains.kotlin.load.java.typeEnhancement;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: typeEnhancementUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0010\"\n\u0002\b\u0005\u001a0\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t\u001a\u0012\u0010\f\u001a\u00020\t*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001aE\u0010\u0010\u001a\u0004\u0018\u0001H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u0012*\b\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\u0006\u0010\u0015\u001a\u0002H\u00112\b\u0010\u0016\u001a\u0004\u0018\u0001H\u00112\u0006\u0010\b\u001a\u00020\tH\u0002¢\u0006\u0002\u0010\u0017\u001a&\u0010\u0010\u001a\u0004\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u00012\u0006\u0010\b\u001a\u00020\tH\u0002\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0018"}, d2 = {"nullabilityForErrors", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "getNullabilityForErrors", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;)Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "computeQualifiersForOverride", "superQualifiers", "", "isCovariant", "", "isForVarargParameter", "ignoreDeclarationNullabilityAnnotations", "hasEnhancedNullability", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "select", ExifInterface.GPS_DIRECTION_TRUE, "", "", "low", "high", "own", "(Ljava/util/Set;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Z)Ljava/lang/Object;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeEnhancementUtilsKt {
    private static final <T> T select(Set<? extends T> set, T t, T t2, T t3, boolean z) {
        Set<? extends T> set2;
        T t4;
        if (z) {
            if (set.contains(t)) {
                t4 = t;
            } else {
                t4 = set.contains(t2) ? t2 : null;
            }
            if (Intrinsics.areEqual(t4, t) && Intrinsics.areEqual(t3, t2)) {
                return null;
            }
            return t3 == null ? t4 : t3;
        }
        if (t3 != null && (set2 = CollectionsKt.toSet(SetsKt.plus(set, t3))) != null) {
            set = set2;
        }
        return (T) CollectionsKt.singleOrNull(set);
    }

    private static final NullabilityQualifier select(Set<? extends NullabilityQualifier> set, NullabilityQualifier nullabilityQualifier, boolean z) {
        if (nullabilityQualifier == NullabilityQualifier.FORCE_FLEXIBILITY) {
            return NullabilityQualifier.FORCE_FLEXIBILITY;
        }
        return (NullabilityQualifier) select(set, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, nullabilityQualifier, z);
    }

    private static final NullabilityQualifier getNullabilityForErrors(JavaTypeQualifiers javaTypeQualifiers) {
        if (javaTypeQualifiers.getIsNullabilityQualifierForWarning()) {
            return null;
        }
        return javaTypeQualifiers.getNullability();
    }

    public static final JavaTypeQualifiers computeQualifiersForOverride(JavaTypeQualifiers javaTypeQualifiers, Collection<JavaTypeQualifiers> superQualifiers, boolean z, boolean z2, boolean z3) {
        NullabilityQualifier nullabilityQualifierSelect;
        boolean z4;
        Intrinsics.checkNotNullParameter(javaTypeQualifiers, "<this>");
        Intrinsics.checkNotNullParameter(superQualifiers, "superQualifiers");
        Collection<JavaTypeQualifiers> collection = superQualifiers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            NullabilityQualifier nullabilityForErrors = getNullabilityForErrors((JavaTypeQualifiers) it.next());
            if (nullabilityForErrors != null) {
                arrayList.add(nullabilityForErrors);
            }
        }
        NullabilityQualifier nullabilityQualifierSelect2 = select(CollectionsKt.toSet(arrayList), getNullabilityForErrors(javaTypeQualifiers), z);
        if (nullabilityQualifierSelect2 == null) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it2 = collection.iterator();
            while (it2.hasNext()) {
                NullabilityQualifier nullability = ((JavaTypeQualifiers) it2.next()).getNullability();
                if (nullability != null) {
                    arrayList2.add(nullability);
                }
            }
            nullabilityQualifierSelect = select(CollectionsKt.toSet(arrayList2), javaTypeQualifiers.getNullability(), z);
        } else {
            nullabilityQualifierSelect = nullabilityQualifierSelect2;
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator<T> it3 = collection.iterator();
        while (it3.hasNext()) {
            MutabilityQualifier mutability = ((JavaTypeQualifiers) it3.next()).getMutability();
            if (mutability != null) {
                arrayList3.add(mutability);
            }
        }
        MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) select(CollectionsKt.toSet(arrayList3), MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, javaTypeQualifiers.getMutability(), z);
        NullabilityQualifier nullabilityQualifier = null;
        if (nullabilityQualifierSelect != null && !z3 && (!z2 || nullabilityQualifierSelect != NullabilityQualifier.NULLABLE)) {
            nullabilityQualifier = nullabilityQualifierSelect;
        }
        if (nullabilityQualifier != NullabilityQualifier.NOT_NULL) {
            z4 = false;
        } else {
            if (!javaTypeQualifiers.getDefinitelyNotNull()) {
                if (!collection.isEmpty()) {
                    Iterator<T> it4 = collection.iterator();
                    while (true) {
                        if (it4.hasNext()) {
                            if (((JavaTypeQualifiers) it4.next()).getDefinitelyNotNull()) {
                            }
                        }
                    }
                }
                z4 = false;
            }
            z4 = true;
        }
        return new JavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, z4, (nullabilityQualifier == null || nullabilityQualifierSelect2 == nullabilityQualifierSelect) ? false : true);
    }

    public static final boolean hasEnhancedNullability(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
        return typeSystemCommonBackendContext.hasAnnotation(type, ENHANCED_NULLABILITY_ANNOTATION);
    }
}
