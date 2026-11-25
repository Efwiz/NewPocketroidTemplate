package org.jetbrains.kotlin.fir.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.EnumClassUtilsKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.FirVersionRequirementsTableKeyKt;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.declarations.ValueClassesUtilsKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirEnumEntryBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirOuterClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.SerializationPluginMetadataExtensions;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ValueClassUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ClassDeserialization.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u008e\u0001\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\b\b\u0002\u0010\"\u001a\u00020#2\u001a\u0010$\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u00120%\u001a$\u0010&\u001a\u00020\f*\u00020'2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010\u0015\u001a\u00020\u0006\u001a \u0010*\u001a\b\u0012\u0004\u0012\u00020,0+*\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020/0+H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"!\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u00060"}, d2 = {"ARRAY", "Lorg/jetbrains/kotlin/name/Name;", "ARRAY_CLASSES", "", "deserializationExtension", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationExtension;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getDeserializationExtension", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationExtension;", "deserializationExtension$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "deserializeClassToSymbol", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "session", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "defaultAnnotationDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "serializerExtensionProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "parentContext", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "deserializeNestedClass", "Lkotlin/Function2;", "addCloneForArrayIfNeeded", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "propertiesInOrder", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ClassOrBuilder;", "versionRequirements", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "fir-deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassDeserializationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassDeserializationKt.class, "fir-deserialization"), "deserializationExtension", "getDeserializationExtension(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationExtension;"))};
    private static final Name ARRAY;
    private static final Set<Name> ARRAY_CLASSES;
    private static final NullableArrayMapAccessor deserializationExtension$delegate;

    /* JADX WARN: Code duplicated, block: B:100:0x04fb  */
    /* JADX WARN: Code duplicated, block: B:101:0x054c  */
    /* JADX WARN: Code duplicated, block: B:104:0x0562  */
    /* JADX WARN: Code duplicated, block: B:108:0x0579  */
    /* JADX WARN: Code duplicated, block: B:116:0x0597  */
    /* JADX WARN: Code duplicated, block: B:117:0x059c  */
    /* JADX WARN: Code duplicated, block: B:120:0x05b4  */
    /* JADX WARN: Code duplicated, block: B:123:0x05d6 A[LOOP:10: B:121:0x05d0->B:123:0x05d6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:127:0x0614  */
    /* JADX WARN: Code duplicated, block: B:135:0x0665  */
    /* JADX WARN: Code duplicated, block: B:144:0x03f3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x03ba A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:150:0x042a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x0417 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x04e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:157:0x0591 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x0241 A[LOOP:0: B:42:0x023b->B:44:0x0241, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:47:0x025e  */
    /* JADX WARN: Code duplicated, block: B:56:0x029c  */
    /* JADX WARN: Code duplicated, block: B:58:0x029f  */
    /* JADX WARN: Code duplicated, block: B:63:0x02ca A[LOOP:2: B:61:0x02c4->B:63:0x02ca, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:67:0x02f9 A[LOOP:3: B:65:0x02f3->B:67:0x02f9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:71:0x0352 A[LOOP:4: B:69:0x034c->B:71:0x0352, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:75:0x038c A[LOOP:5: B:73:0x0386->B:75:0x038c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:79:0x03c0  */
    /* JADX WARN: Code duplicated, block: B:81:0x03e9  */
    /* JADX WARN: Code duplicated, block: B:82:0x03f0  */
    /* JADX WARN: Code duplicated, block: B:88:0x041d  */
    /* JADX WARN: Code duplicated, block: B:94:0x0451  */
    /* JADX WARN: Code duplicated, block: B:96:0x04e5  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void deserializeClassToSymbol(ClassId classId, ProtoBuf.Class classProto, FirRegularClassSymbol symbol, NameResolver nameResolver, FirSession session, FirModuleData moduleData, AbstractAnnotationDeserializer abstractAnnotationDeserializer, FirScopeProvider scopeProvider, SerializerExtensionProtocol serializerExtensionProtocol, FirDeserializationContext firDeserializationContext, DeserializedContainerSource deserializedContainerSource, FirDeclarationOrigin origin, Function2<? super ClassId, ? super FirDeserializationContext, FirRegularClassSymbol> deserializeNestedClass) {
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl;
        ProtoBuf.Class.Kind kind;
        int i;
        ProtoBuf.Class.Kind kind2;
        int i2;
        FirDeserializationContext firDeserializationContextCreateForClass;
        List<VersionRequirement> listCreate;
        FirRegularClassBuilder firRegularClassBuilder;
        ArrayList arrayList;
        Iterator<T> it;
        FirTypeDeserializer typeDeserializer;
        FirMemberDeserializer memberDeserializer;
        List<FirTypeRef> superTypeRefs;
        Iterator<T> it2;
        ArrayList arrayList2;
        FirRegularClassBuilder firRegularClassBuilder2;
        ArrayList arrayList3;
        Iterator<T> it3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        Iterator<T> it4;
        ArrayList arrayList7;
        Iterator<T> it5;
        FirRegularClassSymbol firRegularClassSymbol;
        FirDeserializationExtension deserializationExtension;
        Iterator<T> it6;
        Object next;
        FirRegularClass firRegularClass;
        FirRegularClassSymbol symbol2;
        final FirRegularClass firRegularClassMo6809build;
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentationLoadValueClassRepresentation;
        FirDeserializationExtension deserializationExtension2;
        String strLoadModuleName;
        ArrayList arrayList8;
        FirDeclaration firDeclaration;
        FirEnumEntry firEnumEntryMo6809build;
        FirTypeAlias firTypeAliasLoadTypeAlias$default;
        FirRegularClassSymbol firRegularClassSymbolInvoke;
        FirRegularClass firRegularClass2;
        ArrayList arrayListEmptyList;
        List<FirTypeParameterSymbol> allTypeParameters;
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        Intrinsics.checkNotNullParameter(serializerExtensionProtocol, "serializerExtensionProtocol");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(deserializeNestedClass, "deserializeNestedClass");
        int flags = classProto.getFlags();
        ProtoBuf.Class.Kind kind3 = Flags.CLASS_KIND.get(flags);
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl2 = new FirResolvedDeclarationStatusImpl(visibility, modality, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) (firDeserializationContext != null ? firDeserializationContext.getOuterClassSymbol() : null), true, false, 4, (Object) null));
        Boolean bool = Flags.IS_EXPECT_CLASS.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        firResolvedDeclarationStatusImpl2.setExpect(bool.booleanValue());
        firResolvedDeclarationStatusImpl2.setActual(false);
        firResolvedDeclarationStatusImpl2.setCompanion(kind3 == ProtoBuf.Class.Kind.COMPANION_OBJECT);
        Boolean bool2 = Flags.IS_INNER.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
        firResolvedDeclarationStatusImpl2.setInner(bool2.booleanValue());
        Boolean bool3 = Flags.IS_DATA.get(classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
        firResolvedDeclarationStatusImpl2.setData(bool3.booleanValue());
        Boolean bool4 = Flags.IS_VALUE_CLASS.get(classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
        firResolvedDeclarationStatusImpl2.setInline(bool4.booleanValue());
        Boolean bool5 = Flags.IS_EXTERNAL_CLASS.get(classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool5, "get(...)");
        firResolvedDeclarationStatusImpl2.setExternal(bool5.booleanValue());
        Boolean bool6 = Flags.IS_FUN_INTERFACE.get(classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool6, "get(...)");
        firResolvedDeclarationStatusImpl2.setFun(bool6.booleanValue());
        boolean z = modality == Modality.SEALED;
        FirBuiltinAnnotationDeserializer firBuiltinAnnotationDeserializer = abstractAnnotationDeserializer == null ? new FirBuiltinAnnotationDeserializer(session) : abstractAnnotationDeserializer;
        FirDeserializationExtension deserializationExtension3 = getDeserializationExtension(session);
        FirConstDeserializer firConstDeserializerCreateConstDeserializer = deserializationExtension3 != null ? deserializationExtension3.createConstDeserializer(deserializedContainerSource, session, serializerExtensionProtocol) : null;
        FirConstDeserializer firConstDeserializer = firConstDeserializerCreateConstDeserializer == null ? new FirConstDeserializer(session, serializerExtensionProtocol) : firConstDeserializerCreateConstDeserializer;
        if (firDeserializationContext != null) {
            List<ProtoBuf.TypeParameter> typeParameterList = classProto.getTypeParameterList();
            Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
            FirRegularClassSymbol firRegularClassSymbol2 = symbol;
            ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            TypeTable typeTable2 = new TypeTable(typeTable);
            firResolvedDeclarationStatusImpl = firResolvedDeclarationStatusImpl2;
            kind = kind3;
            i = flags;
            FirDeserializationContext firDeserializationContextChildContext = firDeserializationContext.childContext(typeParameterList, firRegularClassSymbol2, nameResolver, typeTable2, classId.getRelativeClassName(), deserializedContainerSource, symbol, firBuiltinAnnotationDeserializer, (firResolvedDeclarationStatusImpl2.isCompanion() || firConstDeserializerCreateConstDeserializer == null) ? firDeserializationContext.getConstDeserializer() : firConstDeserializer, firResolvedDeclarationStatusImpl2.isInner());
            if (firDeserializationContextChildContext != null) {
                i2 = i;
                kind2 = kind;
                firDeserializationContextCreateForClass = firDeserializationContextChildContext;
            }
            if (firResolvedDeclarationStatusImpl.isCompanion() && firDeserializationContext != null) {
                firDeserializationContextCreateForClass.getAnnotationDeserializer().inheritAnnotationInfo(firDeserializationContext.getAnnotationDeserializer());
                Unit unit = Unit.INSTANCE;
                Unit unit2 = Unit.INSTANCE;
            }
            listCreate = FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, classProto, firDeserializationContextCreateForClass);
            firRegularClassBuilder = new FirRegularClassBuilder();
            firRegularClassBuilder.setModuleData(r5);
            firRegularClassBuilder.setOrigin(origin);
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            firRegularClassBuilder.setName(shortClassName);
            firRegularClassBuilder.setStatus(firResolvedDeclarationStatusImpl);
            firRegularClassBuilder.setClassKind(ProtoEnumFlags.INSTANCE.classKind(kind2));
            firRegularClassBuilder.setScopeProvider(scopeProvider);
            firRegularClassBuilder.setSymbol(r8);
            firRegularClassBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            List<FirTypeParameterRef> typeParameters = firRegularClassBuilder.getTypeParameters();
            List<FirTypeParameterSymbol> ownTypeParameters = firDeserializationContextCreateForClass.getTypeDeserializer().getOwnTypeParameters();
            arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(ownTypeParameters, 10));
            it = ownTypeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add((FirTypeParameter) ((FirTypeParameterSymbol) it.next()).getFir());
            }
            CollectionsKt.addAll(typeParameters, arrayList);
            if (firResolvedDeclarationStatusImpl.isInner()) {
                List<FirTypeParameterRef> typeParameters2 = firRegularClassBuilder.getTypeParameters();
                if (firDeserializationContext != null || (allTypeParameters = firDeserializationContext.getAllTypeParameters()) == null) {
                    arrayListEmptyList = null;
                } else {
                    List<FirTypeParameterSymbol> list = allTypeParameters;
                    ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (FirTypeParameterSymbol firTypeParameterSymbol : list) {
                        FirOuterClassTypeParameterRefBuilder firOuterClassTypeParameterRefBuilder = new FirOuterClassTypeParameterRefBuilder();
                        firOuterClassTypeParameterRefBuilder.setSymbol(firTypeParameterSymbol);
                        arrayList9.add(firOuterClassTypeParameterRefBuilder.build());
                    }
                    arrayListEmptyList = arrayList9;
                }
                if (arrayListEmptyList == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                }
                CollectionsKt.addAll(typeParameters2, arrayListEmptyList);
            }
            typeDeserializer = firDeserializationContextCreateForClass.getTypeDeserializer();
            memberDeserializer = firDeserializationContextCreateForClass.getMemberDeserializer();
            List<ProtoBuf.Type> listSupertypes = ProtoTypeTableUtilKt.supertypes(classProto, firDeserializationContextCreateForClass.getTypeTable());
            superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
            it2 = listSupertypes.iterator();
            while (it2.hasNext()) {
                superTypeRefs.add(typeDeserializer.typeRef((ProtoBuf.Type) it2.next()));
            }
            List<ProtoBuf.Function> functionList = classProto.getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            List<ProtoBuf.Function> list2 = functionList;
            arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (ProtoBuf.Function function : list2) {
                Intrinsics.checkNotNull(function);
                arrayList2.add(FirMemberDeserializer.loadFunction$default(memberDeserializer, function, classProto, symbol, null, 8, null));
                firRegularClassBuilder = firRegularClassBuilder;
                listCreate = listCreate;
            }
            firRegularClassBuilder2 = firRegularClassBuilder;
            List<VersionRequirement> list3 = listCreate;
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList2);
            List<ProtoBuf.Property> listPropertiesInOrder = propertiesInOrder(classProto, list3);
            arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPropertiesInOrder, 10));
            it3 = listPropertiesInOrder.iterator();
            while (it3.hasNext()) {
                arrayList3.add(memberDeserializer.loadProperty((ProtoBuf.Property) it3.next(), classProto, r8));
            }
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList3);
            List<ProtoBuf.Constructor> constructorList = classProto.getConstructorList();
            Intrinsics.checkNotNullExpressionValue(constructorList, "getConstructorList(...)");
            List<ProtoBuf.Constructor> list4 = constructorList;
            arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            for (ProtoBuf.Constructor constructor : list4) {
                Intrinsics.checkNotNull(constructor);
                arrayList4.add(memberDeserializer.loadConstructor(constructor, classProto, firRegularClassBuilder2));
            }
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList4);
            List<Integer> nestedClassNameList = classProto.getNestedClassNameList();
            Intrinsics.checkNotNullExpressionValue(nestedClassNameList, "getNestedClassNameList(...)");
            arrayList5 = new ArrayList();
            for (Integer num : nestedClassNameList) {
                Intrinsics.checkNotNull(num);
                ClassId classIdCreateNestedClassId = classId.createNestedClassId(Name.identifier(nameResolver.getString(num.intValue())));
                Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
                firRegularClassSymbolInvoke = deserializeNestedClass.invoke(classIdCreateNestedClassId, firDeserializationContextCreateForClass);
                if (firRegularClassSymbolInvoke != null) {
                    firRegularClass2 = (FirRegularClass) firRegularClassSymbolInvoke.getFir();
                } else {
                    firRegularClass2 = null;
                }
                if (firRegularClass2 != null) {
                    arrayList5.add(firRegularClass2);
                }
            }
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList5);
            List<ProtoBuf.TypeAlias> typeAliasList = classProto.getTypeAliasList();
            Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
            arrayList6 = new ArrayList();
            it4 = typeAliasList.iterator();
            while (it4.hasNext()) {
                firTypeAliasLoadTypeAlias$default = FirMemberDeserializer.loadTypeAlias$default(memberDeserializer, (ProtoBuf.TypeAlias) it4.next(), null, 2, null);
                if (firTypeAliasLoadTypeAlias$default != null) {
                    arrayList6.add(firTypeAliasLoadTypeAlias$default);
                }
            }
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList6);
            List<ProtoBuf.EnumEntry> enumEntryList = classProto.getEnumEntryList();
            Intrinsics.checkNotNullExpressionValue(enumEntryList, "getEnumEntryList(...)");
            arrayList7 = new ArrayList();
            it5 = enumEntryList.iterator();
            while (it5.hasNext()) {
                Name name2 = NameResolverUtilKt.getName(r7, ((ProtoBuf.EnumEntry) it5.next()).getName());
                ConeClassLikeTypeImpl coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(symbol.getLookupTag(), new ConeTypeProjection[0], false, null, 8, null);
                FirEnumEntryBuilder firEnumEntryBuilder = new FirEnumEntryBuilder();
                firEnumEntryBuilder.setModuleData(moduleData);
                firEnumEntryBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType(coneClassLikeTypeImpl);
                firEnumEntryBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
                firEnumEntryBuilder.setName(name2);
                firEnumEntryBuilder.setSymbol(new FirEnumEntrySymbol(new CallableId(classId, name2)));
                FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl3 = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
                firResolvedDeclarationStatusImpl3.setStatic(true);
                firEnumEntryBuilder.setStatus(firResolvedDeclarationStatusImpl3);
                firEnumEntryBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
                firEnumEntryMo6809build = firEnumEntryBuilder.mo6809build();
                ConeClassLikeType dispatchReceiver = firDeserializationContextCreateForClass.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                ClassMembersKt.setContainingClassForStaticMemberAttr(firEnumEntryMo6809build, dispatchReceiver.getLookupTag());
                if (firEnumEntryMo6809build != null) {
                    arrayList7.add(firEnumEntryMo6809build);
                }
            }
            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList7);
            if (firRegularClassBuilder2.getClassKind() == ClassKind.ENUM_CLASS) {
                FqName packageFqName = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                FqName relativeClassName = classId.getRelativeClassName();
                Intrinsics.checkNotNullExpressionValue(relativeClassName, "getRelativeClassName(...)");
                firRegularClassSymbol = null;
                EnumClassUtilsKt.generateValuesFunction$default(firRegularClassBuilder2, moduleData, packageFqName, relativeClassName, false, null, 24, null);
                FqName packageFqName2 = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                FqName relativeClassName2 = classId.getRelativeClassName();
                Intrinsics.checkNotNullExpressionValue(relativeClassName2, "getRelativeClassName(...)");
                EnumClassUtilsKt.generateValueOfFunction$default(firRegularClassBuilder2, moduleData, packageFqName2, relativeClassName2, false, null, 24, null);
                FqName packageFqName3 = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName3, "getPackageFqName(...)");
                FqName relativeClassName3 = classId.getRelativeClassName();
                Intrinsics.checkNotNullExpressionValue(relativeClassName3, "getRelativeClassName(...)");
                EnumClassUtilsKt.generateEntriesGetter$default(firRegularClassBuilder2, moduleData, packageFqName3, relativeClassName3, false, null, 24, null);
            } else {
                firRegularClassSymbol = null;
            }
            addCloneForArrayIfNeeded(firRegularClassBuilder2, classId, firDeserializationContextCreateForClass.getDispatchReceiver(), session);
            deserializationExtension = getDeserializationExtension(session);
            if (deserializationExtension != null) {
                deserializationExtension.configureDeserializedClass(firRegularClassBuilder2, classId);
                Unit unit3 = Unit.INSTANCE;
                Unit unit4 = Unit.INSTANCE;
            }
            it6 = firRegularClassBuilder2.getDeclarations().iterator();
            while (true) {
                if (it6.hasNext()) {
                    next = firRegularClassSymbol;
                    break;
                }
                next = it6.next();
                firDeclaration = (FirDeclaration) next;
                if (!(firDeclaration instanceof FirRegularClass) && ((FirRegularClass) firDeclaration).getStatus().isCompanion()) {
                    break;
                }
            }
            firRegularClass = (FirRegularClass) next;
            if (firRegularClass != null) {
                symbol2 = firRegularClass.getSymbol();
            } else {
                symbol2 = firRegularClassSymbol;
            }
            firRegularClassBuilder2.setCompanionObjectSymbol(symbol2);
            firRegularClassBuilder2.getContextReceivers().addAll(memberDeserializer.createContextReceiversForClass$fir_deserialization(r7));
            firRegularClassMo6809build = firRegularClassBuilder2.mo6809build();
            if (z) {
                List<Integer> sealedSubclassFqNameList = classProto.getSealedSubclassFqNameList();
                Intrinsics.checkNotNullExpressionValue(sealedSubclassFqNameList, "getSealedSubclassFqNameList(...)");
                List<Integer> list5 = sealedSubclassFqNameList;
                arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                for (Integer num2 : list5) {
                    Intrinsics.checkNotNull(num2);
                    arrayList8.add(ClassId.fromString(r7.getQualifiedClassName(num2.intValue())));
                }
                SealedClassInheritorsKt.setSealedClassInheritors(firRegularClassMo6809build, arrayList8);
            }
            valueClassRepresentationLoadValueClassRepresentation = ValueClassUtilKt.loadValueClassRepresentation(r7, firDeserializationContextCreateForClass.getNameResolver(), firDeserializationContextCreateForClass.getTypeTable(), new ClassDeserializationKt$deserializeClassToSymbol$3$1(firDeserializationContextCreateForClass.getTypeDeserializer()), new Function1<Name, ConeSimpleKotlinType>() { // from class: org.jetbrains.kotlin.fir.deserialization.ClassDeserializationKt$deserializeClassToSymbol$3$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConeSimpleKotlinType invoke(Name name3) {
                    FirTypeRef returnTypeRef;
                    Intrinsics.checkNotNullParameter(name3, "name");
                    Iterator<T> it7 = firRegularClassMo6809build.getDeclarations().iterator();
                    boolean z2 = false;
                    Object obj = null;
                    while (true) {
                        if (!it7.hasNext()) {
                            if (!z2) {
                                break;
                            }
                            break;
                        }
                        Object next2 = it7.next();
                        FirDeclaration firDeclaration2 = (FirDeclaration) next2;
                        if (firDeclaration2 instanceof FirProperty) {
                            FirProperty firProperty = (FirProperty) firDeclaration2;
                            if (firProperty.getReceiverParameter() == null && Intrinsics.areEqual(firProperty.getName(), name3)) {
                                if (!z2) {
                                    z2 = true;
                                    obj = next2;
                                }
                            }
                        }
                        obj = null;
                        break;
                    }
                    FirProperty firProperty2 = (FirProperty) ((FirDeclaration) obj);
                    if (firProperty2 == null || (returnTypeRef = firProperty2.getReturnTypeRef()) == null) {
                        return null;
                    }
                    FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                    ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    return (ConeSimpleKotlinType) (type instanceof ConeSimpleKotlinType ? type : null);
                }
            });
            if (valueClassRepresentationLoadValueClassRepresentation == null) {
                valueClassRepresentationLoadValueClassRepresentation = ValueClassesUtilsKt.computeValueClassRepresentation(firRegularClassMo6809build, session);
            }
            FirValueClassRepresentationKt.setValueClassRepresentation(firRegularClassMo6809build, valueClassRepresentationLoadValueClassRepresentation);
            firRegularClassMo6809build.replaceAnnotations(firDeserializationContextCreateForClass.getAnnotationDeserializer().loadClassAnnotations(r7, firDeserializationContextCreateForClass.getNameResolver()));
            FirVersionRequirementsTableKeyKt.setVersionRequirements(firRegularClassMo6809build, list3);
            DeclarationAttributesKt.setSourceElement(firRegularClassMo6809build, deserializedContainerSource);
            firRegularClassMo6809build.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(firRegularClassMo6809build, session));
            deserializationExtension2 = getDeserializationExtension(session);
            if (deserializationExtension2 != null && (strLoadModuleName = deserializationExtension2.loadModuleName(classProto, r7)) != null) {
                DeclarationAttributesKt.setModuleName(firRegularClassMo6809build, strLoadModuleName);
                Unit unit5 = Unit.INSTANCE;
                Unit unit6 = Unit.INSTANCE;
            }
            if (!Flags.HAS_ENUM_ENTRIES.get(i2).booleanValue()) {
                ClassMembersKt.setHasNoEnumEntriesAttr(firRegularClassMo6809build, true);
            }
            PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firRegularClassMo6809build, session);
        }
        firResolvedDeclarationStatusImpl = firResolvedDeclarationStatusImpl2;
        kind = kind3;
        i = flags;
        kind2 = kind;
        i2 = i;
        firDeserializationContextCreateForClass = FirDeserializationContext.INSTANCE.createForClass(classId, classProto, nameResolver, moduleData, firBuiltinAnnotationDeserializer, firConstDeserializer, deserializedContainerSource, symbol);
        if (firResolvedDeclarationStatusImpl.isCompanion()) {
            firDeserializationContextCreateForClass.getAnnotationDeserializer().inheritAnnotationInfo(firDeserializationContext.getAnnotationDeserializer());
            Unit unit7 = Unit.INSTANCE;
            Unit unit8 = Unit.INSTANCE;
        }
        listCreate = FirDeserializationUtilsKt.create(VersionRequirement.INSTANCE, classProto, firDeserializationContextCreateForClass);
        firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setModuleData(r5);
        firRegularClassBuilder.setOrigin(origin);
        Name shortClassName2 = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
        firRegularClassBuilder.setName(shortClassName2);
        firRegularClassBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firRegularClassBuilder.setClassKind(ProtoEnumFlags.INSTANCE.classKind(kind2));
        firRegularClassBuilder.setScopeProvider(scopeProvider);
        firRegularClassBuilder.setSymbol(r8);
        firRegularClassBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameterRef> typeParameters3 = firRegularClassBuilder.getTypeParameters();
        List<FirTypeParameterSymbol> ownTypeParameters2 = firDeserializationContextCreateForClass.getTypeDeserializer().getOwnTypeParameters();
        arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(ownTypeParameters2, 10));
        it = ownTypeParameters2.iterator();
        while (it.hasNext()) {
            arrayList.add((FirTypeParameter) ((FirTypeParameterSymbol) it.next()).getFir());
        }
        CollectionsKt.addAll(typeParameters3, arrayList);
        if (firResolvedDeclarationStatusImpl.isInner()) {
            List<FirTypeParameterRef> typeParameters4 = firRegularClassBuilder.getTypeParameters();
            if (firDeserializationContext != null) {
                arrayListEmptyList = null;
            } else {
                arrayListEmptyList = null;
            }
            if (arrayListEmptyList == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(typeParameters4, arrayListEmptyList);
        }
        typeDeserializer = firDeserializationContextCreateForClass.getTypeDeserializer();
        memberDeserializer = firDeserializationContextCreateForClass.getMemberDeserializer();
        List<ProtoBuf.Type> listSupertypes2 = ProtoTypeTableUtilKt.supertypes(classProto, firDeserializationContextCreateForClass.getTypeTable());
        superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
        it2 = listSupertypes2.iterator();
        while (it2.hasNext()) {
            superTypeRefs.add(typeDeserializer.typeRef((ProtoBuf.Type) it2.next()));
        }
        List<ProtoBuf.Function> functionList2 = classProto.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(functionList2, "getFunctionList(...)");
        List<ProtoBuf.Function> list6 = functionList2;
        arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
        while (r18.hasNext()) {
            Intrinsics.checkNotNull(function);
            arrayList2.add(FirMemberDeserializer.loadFunction$default(memberDeserializer, function, classProto, symbol, null, 8, null));
            firRegularClassBuilder = firRegularClassBuilder;
            listCreate = listCreate;
        }
        firRegularClassBuilder2 = firRegularClassBuilder;
        List<VersionRequirement> list7 = listCreate;
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList2);
        List<ProtoBuf.Property> listPropertiesInOrder2 = propertiesInOrder(classProto, list7);
        arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPropertiesInOrder2, 10));
        it3 = listPropertiesInOrder2.iterator();
        while (it3.hasNext()) {
            arrayList3.add(memberDeserializer.loadProperty((ProtoBuf.Property) it3.next(), classProto, r8));
        }
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList3);
        List<ProtoBuf.Constructor> constructorList2 = classProto.getConstructorList();
        Intrinsics.checkNotNullExpressionValue(constructorList2, "getConstructorList(...)");
        List<ProtoBuf.Constructor> list8 = constructorList2;
        arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list8, 10));
        while (r0.hasNext()) {
            Intrinsics.checkNotNull(constructor);
            arrayList4.add(memberDeserializer.loadConstructor(constructor, classProto, firRegularClassBuilder2));
        }
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList4);
        List<Integer> nestedClassNameList2 = classProto.getNestedClassNameList();
        Intrinsics.checkNotNullExpressionValue(nestedClassNameList2, "getNestedClassNameList(...)");
        arrayList5 = new ArrayList();
        while (r0.hasNext()) {
            Intrinsics.checkNotNull(num);
            ClassId classIdCreateNestedClassId2 = classId.createNestedClassId(Name.identifier(nameResolver.getString(num.intValue())));
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId2, "createNestedClassId(...)");
            firRegularClassSymbolInvoke = deserializeNestedClass.invoke(classIdCreateNestedClassId2, firDeserializationContextCreateForClass);
            if (firRegularClassSymbolInvoke != null) {
                firRegularClass2 = (FirRegularClass) firRegularClassSymbolInvoke.getFir();
            } else {
                firRegularClass2 = null;
            }
            if (firRegularClass2 != null) {
                arrayList5.add(firRegularClass2);
            }
        }
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList5);
        List<ProtoBuf.TypeAlias> typeAliasList2 = classProto.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(typeAliasList2, "getTypeAliasList(...)");
        arrayList6 = new ArrayList();
        it4 = typeAliasList2.iterator();
        while (it4.hasNext()) {
            firTypeAliasLoadTypeAlias$default = FirMemberDeserializer.loadTypeAlias$default(memberDeserializer, (ProtoBuf.TypeAlias) it4.next(), null, 2, null);
            if (firTypeAliasLoadTypeAlias$default != null) {
                arrayList6.add(firTypeAliasLoadTypeAlias$default);
            }
        }
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList6);
        List<ProtoBuf.EnumEntry> enumEntryList2 = classProto.getEnumEntryList();
        Intrinsics.checkNotNullExpressionValue(enumEntryList2, "getEnumEntryList(...)");
        arrayList7 = new ArrayList();
        it5 = enumEntryList2.iterator();
        while (it5.hasNext()) {
            Name name3 = NameResolverUtilKt.getName(r7, ((ProtoBuf.EnumEntry) it5.next()).getName());
            ConeClassLikeTypeImpl coneClassLikeTypeImpl2 = new ConeClassLikeTypeImpl(symbol.getLookupTag(), new ConeTypeProjection[0], false, null, 8, null);
            FirEnumEntryBuilder firEnumEntryBuilder2 = new FirEnumEntryBuilder();
            firEnumEntryBuilder2.setModuleData(moduleData);
            firEnumEntryBuilder2.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder2.setType(coneClassLikeTypeImpl2);
            firEnumEntryBuilder2.setReturnTypeRef(firResolvedTypeRefBuilder2.mo6809build());
            firEnumEntryBuilder2.setName(name3);
            firEnumEntryBuilder2.setSymbol(new FirEnumEntrySymbol(new CallableId(classId, name3)));
            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl4 = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
            firResolvedDeclarationStatusImpl4.setStatic(true);
            firEnumEntryBuilder2.setStatus(firResolvedDeclarationStatusImpl4);
            firEnumEntryBuilder2.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firEnumEntryMo6809build = firEnumEntryBuilder2.mo6809build();
            ConeClassLikeType dispatchReceiver2 = firDeserializationContextCreateForClass.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver2);
            ClassMembersKt.setContainingClassForStaticMemberAttr(firEnumEntryMo6809build, dispatchReceiver2.getLookupTag());
            if (firEnumEntryMo6809build != null) {
                arrayList7.add(firEnumEntryMo6809build);
            }
        }
        FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder2, arrayList7);
        if (firRegularClassBuilder2.getClassKind() == ClassKind.ENUM_CLASS) {
            FqName packageFqName4 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName4, "getPackageFqName(...)");
            FqName relativeClassName4 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName4, "getRelativeClassName(...)");
            firRegularClassSymbol = null;
            EnumClassUtilsKt.generateValuesFunction$default(firRegularClassBuilder2, moduleData, packageFqName4, relativeClassName4, false, null, 24, null);
            FqName packageFqName5 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName5, "getPackageFqName(...)");
            FqName relativeClassName5 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName5, "getRelativeClassName(...)");
            EnumClassUtilsKt.generateValueOfFunction$default(firRegularClassBuilder2, moduleData, packageFqName5, relativeClassName5, false, null, 24, null);
            FqName packageFqName6 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName6, "getPackageFqName(...)");
            FqName relativeClassName6 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName6, "getRelativeClassName(...)");
            EnumClassUtilsKt.generateEntriesGetter$default(firRegularClassBuilder2, moduleData, packageFqName6, relativeClassName6, false, null, 24, null);
        } else {
            firRegularClassSymbol = null;
        }
        addCloneForArrayIfNeeded(firRegularClassBuilder2, classId, firDeserializationContextCreateForClass.getDispatchReceiver(), session);
        deserializationExtension = getDeserializationExtension(session);
        if (deserializationExtension != null) {
            deserializationExtension.configureDeserializedClass(firRegularClassBuilder2, classId);
            Unit unit9 = Unit.INSTANCE;
            Unit unit10 = Unit.INSTANCE;
        }
        it6 = firRegularClassBuilder2.getDeclarations().iterator();
        while (true) {
            if (it6.hasNext()) {
                next = firRegularClassSymbol;
                break;
            } else {
                next = it6.next();
                firDeclaration = (FirDeclaration) next;
                if (!(firDeclaration instanceof FirRegularClass)) {
                }
            }
        }
        firRegularClass = (FirRegularClass) next;
        if (firRegularClass != null) {
            symbol2 = firRegularClass.getSymbol();
        } else {
            symbol2 = firRegularClassSymbol;
        }
        firRegularClassBuilder2.setCompanionObjectSymbol(symbol2);
        firRegularClassBuilder2.getContextReceivers().addAll(memberDeserializer.createContextReceiversForClass$fir_deserialization(r7));
        firRegularClassMo6809build = firRegularClassBuilder2.mo6809build();
        if (z) {
            List<Integer> sealedSubclassFqNameList2 = classProto.getSealedSubclassFqNameList();
            Intrinsics.checkNotNullExpressionValue(sealedSubclassFqNameList2, "getSealedSubclassFqNameList(...)");
            List<Integer> list9 = sealedSubclassFqNameList2;
            arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list9, 10));
            while (r1.hasNext()) {
                Intrinsics.checkNotNull(num2);
                arrayList8.add(ClassId.fromString(r7.getQualifiedClassName(num2.intValue())));
            }
            SealedClassInheritorsKt.setSealedClassInheritors(firRegularClassMo6809build, arrayList8);
        }
        valueClassRepresentationLoadValueClassRepresentation = ValueClassUtilKt.loadValueClassRepresentation(r7, firDeserializationContextCreateForClass.getNameResolver(), firDeserializationContextCreateForClass.getTypeTable(), new ClassDeserializationKt$deserializeClassToSymbol$3$1(firDeserializationContextCreateForClass.getTypeDeserializer()), new Function1<Name, ConeSimpleKotlinType>() { // from class: org.jetbrains.kotlin.fir.deserialization.ClassDeserializationKt$deserializeClassToSymbol$3$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeSimpleKotlinType invoke(Name name4) {
                FirTypeRef returnTypeRef;
                Intrinsics.checkNotNullParameter(name4, "name");
                Iterator<T> it7 = firRegularClassMo6809build.getDeclarations().iterator();
                boolean z2 = false;
                Object obj = null;
                while (true) {
                    if (!it7.hasNext()) {
                        if (!z2) {
                            break;
                        }
                        break;
                    }
                    Object next2 = it7.next();
                    FirDeclaration firDeclaration2 = (FirDeclaration) next2;
                    if (firDeclaration2 instanceof FirProperty) {
                        FirProperty firProperty = (FirProperty) firDeclaration2;
                        if (firProperty.getReceiverParameter() == null && Intrinsics.areEqual(firProperty.getName(), name4)) {
                            if (!z2) {
                                z2 = true;
                                obj = next2;
                            }
                        }
                    }
                    obj = null;
                    break;
                }
                FirProperty firProperty2 = (FirProperty) ((FirDeclaration) obj);
                if (firProperty2 == null || (returnTypeRef = firProperty2.getReturnTypeRef()) == null) {
                    return null;
                }
                FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                return (ConeSimpleKotlinType) (type instanceof ConeSimpleKotlinType ? type : null);
            }
        });
        if (valueClassRepresentationLoadValueClassRepresentation == null) {
            valueClassRepresentationLoadValueClassRepresentation = ValueClassesUtilsKt.computeValueClassRepresentation(firRegularClassMo6809build, session);
        }
        FirValueClassRepresentationKt.setValueClassRepresentation(firRegularClassMo6809build, valueClassRepresentationLoadValueClassRepresentation);
        firRegularClassMo6809build.replaceAnnotations(firDeserializationContextCreateForClass.getAnnotationDeserializer().loadClassAnnotations(r7, firDeserializationContextCreateForClass.getNameResolver()));
        FirVersionRequirementsTableKeyKt.setVersionRequirements(firRegularClassMo6809build, list7);
        DeclarationAttributesKt.setSourceElement(firRegularClassMo6809build, deserializedContainerSource);
        firRegularClassMo6809build.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(firRegularClassMo6809build, session));
        deserializationExtension2 = getDeserializationExtension(session);
        if (deserializationExtension2 != null) {
            DeclarationAttributesKt.setModuleName(firRegularClassMo6809build, strLoadModuleName);
            Unit unit11 = Unit.INSTANCE;
            Unit unit12 = Unit.INSTANCE;
        }
        if (!Flags.HAS_ENUM_ENTRIES.get(i2).booleanValue()) {
            ClassMembersKt.setHasNoEnumEntriesAttr(firRegularClassMo6809build, true);
        }
        PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firRegularClassMo6809build, session);
    }

    public static final void addCloneForArrayIfNeeded(FirRegularClassBuilder firRegularClassBuilder, ClassId classId, ConeClassLikeType coneClassLikeType, FirSession session) {
        ConeTypeParameterTypeImpl[] coneTypeParameterTypeImplArr;
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        if (Intrinsics.areEqual(classId.getPackageFqName(), StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE()) && ARRAY_CLASSES.contains(classId.getShortClassName()) && FirSymbolProviderKt.getRegularClassSymbolByClassId(FirSymbolProviderKt.getSymbolProvider(session), StandardClassIds.INSTANCE.getCloneable()) != null) {
            List<FirTypeRef> superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getCloneable()), new ConeTypeProjection[0], false, null, 8, null));
            superTypeRefs.add(firResolvedTypeRefBuilder.mo6809build());
            List<FirDeclaration> declarations = firRegularClassBuilder.getDeclarations();
            FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
            firSimpleFunctionBuilder.setModuleData(firRegularClassBuilder.getModuleData());
            firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
            firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
            if (Intrinsics.areEqual(classId.getShortClassName(), ARRAY)) {
                coneTypeParameterTypeImplArr = new ConeTypeParameterTypeImpl[]{new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(((FirTypeParameterRef) CollectionsKt.first((List) firRegularClassBuilder.getTypeParameters())).getSymbol()), false, null, 4, null)};
            } else {
                coneTypeParameterTypeImplArr = new ConeTypeParameterTypeImpl[0];
            }
            firResolvedTypeRefBuilder2.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), coneTypeParameterTypeImplArr, false, null, 8, null));
            firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder2.mo6809build());
            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
            firResolvedDeclarationStatusImpl.setOverride(true);
            firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
            firSimpleFunctionBuilder.setName(StandardClassIds.Callables.INSTANCE.getClone().getCallableName());
            firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(classId, firSimpleFunctionBuilder.getName())));
            Intrinsics.checkNotNull(coneClassLikeType);
            firSimpleFunctionBuilder.setDispatchReceiverType(coneClassLikeType);
            declarations.add(firSimpleFunctionBuilder.mo6809build());
        }
    }

    private static final List<ProtoBuf.Property> propertiesInOrder(ProtoBuf.ClassOrBuilder classOrBuilder, List<VersionRequirement> list) {
        Set set;
        List<ProtoBuf.Property> propertyList = classOrBuilder.getPropertyList();
        List<VersionRequirement> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (((VersionRequirement) it.next()).getVersion().getMajor() >= 2) {
                    Intrinsics.checkNotNull(propertyList);
                    return propertyList;
                }
            }
        }
        Object extension = classOrBuilder.getExtension(SerializationPluginMetadataExtensions.propertiesNamesInProgramOrder);
        List list3 = (List) extension;
        Intrinsics.checkNotNull(list3);
        if (list3.isEmpty()) {
            extension = null;
        }
        List list4 = (List) extension;
        if (list4 == null || (set = CollectionsKt.toSet(list4)) == null) {
            Intrinsics.checkNotNull(propertyList);
            return propertyList;
        }
        Intrinsics.checkNotNull(propertyList);
        List<ProtoBuf.Property> list5 = propertyList;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list5) {
            Integer numValueOf = Integer.valueOf(((ProtoBuf.Property) obj).getName());
            Object obj2 = linkedHashMap.get(numValueOf);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(numValueOf, obj2);
            }
            ((List) obj2).add(obj);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            List listEmptyList = (List) linkedHashMap.get((Integer) it2.next());
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() == propertyList.size()) {
            return arrayList2;
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : list5) {
            if (!set.contains(Integer.valueOf(((ProtoBuf.Property) obj3).getName()))) {
                arrayList4.add(obj3);
            }
        }
        return CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList4);
    }

    static {
        Name nameIdentifier = Name.identifier("Array");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        ARRAY = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("ByteArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        Name nameIdentifier3 = Name.identifier("CharArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        Name nameIdentifier4 = Name.identifier("ShortArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        Name nameIdentifier5 = Name.identifier("IntArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        Name nameIdentifier6 = Name.identifier("LongArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        Name nameIdentifier7 = Name.identifier("FloatArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        Name nameIdentifier8 = Name.identifier("DoubleArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        Name nameIdentifier9 = Name.identifier("BooleanArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        ARRAY_CLASSES = SetsKt.setOf((Object[]) new Name[]{nameIdentifier, nameIdentifier2, nameIdentifier3, nameIdentifier4, nameIdentifier5, nameIdentifier6, nameIdentifier7, nameIdentifier8, nameIdentifier9});
        deserializationExtension$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirDeserializationExtension.class));
    }

    public static final FirDeserializationExtension getDeserializationExtension(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirDeserializationExtension) deserializationExtension$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
