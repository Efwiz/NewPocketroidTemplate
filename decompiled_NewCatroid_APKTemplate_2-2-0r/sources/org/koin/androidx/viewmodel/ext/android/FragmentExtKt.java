package org.koin.androidx.viewmodel.ext.android;

import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
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
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.androidx.viewmodel.koin.KoinExtKt;
import org.koin.androidx.viewmodel.koin.KoinStateVMExtKt;
import org.koin.core.Koin;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: FragmentExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aK\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000b¢\u0006\u0002\u0010\f\u001aB\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0016\b\n\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000bH\u0086\b¢\u0006\u0002\u0010\r\u001aW\u0010\u000e\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000b¢\u0006\u0002\u0010\u0011\u001aN\u0010\u000e\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\b\n\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000bH\u0086\b¢\u0006\u0002\u0010\u0012\u001aL\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0014\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000b\u001aC\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0014\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0016\b\n\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000bH\u0086\b\u001aX\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0014\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000b\u001aO\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0014\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\b\n\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0004\u0018\u0001`\u000bH\u0086\b¨\u0006\u0016"}, d2 = {"getSharedViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Landroidx/fragment/app/Fragment;", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Landroidx/fragment/app/Fragment;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Landroidx/fragment/app/Fragment;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "getStateSharedViewModel", "bundle", "Landroid/os/Bundle;", "(Landroidx/fragment/app/Fragment;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Landroidx/fragment/app/Fragment;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "sharedViewModel", "Lkotlin/Lazy;", "stateSharedViewModel", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class FragmentExtKt {
    public static /* synthetic */ Lazy sharedViewModel$default(Fragment sharedViewModel, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(sharedViewModel, "$this$sharedViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(sharedViewModel, qualifier, function0));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.ext.android.FragmentExtKt$sharedViewModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: FragmentExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class AnonymousClass1<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ Fragment $this_sharedViewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Fragment fragment, Qualifier qualifier, Function0 function0) {
            super(0);
            this.$this_sharedViewModel = fragment;
            this.$qualifier = qualifier;
            this.$parameters = function0;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            Fragment fragment = this.$this_sharedViewModel;
            Qualifier qualifier = this.$qualifier;
            Function0 function0 = this.$parameters;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return FragmentExtKt.getSharedViewModel(fragment, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> sharedViewModel(Fragment sharedViewModel, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(sharedViewModel, "$this$sharedViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(sharedViewModel, qualifier, function0));
    }

    public static /* synthetic */ Lazy sharedViewModel$default(Fragment fragment, KClass kClass, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        return sharedViewModel(fragment, kClass, qualifier, function0);
    }

    public static final <T extends ViewModel> Lazy<T> sharedViewModel(final Fragment sharedViewModel, final KClass<T> clazz, final Qualifier qualifier, final Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(sharedViewModel, "$this$sharedViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0<T>() { // from class: org.koin.androidx.viewmodel.ext.android.FragmentExtKt.sharedViewModel.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModel invoke() {
                return FragmentExtKt.getSharedViewModel(sharedViewModel, clazz, qualifier, function0);
            }
        });
    }

    public static /* synthetic */ ViewModel getSharedViewModel$default(Fragment getSharedViewModel, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(getSharedViewModel, "$this$getSharedViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getSharedViewModel(getSharedViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static final /* synthetic */ <T extends ViewModel> T getSharedViewModel(Fragment getSharedViewModel, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getSharedViewModel, "$this$getSharedViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getSharedViewModel(getSharedViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, function0);
    }

    public static /* synthetic */ ViewModel getSharedViewModel$default(Fragment fragment, KClass kClass, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        return getSharedViewModel(fragment, kClass, qualifier, function0);
    }

    public static final <T extends ViewModel> T getSharedViewModel(Fragment getSharedViewModel, KClass<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getSharedViewModel, "$this$getSharedViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Koin koin = ComponentCallbackExtKt.getKoin(getSharedViewModel);
        FragmentActivity fragmentActivityRequireActivity = getSharedViewModel.requireActivity();
        Intrinsics.checkExpressionValueIsNotNull(fragmentActivityRequireActivity, "requireActivity()");
        return (T) KoinExtKt.getViewModel(koin, fragmentActivityRequireActivity, clazz, qualifier, function0);
    }

    public static /* synthetic */ Lazy stateSharedViewModel$default(Fragment fragment, KClass kClass, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            bundle = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return stateSharedViewModel(fragment, kClass, qualifier, bundle, function0);
    }

    public static final <T extends ViewModel> Lazy<T> stateSharedViewModel(final Fragment stateSharedViewModel, final KClass<T> clazz, final Qualifier qualifier, final Bundle bundle, final Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(stateSharedViewModel, "$this$stateSharedViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0<T>() { // from class: org.koin.androidx.viewmodel.ext.android.FragmentExtKt.stateSharedViewModel.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModel invoke() {
                return FragmentExtKt.getStateSharedViewModel(stateSharedViewModel, clazz, qualifier, bundle, function0);
            }
        });
    }

    public static /* synthetic */ Lazy stateSharedViewModel$default(Fragment stateSharedViewModel, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            bundle = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(stateSharedViewModel, "$this$stateSharedViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new C50482(stateSharedViewModel, qualifier, bundle, function0));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.ext.android.FragmentExtKt$stateSharedViewModel$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FragmentExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class C50482<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Bundle $bundle;
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ Fragment $this_stateSharedViewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C50482(Fragment fragment, Qualifier qualifier, Bundle bundle, Function0 function0) {
            super(0);
            this.$this_stateSharedViewModel = fragment;
            this.$qualifier = qualifier;
            this.$bundle = bundle;
            this.$parameters = function0;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            Fragment fragment = this.$this_stateSharedViewModel;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return FragmentExtKt.getStateSharedViewModel(fragment, Reflection.getOrCreateKotlinClass(ViewModel.class), this.$qualifier, this.$bundle, this.$parameters);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> stateSharedViewModel(Fragment stateSharedViewModel, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(stateSharedViewModel, "$this$stateSharedViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new C50482(stateSharedViewModel, qualifier, bundle, function0));
    }

    public static /* synthetic */ ViewModel getStateSharedViewModel$default(Fragment getStateSharedViewModel, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            bundle = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(getStateSharedViewModel, "$this$getStateSharedViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return SavedStateRegistryOwnerExtKt.getStateViewModel(getStateSharedViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
    }

    public static final /* synthetic */ <T extends ViewModel> T getStateSharedViewModel(Fragment getStateSharedViewModel, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateSharedViewModel, "$this$getStateSharedViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) SavedStateRegistryOwnerExtKt.getStateViewModel(getStateSharedViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
    }

    public static /* synthetic */ ViewModel getStateSharedViewModel$default(Fragment fragment, KClass kClass, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            bundle = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return getStateSharedViewModel(fragment, kClass, qualifier, bundle, function0);
    }

    public static final <T extends ViewModel> T getStateSharedViewModel(Fragment getStateSharedViewModel, KClass<T> clazz, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateSharedViewModel, "$this$getStateSharedViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        Koin koin = ComponentCallbackExtKt.getKoin(getStateSharedViewModel);
        FragmentActivity fragmentActivityRequireActivity = getStateSharedViewModel.requireActivity();
        Intrinsics.checkExpressionValueIsNotNull(fragmentActivityRequireActivity, "requireActivity()");
        return (T) KoinStateVMExtKt.getStateViewModel(koin, fragmentActivityRequireActivity, clazz, qualifier, bundle2, function0);
    }
}
