package org.koin.androidx.viewmodel.ext.android;

import android.content.ComponentCallbacks;
import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;
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
import org.koin.androidx.viewmodel.koin.KoinStateVMExtKt;
import org.koin.core.Koin;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: SavedStateRegistryOwnerExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001aW\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000ej\u0004\u0018\u0001`\u0010¢\u0006\u0002\u0010\u0011\u001aN\u0010\u0003\u001a\u0002H\u0004\"\n\b\u0000\u0010\u0004\u0018\u0001*\u00020\u0005*\u00020\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0016\b\n\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000ej\u0004\u0018\u0001`\u0010H\u0086\b¢\u0006\u0002\u0010\u0012\u001aX\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0014\"\b\b\u0000\u0010\u0004*\u00020\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000ej\u0004\u0018\u0001`\u0010\u001a[\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0014\"\n\b\u0000\u0010\u0004\u0018\u0001*\u00020\u0005*\u00020\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0016\b\n\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\f\u0018\u00010\u000ej\u0004\u0018\u0001`\u00152\u0016\b\n\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000ej\u0004\u0018\u0001`\u0010H\u0086\b¨\u0006\u0016"}, d2 = {"getKoin", "Lorg/koin/core/Koin;", "Landroidx/lifecycle/LifecycleOwner;", "getStateViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Landroidx/savedstate/SavedStateRegistryOwner;", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "bundle", "Landroid/os/Bundle;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Landroidx/savedstate/SavedStateRegistryOwner;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Landroidx/savedstate/SavedStateRegistryOwner;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "stateViewModel", "Lkotlin/Lazy;", "Lorg/koin/androidx/viewmodel/StateBundleParameter;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class SavedStateRegistryOwnerExtKt {
    public static /* synthetic */ Lazy stateViewModel$default(SavedStateRegistryOwner savedStateRegistryOwner, KClass kClass, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            bundle = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return stateViewModel(savedStateRegistryOwner, kClass, qualifier, bundle, function0);
    }

    public static final <T extends ViewModel> Lazy<T> stateViewModel(final SavedStateRegistryOwner stateViewModel, final KClass<T> clazz, final Qualifier qualifier, final Bundle bundle, final Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(stateViewModel, "$this$stateViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0<T>() { // from class: org.koin.androidx.viewmodel.ext.android.SavedStateRegistryOwnerExtKt.stateViewModel.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModel invoke() {
                return SavedStateRegistryOwnerExtKt.getStateViewModel(stateViewModel, clazz, qualifier, bundle, function0);
            }
        });
    }

    public static /* synthetic */ Lazy stateViewModel$default(SavedStateRegistryOwner stateViewModel, Qualifier qualifier, Function0 function0, Function0 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 4) != 0) {
            function1 = null;
        }
        Intrinsics.checkParameterIsNotNull(stateViewModel, "$this$stateViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass2(stateViewModel, qualifier, function0, function1));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.ext.android.SavedStateRegistryOwnerExtKt$stateViewModel$2, reason: invalid class name */
    /* JADX INFO: compiled from: SavedStateRegistryOwnerExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class AnonymousClass2<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Function0 $bundle;
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ SavedStateRegistryOwner $this_stateViewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(SavedStateRegistryOwner savedStateRegistryOwner, Qualifier qualifier, Function0 function0, Function0 function1) {
            super(0);
            this.$this_stateViewModel = savedStateRegistryOwner;
            this.$qualifier = qualifier;
            this.$bundle = function0;
            this.$parameters = function1;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            SavedStateRegistryOwner savedStateRegistryOwner = this.$this_stateViewModel;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(ViewModel.class);
            Qualifier qualifier = this.$qualifier;
            Function0 function0 = this.$bundle;
            return SavedStateRegistryOwnerExtKt.getStateViewModel(savedStateRegistryOwner, orCreateKotlinClass, qualifier, function0 != null ? (Bundle) function0.invoke() : null, this.$parameters);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> stateViewModel(SavedStateRegistryOwner stateViewModel, Qualifier qualifier, Function0<Bundle> function0, Function0<DefinitionParameters> function1) {
        Intrinsics.checkParameterIsNotNull(stateViewModel, "$this$stateViewModel");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass2(stateViewModel, qualifier, function0, function1));
    }

    public static /* synthetic */ ViewModel getStateViewModel$default(SavedStateRegistryOwner getStateViewModel, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            qualifier = null;
        }
        if ((i & 2) != 0) {
            bundle = null;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getStateViewModel(getStateViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
    }

    public static final /* synthetic */ <T extends ViewModel> T getStateViewModel(SavedStateRegistryOwner getStateViewModel, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getStateViewModel(getStateViewModel, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
    }

    public static /* synthetic */ ViewModel getStateViewModel$default(SavedStateRegistryOwner savedStateRegistryOwner, KClass kClass, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            qualifier = null;
        }
        if ((i & 4) != 0) {
            bundle = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return getStateViewModel(savedStateRegistryOwner, kClass, qualifier, bundle, function0);
    }

    public static final <T extends ViewModel> T getStateViewModel(SavedStateRegistryOwner getStateViewModel, KClass<T> clazz, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        if (bundle == null) {
            bundle = new Bundle();
        }
        return (T) KoinStateVMExtKt.getStateViewModel(getKoin(getStateViewModel), getStateViewModel, clazz, qualifier, bundle, function0);
    }

    private static final Koin getKoin(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner != null) {
            return ComponentCallbackExtKt.getKoin((ComponentCallbacks) lifecycleOwner);
        }
        throw new TypeCastException("null cannot be cast to non-null type android.content.ComponentCallbacks");
    }
}
