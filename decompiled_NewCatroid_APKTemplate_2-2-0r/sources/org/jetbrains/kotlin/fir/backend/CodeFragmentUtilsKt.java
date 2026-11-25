package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;

/* JADX INFO: compiled from: CodeFragmentUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\"(\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"3\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\u00018B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u0005\"\u0004\b\u000b\u0010\u0007¨\u0006\u000e"}, d2 = {"newValue", "Lorg/jetbrains/kotlin/fir/backend/CodeFragmentConversionData;", "conversionData", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "getConversionData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;)Lorg/jetbrains/kotlin/fir/backend/CodeFragmentConversionData;", "setConversionData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Lorg/jetbrains/kotlin/fir/backend/CodeFragmentConversionData;)V", "<set-?>", "conversionDataOpt", "getConversionDataOpt", "setConversionDataOpt", "conversionDataOpt$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CodeFragmentUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CodeFragmentUtilsKt.class, "fir2ir"), "conversionDataOpt", "getConversionDataOpt(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;)Lorg/jetbrains/kotlin/fir/backend/CodeFragmentConversionData;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor conversionDataOpt$delegate = FirDeclarationDataRegistry.INSTANCE.data(CodeFragmentTowerDataContext.INSTANCE);

    public static final CodeFragmentConversionData getConversionData(FirCodeFragment firCodeFragment) {
        Intrinsics.checkNotNullParameter(firCodeFragment, "<this>");
        CodeFragmentConversionData conversionDataOpt = getConversionDataOpt(firCodeFragment);
        if (conversionDataOpt != null) {
            return conversionDataOpt;
        }
        throw new IllegalStateException("Conversion data is not set".toString());
    }

    private static final CodeFragmentConversionData getConversionDataOpt(FirCodeFragment firCodeFragment) {
        return (CodeFragmentConversionData) conversionDataOpt$delegate.getValue(firCodeFragment, $$delegatedProperties[0]);
    }

    private static final void setConversionDataOpt(FirCodeFragment firCodeFragment, CodeFragmentConversionData codeFragmentConversionData) {
        conversionDataOpt$delegate.setValue(firCodeFragment, $$delegatedProperties[0], codeFragmentConversionData);
    }

    public static final void setConversionData(FirCodeFragment firCodeFragment, CodeFragmentConversionData newValue) {
        Intrinsics.checkNotNullParameter(firCodeFragment, "<this>");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        setConversionDataOpt(firCodeFragment, newValue);
    }
}
