package org.jetbrains.kotlin.resolve.checkers;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: EmptyIntersectionTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u001e\u0010\n\u001a\u0004\u0018\u00010\u0004*\u00020\u00062\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0002J\u001c\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u0014\u001a\u00020\u000e*\u00020\u00062\u0006\u0010\u0015\u001a\u00020\tH\u0002¨\u0006\u0016²\u0006\n\u0010\u0017\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n\u0010\u0010\u001a\u00020\u0011X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeChecker;", "", "()V", "computeEmptyIntersectionEmptiness", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "computeByHavingCommonSubtype", "first", "second", "isSubtypeOfIgnoringArguments", "", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "typeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "otherConstructorMarker", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "mayCauseEmptyIntersection", ModuleXmlParser.TYPE, "compiler.common", "firstSubstitutedType"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyIntersectionTypeChecker {
    public static final EmptyIntersectionTypeChecker INSTANCE = new EmptyIntersectionTypeChecker();

    private EmptyIntersectionTypeChecker() {
    }

    public final EmptyIntersectionTypeInfo computeEmptyIntersectionEmptiness(final TypeSystemInferenceExtensionContext context, Collection<? extends KotlinTypeMarker> types) {
        EmptyIntersectionTypeInfo emptyIntersectionTypeInfoComputeByHavingCommonSubtype;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(types, "types");
        EmptyIntersectionTypeInfo emptyIntersectionTypeInfo = null;
        if (types.isEmpty()) {
            return null;
        }
        List list = CollectionsKt.toList(types);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            final KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) list.get(i);
            if (INSTANCE.mayCauseEmptyIntersection(context, kotlinTypeMarker)) {
                Lazy lazy = LazyKt.lazy(new Function0<KotlinTypeMarker>() { // from class: org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeChecker$computeEmptyIntersectionEmptiness$1$firstSubstitutedType$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KotlinTypeMarker invoke() {
                        return context.eraseContainingTypeParameters(kotlinTypeMarker);
                    }
                });
                int size2 = list.size();
                for (int i2 = i + 1; i2 < size2; i2++) {
                    KotlinTypeMarker kotlinTypeMarker2 = (KotlinTypeMarker) list.get(i2);
                    EmptyIntersectionTypeChecker emptyIntersectionTypeChecker = INSTANCE;
                    if (emptyIntersectionTypeChecker.mayCauseEmptyIntersection(context, kotlinTypeMarker2)) {
                        KotlinTypeMarker kotlinTypeMarkerEraseContainingTypeParameters = context.eraseContainingTypeParameters(kotlinTypeMarker2);
                        if ((emptyIntersectionTypeChecker.mayCauseEmptyIntersection(context, kotlinTypeMarkerEraseContainingTypeParameters) || emptyIntersectionTypeChecker.mayCauseEmptyIntersection(context, computeEmptyIntersectionEmptiness$lambda$1$lambda$0(lazy))) && (emptyIntersectionTypeInfoComputeByHavingCommonSubtype = emptyIntersectionTypeChecker.computeByHavingCommonSubtype(context, computeEmptyIntersectionEmptiness$lambda$1$lambda$0(lazy), kotlinTypeMarkerEraseContainingTypeParameters)) != null) {
                            if (emptyIntersectionTypeInfoComputeByHavingCommonSubtype.getKind().getIsDefinitelyEmpty()) {
                                return emptyIntersectionTypeInfoComputeByHavingCommonSubtype;
                            }
                            if (!emptyIntersectionTypeInfoComputeByHavingCommonSubtype.getKind().getIsDefinitelyEmpty()) {
                                emptyIntersectionTypeInfo = emptyIntersectionTypeInfoComputeByHavingCommonSubtype;
                            }
                        }
                    }
                }
            }
        }
        return emptyIntersectionTypeInfo;
    }

    private static final KotlinTypeMarker computeEmptyIntersectionEmptiness$lambda$1$lambda$0(Lazy<? extends KotlinTypeMarker> lazy) {
        return lazy.getValue();
    }

    private static final List<KotlinTypeMarker> computeByHavingCommonSubtype$extractIntersectionComponentsIfNeeded(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker kotlinTypeMarker) {
        if (typeSystemInferenceExtensionContext.typeConstructor(kotlinTypeMarker) instanceof IntersectionTypeConstructorMarker) {
            return CollectionsKt.toList(typeSystemInferenceExtensionContext.supertypes(typeSystemInferenceExtensionContext.typeConstructor(kotlinTypeMarker)));
        }
        return CollectionsKt.listOf(kotlinTypeMarker);
    }

    private final EmptyIntersectionTypeInfo computeByHavingCommonSubtype(final TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        List listPlus = CollectionsKt.plus((Collection) computeByHavingCommonSubtype$extractIntersectionComponentsIfNeeded(typeSystemInferenceExtensionContext, kotlinTypeMarker), (Iterable) computeByHavingCommonSubtype$extractIntersectionComponentsIfNeeded(typeSystemInferenceExtensionContext, kotlinTypeMarker2));
        Lazy lazy = LazyKt.lazy(new Function0<TypeCheckerState>() { // from class: org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeChecker$computeByHavingCommonSubtype$typeCheckerState$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final TypeCheckerState invoke() {
                return typeSystemInferenceExtensionContext.newTypeCheckerState(true, true);
            }
        });
        int size = listPlus.size();
        EmptyIntersectionTypeInfo emptyIntersectionTypeInfo = null;
        for (int i = 0; i < size; i++) {
            KotlinTypeMarker kotlinTypeMarkerWithNullability = typeSystemInferenceExtensionContext.withNullability((KotlinTypeMarker) listPlus.get(i), false);
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(kotlinTypeMarkerWithNullability);
            if (mayCauseEmptyIntersection(typeSystemInferenceExtensionContext, kotlinTypeMarkerWithNullability)) {
                int size2 = listPlus.size();
                for (int i2 = i + 1; i2 < size2; i2++) {
                    KotlinTypeMarker kotlinTypeMarkerWithNullability2 = typeSystemInferenceExtensionContext.withNullability((KotlinTypeMarker) listPlus.get(i2), false);
                    TypeConstructorMarker typeConstructorMarkerTypeConstructor2 = typeSystemInferenceExtensionContext.typeConstructor(kotlinTypeMarkerWithNullability2);
                    if (mayCauseEmptyIntersection(typeSystemInferenceExtensionContext, kotlinTypeMarkerWithNullability2) && !typeSystemInferenceExtensionContext.areEqualTypeConstructors(typeConstructorMarkerTypeConstructor, typeConstructorMarkerTypeConstructor2) && !isSubtypeOfIgnoringArguments(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(kotlinTypeMarkerWithNullability), computeByHavingCommonSubtype$lambda$2(lazy), typeConstructorMarkerTypeConstructor2) && !isSubtypeOfIgnoringArguments(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(kotlinTypeMarkerWithNullability2), computeByHavingCommonSubtype$lambda$2(lazy), typeConstructorMarkerTypeConstructor)) {
                        if (!typeSystemInferenceExtensionContext.isInterface(typeConstructorMarkerTypeConstructor) && !typeSystemInferenceExtensionContext.isInterface(typeConstructorMarkerTypeConstructor2)) {
                            return new EmptyIntersectionTypeInfo(EmptyIntersectionTypeKind.MULTIPLE_CLASSES, kotlinTypeMarkerWithNullability, kotlinTypeMarkerWithNullability2);
                        }
                        if (typeSystemInferenceExtensionContext.isFinalClassConstructor(typeConstructorMarkerTypeConstructor) || typeSystemInferenceExtensionContext.isFinalClassConstructor(typeConstructorMarkerTypeConstructor2)) {
                            emptyIntersectionTypeInfo = new EmptyIntersectionTypeInfo(EmptyIntersectionTypeKind.FINAL_CLASS_AND_INTERFACE, kotlinTypeMarkerWithNullability, kotlinTypeMarkerWithNullability2);
                        }
                    }
                }
            }
        }
        return emptyIntersectionTypeInfo;
    }

    private static final TypeCheckerState computeByHavingCommonSubtype$lambda$2(Lazy<? extends TypeCheckerState> lazy) {
        return lazy.getValue();
    }

    private final boolean isSubtypeOfIgnoringArguments(SimpleTypeMarker simpleTypeMarker, TypeCheckerState typeCheckerState, TypeConstructorMarker typeConstructorMarker) {
        return !AbstractTypeChecker.INSTANCE.findCorrespondingSupertypes(typeCheckerState, simpleTypeMarker, typeConstructorMarker).isEmpty();
    }

    private final boolean mayCauseEmptyIntersection(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker kotlinTypeMarker) {
        if (typeSystemInferenceExtensionContext.isStubType(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(kotlinTypeMarker)) || typeSystemInferenceExtensionContext.isError(kotlinTypeMarker)) {
            return false;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(kotlinTypeMarker);
        return ((!typeSystemInferenceExtensionContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor) && !typeSystemInferenceExtensionContext.isTypeParameterTypeConstructor(typeConstructorMarkerTypeConstructor)) || typeSystemInferenceExtensionContext.isAnyConstructor(typeConstructorMarkerTypeConstructor) || typeSystemInferenceExtensionContext.isNothingConstructor(typeConstructorMarkerTypeConstructor)) ? false : true;
    }
}
