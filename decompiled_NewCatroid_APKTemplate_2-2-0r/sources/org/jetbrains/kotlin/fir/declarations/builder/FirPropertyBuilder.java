package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirPropertyBodyResolveState;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirPropertyImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirPropertyBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\n\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\tR\u001c\u0010%\u001a\u0004\u0018\u00010&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001c\u00107\u001a\u0004\u0018\u000108X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001c\u0010=\u001a\u0004\u0018\u00010>X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001c\u0010C\u001a\u0004\u0018\u00010&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010(\"\u0004\bE\u0010*R+\u0010H\u001a\u00020G2\u0006\u0010F\u001a\u00020G8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bL\u0010M\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR+\u0010N\u001a\u00020G2\u0006\u0010F\u001a\u00020G8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bP\u0010M\u001a\u0004\bN\u0010I\"\u0004\bO\u0010KR\u001a\u0010Q\u001a\u00020RX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u001a\u0010W\u001a\u00020XX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001a\u0010]\u001a\u00020^X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u001c\u0010c\u001a\u0004\u0018\u00010dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001a\u0010i\u001a\u00020jX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR\u001a\u0010o\u001a\u00020pX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010r\"\u0004\bs\u0010tR\u001c\u0010u\u001a\u0004\u0018\u00010>X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010@\"\u0004\bw\u0010BR\u001c\u0010x\u001a\u0004\u0018\u00010yX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}R\u001e\u0010~\u001a\u00020\u007fX\u0096.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001\"\u0006\b\u0082\u0001\u0010\u0083\u0001R \u0010\u0084\u0001\u001a\u00030\u0085\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R\u001d\u0010\u008a\u0001\u001a\t\u0012\u0005\u0012\u00030\u008b\u00010\u0006X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010\t¨\u0006\u008f\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirVariableBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParametersOwnerBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "bodyResolveState", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyBodyResolveState;", "getBodyResolveState", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyBodyResolveState;", "setBodyResolveState", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyBodyResolveState;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "delegateFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getDelegateFieldSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "setDelegateFieldSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)V", "initializer", "getInitializer", "setInitializer", "<set-?>", "", "isLocal", "()Z", "setLocal", "(Z)V", "isLocal$delegate", "Lkotlin/properties/ReadWriteProperty;", "isVar", "setVar", "isVar$delegate", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "setSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyBuilder implements FirAnnotationContainerBuilder, FirTypeParametersOwnerBuilder, FirVariableBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirPropertyBuilder.class), "isVar", "isVar()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirPropertyBuilder.class), "isLocal", "isLocal()Z"))};
    private FirBackingField backingField;
    private DeserializedContainerSource containerSource;
    private FirExpression delegate;
    private FirDelegateFieldSymbol delegateFieldSymbol;
    private ConeSimpleKotlinType dispatchReceiverType;
    private FirPropertyAccessor getter;
    private FirExpression initializer;
    public FirModuleData moduleData;
    public Name name;
    public FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    public FirTypeRef returnTypeRef;
    private FirPropertyAccessor setter;
    private KtSourceElement source;
    public FirDeclarationStatus status;
    public FirPropertySymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;

    /* JADX INFO: renamed from: isVar$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isVar = Delegates.INSTANCE.notNull();
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirContextReceiver> contextReceivers = new ArrayList();

    /* JADX INFO: renamed from: isLocal$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isLocal = Delegates.INSTANCE.notNull();
    private FirPropertyBodyResolveState bodyResolveState = FirPropertyBodyResolveState.NOTHING_RESOLVED;
    private final List<FirTypeParameter> typeParameters = new ArrayList();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirDeclarationStatus getStatus() {
        FirDeclarationStatus firDeclarationStatus = this.status;
        if (firDeclarationStatus != null) {
            return firDeclarationStatus;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NotificationCompat.CATEGORY_STATUS);
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirExpression getInitializer() {
        return this.initializer;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setInitializer(FirExpression firExpression) {
        this.initializer = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirExpression getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setDelegate(FirExpression firExpression) {
        this.delegate = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public boolean isVar() {
        return ((Boolean) this.isVar.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setVar(boolean z) {
        this.isVar.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirPropertyAccessor getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setGetter(FirPropertyAccessor firPropertyAccessor) {
        this.getter = firPropertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirPropertyAccessor getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setSetter(FirPropertyAccessor firPropertyAccessor) {
        this.setter = firPropertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public FirBackingField getBackingField() {
        return this.backingField;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setBackingField(FirBackingField firBackingField) {
        this.backingField = firBackingField;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    public final FirPropertySymbol getSymbol() {
        FirPropertySymbol firPropertySymbol = this.symbol;
        if (firPropertySymbol != null) {
            return firPropertySymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<set-?>");
        this.symbol = firPropertySymbol;
    }

    public final FirDelegateFieldSymbol getDelegateFieldSymbol() {
        return this.delegateFieldSymbol;
    }

    public final void setDelegateFieldSymbol(FirDelegateFieldSymbol firDelegateFieldSymbol) {
        this.delegateFieldSymbol = firDelegateFieldSymbol;
    }

    public final boolean isLocal() {
        return ((Boolean) this.isLocal.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setLocal(boolean z) {
        this.isLocal.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final FirPropertyBodyResolveState getBodyResolveState() {
        return this.bodyResolveState;
    }

    public final void setBodyResolveState(FirPropertyBodyResolveState firPropertyBodyResolveState) {
        Intrinsics.checkNotNullParameter(firPropertyBodyResolveState, "<set-?>");
        this.bodyResolveState = firPropertyBodyResolveState;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirTypeParametersOwnerBuilder
    public List<FirTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build, reason: merged with bridge method [inline-methods] */
    public FirProperty mo6809build() {
        return new FirPropertyImpl(getSource(), getResolvePhase(), getModuleData(), getOrigin(), getAttributes(), getStatus(), getReturnTypeRef(), getReceiverParameter(), getDeprecationsProvider(), getContainerSource(), getDispatchReceiverType(), getName(), getInitializer(), getDelegate(), isVar(), getGetter(), getSetter(), getBackingField(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), getSymbol(), this.delegateFieldSymbol, isLocal(), this.bodyResolveState, getTypeParameters(), null);
    }
}
