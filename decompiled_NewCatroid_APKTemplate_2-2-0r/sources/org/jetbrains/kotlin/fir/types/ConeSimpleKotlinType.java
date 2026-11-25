package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003\u0082\u0001\u0006\u0004\u0005\u0006\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "()V", "Lorg/jetbrains/kotlin/fir/types/ConeCapturedType;", "Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "Lorg/jetbrains/kotlin/fir/types/ConeIntersectionType;", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeSimpleKotlinType extends ConeKotlinType implements SimpleTypeMarker {
    public /* synthetic */ ConeSimpleKotlinType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ConeSimpleKotlinType() {
        super(null);
    }
}
