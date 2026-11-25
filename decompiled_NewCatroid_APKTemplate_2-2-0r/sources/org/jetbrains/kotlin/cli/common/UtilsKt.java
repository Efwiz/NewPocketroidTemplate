package org.jetbrains.kotlin.cli.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocationWithRange;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000x\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aT\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u00072\u0006\u0010\b\u001a\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00070\n2\u0006\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\r0\f2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\f\u001a&\u0010\u0010\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00110\n2\b\b\u0002\u0010\u0000\u001a\u00020\u0001\u001a&\u0010\u0012\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00130\n2\b\b\u0002\u0010\u0000\u001a\u00020\u0001\u001aM\u0010\u0014\u001a\u0004\u0018\u00010\u0015\"\b\b\u0000\u0010\u0016*\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u0001H\u00162\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00150\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001b¢\u0006\u0002\u0010\u001d\u001a\u000e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 \u001a\u000e\u0010!\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 \u001a \u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$0#*\u00020%2\u0006\u0010&\u001a\u00020$H\u0002\u001a)\u0010'\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$\u0018\u00010#*\u00020\u00112\b\u0010(\u001a\u0004\u0018\u00010$H\u0002¢\u0006\u0002\u0010)\u001a\u0016\u0010*\u001a\u0004\u0018\u00010+*\u00020,2\u0006\u0010-\u001a\u00020\u0011H\u0002\u001a\n\u0010.\u001a\u00020/*\u00020\u0001\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u00060"}, d2 = {"messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getMessageCollector", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "checkKotlinPackageUsage", "", "F", "configuration", "files", "", "getPackage", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/FqName;", "getMessageLocation", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "checkKotlinPackageUsageForLightTree", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "checkKotlinPackageUsageForPsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "getLibraryFromHome", "Ljava/io/File;", "PathProvider", "", UncompressedFiles.NAME_paths, "getLibrary", "libraryName", "", "noLibraryArgument", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/lang/String;)Ljava/io/File;", "incrementalCompilationIsEnabled", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "incrementalCompilationIsEnabledForJs", "getLineAndColumnByOffsetStartingWithOnes", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "startOffset", "getLineAndColumnStartingWithOnesAt", TypedValues.CycleType.S_WAVE_OFFSET, "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/lang/Integer;)Lkotlin/Pair;", "getLocationWithin", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocationWithRange;", "Lorg/jetbrains/kotlin/KtSourceElement;", "file", "toLogger", "Lorg/jetbrains/kotlin/util/Logger;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final boolean incrementalCompilationIsEnabled(CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Boolean incrementalCompilation = arguments.getIncrementalCompilation();
        return incrementalCompilation != null ? incrementalCompilation.booleanValue() : IncrementalCompilation.isEnabledForJvm();
    }

    public static final boolean incrementalCompilationIsEnabledForJs(CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Boolean incrementalCompilation = arguments.getIncrementalCompilation();
        return incrementalCompilation != null ? incrementalCompilation.booleanValue() : IncrementalCompilation.isEnabledForJs();
    }

    public static final <F> boolean checkKotlinPackageUsage(CompilerConfiguration configuration, Collection<? extends F> files, MessageCollector messageCollector, Function1<? super F, FqName> getPackage, Function1<? super F, ? extends CompilerMessageSourceLocation> getMessageLocation) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(getPackage, "getPackage");
        Intrinsics.checkNotNullParameter(getMessageLocation, "getMessageLocation");
        if (configuration.getBoolean(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE)) {
            return true;
        }
        FqName fqName = new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        for (F f : files) {
            if (FqNamesUtilKt.isSubpackageOf(getPackage.invoke(f), fqName)) {
                messageCollector.report(CompilerMessageSeverity.ERROR, "Only the Kotlin standard library is allowed to use the 'kotlin' package", getMessageLocation.invoke(f));
                return false;
            }
        }
        return true;
    }

    private static final MessageCollector getMessageCollector(CompilerConfiguration compilerConfiguration) {
        Object obj = compilerConfiguration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.INSTANCE.getNONE());
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return (MessageCollector) obj;
    }

    public static /* synthetic */ boolean checkKotlinPackageUsageForPsi$default(CompilerConfiguration compilerConfiguration, Collection collection, MessageCollector messageCollector, int i, Object obj) {
        if ((i & 4) != 0) {
            messageCollector = getMessageCollector(compilerConfiguration);
        }
        return checkKotlinPackageUsageForPsi(compilerConfiguration, collection, messageCollector);
    }

    public static final boolean checkKotlinPackageUsageForPsi(CompilerConfiguration configuration, Collection<? extends KtFile> files, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        return checkKotlinPackageUsage(configuration, files, messageCollector, new Function1<KtFile, FqName>() { // from class: org.jetbrains.kotlin.cli.common.UtilsKt.checkKotlinPackageUsageForPsi.1
            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(KtFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getPackageFqName();
            }
        }, new Function1<KtFile, CompilerMessageSourceLocation>() { // from class: org.jetbrains.kotlin.cli.common.UtilsKt.checkKotlinPackageUsageForPsi.2
            @Override // kotlin.jvm.functions.Function1
            public final CompilerMessageSourceLocation invoke(KtFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KtPackageDirective packageDirective = it.getPackageDirective();
                Intrinsics.checkNotNull(packageDirective);
                return MessageUtil.psiElementToMessageLocation(packageDirective);
            }
        });
    }

    public static /* synthetic */ boolean checkKotlinPackageUsageForLightTree$default(CompilerConfiguration compilerConfiguration, Collection collection, MessageCollector messageCollector, int i, Object obj) {
        if ((i & 4) != 0) {
            messageCollector = getMessageCollector(compilerConfiguration);
        }
        return checkKotlinPackageUsageForLightTree(compilerConfiguration, collection, messageCollector);
    }

    public static final boolean checkKotlinPackageUsageForLightTree(CompilerConfiguration configuration, Collection<? extends FirFile> files, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        return checkKotlinPackageUsage(configuration, files, messageCollector, new Function1<FirFile, FqName>() { // from class: org.jetbrains.kotlin.cli.common.UtilsKt.checkKotlinPackageUsageForLightTree.1
            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(FirFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return org.jetbrains.kotlin.fir.UtilsKt.getPackageFqName(it);
            }
        }, new Function1<FirFile, CompilerMessageSourceLocation>() { // from class: org.jetbrains.kotlin.cli.common.UtilsKt.checkKotlinPackageUsageForLightTree.2
            @Override // kotlin.jvm.functions.Function1
            public final CompilerMessageSourceLocation invoke(FirFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KtSourceElement source = it.getPackageDirective().getSource();
                return source != null ? UtilsKt.getLocationWithin(source, it) : null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CompilerMessageLocationWithRange getLocationWithin(KtSourceElement ktSourceElement, FirFile firFile) {
        Pair<Integer, Integer> lineAndColumnStartingWithOnesAt;
        KtSourceFile sourceFile = firFile.getSourceFile();
        if (sourceFile == null || (lineAndColumnStartingWithOnesAt = getLineAndColumnStartingWithOnesAt(firFile, Integer.valueOf(ktSourceElement.getStartOffset()))) == null) {
            return null;
        }
        int iIntValue = lineAndColumnStartingWithOnesAt.component1().intValue();
        int iIntValue2 = lineAndColumnStartingWithOnesAt.component2().intValue();
        Pair<Integer, Integer> lineAndColumnStartingWithOnesAt2 = getLineAndColumnStartingWithOnesAt(firFile, Integer.valueOf(ktSourceElement.getEndOffset()));
        if (lineAndColumnStartingWithOnesAt2 == null) {
            return null;
        }
        int iIntValue3 = lineAndColumnStartingWithOnesAt2.component1().intValue();
        int iIntValue4 = lineAndColumnStartingWithOnesAt2.component2().intValue();
        CompilerMessageLocationWithRange.Companion companion = CompilerMessageLocationWithRange.INSTANCE;
        String path = sourceFile.getPath();
        Integer numValueOf = Integer.valueOf(iIntValue3);
        Integer numValueOf2 = Integer.valueOf(iIntValue4);
        CharSequence text = KtSourceElementKt.getText(ktSourceElement);
        return companion.create(path, iIntValue, iIntValue2, numValueOf, numValueOf2, text != null ? text.toString() : null);
    }

    private static final Pair<Integer, Integer> getLineAndColumnStartingWithOnesAt(FirFile firFile, Integer num) {
        if (num == null) {
            return null;
        }
        int iIntValue = num.intValue();
        KtSourceFileLinesMapping sourceFileLinesMapping = firFile.getSourceFileLinesMapping();
        if (sourceFileLinesMapping != null) {
            return getLineAndColumnByOffsetStartingWithOnes(sourceFileLinesMapping, iIntValue);
        }
        return null;
    }

    private static final Pair<Integer, Integer> getLineAndColumnByOffsetStartingWithOnes(KtSourceFileLinesMapping ktSourceFileLinesMapping, int i) {
        Pair<Integer, Integer> lineAndColumnByOffset = ktSourceFileLinesMapping.getLineAndColumnByOffset(i);
        return TuplesKt.to(Integer.valueOf(lineAndColumnByOffset.component1().intValue() + 1), Integer.valueOf(lineAndColumnByOffset.component2().intValue() + 1));
    }

    public static final <PathProvider> File getLibraryFromHome(PathProvider pathprovider, Function1<? super PathProvider, ? extends File> getLibrary, String libraryName, MessageCollector messageCollector, String noLibraryArgument) {
        Intrinsics.checkNotNullParameter(getLibrary, "getLibrary");
        Intrinsics.checkNotNullParameter(libraryName, "libraryName");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(noLibraryArgument, "noLibraryArgument");
        if (pathprovider != null) {
            File fileInvoke = getLibrary.invoke(pathprovider);
            if (fileInvoke.exists()) {
                return fileInvoke;
            }
        }
        messageCollector.report(CompilerMessageSeverity.STRONG_WARNING, "Unable to find " + libraryName + " in the Kotlin home directory. Pass either " + noLibraryArgument + " to prevent adding it to the classpath, or the correct '-kotlin-home'", null);
        return null;
    }

    public static final Logger toLogger(final MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "<this>");
        return new Logger() { // from class: org.jetbrains.kotlin.cli.common.UtilsKt.toLogger.1
            @Override // org.jetbrains.kotlin.util.Logger
            /* JADX INFO: renamed from: error */
            public void mo7307error(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, message, null, 4, null);
            }

            @Override // org.jetbrains.kotlin.util.Logger
            public Void fatal(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, message, null, 4, null);
                System.exit(1);
                throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
            }

            @Override // org.jetbrains.kotlin.util.Logger
            public void warning(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, message, null, 4, null);
            }

            @Override // org.jetbrains.kotlin.util.Logger
            public void log(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.LOGGING, message, null, 4, null);
            }
        };
    }
}
