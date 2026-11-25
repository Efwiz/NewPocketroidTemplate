package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirRegularClassImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirRegularClassBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010[\u001a\u00020\\H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\tR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\tR\u001a\u0010\"\u001a\u00020#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020)X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010.\u001a\u00020/X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001c\u0010F\u001a\u0004\u0018\u00010GX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\b\u0012\u0004\u0012\u00020S0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\tR\u001a\u0010U\u001a\u00020\u0017X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010\u0019\"\u0004\bW\u0010\u001bR\u001a\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010\t¨\u0006]"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParameterRefsOwnerBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setClassKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "companionObjectSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getCompanionObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "setCompanionObjectSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "setScopeProvider", "(Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRefs", NativeSymbol.TYPE_NAME, "getSymbol", "setSymbol", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirRegularClassBuilder implements FirAnnotationContainerBuilder, FirClassBuilder, FirTypeParameterRefsOwnerBuilder {
    public ClassKind classKind;
    private FirRegularClassSymbol companionObjectSymbol;
    public FirModuleData moduleData;
    public Name name;
    public FirDeclarationOrigin origin;
    public FirScopeProvider scopeProvider;
    private KtSourceElement source;
    public FirDeclarationStatus status;
    public FirRegularClassSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private final List<FirTypeParameterRef> typeParameters = new ArrayList();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirDeclaration> declarations = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirTypeRef> superTypeRefs = new ArrayList();
    private final List<FirContextReceiver> contextReceivers = new ArrayList();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public FirDeclarationStatus getStatus() {
        FirDeclarationStatus firDeclarationStatus = this.status;
        if (firDeclarationStatus != null) {
            return firDeclarationStatus;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NotificationCompat.CATEGORY_STATUS);
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public ClassKind getClassKind() {
        ClassKind classKind = this.classKind;
        if (classKind != null) {
            return classKind;
        }
        Intrinsics.throwUninitializedPropertyAccessException("classKind");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public void setClassKind(ClassKind classKind) {
        Intrinsics.checkNotNullParameter(classKind, "<set-?>");
        this.classKind = classKind;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public FirScopeProvider getScopeProvider() {
        FirScopeProvider firScopeProvider = this.scopeProvider;
        if (firScopeProvider != null) {
            return firScopeProvider;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scopeProvider");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public void setScopeProvider(FirScopeProvider firScopeProvider) {
        Intrinsics.checkNotNullParameter(firScopeProvider, "<set-?>");
        this.scopeProvider = firScopeProvider;
    }

    public Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public FirRegularClassSymbol getSymbol() {
        FirRegularClassSymbol firRegularClassSymbol = this.symbol;
        if (firRegularClassSymbol != null) {
            return firRegularClassSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public void setSymbol(FirRegularClassSymbol firRegularClassSymbol) {
        Intrinsics.checkNotNullParameter(firRegularClassSymbol, "<set-?>");
        this.symbol = firRegularClassSymbol;
    }

    public FirRegularClassSymbol getCompanionObjectSymbol() {
        return this.companionObjectSymbol;
    }

    public void setCompanionObjectSymbol(FirRegularClassSymbol firRegularClassSymbol) {
        this.companionObjectSymbol = firRegularClassSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirTypeRef> getSuperTypeRefs() {
        return this.superTypeRefs;
    }

    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterRefsOwnerBuilder
    /* JADX INFO: renamed from: build, reason: merged with bridge method [inline-methods] */
    public FirRegularClass mo6810build() {
        return new FirRegularClassImpl(getSource(), getResolvePhase(), getModuleData(), getOrigin(), getAttributes(), getTypeParameters(), getStatus(), getDeprecationsProvider(), getClassKind(), getDeclarations(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getScopeProvider(), getName(), getSymbol(), getCompanionObjectSymbol(), getSuperTypeRefs(), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), null);
    }
}
