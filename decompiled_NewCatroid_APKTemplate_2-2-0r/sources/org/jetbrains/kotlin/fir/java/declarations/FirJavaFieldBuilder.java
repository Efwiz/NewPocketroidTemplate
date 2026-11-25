package org.jetbrains.kotlin.fir.java.declarations;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt;
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
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJavaField.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u00107\u001a\u000208H\u0016R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR+\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R+\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0015\u0010\u0012\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R$\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0018\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R*\u0010%\u001a\u00020$2\u0006\u0010#\u001a\u00020$8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b&\u0010\u0002\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaFieldBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFieldBuilder;", "()V", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotationBuilder", "()Lkotlin/jvm/functions/Function0;", "setAnnotationBuilder", "(Lkotlin/jvm/functions/Function0;)V", "<set-?>", "", "isFromSource", "()Z", "setFromSource", "(Z)V", "isFromSource$delegate", "Lkotlin/properties/ReadWriteProperty;", "isStatic", "setStatic", "isStatic$delegate", "lazyInitializer", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getLazyInitializer", "()Lkotlin/Lazy;", "setLazyInitializer", "(Lkotlin/Lazy;)V", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "value", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "origin", "getOrigin$annotations", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaField;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaFieldBuilder extends FirFieldBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaFieldBuilder.class), "isStatic", "isStatic()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaFieldBuilder.class), "isFromSource", "isFromSource()Z"))};
    public Function0<? extends List<? extends FirAnnotation>> annotationBuilder;
    private Lazy<? extends FirExpression> lazyInitializer;
    private Modality modality;
    public Visibility visibility;

    /* JADX INFO: renamed from: isStatic$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isStatic = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isFromSource$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isFromSource = Delegates.INSTANCE.notNull();
    private FirResolvePhase resolvePhase = FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'origin' has no impact for FirJavaFieldBuilder")
    public static /* synthetic */ void getOrigin$annotations() {
    }

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        this.modality = modality;
    }

    public final Visibility getVisibility() {
        Visibility visibility = this.visibility;
        if (visibility != null) {
            return visibility;
        }
        Intrinsics.throwUninitializedPropertyAccessException("visibility");
        return null;
    }

    public final void setVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "<set-?>");
        this.visibility = visibility;
    }

    public final boolean isStatic() {
        return ((Boolean) this.isStatic.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setStatic(boolean z) {
        this.isStatic.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final boolean isFromSource() {
        return ((Boolean) this.isFromSource.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setFromSource(boolean z) {
        this.isFromSource.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
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

    public final Lazy<FirExpression> getLazyInitializer() {
        return this.lazyInitializer;
    }

    public final void setLazyInitializer(Lazy<? extends FirExpression> lazy) {
        this.lazyInitializer = lazy;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirJavaField build() {
        KtSourceElement source = getSource();
        FirModuleData moduleData = getModuleData();
        FirDeclarationOrigin.Java javaJavaOrigin = UtilsKt.javaOrigin(isFromSource());
        FirFieldSymbol symbol = getSymbol();
        Name name2 = getName();
        FirResolvePhase resolvePhase = getResolvePhase();
        FirTypeRef returnTypeRef = getReturnTypeRef();
        FirDeclarationStatus status = getStatus();
        boolean zIsVar = isVar();
        Function0<List<FirAnnotation>> annotationBuilder = getAnnotationBuilder();
        List<FirTypeParameterRef> typeParameters = getTypeParameters();
        Lazy<? extends FirExpression> lazyLazyOf = this.lazyInitializer;
        if (lazyLazyOf == null) {
            lazyLazyOf = LazyKt.lazyOf(getInitializer());
        }
        return new FirJavaField(source, moduleData, javaJavaOrigin, symbol, name2, resolvePhase, returnTypeRef, status, zIsVar, annotationBuilder, typeParameters, lazyLazyOf, getDispatchReceiverType(), getAttributes());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ FirDeclarationOrigin getOrigin() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ void setOrigin(FirDeclarationOrigin value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }
}
