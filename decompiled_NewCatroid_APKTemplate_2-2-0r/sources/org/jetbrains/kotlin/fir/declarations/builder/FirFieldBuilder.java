package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
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
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirFieldImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirFieldBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\n\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\bR.\u0010 \u001a\u0004\u0018\u00010\u001f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b!\u0010\u0003\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R.\u00103\u001a\u0004\u0018\u0001022\b\u0010\u001e\u001a\u0004\u0018\u0001028V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b4\u0010\u0003\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001c\u00109\u001a\u0004\u0018\u00010\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010#\"\u0004\b;\u0010%R+\u0010>\u001a\u00020=2\u0006\u0010<\u001a\u00020=8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bB\u0010C\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010D\u001a\u00020EX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001a\u0010J\u001a\u00020KX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u001a\u0010P\u001a\u00020QX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR.\u0010W\u001a\u0004\u0018\u00010V2\b\u0010\u001e\u001a\u0004\u0018\u00010V8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\bX\u0010\u0003\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001a\u0010]\u001a\u00020^X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u001a\u0010c\u001a\u00020dX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR.\u0010i\u001a\u0004\u0018\u0001022\b\u0010\u001e\u001a\u0004\u0018\u0001028V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\bj\u0010\u0003\u001a\u0004\bk\u00106\"\u0004\bl\u00108R\u001c\u0010m\u001a\u0004\u0018\u00010nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u001a\u0010s\u001a\u00020tX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR\u001a\u0010y\u001a\u00020zX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010|\"\u0004\b}\u0010~R\u001c\u0010\u007f\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010\u0005X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0081\u0001\u0010\b¨\u0006\u0084\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirFieldBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirVariableBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate$annotations", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getter", "getGetter$annotations", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)V", "initializer", "getInitializer", "setInitializer", "<set-?>", "", "isVar", "()Z", "setVar", "(Z)V", "isVar$delegate", "Lkotlin/properties/ReadWriteProperty;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "receiverParameter", "getReceiverParameter$annotations", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter$annotations", "getSetter", "setSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirField;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirFieldBuilder implements FirAnnotationContainerBuilder, FirVariableBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirFieldBuilder.class), "isVar", "isVar()Z"))};
    private FirBackingField backingField;
    private DeserializedContainerSource containerSource;
    private ConeSimpleKotlinType dispatchReceiverType;
    private FirExpression initializer;
    public FirModuleData moduleData;
    public Name name;
    public FirDeclarationOrigin origin;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    public FirDeclarationStatus status;
    public FirFieldSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private final List<FirTypeParameterRef> typeParameters = new ArrayList();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirContextReceiver> contextReceivers = new ArrayList();

    /* JADX INFO: renamed from: isVar$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isVar = Delegates.INSTANCE.notNull();
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'delegate' has no impact for FirFieldBuilder")
    public static /* synthetic */ void getDelegate$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'getter' has no impact for FirFieldBuilder")
    public static /* synthetic */ void getGetter$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'receiverParameter' has no impact for FirFieldBuilder")
    public static /* synthetic */ void getReceiverParameter$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'setter' has no impact for FirFieldBuilder")
    public static /* synthetic */ void getSetter$annotations() {
    }

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

    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
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
    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
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
    public boolean isVar() {
        return ((Boolean) this.isVar.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public void setVar(boolean z) {
        this.isVar.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
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

    public FirFieldSymbol getSymbol() {
        FirFieldSymbol firFieldSymbol = this.symbol;
        if (firFieldSymbol != null) {
            return firFieldSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public void setSymbol(FirFieldSymbol firFieldSymbol) {
        Intrinsics.checkNotNullParameter(firFieldSymbol, "<set-?>");
        this.symbol = firFieldSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    public FirField mo6809build() {
        return new FirFieldImpl(getSource(), getResolvePhase(), getModuleData(), getOrigin(), getAttributes(), getTypeParameters(), getStatus(), getReturnTypeRef(), getDeprecationsProvider(), getContainerSource(), getDispatchReceiverType(), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), getName(), getInitializer(), isVar(), getBackingField(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getSymbol(), null);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ FirReceiverParameter getReceiverParameter() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ FirExpression getDelegate() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ void setDelegate(FirExpression firExpression) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ FirPropertyAccessor getGetter() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ void setGetter(FirPropertyAccessor firPropertyAccessor) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ FirPropertyAccessor getSetter() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder
    public /* synthetic */ void setSetter(FirPropertyAccessor firPropertyAccessor) {
        throw new IllegalStateException();
    }
}
