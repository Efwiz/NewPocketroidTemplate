package org.jetbrains.kotlin.cli.common;

import android.Manifest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.analyzer.common.CommonPlatformAnalyzerServices;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.HmppCliModule;
import org.jetbrains.kotlin.config.HmppCliModuleStructure;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataImpl;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.session.FirCommonSessionFactory;
import org.jetbrains.kotlin.fir.session.FirJsSessionFactory;
import org.jetbrains.kotlin.fir.session.FirJvmSessionFactory;
import org.jetbrains.kotlin.fir.session.FirNativeSessionFactory;
import org.jetbrains.kotlin.fir.session.FirWasmSessionFactory;
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.KlibIcData;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.load.kotlin.PackageAndMetadataPartProvider;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.platform.konan.NativePlatforms;
import org.jetbrains.kotlin.platform.wasm.WasmPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatformAnalyzerServices;
import org.jetbrains.kotlin.resolve.konan.platform.NativePlatformAnalyzerServices;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;
import org.jetbrains.kotlin.wasm.resolve.WasmPlatformAnalyzerServices;
import org.jetbrains.kotlin.wasm.resolve.WasmWasiPlatformAnalyzerServices;

/* JADX INFO: compiled from: FirSessionConstructionUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001aÕ\u0001\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0019\b\b\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012E\u0010'\u001aA\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(j\b\u0012\u0004\u0012\u0002H\u0014`+H\u0082\b\u001aÇ\u0001\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0019\b\b\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2E\u0010'\u001aA\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(j\b\u0012\u0004\u0012\u0002H\u0014`+H\u0082\b\u001a\u00ad\u0001\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0013\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0019\b\b\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%2E\u0010'\u001aA\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(j\b\u0012\u0004\u0012\u0002H\u0014`+H\u0082\b\u001a´\u0001\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u0016\u001a\u00020\u00172\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\u0006\u00106\u001a\u0002072\u0006\u0010\u001a\u001a\u00020\u001b2\f\u00108\u001a\b\u0012\u0004\u0012\u0002090\u00122\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u001a\u0010:\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0006\u0012\u0004\u0018\u00010;0\b\u001a\u009c\u0001\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u0010\u0016\u001a\u00020\u00172\f\u00108\u001a\b\u0012\u0004\u0012\u00020=0\u00122\u0006\u0010\u001a\u001a\u00020\u001b2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\b\u0010>\u001a\u0004\u0018\u00010?2\b\u0010@\u001a\u0004\u0018\u00010A\u001a¦\u0001\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u0016\u001a\u00020\u00172\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\u0006\u00106\u001a\u0002072\u0006\u0010\u001a\u001a\u00020\u001b2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u001a\u0010:\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0006\u0012\u0004\u0018\u00010;0\b\u001a¦\u0001\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u0010\u0016\u001a\u00020\u00172\f\u00108\u001a\b\u0012\u0004\u0012\u0002090\u00122\u0006\u0010\u001a\u001a\u00020\u001b2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\u0006\u0010D\u001a\u00020\u00042\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0014\b\u0002\u0010E\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020$0\b\u001aâ\u0001\u0010F\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010D\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020*0\b2E\u0010H\u001aA\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(j\b\u0012\u0004\u0012\u0002H\u0014`+H\u0082\b\u001a\u009c\u0001\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u00130\u0012\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00122\u0006\u00100\u001a\u0002012\u0006\u0010\u0016\u001a\u00020\u00172\f\u00108\u001a\b\u0012\u0004\u0012\u00020=0\u00122\u0006\u0010\u001a\u001a\u00020\u001b2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00122\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u00040\b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\b\u0010>\u001a\u0004\u0018\u00010?2\b\u0010@\u001a\u0004\u0018\u00010A\"#\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\t\"'\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"!\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010*|\b\u0002\u0010J\u001a\u0004\b\u0000\u0010\u0014\"7\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(27\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00140\u0012\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020!\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\b¢\u0006\u0002\b%\u0012\u0004\u0012\u00020*0(¨\u0006K"}, d2 = {"fileBelongsToModuleForPsi", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/psi/KtFile;", "", "", "getFileBelongsToModuleForPsi", "()Lkotlin/jvm/functions/Function2;", "isCommonSourceForPsi", "Lkotlin/Function1;", "()Lkotlin/jvm/functions/Function1;", "fileBelongsToModuleForLt", "Lorg/jetbrains/kotlin/KtSourceFile;", "Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "getFileBelongsToModuleForLt", "(Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;)Lkotlin/jvm/functions/Function2;", "isCommonSourceForLt", "(Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;)Lkotlin/jvm/functions/Function1;", "createSessionsForHmppProject", "", "Lorg/jetbrains/kotlin/cli/common/SessionWithSources;", "F", "files", "rootModuleName", "Lorg/jetbrains/kotlin/name/Name;", "hmppModuleStructure", "Lorg/jetbrains/kotlin/config/HmppCliModuleStructure;", "libraryList", "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule;", "targetPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "sessionConfigurator", "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "", "Lkotlin/ExtensionFunctionType;", "fileBelongsToModule", "createFirSession", "Lkotlin/Function4;", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/cli/common/FirSessionProducer;", "createSessionsForLegacyMppProject", "isCommonSource", "createSingleSession", "prepareCommonSessions", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "extensionRegistrars", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "librariesScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "resolvedLibraries", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "createProviderAndScopeForIncrementalCompilation", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "prepareJsSessions", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "icData", "Lorg/jetbrains/kotlin/fir/session/KlibIcData;", "prepareJvmSessions", "prepareNativeSessions", "metadataCompilationMode", "registerExtraComponents", "prepareSessions", "createLibrarySession", "createSourceSession", "prepareWasmSessions", "FirSessionProducer", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSessionConstructionUtilsKt {
    private static final Function1<KtFile, Boolean> isCommonSourceForPsi = new Function1<KtFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt.isCommonSourceForPsi.1
        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(KtFile it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return Boolean.valueOf(Intrinsics.areEqual((Object) IsCommonSourceKt.isCommonSource(it), (Object) true));
        }
    };
    private static final Function2<KtFile, String, Boolean> fileBelongsToModuleForPsi = new Function2<KtFile, String, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$fileBelongsToModuleForPsi$1
        @Override // kotlin.jvm.functions.Function2
        public final Boolean invoke(KtFile file, String moduleName) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return Boolean.valueOf(Intrinsics.areEqual(IsCommonSourceKt.getHmppModuleName(file), moduleName));
        }
    };

    /* JADX INFO: compiled from: FirSessionConstructionUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WasmTarget.values().length];
            try {
                iArr[WasmTarget.JS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WasmTarget.WASI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Function1<KtFile, Boolean> isCommonSourceForPsi() {
        return isCommonSourceForPsi;
    }

    public static final Function2<KtFile, String, Boolean> getFileBelongsToModuleForPsi() {
        return fileBelongsToModuleForPsi;
    }

    public static final Function1<KtSourceFile, Boolean> isCommonSourceForLt(final GroupedKtSources groupedKtSources) {
        Intrinsics.checkNotNullParameter(groupedKtSources, "<this>");
        return new Function1<KtSourceFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt.isCommonSourceForLt.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtSourceFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(groupedKtSources.getCommonSources().contains(it));
            }
        };
    }

    public static final Function2<KtSourceFile, String, Boolean> getFileBelongsToModuleForLt(final GroupedKtSources groupedKtSources) {
        Intrinsics.checkNotNullParameter(groupedKtSources, "<this>");
        return new Function2<KtSourceFile, String, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$fileBelongsToModuleForLt$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(KtSourceFile file, String moduleName) {
                Intrinsics.checkNotNullParameter(file, "file");
                Intrinsics.checkNotNullParameter(moduleName, "moduleName");
                Set<KtSourceFile> setEmptySet = groupedKtSources.getSourcesByModuleName().get(moduleName);
                if (setEmptySet == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                return Boolean.valueOf(setEmptySet.contains(file));
            }
        };
    }

    public static final <F> List<SessionWithSources<F>> prepareJvmSessions(List<? extends F> list, CompilerConfiguration configuration, AbstractProjectEnvironment projectEnvironment, Name rootModuleName, List<? extends FirExtensionRegistrar> extensionRegistrars, AbstractProjectFileSearchScope librariesScope, DependencyListForCliModule libraryList, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> function2, Function1<? super List<? extends F>, IncrementalCompilationContext> createProviderAndScopeForIncrementalCompilation) {
        ArrayList arrayList;
        List<? extends F> files = list;
        Function2<? super F, ? super String, Boolean> fileBelongsToModule = function2;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(rootModuleName, "rootModuleName");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(librariesScope, "librariesScope");
        Intrinsics.checkNotNullParameter(libraryList, "libraryList");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        Intrinsics.checkNotNullParameter(createProviderAndScopeForIncrementalCompilation, "createProviderAndScopeForIncrementalCompilation");
        AbstractProjectFileSearchScope searchScopeForProjectJavaSources = projectEnvironment.getSearchScopeForProjectJavaSources();
        TargetPlatform unspecifiedJvmPlatform = JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform();
        JvmPlatformAnalyzerServices jvmPlatformAnalyzerServices = JvmPlatformAnalyzerServices.INSTANCE;
        boolean zSupportsFeature = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.MultiPlatformProjects);
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        FirProjectSessionProvider firProjectSessionProvider = new FirProjectSessionProvider();
        HmppCliModuleStructure hmppCliModuleStructure2 = hmppCliModuleStructure;
        FirJvmSessionFactory.INSTANCE.createLibrarySession(rootModuleName, firProjectSessionProvider, libraryList.getModuleDataProvider(), projectEnvironment, extensionRegistrars, librariesScope, projectEnvironment.getPackagePartProvider(librariesScope), CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJvmSessions$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }
        });
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 = new FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(configuration.getBoolean(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS));
        if (!zSupportsFeature) {
            return CollectionsKt.listOf(new SessionWithSources(FirJvmSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), libraryList.getDependsOnDependencies(), libraryList.getFriendsDependencies(), unspecifiedJvmPlatform, jvmPlatformAnalyzerServices, null, 64, null), firProjectSessionProvider, searchScopeForProjectJavaSources, projectEnvironment, createProviderAndScopeForIncrementalCompilation.invoke(files), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER), (EnumWhenTracker) configuration.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER), (ImportTracker) configuration.get(CommonConfigurationKeys.IMPORT_TRACKER), true, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJvmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSingleSession$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), files));
        }
        if (hmppCliModuleStructure2 == null) {
            Name nameIdentifier = Name.identifier(rootModuleName.asString() + "-common");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(nameIdentifier, libraryList.getRegularDependencies(), CollectionsKt.emptyList(), libraryList.getFriendsDependencies(), unspecifiedJvmPlatform, jvmPlatformAnalyzerServices, null, 64, null);
            FirModuleDataImpl firModuleDataImpl2 = new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), CollectionsKt.listOf(firModuleDataImpl), libraryList.getFriendsDependencies(), unspecifiedJvmPlatform, jvmPlatformAnalyzerServices, null, 64, null);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (F f : list) {
                (isCommonSource.invoke(f).booleanValue() ? arrayList2 : arrayList3).add(f);
            }
            return CollectionsKt.listOf((Object[]) new SessionWithSources[]{new SessionWithSources(FirJvmSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl, firProjectSessionProvider, searchScopeForProjectJavaSources, projectEnvironment, createProviderAndScopeForIncrementalCompilation.invoke(arrayList2), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER), (EnumWhenTracker) configuration.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER), (ImportTracker) configuration.get(CommonConfigurationKeys.IMPORT_TRACKER), true, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJvmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1), arrayList2), new SessionWithSources(FirJvmSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl2, firProjectSessionProvider, searchScopeForProjectJavaSources, projectEnvironment, createProviderAndScopeForIncrementalCompilation.invoke(arrayList3), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER), (EnumWhenTracker) configuration.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER), (ImportTracker) configuration.get(CommonConfigurationKeys.IMPORT_TRACKER), true, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJvmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForLegacyMppProject$platformSession$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), arrayList3)});
        }
        CompilerConfiguration compilerConfiguration = configuration;
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2 = firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Iterator<HmppCliModule> it = hmppCliModuleStructure2.getModules().iterator(); it.hasNext(); it = it) {
            HmppCliModule next = it.next();
            List<HmppCliModule> list2 = hmppCliModuleStructure2.getDependenciesMap().get(next);
            if (list2 != null) {
                List<HmppCliModule> list3 = list2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it2 = list3.iterator();
                while (it2.hasNext()) {
                    arrayList4.add((FirModuleData) MapsKt.getValue(linkedHashMap, (HmppCliModule) it2.next()));
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            linkedHashMap.put(next, new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), arrayList == null ? CollectionsKt.emptyList() : arrayList, libraryList.getFriendsDependencies(), unspecifiedJvmPlatform, jvmPlatformAnalyzerServices, null, 64, null));
            compilerConfiguration = compilerConfiguration;
        }
        CompilerConfiguration compilerConfiguration2 = compilerConfiguration;
        List<HmppCliModule> modules = hmppCliModuleStructure2.getModules();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
        int i = 0;
        for (Object obj : modules) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            HmppCliModule hmppCliModule = (HmppCliModule) obj;
            FirModuleData firModuleData = (FirModuleData) MapsKt.getValue(linkedHashMap, hmppCliModule);
            ArrayList arrayList6 = new ArrayList();
            Iterator<T> it3 = files.iterator();
            while (it3.hasNext()) {
                Manifest.permission permissionVar = (Object) it3.next();
                if (fileBelongsToModule.invoke(permissionVar, hmppCliModule.getName()).booleanValue()) {
                    arrayList6.add(permissionVar);
                }
            }
            ArrayList arrayList7 = arrayList6;
            ArrayList arrayList8 = arrayList5;
            arrayList8.add(new SessionWithSources(FirJvmSessionFactory.INSTANCE.createModuleBasedSession(firModuleData, firProjectSessionProvider, searchScopeForProjectJavaSources, projectEnvironment, createProviderAndScopeForIncrementalCompilation.invoke(arrayList7), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), (LookupTracker) compilerConfiguration2.get(CommonConfigurationKeys.LOOKUP_TRACKER), (EnumWhenTracker) compilerConfiguration2.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER), (ImportTracker) compilerConfiguration2.get(CommonConfigurationKeys.IMPORT_TRACKER), true, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJvmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it4) {
                    Intrinsics.checkNotNullParameter(it4, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForHmppProject$1$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2, i, hmppCliModuleStructure2)), arrayList7));
            compilerConfiguration2 = configuration;
            fileBelongsToModule = function2;
            arrayList5 = arrayList8;
            firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2 = firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2;
            hmppCliModuleStructure2 = hmppCliModuleStructure2;
            i = i2;
            files = list;
        }
        return arrayList5;
    }

    public static final <F> List<SessionWithSources<F>> prepareJsSessions(List<? extends F> files, CompilerConfiguration configuration, Name rootModuleName, List<? extends KotlinLibrary> resolvedLibraries, DependencyListForCliModule libraryList, List<? extends FirExtensionRegistrar> extensionRegistrars, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> fileBelongsToModule, LookupTracker lookupTracker, KlibIcData klibIcData) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootModuleName, "rootModuleName");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(libraryList, "libraryList");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        TargetPlatform defaultJsPlatform = JsPlatforms.INSTANCE.getDefaultJsPlatform();
        JsPlatformAnalyzerServices jsPlatformAnalyzerServices = JsPlatformAnalyzerServices.INSTANCE;
        boolean zSupportsFeature = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.MultiPlatformProjects);
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        FirProjectSessionProvider firProjectSessionProvider = new FirProjectSessionProvider();
        HmppCliModuleStructure hmppCliModuleStructure2 = hmppCliModuleStructure;
        FirJsSessionFactory.INSTANCE.createLibrarySession(rootModuleName, resolvedLibraries, firProjectSessionProvider, libraryList.getModuleDataProvider(), extensionRegistrars, configuration, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJsSessions$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }
        });
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 = new FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(configuration.getBoolean(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS));
        if (!zSupportsFeature) {
            return CollectionsKt.listOf(new SessionWithSources(FirJsSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), libraryList.getDependsOnDependencies(), libraryList.getFriendsDependencies(), defaultJsPlatform, jsPlatformAnalyzerServices, null, 64, null), firProjectSessionProvider, extensionRegistrars, configuration, lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJsSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSingleSession$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), files));
        }
        int i = 0;
        if (hmppCliModuleStructure2 == null) {
            Name nameIdentifier = Name.identifier(rootModuleName.asString() + "-common");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(nameIdentifier, libraryList.getRegularDependencies(), CollectionsKt.emptyList(), libraryList.getFriendsDependencies(), defaultJsPlatform, jsPlatformAnalyzerServices, null, 64, null);
            FirModuleDataImpl firModuleDataImpl2 = new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), CollectionsKt.listOf(firModuleDataImpl), libraryList.getFriendsDependencies(), defaultJsPlatform, jsPlatformAnalyzerServices, null, 64, null);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (F f : files) {
                (isCommonSource.invoke(f).booleanValue() ? arrayList2 : arrayList3).add(f);
            }
            return CollectionsKt.listOf((Object[]) new SessionWithSources[]{new SessionWithSources(FirJsSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl, firProjectSessionProvider, extensionRegistrars, configuration, lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJsSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1), arrayList2), new SessionWithSources(FirJsSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl2, firProjectSessionProvider, extensionRegistrars, configuration, lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJsSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForLegacyMppProject$platformSession$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), arrayList3)});
        }
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2 = firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (HmppCliModule hmppCliModule : hmppCliModuleStructure2.getModules()) {
            List<HmppCliModule> list = hmppCliModuleStructure2.getDependenciesMap().get(hmppCliModule);
            if (list != null) {
                List<HmppCliModule> list2 = list;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList4.add((FirModuleData) MapsKt.getValue(linkedHashMap, (HmppCliModule) it.next()));
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            linkedHashMap.put(hmppCliModule, new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), arrayList == null ? CollectionsKt.emptyList() : arrayList, libraryList.getFriendsDependencies(), defaultJsPlatform, jsPlatformAnalyzerServices, null, 64, null));
        }
        List<HmppCliModule> modules = hmppCliModuleStructure2.getModules();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
        Iterator<T> it2 = modules.iterator();
        while (true) {
            int i2 = i;
            if (!it2.hasNext()) {
                return arrayList5;
            }
            Object next = it2.next();
            i = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            HmppCliModule hmppCliModule2 = (HmppCliModule) next;
            FirModuleData firModuleData = (FirModuleData) MapsKt.getValue(linkedHashMap, hmppCliModule2);
            ArrayList arrayList6 = new ArrayList();
            Iterator<T> it3 = files.iterator();
            while (it3.hasNext()) {
                Manifest.permission permissionVar = (Object) it3.next();
                HmppCliModuleStructure hmppCliModuleStructure3 = hmppCliModuleStructure2;
                if (fileBelongsToModule.invoke(permissionVar, hmppCliModule2.getName()).booleanValue()) {
                    arrayList6.add(permissionVar);
                }
                hmppCliModuleStructure2 = hmppCliModuleStructure3;
            }
            HmppCliModuleStructure hmppCliModuleStructure4 = hmppCliModuleStructure2;
            arrayList5.add(new SessionWithSources(FirJsSessionFactory.INSTANCE.createModuleBasedSession(firModuleData, firProjectSessionProvider, extensionRegistrars, configuration, lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareJsSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it4) {
                    Intrinsics.checkNotNullParameter(it4, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForHmppProject$1$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2, i2, hmppCliModuleStructure4)), arrayList6));
            hmppCliModuleStructure2 = hmppCliModuleStructure4;
            firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2 = firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$2;
        }
    }

    public static final <F> List<SessionWithSources<F>> prepareNativeSessions(List<? extends F> files, CompilerConfiguration configuration, Name rootModuleName, List<? extends KotlinResolvedLibrary> resolvedLibraries, DependencyListForCliModule libraryList, List<? extends FirExtensionRegistrar> extensionRegistrars, boolean z, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> fileBelongsToModule, Function1<? super FirSession, Unit> registerExtraComponents) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootModuleName, "rootModuleName");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(libraryList, "libraryList");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        TargetPlatform unspecifiedNativePlatform = NativePlatforms.INSTANCE.getUnspecifiedNativePlatform();
        NativePlatformAnalyzerServices nativePlatformAnalyzerServices = NativePlatformAnalyzerServices.INSTANCE;
        boolean zSupportsFeature = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.MultiPlatformProjects);
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        FirProjectSessionProvider firProjectSessionProvider = new FirProjectSessionProvider();
        HmppCliModuleStructure hmppCliModuleStructure2 = hmppCliModuleStructure;
        FirNativeSessionFactory.INSTANCE.createLibrarySession(rootModuleName, resolvedLibraries, firProjectSessionProvider, libraryList.getModuleDataProvider(), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), registerExtraComponents);
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 = new FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(configuration.getBoolean(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS));
        if (z || !zSupportsFeature) {
            return CollectionsKt.listOf(new SessionWithSources(FirNativeSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), libraryList.getDependsOnDependencies(), libraryList.getFriendsDependencies(), unspecifiedNativePlatform, nativePlatformAnalyzerServices, null, 64, null), firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new FirSessionConstructionUtilsKt$createSingleSession$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1), registerExtraComponents), files));
        }
        int i = 0;
        if (hmppCliModuleStructure2 == null) {
            Name nameIdentifier = Name.identifier(rootModuleName.asString() + "-common");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(nameIdentifier, libraryList.getRegularDependencies(), CollectionsKt.emptyList(), libraryList.getFriendsDependencies(), unspecifiedNativePlatform, nativePlatformAnalyzerServices, null, 64, null);
            FirModuleDataImpl firModuleDataImpl2 = new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), CollectionsKt.listOf(firModuleDataImpl), libraryList.getFriendsDependencies(), unspecifiedNativePlatform, nativePlatformAnalyzerServices, null, 64, null);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (F f : files) {
                (isCommonSource.invoke(f).booleanValue() ? arrayList2 : arrayList3).add(f);
            }
            return CollectionsKt.listOf((Object[]) new SessionWithSources[]{new SessionWithSources(FirNativeSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1, registerExtraComponents), arrayList2), new SessionWithSources(FirNativeSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl2, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new FirSessionConstructionUtilsKt$createSessionsForLegacyMppProject$platformSession$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1), registerExtraComponents), arrayList3)});
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (HmppCliModule hmppCliModule : hmppCliModuleStructure2.getModules()) {
            List<HmppCliModule> list = hmppCliModuleStructure2.getDependenciesMap().get(hmppCliModule);
            if (list != null) {
                List<HmppCliModule> list2 = list;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList4.add((FirModuleData) MapsKt.getValue(linkedHashMap, (HmppCliModule) it.next()));
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            linkedHashMap.put(hmppCliModule, new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), arrayList == null ? CollectionsKt.emptyList() : arrayList, libraryList.getFriendsDependencies(), unspecifiedNativePlatform, nativePlatformAnalyzerServices, null, 64, null));
            hmppCliModuleStructure2 = hmppCliModuleStructure2;
        }
        HmppCliModuleStructure hmppCliModuleStructure3 = hmppCliModuleStructure2;
        List<HmppCliModule> modules = hmppCliModuleStructure3.getModules();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
        Iterator<T> it2 = modules.iterator();
        while (true) {
            int i2 = i;
            if (!it2.hasNext()) {
                return arrayList5;
            }
            Object next = it2.next();
            i = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            HmppCliModule hmppCliModule2 = (HmppCliModule) next;
            FirModuleData firModuleData = (FirModuleData) MapsKt.getValue(linkedHashMap, hmppCliModule2);
            ArrayList arrayList6 = new ArrayList();
            Iterator<T> it3 = files.iterator();
            while (it3.hasNext()) {
                Manifest.permission permissionVar = (Object) it3.next();
                if (fileBelongsToModule.invoke(permissionVar, hmppCliModule2.getName()).booleanValue()) {
                    arrayList6.add(permissionVar);
                }
            }
            arrayList5.add(new SessionWithSources(FirNativeSessionFactory.INSTANCE.createModuleBasedSession(firModuleData, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new FirSessionConstructionUtilsKt$createSessionsForHmppProject$1$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1, i2, hmppCliModuleStructure3), registerExtraComponents), arrayList6));
        }
    }

    public static final <F> List<SessionWithSources<F>> prepareWasmSessions(List<? extends F> files, CompilerConfiguration configuration, Name rootModuleName, List<? extends KotlinLibrary> resolvedLibraries, DependencyListForCliModule libraryList, List<? extends FirExtensionRegistrar> extensionRegistrars, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> fileBelongsToModule, LookupTracker lookupTracker, KlibIcData klibIcData) {
        WasmPlatformAnalyzerServices wasmPlatformAnalyzerServices;
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(rootModuleName, "rootModuleName");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(libraryList, "libraryList");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        int i = WhenMappings.$EnumSwitchMapping$0[((WasmTarget) configuration.get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS)).ordinal()];
        if (i == 1) {
            wasmPlatformAnalyzerServices = WasmPlatformAnalyzerServices.INSTANCE;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            wasmPlatformAnalyzerServices = WasmWasiPlatformAnalyzerServices.INSTANCE;
        }
        WasmPlatforms.Default r26 = WasmPlatforms.Default.INSTANCE;
        boolean zSupportsFeature = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.MultiPlatformProjects);
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) configuration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        FirProjectSessionProvider firProjectSessionProvider = new FirProjectSessionProvider();
        HmppCliModuleStructure hmppCliModuleStructure2 = hmppCliModuleStructure;
        FirWasmSessionFactory.INSTANCE.createLibrarySession(rootModuleName, resolvedLibraries, firProjectSessionProvider, libraryList.getModuleDataProvider(), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareWasmSessions$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }
        });
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 = new FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(configuration.getBoolean(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS));
        if (!zSupportsFeature) {
            return CollectionsKt.listOf(new SessionWithSources(FirWasmSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), libraryList.getDependsOnDependencies(), libraryList.getFriendsDependencies(), r26, wasmPlatformAnalyzerServices, null, 64, null), firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareWasmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSingleSession$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), files));
        }
        int i2 = 0;
        if (hmppCliModuleStructure2 == null) {
            Name nameIdentifier = Name.identifier(rootModuleName.asString() + "-common");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(nameIdentifier, libraryList.getRegularDependencies(), CollectionsKt.emptyList(), libraryList.getFriendsDependencies(), r26, wasmPlatformAnalyzerServices, null, 64, null);
            FirModuleDataImpl firModuleDataImpl2 = new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), CollectionsKt.listOf(firModuleDataImpl), libraryList.getFriendsDependencies(), r26, wasmPlatformAnalyzerServices, null, 64, null);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (F f : files) {
                (isCommonSource.invoke(f).booleanValue() ? arrayList2 : arrayList3).add(f);
            }
            return CollectionsKt.listOf((Object[]) new SessionWithSources[]{new SessionWithSources(FirWasmSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareWasmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1), arrayList2), new SessionWithSources(FirWasmSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl2, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareWasmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForLegacyMppProject$platformSession$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1)), arrayList3)});
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (HmppCliModule hmppCliModule : hmppCliModuleStructure2.getModules()) {
            List<HmppCliModule> list = hmppCliModuleStructure2.getDependenciesMap().get(hmppCliModule);
            if (list != null) {
                List<HmppCliModule> list2 = list;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList4.add((FirModuleData) MapsKt.getValue(linkedHashMap, (HmppCliModule) it.next()));
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            linkedHashMap.put(hmppCliModule, new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), arrayList == null ? CollectionsKt.emptyList() : arrayList, libraryList.getFriendsDependencies(), r26, wasmPlatformAnalyzerServices, null, 64, null));
            hmppCliModuleStructure2 = hmppCliModuleStructure2;
        }
        HmppCliModuleStructure hmppCliModuleStructure3 = hmppCliModuleStructure2;
        List<HmppCliModule> modules = hmppCliModuleStructure3.getModules();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
        Iterator<T> it2 = modules.iterator();
        while (true) {
            int i3 = i2;
            if (!it2.hasNext()) {
                return arrayList5;
            }
            Object next = it2.next();
            i2 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            HmppCliModule hmppCliModule2 = (HmppCliModule) next;
            FirModuleData firModuleData = (FirModuleData) MapsKt.getValue(linkedHashMap, hmppCliModule2);
            ArrayList arrayList6 = new ArrayList();
            Iterator<T> it3 = files.iterator();
            while (it3.hasNext()) {
                Manifest.permission permissionVar = (Object) it3.next();
                if (fileBelongsToModule.invoke(permissionVar, hmppCliModule2.getName()).booleanValue()) {
                    arrayList6.add(permissionVar);
                }
            }
            HmppCliModuleStructure hmppCliModuleStructure4 = hmppCliModuleStructure3;
            arrayList5.add(new SessionWithSources(FirWasmSessionFactory.INSTANCE.createModuleBasedSession(firModuleData, firProjectSessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), lookupTracker, klibIcData, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareWasmSessions$2$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it4) {
                    Intrinsics.checkNotNullParameter(it4, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            }, new FirSessionConstructionUtilsKt$createSessionsForHmppProject$1$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1, i3, hmppCliModuleStructure4)), arrayList6));
            hmppCliModuleStructure3 = hmppCliModuleStructure4;
        }
    }

    public static final <F> List<SessionWithSources<F>> prepareCommonSessions(List<? extends F> files, CompilerConfiguration configuration, AbstractProjectEnvironment projectEnvironment, Name rootModuleName, List<? extends FirExtensionRegistrar> extensionRegistrars, AbstractProjectFileSearchScope librariesScope, DependencyListForCliModule libraryList, List<? extends KotlinResolvedLibrary> resolvedLibraries, Function1<? super F, Boolean> isCommonSource, Function2<? super F, ? super String, Boolean> fileBelongsToModule, Function1<? super List<? extends F>, IncrementalCompilationContext> createProviderAndScopeForIncrementalCompilation) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(rootModuleName, "rootModuleName");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(librariesScope, "librariesScope");
        Intrinsics.checkNotNullParameter(libraryList, "libraryList");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(isCommonSource, "isCommonSource");
        Intrinsics.checkNotNullParameter(fileBelongsToModule, "fileBelongsToModule");
        Intrinsics.checkNotNullParameter(createProviderAndScopeForIncrementalCompilation, "createProviderAndScopeForIncrementalCompilation");
        TargetPlatform defaultCommonPlatform = CommonPlatforms.INSTANCE.getDefaultCommonPlatform();
        CommonPlatformAnalyzerServices commonPlatformAnalyzerServices = CommonPlatformAnalyzerServices.INSTANCE;
        CommonConfigurationKeysKt.getLanguageVersionSettings(configuration).supportsFeature(LanguageFeature.MultiPlatformProjects);
        FirProjectSessionProvider firProjectSessionProvider = new FirProjectSessionProvider();
        FirCommonSessionFactory firCommonSessionFactory = FirCommonSessionFactory.INSTANCE;
        ModuleDataProvider moduleDataProvider = libraryList.getModuleDataProvider();
        PackagePartProvider packagePartProvider = projectEnvironment.getPackagePartProvider(librariesScope);
        Intrinsics.checkNotNull(packagePartProvider, "null cannot be cast to non-null type org.jetbrains.kotlin.load.kotlin.PackageAndMetadataPartProvider");
        firCommonSessionFactory.createLibrarySession(rootModuleName, firProjectSessionProvider, moduleDataProvider, projectEnvironment, extensionRegistrars, librariesScope, resolvedLibraries, (PackageAndMetadataPartProvider) packagePartProvider, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareCommonSessions$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }
        });
        FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 = new FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(configuration.getBoolean(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS));
        FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(rootModuleName, libraryList.getRegularDependencies(), libraryList.getDependsOnDependencies(), libraryList.getFriendsDependencies(), defaultCommonPlatform, commonPlatformAnalyzerServices, null, 64, null);
        FirSessionConstructionUtilsKt$createSingleSession$session$1 firSessionConstructionUtilsKt$createSingleSession$session$1 = new FirSessionConstructionUtilsKt$createSingleSession$session$1(firSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1);
        return CollectionsKt.listOf(new SessionWithSources(FirCommonSessionFactory.INSTANCE.createModuleBasedSession(firModuleDataImpl, firProjectSessionProvider, projectEnvironment, createProviderAndScopeForIncrementalCompilation.invoke(files), extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER), (EnumWhenTracker) configuration.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER), (ImportTracker) configuration.get(CommonConfigurationKeys.IMPORT_TRACKER), new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt$prepareCommonSessions$2$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }
        }, firSessionConstructionUtilsKt$createSingleSession$session$1), files));
    }
}
