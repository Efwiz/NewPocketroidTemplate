package org.koin.androidx.viewmodel;

import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.koin.core.error.DefinitionParameterException;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.parameter.DefinitionParametersKt;
import org.koin.core.scope.Scope;

/* JADX INFO: compiled from: ViewModelFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a\"\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006¨\u0006\n"}, d2 = {"defaultViewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/scope/Scope;", "parameters", "Lorg/koin/androidx/viewmodel/ViewModelParameter;", "stateViewModelFactory", "Landroidx/lifecycle/AbstractSavedStateViewModelFactory;", "vmParams", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class ViewModelFactoryKt {
    public static final <T extends ViewModel> AbstractSavedStateViewModelFactory stateViewModelFactory(Scope stateViewModelFactory, ViewModelParameter<T> vmParams) {
        Intrinsics.checkParameterIsNotNull(stateViewModelFactory, "$this$stateViewModelFactory");
        Intrinsics.checkParameterIsNotNull(vmParams, "vmParams");
        SavedStateRegistryOwner stateRegistryOwner = vmParams.getStateRegistryOwner();
        if (stateRegistryOwner == null) {
            throw new IllegalStateException("Can't create SavedStateViewModelFactory without a proper stateRegistryOwner".toString());
        }
        return new C50461(stateViewModelFactory, vmParams, stateRegistryOwner, stateRegistryOwner, vmParams.getBundle());
    }

    /* JADX INFO: renamed from: org.koin.androidx.viewmodel.ViewModelFactoryKt$stateViewModelFactory$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ViewModelFactory.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J7\u0010\u0002\u001a\u0002H\u0003\"\n\b\u0000\u0010\u0003*\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u0014¢\u0006\u0002\u0010\u000bJ\u001f\u0010\f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000e0\r2\u0006\u0010\t\u001a\u00020\nH\u0002¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"org/koin/androidx/viewmodel/ViewModelFactoryKt$stateViewModelFactory$1", "Landroidx/lifecycle/AbstractSavedStateViewModelFactory;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "modelClass", "Ljava/lang/Class;", "handle", "Landroidx/lifecycle/SavedStateHandle;", "(Ljava/lang/String;Ljava/lang/Class;Landroidx/lifecycle/SavedStateHandle;)Landroidx/lifecycle/ViewModel;", "insertStateParameter", "", "", "(Landroidx/lifecycle/SavedStateHandle;)[Ljava/lang/Object;", "koin-androidx-viewmodel_release"}, k = 1, mv = {1, 1, 16})
    public static final class C50461 extends AbstractSavedStateViewModelFactory {
        final /* synthetic */ SavedStateRegistryOwner $registryOwner;
        final /* synthetic */ Scope $this_stateViewModelFactory;
        final /* synthetic */ ViewModelParameter $vmParams;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C50461(Scope scope, ViewModelParameter viewModelParameter, SavedStateRegistryOwner savedStateRegistryOwner, SavedStateRegistryOwner savedStateRegistryOwner2, Bundle bundle) {
            super(savedStateRegistryOwner2, bundle);
            this.$this_stateViewModelFactory = scope;
            this.$vmParams = viewModelParameter;
            this.$registryOwner = savedStateRegistryOwner;
        }

        @Override // androidx.lifecycle.AbstractSavedStateViewModelFactory
        protected <T extends ViewModel> T create(String key, Class<T> modelClass, final SavedStateHandle handle) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
            Intrinsics.checkParameterIsNotNull(handle, "handle");
            return (T) this.$this_stateViewModelFactory.get(this.$vmParams.getClazz(), this.$vmParams.getQualifier(), new Function0<DefinitionParameters>() { // from class: org.koin.androidx.viewmodel.ViewModelFactoryKt$stateViewModelFactory$1$create$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final DefinitionParameters invoke() throws DefinitionParameterException {
                    Object[] objArrInsertStateParameter = this.this$0.insertStateParameter(handle);
                    return DefinitionParametersKt.parametersOf(Arrays.copyOf(objArrInsertStateParameter, objArrInsertStateParameter.length));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object[] insertStateParameter(SavedStateHandle handle) throws DefinitionParameterException {
            DefinitionParameters definitionParametersEmptyParametersHolder;
            Function0<DefinitionParameters> parameters = this.$vmParams.getParameters();
            if (parameters == null || (definitionParametersEmptyParametersHolder = parameters.invoke()) == null) {
                definitionParametersEmptyParametersHolder = DefinitionParametersKt.emptyParametersHolder();
            }
            List mutableList = ArraysKt.toMutableList(definitionParametersEmptyParametersHolder.getValues());
            if (mutableList.size() > 4) {
                throw new DefinitionParameterException("Can't add SavedStateHandle to your definition function parameters, as you already have " + mutableList.size() + " elements: " + mutableList);
            }
            mutableList.add(0, handle);
            Object[] array = mutableList.toArray(new Object[0]);
            if (array != null) {
                return array;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    public static final <T extends ViewModel> ViewModelProvider.Factory defaultViewModelFactory(final Scope defaultViewModelFactory, final ViewModelParameter<T> parameters) {
        Intrinsics.checkParameterIsNotNull(defaultViewModelFactory, "$this$defaultViewModelFactory");
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        return new ViewModelProvider.Factory() { // from class: org.koin.androidx.viewmodel.ViewModelFactoryKt.defaultViewModelFactory.1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public <T extends ViewModel> T create(Class<T> modelClass) {
                Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
                return (T) defaultViewModelFactory.get(parameters.getClazz(), parameters.getQualifier(), parameters.getParameters());
            }
        };
    }
}
