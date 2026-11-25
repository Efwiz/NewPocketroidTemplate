package org.jetbrains.kotlin.fir.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.serialization.FirElementSerializer;
import org.jetbrains.kotlin.fir.serialization.TypeApproximatorForMetadataSerializer;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: FirMetadataSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u001aB\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b\u001aL\u0010\u001c\u001a\u00020\u00112\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00172\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b\u001a*\u0010\"\u001a\u00020#*\u00020#2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020(H\u0000\u001a\u001a\u0010)\u001a\u00020**\u00020+2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002\u001a\u001c\u0010-\u001a\u00020.*\u00020.2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010/\u001a\u000200H\u0002\u001a\u0014\u00101\u001a\u000202*\u0002032\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0014\u00104\u001a\u000205*\u0002052\u0006\u0010\n\u001a\u00020\u000bH\u0000¨\u00066"}, d2 = {"makeElementSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "serializerExtension", "Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmSerializerExtension;", "approximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "parent", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "makeFirMetadataSerializerForIrClass", "Lorg/jetbrains/kotlin/fir/backend/jvm/FirMetadataSerializer;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "serializationBindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "irActualizedResult", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "makeLocalFirMetadataSerializerForMetadataSource", "globalSerializationBindings", "targetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "approximated", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeParameterSet", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "toSuper", "", "collectTypeParameters", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "c", "copyToFreeAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "newPropertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "copyToFreeAnonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "copyToFreeProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "jvm-backend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirMetadataSerializerKt {
    public static final FirMetadataSerializer makeFirMetadataSerializerForIrClass(FirSession session, JvmBackendContext context, IrClass irClass, JvmSerializationBindings serializationBindings, Fir2IrComponents components, MetadataSerializer metadataSerializer, IrActualizedResult irActualizedResult) {
        List listEmptyList;
        FirProperty fir;
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(serializationBindings, "serializationBindings");
        Intrinsics.checkNotNullParameter(components, "components");
        TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer = new TypeApproximatorForMetadataSerializer(session);
        List<IrLocalDelegatedPropertySymbol> list = context.getLocalDelegatedProperties().get(irClass.getAttributeOwnerId());
        if (list == null) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                MetadataSource metadata = ((IrLocalDelegatedPropertySymbol) it.next()).getOwner().getMetadata();
                FirProperty firPropertyCopyToFreeProperty = null;
                FirMetadataSource.Property property = metadata instanceof FirMetadataSource.Property ? (FirMetadataSource.Property) metadata : null;
                if (property != null && (fir = property.getFir()) != null) {
                    firPropertyCopyToFreeProperty = copyToFreeProperty(fir, typeApproximatorForMetadataSerializer);
                }
                if (firPropertyCopyToFreeProperty != null) {
                    arrayList.add(firPropertyCopyToFreeProperty);
                }
            }
            listEmptyList = arrayList;
        }
        TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer2 = typeApproximatorForMetadataSerializer;
        return new FirMetadataSerializer(context.getState().getGlobalSerializationBindings(), serializationBindings, typeApproximatorForMetadataSerializer2, makeElementSerializer(irClass.getMetadata(), components.getSession(), components.getScopeSession(), new FirJvmSerializerExtension(session, serializationBindings, context.getState(), irClass.getMetadata(), listEmptyList, typeApproximatorForMetadataSerializer2, context.getDefaultTypeMapper(), components), typeApproximatorForMetadataSerializer2, metadataSerializer, CommonConfigurationKeysKt.getLanguageVersionSettings(context.getState().getConfiguration())), irActualizedResult);
    }

    public static final FirMetadataSerializer makeLocalFirMetadataSerializerForMetadataSource(MetadataSource metadataSource, FirSession session, ScopeSession scopeSession, JvmSerializationBindings globalSerializationBindings, MetadataSerializer metadataSerializer, TargetId targetId, CompilerConfiguration configuration, IrActualizedResult irActualizedResult) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(globalSerializationBindings, "globalSerializationBindings");
        Intrinsics.checkNotNullParameter(targetId, "targetId");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        JvmSerializationBindings jvmSerializationBindings = new JvmSerializationBindings();
        TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer = new TypeApproximatorForMetadataSerializer(session);
        FirMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1 firMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1 = new FirMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1(session);
        List listEmptyList = CollectionsKt.emptyList();
        TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer2 = typeApproximatorForMetadataSerializer;
        boolean z = configuration.getBoolean(JVMConfigurationKeys.USE_TYPE_TABLE);
        String name2 = targetId.getName();
        ClassBuilderMode FULL = ClassBuilderMode.FULL;
        Intrinsics.checkNotNullExpressionValue(FULL, "FULL");
        return new FirMetadataSerializer(globalSerializationBindings, jvmSerializationBindings, typeApproximatorForMetadataSerializer2, makeElementSerializer(metadataSource, session, scopeSession, new FirJvmSerializerExtension(session, jvmSerializationBindings, metadataSource, listEmptyList, typeApproximatorForMetadataSerializer2, scopeSession, globalSerializationBindings, z, name2, FULL, configuration.getBoolean(JVMConfigurationKeys.DISABLE_PARAM_ASSERTIONS), FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0 && !configuration.getBoolean(JVMConfigurationKeys.NO_UNIFIED_NULL_CHECKS), MetadataHelpersKt.metadataVersion(configuration, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).getLanguageVersion()), (JvmDefaultMode) FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).getFlag(JvmAnalysisFlags.getJvmDefaultMode()), firMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1, null, null), typeApproximatorForMetadataSerializer2, metadataSerializer, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration)), irActualizedResult);
    }

    public static final FirElementSerializer makeElementSerializer(MetadataSource metadataSource, FirSession session, ScopeSession scopeSession, FirJvmSerializerExtension serializerExtension, AbstractTypeApproximator approximator, MetadataSerializer metadataSerializer, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(serializerExtension, "serializerExtension");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (metadataSource instanceof FirMetadataSource.Class) {
            FirElementSerializer.Companion companion = FirElementSerializer.INSTANCE;
            FirClass fir = ((FirMetadataSource.Class) metadataSource).getFir();
            FirJvmSerializerExtension firJvmSerializerExtension = serializerExtension;
            FirMetadataSerializer firMetadataSerializer = metadataSerializer instanceof FirMetadataSerializer ? (FirMetadataSerializer) metadataSerializer : null;
            return companion.create(session, scopeSession, fir, firJvmSerializerExtension, firMetadataSerializer != null ? firMetadataSerializer.getSerializer() : null, approximator, languageVersionSettings);
        }
        if (metadataSource instanceof FirMetadataSource.File) {
            return FirElementSerializer.INSTANCE.createTopLevel(session, scopeSession, serializerExtension, approximator, languageVersionSettings);
        }
        if (metadataSource instanceof FirMetadataSource.Function) {
            return FirElementSerializer.INSTANCE.createForLambda(session, scopeSession, serializerExtension, approximator, languageVersionSettings);
        }
        return null;
    }

    public static final FirTypeRef approximated(FirTypeRef firTypeRef, AbstractTypeApproximator approximator, Collection<FirTypeParameter> typeParameterSet, boolean z) {
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        Intrinsics.checkNotNullParameter(typeParameterSet, "typeParameterSet");
        if (z) {
            kotlinTypeMarkerApproximateToSubType = approximator.approximateToSuperType(FirTypeUtilsKt.getConeType(firTypeRef), TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
        } else {
            kotlinTypeMarkerApproximateToSubType = approximator.approximateToSubType(FirTypeUtilsKt.getConeType(firTypeRef), TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
        }
        FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firTypeRef, kotlinTypeMarkerApproximateToSubType instanceof ConeKotlinType ? (ConeKotlinType) kotlinTypeMarkerApproximateToSubType : null, null, 2, null);
        collectTypeParameters(FirTypeUtilsKt.getConeType(firResolvedTypeRefWithReplacedConeType$default), typeParameterSet);
        return firResolvedTypeRefWithReplacedConeType$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void collectTypeParameters(ConeKotlinType coneKotlinType, Collection<FirTypeParameter> collection) {
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            collectTypeParameters(coneFlexibleType.getLowerBound(), collection);
            collectTypeParameters(coneFlexibleType.getUpperBound(), collection);
        } else {
            if (coneKotlinType instanceof ConeClassLikeType) {
                for (ConeTypeProjection coneTypeProjection : coneKotlinType.getType().getTypeArguments()) {
                    if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
                        collectTypeParameters(((ConeKotlinTypeProjection) coneTypeProjection).getType(), collection);
                    }
                }
                return;
            }
            if (coneKotlinType instanceof ConeTypeParameterType) {
                collection.add(((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getFir());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirAnonymousFunction copyToFreeAnonymousFunction(FirFunction firFunction, AbstractTypeApproximator abstractTypeApproximator) {
        FirReceiverParameter firReceiverParameterMo6809build;
        FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
        List<FirTypeParameterRef> typeParameters = firFunction.getTypeParameters();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : typeParameters) {
            if (obj instanceof FirTypeParameter) {
                linkedHashSet.add(obj);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        CollectionsKt.addAll(firAnonymousFunctionBuilder.getAnnotations(), firFunction.getAnnotations());
        firAnonymousFunctionBuilder.setModuleData(firFunction.getModuleData());
        firAnonymousFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firAnonymousFunctionBuilder.setSymbol(new FirAnonymousFunctionSymbol());
        LinkedHashSet linkedHashSet3 = linkedHashSet2;
        firAnonymousFunctionBuilder.setReturnTypeRef(approximated(firFunction.getReturnTypeRef(), abstractTypeApproximator, linkedHashSet3, true));
        FirReceiverParameter receiverParameter = firFunction.getReceiverParameter();
        if (receiverParameter != null) {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setSource(receiverParameter.getSource());
            firReceiverParameterBuilder.setTypeRef(receiverParameter.getTypeRef());
            firReceiverParameterBuilder.getAnnotations().addAll(receiverParameter.getAnnotations());
            firReceiverParameterBuilder.setTypeRef(approximated(receiverParameter.getTypeRef(), abstractTypeApproximator, linkedHashSet3, false));
            firReceiverParameterMo6809build = firReceiverParameterBuilder.mo6809build();
        } else {
            firReceiverParameterMo6809build = null;
        }
        firAnonymousFunctionBuilder.setReceiverParameter(firReceiverParameterMo6809build);
        boolean z = firFunction instanceof FirAnonymousFunction;
        FirAnonymousFunction firAnonymousFunction = z ? (FirAnonymousFunction) firFunction : null;
        firAnonymousFunctionBuilder.setLambda(firAnonymousFunction != null && firAnonymousFunction.getIsLambda());
        FirAnonymousFunction firAnonymousFunction2 = z ? (FirAnonymousFunction) firFunction : null;
        firAnonymousFunctionBuilder.setHasExplicitParameterList(firAnonymousFunction2 != null && firAnonymousFunction2.getHasExplicitParameterList());
        List<FirValueParameter> valueParameters = firAnonymousFunctionBuilder.getValueParameters();
        List<FirValueParameter> valueParameters2 = firFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
        for (FirValueParameter firValueParameter : valueParameters2) {
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setSource(firValueParameter.getSource());
            firValueParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firValueParameter));
            firValueParameterBuilder.setModuleData(firValueParameter.getModuleData());
            firValueParameterBuilder.setOrigin(firValueParameter.getOrigin());
            firValueParameterBuilder.setAttributes(firValueParameter.getAttributes().copy());
            firValueParameterBuilder.setReturnTypeRef(firValueParameter.getReturnTypeRef());
            firValueParameterBuilder.setDeprecationsProvider(firValueParameter.getDeprecationsProvider());
            firValueParameterBuilder.setContainerSource(firValueParameter.getContainerSource());
            firValueParameterBuilder.setDispatchReceiverType(firValueParameter.getDispatchReceiverType());
            firValueParameterBuilder.getContextReceivers().addAll(firValueParameter.getContextReceivers());
            firValueParameterBuilder.setName(firValueParameter.getName());
            firValueParameterBuilder.setBackingField(firValueParameter.getBackingField());
            firValueParameterBuilder.getAnnotations().addAll(firValueParameter.getAnnotations());
            firValueParameterBuilder.setSymbol(firValueParameter.getSymbol());
            firValueParameterBuilder.setDefaultValue(firValueParameter.getDefaultValue());
            firValueParameterBuilder.setContainingFunctionSymbol(firValueParameter.getContainingFunctionSymbol());
            firValueParameterBuilder.setCrossinline(firValueParameter.getIsCrossinline());
            firValueParameterBuilder.setNoinline(firValueParameter.getIsNoinline());
            firValueParameterBuilder.setVararg(firValueParameter.getIsVararg());
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameter.getName()));
            firValueParameterBuilder.setReturnTypeRef(approximated(firValueParameter.getReturnTypeRef(), abstractTypeApproximator, linkedHashSet3, false));
            arrayList.add(firValueParameterBuilder.mo6809build());
        }
        valueParameters.addAll(arrayList);
        CollectionsKt.addAll(firAnonymousFunctionBuilder.getTypeParameters(), linkedHashSet2);
        firAnonymousFunctionBuilder.setStatus(firFunction.getStatus());
        return firAnonymousFunctionBuilder.mo6809build();
    }

    private static final FirPropertyAccessor copyToFreeAccessor(FirPropertyAccessor firPropertyAccessor, AbstractTypeApproximator abstractTypeApproximator, FirPropertySymbol firPropertySymbol) {
        FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
        Set mutableSet = CollectionsKt.toMutableSet(firPropertyAccessor.getTypeParameters());
        firPropertyAccessorBuilder.setModuleData(firPropertyAccessor.getModuleData());
        firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        Set set = mutableSet;
        firPropertyAccessorBuilder.setReturnTypeRef(approximated(firPropertyAccessor.getReturnTypeRef(), abstractTypeApproximator, set, true));
        firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
        firPropertyAccessorBuilder.setPropertySymbol(firPropertySymbol);
        firPropertyAccessorBuilder.setGetter(firPropertyAccessor.getIsGetter());
        firPropertyAccessorBuilder.setStatus(firPropertyAccessor.getStatus());
        List<FirValueParameter> valueParameters = firPropertyAccessor.getValueParameters();
        List<FirValueParameter> valueParameters2 = firPropertyAccessorBuilder.getValueParameters();
        for (FirValueParameter firValueParameter : valueParameters) {
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setSource(firValueParameter.getSource());
            firValueParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firValueParameter));
            firValueParameterBuilder.setModuleData(firValueParameter.getModuleData());
            firValueParameterBuilder.setOrigin(firValueParameter.getOrigin());
            firValueParameterBuilder.setAttributes(firValueParameter.getAttributes().copy());
            firValueParameterBuilder.setReturnTypeRef(firValueParameter.getReturnTypeRef());
            firValueParameterBuilder.setDeprecationsProvider(firValueParameter.getDeprecationsProvider());
            firValueParameterBuilder.setContainerSource(firValueParameter.getContainerSource());
            firValueParameterBuilder.setDispatchReceiverType(firValueParameter.getDispatchReceiverType());
            firValueParameterBuilder.getContextReceivers().addAll(firValueParameter.getContextReceivers());
            firValueParameterBuilder.setName(firValueParameter.getName());
            firValueParameterBuilder.setBackingField(firValueParameter.getBackingField());
            firValueParameterBuilder.getAnnotations().addAll(firValueParameter.getAnnotations());
            firValueParameterBuilder.setSymbol(firValueParameter.getSymbol());
            firValueParameterBuilder.setDefaultValue(firValueParameter.getDefaultValue());
            firValueParameterBuilder.setContainingFunctionSymbol(firValueParameter.getContainingFunctionSymbol());
            firValueParameterBuilder.setCrossinline(firValueParameter.getIsCrossinline());
            firValueParameterBuilder.setNoinline(firValueParameter.getIsNoinline());
            firValueParameterBuilder.setVararg(firValueParameter.getIsVararg());
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameter.getName()));
            firValueParameterBuilder.setReturnTypeRef(approximated(firValueParameter.getReturnTypeRef(), abstractTypeApproximator, set, false));
            valueParameters2.add(firValueParameterBuilder.mo6809build());
        }
        CollectionsKt.addAll(firPropertyAccessorBuilder.getAnnotations(), firPropertyAccessor.getAnnotations());
        CollectionsKt.addAll(firPropertyAccessorBuilder.getTypeParameters(), mutableSet);
        return firPropertyAccessorBuilder.mo6809build();
    }

    public static final FirProperty copyToFreeProperty(FirProperty firProperty, AbstractTypeApproximator approximator) {
        FirReceiverParameter firReceiverParameterMo6809build;
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Intrinsics.checkNotNullParameter(approximator, "approximator");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        Set mutableSet = CollectionsKt.toMutableSet(firProperty.getTypeParameters());
        firPropertyBuilder.setModuleData(firProperty.getModuleData());
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        FirPropertySymbol firPropertySymbol = new FirPropertySymbol(firProperty.getSymbol().getCallableId());
        firPropertyBuilder.setSymbol(firPropertySymbol);
        Set set = mutableSet;
        firPropertyBuilder.setReturnTypeRef(approximated(firProperty.getReturnTypeRef(), approximator, set, true));
        FirReceiverParameter receiverParameter = firProperty.getReceiverParameter();
        if (receiverParameter != null) {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setSource(receiverParameter.getSource());
            firReceiverParameterBuilder.setTypeRef(receiverParameter.getTypeRef());
            firReceiverParameterBuilder.getAnnotations().addAll(receiverParameter.getAnnotations());
            firReceiverParameterBuilder.setTypeRef(approximated(receiverParameter.getTypeRef(), approximator, set, false));
            firReceiverParameterMo6809build = firReceiverParameterBuilder.mo6809build();
        } else {
            firReceiverParameterMo6809build = null;
        }
        firPropertyBuilder.setReceiverParameter(firReceiverParameterMo6809build);
        firPropertyBuilder.setName(firProperty.getName());
        firPropertyBuilder.setInitializer(firProperty.getInitializer());
        firPropertyBuilder.setDelegate(firProperty.getDelegate());
        FirDelegateFieldSymbol delegateFieldSymbol = firProperty.getDelegateFieldSymbol();
        firPropertyBuilder.setDelegateFieldSymbol(delegateFieldSymbol != null ? new FirDelegateFieldSymbol(delegateFieldSymbol.getCallableId()) : null);
        FirPropertyAccessor getter = firProperty.getGetter();
        firPropertyBuilder.setGetter(getter != null ? copyToFreeAccessor(getter, approximator, firPropertySymbol) : null);
        FirPropertyAccessor setter = firProperty.getSetter();
        firPropertyBuilder.setSetter(setter != null ? copyToFreeAccessor(setter, approximator, firPropertySymbol) : null);
        firPropertyBuilder.setVar(firProperty.getIsVar());
        firPropertyBuilder.setLocal(firProperty.getIsLocal());
        firPropertyBuilder.setStatus(firProperty.getStatus());
        firPropertyBuilder.setDispatchReceiverType(firProperty.getDispatchReceiverType());
        firPropertyBuilder.setAttributes(firProperty.getAttributes().copy());
        CollectionsKt.addAll(firPropertyBuilder.getAnnotations(), firProperty.getAnnotations());
        CollectionsKt.addAll(firPropertyBuilder.getTypeParameters(), mutableSet);
        FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
        FirDelegateFieldSymbol delegateFieldSymbol2 = firPropertyMo6809build.getDelegateFieldSymbol();
        if (delegateFieldSymbol2 != null) {
            delegateFieldSymbol2.bind(firPropertyMo6809build);
        }
        return firPropertyMo6809build;
    }
}
