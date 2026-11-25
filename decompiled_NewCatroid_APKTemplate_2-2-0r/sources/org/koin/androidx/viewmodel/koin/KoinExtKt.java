package org.koin.androidx.viewmodel.koin;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.name.SpecialNames;
import org.koin.androidx.viewmodel.ViewModelParameter;
import org.koin.androidx.viewmodel.scope.ScopeExtKt;
import org.koin.core.Koin;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: KoinExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aS\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\r¢\u0006\u0002\u0010\u000e\u001aJ\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\n\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\rH\u0086\b¢\u0006\u0002\u0010\u000f\u001a'\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0011¢\u0006\u0002\u0010\u0012\u001aK\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0014\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\n\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\rH\u0086\b¨\u0006\u0015"}, d2 = {"getViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/Koin;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Lorg/koin/core/Koin;Landroidx/lifecycle/ViewModelStoreOwner;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Lorg/koin/core/Koin;Landroidx/lifecycle/ViewModelStoreOwner;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "viewModelParameters", "Lorg/koin/androidx/viewmodel/ViewModelParameter;", "(Lorg/koin/core/Koin;Lorg/koin/androidx/viewmodel/ViewModelParameter;)Landroidx/lifecycle/ViewModel;", "viewModel", "Lkotlin/Lazy;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class KoinExtKt {
    public static /* synthetic */ Lazy viewModel$default(Koin viewModel, ViewModelStoreOwner owner, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(viewModel, owner, qualifier, function0));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.koin.KoinExtKt$viewModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: KoinExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class AnonymousClass1<T> extends Lambda implements Function0<T> {
        final /* synthetic */ ViewModelStoreOwner $owner;
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ Koin $this_viewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Koin koin, ViewModelStoreOwner viewModelStoreOwner, Qualifier qualifier, Function0 function0) {
            super(0);
            this.$this_viewModel = koin;
            this.$owner = viewModelStoreOwner;
            this.$qualifier = qualifier;
            this.$parameters = function0;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            Koin koin = this.$this_viewModel;
            ViewModelStoreOwner viewModelStoreOwner = this.$owner;
            Qualifier qualifier = this.$qualifier;
            Function0 function0 = this.$parameters;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return KoinExtKt.getViewModel(koin, viewModelStoreOwner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> viewModel(Koin viewModel, ViewModelStoreOwner owner, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(viewModel, owner, qualifier, function0));
    }

    public static /* synthetic */ ViewModel getViewModel$default(Koin getViewModel, ViewModelStoreOwner owner, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getViewModel(getViewModel, owner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static final /* synthetic */ <T extends ViewModel> T getViewModel(Koin getViewModel, ViewModelStoreOwner owner, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getViewModel(getViewModel, owner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static /* synthetic */ ViewModel getViewModel$default(Koin koin, ViewModelStoreOwner viewModelStoreOwner, KClass kClass, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            qualifier = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return getViewModel(koin, viewModelStoreOwner, kClass, qualifier, function0);
    }

    public static final <T extends ViewModel> T getViewModel(Koin getViewModel, ViewModelStoreOwner owner, KClass<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return (T) ScopeExtKt.getViewModel(getViewModel.get_scopeRegistry().getRootScope(), owner, clazz, qualifier, function0);
    }

    public static final <T extends ViewModel> T getViewModel(Koin getViewModel, ViewModelParameter<T> viewModelParameters) {
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.checkParameterIsNotNull(viewModelParameters, "viewModelParameters");
        return (T) ScopeExtKt.getViewModel(getViewModel.get_scopeRegistry().getRootScope(), viewModelParameters);
    }
}
