package org.jetbrains.kotlin.fir.declarations.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;

/* JADX INFO: compiled from: FirResolvedDeclarationStatusImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u0000 \u00132\u00020\u00012\u00020\u0002:\u0001\u0013B'\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", OverlayablePolicy.NAME_flags, "", "(Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;I)V", "(Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;)V", "getEffectiveVisibility", "()Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedDeclarationStatusImpl extends FirDeclarationStatusImpl implements FirResolvedDeclarationStatus {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirResolvedDeclarationStatusImpl DEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
    private static final FirResolvedDeclarationStatusImpl DEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION;
    private static final FirResolvedDeclarationStatusImpl DEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION;
    private final EffectiveVisibility effectiveVisibility;

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus
    public EffectiveVisibility getEffectiveVisibility() {
        return this.effectiveVisibility;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirResolvedDeclarationStatusImpl(Visibility visibility, Modality modality, EffectiveVisibility effectiveVisibility) {
        super(visibility, modality);
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(effectiveVisibility, "effectiveVisibility");
        this.effectiveVisibility = effectiveVisibility;
    }

    /* JADX INFO: compiled from: FirResolvedDeclarationStatusImpl.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl$Companion;", "", "()V", "DEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS", "()Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "DEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION", "getDEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION", "DEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION", "getDEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirResolvedDeclarationStatusImpl getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS() {
            return FirResolvedDeclarationStatusImpl.DEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS;
        }

        public final FirResolvedDeclarationStatusImpl getDEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION() {
            return FirResolvedDeclarationStatusImpl.DEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION;
        }

        public final FirResolvedDeclarationStatusImpl getDEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION() {
            return FirResolvedDeclarationStatusImpl.DEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION;
        }
    }

    static {
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl.setSuspend(true);
        DEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION = firResolvedDeclarationStatusImpl;
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl2 = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl2.setSuspend(true);
        DEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION = firResolvedDeclarationStatusImpl2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirResolvedDeclarationStatusImpl(Visibility visibility, Modality modality, EffectiveVisibility effectiveVisibility, int i) {
        this(visibility, modality, effectiveVisibility);
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(effectiveVisibility, "effectiveVisibility");
        setFlags(i);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl, org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public Visibility getVisibility() {
        return super.getVisibility();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl, org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus
    public Modality getModality() {
        Modality modality = super.getModality();
        Intrinsics.checkNotNull(modality);
        return modality;
    }
}
