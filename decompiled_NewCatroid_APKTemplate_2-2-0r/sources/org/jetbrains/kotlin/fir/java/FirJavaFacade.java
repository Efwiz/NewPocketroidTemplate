package org.jetbrains.kotlin.fir.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.BuiltinTypes;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.EnumClassUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructedClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirEnumEntryBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirOuterClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirStatusTransformerExtension;
import org.jetbrains.kotlin.fir.extensions.FirStatusTransformerExtensionKt;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClassBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaConstructor;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaConstructorBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaField;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaFieldBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethod;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethodBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameter;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameterBuilder;
import org.jetbrains.kotlin.fir.java.declarations.UtilsKt;
import org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaLoadingKt;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirJavaFacade.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 h2\u00020\u0001:\u0002hiB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ0\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J \u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010$\u001a\u00020%H\u0002J \u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00132\b\u0010/\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001c\u001a\u00020\u001dJR\u00100\u001a\u00020\u001b2\b\u00101\u001a\u0004\u0018\u0001022\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!2\f\u00103\u001a\b\u0012\u0004\u0012\u0002040\u00182\u0006\u00105\u001a\u00020\u00162\b\u00106\u001a\u0004\u0018\u00010\u00132\u0006\u0010$\u001a\u00020%H\u0002J0\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<2\u0006\u00105\u001a\u00020\u00162\u0006\u0010=\u001a\u00020>2\u0006\u0010$\u001a\u00020%H\u0002J8\u0010?\u001a\u00020+2\u0006\u0010@\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020)2\u0006\u0010;\u001a\u00020<2\u0006\u00105\u001a\u00020\u00162\u0006\u0010=\u001a\u00020>2\u0006\u0010$\u001a\u00020%H\u0002JD\u0010A\u001a\u00020B2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020<2\u0006\u0010$\u001a\u00020%2\u0006\u0010C\u001a\u00020>2\f\u00103\u001a\b\u0012\u0004\u0012\u0002040\u00182\f\u0010D\u001a\b\u0012\u0004\u0012\u0002080EH\u0002J2\u0010F\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020\u00132\b\u0010/\u001a\u0004\u0018\u00010\u00132\u0006\u0010;\u001a\u00020<2\u0006\u00105\u001a\u00020\u0016H\u0002J\u001c\u0010G\u001a\u0004\u0018\u00010\u001d2\u0006\u0010;\u001a\u00020<2\n\b\u0002\u0010H\u001a\u0004\u0018\u00010IJ\u0010\u0010J\u001a\u00020%2\u0006\u0010\u001c\u001a\u00020\u001dH&J\u0010\u0010K\u001a\u0004\u0018\u00010\u000b2\u0006\u0010L\u001a\u00020\u000bJ\u000e\u0010M\u001a\u00020N2\u0006\u0010;\u001a\u00020<J\u0016\u0010\t\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010O\u001a\u00020\u000bJ\u001a\u0010P\u001a\u00020B2\u0006\u0010Q\u001a\u00020-2\b\u0010/\u001a\u0004\u0018\u00010\u0013H\u0002J,\u0010R\u001a\u00020B*\u00020S2\u001d\u0010T\u001a\u0019\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020V\u0012\u0004\u0012\u00020V0U¢\u0006\u0002\bWH\u0082\bJ\f\u0010X\u001a\u00020Y*\u00020!H\u0002J4\u0010Z\u001a\b\u0012\u0004\u0012\u0002040\u0018*\b\u0012\u0004\u0012\u00020[0\u00182\u0006\u0010\\\u001a\u00020\u00162\n\u0010]\u001a\u0006\u0012\u0002\b\u00030^2\u0006\u0010$\u001a\u00020%H\u0002J(\u0010_\u001a\u000204*\u00020[2\u0006\u00105\u001a\u00020\u00162\n\u0010]\u001a\u0006\u0012\u0002\b\u00030^2\u0006\u0010$\u001a\u00020%H\u0002J\u0018\u0010`\u001a\b\u0012\u0004\u0012\u00020a0\u0018*\b\u0012\u0004\u0012\u0002040\u0018H\u0002J\u0018\u0010b\u001a\u0004\u0018\u00010c*\u00020d2\b\b\u0002\u0010e\u001a\u00020fH\u0002J\f\u0010g\u001a\u00020\r*\u00020\u000bH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\f\u0012\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u000f\u001a\u0018\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00160\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006j"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "builtinTypes", "Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "classFinder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/BuiltinTypes;Lorg/jetbrains/kotlin/load/java/JavaClassFinder;)V", "knownClassNamesInPackage", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/FqName;", "", "", "", "packageCache", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "parentClassEffectiveVisibilityCache", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "parentClassTypeParameterStackCache", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "statusExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "buildConstructorForAnnotationClass", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructor;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "constructorId", "Lorg/jetbrains/kotlin/name/CallableId;", "ownerClassBuilder", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClassBuilder;", "valueParametersForAnnotationConstructor", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacade$ValueParametersForAnnotationConstructor;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "convertJavaAnnotationMethodToValueParameter", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", "javaMethod", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "firJavaMethod", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaMethod;", "convertJavaClassToFir", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", "classSymbol", "parentClassSymbol", "convertJavaConstructorToFir", "javaConstructor", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "classTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "javaTypeParameterStack", "outerClassSymbol", "convertJavaFieldToFir", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "javaField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "convertJavaMethodToFir", "containingClass", "createDeclarationsForJavaRecord", "", "classType", FirebaseAnalytics.Param.DESTINATION, "", "createFirJavaClass", "findClass", "knownContent", "", "getModuleDataForClass", "getPackage", "fqName", "hasTopLevelClassOf", "", "packageFqName", "updateStatuses", "firJavaClass", "applyExtensionTransformers", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "operation", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "Lkotlin/ExtensionFunctionType;", "buildSelfTypeRef", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "convertTypeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "stack", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toFirTypeParameter", "toRefs", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "toSourceElement", "Lorg/jetbrains/kotlin/KtSourceElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "sourceElementKind", "Lorg/jetbrains/kotlin/KtSourceElementKind;", "topLevelName", "Companion", "ValueParametersForAnnotationConstructor", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirJavaFacade {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Name VALUE_METHOD_NAME;
    private final BuiltinTypes builtinTypes;
    private final JavaClassFinder classFinder;
    private final FirCache knownClassNamesInPackage;
    private final FirCache packageCache;
    private final Map<FirRegularClassSymbol, EffectiveVisibility> parentClassEffectiveVisibilityCache;
    private final Map<FirRegularClassSymbol, JavaTypeParameterStack> parentClassTypeParameterStackCache;
    private final FirSession session;
    private final List<FirStatusTransformerExtension> statusExtensions;

    public abstract FirModuleData getModuleDataForClass(JavaClass javaClass);

    public FirJavaFacade(FirSession session, BuiltinTypes builtinTypes, final JavaClassFinder classFinder) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(builtinTypes, "builtinTypes");
        Intrinsics.checkNotNullParameter(classFinder, "classFinder");
        this.session = session;
        this.builtinTypes = builtinTypes;
        this.classFinder = classFinder;
        this.packageCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final JavaPackage invoke(FqName key, Void r4) {
                Intrinsics.checkNotNullParameter(key, "key");
                FqName fqName = key;
                Set<String> setKnownClassNamesInPackage = this.this$0.knownClassNamesInPackage(fqName);
                return this.this$0.classFinder.findPackage(fqName, setKnownClassNamesInPackage != null ? setKnownClassNamesInPackage.contains("package-info") : true);
            }
        });
        this.knownClassNamesInPackage = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$special$$inlined$createCache$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final Set<? extends String> invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return classFinder.knownClassNamesInPackage(key);
            }
        });
        this.parentClassTypeParameterStackCache = new LinkedHashMap();
        this.parentClassEffectiveVisibilityCache = new LinkedHashMap();
        this.statusExtensions = FirStatusTransformerExtensionKt.getStatusTransformerExtensions(FirExtensionServiceKt.getExtensionService(session));
    }

    /* JADX INFO: compiled from: FirJavaFacade.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaFacade$Companion;", "", "()V", "PACKAGE_INFO_CLASS_NAME", "", "VALUE_METHOD_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getVALUE_METHOD_NAME", "()Lorg/jetbrains/kotlin/name/Name;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Name getVALUE_METHOD_NAME() {
            return FirJavaFacade.VALUE_METHOD_NAME;
        }
    }

    static {
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        VALUE_METHOD_NAME = nameIdentifier;
    }

    public static /* synthetic */ JavaClass findClass$default(FirJavaFacade firJavaFacade, ClassId classId, byte[] bArr, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClass");
        }
        if ((i & 2) != 0) {
            bArr = null;
        }
        return firJavaFacade.findClass(classId, bArr);
    }

    public final JavaClass findClass(ClassId classId, byte[] knownContent) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        JavaClass javaClassFindClass = this.classFinder.findClass(new JavaClassFinder.Request(classId, knownContent, null, 4, null));
        if (javaClassFindClass == null || !Intrinsics.areEqual(JavaElementsKt.getClassId(javaClassFindClass), classId) || JavaUtilsKt.hasMetadataAnnotation(javaClassFindClass)) {
            return null;
        }
        return javaClassFindClass;
    }

    public final FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        JavaPackage javaPackage = (JavaPackage) this.packageCache.getValue(fqName, null);
        if (javaPackage != null) {
            return javaPackage.getFqName();
        }
        return null;
    }

    public final boolean hasTopLevelClassOf(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        Set<String> setKnownClassNamesInPackage = knownClassNamesInPackage(packageFqName);
        if (setKnownClassNamesInPackage == null) {
            return true;
        }
        FqName relativeClassName = classId.getRelativeClassName();
        Intrinsics.checkNotNullExpressionValue(relativeClassName, "getRelativeClassName(...)");
        return setKnownClassNamesInPackage.contains(topLevelName(relativeClassName));
    }

    public final Set<String> knownClassNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (this.classFinder.canComputeKnownClassNamesInPackage()) {
            return (Set) this.knownClassNamesInPackage.getValue(packageFqName, null);
        }
        return null;
    }

    private final List<FirTypeParameter> convertTypeParameters(List<? extends JavaTypeParameter> list, JavaTypeParameterStack javaTypeParameterStack, FirBasedSymbol<?> firBasedSymbol, FirModuleData firModuleData) {
        List<? extends JavaTypeParameter> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(toFirTypeParameter((JavaTypeParameter) it.next(), javaTypeParameterStack, firBasedSymbol, firModuleData));
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: FirJavaFacade.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u000f\u001a\u00020\u00102\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00100\u0012H\u0086\bø\u0001\u0000R(\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaFacade$ValueParametersForAnnotationConstructor;", "", "()V", "valueParameterForValue", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", "getValueParameterForValue", "()Lkotlin/Pair;", "setValueParameterForValue", "(Lkotlin/Pair;)V", "valueParameters", "", "getValueParameters", "()Ljava/util/Map;", "forEach", "", "block", "Lkotlin/Function2;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ValueParametersForAnnotationConstructor {
        private Pair<? extends JavaMethod, FirJavaValueParameter> valueParameterForValue;
        private final Map<JavaMethod, FirJavaValueParameter> valueParameters = new LinkedHashMap();

        public final Map<JavaMethod, FirJavaValueParameter> getValueParameters() {
            return this.valueParameters;
        }

        public final Pair<JavaMethod, FirJavaValueParameter> getValueParameterForValue() {
            return this.valueParameterForValue;
        }

        public final void setValueParameterForValue(Pair<? extends JavaMethod, FirJavaValueParameter> pair) {
            this.valueParameterForValue = pair;
        }

        public final void forEach(Function2<? super JavaMethod, ? super FirJavaValueParameter, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Pair<JavaMethod, FirJavaValueParameter> valueParameterForValue = getValueParameterForValue();
            if (valueParameterForValue != null) {
                block.invoke(valueParameterForValue.component1(), valueParameterForValue.component2());
            }
            for (Map.Entry<JavaMethod, FirJavaValueParameter> entry : getValueParameters().entrySet()) {
                block.invoke(entry.getKey(), entry.getValue());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirJavaClass convertJavaClassToFir(FirRegularClassSymbol classSymbol, FirRegularClassSymbol parentClassSymbol, JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        ClassId classId = classSymbol.getClassId();
        JavaTypeParameterStack javaTypeParameterStack = new JavaTypeParameterStack();
        if (parentClassSymbol != null) {
            JavaTypeParameterStack javaTypeParameterStack2 = this.parentClassTypeParameterStackCache.get(parentClassSymbol);
            if (javaTypeParameterStack2 == null) {
                E fir = parentClassSymbol.getFir();
                FirJavaClass firJavaClass = fir instanceof FirJavaClass ? (FirJavaClass) fir : null;
                javaTypeParameterStack2 = firJavaClass != null ? firJavaClass.getJavaTypeParameterStack() : null;
            }
            if (javaTypeParameterStack2 != null) {
                javaTypeParameterStack.addStack(javaTypeParameterStack2);
            }
        }
        this.parentClassTypeParameterStackCache.put(classSymbol, javaTypeParameterStack);
        FirJavaClass firJavaClassCreateFirJavaClass = createFirJavaClass(javaClass, classSymbol, parentClassSymbol, classId, javaTypeParameterStack);
        this.parentClassTypeParameterStackCache.remove(classSymbol);
        this.parentClassEffectiveVisibilityCache.remove(classSymbol);
        FirSignatureEnhancement firSignatureEnhancement = new FirSignatureEnhancement(firJavaClassCreateFirJavaClass, this.session, new Function1<FirSimpleFunction, List<? extends FirCallableDeclaration>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaClassToFir$enhancement$1
            @Override // kotlin.jvm.functions.Function1
            public final List<FirCallableDeclaration> invoke(FirSimpleFunction $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                return CollectionsKt.emptyList();
            }
        });
        Pair<List<List<FirTypeRef>>, List<FirTypeParameterRef>> pairPerformFirstRoundOfBoundsResolution = firSignatureEnhancement.performFirstRoundOfBoundsResolution(firJavaClassCreateFirJavaClass.getTypeParameters());
        List<List<FirTypeRef>> listComponent1 = pairPerformFirstRoundOfBoundsResolution.component1();
        List<FirTypeParameterRef> listComponent2 = pairPerformFirstRoundOfBoundsResolution.component2();
        firJavaClassCreateFirJavaClass.getTypeParameters().clear();
        CollectionsKt.addAll(firJavaClassCreateFirJavaClass.getTypeParameters(), listComponent2);
        firSignatureEnhancement.enhanceTypeParameterBoundsAfterFirstRound(firJavaClassCreateFirJavaClass.getTypeParameters(), listComponent1);
        updateStatuses(firJavaClassCreateFirJavaClass, parentClassSymbol);
        return firJavaClassCreateFirJavaClass;
    }

    private final void updateStatuses(FirJavaClass firJavaClass, final FirRegularClassSymbol parentClassSymbol) {
        if (this.statusExtensions.isEmpty()) {
            return;
        }
        final FirRegularClassSymbol symbol = firJavaClass.getSymbol();
        firJavaClass.accept(new FirVisitorVoid() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$updateStatuses$visitor$1
            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6787visitElement(FirElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitRegularClass(FirRegularClass regularClass) {
                Intrinsics.checkNotNullParameter(regularClass, "regularClass");
                FirJavaFacade firJavaFacade = this.this$0;
                FirRegularClass firRegularClass = regularClass;
                FirRegularClassSymbol firRegularClassSymbol = parentClassSymbol;
                FirDeclarationStatus status = firRegularClass.getStatus();
                Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl");
                FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = (FirResolvedDeclarationStatusImpl) status;
                List<FirStatusTransformerExtension> list = firJavaFacade.statusExtensions;
                FirDeclarationStatus status2 = firRegularClass.getStatus();
                for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                    if (firStatusTransformerExtension.needTransformStatus(firRegularClass)) {
                        status2 = firStatusTransformerExtension.transformStatus(status2, regularClass, (FirClassLikeSymbol<?>) firRegularClassSymbol, false);
                    }
                }
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl");
                FirDeclarationStatusImpl firDeclarationStatusImpl = (FirDeclarationStatusImpl) status2;
                if (firDeclarationStatusImpl != firResolvedDeclarationStatusImpl) {
                    Visibility visibility = firDeclarationStatusImpl.getVisibility();
                    Modality modality = firDeclarationStatusImpl.getModality();
                    if (modality == null) {
                        modality = firResolvedDeclarationStatusImpl.getModality();
                    }
                    firRegularClass.replaceStatus(firDeclarationStatusImpl.resolved(visibility, modality, firResolvedDeclarationStatusImpl.getEffectiveVisibility()));
                }
                regularClass.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitSimpleFunction(FirSimpleFunction simpleFunction) {
                Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
                FirJavaFacade firJavaFacade = this.this$0;
                FirSimpleFunction firSimpleFunction = simpleFunction;
                FirRegularClassSymbol firRegularClassSymbol = symbol;
                FirDeclarationStatus status = firSimpleFunction.getStatus();
                Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl");
                FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = (FirResolvedDeclarationStatusImpl) status;
                List<FirStatusTransformerExtension> list = firJavaFacade.statusExtensions;
                FirDeclarationStatus status2 = firSimpleFunction.getStatus();
                for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                    if (firStatusTransformerExtension.needTransformStatus(firSimpleFunction)) {
                        status2 = firStatusTransformerExtension.transformStatus(status2, simpleFunction, (FirClassLikeSymbol<?>) firRegularClassSymbol, false);
                    }
                }
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl");
                FirDeclarationStatusImpl firDeclarationStatusImpl = (FirDeclarationStatusImpl) status2;
                if (firDeclarationStatusImpl != firResolvedDeclarationStatusImpl) {
                    Visibility visibility = firDeclarationStatusImpl.getVisibility();
                    Modality modality = firDeclarationStatusImpl.getModality();
                    if (modality == null) {
                        modality = firResolvedDeclarationStatusImpl.getModality();
                    }
                    firSimpleFunction.replaceStatus(firDeclarationStatusImpl.resolved(visibility, modality, firResolvedDeclarationStatusImpl.getEffectiveVisibility()));
                }
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitField(FirField field) {
                Intrinsics.checkNotNullParameter(field, "field");
                FirJavaFacade firJavaFacade = this.this$0;
                FirField firField = field;
                FirRegularClassSymbol firRegularClassSymbol = symbol;
                FirDeclarationStatus status = firField.getStatus();
                Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl");
                FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = (FirResolvedDeclarationStatusImpl) status;
                List<FirStatusTransformerExtension> list = firJavaFacade.statusExtensions;
                FirDeclarationStatus status2 = firField.getStatus();
                for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                    if (firStatusTransformerExtension.needTransformStatus(firField)) {
                        status2 = firStatusTransformerExtension.transformStatus(status2, field, (FirClassLikeSymbol<?>) firRegularClassSymbol, false);
                    }
                }
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl");
                FirDeclarationStatusImpl firDeclarationStatusImpl = (FirDeclarationStatusImpl) status2;
                if (firDeclarationStatusImpl != firResolvedDeclarationStatusImpl) {
                    Visibility visibility = firDeclarationStatusImpl.getVisibility();
                    Modality modality = firDeclarationStatusImpl.getModality();
                    if (modality == null) {
                        modality = firResolvedDeclarationStatusImpl.getModality();
                    }
                    firField.replaceStatus(firDeclarationStatusImpl.resolved(visibility, modality, firResolvedDeclarationStatusImpl.getEffectiveVisibility()));
                }
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitConstructor(FirConstructor constructor) {
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                FirJavaFacade firJavaFacade = this.this$0;
                FirConstructor firConstructor = constructor;
                FirRegularClassSymbol firRegularClassSymbol = symbol;
                FirDeclarationStatus status = firConstructor.getStatus();
                Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl");
                FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = (FirResolvedDeclarationStatusImpl) status;
                List<FirStatusTransformerExtension> list = firJavaFacade.statusExtensions;
                FirDeclarationStatus status2 = firConstructor.getStatus();
                for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                    if (firStatusTransformerExtension.needTransformStatus(firConstructor)) {
                        status2 = firStatusTransformerExtension.transformStatus(status2, constructor, (FirClassLikeSymbol<?>) firRegularClassSymbol, false);
                    }
                }
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl");
                FirDeclarationStatusImpl firDeclarationStatusImpl = (FirDeclarationStatusImpl) status2;
                if (firDeclarationStatusImpl != firResolvedDeclarationStatusImpl) {
                    Visibility visibility = firDeclarationStatusImpl.getVisibility();
                    Modality modality = firDeclarationStatusImpl.getModality();
                    if (modality == null) {
                        modality = firResolvedDeclarationStatusImpl.getModality();
                    }
                    firConstructor.replaceStatus(firDeclarationStatusImpl.resolved(visibility, modality, firResolvedDeclarationStatusImpl.getEffectiveVisibility()));
                }
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00f0  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirJavaClass createFirJavaClass(JavaClass javaClass, FirRegularClassSymbol classSymbol, FirRegularClassSymbol parentClassSymbol, ClassId classId, JavaTypeParameterStack javaTypeParameterStack) {
        EffectiveVisibility.Public effectiveVisibility;
        FirModuleData firModuleData;
        ValueParametersForAnnotationConstructor valueParametersForAnnotationConstructor = new ValueParametersForAnnotationConstructor();
        boolean z = JavaUtilsKt.getClassKind(javaClass) == ClassKind.ANNOTATION_CLASS;
        FirModuleData moduleDataForClass = getModuleDataForClass(javaClass);
        FirJavaClassBuilder firJavaClassBuilder = new FirJavaClassBuilder();
        firJavaClassBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        CollectionsKt.addAll(firJavaClassBuilder.getJavaAnnotations(), javaClass.mo7252getAnnotations());
        firJavaClassBuilder.setSource(toSourceElement$default(this, javaClass, null, 1, null));
        firJavaClassBuilder.setModuleData(moduleDataForClass);
        firJavaClassBuilder.setSymbol(classSymbol);
        firJavaClassBuilder.setName(javaClass.getName());
        firJavaClassBuilder.setFromSource(javaClass.isFromSource());
        Visibility visibility = javaClass.getVisibility();
        firJavaClassBuilder.setVisibility(visibility);
        firJavaClassBuilder.setClassKind(JavaUtilsKt.getClassKind(javaClass));
        firJavaClassBuilder.setModality(JavaUtilsKt.getModality(javaClass));
        firJavaClassBuilder.setTopLevel(classId.getOuterClassId() == null);
        firJavaClassBuilder.setStatic(javaClass.mo7267isStatic());
        firJavaClassBuilder.setJavaPackage((JavaPackage) this.packageCache.getValue(classSymbol.getClassId().getPackageFqName(), null));
        firJavaClassBuilder.setJavaTypeParameterStack(javaTypeParameterStack);
        CollectionsKt.addAll(firJavaClassBuilder.getExistingNestedClassifierNames(), javaClass.getInnerClassNames());
        firJavaClassBuilder.setScopeProvider(JavaScopeProvider.INSTANCE);
        EffectiveVisibility effectiveVisibility$default = EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, parentClassSymbol != null ? parentClassSymbol.getLookupTag() : null, true, false, 4, (Object) null);
        if (parentClassSymbol != null) {
            effectiveVisibility = this.parentClassEffectiveVisibilityCache.get(parentClassSymbol);
            if (effectiveVisibility == null) {
                FirDeclarationStatus status = ((FirMemberDeclaration) parentClassSymbol.getFir()).getStatus();
                FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
                if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
                    effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
                }
            }
            if (effectiveVisibility == null) {
                effectiveVisibility = EffectiveVisibility.Public.INSTANCE;
            }
        } else {
            effectiveVisibility = EffectiveVisibility.Public.INSTANCE;
        }
        EffectiveVisibility effectiveVisibilityLowerBound = effectiveVisibility.lowerBound(effectiveVisibility$default, TypeComponentsKt.getTypeContext(this.session));
        this.parentClassEffectiveVisibilityCache.put(classSymbol, effectiveVisibilityLowerBound);
        List<FirTypeParameter> listConvertTypeParameters = convertTypeParameters(javaClass.mo7258getTypeParameters(), javaTypeParameterStack, classSymbol, moduleDataForClass);
        CollectionsKt.addAll(firJavaClassBuilder.getTypeParameters(), listConvertTypeParameters);
        if (!firJavaClassBuilder.isStatic() && parentClassSymbol != null) {
            List<FirTypeParameterRef> typeParameters = firJavaClassBuilder.getTypeParameters();
            List<FirTypeParameterRef> typeParameters2 = ((FirRegularClass) parentClassSymbol.getFir()).getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
            for (FirTypeParameterRef firTypeParameterRef : typeParameters2) {
                FirOuterClassTypeParameterRefBuilder firOuterClassTypeParameterRefBuilder = new FirOuterClassTypeParameterRefBuilder();
                firOuterClassTypeParameterRefBuilder.setSymbol(firTypeParameterRef.getSymbol());
                arrayList.add(firOuterClassTypeParameterRefBuilder.build());
            }
            CollectionsKt.addAll(typeParameters, arrayList);
        }
        Collection<JavaClassifierType> supertypes = javaClass.getSupertypes();
        List<FirTypeRef> superTypeRefs = firJavaClassBuilder.getSuperTypeRefs();
        Iterator<T> it = supertypes.iterator();
        while (it.hasNext()) {
            superTypeRefs.add(JavaTypeConversionKt.toFirJavaTypeRef((JavaClassifierType) it.next(), this.session));
        }
        if (firJavaClassBuilder.getSuperTypeRefs().isEmpty()) {
            List<FirTypeRef> superTypeRefs2 = firJavaClassBuilder.getSuperTypeRefs();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getAny(), new ConeTypeProjection[0], false, null, 4, null));
            superTypeRefs2.add(firResolvedTypeRefBuilder.mo6809build());
        }
        List<FirTypeParameterRef> typeParameters3 = firJavaClassBuilder.getTypeParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters3, 10));
        Iterator<T> it2 = typeParameters3.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((FirTypeParameterRef) it2.next()).getSymbol());
        }
        ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(classId, arrayList2);
        Modality modality = firJavaClassBuilder.getModality();
        Intrinsics.checkNotNull(modality);
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, modality, effectiveVisibilityLowerBound);
        firResolvedDeclarationStatusImpl.setInner((firJavaClassBuilder.isTopLevel() || firJavaClassBuilder.isStatic()) ? false : true);
        firResolvedDeclarationStatusImpl.setFun(firJavaClassBuilder.getClassKind() == ClassKind.INTERFACE);
        firJavaClassBuilder.setStatus(firResolvedDeclarationStatusImpl);
        Iterator<JavaField> it3 = javaClass.mo7254getFields().iterator();
        while (it3.hasNext()) {
            firJavaClassBuilder.getDeclarations().add(convertJavaFieldToFir(it3.next(), classId, javaTypeParameterStack, coneClassLikeTypeDefaultType, moduleDataForClass));
        }
        Iterator<JavaMethod> it4 = javaClass.mo7255getMethods().iterator();
        while (it4.hasNext()) {
            JavaMethod next = it4.next();
            if (!JavaLoadingKt.isObjectMethodInInterface(next)) {
                Iterator<JavaMethod> it5 = it4;
                FirJavaMethod firJavaMethodConvertJavaMethodToFir = convertJavaMethodToFir(javaClass, next, classId, javaTypeParameterStack, coneClassLikeTypeDefaultType, moduleDataForClass);
                firJavaClassBuilder.getDeclarations().add(firJavaMethodConvertJavaMethodToFir);
                if (z) {
                    FirJavaValueParameter firJavaValueParameterConvertJavaAnnotationMethodToValueParameter = convertJavaAnnotationMethodToValueParameter(next, firJavaMethodConvertJavaMethodToFir, moduleDataForClass);
                    if (Intrinsics.areEqual(next.getName(), VALUE_METHOD_NAME)) {
                        valueParametersForAnnotationConstructor.setValueParameterForValue(TuplesKt.to(next, firJavaValueParameterConvertJavaAnnotationMethodToValueParameter));
                    } else {
                        valueParametersForAnnotationConstructor.getValueParameters().put(next, firJavaValueParameterConvertJavaAnnotationMethodToValueParameter);
                    }
                }
                it4 = it5;
            }
        }
        Collection<JavaConstructor> collectionMo7253getConstructors = javaClass.mo7253getConstructors();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        FqName relativeClassName = classId.getRelativeClassName();
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        CallableId callableId = new CallableId(packageFqName, relativeClassName, shortClassName, null, 8, null);
        if (collectionMo7253getConstructors.isEmpty() && JavaUtilsKt.getClassKind(javaClass) == ClassKind.CLASS && !javaClass.mo7266isRecord() && javaClass.mo7259hasDefaultConstructor()) {
            firJavaClassBuilder.getDeclarations().add(convertJavaConstructorToFir(null, callableId, javaClass, firJavaClassBuilder, listConvertTypeParameters, javaTypeParameterStack, parentClassSymbol, moduleDataForClass));
        }
        Iterator<JavaConstructor> it6 = collectionMo7253getConstructors.iterator();
        while (it6.hasNext()) {
            firJavaClassBuilder.getDeclarations().add(convertJavaConstructorToFir(it6.next(), callableId, javaClass, firJavaClassBuilder, listConvertTypeParameters, javaTypeParameterStack, parentClassSymbol, moduleDataForClass));
        }
        if (firJavaClassBuilder.getClassKind() == ClassKind.ENUM_CLASS) {
            FirJavaClassBuilder firJavaClassBuilder2 = firJavaClassBuilder;
            FqName packageFqName2 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
            FqName relativeClassName2 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName2, "getRelativeClassName(...)");
            firModuleData = moduleDataForClass;
            EnumClassUtilsKt.generateValuesFunction$default(firJavaClassBuilder2, firModuleData, packageFqName2, relativeClassName2, false, null, 24, null);
            FqName packageFqName3 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName3, "getPackageFqName(...)");
            FqName relativeClassName3 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName3, "getRelativeClassName(...)");
            EnumClassUtilsKt.generateValueOfFunction$default(firJavaClassBuilder2, firModuleData, packageFqName3, relativeClassName3, false, null, 24, null);
            FqName packageFqName4 = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName4, "getPackageFqName(...)");
            FqName relativeClassName4 = classId.getRelativeClassName();
            Intrinsics.checkNotNullExpressionValue(relativeClassName4, "getRelativeClassName(...)");
            EnumClassUtilsKt.generateEntriesGetter$default(firJavaClassBuilder2, firModuleData, packageFqName4, relativeClassName4, false, null, 24, null);
        } else {
            firModuleData = moduleDataForClass;
        }
        if (z) {
            firJavaClassBuilder.getDeclarations().add(buildConstructorForAnnotationClass(javaClass, callableId, firJavaClassBuilder, valueParametersForAnnotationConstructor, firModuleData));
        }
        if (javaClass.mo7266isRecord() && javaClass.isFromSource()) {
            createDeclarationsForJavaRecord(javaClass, classId, firModuleData, coneClassLikeTypeDefaultType, listConvertTypeParameters, firJavaClassBuilder.getDeclarations());
        }
        FirJavaClass firJavaClassBuild = firJavaClassBuilder.mo6809build();
        if (firJavaClassBuild.getStatus().getModality() == Modality.SEALED) {
            final Collection<JavaClassifierType> permittedTypes = javaClass.getPermittedTypes();
            SealedClassInheritorsKt.setSealedClassInheritors(firJavaClassBuild, new Function0<List<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$createFirJavaClass$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends ClassId> invoke() {
                    Collection<JavaClassifierType> collection = permittedTypes;
                    ArrayList arrayList3 = new ArrayList();
                    Iterator<T> it7 = collection.iterator();
                    while (it7.hasNext()) {
                        JavaClassifier classifier = ((JavaClassifierType) it7.next()).getClassifier();
                        ClassId classIdMapJavaToKotlin = null;
                        JavaClass javaClass2 = classifier instanceof JavaClass ? (JavaClass) classifier : null;
                        if (javaClass2 != null) {
                            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
                            FqName fqName = javaClass2.getFqName();
                            Intrinsics.checkNotNull(fqName);
                            classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlin(fqName);
                        }
                        if (classIdMapJavaToKotlin != null) {
                            arrayList3.add(classIdMapJavaToKotlin);
                        }
                    }
                    return arrayList3;
                }
            });
        }
        if (z) {
            Pair<JavaMethod, FirJavaValueParameter> valueParameterForValue = valueParametersForAnnotationConstructor.getValueParameterForValue();
            if (valueParameterForValue != null) {
                JavaMethod javaMethodComponent1 = valueParameterForValue.component1();
                FirJavaValueParameter firJavaValueParameterComponent2 = valueParameterForValue.component2();
                JavaAnnotationArgument annotationParameterDefaultValue = javaMethodComponent1.getAnnotationParameterDefaultValue();
                if (annotationParameterDefaultValue != null) {
                    firJavaValueParameterComponent2.setDefaultValue(JavaAnnotationsMappingKt.toFirExpression(annotationParameterDefaultValue, this.session, javaTypeParameterStack, firJavaValueParameterComponent2.getReturnTypeRef()));
                }
            }
            for (Map.Entry<JavaMethod, FirJavaValueParameter> entry : valueParametersForAnnotationConstructor.getValueParameters().entrySet()) {
                JavaMethod key = entry.getKey();
                FirJavaValueParameter value = entry.getValue();
                JavaAnnotationArgument annotationParameterDefaultValue2 = key.getAnnotationParameterDefaultValue();
                if (annotationParameterDefaultValue2 != null) {
                    value.setDefaultValue(JavaAnnotationsMappingKt.toFirExpression(annotationParameterDefaultValue2, this.session, javaTypeParameterStack, value.getReturnTypeRef()));
                }
            }
        }
        if (javaClass.mo7266isRecord()) {
            ClassMembersKt.setJavaRecord(firJavaClassBuild, true);
        }
        return firJavaClassBuild;
    }

    private final void createDeclarationsForJavaRecord(JavaClass javaClass, ClassId classId, FirModuleData moduleData, ConeClassLikeType classType, List<? extends FirTypeParameter> classTypeParameters, List<FirDeclaration> destination) {
        List<FirDeclaration> list = destination;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof FirJavaMethod) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            Name name2 = ((FirJavaMethod) obj2).getName();
            Object obj3 = linkedHashMap.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        for (JavaRecordComponent javaRecordComponent : javaClass.mo7257getRecordComponents()) {
            Name name3 = javaRecordComponent.getName();
            List listEmptyList = (List) linkedHashMap.get(name3);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            List list2 = listEmptyList;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                do {
                    if (it.hasNext()) {
                    }
                } while (!((FirJavaMethod) it.next()).getValueParameters().isEmpty());
            }
            CallableId callableId = new CallableId(classId, name3);
            FirJavaMethodBuilder firJavaMethodBuilder = new FirJavaMethodBuilder();
            firJavaMethodBuilder.setModuleData(moduleData);
            firJavaMethodBuilder.setSource(toSourceElement(javaRecordComponent, KtFakeSourceElementKind.JavaRecordComponentFunction.INSTANCE));
            firJavaMethodBuilder.setSymbol(new FirNamedFunctionSymbol(callableId));
            firJavaMethodBuilder.setName(name3);
            firJavaMethodBuilder.setFromSource(javaRecordComponent.isFromSource());
            firJavaMethodBuilder.setReturnTypeRef(JavaTypeConversionKt.toFirJavaTypeRef(javaRecordComponent.getType(), this.session));
            firJavaMethodBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$createDeclarationsForJavaRecord$2$1
                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirAnnotation> invoke() {
                    return CollectionsKt.emptyList();
                }
            });
            firJavaMethodBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE));
            firJavaMethodBuilder.setDispatchReceiverType(classType);
            FirJavaMethod firJavaMethodBuild = firJavaMethodBuilder.mo6809build();
            ClassMembersKt.setJavaRecordComponent(firJavaMethodBuild, true);
            destination.add(firJavaMethodBuild);
        }
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (FirDeclaration firDeclaration : list) {
                if ((firDeclaration instanceof FirJavaConstructor) && ((FirJavaConstructor) firDeclaration).getIsPrimary()) {
                    return;
                }
            }
        }
        List<FirDeclaration> list3 = destination;
        FirJavaConstructorBuilder firJavaConstructorBuilder = new FirJavaConstructorBuilder();
        firJavaConstructorBuilder.setSource(toSourceElement(javaClass, KtFakeSourceElementKind.ImplicitJavaRecordConstructor.INSTANCE));
        firJavaConstructorBuilder.setModuleData(moduleData);
        firJavaConstructorBuilder.setFromSource(javaClass.isFromSource());
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        firJavaConstructorBuilder.setSymbol(new FirConstructorSymbol(new CallableId(classId, shortClassName)));
        firJavaConstructorBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE));
        firJavaConstructorBuilder.setVisibility(Visibilities.Public.INSTANCE);
        firJavaConstructorBuilder.setPrimary(true);
        firJavaConstructorBuilder.setReturnTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(classType, null, null, 3, null));
        firJavaConstructorBuilder.setDispatchReceiverType(null);
        CollectionsKt.addAll(firJavaConstructorBuilder.getTypeParameters(), toRefs(classTypeParameters));
        firJavaConstructorBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$createDeclarationsForJavaRecord$5$1
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return CollectionsKt.emptyList();
            }
        });
        Collection<JavaRecordComponent> collectionMo7257getRecordComponents = javaClass.mo7257getRecordComponents();
        List<FirValueParameter> valueParameters = firJavaConstructorBuilder.getValueParameters();
        for (JavaRecordComponent javaRecordComponent2 : collectionMo7257getRecordComponents) {
            FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
            firJavaValueParameterBuilder.setContainingFunctionSymbol(firJavaConstructorBuilder.getSymbol());
            firJavaValueParameterBuilder.setSource(toSourceElement(javaRecordComponent2, KtFakeSourceElementKind.ImplicitRecordConstructorParameter.INSTANCE));
            firJavaValueParameterBuilder.setModuleData(moduleData);
            firJavaValueParameterBuilder.setFromSource(javaRecordComponent2.isFromSource());
            firJavaValueParameterBuilder.setReturnTypeRef(JavaTypeConversionKt.toFirJavaTypeRef(javaRecordComponent2.getType(), this.session));
            firJavaValueParameterBuilder.setName(javaRecordComponent2.getName());
            firJavaValueParameterBuilder.setVararg(javaRecordComponent2.getIsVararg());
            firJavaValueParameterBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$createDeclarationsForJavaRecord$5$2$1$1
                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirAnnotation> invoke() {
                    return CollectionsKt.emptyList();
                }
            });
            valueParameters.add(firJavaValueParameterBuilder.build());
        }
        FirJavaConstructor firJavaConstructorBuild = firJavaConstructorBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firJavaConstructorBuild, classType.getLookupTag());
        list3.add(firJavaConstructorBuild);
    }

    private final FirDeclaration convertJavaFieldToFir(final JavaField javaField, ClassId classId, JavaTypeParameterStack javaTypeParameterStack, ConeClassLikeType dispatchReceiver, FirModuleData moduleData) {
        Name name2 = javaField.getName();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        CallableId callableId = new CallableId(packageFqName, classId.getRelativeClassName(), name2, null, 8, null);
        JavaType javaTypeMo7275getType = javaField.getType();
        if (javaField.getIsEnumEntry()) {
            FirEnumEntryBuilder firEnumEntryBuilder = new FirEnumEntryBuilder();
            firEnumEntryBuilder.setSource(toSourceElement$default(this, javaField, null, 1, null));
            firEnumEntryBuilder.setModuleData(moduleData);
            firEnumEntryBuilder.setSymbol(new FirEnumEntrySymbol(callableId));
            firEnumEntryBuilder.setName(name2);
            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(javaField.getVisibility(), JavaUtilsKt.getModality(javaField), EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(javaField.getVisibility(), dispatchReceiver.getLookupTag(), false, false, 6, (Object) null));
            firResolvedDeclarationStatusImpl.setStatic(javaField.mo7267isStatic());
            firEnumEntryBuilder.setStatus(firResolvedDeclarationStatusImpl);
            firEnumEntryBuilder.setReturnTypeRef(JavaTypeConversionKt.resolveIfJavaType(JavaTypeConversionKt.toFirJavaTypeRef(javaTypeMo7275getType, this.session), this.session, javaTypeParameterStack, FirJavaTypeConversionMode.ANNOTATION_MEMBER));
            firEnumEntryBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firEnumEntryBuilder.setOrigin(UtilsKt.javaOrigin(javaField.isFromSource()));
            FirEnumEntry firEnumEntryBuild = firEnumEntryBuilder.mo6809build();
            ClassMembersKt.setContainingClassForStaticMemberAttr(firEnumEntryBuild, TypeConstructionUtilsKt.toLookupTag(classId));
            JavaAnnotationsMappingKt.setAnnotationsFromJava(firEnumEntryBuild, this.session, javaField);
            return firEnumEntryBuild;
        }
        FirJavaFieldBuilder firJavaFieldBuilder = new FirJavaFieldBuilder();
        firJavaFieldBuilder.setSource(toSourceElement$default(this, javaField, null, 1, null));
        firJavaFieldBuilder.setModuleData(moduleData);
        firJavaFieldBuilder.setSymbol(new FirFieldSymbol(callableId));
        firJavaFieldBuilder.setName(name2);
        firJavaFieldBuilder.setFromSource(javaField.isFromSource());
        JavaField javaField2 = javaField;
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl2 = new FirResolvedDeclarationStatusImpl(javaField.getVisibility(), JavaUtilsKt.getModality(javaField2), EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(javaField.getVisibility(), dispatchReceiver.getLookupTag(), false, false, 6, (Object) null));
        firResolvedDeclarationStatusImpl2.setStatic(javaField.mo7267isStatic());
        firJavaFieldBuilder.setStatus(firResolvedDeclarationStatusImpl2);
        firJavaFieldBuilder.setVisibility(javaField.getVisibility());
        firJavaFieldBuilder.setModality(JavaUtilsKt.getModality(javaField2));
        firJavaFieldBuilder.setReturnTypeRef(JavaTypeConversionKt.toFirJavaTypeRef(javaTypeMo7275getType, this.session));
        firJavaFieldBuilder.setVar(!javaField.mo7264isFinal());
        firJavaFieldBuilder.setStatic(javaField.mo7267isStatic());
        firJavaFieldBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaFieldToFir$3$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return JavaAnnotationsMappingKt.convertAnnotationsToFir(javaField, this.session);
            }
        });
        firJavaFieldBuilder.setLazyInitializer(LazyKt.lazy(new Function0<FirExpression>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaFieldToFir$3$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirExpression invoke() {
                Object initializerValue = javaField.getInitializerValue();
                if (initializerValue != null) {
                    return JavaUtilsKt.createConstantIfAny$default(initializerValue, this.session, false, 2, null);
                }
                return null;
            }
        }));
        if (!javaField.mo7267isStatic()) {
            firJavaFieldBuilder.setDispatchReceiverType(dispatchReceiver);
        }
        FirJavaField firJavaFieldBuild = firJavaFieldBuilder.mo6809build();
        if (javaField.mo7267isStatic()) {
            ClassMembersKt.setContainingClassForStaticMemberAttr(firJavaFieldBuild, TypeConstructionUtilsKt.toLookupTag(classId));
        }
        return firJavaFieldBuild;
    }

    private final FirJavaMethod convertJavaMethodToFir(JavaClass containingClass, final JavaMethod javaMethod, ClassId classId, JavaTypeParameterStack javaTypeParameterStack, ConeClassLikeType dispatchReceiver, FirModuleData moduleData) {
        Name name2 = javaMethod.getName();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        FirNamedFunctionSymbol firNamedFunctionSymbol = new FirNamedFunctionSymbol(new CallableId(packageFqName, classId.getRelativeClassName(), name2, null, 8, null));
        JavaType returnType = javaMethod.getReturnType();
        FirJavaMethodBuilder firJavaMethodBuilder = new FirJavaMethodBuilder();
        firJavaMethodBuilder.setModuleData(moduleData);
        firJavaMethodBuilder.setSource(toSourceElement$default(this, javaMethod, null, 1, null));
        firJavaMethodBuilder.setSymbol(firNamedFunctionSymbol);
        firJavaMethodBuilder.setName(name2);
        firJavaMethodBuilder.setFromSource(javaMethod.isFromSource());
        firJavaMethodBuilder.setReturnTypeRef(JavaTypeConversionKt.toFirJavaTypeRef(returnType, this.session));
        firJavaMethodBuilder.setStatic(javaMethod.mo7267isStatic());
        CollectionsKt.addAll(firJavaMethodBuilder.getTypeParameters(), convertTypeParameters(javaMethod.mo7258getTypeParameters(), javaTypeParameterStack, firNamedFunctionSymbol, moduleData));
        Iterator<JavaValueParameter> it = javaMethod.getValueParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            firJavaMethodBuilder.getValueParameters().add(JavaAnnotationsMappingKt.toFirValueParameter(it.next(), this.session, firNamedFunctionSymbol, moduleData, i));
            i++;
        }
        firJavaMethodBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaMethodToFir$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return JavaAnnotationsMappingKt.convertAnnotationsToFir(javaMethod, this.session);
            }
        });
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(javaMethod.getVisibility(), JavaUtilsKt.getModality(javaMethod), EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(javaMethod.getVisibility(), dispatchReceiver.getLookupTag(), false, false, 6, (Object) null));
        firResolvedDeclarationStatusImpl.setStatic(javaMethod.mo7267isStatic());
        firResolvedDeclarationStatusImpl.setHasStableParameterNames(false);
        firJavaMethodBuilder.setStatus(firResolvedDeclarationStatusImpl);
        if (!javaMethod.mo7267isStatic()) {
            firJavaMethodBuilder.setDispatchReceiverType(dispatchReceiver);
        }
        FirJavaMethod firJavaMethodBuild = firJavaMethodBuilder.mo6809build();
        if (javaMethod.mo7267isStatic()) {
            ClassMembersKt.setContainingClassForStaticMemberAttr(firJavaMethodBuild, TypeConstructionUtilsKt.toLookupTag(classId));
        }
        if (containingClass.mo7266isRecord() && firJavaMethodBuild.getValueParameters().isEmpty()) {
            Collection<JavaRecordComponent> collectionMo7257getRecordComponents = containingClass.mo7257getRecordComponents();
            if (!(collectionMo7257getRecordComponents instanceof Collection) || !collectionMo7257getRecordComponents.isEmpty()) {
                Iterator<T> it2 = collectionMo7257getRecordComponents.iterator();
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(((JavaRecordComponent) it2.next()).getName(), name2)) {
                        ClassMembersKt.setJavaRecordComponent(firJavaMethodBuild, true);
                        break;
                    }
                }
            }
        }
        return firJavaMethodBuild;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00a3  */
    private final FirJavaConstructor convertJavaConstructorToFir(final JavaConstructor javaConstructor, CallableId constructorId, JavaClass javaClass, FirJavaClassBuilder ownerClassBuilder, List<? extends FirTypeParameter> classTypeParameters, JavaTypeParameterStack javaTypeParameterStack, FirRegularClassSymbol outerClassSymbol, FirModuleData moduleData) {
        KtSourceElement sourceElement;
        Visibility visibility;
        boolean z;
        ConeClassLikeType coneClassLikeTypeDefaultType;
        FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(constructorId);
        FirJavaConstructorBuilder firJavaConstructorBuilder = new FirJavaConstructorBuilder();
        if (javaConstructor == null || (sourceElement = toSourceElement$default(this, javaConstructor, null, 1, null)) == null) {
            sourceElement = toSourceElement(javaClass, KtFakeSourceElementKind.ImplicitConstructor.INSTANCE);
        }
        firJavaConstructorBuilder.setSource(sourceElement);
        firJavaConstructorBuilder.setModuleData(moduleData);
        firJavaConstructorBuilder.setFromSource(javaClass.isFromSource());
        firJavaConstructorBuilder.setSymbol(firConstructorSymbol);
        int i = 0;
        firJavaConstructorBuilder.setInner((javaClass.getOuterClass() == null || javaClass.mo7267isStatic()) ? false : true);
        boolean zIsInner = firJavaConstructorBuilder.isInner();
        if (javaConstructor == null || (visibility = javaConstructor.getVisibility()) == null) {
            visibility = ownerClassBuilder.getVisibility();
        }
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, Modality.FINAL, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) ownerClassBuilder.getSymbol(), false, false, 6, (Object) null));
        firResolvedDeclarationStatusImpl.setInner(zIsInner);
        firResolvedDeclarationStatusImpl.setHasStableParameterNames(false);
        firJavaConstructorBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firJavaConstructorBuilder.setVisibility(visibility);
        if (javaConstructor == null) {
            z = true;
        } else {
            KtSourceElement source = firJavaConstructorBuilder.getSource();
            if ((source != null ? KtSourceElementKt.getPsi(source) : null) instanceof LightRecordCanonicalConstructor) {
                z = true;
            } else {
                z = false;
            }
        }
        firJavaConstructorBuilder.setPrimary(z);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(buildSelfTypeRef(ownerClassBuilder));
        firJavaConstructorBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        if (zIsInner) {
            coneClassLikeTypeDefaultType = outerClassSymbol != null ? ScopeUtilsKt.defaultType(outerClassSymbol) : null;
        } else {
            coneClassLikeTypeDefaultType = null;
        }
        firJavaConstructorBuilder.setDispatchReceiverType(coneClassLikeTypeDefaultType);
        CollectionsKt.addAll(firJavaConstructorBuilder.getTypeParameters(), toRefs(classTypeParameters));
        if (javaConstructor != null) {
            CollectionsKt.addAll(firJavaConstructorBuilder.getTypeParameters(), convertTypeParameters(javaConstructor.mo7258getTypeParameters(), javaTypeParameterStack, firConstructorSymbol, moduleData));
            firJavaConstructorBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaConstructorToFir$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirAnnotation> invoke() {
                    return JavaAnnotationsMappingKt.convertAnnotationsToFir(javaConstructor, this.session);
                }
            });
            Iterator<JavaValueParameter> it = javaConstructor.getValueParameters().iterator();
            while (it.hasNext()) {
                firJavaConstructorBuilder.getValueParameters().add(JavaAnnotationsMappingKt.toFirValueParameter(it.next(), this.session, firConstructorSymbol, moduleData, i));
                i++;
            }
        } else {
            firJavaConstructorBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaConstructorToFir$1$4
                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirAnnotation> invoke() {
                    return CollectionsKt.emptyList();
                }
            });
        }
        FirJavaConstructor firJavaConstructorBuild = firJavaConstructorBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firJavaConstructorBuild, ownerClassBuilder.getSymbol().getLookupTag());
        return firJavaConstructorBuild;
    }

    private final ConeKotlinType buildSelfTypeRef(FirJavaClassBuilder firJavaClassBuilder) {
        FirRegularClassSymbol symbol = firJavaClassBuilder.getSymbol();
        List<FirTypeParameterRef> typeParameters = firJavaClassBuilder.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        return TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) symbol, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]), false, (ConeAttributes) null, 4, (Object) null);
    }

    private final String topLevelName(FqName fqName) {
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.substringBefore$default(strAsString, ".", (String) null, 2, (Object) null);
    }

    static /* synthetic */ KtSourceElement toSourceElement$default(FirJavaFacade firJavaFacade, JavaElement javaElement, KtSourceElementKind ktSourceElementKind, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toSourceElement");
        }
        if ((i & 1) != 0) {
            ktSourceElementKind = KtRealSourceElementKind.INSTANCE;
        }
        return firJavaFacade.toSourceElement(javaElement, ktSourceElementKind);
    }

    private final KtSourceElement toSourceElement(JavaElement javaElement, KtSourceElementKind ktSourceElementKind) {
        PsiElement psi;
        KtSourceElement ktFakeSourceElement = null;
        JavaElementImpl javaElementImpl = javaElement instanceof JavaElementImpl ? (JavaElementImpl) javaElement : null;
        if (javaElementImpl != null && (psi = javaElementImpl.getPsi()) != null) {
            if (ktSourceElementKind instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(psi);
            } else {
                if (!(ktSourceElementKind instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(psi, (KtFakeSourceElementKind) ktSourceElementKind);
            }
        }
        return ktFakeSourceElement;
    }

    private final List<FirTypeParameterRef> toRefs(List<? extends FirTypeParameter> list) {
        List<? extends FirTypeParameter> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (FirTypeParameter firTypeParameter : list2) {
            FirConstructedClassTypeParameterRefBuilder firConstructedClassTypeParameterRefBuilder = new FirConstructedClassTypeParameterRefBuilder();
            firConstructedClassTypeParameterRefBuilder.setSymbol(firTypeParameter.getSymbol());
            arrayList.add(firConstructedClassTypeParameterRefBuilder.build());
        }
        return arrayList;
    }

    private final FirTypeParameter toFirTypeParameter(JavaTypeParameter javaTypeParameter, JavaTypeParameterStack javaTypeParameterStack, FirBasedSymbol<?> firBasedSymbol, FirModuleData firModuleData) {
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        firTypeParameterBuilder.setModuleData(firModuleData);
        firTypeParameterBuilder.setOrigin(UtilsKt.javaOrigin(javaTypeParameter.isFromSource()));
        firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        firTypeParameterBuilder.setName(javaTypeParameter.getName());
        firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
        firTypeParameterBuilder.setVariance(Variance.INVARIANT);
        firTypeParameterBuilder.setReified(false);
        javaTypeParameterStack.addParameter(javaTypeParameter, firTypeParameterBuilder.getSymbol());
        firTypeParameterBuilder.setContainingDeclarationSymbol(firBasedSymbol);
        Iterator<JavaClassifierType> it = javaTypeParameter.getUpperBounds().iterator();
        while (it.hasNext()) {
            firTypeParameterBuilder.getBounds().add(JavaTypeConversionKt.toFirJavaTypeRef(it.next(), this.session));
        }
        if (firTypeParameterBuilder.getBounds().isEmpty()) {
            List<FirTypeRef> bounds = firTypeParameterBuilder.getBounds();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeFlexibleType(this.builtinTypes.getAnyType().getType(), this.builtinTypes.getNullableAnyType().getType()));
            bounds.add(firResolvedTypeRefBuilder.mo6809build());
        }
        FirTypeParameter firTypeParameterBuild = firTypeParameterBuilder.mo6809build();
        JavaAnnotationsMappingKt.setAnnotationsFromJava(firTypeParameterBuild, this.session, javaTypeParameter);
        return firTypeParameterBuild;
    }

    private final FirJavaValueParameter convertJavaAnnotationMethodToValueParameter(JavaMethod javaMethod, FirJavaMethod firJavaMethod, FirModuleData moduleData) {
        FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
        firJavaValueParameterBuilder.setSource(toSourceElement(javaMethod, KtFakeSourceElementKind.ImplicitJavaAnnotationConstructor.INSTANCE));
        firJavaValueParameterBuilder.setModuleData(moduleData);
        firJavaValueParameterBuilder.setFromSource(javaMethod.isFromSource());
        firJavaValueParameterBuilder.setReturnTypeRef(firJavaMethod.getReturnTypeRef());
        firJavaValueParameterBuilder.setContainingFunctionSymbol(firJavaMethod.getSymbol());
        firJavaValueParameterBuilder.setName(javaMethod.getName());
        firJavaValueParameterBuilder.setVararg((javaMethod.getReturnType() instanceof JavaArrayType) && Intrinsics.areEqual(javaMethod.getName(), VALUE_METHOD_NAME));
        firJavaValueParameterBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$convertJavaAnnotationMethodToValueParameter$1$1
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return CollectionsKt.emptyList();
            }
        });
        return firJavaValueParameterBuilder.build();
    }

    private final FirJavaConstructor buildConstructorForAnnotationClass(JavaClass javaClass, CallableId constructorId, FirJavaClassBuilder ownerClassBuilder, ValueParametersForAnnotationConstructor valueParametersForAnnotationConstructor, FirModuleData moduleData) {
        FirJavaConstructorBuilder firJavaConstructorBuilder = new FirJavaConstructorBuilder();
        firJavaConstructorBuilder.setSource(toSourceElement(javaClass, KtFakeSourceElementKind.ImplicitConstructor.INSTANCE));
        firJavaConstructorBuilder.setModuleData(moduleData);
        firJavaConstructorBuilder.setFromSource(javaClass.isFromSource());
        firJavaConstructorBuilder.setSymbol(new FirConstructorSymbol(constructorId));
        firJavaConstructorBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE));
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(buildSelfTypeRef(ownerClassBuilder));
        firJavaConstructorBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        Pair<JavaMethod, FirJavaValueParameter> valueParameterForValue = valueParametersForAnnotationConstructor.getValueParameterForValue();
        if (valueParameterForValue != null) {
            valueParameterForValue.component1();
            firJavaConstructorBuilder.getValueParameters().add(valueParameterForValue.component2());
        }
        for (Map.Entry<JavaMethod, FirJavaValueParameter> entry : valueParametersForAnnotationConstructor.getValueParameters().entrySet()) {
            entry.getKey();
            firJavaConstructorBuilder.getValueParameters().add(entry.getValue());
        }
        firJavaConstructorBuilder.setVisibility(Visibilities.Public.INSTANCE);
        firJavaConstructorBuilder.setInner(false);
        firJavaConstructorBuilder.setPrimary(true);
        firJavaConstructorBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaFacade$buildConstructorForAnnotationClass$1$3
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return CollectionsKt.emptyList();
            }
        });
        FirJavaConstructor firJavaConstructorBuild = firJavaConstructorBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firJavaConstructorBuild, ownerClassBuilder.getSymbol().getLookupTag());
        return firJavaConstructorBuild;
    }
}
