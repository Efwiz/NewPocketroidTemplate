package org.jetbrains.kotlin.backend.common.serialization;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureSerializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: DeclarationTable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000fJ\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001e\u0010\u001b\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00180\u001fJ\u0016\u0010 \u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016J$\u0010!\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001fJ\u0018\u0010#\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010$\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0014\u001a\u00020\u000eH\u0014J\u0014\u0010%\u001a\u00020\u0016*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u0014\u0010\u0005\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR0\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f`\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "", "globalTable", "Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "(Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;)V", "globalDeclarationTable", "getGlobalDeclarationTable", "()Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "signaturer", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureSerializer;", "getSignaturer", "()Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureSerializer;", "table", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/HashMap;", "getTable", "()Ljava/util/HashMap;", "allocateIndexedSignature", "declaration", "compatibleMode", "", "assumeDeclarationSignature", "", "signature", "computeSignatureByDeclaration", "inFile", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "block", "Lkotlin/Function0;", "isExportedDeclaration", "privateDeclarationSignature", "builder", "signatureByDeclaration", "tryComputeBackendSpecificSignature", "isLocalDeclaration", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeclarationTable {
    private final GlobalDeclarationTable globalDeclarationTable;
    private final IdSignatureSerializer signaturer;
    private final HashMap<IrDeclaration, IdSignature> table;

    protected IdSignature tryComputeBackendSpecificSignature(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return null;
    }

    public DeclarationTable(GlobalDeclarationTable globalTable) {
        Intrinsics.checkNotNullParameter(globalTable, "globalTable");
        this.table = new HashMap<>();
        this.globalDeclarationTable = globalTable;
        this.signaturer = new IdSignatureSerializer(globalTable.getPublicIdSignatureComputer(), this);
    }

    protected final HashMap<IrDeclaration, IdSignature> getTable() {
        return this.table;
    }

    protected GlobalDeclarationTable getGlobalDeclarationTable() {
        return this.globalDeclarationTable;
    }

    public IdSignatureSerializer getSignaturer() {
        return this.signaturer;
    }

    public final void inFile(IrFile file, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        getSignaturer().inFile(file != null ? file.getSymbol() : null, block);
    }

    private final boolean isLocalDeclaration(IrDeclaration irDeclaration, boolean z) {
        return !isExportedDeclaration(irDeclaration, z);
    }

    public final boolean isExportedDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getGlobalDeclarationTable().isExportedDeclaration(declaration, compatibleMode);
    }

    private final IdSignature allocateIndexedSignature(IrDeclaration declaration, boolean compatibleMode) {
        HashMap<IrDeclaration, IdSignature> map = this.table;
        IdSignature idSignatureComposeFileLocalIdSignature = map.get(declaration);
        if (idSignatureComposeFileLocalIdSignature == null) {
            idSignatureComposeFileLocalIdSignature = getSignaturer().composeFileLocalIdSignature(declaration, compatibleMode);
            map.put(declaration, idSignatureComposeFileLocalIdSignature);
        }
        return idSignatureComposeFileLocalIdSignature;
    }

    private final IdSignature computeSignatureByDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        IdSignature idSignatureTryComputeBackendSpecificSignature = tryComputeBackendSpecificSignature(declaration);
        if (idSignatureTryComputeBackendSpecificSignature != null) {
            return idSignatureTryComputeBackendSpecificSignature;
        }
        if (isLocalDeclaration(declaration, compatibleMode)) {
            return allocateIndexedSignature(declaration, compatibleMode);
        }
        return getGlobalDeclarationTable().computeSignatureByDeclaration(declaration, compatibleMode);
    }

    public final IdSignature privateDeclarationSignature(IrDeclaration declaration, boolean compatibleMode, Function0<? extends IdSignature> builder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(builder, "builder");
        isLocalDeclaration(declaration, compatibleMode);
        HashMap<IrDeclaration, IdSignature> map = this.table;
        IdSignature idSignatureInvoke = map.get(declaration);
        if (idSignatureInvoke == null) {
            idSignatureInvoke = builder.invoke();
            map.put(declaration, idSignatureInvoke);
        }
        return idSignatureInvoke;
    }

    public IdSignature signatureByDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return computeSignatureByDeclaration(declaration, compatibleMode);
    }

    public final void assumeDeclarationSignature(IrDeclaration declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.table.get(declaration);
        this.table.put(declaration, signature);
    }
}
