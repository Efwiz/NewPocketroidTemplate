package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;

/* JADX INFO: compiled from: TypeCapabilities.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0003\u001a\n\u0010\u0007\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\b\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0003¨\u0006\n"}, d2 = {"sameTypeConstructors", "", "first", "Lorg/jetbrains/kotlin/types/KotlinType;", "second", "getCustomTypeParameter", "Lorg/jetbrains/kotlin/types/CustomTypeParameter;", "getSubtypeRepresentative", "getSupertypeRepresentative", "isCustomTypeParameter", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeCapabilitiesKt {
    public static final boolean isCustomTypeParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Annotated annotatedUnwrap = kotlinType.unwrap();
        CustomTypeParameter customTypeParameter = annotatedUnwrap instanceof CustomTypeParameter ? (CustomTypeParameter) annotatedUnwrap : null;
        if (customTypeParameter != null) {
            return customTypeParameter.isTypeParameter();
        }
        return false;
    }

    public static final CustomTypeParameter getCustomTypeParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Annotated annotatedUnwrap = kotlinType.unwrap();
        CustomTypeParameter customTypeParameter = annotatedUnwrap instanceof CustomTypeParameter ? (CustomTypeParameter) annotatedUnwrap : null;
        if (customTypeParameter == null || !customTypeParameter.isTypeParameter()) {
            return null;
        }
        return customTypeParameter;
    }

    public static final KotlinType getSubtypeRepresentative(KotlinType kotlinType) {
        KotlinType subTypeRepresentative;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Annotated annotatedUnwrap = kotlinType.unwrap();
        SubtypingRepresentatives subtypingRepresentatives = annotatedUnwrap instanceof SubtypingRepresentatives ? (SubtypingRepresentatives) annotatedUnwrap : null;
        return (subtypingRepresentatives == null || (subTypeRepresentative = subtypingRepresentatives.getSubTypeRepresentative()) == null) ? kotlinType : subTypeRepresentative;
    }

    public static final KotlinType getSupertypeRepresentative(KotlinType kotlinType) {
        KotlinType superTypeRepresentative;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Annotated annotatedUnwrap = kotlinType.unwrap();
        SubtypingRepresentatives subtypingRepresentatives = annotatedUnwrap instanceof SubtypingRepresentatives ? (SubtypingRepresentatives) annotatedUnwrap : null;
        return (subtypingRepresentatives == null || (superTypeRepresentative = subtypingRepresentatives.getSuperTypeRepresentative()) == null) ? kotlinType : superTypeRepresentative;
    }

    public static final boolean sameTypeConstructors(KotlinType first, KotlinType second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        Annotated annotatedUnwrap = first.unwrap();
        SubtypingRepresentatives subtypingRepresentatives = annotatedUnwrap instanceof SubtypingRepresentatives ? (SubtypingRepresentatives) annotatedUnwrap : null;
        if (!(subtypingRepresentatives != null ? subtypingRepresentatives.sameTypeConstructor(second) : false)) {
            Annotated annotatedUnwrap2 = second.unwrap();
            SubtypingRepresentatives subtypingRepresentatives2 = annotatedUnwrap2 instanceof SubtypingRepresentatives ? (SubtypingRepresentatives) annotatedUnwrap2 : null;
            if (!(subtypingRepresentatives2 != null ? subtypingRepresentatives2.sameTypeConstructor(first) : false)) {
                return false;
            }
        }
        return true;
    }
}
