package org.jetbrains.kotlin.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.builtins.FunctionInterfacePackageFragment;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: BuiltInFictitiousFunctionClassFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/BuiltInFictitiousFunctionClassFactory;", "Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "createClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllContributedClassesIfPossible", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "shouldCreateClass", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInFictitiousFunctionClassFactory implements ClassDescriptorFactory {
    private final ModuleDescriptor module;
    private final StorageManager storageManager;

    public BuiltInFictitiousFunctionClassFactory(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory
    public boolean shouldCreateClass(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return (StringsKt.startsWith$default(strAsString, "Function", false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, "SuspendFunction", false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_SUSPEND_FUNCTION_PREFIX, false, 2, (Object) null)) && FunctionTypeKindExtractor.INSTANCE.getDefault().getFunctionalClassKindWithArity(packageFqName, strAsString) != null;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory
    public ClassDescriptor createClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (classId.isLocal() || classId.isNestedClass()) {
            return null;
        }
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!StringsKt.contains$default((CharSequence) strAsString, (CharSequence) "Function", false, 2, (Object) null)) {
            return null;
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        FunctionTypeKindExtractor.KindWithArity functionalClassKindWithArity = FunctionTypeKindExtractor.INSTANCE.getDefault().getFunctionalClassKindWithArity(packageFqName, strAsString);
        if (functionalClassKindWithArity == null) {
            return null;
        }
        FunctionTypeKind functionTypeKindComponent1 = functionalClassKindWithArity.getKind();
        int iComponent2 = functionalClassKindWithArity.getArity();
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
        return new FunctionClassDescriptor(this.storageManager, (BuiltInsPackageFragment) objFirst, functionTypeKindComponent1, iComponent2);
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }
}
