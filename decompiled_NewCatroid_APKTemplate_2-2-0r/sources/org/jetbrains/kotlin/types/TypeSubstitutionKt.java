package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorType;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a6\u0010\u0003\u001a\u00020\u0002*\u00020\u00022\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0007\u001a&\u0010\u0003\u001a\u00020\u0001*\u00020\u00012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\f"}, d2 = {"asSimpleType", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/KotlinType;", SchemaSymbols.ATTVAL_REPLACE, "newArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "newAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "newArgumentsForUpperBound", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeSubstitutionKt {
    public static final SimpleType asSimpleType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        SimpleType simpleType = unwrappedTypeUnwrap instanceof SimpleType ? (SimpleType) unwrappedTypeUnwrap : null;
        if (simpleType != null) {
            return simpleType;
        }
        throw new IllegalStateException(("This is should be simple type: " + kotlinType).toString());
    }

    public static final KotlinType replace(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return replace$default(kotlinType, null, null, null, 7, null);
    }

    public static final KotlinType replace(KotlinType kotlinType, List<? extends TypeProjection> newArguments) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return replace$default(kotlinType, newArguments, null, null, 6, null);
    }

    public static final KotlinType replace(KotlinType kotlinType, List<? extends TypeProjection> newArguments, Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return replace$default(kotlinType, newArguments, newAnnotations, null, 4, null);
    }

    public static final SimpleType replace(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        return replace$default(simpleType, null, null, 3, null);
    }

    public static final SimpleType replace(SimpleType simpleType, List<? extends TypeProjection> newArguments) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return replace$default(simpleType, newArguments, null, 2, null);
    }

    public static /* synthetic */ KotlinType replace$default(KotlinType kotlinType, List list, Annotations annotations, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = kotlinType.getArguments();
        }
        if ((i & 2) != 0) {
            annotations = kotlinType.getAnnotations();
        }
        if ((i & 4) != 0) {
            list2 = list;
        }
        return replace(kotlinType, list, annotations, list2);
    }

    public static final KotlinType replace(KotlinType kotlinType, List<? extends TypeProjection> newArguments, Annotations newAnnotations, List<? extends TypeProjection> newArgumentsForUpperBound) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        Intrinsics.checkNotNullParameter(newArgumentsForUpperBound, "newArgumentsForUpperBound");
        if ((newArguments.isEmpty() || newArguments == kotlinType.getArguments()) && newAnnotations == kotlinType.getAnnotations()) {
            return kotlinType;
        }
        TypeAttributes attributes = kotlinType.getAttributes();
        if ((newAnnotations instanceof FilteredAnnotations) && newAnnotations.isEmpty()) {
            newAnnotations = Annotations.INSTANCE.getEMPTY();
        }
        TypeAttributes typeAttributesReplaceAnnotations = TypeAttributesKt.replaceAnnotations(attributes, newAnnotations);
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            return KotlinTypeFactory.flexibleType(replace(flexibleType.getLowerBound(), newArguments, typeAttributesReplaceAnnotations), replace(flexibleType.getUpperBound(), newArgumentsForUpperBound, typeAttributesReplaceAnnotations));
        }
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return replace((SimpleType) unwrappedTypeUnwrap, newArguments, typeAttributesReplaceAnnotations);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ SimpleType replace$default(SimpleType simpleType, List list, TypeAttributes typeAttributes, int i, Object obj) {
        if ((i & 1) != 0) {
            list = simpleType.getArguments();
        }
        if ((i & 2) != 0) {
            typeAttributes = simpleType.getAttributes();
        }
        return replace(simpleType, (List<? extends TypeProjection>) list, typeAttributes);
    }

    public static final SimpleType replace(SimpleType simpleType, List<? extends TypeProjection> newArguments, TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        if (newArguments.isEmpty() && newAttributes == simpleType.getAttributes()) {
            return simpleType;
        }
        if (newArguments.isEmpty()) {
            return simpleType.replaceAttributes(newAttributes);
        }
        if (simpleType instanceof ErrorType) {
            return ((ErrorType) simpleType).replaceArguments(newArguments);
        }
        return KotlinTypeFactory.simpleType$default(newAttributes, simpleType.getConstructor(), newArguments, simpleType.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
    }
}
