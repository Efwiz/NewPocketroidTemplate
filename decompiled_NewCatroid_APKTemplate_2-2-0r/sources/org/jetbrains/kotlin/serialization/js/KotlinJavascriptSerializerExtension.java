package org.jetbrains.kotlin.serialization.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;
import org.jetbrains.kotlin.serialization.ApproximatingStringTable;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: KotlinJavascriptSerializerExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¢\u0006\u0002\u0010\u0013J(\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J \u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016J*\u0010#\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020%2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J*\u0010&\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020'2\u0006\u0010\u0017\u001a\u00020(2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "fileRegistry", "Lorg/jetbrains/kotlin/serialization/js/KotlinFileRegistry;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "(Lorg/jetbrains/kotlin/serialization/js/KotlinFileRegistry;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/utils/JsMetadataVersion;)V", "getMetadataVersion", "()Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "stringTable", "Lorg/jetbrains/kotlin/serialization/ApproximatingStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/ApproximatingStringTable;", "getFileId", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Ljava/lang/Integer;", "serializeClass", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeFlexibleType", "flexibleType", "Lorg/jetbrains/kotlin/types/FlexibleType;", "lowerProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "upperProto", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptSerializerExtension extends KotlinSerializerExtensionBase {
    private final KotlinFileRegistry fileRegistry;
    private final LanguageVersionSettings languageVersionSettings;
    private final JsMetadataVersion metadataVersion;
    private final ApproximatingStringTable stringTable;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public JsMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinJavascriptSerializerExtension(KotlinFileRegistry fileRegistry, LanguageVersionSettings languageVersionSettings, JsMetadataVersion metadataVersion) {
        super(JsSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(fileRegistry, "fileRegistry");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.fileRegistry = fileRegistry;
        this.languageVersionSettings = languageVersionSettings;
        this.metadataVersion = metadataVersion;
        this.stringTable = new ApproximatingStringTable();
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public ApproximatingStringTable getStringTable() {
        return this.stringTable;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFlexibleType(FlexibleType flexibleType, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
        lowerProto.setFlexibleTypeCapabilitiesId(getStringTable().getStringIndex("kotlin.DynamicType"));
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Integer fileId = getFileId(descriptor);
        if (fileId != null) {
            proto.setExtension(JsProtoBuf.classContainingFileId, fileId);
        }
        super.serializeClass(descriptor, proto, versionRequirementTable, childSerializer);
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Integer fileId = getFileId(descriptor);
        if (fileId != null) {
            proto.setExtension(JsProtoBuf.propertyContainingFileId, fileId);
        }
        super.serializeProperty(descriptor, proto, versionRequirementTable, childSerializer);
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Integer fileId = getFileId(descriptor);
        if (fileId != null) {
            proto.setExtension(JsProtoBuf.functionContainingFileId, fileId);
        }
        super.serializeFunction(descriptor, proto, versionRequirementTable, childSerializer);
    }

    private final Integer getFileId(DeclarationDescriptor descriptor) {
        if (!DescriptorUtils.isTopLevelDeclaration(descriptor) || !(descriptor instanceof DeclarationDescriptorWithSource)) {
            return null;
        }
        Integer numExtractFileId = KotlinJavascriptSerializationUtilKt.extractFileId(descriptor);
        if (numExtractFileId != null) {
            DeclarationDescriptor containingDeclaration = ((DeclarationDescriptorWithSource) descriptor).getContainingModule();
            KotlinJavascriptPackageFragment kotlinJavascriptPackageFragment = containingDeclaration instanceof KotlinJavascriptPackageFragment ? (KotlinJavascriptPackageFragment) containingDeclaration : null;
            if (kotlinJavascriptPackageFragment != null) {
                return Integer.valueOf(this.fileRegistry.lookup(new KotlinDeserializedFileMetadata(kotlinJavascriptPackageFragment, numExtractFileId.intValue())));
            }
        }
        SourceFile containingFile = ((DeclarationDescriptorWithSource) descriptor).getSourceElement().getContainingFile();
        PsiSourceFile psiSourceFile = containingFile instanceof PsiSourceFile ? (PsiSourceFile) containingFile : null;
        if (psiSourceFile == null) {
            return null;
        }
        PsiFile psiFile = psiSourceFile.getPsiFile();
        KtFile ktFile = psiFile instanceof KtFile ? (KtFile) psiFile : null;
        if (ktFile != null) {
            return Integer.valueOf(this.fileRegistry.lookup(new KotlinPsiFileMetadata(ktFile)));
        }
        return null;
    }
}
