package org.jetbrains.kotlin.serialization.js;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0007H\u0000¢\u0006\u0002\u0010\b\u001a\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0002\u001aJ\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r*\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0019"}, d2 = {"packageFragmentProviderForModuleContentWithoutDependencies", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getPackageFragmentProviderForModuleContentWithoutDependencies", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "extractFileId", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Ljava/lang/Integer;", "isEmpty", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "missingMetadata", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "moduleDescriptor", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "js.serializer"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptSerializationUtilKt {
    public static final Map<FqName, byte[]> missingMetadata(Map<FqName, byte[]> map, BindingContext bindingContext, ModuleDescriptor moduleDescriptor, LanguageVersionSettings languageVersionSettings, JsMetadataVersion metadataVersion, Project project) {
        Map<FqName, byte[]> map2 = map;
        Intrinsics.checkNotNullParameter(map2, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(project, "project");
        HashMap map3 = new HashMap();
        for (FqName fqName : CollectionsKt.sortedWith(KotlinJavascriptSerializationUtil.INSTANCE.getPackagesFqNames(moduleDescriptor), new Comparator() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtilKt$missingMetadata$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((FqName) t).asString(), ((FqName) t2).asString());
            }
        })) {
            if (!map2.containsKey(fqName)) {
                KotlinJavascriptSerializationUtil kotlinJavascriptSerializationUtil = KotlinJavascriptSerializationUtil.INSTANCE;
                List<PackageFragmentDescriptor> listPackageFragments = PackageFragmentProviderKt.packageFragments(getPackageFragmentProviderForModuleContentWithoutDependencies(moduleDescriptor), fqName);
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = listPackageFragments.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, ResolutionScope.DefaultImpls.getContributedDescriptors$default(((PackageFragmentDescriptor) it.next()).getScope(), null, null, 3, null));
                }
                ProtoBuf.PackageFragment packageFragmentSerializeDescriptors = kotlinJavascriptSerializationUtil.serializeDescriptors(bindingContext, moduleDescriptor, arrayList, fqName, languageVersionSettings, project, metadataVersion);
                if (!isEmpty(packageFragmentSerializeDescriptors)) {
                    byte[] byteArray = packageFragmentSerializeDescriptors.toByteArray();
                    Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                    map3.put(fqName, byteArray);
                }
            }
            map2 = map;
        }
        return map3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PackageFragmentProvider getPackageFragmentProviderForModuleContentWithoutDependencies(final ModuleDescriptor moduleDescriptor) {
        PackageFragmentProvider packageFragmentProviderForModuleContentWithoutDependencies;
        ModuleDescriptorImpl moduleDescriptorImpl = moduleDescriptor instanceof ModuleDescriptorImpl ? (ModuleDescriptorImpl) moduleDescriptor : null;
        if (moduleDescriptorImpl == null || (packageFragmentProviderForModuleContentWithoutDependencies = moduleDescriptorImpl.getPackageFragmentProviderForModuleContentWithoutDependencies()) == null) {
            throw new IllegalStateException("Unsupported ModuleDescriptor kind: " + new PropertyReference0(moduleDescriptor) { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtilKt$packageFragmentProviderForModuleContentWithoutDependencies$1
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getJavaClass(Ljava/lang/Object;)Ljava/lang/Class;";
                }

                @Override // kotlin.reflect.KProperty0
                public Object get() {
                    return this.receiver.getClass();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "javaClass";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(JvmClassMappingKt.class, "js.serializer");
                }
            });
        }
        return packageFragmentProviderForModuleContentWithoutDependencies;
    }

    private static final boolean isEmpty(ProtoBuf.PackageFragment packageFragment) {
        if (packageFragment.getClass_Count() == 0) {
            ProtoBuf.Package r1 = packageFragment.getPackage();
            if (r1.getFunctionCount() == 0 && r1.getPropertyCount() == 0 && r1.getTypeAliasCount() == 0) {
                return true;
            }
        }
        return false;
    }

    public static final Integer extractFileId(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof DeserializedClassDescriptor) {
            return (Integer) ((DeserializedClassDescriptor) declarationDescriptor).getClassProto().getExtension(JsProtoBuf.classContainingFileId);
        }
        if (declarationDescriptor instanceof DeserializedSimpleFunctionDescriptor) {
            return (Integer) ((DeserializedSimpleFunctionDescriptor) declarationDescriptor).getProto().getExtension(JsProtoBuf.functionContainingFileId);
        }
        if (declarationDescriptor instanceof DeserializedPropertyDescriptor) {
            return (Integer) ((DeserializedPropertyDescriptor) declarationDescriptor).getProto().getExtension(JsProtoBuf.propertyContainingFileId);
        }
        return null;
    }
}
