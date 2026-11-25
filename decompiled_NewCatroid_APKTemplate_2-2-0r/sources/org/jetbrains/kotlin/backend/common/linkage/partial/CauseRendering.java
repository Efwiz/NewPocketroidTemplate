package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u0082\u0001\u0002\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering;", "", "Standalone", "UsedFromDeclaration", "UsedFromExpression", "UsedFromSomewhere", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$Standalone;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromSomewhere;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
interface CauseRendering {

    /* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0082\u0001\u0002\n\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromSomewhere;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering;", "objectSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getObjectSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "objectText", "", "getObjectText", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromDeclaration;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromExpression;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface UsedFromSomewhere extends CauseRendering {
        IrSymbol getObjectSymbol();

        String getObjectText();
    }

    /* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$Standalone;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering;", "()V", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Standalone implements CauseRendering {
        public static final Standalone INSTANCE = new Standalone();

        private Standalone() {
        }
    }

    /* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromDeclaration;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromSomewhere;", "objectText", "", "objectSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getObjectSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getObjectText", "()Ljava/lang/String;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UsedFromDeclaration implements UsedFromSomewhere {
        private final IrSymbol objectSymbol;
        private final String objectText;

        public UsedFromDeclaration(String objectText, IrSymbol objectSymbol) {
            Intrinsics.checkNotNullParameter(objectText, "objectText");
            Intrinsics.checkNotNullParameter(objectSymbol, "objectSymbol");
            this.objectText = objectText;
            this.objectSymbol = objectSymbol;
        }

        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.CauseRendering.UsedFromSomewhere
        public IrSymbol getObjectSymbol() {
            return this.objectSymbol;
        }

        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.CauseRendering.UsedFromSomewhere
        public String getObjectText() {
            return this.objectText;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromExpression;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering$UsedFromSomewhere;", "()V", "objectSymbol", "", "getObjectSymbol", "()Ljava/lang/Void;", "objectText", "", "getObjectText", "()Ljava/lang/String;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UsedFromExpression implements UsedFromSomewhere {
        private static final Void objectSymbol = null;
        public static final UsedFromExpression INSTANCE = new UsedFromExpression();
        private static final String objectText = "Expression";

        private UsedFromExpression() {
        }

        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.CauseRendering.UsedFromSomewhere
        public /* bridge */ /* synthetic */ IrSymbol getObjectSymbol() {
            return (IrSymbol) getObjectSymbol();
        }

        @Override // org.jetbrains.kotlin.backend.common.linkage.partial.CauseRendering.UsedFromSomewhere
        public String getObjectText() {
            return objectText;
        }

        public Void getObjectSymbol() {
            return objectSymbol;
        }
    }
}
