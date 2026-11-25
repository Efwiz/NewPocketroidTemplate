package org.koin.androidx.viewmodel;

import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModelStore;
import androidx.savedstate.SavedStateRegistryOwner;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: ViewModelParameter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002BW\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001f\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/koin/androidx/viewmodel/ViewModelParameter;", ExifInterface.GPS_DIRECTION_TRUE, "", "clazz", "Lkotlin/reflect/KClass;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "bundle", "Landroid/os/Bundle;", "viewModelStore", "Landroidx/lifecycle/ViewModelStore;", "stateRegistryOwner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Lkotlin/reflect/KClass;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;Landroid/os/Bundle;Landroidx/lifecycle/ViewModelStore;Landroidx/savedstate/SavedStateRegistryOwner;)V", "getBundle", "()Landroid/os/Bundle;", "getClazz", "()Lkotlin/reflect/KClass;", "getParameters", "()Lkotlin/jvm/functions/Function0;", "getQualifier", "()Lorg/koin/core/qualifier/Qualifier;", "getStateRegistryOwner", "()Landroidx/savedstate/SavedStateRegistryOwner;", "getViewModelStore", "()Landroidx/lifecycle/ViewModelStore;", "koin-androidx-viewmodel_release"}, k = 1, mv = {1, 1, 16})
public final class ViewModelParameter<T> {
    private final Bundle bundle;
    private final KClass<T> clazz;
    private final Function0<DefinitionParameters> parameters;
    private final Qualifier qualifier;
    private final SavedStateRegistryOwner stateRegistryOwner;
    private final ViewModelStore viewModelStore;

    public ViewModelParameter(KClass<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> function0, Bundle bundle, ViewModelStore viewModelStore, SavedStateRegistryOwner savedStateRegistryOwner) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(viewModelStore, "viewModelStore");
        this.clazz = clazz;
        this.qualifier = qualifier;
        this.parameters = function0;
        this.bundle = bundle;
        this.viewModelStore = viewModelStore;
        this.stateRegistryOwner = savedStateRegistryOwner;
    }

    public final KClass<T> getClazz() {
        return this.clazz;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ViewModelParameter(KClass kClass, Qualifier qualifier, Function0 function0, Bundle bundle, ViewModelStore viewModelStore, SavedStateRegistryOwner savedStateRegistryOwner, int i, DefaultConstructorMarker defaultConstructorMarker) {
        Qualifier qualifier2;
        Function0 function1;
        Bundle bundle2;
        SavedStateRegistryOwner savedStateRegistryOwner2;
        if ((i & 2) != 0) {
            qualifier2 = null;
        } else {
            qualifier2 = qualifier;
        }
        if ((i & 4) != 0) {
            function1 = null;
        } else {
            function1 = function0;
        }
        if ((i & 8) != 0) {
            bundle2 = null;
        } else {
            bundle2 = bundle;
        }
        if ((i & 32) != 0) {
            savedStateRegistryOwner2 = null;
        } else {
            savedStateRegistryOwner2 = savedStateRegistryOwner;
        }
        this(kClass, qualifier2, function1, bundle2, viewModelStore, savedStateRegistryOwner2);
    }

    public final Qualifier getQualifier() {
        return this.qualifier;
    }

    public final Function0<DefinitionParameters> getParameters() {
        return this.parameters;
    }

    public final Bundle getBundle() {
        return this.bundle;
    }

    public final ViewModelStore getViewModelStore() {
        return this.viewModelStore;
    }

    public final SavedStateRegistryOwner getStateRegistryOwner() {
        return this.stateRegistryOwner;
    }
}
