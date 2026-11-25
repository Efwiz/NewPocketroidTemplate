package org.catrobat.catroid.koin;

import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import androidx.work.WorkManager;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.google.android.gms.common.GoogleApiAvailability;
import com.huawei.hms.api.HuaweiApiAvailability;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.db.AppDatabase;
import org.catrobat.catroid.db.DatabaseMigrations;
import org.catrobat.catroid.retrofit.CatroidWebServer;
import org.catrobat.catroid.retrofit.WebService;
import org.catrobat.catroid.stage.HmsSpeechRecognitionHolder;
import org.catrobat.catroid.stage.SpeechRecognitionHolder;
import org.catrobat.catroid.stage.SpeechRecognitionHolderFactory;
import org.catrobat.catroid.stage.SpeechRecognitionHolderInterface;
import org.catrobat.catroid.sync.DefaultFeaturedProjectSync;
import org.catrobat.catroid.sync.DefaultProjectsCategoriesSync;
import org.catrobat.catroid.sync.FeaturedProjectsSync;
import org.catrobat.catroid.sync.ProjectsCategoriesSync;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoriesAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.FeaturedProjectsAdapter;
import org.catrobat.catroid.ui.recyclerview.repository.DefaultFeaturedProjectsRepository;
import org.catrobat.catroid.ui.recyclerview.repository.DefaultLocalHashVersionRepository;
import org.catrobat.catroid.ui.recyclerview.repository.DefaultProjectCategoriesRepository;
import org.catrobat.catroid.ui.recyclerview.repository.FeaturedProjectsRepository;
import org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository;
import org.catrobat.catroid.ui.recyclerview.repository.ProjectCategoriesRepository;
import org.catrobat.catroid.ui.recyclerview.viewmodel.MainFragmentViewModel;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.catrobat.catroid.utils.NetworkConnectionMonitor;
import org.koin.android.ext.koin.KoinExtKt;
import org.koin.android.ext.koin.ModuleExtKt;
import org.koin.core.KoinApplication;
import org.koin.core.context.ContextFunctionsKt;
import org.koin.core.context.KoinContext;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Definitions;
import org.koin.core.definition.Kind;
import org.koin.core.definition.Options;
import org.koin.core.error.DefinitionOverrideException;
import org.koin.core.logger.Level;
import org.koin.core.module.Module;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;
import org.koin.core.scope.ScopeDefinition;
import org.koin.dsl.ModuleKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/koin/CatroidKoinHelperKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: CatroidKoinHelper.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0003\"\u0011\u0010\f\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0003\"\u0011\u0010\u000e\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0003¨\u0006\u0015"}, d2 = {"adapterModules", "Lorg/koin/core/module/Module;", "getAdapterModules", "()Lorg/koin/core/module/Module;", "componentsModules", "getComponentsModules", "myModules", "", "getMyModules", "()Ljava/util/List;", "repositoryModules", "getRepositoryModules", "speechModules", "getSpeechModules", "viewModelModules", "getViewModelModules", "start", "", "application", "Landroid/app/Application;", "modules", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CatroidKoinHelperKt {
    private static final Module adapterModules;
    private static final Module componentsModules;
    private static final List<Module> myModules;
    private static final Module repositoryModules;
    private static final Module speechModules;
    private static final Module viewModelModules;

    static {
        Module module = ModuleKt.module(true, false, new Function1<Module, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module2) throws DefinitionOverrideException {
                invoke2(module2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module module2) throws DefinitionOverrideException {
                Intrinsics.checkNotNullParameter(module2, "$this$module");
                AnonymousClass1 anonymousClass1 = new Function2<Scope, DefinitionParameters, AppDatabase>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final AppDatabase invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return (AppDatabase) Room.databaseBuilder(ModuleExtKt.androidContext(single), AppDatabase.class, "app_database").addMigrations(DatabaseMigrations.INSTANCE.getMIGRATION_1_2()).build();
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = module2.getRootScope();
                Options optionsMakeOptions = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope, new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(AppDatabase.class), null, anonymousClass1, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass2 anonymousClass2 = new Function2<Scope, DefinitionParameters, WebService>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.2
                    @Override // kotlin.jvm.functions.Function2
                    public final WebService invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return CatroidWebServer.INSTANCE.getWebService("https://share.catrob.at/api/");
                    }
                };
                Definitions definitions2 = Definitions.INSTANCE;
                ScopeDefinition rootScope2 = module2.getRootScope();
                Options optionsMakeOptions2 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope2, new BeanDefinition(rootScope2, Reflection.getOrCreateKotlinClass(WebService.class), null, anonymousClass2, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass3 anonymousClass3 = new Function2<Scope, DefinitionParameters, WorkManager>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.3
                    @Override // kotlin.jvm.functions.Function2
                    public final WorkManager invoke(Scope factory, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(factory, "$this$factory");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return WorkManager.getInstance(ModuleExtKt.androidContext(factory));
                    }
                };
                Definitions definitions3 = Definitions.INSTANCE;
                ScopeDefinition rootScope3 = module2.getRootScope();
                Options optionsMakeOptions$default = Module.makeOptions$default(module2, false, false, 2, null);
                ScopeDefinition.save$default(rootScope3, new BeanDefinition(rootScope3, Reflection.getOrCreateKotlinClass(WorkManager.class), null, anonymousClass3, Kind.Factory, CollectionsKt.emptyList(), optionsMakeOptions$default, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass4 anonymousClass4 = new Function2<Scope, DefinitionParameters, ProjectManager>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.4
                    @Override // kotlin.jvm.functions.Function2
                    public final ProjectManager invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new ProjectManager(ModuleExtKt.androidContext(single));
                    }
                };
                Definitions definitions4 = Definitions.INSTANCE;
                ScopeDefinition rootScope4 = module2.getRootScope();
                Options optionsMakeOptions3 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope4, new BeanDefinition(rootScope4, Reflection.getOrCreateKotlinClass(ProjectManager.class), null, anonymousClass4, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions3, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass5 anonymousClass5 = new Function2<Scope, DefinitionParameters, NetworkConnectionMonitor>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.5
                    @Override // kotlin.jvm.functions.Function2
                    public final NetworkConnectionMonitor invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new NetworkConnectionMonitor(ModuleExtKt.androidContext(single));
                    }
                };
                Definitions definitions5 = Definitions.INSTANCE;
                ScopeDefinition rootScope5 = module2.getRootScope();
                Options optionsMakeOptions4 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope5, new BeanDefinition(rootScope5, Reflection.getOrCreateKotlinClass(NetworkConnectionMonitor.class), null, anonymousClass5, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions4, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass6 anonymousClass6 = new Function2<Scope, DefinitionParameters, HuaweiApiAvailability>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.6
                    @Override // kotlin.jvm.functions.Function2
                    public final HuaweiApiAvailability invoke(Scope factory, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(factory, "$this$factory");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return HuaweiApiAvailability.getInstance();
                    }
                };
                Definitions definitions6 = Definitions.INSTANCE;
                ScopeDefinition rootScope6 = module2.getRootScope();
                Options optionsMakeOptions$default2 = Module.makeOptions$default(module2, false, false, 2, null);
                ScopeDefinition.save$default(rootScope6, new BeanDefinition(rootScope6, Reflection.getOrCreateKotlinClass(HuaweiApiAvailability.class), null, anonymousClass6, Kind.Factory, CollectionsKt.emptyList(), optionsMakeOptions$default2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass7 anonymousClass7 = new Function2<Scope, DefinitionParameters, GoogleApiAvailability>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.7
                    @Override // kotlin.jvm.functions.Function2
                    public final GoogleApiAvailability invoke(Scope factory, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(factory, "$this$factory");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return GoogleApiAvailability.getInstance();
                    }
                };
                Definitions definitions7 = Definitions.INSTANCE;
                ScopeDefinition rootScope7 = module2.getRootScope();
                Options optionsMakeOptions$default3 = Module.makeOptions$default(module2, false, false, 2, null);
                ScopeDefinition.save$default(rootScope7, new BeanDefinition(rootScope7, Reflection.getOrCreateKotlinClass(GoogleApiAvailability.class), null, anonymousClass7, Kind.Factory, CollectionsKt.emptyList(), optionsMakeOptions$default3, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass8 anonymousClass8 = new Function2<Scope, DefinitionParameters, MobileServiceAvailability>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.8
                    @Override // kotlin.jvm.functions.Function2
                    public final MobileServiceAvailability invoke(Scope factory, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(factory, "$this$factory");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new MobileServiceAvailability((GoogleApiAvailability) factory.get(Reflection.getOrCreateKotlinClass(GoogleApiAvailability.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (HuaweiApiAvailability) factory.get(Reflection.getOrCreateKotlinClass(HuaweiApiAvailability.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions8 = Definitions.INSTANCE;
                ScopeDefinition rootScope8 = module2.getRootScope();
                Options optionsMakeOptions$default4 = Module.makeOptions$default(module2, false, false, 2, null);
                ScopeDefinition.save$default(rootScope8, new BeanDefinition(rootScope8, Reflection.getOrCreateKotlinClass(MobileServiceAvailability.class), null, anonymousClass8, Kind.Factory, CollectionsKt.emptyList(), optionsMakeOptions$default4, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass9 anonymousClass9 = new Function2<Scope, DefinitionParameters, BackpackListManager>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.9
                    @Override // kotlin.jvm.functions.Function2
                    public final BackpackListManager invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return BackpackListManager.getInstance();
                    }
                };
                Definitions definitions9 = Definitions.INSTANCE;
                ScopeDefinition rootScope9 = module2.getRootScope();
                Options optionsMakeOptions5 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope9, new BeanDefinition(rootScope9, Reflection.getOrCreateKotlinClass(BackpackListManager.class), null, anonymousClass9, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions5, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass10 anonymousClass10 = new Function2<Scope, DefinitionParameters, FeaturedProjectsSync>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.10
                    @Override // kotlin.jvm.functions.Function2
                    public final FeaturedProjectsSync invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new DefaultFeaturedProjectSync((WebService) single.get(Reflection.getOrCreateKotlinClass(WebService.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (AppDatabase) single.get(Reflection.getOrCreateKotlinClass(AppDatabase.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (LocalHashVersionRepository) single.get(Reflection.getOrCreateKotlinClass(LocalHashVersionRepository.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions10 = Definitions.INSTANCE;
                ScopeDefinition rootScope10 = module2.getRootScope();
                Options optionsMakeOptions6 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope10, new BeanDefinition(rootScope10, Reflection.getOrCreateKotlinClass(FeaturedProjectsSync.class), null, anonymousClass10, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions6, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass11 anonymousClass11 = new Function2<Scope, DefinitionParameters, ProjectsCategoriesSync>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$componentsModules$1.11
                    @Override // kotlin.jvm.functions.Function2
                    public final ProjectsCategoriesSync invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new DefaultProjectsCategoriesSync((WebService) single.get(Reflection.getOrCreateKotlinClass(WebService.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (AppDatabase) single.get(Reflection.getOrCreateKotlinClass(AppDatabase.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (LocalHashVersionRepository) single.get(Reflection.getOrCreateKotlinClass(LocalHashVersionRepository.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions11 = Definitions.INSTANCE;
                ScopeDefinition rootScope11 = module2.getRootScope();
                Options optionsMakeOptions7 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope11, new BeanDefinition(rootScope11, Reflection.getOrCreateKotlinClass(ProjectsCategoriesSync.class), null, anonymousClass11, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions7, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
            }
        });
        componentsModules = module;
        Module moduleModule$default = ModuleKt.module$default(false, false, new Function1<Module, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$viewModelModules$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module2) throws DefinitionOverrideException {
                invoke2(module2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module module2) throws DefinitionOverrideException {
                Intrinsics.checkNotNullParameter(module2, "$this$module");
                AnonymousClass1 anonymousClass1 = new Function2<Scope, DefinitionParameters, MainFragmentViewModel>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$viewModelModules$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final MainFragmentViewModel invoke(Scope viewModel, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(viewModel, "$this$viewModel");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new MainFragmentViewModel((WorkManager) viewModel.get(Reflection.getOrCreateKotlinClass(WorkManager.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (FeaturedProjectsRepository) viewModel.get(Reflection.getOrCreateKotlinClass(FeaturedProjectsRepository.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (ProjectCategoriesRepository) viewModel.get(Reflection.getOrCreateKotlinClass(ProjectCategoriesRepository.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (NetworkConnectionMonitor) viewModel.get(Reflection.getOrCreateKotlinClass(NetworkConnectionMonitor.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = module2.getRootScope();
                Options optionsMakeOptions$default = Module.makeOptions$default(module2, false, false, 2, null);
                BeanDefinition beanDefinition = new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(MainFragmentViewModel.class), null, anonymousClass1, Kind.Factory, CollectionsKt.emptyList(), optionsMakeOptions$default, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
                ScopeDefinition.save$default(rootScope, beanDefinition, false, 2, null);
                org.koin.androidx.viewmodel.dsl.ModuleExtKt.setIsViewModel(beanDefinition);
            }
        }, 3, null);
        viewModelModules = moduleModule$default;
        Module moduleModule$default2 = ModuleKt.module$default(false, false, new Function1<Module, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$repositoryModules$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module2) throws DefinitionOverrideException {
                invoke2(module2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module module2) throws DefinitionOverrideException {
                Intrinsics.checkNotNullParameter(module2, "$this$module");
                AnonymousClass1 anonymousClass1 = new Function2<Scope, DefinitionParameters, LocalHashVersionRepository>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$repositoryModules$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final LocalHashVersionRepository invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new DefaultLocalHashVersionRepository(ModuleExtKt.androidContext(single));
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = module2.getRootScope();
                Options optionsMakeOptions = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope, new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(LocalHashVersionRepository.class), null, anonymousClass1, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass2 anonymousClass2 = new Function2<Scope, DefinitionParameters, FeaturedProjectsRepository>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$repositoryModules$1.2
                    @Override // kotlin.jvm.functions.Function2
                    public final FeaturedProjectsRepository invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new DefaultFeaturedProjectsRepository((AppDatabase) single.get(Reflection.getOrCreateKotlinClass(AppDatabase.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions2 = Definitions.INSTANCE;
                ScopeDefinition rootScope2 = module2.getRootScope();
                Options optionsMakeOptions2 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope2, new BeanDefinition(rootScope2, Reflection.getOrCreateKotlinClass(FeaturedProjectsRepository.class), null, anonymousClass2, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass3 anonymousClass3 = new Function2<Scope, DefinitionParameters, ProjectCategoriesRepository>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$repositoryModules$1.3
                    @Override // kotlin.jvm.functions.Function2
                    public final ProjectCategoriesRepository invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new DefaultProjectCategoriesRepository((AppDatabase) single.get(Reflection.getOrCreateKotlinClass(AppDatabase.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions3 = Definitions.INSTANCE;
                ScopeDefinition rootScope3 = module2.getRootScope();
                Options optionsMakeOptions3 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope3, new BeanDefinition(rootScope3, Reflection.getOrCreateKotlinClass(ProjectCategoriesRepository.class), null, anonymousClass3, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions3, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
            }
        }, 3, null);
        repositoryModules = moduleModule$default2;
        Module moduleModule$default3 = ModuleKt.module$default(false, false, new Function1<Module, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$adapterModules$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module2) throws DefinitionOverrideException {
                invoke2(module2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module module2) throws DefinitionOverrideException {
                Intrinsics.checkNotNullParameter(module2, "$this$module");
                AnonymousClass1 anonymousClass1 = new Function2<Scope, DefinitionParameters, FeaturedProjectsAdapter>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$adapterModules$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final FeaturedProjectsAdapter invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new FeaturedProjectsAdapter();
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = module2.getRootScope();
                Options optionsMakeOptions = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope, new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(FeaturedProjectsAdapter.class), null, anonymousClass1, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass2 anonymousClass2 = new Function2<Scope, DefinitionParameters, CategoriesAdapter>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$adapterModules$1.2
                    @Override // kotlin.jvm.functions.Function2
                    public final CategoriesAdapter invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new CategoriesAdapter();
                    }
                };
                Definitions definitions2 = Definitions.INSTANCE;
                ScopeDefinition rootScope2 = module2.getRootScope();
                Options optionsMakeOptions2 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope2, new BeanDefinition(rootScope2, Reflection.getOrCreateKotlinClass(CategoriesAdapter.class), null, anonymousClass2, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
            }
        }, 3, null);
        adapterModules = moduleModule$default3;
        Module moduleModule$default4 = ModuleKt.module$default(false, false, new Function1<Module, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$speechModules$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Module module2) throws DefinitionOverrideException {
                invoke2(module2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Module module2) throws DefinitionOverrideException {
                Intrinsics.checkNotNullParameter(module2, "$this$module");
                AnonymousClass1 anonymousClass1 = new Function2<Scope, DefinitionParameters, SpeechRecognitionHolder>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$speechModules$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final SpeechRecognitionHolder invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new SpeechRecognitionHolder();
                    }
                };
                Definitions definitions = Definitions.INSTANCE;
                ScopeDefinition rootScope = module2.getRootScope();
                Options optionsMakeOptions = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope, new BeanDefinition(rootScope, Reflection.getOrCreateKotlinClass(SpeechRecognitionHolder.class), null, anonymousClass1, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass2 anonymousClass2 = new Function2<Scope, DefinitionParameters, HmsSpeechRecognitionHolder>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$speechModules$1.2
                    @Override // kotlin.jvm.functions.Function2
                    public final HmsSpeechRecognitionHolder invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new HmsSpeechRecognitionHolder();
                    }
                };
                Definitions definitions2 = Definitions.INSTANCE;
                ScopeDefinition rootScope2 = module2.getRootScope();
                Options optionsMakeOptions2 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope2, new BeanDefinition(rootScope2, Reflection.getOrCreateKotlinClass(HmsSpeechRecognitionHolder.class), null, anonymousClass2, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
                AnonymousClass3 anonymousClass3 = new Function2<Scope, DefinitionParameters, SpeechRecognitionHolderFactory>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt$speechModules$1.3
                    @Override // kotlin.jvm.functions.Function2
                    public final SpeechRecognitionHolderFactory invoke(Scope single, DefinitionParameters it) {
                        Intrinsics.checkNotNullParameter(single, "$this$single");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new SpeechRecognitionHolderFactory((SpeechRecognitionHolderInterface) single.get(Reflection.getOrCreateKotlinClass(SpeechRecognitionHolder.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (SpeechRecognitionHolderInterface) single.get(Reflection.getOrCreateKotlinClass(HmsSpeechRecognitionHolder.class), (Qualifier) null, (Function0<DefinitionParameters>) null), (MobileServiceAvailability) single.get(Reflection.getOrCreateKotlinClass(MobileServiceAvailability.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
                    }
                };
                Definitions definitions3 = Definitions.INSTANCE;
                ScopeDefinition rootScope3 = module2.getRootScope();
                Options optionsMakeOptions3 = module2.makeOptions(false, false);
                ScopeDefinition.save$default(rootScope3, new BeanDefinition(rootScope3, Reflection.getOrCreateKotlinClass(SpeechRecognitionHolderFactory.class), null, anonymousClass3, Kind.Single, CollectionsKt.emptyList(), optionsMakeOptions3, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null), false, 2, null);
            }
        }, 3, null);
        speechModules = moduleModule$default4;
        myModules = CollectionsKt.listOf((Object[]) new Module[]{module, moduleModule$default, moduleModule$default2, moduleModule$default3, moduleModule$default4});
    }

    public static final Module getComponentsModules() {
        return componentsModules;
    }

    public static final Module getViewModelModules() {
        return viewModelModules;
    }

    public static final Module getRepositoryModules() {
        return repositoryModules;
    }

    public static final Module getAdapterModules() {
        return adapterModules;
    }

    public static final Module getSpeechModules() {
        return speechModules;
    }

    public static final List<Module> getMyModules() {
        return myModules;
    }

    public static final void start(final Application application, final List<Module> modules) {
        Intrinsics.checkNotNullParameter(application, "application");
        Intrinsics.checkNotNullParameter(modules, "modules");
        ContextFunctionsKt.startKoin$default((KoinContext) null, new Function1<KoinApplication, Unit>() { // from class: org.catrobat.catroid.koin.CatroidKoinHelperKt.start.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KoinApplication koinApplication) {
                invoke2(koinApplication);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KoinApplication startKoin) {
                Intrinsics.checkNotNullParameter(startKoin, "$this$startKoin");
                Context applicationContext = application.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                KoinExtKt.androidContext(startKoin, applicationContext);
                KoinExtKt.androidLogger(startKoin, Level.ERROR);
                startKoin.modules(modules);
            }
        }, 1, (Object) null);
    }
}
