package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionInterfacePackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;

/* JADX INFO: compiled from: BuiltInFictitiousFunctionClassFactory.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class BuiltInFictitiousFunctionClassFactory implements ClassDescriptorFactory {
    private final ModuleDescriptor module;
    private final StorageManager storageManager;

    public BuiltInFictitiousFunctionClassFactory(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public boolean shouldCreateClass(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "name.asString()");
        return (StringsKt.startsWith$default(strAsString, "Function", false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, "SuspendFunction", false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_SUSPEND_FUNCTION_PREFIX, false, 2, (Object) null)) && FunctionClassKind.Companion.parseClassName(strAsString, packageFqName) != null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public ClassDescriptor createClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (classId.isLocal() || classId.isNestedClass()) {
            return null;
        }
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "classId.relativeClassName.asString()");
        if (!StringsKt.contains$default((CharSequence) strAsString, (CharSequence) "Function", false, 2, (Object) null)) {
            return null;
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "classId.packageFqName");
        FunctionClassKind.Companion.KindWithArity className = FunctionClassKind.Companion.parseClassName(strAsString, packageFqName);
        if (className == null) {
            return null;
        }
        FunctionClassKind functionClassKindComponent1 = className.component1();
        int iComponent2 = className.component2();
        List<PackageFragmentDescriptor> fragments = this.module.getPackage(packageFqName).getFragments();
        ArrayList arrayList = new ArrayList();
        for (Object obj : fragments) {
            if (obj instanceof BuiltInsPackageFragment) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (obj2 instanceof FunctionInterfacePackageFragment) {
                arrayList3.add(obj2);
            }
        }
        Object objFirst = (FunctionInterfacePackageFragment) CollectionsKt.firstOrNull((List) arrayList3);
        if (objFirst == null) {
            objFirst = CollectionsKt.first((List<? extends Object>) arrayList2);
        }
        return new FunctionClassDescriptor(this.storageManager, (BuiltInsPackageFragment) objFirst, functionClassKindComponent1, iComponent2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }
}
