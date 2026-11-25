package org.jetbrains.kotlin.analysis.decompiler.psi;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtDecompiledFile;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextKt;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.DecompiledText;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.IncompatibleAbiVersionKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinMetadataStubBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: KotlinMetadataDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003BG\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001eJ \u0010\u001f\u001a\u00020 2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0019H\u0016J\b\u0010$\u001a\u00020\u0011H\u0016J\u0010\u0010%\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001a\u001a\u00020\u001bJ\u001a\u0010%\u001a\u0004\u0018\u00010\u001e2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u001e\u0010(\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010'H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinMetadataDecompiler;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "Lorg/jetbrains/kotlin/com/intellij/psi/compiled/ClassFileDecompilers$Full;", "fileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "serializerProtocol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "expectedBinaryVersion", "invalidBinaryVersion", "stubVersion", "", "(Lcom/intellij/openapi/fileTypes/FileType;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;I)V", "metadataStubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder;", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "getRenderer", "()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "renderer$delegate", "Lkotlin/Lazy;", "accepts", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "buildDecompiledText", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "createFileViewProvider", "Lorg/jetbrains/kotlin/com/intellij/psi/FileViewProvider;", "manager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "physical", "getStubBuilder", "readFile", "bytes", "", "readFileSafely", FirebaseAnalytics.Param.CONTENT, "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinMetadataDecompiler<V extends BinaryVersion> extends ClassFileDecompilers.Full {
    private final Function0<V> expectedBinaryVersion;
    private final FileType fileType;
    private final FlexibleTypeDeserializer flexibleTypeDeserializer;
    private final Function0<V> invalidBinaryVersion;
    private final KotlinMetadataStubBuilder metadataStubBuilder;

    /* JADX INFO: renamed from: renderer$delegate, reason: from kotlin metadata */
    private final Lazy renderer;
    private final Function0<SerializerExtensionProtocol> serializerProtocol;

    public abstract KotlinMetadataStubBuilder.FileWithMetadata readFile(byte[] bytes, VirtualFile file);

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinMetadataDecompiler(FileType fileType, Function0<? extends SerializerExtensionProtocol> serializerProtocol, FlexibleTypeDeserializer flexibleTypeDeserializer, Function0<? extends V> expectedBinaryVersion, Function0<? extends V> invalidBinaryVersion, int i) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        Intrinsics.checkNotNullParameter(flexibleTypeDeserializer, "flexibleTypeDeserializer");
        Intrinsics.checkNotNullParameter(expectedBinaryVersion, "expectedBinaryVersion");
        Intrinsics.checkNotNullParameter(invalidBinaryVersion, "invalidBinaryVersion");
        this.fileType = fileType;
        this.serializerProtocol = serializerProtocol;
        this.flexibleTypeDeserializer = flexibleTypeDeserializer;
        this.expectedBinaryVersion = expectedBinaryVersion;
        this.invalidBinaryVersion = invalidBinaryVersion;
        this.metadataStubBuilder = new KotlinMetadataStubBuilder(i, fileType, serializerProtocol, new KotlinMetadataDecompiler$metadataStubBuilder$1(this));
        this.renderer = LazyKt.lazy(new Function0<DescriptorRenderer>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDecompiler$renderer$2
            @Override // kotlin.jvm.functions.Function0
            public final DescriptorRenderer invoke() {
                return DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDecompiler$renderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DescriptorRendererOptions withOptions) {
                        Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                        BuildDecompiledTextKt.defaultDecompilerRendererOptions(withOptions);
                    }
                });
            }
        });
    }

    private final DescriptorRenderer getRenderer() {
        return (DescriptorRenderer) this.renderer.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Decompiler
    public boolean accepts(VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return Intrinsics.areEqual(file.getExtension(), this.fileType.getDefaultExtension()) || Intrinsics.areEqual(file.getFileType(), this.fileType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    /* JADX INFO: renamed from: getStubBuilder, reason: from getter */
    public KotlinMetadataStubBuilder getMetadataStubBuilder() {
        return this.metadataStubBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    public FileViewProvider createFileViewProvider(VirtualFile file, PsiManager manager, boolean physical) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(manager, "manager");
        return new KotlinDecompiledFileViewProvider(manager, file, physical, new Function1<KotlinDecompiledFileViewProvider, KtDecompiledFile>(this) { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDecompiler.createFileViewProvider.1
            final /* synthetic */ KotlinMetadataDecompiler<V> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final KtDecompiledFile invoke(KotlinDecompiledFileViewProvider provider) {
                Intrinsics.checkNotNullParameter(provider, "provider");
                final VirtualFile virtualFile = provider.getVirtualFile();
                Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
                final KotlinMetadataStubBuilder.FileWithMetadata fileSafely$default = KotlinMetadataDecompiler.readFileSafely$default(this.this$0, virtualFile, null, 2, null);
                if (fileSafely$default == null) {
                    return null;
                }
                final KotlinMetadataDecompiler<V> kotlinMetadataDecompiler = this.this$0;
                return new KtDecompiledFile(provider, new Function1<VirtualFile, DecompiledText>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDecompiler$createFileViewProvider$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final DecompiledText invoke(VirtualFile it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        boolean zAreEqual = Intrinsics.areEqual(it, virtualFile);
                        VirtualFile virtualFile2 = virtualFile;
                        if (!zAreEqual) {
                            throw new IllegalStateException(("Unexpected file " + it + ", expected " + virtualFile2.getFileType()).toString());
                        }
                        return kotlinMetadataDecompiler.buildDecompiledText(fileSafely$default);
                    }
                });
            }
        });
    }

    public final KotlinMetadataStubBuilder.FileWithMetadata readFile(VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return readFileSafely$default(this, file, null, 2, null);
    }

    static /* synthetic */ KotlinMetadataStubBuilder.FileWithMetadata readFileSafely$default(KotlinMetadataDecompiler kotlinMetadataDecompiler, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readFileSafely");
        }
        if ((i & 2) != 0) {
            bArr = null;
        }
        return kotlinMetadataDecompiler.readFileSafely(virtualFile, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinMetadataStubBuilder.FileWithMetadata readFileSafely(VirtualFile file, byte[] content) {
        if (!file.isValid()) {
            return null;
        }
        if (content == null) {
            try {
                content = file.contentsToByteArray(false);
                Intrinsics.checkNotNullExpressionValue(content, "contentsToByteArray(...)");
            } catch (IOException unused) {
                return null;
            }
        }
        return readFile(content, file);
    }

    public final DecompiledText buildDecompiledText(KotlinMetadataStubBuilder.FileWithMetadata file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (file instanceof KotlinMetadataStubBuilder.FileWithMetadata.Incompatible) {
            return IncompatibleAbiVersionKt.createIncompatibleAbiVersionDecompiledText(this.expectedBinaryVersion.invoke(), ((KotlinMetadataStubBuilder.FileWithMetadata.Incompatible) file).getVersion());
        }
        if (!(file instanceof KotlinMetadataStubBuilder.FileWithMetadata.Compatible)) {
            throw new NoWhenBranchMatchedException();
        }
        KotlinMetadataStubBuilder.FileWithMetadata.Compatible compatible = (KotlinMetadataStubBuilder.FileWithMetadata.Compatible) file;
        FqName packageFqName = compatible.getPackageFqName();
        KotlinMetadataDeserializerForDecompiler kotlinMetadataDeserializerForDecompiler = new KotlinMetadataDeserializerForDecompiler(packageFqName, compatible.getProto(), compatible.getNameResolver(), compatible.getVersion(), this.serializerProtocol.invoke(), this.flexibleTypeDeserializer);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(kotlinMetadataDeserializerForDecompiler.resolveDeclarationsInFacade(packageFqName));
        Iterator<ProtoBuf.Class> it = compatible.getClassesToDecompile().iterator();
        while (it.hasNext()) {
            CollectionsKt.addIfNotNull(arrayList, kotlinMetadataDeserializerForDecompiler.resolveTopLevelClass(NameResolverUtilKt.getClassId(compatible.getNameResolver(), it.next().getFqName())));
        }
        return BuildDecompiledTextKt.buildDecompiledText(packageFqName, arrayList, getRenderer());
    }
}
