package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.rendering.KtDefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;

/* JADX INFO: compiled from: BackendErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/BackendErrors;", "", "()V", "NON_LOCAL_RETURN_IN_DISABLED_INLINE", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getNON_LOCAL_RETURN_IN_DISABLED_INLINE", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "NON_LOCAL_RETURN_IN_DISABLED_INLINE$delegate", "Lkotlin/properties/ReadOnlyProperty;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BackendErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public static final BackendErrors INSTANCE;

    /* JADX INFO: renamed from: NON_LOCAL_RETURN_IN_DISABLED_INLINE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_LOCAL_RETURN_IN_DISABLED_INLINE;

    private BackendErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(BackendErrors.class), "NON_LOCAL_RETURN_IN_DISABLED_INLINE", "getNON_LOCAL_RETURN_IN_DISABLED_INLINE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;"))};
        $$delegatedProperties = kPropertyArr;
        BackendErrors backendErrors = new BackendErrors();
        INSTANCE = backendErrors;
        NON_LOCAL_RETURN_IN_DISABLED_INLINE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(backendErrors, kPropertyArr[0]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(KtDefaultErrorMessages.INSTANCE);
    }

    public final KtDiagnosticFactory0 getNON_LOCAL_RETURN_IN_DISABLED_INLINE() {
        return (KtDiagnosticFactory0) NON_LOCAL_RETURN_IN_DISABLED_INLINE.getValue(this, $$delegatedProperties[0]);
    }
}
