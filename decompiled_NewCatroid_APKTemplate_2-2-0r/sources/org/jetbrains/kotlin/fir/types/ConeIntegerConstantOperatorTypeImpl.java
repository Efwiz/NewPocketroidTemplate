package org.jetbrains.kotlin.fir.types;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: ConeIntegerLiteralTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0012\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerConstantOperatorTypeImpl;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerConstantOperatorType;", "isUnsigned", "", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(ZLorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "possibleTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getPossibleTypes", "()Ljava/util/Collection;", "supertypes", "", "getSupertypes", "()Ljava/util/List;", "supertypes$delegate", "Lkotlin/Lazy;", "getApproximatedType", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeIntegerConstantOperatorTypeImpl extends ConeIntegerConstantOperatorType {
    private final Collection<ConeClassLikeType> possibleTypes;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final Lazy supertypes;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeIntegerConstantOperatorTypeImpl(boolean z, ConeNullability nullability) {
        Set of;
        super(z, nullability);
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        if (!z) {
            of = SetsKt.setOf((Object[]) new ConeClassLikeType[]{ConeIntegerLiteralTypeExtensions.INSTANCE.createClassLikeType(StandardClassIds.INSTANCE.getInt()), ConeIntegerLiteralTypeExtensions.INSTANCE.createClassLikeType(StandardClassIds.INSTANCE.getLong())});
        } else if (z) {
            of = SetsKt.setOf((Object[]) new ConeClassLikeType[]{ConeIntegerLiteralTypeExtensions.INSTANCE.createClassLikeType(StandardClassIds.INSTANCE.getUInt()), ConeIntegerLiteralTypeExtensions.INSTANCE.createClassLikeType(StandardClassIds.INSTANCE.getULong())});
        } else {
            throw new NoWhenBranchMatchedException();
        }
        this.possibleTypes = of;
        this.supertypes = LazyKt.lazy(new Function0<List<? extends ConeClassLikeType>>() { // from class: org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorTypeImpl$supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ConeClassLikeType> invoke() {
                return ConeIntegerLiteralTypeExtensions.INSTANCE.createSupertypeList(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public Collection<ConeClassLikeType> getPossibleTypes() {
        return this.possibleTypes;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public List<ConeClassLikeType> getSupertypes() {
        return (List) this.supertypes.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public ConeClassLikeType getApproximatedType(ConeKotlinType expectedType) {
        return ConeIntegerLiteralTypeExtensions.INSTANCE.getApproximatedTypeImpl(this, expectedType);
    }
}
