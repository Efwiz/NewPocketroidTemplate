package org.jetbrains.kotlin.fir.lightTree.fir;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.builder.Context;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier;
import org.jetbrains.kotlin.fir.references.builder.FirPropertyFromParameterResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001Bw\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\u0002\u0010\u0019J\u0006\u0010.\u001a\u00020\u0003J<\u0010/\u001a\u000200\"\u0004\b\u0000\u001012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00032\b\u00105\u001a\u0004\u0018\u0001062\f\u00107\u001a\b\u0012\u0004\u0012\u0002H108R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0015\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001b\u0010#\u001a\u00020$8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u001e\u001a\u0004\b%\u0010&R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/ValueParameter;", "", "isVal", "", "isVar", "modifiers", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "isFromPrimaryConstructor", "additionalAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "name", "Lorg/jetbrains/kotlin/name/Name;", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "destructuringDeclaration", "Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;", "(ZZLorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;ZLjava/util/List;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDestructuringDeclaration", "()Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;", "firValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getFirValueParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "firValueParameter$delegate", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "hasValOrVar", "toFirPropertyFromPrimaryConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", ExifInterface.GPS_DIRECTION_TRUE, "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "isExpect", "currentDispatchReceiver", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValueParameter {
    private final List<FirAnnotation> additionalAnnotations;

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final FirFunctionSymbol<?> containingFunctionSymbol;
    private final FirExpression defaultValue;
    private final DestructuringDeclaration destructuringDeclaration;

    /* JADX INFO: renamed from: firValueParameter$delegate, reason: from kotlin metadata */
    private final Lazy firValueParameter;
    private final boolean isFromPrimaryConstructor;
    private final boolean isVal;
    private final boolean isVar;
    private final Modifier modifiers;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirTypeRef returnTypeRef;
    private final KtSourceElement source;

    /* JADX WARN: Multi-variable type inference failed */
    public ValueParameter(boolean z, boolean z2, Modifier modifiers, FirTypeRef returnTypeRef, KtSourceElement source, FirModuleData moduleData, boolean z3, List<? extends FirAnnotation> additionalAnnotations, Name name2, FirExpression firExpression, FirFunctionSymbol<?> firFunctionSymbol, DestructuringDeclaration destructuringDeclaration) {
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.isVal = z;
        this.isVar = z2;
        this.modifiers = modifiers;
        this.returnTypeRef = returnTypeRef;
        this.source = source;
        this.moduleData = moduleData;
        this.isFromPrimaryConstructor = z3;
        this.additionalAnnotations = additionalAnnotations;
        this.name = name2;
        this.defaultValue = firExpression;
        this.containingFunctionSymbol = firFunctionSymbol;
        this.destructuringDeclaration = destructuringDeclaration;
        this.annotations = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.lightTree.fir.ValueParameter$annotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                ValueParameter valueParameter = this.this$0;
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                if (!valueParameter.isFromPrimaryConstructor) {
                    listCreateListBuilder.addAll(valueParameter.modifiers.getAnnotations());
                } else {
                    for (Object obj : valueParameter.modifiers.getAnnotations()) {
                        if (ConversionUtilsKt.appliesToPrimaryConstructorParameter(((FirAnnotationCall) obj).getUseSiteTarget())) {
                            listCreateListBuilder.add(obj);
                        }
                    }
                }
                listCreateListBuilder.addAll(valueParameter.additionalAnnotations);
                return CollectionsKt.build(listCreateListBuilder);
            }
        });
        this.firValueParameter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<FirValueParameter>() { // from class: org.jetbrains.kotlin.fir.lightTree.fir.ValueParameter$firValueParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirValueParameter invoke() {
                ValueParameter valueParameter = this.this$0;
                FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
                firValueParameterBuilder.setSource(valueParameter.getSource());
                firValueParameterBuilder.setModuleData(valueParameter.moduleData);
                firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firValueParameterBuilder.setReturnTypeRef(valueParameter.getReturnTypeRef());
                firValueParameterBuilder.setName(valueParameter.getName());
                firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getName()));
                firValueParameterBuilder.setDefaultValue(valueParameter.getDefaultValue());
                firValueParameterBuilder.setCrossinline(valueParameter.modifiers.hasCrossinline());
                firValueParameterBuilder.setNoinline(valueParameter.modifiers.hasNoinline());
                firValueParameterBuilder.setVararg(valueParameter.modifiers.hasVararg());
                FirFunctionSymbol<?> firFunctionSymbol2 = valueParameter.containingFunctionSymbol;
                if (firFunctionSymbol2 == null) {
                    throw new IllegalStateException("containingFunctionSymbol should present when converting ValueParameter to a FirValueParameter".toString());
                }
                firValueParameterBuilder.setContainingFunctionSymbol(firFunctionSymbol2);
                CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), valueParameter.getAnnotations());
                CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), valueParameter.additionalAnnotations);
                return firValueParameterBuilder.mo6809build();
            }
        });
    }

    public /* synthetic */ ValueParameter(boolean z, boolean z2, Modifier modifier, FirTypeRef firTypeRef, KtSourceElement ktSourceElement, FirModuleData firModuleData, boolean z3, List list, Name name2, FirExpression firExpression, FirFunctionSymbol firFunctionSymbol, DestructuringDeclaration destructuringDeclaration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, modifier, firTypeRef, ktSourceElement, firModuleData, z3, list, name2, firExpression, firFunctionSymbol, (i & 2048) != 0 ? null : destructuringDeclaration);
    }

    public final FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final Name getName() {
        return this.name;
    }

    public final FirExpression getDefaultValue() {
        return this.defaultValue;
    }

    public final DestructuringDeclaration getDestructuringDeclaration() {
        return this.destructuringDeclaration;
    }

    public final boolean hasValOrVar() {
        return this.isVal || this.isVar;
    }

    public final List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    public final FirValueParameter getFirValueParameter() {
        return (FirValueParameter) this.firValueParameter.getValue();
    }

    public final <T> FirProperty toFirPropertyFromPrimaryConstructor(FirModuleData moduleData, CallableId callableId, boolean isExpect, ConeClassLikeType currentDispatchReceiver, Context<T> context) {
        FirPropertyBuilder firPropertyBuilder;
        FirDefaultPropertySetter firDefaultPropertySetter;
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(context, "context");
        Name name2 = getFirValueParameter().getName();
        FirTypeRef returnTypeRef = getFirValueParameter().getReturnTypeRef();
        if (returnTypeRef instanceof FirImplicitTypeRef) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete code"));
            returnTypeRef = firErrorTypeRefBuilder.mo6809build();
        }
        FirTypeRef firTypeRef = returnTypeRef;
        FirPropertyBuilder firPropertyBuilder2 = new FirPropertyBuilder();
        KtSourceElement source = getFirValueParameter().getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE, 0, 0, 6, null) : null;
        firPropertyBuilder2.setSource(ktSourceElementFakeElement$default);
        firPropertyBuilder2.setModuleData(moduleData);
        firPropertyBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firPropertyBuilder2.setReturnTypeRef(UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE));
        firPropertyBuilder2.setName(name2);
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElementFakeElement$default);
        FirPropertyFromParameterResolvedNamedReferenceBuilder firPropertyFromParameterResolvedNamedReferenceBuilder = new FirPropertyFromParameterResolvedNamedReferenceBuilder();
        firPropertyFromParameterResolvedNamedReferenceBuilder.setSource(ktSourceElementFakeElement$default);
        firPropertyFromParameterResolvedNamedReferenceBuilder.setName(name2);
        firPropertyFromParameterResolvedNamedReferenceBuilder.setResolvedSymbol(getFirValueParameter().getSymbol());
        firPropertyFromParameterResolvedNamedReferenceBuilder.setSource(ktSourceElementFakeElement$default);
        firPropertyAccessExpressionBuilder.setCalleeReference(firPropertyFromParameterResolvedNamedReferenceBuilder.build());
        firPropertyBuilder2.setInitializer(firPropertyAccessExpressionBuilder.mo6809build());
        firPropertyBuilder2.setVar(this.isVar);
        firPropertyBuilder2.setSymbol(new FirPropertySymbol(callableId));
        firPropertyBuilder2.setDispatchReceiverType(currentDispatchReceiver);
        firPropertyBuilder2.setLocal(false);
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(this.modifiers.getVisibility(), this.modifiers.getModality(false));
        firDeclarationStatusImpl.setExpect(isExpect);
        firDeclarationStatusImpl.setActual(this.modifiers.hasActual());
        firDeclarationStatusImpl.setOverride(this.modifiers.hasOverride());
        firDeclarationStatusImpl.setConst(this.modifiers.hasConst());
        firPropertyBuilder2.setStatus(firDeclarationStatusImpl);
        KtSourceElement ktSourceElementFakeElement$default2 = ktSourceElementFakeElement$default != null ? KtSourceElementKt.fakeElement$default(ktSourceElementFakeElement$default, KtFakeSourceElementKind.DefaultAccessor.INSTANCE, 0, 0, 6, null) : null;
        FirDeclarationOrigin.Source source2 = FirDeclarationOrigin.Source.INSTANCE;
        List<FirAnnotationCall> annotations = this.modifiers.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (T t : annotations) {
            FirAnnotationCall firAnnotationCall = (FirAnnotationCall) t;
            if (firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.FIELD || firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                arrayList.add(t);
            }
        }
        List mutableList = CollectionsKt.toMutableList((Collection) arrayList);
        FirTypeRef firTypeRefCopyWithNewSourceKind = UtilsKt.copyWithNewSourceKind(firPropertyBuilder2.getReturnTypeRef(), KtFakeSourceElementKind.DefaultAccessor.INSTANCE);
        boolean zIsVar = firPropertyBuilder2.isVar();
        FirPropertySymbol symbol = firPropertyBuilder2.getSymbol();
        FirDeclarationStatus status = firPropertyBuilder2.getStatus();
        firPropertyBuilder2.setBackingField(new FirDefaultPropertyBackingField(moduleData, source2, ktSourceElementFakeElement$default2, mutableList, firTypeRefCopyWithNewSourceKind, zIsVar, symbol, UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : null, (2097147 & 4) != 0 ? status.isExpect() : false, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false), null, 256, null));
        List<FirAnnotation> annotations2 = firPropertyBuilder2.getAnnotations();
        List<FirAnnotationCall> annotations3 = this.modifiers.getAnnotations();
        ArrayList arrayList2 = new ArrayList();
        for (T t2 : annotations3) {
            FirAnnotationCall firAnnotationCall2 = (FirAnnotationCall) t2;
            if (firAnnotationCall2.getUseSiteTarget() == null || firAnnotationCall2.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY) {
                arrayList2.add(t2);
            }
        }
        CollectionsKt.addAll(annotations2, arrayList2);
        FirDefaultPropertyGetter firDefaultPropertyGetter = new FirDefaultPropertyGetter(ktSourceElementFakeElement$default2, moduleData, FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), this.modifiers.getVisibility(), firPropertyBuilder2.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null);
        ConversionUtilsKt.initContainingClassAttr(firDefaultPropertyGetter, context);
        firDefaultPropertyGetter.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(this.modifiers.getAnnotations(), AnnotationUseSiteTarget.PROPERTY_GETTER));
        firPropertyBuilder2.setGetter(firDefaultPropertyGetter);
        if (firPropertyBuilder2.isVar()) {
            FirDefaultPropertySetter firDefaultPropertySetter2 = new FirDefaultPropertySetter(ktSourceElementFakeElement$default2, moduleData, FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), this.modifiers.getVisibility(), firPropertyBuilder2.getSymbol(), null, null, null, ConversionUtilsKt.filterUseSiteTarget(this.modifiers.getAnnotations(), AnnotationUseSiteTarget.SETTER_PARAMETER), null, 1472, null);
            ConversionUtilsKt.initContainingClassAttr(firDefaultPropertySetter2, context);
            firDefaultPropertySetter2.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(this.modifiers.getAnnotations(), AnnotationUseSiteTarget.PROPERTY_SETTER));
            firDefaultPropertySetter = firDefaultPropertySetter2;
            firPropertyBuilder = firPropertyBuilder2;
        } else {
            firPropertyBuilder = firPropertyBuilder2;
            firDefaultPropertySetter = null;
        }
        firPropertyBuilder.setSetter(firDefaultPropertySetter);
        FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
        if (getFirValueParameter().getIsVararg()) {
            DeclarationAttributesKt.setFromVararg(firPropertyMo6809build, true);
        }
        ClassMembersKt.setCorrespondingProperty(getFirValueParameter(), firPropertyMo6809build);
        DeclarationAttributesKt.setFromPrimaryConstructor(firPropertyMo6809build, true);
        return firPropertyMo6809build;
    }
}
