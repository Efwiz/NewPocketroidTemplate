package org.jetbrains.kotlin.fir.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirDeserializationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationExtension;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "createConstDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "serializerExtensionProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "loadModuleName", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "configureDeserializedClass", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDeserializationExtension implements FirSessionComponent {
    private final FirSession session;

    public void configureDeserializedClass(FirRegularClassBuilder firRegularClassBuilder, ClassId classId) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    public FirConstDeserializer createConstDeserializer(DeserializedContainerSource containerSource, FirSession session, SerializerExtensionProtocol serializerExtensionProtocol) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(serializerExtensionProtocol, "serializerExtensionProtocol");
        return null;
    }

    public String loadModuleName(ProtoBuf.Class classProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return null;
    }

    public FirDeserializationExtension(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }
}
