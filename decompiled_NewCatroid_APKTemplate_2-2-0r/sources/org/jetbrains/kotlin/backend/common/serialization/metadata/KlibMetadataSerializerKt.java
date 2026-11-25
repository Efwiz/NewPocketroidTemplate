package org.jetbrains.kotlin.backend.common.serialization.metadata;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;

/* JADX INFO: compiled from: KlibMetadataSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\f\u001a2\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\f\u001a\u0011\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u0011¢\u0006\u0002\u0010\u0012\u001aM\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00140\f\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u0014*\b\u0012\u0004\u0012\u0002H\u00150\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00102\u0018\u0010\u0018\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\f\u0012\u0004\u0012\u0002H\u00140\u0019H\u0000¢\u0006\u0002\u0010\u001a\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u001b"}, d2 = {"packageFragmentProviderForModuleContentWithoutDependencies", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getPackageFragmentProviderForModuleContentWithoutDependencies", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "serializeKlibHeader", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleName", "", "fragmentNames", "", "emptyPackages", "moduleDescriptor", "extractFileId", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Ljava/lang/Integer;", "maybeChunked", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "size", "transform", "Lkotlin/Function1;", "(Ljava/lang/Iterable;Ljava/lang/Integer;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataSerializerKt {
    public static final <T, R> List<R> maybeChunked(Iterable<? extends T> iterable, Integer num, Function1<? super List<? extends T>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if (num != null) {
            num.intValue();
            List<R> listChunked = CollectionsKt.chunked(iterable, num.intValue(), transform);
            if (listChunked != null) {
                return listChunked;
            }
        }
        return CollectionsKt.listOf(transform.invoke(CollectionsKt.toList(iterable)));
    }

    public static final KlibMetadataProtoBuf.Header serializeKlibHeader(LanguageVersionSettings languageVersionSettings, ModuleDescriptor moduleDescriptor, List<String> fragmentNames, List<String> emptyPackages) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(fragmentNames, "fragmentNames");
        Intrinsics.checkNotNullParameter(emptyPackages, "emptyPackages");
        String strAsString = moduleDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return serializeKlibHeader(languageVersionSettings, strAsString, fragmentNames, emptyPackages);
    }

    public static final KlibMetadataProtoBuf.Header serializeKlibHeader(LanguageVersionSettings languageVersionSettings, String moduleName, List<String> fragmentNames, List<String> emptyPackages) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(fragmentNames, "fragmentNames");
        Intrinsics.checkNotNullParameter(emptyPackages, "emptyPackages");
        KlibMetadataProtoBuf.Header.Builder builderNewBuilder = KlibMetadataProtoBuf.Header.newBuilder();
        builderNewBuilder.setModuleName(moduleName);
        if (languageVersionSettings.isPreRelease()) {
            builderNewBuilder.setFlags(2);
        }
        Iterator<T> it = fragmentNames.iterator();
        while (it.hasNext()) {
            builderNewBuilder.addPackageFragmentName((String) it.next());
        }
        Iterator<T> it2 = emptyPackages.iterator();
        while (it2.hasNext()) {
            builderNewBuilder.addEmptyPackage((String) it2.next());
        }
        KlibMetadataProtoBuf.Header headerBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(headerBuild, "build(...)");
        return headerBuild;
    }

    public static final Integer extractFileId(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof DeserializedClassDescriptor) {
            return (Integer) ((DeserializedClassDescriptor) declarationDescriptor).getClassProto().getExtension(KlibMetadataProtoBuf.classFile);
        }
        if (declarationDescriptor instanceof DeserializedSimpleFunctionDescriptor) {
            return (Integer) ((DeserializedSimpleFunctionDescriptor) declarationDescriptor).getProto().getExtension(KlibMetadataProtoBuf.functionFile);
        }
        if (declarationDescriptor instanceof DeserializedPropertyDescriptor) {
            return (Integer) ((DeserializedPropertyDescriptor) declarationDescriptor).getProto().getExtension(KlibMetadataProtoBuf.propertyFile);
        }
        return null;
    }

    public static final PackageFragmentProvider getPackageFragmentProviderForModuleContentWithoutDependencies(ModuleDescriptor moduleDescriptor) {
        PackageFragmentProvider packageFragmentProviderForModuleContentWithoutDependencies;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        ModuleDescriptorImpl moduleDescriptorImpl = moduleDescriptor instanceof ModuleDescriptorImpl ? (ModuleDescriptorImpl) moduleDescriptor : null;
        if (moduleDescriptorImpl == null || (packageFragmentProviderForModuleContentWithoutDependencies = moduleDescriptorImpl.getPackageFragmentProviderForModuleContentWithoutDependencies()) == null) {
            throw new IllegalStateException(("Can't get a module content package fragments, it's not a " + Reflection.getOrCreateKotlinClass(ModuleDescriptorImpl.class).getSimpleName() + '.').toString());
        }
        return packageFragmentProviderForModuleContentWithoutDependencies;
    }
}
