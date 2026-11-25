package org.jetbrains.kotlin.fir.java.declarations;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
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
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParametersOwnerBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJavaMethod.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010x\u001a\u00020yH\u0016R&\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR.\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b \u0010\u0004\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R \u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000e8VX\u0097\u0004¢\u0006\f\u0012\u0004\b'\u0010\u0004\u001a\u0004\b(\u0010\u0010R*\u0010*\u001a\u00020)2\u0006\u0010\u001d\u001a\u00020)8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b+\u0010\u0004\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001c\u00100\u001a\u0004\u0018\u000101X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R+\u00108\u001a\u0002072\u0006\u00106\u001a\u0002078F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b<\u0010=\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R+\u0010>\u001a\u0002072\u0006\u00106\u001a\u0002078F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b@\u0010=\u001a\u0004\b>\u00109\"\u0004\b?\u0010;R\u001a\u0010A\u001a\u00020BX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020HX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR*\u0010N\u001a\u00020M2\u0006\u0010\u001d\u001a\u00020M8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\bO\u0010\u0004\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020UX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001a\u0010Z\u001a\u00020[X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001c\u0010`\u001a\u0004\u0018\u00010aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u001a\u0010f\u001a\u00020gX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001a\u0010l\u001a\u00020mX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\u001a\u0010r\u001a\b\u0012\u0004\u0012\u00020s0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010\u0010R\u001a\u0010u\u001a\b\u0012\u0004\u0012\u00020v0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bw\u0010\u0010¨\u0006z"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaMethodBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParametersOwnerBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotationBuilder", "()Lkotlin/jvm/functions/Function0;", "setAnnotationBuilder", "(Lkotlin/jvm/functions/Function0;)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "containerSource", "getContainerSource$annotations", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers$annotations", "getContextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "deprecationsProvider", "getDeprecationsProvider$annotations", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "<set-?>", "", "isFromSource", "()Z", "setFromSource", "(Z)V", "isFromSource$delegate", "Lkotlin/properties/ReadWriteProperty;", "isStatic", "setStatic", "isStatic$delegate", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "origin", "getOrigin$annotations", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaMethod;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaMethodBuilder implements FirAnnotationContainerBuilder, FirFunctionBuilder, FirTypeParametersOwnerBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaMethodBuilder.class), "isStatic", "isStatic()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaMethodBuilder.class), "isFromSource", "isFromSource()Z"))};
    public Function0<? extends List<? extends FirAnnotation>> annotationBuilder;
    private FirBlock body;
    private ConeSimpleKotlinType dispatchReceiverType;
    public FirModuleData moduleData;
    public Name name;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    public FirDeclarationStatus status;
    public FirNamedFunctionSymbol symbol;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private final List<FirValueParameter> valueParameters = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirTypeParameter> typeParameters = new ArrayList();

    /* JADX INFO: renamed from: isStatic$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isStatic = Delegates.INSTANCE.notNull();
    private FirResolvePhase resolvePhase = FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES();

    /* JADX INFO: renamed from: isFromSource$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isFromSource = Delegates.INSTANCE.notNull();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'containerSource' has no impact for FirJavaFunctionBuilder")
    public static /* synthetic */ void getContainerSource$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'contextReceivers' has no impact for FirJavaFunctionBuilder")
    public static /* synthetic */ void getContextReceivers$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'deprecation' has no impact for FirJavaFunctionBuilder")
    public static /* synthetic */ void getDeprecationsProvider$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'origin' has no impact for FirJavaFunctionBuilder")
    public static /* synthetic */ void getOrigin$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirBlock getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setBody(FirBlock firBlock) {
        this.body = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirDeclarationStatus getStatus() {
        FirDeclarationStatus firDeclarationStatus = this.status;
        if (firDeclarationStatus != null) {
            return firDeclarationStatus;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NotificationCompat.CATEGORY_STATUS);
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
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

    public final FirNamedFunctionSymbol getSymbol() {
        FirNamedFunctionSymbol firNamedFunctionSymbol = this.symbol;
        if (firNamedFunctionSymbol != null) {
            return firNamedFunctionSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<set-?>");
        this.symbol = firNamedFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirTypeParametersOwnerBuilder
    public List<FirTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    public final boolean isStatic() {
        return ((Boolean) this.isStatic.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setStatic(boolean z) {
        this.isStatic.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
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

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ DeprecationsProvider getDeprecationsProvider() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ DeserializedContainerSource getContainerSource() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ FirDeclarationOrigin getOrigin() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public /* synthetic */ List getContextReceivers() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirTypeParametersOwnerBuilder
    /* JADX INFO: renamed from: build */
    public FirJavaMethod mo6809build() {
        return new FirJavaMethod(getSource(), getModuleData(), UtilsKt.javaOrigin(isFromSource()), getResolvePhase(), getAttributes(), getReturnTypeRef(), getTypeParameters(), getValueParameters(), getName(), getStatus(), getSymbol(), getAnnotationBuilder(), getDispatchReceiverType());
    }
}
