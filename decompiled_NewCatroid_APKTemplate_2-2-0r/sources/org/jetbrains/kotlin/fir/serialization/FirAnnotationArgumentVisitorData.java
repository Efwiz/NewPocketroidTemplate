package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: FirAnnotationArgumentVisitorData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationArgumentVisitorData;", "", "serializer", "Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value$Builder;", "(Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value$Builder;)V", "getBuilder", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value$Builder;", "getSerializer", "()Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationArgumentVisitorData {
    private final ProtoBuf.Annotation.Argument.Value.Builder builder;
    private final FirAnnotationSerializer serializer;

    public FirAnnotationArgumentVisitorData(FirAnnotationSerializer serializer, ProtoBuf.Annotation.Argument.Value.Builder builder) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.serializer = serializer;
        this.builder = builder;
    }

    public final FirAnnotationSerializer getSerializer() {
        return this.serializer;
    }

    public final ProtoBuf.Annotation.Argument.Value.Builder getBuilder() {
        return this.builder;
    }

    public final FirElementAwareStringTable getStringTable() {
        return this.serializer.getStringTable();
    }
}
