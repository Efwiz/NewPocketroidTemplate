package org.jetbrains.kotlin.fir.session;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: MetadataBasedAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/MetadataBasedAnnotationDeserializer;", "Lorg/jetbrains/kotlin/fir/session/AbstractAnnotationDeserializerWithTypeAnnotations;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataBasedAnnotationDeserializer extends AbstractAnnotationDeserializerWithTypeAnnotations {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataBasedAnnotationDeserializer(FirSession session) {
        super(session, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
    }
}
