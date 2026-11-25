package org.jetbrains.kotlin.config;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.load.java.JavaTypeEnhancementState;

/* JADX INFO: compiled from: JvmAnalysisFlags.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0015\bÆ\u0002\u0018\u00002\u00020\u0001:\u00011B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R'\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\t\u0010\n\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR'\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\n\u0012\u0004\b\f\u0010\u0002\u001a\u0004\b\r\u0010\bR'\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\n\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u0011\u0010\bR'\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\n\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\bR'\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u001b\u0010\n\u0012\u0004\b\u0019\u0010\u0002\u001a\u0004\b\u001a\u0010\bR'\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b \u0010\n\u0012\u0004\b\u001e\u0010\u0002\u001a\u0004\b\u001f\u0010\bR'\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b$\u0010\n\u0012\u0004\b\"\u0010\u0002\u001a\u0004\b#\u0010\bR'\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b(\u0010\n\u0012\u0004\b&\u0010\u0002\u001a\u0004\b'\u0010\bR'\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b,\u0010\n\u0012\u0004\b*\u0010\u0002\u001a\u0004\b+\u0010\bR'\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b0\u0010\n\u0012\u0004\b.\u0010\u0002\u001a\u0004\b/\u0010\b¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAnalysisFlags;", "", "()V", "disableUltraLightClasses", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "getDisableUltraLightClasses$annotations", "getDisableUltraLightClasses", "()Lorg/jetbrains/kotlin/config/AnalysisFlag;", "disableUltraLightClasses$delegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "enableJvmPreview", "getEnableJvmPreview$annotations", "getEnableJvmPreview", "enableJvmPreview$delegate", "generatePropertyAnnotationsMethods", "getGeneratePropertyAnnotationsMethods$annotations", "getGeneratePropertyAnnotationsMethods", "generatePropertyAnnotationsMethods$delegate", "inheritMultifileParts", "getInheritMultifileParts$annotations", "getInheritMultifileParts", "inheritMultifileParts$delegate", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "getJavaTypeEnhancementState$annotations", "getJavaTypeEnhancementState", "javaTypeEnhancementState$delegate", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmDefaultMode$annotations", "getJvmDefaultMode", "jvmDefaultMode$delegate", "sanitizeParentheses", "getSanitizeParentheses$annotations", "getSanitizeParentheses", "sanitizeParentheses$delegate", "strictMetadataVersionSemantics", "getStrictMetadataVersionSemantics$annotations", "getStrictMetadataVersionSemantics", "strictMetadataVersionSemantics$delegate", "suppressMissingBuiltinsError", "getSuppressMissingBuiltinsError$annotations", "getSuppressMissingBuiltinsError", "suppressMissingBuiltinsError$delegate", "useIR", "getUseIR$annotations", "getUseIR", "useIR$delegate", "Delegates", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmAnalysisFlags {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public static final JvmAnalysisFlags INSTANCE;

    /* JADX INFO: renamed from: disableUltraLightClasses$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate disableUltraLightClasses;

    /* JADX INFO: renamed from: enableJvmPreview$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate enableJvmPreview;

    /* JADX INFO: renamed from: generatePropertyAnnotationsMethods$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate generatePropertyAnnotationsMethods;

    /* JADX INFO: renamed from: inheritMultifileParts$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate inheritMultifileParts;

    /* JADX INFO: renamed from: javaTypeEnhancementState$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate javaTypeEnhancementState;

    /* JADX INFO: renamed from: jvmDefaultMode$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate jvmDefaultMode;

    /* JADX INFO: renamed from: sanitizeParentheses$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate sanitizeParentheses;

    /* JADX INFO: renamed from: strictMetadataVersionSemantics$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate strictMetadataVersionSemantics;

    /* JADX INFO: renamed from: suppressMissingBuiltinsError$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate suppressMissingBuiltinsError;

    /* JADX INFO: renamed from: useIR$delegate, reason: from kotlin metadata */
    private static final AnalysisFlag.Delegate useIR;

    @JvmStatic
    public static /* synthetic */ void getDisableUltraLightClasses$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getEnableJvmPreview$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getGeneratePropertyAnnotationsMethods$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getInheritMultifileParts$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getJavaTypeEnhancementState$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getJvmDefaultMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getSanitizeParentheses$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getStrictMetadataVersionSemantics$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getSuppressMissingBuiltinsError$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getUseIR$annotations() {
    }

    private JvmAnalysisFlags() {
    }

    static {
        final JvmAnalysisFlags jvmAnalysisFlags = INSTANCE;
        KProperty<?>[] kPropertyArr = {new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$strictMetadataVersionSemantics$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getStrictMetadataVersionSemantics()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getStrictMetadataVersionSemantics();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "strictMetadataVersionSemantics";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$javaTypeEnhancementState$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJavaTypeEnhancementState()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getJavaTypeEnhancementState();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "javaTypeEnhancementState";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$jvmDefaultMode$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJvmDefaultMode()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getJvmDefaultMode();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "jvmDefaultMode";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$inheritMultifileParts$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getInheritMultifileParts()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getInheritMultifileParts();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "inheritMultifileParts";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$sanitizeParentheses$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSanitizeParentheses()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getSanitizeParentheses();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "sanitizeParentheses";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$suppressMissingBuiltinsError$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getSuppressMissingBuiltinsError()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getSuppressMissingBuiltinsError();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "suppressMissingBuiltinsError";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$disableUltraLightClasses$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDisableUltraLightClasses()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getDisableUltraLightClasses();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "disableUltraLightClasses";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$enableJvmPreview$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getEnableJvmPreview()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getEnableJvmPreview();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "enableJvmPreview";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$useIR$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getUseIR()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getUseIR();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "useIR";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }, new PropertyReference0(jvmAnalysisFlags) { // from class: org.jetbrains.kotlin.config.JvmAnalysisFlags$generatePropertyAnnotationsMethods$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getGeneratePropertyAnnotationsMethods()Lorg/jetbrains/kotlin/config/AnalysisFlag;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return JvmAnalysisFlags.getGeneratePropertyAnnotationsMethods();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "generatePropertyAnnotationsMethods";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(JvmAnalysisFlags.class);
            }
        }};
        $$delegatedProperties = kPropertyArr;
        JvmAnalysisFlags jvmAnalysisFlags2 = new JvmAnalysisFlags();
        INSTANCE = jvmAnalysisFlags2;
        strictMetadataVersionSemantics = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[0]);
        javaTypeEnhancementState = Delegates.JavaTypeEnhancementStateWarnByDefault.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[1]);
        jvmDefaultMode = Delegates.JvmDefaultModeDisabledByDefault.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[2]);
        inheritMultifileParts = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[3]);
        sanitizeParentheses = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[4]);
        suppressMissingBuiltinsError = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[5]);
        disableUltraLightClasses = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[6]);
        enableJvmPreview = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[7]);
        useIR = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[8]);
        generatePropertyAnnotationsMethods = AnalysisFlag.Delegates.Boolean.INSTANCE.provideDelegate(jvmAnalysisFlags2, kPropertyArr[9]);
    }

    public static final AnalysisFlag<Boolean> getStrictMetadataVersionSemantics() {
        return strictMetadataVersionSemantics.getValue((Object) INSTANCE, $$delegatedProperties[0]);
    }

    public static final AnalysisFlag<JavaTypeEnhancementState> getJavaTypeEnhancementState() {
        return javaTypeEnhancementState.getValue((Object) INSTANCE, $$delegatedProperties[1]);
    }

    public static final AnalysisFlag<JvmDefaultMode> getJvmDefaultMode() {
        return jvmDefaultMode.getValue((Object) INSTANCE, $$delegatedProperties[2]);
    }

    public static final AnalysisFlag<Boolean> getInheritMultifileParts() {
        return inheritMultifileParts.getValue((Object) INSTANCE, $$delegatedProperties[3]);
    }

    public static final AnalysisFlag<Boolean> getSanitizeParentheses() {
        return sanitizeParentheses.getValue((Object) INSTANCE, $$delegatedProperties[4]);
    }

    public static final AnalysisFlag<Boolean> getSuppressMissingBuiltinsError() {
        return suppressMissingBuiltinsError.getValue((Object) INSTANCE, $$delegatedProperties[5]);
    }

    public static final AnalysisFlag<Boolean> getDisableUltraLightClasses() {
        return disableUltraLightClasses.getValue((Object) INSTANCE, $$delegatedProperties[6]);
    }

    public static final AnalysisFlag<Boolean> getEnableJvmPreview() {
        return enableJvmPreview.getValue((Object) INSTANCE, $$delegatedProperties[7]);
    }

    public static final AnalysisFlag<Boolean> getUseIR() {
        return useIR.getValue((Object) INSTANCE, $$delegatedProperties[8]);
    }

    public static final AnalysisFlag<Boolean> getGeneratePropertyAnnotationsMethods() {
        return generatePropertyAnnotationsMethods.getValue((Object) INSTANCE, $$delegatedProperties[9]);
    }

    /* JADX INFO: compiled from: JvmAnalysisFlags.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bÂ\u0002\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAnalysisFlags$Delegates;", "", "()V", "JavaTypeEnhancementStateWarnByDefault", "JvmDefaultModeDisabledByDefault", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Delegates {
        public static final Delegates INSTANCE = new Delegates();

        /* JADX INFO: compiled from: JvmAnalysisFlags.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0086\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAnalysisFlags$Delegates$JavaTypeEnhancementStateWarnByDefault;", "", "()V", "provideDelegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "instance", "property", "Lkotlin/reflect/KProperty;", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class JavaTypeEnhancementStateWarnByDefault {
            public static final JavaTypeEnhancementStateWarnByDefault INSTANCE = new JavaTypeEnhancementStateWarnByDefault();

            private JavaTypeEnhancementStateWarnByDefault() {
            }

            public final AnalysisFlag.Delegate<JavaTypeEnhancementState> provideDelegate(Object instance, KProperty<?> property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return new AnalysisFlag.Delegate<>(property.getName(), JavaTypeEnhancementState.INSTANCE.getDEFAULT());
            }
        }

        private Delegates() {
        }

        /* JADX INFO: compiled from: JvmAnalysisFlags.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0086\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAnalysisFlags$Delegates$JvmDefaultModeDisabledByDefault;", "", "()V", "provideDelegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "instance", "property", "Lkotlin/reflect/KProperty;", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class JvmDefaultModeDisabledByDefault {
            public static final JvmDefaultModeDisabledByDefault INSTANCE = new JvmDefaultModeDisabledByDefault();

            private JvmDefaultModeDisabledByDefault() {
            }

            public final AnalysisFlag.Delegate<JvmDefaultMode> provideDelegate(Object instance, KProperty<?> property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return new AnalysisFlag.Delegate<>(property.getName(), JvmDefaultMode.DISABLE);
            }
        }
    }
}
