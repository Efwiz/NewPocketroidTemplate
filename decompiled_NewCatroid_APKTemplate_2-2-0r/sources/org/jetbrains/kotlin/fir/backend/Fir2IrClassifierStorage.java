package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.Fir2IrClassSymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: Fir2IrClassifierStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¸\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J$\u0010x\u001a\u0002012\u0006\u0010y\u001a\u0002002\b\u0010z\u001a\u0004\u0018\u00010\u00192\n\b\u0002\u0010{\u001a\u0004\u0018\u00010|J/\u0010}\u001a\u00020r2\u0006\u0010~\u001a\u00020q2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\n\b\u0002\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0002J\u0012\u0010\u0085\u0001\u001a\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020JH\u0002J,\u0010\u0087\u0001\u001a\u00020\u00192\n\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u008c\u0001\u0012\u0004\u0012\u00020\u00190\u008b\u0001H\u0002J,\u0010\u008d\u0001\u001a\u0002012\n\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u008e\u0001\u0012\u0004\u0012\u0002010\u008b\u0001H\u0002J,\u0010\u008f\u0001\u001a\u00020k2\n\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0090\u0001\u0012\u0004\u0012\u00020k0\u008b\u0001H\u0002J\u0013\u0010\u0091\u0001\u001a\u0004\u0018\u00010\u00192\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001J\u0012\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u00192\u0007\u0010\u0086\u0001\u001a\u00020JJ\u0012\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u00192\u0007\u0010\u0096\u0001\u001a\u00020\u001eJ\u0019\u0010\u0097\u0001\u001a\u0004\u0018\u0001012\u0006\u0010y\u001a\u000200H\u0000¢\u0006\u0003\b\u0098\u0001J%\u0010\u0099\u0001\u001a\u0004\u0018\u00010r2\u0006\u0010~\u001a\u00020q2\n\b\u0002\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0000¢\u0006\u0003\b\u009a\u0001J\u0015\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u00192\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0002J\u001a\u0010\u009c\u0001\u001a\u0004\u0018\u00010k2\u0007\u0010\u009d\u0001\u001a\u00020jH\u0000¢\u0006\u0003\b\u009e\u0001J\u0018\u0010\u009f\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010;2\u0007\u0010 \u0001\u001a\u00020\u0019J'\u0010¡\u0001\u001a\u00020\u00192\b\u0010¢\u0001\u001a\u00030£\u00012\b\u0010¤\u0001\u001a\u00030¥\u00012\b\u0010z\u001a\u0004\u0018\u00010\u0019H\u0002J\u0016\u0010¦\u0001\u001a\u00030\u008c\u00012\f\u0010§\u0001\u001a\u0007\u0012\u0002\b\u00030¨\u0001J\u0012\u0010©\u0001\u001a\u00030\u008c\u00012\b\u0010ª\u0001\u001a\u00030\u0093\u0001J#\u0010«\u0001\u001a\u0002012\u0006\u0010y\u001a\u0002002\u0006\u0010z\u001a\u00020\u00192\n\b\u0002\u0010{\u001a\u0004\u0018\u00010|J6\u0010¬\u0001\u001a\u00020r2\u0006\u0010~\u001a\u00020q2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\n\b\u0002\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0000¢\u0006\u0003\b\u00ad\u0001J\u001c\u0010®\u0001\u001a\u00030¯\u00012\b\u0010°\u0001\u001a\u00030±\u00012\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001J\u0011\u0010²\u0001\u001a\u00020R2\u0006\u0010y\u001a\u000200H\u0002J\b\u0010³\u0001\u001a\u00030´\u0001J$\u0010µ\u0001\u001a\u00030´\u00012\b\u0010¶\u0001\u001a\u00030·\u00012\b\u0010¸\u0001\u001a\u00030\u0082\u0001H\u0000¢\u0006\u0003\b¹\u0001J\u001b\u0010º\u0001\u001a\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020J2\t\b\u0002\u0010 \u0001\u001a\u00020\u0019J\b\u0010»\u0001\u001a\u00030´\u0001J\u0019\u0010¼\u0001\u001a\u00030´\u00012\u0006\u0010y\u001a\u0002002\u0007\u0010½\u0001\u001a\u00020\u0019J\u001a\u0010¾\u0001\u001a\u00020\u00192\u0007\u0010\u0096\u0001\u001a\u00020\u001e2\b\u0010¿\u0001\u001a\u00030À\u0001J6\u0010Á\u0001\u001a\u00020\u00192\b\u0010¢\u0001\u001a\u00030£\u00012\n\b\u0002\u0010Â\u0001\u001a\u00030Ã\u00012\n\b\u0002\u0010¤\u0001\u001a\u00030¥\u00012\u000b\b\u0002\u0010z\u001a\u0005\u0018\u00010Ä\u0001J&\u0010Å\u0001\u001a\u00020\u00192\u0007\u0010Æ\u0001\u001a\u00020\u00182\b\u0010Ç\u0001\u001a\u00030Ä\u00012\n\b\u0002\u0010{\u001a\u0004\u0018\u00010|J\u001a\u0010È\u0001\u001a\u00020k2\u0007\u0010É\u0001\u001a\u00020j2\b\u0010Ç\u0001\u001a\u00030Ä\u0001J\u001a\u0010Ê\u0001\u001a\b\u0012\u0004\u0012\u00020<0;*\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020\u0018J\u0017\u0010Ë\u0001\u001a\u00030´\u0001*\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020JH\u0002J\u0017\u0010Ì\u0001\u001a\u00030´\u0001*\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020JH\u0002J\u0017\u0010Í\u0001\u001a\u00030´\u0001*\u00020\u00192\u0007\u0010\u0086\u0001\u001a\u00020\u0018H\u0002J\u000e\u0010Î\u0001\u001a\u00030Ï\u0001*\u00020\u0018H\u0002J\r\u0010Ð\u0001\u001a\u00020R*\u00020\u0018H\u0002J\u001e\u0010Ñ\u0001\u001a\u00030´\u0001*\u00020\u00192\u000e\u0010Ò\u0001\u001a\t\u0012\u0005\u0012\u00030Ó\u00010;H\u0002J+\u0010Ô\u0001\u001a\u00030´\u0001*\u00030Õ\u00012\b\u0010¶\u0001\u001a\u00030·\u00012\n\b\u0002\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0000¢\u0006\u0003\bÖ\u0001J\u001b\u0010×\u0001\u001a\u00030Ø\u0001*\u00030Ù\u00012\n\b\u0002\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0002R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00190\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001f\u001a\u00020 X\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0012\u0010#\u001a\u00020$X\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0012\u0010'\u001a\u00020(X\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u001a\u0010/\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R \u0010:\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020<0;0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010=\u001a\u00020>X\u0096\u0005¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0012\u0010A\u001a\u00020BX\u0096\u0005¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0018\u0010E\u001a\b\u0012\u0004\u0012\u00020F0;X\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u001a\u0010I\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020\u00190\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020LX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010M\u001a\u00020NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u000e\u0010Q\u001a\u00020RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010S\u001a\u00020TX\u0096\u0005¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0012\u0010W\u001a\u00020XX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u0012\u0010[\u001a\u00020\\X\u0096\u0005¢\u0006\u0006\u001a\u0004\b]\u0010^R\u0012\u0010_\u001a\u00020`X\u0096\u0005¢\u0006\u0006\u001a\u0004\ba\u0010bR\u001b\u0010c\u001a\u00020d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bg\u0010h\u001a\u0004\be\u0010fR\u001a\u0010i\u001a\u000e\u0012\u0004\u0012\u00020j\u0012\u0004\u0012\u00020k0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010l\u001a\u00020mX\u0096\u0005¢\u0006\u0006\u001a\u0004\bn\u0010oR\u001a\u0010p\u001a\u000e\u0012\u0004\u0012\u00020q\u0012\u0004\u0012\u00020r0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010s\u001a\u000e\u0012\u0004\u0012\u00020q\u0012\u0004\u0012\u00020r0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010t\u001a\u00020uX\u0096\u0005¢\u0006\u0006\u001a\u0004\bv\u0010w¨\u0006Ú\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "commonMemberStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classCache", "", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "classifierStorage", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "codeFragmentCache", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "enumEntryCache", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "fieldsForContextReceivers", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "localClassesCreatedOnTheFly", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "localStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalClassStorage;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "processMembersOfClassesOnTheFlyImmediately", "", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "temporaryParent", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getTemporaryParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "temporaryParent$delegate", "Lkotlin/Lazy;", "typeAliasCache", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "typeParameterCache", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "typeParameterCacheForSetter", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "createIrEnumEntry", "enumEntry", "irParent", "predefinedOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createIrTypeParameterWithoutBounds", "typeParameter", "index", "", "ownerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "createLocalIrClassOnTheFly", "klass", "declareIrClass", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "declareIrEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "declareIrTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "findIrClass", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getCachedIrClass", "getCachedIrCodeFragment", "codeFragment", "getCachedIrEnumEntry", "getCachedIrEnumEntry$fir2ir", "getCachedIrTypeParameter", "getCachedIrTypeParameter$fir2ir", "getCachedLocalClass", "getCachedTypeAlias", "firTypeAlias", "getCachedTypeAlias$fir2ir", "getFieldsWithContextReceiversForClass", "irClass", "getIrAnonymousObjectForEnumEntry", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getIrClassSymbol", "firClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getIrClassSymbolForNotFoundClass", "classLikeLookupTag", "getIrEnumEntry", "getIrTypeParameter", "getIrTypeParameter$fir2ir", "getIrTypeParameterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "firTypeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "isEnumEntryWhichRequiresSubclass", "preCacheBuiltinClasses", "", "preCacheTypeParameters", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "irOwnerSymbol", "preCacheTypeParameters$fir2ir", "processClassHeader", "processMembersOfClassesCreatedOnTheFly", "putEnumEntryClassInScope", "correspondingClass", "registerCodeFragmentClass", "containingFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "registerIrAnonymousObject", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "registerIrClass", "regularClass", "parent", "registerTypeAlias", "typeAlias", "createContextReceiverFields", "declareSupertypes", "declareTypeParameters", "declareValueClassRepresentation", "enumClassModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "hasAbstractMembersInScope", "setThisReceiver", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "setTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "setTypeParameters$fir2ir", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrClassifierStorage implements Fir2IrComponents {
    private final Map<FirRegularClass, IrClass> classCache;
    private final Map<FirCodeFragment, IrClass> codeFragmentCache;
    private final Fir2IrComponents components;
    private final Map<FirEnumEntry, IrEnumEntry> enumEntryCache;
    private final Map<IrClass, List<IrField>> fieldsForContextReceivers;
    private final Map<FirClass, IrClass> localClassesCreatedOnTheFly;
    private final Fir2IrLocalClassStorage localStorage;
    private boolean processMembersOfClassesOnTheFlyImmediately;

    /* JADX INFO: renamed from: temporaryParent$delegate, reason: from kotlin metadata */
    private final Lazy temporaryParent;
    private final Map<FirTypeAlias, IrTypeAlias> typeAliasCache;
    private final Map<FirTypeParameter, IrTypeParameter> typeParameterCache;
    private final Map<FirTypeParameter, IrTypeParameter> typeParameterCacheForSetter;

    /* JADX INFO: compiled from: Fir2IrClassifierStorage.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

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

    public Fir2IrClassifierStorage(Fir2IrComponents components, Fir2IrCommonMemberStorage commonMemberStorage) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(commonMemberStorage, "commonMemberStorage");
        this.components = components;
        this.classCache = commonMemberStorage.getClassCache();
        this.localClassesCreatedOnTheFly = new LinkedHashMap();
        this.typeAliasCache = new LinkedHashMap();
        this.typeParameterCache = commonMemberStorage.getTypeParameterCache();
        this.typeParameterCacheForSetter = new LinkedHashMap();
        this.enumEntryCache = commonMemberStorage.getEnumEntryCache();
        this.codeFragmentCache = new LinkedHashMap();
        this.fieldsForContextReceivers = new LinkedHashMap();
        this.localStorage = new Fir2IrLocalClassStorage(commonMemberStorage.getLocalClassCache());
        this.temporaryParent = LazyKt.lazy(new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$temporaryParent$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrFactory irFactory = this.this$0.getIrFactory();
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Name nameSpecial = Name.special("<stub>");
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
                Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
                IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -1, -1, defined, nameSpecial, PRIVATE, false, false, this.this$0.getIrBuiltIns().getUnitType(), Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, 1, null), false, false, false, false, false, null, false, 98304, null);
                IrExternalPackageFragmentSymbolImpl irExternalPackageFragmentSymbolImpl = new IrExternalPackageFragmentSymbolImpl(null, 1, null);
                FqName ROOT = FqName.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                irSimpleFunctionCreateSimpleFunction$default.setParent(new IrExternalPackageFragmentImpl(irExternalPackageFragmentSymbolImpl, ROOT));
                return irSimpleFunctionCreateSimpleFunction$default;
            }
        });
    }

    static /* synthetic */ IrType toIrType$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrClassifierStorage.toIrType(firTypeRef, conversionTypeOrigin);
    }

    private final IrType toIrType(FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin) {
        return getTypeConverter().toIrType(firTypeRef, conversionTypeOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void preCacheBuiltinClasses() {
        Iterator<Map.Entry<ClassId, IrClassSymbol>> it = getTypeConverter().getClassIdToSymbolMap$fir2ir().entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<ClassId, IrClassSymbol> next = it.next();
            ClassId key = next.getKey();
            IrClassSymbol value = next.getValue();
            FirClassifierSymbol<?> symbol = FirSymbolProviderKt.toSymbol(key, getSession());
            FirRegularClass firRegularClass = (FirRegularClass) (symbol != null ? symbol.getFir() : null);
            if (firRegularClass != null) {
                IrClass owner = value.getOwner();
                this.classCache.put(firRegularClass, owner);
                processClassHeader(firRegularClass, owner);
                getDeclarationStorage().preCacheBuiltinClassMembers$fir2ir(firRegularClass, owner);
            }
        }
        for (Map.Entry<ClassId, ClassId> entry : StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().entrySet()) {
            ClassId key2 = entry.getKey();
            FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) TypeConstructionUtilsKt.toLookupTag(entry.getValue()), getSession());
            FirRegularClass firRegularClass2 = (FirRegularClass) (symbol2 != null ? (FirClassLikeDeclaration) symbol2.getFir() : null);
            if (firRegularClass2 != null) {
                IrClassSymbol irClassSymbol = getIrBuiltIns().getPrimitiveArrayForType().get(getTypeConverter().getClassIdToTypeMap$fir2ir().get(key2));
                Intrinsics.checkNotNull(irClassSymbol);
                IrClass owner2 = irClassSymbol.getOwner();
                this.classCache.put(firRegularClass2, owner2);
                processClassHeader(firRegularClass2, owner2);
                getDeclarationStorage().preCacheBuiltinClassMembers$fir2ir(firRegularClass2, owner2);
            }
        }
    }

    private final void setThisReceiver(IrClass irClass, List<? extends FirTypeParameterRef> list) {
        IrClass irClass2 = irClass;
        getSymbolTable().enterScope(irClass2);
        List<? extends FirTypeParameterRef> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new IrSimpleTypeImpl((IrClassifierSymbol) getIrTypeParameterSymbol(((FirTypeParameterRef) it.next()).getSymbol(), ConversionTypeOrigin.DEFAULT), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
        }
        boolean z = false;
        irClass.setThisReceiver(ConversionUtilsKt.declareThisReceiverParameter$default(this, irClass, new IrSimpleTypeImpl(irClass.getSymbol(), z, arrayList, CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, 0, 0, null, null, 120, null));
        getSymbolTable().leaveScope(irClass2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void preCacheTypeParameters$fir2ir(FirTypeParameterRefsOwner owner, IrSymbol irOwnerSymbol) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(irOwnerSymbol, "irOwnerSymbol");
        Iterator<FirTypeParameterRef> it = owner.getTypeParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            FirTypeParameter firTypeParameter = (FirTypeParameter) it.next().getSymbol().getFir();
            if (getCachedIrTypeParameter$fir2ir$default(this, firTypeParameter, null, 2, null) == null) {
                createIrTypeParameterWithoutBounds$default(this, firTypeParameter, i, irOwnerSymbol, null, 8, null);
            }
            if ((owner instanceof FirProperty) && ((FirProperty) owner).getIsVar()) {
                ConversionTypeOrigin conversionTypeOrigin = ConversionTypeOrigin.SETTER;
                if (getCachedIrTypeParameter$fir2ir(firTypeParameter, conversionTypeOrigin) == null) {
                    createIrTypeParameterWithoutBounds(firTypeParameter, i, irOwnerSymbol, conversionTypeOrigin);
                }
            }
            i = i2;
        }
    }

    public static /* synthetic */ void setTypeParameters$fir2ir$default(Fir2IrClassifierStorage fir2IrClassifierStorage, IrTypeParametersContainer irTypeParametersContainer, FirTypeParameterRefsOwner firTypeParameterRefsOwner, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        fir2IrClassifierStorage.setTypeParameters$fir2ir(irTypeParametersContainer, firTypeParameterRefsOwner, conversionTypeOrigin);
    }

    public final void setTypeParameters$fir2ir(IrTypeParametersContainer irTypeParametersContainer, FirTypeParameterRefsOwner owner, ConversionTypeOrigin typeOrigin) {
        IrTypeParameter irTypeParameter$fir2ir;
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        List<FirTypeParameterRef> typeParameters = owner.getTypeParameters();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : typeParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) obj;
            if (firTypeParameterRef instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef;
                irTypeParameter$fir2ir = getIrTypeParameter$fir2ir(firTypeParameter, i, irTypeParametersContainer.getSymbol(), typeOrigin);
                irTypeParameter$fir2ir.setParent(irTypeParametersContainer);
                if (irTypeParameter$fir2ir.getSuperTypes().isEmpty()) {
                    List<FirTypeRef> bounds = firTypeParameter.getBounds();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
                    Iterator<T> it = bounds.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(toIrType((FirTypeRef) it.next(), typeOrigin));
                    }
                    irTypeParameter$fir2ir.setSuperTypes(arrayList2);
                }
            } else {
                irTypeParameter$fir2ir = null;
            }
            if (irTypeParameter$fir2ir != null) {
                arrayList.add(irTypeParameter$fir2ir);
            }
            i = i2;
        }
        irTypeParametersContainer.setTypeParameters(arrayList);
    }

    private final void declareTypeParameters(IrClass irClass, FirClass firClass) {
        FirClass firClass2 = firClass;
        preCacheTypeParameters$fir2ir(firClass2, irClass.getSymbol());
        setTypeParameters$fir2ir$default(this, irClass, firClass2, null, 2, null);
        if (firClass instanceof FirRegularClass) {
            List<IrField> listCreateContextReceiverFields = createContextReceiverFields(irClass, (FirRegularClass) firClass);
            List<IrField> list = listCreateContextReceiverFields;
            if (list.isEmpty()) {
                return;
            }
            irClass.getDeclarations().addAll(list);
            this.fieldsForContextReceivers.put(irClass, listCreateContextReceiverFields);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<IrField> createContextReceiverFields(IrClass irClass, FirRegularClass klass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (klass.getContextReceivers().isEmpty()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (FirContextReceiver firContextReceiver : klass.getContextReceivers()) {
            int i2 = i + 1;
            IrFactory irFactory = this.components.getIrFactory();
            IrDeclarationOrigin.FIELD_FOR_CLASS_CONTEXT_RECEIVER field_for_class_context_receiver = IrDeclarationOrigin.FIELD_FOR_CLASS_CONTEXT_RECEIVER.INSTANCE;
            Name nameIdentifier = Name.identifier("contextReceiverField" + i);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            IrField irFieldCreateField = irFactory.createField(-1, -1, (IrDeclarationOrigin) field_for_class_context_receiver, nameIdentifier, PRIVATE, (IrFieldSymbol) new IrFieldSymbolImpl(null, 1, 0 == true ? 1 : 0), toIrType$default(this, firContextReceiver.getTypeRef(), null, 1, null), true, false, false);
            irFieldCreateField.setParent(irClass);
            arrayList.add(irFieldCreateField);
            i = i2;
        }
        return arrayList;
    }

    public final List<IrField> getFieldsWithContextReceiversForClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.fieldsForContextReceivers.get(irClass);
    }

    private final void declareSupertypes(IrClass irClass, FirClass firClass) {
        List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypeRefs, 10));
        Iterator<T> it = superTypeRefs.iterator();
        while (it.hasNext()) {
            arrayList.add(toIrType$default(this, (FirTypeRef) it.next(), null, 1, null));
        }
        irClass.setSuperTypes(arrayList);
    }

    private final void declareValueClassRepresentation(IrClass irClass, FirRegularClass firRegularClass) {
        if (irClass instanceof Fir2IrLazyClass) {
            return;
        }
        irClass.setValueClassRepresentation(ConversionUtilsKt.computeValueClassRepresentation(this, firRegularClass));
    }

    public final IrClass getCachedIrClass(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if ((klass instanceof FirAnonymousObject) || ((klass instanceof FirRegularClass) && Intrinsics.areEqual(klass.getStatus().getVisibility(), Visibilities.Local.INSTANCE))) {
            return this.localStorage.get(klass);
        }
        return this.classCache.get(klass);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrClass getCachedLocalClass(ConeClassLikeLookupTag lookupTag) {
        Fir2IrLocalClassStorage fir2IrLocalClassStorage = this.localStorage;
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(lookupTag, getSession());
        Intrinsics.checkNotNull(symbol);
        E fir = symbol.getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirClass");
        return fir2IrLocalClassStorage.get((FirClass) fir);
    }

    public final IrClass getCachedIrCodeFragment(FirCodeFragment codeFragment) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        return this.codeFragmentCache.get(codeFragment);
    }

    private final Modality enumClassModality(FirRegularClass firRegularClass) {
        List<FirDeclaration> declarations = firRegularClass.getDeclarations();
        if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
            for (FirDeclaration firDeclaration : declarations) {
                if ((firDeclaration instanceof FirCallableDeclaration) && ((FirMemberDeclaration) firDeclaration).getStatus().getModality() == Modality.ABSTRACT) {
                    return Modality.ABSTRACT;
                }
            }
        }
        List<FirDeclaration> declarations2 = firRegularClass.getDeclarations();
        if (!(declarations2 instanceof Collection) || !declarations2.isEmpty()) {
            for (FirDeclaration firDeclaration2 : declarations2) {
                if ((firDeclaration2 instanceof FirEnumEntry) && isEnumEntryWhichRequiresSubclass((FirEnumEntry) firDeclaration2)) {
                    if (hasAbstractMembersInScope(firRegularClass)) {
                        return Modality.ABSTRACT;
                    }
                    return Modality.OPEN;
                }
            }
        }
        return Modality.FINAL;
    }

    private final boolean hasAbstractMembersInScope(FirRegularClass firRegularClass) {
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) firRegularClass, getSession(), getScopeSession(), false, (FirResolvePhase) null);
        Set<Name> callableNames = firTypeScopeUnsubstitutedScope.getCallableNames();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        for (Name name2 : callableNames) {
            firTypeScopeUnsubstitutedScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.hasAbstractMembersInScope.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                        booleanRef.element = true;
                    }
                }
            });
            if (booleanRef.element) {
                return true;
            }
            firTypeScopeUnsubstitutedScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.hasAbstractMembersInScope.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it.getResolvedStatus().getModality() == Modality.ABSTRACT) {
                        booleanRef.element = true;
                    }
                }
            });
            if (booleanRef.element) {
                return true;
            }
        }
        return false;
    }

    private final IrClass createLocalIrClassOnTheFly(FirClass klass) {
        FirClass firClass = (FirClass) SequencesKt.last(SequencesKt.generateSequence(klass, new Function1<FirClass, FirClass>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createLocalIrClassOnTheFly$classOrLocalParent$1
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final FirClass invoke(FirClass c) {
                ConeClassLikeLookupTag containingClassForLocalAttr;
                Intrinsics.checkNotNullParameter(c, "c");
                FirRegularClass firRegularClass = c instanceof FirRegularClass ? (FirRegularClass) c : null;
                if (firRegularClass == null || (containingClassForLocalAttr = ClassMembersKt.getContainingClassForLocalAttr(firRegularClass)) == null) {
                    return null;
                }
                FirClassLikeSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(FirProviderKt.getFirProvider(this.this$0.getSession()).getSymbolProvider(), containingClassForLocalAttr);
                FirClassLikeDeclaration firClassLikeDeclaration = symbolByLookupTag != null ? (FirClassLikeDeclaration) symbolByLookupTag.getFir() : null;
                FirClass firClass2 = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
                if (firClass2 == null || !firClass2.getDeclarations().contains(c)) {
                    return null;
                }
                return firClass2;
            }
        }));
        IrClass irClassProcessLocalClassAndNestedClassesOnTheFly = getConverter().processLocalClassAndNestedClassesOnTheFly(firClass, getTemporaryParent());
        if (this.processMembersOfClassesOnTheFlyImmediately) {
            getConverter().processClassMembers$fir2ir(firClass, irClassProcessLocalClassAndNestedClassesOnTheFly);
            getConverter().bindFakeOverridesInClass(irClassProcessLocalClassAndNestedClassesOnTheFly);
        } else {
            this.localClassesCreatedOnTheFly.put(firClass, irClassProcessLocalClassAndNestedClassesOnTheFly);
        }
        if (firClass != klass && (irClassProcessLocalClassAndNestedClassesOnTheFly = getCachedIrClass(klass)) == null) {
            throw new IllegalStateException(("Assuming that all nested classes of " + FirDeclarationUtilKt.getClassId(firClass).asString() + " should already be cached").toString());
        }
        return irClassProcessLocalClassAndNestedClassesOnTheFly;
    }

    public final void processMembersOfClassesCreatedOnTheFly() {
        this.processMembersOfClassesOnTheFlyImmediately = true;
        for (Map.Entry<FirClass, IrClass> entry : this.localClassesCreatedOnTheFly.entrySet()) {
            FirClass key = entry.getKey();
            IrClass value = entry.getValue();
            getConverter().processClassMembers$fir2ir(key, value);
            getConverter().bindFakeOverridesInClass(value);
        }
        this.localClassesCreatedOnTheFly.clear();
    }

    public static /* synthetic */ IrClass processClassHeader$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirClass firClass, IrClass irClass, int i, Object obj) {
        if ((i & 2) != 0) {
            irClass = fir2IrClassifierStorage.getCachedIrClass(firClass);
            Intrinsics.checkNotNull(irClass);
        }
        return fir2IrClassifierStorage.processClassHeader(firClass, irClass);
    }

    public final IrClass processClassHeader(FirClass klass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        declareTypeParameters(irClass, klass);
        setThisReceiver(irClass, klass.getTypeParameters());
        declareSupertypes(irClass, klass);
        if (klass instanceof FirRegularClass) {
            declareValueClassRepresentation(irClass, (FirRegularClass) klass);
        }
        return irClass;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrTypeAlias declareIrTypeAlias(final IdSignature signature, Function1<? super IrTypeAliasSymbol, ? extends IrTypeAlias> factory) {
        if (signature == null) {
            return factory.invoke(new IrTypeAliasSymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareTypeAlias(signature, new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.declareIrTypeAlias.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrTypeAliasSymbol invoke() {
                return new Fir2IrTypeAliasSymbol(signature);
            }
        }, factory);
    }

    public final IrTypeAlias registerTypeAlias(final FirTypeAlias typeAlias, final IrDeclarationParent parent) {
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IdSignature idSignatureComposeSignature = getSignatureComposer().composeSignature(typeAlias);
        KtSourceElement source = typeAlias.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        return declareIrTypeAlias(idSignatureComposeSignature, new Function1<IrTypeAliasSymbol, IrTypeAlias>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$registerTypeAlias$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeAlias invoke(IrTypeAliasSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                this.this$0.preCacheTypeParameters$fir2ir(typeAlias, symbol);
                IrTypeAlias irTypeAliasCreateTypeAlias = this.this$0.getIrFactory().createTypeAlias(i, endOffset, IrDeclarationOrigin.DEFINED.INSTANCE, typeAlias.getName(), this.this$0.components.getVisibilityConverter().convertToDescriptorVisibility(typeAlias.getStatus().getVisibility()), symbol, typeAlias.getStatus().isActual(), Fir2IrClassifierStorage.toIrType$default(this.this$0, typeAlias.getExpandedTypeRef(), null, 1, null));
                IrDeclarationParent irDeclarationParent = parent;
                Fir2IrClassifierStorage fir2IrClassifierStorage = this.this$0;
                FirTypeAlias firTypeAlias = typeAlias;
                irTypeAliasCreateTypeAlias.setParent(irDeclarationParent);
                Fir2IrClassifierStorage.setTypeParameters$fir2ir$default(fir2IrClassifierStorage, irTypeAliasCreateTypeAlias, firTypeAlias, null, 2, null);
                if (irDeclarationParent instanceof IrFile) {
                    ((IrFile) irDeclarationParent).getDeclarations().add(irTypeAliasCreateTypeAlias);
                }
                this.this$0.typeAliasCache.put(typeAlias, irTypeAliasCreateTypeAlias);
                return irTypeAliasCreateTypeAlias;
            }
        });
    }

    public final IrTypeAlias getCachedTypeAlias$fir2ir(FirTypeAlias firTypeAlias) {
        Intrinsics.checkNotNullParameter(firTypeAlias, "firTypeAlias");
        return this.typeAliasCache.get(firTypeAlias);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrClass declareIrClass(final IdSignature signature, Function1<? super IrClassSymbol, ? extends IrClass> factory) {
        if (signature == null) {
            return factory.invoke(new IrClassSymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareClass(signature, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.declareIrClass.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return new Fir2IrClassSymbol(signature);
            }
        }, factory);
    }

    public static /* synthetic */ IrClass registerIrClass$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirRegularClass firRegularClass, IrDeclarationParent irDeclarationParent, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        return fir2IrClassifierStorage.registerIrClass(firRegularClass, irDeclarationParent, irDeclarationOrigin);
    }

    public final IrClass registerIrClass(final FirRegularClass regularClass, IrDeclarationParent parent, final IrDeclarationOrigin predefinedOrigin) {
        Modality modalityEnumClassModality;
        int startOffset;
        final int i;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(parent, "parent");
        FirRegularClass firRegularClass = regularClass;
        final Visibility visibility = firRegularClass.getStatus().getVisibility();
        int i2 = WhenMappings.$EnumSwitchMapping$0[regularClass.getClassKind().ordinal()];
        boolean z = true;
        if (i2 == 1) {
            modalityEnumClassModality = enumClassModality(regularClass);
        } else if (i2 == 2) {
            modalityEnumClassModality = Modality.OPEN;
        } else {
            modalityEnumClassModality = firRegularClass.getStatus().getModality();
            if (modalityEnumClassModality == null) {
                modalityEnumClassModality = Modality.FINAL;
            }
        }
        final Modality modality = modalityEnumClassModality;
        FirRegularClass firRegularClass2 = regularClass;
        if (!firRegularClass2.getSymbol().getClassId().isLocal() && getConfiguration().getLinkViaSignatures()) {
            z = false;
        }
        IdSignature idSignatureComposeSignature = z ? null : getSignatureComposer().composeSignature(firRegularClass2);
        KtSourceElement source = regularClass.getSource();
        KtSourceElement ktSourceElement = source;
        final int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        IrClass irClassDeclareIrClass = declareIrClass(idSignatureComposeSignature, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$registerIrClass$irClass$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(this.this$0.getIrFactory(), i, endOffset, ConversionUtilsKt.computeIrOrigin(regularClass, predefinedOrigin), regularClass.getName(), this.this$0.components.getVisibilityConverter().convertToDescriptorVisibility(visibility), symbol, regularClass.getClassKind(), modality, regularClass.getStatus().isExternal(), regularClass.getStatus().isCompanion(), regularClass.getStatus().isInner(), regularClass.getStatus().isData(), regularClass.getStatus().isInline(), regularClass.getStatus().isExpect(), regularClass.getStatus().isFun(), (SourceElement) null, 32768, (Object) null);
                irClassCreateClass$default.setMetadata(new FirMetadataSource.Class(regularClass));
                return irClassCreateClass$default;
            }
        });
        irClassDeclareIrClass.setParent(parent);
        if (Intrinsics.areEqual(firRegularClass.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            this.localStorage.set(regularClass, irClassDeclareIrClass);
        } else {
            this.classCache.put(regularClass, irClassDeclareIrClass);
        }
        return irClassDeclareIrClass;
    }

    public static /* synthetic */ IrClass registerIrAnonymousObject$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirAnonymousObject firAnonymousObject, Visibility visibility, Name name2, IrDeclarationParent irDeclarationParent, int i, Object obj) {
        if ((i & 2) != 0) {
            visibility = Visibilities.Local.INSTANCE;
        }
        if ((i & 4) != 0) {
            name2 = SpecialNames.NO_NAME_PROVIDED;
        }
        if ((i & 8) != 0) {
            irDeclarationParent = null;
        }
        return fir2IrClassifierStorage.registerIrAnonymousObject(firAnonymousObject, visibility, name2, irDeclarationParent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass registerIrAnonymousObject(FirAnonymousObject anonymousObject, Visibility visibility, Name name2, IrDeclarationParent irParent) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        Modality modality = Modality.FINAL;
        KtSourceElement source = anonymousObject.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            startOffset = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(getIrFactory(), startOffset, endOffset, (IrDeclarationOrigin) defined, name2, this.components.getVisibilityConverter().convertToDescriptorVisibility(visibility), (IrClassSymbol) new IrClassSymbolImpl(null, 1, 0 == true ? 1 : 0), anonymousObject.getClassKind(), modality, false, false, false, false, false, false, false, (SourceElement) null, 65280, (Object) null);
        FirAnonymousObject firAnonymousObject = anonymousObject;
        irClassCreateClass$default.setMetadata(new FirMetadataSource.Class(firAnonymousObject));
        IrClass irClass = irClassCreateClass$default;
        if (irParent != null) {
            irClass.setParent(irParent);
        }
        this.localStorage.set(firAnonymousObject, irClass);
        return irClass;
    }

    public final IrClass registerCodeFragmentClass(final FirCodeFragment codeFragment, final IrFile containingFile) {
        int startOffset;
        final int i;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        final CodeFragmentConversionData conversionData = CodeFragmentUtilsKt.getConversionData(codeFragment);
        IdSignature idSignatureComposeSignature = getSignatureComposer().composeSignature(codeFragment);
        KtSourceElement source = codeFragment.getSource();
        KtSourceElement ktSourceElement = source;
        final int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        IrClass irClassDeclareIrClass = declareIrClass(idSignatureComposeSignature, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$registerCodeFragmentClass$irClass$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrFactory irFactory = this.this$0.getIrFactory();
                int i2 = i;
                int i3 = endOffset;
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Name shortClassName = conversionData.getClassId().getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(irFactory, i2, i3, (IrDeclarationOrigin) defined, shortClassName, PUBLIC, symbol, ClassKind.CLASS, Modality.FINAL, false, false, false, false, false, false, false, (SourceElement) null, 32768, (Object) null);
                FirCodeFragment firCodeFragment = codeFragment;
                IrFile irFile = containingFile;
                Fir2IrClassifierStorage fir2IrClassifierStorage = this.this$0;
                irClassCreateClass$default.setMetadata(new FirMetadataSource.CodeFragment(firCodeFragment));
                irClassCreateClass$default.setParent(irFile);
                irClassCreateClass$default.setTypeParameters(CollectionsKt.emptyList());
                irClassCreateClass$default.setThisReceiver(ConversionUtilsKt.declareThisReceiverParameter$default(fir2IrClassifierStorage, irClassCreateClass$default, new IrSimpleTypeImpl((IrClassifierSymbol) symbol, false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, 0, 0, null, null, 120, null));
                irClassCreateClass$default.setSuperTypes(CollectionsKt.listOf(fir2IrClassifierStorage.getIrBuiltIns().getAnyType()));
                return irClassCreateClass$default;
            }
        });
        this.codeFragmentCache.put(codeFragment, irClassDeclareIrClass);
        return irClassDeclareIrClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getIrAnonymousObjectForEnumEntry(FirAnonymousObject anonymousObject, Name name2, IrClass irParent) {
        FirAnonymousObject firAnonymousObject = anonymousObject;
        IrClass irClass = this.localStorage.get(firAnonymousObject);
        if (irClass != null) {
            return irClass;
        }
        IrClass irClassRegisterIrAnonymousObject = registerIrAnonymousObject(anonymousObject, Visibilities.Private.INSTANCE, name2, irParent);
        processClassHeader(firAnonymousObject, irClassRegisterIrAnonymousObject);
        return irClassRegisterIrAnonymousObject;
    }

    static /* synthetic */ IrTypeParameter createIrTypeParameterWithoutBounds$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirTypeParameter firTypeParameter, int i, IrSymbol irSymbol, ConversionTypeOrigin conversionTypeOrigin, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrClassifierStorage.createIrTypeParameterWithoutBounds(firTypeParameter, i, irSymbol, conversionTypeOrigin);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:27:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:28:0x00c5  */
    /* JADX WARN: Multi-variable type inference failed */
    private final IrTypeParameter createIrTypeParameterWithoutBounds(final FirTypeParameter typeParameter, final int index, IrSymbol ownerSymbol, ConversionTypeOrigin typeOrigin) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        int i2;
        IrTypeParameter irTypeParameterCreateTypeParameter;
        if (index < 0) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        final IrDeclarationOrigin irDeclarationOriginComputeIrOrigin$default = ConversionUtilsKt.computeIrOrigin$default(typeParameter, null, 1, null);
        KtSourceElement source = typeParameter.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        final IdSignature idSignatureComposeTypeParameterSignature = getSignatureComposer().composeTypeParameterSignature(index, ownerSymbol.getSignature());
        if (idSignatureComposeTypeParameterSignature != null) {
            if (ownerSymbol instanceof IrClassifierSymbol) {
                final int i3 = i;
                final int i4 = endOffset;
                irTypeParameterCreateTypeParameter = getSymbolTable().declareGlobalTypeParameter(idSignatureComposeTypeParameterSignature, new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createIrTypeParameterWithoutBounds$irTypeParameter$1$1$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrTypeParameterSymbol invoke() {
                        return new IrTypeParameterPublicSymbolImpl(idSignatureComposeTypeParameterSignature, null, 2, null);
                    }
                }, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createIrTypeParameterWithoutBounds$irTypeParameter$1$1$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrTypeParameter invoke(IrTypeParameterSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        IrFactory irFactory = this.this$0.getIrFactory();
                        int i5 = i3;
                        int i6 = i4;
                        IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeIrOrigin$default;
                        Name name2 = typeParameter.getName();
                        Variance variance = typeParameter.getVariance();
                        int i7 = index;
                        if (i7 < 0) {
                            i7 = 0;
                        }
                        return irFactory.createTypeParameter(i5, i6, irDeclarationOrigin, name2, symbol, variance, i7, typeParameter.getIsReified());
                    }
                });
            } else {
                final int i5 = i;
                final int i6 = endOffset;
                irTypeParameterCreateTypeParameter = getSymbolTable().declareScopedTypeParameter(idSignatureComposeTypeParameterSignature, new Function1<IdSignature, IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createIrTypeParameterWithoutBounds$irTypeParameter$1$1$1$3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrTypeParameterSymbol invoke(IdSignature it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new IrTypeParameterPublicSymbolImpl(idSignatureComposeTypeParameterSignature, null, 2, null);
                    }
                }, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createIrTypeParameterWithoutBounds$irTypeParameter$1$1$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrTypeParameter invoke(IrTypeParameterSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        IrFactory irFactory = this.this$0.getIrFactory();
                        int i7 = i5;
                        int i8 = i6;
                        IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeIrOrigin$default;
                        Name name2 = typeParameter.getName();
                        Variance variance = typeParameter.getVariance();
                        int i9 = index;
                        if (i9 < 0) {
                            i9 = 0;
                        }
                        return irFactory.createTypeParameter(i7, i8, irDeclarationOrigin, name2, symbol, variance, i9, typeParameter.getIsReified());
                    }
                });
            }
            if (irTypeParameterCreateTypeParameter == null) {
                IrFactory irFactory = getIrFactory();
                Name name2 = typeParameter.getName();
                IrTypeParameterSymbolImpl irTypeParameterSymbolImpl = new IrTypeParameterSymbolImpl(null, 1, 0 == true ? 1 : 0);
                Variance variance = typeParameter.getVariance();
                if (index < 0) {
                    i2 = 0;
                } else {
                    i2 = index;
                }
                irTypeParameterCreateTypeParameter = irFactory.createTypeParameter(i, endOffset, irDeclarationOriginComputeIrOrigin$default, name2, irTypeParameterSymbolImpl, variance, i2, typeParameter.getIsReified());
            }
        } else {
            IrFactory irFactory2 = getIrFactory();
            Name name3 = typeParameter.getName();
            IrTypeParameterSymbolImpl irTypeParameterSymbolImpl2 = new IrTypeParameterSymbolImpl(null, 1, 0 == true ? 1 : 0);
            Variance variance2 = typeParameter.getVariance();
            if (index < 0) {
                i2 = 0;
            } else {
                i2 = index;
            }
            irTypeParameterCreateTypeParameter = irFactory2.createTypeParameter(i, endOffset, irDeclarationOriginComputeIrOrigin$default, name3, irTypeParameterSymbolImpl2, variance2, i2, typeParameter.getIsReified());
        }
        IrTypeParameter irTypeParameter = irTypeParameterCreateTypeParameter;
        if (typeOrigin.getForSetter()) {
            this.typeParameterCacheForSetter.put(typeParameter, irTypeParameter);
        } else {
            this.typeParameterCache.put(typeParameter, irTypeParameter);
        }
        getAnnotationGenerator().generate(irTypeParameter, typeParameter);
        return irTypeParameter;
    }

    public static /* synthetic */ IrTypeParameter getCachedIrTypeParameter$fir2ir$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirTypeParameter firTypeParameter, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrClassifierStorage.getCachedIrTypeParameter$fir2ir(firTypeParameter, conversionTypeOrigin);
    }

    public final IrTypeParameter getCachedIrTypeParameter$fir2ir(FirTypeParameter typeParameter, ConversionTypeOrigin typeOrigin) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        if (typeOrigin.getForSetter()) {
            return this.typeParameterCacheForSetter.get(typeParameter);
        }
        return this.typeParameterCache.get(typeParameter);
    }

    public static /* synthetic */ IrTypeParameter getIrTypeParameter$fir2ir$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirTypeParameter firTypeParameter, int i, IrSymbol irSymbol, ConversionTypeOrigin conversionTypeOrigin, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrClassifierStorage.getIrTypeParameter$fir2ir(firTypeParameter, i, irSymbol, conversionTypeOrigin);
    }

    public final IrTypeParameter getIrTypeParameter$fir2ir(FirTypeParameter typeParameter, int index, IrSymbol ownerSymbol, ConversionTypeOrigin typeOrigin) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(ownerSymbol, "ownerSymbol");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        IrTypeParameter cachedIrTypeParameter$fir2ir = getCachedIrTypeParameter$fir2ir(typeParameter, typeOrigin);
        if (cachedIrTypeParameter$fir2ir != null) {
            return cachedIrTypeParameter$fir2ir;
        }
        IrTypeParameter irTypeParameterCreateIrTypeParameterWithoutBounds = createIrTypeParameterWithoutBounds(typeParameter, index, ownerSymbol, typeOrigin);
        List<FirTypeRef> bounds = typeParameter.getBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
        Iterator<T> it = bounds.iterator();
        while (it.hasNext()) {
            arrayList.add(toIrType$default(this, (FirTypeRef) it.next(), null, 1, null));
        }
        irTypeParameterCreateIrTypeParameterWithoutBounds.setSuperTypes(arrayList);
        return irTypeParameterCreateIrTypeParameterWithoutBounds;
    }

    public final void putEnumEntryClassInScope(FirEnumEntry enumEntry, IrClass correspondingClass) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(correspondingClass, "correspondingClass");
        Fir2IrLocalClassStorage fir2IrLocalClassStorage = this.localStorage;
        FirExpression initializer = enumEntry.getInitializer();
        Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression");
        fir2IrLocalClassStorage.set(((FirAnonymousObjectExpression) initializer).getAnonymousObject(), correspondingClass);
    }

    public final IrEnumEntry getCachedIrEnumEntry$fir2ir(FirEnumEntry enumEntry) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return this.enumEntryCache.get(enumEntry);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrEnumEntry declareIrEnumEntry(final IdSignature signature, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> factory) {
        if (signature == null) {
            return factory.invoke(new IrEnumEntrySymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareEnumEntry(signature, new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.declareIrEnumEntry.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrEnumEntrySymbol invoke() {
                return new Fir2IrEnumEntrySymbol(signature);
            }
        }, factory);
    }

    public static /* synthetic */ IrEnumEntry getIrEnumEntry$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirEnumEntry firEnumEntry, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        return fir2IrClassifierStorage.getIrEnumEntry(firEnumEntry, irClass, irDeclarationOrigin);
    }

    public final IrEnumEntry getIrEnumEntry(FirEnumEntry enumEntry, IrClass irParent, IrDeclarationOrigin predefinedOrigin) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        IrEnumEntry cachedIrEnumEntry$fir2ir = getCachedIrEnumEntry$fir2ir(enumEntry);
        if (cachedIrEnumEntry$fir2ir != null) {
            return cachedIrEnumEntry$fir2ir;
        }
        FirFile firCallableContainerFile = FirProviderKt.getFirProvider(enumEntry.getModuleData().getSession()).getFirCallableContainerFile(enumEntry.getSymbol());
        if (predefinedOrigin == null) {
            if (firCallableContainerFile != null) {
                predefinedOrigin = IrDeclarationOrigin.DEFINED.INSTANCE;
            } else {
                predefinedOrigin = irParent.getOrigin();
            }
        }
        return createIrEnumEntry(enumEntry, irParent, predefinedOrigin);
    }

    public final IrClass findIrClass(ConeClassLikeLookupTag lookupTag) {
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        if (lookupTag.getClassId().isLocal()) {
            return getCachedLocalClass(lookupTag);
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(lookupTag, getSession());
        if (symbol instanceof FirClassSymbol) {
            return getIrClassSymbol((FirClassSymbol) symbol).getOwner();
        }
        return null;
    }

    public static /* synthetic */ IrEnumEntry createIrEnumEntry$default(Fir2IrClassifierStorage fir2IrClassifierStorage, FirEnumEntry firEnumEntry, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        return fir2IrClassifierStorage.createIrEnumEntry(firEnumEntry, irClass, irDeclarationOrigin);
    }

    public final IrEnumEntry createIrEnumEntry(final FirEnumEntry enumEntry, final IrClass irParent, final IrDeclarationOrigin predefinedOrigin) {
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        KtSourceElement source = enumEntry.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        IrEnumEntry irEnumEntryDeclareIrEnumEntry = declareIrEnumEntry(FirBasedSignatureComposer.composeSignature$default(getSignatureComposer(), enumEntry, null, false, 6, null), new Function1<IrEnumEntrySymbol, IrEnumEntry>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$createIrEnumEntry$1$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrEnumEntry invoke(IrEnumEntrySymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrEnumEntry irEnumEntryCreateEnumEntry = this.getIrFactory().createEnumEntry(i, endOffset, ConversionUtilsKt.computeIrOrigin(enumEntry, predefinedOrigin), enumEntry.getName(), symbol);
                Fir2IrClassifierStorage fir2IrClassifierStorage = this;
                IrClass irClass = irParent;
                FirEnumEntry firEnumEntry = enumEntry;
                IrEnumEntry irEnumEntry = irEnumEntryCreateEnumEntry;
                fir2IrClassifierStorage.getDeclarationStorage().enterScope(irEnumEntry);
                if (irClass != null) {
                    irEnumEntryCreateEnumEntry.setParent(irClass);
                }
                if (fir2IrClassifierStorage.isEnumEntryWhichRequiresSubclass(firEnumEntry)) {
                    FirExpression initializer = firEnumEntry.getInitializer();
                    Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression");
                    irEnumEntryCreateEnumEntry.setCorrespondingClass(fir2IrClassifierStorage.getIrAnonymousObjectForEnumEntry(((FirAnonymousObjectExpression) initializer).getAnonymousObject(), firEnumEntry.getName(), irClass));
                }
                fir2IrClassifierStorage.getDeclarationStorage().leaveScope(irEnumEntry);
                return irEnumEntryCreateEnumEntry;
            }
        });
        this.enumEntryCache.put(enumEntry, irEnumEntryDeclareIrEnumEntry);
        return irEnumEntryDeclareIrEnumEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEnumEntryWhichRequiresSubclass(FirEnumEntry enumEntry) {
        FirExpression initializer = enumEntry.getInitializer();
        if (initializer instanceof FirAnonymousObjectExpression) {
            List<FirDeclaration> declarations = ((FirAnonymousObjectExpression) initializer).getAnonymousObject().getDeclarations();
            if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                Iterator<T> it = declarations.iterator();
                while (it.hasNext()) {
                    if (!(((FirDeclaration) it.next()) instanceof FirConstructor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClassSymbol getIrClassSymbol(FirClassSymbol<?> firClassSymbol) {
        int startOffset;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firClassSymbol, "firClassSymbol");
        final FirClass firClass = (FirClass) firClassSymbol.getFir();
        IrClass cachedIrClass = getCachedIrClass(firClass);
        if (cachedIrClass != null) {
            return cachedIrClass.getSymbol();
        }
        if ((firClass instanceof FirAnonymousObject) || ((firClass instanceof FirRegularClass) && Intrinsics.areEqual(firClass.getStatus().getVisibility(), Visibilities.Local.INSTANCE))) {
            return createLocalIrClassOnTheFly(firClass).getSymbol();
        }
        Intrinsics.checkNotNull(firClass, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirRegularClass");
        ClassId classId = firClassSymbol.getClassId();
        ClassId outerClassId = classId.getOuterClassId();
        FirClassLikeSymbol<?> classLikeSymbolByClassId = outerClassId != null ? FirSymbolProviderKt.getSymbolProvider(getSession()).getClassLikeSymbolByClassId(outerClassId) : null;
        Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        final IrDeclarationParent irDeclarationParentFindIrParent$fir2ir = declarationStorage.findIrParent$fir2ir(packageFqName, classLikeSymbolByClassId != null ? classLikeSymbolByClassId.getLookupTag() : null, firClassSymbol, firClass.getOrigin());
        Intrinsics.checkNotNull(irDeclarationParentFindIrParent$fir2ir);
        IrClass cachedIrClass2 = getCachedIrClass(firClass);
        if (cachedIrClass2 != null) {
            return cachedIrClass2.getSymbol();
        }
        IdSignature idSignatureComposeSignature = getConfiguration().getLinkViaSignatures() ? getSignatureComposer().composeSignature(firClass) : null;
        KtSourceElement source = firClass.getSource();
        KtSourceElement ktSourceElement = source;
        final int i = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        IrClass irClassDeclareIrClass = declareIrClass(idSignatureComposeSignature, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage$getIrClassSymbol$irClass$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol irClassSymbol) {
                Intrinsics.checkNotNullParameter(irClassSymbol, "irClassSymbol");
                Fir2IrLazyClass fir2IrLazyClass = new Fir2IrLazyClass(this.components, i, endOffset, ConversionUtilsKt.irOrigin(firClass, FirProviderKt.getFirProvider(this.getSession())), (FirRegularClass) firClass, irClassSymbol);
                fir2IrLazyClass.setParent(irDeclarationParentFindIrParent$fir2ir);
                return fir2IrLazyClass;
            }
        });
        this.classCache.put((FirRegularClass) firClass, irClassDeclareIrClass);
        Intrinsics.checkNotNull(irClassDeclareIrClass, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass");
        ((Fir2IrLazyClass) irClassDeclareIrClass).prepareTypeParameters();
        return irClassDeclareIrClass.getSymbol();
    }

    public final IrClassSymbol getIrClassSymbolForNotFoundClass(ConeClassLikeLookupTag classLikeLookupTag) {
        IrClass irExternalPackageFragment$default;
        Intrinsics.checkNotNullParameter(classLikeLookupTag, "classLikeLookupTag");
        final ClassId classId = classLikeLookupTag.getClassId();
        String strAsString = classId.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        final IdSignature.CommonSignature commonSignature = new IdSignature.CommonSignature(strAsString, strAsString2, 0L, 0L, null);
        ClassId outerClassId = classId.getOuterClassId();
        IrClassSymbol irClassSymbolForNotFoundClass = outerClassId != null ? getIrClassSymbolForNotFoundClass(TypeConstructionUtilsKt.toLookupTag(outerClassId)) : null;
        if (irClassSymbolForNotFoundClass == null || (irExternalPackageFragment$default = irClassSymbolForNotFoundClass.getOwner()) == null) {
            Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            irExternalPackageFragment$default = Fir2IrDeclarationStorage.getIrExternalPackageFragment$default(declarationStorage, packageFqName, null, 2, null);
        }
        final IrElement irElement = irExternalPackageFragment$default;
        return getSymbolTable().referenceClass(commonSignature, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.getIrClassSymbolForNotFoundClass.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return new Fir2IrClassSymbol(commonSignature);
            }
        }, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage.getIrClassSymbolForNotFoundClass.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFactory irFactory = Fir2IrClassifierStorage.this.getIrFactory();
                IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB ir_external_declaration_stub = IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE;
                Name shortClassName = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
                IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(irFactory, -1, -1, (IrDeclarationOrigin) ir_external_declaration_stub, shortClassName, DEFAULT_VISIBILITY, it, ClassKind.CLASS, Modality.FINAL, false, false, false, false, false, false, false, (SourceElement) null, 65280, (Object) null);
                irClassCreateClass$default.setParent((IrDeclarationParent) irElement);
                return irClassCreateClass$default;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrTypeParameterSymbol getIrTypeParameterSymbol(FirTypeParameterSymbol firTypeParameterSymbol, ConversionTypeOrigin typeOrigin) {
        IrTypeParameterSymbol symbol;
        Intrinsics.checkNotNullParameter(firTypeParameterSymbol, "firTypeParameterSymbol");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterSymbol.getFir();
        IrTypeParameter cachedIrTypeParameter$fir2ir = getCachedIrTypeParameter$fir2ir(firTypeParameter, typeOrigin);
        TypeParameterDescriptor typeParameterDescriptor = null;
        Object[] objArr = 0;
        if (cachedIrTypeParameter$fir2ir == null || (symbol = cachedIrTypeParameter$fir2ir.getSymbol()) == null) {
            IrTypeParameter irTypeParameter = this.typeParameterCache.get(firTypeParameter);
            symbol = irTypeParameter != null ? irTypeParameter.getSymbol() : null;
        }
        if (symbol != null) {
            return symbol;
        }
        if (this.components.getConfiguration().getAllowNonCachedDeclarations()) {
            FirDeclaration fir = firTypeParameter.getContainingDeclarationSymbol().getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner");
            FirTypeParameterRefsOwner firTypeParameterRefsOwner = (FirTypeParameterRefsOwner) fir;
            int iIndexOf = firTypeParameterRefsOwner.getTypeParameters().indexOf(firTypeParameter);
            if (iIndexOf < 0) {
                throw new IllegalStateException("Check failed.".toString());
            }
            IrTypeParameter irTypeParameterCreateIrTypeParameterWithoutBounds = createIrTypeParameterWithoutBounds(firTypeParameter, iIndexOf, new IrTypeParameterSymbolImpl(typeParameterDescriptor, 1, objArr == true ? 1 : 0), ((firTypeParameterRefsOwner instanceof FirPropertyAccessor) && ((FirPropertyAccessor) firTypeParameterRefsOwner).isSetter()) ? ConversionTypeOrigin.SETTER : ConversionTypeOrigin.DEFAULT);
            List<FirTypeRef> bounds = firTypeParameter.getBounds();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
            Iterator<T> it = bounds.iterator();
            while (it.hasNext()) {
                arrayList.add(Fir2IrTypeConverterKt.toIrType$default((FirTypeRef) it.next(), getTypeConverter(), null, 2, null));
            }
            irTypeParameterCreateIrTypeParameterWithoutBounds.setSuperTypes(arrayList);
            return irTypeParameterCreateIrTypeParameterWithoutBounds.getSymbol();
        }
        throw new IllegalStateException(("Cannot find cached type parameter by FIR symbol: " + firTypeParameterSymbol.getName() + " of the owner: " + firTypeParameter.getContainingDeclarationSymbol()).toString());
    }

    private final IrSimpleFunction getTemporaryParent() {
        return (IrSimpleFunction) this.temporaryParent.getValue();
    }
}
