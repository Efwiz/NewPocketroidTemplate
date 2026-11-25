package org.jetbrains.kotlin.fir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirBuiltInsPackageFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/descriptors/FirBuiltInsPackageFragment;", "Lorg/jetbrains/kotlin/fir/descriptors/FirPackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/builtins/BuiltInsPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "isFallback", "", "()Z", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBuiltInsPackageFragment extends FirPackageFragmentDescriptor implements BuiltInsPackageFragment {
    @Override // org.jetbrains.kotlin.builtins.BuiltInsPackageFragment
    public boolean isFallback() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBuiltInsPackageFragment(FqName fqName, ModuleDescriptor moduleDescriptor) {
        super(fqName, moduleDescriptor);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
    }
}
