package org.jetbrains.kotlin.contracts.description;

import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: KtValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0001¢\u0006\u0002\u0010\u0005JA\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0002\u0010\u000e\"\u0004\b\u0003\u0010\u000f2\u001e\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0013\u0010\u0004\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtErroneousValueParameterReference;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "diagnostic", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getDiagnostic", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "erroneous", "", "getErroneous", "()Z", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtErroneousValueParameterReference<Type, Diagnostic> extends KtValueParameterReference<Type, Diagnostic> {
    private final Diagnostic diagnostic;

    @Override // org.jetbrains.kotlin.contracts.description.KtValueParameterReference, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return true;
    }

    public KtErroneousValueParameterReference(Diagnostic diagnostic) {
        super(Integer.MAX_VALUE, MediaError.ERROR_TYPE_ERROR);
        this.diagnostic = diagnostic;
    }

    public final Diagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtValueParameterReference, org.jetbrains.kotlin.contracts.description.KtContractDescriptionValue, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitErroneousValueParameterReference(this, data);
    }
}
