package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmBackendClassResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolverForModuleWithDependencies;", "Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "resolveToClassDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBackendClassResolverForModuleWithDependencies implements JvmBackendClassResolver {
    private final ModuleDescriptor moduleDescriptor;

    public JvmBackendClassResolverForModuleWithDependencies(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.moduleDescriptor = moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.JvmBackendClassResolver
    public List<ClassDescriptor> resolveToClassDescriptors(Type type) {
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type.getSort() == 10 && (classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(this.moduleDescriptor, JvmBackendClassResolverKt.getClassId(type))) != null) {
            return CollectionsKt.plus((Collection<? extends ClassDescriptor>) JavaToKotlinClassMapper.INSTANCE.mapPlatformClass(classDescriptorFindClassAcrossModuleDependencies), classDescriptorFindClassAcrossModuleDependencies);
        }
        return CollectionsKt.emptyList();
    }
}
