package org.koin.androidx.viewmodel.koin;

import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;
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
import org.koin.androidx.viewmodel.scope.ScopeStateVMExtKt;
import org.koin.core.Koin;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: KoinStateVMExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a_\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rj\u0004\u0018\u0001`\u000f¢\u0006\u0002\u0010\u0010\u001aV\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0016\b\n\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rj\u0004\u0018\u0001`\u000fH\u0086\b¢\u0006\u0002\u0010\u0011\u001aW\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0013\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0016\b\n\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rj\u0004\u0018\u0001`\u000fH\u0086\b¨\u0006\u0014"}, d2 = {"getStateViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/Koin;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "bundle", "Landroid/os/Bundle;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Lorg/koin/core/Koin;Landroidx/savedstate/SavedStateRegistryOwner;Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "(Lorg/koin/core/Koin;Landroidx/savedstate/SavedStateRegistryOwner;Lorg/koin/core/qualifier/Qualifier;Landroid/os/Bundle;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "stateViewModel", "Lkotlin/Lazy;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class KoinStateVMExtKt {
    public static /* synthetic */ Lazy stateViewModel$default(Koin stateViewModel, SavedStateRegistryOwner owner, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        Qualifier qualifier2;
        Bundle bundle2;
        Function0 function1;
        if ((i & 2) != 0) {
            qualifier2 = null;
        } else {
            qualifier2 = qualifier;
        }
        if ((i & 4) != 0) {
            bundle2 = null;
        } else {
            bundle2 = bundle;
        }
        if ((i & 8) != 0) {
            function1 = null;
        } else {
            function1 = function0;
        }
        Intrinsics.checkParameterIsNotNull(stateViewModel, "$this$stateViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(stateViewModel, owner, qualifier2, bundle2, function1));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.koin.KoinStateVMExtKt$stateViewModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: KoinStateVMExt.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "invoke", "()Landroidx/lifecycle/ViewModel;"}, k = 3, mv = {1, 1, 16})
    public static final class AnonymousClass1<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Bundle $bundle;
        final /* synthetic */ SavedStateRegistryOwner $owner;
        final /* synthetic */ Function0 $parameters;
        final /* synthetic */ Qualifier $qualifier;
        final /* synthetic */ Koin $this_stateViewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Koin koin, SavedStateRegistryOwner savedStateRegistryOwner, Qualifier qualifier, Bundle bundle, Function0 function0) {
            super(0);
            this.$this_stateViewModel = koin;
            this.$owner = savedStateRegistryOwner;
            this.$qualifier = qualifier;
            this.$bundle = bundle;
            this.$parameters = function0;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModel invoke() {
            Koin koin = this.$this_stateViewModel;
            SavedStateRegistryOwner savedStateRegistryOwner = this.$owner;
            Qualifier qualifier = this.$qualifier;
            Bundle bundle = this.$bundle;
            Function0 function0 = this.$parameters;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return KoinStateVMExtKt.getStateViewModel(koin, savedStateRegistryOwner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
        }
    }

    public static final /* synthetic */ <T extends ViewModel> Lazy<T> stateViewModel(Koin stateViewModel, SavedStateRegistryOwner owner, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(stateViewModel, "$this$stateViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        Intrinsics.needClassReification();
        return LazyKt.lazy(lazyThreadSafetyMode, (Function0) new AnonymousClass1(stateViewModel, owner, qualifier, bundle, function0));
    }

    public static /* synthetic */ ViewModel getStateViewModel$default(Koin getStateViewModel, SavedStateRegistryOwner owner, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        Qualifier qualifier2;
        Bundle bundle2;
        Function0 function1;
        if ((i & 2) != 0) {
            qualifier2 = null;
        } else {
            qualifier2 = qualifier;
        }
        if ((i & 4) != 0) {
            bundle2 = null;
        } else {
            bundle2 = bundle;
        }
        if ((i & 8) != 0) {
            function1 = null;
        } else {
            function1 = function0;
        }
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getStateViewModel(getStateViewModel, owner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier2, bundle2, function1);
    }

    public static final /* synthetic */ <T extends ViewModel> T getStateViewModel(Koin getStateViewModel, SavedStateRegistryOwner owner, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getStateViewModel(getStateViewModel, owner, Reflection.getOrCreateKotlinClass(ViewModel.class), qualifier, bundle, function0);
    }

    public static /* synthetic */ ViewModel getStateViewModel$default(Koin koin, SavedStateRegistryOwner savedStateRegistryOwner, KClass kClass, Qualifier qualifier, Bundle bundle, Function0 function0, int i, Object obj) {
        Qualifier qualifier2;
        Bundle bundle2;
        Function0 function1;
        if ((i & 4) != 0) {
            qualifier2 = null;
        } else {
            qualifier2 = qualifier;
        }
        if ((i & 8) != 0) {
            bundle2 = null;
        } else {
            bundle2 = bundle;
        }
        if ((i & 16) != 0) {
            function1 = null;
        } else {
            function1 = function0;
        }
        return getStateViewModel(koin, savedStateRegistryOwner, kClass, qualifier2, bundle2, function1);
    }

    public static final <T extends ViewModel> T getStateViewModel(Koin getStateViewModel, SavedStateRegistryOwner owner, KClass<T> clazz, Qualifier qualifier, Bundle bundle, Function0<DefinitionParameters> function0) {
        Intrinsics.checkParameterIsNotNull(getStateViewModel, "$this$getStateViewModel");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return (T) ScopeStateVMExtKt.getStateViewModel(getStateViewModel.get_scopeRegistry().getRootScope(), owner, clazz, qualifier, bundle, function0);
    }
}
