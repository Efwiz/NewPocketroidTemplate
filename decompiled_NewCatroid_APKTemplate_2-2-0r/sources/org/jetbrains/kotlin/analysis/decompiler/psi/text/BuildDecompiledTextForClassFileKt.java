package org.jetbrains.kotlin.analysis.decompiler.psi.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForClassfileDecompilerKt;
import org.jetbrains.kotlin.analysis.decompiler.psi.ResolverForDecompiler;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsClassFinder;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsKotlinBinaryClassCache;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: buildDecompiledTextForClassFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"decompilerRendererForClassFiles", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "buildDecompiledTextForClassFile", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", "classFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "resolver", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/ResolverForDecompiler;", "decompiler-to-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuildDecompiledTextForClassFileKt {
    private static final DescriptorRenderer decompilerRendererForClassFiles = DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextForClassFileKt$decompilerRendererForClassFiles$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
            invoke2(descriptorRendererOptions);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(DescriptorRendererOptions withOptions) {
            Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
            BuildDecompiledTextKt.defaultDecompilerRendererOptions(withOptions);
            withOptions.setTypeNormalizer(new Function1<KotlinType, KotlinType>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextForClassFileKt$decompilerRendererForClassFiles$1.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(KotlinType type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    return FlexibleTypesKt.isFlexible(type) ? FlexibleTypesKt.asFlexibleType(type).getLowerBound() : type;
                }
            });
        }
    });

    /* JADX INFO: compiled from: buildDecompiledTextForClassFile.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ DecompiledText buildDecompiledTextForClassFile$default(VirtualFile virtualFile, ResolverForDecompiler resolverForDecompiler, int i, Object obj) {
        if ((i & 2) != 0) {
            resolverForDecompiler = DeserializerForClassfileDecompilerKt.DeserializerForClassfileDecompiler(virtualFile);
        }
        return buildDecompiledTextForClassFile(virtualFile, resolverForDecompiler);
    }

    public static final DecompiledText buildDecompiledTextForClassFile(VirtualFile classFile, ResolverForDecompiler resolver) {
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData$default = ClsKotlinBinaryClassCache.getKotlinBinaryClassHeaderData$default(ClsKotlinBinaryClassCache.INSTANCE.getInstance(), classFile, null, 2, null);
        if (kotlinBinaryClassHeaderData$default == null) {
            throw new IllegalStateException(("Decompiled data factory shouldn't be called on an unsupported file: " + classFile).toString());
        }
        ClassId classId = kotlinBinaryClassHeaderData$default.getClassId();
        if (!kotlinBinaryClassHeaderData$default.getMetadataVersion().isCompatibleWithCurrentCompilerVersion()) {
            return IncompatibleAbiVersionKt.createIncompatibleAbiVersionDecompiledText(JvmMetadataVersion.INSTANCE, kotlinBinaryClassHeaderData$default.getMetadataVersion());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kotlinBinaryClassHeaderData$default.getKind().ordinal()];
        if (i == 1 || i == 2) {
            FqName fqNameAsSingleFqName = classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            return buildDecompiledTextForClassFile$buildText(kotlinBinaryClassHeaderData$default, resolver.resolveDeclarationsInFacade(fqNameAsSingleFqName));
        }
        if (i == 3) {
            return buildDecompiledTextForClassFile$buildText(kotlinBinaryClassHeaderData$default, CollectionsKt.listOfNotNull(resolver.resolveTopLevelClass(classId)));
        }
        if (i == 4) {
            List<KotlinJvmBinaryClass> listFindMultifileClassParts = ClsClassFinder.INSTANCE.findMultifileClassParts(classFile, classId, kotlinBinaryClassHeaderData$default.getPartNamesIfMultifileFacade());
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = listFindMultifileClassParts.iterator();
            while (it.hasNext()) {
                FqName fqNameAsSingleFqName2 = ((KotlinJvmBinaryClass) it.next()).getClassId().asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
                CollectionsKt.addAll(arrayList, resolver.resolveDeclarationsInFacade(fqNameAsSingleFqName2));
            }
            return buildDecompiledTextForClassFile$buildText(kotlinBinaryClassHeaderData$default, arrayList);
        }
        throw new UnsupportedOperationException("Unknown header kind: " + kotlinBinaryClassHeaderData$default + ", class " + classId);
    }

    private static final DecompiledText buildDecompiledTextForClassFile$buildText(ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData, List<? extends DeclarationDescriptor> list) {
        return BuildDecompiledTextKt.buildDecompiledText(kotlinBinaryClassHeaderData.getPackageNameWithFallback(), list, decompilerRendererForClassFiles);
    }
}
