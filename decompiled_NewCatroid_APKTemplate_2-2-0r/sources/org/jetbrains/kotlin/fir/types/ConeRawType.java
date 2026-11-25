package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeRawType;", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "lowerBound", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "upperBound", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeRawType extends ConeFlexibleType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ ConeRawType(ConeSimpleKotlinType coneSimpleKotlinType, ConeSimpleKotlinType coneSimpleKotlinType2, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneSimpleKotlinType, coneSimpleKotlinType2);
    }

    /* JADX INFO: compiled from: ConeTypes.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeRawType$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeRawType;", "lowerBound", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "upperBound", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConeRawType create(ConeSimpleKotlinType lowerBound, ConeSimpleKotlinType upperBound) {
            ConeClassLikeTypeImpl coneClassLikeTypeImpl;
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            if (!(lowerBound instanceof ConeClassLikeType) || !(upperBound instanceof ConeClassLikeType)) {
                throw new IllegalArgumentException(("Raw bounds are expected to be class-like types, but " + lowerBound + " and " + upperBound + " were found").toString());
            }
            if (!lowerBound.getAttributes().contains(CompilerConeAttributes.RawType.INSTANCE)) {
                coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(((ConeClassLikeType) lowerBound).getLookupTag(), lowerBound.getTypeArguments(), ConeTypeUtilsKt.isNullable(lowerBound), lowerBound.getAttributes().plus(CompilerConeAttributes.RawType.INSTANCE));
            } else {
                coneClassLikeTypeImpl = (ConeClassLikeType) lowerBound;
            }
            return new ConeRawType(coneClassLikeTypeImpl, upperBound, null);
        }
    }

    private ConeRawType(ConeSimpleKotlinType coneSimpleKotlinType, ConeSimpleKotlinType coneSimpleKotlinType2) {
        super(coneSimpleKotlinType, coneSimpleKotlinType2);
    }
}
