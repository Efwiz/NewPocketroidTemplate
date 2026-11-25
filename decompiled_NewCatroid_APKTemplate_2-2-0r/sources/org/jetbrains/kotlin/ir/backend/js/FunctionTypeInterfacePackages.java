package org.jetbrains.kotlin.ir.backend.js;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.FunctionalInterfacesUtilsKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: FunctionTypeInterfacePackages.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\r\u001a\u00020\u000eR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/FunctionTypeInterfacePackages;", "", "()V", "functionTypeInterfacePackageFiles", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lkotlin/collections/HashSet;", "isFunctionTypeInterfacePackageFile", "", "file", "makePackageAccessor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "stdlibModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Companion", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionTypeInterfacePackages {
    private final HashSet<IrFile> functionTypeInterfacePackageFiles = new HashSet<>();

    public final Function1<PackageFragmentDescriptor, IrFile> makePackageAccessor(final IrModuleFragment stdlibModule) {
        Intrinsics.checkNotNullParameter(stdlibModule, "stdlibModule");
        return new Function1<PackageFragmentDescriptor, IrFile>() { // from class: org.jetbrains.kotlin.ir.backend.js.FunctionTypeInterfacePackages.makePackageAccessor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrFile invoke(PackageFragmentDescriptor packageFragmentDescriptor) {
                Intrinsics.checkNotNullParameter(packageFragmentDescriptor, "packageFragmentDescriptor");
                String string = packageFragmentDescriptor.getFqName().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                if (!FunctionalInterfacesUtilsKt.checkIsFunctionTypeInterfacePackageFqName(string)) {
                    throw new IllegalStateException(("unexpected function type interface package " + string).toString());
                }
                String str = StringsKt.replace$default(string, '.', '-', false, 4, (Object) null) + "-package.kt";
                Iterator<T> it = stdlibModule.getFiles().iterator();
                Object obj = null;
                boolean z = false;
                Object obj2 = null;
                while (true) {
                    if (!it.hasNext()) {
                        if (!z) {
                            break;
                        }
                        obj = obj2;
                        break;
                    }
                    Object next = it.next();
                    IrFile irFile = (IrFile) next;
                    if (StringsKt.endsWith$default(irFile.getFileEntry().getName(), str, false, 2, (Object) null) && StringsKt.contains$default((CharSequence) irFile.getFileEntry().getName(), (CharSequence) "function-type-interface", false, 2, (Object) null)) {
                        if (z) {
                            break;
                        }
                        z = true;
                        obj2 = next;
                    }
                }
                IrFile irFile2 = (IrFile) obj;
                if (irFile2 == null) {
                    throw new IllegalStateException(("can not find a functional interface file for " + string + " package").toString());
                }
                if (Intrinsics.areEqual(packageFragmentDescriptor.getFqName(), irFile2.getPackageFqName())) {
                    this.functionTypeInterfacePackageFiles.add(irFile2);
                    return irFile2;
                }
                throw new IllegalStateException(("unexpected package in file " + irFile2.getFileEntry().getName() + "; expected " + string + ", got " + irFile2.getPackageFqName()).toString());
            }
        };
    }

    public final boolean isFunctionTypeInterfacePackageFile(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.functionTypeInterfacePackageFiles.contains(file);
    }
}
