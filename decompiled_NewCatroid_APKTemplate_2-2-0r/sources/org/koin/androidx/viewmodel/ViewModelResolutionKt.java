package org.koin.androidx.viewmodel;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;

/* JADX INFO: compiled from: ViewModelResolution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000\u001aA\u0010\u0007\u001a\u0002H\u0002\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0000¢\u0006\u0002\u0010\f\u001a)\u0010\r\u001a\u0002H\u0002\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"createViewModelProvider", "Landroidx/lifecycle/ViewModelProvider;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/scope/Scope;", "viewModelParameters", "Lorg/koin/androidx/viewmodel/ViewModelParameter;", "get", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "javaClass", "Ljava/lang/Class;", "(Landroidx/lifecycle/ViewModelProvider;Lorg/koin/androidx/viewmodel/ViewModelParameter;Lorg/koin/core/qualifier/Qualifier;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "resolveInstance", "(Landroidx/lifecycle/ViewModelProvider;Lorg/koin/androidx/viewmodel/ViewModelParameter;)Landroidx/lifecycle/ViewModel;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class ViewModelResolutionKt {
    public static final <T extends ViewModel> T resolveInstance(ViewModelProvider resolveInstance, ViewModelParameter<T> viewModelParameters) {
        Intrinsics.checkParameterIsNotNull(resolveInstance, "$this$resolveInstance");
        Intrinsics.checkParameterIsNotNull(viewModelParameters, "viewModelParameters");
        return (T) get(resolveInstance, viewModelParameters, viewModelParameters.getQualifier(), JvmClassMappingKt.getJavaClass((KClass) viewModelParameters.getClazz()));
    }

    public static final <T extends ViewModel> T get(ViewModelProvider get, ViewModelParameter<T> viewModelParameters, Qualifier qualifier, Class<T> javaClass) {
        Intrinsics.checkParameterIsNotNull(get, "$this$get");
        Intrinsics.checkParameterIsNotNull(viewModelParameters, "viewModelParameters");
        Intrinsics.checkParameterIsNotNull(javaClass, "javaClass");
        if (viewModelParameters.getQualifier() != null) {
            T t = (T) get.get(String.valueOf(qualifier), javaClass);
            Intrinsics.checkExpressionValueIsNotNull(t, "get(qualifier.toString(), javaClass)");
            return t;
        }
        T t2 = (T) get.get(javaClass);
        Intrinsics.checkExpressionValueIsNotNull(t2, "get(javaClass)");
        return t2;
    }

    public static final <T extends ViewModel> ViewModelProvider createViewModelProvider(Scope createViewModelProvider, ViewModelParameter<T> viewModelParameters) {
        AbstractSavedStateViewModelFactory abstractSavedStateViewModelFactoryDefaultViewModelFactory;
        Intrinsics.checkParameterIsNotNull(createViewModelProvider, "$this$createViewModelProvider");
        Intrinsics.checkParameterIsNotNull(viewModelParameters, "viewModelParameters");
        ViewModelStore viewModelStore = viewModelParameters.getViewModelStore();
        if (viewModelParameters.getBundle() != null) {
            abstractSavedStateViewModelFactoryDefaultViewModelFactory = ViewModelFactoryKt.stateViewModelFactory(createViewModelProvider, viewModelParameters);
        } else {
            abstractSavedStateViewModelFactoryDefaultViewModelFactory = ViewModelFactoryKt.defaultViewModelFactory(createViewModelProvider, viewModelParameters);
        }
        return new ViewModelProvider(viewModelStore, abstractSavedStateViewModelFactoryDefaultViewModelFactory);
    }
}
