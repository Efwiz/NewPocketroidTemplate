package org.jetbrains.kotlin.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorImpl;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstructor;
import org.jetbrains.kotlin.types.AbstractTypePreparator;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: KotlinTypePreparator.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\bH\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "()V", "prepareType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "transformToNewType", "Lorg/jetbrains/kotlin/types/SimpleType;", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinTypePreparator extends AbstractTypePreparator {
    private final SimpleType transformToNewType(SimpleType type) {
        KotlinType type2;
        TypeConstructor constructor = type.getConstructor();
        IntersectionTypeConstructor alternative = null;
        unwrappedTypeUnwrap = null;
        UnwrappedType unwrappedTypeUnwrap = null;
        if (constructor instanceof CapturedTypeConstructorImpl) {
            CapturedTypeConstructorImpl capturedTypeConstructorImpl = (CapturedTypeConstructorImpl) constructor;
            TypeProjection projection = capturedTypeConstructorImpl.getProjection();
            if (projection.getProjectionKind() != Variance.IN_VARIANCE) {
                projection = null;
            }
            if (projection != null && (type2 = projection.getType()) != null) {
                unwrappedTypeUnwrap = type2.unwrap();
            }
            UnwrappedType unwrappedType = unwrappedTypeUnwrap;
            if (capturedTypeConstructorImpl.getNewTypeConstructor() == null) {
                TypeProjection projection2 = capturedTypeConstructorImpl.getProjection();
                Collection<KotlinType> collectionMo7438getSupertypes = capturedTypeConstructorImpl.mo7438getSupertypes();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
                Iterator<T> it = collectionMo7438getSupertypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(((KotlinType) it.next()).unwrap());
                }
                capturedTypeConstructorImpl.setNewTypeConstructor(new NewCapturedTypeConstructor(projection2, arrayList, null, 4, null));
            }
            CaptureStatus captureStatus = CaptureStatus.FOR_SUBTYPING;
            NewCapturedTypeConstructor newTypeConstructor = capturedTypeConstructorImpl.getNewTypeConstructor();
            Intrinsics.checkNotNull(newTypeConstructor);
            return new NewCapturedType(captureStatus, newTypeConstructor, unwrappedType, type.getAttributes(), type.getIsMarkedNullable(), false, 32, null);
        }
        boolean z = false;
        if (constructor instanceof IntegerValueTypeConstructor) {
            Collection<KotlinType> collectionMo7438getSupertypes2 = ((IntegerValueTypeConstructor) constructor).mo7438getSupertypes();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes2, 10));
            Iterator<T> it2 = collectionMo7438getSupertypes2.iterator();
            while (it2.hasNext()) {
                KotlinType kotlinTypeMakeNullableAsSpecified = TypeUtils.makeNullableAsSpecified((KotlinType) it2.next(), type.getIsMarkedNullable());
                Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullableAsSpecified, "makeNullableAsSpecified(...)");
                arrayList2.add(kotlinTypeMakeNullableAsSpecified);
            }
            return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type.getAttributes(), new IntersectionTypeConstructor(arrayList2), CollectionsKt.emptyList(), false, type.getMemberScope());
        }
        if (!(constructor instanceof IntersectionTypeConstructor) || !type.getIsMarkedNullable()) {
            return type;
        }
        IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor) constructor;
        Collection<KotlinType> collectionMo7438getSupertypes3 = intersectionTypeConstructor.mo7438getSupertypes();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes3, 10));
        Iterator<T> it3 = collectionMo7438getSupertypes3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(TypeUtilsKt.makeNullable((KotlinType) it3.next()));
            z = true;
        }
        ArrayList arrayList4 = arrayList3;
        if (z) {
            KotlinType alternative2 = intersectionTypeConstructor.getAlternative();
            alternative = new IntersectionTypeConstructor(arrayList4).setAlternative(alternative2 != null ? TypeUtilsKt.makeNullable(alternative2) : null);
        }
        if (alternative != null) {
            intersectionTypeConstructor = alternative;
        }
        return intersectionTypeConstructor.createType();
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypePreparator
    public UnwrappedType prepareType(KotlinTypeMarker type) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        UnwrappedType unwrappedTypeUnwrap = ((KotlinType) type).unwrap();
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            simpleTypeFlexibleType = transformToNewType((SimpleType) unwrappedTypeUnwrap);
        } else if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType simpleTypeTransformToNewType = transformToNewType(flexibleType.getLowerBound());
            SimpleType simpleTypeTransformToNewType2 = transformToNewType(flexibleType.getUpperBound());
            simpleTypeFlexibleType = (simpleTypeTransformToNewType == flexibleType.getLowerBound() && simpleTypeTransformToNewType2 == flexibleType.getUpperBound()) ? unwrappedTypeUnwrap : KotlinTypeFactory.flexibleType(simpleTypeTransformToNewType, simpleTypeTransformToNewType2);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrappedTypeUnwrap, new AnonymousClass1(this));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.checker.KotlinTypePreparator$prepareType$1, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinTypePreparator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<KotlinTypeMarker, UnwrappedType> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "prepareType(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/UnwrappedType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "prepareType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(KotlinTypePreparator.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final UnwrappedType invoke(KotlinTypeMarker p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((KotlinTypePreparator) this.receiver).prepareType(p0);
        }
    }

    /* JADX INFO: compiled from: KotlinTypePreparator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator$Default;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends KotlinTypePreparator {
        public static final Default INSTANCE = new Default();

        private Default() {
        }
    }
}
