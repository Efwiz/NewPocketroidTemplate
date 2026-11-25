package org.koin.androidx.viewmodel.compat;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.koin.androidx.viewmodel.ext.android.FragmentExtKt;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;

/* JADX INFO: compiled from: SharedViewModelCompat.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JQ\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00040\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rj\u0004\u0018\u0001`\u000fH\u0007¢\u0006\u0002\u0010\u0010JR\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0012\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00040\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rj\u0004\u0018\u0001`\u000fH\u0007¨\u0006\u0013"}, d2 = {"Lorg/koin/androidx/viewmodel/compat/SharedViewModelCompat;", "", "()V", "getSharedViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "fragment", "Landroidx/fragment/app/Fragment;", "clazz", "Ljava/lang/Class;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "parameters", "Lkotlin/Function0;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/parameter/ParametersDefinition;", "(Landroidx/fragment/app/Fragment;Ljava/lang/Class;Lorg/koin/core/qualifier/Qualifier;Lkotlin/jvm/functions/Function0;)Landroidx/lifecycle/ViewModel;", "sharedViewModel", "Lkotlin/Lazy;", "koin-androidx-viewmodel_release"}, k = 1, mv = {1, 1, 16})
public final class SharedViewModelCompat {
    public static final SharedViewModelCompat INSTANCE = new SharedViewModelCompat();

    @JvmStatic
    public static final <T extends ViewModel> T getSharedViewModel(Fragment fragment, Class<T> cls) {
        return (T) getSharedViewModel$default(fragment, cls, null, null, 12, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> T getSharedViewModel(Fragment fragment, Class<T> cls, Qualifier qualifier) {
        return (T) getSharedViewModel$default(fragment, cls, qualifier, null, 8, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> sharedViewModel(Fragment fragment, Class<T> cls) {
        return sharedViewModel$default(fragment, cls, null, null, 12, null);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> sharedViewModel(Fragment fragment, Class<T> cls, Qualifier qualifier) {
        return sharedViewModel$default(fragment, cls, qualifier, null, 8, null);
    }

    private SharedViewModelCompat() {
    }

    public static /* synthetic */ Lazy sharedViewModel$default(Fragment fragment, Class cls, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            qualifier = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return sharedViewModel(fragment, cls, qualifier, function0);
    }

    @JvmStatic
    public static final <T extends ViewModel> Lazy<T> sharedViewModel(Fragment fragment, Class<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> parameters) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return FragmentExtKt.sharedViewModel(fragment, JvmClassMappingKt.getKotlinClass(clazz), qualifier, parameters);
    }

    public static /* synthetic */ ViewModel getSharedViewModel$default(Fragment fragment, Class cls, Qualifier qualifier, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            qualifier = null;
        }
        if ((i & 8) != 0) {
            function0 = null;
        }
        return getSharedViewModel(fragment, cls, qualifier, function0);
    }

    @JvmStatic
    public static final <T extends ViewModel> T getSharedViewModel(Fragment fragment, Class<T> clazz, Qualifier qualifier, Function0<DefinitionParameters> parameters) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return (T) FragmentExtKt.getSharedViewModel(fragment, JvmClassMappingKt.getKotlinClass(clazz), qualifier, parameters);
    }
}
