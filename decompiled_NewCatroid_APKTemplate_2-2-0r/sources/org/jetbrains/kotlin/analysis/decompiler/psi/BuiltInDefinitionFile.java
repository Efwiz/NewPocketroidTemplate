package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinMetadataStubBuilder;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KotlinBuiltInDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/BuiltInDefinitionFile;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata$Compatible;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "version", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "packageDirectory", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "isMetadata", "", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;Lcom/intellij/openapi/vfs/VirtualFile;Z)V", "classesToDecompile", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "getClassesToDecompile", "()Ljava/util/List;", "()Z", "getPackageDirectory", "()Lcom/intellij/openapi/vfs/VirtualFile;", "shouldDecompileBuiltInClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Companion", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInDefinitionFile extends KotlinMetadataStubBuilder.FileWithMetadata.Compatible {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean FILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES = true;
    private final boolean isMetadata;
    private final VirtualFile packageDirectory;

    public final VirtualFile getPackageDirectory() {
        return this.packageDirectory;
    }

    /* JADX INFO: renamed from: isMetadata, reason: from getter */
    public final boolean getIsMetadata() {
        return this.isMetadata;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuiltInDefinitionFile(ProtoBuf.PackageFragment proto, BuiltInsBinaryVersion version, VirtualFile packageDirectory, boolean z) {
        super(proto, version, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(packageDirectory, "packageDirectory");
        this.packageDirectory = packageDirectory;
        this.isMetadata = z;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinMetadataStubBuilder.FileWithMetadata.Compatible
    public List<ProtoBuf.Class> getClassesToDecompile() {
        List<ProtoBuf.Class> classesToDecompile = super.getClassesToDecompile();
        if (this.isMetadata || !FILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES) {
            return classesToDecompile;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : classesToDecompile) {
            if (shouldDecompileBuiltInClass(NameResolverUtilKt.getClassId(getNameResolver(), ((ProtoBuf.Class) obj).getFqName()))) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final boolean shouldDecompileBuiltInClass(ClassId classId) {
        return this.packageDirectory.findChild(new StringBuilder().append(classId.getShortClassName().asString()).append('.').append(JavaClassFileType.INSTANCE.getDefaultExtension()).toString()) == null;
    }

    /* JADX INFO: compiled from: KotlinBuiltInDecompiler.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/BuiltInDefinitionFile$Companion;", "", "()V", "<set-?>", "", "FILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES", "getFILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES", "()Z", "setFILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES", "(Z)V", "read", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "contents", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getFILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES() {
            return BuiltInDefinitionFile.FILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES;
        }

        public final void setFILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES(boolean z) {
            BuiltInDefinitionFile.FILTER_OUT_CLASSES_EXISTING_AS_JVM_CLASS_FILES = z;
        }

        public final KotlinMetadataStubBuilder.FileWithMetadata read(byte[] contents, VirtualFile file) throws IOException {
            Intrinsics.checkNotNullParameter(contents, "contents");
            Intrinsics.checkNotNullParameter(file, "file");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contents);
            BuiltInsBinaryVersion from = BuiltInsBinaryVersion.INSTANCE.readFrom(byteArrayInputStream);
            if (!from.isCompatibleWithCurrentCompilerVersion()) {
                return new KotlinMetadataStubBuilder.FileWithMetadata.Incompatible(from);
            }
            ProtoBuf.PackageFragment from2 = ProtoBuf.PackageFragment.parseFrom(byteArrayInputStream, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
            Intrinsics.checkNotNull(from2);
            VirtualFile parent = file.getParent();
            Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
            BuiltInDefinitionFile builtInDefinitionFile = new BuiltInDefinitionFile(from2, from, parent, Intrinsics.areEqual(file.getExtension(), MetadataPackageFragment.METADATA_FILE_EXTENSION));
            ProtoBuf.Package r5 = builtInDefinitionFile.getProto().getPackage();
            if (builtInDefinitionFile.getClassesToDecompile().isEmpty() && r5.getTypeAliasCount() == 0 && r5.getFunctionCount() == 0 && r5.getPropertyCount() == 0) {
                return null;
            }
            return builtInDefinitionFile;
        }
    }
}
