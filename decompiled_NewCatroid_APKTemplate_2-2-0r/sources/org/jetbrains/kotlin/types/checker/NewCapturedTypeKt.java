package org.jetbrains.kotlin.types.checker;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypeBoundsChecker;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a \u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\tH\u0002\u001a\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u001a\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0010\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u0004\u001a\u001a\u0010\u000f\u001a\u00020\u000b*\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002¨\u0006\u0011"}, d2 = {"captureArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/UnwrappedType;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureArgumentsForIntersectionType", "Lorg/jetbrains/kotlin/types/checker/CapturedArguments;", "Lorg/jetbrains/kotlin/types/KotlinType;", "captureFromArguments", "Lorg/jetbrains/kotlin/types/SimpleType;", "captureFromExpression", "prepareArgumentTypeRegardingCaptureTypes", "argumentType", "replaceArguments", "arguments", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NewCapturedTypeKt {
    public static final UnwrappedType prepareArgumentTypeRegardingCaptureTypes(UnwrappedType argumentType) {
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        if (argumentType instanceof NewCapturedType) {
            return null;
        }
        return captureFromExpression(argumentType);
    }

    public static final UnwrappedType captureFromExpression(UnwrappedType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type.getConstructor() instanceof IntersectionTypeConstructor)) {
            return captureFromArguments(type, CaptureStatus.FROM_EXPRESSION);
        }
        List<CapturedArguments> listCaptureArgumentsForIntersectionType = captureArgumentsForIntersectionType(type);
        if (listCaptureArgumentsForIntersectionType == null) {
            return null;
        }
        if (type instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) type;
            return KotlinTypeFactory.flexibleType(IntersectionTypeKt.intersectTypes((List<? extends SimpleType>) captureFromExpression$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, flexibleType.getLowerBound())).makeNullableAsSpecified(flexibleType.getLowerBound().getIsMarkedNullable()), IntersectionTypeKt.intersectTypes((List<? extends SimpleType>) captureFromExpression$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, flexibleType.getUpperBound())).makeNullableAsSpecified(flexibleType.getUpperBound().getIsMarkedNullable()));
        }
        return IntersectionTypeKt.intersectTypes((List<? extends SimpleType>) captureFromExpression$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, type)).makeNullableAsSpecified(type.getIsMarkedNullable());
    }

    private static final List<TypeProjection> captureFromExpression$findCorrespondingCapturedArgumentsForType(List<CapturedArguments> list, KotlinType kotlinType) {
        Object next;
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((CapturedArguments) next).isSuitableForType(kotlinType));
        CapturedArguments capturedArguments = (CapturedArguments) next;
        if (capturedArguments != null) {
            return capturedArguments.getCapturedArguments();
        }
        return null;
    }

    private static final List<SimpleType> captureFromExpression$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(List<CapturedArguments> list, UnwrappedType unwrappedType) {
        SimpleType simpleTypeReplaceArguments;
        if (unwrappedType.getConstructor() instanceof IntersectionTypeConstructor) {
            Collection<KotlinType> collectionMo7438getSupertypes = unwrappedType.getConstructor().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            Collection<KotlinType> collection = collectionMo7438getSupertypes;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            for (KotlinType kotlinType : collection) {
                Intrinsics.checkNotNull(kotlinType);
                List<TypeProjection> listCaptureFromExpression$findCorrespondingCapturedArgumentsForType = captureFromExpression$findCorrespondingCapturedArgumentsForType(list, kotlinType);
                if (listCaptureFromExpression$findCorrespondingCapturedArgumentsForType == null) {
                    simpleTypeReplaceArguments = TypeSubstitutionKt.asSimpleType(kotlinType);
                } else {
                    simpleTypeReplaceArguments = replaceArguments(kotlinType.unwrap(), listCaptureFromExpression$findCorrespondingCapturedArgumentsForType);
                }
                arrayList.add(simpleTypeReplaceArguments);
            }
            return arrayList;
        }
        UnwrappedType unwrappedType2 = unwrappedType;
        List<TypeProjection> listCaptureFromExpression$findCorrespondingCapturedArgumentsForType2 = captureFromExpression$findCorrespondingCapturedArgumentsForType(list, unwrappedType2);
        if (listCaptureFromExpression$findCorrespondingCapturedArgumentsForType2 == null) {
            return CollectionsKt.listOf(TypeSubstitutionKt.asSimpleType(unwrappedType2));
        }
        return CollectionsKt.listOf(replaceArguments(unwrappedType.unwrap(), listCaptureFromExpression$findCorrespondingCapturedArgumentsForType2));
    }

    public static final SimpleType captureFromArguments(SimpleType type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        SimpleType simpleType = type;
        List<TypeProjection> listCaptureArguments = captureArguments(simpleType, status);
        if (listCaptureArguments != null) {
            return replaceArguments(simpleType, listCaptureArguments);
        }
        return null;
    }

    private static final Collection<KotlinType> captureArgumentsForIntersectionType$getTypesToCapture(KotlinType kotlinType) {
        if (!(kotlinType.getConstructor() instanceof IntersectionTypeConstructor)) {
            return CollectionsKt.listOf(kotlinType);
        }
        Collection<KotlinType> collectionMo7438getSupertypes = kotlinType.getConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return collectionMo7438getSupertypes;
    }

    private static final List<CapturedArguments> captureArgumentsForIntersectionType(KotlinType kotlinType) {
        ArrayList arrayListMo7438getSupertypes;
        if (kotlinType instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) kotlinType;
            List listPlus = CollectionsKt.plus((Collection) captureArgumentsForIntersectionType$getTypesToCapture(flexibleType.getLowerBound()), (Iterable) captureArgumentsForIntersectionType$getTypesToCapture(flexibleType.getUpperBound()));
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            for (Object obj : listPlus) {
                KotlinType kotlinType2 = (KotlinType) obj;
                FlexibleTypeBoundsChecker flexibleTypeBoundsChecker = FlexibleTypeBoundsChecker.INSTANCE;
                Intrinsics.checkNotNull(kotlinType2);
                Object baseBoundFqNameByMutability = flexibleTypeBoundsChecker.getBaseBoundFqNameByMutability(kotlinType2);
                if (baseBoundFqNameByMutability == null) {
                    baseBoundFqNameByMutability = kotlinType2.getConstructor();
                }
                if (hashSet.add(TuplesKt.to(baseBoundFqNameByMutability, kotlinType2.getArguments()))) {
                    arrayList.add(obj);
                }
            }
            arrayListMo7438getSupertypes = arrayList;
        } else {
            arrayListMo7438getSupertypes = kotlinType.getConstructor().mo7438getSupertypes();
        }
        Intrinsics.checkNotNull(arrayListMo7438getSupertypes);
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = arrayListMo7438getSupertypes.iterator();
        boolean z = false;
        while (true) {
            CapturedArguments capturedArguments = null;
            if (!it.hasNext()) {
                break;
            }
            KotlinType kotlinType3 = (KotlinType) it.next();
            List<TypeProjection> listCaptureArguments = captureArguments(kotlinType3.unwrap(), CaptureStatus.FROM_EXPRESSION);
            if (listCaptureArguments != null) {
                Intrinsics.checkNotNull(kotlinType3);
                capturedArguments = new CapturedArguments(listCaptureArguments, kotlinType3);
                z = true;
            }
            if (capturedArguments != null) {
                arrayList2.add(capturedArguments);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (z) {
            return arrayList3;
        }
        return null;
    }

    private static final UnwrappedType captureFromArguments(UnwrappedType unwrappedType, CaptureStatus captureStatus) {
        List<TypeProjection> listCaptureArguments = captureArguments(unwrappedType, captureStatus);
        if (listCaptureArguments == null) {
            return null;
        }
        if (unwrappedType instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedType;
            return KotlinTypeFactory.flexibleType(replaceArguments(flexibleType.getLowerBound(), listCaptureArguments), replaceArguments(flexibleType.getUpperBound(), listCaptureArguments));
        }
        return replaceArguments(unwrappedType, listCaptureArguments);
    }

    private static final SimpleType replaceArguments(UnwrappedType unwrappedType, List<? extends TypeProjection> list) {
        return KotlinTypeFactory.simpleType$default(unwrappedType.getAttributes(), unwrappedType.getConstructor(), list, unwrappedType.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
    }

    private static final List<TypeProjection> captureArguments(UnwrappedType unwrappedType, CaptureStatus captureStatus) {
        if (unwrappedType.getArguments().size() != unwrappedType.getConstructor().getParameters().size()) {
            return null;
        }
        List<TypeProjection> arguments = unwrappedType.getArguments();
        List<TypeProjection> list = arguments;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((TypeProjection) it.next()).getProjectionKind() != Variance.INVARIANT) {
                    List<TypeParameterDescriptor> parameters = unwrappedType.getConstructor().getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                    List<Pair> listZip = CollectionsKt.zip(list, parameters);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
                    for (Pair pair : listZip) {
                        TypeProjection typeProjectionAsTypeProjection = (TypeProjection) pair.component1();
                        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) pair.component2();
                        if (typeProjectionAsTypeProjection.getProjectionKind() != Variance.INVARIANT) {
                            UnwrappedType unwrappedTypeUnwrap = (typeProjectionAsTypeProjection.isStarProjection() || typeProjectionAsTypeProjection.getProjectionKind() != Variance.IN_VARIANCE) ? null : typeProjectionAsTypeProjection.getNullableAnyType().unwrap();
                            Intrinsics.checkNotNull(typeParameterDescriptor);
                            typeProjectionAsTypeProjection = TypeUtilsKt.asTypeProjection(new NewCapturedType(captureStatus, unwrappedTypeUnwrap, typeProjectionAsTypeProjection, typeParameterDescriptor));
                        }
                        arrayList.add(typeProjectionAsTypeProjection);
                    }
                    ArrayList arrayList2 = arrayList;
                    TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.INSTANCE.create(unwrappedType.getConstructor(), arrayList2).buildSubstitutor();
                    int size = arguments.size();
                    for (int i = 0; i < size; i++) {
                        TypeProjection typeProjection = arguments.get(i);
                        TypeProjection typeProjection2 = (TypeProjection) arrayList2.get(i);
                        if (typeProjection.getProjectionKind() != Variance.INVARIANT) {
                            List<KotlinType> upperBounds = unwrappedType.getConstructor().getParameters().get(i).getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                            ArrayList arrayList3 = new ArrayList();
                            Iterator<T> it2 = upperBounds.iterator();
                            while (it2.hasNext()) {
                                arrayList3.add(KotlinTypePreparator.Default.INSTANCE.prepareType((KotlinTypeMarker) typeSubstitutorBuildSubstitutor.safeSubstitute((KotlinType) it2.next(), Variance.INVARIANT).unwrap()));
                            }
                            ArrayList arrayList4 = arrayList3;
                            if (!typeProjection.isStarProjection() && typeProjection.getProjectionKind() == Variance.OUT_VARIANCE) {
                                arrayList4.add(KotlinTypePreparator.Default.INSTANCE.prepareType((KotlinTypeMarker) typeProjection.getNullableAnyType().unwrap()));
                            }
                            KotlinType type = typeProjection2.getNullableAnyType();
                            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
                            ((NewCapturedType) type).getConstructor().initializeSupertypes(arrayList4);
                        }
                    }
                    return arrayList2;
                }
            }
        }
        return null;
    }
}
