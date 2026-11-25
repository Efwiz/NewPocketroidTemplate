package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.CompoundConstraintPosition;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typesApproximation.ApproximationBounds;
import org.jetbrains.kotlin.types.typesApproximation.CapturedTypeApproximationKt;

/* JADX INFO: compiled from: constraintIncorporation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u001a\u001c\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0002\u001a\u0012\u0010\t\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\n\u001a\u00020\u0004¨\u0006\u000b"}, d2 = {"addConstraintFromBounds", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl;", "old", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", PsiKeyword.NEW, "generateNewBound", "bound", SchemaSymbols.ATTVAL_SUBSTITUTION, "incorporateBound", "newBound", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintIncorporationKt {

    /* JADX INFO: compiled from: constraintIncorporation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeBounds.BoundKind.values().length];
            try {
                iArr[TypeBounds.BoundKind.EXACT_BOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeBounds.BoundKind.UPPER_BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeBounds.BoundKind.LOWER_BOUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void incorporateBound(ConstraintSystemBuilderImpl constraintSystemBuilderImpl, TypeBounds.Bound newBound) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilderImpl, "<this>");
        Intrinsics.checkNotNullParameter(newBound, "newBound");
        TypeVariable typeVariable = newBound.getTypeVariable();
        TypeBoundsImpl typeBounds$frontend = constraintSystemBuilderImpl.getTypeBounds$frontend(typeVariable);
        int size = typeBounds$frontend.getBounds().size();
        for (int i = 0; i < size; i++) {
            TypeBounds.Bound bound = typeBounds$frontend.getBounds().get(i);
            Intrinsics.checkNotNullExpressionValue(bound, "get(...)");
            addConstraintFromBounds(constraintSystemBuilderImpl, bound, newBound);
        }
        List<TypeBounds.Bound> listEmptyList = constraintSystemBuilderImpl.getUsedInBounds$frontend().get(typeVariable);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        int size2 = listEmptyList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            generateNewBound(constraintSystemBuilderImpl, listEmptyList.get(i2), newBound);
        }
        KotlinType constrainingType = newBound.getConstrainingType();
        if (constraintSystemBuilderImpl.isMyTypeVariable$frontend(constrainingType)) {
            ConstraintContext constraintContext = new ConstraintContext(newBound.getPosition(), newBound.getDerivedFrom(), false, false, 12, null);
            TypeVariable myTypeVariable$frontend = constraintSystemBuilderImpl.getMyTypeVariable$frontend(constrainingType);
            Intrinsics.checkNotNull(myTypeVariable$frontend);
            constraintSystemBuilderImpl.addBound$frontend(myTypeVariable$frontend, typeVariable.getType(), TypeBoundsKt.reverse(newBound.getKind()), constraintContext);
            return;
        }
        Iterator<T> it = constraintSystemBuilderImpl.getNestedTypeVariables$frontend(constrainingType).iterator();
        while (it.hasNext()) {
            ArrayList<TypeBounds.Bound> bounds = constraintSystemBuilderImpl.getTypeBounds$frontend((TypeVariable) it.next()).getBounds();
            int size3 = bounds.size();
            for (int i3 = 0; i3 < size3; i3++) {
                TypeBounds.Bound bound2 = bounds.get(i3);
                Intrinsics.checkNotNullExpressionValue(bound2, "get(...)");
                generateNewBound(constraintSystemBuilderImpl, newBound, bound2);
            }
        }
    }

    private static final void addConstraintFromBounds(ConstraintSystemBuilderImpl constraintSystemBuilderImpl, TypeBounds.Bound bound, TypeBounds.Bound bound2) {
        if (Intrinsics.areEqual(bound, bound2)) {
            return;
        }
        KotlinType constrainingType = bound.getConstrainingType();
        KotlinType constrainingType2 = bound2.getConstrainingType();
        ConstraintContext constraintContext = new ConstraintContext(new CompoundConstraintPosition(bound.getPosition(), bound2.getPosition()), SetsKt.plus((Set) bound.getDerivedFrom(), (Iterable) bound2.getDerivedFrom()), false, false, 12, null);
        if (bound.getKind().ordinal() < bound2.getKind().ordinal()) {
            constraintSystemBuilderImpl.addConstraint(ConstraintSystemBuilderImpl.ConstraintKind.SUB_TYPE, constrainingType, constrainingType2, constraintContext);
            return;
        }
        if (bound.getKind().ordinal() > bound2.getKind().ordinal()) {
            constraintSystemBuilderImpl.addConstraint(ConstraintSystemBuilderImpl.ConstraintKind.SUB_TYPE, constrainingType2, constrainingType, constraintContext);
        } else if (bound.getKind() == bound2.getKind() && bound.getKind() == TypeBounds.BoundKind.EXACT_BOUND) {
            constraintSystemBuilderImpl.addConstraint(ConstraintSystemBuilderImpl.ConstraintKind.EQUAL, constrainingType, constrainingType2, constraintContext);
        }
    }

    private static final void generateNewBound(ConstraintSystemBuilderImpl constraintSystemBuilderImpl, TypeBounds.Bound bound, TypeBounds.Bound bound2) {
        CapturedType constrainingType;
        if (bound == bound2) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[bound2.getKind().ordinal()];
        if (i == 1) {
            constrainingType = bound2.getConstrainingType();
        } else if (i == 2) {
            constrainingType = new CapturedType(new TypeProjectionImpl(Variance.OUT_VARIANCE, bound2.getConstrainingType()), null, false, null, 14, null);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            constrainingType = new CapturedType(new TypeProjectionImpl(Variance.IN_VARIANCE, bound2.getConstrainingType()), null, false, null, 14, null);
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create((Map<TypeConstructor, TypeProjection>) MapsKt.mapOf(TuplesKt.to(bound2.getTypeVariable().getType().getConstructor(), new TypeProjectionImpl(constrainingType))));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        KotlinType kotlinTypeSubstitute = typeSubstitutorCreate.substitute(bound.getConstrainingType(), Variance.INVARIANT);
        if (kotlinTypeSubstitute == null) {
            return;
        }
        CompoundConstraintPosition compoundConstraintPosition = new CompoundConstraintPosition(bound.getPosition(), bound2.getPosition());
        if (bound2.getKind() == TypeBounds.BoundKind.EXACT_BOUND) {
            generateNewBound$addNewBound(constraintSystemBuilderImpl, bound, bound2, compoundConstraintPosition, kotlinTypeSubstitute, bound.getKind());
            return;
        }
        ApproximationBounds<KotlinType> approximationBoundsApproximateCapturedTypes = CapturedTypeApproximationKt.approximateCapturedTypes(kotlinTypeSubstitute);
        if (generateNewBound$containsConstrainingTypeWithoutProjection(approximationBoundsApproximateCapturedTypes.getUpper(), bound2) && bound.getKind() != TypeBounds.BoundKind.LOWER_BOUND) {
            generateNewBound$addNewBound(constraintSystemBuilderImpl, bound, bound2, compoundConstraintPosition, approximationBoundsApproximateCapturedTypes.getUpper(), TypeBounds.BoundKind.UPPER_BOUND);
        }
        if (!generateNewBound$containsConstrainingTypeWithoutProjection(approximationBoundsApproximateCapturedTypes.getLower(), bound2) || bound.getKind() == TypeBounds.BoundKind.UPPER_BOUND) {
            return;
        }
        generateNewBound$addNewBound(constraintSystemBuilderImpl, bound, bound2, compoundConstraintPosition, approximationBoundsApproximateCapturedTypes.getLower(), TypeBounds.BoundKind.LOWER_BOUND);
    }

    private static final void generateNewBound$addNewBound(ConstraintSystemBuilderImpl constraintSystemBuilderImpl, TypeBounds.Bound bound, TypeBounds.Bound bound2, CompoundConstraintPosition compoundConstraintPosition, KotlinType kotlinType, TypeBounds.BoundKind boundKind) {
        if (constraintSystemBuilderImpl.getNestedTypeVariables$frontend(kotlinType).contains(bound.getTypeVariable())) {
            return;
        }
        HashSet hashSet = new HashSet(SetsKt.plus((Set) bound.getDerivedFrom(), (Iterable) bound2.getDerivedFrom()));
        if (hashSet.contains(bound2.getTypeVariable())) {
            return;
        }
        hashSet.add(bound2.getTypeVariable());
        constraintSystemBuilderImpl.addBound$frontend(bound.getTypeVariable(), kotlinType, boundKind, new ConstraintContext(compoundConstraintPosition, hashSet, false, false, 12, null));
    }

    private static final boolean generateNewBound$containsConstrainingTypeWithoutProjection(KotlinType kotlinType, TypeBounds.Bound bound) {
        List<TypeProjection> nestedArguments = ConstraintSystemUtilsKt.getNestedArguments(kotlinType);
        if ((nestedArguments instanceof Collection) && nestedArguments.isEmpty()) {
            return false;
        }
        for (TypeProjection typeProjection : nestedArguments) {
            if (Intrinsics.areEqual(typeProjection.getNullableAnyType().getConstructor(), bound.getConstrainingType().getConstructor()) && typeProjection.getProjectionKind() == Variance.INVARIANT) {
                return true;
            }
        }
        return false;
    }
}
