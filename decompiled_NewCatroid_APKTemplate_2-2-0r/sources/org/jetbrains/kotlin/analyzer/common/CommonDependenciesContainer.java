package org.jetbrains.kotlin.analyzer.common;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;

/* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H&J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\r\u001a\u00020\u0004H&J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;", "", "friendModuleInfos", "", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getFriendModuleInfos", "()Ljava/util/List;", "moduleInfos", "getModuleInfos", "refinesModuleInfos", "getRefinesModuleInfos", "moduleDescriptorForModuleInfo", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleInfo", "packageFragmentProviderForModuleInfo", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "registerDependencyForAllModules", "", "descriptorForModule", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CommonDependenciesContainer {
    List<ModuleInfo> getFriendModuleInfos();

    List<ModuleInfo> getModuleInfos();

    List<ModuleInfo> getRefinesModuleInfos();

    ModuleDescriptor moduleDescriptorForModuleInfo(ModuleInfo moduleInfo);

    PackageFragmentProvider packageFragmentProviderForModuleInfo(ModuleInfo moduleInfo);

    void registerDependencyForAllModules(ModuleInfo moduleInfo, ModuleDescriptorImpl descriptorForModule);
}
