package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: typeMapperUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/ReceiverTypeAndTypeParameters;", "", "receiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/types/KotlinType;Ljava/util/List;)V", "getReceiverType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeParameters", "()Ljava/util/List;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverTypeAndTypeParameters {
    private final KotlinType receiverType;
    private final List<TypeParameterDescriptor> typeParameters;

    /* JADX WARN: Multi-variable type inference failed */
    public ReceiverTypeAndTypeParameters(KotlinType receiverType, List<? extends TypeParameterDescriptor> typeParameters) {
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        this.receiverType = receiverType;
        this.typeParameters = typeParameters;
    }

    public final KotlinType getReceiverType() {
        return this.receiverType;
    }

    public final List<TypeParameterDescriptor> getTypeParameters() {
        return this.typeParameters;
    }
}
