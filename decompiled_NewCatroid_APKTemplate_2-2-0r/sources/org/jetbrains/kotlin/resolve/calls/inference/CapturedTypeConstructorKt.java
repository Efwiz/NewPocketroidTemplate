package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DelegatedTypeSubstitution;
import org.jetbrains.kotlin.types.IndexedParametersSubstitution;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.LazyWrappedType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: CapturedTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u0003*\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0001\u001a\u0014\u0010\t\u001a\u00020\n*\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\b¨\u0006\f"}, d2 = {"createCapturedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "createCapturedIfNeeded", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "isCaptured", "", "wrapWithCapturingSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "needApproximation", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CapturedTypeConstructorKt {
    public static final KotlinType createCapturedType(TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return new CapturedType(typeProjection, null, false, null, 14, null);
    }

    public static final boolean isCaptured(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.getConstructor() instanceof CapturedTypeConstructor;
    }

    public static /* synthetic */ TypeSubstitution wrapWithCapturingSubstitution$default(TypeSubstitution typeSubstitution, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return wrapWithCapturingSubstitution(typeSubstitution, z);
    }

    public static final TypeSubstitution wrapWithCapturingSubstitution(TypeSubstitution typeSubstitution, final boolean z) {
        Intrinsics.checkNotNullParameter(typeSubstitution, "<this>");
        if (typeSubstitution instanceof IndexedParametersSubstitution) {
            IndexedParametersSubstitution indexedParametersSubstitution = (IndexedParametersSubstitution) typeSubstitution;
            TypeParameterDescriptor[] parameters = indexedParametersSubstitution.getParameters();
            List<Pair> listZip = ArraysKt.zip(indexedParametersSubstitution.getArguments(), indexedParametersSubstitution.getParameters());
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                arrayList.add(createCapturedIfNeeded((TypeProjection) pair.getFirst(), (TypeParameterDescriptor) pair.getSecond()));
            }
            return new IndexedParametersSubstitution(parameters, (TypeProjection[]) arrayList.toArray(new TypeProjection[0]), z);
        }
        return new DelegatedTypeSubstitution(typeSubstitution) { // from class: org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt.wrapWithCapturingSubstitution.2
            @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: approximateContravariantCapturedTypes, reason: from getter */
            public boolean getApproximateContravariantCapturedTypes() {
                return z;
            }

            @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                TypeProjection typeProjectionMo7475get = super.mo7475get(key);
                if (typeProjectionMo7475get == null) {
                    return null;
                }
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = key.getConstructor().getThis$0();
                return CapturedTypeConstructorKt.createCapturedIfNeeded(typeProjectionMo7475get, classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeProjection createCapturedIfNeeded(final TypeProjection typeProjection, TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null || typeProjection.getProjectionKind() == Variance.INVARIANT) {
            return typeProjection;
        }
        if (typeParameterDescriptor.getVariance() == typeProjection.getProjectionKind()) {
            if (typeProjection.isStarProjection()) {
                StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
                Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
                return new TypeProjectionImpl(new LazyWrappedType(NO_LOCKS, new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt.createCapturedIfNeeded.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KotlinType invoke() {
                        KotlinType type = typeProjection.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        return type;
                    }
                }));
            }
            return new TypeProjectionImpl(typeProjection.getType());
        }
        return new TypeProjectionImpl(createCapturedType(typeProjection));
    }
}
