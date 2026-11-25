package org.jetbrains.kotlin.js.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: nativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/AbstractNativeIndexerChecker;", "Lorg/jetbrains/kotlin/js/resolve/AbstractNativeAnnotationsChecker;", "requiredAnnotation", "Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "indexerKind", "", "requiredParametersCount", "", "(Lorg/jetbrains/kotlin/js/PredefinedAnnotation;Ljava/lang/String;I)V", "additionalCheck", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractNativeIndexerChecker extends AbstractNativeAnnotationsChecker {
    private final String indexerKind;
    private final int requiredParametersCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractNativeIndexerChecker(PredefinedAnnotation requiredAnnotation, String indexerKind, int i) {
        super(requiredAnnotation);
        Intrinsics.checkNotNullParameter(requiredAnnotation, "requiredAnnotation");
        Intrinsics.checkNotNullParameter(indexerKind, "indexerKind");
        this.indexerKind = indexerKind;
        this.requiredParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.js.resolve.AbstractNativeAnnotationsChecker
    public void additionalCheck(KtNamedFunction declaration, FunctionDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(descriptor);
        if (!valueParameters.isEmpty()) {
            KotlinType type = ((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters)).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (!KotlinBuiltIns.isString(type)) {
                SimpleType defaultType = builtIns.getNumber().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                if (!TypeUtilsKt.isSubtypeOf(type, defaultType)) {
                    DiagnosticFactory1<KtElement, String> diagnosticFactory1 = ErrorsJs.NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER;
                    List<KtParameter> valueParameters2 = declaration.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                    diagnosticHolder.report(diagnosticFactory1.on((PsiElement) CollectionsKt.first((List) valueParameters2), this.indexerKind));
                }
            }
        }
        if (valueParameters.size() != this.requiredParametersCount) {
            diagnosticHolder.report(ErrorsJs.NATIVE_INDEXER_WRONG_PARAMETER_COUNT.on(declaration, Integer.valueOf(this.requiredParametersCount), this.indexerKind));
        }
        for (KtParameter ktParameter : declaration.getValueParameters()) {
            if (ktParameter.hasDefaultValue()) {
                diagnosticHolder.report(ErrorsJs.NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS.on(ktParameter, this.indexerKind));
            }
        }
    }
}
