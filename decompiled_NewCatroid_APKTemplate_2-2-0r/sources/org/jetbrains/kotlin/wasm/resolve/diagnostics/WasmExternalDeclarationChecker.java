package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: WasmExternalDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmExternalDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmExternalDeclarationChecker implements DeclarationChecker {
    public static final WasmExternalDeclarationChecker INSTANCE = new WasmExternalDeclarationChecker();

    private WasmExternalDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor)) {
            if (descriptor instanceof ClassDescriptor) {
                ClassKind kind = ((ClassDescriptor) descriptor).getKind();
                Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
                if (kind == ClassKind.ENUM_CLASS) {
                    check$reportWrongExternalDeclaration(context, declaration, "enum class");
                    return;
                }
                return;
            }
            if (descriptor instanceof PropertyDescriptor) {
                if (((PropertyDescriptor) descriptor).isLateInit()) {
                    check$reportWrongExternalDeclaration(context, declaration, "lateinit property");
                }
            } else if (descriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
                if (functionDescriptor.isTailrec()) {
                    check$reportWrongExternalDeclaration(context, declaration, "tailrec function");
                }
                if (functionDescriptor.isSuspend()) {
                    check$reportWrongExternalDeclaration(context, declaration, "suspend function");
                }
            }
        }
    }

    private static final void check$reportWrongExternalDeclaration(DeclarationCheckerContext declarationCheckerContext, KtDeclaration ktDeclaration, String str) {
        declarationCheckerContext.getTrace().report(ErrorsJs.WRONG_EXTERNAL_DECLARATION.on(ktDeclaration, str));
    }
}
