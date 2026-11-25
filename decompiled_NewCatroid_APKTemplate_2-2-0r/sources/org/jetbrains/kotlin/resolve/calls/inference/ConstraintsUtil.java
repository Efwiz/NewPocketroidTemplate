package org.jetbrains.kotlin.resolve.calls.inference;

import androidx.core.app.NotificationCompat;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: loaded from: classes6.dex */
public class ConstraintsUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 2:
            case 3:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/inference/ConstraintsUtil";
                break;
            case 4:
            case 8:
            default:
                objArr[0] = "constraintSystem";
                break;
            case 5:
                objArr[0] = "typeVariable";
                break;
            case 9:
                objArr[0] = "typeParameter";
                break;
            case 10:
                objArr[0] = "call";
                break;
            case 11:
                objArr[0] = NotificationCompat.CATEGORY_STATUS;
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getSubstitutorsForConflictingParameters";
        } else if (i == 6 || i == 7) {
            objArr[1] = "getSafeValue";
        } else {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/inference/ConstraintsUtil";
        }
        switch (i) {
            case 1:
                objArr[2] = "getSubstitutorsForConflictingParameters";
                break;
            case 2:
            case 3:
            case 6:
            case 7:
                break;
            case 4:
            case 5:
                objArr[2] = "getSafeValue";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "checkUpperBoundIsSatisfied";
                break;
            case 11:
                objArr[2] = "getDebugMessageForStatus";
                break;
            default:
                objArr[2] = "getFirstConflictingVariable";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static TypeVariable getFirstConflictingVariable(ConstraintSystem constraintSystem) {
        if (constraintSystem == null) {
            $$$reportNull$$$0(0);
        }
        for (TypeVariable typeVariable : constraintSystem.getTypeVariables()) {
            if (constraintSystem.getTypeBounds(typeVariable).getValues().size() > 1) {
                return typeVariable;
            }
        }
        return null;
    }

    public static Collection<TypeSubstitutor> getSubstitutorsForConflictingParameters(ConstraintSystem constraintSystem) {
        if (constraintSystem == null) {
            $$$reportNull$$$0(1);
        }
        TypeVariable firstConflictingVariable = getFirstConflictingVariable(constraintSystem);
        if (firstConflictingVariable == null) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }
        TypeParameterDescriptor originalTypeParameter = firstConflictingVariable.getOriginalTypeParameter();
        Collection<KotlinType> values = constraintSystem.getTypeBounds(firstConflictingVariable).getValues();
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (KotlinType kotlinType : values) {
            LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
            linkedHashMapNewLinkedHashMap.put(originalTypeParameter.getTypeConstructor(), new TypeProjectionImpl(kotlinType));
            arrayListNewArrayList.add(linkedHashMapNewLinkedHashMap);
        }
        for (TypeVariable typeVariable : constraintSystem.getTypeVariables()) {
            if (typeVariable != firstConflictingVariable) {
                KotlinType safeValue = getSafeValue(constraintSystem, typeVariable);
                Iterator it = arrayListNewArrayList.iterator();
                while (it.hasNext()) {
                    ((Map) it.next()).put(typeVariable.getOriginalTypeParameter().getTypeConstructor(), new TypeProjectionImpl(safeValue));
                }
            }
        }
        ArrayList arrayList = new ArrayList(arrayListNewArrayList.size());
        Iterator it2 = arrayListNewArrayList.iterator();
        while (it2.hasNext()) {
            arrayList.add(TypeSubstitutor.create((Map<TypeConstructor, TypeProjection>) it2.next()));
        }
        return arrayList;
    }

    private static KotlinType getSafeValue(ConstraintSystem constraintSystem, TypeVariable typeVariable) {
        if (constraintSystem == null) {
            $$$reportNull$$$0(4);
        }
        if (typeVariable == null) {
            $$$reportNull$$$0(5);
        }
        KotlinType value = constraintSystem.getTypeBounds(typeVariable).getValue();
        if (value != null) {
            if (value == null) {
                $$$reportNull$$$0(6);
            }
            return value;
        }
        KotlinType upperBoundsAsType = TypeIntersector.getUpperBoundsAsType(typeVariable.getOriginalTypeParameter());
        if (upperBoundsAsType == null) {
            $$$reportNull$$$0(7);
        }
        return upperBoundsAsType;
    }

    public static boolean checkUpperBoundIsSatisfied(ConstraintSystem constraintSystem, TypeParameterDescriptor typeParameterDescriptor, Call call, boolean z) {
        if (constraintSystem == null) {
            $$$reportNull$$$0(8);
        }
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (call == null) {
            $$$reportNull$$$0(10);
        }
        KotlinType value = constraintSystem.getTypeBounds(ConstraintSystemUtilsKt.descriptorToVariable(constraintSystem, TypeVariableKt.toHandle(call), typeParameterDescriptor)).getValue();
        if (value == null) {
            return true;
        }
        List map = CollectionsKt.map(constraintSystem.getTypeVariables(), new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintsUtil$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((TypeVariable) obj).getOriginalTypeParameter();
            }
        });
        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
            if (z || !TypeUtils.dependsOnTypeParameters(kotlinType, map)) {
                if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(value, constraintSystem.getResultingSubstitutor().substitute(kotlinType, Variance.INVARIANT))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getDebugMessageForStatus(ConstraintSystemStatus constraintSystemStatus) {
        if (constraintSystemStatus == null) {
            $$$reportNull$$$0(11);
        }
        StringBuilder sb = new StringBuilder();
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (Method method : constraintSystemStatus.getClass().getMethods()) {
            String name2 = method.getName();
            boolean z = name2.startsWith(LanguageCodeUtil.IS) || (name2.startsWith("has") && !name2.equals("hashCode"));
            if (method.getParameterTypes().length == 0 && z) {
                arrayListNewArrayList.add(method);
            }
        }
        arrayListNewArrayList.sort(Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintsUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Method) obj).getName();
            }
        }));
        Iterator it = arrayListNewArrayList.iterator();
        while (it.hasNext()) {
            Method method2 = (Method) it.next();
            try {
                sb.append(Parameters.DEFAULT_OPTION_PREFIXES).append(method2.getName()).append(": ").append(method2.invoke(constraintSystemStatus, new Object[0]));
                if (it.hasNext()) {
                    sb.append("\n");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                sb.append(e.getMessage());
            }
        }
        return sb.toString();
    }
}
