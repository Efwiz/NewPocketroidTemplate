package org.koin.androidx.viewmodel.dsl;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Definitions;
import org.koin.core.definition.Kind;
import org.koin.core.definition.Options;
import org.koin.core.error.DefinitionOverrideException;
import org.koin.core.module.Module;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;
import org.koin.core.scope.ScopeDefinition;

/* JADX INFO: compiled from: ModuleExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0002\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u0004\u001a\u000e\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u0004\u001a`\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0004\"\n\b\u0000\u0010\b\u0018\u0001*\u00020\t*\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u00032)\b\b\u0010\u000e\u001a#\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\b0\u000fj\b\u0012\u0004\u0012\u0002H\b`\u0012¢\u0006\u0002\b\u0013H\u0086\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"ATTRIBUTE_VIEW_MODEL", "", ModuleExtKt.ATTRIBUTE_VIEW_MODEL, "", "Lorg/koin/core/definition/BeanDefinition;", "setIsViewModel", "", "viewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/module/Module;", "qualifier", "Lorg/koin/core/qualifier/Qualifier;", "override", "definition", "Lkotlin/Function2;", "Lorg/koin/core/scope/Scope;", "Lorg/koin/core/parameter/DefinitionParameters;", "Lorg/koin/core/definition/Definition;", "Lkotlin/ExtensionFunctionType;", "koin-androidx-viewmodel_release"}, k = 2, mv = {1, 1, 16})
public final class ModuleExtKt {
    public static final String ATTRIBUTE_VIEW_MODEL = "isViewModel";

    public static /* synthetic */ BeanDefinition viewModel$default(Module viewModel, Qualifier qualifier, boolean z, Function2 definition, int i, Object obj) throws DefinitionOverrideException {
        Qualifier qualifier2;
        if ((i & 1) != 0) {
            qualifier2 = null;
        } else {
            qualifier2 = qualifier;
        }
        boolean z2 = (i & 2) != 0 ? false : z;
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        Intrinsics.checkParameterIsNotNull(definition, "definition");
        Definitions definitions = Definitions.INSTANCE;
        ScopeDefinition rootScope = viewModel.getRootScope();
        Options optionsMakeOptions$default = Module.makeOptions$default(viewModel, z2, false, 2, null);
        List listEmptyList = CollectionsKt.emptyList();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        BeanDefinition beanDefinition = new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(Object.class), qualifier2, definition, Kind.Factory, listEmptyList, optionsMakeOptions$default, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        ScopeDefinition.save$default(rootScope, beanDefinition, false, 2, null);
        setIsViewModel(beanDefinition);
        return beanDefinition;
    }

    public static final void setIsViewModel(BeanDefinition<?> setIsViewModel) {
        Intrinsics.checkParameterIsNotNull(setIsViewModel, "$this$setIsViewModel");
        setIsViewModel.getProperties().set(ATTRIBUTE_VIEW_MODEL, true);
    }

    public static final boolean isViewModel(BeanDefinition<?> isViewModel) {
        Intrinsics.checkParameterIsNotNull(isViewModel, "$this$isViewModel");
        Boolean bool = (Boolean) isViewModel.getProperties().getOrNull(ATTRIBUTE_VIEW_MODEL);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static final /* synthetic */ <T extends ViewModel> BeanDefinition<T> viewModel(Module viewModel, Qualifier qualifier, boolean z, Function2<? super Scope, ? super DefinitionParameters, ? extends T> definition) throws DefinitionOverrideException {
        Intrinsics.checkParameterIsNotNull(viewModel, "$this$viewModel");
        Intrinsics.checkParameterIsNotNull(definition, "definition");
        Definitions definitions = Definitions.INSTANCE;
        ScopeDefinition rootScope = viewModel.getRootScope();
        Options optionsMakeOptions$default = Module.makeOptions$default(viewModel, z, false, 2, null);
        List listEmptyList = CollectionsKt.emptyList();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        BeanDefinition<T> beanDefinition = new BeanDefinition<>(rootScope, Reflection.getOrCreateKotlinClass(Object.class), qualifier, definition, Kind.Factory, listEmptyList, optionsMakeOptions$default, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        ScopeDefinition.save$default(rootScope, beanDefinition, false, 2, null);
        setIsViewModel(beanDefinition);
        return beanDefinition;
    }
}
