package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: DeclarationTable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "", "commit", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IdSignatureClashTracker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void commit(IrDeclaration declaration, IdSignature signature);

    /* JADX INFO: compiled from: DeclarationTable.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker$Companion;", "", "()V", "DEFAULT_TRACKER", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "getDEFAULT_TRACKER", "()Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final IdSignatureClashTracker DEFAULT_TRACKER = new IdSignatureClashTracker() { // from class: org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker$Companion$DEFAULT_TRACKER$1
            @Override // org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker
            public void commit(IrDeclaration declaration, IdSignature signature) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(signature, "signature");
            }
        };

        private Companion() {
        }

        public final IdSignatureClashTracker getDEFAULT_TRACKER() {
            return DEFAULT_TRACKER;
        }
    }
}
