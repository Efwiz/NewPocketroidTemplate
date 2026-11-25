package org.koin.androidx.fragment.koin;

import androidx.fragment.app.FragmentFactory;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.koin.androidx.fragment.android.KoinFragmentFactory;
import org.koin.core.KoinApplication;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Definitions;
import org.koin.core.definition.Kind;
import org.koin.core.definition.Options;
import org.koin.core.error.DefinitionOverrideException;
import org.koin.core.module.Module;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.scope.Scope;
import org.koin.core.scope.ScopeDefinition;
import org.koin.dsl.ModuleKt;

/* JADX INFO: compiled from: KoinApplicationExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"fragmentFactory", "", "Lorg/koin/core/KoinApplication;", "koin-androidx-fragment_release"}, k = 2, mv = {1, 1, 16})
public final class KoinApplicationExtKt {
    public static final void fragmentFactory(KoinApplication fragmentFactory) {
        Intrinsics.checkParameterIsNotNull(fragmentFactory, "$this$fragmentFactory");
        fragmentFactory.getKoin().loadModules(CollectionsKt.listOf(ModuleKt.module$default(false, false, new Function1<Module, Unit>() { // from class: org.koin.androidx.fragment.koin.KoinApplicationExtKt.fragmentFactory.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module) throws DefinitionOverrideException {
                invoke2(module);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module receiver) throws DefinitionOverrideException {
                Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
                C03531 c03531 = new Function2<Scope, DefinitionParameters, KoinFragmentFactory>() { // from class: org.koin.androidx.fragment.koin.KoinApplicationExtKt.fragmentFactory.1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final KoinFragmentFactory invoke(Scope receiver2, DefinitionParameters it) {
                        Intrinsics.checkParameterIsNotNull(receiver2, "$receiver");
                        Intrinsics.checkParameterIsNotNull(it, "it");
                        return new KoinFragmentFactory(null, 1, null);
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = receiver.getRootScope();
                Options optionsMakeOptions = receiver.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope, new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(FragmentFactory.class), null, c03531, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
            }
        }, 3, null)));
    }
}
