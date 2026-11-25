package org.jetbrains.kotlin.fir.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: AbstractAnnotationDeserializerWithTypeAnnotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/AbstractAnnotationDeserializerWithTypeAnnotations;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "loadTypeAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "typeProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "loadTypeParameterAnnotations", "typeParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AbstractAnnotationDeserializerWithTypeAnnotations extends AbstractAnnotationDeserializer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractAnnotationDeserializerWithTypeAnnotations(FirSession session, SerializerExtensionProtocol protocol) {
        super(session, protocol);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadTypeAnnotations(ProtoBuf.Type typeProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeProto, "typeProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) typeProto.getExtension(getProtocol().getTypeAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractAnnotationDeserializer.deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadTypeParameterAnnotations(ProtoBuf.TypeParameter typeParameterProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeParameterProto, "typeParameterProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) typeParameterProto.getExtension(getProtocol().getTypeParameterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractAnnotationDeserializer.deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }
}
