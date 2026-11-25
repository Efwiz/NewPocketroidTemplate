package org.jetbrains.kotlin.backend.common.serialization.signature;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IdSignatureComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/signature/DescToIrIdSignatureComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureComputer;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;", "(Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;)V", "computeSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "inFile", "", "file", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "block", "Lkotlin/Function0;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescToIrIdSignatureComputer implements IdSignatureComputer {
    private final IdSignatureDescriptor delegate;

    public DescToIrIdSignatureComputer(IdSignatureDescriptor delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureComputer
    public IdSignature computeSignature(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrEnumEntry) {
            return this.delegate.composeEnumEntrySignature(((IrEnumEntry) declaration).getDescriptor());
        }
        if (declaration instanceof IrField) {
            return this.delegate.composeFieldSignature(((IrField) declaration).getDescriptor());
        }
        return declaration instanceof IrAnonymousInitializer ? this.delegate.composeAnonInitSignature(((IrAnonymousInitializer) declaration).getDescriptor()) : this.delegate.composeSignature(declaration.getDescriptor());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureComputer
    public void inFile(IrFileSymbol file, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke();
    }
}
