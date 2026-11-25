package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.config.LanguageFeature;

/* JADX INFO: compiled from: KtDiagnosticFactoryDsl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b¢\u0006\u0002\u0010\tJ3\u0010\n\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u00022\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0086\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DeprecationDiagnosticFactory1DelegateProvider;", "A", "", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "psiType", "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;Lkotlin/reflect/KClass;)V", "provideDelegate", "Lkotlin/properties/ReadOnlyProperty;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "thisRef", "prop", "Lkotlin/reflect/KProperty;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationDiagnosticFactory1DelegateProvider<A> {
    private final LanguageFeature featureForError;
    private final AbstractSourceElementPositioningStrategy positioningStrategy;
    private final KClass<?> psiType;

    public DeprecationDiagnosticFactory1DelegateProvider(LanguageFeature featureForError, AbstractSourceElementPositioningStrategy positioningStrategy, KClass<?> psiType) {
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
        Intrinsics.checkNotNullParameter(psiType, "psiType");
        this.featureForError = featureForError;
        this.positioningStrategy = positioningStrategy;
        this.psiType = psiType;
    }

    public final ReadOnlyProperty<Object, KtDiagnosticFactoryForDeprecation1<A>> provideDelegate(Object thisRef, KProperty<?> prop) {
        Intrinsics.checkNotNullParameter(prop, "prop");
        return new DummyDelegate(new KtDiagnosticFactoryForDeprecation1(this.featureForError, new KtDiagnosticFactory1(prop.getName() + "_WARNING", Severity.WARNING, this.positioningStrategy, this.psiType), new KtDiagnosticFactory1(prop.getName() + "_ERROR", Severity.ERROR, this.positioningStrategy, this.psiType)));
    }
}
