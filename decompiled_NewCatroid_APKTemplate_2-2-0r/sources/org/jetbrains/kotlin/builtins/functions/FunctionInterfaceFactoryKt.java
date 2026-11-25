package org.jetbrains.kotlin.builtins.functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: FunctionInterfaceFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"functionInterfacePackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionInterfaceFactoryKt {
    public static final PackageFragmentProvider functionInterfacePackageFragmentProvider(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        BuiltInFictitiousFunctionClassFactory builtInFictitiousFunctionClassFactory = new BuiltInFictitiousFunctionClassFactory(storageManager, module);
        List listListOf = CollectionsKt.listOf((Object[]) new FqName[]{StandardNames.KOTLIN_REFLECT_FQ_NAME, StandardNames.BUILT_INS_PACKAGE_FQ_NAME, StandardNames.COROUTINES_PACKAGE_FQ_NAME});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new FunctionInterfacePackageFragmentImpl(builtInFictitiousFunctionClassFactory, module, (FqName) it.next()));
        }
        return new PackageFragmentProviderImpl(arrayList);
    }
}
