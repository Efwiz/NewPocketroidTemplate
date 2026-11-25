package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.types.FunctionPlaceholderTypeConstructor;
import org.jetbrains.kotlin.types.FunctionPlaceholdersKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0000¨\u0006\u0004"}, d2 = {"createTypeForFunctionPlaceholder", "Lorg/jetbrains/kotlin/types/KotlinType;", "functionPlaceholder", "expectedType", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemBuilderImplKt {
    public static final KotlinType createTypeForFunctionPlaceholder(KotlinType functionPlaceholder, KotlinType expectedType) {
        ArrayList argumentTypes;
        Intrinsics.checkNotNullParameter(functionPlaceholder, "functionPlaceholder");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (!FunctionPlaceholdersKt.isFunctionPlaceholder(functionPlaceholder)) {
            return functionPlaceholder;
        }
        TypeConstructor constructor = functionPlaceholder.getConstructor();
        Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.FunctionPlaceholderTypeConstructor");
        FunctionPlaceholderTypeConstructor functionPlaceholderTypeConstructor = (FunctionPlaceholderTypeConstructor) constructor;
        boolean zIsBuiltinExtensionFunctionalType = FunctionTypesKt.isBuiltinExtensionFunctionalType(expectedType);
        if (!functionPlaceholderTypeConstructor.getHasDeclaredArguments()) {
            int size = expectedType.getConstructor().getParameters().size();
            int i = zIsBuiltinExtensionFunctionalType ? size - 2 : size - 1;
            ArrayList arrayList = new ArrayList();
            Iterator<Integer> it = new IntRange(1, i).iterator();
            while (it.hasNext()) {
                ((IntIterator) it).nextInt();
                arrayList.add(TypeUtils.DONT_CARE);
            }
            argumentTypes = arrayList;
        } else {
            argumentTypes = functionPlaceholderTypeConstructor.getArgumentTypes();
        }
        List<KotlinType> list = argumentTypes;
        SimpleType simpleType = zIsBuiltinExtensionFunctionalType ? TypeUtils.DONT_CARE : null;
        IntRange intRangeUntil = RangesKt.until(0, FunctionTypesKt.contextFunctionTypeParamsCount(expectedType));
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it2 = intRangeUntil.iterator();
        while (it2.hasNext()) {
            ((IntIterator) it2).nextInt();
            arrayList2.add(TypeUtils.DONT_CARE);
        }
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(functionPlaceholder);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        SimpleType simpleType2 = simpleType;
        SimpleType DONT_CARE = TypeUtils.DONT_CARE;
        Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
        return FunctionTypesKt.createFunctionType(builtIns, empty, simpleType2, arrayList2, list, null, DONT_CARE, FunctionTypesKt.isSuspendFunctionType(expectedType));
    }
}
