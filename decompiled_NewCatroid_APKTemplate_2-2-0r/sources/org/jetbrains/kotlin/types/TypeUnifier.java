package org.jetbrains.kotlin.types;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: loaded from: classes6.dex */
public class TypeUnifier {

    public interface UnificationResult {
        Map<TypeConstructor, TypeProjection> getSubstitution();

        boolean isSuccess();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "projectWithVariables";
        } else if (i == 2) {
            objArr[0] = VisualPlacementActivity.EXTRA_IS_VARIABLE;
        } else if (i != 3) {
            objArr[0] = "knownProjection";
        } else {
            objArr[0] = "org/jetbrains/kotlin/types/TypeUnifier";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/types/TypeUnifier";
        } else {
            objArr[1] = "unify";
        }
        if (i != 3) {
            objArr[2] = "unify";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public static UnificationResult unify(TypeProjection typeProjection, TypeProjection typeProjection2, Predicate<TypeConstructor> predicate) {
        if (typeProjection == null) {
            $$$reportNull$$$0(0);
        }
        if (typeProjection2 == null) {
            $$$reportNull$$$0(1);
        }
        if (predicate == null) {
            $$$reportNull$$$0(2);
        }
        UnificationResultImpl unificationResultImpl = new UnificationResultImpl();
        doUnify(typeProjection, typeProjection2, predicate, unificationResultImpl);
        return unificationResultImpl;
    }

    private static void doUnify(TypeProjection typeProjection, TypeProjection typeProjection2, Predicate<TypeConstructor> predicate, UnificationResultImpl unificationResultImpl) {
        KotlinType type = typeProjection.getType();
        KotlinType type2 = typeProjection2.getType();
        Variance projectionKind = typeProjection.getProjectionKind();
        Variance projectionKind2 = typeProjection2.getProjectionKind();
        if (projectionKind == projectionKind2 && projectionKind != Variance.INVARIANT) {
            doUnify(new TypeProjectionImpl(type), new TypeProjectionImpl(type2), predicate, unificationResultImpl);
            return;
        }
        if (type.getIsMarkedNullable() && type2.getIsMarkedNullable()) {
            doUnify(new TypeProjectionImpl(projectionKind, TypeUtils.makeNotNullable(type)), new TypeProjectionImpl(projectionKind2, TypeUtils.makeNotNullable(type2)), predicate, unificationResultImpl);
            return;
        }
        if (projectionKind != projectionKind2 && projectionKind2 != Variance.INVARIANT) {
            unificationResultImpl.fail();
            return;
        }
        if (!type.getIsMarkedNullable() && type2.getIsMarkedNullable()) {
            unificationResultImpl.fail();
            return;
        }
        TypeConstructor constructor = type2.getConstructor();
        if (predicate.test(constructor)) {
            unificationResultImpl.put(constructor, typeProjection);
            return;
        }
        if (type.getIsMarkedNullable() != type2.getIsMarkedNullable() || projectionKind != projectionKind2 || !type.getConstructor().equals(type2.getConstructor())) {
            unificationResultImpl.fail();
            return;
        }
        if (type.getArguments().size() != type2.getArguments().size()) {
            unificationResultImpl.fail();
            return;
        }
        if (type.getArguments().isEmpty()) {
            return;
        }
        List<TypeProjection> arguments = type.getArguments();
        List<TypeProjection> arguments2 = type2.getArguments();
        for (int i = 0; i < arguments.size(); i++) {
            doUnify(arguments.get(i), arguments2.get(i), predicate, unificationResultImpl);
        }
    }

    private static class UnificationResultImpl implements UnificationResult {
        private final Set<TypeConstructor> failedVariables;
        private final Map<TypeConstructor, TypeProjection> substitution;
        private boolean success;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/types/TypeUnifier$UnificationResultImpl", "getSubstitution"));
        }

        private UnificationResultImpl() {
            this.success = true;
            this.substitution = CollectionsKt.newHashMapWithExpectedSize(1);
            this.failedVariables = CollectionsKt.newHashSetWithExpectedSize(0);
        }

        @Override // org.jetbrains.kotlin.types.TypeUnifier.UnificationResult
        public boolean isSuccess() {
            return this.success;
        }

        public void fail() {
            this.success = false;
        }

        @Override // org.jetbrains.kotlin.types.TypeUnifier.UnificationResult
        public Map<TypeConstructor, TypeProjection> getSubstitution() {
            Map<TypeConstructor, TypeProjection> map = this.substitution;
            if (map == null) {
                $$$reportNull$$$0(0);
            }
            return map;
        }

        public void put(TypeConstructor typeConstructor, TypeProjection typeProjection) {
            TypeProjection typeProjectionPut;
            if (this.failedVariables.contains(typeConstructor) || (typeProjectionPut = this.substitution.put(typeConstructor, typeProjection)) == null || typeProjectionPut.equals(typeProjection)) {
                return;
            }
            this.substitution.remove(typeConstructor);
            this.failedVariables.add(typeConstructor);
            fail();
        }
    }
}
