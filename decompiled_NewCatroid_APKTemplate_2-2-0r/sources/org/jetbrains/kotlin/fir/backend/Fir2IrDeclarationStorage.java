package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.graphics.GL30;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import com.reandroid.apk.ApkUtil;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataKey;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.descriptors.FirBuiltInsPackageFragment;
import org.jetbrains.kotlin.fir.descriptors.FirModuleDescriptor;
import org.jetbrains.kotlin.fir.descriptors.FirPackageFragmentDescriptor;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirExpressionStub;
import org.jetbrains.kotlin.fir.lazy.Fir2IrLazyClass;
import org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor;
import org.jetbrains.kotlin.fir.lazy.Fir2IrLazyProperty;
import org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.Fir2IrConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLookupTagWithFixedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrScriptImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrScriptImplKt;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrLocalDelegatedPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.utils.ThreadLocalKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000î\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u009d\u00032\u00020\u0001:\u0010\u009d\u0003\u009e\u0003\u009f\u0003 \u0003¡\u0003¢\u0003£\u0003¤\u0003B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J3\u0010\u009a\u0001\u001a\u00030\u009b\u00012\u000e\u0010\u009c\u0001\u001a\t\u0012\u0005\u0012\u00030\u009d\u00010a2\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u000f\u0010 \u0001\u001a\n\u0012\u0005\u0012\u00030¢\u00010¡\u0001J\u001d\u0010£\u0001\u001a\u00030\u0093\u00012\u0007\u0010¤\u0001\u001a\u00020Q2\b\u0010¥\u0001\u001a\u00030\u009f\u0001H\u0002J\"\u0010¦\u0001\u001a\u00030\u009b\u00012\u0007\u0010§\u0001\u001a\u00020r2\u0007\u0010¨\u0001\u001a\u00020JH\u0000¢\u0006\u0003\b©\u0001J#\u0010ª\u0001\u001a\u00030\u009b\u00012\b\u0010«\u0001\u001a\u00030¬\u00012\u0007\u0010¥\u0001\u001a\u00020RH\u0000¢\u0006\u0003\b\u00ad\u0001J\"\u0010®\u0001\u001a\u00030¯\u00012\f\u0010°\u0001\u001a\u0007\u0012\u0002\b\u00030±\u00012\b\u0010²\u0001\u001a\u00030¯\u0001H\u0002J\u0016\u0010³\u0001\u001a\u0004\u0018\u00010?2\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010IH\u0002J3\u0010´\u0001\u001a\u0003Hµ\u0001\"\u0005\b\u0000\u0010µ\u00012\b\u0010¶\u0001\u001a\u00030·\u00012\u000f\u0010¸\u0001\u001a\n\u0012\u0005\u0012\u0003Hµ\u00010¹\u0001H\u0082\b¢\u0006\u0003\u0010º\u0001Jh\u0010»\u0001\u001a\u00030¢\u00012\u0007\u0010¼\u0001\u001a\u00020f2\u0007\u0010½\u0001\u001a\u00020f2\b\u0010¾\u0001\u001a\u00030¿\u00012\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010Á\u00012\f\b\u0002\u0010Â\u0001\u001a\u0005\u0018\u00010Ã\u00012\n\b\u0002\u0010Ä\u0001\u001a\u00030\u0093\u00012\n\b\u0002\u0010Å\u0001\u001a\u00030\u0093\u0001H\u0000¢\u0006\u0003\bÆ\u0001J\u0013\u0010Ç\u0001\u001a\u00020\u00172\b\u0010È\u0001\u001a\u00030É\u0001H\u0002J\u001a\u0010Ç\u0001\u001a\u00020\u00172\u0007\u0010Ê\u0001\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0019\u0010Ë\u0001\u001a\u00020U2\u0007\u0010Ì\u0001\u001a\u00020T2\u0007\u0010Í\u0001\u001a\u00020?J3\u0010Î\u0001\u001a\u00020&2\u0007\u0010Ï\u0001\u001a\u00020%2\u0007\u0010Í\u0001\u001a\u00020?2\f\b\u0002\u0010Ð\u0001\u001a\u0005\u0018\u00010¯\u00012\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u0001J;\u0010Ò\u0001\u001a\u00020D2\u0007\u0010Ó\u0001\u001a\u00020C2\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\n\b\u0002\u0010Ô\u0001\u001a\u00030Õ\u00012\n\b\u0002\u0010Ö\u0001\u001a\u00030¯\u0001H\u0000¢\u0006\u0003\b×\u0001J%\u0010Ø\u0001\u001a\u0004\u0018\u00010D2\u0007\u0010Ó\u0001\u001a\u00020C2\b\u0010Ù\u0001\u001a\u00030Ú\u00012\u0007\u0010Û\u0001\u001a\u00020?JP\u0010Ü\u0001\u001a\u00020R2\u0007\u0010«\u0001\u001a\u00020Q2\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\u000b\b\u0002\u0010Ý\u0001\u001a\u0004\u0018\u00010?2\f\b\u0002\u0010Ð\u0001\u001a\u0005\u0018\u00010¯\u00012\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u00012\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001J0\u0010à\u0001\u001a\u00020R2\b\u0010á\u0001\u001a\u00030¬\u00012\b\u0010â\u0001\u001a\u00030ã\u00012\u0007\u0010ä\u0001\u001a\u00020I2\b\u0010å\u0001\u001a\u00030¯\u0001H\u0002J/\u0010æ\u0001\u001a\u00020J2\u0007\u0010á\u0001\u001a\u00020r2\b\u0010â\u0001\u001a\u00030ã\u00012\u0007\u0010ä\u0001\u001a\u00020I2\b\u0010å\u0001\u001a\u00030¯\u0001H\u0002J\u001a\u0010ç\u0001\u001a\u00030è\u00012\u0007\u0010§\u0001\u001a\u00020r2\u0007\u0010Í\u0001\u001a\u00020IJU\u0010é\u0001\u001a\u00030¢\u00012\b\u0010ê\u0001\u001a\u00030Á\u00012\t\b\u0002\u0010ë\u0001\u001a\u00020f2\n\b\u0002\u0010ì\u0001\u001a\u00030\u0093\u00012\n\b\u0002\u0010í\u0001\u001a\u00030î\u00012\n\b\u0002\u0010ï\u0001\u001a\u00030\u0093\u00012\n\b\u0002\u0010ð\u0001\u001a\u00030\u0093\u0001H\u0000¢\u0006\u0003\bñ\u0001J\u001d\u0010ò\u0001\u001a\u00030¢\u00012\b\u0010ó\u0001\u001a\u00030\u009d\u00012\u0007\u0010ë\u0001\u001a\u00020fH\u0002JP\u0010ô\u0001\u001a\u00020J2\u0007\u0010§\u0001\u001a\u00020r2\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\u000b\b\u0002\u0010Ý\u0001\u001a\u0004\u0018\u00010?2\f\b\u0002\u0010Ð\u0001\u001a\u0005\u0018\u00010¯\u00012\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u00012\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001J\u0098\u0001\u0010õ\u0001\u001a\u00020R2\n\u0010ö\u0001\u001a\u0005\u0018\u00010÷\u00012\u0007\u0010§\u0001\u001a\u00020r2\b\u0010ø\u0001\u001a\u00030ù\u00012\b\u0010ú\u0001\u001a\u00030¿\u00012\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\u000b\b\u0002\u0010Ý\u0001\u001a\u0004\u0018\u00010?2\b\u0010û\u0001\u001a\u00030\u0093\u00012\b\u0010Ö\u0001\u001a\u00030¯\u00012\u0007\u0010¼\u0001\u001a\u00020f2\u0007\u0010½\u0001\u001a\u00020f2\n\b\u0002\u0010ü\u0001\u001a\u00030\u0093\u00012\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u00012\f\b\u0002\u0010ý\u0001\u001a\u0005\u0018\u00010÷\u0001H\u0002J)\u0010þ\u0001\u001a\u00030ÿ\u00012\b\u0010\u0080\u0002\u001a\u00030\u0081\u00022\u0007\u0010Í\u0001\u001a\u00020I2\f\b\u0002\u0010\u0082\u0002\u001a\u0005\u0018\u00010¯\u0001J,\u0010\u0083\u0002\u001a\u00020R2\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u00012\u0015\u0010\u0084\u0002\u001a\u0010\u0012\u0005\u0012\u00030\u0086\u0002\u0012\u0004\u0012\u00020R0\u0085\u0002H\u0002J,\u0010\u0087\u0002\u001a\u00020&2\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u00012\u0015\u0010\u0084\u0002\u001a\u0010\u0012\u0005\u0012\u00030\u0088\u0002\u0012\u0004\u0012\u00020&0\u0085\u0002H\u0002J \u0010\u0089\u0002\u001a\u00020D2\u0015\u0010\u0084\u0002\u001a\u0010\u0012\u0005\u0012\u00030\u008a\u0002\u0012\u0004\u0012\u00020D0\u0085\u0002H\u0002J,\u0010\u008b\u0002\u001a\u00020J2\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u00012\u0015\u0010\u0084\u0002\u001a\u0010\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0004\u0012\u00020J0\u0085\u0002H\u0002J2\u0010\u008d\u0002\u001a\u00020R2\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u00012\u0015\u0010\u0084\u0002\u001a\u0010\u0012\u0005\u0012\u00030\u0086\u0002\u0012\u0004\u0012\u00020R0\u0085\u0002H\u0000¢\u0006\u0003\b\u008e\u0002JX\u0010\u008f\u0002\u001a\u00030ÿ\u00012\u0007\u0010¼\u0001\u001a\u00020f2\u0007\u0010½\u0001\u001a\u00020f2\b\u0010Ö\u0001\u001a\u00030¯\u00012\b\u0010Â\u0001\u001a\u00030Ã\u00012\b\u0010¾\u0001\u001a\u00030¿\u00012\b\u0010\u0090\u0002\u001a\u00030\u0093\u00012\b\u0010\u0091\u0002\u001a\u00030\u0093\u00012\b\u0010\u0092\u0002\u001a\u00030\u0093\u0001H\u0002J \u0010\u0093\u0002\u001a\u00030ÿ\u00012\b\u0010\u0094\u0002\u001a\u00030\u0095\u00022\f\b\u0002\u0010\u0096\u0002\u001a\u0005\u0018\u00010\u0097\u0002J\u0011\u0010\u0098\u0002\u001a\u00030\u009b\u00012\u0007\u0010\u0099\u0002\u001a\u000203J\u001a\u0010\u009a\u0002\u001a\u0004\u0018\u00010I2\u0007\u0010\u009b\u0002\u001a\u00020AH\u0000¢\u0006\u0003\b\u009c\u0002J>\u0010\u009a\u0002\u001a\u0004\u0018\u00010I2\u0007\u0010\u009d\u0002\u001a\u00020\u00162\n\u0010\u009e\u0002\u001a\u0005\u0018\u00010ß\u00012\f\u0010\u009f\u0002\u001a\u0007\u0012\u0002\b\u00030 \u00022\b\u0010¡\u0002\u001a\u00030¢\u0002H\u0000¢\u0006\u0003\b\u009c\u0002J \u0010£\u0002\u001a\u00030\u009b\u00012\b\u0010Â\u0001\u001a\u00030Ã\u00012\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001H\u0002J\u0012\u0010¤\u0002\u001a\u0004\u0018\u00010U2\u0007\u0010Ì\u0001\u001a\u00020TJ\u0087\u0001\u0010¥\u0002\u001a\u0005\u0018\u0001H¦\u0002\"\u000b\b\u0000\u0010§\u0002\u0018\u0001*\u00020A\"\u000b\b\u0001\u0010¦\u0002\u0018\u0001*\u0002032\b\u0010\u0099\u0002\u001a\u0003H§\u00022\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u00012\u0015\u0010¨\u0002\u001a\u0010\u0012\u0005\u0012\u0003H§\u0002\u0012\u0005\u0012\u0003H¦\u00020>2\u0011\u0010©\u0002\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010¹\u00012\u0018\u0010ª\u0002\u001a\u0013\u0012\u0005\u0012\u00030ã\u0001\u0012\u0007\u0012\u0005\u0018\u0001H¦\u00020\u0085\u0002H\u0082\b¢\u0006\u0003\u0010«\u0002J'\u0010¬\u0002\u001a\u0004\u0018\u00010&2\u0007\u0010Ï\u0001\u001a\u00020%2\u0013\b\u0002\u0010©\u0002\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010¹\u0001J\u0012\u0010\u00ad\u0002\u001a\u0004\u0018\u00010D2\u0007\u0010Ó\u0001\u001a\u00020CJ\u0012\u0010®\u0002\u001a\u0004\u0018\u00010D2\u0007\u0010Ó\u0001\u001a\u00020CJ\u0012\u0010¯\u0002\u001a\u0004\u0018\u00010R2\u0007\u0010«\u0001\u001a\u00020QJ\u0013\u0010¯\u0002\u001a\u0004\u0018\u00010R2\b\u0010«\u0001\u001a\u00030¬\u0001J2\u0010¯\u0002\u001a\u0004\u0018\u00010R2\b\u0010«\u0001\u001a\u00030¬\u00012\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u00012\u0011\u0010©\u0002\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010¹\u0001J\u0012\u0010°\u0002\u001a\u0004\u0018\u00010J2\u0007\u0010§\u0001\u001a\u00020rJ1\u0010°\u0002\u001a\u0004\u0018\u00010J2\u0007\u0010§\u0001\u001a\u00020r2\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u00012\u0011\u0010©\u0002\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010¹\u0001J\u0012\u0010±\u0002\u001a\u0004\u0018\u00010y2\u0007\u0010²\u0002\u001a\u00020xJ\u001b\u0010³\u0002\u001a\u0004\u0018\u00010A2\u0007\u0010Û\u0001\u001a\u00020?2\u0007\u0010\u009b\u0002\u001a\u00020AJ \u0010´\u0002\u001a\u0004\u0018\u00010F2\u0007\u0010Ó\u0001\u001a\u00020C2\n\u0010µ\u0002\u001a\u0005\u0018\u00010ß\u0001H\u0002J\u0012\u0010¶\u0002\u001a\u00030·\u00022\b\u0010¸\u0002\u001a\u00030¹\u0002J\u0012\u0010º\u0002\u001a\u00020\u00172\u0007\u0010Ê\u0001\u001a\u00020\u0016H\u0002JÈ\u0002\u0010»\u0002\u001a\u00030·\u0002\"\u0010\b\u0000\u0010¼\u0002\u0018\u0001*\u0007\u0012\u0002\b\u00030±\u0001\"\u000b\b\u0001\u0010½\u0002\u0018\u0001*\u00020A\"\n\b\u0002\u0010¾\u0002*\u00030¿\u00022\b\u0010À\u0002\u001a\u0003H¼\u00022\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u00012U\u0010Á\u0002\u001aP\u0012\u0017\u0012\u0015H½\u0002¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(Ä\u0002\u0012\u0019\u0012\u0017\u0018\u00010ß\u0001¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(Å\u0002\u0012\u000e\u0012\f\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010¹\u0001\u0012\u0007\u0012\u0005\u0018\u0001H¾\u00020Â\u00022B\u0010Æ\u0002\u001a=\u0012\u0018\u0012\u0016\u0018\u00010I¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(\u009e\u0001\u0012\u0017\u0012\u00150¯\u0001¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(Ö\u0001\u0012\u0005\u0012\u0003H¾\u00020Ç\u00022Y\u0010È\u0002\u001aT\u0012\u0017\u0012\u00150ã\u0001¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(â\u0001\u0012\u0016\u0012\u00140I¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(É\u0002\u0012\u0017\u0012\u00150¯\u0001¢\u0006\u000f\bÃ\u0002\u0012\n\bÂ\u0001\u0012\u0005\b\b(Ö\u0001\u0012\u0005\u0012\u0003H¾\u00020Â\u0002H\u0082\b¢\u0006\u0003\u0010Ê\u0002J\u0012\u0010Ë\u0002\u001a\u00030\u0088\u00022\b\u0010Ì\u0002\u001a\u00030Í\u0002J\u0016\u0010Î\u0002\u001a\u00030·\u00022\f\u0010Ï\u0002\u001a\u0007\u0012\u0002\b\u00030Ð\u0002J\u001c\u0010Ñ\u0002\u001a\u00020\u00172\u0007\u0010Ê\u0001\u001a\u00020\u00162\b\u0010¡\u0002\u001a\u00030¢\u0002H\u0002J\u001c\u0010Ò\u0002\u001a\u00020\u00172\u0007\u0010Ê\u0001\u001a\u00020\u00162\n\b\u0002\u0010¡\u0002\u001a\u00030¢\u0002J \u0010Ó\u0002\u001a\u00030\u008a\u00022\b\u0010Ô\u0002\u001a\u00030Õ\u00022\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001J\u0010\u0010Ö\u0002\u001a\u00020M2\u0007\u0010×\u0002\u001a\u00020LJ$\u0010Ø\u0002\u001a\u00030Ù\u00022\f\u0010Ú\u0002\u001a\u0007\u0012\u0002\b\u00030Û\u00022\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001J\u0014\u0010Ü\u0002\u001a\u00030·\u00022\b\u0010á\u0001\u001a\u00030\u0081\u0002H\u0002J \u0010Ý\u0002\u001a\u00030·\u00022\b\u0010Þ\u0002\u001a\u00030ß\u00022\f\b\u0002\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001J\u0016\u0010à\u0002\u001a\u00030·\u00022\f\u0010Ï\u0002\u001a\u0007\u0012\u0002\b\u00030Ð\u0002J\u0014\u0010á\u0002\u001a\u00030â\u00022\b\u0010ã\u0002\u001a\u00030\u0081\u0002H\u0002J\u0016\u0010ä\u0002\u001a\u00030\u0097\u00022\n\u0010\u0096\u0002\u001a\u0005\u0018\u00010\u0097\u0002H\u0002J%\u0010å\u0002\u001a\u00020D2\u0007\u0010Ó\u0001\u001a\u00020C2\b\u0010Ù\u0001\u001a\u00030Ú\u00012\u0007\u0010Û\u0001\u001a\u00020?H\u0002J3\u0010æ\u0002\u001a\u00020&2\u0007\u0010Ï\u0001\u001a\u00020%2\u0007\u0010Í\u0001\u001a\u00020?2\f\b\u0002\u0010Ð\u0001\u001a\u0005\u0018\u00010¯\u00012\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u0001J(\u0010ç\u0002\u001a\u00020R2\b\u0010«\u0001\u001a\u00030¬\u00012\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u0001J'\u0010è\u0002\u001a\u00020J2\u0007\u0010§\u0001\u001a\u00020r2\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\n\b\u0002\u0010Ñ\u0001\u001a\u00030\u0093\u0001J\u0019\u0010é\u0002\u001a\u00020J2\u0007\u0010Ó\u0001\u001a\u00020C2\u0007\u0010Í\u0001\u001a\u00020IJ\u0010\u0010ê\u0002\u001a\u00020y2\u0007\u0010²\u0002\u001a\u00020xJ\u0011\u0010ë\u0002\u001a\u00030\u009b\u00012\u0007\u0010\u0099\u0002\u001a\u000203J\t\u0010ì\u0002\u001a\u00020fH\u0002J\u0012\u0010í\u0002\u001a\u0004\u0018\u0001042\u0007\u0010î\u0002\u001a\u000203J#\u0010ï\u0002\u001a\u00030\u009b\u00012\b\u0010ð\u0002\u001a\u00030ñ\u00022\u0007\u0010Û\u0001\u001a\u00020?H\u0000¢\u0006\u0003\bò\u0002J\u001a\u0010ó\u0002\u001a\u00030\u009b\u00012\u0007\u0010×\u0002\u001a\u00020L2\u0007\u0010ô\u0002\u001a\u00020MJ+\u0010õ\u0002\u001a\u00030\u009b\u00012\u0007\u0010Û\u0001\u001a\u00020?2\u0007\u0010ö\u0002\u001a\u00020A2\u0007\u0010÷\u0002\u001a\u00020AH\u0000¢\u0006\u0003\bø\u0002J\r\u0010ù\u0002\u001a\u00020@*\u00020AH\u0002Jd\u0010ú\u0002\u001a\u0003Hû\u0002\"\n\b\u0000\u0010û\u0002*\u00030\u009f\u0001*\u0003Hû\u00022\t\u0010«\u0001\u001a\u0004\u0018\u00010Q2\t\u0010Í\u0001\u001a\u0004\u0018\u00010I2\t\u0010Ý\u0001\u001a\u0004\u0018\u00010?2\b\u0010ü\u0002\u001a\u00030\u0093\u00012\b\u0010ý\u0002\u001a\u00030\u0093\u00012\f\b\u0002\u0010þ\u0002\u001a\u0005\u0018\u00010ÿ\u0002H\u0002¢\u0006\u0003\u0010\u0080\u0003J#\u0010\u0081\u0003\u001a\u00030\u009b\u0001*\u00030\u0082\u00032\b\u0010\u0083\u0003\u001a\u00030\u0084\u00032\b\u0010Ö\u0001\u001a\u00030¯\u0001H\u0002J^\u0010\u0085\u0003\u001a\u00020D*\u00020J2\u0007\u0010§\u0001\u001a\u00020r2\b\u0010Ö\u0001\u001a\u00030¯\u00012\b\u0010\u0086\u0003\u001a\u00030\u0087\u00032\b\u0010Â\u0001\u001a\u00030Ã\u00012\b\u0010\u0088\u0003\u001a\u00030\u0093\u00012\n\u0010\u0089\u0003\u001a\u0005\u0018\u00010\u008a\u00032\f\b\u0002\u0010¾\u0001\u001a\u0005\u0018\u00010¿\u0001H\u0000¢\u0006\u0003\b\u008b\u0003J7\u0010\u008c\u0003\u001a\u0003Hû\u0002\"\n\b\u0000\u0010û\u0002*\u00030\u009f\u0001*\u0003Hû\u00022\b\u0010¾\u0001\u001a\u00030¿\u00012\n\u0010À\u0001\u001a\u0005\u0018\u00010Á\u0001H\u0002¢\u0006\u0003\u0010\u008d\u0003JW\u0010\u008e\u0003\u001a\u00030\u009b\u0001\"\n\b\u0000\u0010û\u0002*\u00030\u009f\u0001*\u0003Hû\u00022\t\u0010«\u0001\u001a\u0004\u0018\u00010Q2\t\u0010\u008f\u0003\u001a\u0004\u0018\u00010?2\b\u0010ü\u0002\u001a\u00030\u0093\u00012\b\u0010ý\u0002\u001a\u00030\u0093\u00012\n\u0010þ\u0002\u001a\u0005\u0018\u00010ÿ\u0002H\u0002¢\u0006\u0003\u0010\u0090\u0003JP\u0010\u0091\u0003\u001a\u0005\u0018\u00010·\u0002\"\f\b\u0000\u0010\u0092\u0003\u0018\u0001*\u00030·\u0002\"\u0013\b\u0001\u0010\u0093\u0003\u0018\u0001*\n\u0012\u0005\u0012\u0003H\u0092\u00030\u0094\u0003*\u00030\u0095\u00032\b\u0010Â\u0001\u001a\u00030Ã\u00012\u000f\u0010ö\u0002\u001a\n\u0012\u0005\u0012\u0003H\u0092\u00030\u0094\u0003H\u0082\bJ\u001a\u0010\u0096\u0003\u001a\u00030\u0093\u0001*\u00020A2\n\u0010Þ\u0001\u001a\u0005\u0018\u00010ß\u0001H\u0002J(\u0010\u0097\u0003\u001a\u0003Hû\u0002\"\n\b\u0000\u0010û\u0002*\u00030\u009f\u0001*\u0003Hû\u00022\u0007\u0010«\u0001\u001a\u00020Q¢\u0006\u0003\u0010\u0098\u0003J\u0019\u0010\u0099\u0003\u001a\u00030\u009b\u0001*\u0002032\t\u0010Í\u0001\u001a\u0004\u0018\u00010IH\u0002J\u001b\u0010\u009a\u0003\u001a\u00030¿\u0001*\u00030\u009b\u00032\n\b\u0002\u0010í\u0001\u001a\u00030î\u0001H\u0002J\u001b\u0010\u009a\u0003\u001a\u00030¿\u0001*\u00030Õ\u00012\n\b\u0002\u0010í\u0001\u001a\u00030î\u0001H\u0002J\r\u0010\u009c\u0003\u001a\u00020r*\u00020CH\u0002R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u0019X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010 \u001a\u00020!X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020&0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020(X\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u001a\u00102\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u0002040\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u00105\u001a\u000206X\u0096\u0005¢\u0006\u0006\u001a\u0004\b7\u00108R\u0012\u00109\u001a\u00020:X\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010<R&\u0010=\u001a\u001a\u0012\u0004\u0012\u00020?\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020A0>0>X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010B\u001a\u000e\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020D0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010E\u001a\u000e\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020D0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010G\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020I0H\u0012\u0004\u0012\u00020J0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010K\u001a\u000e\u0012\u0004\u0012\u00020L\u0012\u0004\u0012\u00020M0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020O0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010P\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020R0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010S\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020U0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010V\u001a\u00020WX\u0096\u0005¢\u0006\u0006\u001a\u0004\bX\u0010YR\u0012\u0010Z\u001a\u00020[X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u001a\u0010^\u001a\u000e\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u0002030>X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010`\u001a\b\u0012\u0004\u0012\u00020b0aX\u0096\u0005¢\u0006\u0006\u001a\u0004\bc\u0010dR\u000e\u0010e\u001a\u00020fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010g\u001a\u00020h8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bk\u0010l\u001a\u0004\bi\u0010jR\u0012\u0010m\u001a\u00020nX\u0096\u0005¢\u0006\u0006\u001a\u0004\bo\u0010pR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010q\u001a\u000e\u0012\u0004\u0012\u00020r\u0012\u0004\u0012\u00020J0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010s\u001a\u00020tX\u0096\u0005¢\u0006\u0006\u001a\u0004\bu\u0010vR\u001a\u0010w\u001a\u000e\u0012\u0004\u0012\u00020x\u0012\u0004\u0012\u00020y0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010z\u001a\u00020{X\u0096\u0005¢\u0006\u0006\u001a\u0004\b|\u0010}R\u0014\u0010~\u001a\u00020\u007fX\u0096\u0005¢\u0006\b\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R\u0016\u0010\u0082\u0001\u001a\u00030\u0083\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0016\u0010\u0086\u0001\u001a\u00030\u0087\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001R\u0016\u0010\u008a\u0001\u001a\u00030\u008b\u0001X\u0096\u0005¢\u0006\b\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001R\u001c\u0010\u008e\u0001\u001a\u00030\u008f\u0001*\u00020r8BX\u0082\u0004¢\u0006\b\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R=\u0010\u0094\u0001\u001a\u0005\u0018\u00010\u0093\u0001*\u00020r2\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u00018B@BX\u0082\u008e\u0002¢\u0006\u0018\n\u0006\b\u0098\u0001\u0010\u0099\u0001\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001¨\u0006¥\u0003²\u00068\u0010Í\u0001\u001a\u0004\u0018\u00010I\"\u0010\b\u0000\u0010¼\u0002\u0018\u0001*\u0007\u0012\u0002\b\u00030±\u0001\"\u000b\b\u0001\u0010½\u0002\u0018\u0001*\u00020A\"\n\b\u0002\u0010¾\u0002*\u00030¿\u0002X\u008a\u0084\u0002²\u00069\u0010â\u0001\u001a\u0005\u0018\u00010ã\u0001\"\u0010\b\u0000\u0010¼\u0002\u0018\u0001*\u0007\u0012\u0002\b\u00030±\u0001\"\u000b\b\u0001\u0010½\u0002\u0018\u0001*\u00020A\"\n\b\u0002\u0010¾\u0002*\u00030¿\u0002X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "moduleDescriptor", "Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;", "commonMemberStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "builtInsFragmentCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "constructorCache", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "delegatedReverseCache", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "fakeOverridesInClass", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "fieldCache", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "fieldStaticOverrideCache", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FieldStaticOverrideKey;", "fieldToPropertyCache", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "fileCache", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fragmentCache", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$ExternalPackageFragments;", "functionCache", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "initializerCache", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irFakeOverridesForFirFakeOverrideMap", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideIdentifier;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lastTemporaryIndex", "", "localStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalCallableStorage;", "getLocalStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalCallableStorage;", "localStorage$delegate", "Lkotlin/properties/ReadWriteProperty;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "propertyCache", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scriptCache", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "fieldVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getFieldVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "<set-?>", "", "isStubPropertyForPureField", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;", "setStubPropertyForPureField", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/lang/Boolean;)V", "isStubPropertyForPureField$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "addContextReceiverParametersTo", "", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "result", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "areCompatible", "firFunction", "irFunction", "cacheDelegatedProperty", "property", "irProperty", "cacheDelegatedProperty$fir2ir", "cacheDelegationFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "cacheDelegationFunction$fir2ir", "computeDeclarationOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "parentOrigin", "computeThisReceiverOwner", "convertCatching", "R", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "createDefaultSetterParameter", "startOffset", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "firValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isCrossinline", "isNoinline", "createDefaultSetterParameter$fir2ir", "createExternalPackageFragment", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "fqName", "createIrAnonymousInitializer", "anonymousInitializer", "irParent", "createIrConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "predefinedOrigin", "isLocal", "createIrField", "field", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "origin", "createIrField$fir2ir", "createIrFieldAndDelegatedMembers", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "irClass", "createIrFunction", "thisReceiverOwner", "fakeOverrideOwnerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "createIrLazyFunction", "fir", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "lazyParent", "declarationOrigin", "createIrLazyProperty", "createIrLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "createIrParameter", "valueParameter", "index", "useStubForDefaultValueStub", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "skipDefaultParameter", "forcedDefaultValueConversion", "createIrParameter$fir2ir", "createIrParameterFromContextReceiver", "contextReceiver", "createIrProperty", "createIrPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "correspondingProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "propertyType", "isSetter", "dontUseSignature", "propertyAccessorForAnnotations", "createIrVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "givenOrigin", "declareIrAccessor", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "declareIrConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "declareIrField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "declareIrProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "declareIrSimpleFunction", "declareIrSimpleFunction$fir2ir", "declareIrVariable", "isVar", "isConst", "isLateinit", "declareTemporaryVariable", ApkUtil.DEF_MODULE_NAME, "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "nameHint", "", "enterScope", "declaration", "findIrParent", "callableDeclaration", "findIrParent$fir2ir", "packageFqName", "parentLookupTag", "firBasedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "firOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "generateLazyFakeOverrides", "getCachedIrAnonymousInitializer", "getCachedIrCallable", "IC", "FC", ResponseCacheMiddleware.CACHE, "signatureCalculator", "referenceIfAny", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Ljava/util/Map;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getCachedIrConstructor", "getCachedIrDelegateOrBackingField", "getCachedIrFieldStaticFakeOverrideByDeclaration", "getCachedIrFunction", "getCachedIrProperty", "getCachedIrScript", "script", "getFakeOverrideInClass", "getFieldStaticFakeOverrideKey", "ownerLookupTag", "getIrBackingFieldSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "firBackingFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirBackingFieldSymbol;", "getIrBuiltInsPackageFragment", "getIrCallableSymbol", "FS", "F", "I", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "firSymbol", "getCachedIrDeclaration", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "firDeclaration", "dispatchReceiverLookupTag", "createIrDeclaration", "Lkotlin/Function2;", "createIrLazyDeclaration", "lazyOwner", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getIrConstructorSymbol", "firConstructorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getIrDelegateFieldSymbol", "firVariableSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getIrExternalOrBuiltInsPackageFragment", "getIrExternalPackageFragment", "getIrFieldSymbol", "firFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "getIrFile", "firFile", "getIrFunctionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "firFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getIrPropertyForwardedSymbol", "getIrPropertySymbol", "firPropertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getIrValueSymbol", "getIrVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "firVariable", "getNameForTemporary", "getOrCreateDelegateIrField", "getOrCreateIrConstructor", "getOrCreateIrFunction", "getOrCreateIrProperty", "getOrCreateIrPropertyByPureField", "getOrCreateIrScript", "leaveScope", "nextTemporaryIndex", "originalDeclarationForDelegated", "irDeclaration", "preCacheBuiltinClassMembers", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "preCacheBuiltinClassMembers$fir2ir", "registerFile", "irFile", "saveFakeOverrideInClass", "originalDeclaration", "fakeOverride", "saveFakeOverrideInClass$fir2ir", "asFakeOverrideKey", "bindAndDeclareParameters", ExifInterface.GPS_DIRECTION_TRUE, "isStatic", "forSetter", "parentPropertyReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/declarations/IrClass;ZZLorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "convertAnnotationsForNonDeclaredMembers", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "firAnnotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "createBackingField", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isFinal", "firInitializerExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "createBackingField$fir2ir", "declareDefaultSetterParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "declareParameters", "containingClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/ir/declarations/IrClass;ZZLorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "findIrFakeOverride", ExifInterface.LATITUDE_SOUTH, "D", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLookupTagWithFixedSymbol;", "isFakeOverride", "putParametersInScope", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "setAndModifyParent", "toIrType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "toStubProperty", "Companion", "ExternalPackageFragments", "FakeOverrideIdentifier", "FakeOverrideKey", "FieldStaticOverrideKey", "IsStubPropertyForPureFieldKey", "NonCachedSourceFacadeContainerSource", "NonCachedSourceFileFacadeClass", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrDeclarationStorage implements Fir2IrComponents {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrDeclarationStorage.class), "localStorage", "getLocalStorage()Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalCallableStorage;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(Fir2IrDeclarationStorage.class), "isStubPropertyForPureField", "isStubPropertyForPureField(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;"))};

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<Name, IrSyntheticBodyKind> ENUM_SYNTHETIC_NAMES = MapsKt.mapOf(TuplesKt.to(Name.identifier(CompoundEntry.NAME_values), IrSyntheticBodyKind.ENUM_VALUES), TuplesKt.to(Name.identifier("valueOf"), IrSyntheticBodyKind.ENUM_VALUEOF), TuplesKt.to(Name.identifier(TypeBlock.NAME_entries), IrSyntheticBodyKind.ENUM_ENTRIES));
    private final ConcurrentHashMap<FqName, IrExternalPackageFragment> builtInsFragmentCache;
    private final Fir2IrComponents components;
    private final ConcurrentHashMap<FirConstructor, IrConstructor> constructorCache;
    private final ConcurrentHashMap<IrDeclaration, FirDeclaration> delegatedReverseCache;
    private final Map<IrClass, Map<FakeOverrideKey, FirCallableDeclaration>> fakeOverridesInClass;
    private final ConcurrentHashMap<FirField, IrField> fieldCache;
    private final ConcurrentHashMap<FieldStaticOverrideKey, IrField> fieldStaticOverrideCache;
    private final ConcurrentHashMap<Pair<FirField, IrDeclarationParent>, IrProperty> fieldToPropertyCache;
    private final ConcurrentHashMap<FirFile, IrFile> fileCache;
    private final ConcurrentHashMap<FqName, ExternalPackageFragments> fragmentCache;
    private final ConcurrentHashMap<FirFunction, IrSimpleFunction> functionCache;
    private final ConcurrentHashMap<FirAnonymousInitializer, IrAnonymousInitializer> initializerCache;
    private final Map<FakeOverrideIdentifier, IrDeclaration> irFakeOverridesForFirFakeOverrideMap;

    /* JADX INFO: renamed from: isStubPropertyForPureField$delegate, reason: from kotlin metadata */
    private final FirDeclarationDataRegistry.DeclarationDataAccessor isStubPropertyForPureField;
    private int lastTemporaryIndex;

    /* JADX INFO: renamed from: localStorage$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty localStorage;
    private final FirModuleDescriptor moduleDescriptor;
    private final ConcurrentHashMap<FirProperty, IrProperty> propertyCache;
    private final ConcurrentHashMap<FirScript, IrScript> scriptCache;

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

    public Fir2IrDeclarationStorage(Fir2IrComponents components, FirModuleDescriptor moduleDescriptor, Fir2IrCommonMemberStorage commonMemberStorage) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(commonMemberStorage, "commonMemberStorage");
        this.components = components;
        this.moduleDescriptor = moduleDescriptor;
        this.fragmentCache = new ConcurrentHashMap<>();
        this.builtInsFragmentCache = new ConcurrentHashMap<>();
        this.fileCache = new ConcurrentHashMap<>();
        this.scriptCache = new ConcurrentHashMap<>();
        this.functionCache = commonMemberStorage.getFunctionCache();
        this.constructorCache = commonMemberStorage.getConstructorCache();
        this.initializerCache = new ConcurrentHashMap<>();
        this.propertyCache = commonMemberStorage.getPropertyCache();
        this.fakeOverridesInClass = commonMemberStorage.getFakeOverridesInClass();
        this.irFakeOverridesForFirFakeOverrideMap = commonMemberStorage.getIrFakeOverridesForFirFakeOverrideMap();
        this.fieldToPropertyCache = new ConcurrentHashMap<>();
        this.delegatedReverseCache = new ConcurrentHashMap<>();
        this.fieldCache = new ConcurrentHashMap<>();
        this.fieldStaticOverrideCache = new ConcurrentHashMap<>();
        this.localStorage = ThreadLocalKt.threadLocal(new Function0<Fir2IrLocalCallableStorage>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$localStorage$2
            @Override // kotlin.jvm.functions.Function0
            public final Fir2IrLocalCallableStorage invoke() {
                return new Fir2IrLocalCallableStorage();
            }
        });
        this.isStubPropertyForPureField = FirDeclarationDataRegistry.INSTANCE.data(IsStubPropertyForPureFieldKey.INSTANCE);
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$ExternalPackageFragments;", "", "fragmentForDependencies", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "fragmentForPrecompiledBinaries", "(Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;)V", "getFragmentForDependencies", "()Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "getFragmentForPrecompiledBinaries", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ExternalPackageFragments {
        private final IrExternalPackageFragment fragmentForDependencies;
        private final IrExternalPackageFragment fragmentForPrecompiledBinaries;

        public ExternalPackageFragments(IrExternalPackageFragment fragmentForDependencies, IrExternalPackageFragment fragmentForPrecompiledBinaries) {
            Intrinsics.checkNotNullParameter(fragmentForDependencies, "fragmentForDependencies");
            Intrinsics.checkNotNullParameter(fragmentForPrecompiledBinaries, "fragmentForPrecompiledBinaries");
            this.fragmentForDependencies = fragmentForDependencies;
            this.fragmentForPrecompiledBinaries = fragmentForPrecompiledBinaries;
        }

        public final IrExternalPackageFragment getFragmentForDependencies() {
            return this.fragmentForDependencies;
        }

        public final IrExternalPackageFragment getFragmentForPrecompiledBinaries() {
            return this.fragmentForPrecompiledBinaries;
        }
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideIdentifier;", "", "originalSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "dispatchReceiverLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "getDispatchReceiverLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getOriginalSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class FakeOverrideIdentifier {
        private final ConeClassLikeLookupTag dispatchReceiverLookupTag;
        private final FirCallableSymbol<?> originalSymbol;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FakeOverrideIdentifier copy$default(FakeOverrideIdentifier fakeOverrideIdentifier, FirCallableSymbol firCallableSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
            if ((i & 1) != 0) {
                firCallableSymbol = fakeOverrideIdentifier.originalSymbol;
            }
            if ((i & 2) != 0) {
                coneClassLikeLookupTag = fakeOverrideIdentifier.dispatchReceiverLookupTag;
            }
            return fakeOverrideIdentifier.copy(firCallableSymbol, coneClassLikeLookupTag);
        }

        public final FirCallableSymbol<?> component1() {
            return this.originalSymbol;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ConeClassLikeLookupTag getDispatchReceiverLookupTag() {
            return this.dispatchReceiverLookupTag;
        }

        public final FakeOverrideIdentifier copy(FirCallableSymbol<?> originalSymbol, ConeClassLikeLookupTag dispatchReceiverLookupTag) {
            Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
            Intrinsics.checkNotNullParameter(dispatchReceiverLookupTag, "dispatchReceiverLookupTag");
            return new FakeOverrideIdentifier(originalSymbol, dispatchReceiverLookupTag);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FakeOverrideIdentifier)) {
                return false;
            }
            FakeOverrideIdentifier fakeOverrideIdentifier = (FakeOverrideIdentifier) other;
            return Intrinsics.areEqual(this.originalSymbol, fakeOverrideIdentifier.originalSymbol) && Intrinsics.areEqual(this.dispatchReceiverLookupTag, fakeOverrideIdentifier.dispatchReceiverLookupTag);
        }

        public int hashCode() {
            return (this.originalSymbol.hashCode() * 31) + this.dispatchReceiverLookupTag.hashCode();
        }

        public String toString() {
            return "FakeOverrideIdentifier(originalSymbol=" + this.originalSymbol + ", dispatchReceiverLookupTag=" + this.dispatchReceiverLookupTag + ')';
        }

        public FakeOverrideIdentifier(FirCallableSymbol<?> originalSymbol, ConeClassLikeLookupTag dispatchReceiverLookupTag) {
            Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
            Intrinsics.checkNotNullParameter(dispatchReceiverLookupTag, "dispatchReceiverLookupTag");
            this.originalSymbol = originalSymbol;
            this.dispatchReceiverLookupTag = dispatchReceiverLookupTag;
        }

        public final ConeClassLikeLookupTag getDispatchReceiverLookupTag() {
            return this.dispatchReceiverLookupTag;
        }

        public final FirCallableSymbol<?> getOriginalSymbol() {
            return this.originalSymbol;
        }
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey;", "", "()V", "Declaration", "Signature", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey$Declaration;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey$Signature;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class FakeOverrideKey {
        public /* synthetic */ FakeOverrideKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey$Signature;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "getSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Signature extends FakeOverrideKey {
            private final IdSignature signature;

            public static /* synthetic */ Signature copy$default(Signature signature, IdSignature idSignature, int i, Object obj) {
                if ((i & 1) != 0) {
                    idSignature = signature.signature;
                }
                return signature.copy(idSignature);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final IdSignature getSignature() {
                return this.signature;
            }

            public final Signature copy(IdSignature signature) {
                Intrinsics.checkNotNullParameter(signature, "signature");
                return new Signature(signature);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Signature) && Intrinsics.areEqual(this.signature, ((Signature) other).signature);
            }

            public int hashCode() {
                return this.signature.hashCode();
            }

            public String toString() {
                return "Signature(signature=" + this.signature + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Signature(IdSignature signature) {
                super(null);
                Intrinsics.checkNotNullParameter(signature, "signature");
                this.signature = signature;
            }

            public final IdSignature getSignature() {
                return this.signature;
            }
        }

        private FakeOverrideKey() {
        }

        /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey$Declaration;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Declaration extends FakeOverrideKey {
            private final FirCallableDeclaration declaration;

            public static /* synthetic */ Declaration copy$default(Declaration declaration, FirCallableDeclaration firCallableDeclaration, int i, Object obj) {
                if ((i & 1) != 0) {
                    firCallableDeclaration = declaration.declaration;
                }
                return declaration.copy(firCallableDeclaration);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final FirCallableDeclaration getDeclaration() {
                return this.declaration;
            }

            public final Declaration copy(FirCallableDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return new Declaration(declaration);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Declaration) && Intrinsics.areEqual(this.declaration, ((Declaration) other).declaration);
            }

            public int hashCode() {
                return this.declaration.hashCode();
            }

            public String toString() {
                return "Declaration(declaration=" + this.declaration + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Declaration(FirCallableDeclaration declaration) {
                super(null);
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                this.declaration = declaration;
            }

            public final FirCallableDeclaration getDeclaration() {
                return this.declaration;
            }
        }
    }

    private final FakeOverrideKey asFakeOverrideKey(FirCallableDeclaration firCallableDeclaration) {
        IdSignature idSignatureComposeSignature$default = FirBasedSignatureComposer.composeSignature$default(getSignatureComposer(), firCallableDeclaration, null, false, 6, null);
        if (idSignatureComposeSignature$default == null) {
            return new FakeOverrideKey.Declaration(firCallableDeclaration);
        }
        return new FakeOverrideKey.Signature(idSignatureComposeSignature$default);
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FieldStaticOverrideKey;", "", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lorg/jetbrains/kotlin/name/Name;)V", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FieldStaticOverrideKey {
        private final ConeClassLikeLookupTag lookupTag;
        private final Name name;

        public static /* synthetic */ FieldStaticOverrideKey copy$default(FieldStaticOverrideKey fieldStaticOverrideKey, ConeClassLikeLookupTag coneClassLikeLookupTag, Name name2, int i, Object obj) {
            if ((i & 1) != 0) {
                coneClassLikeLookupTag = fieldStaticOverrideKey.lookupTag;
            }
            if ((i & 2) != 0) {
                name2 = fieldStaticOverrideKey.name;
            }
            return fieldStaticOverrideKey.copy(coneClassLikeLookupTag, name2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ConeClassLikeLookupTag getLookupTag() {
            return this.lookupTag;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        public final FieldStaticOverrideKey copy(ConeClassLikeLookupTag lookupTag, Name name2) {
            Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
            Intrinsics.checkNotNullParameter(name2, "name");
            return new FieldStaticOverrideKey(lookupTag, name2);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FieldStaticOverrideKey)) {
                return false;
            }
            FieldStaticOverrideKey fieldStaticOverrideKey = (FieldStaticOverrideKey) other;
            return Intrinsics.areEqual(this.lookupTag, fieldStaticOverrideKey.lookupTag) && Intrinsics.areEqual(this.name, fieldStaticOverrideKey.name);
        }

        public int hashCode() {
            return (this.lookupTag.hashCode() * 31) + this.name.hashCode();
        }

        public String toString() {
            return "FieldStaticOverrideKey(lookupTag=" + this.lookupTag + ", name=" + this.name + ')';
        }

        public FieldStaticOverrideKey(ConeClassLikeLookupTag lookupTag, Name name2) {
            Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.lookupTag = lookupTag;
            this.name = name2;
        }

        public final ConeClassLikeLookupTag getLookupTag() {
            return this.lookupTag;
        }

        public final Name getName() {
            return this.name;
        }
    }

    private final Fir2IrLocalCallableStorage getLocalStorage() {
        return (Fir2IrLocalCallableStorage) this.localStorage.getValue(this, $$delegatedProperties[0]);
    }

    private final boolean areCompatible(FirFunction firFunction, IrFunction irFunction) {
        boolean z;
        if (((firFunction instanceof FirSimpleFunction) && (irFunction instanceof IrSimpleFunction) && !Intrinsics.areEqual(irFunction.getName(), ((FirSimpleFunction) firFunction).getName())) || irFunction.getValueParameters().size() != firFunction.getValueParameters().size()) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(irFunction.getValueParameters(), firFunction.getValueParameters());
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
                FirValueParameter firValueParameter = (FirValueParameter) pair.component2();
                IrType type = irValueParameter.getType();
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
                if (type instanceof IrSimpleType) {
                    IrClassifierSymbol classifier = ((IrSimpleType) type).getClassifier();
                    if (classifier instanceof IrTypeParameterSymbol) {
                        z = coneType instanceof ConeTypeParameterType;
                    } else {
                        if (classifier instanceof IrClassSymbol) {
                            IrClass owner = ((IrClassSymbol) classifier).getOwner();
                            if ((coneType instanceof ConeClassLikeType) && Intrinsics.areEqual(owner.getName(), ((ConeClassLikeType) coneType).getLookupTag().getName())) {
                                z = true;
                            }
                        } else if (!(classifier instanceof IrScriptSymbol)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        z = false;
                    }
                } else {
                    z = false;
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    public final void preCacheBuiltinClassMembers$fir2ir(FirRegularClass firClass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Iterator<FirDeclaration> it = firClass.getDeclarations().iterator();
        while (true) {
            Object obj = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration next = it.next();
            if (next instanceof FirProperty) {
                for (IrProperty irProperty : IrUtilsKt.getProperties(irClass)) {
                    if (Intrinsics.areEqual(irProperty.getName(), ((FirProperty) next).getName())) {
                        obj = irProperty;
                        break;
                    }
                }
                IrProperty irProperty2 = (IrProperty) obj;
                if (irProperty2 != null) {
                    this.propertyCache.put(next, irProperty2);
                }
            } else if (next instanceof FirSimpleFunction) {
                for (IrSimpleFunction irSimpleFunction : IrUtilsKt.getFunctions(irClass)) {
                    if (areCompatible((FirFunction) next, irSimpleFunction)) {
                        obj = irSimpleFunction;
                        break;
                    }
                }
                IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) obj;
                if (irSimpleFunction2 != null) {
                    this.functionCache.put(next, irSimpleFunction2);
                }
            } else if (next instanceof FirConstructor) {
                for (IrConstructor irConstructor : IrUtilsKt.getConstructors(irClass)) {
                    if (areCompatible((FirFunction) next, irConstructor)) {
                        obj = irConstructor;
                        break;
                    }
                }
                IrConstructor irConstructor2 = (IrConstructor) obj;
                if (irConstructor2 != null) {
                    this.constructorCache.put(next, irConstructor2);
                }
            }
        }
        FirRegularClass firRegularClass = firClass;
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) firRegularClass, getSession(), getScopeSession(), false, (FirResolvePhase) null);
        for (Name name2 : firTypeScopeUnsubstitutedScope.getCallableNames()) {
            List<IrDeclaration> listCreateListBuilder = CollectionsKt.createListBuilder();
            getFakeOverrideGenerator().generateFakeOverridesForName$fir2ir(irClass, firTypeScopeUnsubstitutedScope, name2, firRegularClass, listCreateListBuilder, SetsKt.emptySet());
            getFakeOverrideGenerator().bindOverriddenSymbols(CollectionsKt.build(listCreateListBuilder));
        }
    }

    public final void registerFile(FirFile firFile, IrFile irFile) {
        Intrinsics.checkNotNullParameter(firFile, "firFile");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        this.fileCache.put(firFile, irFile);
    }

    public final IrFile getIrFile(FirFile firFile) {
        Intrinsics.checkNotNullParameter(firFile, "firFile");
        IrFile irFile = this.fileCache.get(firFile);
        Intrinsics.checkNotNull(irFile);
        return irFile;
    }

    public final void enterScope(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        getSymbolTable().enterScope(declaration);
        if ((declaration instanceof IrSimpleFunction) || (declaration instanceof IrConstructor) || (declaration instanceof IrAnonymousInitializer) || (declaration instanceof IrProperty) || (declaration instanceof IrEnumEntry) || (declaration instanceof IrScript)) {
            getLocalStorage().enterCallable();
        }
    }

    public final void leaveScope(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if ((declaration instanceof IrSimpleFunction) || (declaration instanceof IrConstructor) || (declaration instanceof IrAnonymousInitializer) || (declaration instanceof IrProperty) || (declaration instanceof IrEnumEntry) || (declaration instanceof IrScript)) {
            getLocalStorage().leaveCallable();
        }
        getSymbolTable().leaveScope(declaration);
    }

    static /* synthetic */ IrType toIrType$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrDeclarationStorage.toIrType(firTypeRef, conversionTypeOrigin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrType toIrType(FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin) {
        return getTypeConverter().toIrType(firTypeRef, conversionTypeOrigin);
    }

    private final IrType toIrType(ConeKotlinType coneKotlinType, ConversionTypeOrigin conversionTypeOrigin) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), coneKotlinType, conversionTypeOrigin, null, false, false, false, 30, null);
    }

    private final IrExternalPackageFragment getIrExternalOrBuiltInsPackageFragment(FqName fqName, FirDeclarationOrigin firOrigin) {
        return StandardNames.BUILT_INS_PACKAGE_FQ_NAMES.contains(fqName) ? getIrBuiltInsPackageFragment(fqName) : getIrExternalPackageFragment(fqName, firOrigin);
    }

    private final IrExternalPackageFragment getIrBuiltInsPackageFragment(FqName fqName) {
        IrExternalPackageFragment irExternalPackageFragmentPutIfAbsent;
        ConcurrentHashMap<FqName, IrExternalPackageFragment> concurrentHashMap = this.builtInsFragmentCache;
        IrExternalPackageFragment irExternalPackageFragmentCreateExternalPackageFragment = concurrentHashMap.get(fqName);
        if (irExternalPackageFragmentCreateExternalPackageFragment == null && (irExternalPackageFragmentPutIfAbsent = concurrentHashMap.putIfAbsent(fqName, (irExternalPackageFragmentCreateExternalPackageFragment = createExternalPackageFragment(new FirBuiltInsPackageFragment(fqName, this.moduleDescriptor))))) != null) {
            irExternalPackageFragmentCreateExternalPackageFragment = irExternalPackageFragmentPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irExternalPackageFragmentCreateExternalPackageFragment, "getOrPut(...)");
        return irExternalPackageFragmentCreateExternalPackageFragment;
    }

    public static /* synthetic */ IrExternalPackageFragment getIrExternalPackageFragment$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FqName fqName, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            firDeclarationOrigin = FirDeclarationOrigin.Library.INSTANCE;
        }
        return fir2IrDeclarationStorage.getIrExternalPackageFragment(fqName, firDeclarationOrigin);
    }

    public final IrExternalPackageFragment getIrExternalPackageFragment(FqName fqName, FirDeclarationOrigin firOrigin) {
        ExternalPackageFragments externalPackageFragmentsPutIfAbsent;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(firOrigin, "firOrigin");
        ConcurrentHashMap<FqName, ExternalPackageFragments> concurrentHashMap = this.fragmentCache;
        ExternalPackageFragments externalPackageFragments = concurrentHashMap.get(fqName);
        if (externalPackageFragments == null && (externalPackageFragmentsPutIfAbsent = concurrentHashMap.putIfAbsent(fqName, (externalPackageFragments = new ExternalPackageFragments(createExternalPackageFragment(fqName, new FirModuleDescriptor(getSession(), this.moduleDescriptor.getBuiltIns())), createExternalPackageFragment(fqName, this.moduleDescriptor))))) != null) {
            externalPackageFragments = externalPackageFragmentsPutIfAbsent;
        }
        ExternalPackageFragments externalPackageFragments2 = externalPackageFragments;
        return Intrinsics.areEqual(firOrigin, FirDeclarationOrigin.Precompiled.INSTANCE) ? externalPackageFragments2.getFragmentForPrecompiledBinaries() : externalPackageFragments2.getFragmentForDependencies();
    }

    private final IrExternalPackageFragment createExternalPackageFragment(FqName fqName, FirModuleDescriptor moduleDescriptor) {
        return createExternalPackageFragment(new FirPackageFragmentDescriptor(fqName, moduleDescriptor));
    }

    private final IrExternalPackageFragment createExternalPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor) {
        return new IrExternalPackageFragmentImpl(new IrExternalPackageFragmentSymbolImpl(packageFragmentDescriptor), packageFragmentDescriptor.getFqName());
    }

    public final IrDeclarationParent findIrParent$fir2ir(FqName packageFqName, ConeClassLikeLookupTag parentLookupTag, FirBasedSymbol<?> firBasedSymbol, FirDeclarationOrigin firOrigin) {
        IrExternalPackageFragment irExternalOrBuiltInsPackageFragment;
        FirFile firClassifierContainerFileIfAny;
        IrDeclarationOrigin.FILE_CLASS file_class;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(firBasedSymbol, "firBasedSymbol");
        Intrinsics.checkNotNullParameter(firOrigin, "firOrigin");
        if (parentLookupTag != null) {
            return getClassifierStorage().findIrClass(parentLookupTag);
        }
        boolean z = firBasedSymbol instanceof FirCallableSymbol;
        if (z) {
            irExternalOrBuiltInsPackageFragment = getIrExternalPackageFragment(packageFqName, firOrigin);
        } else {
            irExternalOrBuiltInsPackageFragment = getIrExternalOrBuiltInsPackageFragment(packageFqName, firOrigin);
        }
        FirProvider firProvider = FirProviderKt.getFirProvider(firBasedSymbol.getModuleData().getSession());
        if (z) {
            firClassifierContainerFileIfAny = firProvider.getFirCallableContainerFile((FirCallableSymbol) firBasedSymbol);
        } else {
            if (!(firBasedSymbol instanceof FirClassLikeSymbol)) {
                throw new IllegalStateException(("Unknown symbol: " + firBasedSymbol).toString());
            }
            firClassifierContainerFileIfAny = firProvider.getFirClassifierContainerFileIfAny((FirClassLikeSymbol<?>) firBasedSymbol);
        }
        if (firClassifierContainerFileIfAny != null) {
            IrFile irFile = this.fileCache.get(firClassifierContainerFileIfAny);
            if (irFile != null) {
                return irFile;
            }
            if (getConfiguration().getAllowNonCachedDeclarations() && z) {
                PsiElement psi = UtilsKt.getPsi(firClassifierContainerFileIfAny);
                JvmClassName jvmClassNameByFqNameWithoutInnerClasses = null;
                PsiFile containingFile = psi != null ? psi.getContainingFile() : null;
                if (containingFile instanceof KtFile) {
                    JvmFileClassInfo fileClassInfoNoResolve = JvmFileClassUtil.getFileClassInfoNoResolve((KtFile) containingFile);
                    JvmClassName jvmClassNameByFqNameWithoutInnerClasses2 = JvmClassName.byFqNameWithoutInnerClasses(fileClassInfoNoResolve.getFileClassFqName());
                    Intrinsics.checkNotNullExpressionValue(jvmClassNameByFqNameWithoutInnerClasses2, "byFqNameWithoutInnerClasses(...)");
                    if (fileClassInfoNoResolve.getWithJvmMultifileClass()) {
                        jvmClassNameByFqNameWithoutInnerClasses = JvmClassName.byFqNameWithoutInnerClasses(fileClassInfoNoResolve.getFacadeClassFqName());
                        file_class = IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE;
                    } else {
                        file_class = IrDeclarationOrigin.FILE_CLASS.INSTANCE;
                    }
                    Name nameShortName = jvmClassNameByFqNameWithoutInnerClasses2.getFqNameForClassNameWithoutDollars().shortName();
                    Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
                    NonCachedSourceFileFacadeClass nonCachedSourceFileFacadeClass = new NonCachedSourceFileFacadeClass(file_class, nameShortName, new NonCachedSourceFacadeContainerSource(jvmClassNameByFqNameWithoutInnerClasses2, jvmClassNameByFqNameWithoutInnerClasses));
                    nonCachedSourceFileFacadeClass.setParent(irExternalOrBuiltInsPackageFragment);
                    IrUtilsKt.createParameterDeclarations(nonCachedSourceFileFacadeClass);
                    return nonCachedSourceFileFacadeClass;
                }
            }
        }
        return irExternalOrBuiltInsPackageFragment;
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$NonCachedSourceFileFacadeClass;", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class NonCachedSourceFileFacadeClass extends IrClassImpl {
        /* JADX WARN: Multi-variable type inference failed */
        public NonCachedSourceFileFacadeClass(IrDeclarationOrigin origin, Name name2, SourceElement source) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(source, "source");
            IrClassSymbolImpl irClassSymbolImpl = new IrClassSymbolImpl(null, 1, 0 == true ? 1 : 0);
            ClassKind classKind = ClassKind.CLASS;
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            super(-1, -1, origin, irClassSymbolImpl, name2, classKind, PUBLIC, Modality.FINAL, false, false, false, false, false, false, false, source, null, 98048, null);
        }
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$NonCachedSourceFacadeContainerSource;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "Lorg/jetbrains/kotlin/load/kotlin/FacadeClassSource;", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "facadeClassName", "(Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;)V", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getClassName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getFacadeClassName", "incompatibility", "", "getIncompatibility", "()Ljava/lang/Void;", "isPreReleaseInvisible", "", "()Z", "presentableString", "", "getPresentableString", "()Ljava/lang/String;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class NonCachedSourceFacadeContainerSource implements FacadeClassSource, DeserializedContainerSource {
        private final JvmClassName className;
        private final JvmClassName facadeClassName;

        public Void getIncompatibility() {
            return null;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        /* JADX INFO: renamed from: isPreReleaseInvisible */
        public boolean getIsPreReleaseInvisible() {
            return false;
        }

        public NonCachedSourceFacadeContainerSource(JvmClassName className, JvmClassName jvmClassName) {
            Intrinsics.checkNotNullParameter(className, "className");
            this.className = className;
            this.facadeClassName = jvmClassName;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        /* JADX INFO: renamed from: getIncompatibility, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ IncompatibleVersionErrorData mo6784getIncompatibility() {
            return (IncompatibleVersionErrorData) getIncompatibility();
        }

        @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
        public JvmClassName getClassName() {
            return this.className;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
        public JvmClassName getFacadeClassName() {
            return this.facadeClassName;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        public DeserializedContainerAbiStability getAbiStability() {
            return DeserializedContainerAbiStability.STABLE;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        public String getPresentableString() {
            String internalName = getClassName().getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return internalName;
        }

        @Override // org.jetbrains.kotlin.descriptors.SourceElement
        public SourceFile getContainingFile() {
            SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
            return NO_SOURCE_FILE;
        }
    }

    private final IrClass computeThisReceiverOwner(IrDeclarationParent parent) {
        if (!(parent instanceof IrClass) || (parent instanceof NonCachedSourceFileFacadeClass)) {
            return null;
        }
        return (IrClass) parent;
    }

    public final IrDeclarationParent findIrParent$fir2ir(FirCallableDeclaration callableDeclaration) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = callableDeclaration.getSymbol();
        CallableId callableId = symbol.getCallableId();
        return findIrParent$fir2ir(callableId.getPackageName(), ClassMembersKt.containingClassLookupTag(callableDeclaration), symbol, callableDeclaration.getOrigin());
    }

    private final void setAndModifyParent(IrDeclaration irDeclaration, IrDeclarationParent irDeclarationParent) {
        if (irDeclarationParent != null) {
            irDeclaration.setParent(irDeclarationParent);
            if (irDeclarationParent instanceof IrExternalPackageFragment) {
                ((IrExternalPackageFragment) irDeclarationParent).getDeclarations().add(irDeclaration);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends IrFunction> T declareDefaultSetterParameter(T t, IrType irType, FirValueParameter firValueParameter) {
        t.setValueParameters(CollectionsKt.listOf(createDefaultSetterParameter$fir2ir$default(this, t.getStartOffset(), t.getEndOffset(), irType, t, firValueParameter, null, false, false, 224, null)));
        return t;
    }

    public static /* synthetic */ IrValueParameter createDefaultSetterParameter$fir2ir$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, int i, int i2, IrType irType, IrFunction irFunction, FirValueParameter firValueParameter, Name name2, boolean z, boolean z2, int i3, Object obj) {
        return fir2IrDeclarationStorage.createDefaultSetterParameter$fir2ir(i, i2, irType, irFunction, firValueParameter, (i3 & 32) != 0 ? null : name2, (i3 & 64) != 0 ? false : z, (i3 & 128) != 0 ? false : z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrValueParameter createDefaultSetterParameter$fir2ir(int startOffset, int endOffset, IrType type, IrFunction parent, FirValueParameter firValueParameter, Name name2, boolean isCrossinline, boolean isNoinline) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrValueParameter irValueParameterCreateValueParameter = getIrFactory().createValueParameter(startOffset, endOffset, (IrDeclarationOrigin) IrDeclarationOrigin.DEFINED.INSTANCE, name2 == null ? SpecialNames.IMPLICIT_SET_PARAMETER : name2, type, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), parent.getContextReceiverParametersCount(), (IrType) null, isCrossinline, isNoinline, false);
        irValueParameterCreateValueParameter.setParent(parent);
        if (firValueParameter != null) {
            getAnnotationGenerator().generate(irValueParameterCreateValueParameter, firValueParameter);
        }
        return irValueParameterCreateValueParameter;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x009f  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:46:0x0102  */
    /* JADX WARN: Code duplicated, block: B:48:0x0106  */
    /* JADX WARN: Code duplicated, block: B:50:0x011c  */
    /* JADX WARN: Code duplicated, block: B:51:0x0121  */
    /* JADX WARN: Code duplicated, block: B:55:0x012e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:56:0x0130  */
    /* JADX WARN: Code duplicated, block: B:57:0x0135  */
    /* JADX WARN: Code duplicated, block: B:59:0x0138  */
    /* JADX WARN: Code duplicated, block: B:63:0x0144  */
    /* JADX WARN: Code duplicated, block: B:64:0x0148  */
    /* JADX WARN: Code duplicated, block: B:78:0x017c  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:97:0x01fb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:99:0x0206  */
    /* JADX WARN: Instruction removed from duplicated block: B:48:0x0106, please report this as an issue */
    private final <T extends IrFunction> void declareParameters(T t, FirFunction firFunction, IrClass irClass, boolean z, boolean z2, FirReceiverParameter firReceiverParameter) {
        boolean z3;
        boolean z4;
        IrDeclarationOrigin.DEFINED defined;
        IrClass parentClassOrNull;
        FirReceiverParameter receiverParameter;
        IrType type;
        KtSourceElement source;
        KtSourceElement ktSourceElement;
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        FirAnonymousFunction firAnonymousFunction;
        Name nameIdentifier;
        FirLabel label;
        String name2;
        boolean z5 = firFunction instanceof FirSimpleFunction;
        if (z5 || (firFunction instanceof FirConstructor)) {
            Fir2IrClassifierStorage.setTypeParameters$fir2ir$default(getClassifierStorage(), t, firFunction, null, 2, null);
        }
        ConversionTypeOrigin conversionTypeOrigin = z2 ? ConversionTypeOrigin.SETTER : ConversionTypeOrigin.DEFAULT;
        boolean z6 = true;
        if (firFunction instanceof FirDefaultPropertySetter) {
            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.first((List) ((FirDefaultPropertySetter) firFunction).getValueParameters());
            declareDefaultSetterParameter(t, toIrType(firValueParameter.getReturnTypeRef(), ConversionTypeOrigin.SETTER), firValueParameter);
        } else {
            if (firFunction != null) {
                List<FirContextReceiver> listContextReceiversForFunctionOrContainingProperty = ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(firFunction);
                t.setContextReceiverParametersCount(listContextReceiversForFunctionOrContainingProperty.size());
                List<IrValueParameter> listCreateListBuilder = CollectionsKt.createListBuilder();
                addContextReceiverParametersTo(listContextReceiversForFunctionOrContainingProperty, t, listCreateListBuilder);
                int i2 = 0;
                for (Object obj : firFunction.getValueParameters()) {
                    List<IrValueParameter> list = listCreateListBuilder;
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    FirValueParameter firValueParameter2 = (FirValueParameter) obj;
                    int contextReceiverParametersCount = t.getContextReceiverParametersCount() + i2;
                    if (!(firFunction instanceof FirConstructor)) {
                        z4 = z6;
                    } else if (Intrinsics.areEqual(irClass != null ? irClass.getName() : null, Name.identifier("Enum"))) {
                        z4 = false;
                    } else {
                        z4 = z6;
                    }
                    IrValueParameter irValueParameterCreateIrParameter$fir2ir$default = createIrParameter$fir2ir$default(this, firValueParameter2, contextReceiverParametersCount, z4, conversionTypeOrigin, (IrFakeOverrideUtilsKt.isFakeOverride(t) || Intrinsics.areEqual(t.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE)) ? z6 : false, false, 32, null);
                    irValueParameterCreateIrParameter$fir2ir$default.setParent(t);
                    list.add(irValueParameterCreateIrParameter$fir2ir$default);
                    z6 = z6;
                    i2 = i3;
                    listCreateListBuilder = listCreateListBuilder;
                }
                z3 = z6;
                t.setValueParameters(CollectionsKt.build(listCreateListBuilder));
            }
            defined = IrDeclarationOrigin.DEFINED.INSTANCE;
            if (firFunction instanceof FirConstructor) {
                if (!(firFunction instanceof FirPropertyAccessor) || firFunction == null) {
                    receiverParameter = firReceiverParameter;
                } else {
                    receiverParameter = firFunction.getReceiverParameter();
                }
                if (receiverParameter != null) {
                    source = receiverParameter.getSource();
                    ktSourceElement = source;
                    if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
                        i = -1;
                        endOffset = -1;
                    } else {
                        if (source == null && (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) != null) {
                            startOffset = numStartOffsetSkippingComments.intValue();
                        } else if (source != null) {
                            startOffset = source.getStartOffset();
                        } else {
                            startOffset = -1;
                        }
                        i = startOffset;
                        endOffset = source != null ? source.getEndOffset() : -1;
                    }
                    if (firFunction instanceof FirAnonymousFunction) {
                        firAnonymousFunction = (FirAnonymousFunction) firFunction;
                    } else {
                        firAnonymousFunction = null;
                    }
                    if (firAnonymousFunction == null && (label = firAnonymousFunction.getLabel()) != null && (name2 = label.getName()) != null) {
                        String str = Name.isValidIdentifier(name2) ? name2 : null;
                        if (str == null) {
                            str = AsmUtil.RECEIVER_PARAMETER_NAME;
                        }
                        Intrinsics.checkNotNull(str);
                        nameIdentifier = Name.identifier(AsmUtil.LABELED_THIS_PARAMETER + str);
                        if (nameIdentifier == null) {
                            nameIdentifier = SpecialNames.THIS;
                        }
                    } else {
                        nameIdentifier = SpecialNames.THIS;
                    }
                    Name name3 = nameIdentifier;
                    Intrinsics.checkNotNull(name3);
                    t.setExtensionReceiverParameter(ConversionUtilsKt.declareThisReceiverParameter(this, t, toIrType(receiverParameter.getTypeRef(), conversionTypeOrigin), defined, i, endOffset, name3, receiverParameter));
                }
                if (z5 || !Intrinsics.areEqual(((FirSimpleFunction) firFunction).getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                    z3 = false;
                }
                if (!(firFunction instanceof FirAnonymousFunction) || irClass == null || z || z3) {
                    return;
                }
                Fir2IrDeclarationStorage fir2IrDeclarationStorage = this;
                T t2 = t;
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                if (thisReceiver != null && (type = thisReceiver.getType()) != null) {
                    t.setDispatchReceiverParameter(ConversionUtilsKt.declareThisReceiverParameter$default(fir2IrDeclarationStorage, t2, type, defined, 0, 0, null, null, 120, null));
                    return;
                }
                throw new IllegalStateException("No this receiver".toString());
            }
            if (irClass != null) {
                parentClassOrNull = IrUtilsKt.getParentClassOrNull(irClass);
            } else {
                parentClassOrNull = null;
            }
            if (irClass == null && irClass.getIsInner() == z3 && parentClassOrNull != null) {
                IrValueParameter thisReceiver2 = parentClassOrNull.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver2);
                t.setDispatchReceiverParameter(ConversionUtilsKt.declareThisReceiverParameter$default(this, t, thisReceiver2.getType(), defined, 0, 0, null, null, 120, null));
                return;
            }
            return;
        }
        z3 = true;
        defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        if (firFunction instanceof FirConstructor) {
            if (irClass != null) {
                parentClassOrNull = IrUtilsKt.getParentClassOrNull(irClass);
            } else {
                parentClassOrNull = null;
            }
            if (irClass == null) {
                return;
            } else {
                return;
            }
        }
        if (firFunction instanceof FirPropertyAccessor) {
            receiverParameter = firReceiverParameter;
        } else {
            receiverParameter = firReceiverParameter;
        }
        if (receiverParameter != null) {
            source = receiverParameter.getSource();
            ktSourceElement = source;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null) {
                    if (source != null) {
                        startOffset = source.getStartOffset();
                    } else {
                        startOffset = -1;
                    }
                } else if (source != null) {
                    startOffset = source.getStartOffset();
                } else {
                    startOffset = -1;
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            if (firFunction instanceof FirAnonymousFunction) {
                firAnonymousFunction = (FirAnonymousFunction) firFunction;
            } else {
                firAnonymousFunction = null;
            }
            if (firAnonymousFunction == null) {
                nameIdentifier = SpecialNames.THIS;
            } else {
                nameIdentifier = SpecialNames.THIS;
            }
            Name name4 = nameIdentifier;
            Intrinsics.checkNotNull(name4);
            t.setExtensionReceiverParameter(ConversionUtilsKt.declareThisReceiverParameter(this, t, toIrType(receiverParameter.getTypeRef(), conversionTypeOrigin), defined, i, endOffset, name4, receiverParameter));
        }
        if (z5) {
            z3 = false;
        } else {
            z3 = false;
        }
        if (firFunction instanceof FirAnonymousFunction) {
        }
    }

    public final void addContextReceiverParametersTo(List<? extends FirContextReceiver> contextReceivers, IrFunction parent, List<IrValueParameter> result) {
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(result, "result");
        int i = 0;
        for (Object obj : contextReceivers) {
            List<IrValueParameter> list = result;
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameterCreateIrParameterFromContextReceiver = createIrParameterFromContextReceiver((FirContextReceiver) obj, i);
            irValueParameterCreateIrParameterFromContextReceiver.setParent(parent);
            list.add(irValueParameterCreateIrParameterFromContextReceiver);
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends IrFunction> T bindAndDeclareParameters(T t, FirFunction firFunction, IrDeclarationParent irDeclarationParent, IrClass irClass, boolean z, boolean z2, FirReceiverParameter firReceiverParameter) {
        setAndModifyParent(t, irDeclarationParent);
        declareParameters(t, firFunction, irClass, z, z2, firReceiverParameter);
        return t;
    }

    public final <T extends IrFunction> T putParametersInScope(T t, FirFunction function) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        for (Pair pair : CollectionsKt.zip(function.getValueParameters(), CollectionsKt.drop(t.getValueParameters(), ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(function).size()))) {
            getLocalStorage().putParameter((FirValueParameter) pair.component1(), (IrValueParameter) pair.component2());
        }
        return t;
    }

    public final IrSimpleFunction getCachedIrFunction(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function instanceof FirSimpleFunction ? getCachedIrFunction((FirSimpleFunction) function) : getLocalStorage().getLocalFunction(function);
    }

    public final IrSimpleFunction getCachedIrFunction(final FirSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getCachedIrFunction(function, null, new Function0<IdSignature>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.getCachedIrFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IdSignature invoke() {
                return FirBasedSignatureComposer.composeSignature$default(Fir2IrDeclarationStorage.this.getSignatureComposer(), function, null, false, 6, null);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0094 A[PHI: r7
      0x0094: PHI (r7v3 org.jetbrains.kotlin.ir.declarations.IrDeclaration) = 
      (r7v2 org.jetbrains.kotlin.ir.declarations.IrDeclaration)
      (r7v10 org.jetbrains.kotlin.ir.declarations.IrSimpleFunction)
      (r7v10 org.jetbrains.kotlin.ir.declarations.IrSimpleFunction)
      (r7v14 org.jetbrains.kotlin.ir.declarations.IrDeclaration)
     binds: [B:33:0x0092, B:48:0x00c3, B:49:0x00c5, B:38:0x00a2] A[DONT_GENERATE, DONT_INLINE]] */
    public final IrSimpleFunction getCachedIrFunction(FirSimpleFunction function, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag, Function0<? extends IdSignature> signatureCalculator) {
        IrSimpleFunction owner;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(signatureCalculator, "signatureCalculator");
        if (Intrinsics.areEqual(function.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            return getLocalStorage().getLocalFunction(function);
        }
        AbstractMap abstractMap = this.functionCache;
        FirSimpleFunction firSimpleFunction = function;
        boolean zIsFakeOverride = isFakeOverride(firSimpleFunction, fakeOverrideOwnerLookupTag);
        IrSimpleFunction irSimpleFunction = null;
        if (zIsFakeOverride) {
            FirCallableDeclaration firCallableDeclaration = firSimpleFunction;
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firCallableDeclaration = originalForSubstitutionOverrideAttr;
            }
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
            if (fakeOverrideOwnerLookupTag == null) {
                fakeOverrideOwnerLookupTag = ClassMembersKt.containingClassLookupTag(firSimpleFunction);
                Intrinsics.checkNotNull(fakeOverrideOwnerLookupTag);
            }
            IrDeclaration irDeclaration = this.irFakeOverridesForFirFakeOverrideMap.get(new FakeOverrideIdentifier(symbol, fakeOverrideOwnerLookupTag));
            if (irDeclaration != null) {
                if (irDeclaration == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                }
                irSimpleFunction = (IrSimpleFunction) irDeclaration;
            }
            return (IrSimpleFunction) irSimpleFunction;
        }
        owner = (IrDeclaration) abstractMap.get(firSimpleFunction);
        if (owner != null) {
            irSimpleFunction = owner;
        }
        return (IrSimpleFunction) irSimpleFunction;
        if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firSimpleFunction) || (owner = (IrDeclaration) abstractMap.get(firSimpleFunction)) == null) {
            IdSignature idSignatureInvoke = signatureCalculator.invoke();
            if (idSignatureInvoke != null) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunctionIfAny = getSymbolTable().referenceSimpleFunctionIfAny(idSignatureInvoke);
                owner = irSimpleFunctionSymbolReferenceSimpleFunctionIfAny != null ? irSimpleFunctionSymbolReferenceSimpleFunctionIfAny.getOwner() : null;
                if (owner != null) {
                    if (!zIsFakeOverride) {
                        abstractMap.put(firSimpleFunction, owner);
                    }
                    irSimpleFunction = owner;
                }
            }
        } else {
            irSimpleFunction = owner;
        }
        return (IrSimpleFunction) irSimpleFunction;
    }

    public final void cacheDelegationFunction$fir2ir(FirSimpleFunction function, IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        this.functionCache.put(function, irFunction);
        this.delegatedReverseCache.put(irFunction, function);
    }

    public final FirDeclaration originalDeclarationForDelegated(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        return this.delegatedReverseCache.get(irDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSimpleFunction declareIrSimpleFunction$fir2ir(final IdSignature signature, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        if (signature == null) {
            return factory.invoke(new IrSimpleFunctionSymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareSimpleFunction(signature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$declareIrSimpleFunction$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                return new Fir2IrSimpleFunctionSymbol(signature);
            }
        }, factory);
    }

    public static /* synthetic */ IrSimpleFunction getOrCreateIrFunction$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirSimpleFunction firSimpleFunction, IrDeclarationParent irDeclarationParent, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.getOrCreateIrFunction(firSimpleFunction, irDeclarationParent, z);
    }

    public final IrSimpleFunction getOrCreateIrFunction(FirSimpleFunction function, IrDeclarationParent irParent, boolean isLocal) {
        Intrinsics.checkNotNullParameter(function, "function");
        IrSimpleFunction cachedIrFunction = getCachedIrFunction(function);
        return cachedIrFunction != null ? cachedIrFunction : createIrFunction$default(this, function, irParent, null, null, isLocal, ClassMembersKt.containingClassLookupTag(function), 12, null);
    }

    public static /* synthetic */ IrSimpleFunction createIrFunction$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirFunction firFunction, IrDeclarationParent irDeclarationParent, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, boolean z, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 4) != 0) {
            irClass = fir2IrDeclarationStorage.computeThisReceiverOwner(irDeclarationParent);
        }
        IrClass irClass2 = irClass;
        IrDeclarationOrigin irDeclarationOrigin2 = (i & 8) != 0 ? null : irDeclarationOrigin;
        if ((i & 16) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.createIrFunction(firFunction, irDeclarationParent, irClass2, irDeclarationOrigin2, z, (i & 32) != 0 ? null : coneClassLikeLookupTag);
    }

    /* JADX WARN: Code duplicated, block: B:125:0x0223  */
    public final IrSimpleFunction createIrFunction(final FirFunction function, final IrDeclarationParent irParent, final IrClass thisReceiverOwner, IrDeclarationOrigin predefinedOrigin, boolean isLocal, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB ir_external_java_declaration_stubComputeIrOrigin;
        Name name2;
        Visibilities.Local visibility;
        final boolean zIsSuspend;
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        FirCallableDeclaration firCallableDeclaration;
        ConeKotlinType type;
        ConeSimpleKotlinType dispatchReceiverType;
        Intrinsics.checkNotNullParameter(function, "function");
        try {
            final FirSimpleFunction firSimpleFunction = function instanceof FirSimpleFunction ? (FirSimpleFunction) function : null;
            boolean z = (function instanceof FirAnonymousFunction) && ((FirAnonymousFunction) function).isLambda();
            if (z) {
                ir_external_java_declaration_stubComputeIrOrigin = IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE;
            } else if (CallableIdUtilsKt.isKFunctionInvoke(function.getSymbol().getCallableId())) {
                ir_external_java_declaration_stubComputeIrOrigin = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
            } else if (firSimpleFunction != null && firSimpleFunction.getStatus().isStatic() && ENUM_SYNTHETIC_NAMES.containsKey(firSimpleFunction.getName())) {
                ir_external_java_declaration_stubComputeIrOrigin = IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE;
            } else {
                IrClass irClass = irParent instanceof IrClass ? (IrClass) irParent : null;
                ir_external_java_declaration_stubComputeIrOrigin = (Intrinsics.areEqual(irClass != null ? irClass.getOrigin() : null, IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE) && DeclarationUtilsKt.isJavaOrEnhancement(function)) ? IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE : ConversionUtilsKt.computeIrOrigin(function, predefinedOrigin);
            }
            final IrDeclarationOrigin irDeclarationOrigin = ir_external_java_declaration_stubComputeIrOrigin;
            IdSignature idSignatureComposeSignature$default = isLocal || (!getConfiguration().getLinkViaSignatures() && !(irParent instanceof Fir2IrLazyClass) && (((dispatchReceiverType = function.getDispatchReceiverType()) == null || !ConeBuiltinTypeUtilsKt.isPrimitive(dispatchReceiverType)) && function.getContainerSource() == null && !Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) && !function.getStatus().isOverride())) ? null : FirBasedSignatureComposer.composeSignature$default(getSignatureComposer(), function, fakeOverrideOwnerLookupTag, false, 4, null);
            if ((irParent instanceof Fir2IrLazyClass) && idSignatureComposeSignature$default != null) {
                return createIrLazyFunction((FirSimpleFunction) function, idSignatureComposeSignature$default, irParent, irDeclarationOrigin);
            }
            if (firSimpleFunction == null || (name2 = firSimpleFunction.getName()) == null) {
                name2 = z ? SpecialNames.ANONYMOUS : SpecialNames.NO_NAME_PROVIDED;
            }
            final Name name3 = name2;
            if (firSimpleFunction == null || (visibility = firSimpleFunction.getStatus().getVisibility()) == null) {
                visibility = Visibilities.Local.INSTANCE;
            }
            final Visibility visibility2 = visibility;
            if (z) {
                FirTypeRef typeRef = ((FirAnonymousFunction) function).getTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                zIsSuspend = (firResolvedTypeRef == null || (type = firResolvedTypeRef.getType()) == null || !FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(type, getSession())) ? false : true;
            } else {
                zIsSuspend = function.getStatus().isSuspend();
            }
            KtSourceElement source = function.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            final int i2 = i;
            IdSignature idSignature = idSignatureComposeSignature$default;
            final int i3 = endOffset;
            IrSimpleFunction irSimpleFunctionDeclareIrSimpleFunction$fir2ir = declareIrSimpleFunction$fir2ir(idSignature, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrFunction$1$created$1$result$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrSimpleFunction invoke(IrSimpleFunctionSymbol symbol) {
                    Modality modality;
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    this.this$0.getClassifierStorage().preCacheTypeParameters$fir2ir(function, symbol);
                    IrFactory irFactory = this.this$0.getIrFactory();
                    int i4 = Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) ? -2 : i2;
                    int i5 = Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) ? -2 : i3;
                    IrDeclarationOrigin irDeclarationOrigin2 = irDeclarationOrigin;
                    Name name4 = name3;
                    DescriptorVisibility descriptorVisibilityConvertToDescriptorVisibility = this.this$0.components.getVisibilityConverter().convertToDescriptorVisibility(visibility2);
                    FirSimpleFunction firSimpleFunction2 = firSimpleFunction;
                    boolean z2 = firSimpleFunction2 != null && firSimpleFunction2.getStatus().isInline();
                    FirSimpleFunction firSimpleFunction3 = firSimpleFunction;
                    boolean z3 = firSimpleFunction3 != null && firSimpleFunction3.getStatus().isExpect();
                    IrType irType$default = Fir2IrDeclarationStorage.toIrType$default(this.this$0, function.getReturnTypeRef(), null, 1, null);
                    FirSimpleFunction firSimpleFunction4 = firSimpleFunction;
                    if (firSimpleFunction4 == null || (modality = firSimpleFunction4.getStatus().getModality()) == null) {
                        modality = Modality.FINAL;
                    }
                    FirSimpleFunction firSimpleFunction5 = firSimpleFunction;
                    boolean z4 = firSimpleFunction5 != null && firSimpleFunction5.getStatus().isTailRec();
                    boolean z5 = zIsSuspend;
                    FirSimpleFunction firSimpleFunction6 = firSimpleFunction;
                    boolean z6 = firSimpleFunction6 != null && firSimpleFunction6.getStatus().isOperator();
                    FirSimpleFunction firSimpleFunction7 = firSimpleFunction;
                    boolean z7 = firSimpleFunction7 != null && firSimpleFunction7.getStatus().isInfix();
                    FirSimpleFunction firSimpleFunction8 = firSimpleFunction;
                    boolean z8 = firSimpleFunction8 != null && firSimpleFunction8.getStatus().isExternal();
                    FirSimpleFunction firSimpleFunction9 = firSimpleFunction;
                    IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, i4, i5, irDeclarationOrigin2, name4, descriptorVisibilityConvertToDescriptorVisibility, z2, z3, irType$default, modality, symbol, z4, z5, z6, z7, z8, firSimpleFunction9 != null ? firSimpleFunction9.getContainerSource() : null, false, 65536, null);
                    FirFunction firFunction = function;
                    Fir2IrDeclarationStorage fir2IrDeclarationStorage = this.this$0;
                    IrDeclarationParent irDeclarationParent = irParent;
                    IrClass irClass2 = thisReceiverOwner;
                    FirSimpleFunction firSimpleFunction10 = firSimpleFunction;
                    irSimpleFunctionCreateSimpleFunction$default.setMetadata(new FirMetadataSource.Function(firFunction));
                    IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
                    fir2IrDeclarationStorage.enterScope(irSimpleFunction);
                    fir2IrDeclarationStorage.bindAndDeclareParameters(irSimpleFunctionCreateSimpleFunction$default, firFunction, irDeclarationParent, irClass2, firSimpleFunction10 != null && firSimpleFunction10.getStatus().isStatic(), false, (32 & 32) != 0 ? null : null);
                    fir2IrDeclarationStorage.convertAnnotationsForNonDeclaredMembers(irSimpleFunctionCreateSimpleFunction$default, firFunction, irSimpleFunctionCreateSimpleFunction$default.getOrigin());
                    fir2IrDeclarationStorage.leaveScope(irSimpleFunction);
                    return irSimpleFunctionCreateSimpleFunction$default;
                }
            });
            if (Intrinsics.areEqual(visibility2, Visibilities.Local.INSTANCE)) {
                getLocalStorage().putLocalFunction(function, irSimpleFunctionDeclareIrSimpleFunction$fir2ir);
                return irSimpleFunctionDeclareIrSimpleFunction$fir2ir;
            }
            if (CallableIdUtilsKt.isKFunctionInvoke(function.getSymbol().getCallableId())) {
                FirFunction fir = function.getSymbol().getFir();
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(fir) || (fir.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(fir) : null;
                FirFunctionSymbol firFunctionSymbol = (FirFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
                FirNamedFunctionSymbol firNamedFunctionSymbol = firFunctionSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) firFunctionSymbol : null;
                if (firNamedFunctionSymbol != null) {
                    List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunctionDeclareIrSimpleFunction$fir2ir.getOverriddenSymbols();
                    firCallableDeclaration = null;
                    IrFunctionSymbol irFunctionSymbol$default = getIrFunctionSymbol$default(this, firNamedFunctionSymbol, null, 2, null);
                    Intrinsics.checkNotNull(irFunctionSymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                    irSimpleFunctionDeclareIrSimpleFunction$fir2ir.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) overriddenSymbols, (IrSimpleFunctionSymbol) irFunctionSymbol$default));
                } else {
                    firCallableDeclaration = null;
                }
            } else {
                firCallableDeclaration = null;
            }
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = fakeOverrideOwnerLookupTag;
            if (isFakeOverride(function, coneClassLikeLookupTagContainingClassLookupTag)) {
                FirFunction firFunction = function;
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firFunction) || (firFunction.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firFunction) : firCallableDeclaration;
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        originalForSubstitutionOverrideAttr2 = ClassMembersKt.isIntersectionOverride(firFunction) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firFunction) : firCallableDeclaration;
                    }
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        break;
                    }
                    firFunction = originalForSubstitutionOverrideAttr2;
                }
                FirFunctionSymbol<? extends FirFunction> symbol = ((FirFunction) firFunction).getSymbol();
                if (coneClassLikeLookupTagContainingClassLookupTag == null) {
                    coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(function);
                    Intrinsics.checkNotNull(coneClassLikeLookupTagContainingClassLookupTag);
                }
                this.irFakeOverridesForFirFakeOverrideMap.put(new FakeOverrideIdentifier(symbol, coneClassLikeLookupTagContainingClassLookupTag), irSimpleFunctionDeclareIrSimpleFunction$fir2ir);
            } else {
                this.functionCache.put(function, irSimpleFunctionDeclareIrSimpleFunction$fir2ir);
            }
            return irSimpleFunctionDeclareIrSimpleFunction$fir2ir;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirFunction firFunction2 = function;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firFunction2.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firFunction2);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    public final IrAnonymousInitializer getCachedIrAnonymousInitializer(FirAnonymousInitializer anonymousInitializer) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        return this.initializerCache.get(anonymousInitializer);
    }

    public final IrAnonymousInitializer createIrAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, IrClass irParent) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        try {
            KtSourceElement source = anonymousInitializer.getSource();
            int i = -1;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                endOffset = source != null ? source.getEndOffset() : -1;
                i = startOffset;
            }
            IrAnonymousInitializer irAnonymousInitializerDeclareAnonymousInitializer = getSymbolTable().getDescriptorExtension().declareAnonymousInitializer(i, endOffset, IrDeclarationOrigin.DEFINED.INSTANCE, irParent.getDescriptor());
            irAnonymousInitializerDeclareAnonymousInitializer.setParent(irParent);
            this.initializerCache.put(anonymousInitializer, irAnonymousInitializerDeclareAnonymousInitializer);
            return irAnonymousInitializerDeclareAnonymousInitializer;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirAnonymousInitializer firAnonymousInitializer = anonymousInitializer;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firAnonymousInitializer.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firAnonymousInitializer);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrConstructor getCachedIrConstructor$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirConstructor firConstructor, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.getCachedIrConstructor.1
                @Override // kotlin.jvm.functions.Function0
                public final Void invoke() {
                    return null;
                }
            };
        }
        return fir2IrDeclarationStorage.getCachedIrConstructor(firConstructor, function0);
    }

    public final IrConstructor getCachedIrConstructor(FirConstructor constructor, Function0<? extends IdSignature> signatureCalculator) {
        IrConstructorSymbol irConstructorSymbolReferenceConstructorIfAny;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(signatureCalculator, "signatureCalculator");
        IrConstructor irConstructor = this.constructorCache.get(constructor);
        if (irConstructor != null) {
            return irConstructor;
        }
        IdSignature idSignatureInvoke = signatureCalculator.invoke();
        if (idSignatureInvoke == null || (irConstructorSymbolReferenceConstructorIfAny = getSymbolTable().referenceConstructorIfAny(idSignatureInvoke)) == null) {
            return null;
        }
        IrConstructor owner = irConstructorSymbolReferenceConstructorIfAny.getOwner();
        this.constructorCache.put(constructor, owner);
        return owner;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrConstructor declareIrConstructor(final IdSignature signature, Function1<? super IrConstructorSymbol, ? extends IrConstructor> factory) {
        if (signature == null) {
            return factory.invoke(new IrConstructorSymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareConstructor(signature, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.declareIrConstructor.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return new Fir2IrConstructorSymbol(signature);
            }
        }, factory);
    }

    public static /* synthetic */ IrConstructor createIrConstructor$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirConstructor firConstructor, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.createIrConstructor(firConstructor, irClass, irDeclarationOrigin, z);
    }

    public final IrConstructor createIrConstructor(final FirConstructor constructor, final IrClass irParent, IrDeclarationOrigin predefinedOrigin, boolean isLocal) {
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        try {
            final IrDeclarationOrigin irDeclarationOriginComputeIrOrigin = ConversionUtilsKt.computeIrOrigin(constructor, predefinedOrigin);
            final boolean zIsPrimary = constructor.getIsPrimary();
            IdSignature idSignatureComposeSignature$default = isLocal || !getConfiguration().getLinkViaSignatures() ? null : FirBasedSignatureComposer.composeSignature$default(getSignatureComposer(), constructor, null, false, 6, null);
            final Visibility visibility = IrUtilsKt.isAnonymousObject(irParent) ? Visibilities.Public.INSTANCE : constructor.getStatus().getVisibility();
            KtSourceElement source = constructor.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            return declareIrConstructor(idSignatureComposeSignature$default, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrConstructor$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrConstructor invoke(IrConstructorSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    this.this$0.getClassifierStorage().preCacheTypeParameters$fir2ir(constructor, symbol);
                    IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(this.this$0.getIrFactory(), i, endOffset, irDeclarationOriginComputeIrOrigin, SpecialNames.INIT, this.this$0.components.getVisibilityConverter().convertToDescriptorVisibility(visibility), false, constructor.getStatus().isExpect(), Fir2IrDeclarationStorage.toIrType$default(this.this$0, constructor.getReturnTypeRef(), null, 1, null), symbol, zIsPrimary, false, (DeserializedContainerSource) null, 2048, (Object) null);
                    FirConstructor firConstructor = constructor;
                    Fir2IrDeclarationStorage fir2IrDeclarationStorage = this.this$0;
                    IrClass irClass = irParent;
                    FirConstructor firConstructor2 = firConstructor;
                    irConstructorCreateConstructor$default.setMetadata(new FirMetadataSource.Function(firConstructor2));
                    fir2IrDeclarationStorage.constructorCache.put(firConstructor, irConstructorCreateConstructor$default);
                    IrConstructor irConstructor = irConstructorCreateConstructor$default;
                    fir2IrDeclarationStorage.enterScope(irConstructor);
                    fir2IrDeclarationStorage.bindAndDeclareParameters(irConstructorCreateConstructor$default, firConstructor2, irClass, irClass, false, false, (32 & 32) != 0 ? null : null);
                    fir2IrDeclarationStorage.leaveScope(irConstructor);
                    return irConstructorCreateConstructor$default;
                }
            });
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirConstructor firConstructor = constructor;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firConstructor.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firConstructor);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    public static /* synthetic */ IrConstructor getOrCreateIrConstructor$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirConstructor firConstructor, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.getOrCreateIrConstructor(firConstructor, irClass, irDeclarationOrigin, z);
    }

    public final IrConstructor getOrCreateIrConstructor(FirConstructor constructor, IrClass irParent, IrDeclarationOrigin predefinedOrigin, boolean isLocal) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        IrConstructor cachedIrConstructor$default = getCachedIrConstructor$default(this, constructor, null, 2, null);
        return cachedIrConstructor$default != null ? cachedIrConstructor$default : createIrConstructor(constructor, irParent, predefinedOrigin, isLocal);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrSimpleFunction declareIrAccessor(final IdSignature signature, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> factory) {
        if (signature == null) {
            return factory.invoke(new IrSimpleFunctionSymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareSimpleFunction(signature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.declareIrAccessor.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                return new Fir2IrSimpleFunctionSymbol(signature);
            }
        }, factory);
    }

    static /* synthetic */ IrSimpleFunction createIrPropertyAccessor$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirPropertyAccessor firPropertyAccessor, FirProperty firProperty, IrDeclarationWithName irDeclarationWithName, IrType irType, IrDeclarationParent irDeclarationParent, IrClass irClass, boolean z, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, boolean z2, ConeClassLikeLookupTag coneClassLikeLookupTag, FirPropertyAccessor firPropertyAccessor2, int i3, Object obj) {
        return fir2IrDeclarationStorage.createIrPropertyAccessor(firPropertyAccessor, firProperty, irDeclarationWithName, irType, irDeclarationParent, (i3 & 32) != 0 ? fir2IrDeclarationStorage.computeThisReceiverOwner(irDeclarationParent) : irClass, z, irDeclarationOrigin, i, i2, (i3 & 1024) != 0 ? false : z2, (i3 & 2048) != 0 ? null : coneClassLikeLookupTag, (i3 & 4096) != 0 ? firPropertyAccessor : firPropertyAccessor2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createIrPropertyAccessor(final FirPropertyAccessor propertyAccessor, final FirProperty property, final IrDeclarationWithName correspondingProperty, final IrType propertyType, final IrDeclarationParent irParent, final IrClass thisReceiverOwner, final boolean isSetter, final IrDeclarationOrigin origin, final int startOffset, final int endOffset, boolean dontUseSignature, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag, final FirPropertyAccessor propertyAccessorForAnnotations) {
        IdSignature idSignatureComposeAccessorSignature;
        FirElement firElement;
        FirElement firElement2 = propertyAccessor != null ? propertyAccessor : property;
        final String str = isSetter ? FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX : "get";
        if (dontUseSignature) {
            idSignatureComposeAccessorSignature = null;
        } else {
            try {
                try {
                    idSignatureComposeAccessorSignature = getSignatureComposer().composeAccessorSignature(property, isSetter, fakeOverrideOwnerLookupTag);
                } catch (Exception e) {
                    e = e;
                    firElement = firElement2;
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firElement.getClass(), e);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firElement);
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            } catch (ProcessCanceledException e2) {
                throw e2;
            }
        }
        IrProperty irProperty = correspondingProperty instanceof IrProperty ? (IrProperty) correspondingProperty : null;
        final DeserializedContainerSource containerSource = irProperty != null ? irProperty.getContainerSource() : null;
        firElement = firElement2;
        try {
            try {
                return declareIrAccessor(idSignatureComposeAccessorSignature, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrPropertyAccessor$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Code duplicated, block: B:66:0x0193  */
                    /* JADX WARN: Code duplicated, block: B:69:0x019a  */
                    @Override // kotlin.jvm.functions.Function1
                    public final IrSimpleFunction invoke(IrSimpleFunctionSymbol symbol) {
                        Modality modality;
                        boolean z;
                        Fir2IrLazyProperty fir2IrLazyProperty;
                        Visibility visibility;
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        IrType unitType = isSetter ? this.getIrBuiltIns().getUnitType() : propertyType;
                        FirPropertyAccessor firPropertyAccessor = propertyAccessor;
                        DescriptorVisibility descriptorVisibilityConvertToDescriptorVisibility = (firPropertyAccessor == null || (visibility = firPropertyAccessor.getStatus().getVisibility()) == null) ? null : this.components.getVisibilityConverter().convertToDescriptorVisibility(visibility);
                        IrFactory irFactory = this.getIrFactory();
                        int i = startOffset;
                        int i2 = endOffset;
                        IrDeclarationOrigin irDeclarationOrigin = origin;
                        Name nameSpecial = Name.special("<" + str + '-' + correspondingProperty.getName() + '>');
                        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                        if (descriptorVisibilityConvertToDescriptorVisibility == null) {
                            IrDeclarationWithName irDeclarationWithName = correspondingProperty;
                            Intrinsics.checkNotNull(irDeclarationWithName, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility");
                            descriptorVisibilityConvertToDescriptorVisibility = ((IrDeclarationWithVisibility) irDeclarationWithName).getVisibility();
                        }
                        DescriptorVisibility descriptorVisibility = descriptorVisibilityConvertToDescriptorVisibility;
                        FirPropertyAccessor firPropertyAccessor2 = propertyAccessor;
                        boolean z2 = false;
                        boolean z3 = firPropertyAccessor2 != null && firPropertyAccessor2.getStatus().isInline();
                        IrDeclarationWithName irDeclarationWithName2 = correspondingProperty;
                        IrOverridableMember irOverridableMember = irDeclarationWithName2 instanceof IrOverridableMember ? (IrOverridableMember) irDeclarationWithName2 : null;
                        if (irOverridableMember == null || (modality = irOverridableMember.getModality()) == null) {
                            modality = Modality.FINAL;
                        }
                        Modality modality2 = modality;
                        FirPropertyAccessor firPropertyAccessor3 = propertyAccessor;
                        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, i, i2, irDeclarationOrigin, nameSpecial, descriptorVisibility, z3, false, unitType, modality2, symbol, false, false, false, false, firPropertyAccessor3 != null && firPropertyAccessor3.getStatus().isExternal(), containerSource, false, 65536, null);
                        IrDeclarationWithName irDeclarationWithName3 = correspondingProperty;
                        FirPropertyAccessor firPropertyAccessor4 = propertyAccessor;
                        Fir2IrDeclarationStorage fir2IrDeclarationStorage = this;
                        IrDeclarationOrigin irDeclarationOrigin2 = origin;
                        FirPropertyAccessor firPropertyAccessor5 = propertyAccessorForAnnotations;
                        boolean z4 = isSetter;
                        FirProperty firProperty = property;
                        IrDeclarationParent irDeclarationParent = irParent;
                        IrClass irClass = thisReceiverOwner;
                        IrProperty irProperty2 = irDeclarationWithName3 instanceof IrProperty ? (IrProperty) irDeclarationWithName3 : null;
                        irSimpleFunctionCreateSimpleFunction$default.setCorrespondingPropertySymbol(irProperty2 != null ? irProperty2.getSymbol() : null);
                        if (firPropertyAccessor4 != null) {
                            irSimpleFunctionCreateSimpleFunction$default.setMetadata(new FirMetadataSource.Function(firPropertyAccessor4));
                            fir2IrDeclarationStorage.convertAnnotationsForNonDeclaredMembers(irSimpleFunctionCreateSimpleFunction$default, firPropertyAccessor4, irDeclarationOrigin2);
                        }
                        if (firPropertyAccessor5 != null) {
                            fir2IrDeclarationStorage.convertAnnotationsForNonDeclaredMembers(irSimpleFunctionCreateSimpleFunction$default, firPropertyAccessor5, irDeclarationOrigin2);
                        }
                        fir2IrDeclarationStorage.getClassifierStorage().setTypeParameters$fir2ir(irSimpleFunctionCreateSimpleFunction$default, firProperty, z4 ? ConversionTypeOrigin.SETTER : ConversionTypeOrigin.DEFAULT);
                        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
                        fir2IrDeclarationStorage.enterScope(irSimpleFunction);
                        if (firPropertyAccessor4 == null && z4) {
                            fir2IrDeclarationStorage.declareDefaultSetterParameter(irSimpleFunctionCreateSimpleFunction$default, fir2IrDeclarationStorage.toIrType(firProperty.getReturnTypeRef(), ConversionTypeOrigin.SETTER), null);
                        }
                        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionCreateSimpleFunction$default;
                        FirPropertyAccessor firPropertyAccessor6 = firPropertyAccessor4;
                        if (irDeclarationParent instanceof IrClass) {
                            if (firPropertyAccessor4 != null) {
                                z = true;
                                if (firPropertyAccessor4.getStatus().isStatic()) {
                                }
                            } else {
                                z = true;
                            }
                            fir2IrDeclarationStorage.bindAndDeclareParameters(irSimpleFunction2, firPropertyAccessor6, irDeclarationParent, irClass, z2, z4, firProperty.getReceiverParameter());
                            fir2IrDeclarationStorage.leaveScope(irSimpleFunction);
                            if (irDeclarationParent != null) {
                                irSimpleFunctionCreateSimpleFunction$default.setParent(irDeclarationParent);
                            }
                            if (irDeclarationWithName3 instanceof Fir2IrLazyProperty) {
                                fir2IrLazyProperty = (Fir2IrLazyProperty) irDeclarationWithName3;
                                if (fir2IrLazyProperty.getContainingClass() != null && !irSimpleFunctionCreateSimpleFunction$default.getIsFakeOverride() && irClass != null) {
                                    irSimpleFunctionCreateSimpleFunction$default.setOverriddenSymbols(ConversionUtilsKt.generateOverriddenAccessorSymbols(fir2IrDeclarationStorage, fir2IrLazyProperty.getFir(), fir2IrLazyProperty.getContainingClass(), true ^ z4));
                                }
                            }
                            return irSimpleFunctionCreateSimpleFunction$default;
                        }
                        z = true;
                        z2 = z;
                        fir2IrDeclarationStorage.bindAndDeclareParameters(irSimpleFunction2, firPropertyAccessor6, irDeclarationParent, irClass, z2, z4, firProperty.getReceiverParameter());
                        fir2IrDeclarationStorage.leaveScope(irSimpleFunction);
                        if (irDeclarationParent != null) {
                            irSimpleFunctionCreateSimpleFunction$default.setParent(irDeclarationParent);
                        }
                        if (irDeclarationWithName3 instanceof Fir2IrLazyProperty) {
                            fir2IrLazyProperty = (Fir2IrLazyProperty) irDeclarationWithName3;
                            if (fir2IrLazyProperty.getContainingClass() != null) {
                                irSimpleFunctionCreateSimpleFunction$default.setOverriddenSymbols(ConversionUtilsKt.generateOverriddenAccessorSymbols(fir2IrDeclarationStorage, fir2IrLazyProperty.getFir(), fir2IrLazyProperty.getContainingClass(), true ^ z4));
                            }
                        }
                        return irSimpleFunctionCreateSimpleFunction$default;
                    }
                });
            } catch (ProcessCanceledException e3) {
                throw e3;
            } catch (Exception e4) {
                e = e4;
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firElement.getClass(), e);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder2 = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder2, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firElement);
                kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder2.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments2;
            }
        } catch (Exception e5) {
            e = e5;
        }
    }

    public final IrField createBackingField$fir2ir(final IrProperty irProperty, final FirProperty property, final IrDeclarationOrigin origin, final DescriptorVisibility visibility, final Name name2, final boolean z, FirExpression firExpression, IrType irType) {
        final IrType irType$default;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (irType == null) {
            try {
                Intrinsics.checkNotNull(firExpression);
                irType$default = toIrType$default(this, firExpression.getTypeRef(), null, 1, null);
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Exception e2) {
                FirProperty firProperty = property;
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firProperty.getClass(), e2);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firProperty);
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
        } else {
            irType$default = irType;
        }
        return declareIrField(new Function1<IrFieldSymbol, IrField>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createBackingField$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrField invoke(IrFieldSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrField irFieldCreateField = this.this$0.getIrFactory().createField(irProperty.getStartOffset(), irProperty.getEndOffset(), origin, name2, visibility, symbol, irType$default, z, property.getStatus().isStatic() || !((irProperty.getParent() instanceof IrClass) || (irProperty.getParent() instanceof IrScript)), property.getStatus().isExternal());
                irFieldCreateField.setCorrespondingPropertySymbol(irProperty.getSymbol());
                FirProperty firProperty2 = property;
                Fir2IrDeclarationStorage fir2IrDeclarationStorage = this.this$0;
                IrDeclarationOrigin irDeclarationOrigin = origin;
                irFieldCreateField.setMetadata(new FirMetadataSource.Property(firProperty2));
                fir2IrDeclarationStorage.convertAnnotationsForNonDeclaredMembers(irFieldCreateField, firProperty2, irDeclarationOrigin);
                return irFieldCreateField;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Visibility getFieldVisibility(FirProperty firProperty) {
        List<FirAnnotation> annotations;
        Visibility visibility;
        Visibility visibility2;
        if (DeclarationAttributesKt.getHasExplicitBackingField(firProperty)) {
            FirBackingField backingField = firProperty.getBackingField();
            return (backingField == null || (visibility2 = backingField.getStatus().getVisibility()) == null) ? firProperty.getStatus().getVisibility() : visibility2;
        }
        FirProperty firProperty2 = firProperty;
        if (firProperty2.getStatus().isLateInit()) {
            FirPropertyAccessor setter = firProperty.getSetter();
            return (setter == null || (visibility = setter.getStatus().getVisibility()) == null) ? firProperty.getStatus().getVisibility() : visibility;
        }
        if (firProperty2.getStatus().isConst()) {
            return firProperty.getStatus().getVisibility();
        }
        FirSession session = getSession();
        FirBackingField backingField2 = firProperty.getBackingField();
        if (backingField2 != null && (annotations = backingField2.getAnnotations()) != null) {
            List<FirAnnotation> list = annotations;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (FirAnnotationUtilsKt.isJvmFieldAnnotation((FirAnnotation) it.next(), session)) {
                        return firProperty.getStatus().getVisibility();
                    }
                }
            }
        }
        return Intrinsics.areEqual(firProperty.getOrigin(), FirDeclarationOrigin.ScriptCustomization.ResultProperty.INSTANCE) ? firProperty.getStatus().getVisibility() : Visibilities.Private.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrProperty declareIrProperty(final IdSignature signature, Function1<? super IrPropertySymbol, ? extends IrProperty> factory) {
        if (signature == null) {
            return factory.invoke(new IrPropertySymbolImpl(null, 1, 0 == true ? 1 : 0));
        }
        return getSymbolTable().declareProperty(signature, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.declareIrProperty.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPropertySymbol invoke() {
                return new Fir2IrPropertySymbol(signature);
            }
        }, factory);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrField declareIrField(Function1<? super IrFieldSymbol, ? extends IrField> factory) {
        return factory.invoke(new IrFieldSymbolImpl(null, 1, 0 == true ? 1 : 0));
    }

    public static /* synthetic */ IrProperty getOrCreateIrProperty$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirProperty firProperty, IrDeclarationParent irDeclarationParent, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.getOrCreateIrProperty(firProperty, irDeclarationParent, z);
    }

    public final IrProperty getOrCreateIrProperty(FirProperty property, IrDeclarationParent irParent, boolean isLocal) {
        Intrinsics.checkNotNullParameter(property, "property");
        IrProperty cachedIrProperty = getCachedIrProperty(property);
        return cachedIrProperty != null ? cachedIrProperty : createIrProperty$default(this, property, irParent, null, null, isLocal, null, 44, null);
    }

    public final IrProperty getOrCreateIrPropertyByPureField(FirField field, IrDeclarationParent irParent) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        ConcurrentHashMap<Pair<FirField, IrDeclarationParent>, IrProperty> concurrentHashMap = this.fieldToPropertyCache;
        Pair<FirField, IrDeclarationParent> pair = TuplesKt.to(field, irParent);
        IrProperty irProperty = concurrentHashMap.get(pair);
        if (irProperty == null) {
            IrClass irClass = irParent instanceof IrClass ? (IrClass) irParent : null;
            ClassId classId = irClass != null ? AdditionalIrUtilsKt.getClassId(irClass) : null;
            IrProperty irPropertyCreateIrProperty$default = createIrProperty$default(this, toStubProperty(field), irParent, null, null, false, classId != null ? TypeConstructionUtilsKt.toLookupTag(classId) : null, 28, null);
            IrProperty irPropertyPutIfAbsent = concurrentHashMap.putIfAbsent(pair, irPropertyCreateIrProperty$default);
            irProperty = irPropertyPutIfAbsent == null ? irPropertyCreateIrProperty$default : irPropertyPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irProperty, "getOrPut(...)");
        return irProperty;
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$IsStubPropertyForPureFieldKey;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", "()V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IsStubPropertyForPureFieldKey extends FirDeclarationDataKey {
        public static final IsStubPropertyForPureFieldKey INSTANCE = new IsStubPropertyForPureFieldKey();

        private IsStubPropertyForPureFieldKey() {
        }
    }

    private final Boolean isStubPropertyForPureField(FirProperty firProperty) {
        return (Boolean) this.isStubPropertyForPureField.getValue(firProperty, $$delegatedProperties[1]);
    }

    private final void setStubPropertyForPureField(FirProperty firProperty, Boolean bool) {
        this.isStubPropertyForPureField.setValue(firProperty, $$delegatedProperties[1], bool);
    }

    public static /* synthetic */ IrProperty createIrProperty$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirProperty firProperty, IrDeclarationParent irDeclarationParent, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, boolean z, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 4) != 0) {
            irClass = fir2IrDeclarationStorage.computeThisReceiverOwner(irDeclarationParent);
        }
        IrClass irClass2 = irClass;
        IrDeclarationOrigin irDeclarationOrigin2 = (i & 8) != 0 ? null : irDeclarationOrigin;
        if ((i & 16) != 0) {
            z = false;
        }
        return fir2IrDeclarationStorage.createIrProperty(firProperty, irDeclarationParent, irClass2, irDeclarationOrigin2, z, (i & 32) != 0 ? null : coneClassLikeLookupTag);
    }

    public final IrProperty createIrProperty(final FirProperty property, final IrDeclarationParent irParent, final IrClass thisReceiverOwner, IrDeclarationOrigin predefinedOrigin, boolean isLocal, final ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        ConeSimpleKotlinType dispatchReceiverType;
        ConeClassLikeLookupTag coneClassLikeLookupTag = fakeOverrideOwnerLookupTag;
        Intrinsics.checkNotNullParameter(property, "property");
        try {
            final IrDeclarationOrigin irDeclarationOriginComputeIrOrigin = (property.getStatus().isStatic() && ENUM_SYNTHETIC_NAMES.containsKey(property.getName())) ? IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE : ConversionUtilsKt.computeIrOrigin(property, predefinedOrigin);
            boolean z = true;
            if (!isLocal && (getConfiguration().getLinkViaSignatures() || (irParent instanceof Fir2IrLazyClass) || (((dispatchReceiverType = property.getDispatchReceiverType()) != null && ConeBuiltinTypeUtilsKt.isPrimitive(dispatchReceiverType)) || property.getContainerSource() != null || Intrinsics.areEqual(irDeclarationOriginComputeIrOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || property.getStatus().isOverride()))) {
                z = false;
            }
            IdSignature idSignatureComposeSignature$default = z ? null : FirBasedSignatureComposer.composeSignature$default(getSignatureComposer(), property, fakeOverrideOwnerLookupTag, false, 4, null);
            if ((irParent instanceof Fir2IrLazyClass) && idSignatureComposeSignature$default != null) {
                return createIrLazyProperty(property, idSignatureComposeSignature$default, irParent, irDeclarationOriginComputeIrOrigin);
            }
            KtSourceElement source = property.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            final IdSignature idSignature = idSignatureComposeSignature$default;
            IrProperty irPropertyDeclareIrProperty = declareIrProperty(idSignatureComposeSignature$default, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrProperty$1$1$result$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Code duplicated, block: B:114:0x02e5  */
                /* JADX WARN: Code duplicated, block: B:115:0x02e8  */
                /* JADX WARN: Code duplicated, block: B:122:0x0300  */
                /* JADX WARN: Code duplicated, block: B:124:0x0306  */
                /* JADX WARN: Code duplicated, block: B:126:0x030c  */
                /* JADX WARN: Code duplicated, block: B:127:0x0311  */
                /* JADX WARN: Code duplicated, block: B:130:0x0337 A[LOOP:1: B:116:0x02ea->B:130:0x0337, LOOP_END] */
                /* JADX WARN: Code duplicated, block: B:136:0x0315 A[EDGE_INSN: B:136:0x0315->B:129:0x0315 BREAK  A[LOOP:1: B:116:0x02ea->B:130:0x0337], SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:30:0x012f  */
                /* JADX WARN: Code duplicated, block: B:31:0x0132  */
                /* JADX WARN: Code duplicated, block: B:33:0x0135  */
                /* JADX WARN: Code duplicated, block: B:79:0x0237  */
                @Override // kotlin.jvm.functions.Function1
                public final IrProperty invoke(IrPropertySymbol symbol) {
                    FirPropertyAccessor firPropertyAccessor;
                    FirExpression initializer;
                    boolean z2;
                    IrField irFieldCreateBackingField$fir2ir;
                    FirTypeRef returnTypeRef;
                    IrType irType$default;
                    FirAnnotationContainer fir;
                    FirTypeParameterRefsOwner firTypeParameterRefsOwner;
                    FirReference calleeReference;
                    IrDeclarationOrigin irDeclarationOrigin;
                    Fir2IrDeclarationStorage fir2IrDeclarationStorage;
                    IrDeclarationOrigin irDeclarationOrigin2;
                    IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR default_property_accessor;
                    boolean z3;
                    FirCallableDeclaration originalForSubstitutionOverrideAttr;
                    IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR default_property_accessor2;
                    IrField backingField;
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    IrPropertySymbol irPropertySymbol = symbol;
                    this.this$0.getClassifierStorage().preCacheTypeParameters$fir2ir(property, irPropertySymbol);
                    IrFactory irFactory = this.this$0.getIrFactory();
                    int i2 = i;
                    int i3 = endOffset;
                    IrDeclarationOrigin irDeclarationOrigin3 = irDeclarationOriginComputeIrOrigin;
                    Name name2 = property.getName();
                    DescriptorVisibility descriptorVisibilityConvertToDescriptorVisibility = this.this$0.components.getVisibilityConverter().convertToDescriptorVisibility(property.getStatus().getVisibility());
                    Modality modality = property.getStatus().getModality();
                    Intrinsics.checkNotNull(modality);
                    IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(irFactory, i2, i3, irDeclarationOrigin3, name2, descriptorVisibilityConvertToDescriptorVisibility, modality, symbol, property.getIsVar(), property.getStatus().isConst(), property.getStatus().isLateInit(), property.getDelegate() != null, property.getStatus().isExternal(), property.getContainerSource(), property.getStatus().isExpect(), false, 16384, (Object) null);
                    FirProperty firProperty = property;
                    Fir2IrDeclarationStorage fir2IrDeclarationStorage2 = this.this$0;
                    IrDeclarationOrigin irDeclarationOrigin4 = irDeclarationOriginComputeIrOrigin;
                    IrDeclarationParent irDeclarationParent = irParent;
                    IrClass irClass = thisReceiverOwner;
                    int i4 = i;
                    int i5 = endOffset;
                    IdSignature idSignature2 = idSignature;
                    ConeClassLikeLookupTag coneClassLikeLookupTag2 = fakeOverrideOwnerLookupTag;
                    irPropertyCreateProperty$default.setMetadata(new FirMetadataSource.Property(firProperty));
                    fir2IrDeclarationStorage2.convertAnnotationsForNonDeclaredMembers(irPropertyCreateProperty$default, firProperty, irDeclarationOrigin4);
                    IrProperty irProperty = irPropertyCreateProperty$default;
                    fir2IrDeclarationStorage2.enterScope(irProperty);
                    if (irDeclarationParent != null) {
                        irPropertyCreateProperty$default.setParent(irDeclarationParent);
                    }
                    IrType irType$default2 = Fir2IrDeclarationStorage.toIrType$default(fir2IrDeclarationStorage2, firProperty.getReturnTypeRef(), null, 1, null);
                    FirExpression delegate = firProperty.getDelegate();
                    FirPropertyAccessor getter = firProperty.getGetter();
                    FirPropertyAccessor setter = firProperty.getSetter();
                    if (delegate != null || DeclarationAttributesKt.getHasBackingField(firProperty)) {
                        if (delegate != null) {
                            FirQualifiedAccessExpression firQualifiedAccessExpression = delegate instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) delegate : null;
                            if (firQualifiedAccessExpression == null || (calleeReference = firQualifiedAccessExpression.getCalleeReference()) == null) {
                                firPropertyAccessor = setter;
                            } else {
                                firPropertyAccessor = setter;
                                FirBasedSymbol resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(calleeReference, false, 1, null);
                                if (resolvedBaseSymbol$default != null) {
                                    fir = resolvedBaseSymbol$default.getFir();
                                }
                                if (fir instanceof FirTypeParameterRefsOwner) {
                                    firTypeParameterRefsOwner = (FirTypeParameterRefsOwner) fir;
                                } else {
                                    firTypeParameterRefsOwner = null;
                                }
                                if (firTypeParameterRefsOwner != null) {
                                    fir2IrDeclarationStorage2.getClassifierStorage().preCacheTypeParameters$fir2ir(firTypeParameterRefsOwner, irPropertySymbol);
                                }
                                irFieldCreateBackingField$fir2ir = fir2IrDeclarationStorage2.createBackingField$fir2ir(irPropertyCreateProperty$default, firProperty, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE, fir2IrDeclarationStorage2.components.getVisibilityConverter().convertToDescriptorVisibility(fir2IrDeclarationStorage2.getFieldVisibility(firProperty)), NameUtils.propertyDelegateName(firProperty.getName()), true, delegate, (64 & 64) != 0 ? null : null);
                                irProperty = irProperty;
                                z2 = true;
                            }
                            fir = null;
                            if (fir instanceof FirTypeParameterRefsOwner) {
                                firTypeParameterRefsOwner = (FirTypeParameterRefsOwner) fir;
                            } else {
                                firTypeParameterRefsOwner = null;
                            }
                            if (firTypeParameterRefsOwner != null) {
                                fir2IrDeclarationStorage2.getClassifierStorage().preCacheTypeParameters$fir2ir(firTypeParameterRefsOwner, irPropertySymbol);
                            }
                            irFieldCreateBackingField$fir2ir = fir2IrDeclarationStorage2.createBackingField$fir2ir(irPropertyCreateProperty$default, firProperty, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE, fir2IrDeclarationStorage2.components.getVisibilityConverter().convertToDescriptorVisibility(fir2IrDeclarationStorage2.getFieldVisibility(firProperty)), NameUtils.propertyDelegateName(firProperty.getName()), true, delegate, (64 & 64) != 0 ? null : null);
                            irProperty = irProperty;
                            z2 = true;
                        } else {
                            firPropertyAccessor = setter;
                            FirBackingField backingField2 = firProperty.getBackingField();
                            if (backingField2 == null || (initializer = backingField2.getInitializer()) == null) {
                                initializer = firProperty.getInitializer();
                            }
                            FirBackingField backingField3 = firProperty.getBackingField();
                            IrField irFieldCreateBackingField$fir2ir2 = fir2IrDeclarationStorage2.createBackingField$fir2ir(irPropertyCreateProperty$default, firProperty, IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE, fir2IrDeclarationStorage2.components.getVisibilityConverter().convertToDescriptorVisibility(fir2IrDeclarationStorage2.getFieldVisibility(firProperty)), firProperty.getName(), firProperty.getIsVal(), initializer, (backingField3 == null || (returnTypeRef = backingField3.getReturnTypeRef()) == null || (irType$default = Fir2IrDeclarationStorage.toIrType$default(fir2IrDeclarationStorage2, returnTypeRef, null, 1, null)) == null) ? irType$default2 : irType$default);
                            if (initializer instanceof FirConstExpression) {
                                FirConstExpression firConstExpression = (FirConstExpression) initializer;
                                z2 = true;
                                irFieldCreateBackingField$fir2ir2.setInitializer(IrFactoryHelpersKt.createExpressionBody(irPropertyCreateProperty$default.getFactory(), ConversionUtilsKt.toIrConst(firConstExpression, Fir2IrDeclarationStorage.toIrType$default(fir2IrDeclarationStorage2, firConstExpression.getTypeRef(), null, 1, null))));
                            } else {
                                z2 = true;
                            }
                            irFieldCreateBackingField$fir2ir = irFieldCreateBackingField$fir2ir2;
                        }
                        irPropertyCreateProperty$default.setBackingField(irFieldCreateBackingField$fir2ir);
                    } else {
                        irProperty = irProperty;
                        firPropertyAccessor = setter;
                        z2 = true;
                    }
                    if (irDeclarationParent != null && (backingField = irPropertyCreateProperty$default.getBackingField()) != null) {
                        backingField.setParent(irDeclarationParent);
                    }
                    IrProperty irProperty2 = irPropertyCreateProperty$default;
                    if (Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE)) {
                        irDeclarationOrigin = irDeclarationOrigin4;
                    } else {
                        if (delegate != null) {
                            default_property_accessor2 = IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE;
                        } else if (Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) || (getter != null && !(getter instanceof FirDefaultPropertyGetter))) {
                            irDeclarationOrigin = irDeclarationOrigin4;
                        } else {
                            default_property_accessor2 = IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE;
                        }
                        irDeclarationOrigin = default_property_accessor2;
                    }
                    boolean z4 = idSignature2 == null ? z2 : false;
                    FirProperty firProperty2 = firProperty;
                    FirCallableDeclaration firCallableDeclaration = firProperty2;
                    while (true) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr2 == null) {
                            originalForSubstitutionOverrideAttr2 = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                        }
                        if (originalForSubstitutionOverrideAttr2 == null) {
                            break;
                        }
                        firCallableDeclaration = originalForSubstitutionOverrideAttr2;
                        z2 = z2;
                        irProperty2 = irProperty2;
                        firPropertyAccessor = firPropertyAccessor;
                    }
                    boolean z5 = z2;
                    IrProperty irProperty3 = irProperty2;
                    IrProperty irProperty4 = irProperty;
                    IrProperty irProperty5 = irProperty2;
                    FirPropertyAccessor firPropertyAccessor2 = firPropertyAccessor;
                    irPropertyCreateProperty$default.setGetter(fir2IrDeclarationStorage2.createIrPropertyAccessor(getter, firProperty, irProperty3, irType$default2, irDeclarationParent, irClass, false, irDeclarationOrigin, i4, i5, z4, coneClassLikeLookupTag2, ((FirProperty) firCallableDeclaration).getGetter()));
                    if (firProperty.getIsVar()) {
                        if (delegate != null) {
                            default_property_accessor = IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE;
                        } else {
                            if (Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin4, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) || !(firPropertyAccessor2 instanceof FirDefaultPropertySetter)) {
                                irDeclarationOrigin2 = irDeclarationOrigin4;
                            } else {
                                default_property_accessor = IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE;
                            }
                            if (idSignature2 == null) {
                                z3 = z5;
                            } else {
                                z3 = false;
                            }
                            while (true) {
                                if (!ClassMembersKt.isSubstitutionOverride(firProperty2) || (firProperty2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
                                    originalForSubstitutionOverrideAttr = ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2);
                                } else {
                                    originalForSubstitutionOverrideAttr = null;
                                }
                                if (originalForSubstitutionOverrideAttr == null) {
                                    if (ClassMembersKt.isIntersectionOverride(firProperty2)) {
                                        originalForSubstitutionOverrideAttr = ClassMembersKt.getOriginalForIntersectionOverrideAttr(firProperty2);
                                    } else {
                                        originalForSubstitutionOverrideAttr = null;
                                    }
                                }
                                if (originalForSubstitutionOverrideAttr == null) {
                                    break;
                                }
                                firProperty2 = originalForSubstitutionOverrideAttr;
                            }
                            fir2IrDeclarationStorage = fir2IrDeclarationStorage2;
                            irPropertyCreateProperty$default.setSetter(fir2IrDeclarationStorage.createIrPropertyAccessor(firPropertyAccessor2, firProperty, irProperty5, irType$default2, irDeclarationParent, irClass, true, irDeclarationOrigin2, i4, i5, z3, coneClassLikeLookupTag2, ((FirProperty) firProperty2).getSetter()));
                        }
                        irDeclarationOrigin2 = default_property_accessor;
                        if (idSignature2 == null) {
                            z3 = z5;
                        } else {
                            z3 = false;
                        }
                        while (true) {
                            if (ClassMembersKt.isSubstitutionOverride(firProperty2)) {
                                originalForSubstitutionOverrideAttr = ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2);
                            } else {
                                originalForSubstitutionOverrideAttr = ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2);
                            }
                            if (originalForSubstitutionOverrideAttr == null) {
                                if (ClassMembersKt.isIntersectionOverride(firProperty2)) {
                                    originalForSubstitutionOverrideAttr = ClassMembersKt.getOriginalForIntersectionOverrideAttr(firProperty2);
                                } else {
                                    originalForSubstitutionOverrideAttr = null;
                                }
                            }
                            if (originalForSubstitutionOverrideAttr == null) {
                                break;
                                break;
                            }
                            firProperty2 = originalForSubstitutionOverrideAttr;
                        }
                        fir2IrDeclarationStorage = fir2IrDeclarationStorage2;
                        irPropertyCreateProperty$default.setSetter(fir2IrDeclarationStorage.createIrPropertyAccessor(firPropertyAccessor2, firProperty, irProperty5, irType$default2, irDeclarationParent, irClass, true, irDeclarationOrigin2, i4, i5, z3, coneClassLikeLookupTag2, ((FirProperty) firProperty2).getSetter()));
                    } else {
                        fir2IrDeclarationStorage = fir2IrDeclarationStorage2;
                    }
                    fir2IrDeclarationStorage.leaveScope(irProperty4);
                    return irPropertyCreateProperty$default;
                }
            });
            if (isFakeOverride(property, coneClassLikeLookupTag)) {
                FirProperty firProperty = property;
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firProperty) || (firProperty.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firProperty) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firProperty) : null;
                    }
                    if (originalForSubstitutionOverrideAttr == null) {
                        break;
                    }
                    firProperty = originalForSubstitutionOverrideAttr;
                }
                FirPropertySymbol symbol = ((FirProperty) firProperty).getSymbol();
                if (coneClassLikeLookupTag == null) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(property);
                    Intrinsics.checkNotNull(coneClassLikeLookupTagContainingClassLookupTag);
                    coneClassLikeLookupTag = coneClassLikeLookupTagContainingClassLookupTag;
                }
                this.irFakeOverridesForFirFakeOverrideMap.put(new FakeOverrideIdentifier(symbol, coneClassLikeLookupTag), irPropertyDeclareIrProperty);
            } else {
                this.propertyCache.put(property, irPropertyDeclareIrProperty);
            }
            return irPropertyDeclareIrProperty;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirProperty firProperty2 = property;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firProperty2.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firProperty2);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    public final IrProperty getCachedIrProperty(final FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return getCachedIrProperty(property, null, new Function0<IdSignature>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.getCachedIrProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IdSignature invoke() {
                return FirBasedSignatureComposer.composeSignature$default(Fir2IrDeclarationStorage.this.getSignatureComposer(), property, null, false, 6, null);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0076 A[PHI: r7
      0x0076: PHI (r7v3 org.jetbrains.kotlin.ir.declarations.IrDeclaration) = 
      (r7v2 org.jetbrains.kotlin.ir.declarations.IrDeclaration)
      (r7v10 org.jetbrains.kotlin.ir.declarations.IrProperty)
      (r7v10 org.jetbrains.kotlin.ir.declarations.IrProperty)
      (r7v14 org.jetbrains.kotlin.ir.declarations.IrDeclaration)
     binds: [B:29:0x0074, B:44:0x00a5, B:45:0x00a7, B:34:0x0084] A[DONT_GENERATE, DONT_INLINE]] */
    public final IrProperty getCachedIrProperty(FirProperty property, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag, Function0<? extends IdSignature> signatureCalculator) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(signatureCalculator, "signatureCalculator");
        AbstractMap abstractMap = this.propertyCache;
        FirProperty firProperty = property;
        boolean zIsFakeOverride = isFakeOverride(firProperty, fakeOverrideOwnerLookupTag);
        IrProperty irProperty = null;
        if (zIsFakeOverride) {
            FirCallableDeclaration firCallableDeclaration = firProperty;
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firCallableDeclaration = originalForSubstitutionOverrideAttr;
            }
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
            if (fakeOverrideOwnerLookupTag == null) {
                fakeOverrideOwnerLookupTag = ClassMembersKt.containingClassLookupTag(firProperty);
                Intrinsics.checkNotNull(fakeOverrideOwnerLookupTag);
            }
            IrDeclaration irDeclaration = this.irFakeOverridesForFirFakeOverrideMap.get(new FakeOverrideIdentifier(symbol, fakeOverrideOwnerLookupTag));
            if (irDeclaration != null) {
                if (irDeclaration == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrProperty");
                }
                irProperty = (IrProperty) irDeclaration;
            }
            return (IrProperty) irProperty;
        }
        owner = (IrDeclaration) abstractMap.get(firProperty);
        if (owner != null) {
            irProperty = owner;
        }
        return (IrProperty) irProperty;
        if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firProperty) || (owner = (IrDeclaration) abstractMap.get(firProperty)) == null) {
            IdSignature idSignatureInvoke = signatureCalculator.invoke();
            if (idSignatureInvoke != null) {
                IrPropertySymbol irPropertySymbolReferencePropertyIfAny = getSymbolTable().referencePropertyIfAny(idSignatureInvoke);
                owner = irPropertySymbolReferencePropertyIfAny != null ? irPropertySymbolReferencePropertyIfAny.getOwner() : null;
                if (owner != null) {
                    if (!zIsFakeOverride) {
                        abstractMap.put(firProperty, owner);
                    }
                    irProperty = owner;
                }
            }
        } else {
            irProperty = owner;
        }
        return (IrProperty) irProperty;
    }

    public final void cacheDelegatedProperty$fir2ir(FirProperty property, IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        this.propertyCache.put(property, irProperty);
        this.delegatedReverseCache.put(irProperty, property);
    }

    public final void saveFakeOverrideInClass$fir2ir(IrClass irClass, FirCallableDeclaration originalDeclaration, FirCallableDeclaration fakeOverride) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
        Map<IrClass, Map<FakeOverrideKey, FirCallableDeclaration>> map = this.fakeOverridesInClass;
        LinkedHashMap linkedHashMap = map.get(irClass);
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            map.put(irClass, linkedHashMap);
        }
        linkedHashMap.put(asFakeOverrideKey(originalDeclaration), fakeOverride);
    }

    public final FirCallableDeclaration getFakeOverrideInClass(IrClass irClass, FirCallableDeclaration callableDeclaration) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        if (irClass instanceof Fir2IrLazyClass) {
            ((Fir2IrLazyClass) irClass).getFakeOverridesByName(callableDeclaration.getSymbol().getCallableId().getCallableName());
        }
        Map<FakeOverrideKey, FirCallableDeclaration> map = this.fakeOverridesInClass.get(irClass);
        if (map != null) {
            return map.get(asFakeOverrideKey(callableDeclaration));
        }
        return null;
    }

    public final IrField getCachedIrDelegateOrBackingField(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return this.fieldCache.get(field);
    }

    public final IrField getCachedIrFieldStaticFakeOverrideByDeclaration(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(field);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return null;
        }
        return this.fieldStaticOverrideCache.get(new FieldStaticOverrideKey(coneClassLikeLookupTagContainingClassLookupTag, field.getName()));
    }

    public final IrField createIrFieldAndDelegatedMembers(FirField field, FirClass owner, IrClass irClass) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        IrField orCreateDelegateIrField = getOrCreateDelegateIrField(field, owner, irClass);
        getDelegatedMemberGenerator().generate(orCreateDelegateIrField, field, owner, irClass);
        if (ControlFlowGraphBuilderKt.isLocalClassOrAnonymousObject(owner)) {
            getDelegatedMemberGenerator().generateBodies();
        }
        if (orCreateDelegateIrField.getCorrespondingPropertySymbol() == null) {
            return orCreateDelegateIrField;
        }
        return null;
    }

    private final IrField getOrCreateDelegateIrField(FirField field, FirClass owner, IrClass irClass) {
        FirTypeRef returnTypeRef;
        FirExpression initializer = field.getInitializer();
        if (initializer instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) initializer;
            if (firQualifiedAccessExpression.getExplicitReceiver() == null) {
                Object obj = null;
                FirValueParameterSymbol resolvedValueParameterSymbol$default = FirReferenceUtilsKt.toResolvedValueParameterSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
                if (resolvedValueParameterSymbol$default != null) {
                    Name name2 = resolvedValueParameterSymbol$default.getName();
                    List<FirDeclaration> declarations = owner.getDeclarations();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : declarations) {
                        if (obj2 instanceof FirProperty) {
                            arrayList.add(obj2);
                        }
                    }
                    for (Object obj3 : arrayList) {
                        FirProperty firProperty = (FirProperty) obj3;
                        if (Intrinsics.areEqual(firProperty.getName(), name2)) {
                            KtSourceElement source = firProperty.getSource();
                            if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind.PropertyFromParameter) {
                                obj = obj3;
                                break;
                            }
                        }
                    }
                    FirProperty firProperty2 = (FirProperty) obj;
                    if (firProperty2 != null) {
                        IrField backingField = getOrCreateIrProperty$default(this, firProperty2, irClass, false, 4, null).getBackingField();
                        Intrinsics.checkNotNull(backingField);
                        this.fieldCache.put(field, backingField);
                        return backingField;
                    }
                }
            }
        }
        IrClass irClass2 = irClass;
        if (initializer == null || (returnTypeRef = initializer.getTypeRef()) == null) {
            returnTypeRef = field.getReturnTypeRef();
        }
        IrField irFieldCreateIrField$fir2ir = createIrField$fir2ir(field, irClass2, returnTypeRef, IrDeclarationOrigin.DELEGATE.INSTANCE);
        irFieldCreateIrField$fir2ir.setMetadata(new FirMetadataSource.Field(field));
        return irFieldCreateIrField$fir2ir;
    }

    public static /* synthetic */ IrField createIrField$fir2ir$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirField firField, IrDeclarationParent irDeclarationParent, FirTypeRef firTypeRef, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            firTypeRef = firField.getReturnTypeRef();
        }
        if ((i & 8) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE;
        }
        return fir2IrDeclarationStorage.createIrField$fir2ir(firField, irDeclarationParent, firTypeRef, irDeclarationOrigin);
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0154, code lost:
    
        r15 = r15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [org.jetbrains.kotlin.ir.declarations.IrFactory] */
    /* JADX WARN: Type inference failed for: r18v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v20, types: [org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration] */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.jetbrains.kotlin.ir.declarations.IrField createIrField$fir2ir(final org.jetbrains.kotlin.fir.declarations.FirField r29, org.jetbrains.kotlin.ir.declarations.IrDeclarationParent r30, org.jetbrains.kotlin.fir.types.FirTypeRef r31, final org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin r32) {
        /*
            Method dump skipped, instruction units count: 439
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.createIrField$fir2ir(org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.ir.declarations.IrDeclarationParent, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin):org.jetbrains.kotlin.ir.declarations.IrField");
    }

    private final FieldStaticOverrideKey getFieldStaticFakeOverrideKey(FirField field, ConeClassLikeLookupTag ownerLookupTag) {
        if (ownerLookupTag == null || !field.getStatus().isStatic()) {
            return null;
        }
        FirField firField = field;
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firField) || !Intrinsics.areEqual(ownerLookupTag, ClassMembersKt.containingClassLookupTag(firField))) {
            return new FieldStaticOverrideKey(ownerLookupTag, field.getName());
        }
        return null;
    }

    public static /* synthetic */ IrValueParameter createIrParameter$fir2ir$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirValueParameter firValueParameter, int i, boolean z, ConversionTypeOrigin conversionTypeOrigin, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        int i3 = i;
        if ((i2 & 4) != 0) {
            z = true;
        }
        boolean z4 = z;
        if ((i2 & 8) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrDeclarationStorage.createIrParameter$fir2ir(firValueParameter, i3, z4, conversionTypeOrigin, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrValueParameter createIrParameter$fir2ir(FirValueParameter valueParameter, int index, boolean useStubForDefaultValueStub, ConversionTypeOrigin typeOrigin, boolean skipDefaultParameter, boolean forcedDefaultValueConversion) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        ConeKotlinType coneKotlinTypeArrayElementType;
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        try {
            int i = 1;
            IrExpressionBody irExpressionBodyCreateExpressionBody = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            IrDeclarationOrigin irDeclarationOriginComputeIrOrigin$default = ConversionUtilsKt.computeIrOrigin$default(valueParameter, null, 1, null);
            IrType irType = toIrType(valueParameter.getReturnTypeRef(), typeOrigin);
            KtSourceElement source = valueParameter.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                startOffset = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            IrValueParameter irValueParameterCreateValueParameter = getIrFactory().createValueParameter(startOffset, endOffset, irDeclarationOriginComputeIrOrigin$default, valueParameter.getName(), irType, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(objArr2 == true ? 1 : 0, i, objArr == true ? 1 : 0), index, (valueParameter.getIsVararg() && (coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(FirTypeUtilsKt.getConeType(valueParameter.getReturnTypeRef()))) != null) ? toIrType(coneKotlinTypeArrayElementType, typeOrigin) : null, valueParameter.getIsCrossinline(), valueParameter.getIsNoinline(), false);
            FirExpression defaultValue = valueParameter.getDefaultValue();
            if (!skipDefaultParameter && defaultValue != null) {
                if (forcedDefaultValueConversion && !(defaultValue instanceof FirExpressionStub)) {
                    irExpressionBodyCreateExpressionBody = ConversionUtilsKt.asCompileTimeIrInitializer(defaultValue, this.components);
                } else if (useStubForDefaultValueStub || !(defaultValue instanceof FirExpressionStub)) {
                    irExpressionBodyCreateExpressionBody = IrFactoryHelpersKt.createExpressionBody(irValueParameterCreateValueParameter.getFactory(), new IrErrorExpressionImpl(-1, -1, irType, "Stub expression for default value of " + valueParameter.getName()));
                }
                irValueParameterCreateValueParameter.setDefaultValue(irExpressionBodyCreateExpressionBody);
            }
            getAnnotationGenerator().generate(irValueParameterCreateValueParameter, valueParameter);
            IrValueParameter irValueParameter = irValueParameterCreateValueParameter;
            getLocalStorage().putParameter(valueParameter, irValueParameter);
            return irValueParameter;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirValueParameter firValueParameter = valueParameter;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firValueParameter.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firValueParameter);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrValueParameter createIrParameterFromContextReceiver(FirContextReceiver contextReceiver, int index) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        try {
            int i = 1;
            ParameterDescriptor parameterDescriptor = null;
            Object[] objArr = 0;
            try {
                IrType irType$default = toIrType$default(this, contextReceiver.getTypeRef(), null, 1, null);
                KtSourceElement source = contextReceiver.getSource();
                int i2 = -1;
                if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                    endOffset = -1;
                } else {
                    if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                        startOffset = source != null ? source.getStartOffset() : -1;
                    } else {
                        startOffset = numStartOffsetSkippingComments.intValue();
                    }
                    endOffset = source != null ? source.getEndOffset() : -1;
                    i2 = startOffset;
                }
                return getIrFactory().createValueParameter(i2, endOffset, (IrDeclarationOrigin) IrDeclarationOrigin.DEFINED.INSTANCE, NameUtils.contextReceiverName(index), irType$default, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(parameterDescriptor, i, objArr == true ? 1 : 0), index, (IrType) null, false, false, false);
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Exception e2) {
                e = e2;
                FirContextReceiver firContextReceiver = contextReceiver;
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firContextReceiver.getClass(), e);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firContextReceiver);
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
        } catch (ProcessCanceledException e3) {
            throw e3;
        } catch (Exception e4) {
            e = e4;
        }
    }

    private final int nextTemporaryIndex() {
        int i = this.lastTemporaryIndex;
        this.lastTemporaryIndex = i + 1;
        return i;
    }

    private final String getNameForTemporary(String nameHint) {
        int iNextTemporaryIndex = nextTemporaryIndex();
        return (nameHint != null ? new StringBuilder(Constants.TMP_DIRECTORY_NAME).append(iNextTemporaryIndex).append('_').append(nameHint) : new StringBuilder(Constants.TMP_DIRECTORY_NAME).append(iNextTemporaryIndex)).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrVariable declareIrVariable(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrType type, boolean isVar, boolean isConst, boolean isLateinit) {
        return new IrVariableImpl(startOffset, endOffset, origin, new IrVariableSymbolImpl(null, 1, 0 == true ? 1 : 0), name2, type, isVar, isConst, isLateinit);
    }

    public static /* synthetic */ IrVariable createIrVariable$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirVariable firVariable, IrDeclarationParent irDeclarationParent, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = null;
        }
        return fir2IrDeclarationStorage.createIrVariable(firVariable, irDeclarationParent, irDeclarationOrigin);
    }

    public final IrVariable createIrVariable(FirVariable variable, IrDeclarationParent irParent, IrDeclarationOrigin givenOrigin) {
        FirTypeRef returnTypeRef;
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        try {
            FirExpression initializer = variable.getInitializer();
            FirComponentCall firComponentCall = initializer instanceof FirComponentCall ? (FirComponentCall) initializer : null;
            if (firComponentCall == null || (returnTypeRef = firComponentCall.getTypeRef()) == null) {
                returnTypeRef = variable.getReturnTypeRef();
            }
            IrType irType$default = toIrType$default(this, returnTypeRef, null, 1, null);
            if (givenOrigin == null) {
                if (Intrinsics.areEqual(variable.getName(), SpecialNames.ITERATOR)) {
                    givenOrigin = IrDeclarationOrigin.FOR_LOOP_ITERATOR.INSTANCE;
                } else {
                    givenOrigin = variable.getName().isSpecial() ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : IrDeclarationOrigin.DEFINED.INSTANCE;
                }
            }
            IrDeclarationOrigin irDeclarationOrigin = givenOrigin;
            boolean zIsLateInit = variable instanceof FirProperty ? variable.getStatus().isLateInit() : false;
            KtSourceElement source = variable.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            IrVariable irVariableDeclareIrVariable = declareIrVariable(i, endOffset, irDeclarationOrigin, variable.getName(), irType$default, variable.getIsVar(), false, zIsLateInit);
            irVariableDeclareIrVariable.setParent(irParent);
            getLocalStorage().putVariable(variable, irVariableDeclareIrVariable);
            return irVariableDeclareIrVariable;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            FirVariable firVariable = variable;
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firVariable.getClass(), e2);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firVariable);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrLocalDelegatedProperty createIrLocalDelegatedProperty(FirProperty property, IrDeclarationParent irParent) {
        FirProperty firProperty;
        int startOffset;
        Integer numStartOffsetSkippingComments;
        IrLocalDelegatedProperty irLocalDelegatedProperty;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        try {
            int i = 1;
            VariableDescriptorWithAccessors variableDescriptorWithAccessors = null;
            Object[] objArr = 0;
            IrType irType$default = toIrType$default(this, property.getReturnTypeRef(), null, 1, null);
            IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
            KtSourceElement source = property.getSource();
            int endOffset = -1;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                startOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                if (source != null) {
                    endOffset = source.getEndOffset();
                }
            }
            IrLocalDelegatedProperty irLocalDelegatedPropertyCreateLocalDelegatedProperty = getIrFactory().createLocalDelegatedProperty(startOffset, endOffset, defined, property.getName(), new IrLocalDelegatedPropertySymbolImpl(variableDescriptorWithAccessors, i, objArr == true ? 1 : 0), irType$default, property.getIsVar());
            IrLocalDelegatedProperty irLocalDelegatedProperty2 = irLocalDelegatedPropertyCreateLocalDelegatedProperty;
            irLocalDelegatedProperty2.setParent(irParent);
            irLocalDelegatedProperty2.setMetadata(new FirMetadataSource.Property(property));
            enterScope(irLocalDelegatedProperty2);
            int startOffset2 = irLocalDelegatedProperty2.getStartOffset();
            int endOffset2 = irLocalDelegatedProperty2.getEndOffset();
            IrDeclarationOrigin.PROPERTY_DELEGATE property_delegate = IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE;
            Name namePropertyDelegateName = NameUtils.propertyDelegateName(property.getName());
            FirExpression delegate = property.getDelegate();
            Intrinsics.checkNotNull(delegate);
            irLocalDelegatedProperty2.setDelegate(declareIrVariable(startOffset2, endOffset2, property_delegate, namePropertyDelegateName, toIrType$default(this, delegate.getTypeRef(), null, 1, null), false, false, false));
            irLocalDelegatedProperty2.getDelegate().setParent(irParent);
            try {
                irLocalDelegatedProperty2.setGetter(createIrPropertyAccessor$default(this, property.getGetter(), property, irLocalDelegatedProperty2, irType$default, irParent, null, false, IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, irLocalDelegatedProperty2.getStartOffset(), irLocalDelegatedProperty2.getEndOffset(), true, null, null, GL30.GL_COLOR, null));
                if (property.getIsVar()) {
                    irLocalDelegatedProperty = irLocalDelegatedProperty2;
                    irLocalDelegatedProperty.setSetter(createIrPropertyAccessor$default(this, property.getSetter(), property, irLocalDelegatedProperty2, irType$default, irParent, null, true, IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, irLocalDelegatedProperty2.getStartOffset(), irLocalDelegatedProperty2.getEndOffset(), true, null, null, GL30.GL_COLOR, null));
                } else {
                    irLocalDelegatedProperty = irLocalDelegatedProperty2;
                }
                try {
                    try {
                        leaveScope(irLocalDelegatedProperty);
                        IrLocalDelegatedProperty irLocalDelegatedProperty3 = irLocalDelegatedPropertyCreateLocalDelegatedProperty;
                        firProperty = property;
                        try {
                            getLocalStorage().putDelegatedProperty(firProperty, irLocalDelegatedProperty3);
                            return irLocalDelegatedProperty3;
                        } catch (Exception e) {
                            e = e;
                            FirProperty firProperty2 = firProperty;
                            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firProperty2.getClass(), e);
                            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firProperty2);
                            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                            throw kotlinIllegalArgumentExceptionWithAttachments;
                        }
                    } catch (ProcessCanceledException e2) {
                        throw e2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    firProperty = property;
                    FirProperty firProperty3 = firProperty;
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = new KotlinIllegalArgumentExceptionWithAttachments("Exception was thrown during transformation of " + firProperty3.getClass(), e);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder2 = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder2, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, firProperty3);
                    kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder2.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments2;
                }
            } catch (ProcessCanceledException e4) {
                throw e4;
            } catch (Exception e5) {
                e = e5;
            }
        } catch (ProcessCanceledException e6) {
            throw e6;
        } catch (Exception e7) {
            e = e7;
            firProperty = property;
        }
    }

    public static /* synthetic */ IrVariable declareTemporaryVariable$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, IrExpression irExpression, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return fir2IrDeclarationStorage.declareTemporaryVariable(irExpression, str);
    }

    public final IrVariable declareTemporaryVariable(IrExpression base, String nameHint) {
        Intrinsics.checkNotNullParameter(base, "base");
        int startOffset = base.getStartOffset();
        int endOffset = base.getEndOffset();
        IrDeclarationOrigin.IR_TEMPORARY_VARIABLE ir_temporary_variable = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        Name nameIdentifier = Name.identifier(getNameForTemporary(nameHint));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableDeclareIrVariable = declareIrVariable(startOffset, endOffset, ir_temporary_variable, nameIdentifier, base.getType(), false, false, false);
        irVariableDeclareIrVariable.setInitializer(base);
        return irVariableDeclareIrVariable;
    }

    /* JADX WARN: Code duplicated, block: B:90:0x01ea A[Catch: all -> 0x0222, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x003d, B:8:0x004f, B:11:0x0056, B:13:0x005e, B:16:0x0064, B:19:0x006e, B:21:0x007d, B:23:0x0085, B:24:0x008d, B:26:0x0093, B:39:0x00cf, B:30:0x00b1, B:32:0x00ba, B:37:0x00c9, B:34:0x00c1, B:90:0x01ea, B:42:0x010e, B:45:0x0113, B:47:0x0119, B:53:0x012d, B:55:0x0133, B:59:0x013d, B:61:0x0142, B:64:0x0153, B:66:0x0157, B:51:0x0127, B:70:0x0166, B:72:0x016a, B:74:0x0170, B:87:0x01ac, B:78:0x018e, B:80:0x0197, B:85:0x01a6, B:82:0x019e, B:18:0x006a), top: B:99:0x003d }] */
    /* JADX WARN: Multi-variable type inference failed */
    public final IrConstructorSymbol getIrConstructorSymbol(FirConstructorSymbol firConstructorSymbol) {
        IrDeclarationOrigin.DEFINED origin;
        IdSignature idSignatureM6783getIrCallableSymbol$lambda87;
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        IrFunctionSymbol symbol;
        int startOffset2;
        final int i2;
        final int endOffset2;
        Integer numStartOffsetSkippingComments2;
        Intrinsics.checkNotNullParameter(firConstructorSymbol, "firConstructorSymbol");
        final FirConstructor firConstructor = (FirConstructor) firConstructorSymbol.getFir();
        D fir = firConstructorSymbol.getFir();
        if (fir == 0) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirConstructor");
        }
        FirConstructor firConstructor2 = (FirConstructor) fir;
        Lazy lazy = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$irParent$2(this, firConstructor2));
        Lazy lazy2 = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$signature$2(this, firConstructor2, null));
        synchronized (getSymbolTable().getLock()) {
            IrConstructor cachedIrConstructor = getCachedIrConstructor(firConstructor2, new Fir2IrDeclarationStorage$getIrCallableSymbol$1$2(lazy, lazy2));
            if (cachedIrConstructor != null) {
                symbol = cachedIrConstructor.getSymbol();
            } else {
                IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda86 = m6782getIrCallableSymbol$lambda86(lazy);
                IrDeclaration irDeclaration = irDeclarationParentM6782getIrCallableSymbol$lambda86 instanceof IrDeclaration ? (IrDeclaration) irDeclarationParentM6782getIrCallableSymbol$lambda86 : null;
                if (irDeclaration == null || (origin = irDeclaration.getOrigin()) == null) {
                    origin = IrDeclarationOrigin.DEFINED.INSTANCE;
                }
                final IrDeclarationOrigin irDeclarationOriginComputeDeclarationOrigin = computeDeclarationOrigin(firConstructorSymbol, origin);
                final IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda87 = m6782getIrCallableSymbol$lambda86(lazy);
                if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof Fir2IrLazyClass) {
                    if (Intrinsics.areEqual(origin, IrDeclarationOrigin.DEFINED.INSTANCE)) {
                        getConfiguration().getAllowNonCachedDeclarations();
                    }
                    IdSignature idSignatureM6783getIrCallableSymbol$lambda88 = m6783getIrCallableSymbol$lambda87(lazy2);
                    if (idSignatureM6783getIrCallableSymbol$lambda88 != null) {
                        final Fir2IrConstructorSymbol fir2IrConstructorSymbol = new Fir2IrConstructorSymbol(idSignatureM6783getIrCallableSymbol$lambda88);
                        KtSourceElement source = firConstructor.getSource();
                        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                            i2 = -1;
                            endOffset2 = -1;
                        } else {
                            if (source == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                                startOffset2 = source != null ? source.getStartOffset() : -1;
                            } else {
                                startOffset2 = numStartOffsetSkippingComments2.intValue();
                            }
                            i2 = startOffset2;
                            endOffset2 = source != null ? source.getEndOffset() : -1;
                        }
                        IrConstructor irConstructorDeclareConstructor = getSymbolTable().declareConstructor(idSignatureM6783getIrCallableSymbol$lambda88, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getIrConstructorSymbol$3$irConstructor$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final IrConstructorSymbol invoke() {
                                return fir2IrConstructorSymbol;
                            }
                        }, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getIrConstructorSymbol$3$irConstructor$1$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final IrConstructor invoke(IrConstructorSymbol it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                Fir2IrLazyConstructor fir2IrLazyConstructor = new Fir2IrLazyConstructor(this.this$0.components, i2, endOffset2, irDeclarationOriginComputeDeclarationOrigin, firConstructor, fir2IrConstructorSymbol);
                                fir2IrLazyConstructor.setParent(irDeclarationParentM6782getIrCallableSymbol$lambda87);
                                return fir2IrLazyConstructor;
                            }
                        });
                        this.constructorCache.put(firConstructor, irConstructorDeclareConstructor);
                        Intrinsics.checkNotNull(irConstructorDeclareConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor");
                        ((Fir2IrLazyConstructor) irConstructorDeclareConstructor).prepareTypeParameters();
                        symbol = irConstructorDeclareConstructor.getSymbol();
                    } else {
                        IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda88 = m6782getIrCallableSymbol$lambda86(lazy);
                        Intrinsics.checkNotNull(irDeclarationParentM6782getIrCallableSymbol$lambda88, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                        IrConstructor irConstructorCreateIrConstructor$default = createIrConstructor$default(this, firConstructor, (IrClass) irDeclarationParentM6782getIrCallableSymbol$lambda88, irDeclarationOriginComputeDeclarationOrigin, false, 8, null);
                        IrConstructor irConstructor = irConstructorCreateIrConstructor$default;
                        Intrinsics.checkNotNull(irConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irConstructor, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irConstructorCreateIrConstructor$default.getSymbol();
                    }
                } else if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrLazyClass) {
                    FirCallableDeclaration firCallableDeclaration = firConstructor2;
                    while (true) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr == null) {
                            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                        }
                        if (originalForSubstitutionOverrideAttr == null) {
                            break;
                        }
                        firCallableDeclaration = originalForSubstitutionOverrideAttr;
                    }
                    if (firCallableDeclaration == firConstructor2) {
                        IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda89 = m6782getIrCallableSymbol$lambda86(lazy);
                        Intrinsics.checkNotNull(irDeclarationParentM6782getIrCallableSymbol$lambda89, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                        IrConstructor irConstructorCreateIrConstructor$default2 = createIrConstructor$default(this, firConstructor, (IrClass) irDeclarationParentM6782getIrCallableSymbol$lambda89, irDeclarationOriginComputeDeclarationOrigin, false, 8, null);
                        IrConstructor irConstructor2 = irConstructorCreateIrConstructor$default2;
                        Intrinsics.checkNotNull(irConstructor2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irConstructor2, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irConstructorCreateIrConstructor$default2.getSymbol();
                    } else if (firCallableDeclaration instanceof FirSimpleFunction) {
                        symbol = getIrFunctionSymbol$default(this, ((FirSimpleFunction) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else if (firCallableDeclaration instanceof FirProperty) {
                        symbol = getIrPropertySymbol$default(this, ((FirProperty) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else {
                        IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda810 = m6782getIrCallableSymbol$lambda86(lazy);
                        Intrinsics.checkNotNull(irDeclarationParentM6782getIrCallableSymbol$lambda810, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                        IrConstructor irConstructorCreateIrConstructor$default3 = createIrConstructor$default(this, firConstructor, (IrClass) irDeclarationParentM6782getIrCallableSymbol$lambda810, irDeclarationOriginComputeDeclarationOrigin, false, 8, null);
                        IrConstructor irConstructor3 = irConstructorCreateIrConstructor$default3;
                        Intrinsics.checkNotNull(irConstructor3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irConstructor3, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irConstructorCreateIrConstructor$default3.getSymbol();
                    }
                } else if (!(irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrExternalPackageFragment) || (idSignatureM6783getIrCallableSymbol$lambda87 = m6783getIrCallableSymbol$lambda87(lazy2)) == null) {
                    IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda811 = m6782getIrCallableSymbol$lambda86(lazy);
                    Intrinsics.checkNotNull(irDeclarationParentM6782getIrCallableSymbol$lambda811, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    IrConstructor irConstructorCreateIrConstructor$default4 = createIrConstructor$default(this, firConstructor, (IrClass) irDeclarationParentM6782getIrCallableSymbol$lambda811, irDeclarationOriginComputeDeclarationOrigin, false, 8, null);
                    IrConstructor irConstructor4 = irConstructorCreateIrConstructor$default4;
                    Intrinsics.checkNotNull(irConstructor4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                    setAndModifyParent(irConstructor4, m6782getIrCallableSymbol$lambda86(lazy));
                    symbol = irConstructorCreateIrConstructor$default4.getSymbol();
                } else {
                    final Fir2IrConstructorSymbol fir2IrConstructorSymbol2 = new Fir2IrConstructorSymbol(idSignatureM6783getIrCallableSymbol$lambda87);
                    KtSourceElement source2 = firConstructor.getSource();
                    if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source2))) {
                        i = -1;
                        endOffset = -1;
                    } else {
                        if (source2 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source2)) == null) {
                            startOffset = source2 != null ? source2.getStartOffset() : -1;
                        } else {
                            startOffset = numStartOffsetSkippingComments.intValue();
                        }
                        i = startOffset;
                        endOffset = source2 != null ? source2.getEndOffset() : -1;
                    }
                    IrConstructor irConstructorDeclareConstructor2 = getSymbolTable().declareConstructor(idSignatureM6783getIrCallableSymbol$lambda87, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getIrConstructorSymbol$3$irConstructor$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrConstructorSymbol invoke() {
                            return fir2IrConstructorSymbol2;
                        }
                    }, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getIrConstructorSymbol$3$irConstructor$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrConstructor invoke(IrConstructorSymbol it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            Fir2IrLazyConstructor fir2IrLazyConstructor = new Fir2IrLazyConstructor(this.this$0.components, i, endOffset, irDeclarationOriginComputeDeclarationOrigin, firConstructor, fir2IrConstructorSymbol2);
                            fir2IrLazyConstructor.setParent(irDeclarationParentM6782getIrCallableSymbol$lambda87);
                            return fir2IrLazyConstructor;
                        }
                    });
                    this.constructorCache.put(firConstructor, irConstructorDeclareConstructor2);
                    Intrinsics.checkNotNull(irConstructorDeclareConstructor2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.lazy.Fir2IrLazyConstructor");
                    ((Fir2IrLazyConstructor) irConstructorDeclareConstructor2).prepareTypeParameters();
                    symbol = irConstructorDeclareConstructor2.getSymbol();
                }
            }
        }
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
        return (IrConstructorSymbol) symbol;
    }

    public static /* synthetic */ IrFunctionSymbol getIrFunctionSymbol$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirFunctionSymbol firFunctionSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 2) != 0) {
            coneClassLikeLookupTag = null;
        }
        return fir2IrDeclarationStorage.getIrFunctionSymbol(firFunctionSymbol, coneClassLikeLookupTag);
    }

    /* JADX WARN: Code duplicated, block: B:87:0x0178 A[Catch: all -> 0x022a, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:25:0x009d, B:29:0x00a4, B:31:0x00b6, B:34:0x00bd, B:36:0x00c5, B:39:0x00cb, B:42:0x00d5, B:44:0x00e3, B:46:0x00eb, B:47:0x00f3, B:49:0x00f9, B:87:0x0178, B:52:0x0109, B:55:0x010e, B:57:0x0114, B:63:0x0128, B:65:0x012e, B:69:0x0138, B:71:0x013d, B:74:0x014d, B:76:0x0151, B:61:0x0122, B:80:0x015f, B:82:0x0163, B:84:0x0169, B:41:0x00d1), top: B:124:0x009d }] */
    public final IrFunctionSymbol getIrFunctionSymbol(FirFunctionSymbol<?> firFunctionSymbol, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        IrDeclarationOrigin.DEFINED origin;
        IdSignature idSignatureM6783getIrCallableSymbol$lambda87;
        IrFunctionSymbol symbol;
        Object next;
        IrDeclarationOrigin.DEFINED origin2;
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "firFunctionSymbol");
        FirFunction firFunction = (FirFunction) firFunctionSymbol.getFir();
        if (firFunction instanceof FirAnonymousFunction) {
            IrSimpleFunction cachedIrFunction = getCachedIrFunction(firFunction);
            if (cachedIrFunction != null) {
                return cachedIrFunction.getSymbol();
            }
            IrDeclarationParent irDeclarationParentFindIrParent$fir2ir = findIrParent$fir2ir(firFunction);
            IrDeclaration irDeclaration = irDeclarationParentFindIrParent$fir2ir instanceof IrDeclaration ? (IrDeclaration) irDeclarationParentFindIrParent$fir2ir : null;
            if (irDeclaration == null || (origin2 = irDeclaration.getOrigin()) == null) {
                origin2 = IrDeclarationOrigin.DEFINED.INSTANCE;
            }
            return createIrFunction$default(this, firFunction, irDeclarationParentFindIrParent$fir2ir, null, computeDeclarationOrigin(firFunctionSymbol, origin2), false, null, 52, null).getSymbol();
        }
        if (!(firFunction instanceof FirSimpleFunction)) {
            if (firFunction instanceof FirConstructor) {
                return getIrConstructorSymbol(((FirConstructor) firFunction).getSymbol());
            }
            throw new IllegalStateException(("Unknown kind of function: " + firFunction.getClass() + ": " + UtilsKt.render(firFunction)).toString());
        }
        FirFunctionSymbol<?> firFunctionSymbol2 = firFunctionSymbol;
        boolean zAreEqual = Intrinsics.areEqual(fakeOverrideOwnerLookupTag, ClassMembersKt.containingClassLookupTag(firFunctionSymbol2));
        if (!zAreEqual) {
            generateLazyFakeOverrides(((FirSimpleFunction) firFunction).getName(), fakeOverrideOwnerLookupTag);
        }
        Object fir = firFunctionSymbol2.getFir();
        if (fir == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirSimpleFunction");
        }
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) fir;
        Lazy lazy = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$irParent$2(this, firSimpleFunction));
        Lazy lazy2 = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$signature$2(this, firSimpleFunction, fakeOverrideOwnerLookupTag));
        synchronized (getSymbolTable().getLock()) {
            IrSimpleFunction cachedIrFunction2 = getCachedIrFunction(firSimpleFunction, !(fakeOverrideOwnerLookupTag instanceof ConeClassLookupTagWithFixedSymbol) ? fakeOverrideOwnerLookupTag : null, new Fir2IrDeclarationStorage$getIrCallableSymbol$1$2(lazy, lazy2));
            if (cachedIrFunction2 != null) {
                symbol = cachedIrFunction2.getSymbol();
            } else {
                IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda86 = m6782getIrCallableSymbol$lambda86(lazy);
                IrDeclaration irDeclaration2 = irDeclarationParentM6782getIrCallableSymbol$lambda86 instanceof IrDeclaration ? (IrDeclaration) irDeclarationParentM6782getIrCallableSymbol$lambda86 : null;
                if (irDeclaration2 == null || (origin = irDeclaration2.getOrigin()) == null) {
                    origin = IrDeclarationOrigin.DEFINED.INSTANCE;
                }
                IrDeclarationOrigin irDeclarationOriginComputeDeclarationOrigin = computeDeclarationOrigin(firFunctionSymbol, origin);
                IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda87 = m6782getIrCallableSymbol$lambda86(lazy);
                if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof Fir2IrLazyClass) {
                    if (Intrinsics.areEqual(origin, IrDeclarationOrigin.DEFINED.INSTANCE)) {
                        getConfiguration().getAllowNonCachedDeclarations();
                    }
                    IdSignature idSignatureM6783getIrCallableSymbol$lambda88 = m6783getIrCallableSymbol$lambda87(lazy2);
                    if (idSignatureM6783getIrCallableSymbol$lambda88 != null) {
                        symbol = createIrLazyFunction((FirSimpleFunction) firFunction, idSignatureM6783getIrCallableSymbol$lambda88, irDeclarationParentM6782getIrCallableSymbol$lambda87, irDeclarationOriginComputeDeclarationOrigin).getSymbol();
                    } else {
                        IrSimpleFunction irSimpleFunctionCreateIrFunction$default = createIrFunction$default(this, firFunction, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, fakeOverrideOwnerLookupTag, 20, null);
                        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateIrFunction$default;
                        Intrinsics.checkNotNull(irSimpleFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irSimpleFunction, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irSimpleFunctionCreateIrFunction$default.getSymbol();
                    }
                } else if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrLazyClass) {
                    FirCallableDeclaration firCallableDeclaration = firSimpleFunction;
                    while (true) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr == null) {
                            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                        }
                        if (originalForSubstitutionOverrideAttr == null) {
                            break;
                        }
                        firCallableDeclaration = originalForSubstitutionOverrideAttr;
                    }
                    if (firCallableDeclaration == firSimpleFunction) {
                        IrSimpleFunction irSimpleFunctionCreateIrFunction$default2 = createIrFunction$default(this, firFunction, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, fakeOverrideOwnerLookupTag, 20, null);
                        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionCreateIrFunction$default2;
                        Intrinsics.checkNotNull(irSimpleFunction2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irSimpleFunction2, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irSimpleFunctionCreateIrFunction$default2.getSymbol();
                    } else if (firCallableDeclaration instanceof FirSimpleFunction) {
                        symbol = getIrFunctionSymbol$default(this, ((FirSimpleFunction) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else if (firCallableDeclaration instanceof FirProperty) {
                        symbol = getIrPropertySymbol$default(this, ((FirProperty) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else {
                        IrSimpleFunction irSimpleFunctionCreateIrFunction$default3 = createIrFunction$default(this, firFunction, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, fakeOverrideOwnerLookupTag, 20, null);
                        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionCreateIrFunction$default3;
                        Intrinsics.checkNotNull(irSimpleFunction3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        setAndModifyParent(irSimpleFunction3, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irSimpleFunctionCreateIrFunction$default3.getSymbol();
                    }
                } else if (!(irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrExternalPackageFragment) || (idSignatureM6783getIrCallableSymbol$lambda87 = m6783getIrCallableSymbol$lambda87(lazy2)) == null) {
                    IrSimpleFunction irSimpleFunctionCreateIrFunction$default4 = createIrFunction$default(this, firFunction, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, fakeOverrideOwnerLookupTag, 20, null);
                    IrSimpleFunction irSimpleFunction4 = irSimpleFunctionCreateIrFunction$default4;
                    Intrinsics.checkNotNull(irSimpleFunction4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                    setAndModifyParent(irSimpleFunction4, m6782getIrCallableSymbol$lambda86(lazy));
                    symbol = irSimpleFunctionCreateIrFunction$default4.getSymbol();
                } else {
                    symbol = createIrLazyFunction((FirSimpleFunction) firFunction, idSignatureM6783getIrCallableSymbol$lambda87, irDeclarationParentM6782getIrCallableSymbol$lambda87, irDeclarationOriginComputeDeclarationOrigin).getSymbol();
                }
            }
        }
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol");
        IrFunctionSymbol irFunctionSymbol = (IrFunctionSymbol) symbol;
        if (zAreEqual || !(fakeOverrideOwnerLookupTag instanceof ConeClassLookupTagWithFixedSymbol)) {
            return irFunctionSymbol;
        }
        IrFunction owner = irFunctionSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrSimpleFunction irSimpleFunction5 = (IrSimpleFunction) owner;
        Name name2 = ((FirSimpleFunction) firFunction).getName();
        Fir2IrClassifierStorage classifierStorage = getClassifierStorage();
        FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(fakeOverrideOwnerLookupTag, getSession());
        Intrinsics.checkNotNull(symbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol<*>");
        Iterator<T> it = classifierStorage.getIrClassSymbol((FirClassSymbol) symbol2).getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration3 = (IrDeclaration) next;
            if (irDeclaration3 instanceof IrOverridableDeclaration) {
                IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) irDeclaration3;
                if (irOverridableDeclaration.getIsFakeOverride() && Intrinsics.areEqual(irOverridableDeclaration.getName(), name2) && AdditionalIrUtilsKt.overrides(irOverridableDeclaration, irSimpleFunction5)) {
                    break;
                }
            }
        }
        IrDeclaration irDeclaration4 = (IrDeclaration) next;
        IrFunctionSymbol irFunctionSymbol2 = (IrFunctionSymbol) (irDeclaration4 != null ? irDeclaration4.getSymbol() : null);
        return irFunctionSymbol2 == null ? irFunctionSymbol : irFunctionSymbol2;
    }

    private final IrSimpleFunction createIrLazyFunction(final FirSimpleFunction fir, IdSignature signature, final IrDeclarationParent lazyParent, final IrDeclarationOrigin declarationOrigin) {
        int startOffset;
        final int i;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        final IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = getSymbolTable().referenceSimpleFunction(signature);
        KtSourceElement source = fir.getSource();
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
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = getSymbolTable().declareSimpleFunction(signature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrLazyFunction$irFunction$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                return irSimpleFunctionSymbolReferenceSimpleFunction;
            }
        }, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrLazyFunction$irFunction$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                boolean zIsSubstitutionOrIntersectionOverride = ClassMembersKt.isSubstitutionOrIntersectionOverride(fir);
                Fir2IrComponents fir2IrComponents = this.components;
                int i2 = i;
                int i3 = endOffset;
                IrDeclarationOrigin irDeclarationOrigin = declarationOrigin;
                FirSimpleFunction firSimpleFunction = fir;
                IrDeclarationParent irDeclarationParent = lazyParent;
                Fir2IrLazyClass fir2IrLazyClass = irDeclarationParent instanceof Fir2IrLazyClass ? (Fir2IrLazyClass) irDeclarationParent : null;
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction = new Fir2IrLazySimpleFunction(fir2IrComponents, i2, i3, irDeclarationOrigin, firSimpleFunction, fir2IrLazyClass != null ? fir2IrLazyClass.getFir() : null, irSimpleFunctionSymbolReferenceSimpleFunction, zIsSubstitutionOrIntersectionOverride);
                fir2IrLazySimpleFunction.setParent(lazyParent);
                return fir2IrLazySimpleFunction;
            }
        });
        this.functionCache.put(fir, irSimpleFunctionDeclareSimpleFunction);
        Intrinsics.checkNotNull(irSimpleFunctionDeclareSimpleFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction");
        ((Fir2IrLazySimpleFunction) irSimpleFunctionDeclareSimpleFunction).prepareTypeParameters();
        return irSimpleFunctionDeclareSimpleFunction;
    }

    public static /* synthetic */ IrSymbol getIrPropertySymbol$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirPropertySymbol firPropertySymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 2) != 0) {
            coneClassLikeLookupTag = null;
        }
        return fir2IrDeclarationStorage.getIrPropertySymbol(firPropertySymbol, coneClassLikeLookupTag);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSymbol getIrPropertySymbol(FirPropertySymbol firPropertySymbol, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        Object next;
        IrSymbol irVariableSymbol;
        Intrinsics.checkNotNullParameter(firPropertySymbol, "firPropertySymbol");
        FirProperty firProperty = (FirProperty) firPropertySymbol.getFir();
        if (firProperty.isLocal()) {
            IrLocalDelegatedProperty delegatedProperty = getLocalStorage().getDelegatedProperty(firProperty);
            if (delegatedProperty == null || (irVariableSymbol = delegatedProperty.getSymbol()) == null) {
                irVariableSymbol = getIrVariableSymbol(firProperty);
            }
            return irVariableSymbol;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firPropertySymbol);
        boolean zAreEqual = Intrinsics.areEqual(fakeOverrideOwnerLookupTag, coneClassLikeLookupTagContainingClassLookupTag);
        if (!zAreEqual) {
            generateLazyFakeOverrides(firProperty.getName(), fakeOverrideOwnerLookupTag);
        }
        IrSymbol irPropertySymbol$getIrCallableSymbol = getIrPropertySymbol$getIrCallableSymbol(fakeOverrideOwnerLookupTag, this, firPropertySymbol, firProperty, fakeOverrideOwnerLookupTag);
        IrSymbolOwner owner = irPropertySymbol$getIrCallableSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrProperty");
        IrProperty irProperty = (IrProperty) owner;
        if (fakeOverrideOwnerLookupTag != null && (firPropertySymbol instanceof FirSyntheticPropertySymbol) && getIrPropertySymbol$isIllegalFakeOverride(irProperty)) {
            return getIrPropertySymbol$getIrCallableSymbol(coneClassLikeLookupTagContainingClassLookupTag, this, firPropertySymbol, firProperty, fakeOverrideOwnerLookupTag);
        }
        if (zAreEqual || !(fakeOverrideOwnerLookupTag instanceof ConeClassLookupTagWithFixedSymbol)) {
            return irPropertySymbol$getIrCallableSymbol;
        }
        Name name2 = firProperty.getName();
        Fir2IrClassifierStorage classifierStorage = getClassifierStorage();
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(fakeOverrideOwnerLookupTag, getSession());
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol<*>");
        Iterator<T> it = classifierStorage.getIrClassSymbol((FirClassSymbol) symbol).getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if (irDeclaration instanceof IrOverridableDeclaration) {
                IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) irDeclaration;
                if (irOverridableDeclaration.getIsFakeOverride() && Intrinsics.areEqual(irOverridableDeclaration.getName(), name2) && AdditionalIrUtilsKt.overrides(irOverridableDeclaration, irProperty)) {
                    break;
                }
            }
        }
        IrDeclaration irDeclaration2 = (IrDeclaration) next;
        IrSymbol symbol2 = irDeclaration2 != null ? irDeclaration2.getSymbol() : null;
        Intrinsics.checkNotNull(symbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
        return (IrPropertySymbol) symbol2;
    }

    private static final boolean getIrPropertySymbol$isIllegalFakeOverride(IrProperty irProperty) {
        if (!irProperty.getIsFakeOverride()) {
            return false;
        }
        List<IrPropertySymbol> overriddenSymbols = irProperty.getOverriddenSymbols();
        if (!overriddenSymbols.isEmpty()) {
            List<IrPropertySymbol> list = overriddenSymbols;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (getIrPropertySymbol$isIllegalFakeOverride(((IrPropertySymbol) it.next()).getOwner())) {
                }
            }
            return false;
        }
        return true;
    }

    private final IrProperty createIrLazyProperty(final FirProperty fir, IdSignature signature, final IrDeclarationParent lazyParent, final IrDeclarationOrigin declarationOrigin) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrProperty irPropertyDeclareProperty;
        final Fir2IrPropertySymbol fir2IrPropertySymbol = new Fir2IrPropertySymbol(signature);
        final FirPropertySymbol symbol = fir.getSymbol();
        KtSourceElement source = fir.getSource();
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
        if (Intrinsics.areEqual((Object) isStubPropertyForPureField(fir), (Object) true)) {
            getSymbolTable().declarePropertyWithSignature(signature, fir2IrPropertySymbol);
            createIrLazyProperty$create(fir, symbol, this, declarationOrigin, lazyParent, fir2IrPropertySymbol, i, endOffset);
            irPropertyDeclareProperty = fir2IrPropertySymbol.getOwner();
        } else {
            final int i2 = i;
            final int i3 = endOffset;
            irPropertyDeclareProperty = getSymbolTable().declareProperty(signature, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrLazyProperty$irProperty$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return fir2IrPropertySymbol;
                }
            }, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$createIrLazyProperty$irProperty$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrProperty invoke(IrPropertySymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Fir2IrDeclarationStorage.createIrLazyProperty$create(fir, symbol, this, declarationOrigin, lazyParent, fir2IrPropertySymbol, i2, i3);
                }
            });
        }
        IrProperty irProperty = irPropertyDeclareProperty;
        this.propertyCache.put(fir, irProperty);
        return irProperty;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:21:0x0050  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Fir2IrLazyProperty createIrLazyProperty$create(FirProperty firProperty, FirPropertySymbol firPropertySymbol, Fir2IrDeclarationStorage fir2IrDeclarationStorage, IrDeclarationOrigin irDeclarationOrigin, IrDeclarationParent irDeclarationParent, Fir2IrPropertySymbol fir2IrPropertySymbol, int i, int i2) {
        boolean z;
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firProperty)) {
            FirPropertySymbol firPropertySymbol2 = firPropertySymbol;
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firPropertySymbol2);
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol2.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            FirPropertySymbol firPropertySymbol3 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
            if (Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, firPropertySymbol3 != null ? ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firPropertySymbol3) : null)) {
                z = false;
            } else {
                z = true;
            }
        } else {
            z = false;
        }
        boolean z2 = z;
        Fir2IrComponents fir2IrComponents = fir2IrDeclarationStorage.components;
        Fir2IrLazyClass fir2IrLazyClass = irDeclarationParent instanceof Fir2IrLazyClass ? (Fir2IrLazyClass) irDeclarationParent : null;
        Fir2IrLazyProperty fir2IrLazyProperty = new Fir2IrLazyProperty(fir2IrComponents, i, i2, irDeclarationOrigin, firProperty, fir2IrLazyClass != null ? fir2IrLazyClass.getFir() : null, fir2IrPropertySymbol, z2);
        fir2IrLazyProperty.setParent(irDeclarationParent);
        return fir2IrLazyProperty;
    }

    private final void generateLazyFakeOverrides(Name name2, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        FirClassLikeSymbol<?> symbol = fakeOverrideOwnerLookupTag != null ? LookupTagUtilsKt.toSymbol(fakeOverrideOwnerLookupTag, getSession()) : null;
        FirClassSymbol<?> firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol != null) {
            IrClass owner = getClassifierStorage().getIrClassSymbol(firClassSymbol).getOwner();
            if (owner instanceof Fir2IrLazyClass) {
                ((Fir2IrLazyClass) owner).getFakeOverridesByName(name2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getIrCallableSymbol$lambda-86, reason: not valid java name */
    public static final IrDeclarationParent m6782getIrCallableSymbol$lambda86(Lazy<? extends IrDeclarationParent> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getIrCallableSymbol$lambda-87, reason: not valid java name */
    public static final IdSignature m6783getIrCallableSymbol$lambda87(Lazy<? extends IdSignature> lazy) {
        return lazy.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
    
        if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isJavaOrEnhancement(r0) == true) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin computeDeclarationOrigin(org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<?> r4, org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin r5) {
        /*
            r3 = this;
            org.jetbrains.kotlin.fir.declarations.FirDeclaration r0 = r4.getFir()
            org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration r0 = (org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration) r0
            boolean r0 = org.jetbrains.kotlin.fir.ClassMembersKt.isIntersectionOverride(r0)
            if (r0 != 0) goto L7d
            org.jetbrains.kotlin.fir.declarations.FirDeclaration r0 = r4.getFir()
            org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration r0 = (org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration) r0
            boolean r0 = org.jetbrains.kotlin.fir.ClassMembersKt.isSubstitutionOverride(r0)
            if (r0 == 0) goto L19
            goto L7d
        L19:
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin$IR_EXTERNAL_DECLARATION_STUB r0 = org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r0 == 0) goto L5e
            r0 = r4
            org.jetbrains.kotlin.fir.symbols.FirBasedSymbol r0 = (org.jetbrains.kotlin.fir.symbols.FirBasedSymbol) r0
            org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin r1 = r0.getOrigin()
            boolean r2 = r1 instanceof org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin.Java
            if (r2 != 0) goto L58
            org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin$Enhancement r2 = org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin.Enhancement.INSTANCE
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 == 0) goto L35
            goto L58
        L35:
            org.jetbrains.kotlin.fir.declarations.FirDeclaration r0 = r0.getFir()
            boolean r1 = r0 instanceof org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
            if (r1 == 0) goto L40
            org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration r0 = (org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration) r0
            goto L41
        L40:
            r0 = 0
        L41:
            if (r0 == 0) goto L5e
            org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData r0 = org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(r0)
            if (r0 == 0) goto L5e
            org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration r0 = r0.getOriginal()
            if (r0 == 0) goto L5e
            org.jetbrains.kotlin.fir.declarations.FirDeclaration r0 = (org.jetbrains.kotlin.fir.declarations.FirDeclaration) r0
            boolean r0 = org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isJavaOrEnhancement(r0)
            r1 = 1
            if (r0 != r1) goto L5e
        L58:
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin$IR_EXTERNAL_JAVA_DECLARATION_STUB r4 = org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE
            r5 = r4
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin r5 = (org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin) r5
            goto L82
        L5e:
            org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin r0 = r4.getOrigin()
            boolean r0 = r0 instanceof org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin.Plugin
            if (r0 == 0) goto L82
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin$GeneratedByPlugin r5 = new org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin$GeneratedByPlugin
            org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin r4 = r4.getOrigin()
            java.lang.String r0 = "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin.Plugin"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4, r0)
            org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin$Plugin r4 = (org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin.Plugin) r4
            org.jetbrains.kotlin.GeneratedDeclarationKey r4 = r4.getKey()
            r5.<init>(r4)
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin r5 = (org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin) r5
            goto L82
        L7d:
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin$FAKE_OVERRIDE r4 = org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE
            r5 = r4
            org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin r5 = (org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin) r5
        L82:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage.computeDeclarationOrigin(org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol, org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin):org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin");
    }

    public static /* synthetic */ IrFieldSymbol getIrFieldSymbol$default(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirFieldSymbol firFieldSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 2) != 0) {
            coneClassLikeLookupTag = null;
        }
        return fir2IrDeclarationStorage.getIrFieldSymbol(firFieldSymbol, coneClassLikeLookupTag);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrFieldSymbol getIrFieldSymbol(FirFieldSymbol firFieldSymbol, ConeClassLikeLookupTag fakeOverrideOwnerLookupTag) {
        Intrinsics.checkNotNullParameter(firFieldSymbol, "firFieldSymbol");
        FirField firField = (FirField) firFieldSymbol.getFir();
        FieldStaticOverrideKey fieldStaticFakeOverrideKey = getFieldStaticFakeOverrideKey(firField, fakeOverrideOwnerLookupTag);
        if (fieldStaticFakeOverrideKey == null) {
            IrField irField = this.fieldCache.get(firField);
            if (irField != null) {
                return irField.getSymbol();
            }
            FirField firField2 = firField;
            IrDeclarationParent irDeclarationParentFindIrParent$fir2ir = findIrParent$fir2ir(firField2);
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firField2) || (firField2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firField2) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firField2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firField2) : null;
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firField2 = originalForSubstitutionOverrideAttr;
            }
            FirField firField3 = (FirField) firField2;
            if (firField3 != firField) {
                return getIrFieldSymbol$default(this, firField3.getSymbol(), null, 2, null);
            }
            return createIrField$fir2ir$default(this, firField, irDeclarationParentFindIrParent$fir2ir, null, null, 12, null).getSymbol();
        }
        generateLazyFakeOverrides(firField.getName(), fakeOverrideOwnerLookupTag);
        IrField irField2 = this.fieldStaticOverrideCache.get(fieldStaticFakeOverrideKey);
        Intrinsics.checkNotNull(irField2);
        return irField2.getSymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSymbol getIrBackingFieldSymbol(FirBackingFieldSymbol firBackingFieldSymbol) {
        Intrinsics.checkNotNullParameter(firBackingFieldSymbol, "firBackingFieldSymbol");
        return getIrPropertyForwardedSymbol((FirVariable) ((FirBackingField) firBackingFieldSymbol.getFir()).getPropertySymbol().getFir());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSymbol getIrDelegateFieldSymbol(FirVariableSymbol<?> firVariableSymbol) {
        Intrinsics.checkNotNullParameter(firVariableSymbol, "firVariableSymbol");
        return getIrPropertyForwardedSymbol((FirVariable) firVariableSymbol.getFir());
    }

    public final IrScript getCachedIrScript(FirScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return this.scriptCache.get(script);
    }

    public final IrScript getOrCreateIrScript(final FirScript script) {
        int startOffset;
        final int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(script, "script");
        IrScript cachedIrScript = getCachedIrScript(script);
        if (cachedIrScript != null) {
            return cachedIrScript;
        }
        KtSourceElement source = script.getSource();
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
        final IdSignature idSignatureComposeSignature = getSignatureComposer().composeSignature(script);
        Intrinsics.checkNotNull(idSignatureComposeSignature);
        return getSymbolTable().declareScript(idSignatureComposeSignature, new Function0<IrScriptSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getOrCreateIrScript$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrScriptSymbol invoke() {
                return new Fir2IrScriptSymbol(idSignatureComposeSignature);
            }
        }, new Function1<IrScriptSymbol, IrScript>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage$getOrCreateIrScript$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrScript invoke(IrScriptSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrScriptImpl irScriptImpl = new IrScriptImpl(symbol, script.getName(), this.getIrFactory(), i, endOffset);
                FirScript firScript = script;
                Fir2IrDeclarationStorage fir2IrDeclarationStorage = this;
                irScriptImpl.setOrigin(IrScriptImplKt.getSCRIPT_K2_ORIGIN());
                irScriptImpl.setMetadata(new FirMetadataSource.Script(firScript));
                irScriptImpl.setImplicitReceiversParameters(CollectionsKt.emptyList());
                irScriptImpl.setProvidedProperties(CollectionsKt.emptyList());
                irScriptImpl.setProvidedPropertiesParameters(CollectionsKt.emptyList());
                fir2IrDeclarationStorage.scriptCache.put(firScript, irScriptImpl);
                return irScriptImpl;
            }
        });
    }

    private final IrSymbol getIrPropertyForwardedSymbol(FirVariable fir) {
        IrDeclarationOrigin.DEFINED origin;
        IrVariable delegate;
        IrVariableSymbol symbol;
        if (fir instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) fir;
            if (firProperty.isLocal()) {
                IrLocalDelegatedProperty delegatedProperty = getLocalStorage().getDelegatedProperty(firProperty);
                return (delegatedProperty == null || (delegate = delegatedProperty.getDelegate()) == null || (symbol = delegate.getSymbol()) == null) ? getIrVariableSymbol(fir) : symbol;
            }
            IrProperty irProperty = this.propertyCache.get(fir);
            if (irProperty != null) {
                IrField backingField = irProperty.getBackingField();
                Intrinsics.checkNotNull(backingField);
                return backingField.getSymbol();
            }
            IrDeclarationParent irDeclarationParentFindIrParent$fir2ir = findIrParent$fir2ir(fir);
            IrDeclaration irDeclaration = irDeclarationParentFindIrParent$fir2ir instanceof IrDeclaration ? (IrDeclaration) irDeclarationParentFindIrParent$fir2ir : null;
            if (irDeclaration == null || (origin = irDeclaration.getOrigin()) == null) {
                origin = IrDeclarationOrigin.DEFINED.INSTANCE;
            }
            IrProperty irPropertyCreateIrProperty$default = createIrProperty$default(this, firProperty, irDeclarationParentFindIrParent$fir2ir, null, origin, false, null, 52, null);
            setAndModifyParent(irPropertyCreateIrProperty$default, irDeclarationParentFindIrParent$fir2ir);
            IrField backingField2 = irPropertyCreateIrProperty$default.getBackingField();
            Intrinsics.checkNotNull(backingField2);
            return backingField2.getSymbol();
        }
        return getIrVariableSymbol(fir);
    }

    private final IrVariableSymbol getIrVariableSymbol(FirVariable firVariable) {
        IrVariableSymbol symbol;
        IrVariable variable = getLocalStorage().getVariable(firVariable);
        if (variable == null || (symbol = variable.getSymbol()) == null) {
            throw new IllegalArgumentException("Cannot find variable " + UtilsKt.render(firVariable) + " in local storage");
        }
        return symbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSymbol getIrValueSymbol(FirVariableSymbol<?> firVariableSymbol) {
        IrValueParameterSymbol symbol;
        IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB origin;
        Intrinsics.checkNotNullParameter(firVariableSymbol, "firVariableSymbol");
        FirVariable firVariable = (FirVariable) firVariableSymbol.getFir();
        if (firVariable instanceof FirEnumEntry) {
            FirEnumEntry firEnumEntry = (FirEnumEntry) firVariable;
            IrEnumEntry cachedIrEnumEntry$fir2ir = getClassifierStorage().getCachedIrEnumEntry$fir2ir(firEnumEntry);
            if (cachedIrEnumEntry$fir2ir != null) {
                return cachedIrEnumEntry$fir2ir.getSymbol();
            }
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firVariable);
            IrClass irClassFindIrClass = coneClassLikeLookupTagContainingClassLookupTag != null ? getClassifierStorage().findIrClass(coneClassLikeLookupTagContainingClassLookupTag) : null;
            FirFile firCallableContainerFile = FirProviderKt.getFirProvider(firVariableSymbol.getModuleData().getSession()).getFirCallableContainerFile(firVariableSymbol);
            Fir2IrClassifierStorage classifierStorage = getClassifierStorage();
            if (firCallableContainerFile != null) {
                origin = IrDeclarationOrigin.DEFINED.INSTANCE;
            } else if (irClassFindIrClass == null || (origin = irClassFindIrClass.getOrigin()) == null) {
                origin = IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE;
            }
            return classifierStorage.createIrEnumEntry(firEnumEntry, irClassFindIrClass, origin).getSymbol();
        }
        if (firVariable instanceof FirValueParameter) {
            IrValueParameter parameter = getLocalStorage().getParameter((FirValueParameter) firVariable);
            return (parameter == null || (symbol = parameter.getSymbol()) == null) ? getIrVariableSymbol(firVariable) : symbol;
        }
        return getIrVariableSymbol(firVariable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void convertAnnotationsForNonDeclaredMembers(IrMutableAnnotationContainer irMutableAnnotationContainer, FirAnnotationContainer firAnnotationContainer, IrDeclarationOrigin irDeclarationOrigin) {
        FirDeclaration firDeclaration = firAnnotationContainer instanceof FirDeclaration ? (FirDeclaration) firAnnotationContainer : null;
        if ((firDeclaration == null || !(Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Library.INSTANCE) || Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Java.Library.INSTANCE) || Intrinsics.areEqual(firDeclaration.getOrigin(), FirDeclarationOrigin.Precompiled.INSTANCE))) && !Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE)) {
            return;
        }
        getAnnotationGenerator().generate(irMutableAnnotationContainer, firAnnotationContainer);
    }

    /* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$Companion;", "", "()V", "ENUM_SYNTHETIC_NAMES", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBodyKind;", "getENUM_SYNTHETIC_NAMES$fir2ir", "()Ljava/util/Map;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<Name, IrSyntheticBodyKind> getENUM_SYNTHETIC_NAMES$fir2ir() {
            return Fir2IrDeclarationStorage.ENUM_SYNTHETIC_NAMES;
        }
    }

    private final boolean isFakeOverride(FirCallableDeclaration firCallableDeclaration, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            return true;
        }
        if (coneClassLikeLookupTag == null) {
            return false;
        }
        return !Intrinsics.areEqual(coneClassLikeLookupTag, ClassMembersKt.containingClassLookupTag(firCallableDeclaration));
    }

    private final FirProperty toStubProperty(FirField firField) {
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(firField.getSource());
        firPropertyBuilder.setModuleData(firField.getModuleData());
        firPropertyBuilder.setOrigin(firField.getOrigin());
        firPropertyBuilder.setReturnTypeRef(firField.getReturnTypeRef());
        firPropertyBuilder.setName(firField.getName());
        firPropertyBuilder.setVar(firField.getIsVar());
        firPropertyBuilder.setGetter(firField.getGetter());
        firPropertyBuilder.setSetter(firField.getSetter());
        firPropertyBuilder.setSymbol(new FirPropertySymbol(firField.getSymbol().getCallableId()));
        firPropertyBuilder.setLocal(false);
        firPropertyBuilder.setStatus(firField.getStatus());
        FirProperty firPropertyBuild = firPropertyBuilder.mo6809build();
        setStubPropertyForPureField(firPropertyBuild, true);
        return firPropertyBuild;
    }

    /* JADX WARN: Code duplicated, block: B:68:0x0105 A[Catch: all -> 0x012f, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x002f, B:10:0x0036, B:12:0x0048, B:15:0x004f, B:17:0x0057, B:20:0x005d, B:23:0x0067, B:25:0x0076, B:27:0x007e, B:28:0x0086, B:30:0x008c, B:68:0x0105, B:33:0x0099, B:36:0x009e, B:38:0x00a4, B:44:0x00b8, B:46:0x00be, B:50:0x00c8, B:52:0x00cd, B:55:0x00dd, B:57:0x00e1, B:42:0x00b2, B:61:0x00ef, B:63:0x00f3, B:65:0x00f9, B:22:0x0063), top: B:76:0x002f }] */
    /* JADX WARN: Multi-variable type inference failed */
    private static final IrSymbol getIrPropertySymbol$getIrCallableSymbol(ConeClassLikeLookupTag coneClassLikeLookupTag, Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirPropertySymbol firPropertySymbol, FirProperty firProperty, ConeClassLikeLookupTag coneClassLikeLookupTag2) {
        IrDeclarationOrigin.DEFINED origin;
        IdSignature idSignatureM6783getIrCallableSymbol$lambda87;
        IrFunctionSymbol symbol;
        ConeClassLikeLookupTag coneClassLikeLookupTag3 = coneClassLikeLookupTag;
        D fir = firPropertySymbol.getFir();
        if (fir == 0) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirProperty");
        }
        FirProperty firProperty2 = (FirProperty) fir;
        Lazy lazy = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$irParent$2(fir2IrDeclarationStorage, firProperty2));
        Lazy lazy2 = LazyKt.lazy(new Fir2IrDeclarationStorage$getIrCallableSymbol$signature$2(fir2IrDeclarationStorage, firProperty2, coneClassLikeLookupTag));
        synchronized (fir2IrDeclarationStorage.getSymbolTable().getLock()) {
            if (coneClassLikeLookupTag3 instanceof ConeClassLookupTagWithFixedSymbol) {
                coneClassLikeLookupTag3 = null;
            }
            IrProperty cachedIrProperty = fir2IrDeclarationStorage.getCachedIrProperty(firProperty2, coneClassLikeLookupTag3, new Fir2IrDeclarationStorage$getIrCallableSymbol$1$2(lazy, lazy2));
            if (cachedIrProperty != null) {
                symbol = cachedIrProperty.getSymbol();
            } else {
                IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda86 = m6782getIrCallableSymbol$lambda86(lazy);
                IrDeclaration irDeclaration = irDeclarationParentM6782getIrCallableSymbol$lambda86 instanceof IrDeclaration ? (IrDeclaration) irDeclarationParentM6782getIrCallableSymbol$lambda86 : null;
                if (irDeclaration == null || (origin = irDeclaration.getOrigin()) == null) {
                    origin = IrDeclarationOrigin.DEFINED.INSTANCE;
                }
                IrDeclarationOrigin irDeclarationOriginComputeDeclarationOrigin = fir2IrDeclarationStorage.computeDeclarationOrigin(firPropertySymbol, origin);
                IrDeclarationParent irDeclarationParentM6782getIrCallableSymbol$lambda87 = m6782getIrCallableSymbol$lambda86(lazy);
                if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof Fir2IrLazyClass) {
                    if (Intrinsics.areEqual(origin, IrDeclarationOrigin.DEFINED.INSTANCE)) {
                        fir2IrDeclarationStorage.getConfiguration().getAllowNonCachedDeclarations();
                    }
                    IdSignature idSignatureM6783getIrCallableSymbol$lambda88 = m6783getIrCallableSymbol$lambda87(lazy2);
                    if (idSignatureM6783getIrCallableSymbol$lambda88 != null) {
                        symbol = fir2IrDeclarationStorage.createIrLazyProperty(firProperty, idSignatureM6783getIrCallableSymbol$lambda88, irDeclarationParentM6782getIrCallableSymbol$lambda87, irDeclarationOriginComputeDeclarationOrigin).getSymbol();
                    } else {
                        IrProperty irPropertyCreateIrProperty$default = createIrProperty$default(fir2IrDeclarationStorage, firProperty, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, coneClassLikeLookupTag2, 20, null);
                        IrProperty irProperty = irPropertyCreateIrProperty$default;
                        Intrinsics.checkNotNull(irProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        fir2IrDeclarationStorage.setAndModifyParent(irProperty, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irPropertyCreateIrProperty$default.getSymbol();
                    }
                } else if (irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrLazyClass) {
                    FirCallableDeclaration firCallableDeclaration = firProperty2;
                    while (true) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr == null) {
                            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                        }
                        if (originalForSubstitutionOverrideAttr == null) {
                            break;
                        }
                        firCallableDeclaration = originalForSubstitutionOverrideAttr;
                    }
                    if (firCallableDeclaration == firProperty2) {
                        IrProperty irPropertyCreateIrProperty$default2 = createIrProperty$default(fir2IrDeclarationStorage, firProperty, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, coneClassLikeLookupTag2, 20, null);
                        IrProperty irProperty2 = irPropertyCreateIrProperty$default2;
                        Intrinsics.checkNotNull(irProperty2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        fir2IrDeclarationStorage.setAndModifyParent(irProperty2, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irPropertyCreateIrProperty$default2.getSymbol();
                    } else if (firCallableDeclaration instanceof FirSimpleFunction) {
                        symbol = getIrFunctionSymbol$default(fir2IrDeclarationStorage, ((FirSimpleFunction) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else if (firCallableDeclaration instanceof FirProperty) {
                        symbol = getIrPropertySymbol$default(fir2IrDeclarationStorage, ((FirProperty) firCallableDeclaration).getSymbol(), null, 2, null);
                    } else {
                        IrProperty irPropertyCreateIrProperty$default3 = createIrProperty$default(fir2IrDeclarationStorage, firProperty, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, coneClassLikeLookupTag2, 20, null);
                        IrProperty irProperty3 = irPropertyCreateIrProperty$default3;
                        Intrinsics.checkNotNull(irProperty3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        fir2IrDeclarationStorage.setAndModifyParent(irProperty3, m6782getIrCallableSymbol$lambda86(lazy));
                        symbol = irPropertyCreateIrProperty$default3.getSymbol();
                    }
                } else if (!(irDeclarationParentM6782getIrCallableSymbol$lambda87 instanceof IrExternalPackageFragment) || (idSignatureM6783getIrCallableSymbol$lambda87 = m6783getIrCallableSymbol$lambda87(lazy2)) == null) {
                    IrProperty irPropertyCreateIrProperty$default4 = createIrProperty$default(fir2IrDeclarationStorage, firProperty, m6782getIrCallableSymbol$lambda86(lazy), null, irDeclarationOriginComputeDeclarationOrigin, false, coneClassLikeLookupTag2, 20, null);
                    IrProperty irProperty4 = irPropertyCreateIrProperty$default4;
                    Intrinsics.checkNotNull(irProperty4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                    fir2IrDeclarationStorage.setAndModifyParent(irProperty4, m6782getIrCallableSymbol$lambda86(lazy));
                    symbol = irPropertyCreateIrProperty$default4.getSymbol();
                } else {
                    symbol = fir2IrDeclarationStorage.createIrLazyProperty(firProperty, idSignatureM6783getIrCallableSymbol$lambda87, irDeclarationParentM6782getIrCallableSymbol$lambda87, irDeclarationOriginComputeDeclarationOrigin).getSymbol();
                }
            }
        }
        return symbol;
    }
}
