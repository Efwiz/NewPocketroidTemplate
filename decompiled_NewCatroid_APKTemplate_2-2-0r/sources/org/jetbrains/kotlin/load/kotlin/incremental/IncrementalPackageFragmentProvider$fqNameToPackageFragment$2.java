package org.jetbrains.kotlin.load.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IncrementalPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class IncrementalPackageFragmentProvider$fqNameToPackageFragment$2 extends FunctionReference implements Function1<FqName, IncrementalPackageFragmentProvider.IncrementalPackageFragment> {
    IncrementalPackageFragmentProvider$fqNameToPackageFragment$2(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;Lorg/jetbrains/kotlin/name/FqName;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(IncrementalPackageFragmentProvider.IncrementalPackageFragment.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final IncrementalPackageFragmentProvider.IncrementalPackageFragment invoke(FqName p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new IncrementalPackageFragmentProvider.IncrementalPackageFragment((IncrementalPackageFragmentProvider) this.receiver, p0);
    }
}
