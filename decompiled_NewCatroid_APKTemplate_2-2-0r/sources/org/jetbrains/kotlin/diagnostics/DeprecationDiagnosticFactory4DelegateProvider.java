package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.config.LanguageFeature;

/* JADX INFO: compiled from: KtDiagnosticFactoryDsl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u00042\u00020\u0005B!\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b¢\u0006\u0002\u0010\fJE\u0010\r\u001a(\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u000f0\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0086\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory4DelegateProvider;", "A", "B", "C", "D", "", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "provideDelegate", "Lkotlin/properties/ReadOnlyProperty;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "thisRef", "prop", "Lkotlin/reflect/KProperty;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationDiagnosticFactory4DelegateProvider<A, B, C, D> {
    private final LanguageFeature featureForError;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final KClass<?> psiType;

    public DeprecationDiagnosticFactory4DelegateProvider(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, KClass<?> psiType) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.featureForError = featureForError;
        this.positioningStrategy = positioningStrategy;
        this.psiType = psiType;
    }

    public final ReadOnlyProperty<Object, KtDiagnosticFactoryForDeprecation4<A, B, C, D>> provideDelegate(Object thisRef, KProperty<?> prop) {
        Intrinsics.checkNotNullParameter(prop, "prop");
        return new DummyDelegate(new KtDiagnosticFactoryForDeprecation4(this.featureForError, new KtDiagnosticFactory4(prop.getName() + "_WARNING", Severity.WARNING, this.positioningStrategy, this.psiType), new KtDiagnosticFactory4(prop.getName() + "_ERROR", Severity.ERROR, this.positioningStrategy, this.psiType)));
    }
}
