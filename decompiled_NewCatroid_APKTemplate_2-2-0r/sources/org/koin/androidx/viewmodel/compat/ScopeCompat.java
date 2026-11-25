package org.koin.androidx.viewmodel.compat;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.koin.androidx.viewmodel.scope.ScopeExtKt;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;

/* JADX INFO: compiled from: ScopeCompat.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JY\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\u0004\u0018\u0001`\u0011H\u0007¢\u0006\u0002\u0010\u0012JZ\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0014\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\u0004\u0018\u0001`\u0011H\u0007¨\u0006\u0015"}, d2 = {"Lorg/koin/androidx/viewmodel/compat/ScopeCompat;", "", "()V", "getViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "scope", "Lorg/koin/core/scope/Scope;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "clazz", "Ljava/lang/Class;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Lorg/koin/core/scope/Scope;Landroidx/lifecycle/ViewModelStoreOwner;Ljava/lang/Class;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "viewModel", "Lkotlin/Lazy;", "koin-androidx-viewmodel_release"}, k = 1, mv = {1, 1, 16})
public final class ScopeCompat {
    public static final ScopeCompat INSTANCE = new ScopeCompat();

    @JvmStatic
    public static final <T extends ViewModel> T getViewModel(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class<T> cls) {
        return (T) getViewModel$default(scope, viewModelStoreOwner, cls, null, null, 24, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> T getViewModel(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class<T> cls, Qualifier qualifier) {
        return (T) getViewModel$default(scope, viewModelStoreOwner, cls, qualifier, null, 16, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> viewModel(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class<T> cls) {
        return viewModel$default(scope, viewModelStoreOwner, cls, null, null, 24, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> viewModel(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class<T> cls, Qualifier qualifier) {
        return viewModel$default(scope, viewModelStoreOwner, cls, qualifier, null, 16, null);
    }

    private ScopeCompat() {
    }

    public static /* synthetic */ Lazy viewModel$default(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class cls, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            qualifier = null;
        }
        if ((i & 16) != 0) {
            function0 = null;
        }
        return viewModel(scope, viewModelStoreOwner, cls, qualifier, function0);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> viewModel(final Scope scope, final ViewModelStoreOwner owner, final Class<T> clazz, final Qualifier qualifier, final Function0<DefinitionParameters> parameters) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0<T>() { // from class: org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModel invoke() {
                return ScopeExtKt.getViewModel(scope, owner, JvmClassMappingKt.getKotlinClass(clazz), qualifier, parameters);
            }
        });
    }

    public static /* synthetic */ ViewModel getViewModel$default(Scope scope, ViewModelStoreOwner viewModelStoreOwner, Class cls, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            qualifier = null;
        }
        if ((i & 16) != 0) {
            function0 = null;
        }
        return getViewModel(scope, viewModelStoreOwner, cls, qualifier, function0);
    }

    @JvmStatic
    public static final <T extends ViewModel> T getViewModel(Scope scope, ViewModelStoreOwner owner, Class<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> parameters) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return (T) ScopeExtKt.getViewModel(scope, owner, JvmClassMappingKt.getKotlinClass(clazz), qualifier, parameters);
    }
}
