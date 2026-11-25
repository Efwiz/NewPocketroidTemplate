package org.jetbrains.kotlin.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: AnalysisFlags.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R'\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\t\u0010\n\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR'\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\n\u0012\u0004\b\f\u0010\u0002\u001a\u0004\b\r\u0010\bR'\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\n\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u0011\u0010\bR'\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\n\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\bR'\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u001a\u0010\n\u0012\u0004\b\u0018\u0010\u0002\u001a\u0004\b\u0019\u0010\bR'\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\n\u0012\u0004\b\u001c\u0010\u0002\u001a\u0004\b\u001d\u0010\bR'\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\"\u0010\n\u0012\u0004\b \u0010\u0002\u001a\u0004\b!\u0010\bR'\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b'\u0010\n\u0012\u0004\b%\u0010\u0002\u001a\u0004\b&\u0010\bR'\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b+\u0010\n\u0012\u0004\b)\u0010\u0002\u001a\u0004\b*\u0010\bR'\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b/\u0010\n\u0012\u0004\b-\u0010\u0002\u001a\u0004\b.\u0010\bR'\u00100\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b3\u0010\n\u0012\u0004\b1\u0010\u0002\u001a\u0004\b2\u0010\bR'\u00104\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b7\u0010\n\u0012\u0004\b5\u0010\u0002\u001a\u0004\b6\u0010\bR'\u00108\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b;\u0010\n\u0012\u0004\b9\u0010\u0002\u001a\u0004\b:\u0010\bR'\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b?\u0010\n\u0012\u0004\b=\u0010\u0002\u001a\u0004\b>\u0010\bR'\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\bC\u0010\n\u0012\u0004\bA\u0010\u0002\u001a\u0004\bB\u0010\bR'\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\bG\u0010\n\u0012\u0004\bE\u0010\u0002\u001a\u0004\bF\u0010\bR-\u0010H\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020J0I0\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\bM\u0010\n\u0012\u0004\bK\u0010\u0002\u001a\u0004\bL\u0010\bR'\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\bQ\u0010\n\u0012\u0004\bO\u0010\u0002\u001a\u0004\bP\u0010\bR'\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\bU\u0010\n\u0012\u0004\bS\u0010\u0002\u001a\u0004\bT\u0010\b¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlags;", "", "()V", "allowFullyQualifiedNameInKClass", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "getAllowFullyQualifiedNameInKClass$annotations", "getAllowFullyQualifiedNameInKClass", "()Lorg/jetbrains/kotlin/config/AnalysisFlag;", "allowFullyQualifiedNameInKClass$delegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "allowKotlinPackage", "getAllowKotlinPackage$annotations", "getAllowKotlinPackage", "allowKotlinPackage$delegate", "allowResultReturnType", "getAllowResultReturnType$annotations", "getAllowResultReturnType", "allowResultReturnType$delegate", "allowUnstableDependencies", "getAllowUnstableDependencies$annotations", "getAllowUnstableDependencies", "allowUnstableDependencies$delegate", "builtInsFromSources", "getBuiltInsFromSources$annotations", "getBuiltInsFromSources", "builtInsFromSources$delegate", "eagerResolveOfLightClasses", "getEagerResolveOfLightClasses$annotations", "getEagerResolveOfLightClasses", "eagerResolveOfLightClasses$delegate", "expectActualLinker", "getExpectActualLinker$annotations", "getExpectActualLinker", "expectActualLinker$delegate", "explicitApiMode", "Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "getExplicitApiMode$annotations", "getExplicitApiMode", "explicitApiMode$delegate", "explicitApiVersion", "getExplicitApiVersion$annotations", "getExplicitApiVersion", "explicitApiVersion$delegate", "extendedCompilerChecks", "getExtendedCompilerChecks$annotations", "getExtendedCompilerChecks", "extendedCompilerChecks$delegate", "ideMode", "getIdeMode$annotations", "getIdeMode", "ideMode$delegate", "ignoreDataFlowInAssert", "getIgnoreDataFlowInAssert$annotations", "getIgnoreDataFlowInAssert", "ignoreDataFlowInAssert$delegate", "libraryToSourceAnalysis", "getLibraryToSourceAnalysis$annotations", "getLibraryToSourceAnalysis", "libraryToSourceAnalysis$delegate", "metadataCompilation", "getMetadataCompilation$annotations", "getMetadataCompilation", "metadataCompilation$delegate", "multiPlatformDoNotCheckActual", "getMultiPlatformDoNotCheckActual$annotations", "getMultiPlatformDoNotCheckActual", "multiPlatformDoNotCheckActual$delegate", "muteExpectActualClassesWarning", "getMuteExpectActualClassesWarning$annotations", "getMuteExpectActualClassesWarning", "muteExpectActualClassesWarning$delegate", "optIn", "", "", "getOptIn$annotations", "getOptIn", "optIn$delegate", "skipMetadataVersionCheck", "getSkipMetadataVersionCheck$annotations", "getSkipMetadataVersionCheck", "skipMetadataVersionCheck$delegate", "skipPrereleaseCheck", "getSkipPrereleaseCheck$annotations", "getSkipPrereleaseCheck", "skipPrereleaseCheck$delegate", "config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalysisFlags {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public static final AnalysisFlags INSTANCE;

    /* JADX INFO: renamed from: allowFullyQualifiedNameInKClass$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate allowFullyQualifiedNameInKClass;

    /* JADX INFO: renamed from: allowKotlinPackage$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate allowKotlinPackage;

    /* JADX INFO: renamed from: allowResultReturnType$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate allowResultReturnType;

    /* JADX INFO: renamed from: allowUnstableDependencies$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate allowUnstableDependencies;

    /* JADX INFO: renamed from: builtInsFromSources$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate builtInsFromSources;

    /* JADX INFO: renamed from: eagerResolveOfLightClasses$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate eagerResolveOfLightClasses;

    /* JADX INFO: renamed from: expectActualLinker$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate expectActualLinker;

    /* JADX INFO: renamed from: explicitApiMode$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate explicitApiMode;

    /* JADX INFO: renamed from: explicitApiVersion$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate explicitApiVersion;

    /* JADX INFO: renamed from: extendedCompilerChecks$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate extendedCompilerChecks;

    /* JADX INFO: renamed from: ideMode$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate ideMode;

    /* JADX INFO: renamed from: ignoreDataFlowInAssert$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate ignoreDataFlowInAssert;

    /* JADX INFO: renamed from: libraryToSourceAnalysis$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate libraryToSourceAnalysis;

    /* JADX INFO: renamed from: metadataCompilation$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate metadataCompilation;

    /* JADX INFO: renamed from: multiPlatformDoNotCheckActual$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate multiPlatformDoNotCheckActual;

    /* JADX INFO: renamed from: muteExpectActualClassesWarning$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate muteExpectActualClassesWarning;

    /* JADX INFO: renamed from: optIn$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate optIn;

    /* JADX INFO: renamed from: skipMetadataVersionCheck$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate skipMetadataVersionCheck;

    /* JADX INFO: renamed from: skipPrereleaseCheck$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate skipPrereleaseCheck;

    @JvmStatic
    public static /* synthetic */ void getAllowFullyQualifiedNameInKClass$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getAllowKotlinPackage$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getAllowResultReturnType$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getAllowUnstableDependencies$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getBuiltInsFromSources$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getEagerResolveOfLightClasses$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getExpectActualLinker$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getExplicitApiMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getExplicitApiVersion$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getExtendedCompilerChecks$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getIdeMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getIgnoreDataFlowInAssert$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getLibraryToSourceAnalysis$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getMetadataCompilation$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getMultiPlatformDoNotCheckActual$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getMuteExpectActualClassesWarning$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getOptIn$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getSkipMetadataVersionCheck$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getSkipPrereleaseCheck$annotations() {
    }

    private AnalysisFlags() {
    }

    static {
        final AnalysisFlags analysisFlags = INSTANCE;
        KProperty<?>[] kPropertyArr = {new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$skipMetadataVersionCheck$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSkipMetadataVersionCheck()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getSkipMetadataVersionCheck();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "skipMetadataVersionCheck";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$metadataCompilation$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getMetadataCompilation()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getMetadataCompilation();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "metadataCompilation";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$skipPrereleaseCheck$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSkipPrereleaseCheck()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getSkipPrereleaseCheck();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "skipPrereleaseCheck";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$multiPlatformDoNotCheckActual$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getMultiPlatformDoNotCheckActual()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getMultiPlatformDoNotCheckActual();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "multiPlatformDoNotCheckActual";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$expectActualLinker$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getExpectActualLinker()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getExpectActualLinker();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "expectActualLinker";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$optIn$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getOptIn()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getOptIn();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "optIn";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$explicitApiVersion$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getExplicitApiVersion()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getExplicitApiVersion();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "explicitApiVersion";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$ignoreDataFlowInAssert$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getIgnoreDataFlowInAssert()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getIgnoreDataFlowInAssert();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "ignoreDataFlowInAssert";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$allowResultReturnType$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllowResultReturnType()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getAllowResultReturnType();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allowResultReturnType";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$explicitApiMode$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getExplicitApiMode()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getExplicitApiMode();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "explicitApiMode";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$ideMode$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getIdeMode()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getIdeMode();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "ideMode";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$allowUnstableDependencies$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllowUnstableDependencies()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getAllowUnstableDependencies();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allowUnstableDependencies";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$libraryToSourceAnalysis$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getLibraryToSourceAnalysis()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getLibraryToSourceAnalysis();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "libraryToSourceAnalysis";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$extendedCompilerChecks$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getExtendedCompilerChecks()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getExtendedCompilerChecks();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "extendedCompilerChecks";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$allowKotlinPackage$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllowKotlinPackage()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getAllowKotlinPackage();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allowKotlinPackage";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$builtInsFromSources$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getBuiltInsFromSources()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getBuiltInsFromSources();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "builtInsFromSources";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$muteExpectActualClassesWarning$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getMuteExpectActualClassesWarning()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getMuteExpectActualClassesWarning();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "muteExpectActualClassesWarning";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$allowFullyQualifiedNameInKClass$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllowFullyQualifiedNameInKClass()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getAllowFullyQualifiedNameInKClass();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allowFullyQualifiedNameInKClass";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }, new PropertyReference0(analysisFlags) { // from class: org.jetbrains.kotlin.config.AnalysisFlags$eagerResolveOfLightClasses$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getEagerResolveOfLightClasses()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return AnalysisFlags.getEagerResolveOfLightClasses();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "eagerResolveOfLightClasses";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnalysisFlags.class);
            }
        }};
        $$delegatedProperties = kPropertyArr;
        AnalysisFlags analysisFlags2 = new AnalysisFlags();
        INSTANCE = analysisFlags2;
        skipMetadataVersionCheck = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[0]);
        metadataCompilation = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[1]);
        skipPrereleaseCheck = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[2]);
        multiPlatformDoNotCheckActual = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[3]);
        expectActualLinker = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[4]);
        optIn = AnalysisFlag.Delegates.ListOfStrings.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[5]);
        explicitApiVersion = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[6]);
        ignoreDataFlowInAssert = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[7]);
        allowResultReturnType = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[8]);
        explicitApiMode = AnalysisFlag.Delegates.ApiModeDisabledByDefault.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[9]);
        ideMode = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[10]);
        allowUnstableDependencies = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[11]);
        libraryToSourceAnalysis = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[12]);
        extendedCompilerChecks = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[13]);
        allowKotlinPackage = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[14]);
        builtInsFromSources = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[15]);
        muteExpectActualClassesWarning = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[16]);
        allowFullyQualifiedNameInKClass = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[17]);
        eagerResolveOfLightClasses = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(analysisFlags2, kPropertyArr[18]);
    }

    public static final AnalysisFlag<Boolean> getSkipMetadataVersionCheck() {
        return skipMetadataVersionCheck.getValue((Object) INSTANCE, $$delegatedProperties[0]);
    }

    public static final AnalysisFlag<Boolean> getMetadataCompilation() {
        return metadataCompilation.getValue((Object) INSTANCE, $$delegatedProperties[1]);
    }

    public static final AnalysisFlag<Boolean> getSkipPrereleaseCheck() {
        return skipPrereleaseCheck.getValue((Object) INSTANCE, $$delegatedProperties[2]);
    }

    public static final AnalysisFlag<Boolean> getMultiPlatformDoNotCheckActual() {
        return multiPlatformDoNotCheckActual.getValue((Object) INSTANCE, $$delegatedProperties[3]);
    }

    public static final AnalysisFlag<Boolean> getExpectActualLinker() {
        return expectActualLinker.getValue((Object) INSTANCE, $$delegatedProperties[4]);
    }

    public static final AnalysisFlag<List<String>> getOptIn() {
        return optIn.getValue((Object) INSTANCE, $$delegatedProperties[5]);
    }

    public static final AnalysisFlag<Boolean> getExplicitApiVersion() {
        return explicitApiVersion.getValue((Object) INSTANCE, $$delegatedProperties[6]);
    }

    public static final AnalysisFlag<Boolean> getIgnoreDataFlowInAssert() {
        return ignoreDataFlowInAssert.getValue((Object) INSTANCE, $$delegatedProperties[7]);
    }

    public static final AnalysisFlag<Boolean> getAllowResultReturnType() {
        return allowResultReturnType.getValue((Object) INSTANCE, $$delegatedProperties[8]);
    }

    public static final AnalysisFlag<ExplicitApiMode> getExplicitApiMode() {
        return explicitApiMode.getValue((Object) INSTANCE, $$delegatedProperties[9]);
    }

    public static final AnalysisFlag<Boolean> getIdeMode() {
        return ideMode.getValue((Object) INSTANCE, $$delegatedProperties[10]);
    }

    public static final AnalysisFlag<Boolean> getAllowUnstableDependencies() {
        return allowUnstableDependencies.getValue((Object) INSTANCE, $$delegatedProperties[11]);
    }

    public static final AnalysisFlag<Boolean> getLibraryToSourceAnalysis() {
        return libraryToSourceAnalysis.getValue((Object) INSTANCE, $$delegatedProperties[12]);
    }

    public static final AnalysisFlag<Boolean> getExtendedCompilerChecks() {
        return extendedCompilerChecks.getValue((Object) INSTANCE, $$delegatedProperties[13]);
    }

    public static final AnalysisFlag<Boolean> getAllowKotlinPackage() {
        return allowKotlinPackage.getValue((Object) INSTANCE, $$delegatedProperties[14]);
    }

    public static final AnalysisFlag<Boolean> getBuiltInsFromSources() {
        return builtInsFromSources.getValue((Object) INSTANCE, $$delegatedProperties[15]);
    }

    public static final AnalysisFlag<Boolean> getMuteExpectActualClassesWarning() {
        return muteExpectActualClassesWarning.getValue((Object) INSTANCE, $$delegatedProperties[16]);
    }

    public static final AnalysisFlag<Boolean> getAllowFullyQualifiedNameInKClass() {
        return allowFullyQualifiedNameInKClass.getValue((Object) INSTANCE, $$delegatedProperties[17]);
    }

    public static final AnalysisFlag<Boolean> getEagerResolveOfLightClasses() {
        return eagerResolveOfLightClasses.getValue((Object) INSTANCE, $$delegatedProperties[18]);
    }
}
