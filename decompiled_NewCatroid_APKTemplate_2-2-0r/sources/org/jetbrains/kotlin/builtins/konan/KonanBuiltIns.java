package org.jetbrains.kotlin.builtins.konan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KonanBuiltIns.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/builtins/konan/KonanBuiltIns;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "getSuspendFunction", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parameterCount", "", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KonanBuiltIns extends KotlinBuiltIns {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KonanBuiltIns(StorageManager storageManager) {
        super(storageManager);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
    }

    @Override // org.jetbrains.kotlin.builtins.KotlinBuiltIns
    public ClassDescriptor getSuspendFunction(int parameterCount) {
        ModuleDescriptorImpl builtInsModule = getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(builtInsModule, new ClassId(StandardNames.COROUTINES_PACKAGE_FQ_NAME, Name.identifier("SuspendFunction" + parameterCount)));
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        return classDescriptorFindClassAcrossModuleDependencies;
    }
}
