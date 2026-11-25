package org.koin.androidx.fragment.android;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;

/* JADX INFO: compiled from: ActivityExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"setupKoinFragmentFactory", "", "Landroidx/fragment/app/FragmentActivity;", "scope", "Lorg/koin/core/scope/Scope;", "koin-androidx-fragment_release"}, k = 2, mv = {1, 1, 16})
public final class ActivityExtKt {
    public static /* synthetic */ void setupKoinFragmentFactory$default(FragmentActivity fragmentActivity, Scope scope, int i, Object obj) {
        if ((i & 1) != 0) {
            scope = null;
        }
        setupKoinFragmentFactory(fragmentActivity, scope);
    }

    public static final void setupKoinFragmentFactory(FragmentActivity setupKoinFragmentFactory, Scope scope) {
        Intrinsics.checkParameterIsNotNull(setupKoinFragmentFactory, "$this$setupKoinFragmentFactory");
        if (scope == null) {
            FragmentManager supportFragmentManager = setupKoinFragmentFactory.getSupportFragmentManager();
            Intrinsics.checkExpressionValueIsNotNull(supportFragmentManager, "supportFragmentManager");
            supportFragmentManager.setFragmentFactory((FragmentFactory) ComponentCallbackExtKt.getKoin(setupKoinFragmentFactory).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(FragmentFactory.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
            return;
        }
        FragmentManager supportFragmentManager2 = setupKoinFragmentFactory.getSupportFragmentManager();
        Intrinsics.checkExpressionValueIsNotNull(supportFragmentManager2, "supportFragmentManager");
        supportFragmentManager2.setFragmentFactory(new KoinFragmentFactory(scope));
    }
}
