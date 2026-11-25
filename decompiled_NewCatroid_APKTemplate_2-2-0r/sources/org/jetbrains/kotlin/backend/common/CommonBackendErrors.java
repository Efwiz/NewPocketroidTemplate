package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: CommonBackendErrors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R3\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u000f\u0010\u0010R1\u0010\u0012\u001a\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0014\u0010\tR'\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u00178FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u0018\u0010\u0019R'\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u00178FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001c\u0010\u0019R'\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001f0\u00178FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b \u0010\u0019¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CommonBackendErrors;", "", "()V", "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT$delegate", "Lkotlin/properties/ReadOnlyProperty;", "EVALUATION_ERROR", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "", "getEVALUATION_ERROR", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "EVALUATION_ERROR$delegate", "INCOMPATIBLE_MATCHING", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "getINCOMPATIBLE_MATCHING", "INCOMPATIBLE_MATCHING$delegate", "MANY_IMPL_MEMBER_NOT_IMPLEMENTED", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "getMANY_IMPL_MEMBER_NOT_IMPLEMENTED", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "MANY_IMPL_MEMBER_NOT_IMPLEMENTED$delegate", "MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED", "getMANY_INTERFACES_MEMBER_NOT_IMPLEMENTED", "MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED$delegate", "NO_ACTUAL_FOR_EXPECT", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getNO_ACTUAL_FOR_EXPECT", "NO_ACTUAL_FOR_EXPECT$delegate", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonBackendErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;

    /* JADX INFO: renamed from: ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT;

    /* JADX INFO: renamed from: EVALUATION_ERROR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EVALUATION_ERROR;

    /* JADX INFO: renamed from: INCOMPATIBLE_MATCHING$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INCOMPATIBLE_MATCHING;
    public static final CommonBackendErrors INSTANCE;

    /* JADX INFO: renamed from: MANY_IMPL_MEMBER_NOT_IMPLEMENTED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty MANY_IMPL_MEMBER_NOT_IMPLEMENTED;

    /* JADX INFO: renamed from: MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED;

    /* JADX INFO: renamed from: NO_ACTUAL_FOR_EXPECT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NO_ACTUAL_FOR_EXPECT;

    private CommonBackendErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "NO_ACTUAL_FOR_EXPECT", "getNO_ACTUAL_FOR_EXPECT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED", "getMANY_INTERFACES_MEMBER_NOT_IMPLEMENTED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "MANY_IMPL_MEMBER_NOT_IMPLEMENTED", "getMANY_IMPL_MEMBER_NOT_IMPLEMENTED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "INCOMPATIBLE_MATCHING", "getINCOMPATIBLE_MATCHING()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT", "getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(CommonBackendErrors.class), "EVALUATION_ERROR", "getEVALUATION_ERROR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;"))};
        $$delegatedProperties = kPropertyArr;
        CommonBackendErrors commonBackendErrors = new CommonBackendErrors();
        INSTANCE = commonBackendErrors;
        NO_ACTUAL_FOR_EXPECT = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[0]);
        MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[1]);
        MANY_IMPL_MEMBER_NOT_IMPLEMENTED = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[2]);
        INCOMPATIBLE_MATCHING = new DiagnosticFactory3DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[3]);
        ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT = new DiagnosticFactory3DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[4]);
        EVALUATION_ERROR = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(commonBackendErrors, kPropertyArr[5]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(KtDefaultCommonBackendErrorMessages.INSTANCE);
    }

    public final KtDiagnosticFactory2<String, ModuleDescriptor> getNO_ACTUAL_FOR_EXPECT() {
        return (KtDiagnosticFactory2) NO_ACTUAL_FOR_EXPECT.getValue(this, $$delegatedProperties[0]);
    }

    public final KtDiagnosticFactory2<String, String> getMANY_INTERFACES_MEMBER_NOT_IMPLEMENTED() {
        return (KtDiagnosticFactory2) MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED.getValue(this, $$delegatedProperties[1]);
    }

    public final KtDiagnosticFactory2<String, String> getMANY_IMPL_MEMBER_NOT_IMPLEMENTED() {
        return (KtDiagnosticFactory2) MANY_IMPL_MEMBER_NOT_IMPLEMENTED.getValue(this, $$delegatedProperties[2]);
    }

    public final KtDiagnosticFactory3<String, String, ExpectActualCompatibility.Incompatible<?>> getINCOMPATIBLE_MATCHING() {
        return (KtDiagnosticFactory3) INCOMPATIBLE_MATCHING.getValue(this, $$delegatedProperties[3]);
    }

    public final KtDiagnosticFactory3<IrSymbol, IrSymbol, ExpectActualAnnotationsIncompatibilityType<IrConstructorCall>> getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT() {
        return (KtDiagnosticFactory3) ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT.getValue(this, $$delegatedProperties[4]);
    }

    public final KtDiagnosticFactory1<String> getEVALUATION_ERROR() {
        return (KtDiagnosticFactory1) EVALUATION_ERROR.getValue(this, $$delegatedProperties[5]);
    }
}
