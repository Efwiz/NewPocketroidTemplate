package org.jetbrains.kotlin.fir.java.declarations;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJavaValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010@\u001a\u00020AR&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR+\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u001e8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R+\u0010%\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u001e8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b'\u0010$\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"R\u001a\u0010(\u001a\u00020)X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010.\u001a\u00020/X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001c\u0010:\u001a\u0004\u0018\u00010;X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameterBuilder;", "", "()V", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotationBuilder", "()Lkotlin/jvm/functions/Function0;", "setAnnotationBuilder", "(Lkotlin/jvm/functions/Function0;)V", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "setContainingFunctionSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;)V", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "<set-?>", "", "isFromSource", "()Z", "setFromSource", "(Z)V", "isFromSource$delegate", "Lkotlin/properties/ReadWriteProperty;", "isVararg", "setVararg", "isVararg$delegate", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaValueParameterBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaValueParameterBuilder.class), "isVararg", "isVararg()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaValueParameterBuilder.class), "isFromSource", "isFromSource()Z"))};
    public Function0<? extends List<? extends FirAnnotation>> annotationBuilder;
    public FirFunctionSymbol<?> containingFunctionSymbol;
    private FirExpression defaultValue;
    public FirModuleData moduleData;
    public Name name;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();

    /* JADX INFO: renamed from: isVararg$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isVararg = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isFromSource$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isFromSource = Delegates.INSTANCE.notNull();

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public final void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public final FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    public final FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    public final void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    public final Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public final void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public final Function0<List<FirAnnotation>> getAnnotationBuilder() {
        Function0 function0 = this.annotationBuilder;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationBuilder");
        return null;
    }

    public final void setAnnotationBuilder(Function0<? extends List<? extends FirAnnotation>> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.annotationBuilder = function0;
    }

    public final FirExpression getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(FirExpression firExpression) {
        this.defaultValue = firExpression;
    }

    public final FirFunctionSymbol<?> getContainingFunctionSymbol() {
        FirFunctionSymbol<?> firFunctionSymbol = this.containingFunctionSymbol;
        if (firFunctionSymbol != null) {
            return firFunctionSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("containingFunctionSymbol");
        return null;
    }

    public final void setContainingFunctionSymbol(FirFunctionSymbol<?> firFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<set-?>");
        this.containingFunctionSymbol = firFunctionSymbol;
    }

    public final boolean isVararg() {
        return ((Boolean) this.isVararg.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setVararg(boolean z) {
        this.isVararg.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final boolean isFromSource() {
        return ((Boolean) this.isFromSource.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setFromSource(boolean z) {
        this.isFromSource.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final FirJavaValueParameter build() {
        return new FirJavaValueParameter(this.source, getModuleData(), UtilsKt.javaOrigin(isFromSource()), FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES(), this.attributes, getReturnTypeRef(), getName(), new FirValueParameterSymbol(getName()), getAnnotationBuilder(), this.defaultValue, getContainingFunctionSymbol(), isVararg());
    }
}
