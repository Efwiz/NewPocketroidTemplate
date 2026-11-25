package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PsiInlineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"&\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"explicitParameters", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getExplicitParameters", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Ljava/util/List;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiInlineCodegenKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<ParameterDescriptor> getExplicitParameters(FunctionDescriptor functionDescriptor) {
        List listListOfNotNull = CollectionsKt.listOfNotNull(functionDescriptor.getExtensionReceiverParameter());
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) valueParameters);
    }
}
