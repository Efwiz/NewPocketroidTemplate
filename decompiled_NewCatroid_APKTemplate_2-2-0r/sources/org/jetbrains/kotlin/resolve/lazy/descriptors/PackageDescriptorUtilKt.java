package org.jetbrains.kotlin.resolve.lazy.descriptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: packageDescriptorUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"findPackageFragmentForFile", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PackageDescriptorUtilKt {
    public static final PackageFragmentDescriptor findPackageFragmentForFile(ModuleDescriptor moduleDescriptor, KtFile ktFile) {
        Object next;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        List<PackageFragmentDescriptor> fragments = moduleDescriptor.getPackage(ktFile.getPackageFqName()).getFragments();
        ArrayList arrayList = new ArrayList();
        for (Object obj : fragments) {
            if (obj instanceof LazyPackageDescriptor) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((LazyPackageDescriptor) next).getDeclarationProvider().containsFile(ktFile)) {
                return (PackageFragmentDescriptor) next;
            }
        }
        next = null;
        return (PackageFragmentDescriptor) next;
    }
}
