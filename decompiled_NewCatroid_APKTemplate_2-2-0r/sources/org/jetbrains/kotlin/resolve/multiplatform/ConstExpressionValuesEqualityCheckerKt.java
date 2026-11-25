package org.jetbrains.kotlin.resolve.multiplatform;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;

/* JADX INFO: compiled from: constExpressionValuesEqualityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¨\u0006\b"}, d2 = {"areExpressionConstValuesEqual", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ClassicExpectActualMatchingContext;", "expectValue", "", "actualValue", "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstExpressionValuesEqualityCheckerKt {
    public static final boolean areExpressionConstValuesEqual(final ClassicExpectActualMatchingContext classicExpectActualMatchingContext, Object obj, Object obj2, final ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy) {
        Intrinsics.checkNotNullParameter(classicExpectActualMatchingContext, "<this>");
        Intrinsics.checkNotNullParameter(collectionArgumentsCompatibilityCheckStrategy, "collectionArgumentsCompatibilityCheckStrategy");
        if ((obj instanceof AnnotationDescriptor) && (obj2 instanceof AnnotationDescriptor)) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) obj;
            Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
            AnnotationDescriptor annotationDescriptor2 = (AnnotationDescriptor) obj2;
            Map<Name, ConstantValue<?>> allValueArguments2 = annotationDescriptor2.getAllValueArguments();
            if (allValueArguments.size() != allValueArguments2.size() || !classicExpectActualMatchingContext.areCompatibleExpectActualTypes(annotationDescriptor.getType(), annotationDescriptor2.getType())) {
                return false;
            }
            Set<Name> setKeySet = allValueArguments.keySet();
            if (!(setKeySet instanceof Collection) || !setKeySet.isEmpty()) {
                for (Name name2 : setKeySet) {
                    if (!areExpressionConstValuesEqual(classicExpectActualMatchingContext, allValueArguments.get(name2), allValueArguments2.get(name2), collectionArgumentsCompatibilityCheckStrategy)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if ((obj instanceof ConstantValue) && (obj2 instanceof ConstantValue)) {
            return areExpressionConstValuesEqual(classicExpectActualMatchingContext, ((ConstantValue) obj).getValue(), ((ConstantValue) obj2).getValue(), collectionArgumentsCompatibilityCheckStrategy);
        }
        if ((obj instanceof Collection) && (obj2 instanceof Collection)) {
            return collectionArgumentsCompatibilityCheckStrategy.areCompatible((Collection) obj, (Collection) obj2, new Function2<Object, Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ConstExpressionValuesEqualityCheckerKt.areExpressionConstValuesEqual.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(Object obj3, Object obj4) {
                    return Boolean.valueOf(ConstExpressionValuesEqualityCheckerKt.areExpressionConstValuesEqual(classicExpectActualMatchingContext, obj3, obj4, collectionArgumentsCompatibilityCheckStrategy));
                }
            });
        }
        if ((obj instanceof Object[]) && (obj2 instanceof Object[])) {
            return collectionArgumentsCompatibilityCheckStrategy.areCompatible(ArraysKt.toList((Object[]) obj), ArraysKt.toList((Object[]) obj2), new Function2<Object, Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ConstExpressionValuesEqualityCheckerKt.areExpressionConstValuesEqual.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(Object obj3, Object obj4) {
                    return Boolean.valueOf(ConstExpressionValuesEqualityCheckerKt.areExpressionConstValuesEqual(classicExpectActualMatchingContext, obj3, obj4, collectionArgumentsCompatibilityCheckStrategy));
                }
            });
        }
        if ((obj instanceof KClassValue.Value.NormalClass) && (obj2 instanceof KClassValue.Value.NormalClass)) {
            KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) obj;
            ClassId classId = normalClass.getClassId();
            ClassDescriptor classDescriptorFindExpandedExpectClassInPlatformModule$resolution = classicExpectActualMatchingContext.findExpandedExpectClassInPlatformModule$resolution(classId);
            ClassId classId2 = classDescriptorFindExpandedExpectClassInPlatformModule$resolution != null ? classicExpectActualMatchingContext.getClassId(classDescriptorFindExpandedExpectClassInPlatformModule$resolution) : null;
            ClassLiteralValue value = normalClass.getValue();
            if (classId2 != null) {
                classId = classId2;
            }
            return Intrinsics.areEqual(normalClass.copy(ClassLiteralValue.copy$default(value, classId, 0, 2, null)), obj2);
        }
        return Intrinsics.areEqual(obj, obj2);
    }
}
