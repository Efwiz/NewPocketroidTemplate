package org.jetbrains.kotlin.ir.util;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* JADX INFO: compiled from: IdSignatureRenderer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u000bH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\fH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\rH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u000eH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u000fH\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u0010H\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u0011H\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u0012H\u0002J\u001c\u0010\u0005\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\u0006\u0010\u0007\u001a\u00020\u0013H\u0002J\u001e\u0010\u0014\u001a\u00060\tj\u0002`\n*\u00060\tj\u0002`\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignatureRenderer;", "", "showDescriptionForPublicSignatures", "", "(Z)V", "render", "", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileLocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LoweredDeclarationSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$ScopeLocalDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$SpecialFakeOverrideSignature;", "renderDescriptionForLocalSignature", MediaTrack.ROLE_DESCRIPTION, "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureRenderer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final IdSignatureRenderer DEFAULT = new IdSignatureRenderer(true);
    private static final IdSignatureRenderer LEGACY = new IdSignatureRenderer(false);
    private final boolean showDescriptionForPublicSignatures;

    private IdSignatureRenderer(boolean z) {
        this.showDescriptionForPublicSignatures = z;
    }

    public final String render(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        StringBuilder sb = new StringBuilder();
        render(sb, signature);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final StringBuilder render(StringBuilder sb, IdSignature idSignature) {
        if (idSignature instanceof IdSignature.CommonSignature) {
            return render(sb, (IdSignature.CommonSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.AccessorSignature) {
            return render(sb, (IdSignature.AccessorSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.CompositeSignature) {
            return render(sb, (IdSignature.CompositeSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.FileSignature) {
            return render(sb, (IdSignature.FileSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.LocalSignature) {
            return render(sb, (IdSignature.LocalSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.FileLocalSignature) {
            return render(sb, (IdSignature.FileLocalSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.ScopeLocalDeclaration) {
            return render(sb, (IdSignature.ScopeLocalDeclaration) idSignature);
        }
        if (idSignature instanceof IdSignature.SpecialFakeOverrideSignature) {
            return render(sb, (IdSignature.SpecialFakeOverrideSignature) idSignature);
        }
        if (idSignature instanceof IdSignature.LoweredDeclarationSignature) {
            return render(sb, (IdSignature.LoweredDeclarationSignature) idSignature);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.CommonSignature commonSignature) {
        Object id2;
        sb.append(commonSignature.getPackageFqName()).append('/').append(commonSignature.getDeclarationFqName()).append('|');
        if (!this.showDescriptionForPublicSignatures || (id2 = commonSignature.getDescription()) == null) {
            id2 = commonSignature.getId();
        }
        sb.append(id2);
        StringBuilder sbAppend = sb.append('[');
        String string = Long.toString(commonSignature.getMask(), CharsKt.checkRadix(2));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        StringBuilder sbAppend2 = sbAppend.append(string).append(']');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "with(...)");
        return sbAppend2;
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.AccessorSignature accessorSignature) {
        return render(sb, accessorSignature.getAccessorSignature());
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.CompositeSignature compositeSignature) {
        StringBuilder sbAppend = sb.append("[ ");
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = render(sbAppend, compositeSignature.getContainer()).append(" <- ");
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        StringBuilder sbAppend3 = render(sbAppend2, compositeSignature.getInner()).append(" ]");
        Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
        return sbAppend3;
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.FileSignature fileSignature) {
        StringBuilder sbAppend = sb.append("File '").append(fileSignature.getFileName()).append('\'');
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.LocalSignature localSignature) {
        sb.append("Local[").append(localSignature.getLocalFqn());
        Long hashSig = localSignature.getHashSig();
        if (hashSig != null) {
            hashSig.longValue();
            sb.append(",").append(localSignature.getHashSig().longValue());
        }
        renderDescriptionForLocalSignature(sb, localSignature.getDescription());
        StringBuilder sbAppend = sb.append(']');
        Intrinsics.checkNotNullExpressionValue(sbAppend, "with(...)");
        return sbAppend;
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.FileLocalSignature fileLocalSignature) {
        render(sb, fileLocalSignature.getContainer()).append(':').append(fileLocalSignature.getId());
        return renderDescriptionForLocalSignature(sb, fileLocalSignature.getDescription());
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.ScopeLocalDeclaration scopeLocalDeclaration) {
        sb.append('#').append(scopeLocalDeclaration.getId());
        return renderDescriptionForLocalSignature(sb, scopeLocalDeclaration.getDescription());
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.SpecialFakeOverrideSignature specialFakeOverrideSignature) {
        return render(sb, specialFakeOverrideSignature.getMemberSignature());
    }

    private final StringBuilder render(StringBuilder sb, IdSignature.LoweredDeclarationSignature loweredDeclarationSignature) {
        sb.append("ic#").append(loweredDeclarationSignature.getStage()).append(':');
        render(sb, loweredDeclarationSignature.getOriginal());
        StringBuilder sbAppend = sb.append('-').append(loweredDeclarationSignature.getIndex());
        Intrinsics.checkNotNullExpressionValue(sbAppend, "with(...)");
        return sbAppend;
    }

    private final StringBuilder renderDescriptionForLocalSignature(StringBuilder sb, String str) {
        if (str != null) {
            sb.append('|').append(str);
        }
        return sb;
    }

    /* JADX INFO: compiled from: IdSignatureRenderer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignatureRenderer$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/ir/util/IdSignatureRenderer;", "getDEFAULT", "()Lorg/jetbrains/kotlin/ir/util/IdSignatureRenderer;", "LEGACY", "getLEGACY", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IdSignatureRenderer getDEFAULT() {
            return IdSignatureRenderer.DEFAULT;
        }

        public final IdSignatureRenderer getLEGACY() {
            return IdSignatureRenderer.LEGACY;
        }
    }
}
