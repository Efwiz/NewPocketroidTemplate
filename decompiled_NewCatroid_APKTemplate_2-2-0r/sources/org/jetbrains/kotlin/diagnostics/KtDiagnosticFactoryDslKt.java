package org.jetbrains.kotlin.diagnostics;

import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;

/* JADX INFO: compiled from: KtDiagnosticFactoryDsl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a'\u0010\u0003\u001a\u00020\u0004\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a3\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r2\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a?\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00100\u000f\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u00102\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aK\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00130\u0012\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u00132\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aW\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00160\u0015\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u0013\"\u0004\b\u0004\u0010\u00162\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a\u001f\u0010\u0017\u001a\u00020\u0018\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a+\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\r0\u001a\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a7\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00100\u001c\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u00102\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aC\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00130\u001e\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u00132\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aO\u0010\u001f\u001a\u001a\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00160 \"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u0013\"\u0004\b\u0004\u0010\u00162\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a\u001f\u0010!\u001a\u00020\u0018\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a+\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\r0\u001a\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001a7\u0010#\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00100\u001c\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u00102\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aC\u0010$\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00130\u001e\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u00132\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\u001aO\u0010%\u001a\u001a\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00160 \"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0006\"\u0004\b\u0001\u0010\r\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u0013\"\u0004\b\u0004\u0010\u00162\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {MediaError.ERROR_TYPE_ERROR, "", "WARNING", "deprecationError0", "Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory0DelegateProvider;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "deprecationError1", "Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory1DelegateProvider;", "A", "deprecationError2", "Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory2DelegateProvider;", "B", "deprecationError3", "Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory3DelegateProvider;", "C", "deprecationError4", "Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory4DelegateProvider;", "D", "error0", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0DelegateProvider;", "error1", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1DelegateProvider;", "error2", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2DelegateProvider;", "error3", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3DelegateProvider;", "error4", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory4DelegateProvider;", "warning0", "warning1", "warning2", "warning3", "warning4", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactoryDslKt {
    public static /* synthetic */ DiagnosticFactory0DelegateProvider warning0$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory0DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement> DiagnosticFactory0DelegateProvider warning0(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory0DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory1DelegateProvider warning1$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory1DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A> DiagnosticFactory1DelegateProvider<A> warning1(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory1DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory2DelegateProvider warning2$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory2DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B> DiagnosticFactory2DelegateProvider<A, B> warning2(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory2DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory3DelegateProvider warning3$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory3DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C> DiagnosticFactory3DelegateProvider<A, B, C> warning3(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory3DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory4DelegateProvider warning4$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory4DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C, D> DiagnosticFactory4DelegateProvider<A, B, C, D> warning4(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.WARNING;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory4DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory0DelegateProvider error0$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory0DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement> DiagnosticFactory0DelegateProvider error0(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory0DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory1DelegateProvider error1$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory1DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A> DiagnosticFactory1DelegateProvider<A> error1(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory1DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory2DelegateProvider error2$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory2DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B> DiagnosticFactory2DelegateProvider<A, B> error2(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory2DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory3DelegateProvider error3$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory3DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C> DiagnosticFactory3DelegateProvider<A, B, C> error3(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory3DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DiagnosticFactory4DelegateProvider error4$default(AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 1) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory4DelegateProvider(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C, D> DiagnosticFactory4DelegateProvider<A, B, C, D> error4(AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Severity severity = Severity.ERROR;
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DiagnosticFactory4DelegateProvider<>(severity, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DeprecationDiagnosticFactory0DelegateProvider deprecationError0$default(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory0DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement> DeprecationDiagnosticFactory0DelegateProvider deprecationError0(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory0DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DeprecationDiagnosticFactory1DelegateProvider deprecationError1$default(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory1DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A> DeprecationDiagnosticFactory1DelegateProvider<A> deprecationError1(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory1DelegateProvider<>(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DeprecationDiagnosticFactory2DelegateProvider deprecationError2$default(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory2DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B> DeprecationDiagnosticFactory2DelegateProvider<A, B> deprecationError2(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory2DelegateProvider<>(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DeprecationDiagnosticFactory3DelegateProvider deprecationError3$default(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory3DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C> DeprecationDiagnosticFactory3DelegateProvider<A, B, C> deprecationError3(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory3DelegateProvider<>(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static /* synthetic */ DeprecationDiagnosticFactory4DelegateProvider deprecationError4$default(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            positioningStrategy = AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory4DelegateProvider(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }

    public static final /* synthetic */ <P extends PsiElement, A, B, C, D> DeprecationDiagnosticFactory4DelegateProvider<A, B, C, D> deprecationError4(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.reifiedOperationMarker(4, "P");
        return new DeprecationDiagnosticFactory4DelegateProvider<>(featureForError, positioningStrategy, Reflection.getOrCreateKotlinClass(PsiElement.class));
    }
}
