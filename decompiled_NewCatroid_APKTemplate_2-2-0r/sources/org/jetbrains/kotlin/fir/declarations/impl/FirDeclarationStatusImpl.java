package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirDeclarationStatusImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b0\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002:\u0001YB\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J5\u0010E\u001a\u00020F\"\u0004\b\u0000\u0010G\"\u0004\b\u0001\u0010H2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u0002HG\u0012\u0004\u0012\u0002HH0J2\u0006\u0010K\u001a\u0002HHH\u0016¢\u0006\u0002\u0010LJ\u0011\u0010M\u001a\u00020\u000f2\u0006\u0010N\u001a\u00020OH\u0086\u0002J\u001e\u0010P\u001a\u00020Q2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010R\u001a\u00020SJ\u0019\u0010T\u001a\u00020F2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\u0002J)\u0010U\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HH0W2\u0006\u0010K\u001a\u0002HHH\u0016¢\u0006\u0002\u0010XR\u001a\u0010\b\u001a\u00020\tX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R$\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0012\"\u0004\b\u0018\u0010\u0014R$\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R$\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u0012\"\u0004\b\u001c\u0010\u0014R$\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u0012\"\u0004\b\u001e\u0010\u0014R$\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R$\u0010!\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b!\u0010\u0012\"\u0004\b\"\u0010\u0014R$\u0010#\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b#\u0010\u0012\"\u0004\b$\u0010\u0014R$\u0010%\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b%\u0010\u0012\"\u0004\b&\u0010\u0014R$\u0010'\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b'\u0010\u0012\"\u0004\b(\u0010\u0014R$\u0010)\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b)\u0010\u0012\"\u0004\b*\u0010\u0014R$\u0010+\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b+\u0010\u0012\"\u0004\b,\u0010\u0014R$\u0010-\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b-\u0010\u0012\"\u0004\b.\u0010\u0014R$\u0010/\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b/\u0010\u0012\"\u0004\b0\u0010\u0014R$\u00101\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b1\u0010\u0012\"\u0004\b2\u0010\u0014R$\u00103\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b3\u0010\u0012\"\u0004\b4\u0010\u0014R$\u00105\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b5\u0010\u0012\"\u0004\b6\u0010\u0014R$\u00107\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b7\u0010\u0012\"\u0004\b8\u0010\u0014R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u001a\u0010;\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b<\u0010=\u001a\u0004\b>\u0010\u000bR\u0016\u0010?\u001a\u0004\u0018\u00010@8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010D¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "(Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/descriptors/Modality;)V", OverlayablePolicy.NAME_flags, "", "getFlags", "()I", "setFlags", "(I)V", "value", "", "hasStableParameterNames", "getHasStableParameterNames", "()Z", "setHasStableParameterNames", "(Z)V", "isActual", "setActual", "isCompanion", "setCompanion", "isConst", "setConst", "isData", "setData", "isExpect", "setExpect", "isExternal", "setExternal", "isFromEnumClass", "setFromEnumClass", "isFromSealedClass", "setFromSealedClass", "isFun", "setFun", "isInfix", "setInfix", "isInline", "setInline", "isInner", "setInner", "isLateInit", "setLateInit", "isOperator", "setOperator", "isOverride", "setOverride", "isStatic", "setStatic", "isSuspend", "setSuspend", "isTailRec", "setTailRec", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "rawFlags", "getRawFlags$tree$annotations", "()V", "getRawFlags$tree", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "get", "modifier", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl$Modifier;", "resolved", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl;", "Modifier", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirDeclarationStatusImpl extends FirPureAbstractElement implements FirDeclarationStatus {
    private int flags;
    private final Modality modality;
    private final Visibility visibility;

    @FirImplementationDetail
    public static /* synthetic */ void getRawFlags$tree$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirDeclarationStatusImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public Modality getModality() {
        return this.modality;
    }

    public FirDeclarationStatusImpl(Visibility visibility, Modality modality) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        this.visibility = visibility;
        this.modality = modality;
        this.flags = Modifier.HAS_STABLE_PARAMETER_NAMES.getMask();
    }

    protected final int getFlags() {
        return this.flags;
    }

    protected final void setFlags(int i) {
        this.flags = i;
    }

    public final int getRawFlags$tree() {
        return this.flags;
    }

    public final boolean get(Modifier modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        return (modifier.getMask() & this.flags) != 0;
    }

    public final void set(Modifier modifier, boolean value) {
        int mask;
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        if (value) {
            mask = modifier.getMask() | this.flags;
        } else {
            mask = (~modifier.getMask()) & this.flags;
        }
        this.flags = mask;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isExpect() {
        return get(Modifier.EXPECT);
    }

    public void setExpect(boolean z) {
        set(Modifier.EXPECT, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isActual() {
        return get(Modifier.ACTUAL);
    }

    public void setActual(boolean z) {
        set(Modifier.ACTUAL, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isOverride() {
        return get(Modifier.OVERRIDE);
    }

    public void setOverride(boolean z) {
        set(Modifier.OVERRIDE, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isOperator() {
        return get(Modifier.OPERATOR);
    }

    public void setOperator(boolean z) {
        set(Modifier.OPERATOR, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isInfix() {
        return get(Modifier.INFIX);
    }

    public void setInfix(boolean z) {
        set(Modifier.INFIX, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isInline() {
        return get(Modifier.INLINE);
    }

    public void setInline(boolean z) {
        set(Modifier.INLINE, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isTailRec() {
        return get(Modifier.TAILREC);
    }

    public void setTailRec(boolean z) {
        set(Modifier.TAILREC, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isExternal() {
        return get(Modifier.EXTERNAL);
    }

    public void setExternal(boolean z) {
        set(Modifier.EXTERNAL, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isConst() {
        return get(Modifier.CONST);
    }

    public void setConst(boolean z) {
        set(Modifier.CONST, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isLateInit() {
        return get(Modifier.LATEINIT);
    }

    public void setLateInit(boolean z) {
        set(Modifier.LATEINIT, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isInner() {
        return get(Modifier.INNER);
    }

    public void setInner(boolean z) {
        set(Modifier.INNER, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isCompanion() {
        return get(Modifier.COMPANION);
    }

    public void setCompanion(boolean z) {
        set(Modifier.COMPANION, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isData() {
        return get(Modifier.DATA);
    }

    public void setData(boolean z) {
        set(Modifier.DATA, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isSuspend() {
        return get(Modifier.SUSPEND);
    }

    public void setSuspend(boolean z) {
        set(Modifier.SUSPEND, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isStatic() {
        return get(Modifier.STATIC);
    }

    public void setStatic(boolean z) {
        set(Modifier.STATIC, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isFromSealedClass() {
        return get(Modifier.FROM_SEALED);
    }

    public void setFromSealedClass(boolean z) {
        set(Modifier.FROM_SEALED, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isFromEnumClass() {
        return get(Modifier.FROM_ENUM);
    }

    public void setFromEnumClass(boolean z) {
        set(Modifier.FROM_ENUM, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean isFun() {
        return get(Modifier.FUN);
    }

    public void setFun(boolean z) {
        set(Modifier.FUN, z);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public boolean getHasStableParameterNames() {
        return get(Modifier.HAS_STABLE_PARAMETER_NAMES);
    }

    public void setHasStableParameterNames(boolean z) {
        set(Modifier.HAS_STABLE_PARAMETER_NAMES, z);
    }

    /* JADX INFO: compiled from: FirDeclarationStatusImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0017\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl$Modifier;", "", "mask", "", "(Ljava/lang/String;II)V", "getMask", "()I", "EXPECT", "ACTUAL", "OVERRIDE", "OPERATOR", "INFIX", "INLINE", "TAILREC", "EXTERNAL", "CONST", "LATEINIT", "INNER", "COMPANION", "DATA", "SUSPEND", "STATIC", "FROM_SEALED", "FROM_ENUM", "FUN", "HAS_STABLE_PARAMETER_NAMES", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Modifier {
        EXPECT(1),
        ACTUAL(2),
        OVERRIDE(4),
        OPERATOR(8),
        INFIX(16),
        INLINE(32),
        TAILREC(64),
        EXTERNAL(128),
        CONST(256),
        LATEINIT(512),
        INNER(1024),
        COMPANION(2048),
        DATA(4096),
        SUSPEND(8192),
        STATIC(16384),
        FROM_SEALED(32768),
        FROM_ENUM(65536),
        FUN(131072),
        HAS_STABLE_PARAMETER_NAMES(262144);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final int mask;

        public static EnumEntries<Modifier> getEntries() {
            return $ENTRIES;
        }

        Modifier(int i) {
            this.mask = i;
        }

        public final int getMask() {
            return this.mask;
        }
    }

    public final FirResolvedDeclarationStatusImpl resolved(Visibility visibility, Modality modality, EffectiveVisibility effectiveVisibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(effectiveVisibility, "effectiveVisibility");
        return new FirResolvedDeclarationStatusImpl(visibility, modality, effectiveVisibility, this.flags);
    }
}
