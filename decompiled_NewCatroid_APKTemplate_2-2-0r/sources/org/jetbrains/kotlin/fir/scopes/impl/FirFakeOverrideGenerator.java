package org.jetbrains.kotlin.fir.scopes.impl;

import com.badlogic.gdx.graphics.GL30;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirVariableBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.resolve.substitution.ChainedSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideSubstitution;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculatorKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirFakeOverrideGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001^B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u008a\u0001\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00172\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0094\u0001\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020 2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJº\u0001\u0010)\u001a\u00020*2\u0006\u0010!\u001a\u00020+2\u0006\u0010,\u001a\u00020*2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0012\b\u0002\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ¦\u0001\u0010.\u001a\u00020/2\u0006\u0010!\u001a\u0002002\u0006\u00101\u001a\u00020/2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJN\u00102\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u0017\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u00020\f2\u0006\u0010\u0005\u001a\u0002062\n\u00107\u001a\u0006\u0012\u0002\b\u0003082\u0006\u00109\u001a\u0002042\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010:\u001a\u00020\u001cJ0\u0010;\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010#\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0010\u001a\u00020<J¢\u0001\u0010=\u001a\u00020+2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010>\u001a\u00020+2\u0006\u0010,\u001a\u00020*2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020<2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0098\u0001\u0010=\u001a\u00020*2\u0006\u0010\t\u001a\u00020+2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010,\u001a\u00020*2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0010\u001a\u00020<H\u0002J\u008c\u0001\u0010?\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010>\u001a\u0002002\u0006\u00101\u001a\u00020/2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020<2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00152\u0012\b\u0002\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u001a\u0010@\u001a\u00020+2\u0006\u0010A\u001a\u00020+2\n\b\u0002\u0010B\u001a\u0004\u0018\u00010CJ\u001a\u0010@\u001a\u0002002\u0006\u0010A\u001a\u0002002\n\b\u0002\u0010B\u001a\u0004\u0018\u00010CJ\u0010\u0010D\u001a\u00020\u001c2\u0006\u0010E\u001a\u00020\u0006H\u0002Jb\u0010F\u001a&\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0017\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150H0G2\u0006\u0010I\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u00109\u001a\u000204H\u0002JH\u0010J\u001a\u00020K*\u00020K2\u0006\u0010L\u001a\u00020M2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u0002002\b\u0010Q\u001a\u0004\u0018\u00010\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u00101\u001a\u00020/H\u0002JJ\u0010R\u001a\u0004\u0018\u00010K*\u00020K2\u0006\u0010L\u001a\u00020M2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u0002002\b\u0010Q\u001a\u0004\u0018\u00010\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u00101\u001a\u00020/H\u0002Jj\u0010S\u001a\u00020\u0004*\u00020T2\u0006\u0010,\u001a\u00020U2\n\u0010V\u001a\u0006\u0012\u0002\b\u00030W2\u0010\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002JN\u0010S\u001a\u00020\u0004*\u00020X2\u0006\u0010Y\u001a\u00020Z2\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010[\u001a\u00020\u001cH\u0002J\u0080\u0001\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017*\u00020T2\u0006\u00105\u001a\u00020\f2\u0006\u0010,\u001a\u00020U2\u0010\u0010\u0016\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00172\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\u00107\u001a\u0006\u0012\u0002\b\u00030WH\u0002Jb\u0010\\\u001a\b\u0012\u0004\u0012\u00020-0\u0017*\u00020]2\u0006\u00105\u001a\u00020\f2\u0006\u00101\u001a\u00020/2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\u00172\b\u0010$\u001a\u0004\u0018\u00010\u00152\u0010\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator;", "", "()V", "checkStatusIsResolved", "", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "createCopyForFirConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "fakeOverrideSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseConstructor", "derivedClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "newDispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "newReturnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "newParameterTypes", "", "newContextReceiverTypes", "newTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "isExpect", "", "fakeOverrideSubstitution", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "createCopyForFirField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "newSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "baseField", "newReceiverType", "newModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "newVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "createCopyForFirFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "baseFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "createCopyForFirProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "baseProperty", "createNewTypeParametersAndSubstitutor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "symbolForOverride", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "substitutor", "forceTypeParametersRecreation", "createSubstitutionOverrideField", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "createSubstitutionOverrideFunction", "symbolForSubstitutionOverride", "createSubstitutionOverrideProperty", "createSymbolForSubstitutionOverride", "baseSymbol", "derivedClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "shouldOverrideSetContainingClass", "baseDeclaration", "substituteReceiverAndReturnType", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe;", "baseCallable", "buildCopy", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "propertyReturnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "propertySymbol", "dispatchReceiverType", "buildCopyIfNeeded", "configureAnnotationsAndSignature", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "fakeFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirVariableBuilder;", "baseVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "updateReceiver", "configureAnnotationsTypeParametersAndSignature", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyBuilder;", "Maybe", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFakeOverrideGenerator {
    public static final FirFakeOverrideGenerator INSTANCE = new FirFakeOverrideGenerator();

    private FirFakeOverrideGenerator() {
    }

    public final FirNamedFunctionSymbol createSubstitutionOverrideFunction(FirSession session, FirNamedFunctionSymbol symbolForSubstitutionOverride, FirSimpleFunction baseFunction, ConeClassLikeLookupTag derivedClassLookupTag, ConeSimpleKotlinType newDispatchReceiverType, FirDeclarationOrigin.SubstitutionOverride origin, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, List<? extends ConeKotlinType> newParameterTypes, List<? extends FirTypeParameter> newTypeParameters, boolean isExpect, FakeOverrideSubstitution fakeOverrideSubstitution) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(symbolForSubstitutionOverride, "symbolForSubstitutionOverride");
        Intrinsics.checkNotNullParameter(baseFunction, "baseFunction");
        Intrinsics.checkNotNullParameter(origin, "origin");
        createSubstitutionOverrideFunction(symbolForSubstitutionOverride, session, baseFunction, derivedClassLookupTag, newDispatchReceiverType, newReceiverType, newContextReceiverTypes, newReturnType, newParameterTypes, newTypeParameters, isExpect, fakeOverrideSubstitution, origin);
        return symbolForSubstitutionOverride;
    }

    public static /* synthetic */ FirNamedFunctionSymbol createSymbolForSubstitutionOverride$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirNamedFunctionSymbol firNamedFunctionSymbol, ClassId classId, int i, Object obj) {
        if ((i & 2) != 0) {
            classId = null;
        }
        return firFakeOverrideGenerator.createSymbolForSubstitutionOverride(firNamedFunctionSymbol, classId);
    }

    public final FirNamedFunctionSymbol createSymbolForSubstitutionOverride(FirNamedFunctionSymbol baseSymbol, ClassId derivedClassId) {
        Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
        if (derivedClassId == null) {
            return new FirNamedFunctionSymbol(baseSymbol.getCallableId());
        }
        return new FirNamedFunctionSymbol(new CallableId(derivedClassId, baseSymbol.getCallableId().getCallableName()));
    }

    private final FirSimpleFunction createSubstitutionOverrideFunction(FirNamedFunctionSymbol fakeOverrideSymbol, FirSession session, FirSimpleFunction baseFunction, ConeClassLikeLookupTag derivedClassLookupTag, ConeSimpleKotlinType newDispatchReceiverType, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, List<? extends ConeKotlinType> newParameterTypes, List<? extends FirTypeParameter> newTypeParameters, boolean isExpect, FakeOverrideSubstitution fakeOverrideSubstitution, FirDeclarationOrigin.SubstitutionOverride origin) {
        FirSimpleFunction firSimpleFunctionCreateCopyForFirFunction$default = createCopyForFirFunction$default(this, fakeOverrideSymbol, baseFunction, derivedClassLookupTag, session, origin, isExpect, newDispatchReceiverType, newParameterTypes, newTypeParameters, newReceiverType, newContextReceiverTypes, newReturnType, null, null, fakeOverrideSubstitution, 12288, null);
        ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firSimpleFunctionCreateCopyForFirFunction$default, baseFunction);
        return firSimpleFunctionCreateCopyForFirFunction$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirSimpleFunction createCopyForFirFunction$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirNamedFunctionSymbol firNamedFunctionSymbol, FirSimpleFunction firSimpleFunction, ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession firSession, FirDeclarationOrigin firDeclarationOrigin, boolean z, ConeSimpleKotlinType coneSimpleKotlinType, List list, List list2, ConeKotlinType coneKotlinType, List list3, ConeKotlinType coneKotlinType2, Modality modality, Visibility visibility, FakeOverrideSubstitution fakeOverrideSubstitution, int i, Object obj) {
        return firFakeOverrideGenerator.createCopyForFirFunction(firNamedFunctionSymbol, firSimpleFunction, coneClassLikeLookupTag, firSession, firDeclarationOrigin, (i & 32) != 0 ? firSimpleFunction.getStatus().isExpect() : z, coneSimpleKotlinType, (i & 128) != 0 ? null : list, (i & 256) != 0 ? null : list2, (i & 512) != 0 ? null : coneKotlinType, (i & 1024) != 0 ? null : list3, (i & 2048) != 0 ? null : coneKotlinType2, (i & 4096) != 0 ? null : modality, (i & 8192) != 0 ? null : visibility, (i & 16384) != 0 ? null : fakeOverrideSubstitution);
    }

    public final FirSimpleFunction createCopyForFirFunction(FirNamedFunctionSymbol newSymbol, FirSimpleFunction baseFunction, ConeClassLikeLookupTag derivedClassLookupTag, FirSession session, FirDeclarationOrigin origin, boolean isExpect, ConeSimpleKotlinType newDispatchReceiverType, List<? extends ConeKotlinType> newParameterTypes, List<? extends FirTypeParameter> newTypeParameters, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, Modality newModality, Visibility newVisibility, FakeOverrideSubstitution fakeOverrideSubstitution) {
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        Intrinsics.checkNotNullParameter(baseFunction, "baseFunction");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirSimpleFunction firSimpleFunction = baseFunction;
        checkStatusIsResolved(firSimpleFunction);
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(baseFunction.getSource());
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(session);
        if (nullableModuleData == null) {
            nullableModuleData = baseFunction.getModuleData();
        }
        firSimpleFunctionBuilder.setModuleData(nullableModuleData);
        firSimpleFunctionBuilder.setOrigin(origin);
        firSimpleFunctionBuilder.setName(baseFunction.getName());
        FirDeclarationStatus status = baseFunction.getStatus();
        firSimpleFunctionBuilder.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : newVisibility, (2097147 & 2) != 0 ? status.getModality() : newModality, (2097147 & 4) != 0 ? status.isExpect() : isExpect, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
        firSimpleFunctionBuilder.setSymbol(newSymbol);
        firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(baseFunction));
        firSimpleFunctionBuilder.setDispatchReceiverType(newDispatchReceiverType);
        firSimpleFunctionBuilder.setAttributes(baseFunction.getAttributes().copy());
        List<FirTypeParameter> typeParameters = firSimpleFunctionBuilder.getTypeParameters();
        List<FirTypeParameterRef> listConfigureAnnotationsTypeParametersAndSignature = INSTANCE.configureAnnotationsTypeParametersAndSignature(firSimpleFunctionBuilder, session, baseFunction, newParameterTypes, newTypeParameters, newReceiverType, newContextReceiverTypes, newReturnType, fakeOverrideSubstitution, newSymbol);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listConfigureAnnotationsTypeParametersAndSignature) {
            if (obj instanceof FirTypeParameter) {
                arrayList.add(obj);
            }
        }
        CollectionsKt.addAll(typeParameters, arrayList);
        firSimpleFunctionBuilder.setDeprecationsProvider(baseFunction.getDeprecationsProvider());
        FirSimpleFunction firSimpleFunctionBuild = firSimpleFunctionBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firSimpleFunctionBuild, Boolean.valueOf(INSTANCE.shouldOverrideSetContainingClass(firSimpleFunction)).booleanValue() ? derivedClassLookupTag : null);
        return firSimpleFunctionBuild;
    }

    public final FirConstructor createCopyForFirConstructor(FirConstructorSymbol fakeOverrideSymbol, FirSession session, FirConstructor baseConstructor, ConeClassLikeLookupTag derivedClassLookupTag, FirDeclarationOrigin origin, ConeSimpleKotlinType newDispatchReceiverType, ConeKotlinType newReturnType, List<? extends ConeKotlinType> newParameterTypes, List<? extends ConeKotlinType> newContextReceiverTypes, List<? extends FirTypeParameterRef> newTypeParameters, boolean isExpect, FakeOverrideSubstitution fakeOverrideSubstitution) {
        FirReceiverParameter firReceiverParameterBuild;
        Intrinsics.checkNotNullParameter(fakeOverrideSymbol, "fakeOverrideSymbol");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(baseConstructor, "baseConstructor");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirConstructor firConstructor = baseConstructor;
        checkStatusIsResolved(firConstructor);
        FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
        CollectionsKt.addAll(firConstructorBuilder.getAnnotations(), baseConstructor.getAnnotations());
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(session);
        if (nullableModuleData == null) {
            nullableModuleData = baseConstructor.getModuleData();
        }
        firConstructorBuilder.setModuleData(nullableModuleData);
        firConstructorBuilder.setOrigin(origin);
        FirReceiverParameter receiverParameter = baseConstructor.getReceiverParameter();
        if (receiverParameter != null) {
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setSource(receiverParameter.getSource());
            firReceiverParameterBuilder.setTypeRef(receiverParameter.getTypeRef());
            firReceiverParameterBuilder.getAnnotations().addAll(receiverParameter.getAnnotations());
            firReceiverParameterBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType$default(receiverParameter.getTypeRef(), null, null, 2, null));
            firReceiverParameterBuild = firReceiverParameterBuilder.mo6809build();
        } else {
            firReceiverParameterBuild = null;
        }
        firConstructorBuilder.setReceiverParameter(firReceiverParameterBuild);
        FirDeclarationStatus status = baseConstructor.getStatus();
        firConstructorBuilder.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : null, (2097147 & 4) != 0 ? status.isExpect() : isExpect, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
        firConstructorBuilder.setSymbol(fakeOverrideSymbol);
        List<FirTypeParameterRef> typeParameters = firConstructorBuilder.getTypeParameters();
        FirFakeOverrideGenerator firFakeOverrideGenerator = INSTANCE;
        CollectionsKt.addAll(typeParameters, firFakeOverrideGenerator.configureAnnotationsTypeParametersAndSignature(firConstructorBuilder, session, baseConstructor, newParameterTypes, newTypeParameters, null, newContextReceiverTypes, newReturnType, fakeOverrideSubstitution, fakeOverrideSymbol));
        firConstructorBuilder.setDispatchReceiverType(newDispatchReceiverType);
        firConstructorBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(baseConstructor));
        firConstructorBuilder.setSource(baseConstructor.getSource());
        firConstructorBuilder.setAttributes(baseConstructor.getAttributes().copy());
        firConstructorBuilder.setDeprecationsProvider(baseConstructor.getDeprecationsProvider());
        FirConstructor firConstructorBuild = firConstructorBuilder.mo6809build();
        FirConstructor firConstructor2 = firConstructorBuild;
        ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firConstructor2, firConstructor);
        ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructor2, Boolean.valueOf(firFakeOverrideGenerator.shouldOverrideSetContainingClass(firConstructor)).booleanValue() ? derivedClassLookupTag : null);
        return firConstructorBuild;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<FirTypeParameterRef> configureAnnotationsTypeParametersAndSignature(FirFunctionBuilder firFunctionBuilder, FirSession firSession, FirFunction firFunction, List<? extends ConeKotlinType> list, List<? extends FirTypeParameterRef> list2, ConeKotlinType coneKotlinType, List<? extends ConeKotlinType> list3, ConeKotlinType coneKotlinType2, FakeOverrideSubstitution fakeOverrideSubstitution, FirFunctionSymbol<?> firFunctionSymbol) {
        if (firFunction.getTypeParameters().isEmpty()) {
            configureAnnotationsAndSignature(firFunctionBuilder, firFunction, firFunctionSymbol, list, coneKotlinType, list3, coneKotlinType2, fakeOverrideSubstitution, firFunctionBuilder.getOrigin());
            return CollectionsKt.emptyList();
        }
        if (list2 == 0) {
            Pair pairCreateNewTypeParametersAndSubstitutor$default = createNewTypeParametersAndSubstitutor$default(this, firSession, firFunction, firFunctionSymbol, ConeSubstitutor.Empty.INSTANCE, firFunctionBuilder.getOrigin(), false, 32, null);
            List<FirTypeParameterRef> list4 = (List) pairCreateNewTypeParametersAndSubstitutor$default.component1();
            ConeSubstitutor coneSubstitutor = (ConeSubstitutor) pairCreateNewTypeParametersAndSubstitutor$default.component2();
            List<FirValueParameter> valueParameters = firFunction.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(coneSubstitutor.substituteOrNull(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef())));
            }
            ArrayList arrayList2 = arrayList;
            FirFunctionSymbol<? extends FirFunction> symbol = firFunction.getSymbol();
            Intrinsics.checkNotNull(firFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
            Triple<ConeKotlinType, List<ConeKotlinType>, Maybe<ConeKotlinType>> tripleSubstituteReceiverAndReturnType = substituteReceiverAndReturnType(firFunction, coneKotlinType, list3, coneKotlinType2, coneSubstitutor);
            ConeKotlinType coneKotlinTypeComponent1 = tripleSubstituteReceiverAndReturnType.component1();
            List<ConeKotlinType> listComponent2 = tripleSubstituteReceiverAndReturnType.component2();
            Maybe<ConeKotlinType> maybeComponent3 = tripleSubstituteReceiverAndReturnType.component3();
            Pair pair = maybeComponent3 instanceof Maybe.Value ? TuplesKt.to(((Maybe.Value) maybeComponent3).getValue(), null) : TuplesKt.to(null, new FakeOverrideSubstitution(coneSubstitutor, symbol));
            configureAnnotationsAndSignature(firFunctionBuilder, firFunction, firFunctionSymbol, arrayList2, coneKotlinTypeComponent1, listComponent2, (ConeKotlinType) pair.component1(), (FakeOverrideSubstitution) pair.component2(), firFunctionBuilder.getOrigin());
            return list4;
        }
        configureAnnotationsAndSignature(firFunctionBuilder, firFunction, firFunctionSymbol, list, coneKotlinType, list3, coneKotlinType2, fakeOverrideSubstitution, firFunctionBuilder.getOrigin());
        return list2;
    }

    private final void configureAnnotationsAndSignature(FirFunctionBuilder firFunctionBuilder, FirFunction firFunction, FirFunctionSymbol<?> firFunctionSymbol, List<? extends ConeKotlinType> list, ConeKotlinType coneKotlinType, List<? extends ConeKotlinType> list2, ConeKotlinType coneKotlinType2, FakeOverrideSubstitution fakeOverrideSubstitution, FirDeclarationOrigin firDeclarationOrigin) {
        FakeOverrideSubstitution fakeOverrideSubstitution2;
        ArrayList arrayList;
        ArrayList arrayList2;
        FirReceiverParameter firReceiverParameterBuild;
        CollectionsKt.addAll(firFunctionBuilder.getAnnotations(), firFunction.getAnnotations());
        if (fakeOverrideSubstitution == null) {
            fakeOverrideSubstitution2 = firFunction.getReturnTypeRef() instanceof FirImplicitTypeRef ? new FakeOverrideSubstitution(ConeSubstitutor.Empty.INSTANCE, firFunction.getSymbol()) : null;
        } else {
            fakeOverrideSubstitution2 = fakeOverrideSubstitution;
        }
        if (fakeOverrideSubstitution2 != null) {
            firFunctionBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            FakeOverrideTypeCalculatorKt.setFakeOverrideSubstitution(firFunctionBuilder.getAttributes(), fakeOverrideSubstitution2);
        } else {
            firFunctionBuilder.setReturnTypeRef(TypeUtilsKt.withReplacedReturnType(firFunction.getReturnTypeRef(), coneKotlinType2));
        }
        if (firFunctionBuilder instanceof FirSimpleFunctionBuilder) {
            FirSimpleFunctionBuilder firSimpleFunctionBuilder = (FirSimpleFunctionBuilder) firFunctionBuilder;
            FirReceiverParameter receiverParameter = firFunction.getReceiverParameter();
            if (receiverParameter != null) {
                FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
                firReceiverParameterBuilder.setSource(receiverParameter.getSource());
                firReceiverParameterBuilder.setTypeRef(receiverParameter.getTypeRef());
                firReceiverParameterBuilder.getAnnotations().addAll(receiverParameter.getAnnotations());
                firReceiverParameterBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType$default(receiverParameter.getTypeRef(), coneKotlinType, null, 2, null));
                firReceiverParameterBuild = firReceiverParameterBuilder.mo6809build();
            } else {
                firReceiverParameterBuild = null;
            }
            firSimpleFunctionBuilder.setReceiverParameter(firReceiverParameterBuild);
        }
        List<FirValueParameter> valueParameters = firFunctionBuilder.getValueParameters();
        List<FirValueParameter> valueParameters2 = firFunction.getValueParameters();
        if (list == null) {
            int size = firFunction.getValueParameters().size();
            ArrayList arrayList3 = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList3.add(null);
            }
            arrayList = arrayList3;
        } else {
            arrayList = list;
        }
        List<? extends ConeKotlinType> list3 = arrayList;
        Iterator<T> it = valueParameters2.iterator();
        Iterator<T> it2 = list3.iterator();
        ArrayList arrayList4 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10), CollectionsKt.collectionSizeOrDefault(list3, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            ConeKotlinType coneKotlinType3 = (ConeKotlinType) it2.next();
            FirValueParameter firValueParameter = (FirValueParameter) next;
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
            firValueParameterBuilder.setOrigin(firDeclarationOrigin);
            firValueParameterBuilder.setReturnTypeRef(TypeUtilsKt.withReplacedConeType$default(firValueParameter.getReturnTypeRef(), coneKotlinType3, null, 2, null));
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameter.getName()));
            firValueParameterBuilder.setContainingFunctionSymbol(firFunctionSymbol);
            FirValueParameter firValueParameterBuild = firValueParameterBuilder.mo6809build();
            ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firValueParameterBuild, firValueParameter);
            arrayList4.add(firValueParameterBuild);
        }
        CollectionsKt.addAll(valueParameters, arrayList4);
        List<FirContextReceiver> contextReceivers = firFunctionBuilder.getContextReceivers();
        List<FirContextReceiver> contextReceivers2 = firFunction.getContextReceivers();
        if (list2 == null) {
            int size2 = firFunction.getContextReceivers().size();
            ArrayList arrayList5 = new ArrayList(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                arrayList5.add(null);
            }
            arrayList2 = arrayList5;
        } else {
            arrayList2 = list2;
        }
        List<? extends ConeKotlinType> list4 = arrayList2;
        Iterator<T> it3 = contextReceivers2.iterator();
        Iterator<T> it4 = list4.iterator();
        ArrayList arrayList6 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(contextReceivers2, 10), CollectionsKt.collectionSizeOrDefault(list4, 10)));
        while (it3.hasNext() && it4.hasNext()) {
            Object next2 = it3.next();
            ConeKotlinType coneKotlinType4 = (ConeKotlinType) it4.next();
            FirContextReceiver firContextReceiver = (FirContextReceiver) next2;
            FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
            firContextReceiverBuilder.setSource(firContextReceiver.getSource());
            firContextReceiverBuilder.setTypeRef(firContextReceiver.getTypeRef());
            firContextReceiverBuilder.setCustomLabelName(firContextReceiver.getCustomLabelName());
            firContextReceiverBuilder.setLabelNameFromTypeRef(firContextReceiver.getLabelNameFromTypeRef());
            firContextReceiverBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType$default(firContextReceiver.getTypeRef(), coneKotlinType4, null, 2, null));
            arrayList6.add(firContextReceiverBuilder.build());
        }
        CollectionsKt.addAll(contextReceivers, arrayList6);
    }

    public final FirPropertySymbol createSubstitutionOverrideProperty(FirSession session, FirPropertySymbol symbolForSubstitutionOverride, FirProperty baseProperty, ConeClassLikeLookupTag derivedClassLookupTag, ConeSimpleKotlinType newDispatchReceiverType, FirDeclarationOrigin.SubstitutionOverride origin, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, List<? extends FirTypeParameter> newTypeParameters, boolean isExpect, FakeOverrideSubstitution fakeOverrideSubstitution) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(symbolForSubstitutionOverride, "symbolForSubstitutionOverride");
        Intrinsics.checkNotNullParameter(baseProperty, "baseProperty");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        Intrinsics.checkNotNullParameter(origin, "origin");
        ClassMembersKt.setOriginalForSubstitutionOverrideAttr(createCopyForFirProperty$default(this, symbolForSubstitutionOverride, baseProperty, derivedClassLookupTag, session, origin, isExpect, newDispatchReceiverType, newTypeParameters, newReceiverType, newContextReceiverTypes, newReturnType, null, null, fakeOverrideSubstitution, GL30.GL_COLOR, null), baseProperty);
        return symbolForSubstitutionOverride;
    }

    public static /* synthetic */ FirPropertySymbol createSymbolForSubstitutionOverride$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirPropertySymbol firPropertySymbol, ClassId classId, int i, Object obj) {
        if ((i & 2) != 0) {
            classId = null;
        }
        return firFakeOverrideGenerator.createSymbolForSubstitutionOverride(firPropertySymbol, classId);
    }

    public final FirPropertySymbol createSymbolForSubstitutionOverride(FirPropertySymbol baseSymbol, ClassId derivedClassId) {
        Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
        if (derivedClassId == null) {
            return new FirPropertySymbol(baseSymbol.getCallableId());
        }
        return new FirPropertySymbol(new CallableId(derivedClassId, baseSymbol.getCallableId().getCallableName()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirProperty createCopyForFirProperty$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirPropertySymbol firPropertySymbol, FirProperty firProperty, ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession firSession, FirDeclarationOrigin firDeclarationOrigin, boolean z, ConeSimpleKotlinType coneSimpleKotlinType, List list, ConeKotlinType coneKotlinType, List list2, ConeKotlinType coneKotlinType2, Modality modality, Visibility visibility, FakeOverrideSubstitution fakeOverrideSubstitution, int i, Object obj) {
        return firFakeOverrideGenerator.createCopyForFirProperty(firPropertySymbol, firProperty, coneClassLikeLookupTag, firSession, firDeclarationOrigin, (i & 32) != 0 ? firProperty.getStatus().isExpect() : z, coneSimpleKotlinType, (i & 128) != 0 ? null : list, (i & 256) != 0 ? null : coneKotlinType, (i & 512) != 0 ? null : list2, (i & 1024) != 0 ? null : coneKotlinType2, (i & 2048) != 0 ? null : modality, (i & 4096) != 0 ? null : visibility, (i & 8192) != 0 ? null : fakeOverrideSubstitution);
    }

    public final FirProperty createCopyForFirProperty(FirPropertySymbol newSymbol, FirProperty baseProperty, ConeClassLikeLookupTag derivedClassLookupTag, FirSession session, FirDeclarationOrigin origin, boolean isExpect, ConeSimpleKotlinType newDispatchReceiverType, List<? extends FirTypeParameter> newTypeParameters, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, Modality newModality, Visibility newVisibility, FakeOverrideSubstitution fakeOverrideSubstitution) {
        FirPropertyAccessor firPropertyAccessorBuildCopyIfNeeded;
        FirPropertyAccessor firPropertyAccessorBuildCopyIfNeeded2;
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        Intrinsics.checkNotNullParameter(baseProperty, "baseProperty");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirProperty firProperty = baseProperty;
        checkStatusIsResolved(firProperty);
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(baseProperty.getSource());
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(session);
        if (nullableModuleData == null) {
            nullableModuleData = baseProperty.getModuleData();
        }
        firPropertyBuilder.setModuleData(nullableModuleData);
        firPropertyBuilder.setOrigin(origin);
        firPropertyBuilder.setName(baseProperty.getName());
        firPropertyBuilder.setVar(baseProperty.getIsVar());
        firPropertyBuilder.setSymbol(newSymbol);
        firPropertyBuilder.setLocal(false);
        FirDeclarationStatus status = baseProperty.getStatus();
        firPropertyBuilder.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : newVisibility, (2097147 & 2) != 0 ? status.getModality() : newModality, (2097147 & 4) != 0 ? status.isExpect() : isExpect, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
        firPropertyBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(baseProperty));
        firPropertyBuilder.setDispatchReceiverType(newDispatchReceiverType);
        firPropertyBuilder.setAttributes(baseProperty.getAttributes().copy());
        List<FirTypeParameter> typeParameters = firPropertyBuilder.getTypeParameters();
        FirFakeOverrideGenerator firFakeOverrideGenerator = INSTANCE;
        CollectionsKt.addAll(typeParameters, firFakeOverrideGenerator.configureAnnotationsTypeParametersAndSignature(firPropertyBuilder, session, baseProperty, newTypeParameters, newReceiverType, newContextReceiverTypes, newReturnType, fakeOverrideSubstitution));
        firPropertyBuilder.setDeprecationsProvider(baseProperty.getDeprecationsProvider());
        FirPropertyAccessor getter = baseProperty.getGetter();
        if (getter != null) {
            FirModuleData nullableModuleData2 = FirModuleDataKt.getNullableModuleData(session);
            if (nullableModuleData2 == null) {
                nullableModuleData2 = baseProperty.getModuleData();
            }
            firPropertyAccessorBuildCopyIfNeeded = firFakeOverrideGenerator.buildCopyIfNeeded(getter, nullableModuleData2, origin, firPropertyBuilder.getReturnTypeRef(), newSymbol, firPropertyBuilder.getDispatchReceiverType(), derivedClassLookupTag, baseProperty);
        } else {
            firPropertyAccessorBuildCopyIfNeeded = null;
        }
        firPropertyBuilder.setGetter(firPropertyAccessorBuildCopyIfNeeded);
        FirPropertyAccessor setter = baseProperty.getSetter();
        if (setter != null) {
            FirModuleData nullableModuleData3 = FirModuleDataKt.getNullableModuleData(session);
            if (nullableModuleData3 == null) {
                nullableModuleData3 = baseProperty.getModuleData();
            }
            firPropertyAccessorBuildCopyIfNeeded2 = firFakeOverrideGenerator.buildCopyIfNeeded(setter, nullableModuleData3, origin, firPropertyBuilder.getReturnTypeRef(), newSymbol, firPropertyBuilder.getDispatchReceiverType(), derivedClassLookupTag, baseProperty);
        } else {
            firPropertyAccessorBuildCopyIfNeeded2 = null;
        }
        firPropertyBuilder.setSetter(firPropertyAccessorBuildCopyIfNeeded2);
        FirProperty firPropertyBuild = firPropertyBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firPropertyBuild, Boolean.valueOf(firFakeOverrideGenerator.shouldOverrideSetContainingClass(firProperty)).booleanValue() ? derivedClassLookupTag : null);
        return firPropertyBuild;
    }

    private final FirPropertyAccessor buildCopyIfNeeded(FirPropertyAccessor firPropertyAccessor, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirTypeRef firTypeRef, FirPropertySymbol firPropertySymbol, ConeSimpleKotlinType coneSimpleKotlinType, ConeClassLikeLookupTag coneClassLikeLookupTag, FirProperty firProperty) {
        if (firPropertyAccessor.getAnnotations().isEmpty() && Intrinsics.areEqual(firPropertyAccessor.getStatus().getVisibility(), firProperty.getStatus().getVisibility())) {
            return null;
        }
        return buildCopy(firPropertyAccessor, firModuleData, firDeclarationOrigin, firTypeRef, firPropertySymbol, coneSimpleKotlinType, coneClassLikeLookupTag, firProperty);
    }

    private final FirPropertyAccessor buildCopy(FirPropertyAccessor firPropertyAccessor, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirTypeRef firTypeRef, FirPropertySymbol firPropertySymbol, ConeSimpleKotlinType coneSimpleKotlinType, ConeClassLikeLookupTag coneClassLikeLookupTag, FirProperty firProperty) {
        FirDefaultPropertySetter firDefaultPropertySetterBuild;
        EffectiveVisibility.Local effectiveVisibility;
        EffectiveVisibility.Local effectiveVisibility2;
        if (firPropertyAccessor instanceof FirDefaultPropertyGetter) {
            KtSourceElement source = firPropertyAccessor.getSource();
            FirPropertyAccessor firPropertyAccessor2 = firPropertyAccessor;
            Visibility visibility = firPropertyAccessor2.getStatus().getVisibility();
            Modality modality = firPropertyAccessor2.getStatus().getModality();
            if (modality == null) {
                modality = Modality.FINAL;
            }
            Modality modality2 = modality;
            FirDeclarationStatus status = firPropertyAccessor2.getStatus();
            FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
            if (firResolvedDeclarationStatus == null || (effectiveVisibility2 = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
                effectiveVisibility2 = EffectiveVisibility.Local.INSTANCE;
            }
            FirDefaultPropertyGetter firDefaultPropertyGetter = new FirDefaultPropertyGetter(source, firModuleData, firDeclarationOrigin, firTypeRef, visibility, firPropertySymbol, modality2, effectiveVisibility2, null, FirResolveStateKt.getResolvePhase(firPropertyAccessor), 256, null);
            firDefaultPropertyGetter.replaceAnnotations(MutableOrEmptyList.m6756boximpl(firDefaultPropertyGetter.m6857getAnnotations5e3fPpI()));
            firDefaultPropertySetterBuild = firDefaultPropertyGetter;
        } else if (firPropertyAccessor instanceof FirDefaultPropertySetter) {
            KtSourceElement source2 = firPropertyAccessor.getSource();
            FirPropertyAccessor firPropertyAccessor3 = firPropertyAccessor;
            Visibility visibility2 = firPropertyAccessor3.getStatus().getVisibility();
            Modality modality3 = firPropertyAccessor3.getStatus().getModality();
            if (modality3 == null) {
                modality3 = Modality.FINAL;
            }
            Modality modality4 = modality3;
            FirDeclarationStatus status2 = firPropertyAccessor3.getStatus();
            FirResolvedDeclarationStatus firResolvedDeclarationStatus2 = status2 instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status2 : null;
            if (firResolvedDeclarationStatus2 == null || (effectiveVisibility = firResolvedDeclarationStatus2.getEffectiveVisibility()) == null) {
                effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
            }
            FirDefaultPropertySetter firDefaultPropertySetter = new FirDefaultPropertySetter(source2, firModuleData, firDeclarationOrigin, firTypeRef, visibility2, firPropertySymbol, modality4, effectiveVisibility, null, null, FirResolveStateKt.getResolvePhase(firPropertyAccessor), 768, null);
            firDefaultPropertySetter.replaceAnnotations(MutableOrEmptyList.m6756boximpl(firDefaultPropertySetter.m6857getAnnotations5e3fPpI()));
            firDefaultPropertySetterBuild = firDefaultPropertySetter;
        } else {
            FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
            firPropertyAccessorBuilder.setSource(firPropertyAccessor.getSource());
            firPropertyAccessorBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firPropertyAccessor));
            firPropertyAccessorBuilder.setModuleData(firPropertyAccessor.getModuleData());
            firPropertyAccessorBuilder.setOrigin(firPropertyAccessor.getOrigin());
            firPropertyAccessorBuilder.setAttributes(firPropertyAccessor.getAttributes().copy());
            firPropertyAccessorBuilder.setStatus(firPropertyAccessor.getStatus());
            firPropertyAccessorBuilder.setReturnTypeRef(firPropertyAccessor.getReturnTypeRef());
            firPropertyAccessorBuilder.setDeprecationsProvider(firPropertyAccessor.getDeprecationsProvider());
            firPropertyAccessorBuilder.setContainerSource(firPropertyAccessor.getContainerSource());
            firPropertyAccessorBuilder.setDispatchReceiverType(firPropertyAccessor.getDispatchReceiverType());
            firPropertyAccessorBuilder.getContextReceivers().addAll(firPropertyAccessor.getContextReceivers());
            firPropertyAccessorBuilder.getValueParameters().addAll(firPropertyAccessor.getValueParameters());
            firPropertyAccessorBuilder.setBody(firPropertyAccessor.getBody());
            firPropertyAccessorBuilder.setContractDescription(firPropertyAccessor.getContractDescription());
            firPropertyAccessorBuilder.setSymbol(firPropertyAccessor.getSymbol());
            firPropertyAccessorBuilder.setPropertySymbol(firPropertyAccessor.getPropertySymbol());
            firPropertyAccessorBuilder.setGetter(firPropertyAccessor.getIsGetter());
            firPropertyAccessorBuilder.getAnnotations().addAll(firPropertyAccessor.getAnnotations());
            firPropertyAccessorBuilder.getTypeParameters().addAll(firPropertyAccessor.getTypeParameters());
            firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
            firPropertyAccessorBuilder.setModuleData(firModuleData);
            firPropertyAccessorBuilder.setOrigin(firDeclarationOrigin);
            firPropertyAccessorBuilder.setPropertySymbol(firPropertySymbol);
            firPropertyAccessorBuilder.setDispatchReceiverType(coneSimpleKotlinType);
            firPropertyAccessorBuilder.setBody(null);
            firDefaultPropertySetterBuild = firPropertyAccessorBuilder.mo6809build();
            if (firDefaultPropertySetterBuild.isSetter()) {
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.first((List) firDefaultPropertySetterBuild.getValueParameters());
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
                firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getSymbol().getName()));
                firValueParameterBuilder.setReturnTypeRef(firTypeRef);
                firDefaultPropertySetterBuild.replaceValueParameters(CollectionsKt.listOf(firValueParameterBuilder.mo6809build()));
            } else {
                firDefaultPropertySetterBuild.replaceReturnTypeRef(firTypeRef);
            }
        }
        FirDeclarationOrigin origin = firDefaultPropertySetterBuild.getOrigin();
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.IntersectionOverride.INSTANCE)) {
            ClassMembersKt.setOriginalForIntersectionOverrideAttr(firDefaultPropertySetterBuild, firPropertyAccessor);
        } else if (origin instanceof FirDeclarationOrigin.SubstitutionOverride) {
            ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firDefaultPropertySetterBuild, firPropertyAccessor);
        }
        ClassMembersKt.setContainingClassForStaticMemberAttr(firDefaultPropertySetterBuild, Boolean.valueOf((firDefaultPropertySetterBuild instanceof FirDefaultPropertyAccessor) || INSTANCE.shouldOverrideSetContainingClass(firProperty)).booleanValue() ? coneClassLikeLookupTag : null);
        return firDefaultPropertySetterBuild;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<FirTypeParameter> configureAnnotationsTypeParametersAndSignature(FirPropertyBuilder firPropertyBuilder, FirSession firSession, FirProperty firProperty, List<? extends FirTypeParameter> list, ConeKotlinType coneKotlinType, List<? extends ConeKotlinType> list2, ConeKotlinType coneKotlinType2, FakeOverrideSubstitution fakeOverrideSubstitution) {
        if (firProperty.getTypeParameters().isEmpty()) {
            configureAnnotationsAndSignature$default(this, firPropertyBuilder, firProperty, coneKotlinType, list2, coneKotlinType2, fakeOverrideSubstitution, false, 32, null);
            return CollectionsKt.emptyList();
        }
        if (list == 0) {
            Pair pairCreateNewTypeParametersAndSubstitutor$default = createNewTypeParametersAndSubstitutor$default(this, firSession, firProperty, firPropertyBuilder.getSymbol(), ConeSubstitutor.Empty.INSTANCE, firPropertyBuilder.getOrigin(), false, 32, null);
            List list3 = (List) pairCreateNewTypeParametersAndSubstitutor$default.component1();
            ConeSubstitutor coneSubstitutor = (ConeSubstitutor) pairCreateNewTypeParametersAndSubstitutor$default.component2();
            Triple<ConeKotlinType, List<ConeKotlinType>, Maybe<ConeKotlinType>> tripleSubstituteReceiverAndReturnType = substituteReceiverAndReturnType(firProperty, coneKotlinType, list2, coneKotlinType2, coneSubstitutor);
            ConeKotlinType coneKotlinTypeComponent1 = tripleSubstituteReceiverAndReturnType.component1();
            List<ConeKotlinType> listComponent2 = tripleSubstituteReceiverAndReturnType.component2();
            Maybe<ConeKotlinType> maybeComponent3 = tripleSubstituteReceiverAndReturnType.component3();
            Pair pair = maybeComponent3 instanceof Maybe.Value ? TuplesKt.to(((Maybe.Value) maybeComponent3).getValue(), null) : TuplesKt.to(null, new FakeOverrideSubstitution(coneSubstitutor, firProperty.getSymbol()));
            configureAnnotationsAndSignature$default(this, firPropertyBuilder, firProperty, coneKotlinTypeComponent1, listComponent2, (ConeKotlinType) pair.component1(), (FakeOverrideSubstitution) pair.component2(), false, 32, null);
            ArrayList arrayList = new ArrayList();
            for (Object obj : list3) {
                if (obj instanceof FirTypeParameter) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
        configureAnnotationsAndSignature$default(this, firPropertyBuilder, firProperty, coneKotlinType, list2, coneKotlinType2, fakeOverrideSubstitution, false, 32, null);
        return list;
    }

    private final Triple<ConeKotlinType, List<ConeKotlinType>, Maybe<ConeKotlinType>> substituteReceiverAndReturnType(FirCallableDeclaration baseCallable, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, ConeSubstitutor substitutor) {
        ConeKotlinType coneKotlinTypeSubstituteOrNull;
        FirTypeRef typeRef;
        ArrayList arrayList;
        ConeKotlinType coneKotlinTypeSubstituteOrNull2;
        ConeKotlinType type;
        if (newReceiverType == null || (coneKotlinTypeSubstituteOrNull = substitutor.substituteOrNull(newReceiverType)) == null) {
            FirReceiverParameter receiverParameter = baseCallable.getReceiverParameter();
            coneKotlinTypeSubstituteOrNull = (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : substitutor.substituteOrNull(FirTypeUtilsKt.getConeType(typeRef));
        }
        if (newContextReceiverTypes != null) {
            List<? extends ConeKotlinType> list = newContextReceiverTypes;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ConeKotlinType coneKotlinType : list) {
                arrayList2.add((coneKotlinType == null || (type = coneKotlinType.getType()) == null) ? null : substitutor.substituteOrNull(type));
            }
            arrayList = arrayList2;
        } else {
            List<FirContextReceiver> contextReceivers = baseCallable.getContextReceivers();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
            Iterator<T> it = contextReceivers.iterator();
            while (it.hasNext()) {
                arrayList3.add(substitutor.substituteOrNull(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef())));
            }
            arrayList = arrayList3;
        }
        if (newReturnType == null || (coneKotlinTypeSubstituteOrNull2 = substitutor.substituteOrNull(newReturnType)) == null) {
            baseCallable.getReturnTypeRef();
            FirTypeRef returnTypeRef = baseCallable.getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type2 = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeKotlinType coneKotlinType2 = type2 instanceof ConeKotlinType ? type2 : null;
            if (coneKotlinType2 == null) {
                return new Triple<>(coneKotlinTypeSubstituteOrNull, arrayList, Maybe.Nothing.INSTANCE);
            }
            coneKotlinTypeSubstituteOrNull2 = substitutor.substituteOrNull(coneKotlinType2);
        }
        return new Triple<>(coneKotlinTypeSubstituteOrNull, arrayList, new Maybe.Value(coneKotlinTypeSubstituteOrNull2));
    }

    static /* synthetic */ void configureAnnotationsAndSignature$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirVariableBuilder firVariableBuilder, FirVariable firVariable, ConeKotlinType coneKotlinType, List list, ConeKotlinType coneKotlinType2, FakeOverrideSubstitution fakeOverrideSubstitution, boolean z, int i, Object obj) {
        firFakeOverrideGenerator.configureAnnotationsAndSignature(firVariableBuilder, firVariable, coneKotlinType, list, coneKotlinType2, fakeOverrideSubstitution, (i & 32) != 0 ? true : z);
    }

    private final void configureAnnotationsAndSignature(FirVariableBuilder firVariableBuilder, FirVariable firVariable, ConeKotlinType coneKotlinType, List<? extends ConeKotlinType> list, ConeKotlinType coneKotlinType2, FakeOverrideSubstitution fakeOverrideSubstitution, boolean z) {
        FirReceiverParameter firReceiverParameterBuild;
        CollectionsKt.addAll(firVariableBuilder.getAnnotations(), firVariable.getAnnotations());
        if (fakeOverrideSubstitution == null) {
            fakeOverrideSubstitution = firVariable.getReturnTypeRef() instanceof FirImplicitTypeRef ? new FakeOverrideSubstitution(ConeSubstitutor.Empty.INSTANCE, firVariable.getSymbol()) : null;
        }
        if (fakeOverrideSubstitution != null) {
            firVariableBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            FakeOverrideTypeCalculatorKt.setFakeOverrideSubstitution(firVariableBuilder.getAttributes(), fakeOverrideSubstitution);
        } else {
            firVariableBuilder.setReturnTypeRef(TypeUtilsKt.withReplacedReturnType(firVariable.getReturnTypeRef(), coneKotlinType2));
        }
        if (z) {
            FirReceiverParameter receiverParameter = firVariable.getReceiverParameter();
            if (receiverParameter != null) {
                FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
                firReceiverParameterBuilder.setSource(receiverParameter.getSource());
                firReceiverParameterBuilder.setTypeRef(receiverParameter.getTypeRef());
                firReceiverParameterBuilder.getAnnotations().addAll(receiverParameter.getAnnotations());
                firReceiverParameterBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType$default(receiverParameter.getTypeRef(), coneKotlinType, null, 2, null));
                firReceiverParameterBuild = firReceiverParameterBuilder.mo6809build();
            } else {
                firReceiverParameterBuild = null;
            }
            firVariableBuilder.setReceiverParameter(firReceiverParameterBuild);
        }
        List<FirContextReceiver> contextReceivers = firVariableBuilder.getContextReceivers();
        List<FirContextReceiver> contextReceivers2 = firVariable.getContextReceivers();
        if (list == null) {
            int size = firVariable.getContextReceivers().size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(null);
            }
            list = arrayList;
        }
        List<? extends ConeKotlinType> list2 = list;
        Iterator<T> it = contextReceivers2.iterator();
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(contextReceivers2, 10), CollectionsKt.collectionSizeOrDefault(list2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            ConeKotlinType coneKotlinType3 = (ConeKotlinType) it2.next();
            FirContextReceiver firContextReceiver = (FirContextReceiver) next;
            FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
            firContextReceiverBuilder.setSource(firContextReceiver.getSource());
            firContextReceiverBuilder.setTypeRef(firContextReceiver.getTypeRef());
            firContextReceiverBuilder.setCustomLabelName(firContextReceiver.getCustomLabelName());
            firContextReceiverBuilder.setLabelNameFromTypeRef(firContextReceiver.getLabelNameFromTypeRef());
            firContextReceiverBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType$default(firContextReceiver.getTypeRef(), coneKotlinType3, null, 2, null));
            arrayList2.add(firContextReceiverBuilder.build());
        }
        CollectionsKt.addAll(contextReceivers, arrayList2);
    }

    public final FirFieldSymbol createSubstitutionOverrideField(FirSession session, FirField baseField, ConeClassLikeLookupTag derivedClassLookupTag, ConeKotlinType newReturnType, FirDeclarationOrigin.SubstitutionOverride origin) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(baseField, "baseField");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirFieldSymbol firFieldSymbol = new FirFieldSymbol(new CallableId(derivedClassLookupTag.getClassId(), baseField.getName()));
        FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(session);
        if (nullableModuleData == null) {
            nullableModuleData = baseField.getModuleData();
        }
        firFieldBuilder.setModuleData(nullableModuleData);
        firFieldBuilder.setSymbol(firFieldSymbol);
        firFieldBuilder.setOrigin(origin);
        firFieldBuilder.setReturnTypeRef(TypeUtilsKt.withReplacedConeType$default(baseField.getReturnTypeRef(), newReturnType, null, 2, null));
        firFieldBuilder.setSource(baseField.getSource());
        FirField firField = baseField;
        firFieldBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firField));
        firFieldBuilder.setName(baseField.getName());
        firFieldBuilder.setVar(baseField.getIsVar());
        firFieldBuilder.setStatus(baseField.getStatus());
        firFieldBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firField));
        CollectionsKt.addAll(firFieldBuilder.getAnnotations(), baseField.getAnnotations());
        firFieldBuilder.setAttributes(baseField.getAttributes().copy());
        firFieldBuilder.setDispatchReceiverType(baseField.getDispatchReceiverType());
        FirField firFieldBuild = firFieldBuilder.mo6809build();
        FirField firField2 = baseField;
        ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firFieldBuild, firField2);
        if (!Boolean.valueOf(INSTANCE.shouldOverrideSetContainingClass(firField2)).booleanValue()) {
            derivedClassLookupTag = null;
        }
        ClassMembersKt.setContainingClassForStaticMemberAttr(firFieldBuild, derivedClassLookupTag);
        return firFieldSymbol;
    }

    public static /* synthetic */ Pair createNewTypeParametersAndSubstitutor$default(FirFakeOverrideGenerator firFakeOverrideGenerator, FirSession firSession, FirTypeParameterRefsOwner firTypeParameterRefsOwner, FirBasedSymbol firBasedSymbol, ConeSubstitutor coneSubstitutor, FirDeclarationOrigin firDeclarationOrigin, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = true;
        }
        return firFakeOverrideGenerator.createNewTypeParametersAndSubstitutor(firSession, firTypeParameterRefsOwner, firBasedSymbol, coneSubstitutor, firDeclarationOrigin, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<List<FirTypeParameterRef>, ConeSubstitutor> createNewTypeParametersAndSubstitutor(FirSession useSiteSession, FirTypeParameterRefsOwner member, FirBasedSymbol<?> symbolForOverride, ConeSubstitutor substitutor, FirDeclarationOrigin origin, boolean forceTypeParametersRecreation) {
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(symbolForOverride, "symbolForOverride");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(origin, "origin");
        if (member.getTypeParameters().isEmpty()) {
            return new Pair<>(member.getTypeParameters(), substitutor);
        }
        List<FirTypeParameterRef> typeParameters = member.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            FirTypeParameter firTypeParameter = (FirTypeParameter) ((FirTypeParameterRef) it.next()).getSymbol().getFir();
            FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
            firTypeParameterBuilder.setSource(firTypeParameter.getSource());
            firTypeParameterBuilder.setModuleData(firTypeParameter.getModuleData());
            firTypeParameterBuilder.setOrigin(origin);
            firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getDECLARATIONS());
            firTypeParameterBuilder.setName(firTypeParameter.getName());
            firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
            firTypeParameterBuilder.setVariance(firTypeParameter.getVariance());
            firTypeParameterBuilder.setReified(firTypeParameter.getIsReified());
            CollectionsKt.addAll(firTypeParameterBuilder.getAnnotations(), firTypeParameter.getAnnotations());
            firTypeParameterBuilder.setContainingDeclarationSymbol(symbolForOverride);
            arrayList.add(firTypeParameterBuilder);
        }
        ArrayList arrayList2 = arrayList;
        List<Pair> listZip = CollectionsKt.zip(member.getTypeParameters(), arrayList2);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            boolean z = false;
            ConeAttributes coneAttributes = null;
            Pair pair2 = new Pair(((FirTypeParameterRef) pair.component1()).getSymbol(), new ConeTypeParameterTypeImpl(((FirTypeParameterBuilder) pair.component2()).getSymbol().getLookupTag(), z, coneAttributes, 4, null));
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, useSiteSession);
        boolean z2 = forceTypeParametersRecreation;
        for (Pair pair3 : CollectionsKt.zip(arrayList2, member.getTypeParameters())) {
            FirTypeParameterBuilder firTypeParameterBuilder2 = (FirTypeParameterBuilder) pair3.component1();
            for (FirResolvedTypeRef firResolvedTypeRef : ((FirTypeParameter) ((FirTypeParameterRef) pair3.component2()).getSymbol().getFir()).getSymbol().getResolvedBounds()) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firResolvedTypeRef);
                ConeKotlinType coneKotlinTypeSubstituteOrNull = substitutor.substituteOrNull(coneType);
                if (coneKotlinTypeSubstituteOrNull != null) {
                    z2 = true;
                }
                List<FirTypeRef> bounds = firTypeParameterBuilder2.getBounds();
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setSource(firResolvedTypeRef.getSource());
                if (coneKotlinTypeSubstituteOrNull != null) {
                    coneType = coneKotlinTypeSubstituteOrNull;
                }
                firResolvedTypeRefBuilder.setType(coneSubstitutorSubstitutorByMap.substituteOrSelf(coneType));
                bounds.add(firResolvedTypeRefBuilder.mo6809build());
            }
        }
        if (!z2) {
            return new Pair<>(member.getTypeParameters(), substitutor);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((FirTypeParameterBuilder) it2.next()).mo6809build());
        }
        return new Pair<>(arrayList3, new ChainedSubstitutor(substitutor, coneSubstitutorSubstitutorByMap));
    }

    private final boolean shouldOverrideSetContainingClass(FirCallableDeclaration baseDeclaration) {
        return (baseDeclaration instanceof FirConstructor) || baseDeclaration.getStatus().isStatic() || ClassMembersKt.getContainingClassForStaticMemberAttr(baseDeclaration) != null;
    }

    /* JADX INFO: compiled from: FirFakeOverrideGenerator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0003\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe;", "A", "", "()V", "Nothing", "Value", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe$Nothing;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe$Value;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class Maybe<A> {
        public /* synthetic */ Maybe(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: FirFakeOverrideGenerator.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe$Value;", "A", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe;", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Value<A> extends Maybe<A> {
            private final A value;

            public Value(A a) {
                super(null);
                this.value = a;
            }

            public final A getValue() {
                return this.value;
            }
        }

        private Maybe() {
        }

        /* JADX INFO: compiled from: FirFakeOverrideGenerator.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe$Nothing;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirFakeOverrideGenerator$Maybe;", "", "()V", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Nothing extends Maybe {
            public static final Nothing INSTANCE = new Nothing();

            private Nothing() {
                super(null);
            }
        }
    }

    private final void checkStatusIsResolved(FirCallableDeclaration member) {
        if (!(member.getStatus() instanceof FirResolvedDeclarationStatus)) {
            throw new IllegalStateException(("Status should be resolved for a declaration to create it fake override, otherwise the status of the fake override will never be resolved.The status was unresolved for " + member.getClass().getSimpleName()).toString());
        }
    }

    public final FirField createCopyForFirField(FirFieldSymbol newSymbol, FirField baseField, ConeClassLikeLookupTag derivedClassLookupTag, FirSession session, FirDeclarationOrigin origin, boolean isExpect, ConeSimpleKotlinType newDispatchReceiverType, ConeKotlinType newReceiverType, List<? extends ConeKotlinType> newContextReceiverTypes, ConeKotlinType newReturnType, Modality newModality, Visibility newVisibility, FakeOverrideSubstitution fakeOverrideSubstitution) {
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        Intrinsics.checkNotNullParameter(baseField, "baseField");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(origin, "origin");
        FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
        firFieldBuilder.setSource(baseField.getSource());
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(session);
        if (nullableModuleData == null) {
            nullableModuleData = baseField.getModuleData();
        }
        firFieldBuilder.setModuleData(nullableModuleData);
        firFieldBuilder.setOrigin(origin);
        firFieldBuilder.setName(baseField.getName());
        firFieldBuilder.setVar(baseField.getIsVar());
        firFieldBuilder.setSymbol(newSymbol);
        FirDeclarationStatus status = baseField.getStatus();
        firFieldBuilder.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : newVisibility, (2097147 & 2) != 0 ? status.getModality() : newModality, (2097147 & 4) != 0 ? status.isExpect() : isExpect, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
        firFieldBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(baseField));
        firFieldBuilder.setDispatchReceiverType(newDispatchReceiverType);
        firFieldBuilder.setAttributes(baseField.getAttributes().copy());
        FirFakeOverrideGenerator firFakeOverrideGenerator = INSTANCE;
        firFakeOverrideGenerator.configureAnnotationsAndSignature(firFieldBuilder, baseField, newReceiverType, newContextReceiverTypes, newReturnType, fakeOverrideSubstitution, false);
        firFieldBuilder.setDeprecationsProvider(baseField.getDeprecationsProvider());
        FirField firFieldBuild = firFieldBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firFieldBuild, Boolean.valueOf(firFakeOverrideGenerator.shouldOverrideSetContainingClass(baseField)).booleanValue() ? derivedClassLookupTag : null);
        return firFieldBuild;
    }
}
