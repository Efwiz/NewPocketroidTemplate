package org.jetbrains.kotlin.fir.declarations.impl;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
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
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirRegularClassImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B¯\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\r\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\b\u0010 \u001a\u0004\u0018\u00010\u001f\u0012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\r\u0012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0018¢\u0006\u0002\u0010%J5\u0010U\u001a\u00020V\"\u0004\b\u0000\u0010W\"\u0004\b\u0001\u0010X2\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u0002HW\u0012\u0004\u0012\u0002HX0Z2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010\\J\u0016\u0010]\u001a\u00020V2\f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00190_H\u0016J\u0012\u0010`\u001a\u00020V2\b\u0010a\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010b\u001a\u00020V2\b\u0010c\u001a\u0004\u0018\u000106H\u0016J\u0010\u0010d\u001a\u00020V2\u0006\u0010e\u001a\u00020\u0012H\u0016J\u0010\u0010f\u001a\u00020V2\u0006\u0010g\u001a\u00020\u0010H\u0016J\u0016\u0010h\u001a\u00020V2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\"0_H\u0016J)\u0010j\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mJ)\u0010n\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mJ)\u0010o\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mJ)\u0010p\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mJ)\u0010q\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mJ)\u0010r\u001a\u00020\u0000\"\u0004\b\u0000\u0010X2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002HX0l2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010mR(\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010*\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u001c\u0010 \u001a\u0004\u0018\u00010\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R(\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0018X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010*\u001a\u0004\b3\u0010'\"\u0004\b4\u0010)R\u001c\u00105\u001a\u0004\u0018\u000106X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010'R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u0014\u0010@\u001a\u00020A8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010'R\u0014\u0010\u001e\u001a\u00020\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u00100R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010'\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006s"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirRegularClassImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "name", "Lorg/jetbrains/kotlin/name/Name;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "companionObjectSymbol", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;Lorg/jetbrains/kotlin/descriptors/ClassKind;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Ljava/util/List;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getCompanionObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "setCompanionObjectSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;)V", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "getDeclarations", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "hasLazyNestedClassifiers", "", "getHasLazyNestedClassifiers", "()Z", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSuperTypeRefs", "getSymbol", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCompanionObjectSymbol", "newCompanionObjectSymbol", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceStatus", "newStatus", "replaceSuperTypeRefs", "newSuperTypeRefs", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirRegularClassImpl;", "transformChildren", "transformDeclarations", "transformStatus", "transformSuperTypeRefs", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRegularClassImpl extends FirRegularClass {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private final ClassKind classKind;
    private FirRegularClassSymbol companionObjectSymbol;
    private List<T> contextReceivers;
    private FirControlFlowGraphReference controlFlowGraphReference;
    private final List<FirDeclaration> declarations;
    private DeprecationsProvider deprecationsProvider;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin origin;
    private final FirScopeProvider scopeProvider;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final List<FirTypeRef> superTypeRefs;
    private final FirRegularClassSymbol symbol;
    private final List<FirTypeParameterRef> typeParameters;

    public /* synthetic */ FirRegularClassImpl(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, List list, FirDeclarationStatus firDeclarationStatus, DeprecationsProvider deprecationsProvider, ClassKind classKind, List list2, List list3, FirScopeProvider firScopeProvider, Name name2, FirRegularClassSymbol firRegularClassSymbol, FirRegularClassSymbol firRegularClassSymbol2, List list4, List list5, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, firModuleData, firDeclarationOrigin, firDeclarationAttributes, list, firDeclarationStatus, deprecationsProvider, classKind, list2, list3, firScopeProvider, name2, firRegularClassSymbol, firRegularClassSymbol2, list4, list5);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public boolean getHasLazyNestedClassifiers() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6867getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6868getContextReceivers5e3fPpI());
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public /* bridge */ /* synthetic */ FirRegularClass transformSuperTypeRefs(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirRegularClass transformTypeParameters(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public ClassKind getClassKind() {
        return this.classKind;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6867getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6869setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public FirScopeProvider getScopeProvider() {
        return this.scopeProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirRegularClassSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public FirRegularClassSymbol getCompanionObjectSymbol() {
        return this.companionObjectSymbol;
    }

    public void setCompanionObjectSymbol(FirRegularClassSymbol firRegularClassSymbol) {
        this.companionObjectSymbol = firRegularClassSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public List<FirTypeRef> getSuperTypeRefs() {
        return this.superTypeRefs;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6868getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6870setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    private FirRegularClassImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, List<FirTypeParameterRef> typeParameters, FirDeclarationStatus status, DeprecationsProvider deprecationsProvider, ClassKind classKind, List<FirDeclaration> declarations, List<T> list, FirScopeProvider scopeProvider, Name name2, FirRegularClassSymbol symbol, FirRegularClassSymbol firRegularClassSymbol, List<FirTypeRef> superTypeRefs, List<T> list2) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(superTypeRefs, "superTypeRefs");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.typeParameters = typeParameters;
        this.status = status;
        this.deprecationsProvider = deprecationsProvider;
        this.classKind = classKind;
        this.declarations = declarations;
        this.annotations = list;
        this.scopeProvider = scopeProvider;
        this.name = name2;
        this.symbol = symbol;
        this.companionObjectSymbol = firRegularClassSymbol;
        this.superTypeRefs = superTypeRefs;
        this.contextReceivers = list2;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    public void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        this.controlFlowGraphReference = firControlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((FirTypeParameterRef) it.next()).accept(visitor, data);
        }
        getStatus().accept(visitor, data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        if (controlFlowGraphReference != null) {
            controlFlowGraphReference.accept(visitor, data);
        }
        Iterator<T> it2 = getDeclarations().iterator();
        while (it2.hasNext()) {
            ((FirDeclaration) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m6867getAnnotations5e3fPpI()).iterator();
        while (it3.hasNext()) {
            ((FirAnnotation) it3.next()).accept(visitor, data);
        }
        Iterator<T> it4 = getSuperTypeRefs().iterator();
        while (it4.hasNext()) {
            ((FirTypeRef) it4.next()).accept(visitor, data);
        }
        Iterator<T> it5 = MutableOrEmptyList.m6756boximpl(m6868getContextReceivers5e3fPpI()).iterator();
        while (it5.hasNext()) {
            ((FirContextReceiver) it5.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirRegularClassImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        transformStatus((FirTransformer) transformer, (Object) data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        setControlFlowGraphReference(controlFlowGraphReference != null ? (FirControlFlowGraphReference) controlFlowGraphReference.transform(transformer, data) : null);
        transformDeclarations((FirTransformer) transformer, (Object) data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformSuperTypeRefs((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6868getContextReceivers5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirRegularClassImpl transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirRegularClassImpl transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) getStatus().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public <D> FirRegularClassImpl transformDeclarations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getDeclarations(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirRegularClassImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6867getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public <D> FirRegularClassImpl transformSuperTypeRefs(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getSuperTypeRefs(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        setControlFlowGraphReference(newControlFlowGraphReference);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6869setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass
    public void replaceCompanionObjectSymbol(FirRegularClassSymbol newCompanionObjectSymbol) {
        setCompanionObjectSymbol(newCompanionObjectSymbol);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.declarations.FirClass
    public void replaceSuperTypeRefs(List<? extends FirTypeRef> newSuperTypeRefs) {
        Intrinsics.checkNotNullParameter(newSuperTypeRefs, "newSuperTypeRefs");
        getSuperTypeRefs().clear();
        getSuperTypeRefs().addAll(newSuperTypeRefs);
    }
}
