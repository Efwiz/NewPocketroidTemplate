package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirAbstractContractResolveTransformerDispatcher.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\u001a\f\u0010\n\u001a\u00020\u000b*\u00020\u0002H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "getBody", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;)Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;)Ljava/util/List;", "error", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractContractResolveTransformerDispatcherKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final List<FirValueParameter> getValueParameters(FirContractDescriptionOwner firContractDescriptionOwner) {
        if (firContractDescriptionOwner instanceof FirFunction) {
            return ((FirFunction) firContractDescriptionOwner).getValueParameters();
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Expected " + FirFunction.class + " but " + firContractDescriptionOwner.getClass() + " found", null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "foundElement", firContractDescriptionOwner);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirBlock getBody(FirContractDescriptionOwner firContractDescriptionOwner) {
        if (firContractDescriptionOwner instanceof FirFunction) {
            FirBlock body = ((FirFunction) firContractDescriptionOwner).getBody();
            Intrinsics.checkNotNull(body);
            return body;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Expected " + FirFunction.class + " but " + firContractDescriptionOwner.getClass() + " found", null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "foundElement", firContractDescriptionOwner);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }
}
