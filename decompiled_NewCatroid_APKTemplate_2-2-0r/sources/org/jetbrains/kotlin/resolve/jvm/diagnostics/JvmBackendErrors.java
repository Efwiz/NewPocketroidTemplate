package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmBackendErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001c\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\u000b\u0010\u0007R!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\t\u001a\u0004\b\u000e\u0010\u0007R'\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00118FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\t\u001a\u0004\b\u0013\u0010\u0014R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\t\u001a\u0004\b\u0018\u0010\u0007R\u001b\u0010\u001a\u001a\u00020\u001b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\t\u001a\u0004\b\u001c\u0010\u001dR!\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\t\u001a\u0004\b \u0010\u0007R!\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\t\u001a\u0004\b#\u0010\u0007R!\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\t\u001a\u0004\b&\u0010\u0007R'\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00118FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\t\u001a\u0004\b)\u0010\u0014R!\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\t\u001a\u0004\b,\u0010\u0007R!\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\t\u001a\u0004\b/\u0010\u0007R!\u00101\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\t\u001a\u0004\b2\u0010\u0007R\u001b\u00104\u001a\u00020\u001b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b6\u0010\t\u001a\u0004\b5\u0010\u001d¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmBackendErrors;", "", "()V", "ACCIDENTAL_OVERRIDE", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "getACCIDENTAL_OVERRIDE", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "ACCIDENTAL_OVERRIDE$delegate", "Lkotlin/properties/ReadOnlyProperty;", "CONFLICTING_INHERITED_JVM_DECLARATIONS", "getCONFLICTING_INHERITED_JVM_DECLARATIONS", "CONFLICTING_INHERITED_JVM_DECLARATIONS$delegate", "CONFLICTING_JVM_DECLARATIONS", "getCONFLICTING_JVM_DECLARATIONS", "CONFLICTING_JVM_DECLARATIONS$delegate", "DUPLICATE_CLASS_NAMES", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "", "getDUPLICATE_CLASS_NAMES", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "DUPLICATE_CLASS_NAMES$delegate", "INLINE_CALL_CYCLE", "Lorg/jetbrains/kotlin/name/Name;", "getINLINE_CALL_CYCLE", "INLINE_CALL_CYCLE$delegate", "NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getNOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC$delegate", "SCRIPT_CAPTURING_ENUM", "getSCRIPT_CAPTURING_ENUM", "SCRIPT_CAPTURING_ENUM$delegate", "SCRIPT_CAPTURING_ENUM_ENTRY", "getSCRIPT_CAPTURING_ENUM_ENTRY", "SCRIPT_CAPTURING_ENUM_ENTRY$delegate", "SCRIPT_CAPTURING_INTERFACE", "getSCRIPT_CAPTURING_INTERFACE", "SCRIPT_CAPTURING_INTERFACE$delegate", "SCRIPT_CAPTURING_NESTED_CLASS", "getSCRIPT_CAPTURING_NESTED_CLASS", "SCRIPT_CAPTURING_NESTED_CLASS$delegate", "SCRIPT_CAPTURING_OBJECT", "getSCRIPT_CAPTURING_OBJECT", "SCRIPT_CAPTURING_OBJECT$delegate", "SUSPENSION_POINT_INSIDE_MONITOR", "getSUSPENSION_POINT_INSIDE_MONITOR", "SUSPENSION_POINT_INSIDE_MONITOR$delegate", "TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND", "getTYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND", "TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND$delegate", "TYPEOF_SUSPEND_TYPE", "getTYPEOF_SUSPEND_TYPE", "TYPEOF_SUSPEND_TYPE$delegate", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBackendErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;

    /* JADX INFO: renamed from: ACCIDENTAL_OVERRIDE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ACCIDENTAL_OVERRIDE;

    /* JADX INFO: renamed from: CONFLICTING_INHERITED_JVM_DECLARATIONS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CONFLICTING_INHERITED_JVM_DECLARATIONS;

    /* JADX INFO: renamed from: CONFLICTING_JVM_DECLARATIONS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CONFLICTING_JVM_DECLARATIONS;

    /* JADX INFO: renamed from: DUPLICATE_CLASS_NAMES$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty DUPLICATE_CLASS_NAMES;

    /* JADX INFO: renamed from: INLINE_CALL_CYCLE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INLINE_CALL_CYCLE;
    public static final JvmBackendErrors INSTANCE;

    /* JADX INFO: renamed from: NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC;

    /* JADX INFO: renamed from: SCRIPT_CAPTURING_ENUM$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SCRIPT_CAPTURING_ENUM;

    /* JADX INFO: renamed from: SCRIPT_CAPTURING_ENUM_ENTRY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SCRIPT_CAPTURING_ENUM_ENTRY;

    /* JADX INFO: renamed from: SCRIPT_CAPTURING_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SCRIPT_CAPTURING_INTERFACE;

    /* JADX INFO: renamed from: SCRIPT_CAPTURING_NESTED_CLASS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SCRIPT_CAPTURING_NESTED_CLASS;

    /* JADX INFO: renamed from: SCRIPT_CAPTURING_OBJECT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SCRIPT_CAPTURING_OBJECT;

    /* JADX INFO: renamed from: SUSPENSION_POINT_INSIDE_MONITOR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SUSPENSION_POINT_INSIDE_MONITOR;

    /* JADX INFO: renamed from: TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND;

    /* JADX INFO: renamed from: TYPEOF_SUSPEND_TYPE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty TYPEOF_SUSPEND_TYPE;

    private JvmBackendErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "CONFLICTING_JVM_DECLARATIONS", "getCONFLICTING_JVM_DECLARATIONS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "CONFLICTING_INHERITED_JVM_DECLARATIONS", "getCONFLICTING_INHERITED_JVM_DECLARATIONS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "ACCIDENTAL_OVERRIDE", "getACCIDENTAL_OVERRIDE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "TYPEOF_SUSPEND_TYPE", "getTYPEOF_SUSPEND_TYPE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND", "getTYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SUSPENSION_POINT_INSIDE_MONITOR", "getSUSPENSION_POINT_INSIDE_MONITOR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SCRIPT_CAPTURING_NESTED_CLASS", "getSCRIPT_CAPTURING_NESTED_CLASS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SCRIPT_CAPTURING_OBJECT", "getSCRIPT_CAPTURING_OBJECT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SCRIPT_CAPTURING_INTERFACE", "getSCRIPT_CAPTURING_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SCRIPT_CAPTURING_ENUM", "getSCRIPT_CAPTURING_ENUM()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "SCRIPT_CAPTURING_ENUM_ENTRY", "getSCRIPT_CAPTURING_ENUM_ENTRY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "INLINE_CALL_CYCLE", "getINLINE_CALL_CYCLE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC", "getNOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBackendErrors.class), "DUPLICATE_CLASS_NAMES", "getDUPLICATE_CLASS_NAMES()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;"))};
        $$delegatedProperties = kPropertyArr;
        JvmBackendErrors jvmBackendErrors = new JvmBackendErrors();
        INSTANCE = jvmBackendErrors;
        CONFLICTING_JVM_DECLARATIONS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[0]);
        CONFLICTING_INHERITED_JVM_DECLARATIONS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[1]);
        ACCIDENTAL_OVERRIDE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[2]);
        TYPEOF_SUSPEND_TYPE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[3]);
        TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[4]);
        SUSPENSION_POINT_INSIDE_MONITOR = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[5]);
        SCRIPT_CAPTURING_NESTED_CLASS = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[6]);
        SCRIPT_CAPTURING_OBJECT = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[7]);
        SCRIPT_CAPTURING_INTERFACE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[8]);
        SCRIPT_CAPTURING_ENUM = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[9]);
        SCRIPT_CAPTURING_ENUM_ENTRY = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[10]);
        INLINE_CALL_CYCLE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[11]);
        NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[12]);
        DUPLICATE_CLASS_NAMES = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(jvmBackendErrors, kPropertyArr[13]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(KtDefaultJvmErrorMessages.INSTANCE);
    }

    public final KtDiagnosticFactory1<ConflictingJvmDeclarationsData> getCONFLICTING_JVM_DECLARATIONS() {
        return (KtDiagnosticFactory1) CONFLICTING_JVM_DECLARATIONS.getValue(this, $$delegatedProperties[0]);
    }

    public final KtDiagnosticFactory1<ConflictingJvmDeclarationsData> getCONFLICTING_INHERITED_JVM_DECLARATIONS() {
        return (KtDiagnosticFactory1) CONFLICTING_INHERITED_JVM_DECLARATIONS.getValue(this, $$delegatedProperties[1]);
    }

    public final KtDiagnosticFactory1<ConflictingJvmDeclarationsData> getACCIDENTAL_OVERRIDE() {
        return (KtDiagnosticFactory1) ACCIDENTAL_OVERRIDE.getValue(this, $$delegatedProperties[2]);
    }

    public final KtDiagnosticFactory0 getTYPEOF_SUSPEND_TYPE() {
        return (KtDiagnosticFactory0) TYPEOF_SUSPEND_TYPE.getValue(this, $$delegatedProperties[3]);
    }

    public final KtDiagnosticFactory1<String> getTYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND() {
        return (KtDiagnosticFactory1) TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND.getValue(this, $$delegatedProperties[4]);
    }

    public final KtDiagnosticFactory1<String> getSUSPENSION_POINT_INSIDE_MONITOR() {
        return (KtDiagnosticFactory1) SUSPENSION_POINT_INSIDE_MONITOR.getValue(this, $$delegatedProperties[5]);
    }

    public final KtDiagnosticFactory2<String, String> getSCRIPT_CAPTURING_NESTED_CLASS() {
        return (KtDiagnosticFactory2) SCRIPT_CAPTURING_NESTED_CLASS.getValue(this, $$delegatedProperties[6]);
    }

    public final KtDiagnosticFactory1<String> getSCRIPT_CAPTURING_OBJECT() {
        return (KtDiagnosticFactory1) SCRIPT_CAPTURING_OBJECT.getValue(this, $$delegatedProperties[7]);
    }

    public final KtDiagnosticFactory1<String> getSCRIPT_CAPTURING_INTERFACE() {
        return (KtDiagnosticFactory1) SCRIPT_CAPTURING_INTERFACE.getValue(this, $$delegatedProperties[8]);
    }

    public final KtDiagnosticFactory1<String> getSCRIPT_CAPTURING_ENUM() {
        return (KtDiagnosticFactory1) SCRIPT_CAPTURING_ENUM.getValue(this, $$delegatedProperties[9]);
    }

    public final KtDiagnosticFactory1<String> getSCRIPT_CAPTURING_ENUM_ENTRY() {
        return (KtDiagnosticFactory1) SCRIPT_CAPTURING_ENUM_ENTRY.getValue(this, $$delegatedProperties[10]);
    }

    public final KtDiagnosticFactory1<Name> getINLINE_CALL_CYCLE() {
        return (KtDiagnosticFactory1) INLINE_CALL_CYCLE.getValue(this, $$delegatedProperties[11]);
    }

    public final KtDiagnosticFactory0 getNOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC() {
        return (KtDiagnosticFactory0) NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC.getValue(this, $$delegatedProperties[12]);
    }

    public final KtDiagnosticFactory2<String, String> getDUPLICATE_CLASS_NAMES() {
        return (KtDiagnosticFactory2) DUPLICATE_CLASS_NAMES.getValue(this, $$delegatedProperties[13]);
    }
}
