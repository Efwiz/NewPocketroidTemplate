package org.jetbrains.kotlin.fir.java.declarations;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;

/* JADX INFO: compiled from: FirJavaConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010C\u001a\u00020DH\u0016R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR.\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\f8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u000e\u0010\u0002\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R.\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\u0010\u000b\u001a\u0004\u0018\u00010\u00138V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u0015\u0010\u0002\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R+\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u001b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b \u0010!\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR+\u0010\"\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u001b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b$\u0010!\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR+\u0010%\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u001b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b'\u0010!\u001a\u0004\b%\u0010\u001d\"\u0004\b&\u0010\u001fR*\u0010)\u001a\u00020(2\u0006\u0010\u000b\u001a\u00020(8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b*\u0010\u0002\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R.\u00100\u001a\u0004\u0018\u00010/2\b\u0010\u000b\u001a\u0004\u0018\u00010/8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b1\u0010\u0002\u001a\u0004\b2\u00103\"\u0004\b4\u00105R*\u00107\u001a\u0002062\u0006\u0010\u000b\u001a\u0002068V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b8\u0010\u0002\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructorBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirConstructorBuilder;", "()V", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotationBuilder", "()Lkotlin/jvm/functions/Function0;", "setAnnotationBuilder", "(Lkotlin/jvm/functions/Function0;)V", "value", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "body", "getBody$annotations", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "delegatedConstructor", "getDelegatedConstructor$annotations", "getDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "setDelegatedConstructor", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;)V", "<set-?>", "", "isFromSource", "()Z", "setFromSource", "(Z)V", "isFromSource$delegate", "Lkotlin/properties/ReadWriteProperty;", "isInner", "setInner", "isInner$delegate", "isPrimary", "setPrimary", "isPrimary$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "origin", "getOrigin$annotations", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "receiverParameter", "getReceiverParameter$annotations", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "resolvePhase", "getResolvePhase$annotations", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructor;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaConstructorBuilder extends FirConstructorBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaConstructorBuilder.class), "isInner", "isInner()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaConstructorBuilder.class), "isPrimary", "isPrimary()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaConstructorBuilder.class), "isFromSource", "isFromSource()Z"))};
    public Function0<? extends List<? extends FirAnnotation>> annotationBuilder;
    public Visibility visibility;

    /* JADX INFO: renamed from: isInner$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isInner = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isPrimary$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isPrimary = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isFromSource$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isFromSource = Delegates.INSTANCE.notNull();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'body' has no impact for FirJavaConstructorBuilder")
    public static /* synthetic */ void getBody$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'delegatedConstructor' has no impact for FirJavaConstructorBuilder")
    public static /* synthetic */ void getDelegatedConstructor$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'origin' has no impact for FirJavaConstructorBuilder")
    public static /* synthetic */ void getOrigin$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'receiverParameter' has no impact for FirJavaConstructorBuilder")
    public static /* synthetic */ void getReceiverParameter$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'resolvePhase' has no impact for FirJavaConstructorBuilder")
    public static /* synthetic */ void getResolvePhase$annotations() {
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

    public final boolean isInner() {
        return ((Boolean) this.isInner.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setInner(boolean z) {
        this.isInner.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final boolean isPrimary() {
        return ((Boolean) this.isPrimary.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setPrimary(boolean z) {
        this.isPrimary.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final boolean isFromSource() {
        return ((Boolean) this.isFromSource.getValue(this, $$delegatedProperties[2])).booleanValue();
    }

    public final void setFromSource(boolean z) {
        this.isFromSource.setValue(this, $$delegatedProperties[2], Boolean.valueOf(z));
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

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirJavaConstructor build() {
        return new FirJavaConstructor(getSource(), getModuleData(), getSymbol(), UtilsKt.javaOrigin(isFromSource()), isPrimary(), getReturnTypeRef(), getValueParameters(), getTypeParameters(), getAnnotationBuilder(), getStatus(), FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES(), getDispatchReceiverType());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ FirBlock getBody() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ void setBody(FirBlock firBlock) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ FirDelegatedConstructorCall getDelegatedConstructor() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ void setDelegatedConstructor(FirDelegatedConstructorCall firDelegatedConstructorCall) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ FirResolvePhase getResolvePhase() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ void setResolvePhase(FirResolvePhase value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ FirReceiverParameter getReceiverParameter() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ FirDeclarationOrigin getOrigin() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ void setOrigin(FirDeclarationOrigin value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }
}
