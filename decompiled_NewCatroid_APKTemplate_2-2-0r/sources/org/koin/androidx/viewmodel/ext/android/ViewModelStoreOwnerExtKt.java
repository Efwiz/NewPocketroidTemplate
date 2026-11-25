package org.koin.androidx.viewmodel.ext.android;

import android.content.ComponentCallbacks;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.name.SpecialNames;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.androidx.viewmodel.koin.KoinExtKt;
import org.koin.core.Koin;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: ViewModelStoreOwnerExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001aK\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u0005*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\r¢\u0006\u0002\u0010\u000e\u001aB\u0010\u0003\u001a\u0002H\u0004\"\n\b\u0000\u0010\u0004\u0018\u0001*\u00020\u0005*\u00020\u00022\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\n\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\rH\u0086\b¢\u0006\u0002\u0010\u000f\u001aL\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\b\b\u0000\u0010\u0004*\u00020\u0005*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\r\u001aC\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\n\b\u0000\u0010\u0004\u0018\u0001*\u00020\u0005*\u00020\u00022\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0016\b\n\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bj\u0004\u0018\u0001`\rH\u0086\b¨\u0006\u0012"}, d2 = {"getKoin", "Lorg/koin/core/Koin;", "Landroidx/lifecycle/ViewModelStoreOwner;", "getViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Landroidx/lifecycle/ViewModelStoreOwner;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Landroidx/lifecycle/ViewModelStoreOwner;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "viewModel", "Lkotlin/Lazy;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class ViewModelStoreOwnerExtKt {
    public static /* synthetic */ Lazy viewModel$default(ViewModelStoreOwner viewModelStoreOwner, KClass kClass, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        return viewModel(viewModelStoreOwner, kClass, qualifier, function0);
    }

    public static final <T extends ViewModel> Lazy<T> viewModel(final ViewModelStoreOwner viewModel, final KClass<T> clazz, final Qualifier qualifier, final Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0<T>() { // from class: org.koin.androidx.viewmodel.ext.android.ViewModelStoreOwnerExtKt.viewModel.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModel invoke() {
                return ViewModelStoreOwnerExtKt.getViewModel(viewModel, clazz, qualifier, function0);
            }
        });
    }

    public static /* synthetic */ Lazy viewModel$default(ViewModelStoreOwner viewModel, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass2(viewModel, qualifier, function0));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.ext.android.ViewModelStoreOwnerExtKt$viewModel$2, reason: invalid class name */
    /* JADX INFO: compiled from: ViewModelStoreOwnerExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class AnonymousClass2<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ ViewModelStoreOwner $this_viewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ViewModelStoreOwner viewModelStoreOwner, Qualifier qualifier, Function0 function0) {
            super(0);
            this.$this_viewModel = viewModelStoreOwner;
            this.$qualifier = qualifier;
            this.$parameters = function0;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            ViewModelStoreOwner viewModelStoreOwner = this.$this_viewModel;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return ViewModelStoreOwnerExtKt.getViewModel(viewModelStoreOwner, Reflection.getOrCreateKotlinClass(ViewModel.class), this.$qualifier, this.$parameters);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> viewModel(ViewModelStoreOwner viewModel, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass2(viewModel, qualifier, function0));
    }

    public static /* synthetic */ ViewModel getViewModel$default(ViewModelStoreOwner getViewModel, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getViewModel(getViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static final /* synthetic */ <T extends ViewModel> T getViewModel(ViewModelStoreOwner getViewModel, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getViewModel(getViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static /* synthetic */ ViewModel getViewModel$default(ViewModelStoreOwner viewModelStoreOwner, KClass kClass, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        return getViewModel(viewModelStoreOwner, kClass, qualifier, function0);
    }

    public static final <T extends ViewModel> T getViewModel(ViewModelStoreOwner getViewModel, KClass<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getViewModel, "$this$getViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return (T) KoinExtKt.getViewModel(getKoin(getViewModel), getViewModel, clazz, qualifier, function0);
    }

    private static final Koin getKoin(ViewModelStoreOwner viewModelStoreOwner) {
        if (viewModelStoreOwner != null) {
            return ComponentCallbackExtKt.getKoin((ComponentCallbacks) viewModelStoreOwner);
        }
        throw new TypeCastException("null cannot be cast to non-null type android.content.ComponentCallbacks");
    }
}
