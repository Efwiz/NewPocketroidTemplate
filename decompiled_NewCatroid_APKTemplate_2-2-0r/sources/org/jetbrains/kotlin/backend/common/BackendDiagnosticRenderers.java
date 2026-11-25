package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: CommonBackendErrors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/BackendDiagnosticRenderers;", "", "()V", "EXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getEXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY", "()Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "INCOMPATIBILITY", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "getINCOMPATIBILITY", "SYMBOL_OWNER_DECLARATION_FQ_NAME", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getSYMBOL_OWNER_DECLARATION_FQ_NAME", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BackendDiagnosticRenderers {
    public static final BackendDiagnosticRenderers INSTANCE = new BackendDiagnosticRenderers();
    private static final ContextIndependentParameterRenderer<ExpectActualCompatibility.Incompatible<?>> INCOMPATIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<ExpectActualCompatibility.Incompatible<?>, String>() { // from class: org.jetbrains.kotlin.backend.common.BackendDiagnosticRenderers$INCOMPATIBILITY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(ExpectActualCompatibility.Incompatible<?> it) {
            Intrinsics.checkNotNullParameter(it, "it");
            String reason = it.getReason();
            return reason == null ? "<unknown>" : reason;
        }
    });
    private static final ContextIndependentParameterRenderer<IrSymbol> SYMBOL_OWNER_DECLARATION_FQ_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<IrSymbol, String>() { // from class: org.jetbrains.kotlin.backend.common.BackendDiagnosticRenderers$SYMBOL_OWNER_DECLARATION_FQ_NAME$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(IrSymbol it) {
            FqName fqNameWhenAvailable;
            Intrinsics.checkNotNullParameter(it, "it");
            IrSymbolOwner owner = it.getOwner();
            String strAsString = null;
            IrDeclarationWithName irDeclarationWithName = owner instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner : null;
            if (irDeclarationWithName != null && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)) != null) {
                strAsString = fqNameWhenAvailable.asString();
            }
            return strAsString == null ? "unknown name" : strAsString;
        }
    });
    private static final ContextIndependentParameterRenderer<ExpectActualAnnotationsIncompatibilityType<? extends IrConstructorCall>> EXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<ExpectActualAnnotationsIncompatibilityType<? extends IrConstructorCall>, String>() { // from class: org.jetbrains.kotlin.backend.common.BackendDiagnosticRenderers$EXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(ExpectActualAnnotationsIncompatibilityType<? extends IrConstructorCall> incompatibilityType) {
            String str;
            Intrinsics.checkNotNullParameter(incompatibilityType, "incompatibilityType");
            Object classFqName = IrTypesKt.getClassFqName(incompatibilityType.getExpectAnnotation().getType());
            if (classFqName == null) {
                classFqName = "<unknown>";
            }
            if (incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.MissingOnActual) {
                str = "is missing on actual declaration";
            } else {
                if (!(incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.DifferentOnActual)) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "has different arguments on actual declaration";
            }
            return "Annotation `" + classFqName + "` " + str;
        }
    });

    private BackendDiagnosticRenderers() {
    }

    public final ContextIndependentParameterRenderer<ExpectActualCompatibility.Incompatible<?>> getINCOMPATIBILITY() {
        return INCOMPATIBILITY;
    }

    public final ContextIndependentParameterRenderer<IrSymbol> getSYMBOL_OWNER_DECLARATION_FQ_NAME() {
        return SYMBOL_OWNER_DECLARATION_FQ_NAME;
    }

    public final ContextIndependentParameterRenderer<ExpectActualAnnotationsIncompatibilityType<? extends IrConstructorCall>> getEXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY() {
        return EXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY;
    }
}
