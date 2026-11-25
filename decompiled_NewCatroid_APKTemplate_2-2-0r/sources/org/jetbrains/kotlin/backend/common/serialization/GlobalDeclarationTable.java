package org.jetbrains.kotlin.backend.common.serialization;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: DeclarationTable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0016\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR0\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f`\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;)V", "clashTracker", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;)V", "publicIdSignatureComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;", "getPublicIdSignatureComputer", "()Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;", "table", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/HashMap;", "getTable", "()Ljava/util/HashMap;", "computeSignatureByDeclaration", "declaration", "compatibleMode", "", "isExportedDeclaration", "loadKnownBuiltins", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class GlobalDeclarationTable {
    private final IdSignatureClashTracker clashTracker;
    private final KotlinMangler.IrMangler mangler;
    private final PublicIdSignatureComputer publicIdSignatureComputer;
    private final HashMap<IrDeclaration, IdSignature> table;

    public GlobalDeclarationTable(KotlinMangler.IrMangler mangler, IdSignatureClashTracker clashTracker) {
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        Intrinsics.checkNotNullParameter(clashTracker, "clashTracker");
        this.mangler = mangler;
        this.clashTracker = clashTracker;
        this.publicIdSignatureComputer = new PublicIdSignatureComputer(mangler);
        this.table = new HashMap<>();
    }

    public final PublicIdSignatureComputer getPublicIdSignatureComputer() {
        return this.publicIdSignatureComputer;
    }

    protected final HashMap<IrDeclaration, IdSignature> getTable() {
        return this.table;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GlobalDeclarationTable(KotlinMangler.IrMangler mangler) {
        this(mangler, IdSignatureClashTracker.INSTANCE.getDEFAULT_TRACKER());
        Intrinsics.checkNotNullParameter(mangler, "mangler");
    }

    protected final void loadKnownBuiltins(IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        for (IrDeclaration irDeclaration : DeclarationTableKt.getKnownBuiltins(builtIns)) {
            Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            IrSymbol symbol = irDeclaration.getSymbol();
            HashMap<IrDeclaration, IdSignature> map = this.table;
            IdSignature signature = symbol.getSignature();
            Intrinsics.checkNotNull(signature);
            this.clashTracker.commit(irDeclaration, signature);
            map.put(irDeclaration, signature);
        }
    }

    public IdSignature computeSignatureByDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrDeclaration, IdSignature> map = this.table;
        IdSignature idSignatureComposePublicIdSignature = map.get(declaration);
        if (idSignatureComposePublicIdSignature == null) {
            idSignatureComposePublicIdSignature = this.publicIdSignatureComputer.composePublicIdSignature(declaration, compatibleMode);
            this.clashTracker.commit(declaration, idSignatureComposePublicIdSignature);
            map.put(declaration, idSignatureComposePublicIdSignature);
        }
        return idSignatureComposePublicIdSignature;
    }

    public final boolean isExportedDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.mangler.isExported(declaration, compatibleMode);
    }
}
