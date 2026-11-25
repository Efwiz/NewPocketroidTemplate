package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0006X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UnexpectedUnboundIrSymbols;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssue;", "unboundSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "whenDetected", "", "(Ljava/util/Set;Ljava/lang/String;)V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "getErrorMessage", "()Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnexpectedUnboundIrSymbols extends KotlinIrLinkerIssue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String errorMessage;

    public UnexpectedUnboundIrSymbols(Set<? extends IrSymbol> unboundSymbols, String whenDetected) {
        String str;
        Intrinsics.checkNotNullParameter(unboundSymbols, "unboundSymbols");
        Intrinsics.checkNotNullParameter(whenDetected, "whenDetected");
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = sb.append("There ");
        int size = unboundSymbols.size();
        if (size == 1) {
            str = "is still an unbound symbol";
        } else {
            str = "are still " + size + " unbound symbols";
        }
        sbAppend.append(str).append(" ").append(whenDetected).append(":\n");
        Set<? extends IrSymbol> set = unboundSymbols;
        CollectionsKt.joinTo(set, sb, (112 & 2) != 0 ? ", " : "\n", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        sb.append("\n\nThis could happen if there are two libraries, where one library was compiled against the different version of the other library than the one currently used in the project. Please check that the project configuration is correct and has consistent versions of dependencies.");
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                if (INSTANCE.looksLikeEnumEntries(((IrSymbol) it.next()).getSignature())) {
                    sb.append("\n\nAnother possible reason is that some parts of the project are compiled with EnumEntries language feature enabled, but other parts or used libraries are compiled with EnumEntries language feature disabled.");
                    break;
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        this.errorMessage = string;
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssue
    protected String getErrorMessage() {
        return this.errorMessage;
    }

    /* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/UnexpectedUnboundIrSymbols$Companion;", "", "()V", "looksLikeEnumEntries", "", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean looksLikeEnumEntries(IdSignature signature) {
            if (signature instanceof IdSignature.AccessorSignature) {
                return looksLikeEnumEntries(((IdSignature.AccessorSignature) signature).getPropertySignature());
            }
            if (signature instanceof IdSignature.CompositeSignature) {
                return looksLikeEnumEntries(((IdSignature.CompositeSignature) signature).getInner());
            }
            if (signature instanceof IdSignature.CommonSignature) {
                return Intrinsics.areEqual(((IdSignature.CommonSignature) signature).getShortName(), TypeBlock.NAME_entries);
            }
            return false;
        }
    }
}
