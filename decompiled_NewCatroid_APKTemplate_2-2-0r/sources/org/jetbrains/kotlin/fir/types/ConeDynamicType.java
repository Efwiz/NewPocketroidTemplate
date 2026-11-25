package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00072\u00020\u00012\u00020\u0002:\u0001\u0007B\u0017\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeDynamicType;", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "lowerBound", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "upperBound", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeDynamicType extends ConeFlexibleType implements DynamicTypeMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: ConeTypes.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeDynamicType$Companion;", "", "()V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @DynamicTypeConstructor
    public ConeDynamicType(ConeSimpleKotlinType lowerBound, ConeSimpleKotlinType upperBound) {
        super(lowerBound, upperBound);
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
    }
}
