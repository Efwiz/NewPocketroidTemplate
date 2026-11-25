package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: UpperBoundViolatedInTypealiasConstructorChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/UpperBoundViolatedInTypealiasConstructorChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UpperBoundViolatedInTypealiasConstructorChecker implements CallChecker {
    public static final UpperBoundViolatedInTypealiasConstructorChecker INSTANCE = new UpperBoundViolatedInTypealiasConstructorChecker();

    private UpperBoundViolatedInTypealiasConstructorChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtCallExpression ktCallExpression;
        KtTypeReference typeReference;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = resultingDescriptor instanceof TypeAliasConstructorDescriptor ? (TypeAliasConstructorDescriptor) resultingDescriptor : null;
        if (typeAliasConstructorDescriptor == null || (ktCallExpression = (KtCallExpression) PsiTreeUtil.getParentOfType(reportOn, KtCallExpression.class, true)) == null) {
            return;
        }
        KotlinType returnType = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor().getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        List<TypeProjection> arguments = returnType.getArguments();
        List<TypeParameterDescriptor> parameters = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor().getReturnType().getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        ClassicTypeCheckerState classicTypeCheckerState = new ClassicTypeCheckerState(false, false, null, null, null, 30, null);
        List<TypeParameterDescriptor> list = parameters;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(list);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Pair pair = new Pair(((TypeParameterDescriptor) indexedValue.getValue()).getTypeConstructor(), arguments.get(indexedValue.getIndex()).getNullableAnyType().unwrap());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = new NewTypeSubstitutorByConstructorMap(linkedHashMap);
        List<TypeParameterDescriptor> typeParameters = resolvedCall.get$suspendPropertyDescriptor().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeProjection> arguments2 = typeAliasConstructorDescriptor.getTypeAliasDescriptor().getUnderlyingType().getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments2, 10));
        Iterator<T> it = arguments2.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType());
        }
        ArrayList arrayList2 = arrayList;
        int i = 0;
        for (Pair pair2 : CollectionsKt.zip(arguments, list)) {
            int i2 = i + 1;
            TypeProjection typeProjection = (TypeProjection) pair2.component1();
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) pair2.component2();
            TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) CollectionsKt.getOrNull(typeParameters, i);
            if (!Intrinsics.areEqual(typeParameterDescriptor2 != null ? typeParameterDescriptor2.getDefaultType() : null, CollectionsKt.getOrNull(arrayList2, i))) {
                for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
                    KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    if (!AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerState) classicTypeCheckerState, (KotlinTypeMarker) nullableAnyType, (KotlinTypeMarker) newTypeSubstitutorByConstructorMap.safeSubstitute(kotlinType.unwrap()), false, 8, (Object) null)) {
                        List<KtTypeProjection> typeArguments = ktCallExpression.getTypeArguments();
                        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
                        KtTypeProjection ktTypeProjection = (KtTypeProjection) CollectionsKt.getOrNull(typeArguments, i);
                        if (ktTypeProjection != null && (typeReference = ktTypeProjection.getTypeReference()) != null) {
                            context.getTrace().report(Errors.UPPER_BOUND_VIOLATED_WARNING.on(typeReference, kotlinType, typeProjection.getNullableAnyType()));
                        }
                    }
                }
            }
            i = i2;
        }
    }
}
