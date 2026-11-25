package org.jetbrains.kotlin.serialization.builtins;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.AnnotationSerializer;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: BuiltInsSerializerExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0014J\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\rH\u0002J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\u00020\u0005*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/serialization/builtins/BuiltInsSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "()V", "ignoredAnnotationShortNames", "", "", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "shortNameToClassId", "", "presentableName", "Lorg/jetbrains/kotlin/types/KotlinType;", "getPresentableName", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/String;", "createAnnotationSerializer", "Lorg/jetbrains/kotlin/serialization/AnnotationSerializer;", "resolveUnresolvedType", ModuleXmlParser.TYPE, "serializeErrorType", "", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "shouldUseTypeTable", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInsSerializerExtension extends KotlinSerializerExtensionBase {
    private final Set<String> ignoredAnnotationShortNames;
    private final Map<String, String> shortNameToClassId;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    /* JADX INFO: renamed from: shouldUseTypeTable */
    public boolean getUseTypeTable() {
        return true;
    }

    public BuiltInsSerializerExtension() {
        super(BuiltInSerializerProtocol.INSTANCE);
        this.shortNameToClassId = MapsKt.mapOf(TuplesKt.to("IntRange", "kotlin/ranges/IntRange"), TuplesKt.to("LongRange", "kotlin/ranges/LongRange"), TuplesKt.to("CharRange", "kotlin/ranges/CharRange"), TuplesKt.to("ExperimentalStdlibApi", "kotlin/ExperimentalStdlibApi"));
        this.ignoredAnnotationShortNames = SetsKt.setOf((Object[]) new String[]{"JvmStatic", "JvmField", "OptIn"});
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    protected AnnotationSerializer createAnnotationSerializer() {
        return new AnnotationSerializer(getStringTable()) { // from class: org.jetbrains.kotlin.serialization.builtins.BuiltInsSerializerExtension.createAnnotationSerializer.1
            {
                super(stringTableImpl);
            }

            @Override // org.jetbrains.kotlin.serialization.AnnotationSerializer
            protected ClassId getAnnotationClassId(AnnotationDescriptor annotation) {
                Intrinsics.checkNotNullParameter(annotation, "annotation");
                KotlinType type = annotation.getType();
                ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotation);
                if (annotationClass == null) {
                    throw new IllegalStateException(("Annotation type is not a class: " + type).toString());
                }
                if (ErrorUtils.isError(annotationClass)) {
                    if (BuiltInsSerializerExtension.this.ignoredAnnotationShortNames.contains(BuiltInsSerializerExtension.this.getPresentableName(type))) {
                        return null;
                    }
                    return ClassId.fromString(BuiltInsSerializerExtension.this.resolveUnresolvedType(type));
                }
                return DescriptorUtilsKt.getClassId((ClassifierDescriptor) annotationClass);
            }
        };
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BinaryVersion getMetadataVersion() {
        return BuiltInsBinaryVersion.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeErrorType(KotlinType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        builder.setClassName(getStringTable().getQualifiedClassNameIndex(resolveUnresolvedType(type), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String resolveUnresolvedType(KotlinType type) {
        String str = this.shortNameToClassId.get(getPresentableName(type));
        if (str != null) {
            return str;
        }
        throw new UnsupportedOperationException("Unsupported unresolved type: " + type.unwrap() + ".\nConsider adding it to `BuiltInsSerializerExtension.shortNameToClassId`.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getPresentableName(KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (!TypeUtilsKt.isUnresolvedType(unwrappedTypeUnwrap)) {
            throw new UnsupportedOperationException("Error types which are not unresolved type instances are not supported here: " + unwrappedTypeUnwrap);
        }
        return StringsKt.removePrefix(((ErrorType) unwrappedTypeUnwrap).getDebugMessage(), (CharSequence) "Unresolved type for ");
    }
}
