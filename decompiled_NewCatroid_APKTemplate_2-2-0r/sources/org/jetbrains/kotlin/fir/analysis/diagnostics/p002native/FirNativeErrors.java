package org.jetbrains.kotlin.fir.analysis.diagnostics.p002native;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DeprecationDiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: FirNativeErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0012\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\rR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\t\u001a\u0004\b\u0010\u0010\u0007R!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\t\u001a\u0004\b\u0013\u0010\u0007R\u001b\u0010\u0015\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\t\u001a\u0004\b\u0016\u0010\rR\u001b\u0010\u0018\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u0019\u0010\rR\u001b\u0010\u001b\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\t\u001a\u0004\b\u001c\u0010\rR\u001b\u0010\u001e\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\t\u001a\u0004\b\u001f\u0010\rR\u001b\u0010!\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\t\u001a\u0004\b\"\u0010\rR\u001b\u0010$\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\t\u001a\u0004\b%\u0010\rR1\u0010'\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030)\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*0(8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\t\u001a\u0004\b,\u0010-R1\u0010/\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030)\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*0(8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\t\u001a\u0004\b0\u0010-R'\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\t\u001a\u0004\b3\u0010\u0007R!\u00105\u001a\b\u0012\u0004\u0012\u00020+0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\t\u001a\u0004\b6\u0010\u0007R!\u00108\u001a\b\u0012\u0004\u0012\u00020:098FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\t\u001a\u0004\b;\u0010<R\u001b\u0010>\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b@\u0010\t\u001a\u0004\b?\u0010\rR\u001b\u0010A\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bC\u0010\t\u001a\u0004\bB\u0010\rR!\u0010D\u001a\b\u0012\u0004\u0012\u00020:0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bF\u0010\t\u001a\u0004\bE\u0010\u0007R!\u0010G\u001a\b\u0012\u0004\u0012\u00020:0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bI\u0010\t\u001a\u0004\bH\u0010\u0007R\u001b\u0010J\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bL\u0010\t\u001a\u0004\bK\u0010\rR\u001b\u0010M\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bO\u0010\t\u001a\u0004\bN\u0010\rR!\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\t\u001a\u0004\bR\u0010\u0007R\u001b\u0010T\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bV\u0010\t\u001a\u0004\bU\u0010\rR\u001b\u0010W\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bY\u0010\t\u001a\u0004\bX\u0010\rR\u001b\u0010Z\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\\\u0010\t\u001a\u0004\b[\u0010\rR\u001b\u0010]\u001a\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b_\u0010\t\u001a\u0004\b^\u0010\rR'\u0010`\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050(8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bb\u0010\t\u001a\u0004\ba\u0010-¨\u0006c"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/native/FirNativeErrors;", "", "()V", "CANNOT_CHECK_FOR_FORWARD_DECLARATION", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getCANNOT_CHECK_FOR_FORWARD_DECLARATION", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "CANNOT_CHECK_FOR_FORWARD_DECLARATION$delegate", "Lkotlin/properties/ReadOnlyProperty;", "EMPTY_OBJC_NAME", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getEMPTY_OBJC_NAME", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "EMPTY_OBJC_NAME$delegate", "FORWARD_DECLARATION_AS_CLASS_LITERAL", "getFORWARD_DECLARATION_AS_CLASS_LITERAL", "FORWARD_DECLARATION_AS_CLASS_LITERAL$delegate", "FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT", "getFORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT", "FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT$delegate", "INAPPLICABLE_EXACT_OBJC_NAME", "getINAPPLICABLE_EXACT_OBJC_NAME", "INAPPLICABLE_EXACT_OBJC_NAME$delegate", "INAPPLICABLE_OBJC_NAME", "getINAPPLICABLE_OBJC_NAME", "INAPPLICABLE_OBJC_NAME$delegate", "INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY", "getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY", "INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY$delegate", "INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL", "getINAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL", "INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL$delegate", "INAPPLICABLE_THREAD_LOCAL", "getINAPPLICABLE_THREAD_LOCAL", "INAPPLICABLE_THREAD_LOCAL$delegate", "INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL", "getINAPPLICABLE_THREAD_LOCAL_TOP_LEVEL", "INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL$delegate", "INCOMPATIBLE_OBJC_NAME_OVERRIDE", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getINCOMPATIBLE_OBJC_NAME_OVERRIDE", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "INCOMPATIBLE_OBJC_NAME_OVERRIDE$delegate", "INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE", "getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE", "INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE$delegate", "INCOMPATIBLE_THROWS_INHERITED", "getINCOMPATIBLE_THROWS_INHERITED", "INCOMPATIBLE_THROWS_INHERITED$delegate", "INCOMPATIBLE_THROWS_OVERRIDE", "getINCOMPATIBLE_THROWS_OVERRIDE", "INCOMPATIBLE_THROWS_OVERRIDE$delegate", "INVALID_CHARACTERS_NATIVE", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "", "getINVALID_CHARACTERS_NATIVE", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "INVALID_CHARACTERS_NATIVE$delegate", "INVALID_OBJC_HIDES_TARGETS", "getINVALID_OBJC_HIDES_TARGETS", "INVALID_OBJC_HIDES_TARGETS$delegate", "INVALID_OBJC_NAME", "getINVALID_OBJC_NAME", "INVALID_OBJC_NAME$delegate", "INVALID_OBJC_NAME_CHARS", "getINVALID_OBJC_NAME_CHARS", "INVALID_OBJC_NAME_CHARS$delegate", "INVALID_OBJC_NAME_FIRST_CHAR", "getINVALID_OBJC_NAME_FIRST_CHAR", "INVALID_OBJC_NAME_FIRST_CHAR$delegate", "INVALID_REFINES_IN_SWIFT_TARGETS", "getINVALID_REFINES_IN_SWIFT_TARGETS", "INVALID_REFINES_IN_SWIFT_TARGETS$delegate", "MISSING_EXACT_OBJC_NAME", "getMISSING_EXACT_OBJC_NAME", "MISSING_EXACT_OBJC_NAME$delegate", "MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND", "Lorg/jetbrains/kotlin/name/FqName;", "getMISSING_EXCEPTION_IN_THROWS_ON_SUSPEND", "MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND$delegate", "NON_LITERAL_OBJC_NAME_ARG", "getNON_LITERAL_OBJC_NAME_ARG", "NON_LITERAL_OBJC_NAME_ARG$delegate", "REDUNDANT_SWIFT_REFINEMENT", "getREDUNDANT_SWIFT_REFINEMENT", "REDUNDANT_SWIFT_REFINEMENT$delegate", "SUBTYPE_OF_HIDDEN_FROM_OBJC", "getSUBTYPE_OF_HIDDEN_FROM_OBJC", "SUBTYPE_OF_HIDDEN_FROM_OBJC$delegate", "THROWS_LIST_EMPTY", "getTHROWS_LIST_EMPTY", "THROWS_LIST_EMPTY$delegate", "UNCHECKED_CAST_TO_FORWARD_DECLARATION", "getUNCHECKED_CAST_TO_FORWARD_DECLARATION", "UNCHECKED_CAST_TO_FORWARD_DECLARATION$delegate", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;

    /* JADX INFO: renamed from: CANNOT_CHECK_FOR_FORWARD_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CANNOT_CHECK_FOR_FORWARD_DECLARATION;

    /* JADX INFO: renamed from: EMPTY_OBJC_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EMPTY_OBJC_NAME;

    /* JADX INFO: renamed from: FORWARD_DECLARATION_AS_CLASS_LITERAL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty FORWARD_DECLARATION_AS_CLASS_LITERAL;

    /* JADX INFO: renamed from: FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT;

    /* JADX INFO: renamed from: INAPPLICABLE_EXACT_OBJC_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_EXACT_OBJC_NAME;

    /* JADX INFO: renamed from: INAPPLICABLE_OBJC_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_OBJC_NAME;

    /* JADX INFO: renamed from: INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY;

    /* JADX INFO: renamed from: INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL;

    /* JADX INFO: renamed from: INAPPLICABLE_THREAD_LOCAL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_THREAD_LOCAL;

    /* JADX INFO: renamed from: INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL;

    /* JADX INFO: renamed from: INCOMPATIBLE_OBJC_NAME_OVERRIDE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INCOMPATIBLE_OBJC_NAME_OVERRIDE;

    /* JADX INFO: renamed from: INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE;

    /* JADX INFO: renamed from: INCOMPATIBLE_THROWS_INHERITED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INCOMPATIBLE_THROWS_INHERITED;

    /* JADX INFO: renamed from: INCOMPATIBLE_THROWS_OVERRIDE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INCOMPATIBLE_THROWS_OVERRIDE;
    public static final FirNativeErrors INSTANCE;

    /* JADX INFO: renamed from: INVALID_CHARACTERS_NATIVE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_CHARACTERS_NATIVE;

    /* JADX INFO: renamed from: INVALID_OBJC_HIDES_TARGETS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_OBJC_HIDES_TARGETS;

    /* JADX INFO: renamed from: INVALID_OBJC_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_OBJC_NAME;

    /* JADX INFO: renamed from: INVALID_OBJC_NAME_CHARS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_OBJC_NAME_CHARS;

    /* JADX INFO: renamed from: INVALID_OBJC_NAME_FIRST_CHAR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_OBJC_NAME_FIRST_CHAR;

    /* JADX INFO: renamed from: INVALID_REFINES_IN_SWIFT_TARGETS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INVALID_REFINES_IN_SWIFT_TARGETS;

    /* JADX INFO: renamed from: MISSING_EXACT_OBJC_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty MISSING_EXACT_OBJC_NAME;

    /* JADX INFO: renamed from: MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND;

    /* JADX INFO: renamed from: NON_LITERAL_OBJC_NAME_ARG$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_LITERAL_OBJC_NAME_ARG;

    /* JADX INFO: renamed from: REDUNDANT_SWIFT_REFINEMENT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REDUNDANT_SWIFT_REFINEMENT;

    /* JADX INFO: renamed from: SUBTYPE_OF_HIDDEN_FROM_OBJC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SUBTYPE_OF_HIDDEN_FROM_OBJC;

    /* JADX INFO: renamed from: THROWS_LIST_EMPTY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty THROWS_LIST_EMPTY;

    /* JADX INFO: renamed from: UNCHECKED_CAST_TO_FORWARD_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty UNCHECKED_CAST_TO_FORWARD_DECLARATION;

    private FirNativeErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "THROWS_LIST_EMPTY", "getTHROWS_LIST_EMPTY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INCOMPATIBLE_THROWS_OVERRIDE", "getINCOMPATIBLE_THROWS_OVERRIDE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INCOMPATIBLE_THROWS_INHERITED", "getINCOMPATIBLE_THROWS_INHERITED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND", "getMISSING_EXCEPTION_IN_THROWS_ON_SUSPEND()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY", "getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL", "getINAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_THREAD_LOCAL", "getINAPPLICABLE_THREAD_LOCAL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL", "getINAPPLICABLE_THREAD_LOCAL_TOP_LEVEL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_CHARACTERS_NATIVE", "getINVALID_CHARACTERS_NATIVE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "REDUNDANT_SWIFT_REFINEMENT", "getREDUNDANT_SWIFT_REFINEMENT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE", "getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_OBJC_NAME", "getINAPPLICABLE_OBJC_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_OBJC_NAME", "getINVALID_OBJC_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_OBJC_NAME_CHARS", "getINVALID_OBJC_NAME_CHARS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_OBJC_NAME_FIRST_CHAR", "getINVALID_OBJC_NAME_FIRST_CHAR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "EMPTY_OBJC_NAME", "getEMPTY_OBJC_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INCOMPATIBLE_OBJC_NAME_OVERRIDE", "getINCOMPATIBLE_OBJC_NAME_OVERRIDE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INAPPLICABLE_EXACT_OBJC_NAME", "getINAPPLICABLE_EXACT_OBJC_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "MISSING_EXACT_OBJC_NAME", "getMISSING_EXACT_OBJC_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "NON_LITERAL_OBJC_NAME_ARG", "getNON_LITERAL_OBJC_NAME_ARG()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_OBJC_HIDES_TARGETS", "getINVALID_OBJC_HIDES_TARGETS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "INVALID_REFINES_IN_SWIFT_TARGETS", "getINVALID_REFINES_IN_SWIFT_TARGETS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "SUBTYPE_OF_HIDDEN_FROM_OBJC", "getSUBTYPE_OF_HIDDEN_FROM_OBJC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "CANNOT_CHECK_FOR_FORWARD_DECLARATION", "getCANNOT_CHECK_FOR_FORWARD_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "UNCHECKED_CAST_TO_FORWARD_DECLARATION", "getUNCHECKED_CAST_TO_FORWARD_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT", "getFORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirNativeErrors.class), "FORWARD_DECLARATION_AS_CLASS_LITERAL", "getFORWARD_DECLARATION_AS_CLASS_LITERAL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;"))};
        $$delegatedProperties = kPropertyArr;
        FirNativeErrors firNativeErrors = new FirNativeErrors();
        INSTANCE = firNativeErrors;
        THROWS_LIST_EMPTY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[0]);
        INCOMPATIBLE_THROWS_OVERRIDE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[1]);
        INCOMPATIBLE_THROWS_INHERITED = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firNativeErrors, kPropertyArr[2]);
        MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[3]);
        INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[4]);
        INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[5]);
        INAPPLICABLE_THREAD_LOCAL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[6]);
        INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[7]);
        INVALID_CHARACTERS_NATIVE = new DeprecationDiagnosticFactory1DelegateProvider(LanguageFeature.ProhibitInvalidCharsInNativeIdentifiers, SourceElementPositioningStrategies.INSTANCE.getNAME_IDENTIFIER(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firNativeErrors, kPropertyArr[8]);
        REDUNDANT_SWIFT_REFINEMENT = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[9]);
        INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[10]);
        INAPPLICABLE_OBJC_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[11]);
        INVALID_OBJC_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[12]);
        INVALID_OBJC_NAME_CHARS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[13]);
        INVALID_OBJC_NAME_FIRST_CHAR = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[14]);
        EMPTY_OBJC_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[15]);
        INCOMPATIBLE_OBJC_NAME_OVERRIDE = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[16]);
        INAPPLICABLE_EXACT_OBJC_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[17]);
        MISSING_EXACT_OBJC_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[18]);
        NON_LITERAL_OBJC_NAME_ARG = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[19]);
        INVALID_OBJC_HIDES_TARGETS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[20]);
        INVALID_REFINES_IN_SWIFT_TARGETS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[21]);
        SUBTYPE_OF_HIDDEN_FROM_OBJC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[22]);
        CANNOT_CHECK_FOR_FORWARD_DECLARATION = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[23]);
        UNCHECKED_CAST_TO_FORWARD_DECLARATION = new DiagnosticFactory2DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[24]);
        FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[25]);
        FORWARD_DECLARATION_AS_CLASS_LITERAL = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firNativeErrors, kPropertyArr[26]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(FirNativeErrorsDefaultMessages.INSTANCE);
    }

    public final KtDiagnosticFactory0 getTHROWS_LIST_EMPTY() {
        return (KtDiagnosticFactory0) THROWS_LIST_EMPTY.getValue(this, $$delegatedProperties[0]);
    }

    public final KtDiagnosticFactory1<FirRegularClassSymbol> getINCOMPATIBLE_THROWS_OVERRIDE() {
        return (KtDiagnosticFactory1) INCOMPATIBLE_THROWS_OVERRIDE.getValue(this, $$delegatedProperties[1]);
    }

    public final KtDiagnosticFactory1<Collection<FirRegularClassSymbol>> getINCOMPATIBLE_THROWS_INHERITED() {
        return (KtDiagnosticFactory1) INCOMPATIBLE_THROWS_INHERITED.getValue(this, $$delegatedProperties[2]);
    }

    public final KtDiagnosticFactory1<FqName> getMISSING_EXCEPTION_IN_THROWS_ON_SUSPEND() {
        return (KtDiagnosticFactory1) MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND.getValue(this, $$delegatedProperties[3]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY() {
        return (KtDiagnosticFactory0) INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY.getValue(this, $$delegatedProperties[4]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL() {
        return (KtDiagnosticFactory0) INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL.getValue(this, $$delegatedProperties[5]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_THREAD_LOCAL() {
        return (KtDiagnosticFactory0) INAPPLICABLE_THREAD_LOCAL.getValue(this, $$delegatedProperties[6]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_THREAD_LOCAL_TOP_LEVEL() {
        return (KtDiagnosticFactory0) INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL.getValue(this, $$delegatedProperties[7]);
    }

    public final KtDiagnosticFactoryForDeprecation1<String> getINVALID_CHARACTERS_NATIVE() {
        return (KtDiagnosticFactoryForDeprecation1) INVALID_CHARACTERS_NATIVE.getValue(this, $$delegatedProperties[8]);
    }

    public final KtDiagnosticFactory0 getREDUNDANT_SWIFT_REFINEMENT() {
        return (KtDiagnosticFactory0) REDUNDANT_SWIFT_REFINEMENT.getValue(this, $$delegatedProperties[9]);
    }

    public final KtDiagnosticFactory2<FirBasedSymbol<?>, Collection<FirRegularClassSymbol>> getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE() {
        return (KtDiagnosticFactory2) INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE.getValue(this, $$delegatedProperties[10]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_OBJC_NAME() {
        return (KtDiagnosticFactory0) INAPPLICABLE_OBJC_NAME.getValue(this, $$delegatedProperties[11]);
    }

    public final KtDiagnosticFactory0 getINVALID_OBJC_NAME() {
        return (KtDiagnosticFactory0) INVALID_OBJC_NAME.getValue(this, $$delegatedProperties[12]);
    }

    public final KtDiagnosticFactory1<String> getINVALID_OBJC_NAME_CHARS() {
        return (KtDiagnosticFactory1) INVALID_OBJC_NAME_CHARS.getValue(this, $$delegatedProperties[13]);
    }

    public final KtDiagnosticFactory1<String> getINVALID_OBJC_NAME_FIRST_CHAR() {
        return (KtDiagnosticFactory1) INVALID_OBJC_NAME_FIRST_CHAR.getValue(this, $$delegatedProperties[14]);
    }

    public final KtDiagnosticFactory0 getEMPTY_OBJC_NAME() {
        return (KtDiagnosticFactory0) EMPTY_OBJC_NAME.getValue(this, $$delegatedProperties[15]);
    }

    public final KtDiagnosticFactory2<FirBasedSymbol<?>, Collection<FirRegularClassSymbol>> getINCOMPATIBLE_OBJC_NAME_OVERRIDE() {
        return (KtDiagnosticFactory2) INCOMPATIBLE_OBJC_NAME_OVERRIDE.getValue(this, $$delegatedProperties[16]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_EXACT_OBJC_NAME() {
        return (KtDiagnosticFactory0) INAPPLICABLE_EXACT_OBJC_NAME.getValue(this, $$delegatedProperties[17]);
    }

    public final KtDiagnosticFactory0 getMISSING_EXACT_OBJC_NAME() {
        return (KtDiagnosticFactory0) MISSING_EXACT_OBJC_NAME.getValue(this, $$delegatedProperties[18]);
    }

    public final KtDiagnosticFactory0 getNON_LITERAL_OBJC_NAME_ARG() {
        return (KtDiagnosticFactory0) NON_LITERAL_OBJC_NAME_ARG.getValue(this, $$delegatedProperties[19]);
    }

    public final KtDiagnosticFactory0 getINVALID_OBJC_HIDES_TARGETS() {
        return (KtDiagnosticFactory0) INVALID_OBJC_HIDES_TARGETS.getValue(this, $$delegatedProperties[20]);
    }

    public final KtDiagnosticFactory0 getINVALID_REFINES_IN_SWIFT_TARGETS() {
        return (KtDiagnosticFactory0) INVALID_REFINES_IN_SWIFT_TARGETS.getValue(this, $$delegatedProperties[21]);
    }

    public final KtDiagnosticFactory0 getSUBTYPE_OF_HIDDEN_FROM_OBJC() {
        return (KtDiagnosticFactory0) SUBTYPE_OF_HIDDEN_FROM_OBJC.getValue(this, $$delegatedProperties[22]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getCANNOT_CHECK_FOR_FORWARD_DECLARATION() {
        return (KtDiagnosticFactory1) CANNOT_CHECK_FOR_FORWARD_DECLARATION.getValue(this, $$delegatedProperties[23]);
    }

    public final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getUNCHECKED_CAST_TO_FORWARD_DECLARATION() {
        return (KtDiagnosticFactory2) UNCHECKED_CAST_TO_FORWARD_DECLARATION.getValue(this, $$delegatedProperties[24]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getFORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT() {
        return (KtDiagnosticFactory1) FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT.getValue(this, $$delegatedProperties[25]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getFORWARD_DECLARATION_AS_CLASS_LITERAL() {
        return (KtDiagnosticFactory1) FORWARD_DECLARATION_AS_CLASS_LITERAL.getValue(this, $$delegatedProperties[26]);
    }
}
