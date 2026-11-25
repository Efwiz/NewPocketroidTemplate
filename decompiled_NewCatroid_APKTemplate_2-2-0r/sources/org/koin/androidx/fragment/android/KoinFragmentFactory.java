package org.koin.androidx.fragment.android;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.scope.Scope;

/* JADX INFO: compiled from: KoinFragmentFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/koin/androidx/fragment/android/KoinFragmentFactory;", "Landroidx/fragment/app/FragmentFactory;", "Lorg/koin/core/KoinComponent;", "scope", "Lorg/koin/core/scope/Scope;", "(Lorg/koin/core/scope/Scope;)V", "getScope", "()Lorg/koin/core/scope/Scope;", "instantiate", "Landroidx/fragment/app/Fragment;", "classLoader", "Ljava/lang/ClassLoader;", "className", "", "koin-androidx-fragment_release"}, k = 1, mv = {1, 1, 16})
public final class KoinFragmentFactory extends FragmentFactory implements KoinComponent {
    private final Scope scope;

    /* JADX WARN: Multi-variable type inference failed */
    public KoinFragmentFactory() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public KoinFragmentFactory(Scope scope) {
        this.scope = scope;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KoinFragmentFactory(Scope scope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            scope = null;
        }
        this(scope);
    }

    @Override // org.koin.core.KoinComponent
    public Koin getKoin() {
        return KoinComponent.DefaultImpls.getKoin(this);
    }

    public final Scope getScope() {
        return this.scope;
    }

    @Override // androidx.fragment.app.FragmentFactory
    public Fragment instantiate(ClassLoader classLoader, String className) throws ClassNotFoundException {
        Fragment fragment;
        Intrinsics.checkParameterIsNotNull(classLoader, "classLoader");
        Intrinsics.checkParameterIsNotNull(className, "className");
        Class<?> cls = Class.forName(className);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(className)");
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(cls);
        Scope scope = this.scope;
        if (scope != null) {
            fragment = (Fragment) Scope.getOrNull$default(scope, kotlinClass, null, null, 6, null);
        } else {
            fragment = (Fragment) Koin.getOrNull$default(getKoin(), kotlinClass, null, null, 6, null);
        }
        if (fragment != null) {
            return fragment;
        }
        Fragment fragmentInstantiate = super.instantiate(classLoader, className);
        Intrinsics.checkExpressionValueIsNotNull(fragmentInstantiate, "super.instantiate(classLoader, className)");
        return fragmentInstantiate;
    }
}
