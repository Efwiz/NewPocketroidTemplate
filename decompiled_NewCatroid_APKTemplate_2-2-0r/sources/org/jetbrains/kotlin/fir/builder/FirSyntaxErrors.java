package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;

/* JADX INFO: compiled from: FirSyntaxErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/FirSyntaxErrors;", "", "()V", "SYNTAX", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "", "getSYNTAX", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "SYNTAX$delegate", "Lkotlin/properties/ReadOnlyProperty;", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntaxErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public static final FirSyntaxErrors INSTANCE;

    /* JADX INFO: renamed from: SYNTAX$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SYNTAX;

    private FirSyntaxErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirSyntaxErrors.class), "SYNTAX", "getSYNTAX()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;"))};
        $$delegatedProperties = kPropertyArr;
        FirSyntaxErrors firSyntaxErrors = new FirSyntaxErrors();
        INSTANCE = firSyntaxErrors;
        SYNTAX = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firSyntaxErrors, kPropertyArr[0]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(FirSyntaxErrorsDefaultMessages.INSTANCE);
    }

    public final KtDiagnosticFactory1<String> getSYNTAX() {
        return (KtDiagnosticFactory1) SYNTAX.getValue(this, $$delegatedProperties[0]);
    }
}
