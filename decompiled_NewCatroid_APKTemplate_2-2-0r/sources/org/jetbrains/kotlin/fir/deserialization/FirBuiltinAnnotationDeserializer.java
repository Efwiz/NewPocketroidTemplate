package org.jetbrains.kotlin.fir.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: FirBuiltinAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirBuiltinAnnotationDeserializer;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "loadTypeAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "typeProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBuiltinAnnotationDeserializer extends AbstractAnnotationDeserializer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBuiltinAnnotationDeserializer(FirSession session) {
        super(session, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadTypeAnnotations(ProtoBuf.Type typeProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeProto, "typeProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        if (!Flags.HAS_ANNOTATIONS.get(typeProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
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
}
