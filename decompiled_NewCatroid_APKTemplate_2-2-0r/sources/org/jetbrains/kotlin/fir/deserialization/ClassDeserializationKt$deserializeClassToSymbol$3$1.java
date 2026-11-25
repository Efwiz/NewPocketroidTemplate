package org.jetbrains.kotlin.fir.deserialization;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: ClassDeserialization.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ClassDeserializationKt$deserializeClassToSymbol$3$1 extends FunctionReference implements Function1<ProtoBuf.Type, ConeSimpleKotlinType> {
    ClassDeserializationKt$deserializeClassToSymbol$3$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "simpleType(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;)Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "simpleType";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirTypeDeserializer.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ConeSimpleKotlinType invoke(ProtoBuf.Type p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((FirTypeDeserializer) this.receiver).simpleType(p0);
    }
}
