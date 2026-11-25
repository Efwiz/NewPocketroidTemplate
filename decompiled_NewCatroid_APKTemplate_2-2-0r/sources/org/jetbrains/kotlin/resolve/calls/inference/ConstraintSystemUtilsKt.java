package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;

/* JADX INFO: compiled from: constraintSystemUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0007\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\b\u001a\u00020\t\u001a\u0012\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\u00020\rH\u0000\u001a\u0012\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b*\u00020\rH\u0000\u001a\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\r¨\u0006\u0011"}, d2 = {"descriptorToVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "call", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "filterConstraintsOut", "excludePositionKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getNestedArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getNestedTypeParameters", "getNestedTypeVariables", ModuleXmlParser.TYPE, "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemUtilsKt {
    public static final List<TypeVariable> getNestedTypeVariables(ConstraintSystem constraintSystem, KotlinType type) {
        Intrinsics.checkNotNullParameter(constraintSystem, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Set set = CollectionsKt.toSet(getNestedTypeParameters(type));
        Set<TypeVariable> typeVariables = constraintSystem.getTypeVariables();
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeVariables) {
            if (set.contains(((TypeVariable) obj).getOriginalTypeParameter())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final ConstraintSystem filterConstraintsOut(ConstraintSystem constraintSystem, final ConstraintPositionKind excludePositionKind) {
        Intrinsics.checkNotNullParameter(constraintSystem, "<this>");
        Intrinsics.checkNotNullParameter(excludePositionKind, "excludePositionKind");
        return constraintSystem.toBuilder(new Function1<ConstraintPosition, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt.filterConstraintsOut.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConstraintPosition it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!ConstraintPositionKt.derivedFrom(it, excludePositionKind));
            }
        }).build();
    }

    public static final TypeVariable descriptorToVariable(ConstraintSystem constraintSystem, CallHandle call, TypeParameterDescriptor descriptor) {
        Object next;
        Intrinsics.checkNotNullParameter(constraintSystem, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Iterator<T> it = constraintSystem.getTypeVariables().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            TypeVariable typeVariable = (TypeVariable) next;
            if (Intrinsics.areEqual(typeVariable.getCall(), call) && Intrinsics.areEqual(typeVariable.getOriginalTypeParameter(), descriptor)) {
                break;
            }
        }
        TypeVariable typeVariable2 = (TypeVariable) next;
        if (typeVariable2 != null) {
            return typeVariable2;
        }
        throw new IllegalArgumentException("Unknown descriptor: " + descriptor + ", call: " + call);
    }

    public static final List<TypeProjection> getNestedArguments(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(new TypeProjectionImpl(kotlinType));
        while (!arrayDeque.isEmpty()) {
            TypeProjection typeProjection = (TypeProjection) arrayDeque.pop();
            if (!typeProjection.isStarProjection()) {
                arrayList.add(typeProjection);
                Iterator<T> it = typeProjection.getNullableAnyType().getArguments().iterator();
                while (it.hasNext()) {
                    arrayDeque.add((TypeProjection) it.next());
                }
            }
        }
        return arrayList;
    }

    public static final List<TypeParameterDescriptor> getNestedTypeParameters(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        List<TypeProjection> nestedArguments = getNestedArguments(kotlinType);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = nestedArguments.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = ((TypeProjection) it.next()).getNullableAnyType().getConstructor().getThis$0();
            TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
            if (typeParameterDescriptor != null) {
                arrayList.add(typeParameterDescriptor);
            }
        }
        return arrayList;
    }
}
