package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.core.app.NotificationCompat;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.UStringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.CommonKLibResolver;
import org.jetbrains.kotlin.backend.common.serialization.CityHashKt;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.backend.common.serialization.IrInterningService;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolverKt;

/* JADX INFO: compiled from: CacheUpdater.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0003FGHB_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000eJJ\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000e2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020&0%2\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00030%2\u0012\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\u000e0)H\u0002Jb\u0010+\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020.\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\u000e0\u000b0-0,2\u0006\u0010/\u001a\u0002002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002020%2\u0018\u00103\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020.040%H\u0002J@\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\u000e0)2*\u00106\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020.\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\u000e0\u000b0-0,H\u0002J\u0012\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160)J\u0018\u00108\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020:090\u000eJ\b\u0010;\u001a\u00020<H\u0002J\b\u0010=\u001a\u00020>H\u0002J8\u0010?\u001a\u00020@*\u0014\u0012\u0004\u0012\u00020.\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160A2\u0006\u0010B\u001a\u00020.2\u0006\u0010C\u001a\u00020\u0017H\u0002ø\u0001\u0000¢\u0006\u0004\bD\u0010ER\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\u00020\u001dX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u001eR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater;", "", "mainModule", "", "allModules", "", "mainModuleFriends", "cacheDir", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "irFactory", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "mainArguments", "", "compilerInterfaceFactory", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterfaceFactory;", "(Ljava/lang/String;Ljava/util/Collection;Ljava/util/Collection;Ljava/lang/String;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lkotlin/jvm/functions/Function0;Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterfaceFactory;)V", "cacheRootDir", "Ljava/io/File;", "dirtyFileStats", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "Ljava/util/EnumSet;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileState;", "icHasher", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHasher;", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "mainLibraryFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Ljava/lang/String;", NotificationCompat.CATEGORY_STOPWATCH, "Lorg/jetbrains/kotlin/ir/backend/js/ic/StopwatchIC;", "actualizeCaches", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "commitCacheAndBuildModuleArtifacts", "incrementalCacheArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "moduleNames", "rebuiltFileFragments", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "compileDirtyFiles", "", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "compilerForIC", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "loadedFragments", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dirtyFiles", "", "generateIrFragments", "generators", "getDirtyFileLastStats", "getStopwatchLastLaps", "Lkotlin/Pair;", "", "loadIrAndMakeIrFragmentGenerators", "Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater$FragmentGenerators;", "loadIrForDirtyFilesAndInitCompiler", "Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater$IrForDirtyFilesAndCompiler;", "addDirtFileStat", "", "", "srcFile", "state", "addDirtFileStat-bkrwbZk", "(Ljava/util/Map;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileState;)V", "CacheUpdaterInternal", "FragmentGenerators", "IrForDirtyFilesAndCompiler", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CacheUpdater {
    private final Collection<String> allModules;
    private final File cacheRootDir;
    private final CompilerConfiguration compilerConfiguration;
    private final JsIrCompilerICInterfaceFactory compilerInterfaceFactory;
    private final KotlinSourceFileMutableMap<EnumSet<DirtyFileState>> dirtyFileStats;
    private final ICHasher icHasher;
    private final IrInterningService internationService;
    private final Function0<IrFactory> irFactory;
    private final List<String> mainArguments;
    private final String mainLibraryFile;
    private final Collection<String> mainModuleFriends;
    private final StopwatchIC stopwatch;

    /* JADX WARN: Multi-variable type inference failed */
    public CacheUpdater(String mainModule, Collection<String> allModules, Collection<String> mainModuleFriends, String cacheDir, CompilerConfiguration compilerConfiguration, Function0<? extends IrFactory> irFactory, List<String> list, JsIrCompilerICInterfaceFactory compilerInterfaceFactory) throws IOException {
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        Intrinsics.checkNotNullParameter(mainModuleFriends, "mainModuleFriends");
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(compilerInterfaceFactory, "compilerInterfaceFactory");
        this.allModules = allModules;
        this.mainModuleFriends = mainModuleFriends;
        this.compilerConfiguration = compilerConfiguration;
        this.irFactory = irFactory;
        this.mainArguments = list;
        this.compilerInterfaceFactory = compilerInterfaceFactory;
        this.stopwatch = new StopwatchIC();
        this.dirtyFileStats = new KotlinSourceFileMutableMap<>(null, 1, null);
        String canonicalPath = new File(mainModule).getCanonicalPath();
        Intrinsics.checkNotNullExpressionValue(canonicalPath, "getCanonicalPath(...)");
        this.mainLibraryFile = KotlinLibraryFile.m7150constructorimpl(canonicalPath);
        this.icHasher = new ICHasher();
        this.internationService = new IrInterningService();
        this.cacheRootDir = new File(cacheDir, "version." + UStringsKt.m4237toStringJSWoG40(this.icHasher.m7117calculateConfigHashTVZkk9w(this.compilerConfiguration).m6139getLowBytessVKNKU(), 36));
    }

    public final KotlinSourceFileMap<EnumSet<DirtyFileState>> getDirtyFileLastStats() {
        return this.dirtyFileStats;
    }

    public final List<Pair<String, Long>> getStopwatchLastLaps() {
        return this.stopwatch.getLaps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addDirtFileStat-bkrwbZk, reason: not valid java name */
    public final void m7097addDirtFileStatbkrwbZk(Map<KotlinSourceFile, EnumSet<DirtyFileState>> map, String str, DirtyFileState dirtyFileState) {
        EnumSet<DirtyFileState> enumSet = map.get(KotlinSourceFile.m7163boximpl(str));
        if (enumSet == null) {
            KotlinSourceFile kotlinSourceFileM7163boximpl = KotlinSourceFile.m7163boximpl(str);
            EnumSet<DirtyFileState> enumSetOf = EnumSet.of(dirtyFileState);
            Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
            map.put(kotlinSourceFileM7163boximpl, enumSetOf);
            return;
        }
        enumSet.add(dirtyFileState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: CacheUpdater.kt */
    @Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001b0\u00042\u0006\u0010\u001c\u001a\u00020\u001dJ\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00182\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0015J\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u0015J\u0018\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\u0015J\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)J\u001a\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u0005H\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.J\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\"\u00100\u001a\b\u0012\u0004\u0012\u00020&0\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0015J0\u00102\u001a\u0002032\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00104\u001a\u0002052\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002060)0\u0004J4\u00107\u001a\u000203*\b\u0012\u0004\u0012\u00020#0\u00182\u0006\u0010,\u001a\u00020\u00052\u0006\u00108\u001a\u0002062\u0006\u00109\u001a\u00020&H\u0002ø\u0001\u0000¢\u0006\u0004\b:\u0010;J4\u0010<\u001a\u000203*\b\u0012\u0004\u0012\u00020#0\u00182\u0006\u0010,\u001a\u00020\u00052\u0006\u00108\u001a\u0002062\u0006\u00109\u001a\u00020&H\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010;J4\u0010>\u001a\u000203*\b\u0012\u0004\u0012\u00020#0\u00182\u0006\u0010,\u001a\u00020\u00052\u0006\u00108\u001a\u0002062\u0006\u00109\u001a\u00020&H\u0002ø\u0001\u0000¢\u0006\u0004\b?\u0010;JL\u0010@\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020A0\u0004*\b\u0012\u0004\u0012\u00020&0\u00182\f\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\t2\u0006\u0010,\u001a\u00020\u00052\f\u00101\u001a\b\u0012\u0004\u0012\u0002060)H\u0002ø\u0001\u0000¢\u0006\u0004\bD\u0010EJH\u0010F\u001a\u000203*\u00020&2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020A0\u00042\f\u0010H\u001a\b\u0012\u0004\u0012\u00020&0\u00152\u0006\u0010,\u001a\u00020\u00052\u0006\u00108\u001a\u000206H\u0002ø\u0001\u0000¢\u0006\u0004\bI\u0010JR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006K²\u0006\u0016\u0010L\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00160MX\u008a\u0084\u0002²\u0006\u001c\u0010N\u001a\u0014\u0012\u0004\u0012\u000206\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0O0MX\u008a\u0084\u0002²\u0006\u001c\u0010N\u001a\u0014\u0012\u0004\u0012\u000206\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0O0MX\u008a\u0084\u0002²\u0006\u0016\u0010Q\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u001f0MX\u008a\u0084\u0002²\u0006\u001c\u0010N\u001a\u0014\u0012\u0004\u0012\u000206\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0O0MX\u008a\u0084\u0002²\u0006\u0016\u0010Q\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u001f0MX\u008a\u0084\u0002²\u0006\u001c\u0010N\u001a\u0014\u0012\u0004\u0012\u000206\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0O0MX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater$CacheUpdaterInternal;", "", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater;)V", "incrementalCaches", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache;", "libraryDependencies", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "", "getLibraryDependencies", "()Ljava/util/Map;", "mainModuleFriendLibraries", "getMainModuleFriendLibraries", "()Ljava/util/List;", "removedIncrementalCaches", "signatureHashCalculator", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureHashCalculator;", "getSignatureHashCalculator", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureHashCalculator;", "addFilesWithRemovedDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "modifiedFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "removedFiles", "buildAndCommitCacheArtifacts", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "loadedIr", "Lorg/jetbrains/kotlin/ir/backend/js/ic/LoadedJsIr;", "collectExportedSymbolsForDirtyFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileExports;", "dirtyFiles", "collectFilesToRebuildSignatures", "filesWithModifiedExportsOrImports", "Lorg/jetbrains/kotlin/ir/backend/js/ic/UpdatedDependenciesMetadata;", "collectFilesWithModifiedExportsAndImports", "loadedDirtyFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileMetadata;", "collectFilesWithUpdatedStubbedSymbols", "collectStubbedSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getLibIncrementalCache", "libFile", "getLibIncrementalCache-5h9WozY", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache;", "loadModifiedFiles", "rebuildDirtySourceMetadata", "dirtySrcFiles", "updateStdlibIntrinsicDependencies", "", "mainModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "addDependenciesWithRemovedInverseDependencies", "srcFile", "srcFileMetadata", "addDependenciesWithRemovedInverseDependencies-K7-jKXY", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileMetadata;)V", "addDependenciesWithUpdatedSignatures", "addDependenciesWithUpdatedSignatures-K7-jKXY", "addDependentsWithUpdatedImports", "addDependentsWithUpdatedImports-K7-jKXY", "getExportedSignaturesAndAddMetadata", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSource;", "symbolProviders", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider;", "getExportedSignaturesAndAddMetadata-ctoV0kk", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;Ljava/util/List;Ljava/lang/String;Ljava/util/Set;)Ljava/util/Map;", "setAllDependencies", "idSignatureToFile", "updatedMetadata", "setAllDependencies-lPQlORE", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileMetadata;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;Ljava/lang/String;Ljava/lang/String;)V", "backend.js", "extraModifiedFiles", "", "fileStats", "Ljava/util/EnumSet;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileState;", "filesToRebuild"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class CacheUpdaterInternal {
        private final Map<KotlinLibraryFile, IncrementalCache> incrementalCaches;
        private final Map<KotlinLibrary, List<KotlinLibrary>> libraryDependencies;
        private final List<KotlinLibrary> mainModuleFriendLibraries;
        private final List<IncrementalCache> removedIncrementalCaches;
        private final IdSignatureHashCalculator signatureHashCalculator;

        public CacheUpdaterInternal() throws IOException {
            int iNextIndex;
            this.signatureHashCalculator = new IdSignatureHashCalculator(CacheUpdater.this.icHasher);
            StopwatchIC stopwatchIC = CacheUpdater.this.stopwatch;
            stopwatchIC.startNext("Resolving and loading klib dependencies");
            ArrayList fullList = CommonKLibResolver.INSTANCE.resolve(CacheUpdater.this.allModules, KlibKt.getResolverLogger(CacheUpdater.this.compilerConfiguration), (ZipFileSystemAccessor) CacheUpdater.this.compilerConfiguration.get(JSConfigurationKeys.ZIP_FILE_SYSTEM_ACCESSOR)).getFullList(KotlinLibraryResolverKt.getTopologicalLibraryOrder());
            ListIterator<KotlinLibrary> listIterator = fullList.listIterator(fullList.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (KotlinLibraryFile.m7153equalsimpl0(KotlinLibraryFile.m7151constructorimpl(listIterator.previous()), CacheUpdater.this.mainLibraryFile)) {
                        iNextIndex = listIterator.nextIndex();
                        break;
                    }
                } else {
                    iNextIndex = -1;
                    break;
                }
            }
            Integer numValueOf = Integer.valueOf(iNextIndex);
            numValueOf = numValueOf.intValue() < 0 ? null : numValueOf;
            if (numValueOf == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("main library", CacheUpdater.this.mainLibraryFile, null, 4, null);
                throw null;
            }
            int iIntValue = numValueOf.intValue();
            if (iIntValue != CollectionsKt.getLastIndex(fullList)) {
                ArrayList arrayList = new ArrayList(fullList.size());
                int i = 0;
                for (Object obj : fullList) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    if (i != iIntValue) {
                        arrayList.add(obj);
                    }
                    i = i2;
                }
                arrayList.add(fullList.get(iIntValue));
                fullList = arrayList;
            }
            List<KotlinLibrary> list = fullList;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj2 : list) {
                linkedHashMap.put(KlibKt.getModuleName((KotlinLibrary) obj2), obj2);
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj3 : list) {
                LinkedHashMap linkedHashMap3 = linkedHashMap2;
                List<String> listPropertyList$default = PropertiesKt.propertyList$default(((KotlinLibrary) obj3).getManifestProperties(), KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, null, true, 2, null);
                ArrayList arrayList2 = new ArrayList(listPropertyList$default.size());
                for (String str : listPropertyList$default) {
                    KotlinLibrary kotlinLibrary = (KotlinLibrary) linkedHashMap.get(str);
                    if (kotlinLibrary == null) {
                        ICUtilsKt.m7124notFoundIcError5T69zGA$default("library " + str, null, null, 6, null);
                        throw null;
                    }
                    arrayList2.add(kotlinLibrary);
                }
                linkedHashMap3.put(obj3, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
            }
            LinkedHashMap linkedHashMap4 = linkedHashMap2;
            StopwatchIC.stop$default(stopwatchIC, null, 1, null);
            this.libraryDependencies = linkedHashMap4;
            Set setKeySet = linkedHashMap4.keySet();
            CacheUpdater cacheUpdater = CacheUpdater.this;
            Collection collection = cacheUpdater.mainModuleFriends;
            HashSet hashSetNewHashSetWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(cacheUpdater.mainModuleFriends.size());
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                hashSetNewHashSetWithExpectedSize.add(new File((String) it.next()).getCanonicalPath());
            }
            HashSet hashSet = hashSetNewHashSetWithExpectedSize;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj4 : setKeySet) {
                if (hashSet.contains(((KotlinLibrary) obj4).getLibraryFile().getCanonicalPath())) {
                    arrayList3.add(obj4);
                }
            }
            this.mainModuleFriendLibraries = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3);
            Set<KotlinLibrary> setKeySet2 = this.libraryDependencies.keySet();
            CacheUpdater cacheUpdater2 = CacheUpdater.this;
            LinkedHashMap linkedHashMap5 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setKeySet2, 10)), 16));
            for (KotlinLibrary kotlinLibrary2 : setKeySet2) {
                String strM7151constructorimpl = KotlinLibraryFile.m7151constructorimpl(kotlinLibrary2);
                File file = new File(strM7151constructorimpl);
                String absolutePath = file.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                Pair pair = TuplesKt.to(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl), new IncrementalCache(new KotlinLoadedLibraryHeader(kotlinLibrary2, cacheUpdater2.internationService), new File(cacheUpdater2.cacheRootDir, file.getName() + '.' + CityHashKt.cityHash64String(absolutePath))));
                linkedHashMap5.put(pair.getFirst(), pair.getSecond());
            }
            this.incrementalCaches = linkedHashMap5;
            final CacheUpdater cacheUpdater3 = CacheUpdater.this;
            final List listCreateListBuilder = CollectionsKt.createListBuilder();
            if (cacheUpdater3.cacheRootDir.isDirectory()) {
                Collection collectionValues = linkedHashMap5.values();
                HashSet hashSetNewHashSetWithExpectedSize2 = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(linkedHashMap5.size());
                Iterator it2 = collectionValues.iterator();
                while (it2.hasNext()) {
                    hashSetNewHashSetWithExpectedSize2.add(((IncrementalCache) it2.next()).getCacheDir());
                }
                final HashSet hashSet2 = hashSetNewHashSetWithExpectedSize2;
                Stream<Path> streamWalk = Files.walk(cacheUpdater3.cacheRootDir.toPath(), 1, new FileVisitOption[0]);
                final CacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$allDirs$1 cacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$allDirs$1 = new Function1<Path, File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$allDirs$1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(Path path) {
                        return path.toFile();
                    }
                };
                Stream<R> map = streamWalk.map(new Function() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj5) {
                        return CacheUpdater.CacheUpdaterInternal.removedIncrementalCaches$lambda$17$lambda$14(cacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$allDirs$1, obj5);
                    }
                });
                final Function1<File, Boolean> function1 = new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(File file2) {
                        return Boolean.valueOf((Intrinsics.areEqual(file2, cacheUpdater3.cacheRootDir) || hashSet2.contains(file2)) ? false : true);
                    }
                };
                Stream streamFilter = map.filter(new Predicate() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj5) {
                        return CacheUpdater.CacheUpdaterInternal.removedIncrementalCaches$lambda$17$lambda$15(function1, obj5);
                    }
                });
                final Function1<File, Unit> function2 = new Function1<File, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$removedIncrementalCaches$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(File file2) {
                        invoke2(file2);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(File file2) {
                        List<IncrementalCache> list2 = listCreateListBuilder;
                        Intrinsics.checkNotNull(file2);
                        list2.add(new IncrementalCache(new KotlinRemovedLibraryHeader(file2), file2));
                    }
                };
                streamFilter.forEach(new Consumer() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj5) {
                        CacheUpdater.CacheUpdaterInternal.removedIncrementalCaches$lambda$17$lambda$16(function2, obj5);
                    }
                });
            }
            this.removedIncrementalCaches = CollectionsKt.build(listCreateListBuilder);
        }

        public final IdSignatureHashCalculator getSignatureHashCalculator() {
            return this.signatureHashCalculator;
        }

        public final Map<KotlinLibrary, List<KotlinLibrary>> getLibraryDependencies() {
            return this.libraryDependencies;
        }

        public final List<KotlinLibrary> getMainModuleFriendLibraries() {
            return this.mainModuleFriendLibraries;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final File removedIncrementalCaches$lambda$17$lambda$14(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (File) tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean removedIncrementalCaches$lambda$17$lambda$15(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void removedIncrementalCaches$lambda$17$lambda$16(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: renamed from: getLibIncrementalCache-5h9WozY, reason: not valid java name */
        private final IncrementalCache m7102getLibIncrementalCache5h9WozY(String libFile) {
            IncrementalCache incrementalCache = this.incrementalCaches.get(KotlinLibraryFile.m7149boximpl(libFile));
            if (incrementalCache != null) {
                return incrementalCache;
            }
            ICUtilsKt.m7124notFoundIcError5T69zGA$default("incremental cache", libFile, null, 4, null);
            throw null;
        }

        private final KotlinSourceFileMap<KotlinSourceFileMetadata> addFilesWithRemovedDependencies(KotlinSourceFileMutableMap<KotlinSourceFileMetadata> modifiedFiles, KotlinSourceFileMap<? extends KotlinSourceFileMetadata> removedFiles) {
            KotlinSourceFileMutableMap kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap(null, 1, null);
            CacheUpdater cacheUpdater = CacheUpdater.this;
            Iterator it = removedFiles.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    KotlinSourceFileMetadata kotlinSourceFileMetadata = (KotlinSourceFileMetadata) entry2.getValue();
                    addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles(this, modifiedFiles, removedFiles, cacheUpdater, kotlinSourceFileMutableMap, kotlinSourceFileMetadata.getDirectDependencies(), DirtyFileState.REMOVED_INVERSE_DEPENDS);
                    addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles(this, modifiedFiles, removedFiles, cacheUpdater, kotlinSourceFileMutableMap, kotlinSourceFileMetadata.getInverseDependencies(), DirtyFileState.REMOVED_DIRECT_DEPENDS);
                }
            }
            modifiedFiles.copyFilesFrom(kotlinSourceFileMutableMap);
            return modifiedFiles;
        }

        private static final void addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles(CacheUpdaterInternal cacheUpdaterInternal, KotlinSourceFileMutableMap<KotlinSourceFileMetadata> kotlinSourceFileMutableMap, KotlinSourceFileMap<? extends KotlinSourceFileMetadata> kotlinSourceFileMap, final CacheUpdater cacheUpdater, final KotlinSourceFileMutableMap<KotlinSourceFileMetadata> kotlinSourceFileMutableMap2, KotlinSourceFileMap<?> kotlinSourceFileMap2, DirtyFileState dirtyFileState) {
            Iterator it = kotlinSourceFileMap2.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                final String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                IncrementalCache incrementalCache = cacheUpdaterInternal.incrementalCaches.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
                if (incrementalCache != null) {
                    Map<KotlinSourceFile, KotlinSourceFileMetadata> mapEmptyMap = kotlinSourceFileMutableMap.m7177get5h9WozY(strM7157unboximpl);
                    if (mapEmptyMap == null) {
                        mapEmptyMap = MapsKt.emptyMap();
                    }
                    Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> mapM7177get5h9WozY = kotlinSourceFileMap.m7177get5h9WozY(strM7157unboximpl);
                    if (mapM7177get5h9WozY == null) {
                        mapM7177get5h9WozY = MapsKt.emptyMap();
                    }
                    Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, KotlinSourceFileMetadata>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$extraModifiedFiles$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Map<KotlinSourceFile, KotlinSourceFileMetadata> invoke() {
                            return kotlinSourceFileMutableMap2.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                        }
                    });
                    Lazy lazy2 = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, EnumSet<DirtyFileState>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$fileStats$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Map<KotlinSourceFile, EnumSet<DirtyFileState>> invoke() {
                            return cacheUpdater.dirtyFileStats.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                        }
                    });
                    Iterator it2 = map.keySet().iterator();
                    while (it2.hasNext()) {
                        String strM7171unboximpl = ((KotlinSourceFile) it2.next()).m7171unboximpl();
                        if (!mapEmptyMap.containsKey(KotlinSourceFile.m7163boximpl(strM7171unboximpl)) && !mapM7177get5h9WozY.containsKey(KotlinSourceFile.m7163boximpl(strM7171unboximpl))) {
                            if (!addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$lambda$18(lazy).containsKey(KotlinSourceFile.m7163boximpl(strM7171unboximpl))) {
                                KotlinSourceFileMetadata kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s = incrementalCache.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl);
                                addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$lambda$18(lazy).put(KotlinSourceFile.m7163boximpl(strM7171unboximpl), kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s);
                                cacheUpdater.m7097addDirtFileStatbkrwbZk(addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$lambda$19(lazy2), strM7171unboximpl, dirtyFileState);
                            }
                        }
                    }
                }
            }
        }

        private static final Map<KotlinSourceFile, KotlinSourceFileMetadata> addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$lambda$18(Lazy<? extends Map<KotlinSourceFile, KotlinSourceFileMetadata>> lazy) {
            return lazy.getValue();
        }

        private static final Map<KotlinSourceFile, EnumSet<DirtyFileState>> addFilesWithRemovedDependencies$addDependenciesToExtraModifiedFiles$lambda$19(Lazy<? extends Map<KotlinSourceFile, EnumSet<DirtyFileState>>> lazy) {
            return lazy.getValue();
        }

        public final KotlinSourceFileMap<KotlinSourceFileMetadata> loadModifiedFiles() {
            HashMap map = new HashMap();
            for (IncrementalCache incrementalCache : this.removedIncrementalCaches) {
                String strM7139getLibraryFileFromHeadery7ymc5g = incrementalCache.m7139getLibraryFileFromHeadery7ymc5g();
                if (strM7139getLibraryFileFromHeadery7ymc5g == null) {
                    ICUtilsKt.m7124notFoundIcError5T69zGA$default("removed library name; cache dir: " + incrementalCache.getCacheDir(), null, null, 6, null);
                    throw null;
                }
                Map<KotlinSourceFile, KotlinSourceFileMetadata> mapLoadModifiedFiles$collectDirtyFiles = loadModifiedFiles$collectDirtyFiles(map, CacheUpdater.this, strM7139getLibraryFileFromHeadery7ymc5g, incrementalCache);
                if (!mapLoadModifiedFiles$collectDirtyFiles.isEmpty()) {
                    KotlinSourceFile kotlinSourceFile = (KotlinSourceFile) CollectionsKt.first(mapLoadModifiedFiles$collectDirtyFiles.keySet());
                    ICUtilsKt.m7121icError5T69zGA("unexpected dirty file", strM7139getLibraryFileFromHeadery7ymc5g, kotlinSourceFile != null ? kotlinSourceFile.m7171unboximpl() : null);
                    throw null;
                }
            }
            Set<Map.Entry<KotlinLibraryFile, IncrementalCache>> setEntrySet = this.incrementalCaches.entrySet();
            HashMap map2 = new HashMap();
            CacheUpdater cacheUpdater = CacheUpdater.this;
            Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Pair pair = TuplesKt.to(KotlinLibraryFile.m7149boximpl(strM7157unboximpl), loadModifiedFiles$collectDirtyFiles(map, cacheUpdater, strM7157unboximpl, (IncrementalCache) entry.getValue()));
                map2.put(pair.getFirst(), pair.getSecond());
            }
            return addFilesWithRemovedDependencies(new KotlinSourceFileMutableMap<>(map2), new KotlinSourceFileMap<>(map));
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static final Map<KotlinSourceFile, KotlinSourceFileMetadata> loadModifiedFiles$collectDirtyFiles(HashMap<KotlinLibraryFile, Map<KotlinSourceFile, KotlinSourceFileMetadata>> map, final CacheUpdater cacheUpdater, final String str, IncrementalCache incrementalCache) {
            IncrementalCache.ModifiedFiles modifiedFilesCollectModifiedFiles = incrementalCache.collectModifiedFiles();
            Collection<KotlinSourceFile> collectionComponent1 = modifiedFilesCollectModifiedFiles.component1();
            Map<KotlinSourceFile, KotlinSourceFileMetadata> mapComponent2 = modifiedFilesCollectModifiedFiles.component2();
            Map<KotlinSourceFile, KotlinSourceFileMetadata> mapComponent3 = modifiedFilesCollectModifiedFiles.component3();
            Collection<KotlinSourceFile> collectionComponent4 = modifiedFilesCollectModifiedFiles.component4();
            Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, EnumSet<DirtyFileState>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$loadModifiedFiles$collectDirtyFiles$fileStats$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<KotlinSourceFile, EnumSet<DirtyFileState>> invoke() {
                    return cacheUpdater.dirtyFileStats.m7185getOrPutFiles5h9WozY(str);
                }
            });
            Collection<KotlinSourceFile> collection = collectionComponent1;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                cacheUpdater.m7097addDirtFileStatbkrwbZk(loadModifiedFiles$collectDirtyFiles$lambda$21(lazy), ((KotlinSourceFile) it.next()).m7171unboximpl(), DirtyFileState.ADDED_FILE);
            }
            Iterator<Map.Entry<KotlinSourceFile, KotlinSourceFileMetadata>> it2 = mapComponent2.entrySet().iterator();
            while (it2.hasNext()) {
                cacheUpdater.m7097addDirtFileStatbkrwbZk(loadModifiedFiles$collectDirtyFiles$lambda$21(lazy), it2.next().getKey().m7171unboximpl(), DirtyFileState.REMOVED_FILE);
            }
            Iterator<Map.Entry<KotlinSourceFile, KotlinSourceFileMetadata>> it3 = mapComponent3.entrySet().iterator();
            while (it3.hasNext()) {
                cacheUpdater.m7097addDirtFileStatbkrwbZk(loadModifiedFiles$collectDirtyFiles$lambda$21(lazy), it3.next().getKey().m7171unboximpl(), DirtyFileState.MODIFIED_IR);
            }
            Iterator<T> it4 = collectionComponent4.iterator();
            while (it4.hasNext()) {
                cacheUpdater.m7097addDirtFileStatbkrwbZk(loadModifiedFiles$collectDirtyFiles$lambda$21(lazy), ((KotlinSourceFile) it4.next()).m7171unboximpl(), DirtyFileState.NON_MODIFIED_IR);
            }
            if (!mapComponent2.isEmpty()) {
                map.put(KotlinLibraryFile.m7149boximpl(str), mapComponent2);
            }
            Map<KotlinSourceFile, KotlinSourceFileMetadata> mutableMap = MapsKt.toMutableMap(mapComponent3);
            for (Object obj : collection) {
                ((KotlinSourceFile) obj).m7171unboximpl();
                mutableMap.put(obj, KotlinSourceFileMetadataNotExist.INSTANCE);
            }
            return mutableMap;
        }

        private static final Map<KotlinSourceFile, EnumSet<DirtyFileState>> loadModifiedFiles$collectDirtyFiles$lambda$21(Lazy<? extends Map<KotlinSourceFile, EnumSet<DirtyFileState>>> lazy) {
            return lazy.getValue();
        }

        public final KotlinSourceFileMutableMap<KotlinSourceFileExports> collectExportedSymbolsForDirtyFiles(KotlinSourceFileMap<? extends KotlinSourceFileMetadata> dirtyFiles) {
            Map<IdSignature, ICHash> mapM7178getIlDO4Ro;
            KotlinSourceFileMap<? extends KotlinSourceFileMetadata> dirtyFiles2 = dirtyFiles;
            Intrinsics.checkNotNullParameter(dirtyFiles2, "dirtyFiles");
            KotlinSourceFileMutableMap<KotlinSourceFileExports> kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap<>(null, 1, null);
            Iterator it = dirtyFiles2.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                HashMap map2 = new HashMap(map.size());
                for (Map.Entry entry2 : map.entrySet()) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    KotlinSourceFileMetadata kotlinSourceFileMetadata = (KotlinSourceFileMetadata) entry2.getValue();
                    DirtyFileExports dirtyFileExports = new DirtyFileExports();
                    Iterator it2 = kotlinSourceFileMetadata.getInverseDependencies().entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry entry3 = (Map.Entry) it2.next();
                        String strM7157unboximpl2 = ((KotlinLibraryFile) entry3.getKey()).m7157unboximpl();
                        Map map3 = (Map) entry3.getValue();
                        IncrementalCache incrementalCache = this.incrementalCaches.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl2));
                        if (incrementalCache != null) {
                            Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> mapM7177get5h9WozY = dirtyFiles2.m7177get5h9WozY(strM7157unboximpl2);
                            if (mapM7177get5h9WozY == null) {
                                mapM7177get5h9WozY = MapsKt.emptyMap();
                            }
                            Iterator it3 = map3.keySet().iterator();
                            while (it3.hasNext()) {
                                String strM7171unboximpl2 = ((KotlinSourceFile) it3.next()).m7171unboximpl();
                                if (!mapM7177get5h9WozY.containsKey(KotlinSourceFile.m7163boximpl(strM7171unboximpl2)) && (mapM7178getIlDO4Ro = incrementalCache.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl2).getDirectDependencies().m7178getIlDO4Ro(strM7157unboximpl, strM7171unboximpl)) != null) {
                                    dirtyFileExports.getInverseDependencies().m7188setuEadwyg(strM7157unboximpl2, strM7171unboximpl2, mapM7178getIlDO4Ro.keySet());
                                    CollectionsKt.addAll(dirtyFileExports.getAllExportedSignatures(), mapM7178getIlDO4Ro.keySet());
                                    it = it;
                                }
                            }
                            dirtyFiles2 = dirtyFiles;
                        }
                    }
                    map2.put(KotlinSourceFile.m7163boximpl(strM7171unboximpl), dirtyFileExports);
                    dirtyFiles2 = dirtyFiles;
                    it = it;
                }
                Iterator it4 = it;
                HashMap map4 = map2;
                if (!map4.isEmpty()) {
                    kotlinSourceFileMutableMap.m7187setBOFFYcU(strM7157unboximpl, map4);
                }
                dirtyFiles2 = dirtyFiles;
                it = it4;
            }
            return kotlinSourceFileMutableMap;
        }

        public final Set<IdSignature> collectStubbedSignatures() {
            HashSet hashSet = new HashSet();
            Iterator<IncrementalCache> it = this.incrementalCaches.values().iterator();
            while (it.hasNext()) {
                Iterator<Set<IdSignature>> it2 = it.next().collectFilesWithStubbedSignatures().values().iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(hashSet, it2.next());
                }
            }
            return hashSet;
        }

        /* JADX INFO: renamed from: getExportedSignaturesAndAddMetadata-ctoV0kk, reason: not valid java name */
        private final Map<IdSignature, IdSignatureSource> m7101getExportedSignaturesAndAddMetadatactoV0kk(KotlinSourceFileMutableMap<DirtyFileMetadata> kotlinSourceFileMutableMap, List<? extends FileSignatureProvider> list, String str, Set<KotlinSourceFile> set) {
            HashMap map = new HashMap();
            IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(str);
            for (FileSignatureProvider fileSignatureProvider : list) {
                HashSet hashSet = CollectionsKt.toHashSet(fileSignatureProvider.getReachableSignatures());
                Map<IdSignature, IrSymbol> implementedSymbols = fileSignatureProvider.getImplementedSymbols();
                for (Map.Entry<IdSignature, IrSymbol> entry : implementedSymbols.entrySet()) {
                    IdSignature key = entry.getKey();
                    IrSymbol value = entry.getValue();
                    boolean zRemove = hashSet.remove(key);
                    IdSignature idSignatureResolveFakeOverrideFunction = IdSignatureSourceKt.resolveFakeOverrideFunction(value);
                    if (idSignatureResolveFakeOverrideFunction != null) {
                        if (!implementedSymbols.containsKey(idSignatureResolveFakeOverrideFunction)) {
                            hashSet.add(idSignatureResolveFakeOverrideFunction);
                        }
                        zRemove = true;
                    }
                    if (zRemove) {
                        map.put(key, new IdSignatureSource(str, fileSignatureProvider.getIrFile(), value, null));
                    }
                }
                String strM7165constructorimpl = KotlinSourceFile.m7165constructorimpl(fileSignatureProvider.getIrFile());
                if (set.contains(KotlinSourceFile.m7163boximpl(strM7165constructorimpl))) {
                    kotlinSourceFileMutableMap.m7188setuEadwyg(str, strM7165constructorimpl, new DirtyFileMetadata(hashSet, incrementalCacheM7102getLibIncrementalCache5h9WozY.m7138fetchSourceFileFullMetadatawxjXL9s(strM7165constructorimpl).getDirectDependencies()));
                }
            }
            return map;
        }

        /* JADX INFO: renamed from: setAllDependencies-lPQlORE, reason: not valid java name */
        private final void m7103setAllDependencieslPQlORE(DirtyFileMetadata dirtyFileMetadata, Map<IdSignature, IdSignatureSource> map, KotlinSourceFileMap<DirtyFileMetadata> kotlinSourceFileMap, String str, String str2) {
            for (IdSignature idSignature : IdSignatureSourceKt.m7134addParentSignaturesouLM99E(dirtyFileMetadata.getMaybeImportedSignatures(), map, str, str2)) {
                IdSignatureSource idSignatureSource = map.get(idSignature);
                if (idSignatureSource != null) {
                    Hash128Bits hash128BitsM7129getZ1Gnr_Y = this.signatureHashCalculator.m7129getZ1Gnr_Y(idSignature);
                    if (hash128BitsM7129getZ1Gnr_Y != null) {
                        dirtyFileMetadata.m7104addDirectDependencycWck5Sc(idSignatureSource.getLib(), idSignatureSource.m7133getSrczDqpWdY(), idSignature, hash128BitsM7129getZ1Gnr_Y);
                        DirtyFileMetadata dirtyFileMetadataM7178getIlDO4Ro = kotlinSourceFileMap.m7178getIlDO4Ro(idSignatureSource.getLib(), idSignatureSource.m7133getSrczDqpWdY());
                        if (dirtyFileMetadataM7178getIlDO4Ro != null) {
                            dirtyFileMetadataM7178getIlDO4Ro.m7105addInverseDependencyuEadwyg(str, str2, idSignature);
                        }
                    } else {
                        ICUtilsKt.m7123notFoundIcError5T69zGA("signature " + idSignature + " hash", idSignatureSource.getLib(), idSignatureSource.m7133getSrczDqpWdY());
                        throw null;
                    }
                }
            }
        }

        public final KotlinSourceFileMap<DirtyFileMetadata> rebuildDirtySourceMetadata(LoadedJsIr loadedIr, KotlinSourceFileMap<? extends KotlinSourceFileExports> dirtySrcFiles) {
            Set<KotlinSourceFile> setEmptySet;
            Intrinsics.checkNotNullParameter(loadedIr, "loadedIr");
            Intrinsics.checkNotNullParameter(dirtySrcFiles, "dirtySrcFiles");
            HashMap map = new HashMap();
            KotlinSourceFileMutableMap<DirtyFileMetadata> kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap<>(null, 1, null);
            Iterator<KotlinLibraryFile> it = loadedIr.getLoadedFragments().keySet().iterator();
            while (it.hasNext()) {
                String strM7157unboximpl = it.next().m7157unboximpl();
                Map<KotlinSourceFile, ? extends KotlinSourceFileExports> mapM7177get5h9WozY = dirtySrcFiles.m7177get5h9WozY(strM7157unboximpl);
                if (mapM7177get5h9WozY == null || (setEmptySet = mapM7177get5h9WozY.keySet()) == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                map.putAll(m7101getExportedSignaturesAndAddMetadatactoV0kk(kotlinSourceFileMutableMap, loadedIr.m7189getSignatureProvidersForLib5h9WozY(strM7157unboximpl), strM7157unboximpl, setEmptySet));
            }
            HashMap map2 = map;
            this.signatureHashCalculator.addAllSignatureSymbols(map2);
            Iterator it2 = kotlinSourceFileMutableMap.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry = (Map.Entry) it2.next();
                String strM7157unboximpl2 = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map3 = (Map) entry.getValue();
                Map<KotlinSourceFile, ? extends KotlinSourceFileExports> mapM7177get5h9WozY2 = dirtySrcFiles.m7177get5h9WozY(strM7157unboximpl2);
                if (mapM7177get5h9WozY2 != null) {
                    for (Map.Entry entry2 : map3.entrySet()) {
                        String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                        DirtyFileMetadata dirtyFileMetadata = (DirtyFileMetadata) entry2.getValue();
                        KotlinSourceFileExports kotlinSourceFileExports = mapM7177get5h9WozY2.get(KotlinSourceFile.m7163boximpl(strM7171unboximpl));
                        if (kotlinSourceFileExports != null) {
                            Iterator it3 = kotlinSourceFileExports.getInverseDependencies().entrySet().iterator();
                            while (it3.hasNext()) {
                                Map.Entry entry3 = (Map.Entry) it3.next();
                                String strM7157unboximpl3 = ((KotlinLibraryFile) entry3.getKey()).m7157unboximpl();
                                Iterator it4 = ((Map) entry3.getValue()).entrySet().iterator();
                                while (it4.hasNext()) {
                                    Map.Entry entry4 = (Map.Entry) it4.next();
                                    String strM7171unboximpl2 = ((KotlinSourceFile) entry4.getKey()).m7171unboximpl();
                                    for (IdSignature idSignature : (Set) entry4.getValue()) {
                                        IdSignatureSource idSignatureSource = (IdSignatureSource) map.get(idSignature);
                                        DirtyFileMetadata dirtyFileMetadata2 = kotlinSourceFileMutableMap.m7178getIlDO4Ro(idSignatureSource != null ? idSignatureSource.getLib() : strM7157unboximpl2, idSignatureSource != null ? idSignatureSource.m7133getSrczDqpWdY() : strM7171unboximpl);
                                        if (dirtyFileMetadata2 != null) {
                                            dirtyFileMetadata2.m7105addInverseDependencyuEadwyg(strM7157unboximpl3, strM7171unboximpl2, idSignature);
                                        }
                                        it3 = it3;
                                        it4 = it4;
                                    }
                                }
                            }
                            m7103setAllDependencieslPQlORE(dirtyFileMetadata, map2, kotlinSourceFileMutableMap, strM7157unboximpl2, strM7171unboximpl);
                        }
                    }
                }
            }
            KotlinSourceFileMutableMap kotlinSourceFileMutableMap2 = new KotlinSourceFileMutableMap(null, 1, null);
            Iterator it5 = dirtySrcFiles.entrySet().iterator();
            while (it5.hasNext()) {
                Map.Entry entry5 = (Map.Entry) it5.next();
                String strM7157unboximpl4 = ((KotlinLibraryFile) entry5.getKey()).m7157unboximpl();
                Map map4 = (Map) entry5.getValue();
                IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(strM7157unboximpl4);
                Map<KotlinSourceFile, DirtyFileMetadata> map5 = kotlinSourceFileMutableMap.m7177get5h9WozY(strM7157unboximpl4);
                if (map5 == null) {
                    ICUtilsKt.m7124notFoundIcError5T69zGA$default("metadata", strM7157unboximpl4, null, 4, null);
                    throw null;
                }
                Iterator it6 = map4.keySet().iterator();
                while (it6.hasNext()) {
                    String strM7171unboximpl3 = ((KotlinSourceFile) it6.next()).m7171unboximpl();
                    DirtyFileMetadata dirtyFileMetadata3 = map5.get(KotlinSourceFile.m7163boximpl(strM7171unboximpl3));
                    if (dirtyFileMetadata3 == null) {
                        ICUtilsKt.m7123notFoundIcError5T69zGA("metadata", strM7157unboximpl4, strM7171unboximpl3);
                        throw null;
                    }
                    incrementalCacheM7102getLibIncrementalCache5h9WozY.m7140updateSourceFileMetadata9XGJH6U(strM7171unboximpl3, dirtyFileMetadata3);
                    kotlinSourceFileMutableMap2.m7188setuEadwyg(strM7157unboximpl4, strM7171unboximpl3, dirtyFileMetadata3);
                }
            }
            return kotlinSourceFileMutableMap2;
        }

        /* JADX INFO: renamed from: addDependenciesWithUpdatedSignatures-K7-jKXY, reason: not valid java name */
        private final void m7099addDependenciesWithUpdatedSignaturesK7jKXY(KotlinSourceFileMutableMap<UpdatedDependenciesMetadata> kotlinSourceFileMutableMap, String str, String str2, DirtyFileMetadata dirtyFileMetadata) {
            Iterator it = dirtyFileMetadata.getDirectDependencies().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(strM7157unboximpl);
                for (Map.Entry entry2 : map.entrySet()) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    Map map2 = (Map) entry2.getValue();
                    KotlinSourceFileMetadata kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s = incrementalCacheM7102getLibIncrementalCache5h9WozY.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl);
                    Set<IdSignature> setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(str, str2);
                    if (setM7178getIlDO4Ro == null) {
                        setM7178getIlDO4Ro = SetsKt.emptySet();
                    }
                    if (!Intrinsics.areEqual(setM7178getIlDO4Ro, map2)) {
                        KotlinSourceFileMetadataKt.m7184addNewMetadataK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s).getInverseDependencies().m7188setuEadwyg(str, str2, map2.keySet());
                    }
                }
            }
        }

        /* JADX INFO: renamed from: addDependenciesWithRemovedInverseDependencies-K7-jKXY, reason: not valid java name */
        private final void m7098addDependenciesWithRemovedInverseDependenciesK7jKXY(KotlinSourceFileMutableMap<UpdatedDependenciesMetadata> kotlinSourceFileMutableMap, String str, String str2, DirtyFileMetadata dirtyFileMetadata) {
            Iterator it = dirtyFileMetadata.getOldDirectDependencies().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                IncrementalCache incrementalCache = this.incrementalCaches.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
                if (incrementalCache != null) {
                    Map<KotlinSourceFile, Map<IdSignature, ICHash>> mapEmptyMap = dirtyFileMetadata.getDirectDependencies().m7177get5h9WozY(strM7157unboximpl);
                    if (mapEmptyMap == null) {
                        mapEmptyMap = MapsKt.emptyMap();
                    }
                    Iterator it2 = map.keySet().iterator();
                    while (it2.hasNext()) {
                        String strM7171unboximpl = ((KotlinSourceFile) it2.next()).m7171unboximpl();
                        if (!mapEmptyMap.containsKey(KotlinSourceFile.m7163boximpl(strM7171unboximpl))) {
                            KotlinSourceFileMetadata kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s = incrementalCache.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl);
                            if (kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(str, str2) != null) {
                                KotlinSourceFileMetadataKt.m7184addNewMetadataK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s).getInverseDependencies().m7186removeFileIlDO4Ro(str, str2);
                            }
                        }
                    }
                }
            }
        }

        /* JADX INFO: renamed from: addDependentsWithUpdatedImports-K7-jKXY, reason: not valid java name */
        private final void m7100addDependentsWithUpdatedImportsK7jKXY(KotlinSourceFileMutableMap<UpdatedDependenciesMetadata> kotlinSourceFileMutableMap, String str, String str2, DirtyFileMetadata dirtyFileMetadata) {
            ImportedSignaturesState importedSignaturesState;
            Iterator it = dirtyFileMetadata.getInverseDependencies().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                IncrementalCache incrementalCache = this.incrementalCaches.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
                if (incrementalCache != null) {
                    for (Map.Entry entry2 : map.entrySet()) {
                        String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                        Set set = (Set) entry2.getValue();
                        KotlinSourceFileMetadata kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s = incrementalCache.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl);
                        Map<IdSignature, ICHash> mapM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().m7178getIlDO4Ro(str, str2);
                        if (mapM7178getIlDO4Ro == null) {
                            mapM7178getIlDO4Ro = MapsKt.emptyMap();
                        }
                        if (!(kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s instanceof DirtyFileMetadata)) {
                            UpdatedDependenciesMetadata updatedDependenciesMetadata = kotlinSourceFileMutableMap.m7178getIlDO4Ro(strM7157unboximpl, strM7171unboximpl);
                            if ((updatedDependenciesMetadata != null ? updatedDependenciesMetadata.getImportedSignaturesState() : null) == ImportedSignaturesState.MODIFIED) {
                                continue;
                            } else {
                                if (!mapM7178getIlDO4Ro.isEmpty()) {
                                    Iterator<Map.Entry<IdSignature, ICHash>> it2 = mapM7178getIlDO4Ro.entrySet().iterator();
                                    while (true) {
                                        if (it2.hasNext()) {
                                            Map.Entry<IdSignature, ICHash> next = it2.next();
                                            Hash128Bits hash128BitsM7129getZ1Gnr_Y = this.signatureHashCalculator.m7129getZ1Gnr_Y(next.getKey());
                                            if (!(hash128BitsM7129getZ1Gnr_Y == null ? false : ICHash.m7111equalsimpl0(hash128BitsM7129getZ1Gnr_Y, next.getValue().m7115unboximpl()))) {
                                                KotlinSourceFileMetadataKt.m7184addNewMetadataK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s).setImportedSignaturesState(ImportedSignaturesState.MODIFIED);
                                            }
                                        }
                                    }
                                }
                                if (Intrinsics.areEqual(mapM7178getIlDO4Ro.keySet(), set)) {
                                    continue;
                                } else {
                                    UpdatedDependenciesMetadata updatedDependenciesMetadataM7184addNewMetadataK7jKXY = KotlinSourceFileMetadataKt.m7184addNewMetadataK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s);
                                    if (updatedDependenciesMetadataM7184addNewMetadataK7jKXY.getImportedSignaturesState() == ImportedSignaturesState.UNKNOWN) {
                                        Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, Map<IdSignature, ICHash>>>> setEntrySet = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().entrySet();
                                        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
                                            importedSignaturesState = ImportedSignaturesState.NON_MODIFIED;
                                            break;
                                        }
                                        Iterator<T> it3 = setEntrySet.iterator();
                                        while (true) {
                                            if (!it3.hasNext()) {
                                                importedSignaturesState = ImportedSignaturesState.NON_MODIFIED;
                                                break;
                                            }
                                            Map.Entry entry3 = (Map.Entry) it3.next();
                                            ((KotlinLibraryFile) entry3.getKey()).m7157unboximpl();
                                            Set<Map.Entry> setEntrySet2 = ((Map) entry3.getValue()).entrySet();
                                            if (!(setEntrySet2 instanceof Collection) || !setEntrySet2.isEmpty()) {
                                                for (Map.Entry entry4 : setEntrySet2) {
                                                    ((KotlinSourceFile) entry4.getKey()).m7171unboximpl();
                                                    Map map2 = (Map) entry4.getValue();
                                                    if (!map2.isEmpty()) {
                                                        for (Map.Entry entry5 : map2.entrySet()) {
                                                            Hash128Bits hash128BitsM7129getZ1Gnr_Y2 = this.signatureHashCalculator.m7129getZ1Gnr_Y((IdSignature) entry5.getKey());
                                                            if (hash128BitsM7129getZ1Gnr_Y2 != null && !ICHash.m7111equalsimpl0(hash128BitsM7129getZ1Gnr_Y2, ((ICHash) entry5.getValue()).m7115unboximpl())) {
                                                                importedSignaturesState = ImportedSignaturesState.MODIFIED;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        updatedDependenciesMetadataM7184addNewMetadataK7jKXY.setImportedSignaturesState(importedSignaturesState);
                                    }
                                    if (updatedDependenciesMetadataM7184addNewMetadataK7jKXY.getImportedSignaturesState() == ImportedSignaturesState.NON_MODIFIED) {
                                        Set set2 = set;
                                        HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(set.size());
                                        for (Object obj : set2) {
                                            IdSignature idSignature = (IdSignature) obj;
                                            Hash128Bits hash128BitsM7129getZ1Gnr_Y3 = this.signatureHashCalculator.m7129getZ1Gnr_Y(idSignature);
                                            if (hash128BitsM7129getZ1Gnr_Y3 == null) {
                                                ICUtilsKt.m7123notFoundIcError5T69zGA("signature " + idSignature + " hash", str, str2);
                                                throw null;
                                            }
                                            mapNewHashMapWithExpectedSize.put(obj, ICHash.m7107boximpl(hash128BitsM7129getZ1Gnr_Y3));
                                        }
                                        updatedDependenciesMetadataM7184addNewMetadataK7jKXY.getDirectDependencies().m7188setuEadwyg(str, str2, mapNewHashMapWithExpectedSize);
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public final KotlinSourceFileMap<UpdatedDependenciesMetadata> collectFilesWithModifiedExportsAndImports(KotlinSourceFileMap<DirtyFileMetadata> loadedDirtyFiles) {
            Intrinsics.checkNotNullParameter(loadedDirtyFiles, "loadedDirtyFiles");
            KotlinSourceFileMutableMap<UpdatedDependenciesMetadata> kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap<>(null, 1, null);
            Iterator it = loadedDirtyFiles.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    DirtyFileMetadata dirtyFileMetadata = (DirtyFileMetadata) entry2.getValue();
                    m7099addDependenciesWithUpdatedSignaturesK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, dirtyFileMetadata);
                    m7098addDependenciesWithRemovedInverseDependenciesK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, dirtyFileMetadata);
                    m7100addDependentsWithUpdatedImportsK7jKXY(kotlinSourceFileMutableMap, strM7157unboximpl, strM7171unboximpl, dirtyFileMetadata);
                }
            }
            return kotlinSourceFileMutableMap;
        }

        public final KotlinSourceFileMap<KotlinSourceFileExports> collectFilesToRebuildSignatures(KotlinSourceFileMap<UpdatedDependenciesMetadata> filesWithModifiedExportsOrImports) {
            Intrinsics.checkNotNullParameter(filesWithModifiedExportsOrImports, "filesWithModifiedExportsOrImports");
            final KotlinSourceFileMutableMap kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap(null, 1, null);
            Iterator it = filesWithModifiedExportsOrImports.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                final String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                Map map = (Map) entry.getValue();
                Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, KotlinSourceFileExports>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$collectFilesToRebuildSignatures$filesToRebuild$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<KotlinSourceFile, KotlinSourceFileExports> invoke() {
                        return kotlinSourceFileMutableMap.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                    }
                });
                LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
                final CacheUpdater cacheUpdater = CacheUpdater.this;
                Lazy lazy2 = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<Map<KotlinSourceFile, EnumSet<DirtyFileState>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$collectFilesToRebuildSignatures$fileStats$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<KotlinSourceFile, EnumSet<DirtyFileState>> invoke() {
                        return cacheUpdater.dirtyFileStats.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                    }
                });
                IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(strM7157unboximpl);
                for (Map.Entry entry2 : map.entrySet()) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    UpdatedDependenciesMetadata updatedDependenciesMetadata = (UpdatedDependenciesMetadata) entry2.getValue();
                    boolean zIsExportedSignaturesUpdated = updatedDependenciesMetadata.isExportedSignaturesUpdated();
                    if (zIsExportedSignaturesUpdated || updatedDependenciesMetadata.getImportedSignaturesState() == ImportedSignaturesState.MODIFIED) {
                        collectFilesToRebuildSignatures$lambda$40(lazy).put(KotlinSourceFile.m7163boximpl(strM7171unboximpl), updatedDependenciesMetadata);
                        if (zIsExportedSignaturesUpdated) {
                            CacheUpdater.this.m7097addDirtFileStatbkrwbZk(collectFilesToRebuildSignatures$lambda$41(lazy2), strM7171unboximpl, DirtyFileState.UPDATED_EXPORTS);
                        }
                        if (updatedDependenciesMetadata.getImportedSignaturesState() == ImportedSignaturesState.MODIFIED) {
                            CacheUpdater.this.m7097addDirtFileStatbkrwbZk(collectFilesToRebuildSignatures$lambda$41(lazy2), strM7171unboximpl, DirtyFileState.UPDATED_IMPORTS);
                        }
                    } else {
                        incrementalCacheM7102getLibIncrementalCache5h9WozY.m7140updateSourceFileMetadata9XGJH6U(strM7171unboximpl, updatedDependenciesMetadata);
                    }
                }
            }
            return kotlinSourceFileMutableMap;
        }

        private static final Map<KotlinSourceFile, KotlinSourceFileExports> collectFilesToRebuildSignatures$lambda$40(Lazy<? extends Map<KotlinSourceFile, KotlinSourceFileExports>> lazy) {
            return lazy.getValue();
        }

        private static final Map<KotlinSourceFile, EnumSet<DirtyFileState>> collectFilesToRebuildSignatures$lambda$41(Lazy<? extends Map<KotlinSourceFile, EnumSet<DirtyFileState>>> lazy) {
            return lazy.getValue();
        }

        public final KotlinSourceFileMap<KotlinSourceFileExports> collectFilesWithUpdatedStubbedSymbols(KotlinSourceFileMap<?> dirtyFiles) {
            Set<KotlinSourceFile> setEmptySet;
            Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
            final KotlinSourceFileMutableMap kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap(null, 1, null);
            for (Map.Entry<KotlinLibraryFile, IncrementalCache> entry : this.incrementalCaches.entrySet()) {
                final String strM7157unboximpl = entry.getKey().m7157unboximpl();
                IncrementalCache value = entry.getValue();
                Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, KotlinSourceFileExports>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$collectFilesWithUpdatedStubbedSymbols$filesToRebuild$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<KotlinSourceFile, KotlinSourceFileExports> invoke() {
                        return kotlinSourceFileMutableMap.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                    }
                });
                LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
                final CacheUpdater cacheUpdater = CacheUpdater.this;
                Lazy lazy2 = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<Map<KotlinSourceFile, EnumSet<DirtyFileState>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater$CacheUpdaterInternal$collectFilesWithUpdatedStubbedSymbols$fileStats$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<KotlinSourceFile, EnumSet<DirtyFileState>> invoke() {
                        return cacheUpdater.dirtyFileStats.m7185getOrPutFiles5h9WozY(strM7157unboximpl);
                    }
                });
                Map<KotlinSourceFile, ?> mapM7177get5h9WozY = dirtyFiles.m7177get5h9WozY(strM7157unboximpl);
                if (mapM7177get5h9WozY == null || (setEmptySet = mapM7177get5h9WozY.keySet()) == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                for (Map.Entry<KotlinSourceFile, Set<IdSignature>> entry2 : value.collectFilesWithStubbedSignatures().entrySet()) {
                    String strM7171unboximpl = entry2.getKey().m7171unboximpl();
                    Set<IdSignature> value2 = entry2.getValue();
                    if (!setEmptySet.contains(KotlinSourceFile.m7163boximpl(strM7171unboximpl))) {
                        Set<IdSignature> set = value2;
                        if (!(set instanceof Collection) || !set.isEmpty()) {
                            Iterator<T> it = set.iterator();
                            while (it.hasNext()) {
                                if (this.signatureHashCalculator.contains((IdSignature) it.next())) {
                                    collectFilesWithUpdatedStubbedSymbols$lambda$42(lazy).put(KotlinSourceFile.m7163boximpl(strM7171unboximpl), value.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl));
                                    CacheUpdater.this.m7097addDirtFileStatbkrwbZk(collectFilesWithUpdatedStubbedSymbols$lambda$43(lazy2), strM7171unboximpl, DirtyFileState.UPDATED_IMPORTS);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return kotlinSourceFileMutableMap;
        }

        private static final Map<KotlinSourceFile, KotlinSourceFileExports> collectFilesWithUpdatedStubbedSymbols$lambda$42(Lazy<? extends Map<KotlinSourceFile, KotlinSourceFileExports>> lazy) {
            return lazy.getValue();
        }

        private static final Map<KotlinSourceFile, EnumSet<DirtyFileState>> collectFilesWithUpdatedStubbedSymbols$lambda$43(Lazy<? extends Map<KotlinSourceFile, EnumSet<DirtyFileState>>> lazy) {
            return lazy.getValue();
        }

        /* JADX WARN: Code duplicated, block: B:101:0x02fe A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:103:0x021c A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:106:? A[LOOP:4: B:52:0x01af->B:106:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:113:0x02ea A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:114:0x02e2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:118:0x0219 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:119:0x01de A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:122:? A[LOOP:9: B:60:0x01e4->B:122:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:51:0x01ab  */
        /* JADX WARN: Code duplicated, block: B:54:0x01b5  */
        /* JADX WARN: Code duplicated, block: B:56:0x01d5  */
        /* JADX WARN: Code duplicated, block: B:59:0x01e0  */
        /* JADX WARN: Code duplicated, block: B:62:0x01ea  */
        /* JADX WARN: Code duplicated, block: B:64:0x020c  */
        /* JADX WARN: Code duplicated, block: B:65:0x0213  */
        /* JADX WARN: Code duplicated, block: B:72:0x0230  */
        /* JADX WARN: Code duplicated, block: B:75:0x0254  */
        /* JADX WARN: Code duplicated, block: B:77:0x0276  */
        /* JADX WARN: Code duplicated, block: B:78:0x027a  */
        /* JADX WARN: Code duplicated, block: B:83:0x029c  */
        /* JADX WARN: Code duplicated, block: B:86:0x02c0  */
        public final void updateStdlibIntrinsicDependencies(LoadedJsIr loadedIr, IrModuleFragment mainModule, Map<KotlinLibraryFile, ? extends Set<KotlinSourceFile>> dirtyFiles) {
            Set<IdSignature> setKeySet;
            Iterator it;
            Iterator it2;
            String strM7157unboximpl;
            String strM7171unboximpl;
            Set set;
            Set<IdSignature> set2;
            String strM7157unboximpl2;
            String strM7171unboximpl2;
            Map<? extends IdSignature, ? extends ICHash> map;
            Map<IdSignature, ICHash> map2;
            Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, Set<IdSignature>>>> setEntrySet;
            Iterator<T> it3;
            String strM7157unboximpl3;
            Set setEntrySet2;
            Iterator it4;
            Set set3;
            Set<IdSignature> setM7178getIlDO4Ro;
            boolean zIsEmpty;
            boolean z;
            Intrinsics.checkNotNullParameter(loadedIr, "loadedIr");
            Intrinsics.checkNotNullParameter(mainModule, "mainModule");
            Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
            String strM7157unboximpl4 = ICUtilsKt.findStdlib(mainModule, loadedIr.getLoadedFragments()).component1().m7157unboximpl();
            Set<KotlinSourceFile> set4 = dirtyFiles.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl4));
            if (set4 == null) {
                return;
            }
            List<FileSignatureProvider> listM7189getSignatureProvidersForLib5h9WozY = loadedIr.m7189getSignatureProvidersForLib5h9WozY(strM7157unboximpl4);
            KotlinSourceFileMutableMap<DirtyFileMetadata> kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap<>(null, 1, null);
            Map<IdSignature, IdSignatureSource> mapM7101getExportedSignaturesAndAddMetadatactoV0kk = m7101getExportedSignaturesAndAddMetadatactoV0kk(kotlinSourceFileMutableMap, listM7189getSignatureProvidersForLib5h9WozY, strM7157unboximpl4, set4);
            this.signatureHashCalculator.addAllSignatureSymbols(mapM7101getExportedSignaturesAndAddMetadatactoV0kk);
            KotlinSourceFileMutableMap<DirtyFileMetadata> kotlinSourceFileMutableMap2 = kotlinSourceFileMutableMap;
            KotlinSourceFileMutableMap<DirtyFileMetadata> kotlinSourceFileMutableMap3 = kotlinSourceFileMutableMap2;
            Iterator it5 = kotlinSourceFileMutableMap3.entrySet().iterator();
            while (it5.hasNext()) {
                Map.Entry entry = (Map.Entry) it5.next();
                String strM7157unboximpl5 = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    m7103setAllDependencieslPQlORE((DirtyFileMetadata) entry2.getValue(), mapM7101getExportedSignaturesAndAddMetadatactoV0kk, kotlinSourceFileMutableMap2, strM7157unboximpl5, ((KotlinSourceFile) entry2.getKey()).m7171unboximpl());
                }
            }
            IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(strM7157unboximpl4);
            Iterator it6 = kotlinSourceFileMutableMap3.entrySet().iterator();
            while (it6.hasNext()) {
                Map.Entry entry3 = (Map.Entry) it6.next();
                String strM7157unboximpl6 = ((KotlinLibraryFile) entry3.getKey()).m7157unboximpl();
                for (Map.Entry entry4 : ((Map) entry3.getValue()).entrySet()) {
                    String strM7171unboximpl3 = ((KotlinSourceFile) entry4.getKey()).m7171unboximpl();
                    DirtyFileMetadata dirtyFileMetadata = (DirtyFileMetadata) entry4.getValue();
                    if (!KotlinLibraryFile.m7153equalsimpl0(strM7157unboximpl6, strM7157unboximpl4)) {
                        ICUtilsKt.m7121icError5T69zGA("unexpected lib while parsing stdlib dependencies", strM7157unboximpl6, strM7171unboximpl3);
                        throw null;
                    }
                    KotlinSourceFileMetadata kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s = incrementalCacheM7102getLibIncrementalCache5h9WozY.m7138fetchSourceFileFullMetadatawxjXL9s(strM7171unboximpl3);
                    Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, Map<IdSignature, ICHash>>>> setEntrySet3 = dirtyFileMetadata.getDirectDependencies().entrySet();
                    if (!(setEntrySet3 instanceof Collection) || !setEntrySet3.isEmpty()) {
                        Iterator<T> it7 = setEntrySet3.iterator();
                        while (true) {
                            if (it7.hasNext()) {
                                Map.Entry entry5 = (Map.Entry) it7.next();
                                String strM7157unboximpl7 = ((KotlinLibraryFile) entry5.getKey()).m7157unboximpl();
                                Set setEntrySet4 = ((Map) entry5.getValue()).entrySet();
                                if (!(setEntrySet4 instanceof Collection) || !setEntrySet4.isEmpty()) {
                                    Iterator it8 = setEntrySet4.iterator();
                                    while (true) {
                                        if (it8.hasNext()) {
                                            Map.Entry entry6 = (Map.Entry) it8.next();
                                            String strM7171unboximpl4 = ((KotlinSourceFile) entry6.getKey()).m7171unboximpl();
                                            Map map3 = (Map) entry6.getValue();
                                            Map<IdSignature, ICHash> mapM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().m7178getIlDO4Ro(strM7157unboximpl7, strM7171unboximpl4);
                                            if (!((mapM7178getIlDO4Ro == null || (setKeySet = mapM7178getIlDO4Ro.keySet()) == null) ? map3.isEmpty() : setKeySet.containsAll(map3.keySet()))) {
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                            } else {
                                setEntrySet = dirtyFileMetadata.getInverseDependencies().entrySet();
                                if ((setEntrySet instanceof Collection) || !setEntrySet.isEmpty()) {
                                    it3 = setEntrySet.iterator();
                                    while (true) {
                                        if (it3.hasNext()) {
                                            Map.Entry entry7 = (Map.Entry) it3.next();
                                            strM7157unboximpl3 = ((KotlinLibraryFile) entry7.getKey()).m7157unboximpl();
                                            setEntrySet2 = ((Map) entry7.getValue()).entrySet();
                                            if (!(setEntrySet2 instanceof Collection) && setEntrySet2.isEmpty()) {
                                                z = true;
                                                break;
                                                break;
                                            }
                                            it4 = setEntrySet2.iterator();
                                            while (true) {
                                                if (!it4.hasNext()) {
                                                    z = true;
                                                    break;
                                                }
                                                Map.Entry entry8 = (Map.Entry) it4.next();
                                                String strM7171unboximpl5 = ((KotlinSourceFile) entry8.getKey()).m7171unboximpl();
                                                set3 = (Set) entry8.getValue();
                                                setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl3, strM7171unboximpl5);
                                                if (setM7178getIlDO4Ro != null) {
                                                    zIsEmpty = setM7178getIlDO4Ro.containsAll(set3);
                                                } else {
                                                    zIsEmpty = set3.isEmpty();
                                                }
                                                if (!zIsEmpty) {
                                                    z = false;
                                                    break;
                                                }
                                            }
                                            if (!z) {
                                            }
                                        }
                                    }
                                }
                            }
                            it = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry entry9 = (Map.Entry) it.next();
                                strM7157unboximpl2 = ((KotlinLibraryFile) entry9.getKey()).m7157unboximpl();
                                for (Map.Entry entry10 : ((Map) entry9.getValue()).entrySet()) {
                                    strM7171unboximpl2 = ((KotlinSourceFile) entry10.getKey()).m7171unboximpl();
                                    map = (Map) entry10.getValue();
                                    map2 = dirtyFileMetadata.getDirectDependencies().m7178getIlDO4Ro(strM7157unboximpl2, strM7171unboximpl2);
                                    if (map2 != null) {
                                        map2.putAll(map);
                                    } else {
                                        dirtyFileMetadata.getDirectDependencies().m7188setuEadwyg(strM7157unboximpl2, strM7171unboximpl2, new HashMap(map));
                                    }
                                }
                            }
                            it2 = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().entrySet().iterator();
                            while (it2.hasNext()) {
                                Map.Entry entry11 = (Map.Entry) it2.next();
                                strM7157unboximpl = ((KotlinLibraryFile) entry11.getKey()).m7157unboximpl();
                                for (Map.Entry entry12 : ((Map) entry11.getValue()).entrySet()) {
                                    strM7171unboximpl = ((KotlinSourceFile) entry12.getKey()).m7171unboximpl();
                                    set = (Set) entry12.getValue();
                                    set2 = dirtyFileMetadata.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl, strM7171unboximpl);
                                    if (set2 != null) {
                                        CollectionsKt.addAll(set2, set);
                                    } else {
                                        dirtyFileMetadata.getInverseDependencies().m7188setuEadwyg(strM7157unboximpl, strM7171unboximpl, new HashSet(set));
                                    }
                                }
                            }
                            incrementalCacheM7102getLibIncrementalCache5h9WozY.m7140updateSourceFileMetadata9XGJH6U(strM7171unboximpl3, dirtyFileMetadata);
                        }
                    } else {
                        setEntrySet = dirtyFileMetadata.getInverseDependencies().entrySet();
                        if (setEntrySet instanceof Collection) {
                            it3 = setEntrySet.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    Map.Entry entry13 = (Map.Entry) it3.next();
                                    strM7157unboximpl3 = ((KotlinLibraryFile) entry13.getKey()).m7157unboximpl();
                                    setEntrySet2 = ((Map) entry13.getValue()).entrySet();
                                    if (!(setEntrySet2 instanceof Collection)) {
                                        it4 = setEntrySet2.iterator();
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                z = true;
                                                break;
                                                break;
                                            }
                                            Map.Entry entry14 = (Map.Entry) it4.next();
                                            String strM7171unboximpl6 = ((KotlinSourceFile) entry14.getKey()).m7171unboximpl();
                                            set3 = (Set) entry14.getValue();
                                            setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl3, strM7171unboximpl6);
                                            if (setM7178getIlDO4Ro != null) {
                                                zIsEmpty = setM7178getIlDO4Ro.containsAll(set3);
                                            } else {
                                                zIsEmpty = set3.isEmpty();
                                            }
                                            if (!zIsEmpty) {
                                                z = false;
                                                break;
                                                break;
                                            }
                                        }
                                    } else {
                                        it4 = setEntrySet2.iterator();
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                z = true;
                                                break;
                                                break;
                                            }
                                            Map.Entry entry15 = (Map.Entry) it4.next();
                                            String strM7171unboximpl7 = ((KotlinSourceFile) entry15.getKey()).m7171unboximpl();
                                            set3 = (Set) entry15.getValue();
                                            setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl3, strM7171unboximpl7);
                                            if (setM7178getIlDO4Ro != null) {
                                                zIsEmpty = setM7178getIlDO4Ro.containsAll(set3);
                                            } else {
                                                zIsEmpty = set3.isEmpty();
                                            }
                                            if (!zIsEmpty) {
                                                z = false;
                                                break;
                                                break;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        it = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().entrySet().iterator();
                                        while (it.hasNext()) {
                                            Map.Entry entry16 = (Map.Entry) it.next();
                                            strM7157unboximpl2 = ((KotlinLibraryFile) entry16.getKey()).m7157unboximpl();
                                            while (r11.hasNext()) {
                                                strM7171unboximpl2 = ((KotlinSourceFile) entry10.getKey()).m7171unboximpl();
                                                map = (Map) entry10.getValue();
                                                map2 = dirtyFileMetadata.getDirectDependencies().m7178getIlDO4Ro(strM7157unboximpl2, strM7171unboximpl2);
                                                if (map2 != null) {
                                                    map2.putAll(map);
                                                } else {
                                                    dirtyFileMetadata.getDirectDependencies().m7188setuEadwyg(strM7157unboximpl2, strM7171unboximpl2, new HashMap(map));
                                                }
                                            }
                                        }
                                        it2 = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().entrySet().iterator();
                                        while (it2.hasNext()) {
                                            Map.Entry entry17 = (Map.Entry) it2.next();
                                            strM7157unboximpl = ((KotlinLibraryFile) entry17.getKey()).m7157unboximpl();
                                            while (r9.hasNext()) {
                                                strM7171unboximpl = ((KotlinSourceFile) entry12.getKey()).m7171unboximpl();
                                                set = (Set) entry12.getValue();
                                                set2 = dirtyFileMetadata.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl, strM7171unboximpl);
                                                if (set2 != null) {
                                                    CollectionsKt.addAll(set2, set);
                                                } else {
                                                    dirtyFileMetadata.getInverseDependencies().m7188setuEadwyg(strM7157unboximpl, strM7171unboximpl, new HashSet(set));
                                                }
                                            }
                                        }
                                        incrementalCacheM7102getLibIncrementalCache5h9WozY.m7140updateSourceFileMetadata9XGJH6U(strM7171unboximpl3, dirtyFileMetadata);
                                    }
                                }
                            }
                        } else {
                            it3 = setEntrySet.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    Map.Entry entry18 = (Map.Entry) it3.next();
                                    strM7157unboximpl3 = ((KotlinLibraryFile) entry18.getKey()).m7157unboximpl();
                                    setEntrySet2 = ((Map) entry18.getValue()).entrySet();
                                    if (!(setEntrySet2 instanceof Collection)) {
                                        it4 = setEntrySet2.iterator();
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                z = true;
                                                break;
                                                break;
                                            }
                                            Map.Entry entry19 = (Map.Entry) it4.next();
                                            String strM7171unboximpl8 = ((KotlinSourceFile) entry19.getKey()).m7171unboximpl();
                                            set3 = (Set) entry19.getValue();
                                            setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl3, strM7171unboximpl8);
                                            if (setM7178getIlDO4Ro != null) {
                                                zIsEmpty = setM7178getIlDO4Ro.containsAll(set3);
                                            } else {
                                                zIsEmpty = set3.isEmpty();
                                            }
                                            if (!zIsEmpty) {
                                                z = false;
                                                break;
                                                break;
                                            }
                                        }
                                    } else {
                                        it4 = setEntrySet2.iterator();
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                z = true;
                                                break;
                                                break;
                                            }
                                            Map.Entry entry110 = (Map.Entry) it4.next();
                                            String strM7171unboximpl9 = ((KotlinSourceFile) entry110.getKey()).m7171unboximpl();
                                            set3 = (Set) entry110.getValue();
                                            setM7178getIlDO4Ro = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl3, strM7171unboximpl9);
                                            if (setM7178getIlDO4Ro != null) {
                                                zIsEmpty = setM7178getIlDO4Ro.containsAll(set3);
                                            } else {
                                                zIsEmpty = set3.isEmpty();
                                            }
                                            if (!zIsEmpty) {
                                                z = false;
                                                break;
                                                break;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        it = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getDirectDependencies().entrySet().iterator();
                                        while (it.hasNext()) {
                                            Map.Entry entry111 = (Map.Entry) it.next();
                                            strM7157unboximpl2 = ((KotlinLibraryFile) entry111.getKey()).m7157unboximpl();
                                            while (r11.hasNext()) {
                                                strM7171unboximpl2 = ((KotlinSourceFile) entry10.getKey()).m7171unboximpl();
                                                map = (Map) entry10.getValue();
                                                map2 = dirtyFileMetadata.getDirectDependencies().m7178getIlDO4Ro(strM7157unboximpl2, strM7171unboximpl2);
                                                if (map2 != null) {
                                                    map2.putAll(map);
                                                } else {
                                                    dirtyFileMetadata.getDirectDependencies().m7188setuEadwyg(strM7157unboximpl2, strM7171unboximpl2, new HashMap(map));
                                                }
                                            }
                                        }
                                        it2 = kotlinSourceFileMetadataM7138fetchSourceFileFullMetadatawxjXL9s.getInverseDependencies().entrySet().iterator();
                                        while (it2.hasNext()) {
                                            Map.Entry entry112 = (Map.Entry) it2.next();
                                            strM7157unboximpl = ((KotlinLibraryFile) entry112.getKey()).m7157unboximpl();
                                            while (r9.hasNext()) {
                                                strM7171unboximpl = ((KotlinSourceFile) entry12.getKey()).m7171unboximpl();
                                                set = (Set) entry12.getValue();
                                                set2 = dirtyFileMetadata.getInverseDependencies().m7178getIlDO4Ro(strM7157unboximpl, strM7171unboximpl);
                                                if (set2 != null) {
                                                    CollectionsKt.addAll(set2, set);
                                                } else {
                                                    dirtyFileMetadata.getInverseDependencies().m7188setuEadwyg(strM7157unboximpl, strM7171unboximpl, new HashSet(set));
                                                }
                                            }
                                        }
                                        incrementalCacheM7102getLibIncrementalCache5h9WozY.m7140updateSourceFileMetadata9XGJH6U(strM7171unboximpl3, dirtyFileMetadata);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public final Map<KotlinLibraryFile, IncrementalCacheArtifact> buildAndCommitCacheArtifacts(LoadedJsIr loadedIr) {
            Intrinsics.checkNotNullParameter(loadedIr, "loadedIr");
            for (IncrementalCache incrementalCache : this.removedIncrementalCaches) {
                if (!FilesKt.deleteRecursively(incrementalCache.getCacheDir())) {
                    ICUtilsKt.m7122icError5T69zGA$default("can not delete cache directory " + incrementalCache.getCacheDir().getAbsolutePath(), null, null, 6, null);
                    throw null;
                }
            }
            Set<IrSymbol> setCollectSymbolsReplacedWithStubs = loadedIr.collectSymbolsReplacedWithStubs();
            HashSet hashSet = new HashSet();
            Iterator<T> it = setCollectSymbolsReplacedWithStubs.iterator();
            while (it.hasNext()) {
                IdSignature signature = ((IrSymbol) it.next()).getSignature();
                if (signature != null) {
                    hashSet.add(signature);
                }
            }
            HashSet hashSet2 = hashSet;
            Set<KotlinLibrary> setKeySet = this.libraryDependencies.keySet();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setKeySet, 10)), 16));
            Iterator<T> it2 = setKeySet.iterator();
            while (it2.hasNext()) {
                String strM7151constructorimpl = KotlinLibraryFile.m7151constructorimpl((KotlinLibrary) it2.next());
                IncrementalCache incrementalCacheM7102getLibIncrementalCache5h9WozY = m7102getLibIncrementalCache5h9WozY(strM7151constructorimpl);
                List<FileSignatureProvider> listM7189getSignatureProvidersForLib5h9WozY = loadedIr.m7189getSignatureProvidersForLib5h9WozY(strM7151constructorimpl);
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listM7189getSignatureProvidersForLib5h9WozY, 10)), 16));
                for (FileSignatureProvider fileSignatureProvider : listM7189getSignatureProvidersForLib5h9WozY) {
                    Pair pair = TuplesKt.to(KotlinSourceFile.m7163boximpl(KotlinSourceFile.m7165constructorimpl(fileSignatureProvider.getIrFile())), fileSignatureProvider.getSignatureToIndexMapping());
                    linkedHashMap2.put(pair.getFirst(), pair.getSecond());
                }
                IncrementalCacheArtifact incrementalCacheArtifactBuildAndCommitCacheArtifact = incrementalCacheM7102getLibIncrementalCache5h9WozY.buildAndCommitCacheArtifact(linkedHashMap2, hashSet2);
                IrModuleFragment irModuleFragment = loadedIr.getLoadedFragments().get(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl));
                if (irModuleFragment == null) {
                    ICUtilsKt.m7124notFoundIcError5T69zGA$default("loaded fragment", strM7151constructorimpl, null, 4, null);
                    throw null;
                }
                HashSet<KotlinSourceFile> sourceFiles = incrementalCacheArtifactBuildAndCommitCacheArtifact.getSourceFiles();
                for (IrFile irFile : irModuleFragment.getFiles()) {
                    if (!sourceFiles.contains(KotlinSourceFile.m7163boximpl(KotlinSourceFile.m7165constructorimpl(irFile)))) {
                        ICUtilsKt.m7122icError5T69zGA$default("file " + irFile.getFileEntry().getName() + " is absent in incremental cache and klib", strM7151constructorimpl, null, 4, null);
                        throw null;
                    }
                }
                Pair pair2 = TuplesKt.to(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl), incrementalCacheArtifactBuildAndCommitCacheArtifact);
                linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
            }
            return linkedHashMap;
        }
    }

    private final List<ModuleArtifact> commitCacheAndBuildModuleArtifacts(Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts, Map<KotlinLibraryFile, String> moduleNames, KotlinSourceFileMap<? extends List<JsIrProgramFragment>> rebuiltFileFragments) {
        StopwatchIC stopwatchIC = this.stopwatch;
        stopwatchIC.startNext("Incremental cache - committing artifacts");
        ArrayList arrayList = new ArrayList(incrementalCacheArtifacts.size());
        for (Map.Entry<KotlinLibraryFile, IncrementalCacheArtifact> entry : incrementalCacheArtifacts.entrySet()) {
            String strM7157unboximpl = entry.getKey().m7157unboximpl();
            IncrementalCacheArtifact value = entry.getValue();
            String str = moduleNames.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
            if (str == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("module name", strM7157unboximpl, null, 4, null);
                throw null;
            }
            Map<KotlinSourceFile, ? extends List<JsIrProgramFragment>> mapM7177get5h9WozY = rebuiltFileFragments.m7177get5h9WozY(strM7157unboximpl);
            if (mapM7177get5h9WozY == null) {
                mapM7177get5h9WozY = MapsKt.emptyMap();
            }
            arrayList.add(value.buildModuleArtifactAndCommitCache(str, mapM7177get5h9WozY));
        }
        ArrayList arrayList2 = arrayList;
        StopwatchIC.stop$default(stopwatchIC, null, 1, null);
        return arrayList2;
    }

    private final List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> compileDirtyFiles(JsIrCompilerICInterface compilerForIC, Map<KotlinLibraryFile, ? extends IrModuleFragment> loadedFragments, Map<KotlinLibraryFile, ? extends Set<KotlinSourceFile>> dirtyFiles) {
        StopwatchIC stopwatchIC = this.stopwatch;
        stopwatchIC.startNext("Processing IR - lowering");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<KotlinLibraryFile, ? extends IrModuleFragment> entry : loadedFragments.entrySet()) {
            String strM7157unboximpl = entry.getKey().m7157unboximpl();
            IrModuleFragment value = entry.getValue();
            Set<KotlinSourceFile> set = dirtyFiles.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
            if (set != null) {
                for (IrFile irFile : value.getFiles()) {
                    String strM7165constructorimpl = KotlinSourceFile.m7165constructorimpl(irFile);
                    if (set.contains(KotlinSourceFile.m7163boximpl(strM7165constructorimpl))) {
                        arrayList.add(irFile);
                        arrayList2.add(TuplesKt.to(KotlinLibraryFile.m7149boximpl(strM7157unboximpl), KotlinSourceFile.m7163boximpl(strM7165constructorimpl)));
                    }
                }
            }
        }
        List<Function0<List<JsIrProgramFragment>>> listCompile = compilerForIC.compile(loadedFragments.values(), arrayList, this.mainArguments);
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        int i = 0;
        for (Object obj : arrayList2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair = (Pair) obj;
            arrayList3.add(new Triple(pair.getFirst(), pair.getSecond(), listCompile.get(i)));
            i = i2;
        }
        StopwatchIC.stop$default(stopwatchIC, null, 1, null);
        return arrayList3;
    }

    /* JADX INFO: compiled from: CacheUpdater.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001BO\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0015\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J\u001b\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\fHÆ\u0003J[\u0010\u0018\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u00032\u001a\b\u0002\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater$IrForDirtyFilesAndCompiler;", "", "incrementalCacheArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "loadedFragments", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dirtyFiles", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "irCompiler", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;)V", "getDirtyFiles", "()Ljava/util/Map;", "getIncrementalCacheArtifacts", "getIrCompiler", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "getLoadedFragments", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class IrForDirtyFilesAndCompiler {
        private final Map<KotlinLibraryFile, Set<KotlinSourceFile>> dirtyFiles;
        private final Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts;
        private final JsIrCompilerICInterface irCompiler;
        private final Map<KotlinLibraryFile, IrModuleFragment> loadedFragments;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ IrForDirtyFilesAndCompiler copy$default(IrForDirtyFilesAndCompiler irForDirtyFilesAndCompiler, Map map, Map map2, Map map3, JsIrCompilerICInterface jsIrCompilerICInterface, int i, Object obj) {
            if ((i & 1) != 0) {
                map = irForDirtyFilesAndCompiler.incrementalCacheArtifacts;
            }
            if ((i & 2) != 0) {
                map2 = irForDirtyFilesAndCompiler.loadedFragments;
            }
            if ((i & 4) != 0) {
                map3 = irForDirtyFilesAndCompiler.dirtyFiles;
            }
            if ((i & 8) != 0) {
                jsIrCompilerICInterface = irForDirtyFilesAndCompiler.irCompiler;
            }
            return irForDirtyFilesAndCompiler.copy(map, map2, map3, jsIrCompilerICInterface);
        }

        public final Map<KotlinLibraryFile, IncrementalCacheArtifact> component1() {
            return this.incrementalCacheArtifacts;
        }

        public final Map<KotlinLibraryFile, IrModuleFragment> component2() {
            return this.loadedFragments;
        }

        public final Map<KotlinLibraryFile, Set<KotlinSourceFile>> component3() {
            return this.dirtyFiles;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final JsIrCompilerICInterface getIrCompiler() {
            return this.irCompiler;
        }

        public final IrForDirtyFilesAndCompiler copy(Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts, Map<KotlinLibraryFile, ? extends IrModuleFragment> loadedFragments, Map<KotlinLibraryFile, ? extends Set<KotlinSourceFile>> dirtyFiles, JsIrCompilerICInterface irCompiler) {
            Intrinsics.checkNotNullParameter(incrementalCacheArtifacts, "incrementalCacheArtifacts");
            Intrinsics.checkNotNullParameter(loadedFragments, "loadedFragments");
            Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
            Intrinsics.checkNotNullParameter(irCompiler, "irCompiler");
            return new IrForDirtyFilesAndCompiler(incrementalCacheArtifacts, loadedFragments, dirtyFiles, irCompiler);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IrForDirtyFilesAndCompiler)) {
                return false;
            }
            IrForDirtyFilesAndCompiler irForDirtyFilesAndCompiler = (IrForDirtyFilesAndCompiler) other;
            return Intrinsics.areEqual(this.incrementalCacheArtifacts, irForDirtyFilesAndCompiler.incrementalCacheArtifacts) && Intrinsics.areEqual(this.loadedFragments, irForDirtyFilesAndCompiler.loadedFragments) && Intrinsics.areEqual(this.dirtyFiles, irForDirtyFilesAndCompiler.dirtyFiles) && Intrinsics.areEqual(this.irCompiler, irForDirtyFilesAndCompiler.irCompiler);
        }

        public int hashCode() {
            return (((((this.incrementalCacheArtifacts.hashCode() * 31) + this.loadedFragments.hashCode()) * 31) + this.dirtyFiles.hashCode()) * 31) + this.irCompiler.hashCode();
        }

        public String toString() {
            return "IrForDirtyFilesAndCompiler(incrementalCacheArtifacts=" + this.incrementalCacheArtifacts + ", loadedFragments=" + this.loadedFragments + ", dirtyFiles=" + this.dirtyFiles + ", irCompiler=" + this.irCompiler + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public IrForDirtyFilesAndCompiler(Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts, Map<KotlinLibraryFile, ? extends IrModuleFragment> loadedFragments, Map<KotlinLibraryFile, ? extends Set<KotlinSourceFile>> dirtyFiles, JsIrCompilerICInterface irCompiler) {
            Intrinsics.checkNotNullParameter(incrementalCacheArtifacts, "incrementalCacheArtifacts");
            Intrinsics.checkNotNullParameter(loadedFragments, "loadedFragments");
            Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
            Intrinsics.checkNotNullParameter(irCompiler, "irCompiler");
            this.incrementalCacheArtifacts = incrementalCacheArtifacts;
            this.loadedFragments = loadedFragments;
            this.dirtyFiles = dirtyFiles;
            this.irCompiler = irCompiler;
        }

        public final Map<KotlinLibraryFile, IncrementalCacheArtifact> getIncrementalCacheArtifacts() {
            return this.incrementalCacheArtifacts;
        }

        public final Map<KotlinLibraryFile, IrModuleFragment> getLoadedFragments() {
            return this.loadedFragments;
        }

        public final Map<KotlinLibraryFile, Set<KotlinSourceFile>> getDirtyFiles() {
            return this.dirtyFiles;
        }

        public final JsIrCompilerICInterface getIrCompiler() {
            return this.irCompiler;
        }
    }

    private final IrForDirtyFilesAndCompiler loadIrForDirtyFilesAndInitCompiler() {
        CacheUpdaterInternal cacheUpdaterInternal = new CacheUpdaterInternal();
        this.stopwatch.startNext("Modified files - checking hashes and collecting");
        KotlinSourceFileMap<KotlinSourceFileMetadata> kotlinSourceFileMapLoadModifiedFiles = cacheUpdaterInternal.loadModifiedFiles();
        this.stopwatch.startNext("Modified files - collecting exported signatures");
        KotlinSourceFileMutableMap<KotlinSourceFileExports> kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles = cacheUpdaterInternal.collectExportedSymbolsForDirtyFiles(kotlinSourceFileMapLoadModifiedFiles);
        Set<IdSignature> setCollectStubbedSignatures = cacheUpdaterInternal.collectStubbedSignatures();
        this.stopwatch.startNext("Modified files - loading and linking IR");
        JsIrLinkerLoader jsIrLinkerLoader = new JsIrLinkerLoader(this.compilerConfiguration, cacheUpdaterInternal.getLibraryDependencies(), cacheUpdaterInternal.getMainModuleFriendLibraries(), this.irFactory.invoke(), setCollectStubbedSignatures);
        KotlinSourceFileMutableMap<KotlinSourceFileExports> kotlinSourceFileMutableMap = kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles;
        LoadedJsIr loadedJsIrLoadIr$default = JsIrLinkerLoader.loadIr$default(jsIrLinkerLoader, kotlinSourceFileMutableMap, false, 2, null);
        KotlinSourceFileMap<? extends KotlinSourceFileExports> kotlinSourceFileMapCombineWith = kotlinSourceFileMutableMap;
        int i = 0;
        while (true) {
            this.stopwatch.startNext("Dependencies (" + i + ") - updating a dependency graph");
            KotlinSourceFileMap<DirtyFileMetadata> kotlinSourceFileMapRebuildDirtySourceMetadata = cacheUpdaterInternal.rebuildDirtySourceMetadata(loadedJsIrLoadIr$default, kotlinSourceFileMapCombineWith);
            this.stopwatch.startNext("Dependencies (" + i + ") - collecting files with updated exports and imports");
            KotlinSourceFileMap<UpdatedDependenciesMetadata> kotlinSourceFileMapCollectFilesWithModifiedExportsAndImports = cacheUpdaterInternal.collectFilesWithModifiedExportsAndImports(kotlinSourceFileMapRebuildDirtySourceMetadata);
            this.stopwatch.startNext("Dependencies (" + i + ") - collecting exported signatures for files with updated exports and imports");
            KotlinSourceFileMap<KotlinSourceFileExports> kotlinSourceFileMapCollectFilesToRebuildSignatures = cacheUpdaterInternal.collectFilesToRebuildSignatures(kotlinSourceFileMapCollectFilesWithModifiedExportsAndImports);
            kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles.copyFilesFrom(kotlinSourceFileMapCollectFilesToRebuildSignatures);
            this.stopwatch.startNext("Dependencies (" + i + ") - collecting files that contain updated stubbed symbols");
            KotlinSourceFileMap<KotlinSourceFileExports> kotlinSourceFileMapCollectFilesWithUpdatedStubbedSymbols = cacheUpdaterInternal.collectFilesWithUpdatedStubbedSymbols(kotlinSourceFileMutableMap);
            kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles.copyFilesFrom(kotlinSourceFileMapCollectFilesWithUpdatedStubbedSymbols);
            kotlinSourceFileMapCombineWith = KotlinSourceFileMetadataKt.combineWith(kotlinSourceFileMapCollectFilesToRebuildSignatures, kotlinSourceFileMapCollectFilesWithUpdatedStubbedSymbols);
            if (kotlinSourceFileMapCombineWith.isEmpty()) {
                break;
            }
            this.stopwatch.startNext("Dependencies (" + i + ") - loading and linking IR for files with modified exports and imports");
            loadedJsIrLoadIr$default = JsIrLinkerLoader.loadIr$default(jsIrLinkerLoader, kotlinSourceFileMapCombineWith, false, 2, null);
            i++;
        }
        if (i != 0) {
            this.stopwatch.startNext("Loading and linking all IR");
            loadedJsIrLoadIr$default = JsIrLinkerLoader.loadIr$default(jsIrLinkerLoader, kotlinSourceFileMutableMap, false, 2, null);
        }
        this.stopwatch.startNext("Processing IR - initializing backend context");
        IrModuleFragment irModuleFragment = loadedJsIrLoadIr$default.getLoadedFragments().get(KotlinLibraryFile.m7149boximpl(this.mainLibraryFile));
        if (irModuleFragment == null) {
            ICUtilsKt.m7124notFoundIcError5T69zGA$default("main module fragment", this.mainLibraryFile, null, 4, null);
            throw null;
        }
        JsIrCompilerICInterface jsIrCompilerICInterfaceCreateCompilerForIC = this.compilerInterfaceFactory.createCompilerForIC(irModuleFragment, this.compilerConfiguration);
        loadedJsIrLoadIr$default.loadUnboundSymbols();
        Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, KotlinSourceFileExports>>> setEntrySet = kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles.entrySet();
        HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(kotlinSourceFileMutableMapCollectExportedSymbolsForDirtyFiles.size());
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getKey(), new HashSet(((Map) entry.getValue()).keySet()));
            mapNewHashMapWithExpectedSize.put(pair.getFirst(), pair.getSecond());
        }
        this.stopwatch.startNext("Processing IR - updating intrinsics and builtins dependencies");
        HashMap map = mapNewHashMapWithExpectedSize;
        cacheUpdaterInternal.updateStdlibIntrinsicDependencies(loadedJsIrLoadIr$default, irModuleFragment, map);
        this.stopwatch.startNext("Incremental cache - building artifacts");
        Map<KotlinLibraryFile, IncrementalCacheArtifact> mapBuildAndCommitCacheArtifacts = cacheUpdaterInternal.buildAndCommitCacheArtifacts(loadedJsIrLoadIr$default);
        StopwatchIC.stop$default(this.stopwatch, null, 1, null);
        return new IrForDirtyFilesAndCompiler(mapBuildAndCommitCacheArtifacts, loadedJsIrLoadIr$default.getLoadedFragments(), map, jsIrCompilerICInterfaceCreateCompilerForIC);
    }

    /* JADX INFO: compiled from: CacheUpdater.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001BY\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003\u0012*\u0010\b\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\n0\t¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J-\u0010\u0017\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\n0\tHÆ\u0003Jc\u0010\u0018\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u00032,\b\u0002\u0010\b\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\n0\tHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0007HÖ\u0001R5\u0010\b\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/CacheUpdater$FragmentGenerators;", "", "incrementalCacheArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "moduleNames", "", "generators", "", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/List;)V", "getGenerators", "()Ljava/util/List;", "getIncrementalCacheArtifacts", "()Ljava/util/Map;", "getModuleNames", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FragmentGenerators {
        private final List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> generators;
        private final Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts;
        private final Map<KotlinLibraryFile, String> moduleNames;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FragmentGenerators copy$default(FragmentGenerators fragmentGenerators, Map map, Map map2, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                map = fragmentGenerators.incrementalCacheArtifacts;
            }
            if ((i & 2) != 0) {
                map2 = fragmentGenerators.moduleNames;
            }
            if ((i & 4) != 0) {
                list = fragmentGenerators.generators;
            }
            return fragmentGenerators.copy(map, map2, list);
        }

        public final Map<KotlinLibraryFile, IncrementalCacheArtifact> component1() {
            return this.incrementalCacheArtifacts;
        }

        public final Map<KotlinLibraryFile, String> component2() {
            return this.moduleNames;
        }

        public final List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> component3() {
            return this.generators;
        }

        public final FragmentGenerators copy(Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts, Map<KotlinLibraryFile, String> moduleNames, List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> generators) {
            Intrinsics.checkNotNullParameter(incrementalCacheArtifacts, "incrementalCacheArtifacts");
            Intrinsics.checkNotNullParameter(moduleNames, "moduleNames");
            Intrinsics.checkNotNullParameter(generators, "generators");
            return new FragmentGenerators(incrementalCacheArtifacts, moduleNames, generators);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FragmentGenerators)) {
                return false;
            }
            FragmentGenerators fragmentGenerators = (FragmentGenerators) other;
            return Intrinsics.areEqual(this.incrementalCacheArtifacts, fragmentGenerators.incrementalCacheArtifacts) && Intrinsics.areEqual(this.moduleNames, fragmentGenerators.moduleNames) && Intrinsics.areEqual(this.generators, fragmentGenerators.generators);
        }

        public int hashCode() {
            return (((this.incrementalCacheArtifacts.hashCode() * 31) + this.moduleNames.hashCode()) * 31) + this.generators.hashCode();
        }

        public String toString() {
            return "FragmentGenerators(incrementalCacheArtifacts=" + this.incrementalCacheArtifacts + ", moduleNames=" + this.moduleNames + ", generators=" + this.generators + ')';
        }

        public FragmentGenerators(Map<KotlinLibraryFile, IncrementalCacheArtifact> incrementalCacheArtifacts, Map<KotlinLibraryFile, String> moduleNames, List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> generators) {
            Intrinsics.checkNotNullParameter(incrementalCacheArtifacts, "incrementalCacheArtifacts");
            Intrinsics.checkNotNullParameter(moduleNames, "moduleNames");
            Intrinsics.checkNotNullParameter(generators, "generators");
            this.incrementalCacheArtifacts = incrementalCacheArtifacts;
            this.moduleNames = moduleNames;
            this.generators = generators;
        }

        public final Map<KotlinLibraryFile, IncrementalCacheArtifact> getIncrementalCacheArtifacts() {
            return this.incrementalCacheArtifacts;
        }

        public final Map<KotlinLibraryFile, String> getModuleNames() {
            return this.moduleNames;
        }

        public final List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> getGenerators() {
            return this.generators;
        }
    }

    private final FragmentGenerators loadIrAndMakeIrFragmentGenerators() {
        IrForDirtyFilesAndCompiler irForDirtyFilesAndCompilerLoadIrForDirtyFilesAndInitCompiler = loadIrForDirtyFilesAndInitCompiler();
        Map<KotlinLibraryFile, IncrementalCacheArtifact> mapComponent1 = irForDirtyFilesAndCompilerLoadIrForDirtyFilesAndInitCompiler.component1();
        Map<KotlinLibraryFile, IrModuleFragment> mapComponent2 = irForDirtyFilesAndCompilerLoadIrForDirtyFilesAndInitCompiler.component2();
        Map<KotlinLibraryFile, Set<KotlinSourceFile>> mapComponent3 = irForDirtyFilesAndCompilerLoadIrForDirtyFilesAndInitCompiler.component3();
        JsIrCompilerICInterface irCompiler = irForDirtyFilesAndCompilerLoadIrForDirtyFilesAndInitCompiler.getIrCompiler();
        Set<Map.Entry<KotlinLibraryFile, IrModuleFragment>> setEntrySet = mapComponent2.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getKey(), ((IrModuleFragment) entry.getValue()).getName().asString());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new FragmentGenerators(mapComponent1, linkedHashMap, compileDirtyFiles(irCompiler, mapComponent2, mapComponent3));
    }

    private final KotlinSourceFileMap<List<JsIrProgramFragment>> generateIrFragments(List<Triple<KotlinLibraryFile, KotlinSourceFile, Function0<List<JsIrProgramFragment>>>> generators) {
        StopwatchIC stopwatchIC = this.stopwatch;
        stopwatchIC.startNext("Processing IR - generating program fragments");
        KotlinSourceFileMutableMap kotlinSourceFileMutableMap = new KotlinSourceFileMutableMap(null, 1, null);
        while (!generators.isEmpty()) {
            Triple triple = (Triple) CollectionsKt.removeFirst(generators);
            kotlinSourceFileMutableMap.m7188setuEadwyg(((KotlinLibraryFile) triple.component1()).m7157unboximpl(), ((KotlinSourceFile) triple.component2()).m7171unboximpl(), ((Function0) triple.component3()).invoke());
        }
        StopwatchIC.stop$default(stopwatchIC, null, 1, null);
        return kotlinSourceFileMutableMap;
    }

    public final List<ModuleArtifact> actualizeCaches() {
        this.stopwatch.clear();
        this.dirtyFileStats.clear();
        FragmentGenerators fragmentGeneratorsLoadIrAndMakeIrFragmentGenerators = loadIrAndMakeIrFragmentGenerators();
        return commitCacheAndBuildModuleArtifacts(fragmentGeneratorsLoadIrAndMakeIrFragmentGenerators.component1(), fragmentGeneratorsLoadIrAndMakeIrFragmentGenerators.component2(), generateIrFragments(fragmentGeneratorsLoadIrAndMakeIrFragmentGenerators.component3()));
    }
}
