package org.jetbrains.kotlin.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.CallHandle;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;

/* JADX INFO: loaded from: classes6.dex */
public class TypeIntersector {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "typeB";
                break;
            case 2:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 3:
            case 5:
                objArr[0] = "descriptor";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/types/TypeIntersector";
                break;
            case 6:
                objArr[0] = "upperBounds";
                break;
            default:
                objArr[0] = "typeA";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/types/TypeIntersector";
        } else {
            objArr[1] = "getUpperBoundsAsType";
        }
        if (i == 2) {
            objArr[2] = "intersectTypes";
        } else if (i == 3) {
            objArr[2] = "getUpperBoundsAsType";
        } else if (i != 4) {
            if (i == 5 || i == 6) {
                objArr[2] = "intersectUpperBounds";
            } else {
                objArr[2] = "isIntersectionEmpty";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public static boolean isIntersectionEmpty(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(1);
        }
        return intersectTypes(new LinkedHashSet(Arrays.asList(kotlinType, kotlinType2))) == null;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static KotlinType intersectTypes(Collection<KotlinType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        if (collection.size() == 1) {
            return collection.iterator().next();
        }
        ArrayList<KotlinType> arrayList = new ArrayList(collection.size());
        boolean isMarkedNullable = true;
        KotlinType kotlinType = null;
        for (KotlinType kotlinType2 : collection) {
            if (!KotlinTypeKt.isError(kotlinType2)) {
                if (KotlinBuiltIns.isNothingOrNullableNothing(kotlinType2)) {
                    kotlinType = kotlinType2;
                }
                isMarkedNullable &= kotlinType2.getIsMarkedNullable();
                arrayList.add(TypeUtils.makeNotNullable(kotlinType2));
            }
        }
        if (kotlinType != null) {
            return TypeUtils.makeNullableAsSpecified(kotlinType, isMarkedNullable);
        }
        if (arrayList.isEmpty()) {
            return org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(ErrorTypeKind.INTERSECTION_OF_ERROR_TYPES, collection.toString());
        }
        KotlinTypeChecker kotlinTypeChecker = KotlinTypeChecker.DEFAULT;
        ArrayList arrayList2 = new ArrayList();
        for (KotlinType kotlinType3 : arrayList) {
            if (!TypeUtils.canHaveSubtypes(kotlinTypeChecker, kotlinType3)) {
                for (KotlinType kotlinType4 : arrayList) {
                    boolean zMayBeEqual = TypeUnifier.mayBeEqual(kotlinType3, kotlinType4);
                    boolean z = kotlinTypeChecker.isSubtypeOf(kotlinType3, kotlinType4) || kotlinTypeChecker.isSubtypeOf(kotlinType4, kotlinType3);
                    if (!zMayBeEqual && !z) {
                        return null;
                    }
                    if (!z) {
                    }
                }
                return TypeUtils.makeNullableAsSpecified(kotlinType3, isMarkedNullable);
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    KotlinType kotlinType5 = (KotlinType) it.next();
                    if (!kotlinType3.equals(kotlinType5) && kotlinTypeChecker.isSubtypeOf(kotlinType5, kotlinType3)) {
                        break;
                    }
                } else {
                    Iterator it2 = arrayList2.iterator();
                    do {
                        if (!it2.hasNext()) {
                            arrayList2.add(kotlinType3);
                            break;
                        }
                    } while (!kotlinTypeChecker.equalTypes((KotlinType) it2.next(), kotlinType3));
                }
            }
        }
        if (arrayList2.isEmpty()) {
            KotlinType kotlinTypeSingleBestRepresentative = FlexibleTypesKt.singleBestRepresentative((Collection<? extends KotlinType>) arrayList);
            if (kotlinTypeSingleBestRepresentative == null) {
                kotlinTypeSingleBestRepresentative = UtilsKt.hackForTypeIntersector(arrayList);
            }
            if (kotlinTypeSingleBestRepresentative == null) {
                return null;
            }
            return TypeUtils.makeNullableAsSpecified(kotlinTypeSingleBestRepresentative, isMarkedNullable);
        }
        if (arrayList2.size() == 1) {
            return TypeUtils.makeNullableAsSpecified((KotlinType) arrayList2.get(0), isMarkedNullable);
        }
        return new IntersectionTypeConstructor(arrayList2).createType();
    }

    public static KotlinType getUpperBoundsAsType(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        KotlinType kotlinTypeIntersectUpperBounds = intersectUpperBounds(typeParameterDescriptor, typeParameterDescriptor.getUpperBounds());
        if (kotlinTypeIntersectUpperBounds == null) {
            $$$reportNull$$$0(4);
        }
        return kotlinTypeIntersectUpperBounds;
    }

    public static KotlinType intersectUpperBounds(TypeParameterDescriptor typeParameterDescriptor, List<KotlinType> list) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        KotlinType kotlinTypeIntersectTypes = intersectTypes(list);
        return kotlinTypeIntersectTypes != null ? kotlinTypeIntersectTypes : DescriptorUtilsKt.getBuiltIns(typeParameterDescriptor).getNothingType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TypeUnifier {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "other";
            }
            objArr[1] = "org/jetbrains/kotlin/types/TypeIntersector$TypeUnifier";
            objArr[2] = "mayBeEqual";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class TypeParameterUsage {
            private final Variance howTheTypeParameterIsUsed;
            private final TypeParameterDescriptor typeParameterDescriptor;

            public TypeParameterUsage(TypeParameterDescriptor typeParameterDescriptor, Variance variance) {
                this.typeParameterDescriptor = typeParameterDescriptor;
                this.howTheTypeParameterIsUsed = variance;
            }
        }

        private TypeUnifier() {
        }

        public static boolean mayBeEqual(KotlinType kotlinType, KotlinType kotlinType2) {
            if (kotlinType == null) {
                $$$reportNull$$$0(0);
            }
            if (kotlinType2 == null) {
                $$$reportNull$$$0(1);
            }
            return unify(kotlinType, kotlinType2);
        }

        private static boolean unify(KotlinType kotlinType, KotlinType kotlinType2) {
            final HashMap map = new HashMap();
            Function1 function1 = new Function1() { // from class: org.jetbrains.kotlin.types.TypeIntersector$TypeUnifier$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return TypeIntersector.TypeUnifier.lambda$unify$0(map, (TypeIntersector.TypeUnifier.TypeParameterUsage) obj);
                }
            };
            processAllTypeParameters(kotlinType, Variance.INVARIANT, function1, new Function1() { // from class: org.jetbrains.kotlin.types.TypeIntersector$TypeUnifier$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(map.containsKey((TypeParameterDescriptor) obj));
                }
            });
            processAllTypeParameters(kotlinType2, Variance.INVARIANT, function1, new Function1() { // from class: org.jetbrains.kotlin.types.TypeIntersector$TypeUnifier$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(map.containsKey((TypeParameterDescriptor) obj));
                }
            });
            ConstraintSystemBuilderImpl constraintSystemBuilderImpl = new ConstraintSystemBuilderImpl();
            constraintSystemBuilderImpl.addSubtypeConstraint(kotlinType, constraintSystemBuilderImpl.registerTypeVariables(CallHandle.NONE.INSTANCE, map.keySet(), false).substitute(kotlinType2, Variance.INVARIANT), ConstraintPositionKind.SPECIAL.position());
            return constraintSystemBuilderImpl.build().getStatus().isSuccessful();
        }

        static /* synthetic */ Unit lambda$unify$0(Map map, TypeParameterUsage typeParameterUsage) {
            Variance variance = (Variance) map.get(typeParameterUsage.typeParameterDescriptor);
            if (variance == null) {
                variance = Variance.INVARIANT;
            }
            map.put(typeParameterUsage.typeParameterDescriptor, typeParameterUsage.howTheTypeParameterIsUsed.superpose(variance));
            return Unit.INSTANCE;
        }

        private static void processAllTypeParameters(KotlinType kotlinType, Variance variance, Function1<TypeParameterUsage, Unit> function1, Function1<TypeParameterDescriptor, Boolean> function2) {
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            if (this$0 instanceof TypeParameterDescriptor) {
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) this$0;
                if (function2.invoke(typeParameterDescriptor).booleanValue()) {
                    return;
                }
                function1.invoke(new TypeParameterUsage(typeParameterDescriptor, variance));
                Iterator<KotlinType> it = kotlinType.getConstructor().mo7438getSupertypes().iterator();
                while (it.hasNext()) {
                    processAllTypeParameters(it.next(), variance, function1, function2);
                }
            }
            for (TypeProjection typeProjection : kotlinType.getArguments()) {
                if (!typeProjection.isStarProjection()) {
                    processAllTypeParameters(typeProjection.getNullableAnyType(), typeProjection.getProjectionKind(), function1, function2);
                }
            }
        }
    }
}
