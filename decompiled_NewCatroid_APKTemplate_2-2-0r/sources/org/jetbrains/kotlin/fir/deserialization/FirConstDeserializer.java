package org.jetbrains.kotlin.fir.deserialization;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: FirConstDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "constantCache", "", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getConstantCache", "()Ljava/util/Map;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "loadConstant", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "callableId", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirConstDeserializer {
    private final Map<CallableId, FirExpression> constantCache;
    private final SerializerExtensionProtocol protocol;
    private final FirSession session;

    public FirConstDeserializer(FirSession session, SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.session = session;
        this.protocol = protocol;
        this.constantCache = new LinkedHashMap();
    }

    public final FirSession getSession() {
        return this.session;
    }

    protected final Map<CallableId, FirExpression> getConstantCache() {
        return this.constantCache;
    }

    public FirExpression loadConstant(ProtoBuf.Property propertyProto, CallableId callableId, NameResolver nameResolver) {
        FirExpression firExpressionBuildFirConstant;
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        if (!Flags.HAS_CONSTANT.get(propertyProto.getFlags()).booleanValue()) {
            return null;
        }
        FirExpression firExpression = this.constantCache.get(callableId);
        if (firExpression != null) {
            return firExpression;
        }
        ProtoBuf.Annotation.Argument.Value value = (ProtoBuf.Annotation.Argument.Value) ProtoBufUtilKt.getExtensionOrNull(propertyProto, this.protocol.getCompileTimeValue());
        if (value == null || (firExpressionBuildFirConstant = FirConstDeserializerKt.buildFirConstant(value, null, value.getType().name(), nameResolver)) == null) {
            return null;
        }
        this.constantCache.put(callableId, firExpressionBuildFirConstant);
        return firExpressionBuildFirConstant;
    }
}
