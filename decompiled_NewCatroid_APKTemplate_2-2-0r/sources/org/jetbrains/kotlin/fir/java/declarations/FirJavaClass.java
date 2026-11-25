package org.jetbrains.kotlin.fir.java.declarations;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.java.JavaAnnotationsMappingKt;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJavaClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B©\u0001\b\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u0012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0014\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\u0006\u0010!\u001a\u00020\"\u0012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\u001b¢\u0006\u0002\u0010$J5\u0010^\u001a\u00020_\"\u0004\b\u0000\u0010`\"\u0004\b\u0001\u0010a2\u0012\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H`\u0012\u0004\u0012\u0002Ha0c2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010eJ\u0016\u0010f\u001a\u00020_2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020&0\u001bH\u0016J\u0012\u0010h\u001a\u00020_2\b\u0010i\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010j\u001a\u00020_2\b\u0010k\u001a\u0004\u0018\u000108H\u0016J\u0010\u0010l\u001a\u00020_2\u0006\u0010m\u001a\u00020=H\u0016J\u0010\u0010n\u001a\u00020_2\u0006\u0010o\u001a\u00020\u0010H\u0016J\u0016\u0010p\u001a\u00020_2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0016J)\u0010r\u001a\u00020\u0000\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010uJ)\u0010v\u001a\u00020\u0000\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010uJ)\u0010w\u001a\u00020\u0000\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010uJ)\u0010x\u001a\u00020\u0000\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010uJ)\u0010y\u001a\u00020\u0001\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010zJ)\u0010{\u001a\u00020\u0001\"\u0004\b\u0000\u0010a2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Ha0t2\u0006\u0010d\u001a\u0002HaH\u0016¢\u0006\u0002\u0010zR!\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b'\u0010(R\u0014\u0010+\u001a\u00020,X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0016\u00101\u001a\u0004\u0018\u00010\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u001a\u00104\u001a\b\u0012\u0004\u0012\u0002050\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u0010(R\u0016\u00107\u001a\u0004\u0018\u0001088VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010(R\u001b\u0010<\u001a\u00020=8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b@\u0010*\u001a\u0004\b>\u0010?R\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\u001bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bA\u0010(R\u0014\u0010B\u001a\u00020C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0016\u0010\u001f\u001a\u0004\u0018\u00010 X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR!\u0010X\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bZ\u0010*\u001a\u0004\bY\u0010(R\u0014\u0010\u0018\u001a\u00020\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b[\u00103R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010(R\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010]R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006|"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "name", "Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "unEnhancedAnnotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "declarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "unenhnancedSuperTypes", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "javaPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "existingNestedClassifierNames", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/descriptors/ClassKind;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "companionObjectSymbol", "getCompanionObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getDeclarations", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "deprecationsProvider$delegate", "getExistingNestedClassifierNames$java", "hasLazyNestedClassifiers", "", "getHasLazyNestedClassifiers", "()Z", "getJavaPackage$java", "()Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "getJavaTypeParameterStack", "()Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "superTypeRefs", "getSuperTypeRefs", "superTypeRefs$delegate", "getSymbol", "getTypeParameters", "Ljava/util/List;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceCompanionObjectSymbol", "newCompanionObjectSymbol", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceStatus", "newStatus", "replaceSuperTypeRefs", "newSuperTypeRefs", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", "transformChildren", "transformDeclarations", "transformStatus", "transformSuperTypeRefs", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformTypeParameters", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaClass extends FirRegularClass {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final FirDeclarationAttributes attributes;
    private final ClassKind classKind;
    private final List<FirDeclaration> declarations;

    /* JADX INFO: renamed from: deprecationsProvider$delegate, reason: from kotlin metadata */
    private final Lazy deprecationsProvider;
    private final List<Name> existingNestedClassifierNames;
    private final JavaPackage javaPackage;
    private final JavaTypeParameterStack javaTypeParameterStack;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin.Java origin;
    private final FirScopeProvider scopeProvider;
    private final KtSourceElement source;
    private FirDeclarationStatus status;

    /* JADX INFO: renamed from: superTypeRefs$delegate, reason: from kotlin metadata */
    private final Lazy superTypeRefs;
    private final FirRegularClassSymbol symbol;
    private final List<FirTypeParameterRef> typeParameters;
    private final List<T> unEnhancedAnnotations;
    private final List<FirTypeRef> unenhnancedSuperTypes;

    @FirImplementationDetail
    public /* synthetic */ FirJavaClass(KtSourceElement ktSourceElement, FirModuleData firModuleData, FirResolvePhase firResolvePhase, Name name2, FirDeclarationOrigin.Java java2, List list, FirDeclarationStatus firDeclarationStatus, ClassKind classKind, List list2, FirScopeProvider firScopeProvider, FirRegularClassSymbol firRegularClassSymbol, List list3, List list4, JavaPackage javaPackage, JavaTypeParameterStack javaTypeParameterStack, List list5, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firModuleData, firResolvePhase, name2, java2, list, firDeclarationStatus, classKind, list2, firScopeProvider, firRegularClassSymbol, list3, list4, javaPackage, javaTypeParameterStack, list5);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public FirRegularClassSymbol getCompanionObjectSymbol() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public boolean getHasLazyNestedClassifiers() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public void replaceCompanionObjectSymbol(FirRegularClassSymbol newCompanionObjectSymbol) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirJavaClass transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirClass transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirClassLikeDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirRegularClass transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public /* bridge */ /* synthetic */ FirClass transformDeclarations(FirTransformer firTransformer, Object obj) {
        return transformDeclarations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public /* bridge */ /* synthetic */ FirRegularClass transformDeclarations(FirTransformer firTransformer, Object obj) {
        return transformDeclarations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirClass transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirClassLikeDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirRegularClass transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public /* bridge */ /* synthetic */ FirClass transformSuperTypeRefs(FirTransformer firTransformer, Object obj) {
        return transformSuperTypeRefs((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirClass transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirClassLikeDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirTypeParameterRefsOwner transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin.Java getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public ClassKind getClassKind() {
        return this.classKind;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public FirScopeProvider getScopeProvider() {
        return this.scopeProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirRegularClassSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    /* JADX INFO: renamed from: getJavaPackage$java, reason: from getter */
    public final JavaPackage getJavaPackage() {
        return this.javaPackage;
    }

    public final JavaTypeParameterStack getJavaTypeParameterStack() {
        return this.javaTypeParameterStack;
    }

    public final List<Name> getExistingNestedClassifierNames$java() {
        return this.existingNestedClassifierNames;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirJavaClass(KtSourceElement ktSourceElement, FirModuleData moduleData, FirResolvePhase resolvePhase, Name name2, FirDeclarationOrigin.Java origin, List<T> list, FirDeclarationStatus status, ClassKind classKind, List<FirDeclaration> declarations, FirScopeProvider scopeProvider, FirRegularClassSymbol symbol, List<? extends FirTypeRef> unenhnancedSuperTypes, List<FirTypeParameterRef> typeParameters, JavaPackage javaPackage, JavaTypeParameterStack javaTypeParameterStack, List<Name> existingNestedClassifierNames) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(unenhnancedSuperTypes, "unenhnancedSuperTypes");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        Intrinsics.checkNotNullParameter(existingNestedClassifierNames, "existingNestedClassifierNames");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.name = name2;
        this.origin = origin;
        this.unEnhancedAnnotations = list;
        this.status = status;
        this.classKind = classKind;
        this.declarations = declarations;
        this.scopeProvider = scopeProvider;
        this.symbol = symbol;
        this.unenhnancedSuperTypes = unenhnancedSuperTypes;
        this.typeParameters = typeParameters;
        this.javaPackage = javaPackage;
        this.javaTypeParameterStack = javaTypeParameterStack;
        this.existingNestedClassifierNames = existingNestedClassifierNames;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
        this.attributes = new FirDeclarationAttributes();
        this.superTypeRefs = LazyKt.lazy(new Function0<List<? extends FirTypeRef>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaClass$superTypeRefs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirTypeRef> invoke() {
                FirJavaClass firJavaClass = this.this$0;
                return new FirSignatureEnhancement(firJavaClass, firJavaClass.getModuleData().getSession(), new Function1<FirSimpleFunction, List<? extends FirCallableDeclaration>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaClass$superTypeRefs$2$enhancement$1
                    @Override // kotlin.jvm.functions.Function1
                    public final List<FirCallableDeclaration> invoke(FirSimpleFunction $receiver) {
                        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                        return CollectionsKt.emptyList();
                    }
                }).enhanceSuperTypes(this.this$0.unenhnancedSuperTypes);
            }
        });
        this.annotations = LazyKt.lazy(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaClass$annotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return JavaAnnotationsMappingKt.convertAnnotationsToFir(MutableOrEmptyList.m6756boximpl(this.this$0.unEnhancedAnnotations), this.this$0.getModuleData().getSession());
            }
        });
        this.deprecationsProvider = LazyKt.lazy(new Function0<DeprecationsProvider>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaClass$deprecationsProvider$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeprecationsProvider invoke() {
                FirJavaClass firJavaClass = this.this$0;
                return DeprecationUtilsKt.getDeprecationsProvider(firJavaClass, firJavaClass.getModuleData().getSession());
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public List<FirTypeRef> getSuperTypeRefs() {
        return (List) this.superTypeRefs.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return (DeprecationsProvider) this.deprecationsProvider.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public void replaceSuperTypeRefs(List<? extends FirTypeRef> newSuperTypeRefs) {
        Intrinsics.checkNotNullParameter(newSuperTypeRefs, "newSuperTypeRefs");
        throw new IllegalStateException(("replaceSuperTypeRefs should not be called for " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + ", " + Reflection.getOrCreateKotlinClass(getSuperTypeRefs().getClass()).getSimpleName() + " is lazily calulated").toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        throw new IllegalStateException(("replaceDeprecationsProvider should not be called for " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + ", " + Reflection.getOrCreateKotlinClass(getDeprecationsProvider().getClass()).getSimpleName() + " is lazily calculated").toString());
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getDeclarations().iterator();
        while (it.hasNext()) {
            ((FirDeclaration) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getAnnotations().iterator();
        while (it2.hasNext()) {
            ((FirAnnotation) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = getTypeParameters().iterator();
        while (it3.hasNext()) {
            ((FirTypeParameterRef) it3.next()).accept(visitor, data);
        }
        getStatus().accept(visitor, data);
        Iterator<T> it4 = getSuperTypeRefs().iterator();
        while (it4.hasNext()) {
            ((FirTypeRef) it4.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirJavaClass transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        transformDeclarations((FirTransformer) transformer, (Object) data);
        setStatus((FirDeclarationStatus) FirTransformerUtilKt.transformSingle(getStatus(), transformer, data));
        transformSuperTypeRefs((FirTransformer) transformer, (Object) data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public <D> FirRegularClass transformSuperTypeRefs(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirJavaClass transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) FirTransformerUtilKt.transformSingle(getStatus(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new IllegalStateException(("replaceAnnotations should not be called for " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + ", " + Reflection.getOrCreateKotlinClass(getAnnotations().getClass()).getSimpleName() + " is lazily calculated").toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public <D> FirJavaClass transformDeclarations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getDeclarations(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirRegularClass transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }
}
