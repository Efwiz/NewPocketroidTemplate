package org.jetbrains.kotlin.fir.java.deserialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsSignatures;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationExtension;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirJvmDeserializationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0014\u0010\u0016\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/FirJvmDeserializationExtension;", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "createConstDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "serializerExtensionProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "loadModuleName", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "addSerializableIfNeeded", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "configureDeserializedClass", "Companion", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmDeserializationExtension extends FirDeserializationExtension {
    private static final ClassId JAVA_IO_SERIALIZABLE;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJvmDeserializationExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.FirDeserializationExtension
    public FirConstDeserializer createConstDeserializer(DeserializedContainerSource containerSource, FirSession session, SerializerExtensionProtocol serializerExtensionProtocol) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(serializerExtensionProtocol, "serializerExtensionProtocol");
        if (containerSource instanceof KotlinJvmBinarySourceElement) {
            return new FirJvmConstDeserializer(session, ((KotlinJvmBinarySourceElement) containerSource).getBinaryClass(), serializerExtensionProtocol);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.FirDeserializationExtension
    public void configureDeserializedClass(FirRegularClassBuilder firRegularClassBuilder, ClassId classId) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        addSerializableIfNeeded(firRegularClassBuilder, classId);
    }

    private final void addSerializableIfNeeded(FirRegularClassBuilder firRegularClassBuilder, ClassId classId) {
        JvmBuiltInsSignatures jvmBuiltInsSignatures = JvmBuiltInsSignatures.INSTANCE;
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        if (jvmBuiltInsSignatures.isSerializableInJava(unsafe)) {
            List<FirTypeRef> superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(JAVA_IO_SERIALIZABLE), new ConeTypeProjection[0], false, null, 8, null));
            superTypeRefs.add(firResolvedTypeRefBuilder.mo6809build());
        }
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.FirDeserializationExtension
    public String loadModuleName(ProtoBuf.Class classProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classModuleName = JvmProtoBuf.classModuleName;
        Intrinsics.checkNotNullExpressionValue(classModuleName, "classModuleName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(classProto, classModuleName);
        if (num != null) {
            return nameResolver.getString(num.intValue());
        }
        return null;
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName(CommonClassNames.JAVA_IO_SERIALIZABLE));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JAVA_IO_SERIALIZABLE = classId;
    }
}
