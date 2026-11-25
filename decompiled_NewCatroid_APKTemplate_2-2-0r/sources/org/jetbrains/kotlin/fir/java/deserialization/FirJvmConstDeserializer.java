package org.jetbrains.kotlin.fir.java.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializerKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: FirJvmConstDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/FirJvmConstDeserializer;", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "binaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "loadConstant", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmConstDeserializer extends FirConstDeserializer {
    private final KotlinJvmBinaryClass binaryClass;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJvmConstDeserializer(FirSession session, KotlinJvmBinaryClass binaryClass, SerializerExtensionProtocol protocol) {
        super(session, protocol);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(binaryClass, "binaryClass");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.binaryClass = binaryClass;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer
    public FirExpression loadConstant(ProtoBuf.Property propertyProto, final CallableId callableId, final NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        if (!Flags.HAS_CONSTANT.get(propertyProto.getFlags()).booleanValue()) {
            return null;
        }
        FirExpression firExpression = getConstantCache().get(callableId);
        if (firExpression != null) {
            return firExpression;
        }
        this.binaryClass.visitMembers(new KotlinJvmBinaryClass.MemberVisitor() { // from class: org.jetbrains.kotlin.fir.java.deserialization.FirJvmConstDeserializer.loadConstant.2
            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(Name name2, String desc) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                return null;
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.AnnotationVisitor visitField(Name name2, String desc, Object initializer) {
                FirExpression firExpressionBuildFirConstant;
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (initializer != null && (firExpressionBuildFirConstant = FirConstDeserializerKt.buildFirConstant(null, initializer, desc, nameResolver)) != null) {
                    this.getConstantCache().put(FirConstDeserializerKt.replaceName(callableId, name2), firExpressionBuildFirConstant);
                }
                return null;
            }
        }, null);
        return getConstantCache().get(callableId);
    }
}
