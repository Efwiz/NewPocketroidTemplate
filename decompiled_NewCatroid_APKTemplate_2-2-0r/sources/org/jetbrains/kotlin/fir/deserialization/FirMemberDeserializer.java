package org.jetbrains.kotlin.fir.deserialization;

import androidx.core.view.PointerIconCompat;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVersionRequirementsTableKeyKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructedClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPrimaryConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeAliasBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirExpressionStubBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;

/* JADX INFO: compiled from: FirMemberDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\fJ\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0018H\u0002J4\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0010H\u0002J*\u0010\"\u001a\u00020#2\u0006\u0010\u0013\u001a\u00020$2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001dJF\u0010%\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020$2\f\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d2\u0006\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020.H\u0002JR\u0010/\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020$2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d2\u0006\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020.H\u0002J\u001a\u00100\u001a\u0002012\u0006\u0010\u0013\u001a\u0002022\n\b\u0002\u00103\u001a\u0004\u0018\u000104JL\u00105\u001a\b\u0012\u0004\u0012\u0002060\b2\f\u00105\u001a\b\u0012\u0004\u0012\u0002070\b2\n\u00108\u001a\u0006\u0012\u0002\b\u0003092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010>\u001a\u00020?H\u0002J\u0014\u0010@\u001a\u00020)*\u00020\u00182\u0006\u0010A\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirMemberDeserializer;", "", "c", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "(Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)V", "contractDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirContractDeserializer;", "createContextReceiversForClass", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "createContextReceiversForClass$fir_deserialization", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", OverlayablePolicy.NAME_flags, "", "loadConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "classBuilder", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "loadContextReceiver", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "deserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "loadOldFlags", "oldFlags", "loadProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "loadPropertyGetter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "defaultAccessorFlags", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "local", "propertyModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "loadPropertySetter", "loadTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "preComputedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "callableProto", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "callableKind", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer$CallableKind;", "addDefaultValue", "", "toTypeRef", "context", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMemberDeserializer {
    private final FirDeserializationContext c;
    private final FirContractDeserializer contractDeserializer;

    private final int loadOldFlags(int oldFlags) {
        return (oldFlags & 63) + ((oldFlags >> 8) << 6);
    }

    public FirMemberDeserializer(FirDeserializationContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.contractDeserializer = new FirContractDeserializer(c);
    }

    public static /* synthetic */ FirTypeAlias loadTypeAlias$default(FirMemberDeserializer firMemberDeserializer, ProtoBuf.TypeAlias typeAlias, FirTypeAliasSymbol firTypeAliasSymbol, int i, Object obj) {
        if ((i & 2) != 0) {
            firTypeAliasSymbol = null;
        }
        return firMemberDeserializer.loadTypeAlias(typeAlias, firTypeAliasSymbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirTypeAlias loadTypeAlias(ProtoBuf.TypeAlias proto, FirTypeAliasSymbol preComputedSymbol) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        int flags = proto.getFlags();
        Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        FirTypeAliasSymbol firTypeAliasSymbol = preComputedSymbol == null ? new FirTypeAliasSymbol(new ClassId(this.c.getPackageFqName(), name2)) : preComputedSymbol;
        FirDeserializationContext firDeserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        FirDeserializationContext firDeserializationContextChildContext = firDeserializationContext.childContext(typeParameterList, firTypeAliasSymbol, (PointerIconCompat.TYPE_GRAB & 4) != 0 ? firDeserializationContext.nameResolver : null, (PointerIconCompat.TYPE_GRAB & 8) != 0 ? firDeserializationContext.typeTable : null, (PointerIconCompat.TYPE_GRAB & 16) != 0 ? firDeserializationContext.relativeClassName : null, (PointerIconCompat.TYPE_GRAB & 32) != 0 ? firDeserializationContext.containerSource : null, (PointerIconCompat.TYPE_GRAB & 64) != 0 ? firDeserializationContext.outerClassSymbol : null, (PointerIconCompat.TYPE_GRAB & 128) != 0 ? firDeserializationContext.annotationDeserializer : null, (PointerIconCompat.TYPE_GRAB & 256) != 0 ? firDeserializationContext.constDeserializer : null, (PointerIconCompat.TYPE_GRAB & 512) != 0);
        List<VersionRequirement> listCreate = FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c);
        FirTypeAliasBuilder firTypeAliasBuilder = new FirTypeAliasBuilder();
        firTypeAliasBuilder.setModuleData(this.c.getModuleData());
        firTypeAliasBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firTypeAliasBuilder.setName(name2);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, Modality.FINAL, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (ConeClassLikeLookupTag) null, false, false, 6, (Object) null));
        Boolean bool = Flags.IS_EXPECT_CLASS.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        firResolvedDeclarationStatusImpl.setExpect(bool.booleanValue());
        firResolvedDeclarationStatusImpl.setActual(false);
        firTypeAliasBuilder.setStatus(firResolvedDeclarationStatusImpl);
        CollectionsKt.addAll(firTypeAliasBuilder.getAnnotations(), this.c.getAnnotationDeserializer().loadTypeAliasAnnotations(proto, firDeserializationContextChildContext.getNameResolver()));
        firTypeAliasBuilder.setSymbol(firTypeAliasSymbol);
        firTypeAliasBuilder.setExpandedTypeRef(toTypeRef(ProtoTypeTableUtilKt.underlyingType(proto, this.c.getTypeTable()), firDeserializationContextChildContext));
        firTypeAliasBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameter> typeParameters = firTypeAliasBuilder.getTypeParameters();
        List<FirTypeParameterSymbol> ownTypeParameters = firDeserializationContextChildContext.getTypeDeserializer().getOwnTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(ownTypeParameters, 10));
        Iterator<T> it = ownTypeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add((FirTypeParameter) ((FirTypeParameterSymbol) it.next()).getFir());
        }
        CollectionsKt.addAll(typeParameters, arrayList);
        firTypeAliasBuilder.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAnnotations(firTypeAliasBuilder.getAnnotations(), this.c.getSession(), false, listCreate));
        FirTypeAlias firTypeAliasMo6809build = firTypeAliasBuilder.mo6809build();
        FirVersionRequirementsTableKeyKt.setVersionRequirements(firTypeAliasMo6809build, listCreate);
        DeclarationAttributesKt.setSourceElement(firTypeAliasMo6809build, this.c.getContainerSource());
        return firTypeAliasMo6809build;
    }

    private final FirPropertyAccessor loadPropertyGetter(ProtoBuf.Property proto, FirClassSymbol<?> classSymbol, int defaultAccessorFlags, FirTypeRef returnTypeRef, FirPropertySymbol propertySymbol, FirDeserializationContext local, Modality propertyModality) {
        FirDefaultPropertyGetter firDefaultPropertyGetter;
        int getterFlags = proto.hasGetterFlags() ? proto.getGetterFlags() : defaultAccessorFlags;
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(getterFlags));
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(getterFlags));
        EffectiveVisibility effectiveVisibility$default = EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) classSymbol, false, false, 6, (Object) null);
        Boolean bool = Flags.IS_NOT_DEFAULT.get(getterFlags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (!bool.booleanValue()) {
            firDefaultPropertyGetter = new FirDefaultPropertyGetter(null, this.c.getModuleData(), FirDeclarationOrigin.Library.INSTANCE, returnTypeRef, visibility, propertySymbol, propertyModality, effectiveVisibility$default, null, FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES(), 256, null);
        } else {
            FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
            firPropertyAccessorBuilder.setModuleData(this.c.getModuleData());
            firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
            firPropertyAccessorBuilder.setReturnTypeRef(returnTypeRef);
            firPropertyAccessorBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firPropertyAccessorBuilder.setGetter(true);
            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, modality, effectiveVisibility$default);
            Boolean bool2 = Flags.IS_INLINE_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            firResolvedDeclarationStatusImpl.setInline(bool2.booleanValue());
            Boolean bool3 = Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
            firResolvedDeclarationStatusImpl.setExternal(bool3.booleanValue());
            firPropertyAccessorBuilder.setStatus(firResolvedDeclarationStatusImpl);
            firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
            firPropertyAccessorBuilder.setDispatchReceiverType(this.c.getDispatchReceiver());
            firPropertyAccessorBuilder.setPropertySymbol(propertySymbol);
            firDefaultPropertyGetter = firPropertyAccessorBuilder.mo6809build();
            FirVersionRequirementsTableKeyKt.setVersionRequirements(firDefaultPropertyGetter, FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c));
        }
        firDefaultPropertyGetter.replaceAnnotations(this.c.getAnnotationDeserializer().loadPropertyGetterAnnotations(this.c.getContainerSource(), proto, local.getNameResolver(), local.getTypeTable(), getterFlags));
        FirPropertyAccessor firPropertyAccessor = firDefaultPropertyGetter;
        ConeClassLikeType dispatchReceiver = this.c.getDispatchReceiver();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firPropertyAccessor, dispatchReceiver != null ? dispatchReceiver.getLookupTag() : null);
        return firDefaultPropertyGetter;
    }

    private final FirPropertyAccessor loadPropertySetter(ProtoBuf.Property proto, ProtoBuf.Class classProto, FirClassSymbol<?> classSymbol, int defaultAccessorFlags, FirTypeRef returnTypeRef, FirPropertySymbol propertySymbol, FirDeserializationContext local, Modality propertyModality) {
        FirDefaultPropertySetter firDefaultPropertySetter;
        int setterFlags = proto.hasSetterFlags() ? proto.getSetterFlags() : defaultAccessorFlags;
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(setterFlags));
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(setterFlags));
        EffectiveVisibility effectiveVisibility$default = EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) classSymbol, false, false, 6, (Object) null);
        Boolean bool = Flags.IS_NOT_DEFAULT.get(setterFlags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (!bool.booleanValue()) {
            firDefaultPropertySetter = new FirDefaultPropertySetter(null, this.c.getModuleData(), FirDeclarationOrigin.Library.INSTANCE, returnTypeRef, visibility, propertySymbol, propertyModality, effectiveVisibility$default, null, null, FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES(), 768, null);
        } else {
            FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
            firPropertyAccessorBuilder.setModuleData(this.c.getModuleData());
            firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
            firPropertyAccessorBuilder.setReturnTypeRef(new FirImplicitUnitTypeRef(firPropertyAccessorBuilder.getSource()));
            firPropertyAccessorBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firPropertyAccessorBuilder.setGetter(false);
            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, modality, effectiveVisibility$default);
            Boolean bool2 = Flags.IS_INLINE_ACCESSOR.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            firResolvedDeclarationStatusImpl.setInline(bool2.booleanValue());
            Boolean bool3 = Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
            firResolvedDeclarationStatusImpl.setExternal(bool3.booleanValue());
            firPropertyAccessorBuilder.setStatus(firResolvedDeclarationStatusImpl);
            firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
            firPropertyAccessorBuilder.setDispatchReceiverType(this.c.getDispatchReceiver());
            CollectionsKt.addAll(firPropertyAccessorBuilder.getValueParameters(), valueParameters$default(local.getMemberDeserializer(), CollectionsKt.listOf(proto.getSetterValueParameter()), firPropertyAccessorBuilder.getSymbol(), proto, AbstractAnnotationDeserializer.CallableKind.PROPERTY_SETTER, classProto, false, 32, null));
            firPropertyAccessorBuilder.setPropertySymbol(propertySymbol);
            firDefaultPropertySetter = firPropertyAccessorBuilder.mo6809build();
            FirVersionRequirementsTableKeyKt.setVersionRequirements(firDefaultPropertySetter, FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c));
        }
        firDefaultPropertySetter.replaceAnnotations(this.c.getAnnotationDeserializer().loadPropertySetterAnnotations(this.c.getContainerSource(), proto, local.getNameResolver(), local.getTypeTable(), setterFlags));
        FirPropertyAccessor firPropertyAccessor = firDefaultPropertySetter;
        ConeClassLikeType dispatchReceiver = this.c.getDispatchReceiver();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firPropertyAccessor, dispatchReceiver != null ? dispatchReceiver.getLookupTag() : null);
        return firDefaultPropertySetter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirProperty loadProperty$default(FirMemberDeserializer firMemberDeserializer, ProtoBuf.Property property, ProtoBuf.Class r3, FirClassSymbol firClassSymbol, int i, Object obj) {
        if ((i & 2) != 0) {
            r3 = null;
        }
        if ((i & 4) != 0) {
            firClassSymbol = null;
        }
        return firMemberDeserializer.loadProperty(property, r3, firClassSymbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirProperty loadProperty(ProtoBuf.Property proto, ProtoBuf.Class classProto, FirClassSymbol<?> classSymbol) {
        List<FirAnnotation> listEmptyList;
        FirReceiverParameter firReceiverParameterMo6809build;
        FirPropertyBuilder firPropertyBuilder;
        ProtoBuf.Property property;
        FirPropertyBuilder firPropertyBuilder2;
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        FirPropertySymbol firPropertySymbol = new FirPropertySymbol(new CallableId(this.c.getPackageFqName(), this.c.getRelativeClassName(), name2, null, 8, null));
        FirDeserializationContext firDeserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        FirDeserializationContext firDeserializationContextChildContext = firDeserializationContext.childContext(typeParameterList, firPropertySymbol, (PointerIconCompat.TYPE_GRAB & 4) != 0 ? firDeserializationContext.nameResolver : null, (PointerIconCompat.TYPE_GRAB & 8) != 0 ? firDeserializationContext.typeTable : null, (PointerIconCompat.TYPE_GRAB & 16) != 0 ? firDeserializationContext.relativeClassName : null, (PointerIconCompat.TYPE_GRAB & 32) != 0 ? firDeserializationContext.containerSource : null, (PointerIconCompat.TYPE_GRAB & 64) != 0 ? firDeserializationContext.outerClassSymbol : null, (PointerIconCompat.TYPE_GRAB & 128) != 0 ? firDeserializationContext.annotationDeserializer : null, (PointerIconCompat.TYPE_GRAB & 256) != 0 ? firDeserializationContext.constDeserializer : null, (PointerIconCompat.TYPE_GRAB & 512) != 0);
        Boolean bool = Flags.HAS_ANNOTATIONS.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        int accessorFlags = Flags.getAccessorFlags(bool.booleanValue(), Flags.VISIBILITY.get(flags), Flags.MODALITY.get(flags), false, false, false);
        FirTypeRef typeRef2 = toTypeRef(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()), firDeserializationContextChildContext);
        Boolean bool2 = Flags.HAS_GETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
        boolean zBooleanValue = bool2.booleanValue();
        if (zBooleanValue && ProtoTypeTableUtilKt.hasReceiver(proto)) {
            listEmptyList = this.c.getAnnotationDeserializer().loadExtensionReceiverParameterAnnotations(this.c.getContainerSource(), proto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable(), AbstractAnnotationDeserializer.CallableKind.PROPERTY_GETTER);
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        Boolean bool3 = Flags.IS_VAR.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
        boolean zBooleanValue2 = bool3.booleanValue();
        List<VersionRequirement> listCreate = FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c);
        FirPropertyBuilder firPropertyBuilder3 = new FirPropertyBuilder();
        firPropertyBuilder3.setModuleData(this.c.getModuleData());
        firPropertyBuilder3.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firPropertyBuilder3.setReturnTypeRef(typeRef2);
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        FirExpression firExpressionLoadAnnotationPropertyDefaultValue = null;
        if (typeReceiverType == null || (typeRef = toTypeRef(typeReceiverType, firDeserializationContextChildContext)) == null) {
            firReceiverParameterMo6809build = null;
        } else {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setTypeRef(typeRef);
            CollectionsKt.addAll(firReceiverParameterBuilder.getAnnotations(), listEmptyList);
            firReceiverParameterMo6809build = firReceiverParameterBuilder.mo6809build();
        }
        firPropertyBuilder3.setReceiverParameter(firReceiverParameterMo6809build);
        firPropertyBuilder3.setName(name2);
        firPropertyBuilder3.setVar(zBooleanValue2);
        firPropertyBuilder3.setSymbol(firPropertySymbol);
        firPropertyBuilder3.setDispatchReceiverType(this.c.getDispatchReceiver());
        firPropertyBuilder3.setLocal(false);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, modality, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) classSymbol, false, false, 6, (Object) null));
        Boolean bool4 = Flags.IS_EXPECT_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
        firResolvedDeclarationStatusImpl.setExpect(bool4.booleanValue());
        firResolvedDeclarationStatusImpl.setActual(false);
        firResolvedDeclarationStatusImpl.setOverride(false);
        Boolean bool5 = Flags.IS_CONST.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool5, "get(...)");
        firResolvedDeclarationStatusImpl.setConst(bool5.booleanValue());
        Boolean bool6 = Flags.IS_LATEINIT.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool6, "get(...)");
        firResolvedDeclarationStatusImpl.setLateInit(bool6.booleanValue());
        Boolean bool7 = Flags.IS_EXTERNAL_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool7, "get(...)");
        firResolvedDeclarationStatusImpl.setExternal(bool7.booleanValue());
        firPropertyBuilder3.setStatus(firResolvedDeclarationStatusImpl);
        firPropertyBuilder3.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameter> typeParameters = firPropertyBuilder3.getTypeParameters();
        List<FirTypeParameterSymbol> ownTypeParameters = firDeserializationContextChildContext.getTypeDeserializer().getOwnTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(ownTypeParameters, 10));
        Iterator<T> it = ownTypeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add((FirTypeParameter) ((FirTypeParameterSymbol) it.next()).getFir());
        }
        CollectionsKt.addAll(typeParameters, arrayList);
        CollectionsKt.addAll(firPropertyBuilder3.getAnnotations(), this.c.getAnnotationDeserializer().loadPropertyAnnotations(this.c.getContainerSource(), proto, classProto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable()));
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = arrayList2;
        CollectionsKt.addAll(arrayList3, this.c.getAnnotationDeserializer().loadPropertyBackingFieldAnnotations(this.c.getContainerSource(), proto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable()));
        CollectionsKt.addAll(arrayList3, this.c.getAnnotationDeserializer().loadPropertyDelegatedFieldAnnotations(this.c.getContainerSource(), proto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable()));
        firPropertyBuilder3.setBackingField(new FirDefaultPropertyBackingField(this.c.getModuleData(), FirDeclarationOrigin.Library.INSTANCE, null, arrayList2, typeRef2, zBooleanValue2, firPropertySymbol, firPropertyBuilder3.getStatus(), null, 256, null));
        if (zBooleanValue) {
            firPropertyBuilder = firPropertyBuilder3;
            firPropertyBuilder.setGetter(loadPropertyGetter(proto, classSymbol, accessorFlags, typeRef2, firPropertySymbol, firDeserializationContextChildContext, modality));
        } else {
            firPropertyBuilder = firPropertyBuilder3;
        }
        Boolean bool8 = Flags.HAS_SETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool8, "get(...)");
        if (bool8.booleanValue()) {
            firPropertyBuilder.setSetter(loadPropertySetter(proto, classProto, classSymbol, accessorFlags, typeRef2, firPropertySymbol, firDeserializationContextChildContext, modality));
        }
        firPropertyBuilder.setContainerSource(this.c.getContainerSource());
        Boolean bool9 = Flags.HAS_CONSTANT.get(proto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool9, "get(...)");
        if (bool9.booleanValue()) {
            property = proto;
            firPropertyBuilder2 = firPropertyBuilder;
            firExpressionLoadAnnotationPropertyDefaultValue = this.c.getConstDeserializer().loadConstant(property, firPropertySymbol.getCallableId(), this.c.getNameResolver());
        } else {
            property = proto;
            firPropertyBuilder2 = firPropertyBuilder;
            if (classProto != null && Flags.CLASS_KIND.get(classProto.getFlags()) == ProtoBuf.Class.Kind.ANNOTATION_CLASS) {
                firExpressionLoadAnnotationPropertyDefaultValue = this.c.getAnnotationDeserializer().loadAnnotationPropertyDefaultValue(this.c.getContainerSource(), proto, typeRef2, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable());
            }
        }
        firPropertyBuilder2.setInitializer(firExpressionLoadAnnotationPropertyDefaultValue);
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(property, this.c.getTypeTable());
        List<FirContextReceiver> contextReceivers = firPropertyBuilder2.getContextReceivers();
        Iterator<T> it2 = listContextReceiverTypes.iterator();
        while (it2.hasNext()) {
            contextReceivers.add(loadContextReceiver((ProtoBuf.Type) it2.next()));
        }
        FirProperty firPropertyMo6809build = firPropertyBuilder2.mo6809build();
        FirExpression initializer = firPropertyMo6809build.getInitializer();
        if (initializer != null) {
            initializer.replaceTypeRef(typeRef2);
        }
        FirVersionRequirementsTableKeyKt.setVersionRequirements(firPropertyMo6809build, listCreate);
        firPropertyMo6809build.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(firPropertyMo6809build, this.c.getSession()));
        PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firPropertyMo6809build, this.c.getSession());
        FirPropertyAccessor getter = firPropertyMo6809build.getGetter();
        if (getter != null) {
            PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(getter, firPropertyMo6809build.getAnnotations(), firPropertyMo6809build, this.c.getSession());
        }
        FirPropertyAccessor setter = firPropertyMo6809build.getSetter();
        if (setter != null) {
            PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(setter, firPropertyMo6809build.getAnnotations(), firPropertyMo6809build, this.c.getSession());
        }
        return firPropertyMo6809build;
    }

    private final FirContextReceiver loadContextReceiver(ProtoBuf.Type proto) {
        ConeClassifierLookupTag lookupTag;
        FirTypeRef typeRef = toTypeRef(proto, this.c);
        FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(typeRef);
        Name name2 = null;
        ConeLookupTagBasedType coneLookupTagBasedType = coneType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneType : null;
        if (coneLookupTagBasedType != null && (lookupTag = coneLookupTagBasedType.getLookupTag()) != null) {
            name2 = lookupTag.getName();
        }
        firContextReceiverBuilder.setLabelNameFromTypeRef(name2);
        firContextReceiverBuilder.setTypeRef(typeRef);
        return firContextReceiverBuilder.build();
    }

    public final List<FirContextReceiver> createContextReceiversForClass$fir_deserialization(ProtoBuf.Class classProto) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(classProto, this.c.getTypeTable());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listContextReceiverTypes, 10));
        Iterator<T> it = listContextReceiverTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(loadContextReceiver((ProtoBuf.Type) it.next()));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirSimpleFunction loadFunction$default(FirMemberDeserializer firMemberDeserializer, ProtoBuf.Function function, ProtoBuf.Class r3, FirClassSymbol firClassSymbol, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            r3 = null;
        }
        if ((i & 4) != 0) {
            firClassSymbol = null;
        }
        if ((i & 8) != 0) {
            firDeclarationOrigin = FirDeclarationOrigin.Library.INSTANCE;
        }
        return firMemberDeserializer.loadFunction(function, r3, firClassSymbol, firDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirSimpleFunction loadFunction(ProtoBuf.Function proto, ProtoBuf.Class classProto, FirClassSymbol<?> classSymbol, FirDeclarationOrigin deserializationOrigin) {
        List<FirAnnotation> listEmptyList;
        FirReceiverParameter firReceiverParameterMo6809build;
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(deserializationOrigin, "deserializationOrigin");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        if (ProtoTypeTableUtilKt.hasReceiver(proto)) {
            listEmptyList = this.c.getAnnotationDeserializer().loadExtensionReceiverParameterAnnotations(this.c.getContainerSource(), proto, this.c.getNameResolver(), this.c.getTypeTable(), AbstractAnnotationDeserializer.CallableKind.OTHERS);
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        FirNamedFunctionSymbol firNamedFunctionSymbol = new FirNamedFunctionSymbol(new CallableId(this.c.getPackageFqName(), this.c.getRelativeClassName(), name2, null, 8, null));
        FirDeserializationContext firDeserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        FirDeserializationContext firDeserializationContextChildContext = firDeserializationContext.childContext(typeParameterList, firNamedFunctionSymbol, (PointerIconCompat.TYPE_GRAB & 4) != 0 ? firDeserializationContext.nameResolver : null, (PointerIconCompat.TYPE_GRAB & 8) != 0 ? firDeserializationContext.typeTable : null, (PointerIconCompat.TYPE_GRAB & 16) != 0 ? firDeserializationContext.relativeClassName : null, (PointerIconCompat.TYPE_GRAB & 32) != 0 ? firDeserializationContext.containerSource : null, (PointerIconCompat.TYPE_GRAB & 64) != 0 ? firDeserializationContext.outerClassSymbol : null, (PointerIconCompat.TYPE_GRAB & 128) != 0 ? firDeserializationContext.annotationDeserializer : null, (PointerIconCompat.TYPE_GRAB & 256) != 0 ? firDeserializationContext.constDeserializer : null, (PointerIconCompat.TYPE_GRAB & 512) != 0);
        List<VersionRequirement> listCreate = FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c);
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setModuleData(this.c.getModuleData());
        firSimpleFunctionBuilder.setOrigin(deserializationOrigin);
        firSimpleFunctionBuilder.setReturnTypeRef(toTypeRef(ProtoTypeTableUtilKt.returnType(proto, firDeserializationContextChildContext.getTypeTable()), firDeserializationContextChildContext));
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(proto, firDeserializationContextChildContext.getTypeTable());
        if (typeReceiverType == null || (typeRef = toTypeRef(typeReceiverType, firDeserializationContextChildContext)) == null) {
            firReceiverParameterMo6809build = null;
        } else {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setTypeRef(typeRef);
            CollectionsKt.addAll(firReceiverParameterBuilder.getAnnotations(), listEmptyList);
            firReceiverParameterMo6809build = firReceiverParameterBuilder.mo6809build();
        }
        firSimpleFunctionBuilder.setReceiverParameter(firReceiverParameterMo6809build);
        firSimpleFunctionBuilder.setName(name2);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags)), EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) classSymbol, false, false, 6, (Object) null));
        Boolean bool = Flags.IS_EXPECT_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        firResolvedDeclarationStatusImpl.setExpect(bool.booleanValue());
        firResolvedDeclarationStatusImpl.setActual(false);
        firResolvedDeclarationStatusImpl.setOverride(false);
        Boolean bool2 = Flags.IS_OPERATOR.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
        firResolvedDeclarationStatusImpl.setOperator(bool2.booleanValue());
        Boolean bool3 = Flags.IS_INFIX.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
        firResolvedDeclarationStatusImpl.setInfix(bool3.booleanValue());
        Boolean bool4 = Flags.IS_INLINE.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
        firResolvedDeclarationStatusImpl.setInline(bool4.booleanValue());
        Boolean bool5 = Flags.IS_TAILREC.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool5, "get(...)");
        firResolvedDeclarationStatusImpl.setTailRec(bool5.booleanValue());
        Boolean bool6 = Flags.IS_EXTERNAL_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool6, "get(...)");
        firResolvedDeclarationStatusImpl.setExternal(bool6.booleanValue());
        Boolean bool7 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool7, "get(...)");
        firResolvedDeclarationStatusImpl.setSuspend(bool7.booleanValue());
        firResolvedDeclarationStatusImpl.setHasStableParameterNames(!Flags.IS_FUNCTION_WITH_NON_STABLE_PARAMETER_NAMES.get(flags).booleanValue());
        firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firSimpleFunctionBuilder.setSymbol(firNamedFunctionSymbol);
        firSimpleFunctionBuilder.setDispatchReceiverType(this.c.getDispatchReceiver());
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameter> typeParameters = firSimpleFunctionBuilder.getTypeParameters();
        List<FirTypeParameterSymbol> ownTypeParameters = firDeserializationContextChildContext.getTypeDeserializer().getOwnTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(ownTypeParameters, 10));
        Iterator<T> it = ownTypeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add((FirTypeParameter) ((FirTypeParameterSymbol) it.next()).getFir());
        }
        CollectionsKt.addAll(typeParameters, arrayList);
        List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
        FirMemberDeserializer memberDeserializer = firDeserializationContextChildContext.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        CollectionsKt.addAll(valueParameters, valueParameters$default(memberDeserializer, valueParameterList, firNamedFunctionSymbol, proto, AbstractAnnotationDeserializer.CallableKind.OTHERS, classProto, false, 32, null));
        CollectionsKt.addAll(firSimpleFunctionBuilder.getAnnotations(), this.c.getAnnotationDeserializer().loadFunctionAnnotations(this.c.getContainerSource(), proto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable()));
        firSimpleFunctionBuilder.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAnnotations(firSimpleFunctionBuilder.getAnnotations(), this.c.getSession(), false, listCreate));
        firSimpleFunctionBuilder.setContainerSource(this.c.getContainerSource());
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(proto, this.c.getTypeTable());
        List<FirContextReceiver> contextReceivers = firSimpleFunctionBuilder.getContextReceivers();
        Iterator<T> it2 = listContextReceiverTypes.iterator();
        while (it2.hasNext()) {
            contextReceivers.add(loadContextReceiver((ProtoBuf.Type) it2.next()));
        }
        FirSimpleFunction firSimpleFunctionMo6809build = firSimpleFunctionBuilder.mo6809build();
        FirVersionRequirementsTableKeyKt.setVersionRequirements(firSimpleFunctionMo6809build, listCreate);
        PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firSimpleFunctionMo6809build, this.c.getSession());
        if (proto.hasContract()) {
            FirContractDeserializer firContractDeserializer = proto.getTypeParameterList().isEmpty() ? this.contractDeserializer : new FirContractDeserializer(firDeserializationContextChildContext);
            ProtoBuf.Contract contract = proto.getContract();
            Intrinsics.checkNotNullExpressionValue(contract, "getContract(...)");
            FirContractDescription firContractDescriptionLoadContract = firContractDeserializer.loadContract(contract, firSimpleFunctionMo6809build);
            if (firContractDescriptionLoadContract != null) {
                firSimpleFunctionMo6809build.replaceContractDescription(firContractDescriptionLoadContract);
            }
        }
        return firSimpleFunctionMo6809build;
    }

    public final FirConstructor loadConstructor(ProtoBuf.Constructor proto, ProtoBuf.Class classProto, FirRegularClassBuilder classBuilder) {
        FirConstructorBuilder firConstructorBuilder;
        ConeClassLikeType coneClassLikeTypeDefaultType;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        int flags = proto.getFlags();
        FqName relativeClassName = this.c.getRelativeClassName();
        Intrinsics.checkNotNull(relativeClassName);
        FqName packageFqName = this.c.getPackageFqName();
        Name nameShortName = relativeClassName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(new CallableId(packageFqName, relativeClassName, nameShortName, null, 8, null));
        FirDeserializationContext firDeserializationContext = this.c;
        FirDeserializationContext firDeserializationContextChildContext = firDeserializationContext.childContext(CollectionsKt.emptyList(), firConstructorSymbol, (PointerIconCompat.TYPE_GRAB & 4) != 0 ? firDeserializationContext.nameResolver : null, (PointerIconCompat.TYPE_GRAB & 8) != 0 ? firDeserializationContext.typeTable : null, (PointerIconCompat.TYPE_GRAB & 16) != 0 ? firDeserializationContext.relativeClassName : null, (PointerIconCompat.TYPE_GRAB & 32) != 0 ? firDeserializationContext.containerSource : null, (PointerIconCompat.TYPE_GRAB & 64) != 0 ? firDeserializationContext.outerClassSymbol : null, (PointerIconCompat.TYPE_GRAB & 128) != 0 ? firDeserializationContext.annotationDeserializer : null, (PointerIconCompat.TYPE_GRAB & 256) != 0 ? firDeserializationContext.constDeserializer : null, (PointerIconCompat.TYPE_GRAB & 512) != 0);
        boolean zBooleanValue = Flags.IS_SECONDARY.get(flags).booleanValue();
        List<FirTypeParameterRef> typeParameters = classBuilder.getTypeParameters();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        ConeClassLikeLookupTag lookupTag = classBuilder.getSymbol().getLookupTag();
        List<FirTypeParameterRef> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null));
        FirResolvedTypeRef firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
        if (!zBooleanValue) {
            firConstructorBuilder = new FirPrimaryConstructorBuilder();
        } else {
            firConstructorBuilder = new FirConstructorBuilder();
        }
        FirAbstractConstructorBuilder firAbstractConstructorBuilder = firConstructorBuilder;
        firAbstractConstructorBuilder.setModuleData(this.c.getModuleData());
        firAbstractConstructorBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firAbstractConstructorBuilder.setReturnTypeRef(firResolvedTypeRefMo6809build);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        boolean zIsInner = classBuilder.getStatus().isInner();
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, Modality.FINAL, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) classBuilder.getSymbol(), false, false, 6, (Object) null));
        Boolean bool = Flags.IS_EXPECT_CLASS.get(classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        firResolvedDeclarationStatusImpl.setExpect(bool.booleanValue());
        firResolvedDeclarationStatusImpl.setHasStableParameterNames(!Flags.IS_CONSTRUCTOR_WITH_NON_STABLE_PARAMETER_NAMES.get(flags).booleanValue());
        firResolvedDeclarationStatusImpl.setActual(false);
        firResolvedDeclarationStatusImpl.setOverride(false);
        firResolvedDeclarationStatusImpl.setInner(zIsInner);
        firAbstractConstructorBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firAbstractConstructorBuilder.setSymbol(firConstructorSymbol);
        if (zIsInner) {
            FirDeserializationContext firDeserializationContext2 = this.c;
            coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(new ClassId(firDeserializationContext2.getPackageFqName(), relativeClassName.parent(), false), firDeserializationContext2.getOuterTypeParameters());
        } else {
            coneClassLikeTypeDefaultType = null;
        }
        firAbstractConstructorBuilder.setDispatchReceiverType(coneClassLikeTypeDefaultType);
        firAbstractConstructorBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameterRef> typeParameters2 = firAbstractConstructorBuilder.getTypeParameters();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof FirTypeParameter) {
                arrayList2.add(obj);
            }
        }
        ArrayList<FirTypeParameter> arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        for (FirTypeParameter firTypeParameter : arrayList3) {
            FirConstructedClassTypeParameterRefBuilder firConstructedClassTypeParameterRefBuilder = new FirConstructedClassTypeParameterRefBuilder();
            firConstructedClassTypeParameterRefBuilder.setSymbol(firTypeParameter.getSymbol());
            arrayList4.add(firConstructedClassTypeParameterRefBuilder.build());
        }
        CollectionsKt.addAll(typeParameters2, arrayList4);
        List<FirValueParameter> valueParameters = firAbstractConstructorBuilder.getValueParameters();
        FirMemberDeserializer memberDeserializer = firDeserializationContextChildContext.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        CollectionsKt.addAll(valueParameters, memberDeserializer.valueParameters(valueParameterList, firConstructorSymbol, proto, AbstractAnnotationDeserializer.CallableKind.OTHERS, classProto, Intrinsics.areEqual(classBuilder.getSymbol().getClassId(), StandardClassIds.INSTANCE.getEnum())));
        CollectionsKt.addAll(firAbstractConstructorBuilder.getAnnotations(), this.c.getAnnotationDeserializer().loadConstructorAnnotations(this.c.getContainerSource(), proto, firDeserializationContextChildContext.getNameResolver(), firDeserializationContextChildContext.getTypeTable()));
        firAbstractConstructorBuilder.setContainerSource(this.c.getContainerSource());
        firAbstractConstructorBuilder.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAnnotations$default(firAbstractConstructorBuilder.getAnnotations(), this.c.getSession(), false, null, 4, null));
        firAbstractConstructorBuilder.getContextReceivers().addAll(createContextReceiversForClass$fir_deserialization(classProto));
        FirConstructor firConstructorMo6809build = firAbstractConstructorBuilder.mo6809build();
        ConeClassLikeType dispatchReceiver = this.c.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, dispatchReceiver.getLookupTag());
        FirVersionRequirementsTableKeyKt.setVersionRequirements(firConstructorMo6809build, FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, proto, this.c));
        PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firConstructorMo6809build, this.c.getSession());
        return firConstructorMo6809build;
    }

    private final FirExpression defaultValue(int flags) {
        Boolean bool = Flags.DECLARES_DEFAULT_VALUE.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            return new FirExpressionStubBuilder().mo6809build();
        }
        return null;
    }

    static /* synthetic */ List valueParameters$default(FirMemberDeserializer firMemberDeserializer, List list, FirFunctionSymbol firFunctionSymbol, MessageLite messageLite, AbstractAnnotationDeserializer.CallableKind callableKind, ProtoBuf.Class r12, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        return firMemberDeserializer.valueParameters(list, firFunctionSymbol, messageLite, callableKind, r12, z);
    }

    private final List<FirValueParameter> valueParameters(List<ProtoBuf.ValueParameter> valueParameters, FirFunctionSymbol<?> functionSymbol, MessageLite callableProto, AbstractAnnotationDeserializer.CallableKind callableKind, ProtoBuf.Class classProto, boolean addDefaultValue) {
        List<ProtoBuf.ValueParameter> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ProtoBuf.ValueParameter valueParameter = (ProtoBuf.ValueParameter) obj;
            int flags = valueParameter.hasFlags() ? valueParameter.getFlags() : 0;
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), valueParameter.getName());
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setModuleData(this.c.getModuleData());
            firValueParameterBuilder.setContainingFunctionSymbol(functionSymbol);
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
            firValueParameterBuilder.setReturnTypeRef(toTypeRef(ProtoTypeTableUtilKt.type(valueParameter, this.c.getTypeTable()), this.c));
            firValueParameterBuilder.setName(name2);
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name2));
            firValueParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firValueParameterBuilder.setDefaultValue(defaultValue(flags));
            if (addDefaultValue) {
                FirExpressionStubBuilder firExpressionStubBuilder = new FirExpressionStubBuilder();
                Unit unit = Unit.INSTANCE;
                firValueParameterBuilder.setDefaultValue(firExpressionStubBuilder.mo6809build());
            }
            Boolean bool = Flags.IS_CROSSINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            firValueParameterBuilder.setCrossinline(bool.booleanValue());
            Boolean bool2 = Flags.IS_NOINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            firValueParameterBuilder.setNoinline(bool2.booleanValue());
            firValueParameterBuilder.setVararg(ProtoTypeTableUtilKt.varargElementType(valueParameter, this.c.getTypeTable()) != null);
            CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), this.c.getAnnotationDeserializer().loadValueParameterAnnotations(this.c.getContainerSource(), callableProto, valueParameter, classProto, this.c.getNameResolver(), this.c.getTypeTable(), callableKind, i));
            arrayList.add(firValueParameterBuilder.mo6809build());
            i = i2;
        }
        return CollectionsKt.toList(arrayList);
    }

    private final FirTypeRef toTypeRef(ProtoBuf.Type type, FirDeserializationContext firDeserializationContext) {
        return firDeserializationContext.getTypeDeserializer().typeRef(type);
    }
}
