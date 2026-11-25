package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClassMembers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u001a\f\u0010m\u001a\u0004\u0018\u00010\t*\u00020\u000b\u001a\f\u0010n\u001a\u0004\u0018\u00010\t*\u00020\u0013\u001a\f\u0010o\u001a\u0004\u0018\u00010\t*\u00020\u0002\u001a\u0010\u0010o\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\u0006\u001a\f\u0010p\u001a\u0004\u0018\u00010\t*\u00020\u0002\u001a\u0010\u0010p\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\u0006\u001a\f\u0010q\u001a\u0004\u0018\u00010r*\u00020\u0002\u001a\u0010\u0010q\u001a\u0004\u0018\u00010r*\u0006\u0012\u0002\b\u00030\u0006\u001a\u0010\u0010s\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030t\u001a \u0010u\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a$\u0010u\u001a\u0004\u0018\u0001H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u0005H\u0086\b¢\u0006\u0002\u0010\u0007\u001a \u0010v\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a\u001e\u0010w\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a\"\u0010w\u001a\u0002H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u0005H\u0086\b¢\u0006\u0002\u0010\u0007\u001a\u001e\u0010x\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a\"\u0010x\u001a\u0002H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u0005H\u0086\b¢\u0006\u0002\u0010\u0007\u001a\u001e\u0010y\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a\u001e\u0010z\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\u001a\"\u0010z\u001a\u0002H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u0005H\u0086\b¢\u0006\u0002\u0010\u0007\u001a\u001e\u0010{\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u0004\"$\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"(\u0010\u0000\u001a\u0004\u0018\u0001H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0007\"3\u0010\n\u001a\u0004\u0018\u00010\t*\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\t8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\"3\u0010\u0012\u001a\u0004\u0018\u00010\t*\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u00010\t8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\u0011\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\"3\u0010\u0019\u001a\u0004\u0018\u00010\t*\u00020\u00022\b\u0010\b\u001a\u0004\u0018\u00010\t8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u0011\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\"3\u0010 \u001a\u0004\u0018\u00010\u001f*\u00020!2\b\u0010\b\u001a\u0004\u0018\u00010\u001f8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b&\u0010\u0011\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%\"I\u0010(\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010'\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010'8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b-\u0010\u0011\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,\"/\u0010(\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010'\"\b\b\u0000\u0010\u0001*\u00020\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\u00068F¢\u0006\u0006\u001a\u0004\b)\u0010.\"\u0015\u0010/\u001a\u000200*\u0002018F¢\u0006\u0006\u001a\u0004\b2\u00103\"3\u00104\u001a\u0004\u0018\u000100*\u0002012\b\u0010\b\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b9\u0010\u0011\u001a\u0004\b5\u00106\"\u0004\b7\u00108\"3\u0010:\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u00028F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b>\u0010\u0011\u001a\u0004\b;\u0010\u0004\"\u0004\b<\u0010=\"3\u0010?\u001a\u0004\u0018\u000100*\u00020\u001f2\b\u0010\b\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bC\u0010\u0011\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B\"\u0015\u0010D\u001a\u000200*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bD\u0010E\"\u0019\u0010D\u001a\u000200*\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\bD\u0010F\"3\u0010G\u001a\u0004\u0018\u000100*\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bK\u0010\u0011\u001a\u0004\bG\u0010H\"\u0004\bI\u0010J\"3\u0010L\u001a\u0004\u0018\u000100*\u00020M2\b\u0010\b\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bQ\u0010\u0011\u001a\u0004\bL\u0010N\"\u0004\bO\u0010P\"3\u0010R\u001a\u0004\u0018\u000100*\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bT\u0010\u0011\u001a\u0004\bR\u0010H\"\u0004\bS\u0010J\"\u0015\u0010U\u001a\u000200*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bU\u0010E\"\u0019\u0010U\u001a\u000200*\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\bU\u0010F\"\u0015\u0010V\u001a\u000200*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bV\u0010E\"\u0019\u0010V\u001a\u000200*\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\bV\u0010F\"=\u0010X\u001a\u0004\u0018\u00010W\"\b\b\u0000\u0010\u0001*\u00020Y*\u0002H\u00012\b\u0010\b\u001a\u0004\u0018\u00010W8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b^\u0010\u0011\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]\"=\u0010_\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\b\u0010\b\u001a\u0004\u0018\u00018\u00008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bb\u0010\u0011\u001a\u0004\b`\u0010\u0004\"\u0004\ba\u0010=\"$\u0010c\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\bd\u0010\u0004\"(\u0010c\u001a\u0004\u0018\u0001H\u0005\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0006*\u0002H\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\bd\u0010\u0007\"=\u0010e\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\b\u0010\b\u001a\u0004\u0018\u00018\u00008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bh\u0010\u0011\u001a\u0004\bf\u0010\u0004\"\u0004\bg\u0010=\"\u0015\u0010i\u001a\u00020\u0002*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bj\u0010\u0004\"\u0015\u0010k\u001a\u00020\u0002*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bl\u0010\u0004¨\u0006|"}, d2 = {"baseForIntersectionOverride", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getBaseForIntersectionOverride", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "<set-?>", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "containingClassAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "getContainingClassAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "setContainingClassAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "containingClassAttr$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "containingClassForLocalAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getContainingClassForLocalAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "setContainingClassForLocalAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "containingClassForLocalAttr$delegate", "containingClassForStaticMemberAttr", "getContainingClassForStaticMemberAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "setContainingClassForStaticMemberAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "containingClassForStaticMemberAttr$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "correspondingProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getCorrespondingProperty", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "setCorrespondingProperty", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)V", "correspondingProperty$delegate", "Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;", "delegatedWrapperData", "getDelegatedWrapperData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;", "setDelegatedWrapperData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;)V", "delegatedWrapperData$delegate", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;", "hasEnumEntries", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getHasEnumEntries", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Z", "hasNoEnumEntriesAttr", "getHasNoEnumEntriesAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/lang/Boolean;", "setHasNoEnumEntriesAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Ljava/lang/Boolean;)V", "hasNoEnumEntriesAttr$delegate", "initialSignatureAttr", "getInitialSignatureAttr", "setInitialSignatureAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)V", "initialSignatureAttr$delegate", "isCatchParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;", "setCatchParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/lang/Boolean;)V", "isCatchParameter$delegate", "isIntersectionOverride", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Z", "isJavaRecord", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/Boolean;", "setJavaRecord", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Ljava/lang/Boolean;)V", "isJavaRecord$delegate", "isJavaRecordComponent", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Ljava/lang/Boolean;", "setJavaRecordComponent", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Ljava/lang/Boolean;)V", "isJavaRecordComponent$delegate", "isNewPlaceForBodyGeneration", "setNewPlaceForBodyGeneration", "isNewPlaceForBodyGeneration$delegate", "isSubstitutionOrIntersectionOverride", "isSubstitutionOverride", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "matchingParameterFunctionType", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "getMatchingParameterFunctionType", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "setMatchingParameterFunctionType", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "matchingParameterFunctionType$delegate", "originalForIntersectionOverrideAttr", "getOriginalForIntersectionOverrideAttr", "setOriginalForIntersectionOverrideAttr", "originalForIntersectionOverrideAttr$delegate", "originalForSubstitutionOverride", "getOriginalForSubstitutionOverride", "originalForSubstitutionOverrideAttr", "getOriginalForSubstitutionOverrideAttr", "setOriginalForSubstitutionOverrideAttr", "originalForSubstitutionOverrideAttr$delegate", "propertyIfAccessor", "getPropertyIfAccessor", "propertyIfBackingField", "getPropertyIfBackingField", "containingClass", "containingClassForLocal", "containingClassLookupTag", "dispatchReceiverClassLookupTagOrNull", "dispatchReceiverClassTypeOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getContainingClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "originalIfFakeOverride", "originalIfFakeOverrideOrDelegated", "originalOrSelf", "unwrapFakeOverrides", "unwrapFakeOverridesOrDelegated", "unwrapSubstitutionOverrides", "unwrapUseSiteSubstitutionOverrides", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassMembersKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "containingClassForStaticMemberAttr", "getContainingClassForStaticMemberAttr(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "containingClassForLocalAttr", "getContainingClassForLocalAttr(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "containingClassAttr", "getContainingClassAttr(Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;)Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "hasNoEnumEntriesAttr", "getHasNoEnumEntriesAttr(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "isNewPlaceForBodyGeneration", "isNewPlaceForBodyGeneration(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "originalForSubstitutionOverrideAttr", "getOriginalForSubstitutionOverrideAttr(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "originalForIntersectionOverrideAttr", "getOriginalForIntersectionOverrideAttr(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "initialSignatureAttr", "getInitialSignatureAttr(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "matchingParameterFunctionType", "getMatchingParameterFunctionType(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "correspondingProperty", "getCorrespondingProperty(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)Lorg/jetbrains/kotlin/fir/declarations/FirProperty;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "isJavaRecord", "isJavaRecord(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "isJavaRecordComponent", "isJavaRecordComponent(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "isCatchParameter", "isCatchParameter(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ClassMembersKt.class, Constants.TYPE_TREE), "delegatedWrapperData", "getDelegatedWrapperData(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/DelegatedWrapperData;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor containingClassForStaticMemberAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(ContainingClassKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor containingClassForLocalAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(ContainingClassKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor containingClassAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(ContainingClassKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor hasNoEnumEntriesAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(HasNoEnumEntriesKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isNewPlaceForBodyGeneration$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsNewPlaceForBodyGeneration.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor originalForSubstitutionOverrideAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(SubstitutedOverrideOriginalKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor originalForIntersectionOverrideAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(IntersectionOverrideOriginalKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor initialSignatureAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(InitialSignatureKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor matchingParameterFunctionType$delegate = FirDeclarationDataRegistry.INSTANCE.data(MatchingParameterFunctionTypeKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor correspondingProperty$delegate = FirDeclarationDataRegistry.INSTANCE.data(CorrespondingProperty.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isJavaRecord$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsJavaRecordKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isJavaRecordComponent$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsJavaRecordComponentKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isCatchParameter$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsCatchParameterProperty.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor delegatedWrapperData$delegate = FirDeclarationDataRegistry.INSTANCE.data(DelegatedWrapperDataKey.INSTANCE);

    public static final ConeClassLikeType dispatchReceiverClassTypeOrNull(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return dispatchReceiverClassTypeOrNull((FirCallableDeclaration) firCallableSymbol.getFir());
    }

    public static final ConeClassLikeType dispatchReceiverClassTypeOrNull(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        if (!(firCallableDeclaration.getDispatchReceiverType() instanceof ConeIntersectionType) || !isIntersectionOverride(firCallableDeclaration)) {
            ConeSimpleKotlinType dispatchReceiverType = firCallableDeclaration.getDispatchReceiverType();
            if (dispatchReceiverType instanceof ConeClassLikeType) {
                return (ConeClassLikeType) dispatchReceiverType;
            }
            return null;
        }
        FirCallableDeclaration originalForIntersectionOverrideAttr = isIntersectionOverride(firCallableDeclaration) ? getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
        Intrinsics.checkNotNull(originalForIntersectionOverrideAttr);
        return dispatchReceiverClassTypeOrNull(originalForIntersectionOverrideAttr);
    }

    public static final ConeClassLikeLookupTag dispatchReceiverClassLookupTagOrNull(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return dispatchReceiverClassLookupTagOrNull((FirCallableDeclaration) firCallableSymbol.getFir());
    }

    public static final ConeClassLikeLookupTag dispatchReceiverClassLookupTagOrNull(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        ConeClassLikeType coneClassLikeTypeDispatchReceiverClassTypeOrNull = dispatchReceiverClassTypeOrNull(firCallableDeclaration);
        if (coneClassLikeTypeDispatchReceiverClassTypeOrNull != null) {
            return coneClassLikeTypeDispatchReceiverClassTypeOrNull.getLookupTag();
        }
        return null;
    }

    public static final ConeClassLikeLookupTag containingClassLookupTag(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return containingClassLookupTag((FirCallableDeclaration) firCallableSymbol.getFir());
    }

    public static final ConeClassLikeLookupTag containingClassLookupTag(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        ConeClassLikeLookupTag containingClassForStaticMemberAttr = getContainingClassForStaticMemberAttr(firCallableDeclaration);
        return containingClassForStaticMemberAttr == null ? dispatchReceiverClassLookupTagOrNull(firCallableDeclaration) : containingClassForStaticMemberAttr;
    }

    public static final ConeClassLikeLookupTag containingClassForLocal(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        if (firRegularClass.getSymbol().getClassId().isLocal()) {
            return getContainingClassForLocalAttr(firRegularClass);
        }
        return null;
    }

    public static final ConeClassLikeLookupTag containingClass(FirDanglingModifierList firDanglingModifierList) {
        Intrinsics.checkNotNullParameter(firDanglingModifierList, "<this>");
        return getContainingClassAttr(firDanglingModifierList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeClassLikeLookupTag getContainingClassLookupTag(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        if (firClassLikeSymbol.getClassId().isLocal()) {
            E fir = firClassLikeSymbol.getFir();
            FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
            if (firRegularClass != null) {
                return containingClassForLocal(firRegularClass);
            }
            return null;
        }
        ClassId outerClassId = firClassLikeSymbol.getClassId().getOuterClassId();
        return outerClassId != null ? TypeConstructionUtilsKt.toLookupTag(outerClassId) : null;
    }

    public static final ConeClassLikeLookupTag getContainingClassForStaticMemberAttr(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return (ConeClassLikeLookupTag) containingClassForStaticMemberAttr$delegate.getValue(firCallableDeclaration, $$delegatedProperties[0]);
    }

    public static final void setContainingClassForStaticMemberAttr(FirCallableDeclaration firCallableDeclaration, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        containingClassForStaticMemberAttr$delegate.setValue(firCallableDeclaration, $$delegatedProperties[0], coneClassLikeLookupTag);
    }

    public static final ConeClassLikeLookupTag getContainingClassForLocalAttr(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (ConeClassLikeLookupTag) containingClassForLocalAttr$delegate.getValue(firRegularClass, $$delegatedProperties[1]);
    }

    public static final void setContainingClassForLocalAttr(FirRegularClass firRegularClass, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        containingClassForLocalAttr$delegate.setValue(firRegularClass, $$delegatedProperties[1], coneClassLikeLookupTag);
    }

    public static final ConeClassLikeLookupTag getContainingClassAttr(FirDanglingModifierList firDanglingModifierList) {
        Intrinsics.checkNotNullParameter(firDanglingModifierList, "<this>");
        return (ConeClassLikeLookupTag) containingClassAttr$delegate.getValue(firDanglingModifierList, $$delegatedProperties[2]);
    }

    public static final void setContainingClassAttr(FirDanglingModifierList firDanglingModifierList, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        Intrinsics.checkNotNullParameter(firDanglingModifierList, "<this>");
        containingClassAttr$delegate.setValue(firDanglingModifierList, $$delegatedProperties[2], coneClassLikeLookupTag);
    }

    public static final Boolean getHasNoEnumEntriesAttr(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return (Boolean) hasNoEnumEntriesAttr$delegate.getValue(firClass, $$delegatedProperties[3]);
    }

    public static final void setHasNoEnumEntriesAttr(FirClass firClass, Boolean bool) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        hasNoEnumEntriesAttr$delegate.setValue(firClass, $$delegatedProperties[3], bool);
    }

    public static final boolean getHasEnumEntries(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return !Intrinsics.areEqual((Object) getHasNoEnumEntriesAttr(firClass), (Object) true);
    }

    public static final Boolean isNewPlaceForBodyGeneration(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (Boolean) isNewPlaceForBodyGeneration$delegate.getValue(firRegularClass, $$delegatedProperties[4]);
    }

    public static final void setNewPlaceForBodyGeneration(FirRegularClass firRegularClass, Boolean bool) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        isNewPlaceForBodyGeneration$delegate.setValue(firRegularClass, $$delegatedProperties[4], bool);
    }

    public static final boolean isIntersectionOverride(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return Intrinsics.areEqual(firCallableDeclaration.getOrigin(), FirDeclarationOrigin.IntersectionOverride.INSTANCE);
    }

    public static final boolean isSubstitutionOverride(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride;
    }

    public static final boolean isSubstitutionOrIntersectionOverride(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return isSubstitutionOverride(firCallableDeclaration) || isIntersectionOverride(firCallableDeclaration);
    }

    public static final boolean isIntersectionOverride(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return Intrinsics.areEqual(firCallableSymbol.getOrigin(), FirDeclarationOrigin.IntersectionOverride.INSTANCE);
    }

    public static final boolean isSubstitutionOverride(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride;
    }

    public static final boolean isSubstitutionOrIntersectionOverride(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return isSubstitutionOverride(firCallableSymbol) || isIntersectionOverride(firCallableSymbol);
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D getOriginalForSubstitutionOverride(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        if (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
            return (D) getOriginalForSubstitutionOverrideAttr(d);
        }
        return null;
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S getOriginalForSubstitutionOverride(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null;
        Intrinsics.reifiedOperationMarker(1, "S?");
        return symbol;
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D getBaseForIntersectionOverride(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        if (isIntersectionOverride(d)) {
            return (D) getOriginalForIntersectionOverrideAttr(d);
        }
        return null;
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S getBaseForIntersectionOverride(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        FirCallableDeclaration originalForIntersectionOverrideAttr = isIntersectionOverride(firCallableDeclaration) ? getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = originalForIntersectionOverrideAttr != null ? originalForIntersectionOverrideAttr.getSymbol() : null;
        Intrinsics.reifiedOperationMarker(1, "S?");
        return symbol;
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S originalIfFakeOverride(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        if (originalForSubstitutionOverrideAttr == null) {
            originalForSubstitutionOverrideAttr = isIntersectionOverride(firCallableDeclaration) ? getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null;
        Intrinsics.reifiedOperationMarker(1, "S?");
        return symbol;
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D originalOrSelf(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        while (isSubstitutionOrIntersectionOverride(d)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = isIntersectionOverride(d) ? getOriginalForIntersectionOverrideAttr(d) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            d = (D) originalForSubstitutionOverrideAttr;
        }
        return d;
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S originalOrSelf(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        while (isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = isIntersectionOverride(firCallableDeclaration) ? getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.LATITUDE_SOUTH);
        return symbol;
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D unwrapUseSiteSubstitutionOverrides(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        while (Intrinsics.areEqual(d.getOrigin(), FirDeclarationOrigin.SubstitutionOverride.CallSite.INSTANCE)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                return d;
            }
            d = (D) originalForSubstitutionOverrideAttr;
        }
        return d;
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S unwrapFakeOverrides(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = isIntersectionOverride(firCallableDeclaration) ? getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
                Intrinsics.reifiedOperationMarker(1, ExifInterface.LATITUDE_SOUTH);
                return symbol;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
    }

    public static final /* synthetic */ <S extends FirCallableSymbol<?>> S unwrapSubstitutionOverrides(S s) {
        Intrinsics.checkNotNullParameter(s, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) s.getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
                Intrinsics.reifiedOperationMarker(1, ExifInterface.LATITUDE_SOUTH);
                return symbol;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
    }

    public static final <D extends FirCallableDeclaration> D getOriginalForSubstitutionOverrideAttr(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (D) originalForSubstitutionOverrideAttr$delegate.getValue(d, $$delegatedProperties[5]);
    }

    public static final <D extends FirCallableDeclaration> void setOriginalForSubstitutionOverrideAttr(D d, D d2) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        originalForSubstitutionOverrideAttr$delegate.setValue(d, $$delegatedProperties[5], d2);
    }

    public static final <D extends FirCallableDeclaration> D getOriginalForIntersectionOverrideAttr(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (D) originalForIntersectionOverrideAttr$delegate.getValue(d, $$delegatedProperties[6]);
    }

    public static final <D extends FirCallableDeclaration> void setOriginalForIntersectionOverrideAttr(D d, D d2) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        originalForIntersectionOverrideAttr$delegate.setValue(d, $$delegatedProperties[6], d2);
    }

    public static final FirCallableDeclaration getInitialSignatureAttr(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return (FirCallableDeclaration) initialSignatureAttr$delegate.getValue(firCallableDeclaration, $$delegatedProperties[7]);
    }

    public static final void setInitialSignatureAttr(FirCallableDeclaration firCallableDeclaration, FirCallableDeclaration firCallableDeclaration2) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        initialSignatureAttr$delegate.setValue(firCallableDeclaration, $$delegatedProperties[7], firCallableDeclaration2);
    }

    public static final <D extends FirAnonymousFunction> ConeKotlinType getMatchingParameterFunctionType(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (ConeKotlinType) matchingParameterFunctionType$delegate.getValue(d, $$delegatedProperties[8]);
    }

    public static final <D extends FirAnonymousFunction> void setMatchingParameterFunctionType(D d, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        matchingParameterFunctionType$delegate.setValue(d, $$delegatedProperties[8], coneKotlinType);
    }

    public static final FirProperty getCorrespondingProperty(FirValueParameter firValueParameter) {
        Intrinsics.checkNotNullParameter(firValueParameter, "<this>");
        return (FirProperty) correspondingProperty$delegate.getValue(firValueParameter, $$delegatedProperties[9]);
    }

    public static final void setCorrespondingProperty(FirValueParameter firValueParameter, FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firValueParameter, "<this>");
        correspondingProperty$delegate.setValue(firValueParameter, $$delegatedProperties[9], firProperty);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirCallableDeclaration getPropertyIfAccessor(FirCallableDeclaration firCallableDeclaration) {
        FirPropertySymbol propertySymbol;
        FirProperty firProperty;
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        FirPropertyAccessor firPropertyAccessor = firCallableDeclaration instanceof FirPropertyAccessor ? (FirPropertyAccessor) firCallableDeclaration : null;
        return (firPropertyAccessor == null || (propertySymbol = firPropertyAccessor.getPropertySymbol()) == null || (firProperty = (FirProperty) propertySymbol.getFir()) == null) ? firCallableDeclaration : firProperty;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirCallableDeclaration getPropertyIfBackingField(FirCallableDeclaration firCallableDeclaration) {
        FirPropertySymbol propertySymbol;
        FirProperty firProperty;
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        FirBackingField firBackingField = firCallableDeclaration instanceof FirBackingField ? (FirBackingField) firCallableDeclaration : null;
        return (firBackingField == null || (propertySymbol = firBackingField.getPropertySymbol()) == null || (firProperty = (FirProperty) propertySymbol.getFir()) == null) ? firCallableDeclaration : firProperty;
    }

    public static final Boolean isJavaRecord(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (Boolean) isJavaRecord$delegate.getValue(firRegularClass, $$delegatedProperties[10]);
    }

    public static final void setJavaRecord(FirRegularClass firRegularClass, Boolean bool) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        isJavaRecord$delegate.setValue(firRegularClass, $$delegatedProperties[10], bool);
    }

    public static final Boolean isJavaRecordComponent(FirFunction firFunction) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        return (Boolean) isJavaRecordComponent$delegate.getValue(firFunction, $$delegatedProperties[11]);
    }

    public static final void setJavaRecordComponent(FirFunction firFunction, Boolean bool) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        isJavaRecordComponent$delegate.setValue(firFunction, $$delegatedProperties[11], bool);
    }

    public static final Boolean isCatchParameter(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (Boolean) isCatchParameter$delegate.getValue(firProperty, $$delegatedProperties[12]);
    }

    public static final void setCatchParameter(FirProperty firProperty, Boolean bool) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        isCatchParameter$delegate.setValue(firProperty, $$delegatedProperties[12], bool);
    }

    public static final <D extends FirCallableDeclaration> DelegatedWrapperData<D> getDelegatedWrapperData(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (DelegatedWrapperData) delegatedWrapperData$delegate.getValue(d, $$delegatedProperties[13]);
    }

    public static final <D extends FirCallableDeclaration> void setDelegatedWrapperData(D d, DelegatedWrapperData<D> delegatedWrapperData) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        delegatedWrapperData$delegate.setValue(d, $$delegatedProperties[13], delegatedWrapperData);
    }

    public static final <D extends FirCallableDeclaration> DelegatedWrapperData<D> getDelegatedWrapperData(FirCallableSymbol<? extends D> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return getDelegatedWrapperData(firCallableSymbol.getFir());
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D originalIfFakeOverride(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
        if (originalForSubstitutionOverrideAttr != null) {
            return (D) originalForSubstitutionOverrideAttr;
        }
        if (isIntersectionOverride(d)) {
            return (D) getOriginalForIntersectionOverrideAttr(d);
        }
        return null;
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D originalIfFakeOverrideOrDelegated(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
        if (originalForSubstitutionOverrideAttr == null) {
            originalForSubstitutionOverrideAttr = isIntersectionOverride(d) ? getOriginalForIntersectionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                DelegatedWrapperData delegatedWrapperData = getDelegatedWrapperData(d);
                if (delegatedWrapperData != null) {
                    return (D) delegatedWrapperData.getWrapped();
                }
                return null;
            }
        }
        return (D) originalForSubstitutionOverrideAttr;
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D unwrapFakeOverrides(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = isIntersectionOverride(d) ? getOriginalForIntersectionOverrideAttr(d) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                return d;
            }
            d = (D) originalForSubstitutionOverrideAttr;
        }
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D unwrapFakeOverridesOrDelegated(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = isIntersectionOverride(d) ? getOriginalForIntersectionOverrideAttr(d) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    DelegatedWrapperData delegatedWrapperData = getDelegatedWrapperData(d);
                    originalForSubstitutionOverrideAttr = delegatedWrapperData != null ? delegatedWrapperData.getWrapped() : null;
                }
            }
            if (originalForSubstitutionOverrideAttr == null) {
                return d;
            }
            d = (D) originalForSubstitutionOverrideAttr;
        }
    }

    public static final /* synthetic */ <D extends FirCallableDeclaration> D unwrapSubstitutionOverrides(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (isSubstitutionOverride(d) || (d.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? getOriginalForSubstitutionOverrideAttr(d) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                return d;
            }
            d = (D) originalForSubstitutionOverrideAttr;
        }
    }
}
