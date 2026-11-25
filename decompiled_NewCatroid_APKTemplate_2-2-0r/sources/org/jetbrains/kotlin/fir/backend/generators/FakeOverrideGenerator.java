package org.jetbrains.kotlin.fir.backend.generators;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConversionScope;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirFakeOverrideGenerator;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FakeOverrideGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000´\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010d\u001a\u00020e2\f\u0010f\u001a\b\u0012\u0004\u0012\u00020g0\u0011J%\u0010h\u001a\u00020e2\u0006\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020\u0012H\u0000¢\u0006\u0002\bnJa\u0010o\u001a\b\u0012\u0004\u0012\u0002Hp0\u0011\"\u000e\b\u0000\u0010p\u0018\u0001*\u0006\u0012\u0002\b\u00030q2\u0006\u0010r\u001a\u0002Hp2#\u0010s\u001a\u001f\u0012\u0004\u0012\u00020u\u0012\u0004\u0012\u0002Hp\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hp0\u00110t¢\u0006\u0002\bv2\u0006\u0010w\u001a\u00020x2\u0006\u0010y\u001a\u00020zH\u0082\b¢\u0006\u0002\u0010{J\u0087\u0004\u0010|\u001a\u00020e\"\n\b\u0000\u0010}\u0018\u0001*\u00020~\"\u0010\b\u0001\u0010p\u0018\u0001*\b\u0012\u0004\u0012\u0002H}0q\"\n\b\u0002\u0010\u007f\u0018\u0001*\u00020g2\u0006\u0010i\u001a\u00020j2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\n\u0010m\u001a\u0006\u0012\u0002\b\u00030q2R\u0010\u0084\u0001\u001aM\u0012\u0016\u0012\u0014H}¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0088\u0001\u0012\u0018\u0012\u0016\u0018\u00010z¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0089\u0001\u0012\u000e\u0012\f\u0012\u0007\u0012\u0005\u0018\u00010\u008b\u00010\u008a\u0001\u0012\u0006\u0012\u0004\u0018\u0001H\u007f0\u0085\u00012\u008d\u0001\u0010\u008c\u0001\u001a\u0087\u0001\u0012\u0016\u0012\u0014H}¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0088\u0001\u0012\u0017\u0012\u00150\u0081\u0001¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u008e\u0001\u0012\u0019\u0012\u0017\u0018\u00010\u0081\u0001¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u008f\u0001\u0012\u0017\u0012\u00150\u0090\u0001¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0091\u0001\u0012\u0017\u0012\u00150\u0083\u0001¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0082\u0001\u0012\u0004\u0012\u0002H\u007f0\u008d\u00012=\u0010\u0092\u0001\u001a8\u0012\u0016\u0012\u0014H}¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0088\u0001\u0012\u0016\u0012\u0014Hp¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0093\u0001\u0012\u0004\u0012\u0002Hp0t2\u0019\u0010\u0094\u0001\u001a\u0014\u0012\u0004\u0012\u0002H\u007f\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hp0\u00110\u000f2\u0012\u0010\u0095\u0001\u001a\r\u0012\u0006\b\u0000\u0012\u0002H\u007f\u0018\u00010\u0096\u00012\u0015\u0010\u0097\u0001\u001a\u0010\u0012\u0004\u0012\u0002H\u007f\u0012\u0005\u0012\u00030\u0083\u00010\u0098\u00012\u0013\u0010\u0099\u0001\u001a\u000e\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u009b\u00010\u009a\u00012$\u0010\u009c\u0001\u001a\u001f\u0012\u0004\u0012\u00020u\u0012\u0004\u0012\u0002Hp\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hp0\u00110t¢\u0006\u0002\bv2\u0006\u0010w\u001a\u00020xH\u0082\bJÈ\u0001\u0010\u009d\u0001\u001a\u0017\u0012\u0004\u0012\u0002H}\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hp0\u0011\u0018\u00010\u009e\u0001\"\b\b\u0000\u0010}*\u00020~\"\u0010\b\u0001\u0010p\u0018\u0001*\b\u0012\u0004\u0012\u0002H}0q2\u0006\u0010i\u001a\u00020j2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010m\u001a\u0002Hp2=\u0010\u0092\u0001\u001a8\u0012\u0016\u0012\u0014H}¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0088\u0001\u0012\u0016\u0012\u0014Hp¢\u0006\u000f\b\u0086\u0001\u0012\n\b\u0087\u0001\u0012\u0005\b\b(\u0093\u0001\u0012\u0004\u0012\u0002Hp0t2$\u0010\u009c\u0001\u001a\u001f\u0012\u0004\u0012\u00020u\u0012\u0004\u0012\u0002Hp\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hp0\u00110t¢\u0006\u0002\bv2\u0006\u0010w\u001a\u00020uH\u0082\b¢\u0006\u0003\u0010\u009f\u0001JG\u0010 \u0001\u001a\u0018\u0012\u0005\u0012\u00030¡\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0018\u00010\u009e\u00012\u0006\u0010i\u001a\u00020j2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010m\u001a\u00020\u00122\u0006\u0010w\u001a\u00020uH\u0000¢\u0006\u0003\b¢\u0001JG\u0010£\u0001\u001a\u0018\u0012\u0005\u0012\u00030¤\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0011\u0018\u00010\u009e\u00012\u0006\u0010i\u001a\u00020j2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010m\u001a\u00020\u00152\u0006\u0010w\u001a\u00020uH\u0000¢\u0006\u0003\b¥\u0001J\\\u0010¦\u0001\u001a\u00020e2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010§\u0001\u001a\u00020x2\b\u0010\u0087\u0001\u001a\u00030¨\u00012\u0007\u0010©\u0001\u001a\u00020j2\u0010\u0010\u0095\u0001\u001a\u000b\u0012\u0004\u0012\u00020g\u0018\u00010\u0096\u00012\u0013\u0010\u0099\u0001\u001a\u000e\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u009b\u00010\u009a\u0001H\u0000¢\u0006\u0003\bª\u0001J*\u0010¦\u0001\u001a\b\u0012\u0004\u0012\u00020g0\u00112\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\b\u0010\u0087\u0001\u001a\u00030¨\u00012\u0007\u0010©\u0001\u001a\u00020jJ!\u0010«\u0001\u001a\u000b\u0012\u0005\u0012\u00030¬\u0001\u0018\u00010\u00112\u0007\u0010\u00ad\u0001\u001a\u00020lH\u0000¢\u0006\u0003\b®\u0001Ja\u0010¯\u0001\u001a\b\u0012\u0004\u0012\u0002Hp0\u0011\"\r\b\u0000\u0010°\u0001*\u0006\u0012\u0002\b\u00030q\"\t\b\u0001\u0010p*\u00030±\u00012\b\u0010²\u0001\u001a\u0003H°\u00012\u000f\u0010³\u0001\u001a\n\u0012\u0005\u0012\u00030\u0081\u00010\u009a\u00012\u0015\u0010´\u0001\u001a\u0010\u0012\u0005\u0012\u0003H°\u0001\u0012\u0004\u0012\u0002Hp0\u0098\u0001H\u0002¢\u0006\u0003\u0010µ\u0001Ji\u0010¯\u0001\u001a\b\u0012\u0004\u0012\u0002Hp0\u0011\"\b\b\u0000\u0010\u007f*\u00020g\"\t\b\u0001\u0010p*\u00030±\u0001\"\r\b\u0002\u0010°\u0001*\u0006\u0012\u0002\b\u00030q2\u0007\u0010¶\u0001\u001a\u0002H\u007f2\u000e\u0010\u0094\u0001\u001a\t\u0012\u0005\u0012\u0003H°\u00010\u00112\u0015\u0010´\u0001\u001a\u0010\u0012\u0005\u0012\u0003H°\u0001\u0012\u0004\u0012\u0002Hp0\u0098\u0001H\u0002¢\u0006\u0003\u0010·\u0001J0\u0010¯\u0001\u001a\t\u0012\u0005\u0012\u00030¬\u00010\u00112\u0007\u0010²\u0001\u001a\u00020\u00122\u000f\u0010³\u0001\u001a\n\u0012\u0005\u0012\u00030\u0081\u00010\u009a\u0001H\u0000¢\u0006\u0003\b¸\u0001J0\u0010¯\u0001\u001a\t\u0012\u0005\u0012\u00030¹\u00010\u00112\u0007\u0010²\u0001\u001a\u00020\u00152\u000f\u0010³\u0001\u001a\n\u0012\u0005\u0012\u00030\u0081\u00010\u009a\u0001H\u0000¢\u0006\u0003\b¸\u0001J$\u0010º\u0001\u001a\u00020e*\u00030\u0081\u00012\u0006\u0010i\u001a\u00020j2\u000e\u0010f\u001a\n\u0012\u0005\u0012\u00030¼\u00010»\u0001J\u000f\u0010½\u0001\u001a\u00030\u0083\u0001*\u00030¾\u0001H\u0002J\u001c\u0010¿\u0001\u001a\u00020e*\u00020\u00142\r\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00150\u0011H\u0002J-\u0010À\u0001\u001a\b\u0012\u0004\u0012\u00020g0\u0011*\u00030\u0081\u00012\u0006\u0010i\u001a\u00020j2\u000f\u0010Á\u0001\u001a\n\u0012\u0005\u0012\u00030¼\u00010»\u0001H\u0002J.\u0010Â\u0001\u001a\u00020\u0014*\u00020\u00142\u0006\u0010-\u001a\u00020.2\b\u0010Ã\u0001\u001a\u00030\u0083\u00012\r\u0010Ä\u0001\u001a\b\u0012\u0004\u0012\u00020\u00150\u0011H\u0002J\u001b\u0010Å\u0001\u001a\u00030\u0083\u0001*\u0006\u0012\u0002\b\u00030q2\u0007\u0010Æ\u0001\u001a\u00020zH\u0002J)\u0010Ç\u0001\u001a\u00020e*\u00020l2\u0019\u0010È\u0001\u001a\u0014\u0012\u0004\u0012\u00020l\u0012\u0004\u0012\u00020e0\u0098\u0001¢\u0006\u0002\bvH\u0082\bJ)\u0010É\u0001\u001a\u00020e*\u00020\u00142\u0019\u0010È\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020e0\u0098\u0001¢\u0006\u0002\bvH\u0082\bR\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u001aX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010%\u001a\u00020&X\u0096\u0005¢\u0006\u0006\u001a\u0004\b'\u0010(R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010)\u001a\u00020*X\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0012\u0010-\u001a\u00020.X\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u00100R\u0012\u00101\u001a\u000202X\u0096\u0005¢\u0006\u0006\u001a\u0004\b3\u00104R\u0012\u00105\u001a\u000206X\u0096\u0005¢\u0006\u0006\u001a\u0004\b7\u00108R\u0012\u00109\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0012\u0010<\u001a\u00020=X\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0012\u0010@\u001a\u00020AX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0018\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0012\u0010L\u001a\u00020MX\u0096\u0005¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0012\u0010P\u001a\u00020QX\u0096\u0005¢\u0006\u0006\u001a\u0004\bR\u0010SR\u0012\u0010T\u001a\u00020UX\u0096\u0005¢\u0006\u0006\u001a\u0004\bV\u0010WR\u0012\u0010X\u001a\u00020YX\u0096\u0005¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0012\u0010\\\u001a\u00020]X\u0096\u0005¢\u0006\u0006\u001a\u0004\b^\u0010_R\u0012\u0010`\u001a\u00020aX\u0096\u0005¢\u0006\u0006\u001a\u0004\bb\u0010c¨\u0006Ê\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "baseFunctionSymbols", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "basePropertySymbols", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "baseStaticFieldSymbols", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "bindOverriddenSymbols", "", "declarations", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "calcBaseSymbolsForFakeOverrideFunction", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "fakeOverride", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "originalSymbol", "calcBaseSymbolsForFakeOverrideFunction$fir2ir", "computeBaseSymbols", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", NativeSymbol.TYPE_NAME, "directOverridden", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lkotlin/ExtensionFunctionType;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)Ljava/util/List;", "createFakeOverriddenIfNeeded", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "I", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isLocal", "", "cachedIrDeclaration", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "firDeclaration", "dispatchReceiverLookupTag", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "createIrDeclaration", "Lkotlin/Function5;", "irParent", "thisReceiverOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "origin", "createFakeOverrideSymbol", "baseSymbol", "baseSymbols", "result", "", "containsErrorTypes", "Lkotlin/Function1;", "realDeclarationSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "computeDirectOverridden", "createFirFakeOverride", "Lkotlin/Pair;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lkotlin/Pair;", "createFirFunctionFakeOverride", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "createFirFunctionFakeOverride$fir2ir", "createFirPropertyFakeOverride", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "createFirPropertyFakeOverride$fir2ir", "generateFakeOverridesForName", "useSiteOrStaticScope", "Lorg/jetbrains/kotlin/name/Name;", "firClass", "generateFakeOverridesForName$fir2ir", "getOverriddenSymbolsForFakeOverride", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "function", "getOverriddenSymbolsForFakeOverride$fir2ir", "getOverriddenSymbolsInSupertypes", "F", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "overridden", "superClasses", "irProducer", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Ljava/util/Set;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "declaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/util/List;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "getOverriddenSymbolsInSupertypes$fir2ir", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "addFakeOverrides", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "containsErrorType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "discardAccessorsAccordingToBaseVisibility", "getFakeOverrides", "realDeclarations", "setOverriddenSymbolsForProperty", "isVar", "firOverriddenSymbols", "shouldHaveComputedBaseSymbolsForClass", "classLookupTag", "withFunction", "f", "withProperty", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideGenerator implements Fir2IrComponents {
    private final Map<IrFunction, List<FirNamedFunctionSymbol>> baseFunctionSymbols;
    private final Map<IrProperty, List<FirPropertySymbol>> basePropertySymbols;
    private final Map<IrField, List<FirFieldSymbol>> baseStaticFieldSymbols;
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.components.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.components.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.components.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.components.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.components.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.components.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.components.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.components.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.components.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.components.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.components.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.components.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.components.getLock();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.components.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.components.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.components.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.components.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.components.getVisibilityConverter();
    }

    public FakeOverrideGenerator(Fir2IrComponents components, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.conversionScope = conversionScope;
        this.baseFunctionSymbols = new LinkedHashMap();
        this.basePropertySymbols = new LinkedHashMap();
        this.baseStaticFieldSymbols = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean containsErrorType(IrType irType) {
        if (irType instanceof IrErrorType) {
            return true;
        }
        if (irType instanceof IrSimpleType) {
            List<IrTypeArgument> arguments = ((IrSimpleType) irType).getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                for (IrTypeArgument irTypeArgument : arguments) {
                    if ((irTypeArgument instanceof IrTypeProjection) && containsErrorType(((IrTypeProjection) irTypeArgument).getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void addFakeOverrides(IrClass irClass, FirClass klass, Collection<? extends FirDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        CollectionsKt.addAll(irClass.getDeclarations(), getFakeOverrides(irClass, klass, declarations));
    }

    private final List<IrDeclaration> getFakeOverrides(IrClass irClass, FirClass firClass, Collection<? extends FirDeclaration> collection) {
        ArrayList arrayList = new ArrayList();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firClass, getSession(), getScopeSession(), true, (FirResolvePhase) null);
        Set<Name> callableNames = firTypeScopeUnsubstitutedScope.getCallableNames();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((FirDeclaration) it.next()).getSymbol());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        Iterator<Name> it2 = callableNames.iterator();
        while (it2.hasNext()) {
            generateFakeOverridesForName$fir2ir(irClass, firTypeScopeUnsubstitutedScope, it2.next(), firClass, arrayList, linkedHashSet2);
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x006d  */
    public final List<IrDeclaration> generateFakeOverridesForName(IrClass irClass, Name name2, FirClass firClass) {
        FirSyntheticPropertiesScope firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        List<IrDeclaration> listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firClass, getSession(), getScopeSession(), true, (FirResolvePhase) null);
        generateFakeOverridesForName$fir2ir(irClass, firTypeScopeUnsubstitutedScope, name2, firClass, listCreateListBuilder, SetsKt.emptySet());
        List<IrDeclaration> list = listCreateListBuilder;
        if ((list instanceof Collection) && list.isEmpty()) {
            firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default = FirSyntheticPropertiesScope.Companion.createIfSyntheticNamesProviderIsDefined$default(FirSyntheticPropertiesScope.INSTANCE, getSession(), ScopeUtilsKt.defaultType(firClass), firTypeScopeUnsubstitutedScope, null, 8, null);
            if (firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default != null) {
                generateFakeOverridesForName$fir2ir(irClass, firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default, name2, firClass, null, SetsKt.emptySet());
            }
        } else {
            Iterator<T> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((IrDeclaration) it.next()) instanceof IrProperty) {
                    }
                } else {
                    firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default = FirSyntheticPropertiesScope.Companion.createIfSyntheticNamesProviderIsDefined$default(FirSyntheticPropertiesScope.INSTANCE, getSession(), ScopeUtilsKt.defaultType(firClass), firTypeScopeUnsubstitutedScope, null, 8, null);
                    if (firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default != null) {
                        generateFakeOverridesForName$fir2ir(irClass, firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined$default, name2, firClass, null, SetsKt.emptySet());
                    }
                }
            }
        }
        FirContainingNamesAwareScope staticMemberScopeForCallables = firClass.getScopeProvider().getStaticMemberScopeForCallables(firClass, getSession(), getScopeSession());
        if (staticMemberScopeForCallables != null) {
            generateFakeOverridesForName$fir2ir(irClass, staticMemberScopeForCallables, name2, firClass, listCreateListBuilder, SetsKt.emptySet());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    public final void generateFakeOverridesForName$fir2ir(final IrClass irClass, final FirScope useSiteOrStaticScope, Name name2, final FirClass firClass, final List<IrDeclaration> result, final Set<? extends FirBasedSymbol<?>> realDeclarationSymbols) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(useSiteOrStaticScope, "useSiteOrStaticScope");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(realDeclarationSymbols, "realDeclarationSymbols");
        final boolean z = !(firClass instanceof FirRegularClass) || firClass.getSymbol().getClassId().isLocal();
        useSiteOrStaticScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator.generateFakeOverridesForName.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:77:0x022a  */
            /* JADX WARN: Code duplicated, block: B:79:0x023c  */
            /* JADX WARN: Code duplicated, block: B:80:0x0240  */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                Pair pair;
                IrDeclarationParent irDeclarationParentFindIrParent$fir2ir;
                IrClass irClass2;
                ArrayList arrayListEmptyList;
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                FakeOverrideGenerator fakeOverrideGenerator = FakeOverrideGenerator.this;
                FirClass firClass2 = firClass;
                IrClass irClass3 = irClass;
                boolean z2 = z;
                Fir2IrDeclarationStorage declarationStorage = fakeOverrideGenerator.getDeclarationStorage();
                Fir2IrDeclarationStorage declarationStorage2 = FakeOverrideGenerator.this.getDeclarationStorage();
                FirClass firClass3 = firClass;
                FakeOverrideGenerator fakeOverrideGenerator2 = FakeOverrideGenerator.this;
                Map map = fakeOverrideGenerator2.baseFunctionSymbols;
                List<IrDeclaration> list = result;
                FakeOverrideGenerator fakeOverrideGenerator3 = FakeOverrideGenerator.this;
                Set<FirBasedSymbol<?>> set = realDeclarationSymbols;
                FirScope firScope = useSiteOrStaticScope;
                FirNamedFunctionSymbol firNamedFunctionSymbol = functionSymbol;
                ConeClassLikeLookupTag lookupTag = firClass2.getSymbol().getLookupTag();
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
                if ((!Intrinsics.areEqual(ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol), lookupTag) || firCallableDeclaration.getOrigin().getFromSupertypes()) && !set.contains(firNamedFunctionSymbol)) {
                    FirCallableDeclaration firCallableDeclaration2 = firCallableDeclaration;
                    if (FirVisibilityCheckerKt.getVisibilityChecker(fakeOverrideGenerator.getSession()).isVisibleForOverriding(firClass2.getModuleData(), firClass2.getSymbol(), firCallableDeclaration2)) {
                        IrDeclarationOrigin.FAKE_OVERRIDE fake_override = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
                        FirCallableSymbol<?> firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides = ConversionUtilsKt.unwrapSubstitutionAndIntersectionOverrides(firNamedFunctionSymbol);
                        if (firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
                        }
                        FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides;
                        if (fakeOverrideGenerator.shouldHaveComputedBaseSymbolsForClass(firNamedFunctionSymbol, lookupTag)) {
                            if (((FirCallableDeclaration) firNamedFunctionSymbol.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
                                FirCallableDeclaration firCallableDeclaration3 = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
                                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration3) || (firCallableDeclaration3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration3) : null;
                                FirNamedFunctionSymbol firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
                                Intrinsics.checkNotNull(firNamedFunctionSymbol3);
                                arrayListEmptyList = CollectionsKt.listOf(firNamedFunctionSymbol3);
                            } else if (firScope instanceof FirTypeScope) {
                                List<FirCallableSymbol> directOverriddenFunctions$default = FirTypeScopeKt.getDirectOverriddenFunctions$default((FirTypeScope) firScope, firNamedFunctionSymbol, false, 2, null);
                                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directOverriddenFunctions$default, 10));
                                for (FirNamedFunctionSymbol firNamedFunctionSymbol4 : directOverriddenFunctions$default) {
                                    if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firNamedFunctionSymbol4.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firNamedFunctionSymbol4), lookupTag)) {
                                        FirCallableDeclaration firCallableDeclaration4 = (FirCallableDeclaration) firNamedFunctionSymbol4.getFir();
                                        FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration4) || (firCallableDeclaration4.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration4) : null;
                                        firNamedFunctionSymbol4 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr2 != null ? originalForSubstitutionOverrideAttr2.getSymbol() : null);
                                        Intrinsics.checkNotNull(firNamedFunctionSymbol4);
                                    }
                                    arrayList.add(firNamedFunctionSymbol4);
                                }
                                arrayListEmptyList = arrayList;
                            } else {
                                arrayListEmptyList = CollectionsKt.emptyList();
                            }
                            pair = TuplesKt.to(firCallableDeclaration, arrayListEmptyList);
                        } else {
                            if (Visibilities.INSTANCE.isPrivate(firCallableDeclaration2.getStatus().getVisibility()) || Intrinsics.areEqual(firCallableDeclaration2.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) {
                                return;
                            }
                            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firCallableDeclaration;
                            FirNamedFunctionSymbol firNamedFunctionSymbolCreateSymbolForSubstitutionOverride = FirFakeOverrideGenerator.INSTANCE.createSymbolForSubstitutionOverride(firNamedFunctionSymbol2, firClass3.getSymbol().getClassId());
                            FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
                            FirSession session = fakeOverrideGenerator2.getSession();
                            ConeClassLikeLookupTag lookupTag2 = firClass3.getSymbol().getLookupTag();
                            ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firClass3);
                            FirDeclarationOrigin.SubstitutionOverride.DeclarationSite declarationSite = FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE;
                            FirRegularClass firRegularClass = firClass3 instanceof FirRegularClass ? (FirRegularClass) firClass3 : null;
                            FirNamedFunctionSymbol firNamedFunctionSymbolCreateSubstitutionOverrideFunction = firFakeOverrideGenerator.createSubstitutionOverrideFunction(session, firNamedFunctionSymbolCreateSymbolForSubstitutionOverride, firSimpleFunction, lookupTag2, coneClassLikeTypeDefaultType, declarationSite, (6080 & 64) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 128) != 0 ? null : null), (6080 & 256) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 512) != 0 ? null : null), (List<? extends FirTypeParameter>) ((6080 & 1024) != 0 ? null : null), (6080 & 2048) != 0 ? firSimpleFunction.getStatus().isExpect() : (firRegularClass != null && firRegularClass.getStatus().isExpect()) || firSimpleFunction.getStatus().isExpect(), (6080 & 4096) != 0 ? null : null);
                            fakeOverrideGenerator.getDeclarationStorage().saveFakeOverrideInClass$fir2ir(irClass3, firCallableDeclaration, (FirCallableDeclaration) firNamedFunctionSymbolCreateSubstitutionOverrideFunction.getFir());
                            pair = TuplesKt.to(firNamedFunctionSymbolCreateSubstitutionOverrideFunction.getFir(), CollectionsKt.listOf(firNamedFunctionSymbol));
                        }
                        FirCallableDeclaration firCallableDeclaration5 = (FirCallableDeclaration) pair.component1();
                        List list2 = (List) pair.component2();
                        IrSimpleFunction cachedIrFunction = declarationStorage.getCachedIrFunction((FirSimpleFunction) firCallableDeclaration5, null, new FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1(fakeOverrideGenerator, firCallableDeclaration5));
                        if (cachedIrFunction == null) {
                            irDeclarationParentFindIrParent$fir2ir = fakeOverrideGenerator.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firNamedFunctionSymbol2.getFir());
                            if (irDeclarationParentFindIrParent$fir2ir instanceof IrClass) {
                                irClass2 = (IrClass) irDeclarationParentFindIrParent$fir2ir;
                            } else {
                                irClass2 = null;
                            }
                            cachedIrFunction = Fir2IrDeclarationStorage.createIrFunction$default(declarationStorage2, (FirFunction) firCallableDeclaration5, irClass3, irClass2, fake_override, z2, null, 32, null);
                        } else {
                            if (!Intrinsics.areEqual(cachedIrFunction.getParent(), irClass3)) {
                                cachedIrFunction = null;
                            }
                            if (cachedIrFunction == null) {
                                irDeclarationParentFindIrParent$fir2ir = fakeOverrideGenerator.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firNamedFunctionSymbol2.getFir());
                                if (irDeclarationParentFindIrParent$fir2ir instanceof IrClass) {
                                    irClass2 = (IrClass) irDeclarationParentFindIrParent$fir2ir;
                                } else {
                                    irClass2 = null;
                                }
                                cachedIrFunction = Fir2IrDeclarationStorage.createIrFunction$default(declarationStorage2, (FirFunction) firCallableDeclaration5, irClass3, irClass2, fake_override, z2, null, 32, null);
                            }
                        }
                        IrSimpleFunction irSimpleFunction = cachedIrFunction;
                        if (r16.containsErrorType(irSimpleFunction.getReturnType())) {
                            return;
                        }
                        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
                        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                            Iterator<T> it = valueParameters.iterator();
                            while (it.hasNext()) {
                                if (fakeOverrideGenerator3.containsErrorType(((IrValueParameter) it.next()).getType())) {
                                    return;
                                }
                            }
                        }
                        map.put(cachedIrFunction, list2);
                        if (list != null) {
                            list.add(cachedIrFunction);
                        }
                    }
                }
            }
        });
        useSiteOrStaticScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator.generateFakeOverridesForName.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:174:0x047d  */
            /* JADX WARN: Code duplicated, block: B:176:0x048f  */
            /* JADX WARN: Code duplicated, block: B:82:0x023b  */
            /* JADX WARN: Code duplicated, block: B:84:0x024d  */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> propertyOrFieldSymbol) {
                Pair pair;
                IrDeclarationParent irDeclarationParentFindIrParent$fir2ir;
                ArrayList arrayListEmptyList;
                Pair pair2;
                IrProperty irPropertyCreateIrProperty$default;
                IrSimpleFunction getter;
                IrType returnType;
                IrType type;
                ArrayList arrayListEmptyList2;
                Intrinsics.checkNotNullParameter(propertyOrFieldSymbol, "propertyOrFieldSymbol");
                if (propertyOrFieldSymbol instanceof FirPropertySymbol) {
                    FakeOverrideGenerator fakeOverrideGenerator = FakeOverrideGenerator.this;
                    FirClass firClass2 = firClass;
                    IrClass irClass2 = irClass;
                    boolean z2 = z;
                    Fir2IrDeclarationStorage declarationStorage = fakeOverrideGenerator.getDeclarationStorage();
                    Fir2IrDeclarationStorage declarationStorage2 = FakeOverrideGenerator.this.getDeclarationStorage();
                    FirClass firClass3 = firClass;
                    FakeOverrideGenerator fakeOverrideGenerator2 = FakeOverrideGenerator.this;
                    Map map = fakeOverrideGenerator2.basePropertySymbols;
                    List<IrDeclaration> list = result;
                    FakeOverrideGenerator fakeOverrideGenerator3 = FakeOverrideGenerator.this;
                    Set<FirBasedSymbol<?>> set = realDeclarationSymbols;
                    FirScope firScope = useSiteOrStaticScope;
                    FirVariableSymbol<?> firVariableSymbol = propertyOrFieldSymbol;
                    if (firVariableSymbol instanceof FirPropertySymbol) {
                        ConeClassLikeLookupTag lookupTag = firClass2.getSymbol().getLookupTag();
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firVariableSymbol.getFir();
                        if (Intrinsics.areEqual(ClassMembersKt.containingClassLookupTag(firVariableSymbol), lookupTag) && !firCallableDeclaration.getOrigin().getFromSupertypes()) {
                            return;
                        }
                        if (!set.contains(firVariableSymbol)) {
                            FirCallableDeclaration firCallableDeclaration2 = firCallableDeclaration;
                            if (FirVisibilityCheckerKt.getVisibilityChecker(fakeOverrideGenerator.getSession()).isVisibleForOverriding(firClass2.getModuleData(), firClass2.getSymbol(), firCallableDeclaration2)) {
                                IrDeclarationOrigin.FAKE_OVERRIDE fake_override = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
                                FirCallableSymbol<?> firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides = ConversionUtilsKt.unwrapSubstitutionAndIntersectionOverrides(firVariableSymbol);
                                if (firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
                                }
                                FirPropertySymbol firPropertySymbol = (FirPropertySymbol) firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides;
                                if (fakeOverrideGenerator.shouldHaveComputedBaseSymbolsForClass(firVariableSymbol, lookupTag)) {
                                    if (((FirCallableDeclaration) firVariableSymbol.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
                                        FirCallableDeclaration firCallableDeclaration3 = (FirCallableDeclaration) firVariableSymbol.getFir();
                                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration3) || (firCallableDeclaration3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration3) : null;
                                        FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
                                        Intrinsics.checkNotNull(firPropertySymbol2);
                                        arrayListEmptyList2 = CollectionsKt.listOf(firPropertySymbol2);
                                    } else if (firScope instanceof FirTypeScope) {
                                        List<FirCallableSymbol> directOverriddenProperties$default = FirTypeScopeKt.getDirectOverriddenProperties$default((FirTypeScope) firScope, (FirPropertySymbol) firVariableSymbol, false, 2, null);
                                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directOverriddenProperties$default, 10));
                                        for (FirPropertySymbol firPropertySymbol3 : directOverriddenProperties$default) {
                                            if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firPropertySymbol3.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firPropertySymbol3), lookupTag)) {
                                                FirCallableDeclaration firCallableDeclaration4 = (FirCallableDeclaration) firPropertySymbol3.getFir();
                                                FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration4) || (firCallableDeclaration4.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration4) : null;
                                                firPropertySymbol3 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr2 != null ? originalForSubstitutionOverrideAttr2.getSymbol() : null);
                                                Intrinsics.checkNotNull(firPropertySymbol3);
                                            }
                                            arrayList.add(firPropertySymbol3);
                                        }
                                        arrayListEmptyList2 = arrayList;
                                    } else {
                                        arrayListEmptyList2 = CollectionsKt.emptyList();
                                    }
                                    pair2 = TuplesKt.to(firCallableDeclaration, arrayListEmptyList2);
                                } else if (!Visibilities.INSTANCE.isPrivate(firCallableDeclaration2.getStatus().getVisibility()) && !Intrinsics.areEqual(firCallableDeclaration2.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) {
                                    FirProperty firProperty = (FirProperty) firCallableDeclaration;
                                    FirPropertySymbol firPropertySymbolCreateSymbolForSubstitutionOverride = FirFakeOverrideGenerator.INSTANCE.createSymbolForSubstitutionOverride(firPropertySymbol, firClass3.getSymbol().getClassId());
                                    FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
                                    FirSession session = fakeOverrideGenerator2.getSession();
                                    ConeClassLikeLookupTag lookupTag2 = firClass3.getSymbol().getLookupTag();
                                    ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firClass3);
                                    FirDeclarationOrigin.SubstitutionOverride.DeclarationSite declarationSite = FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE;
                                    FirRegularClass firRegularClass = firClass3 instanceof FirRegularClass ? (FirRegularClass) firClass3 : null;
                                    FirPropertySymbol firPropertySymbolCreateSubstitutionOverrideProperty = firFakeOverrideGenerator.createSubstitutionOverrideProperty(session, firPropertySymbolCreateSymbolForSubstitutionOverride, firProperty, lookupTag2, coneClassLikeTypeDefaultType, declarationSite, (XKeySym.XK_overbar & 64) != 0 ? null : null, (XKeySym.XK_overbar & 128) != 0 ? null : null, (XKeySym.XK_overbar & 256) != 0 ? null : null, (XKeySym.XK_overbar & 512) != 0 ? null : null, (XKeySym.XK_overbar & 1024) != 0 ? firProperty.getStatus().isExpect() : (firRegularClass != null && firRegularClass.getStatus().isExpect()) || firProperty.getStatus().isExpect(), (XKeySym.XK_overbar & 2048) != 0 ? null : null);
                                    fakeOverrideGenerator.getDeclarationStorage().saveFakeOverrideInClass$fir2ir(irClass2, firCallableDeclaration, (FirCallableDeclaration) firPropertySymbolCreateSubstitutionOverrideProperty.getFir());
                                    pair2 = TuplesKt.to(firPropertySymbolCreateSubstitutionOverrideProperty.getFir(), CollectionsKt.listOf(firVariableSymbol));
                                }
                                FirCallableDeclaration firCallableDeclaration5 = (FirCallableDeclaration) pair2.component1();
                                List list2 = (List) pair2.component2();
                                FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1 fakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1 = new FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1(fakeOverrideGenerator, firCallableDeclaration5);
                                FirProperty firProperty2 = (FirProperty) firCallableDeclaration5;
                                IrProperty cachedIrProperty = declarationStorage.getCachedIrProperty(firProperty2, null, fakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1);
                                if (cachedIrProperty == null) {
                                    IrDeclarationParent irDeclarationParentFindIrParent$fir2ir2 = fakeOverrideGenerator.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firPropertySymbol.getFir());
                                    irPropertyCreateIrProperty$default = Fir2IrDeclarationStorage.createIrProperty$default(declarationStorage2, firProperty2, irClass2, irDeclarationParentFindIrParent$fir2ir2 instanceof IrClass ? (IrClass) irDeclarationParentFindIrParent$fir2ir2 : null, fake_override, z2, null, 32, null);
                                } else {
                                    if (!Intrinsics.areEqual(cachedIrProperty.getParent(), irClass2)) {
                                        cachedIrProperty = null;
                                    }
                                    if (cachedIrProperty != null) {
                                        irPropertyCreateIrProperty$default = cachedIrProperty;
                                    } else {
                                        IrDeclarationParent irDeclarationParentFindIrParent$fir2ir3 = fakeOverrideGenerator.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firPropertySymbol.getFir());
                                        irPropertyCreateIrProperty$default = Fir2IrDeclarationStorage.createIrProperty$default(declarationStorage2, firProperty2, irClass2, irDeclarationParentFindIrParent$fir2ir3 instanceof IrClass ? (IrClass) irDeclarationParentFindIrParent$fir2ir3 : null, fake_override, z2, null, 32, null);
                                    }
                                }
                                IrProperty irProperty = irPropertyCreateIrProperty$default;
                                IrField backingField = irProperty.getBackingField();
                                if ((backingField == null || (type = backingField.getType()) == null || !fakeOverrideGenerator3.containsErrorType(type)) && ((getter = irProperty.getGetter()) == null || (returnType = getter.getReturnType()) == null || !r19.containsErrorType(returnType))) {
                                    map.put(irPropertyCreateIrProperty$default, list2);
                                    if (r18 != 0) {
                                        Boolean.valueOf(list.add(irPropertyCreateIrProperty$default));
                                    }
                                }
                            }
                        }
                    }
                } else if (propertyOrFieldSymbol instanceof FirFieldSymbol) {
                    FirVariableSymbol<?> firVariableSymbol2 = propertyOrFieldSymbol;
                    if (firVariableSymbol2.getRawStatus().isStatic()) {
                        FakeOverrideGenerator fakeOverrideGenerator4 = FakeOverrideGenerator.this;
                        FirClass firClass4 = firClass;
                        IrClass irClass3 = irClass;
                        Map map2 = fakeOverrideGenerator4.baseStaticFieldSymbols;
                        List<IrDeclaration> list3 = result;
                        FakeOverrideGenerator fakeOverrideGenerator5 = FakeOverrideGenerator.this;
                        Set<FirBasedSymbol<?>> set2 = realDeclarationSymbols;
                        FirScope firScope2 = useSiteOrStaticScope;
                        if (firVariableSymbol2 instanceof FirFieldSymbol) {
                            ConeClassLikeLookupTag lookupTag3 = firClass4.getSymbol().getLookupTag();
                            FirCallableDeclaration firCallableDeclaration6 = (FirCallableDeclaration) firVariableSymbol2.getFir();
                            if ((!Intrinsics.areEqual(ClassMembersKt.containingClassLookupTag(firVariableSymbol2), lookupTag3) || firCallableDeclaration6.getOrigin().getFromSupertypes()) && !set2.contains(firVariableSymbol2)) {
                                FirCallableDeclaration firCallableDeclaration7 = firCallableDeclaration6;
                                if (FirVisibilityCheckerKt.getVisibilityChecker(fakeOverrideGenerator4.getSession()).isVisibleForOverriding(firClass4.getModuleData(), firClass4.getSymbol(), firCallableDeclaration7)) {
                                    IrDeclarationOrigin.FAKE_OVERRIDE fake_override2 = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
                                    FirCallableSymbol<?> firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides2 = ConversionUtilsKt.unwrapSubstitutionAndIntersectionOverrides(firVariableSymbol2);
                                    if (firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides2 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol");
                                    }
                                    FirFieldSymbol firFieldSymbol = (FirFieldSymbol) firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides2;
                                    if (fakeOverrideGenerator4.shouldHaveComputedBaseSymbolsForClass(firVariableSymbol2, lookupTag3)) {
                                        if (((FirCallableDeclaration) firVariableSymbol2.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
                                            FirCallableDeclaration firCallableDeclaration8 = (FirCallableDeclaration) firVariableSymbol2.getFir();
                                            FirCallableDeclaration originalForSubstitutionOverrideAttr3 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration8) || (firCallableDeclaration8.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration8) : null;
                                            FirFieldSymbol firFieldSymbol2 = (FirFieldSymbol) (originalForSubstitutionOverrideAttr3 != null ? originalForSubstitutionOverrideAttr3.getSymbol() : null);
                                            Intrinsics.checkNotNull(firFieldSymbol2);
                                            arrayListEmptyList = CollectionsKt.listOf(firFieldSymbol2);
                                        } else if (firScope2 instanceof FirTypeScope) {
                                            List<FirCallableSymbol> listEmptyList = CollectionsKt.emptyList();
                                            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listEmptyList, 10));
                                            for (FirFieldSymbol firFieldSymbol3 : listEmptyList) {
                                                if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firFieldSymbol3.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firFieldSymbol3), lookupTag3)) {
                                                    FirCallableDeclaration firCallableDeclaration9 = (FirCallableDeclaration) firFieldSymbol3.getFir();
                                                    FirCallableDeclaration originalForSubstitutionOverrideAttr4 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration9) || (firCallableDeclaration9.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration9) : null;
                                                    firFieldSymbol3 = (FirFieldSymbol) (originalForSubstitutionOverrideAttr4 != null ? originalForSubstitutionOverrideAttr4.getSymbol() : null);
                                                    Intrinsics.checkNotNull(firFieldSymbol3);
                                                }
                                                arrayList2.add(firFieldSymbol3);
                                            }
                                            arrayListEmptyList = arrayList2;
                                        } else {
                                            arrayListEmptyList = CollectionsKt.emptyList();
                                        }
                                        pair = TuplesKt.to(firCallableDeclaration6, arrayListEmptyList);
                                    } else {
                                        if (Visibilities.INSTANCE.isPrivate(firCallableDeclaration7.getStatus().getVisibility()) || Intrinsics.areEqual(firCallableDeclaration7.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) {
                                            return;
                                        }
                                        FirField firField = (FirField) firCallableDeclaration6;
                                        FirFieldSymbol firFieldSymbolCreateSubstitutionOverrideField = FirFakeOverrideGenerator.INSTANCE.createSubstitutionOverrideField(fakeOverrideGenerator4.getSession(), firField, firClass4.getSymbol().getLookupTag(), FirTypeUtilsKt.getConeType(firField.getReturnTypeRef()), FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE);
                                        fakeOverrideGenerator4.getDeclarationStorage().saveFakeOverrideInClass$fir2ir(irClass3, firCallableDeclaration6, (FirCallableDeclaration) firFieldSymbolCreateSubstitutionOverrideField.getFir());
                                        pair = TuplesKt.to(firFieldSymbolCreateSubstitutionOverrideField.getFir(), CollectionsKt.listOf(firVariableSymbol2));
                                    }
                                    FirCallableDeclaration firCallableDeclaration10 = (FirCallableDeclaration) pair.component1();
                                    List list4 = (List) pair.component2();
                                    new FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1(fakeOverrideGenerator4, firCallableDeclaration10);
                                    FirField firField2 = (FirField) firCallableDeclaration10;
                                    IrField cachedIrFieldStaticFakeOverrideByDeclaration = fakeOverrideGenerator4.getDeclarationStorage().getCachedIrFieldStaticFakeOverrideByDeclaration(firField2);
                                    if (cachedIrFieldStaticFakeOverrideByDeclaration == null) {
                                        irDeclarationParentFindIrParent$fir2ir = fakeOverrideGenerator4.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firFieldSymbol.getFir());
                                        if (irDeclarationParentFindIrParent$fir2ir instanceof IrClass) {
                                        }
                                        cachedIrFieldStaticFakeOverrideByDeclaration = Fir2IrDeclarationStorage.createIrField$fir2ir$default(fakeOverrideGenerator4.getDeclarationStorage(), firField2, irClass3, null, null, 12, null);
                                    } else {
                                        if (!Intrinsics.areEqual(cachedIrFieldStaticFakeOverrideByDeclaration.getParent(), irClass3)) {
                                            cachedIrFieldStaticFakeOverrideByDeclaration = null;
                                        }
                                        if (cachedIrFieldStaticFakeOverrideByDeclaration == null) {
                                            irDeclarationParentFindIrParent$fir2ir = fakeOverrideGenerator4.getDeclarationStorage().findIrParent$fir2ir((FirCallableDeclaration) firFieldSymbol.getFir());
                                            if (irDeclarationParentFindIrParent$fir2ir instanceof IrClass) {
                                            }
                                            cachedIrFieldStaticFakeOverrideByDeclaration = Fir2IrDeclarationStorage.createIrField$fir2ir$default(fakeOverrideGenerator4.getDeclarationStorage(), firField2, irClass3, null, null, 12, null);
                                        }
                                    }
                                    if (fakeOverrideGenerator5.containsErrorType(cachedIrFieldStaticFakeOverrideByDeclaration.getType())) {
                                        return;
                                    }
                                    map2.put(cachedIrFieldStaticFakeOverrideByDeclaration, list4);
                                    if (list3 != null) {
                                        Boolean.valueOf(list3.add(cachedIrFieldStaticFakeOverrideByDeclaration));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void calcBaseSymbolsForFakeOverrideFunction$fir2ir(FirClass klass, IrSimpleFunction fakeOverride, FirNamedFunctionSymbol originalSymbol) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
        Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(klass, getSession(), getScopeSession(), true, (FirResolvePhase) null);
        ConeClassLikeLookupTag lookupTag = klass.getSymbol().getLookupTag();
        FirNamedFunctionSymbol firNamedFunctionSymbol = originalSymbol;
        if (!shouldHaveComputedBaseSymbolsForClass(firNamedFunctionSymbol, lookupTag)) {
            arrayListListOf = CollectionsKt.listOf(originalSymbol);
        } else if (((FirCallableDeclaration) firNamedFunctionSymbol.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
            Intrinsics.checkNotNull(firNamedFunctionSymbol2);
            arrayListListOf = CollectionsKt.listOf(firNamedFunctionSymbol2);
        } else {
            FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
            if (firTypeScope instanceof FirTypeScope) {
                List<FirCallableSymbol> directOverriddenFunctions$default = FirTypeScopeKt.getDirectOverriddenFunctions$default(firTypeScope, firNamedFunctionSymbol, false, 2, null);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directOverriddenFunctions$default, 10));
                for (FirNamedFunctionSymbol firNamedFunctionSymbol3 : directOverriddenFunctions$default) {
                    if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firNamedFunctionSymbol3.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firNamedFunctionSymbol3), lookupTag)) {
                        FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) firNamedFunctionSymbol3.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
                        firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr2 != null ? originalForSubstitutionOverrideAttr2.getSymbol() : null);
                        Intrinsics.checkNotNull(firNamedFunctionSymbol3);
                    }
                    arrayList.add(firNamedFunctionSymbol3);
                }
                arrayListListOf = arrayList;
            } else {
                arrayListListOf = CollectionsKt.emptyList();
            }
        }
        this.baseFunctionSymbols.put(fakeOverride, arrayListListOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldHaveComputedBaseSymbolsForClass(FirCallableSymbol<?> firCallableSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        return ((FirCallableDeclaration) firCallableSymbol.getFir()).getOrigin().getFromSupertypes() && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firCallableSymbol), coneClassLikeLookupTag);
    }

    public final List<IrSimpleFunctionSymbol> getOverriddenSymbolsForFakeOverride$fir2ir(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<FirNamedFunctionSymbol> list = this.baseFunctionSymbols.get(function);
        if (list == null) {
            return null;
        }
        return getOverriddenSymbolsInSupertypes(function, list, new Function1<FirNamedFunctionSymbol, IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator$getOverriddenSymbolsForFakeOverride$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunctionSymbol invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFunctionSymbol irFunctionSymbol$default = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(this.this$0.getDeclarationStorage(), it, null, 2, null);
                Intrinsics.checkNotNull(irFunctionSymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                return (IrSimpleFunctionSymbol) irFunctionSymbol$default;
            }
        });
    }

    public final List<IrSimpleFunctionSymbol> getOverriddenSymbolsInSupertypes$fir2ir(FirNamedFunctionSymbol overridden, Set<? extends IrClass> superClasses) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        return getOverriddenSymbolsInSupertypes(overridden, superClasses, new Function1<FirNamedFunctionSymbol, IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator.getOverriddenSymbolsInSupertypes.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunctionSymbol invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFunctionSymbol irFunctionSymbol$default = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(FakeOverrideGenerator.this.getDeclarationStorage(), it, null, 2, null);
                Intrinsics.checkNotNull(irFunctionSymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                return (IrSimpleFunctionSymbol) irFunctionSymbol$default;
            }
        });
    }

    public final List<IrPropertySymbol> getOverriddenSymbolsInSupertypes$fir2ir(FirPropertySymbol overridden, Set<? extends IrClass> superClasses) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        return getOverriddenSymbolsInSupertypes(overridden, superClasses, new Function1<FirPropertySymbol, IrPropertySymbol>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator.getOverriddenSymbolsInSupertypes.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrPropertySymbol invoke(FirPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSymbol irPropertySymbol$default = Fir2IrDeclarationStorage.getIrPropertySymbol$default(FakeOverrideGenerator.this.getDeclarationStorage(), it, null, 2, null);
                Intrinsics.checkNotNull(irPropertySymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
                return (IrPropertySymbol) irPropertySymbol$default;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <I extends IrDeclaration, S extends IrSymbol, F extends FirCallableSymbol<?>> List<S> getOverriddenSymbolsInSupertypes(I declaration, List<? extends F> baseSymbols, Function1<? super F, ? extends S> irProducer) {
        List<IrType> superTypes = IrUtilsKt.getParentAsClass(declaration).getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
            IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass != null) {
                arrayList.add(irClass);
            }
        }
        Set set = CollectionsKt.toSet(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = baseSymbols.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, getOverriddenSymbolsInSupertypes((FirCallableSymbol) it2.next(), (Set<? extends IrClass>) set, irProducer));
        }
        return CollectionsKt.distinct(arrayList2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <F extends FirCallableSymbol<?>, S extends IrSymbol> List<S> getOverriddenSymbolsInSupertypes(F overridden, Set<? extends IrClass> superClasses, Function1<? super F, ? extends S> irProducer) {
        S sInvoke;
        FirClassLikeSymbol<?> symbol;
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(overridden);
        FirClassLikeDeclaration firClassLikeDeclaration = (coneClassLikeLookupTagContainingClassLookupTag == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, getSession())) == null) ? null : (FirClassLikeDeclaration) symbol.getFir();
        FirClass firClass = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
        if (firClass == null) {
            return CollectionsKt.emptyList();
        }
        IrClass owner = getDeclarationStorage().getClassifierStorage().getIrClassSymbol(firClass.getSymbol()).getOwner();
        IrClass irClass = owner instanceof IrClass ? owner : null;
        if (irClass == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (IrClass irClass2 : superClasses) {
            if (Intrinsics.areEqual(irClass2, irClass) || (overridden.getRawStatus().isStatic() && !Intrinsics.areEqual(irClass2.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE))) {
                sInvoke = irProducer.invoke(overridden);
            } else {
                FirCallableDeclaration fakeOverrideInClass = getDeclarationStorage().getFakeOverrideInClass(irClass2, (FirCallableDeclaration) overridden.getFir());
                if (fakeOverrideInClass != null) {
                    FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = fakeOverrideInClass.getSymbol();
                    Intrinsics.checkNotNull(symbol2, "null cannot be cast to non-null type F of org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator.getOverriddenSymbolsInSupertypes$lambda$12$lambda$11");
                    sInvoke = irProducer.invoke(symbol2);
                } else {
                    sInvoke = null;
                }
            }
            if (sInvoke != null) {
                arrayList.add(sInvoke);
            }
        }
        return arrayList;
    }

    public final void bindOverriddenSymbols(List<? extends IrDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        for (IrDeclaration irDeclaration : declarations) {
            if (Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE)) {
                if (irDeclaration instanceof IrSimpleFunction) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                    List<IrSimpleFunctionSymbol> overriddenSymbolsForFakeOverride$fir2ir = getOverriddenSymbolsForFakeOverride$fir2ir(irSimpleFunction);
                    Intrinsics.checkNotNull(overriddenSymbolsForFakeOverride$fir2ir);
                    Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
                    fir2IrConversionScope.getFunctionStack().add(irSimpleFunction);
                    try {
                        irSimpleFunction.setOverriddenSymbols(overriddenSymbolsForFakeOverride$fir2ir);
                        Unit unit = Unit.INSTANCE;
                        fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                    } catch (Throwable th) {
                        fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                        throw th;
                    }
                } else if (irDeclaration instanceof IrProperty) {
                    List<FirPropertySymbol> list = this.basePropertySymbols.get(irDeclaration);
                    Intrinsics.checkNotNull(list);
                    List<FirPropertySymbol> list2 = list;
                    IrProperty irProperty = (IrProperty) irDeclaration;
                    Fir2IrConversionScope fir2IrConversionScope2 = this.conversionScope;
                    fir2IrConversionScope2.getPropertyStack().add(TuplesKt.to(irProperty, null));
                    try {
                        discardAccessorsAccordingToBaseVisibility(irProperty, list2);
                        setOverriddenSymbolsForProperty(irProperty, getDeclarationStorage(), ((IrProperty) irDeclaration).getIsVar(), list2);
                        Unit unit2 = Unit.INSTANCE;
                        fir2IrConversionScope2.getPropertyStack().remove(fir2IrConversionScope2.getPropertyStack().size() - 1);
                    } catch (Throwable th2) {
                        fir2IrConversionScope2.getPropertyStack().remove(fir2IrConversionScope2.getPropertyStack().size() - 1);
                        throw th2;
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0090  */
    /* JADX WARN: Multi-variable type inference failed */
    private final void discardAccessorsAccordingToBaseVisibility(IrProperty irProperty, List<? extends FirPropertySymbol> list) {
        Iterator<? extends FirPropertySymbol> it = list.iterator();
        while (it.hasNext()) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) it.next().getFir();
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                } else {
                    firCallableDeclaration = originalForSubstitutionOverrideAttr;
                }
            }
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
            if (symbol != null) {
                FirProperty firProperty = (FirProperty) ((FirPropertySymbol) symbol).getFir();
                FirPropertyAccessor getter = firProperty.getGetter();
                if (getter != null) {
                    Visibility visibility = getter.getStatus().getVisibility();
                    if (Visibilities.INSTANCE.isPrivate(visibility) || Intrinsics.areEqual(visibility, Visibilities.InvisibleFake.INSTANCE)) {
                        irProperty.setGetter(null);
                    }
                } else {
                    Visibility visibility2 = firProperty.getStatus().getVisibility();
                    if (Visibilities.INSTANCE.isPrivate(visibility2) || Intrinsics.areEqual(visibility2, Visibilities.InvisibleFake.INSTANCE)) {
                        irProperty.setGetter(null);
                    }
                }
                FirPropertyAccessor setter = firProperty.getSetter();
                if (setter != null) {
                    Visibility visibility3 = setter.getStatus().getVisibility();
                    if (Visibilities.INSTANCE.isPrivate(visibility3) || Intrinsics.areEqual(visibility3, Visibilities.InvisibleFake.INSTANCE)) {
                        irProperty.setSetter(null);
                    }
                } else {
                    Visibility visibility4 = firProperty.getStatus().getVisibility();
                    if (Visibilities.INSTANCE.isPrivate(visibility4) || Intrinsics.areEqual(visibility4, Visibilities.InvisibleFake.INSTANCE)) {
                        irProperty.setSetter(null);
                    }
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
            }
        }
    }

    private final IrProperty setOverriddenSymbolsForProperty(IrProperty irProperty, final Fir2IrDeclarationStorage fir2IrDeclarationStorage, boolean z, List<? extends FirPropertySymbol> list) {
        IrSimpleFunction setter;
        List overriddenSymbolsInSupertypes = getOverriddenSymbolsInSupertypes(irProperty, list, new Function1<FirPropertySymbol, IrPropertySymbol>() { // from class: org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator$setOverriddenSymbolsForProperty$overriddenIrSymbols$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrPropertySymbol invoke(FirPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSymbol irPropertySymbol$default = Fir2IrDeclarationStorage.getIrPropertySymbol$default(fir2IrDeclarationStorage, it, null, 2, null);
                Intrinsics.checkNotNull(irPropertySymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
                return (IrPropertySymbol) irPropertySymbol$default;
            }
        });
        List list2 = overriddenSymbolsInSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrPropertySymbol) it.next()).getOwner());
        }
        ArrayList arrayList2 = arrayList;
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                IrSimpleFunction getter2 = ((IrProperty) it2.next()).getGetter();
                IrSimpleFunctionSymbol symbol = getter2 != null ? getter2.getSymbol() : null;
                if (symbol != null) {
                    arrayList3.add(symbol);
                }
            }
            getter.setOverriddenSymbols(arrayList3);
        }
        if (z && (setter = irProperty.getSetter()) != null) {
            ArrayList arrayList4 = new ArrayList();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                IrSimpleFunction setter2 = ((IrProperty) it3.next()).getSetter();
                IrSimpleFunctionSymbol symbol2 = setter2 != null ? setter2.getSymbol() : null;
                if (symbol2 != null) {
                    arrayList4.add(symbol2);
                }
            }
            setter.setOverriddenSymbols(arrayList4);
        }
        irProperty.setOverriddenSymbols(overriddenSymbolsInSupertypes);
        return irProperty;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<FirSimpleFunction, List<FirNamedFunctionSymbol>> createFirFunctionFakeOverride$fir2ir(FirClass klass, IrClass irClass, FirNamedFunctionSymbol originalSymbol, FirTypeScope scope) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
        Intrinsics.checkNotNullParameter(scope, "scope");
        ConeClassLikeLookupTag lookupTag = klass.getSymbol().getLookupTag();
        FirNamedFunctionSymbol firNamedFunctionSymbol = originalSymbol;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
        FirCallableSymbol<?> firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides = ConversionUtilsKt.unwrapSubstitutionAndIntersectionOverrides(firNamedFunctionSymbol);
        if (firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides;
        if (!shouldHaveComputedBaseSymbolsForClass(firNamedFunctionSymbol, lookupTag)) {
            FirCallableDeclaration firCallableDeclaration2 = firCallableDeclaration;
            if (Visibilities.INSTANCE.isPrivate(firCallableDeclaration2.getStatus().getVisibility()) || Intrinsics.areEqual(firCallableDeclaration2.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) {
                return null;
            }
            FirNamedFunctionSymbol firNamedFunctionSymbol3 = firNamedFunctionSymbol2;
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firCallableDeclaration;
            FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
            FirSession session = getSession();
            ConeClassLikeLookupTag lookupTag2 = klass.getSymbol().getLookupTag();
            ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(klass);
            FirDeclarationOrigin.SubstitutionOverride.DeclarationSite declarationSite = FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE;
            Object obj = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
            FirNamedFunctionSymbol firNamedFunctionSymbolCreateSubstitutionOverrideFunction = firFakeOverrideGenerator.createSubstitutionOverrideFunction(session, firNamedFunctionSymbol3, firSimpleFunction, lookupTag2, coneClassLikeTypeDefaultType, declarationSite, (6080 & 64) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 128) != 0 ? null : null), (6080 & 256) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 512) != 0 ? null : null), (List<? extends FirTypeParameter>) ((6080 & 1024) != 0 ? null : null), (6080 & 2048) != 0 ? firSimpleFunction.getStatus().isExpect() : (obj != null && ((FirMemberDeclaration) obj).getStatus().isExpect()) || firSimpleFunction.getStatus().isExpect(), (6080 & 4096) != 0 ? null : null);
            getDeclarationStorage().saveFakeOverrideInClass$fir2ir(irClass, firCallableDeclaration, (FirCallableDeclaration) firNamedFunctionSymbolCreateSubstitutionOverrideFunction.getFir());
            getClassifierStorage().preCacheTypeParameters$fir2ir(firCallableDeclaration, irClass.getSymbol());
            return TuplesKt.to(firNamedFunctionSymbolCreateSubstitutionOverrideFunction.getFir(), CollectionsKt.listOf(firNamedFunctionSymbol));
        }
        if (((FirCallableDeclaration) firNamedFunctionSymbol.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
            FirCallableDeclaration firCallableDeclaration3 = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration3) || (firCallableDeclaration3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration3) : null;
            FirNamedFunctionSymbol firNamedFunctionSymbol4 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
            Intrinsics.checkNotNull(firNamedFunctionSymbol4);
            arrayListListOf = CollectionsKt.listOf(firNamedFunctionSymbol4);
        } else {
            List<FirCallableSymbol> directOverriddenFunctions$default = FirTypeScopeKt.getDirectOverriddenFunctions$default(scope, firNamedFunctionSymbol, false, 2, null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directOverriddenFunctions$default, 10));
            for (FirNamedFunctionSymbol firNamedFunctionSymbol5 : directOverriddenFunctions$default) {
                if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firNamedFunctionSymbol5.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firNamedFunctionSymbol5), lookupTag)) {
                    FirCallableDeclaration firCallableDeclaration4 = (FirCallableDeclaration) firNamedFunctionSymbol5.getFir();
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration4) || (firCallableDeclaration4.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration4) : null;
                    firNamedFunctionSymbol5 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr2 != null ? originalForSubstitutionOverrideAttr2.getSymbol() : null);
                    Intrinsics.checkNotNull(firNamedFunctionSymbol5);
                }
                arrayList.add(firNamedFunctionSymbol5);
            }
            arrayListListOf = arrayList;
        }
        return TuplesKt.to(firCallableDeclaration, arrayListListOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<FirProperty, List<FirPropertySymbol>> createFirPropertyFakeOverride$fir2ir(FirClass klass, IrClass irClass, FirPropertySymbol originalSymbol, FirTypeScope scope) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
        Intrinsics.checkNotNullParameter(scope, "scope");
        ConeClassLikeLookupTag lookupTag = klass.getSymbol().getLookupTag();
        FirPropertySymbol firPropertySymbol = originalSymbol;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol.getFir();
        FirCallableSymbol<?> firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides = ConversionUtilsKt.unwrapSubstitutionAndIntersectionOverrides(firPropertySymbol);
        if (firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
        }
        FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) firCallableSymbolUnwrapSubstitutionAndIntersectionOverrides;
        if (!shouldHaveComputedBaseSymbolsForClass(firPropertySymbol, lookupTag)) {
            FirCallableDeclaration firCallableDeclaration2 = firCallableDeclaration;
            if (Visibilities.INSTANCE.isPrivate(firCallableDeclaration2.getStatus().getVisibility()) || Intrinsics.areEqual(firCallableDeclaration2.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) {
                return null;
            }
            FirPropertySymbol firPropertySymbol3 = firPropertySymbol2;
            FirProperty firProperty = (FirProperty) firCallableDeclaration;
            FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
            FirSession session = getSession();
            ConeClassLikeLookupTag lookupTag2 = klass.getSymbol().getLookupTag();
            ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(klass);
            FirDeclarationOrigin.SubstitutionOverride.DeclarationSite declarationSite = FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE;
            Object obj = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
            FirPropertySymbol firPropertySymbolCreateSubstitutionOverrideProperty = firFakeOverrideGenerator.createSubstitutionOverrideProperty(session, firPropertySymbol3, firProperty, lookupTag2, coneClassLikeTypeDefaultType, declarationSite, (XKeySym.XK_overbar & 64) != 0 ? null : null, (XKeySym.XK_overbar & 128) != 0 ? null : null, (XKeySym.XK_overbar & 256) != 0 ? null : null, (XKeySym.XK_overbar & 512) != 0 ? null : null, (XKeySym.XK_overbar & 1024) != 0 ? firProperty.getStatus().isExpect() : (obj != null && ((FirMemberDeclaration) obj).getStatus().isExpect()) || firProperty.getStatus().isExpect(), (XKeySym.XK_overbar & 2048) != 0 ? null : null);
            getDeclarationStorage().saveFakeOverrideInClass$fir2ir(irClass, firCallableDeclaration, (FirCallableDeclaration) firPropertySymbolCreateSubstitutionOverrideProperty.getFir());
            getClassifierStorage().preCacheTypeParameters$fir2ir(firCallableDeclaration, irClass.getSymbol());
            return TuplesKt.to(firPropertySymbolCreateSubstitutionOverrideProperty.getFir(), CollectionsKt.listOf(firPropertySymbol));
        }
        if (((FirCallableDeclaration) firPropertySymbol.getFir()).getOrigin() instanceof FirDeclarationOrigin.SubstitutionOverride) {
            FirCallableDeclaration firCallableDeclaration3 = (FirCallableDeclaration) firPropertySymbol.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration3) || (firCallableDeclaration3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration3) : null;
            FirPropertySymbol firPropertySymbol4 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
            Intrinsics.checkNotNull(firPropertySymbol4);
            arrayListListOf = CollectionsKt.listOf(firPropertySymbol4);
        } else {
            List<FirCallableSymbol> directOverriddenProperties$default = FirTypeScopeKt.getDirectOverriddenProperties$default(scope, firPropertySymbol, false, 2, null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(directOverriddenProperties$default, 10));
            for (FirPropertySymbol firPropertySymbol5 : directOverriddenProperties$default) {
                if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) firPropertySymbol5.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableSymbol<?>) firPropertySymbol5), lookupTag)) {
                    FirCallableDeclaration firCallableDeclaration4 = (FirCallableDeclaration) firPropertySymbol5.getFir();
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration4) || (firCallableDeclaration4.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration4) : null;
                    firPropertySymbol5 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr2 != null ? originalForSubstitutionOverrideAttr2.getSymbol() : null);
                    Intrinsics.checkNotNull(firPropertySymbol5);
                }
                arrayList.add(firPropertySymbol5);
            }
            arrayListListOf = arrayList;
        }
        return TuplesKt.to(firCallableDeclaration, arrayListListOf);
    }
}
