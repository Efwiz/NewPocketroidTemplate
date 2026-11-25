package org.jetbrains.kotlin.fir.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverterKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.lazy.IrMaybeDeserializedClass;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.DeserializableClass;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrLazyClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008c\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u0007B5\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\u0016\u0010í\u0001\u001a\b\u0012\u0004\u0012\u00020E0_2\u0007\u0010\u009b\u0001\u001a\u00020^J\t\u0010î\u0001\u001a\u00020kH\u0016J\u0012\u0010ï\u0001\u001a\u00020k2\u0007\u0010\u000e\u001a\u00030ð\u0001H\u0002J\u000e\u0010ñ\u0001\u001a\u00020k*\u00030ò\u0001H\u0002R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R7\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0012\u0010 \u001a\u00020!X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010#R$\u0010&\u001a\u00020%2\u0006\u0010$\u001a\u00020%8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0012\u0010/\u001a\u000200X\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u00102R\u0012\u00103\u001a\u000204X\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u00106R\u0012\u00107\u001a\u000208X\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0012\u0010;\u001a\u00020<X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010BR!\u0010C\u001a\b\u0012\u0004\u0012\u00020E0D8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bG\u0010\u001f\u001a\u0004\bF\u0010\u001bR\u0012\u0010H\u001a\u00020IX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020M8VX\u0097\u0004¢\u0006\f\u0012\u0004\bN\u0010O\u001a\u0004\bP\u0010QR\u0014\u0010\u000b\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u0012\u0010T\u001a\u00020UX\u0096\u0005¢\u0006\u0006\u001a\u0004\bV\u0010WR\u0012\u0010X\u001a\u00020YX\u0096\u0005¢\u0006\u0006\u001a\u0004\bZ\u0010[R \u0010\\\u001a\u0014\u0012\u0004\u0012\u00020^\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0_0]X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u0012\u0010b\u001a\u00020cX\u0096\u0005¢\u0006\u0006\u001a\u0004\bd\u0010eR\u0012\u0010f\u001a\u00020gX\u0096\u0005¢\u0006\u0006\u001a\u0004\bh\u0010iR\u0012\u0010j\u001a\u0004\u0018\u00010kX\u0082\u000e¢\u0006\u0004\n\u0002\u0010lR\u0018\u0010m\u001a\b\u0012\u0004\u0012\u00020n0\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\bo\u0010\u001bR$\u0010p\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR$\u0010t\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bt\u0010q\"\u0004\bu\u0010sR$\u0010v\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bv\u0010q\"\u0004\bw\u0010sR$\u0010x\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bx\u0010q\"\u0004\by\u0010sR$\u0010z\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bz\u0010q\"\u0004\b{\u0010sR$\u0010|\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b|\u0010q\"\u0004\b}\u0010sR\u0014\u0010~\u001a\u00020k8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b~\u0010qR%\u0010\u007f\u001a\u00020k2\u0006\u0010$\u001a\u00020k8V@VX\u0096\u000e¢\u0006\r\u001a\u0004\b\u007f\u0010q\"\u0005\b\u0080\u0001\u0010sR+\u0010\u0082\u0001\u001a\u00030\u0081\u00012\u0007\u0010$\u001a\u00030\u0081\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001\"\u0006\b\u0085\u0001\u0010\u0086\u0001R\u0016\u0010\u0087\u0001\u001a\u00030\u0088\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001R/\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008b\u00012\t\u0010$\u001a\u0005\u0018\u00010\u008b\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001\"\u0006\b\u008f\u0001\u0010\u0090\u0001R+\u0010\u0092\u0001\u001a\u00030\u0091\u00012\u0007\u0010$\u001a\u00030\u0091\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u0093\u0001\u0010\u0094\u0001\"\u0006\b\u0095\u0001\u0010\u0096\u0001R\u001a\u0010\u0097\u0001\u001a\u0005\u0018\u00010\u0098\u00018VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0099\u0001\u0010\u009a\u0001R)\u0010\u009b\u0001\u001a\u00020^2\u0006\u0010$\u001a\u00020^8V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\b\u009c\u0001\u0010\u009d\u0001\"\u0006\b\u009e\u0001\u0010\u009f\u0001R\u001e\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b \u0001\u0010¡\u0001\"\u0006\b¢\u0001\u0010£\u0001R+\u0010¤\u0001\u001a\u0004\u0018\u00010%2\b\u0010$\u001a\u0004\u0018\u00010%8V@VX\u0096\u000e¢\u0006\u000e\u001a\u0005\b¥\u0001\u0010(\"\u0005\b¦\u0001\u0010*R \u0010§\u0001\u001a\u00030¨\u0001X\u0096.¢\u0006\u0012\n\u0000\u001a\u0006\b©\u0001\u0010ª\u0001\"\u0006\b«\u0001\u0010¬\u0001R\u0016\u0010\u00ad\u0001\u001a\u00030®\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b¯\u0001\u0010°\u0001R;\u0010±\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u00178V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b´\u0001\u0010\u001f\u001a\u0005\b²\u0001\u0010\u001b\"\u0005\b³\u0001\u0010\u001dR\u0016\u0010µ\u0001\u001a\u00030¶\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b·\u0001\u0010¸\u0001R\u0016\u0010¹\u0001\u001a\u00030º\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b»\u0001\u0010¼\u0001R\u0018\u0010½\u0001\u001a\u00030¾\u00018VX\u0096\u0004¢\u0006\b\u001a\u0006\b¿\u0001\u0010À\u0001R\u0015\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÁ\u0001\u0010SR=\u0010Ã\u0001\u001a\t\u0012\u0005\u0012\u00030Â\u00010\u00172\r\u0010\u0016\u001a\t\u0012\u0005\u0012\u00030Â\u00010\u00178V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bÆ\u0001\u0010\u001f\u001a\u0005\bÄ\u0001\u0010\u001b\"\u0005\bÅ\u0001\u0010\u001dR\u0016\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÇ\u0001\u0010È\u0001R\u0016\u0010É\u0001\u001a\u00030Ê\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bË\u0001\u0010Ì\u0001R7\u0010Î\u0001\u001a\u0005\u0018\u00010Í\u00012\t\u0010\u0016\u001a\u0005\u0018\u00010Í\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\bÓ\u0001\u0010\u001f\u001a\u0006\bÏ\u0001\u0010Ð\u0001\"\u0006\bÑ\u0001\u0010Ò\u0001R\u0016\u0010Ô\u0001\u001a\u00030Õ\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bÖ\u0001\u0010×\u0001R$\u0010Ø\u0001\u001a\t\u0012\u0005\u0012\u00030Ù\u00010\u0017X\u0096.¢\u0006\u0010\n\u0000\u001a\u0005\bÚ\u0001\u0010\u001b\"\u0005\bÛ\u0001\u0010\u001dR=\u0010Þ\u0001\u001a\f\u0012\u0005\u0012\u00030Ý\u0001\u0018\u00010Ü\u00012\u0010\u0010$\u001a\f\u0012\u0005\u0012\u00030Ý\u0001\u0018\u00010Ü\u00018V@VX\u0096\u000e¢\u0006\u0010\u001a\u0006\bß\u0001\u0010à\u0001\"\u0006\bá\u0001\u0010â\u0001R+\u0010ä\u0001\u001a\u00030ã\u00012\u0007\u0010$\u001a\u00030ã\u0001@VX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bå\u0001\u0010æ\u0001\"\u0006\bç\u0001\u0010è\u0001R\u0016\u0010é\u0001\u001a\u00030ê\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\bë\u0001\u0010ì\u0001¨\u0006ó\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/Fir2IrLazyClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/lazy/Fir2IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrMaybeDeserializedClass;", "Lorg/jetbrains/kotlin/ir/util/DeserializableClass;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "fir", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "attributeOwnerId", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "declarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDeclarations", "declarations$delegate", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getEndOffset", "()I", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "fakeOverridesByName", "", "Lorg/jetbrains/kotlin/name/Name;", "", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irLoaded", "", "Ljava/lang/Boolean;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "isCompanion", "()Z", "setCompanion", "(Z)V", "isData", "setData", "isExpect", "setExpect", "isExternal", "setExternal", "isFun", "setFun", "isInner", "setInner", "isNewPlaceForBodyGeneration", "isValue", "setValue", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", Namer.METADATA_CLASS_KIND, "getKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "Lorg/jetbrains/kotlin/descriptors/Modality;", "modality", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "moduleName", "", "getModuleName", "()Ljava/lang/String;", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "sealedSubclasses", "getSealedSubclasses", "setSealedSubclasses", "sealedSubclasses$delegate", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStartOffset", "Lorg/jetbrains/kotlin/ir/types/IrType;", "superTypes", "getSuperTypes", "setSuperTypes", "superTypes$delegate", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "thisReceiver", "getThisReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setThisReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "thisReceiver$delegate", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "valueClassRepresentation", "getValueClassRepresentation", "()Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "setValueClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;)V", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "visibility", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getFakeOverridesByName", "loadIr", "shouldBuildStub", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isAbstractMethodOfAny", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLazyClass extends IrClass implements Fir2IrComponents, AbstractFir2IrLazyDeclaration<FirRegularClass>, Fir2IrTypeParametersContainer, IrMaybeDeserializedClass, DeserializableClass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyClass.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyClass.class), "superTypes", "getSuperTypes()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyClass.class), "sealedSubclasses", "getSealedSubclasses()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyClass.class), "thisReceiver", "getThisReceiver()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyClass.class), "declarations", "getDeclarations()Ljava/util/List;"))};
    private final /* synthetic */ Fir2IrComponents $$delegate_0;

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;

    /* JADX INFO: renamed from: declarations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty declarations;
    private final int endOffset;
    private final Map<Name, Collection<IrDeclaration>> fakeOverridesByName;
    private final FirRegularClass fir;
    private Boolean irLoaded;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;

    /* JADX INFO: renamed from: sealedSubclasses$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty sealedSubclasses;
    private final int startOffset;

    /* JADX INFO: renamed from: superTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty superTypes;
    private final IrClassSymbol symbol;

    /* JADX INFO: renamed from: thisReceiver$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty thisReceiver;
    public List<? extends IrTypeParameter> typeParameters;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.$$delegate_0.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.$$delegate_0.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.$$delegate_0.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.$$delegate_0.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.$$delegate_0.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.$$delegate_0.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.$$delegate_0.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.$$delegate_0.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.$$delegate_0.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.$$delegate_0.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.$$delegate_0.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.$$delegate_0.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.$$delegate_0.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.$$delegate_0.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.$$delegate_0.getLock();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.$$delegate_0.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.$$delegate_0.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.$$delegate_0.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.$$delegate_0.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.$$delegate_0.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.$$delegate_0.getVisibilityConverter();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return AbstractFir2IrLazyDeclaration.DefaultImpls.toIrType(this, kotlinType);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyDeclaration
    public FirRegularClass getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrClassSymbol getSymbol() {
        return this.symbol;
    }

    public Fir2IrLazyClass(Fir2IrComponents components, int i, int i2, IrDeclarationOrigin origin, FirRegularClass fir, IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.fir = fir;
        this.symbol = symbol;
        this.$$delegate_0 = components;
        getSymbol().bind(this);
        getClassifierStorage().preCacheTypeParameters$fir2ir(getFir(), getSymbol());
        this.annotations = createLazyAnnotations();
        this.visibility = components.getVisibilityConverter().convertToDescriptorVisibility(getFir().getStatus().getVisibility());
        this.superTypes = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrType>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$superTypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrType> invoke() {
                List<FirTypeRef> superTypeRefs = this.this$0.getFir().getSuperTypeRefs();
                Fir2IrLazyClass fir2IrLazyClass = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypeRefs, 10));
                Iterator<T> it = superTypeRefs.iterator();
                while (it.hasNext()) {
                    arrayList.add(Fir2IrTypeConverterKt.toIrType$default((FirTypeRef) it.next(), fir2IrLazyClass.getTypeConverter(), null, 2, null));
                }
                return arrayList;
            }
        });
        this.sealedSubclasses = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$sealedSubclasses$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrClassSymbol> invoke() {
                if (this.this$0.getFir().getStatus().getModality() == Modality.SEALED) {
                    Fir2IrLazyClass fir2IrLazyClass = this.this$0;
                    return ConversionUtilsKt.getIrSymbolsForSealedSubclasses(fir2IrLazyClass, fir2IrLazyClass.getFir());
                }
                return CollectionsKt.emptyList();
            }
        });
        this.thisReceiver = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$thisReceiver$2
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                this.this$0.getSymbolTable().enterScope(this.this$0);
                List<FirTypeParameterRef> typeParameters = this.this$0.getFir().getTypeParameters();
                Fir2IrLazyClass fir2IrLazyClass = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    IrTypeParameter cachedIrTypeParameter$fir2ir$default = Fir2IrClassifierStorage.getCachedIrTypeParameter$fir2ir$default(fir2IrLazyClass.getClassifierStorage(), (FirTypeParameter) ((FirTypeParameterRef) it.next()).getSymbol().getFir(), null, 2, null);
                    Intrinsics.checkNotNull(cachedIrTypeParameter$fir2ir$default);
                    arrayList.add(new IrSimpleTypeImpl((IrClassifierSymbol) cachedIrTypeParameter$fir2ir$default.getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
                }
                Fir2IrLazyClass fir2IrLazyClass2 = this.this$0;
                IrValueParameter irValueParameterDeclareThisReceiverParameter$default = ConversionUtilsKt.declareThisReceiverParameter$default(fir2IrLazyClass2, fir2IrLazyClass2, new IrSimpleTypeImpl((IrClassifierSymbol) this.this$0.getSymbol(), false, (List) arrayList, CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, 0, 0, null, null, 120, null);
                this.this$0.getSymbolTable().leaveScope(this.this$0);
                return irValueParameterDeclareThisReceiverParameter$default;
            }
        });
        this.fakeOverridesByName = new LinkedHashMap();
        this.declarations = LazyUtilKt.lazyVar(getLock(), new Function0<List<IrDeclaration>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<IrDeclaration> invoke() {
                final ArrayList arrayList = new ArrayList();
                FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) this.this$0.getFir(), this.this$0.getSession(), this.this$0.getScopeSession(), true, (FirResolvePhase) null);
                final Fir2IrLazyClass fir2IrLazyClass = this.this$0;
                firTypeScopeUnsubstitutedScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                        invoke2(firConstructorSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Type inference failed for: r1v0, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirConstructorSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (fir2IrLazyClass.shouldBuildStub(it.getFir())) {
                            arrayList.add(fir2IrLazyClass.getDeclarationStorage().getIrConstructorSymbol(it).getOwner());
                        }
                    }
                });
                for (Name name2 : firTypeScopeUnsubstitutedScope.getClassifierNames()) {
                    final Fir2IrLazyClass fir2IrLazyClass2 = this.this$0;
                    final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                            invoke2(firClassifierSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirClassifierSymbol<?> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            Object fir2 = it.getFir();
                            FirRegularClass firRegularClass = fir2 instanceof FirRegularClass ? (FirRegularClass) fir2 : null;
                            if (firRegularClass != null && Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firRegularClass).getOuterClassId(), FirDeclarationUtilKt.getClassId(fir2IrLazyClass2.getFir())) && fir2IrLazyClass2.shouldBuildStub(firRegularClass)) {
                                arrayList.add(fir2IrLazyClass2.getClassifierStorage().getIrClassSymbol(firRegularClass.getSymbol()).getOwner());
                            }
                        }
                    };
                    firTypeScopeUnsubstitutedScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2$invoke$$inlined$processClassifiersByName$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                            invoke2(firClassifierSymbol, coneSubstitutor);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirClassifierSymbol<?> symbol2, ConeSubstitutor coneSubstitutor) {
                            Intrinsics.checkNotNullParameter(symbol2, "symbol");
                            Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                            function1.invoke(symbol2);
                        }
                    });
                }
                if (this.this$0.getFir().getClassKind() == ClassKind.ENUM_CLASS) {
                    for (FirDeclaration firDeclaration : this.this$0.getFir().getDeclarations()) {
                        if ((firDeclaration instanceof FirEnumEntry) && this.this$0.shouldBuildStub(firDeclaration)) {
                            IrSymbolOwner owner = this.this$0.getDeclarationStorage().getIrValueSymbol(((FirEnumEntry) firDeclaration).getSymbol()).getOwner();
                            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                            arrayList.add((IrDeclaration) owner);
                        }
                    }
                }
                ConeClassLikeLookupTag lookupTag = this.this$0.getFir().getSymbol().getLookupTag();
                invoke$addDeclarationsFromScope(this.this$0, lookupTag, arrayList, firTypeScopeUnsubstitutedScope);
                Fir2IrLazyClass fir2IrLazyClass3 = this.this$0;
                invoke$addDeclarationsFromScope(fir2IrLazyClass3, lookupTag, arrayList, ImplicitReceiverUtilsKt.staticScope(fir2IrLazyClass3.getFir(), this.this$0.getSession(), this.this$0.getScopeSession()));
                Fir2IrClassifierStorage classifierStorage = this.this$0.getClassifierStorage();
                Fir2IrLazyClass fir2IrLazyClass4 = this.this$0;
                arrayList.addAll(classifierStorage.createContextReceiverFields(fir2IrLazyClass4, fir2IrLazyClass4.getFir()));
                Iterator<Name> it = firTypeScopeUnsubstitutedScope.getCallableNames().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, this.this$0.getFakeOverridesByName(it.next()));
                }
                return arrayList;
            }

            private static final void invoke$addDeclarationsFromScope(final Fir2IrLazyClass fir2IrLazyClass, final ConeClassLikeLookupTag coneClassLikeLookupTag, final List<IrDeclaration> list, FirContainingNamesAwareScope firContainingNamesAwareScope) {
                if (firContainingNamesAwareScope == null) {
                    return;
                }
                for (Name name2 : firContainingNamesAwareScope.getCallableNames()) {
                    firContainingNamesAwareScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2$addDeclarationsFromScope$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                            invoke2(firNamedFunctionSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirNamedFunctionSymbol it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            FirNamedFunctionSymbol firNamedFunctionSymbol = it;
                            if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firNamedFunctionSymbol) && fir2IrLazyClass.shouldBuildStub(it.getFir())) {
                                if ((firNamedFunctionSymbol.getRawStatus().isStatic() || Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firNamedFunctionSymbol), coneClassLikeLookupTag)) && !fir2IrLazyClass.isAbstractMethodOfAny(it)) {
                                    list.add(Fir2IrDeclarationStorage.getIrFunctionSymbol$default(fir2IrLazyClass.getDeclarationStorage(), it, null, 2, null).getOwner());
                                }
                            }
                        }
                    });
                    firContainingNamesAwareScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass$declarations$2$addDeclarationsFromScope$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                            invoke2(firVariableSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            FirVariableSymbol<?> firVariableSymbol = it;
                            if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firVariableSymbol) && fir2IrLazyClass.shouldBuildStub(it.getFir()) && (it instanceof FirPropertySymbol)) {
                                if (firVariableSymbol.getRawStatus().isStatic() || Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firVariableSymbol), coneClassLikeLookupTag)) {
                                    List<IrDeclaration> list2 = list;
                                    IrSymbolOwner owner = Fir2IrDeclarationStorage.getIrPropertySymbol$default(fir2IrLazyClass.getDeclarationStorage(), (FirPropertySymbol) it, null, 2, null).getOwner();
                                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list2, owner instanceof IrDeclaration ? (IrDeclaration) owner : null);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[0], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        List list = this.typeParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        IrDeclarationParent irDeclarationParent = this.parent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent = irDeclarationParent;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public SourceElement getSource() {
        SourceElement sourceElement = DeclarationAttributesKt.getSourceElement(getFir());
        if (sourceElement != null) {
            return sourceElement;
        }
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return getFir().getName();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public Modality getModality() {
        Modality modality = getFir().getStatus().getModality();
        Intrinsics.checkNotNull(modality);
        return modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getAttributeOwnerId() {
        return this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setAttributeOwnerId(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        throw new IllegalStateException("Mutating Fir2Ir lazy elements is not possible".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ClassKind getKind() {
        return getFir().getClassKind();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setKind(ClassKind classKind) {
        Intrinsics.checkNotNullParameter(classKind, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isCompanion */
    public boolean getIsCompanion() {
        return getFir().getStatus().isCompanion();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setCompanion(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return getFir().getStatus().isInner();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setInner(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isData */
    public boolean getIsData() {
        return getFir().getStatus().isData();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setData(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    /* JADX INFO: renamed from: isExternal */
    public boolean getIsExternal() {
        return getFir().getStatus().isExternal();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    public void setExternal(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isValue */
    public boolean getIsValue() {
        return getFir().getStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValue(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isExpect */
    public boolean getIsExpect() {
        return getFir().getStatus().isExpect();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setExpect(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isFun */
    public boolean getIsFun() {
        return getFir().getStatus().isFun();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setFun(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrType> getSuperTypes() {
        return (List) this.superTypes.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSuperTypes(List<? extends IrType> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.superTypes.setValue(this, $$delegatedProperties[1], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrClassSymbol> getSealedSubclasses() {
        return (List) this.sealedSubclasses.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSealedSubclasses(List<? extends IrClassSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.sealedSubclasses.setValue(this, $$delegatedProperties[2], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public IrValueParameter getThisReceiver() {
        return (IrValueParameter) this.thisReceiver.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setThisReceiver(IrValueParameter irValueParameter) {
        this.thisReceiver.setValue(this, $$delegatedProperties[3], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ValueClassRepresentation<IrSimpleType> getValueClassRepresentation() {
        return ConversionUtilsKt.computeValueClassRepresentation(this, getFir());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValueClassRepresentation(ValueClassRepresentation<IrSimpleType> valueClassRepresentation) {
        mutationNotSupported();
        throw null;
    }

    public final Collection<IrDeclaration> getFakeOverridesByName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Map<Name, Collection<IrDeclaration>> map = this.fakeOverridesByName;
        List<IrDeclaration> list = map.get(name2);
        if (list == null) {
            List<IrDeclaration> listGenerateFakeOverridesForName = getFakeOverrideGenerator().generateFakeOverridesForName(this, name2, getFir());
            getConverter().bindFakeOverridesOrPostpone(listGenerateFakeOverridesForName);
            list = listGenerateFakeOverridesForName;
            map.put(name2, list);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
    public List<IrDeclaration> getDeclarations() {
        return (List) this.declarations.getValue(this, $$delegatedProperties[4]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldBuildStub(FirDeclaration fir) {
        if ((fir instanceof FirMemberDeclaration) && Visibilities.INSTANCE.isPrivate(((FirMemberDeclaration) fir).getStatus().getVisibility())) {
            Fir2IrLazyClass fir2IrLazyClass = this;
            if ((!IrUtilsKt.isObject(fir2IrLazyClass) || !(fir instanceof FirConstructor)) && (!IrUtilsKt.isEnumClass(fir2IrLazyClass) || !(fir instanceof FirConstructor))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrMaybeDeserializedClass
    public String getModuleName() {
        return DeclarationAttributesKt.getModuleName(getFir());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrMaybeDeserializedClass
    public boolean isNewPlaceForBodyGeneration() {
        return Intrinsics.areEqual((Object) ClassMembersKt.isNewPlaceForBodyGeneration(getFir()), (Object) true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAbstractMethodOfAny(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        if (firNamedFunctionSymbol.getResolvedStatus().getModality() != Modality.ABSTRACT) {
            return false;
        }
        return FirDeclarationUtilKt.isMethodOfAny(firNamedFunctionSymbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.DeserializableClass
    public boolean loadIr() {
        boolean z = getParent() instanceof IrPackageFragment;
        Boolean bool = this.irLoaded;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zDeserializeToplevelClass = getExtensions().deserializeToplevelClass(this, this);
        this.irLoaded = Boolean.valueOf(zDeserializeToplevelClass);
        return zDeserializeToplevelClass;
    }
}
