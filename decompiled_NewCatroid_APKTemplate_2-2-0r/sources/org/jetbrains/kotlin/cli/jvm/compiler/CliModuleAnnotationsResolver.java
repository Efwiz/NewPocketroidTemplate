package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.resolve.ModuleAnnotationsKt;
import org.jetbrains.kotlin.load.kotlin.ModuleNameKt;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver;

/* JADX INFO: compiled from: CliModuleAnnotationsResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliModuleAnnotationsResolver;", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "()V", "packagePartProviders", "", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "addPackagePartProvider", "", "packagePartProvider", "getAnnotationsOnContainingModule", "", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliModuleAnnotationsResolver implements ModuleAnnotationsResolver {
    private final List<PackagePartProvider> packagePartProviders = new ArrayList();

    public final void addPackagePartProvider(PackagePartProvider packagePartProvider) {
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        this.packagePartProviders.add(packagePartProvider);
    }

    @Override // org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver
    public List<ClassId> getAnnotationsOnContainingModule(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<ClassId> annotationsOnContainingJsModule = ModuleAnnotationsKt.getAnnotationsOnContainingJsModule(descriptor);
        if (annotationsOnContainingJsModule != null) {
            return annotationsOnContainingJsModule;
        }
        String jvmModuleNameForDeserializedDescriptor = ModuleNameKt.getJvmModuleNameForDeserializedDescriptor(descriptor);
        if (jvmModuleNameForDeserializedDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        List<PackagePartProvider> list = this.packagePartProviders;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackagePartProvider) it.next()).getAnnotationsOnBinaryModule(jvmModuleNameForDeserializedDescriptor));
        }
        return arrayList;
    }
}
