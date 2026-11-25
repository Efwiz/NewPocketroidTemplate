package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.js.resolve.diagnostics.SourceLocationUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: WasmWasiExternalDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmWasiExternalDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmWasiExternalDeclarationChecker implements DeclarationChecker {
    public static final WasmWasiExternalDeclarationChecker INSTANCE = new WasmWasiExternalDeclarationChecker();

    private WasmWasiExternalDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor) && (descriptor instanceof FunctionDescriptor)) {
            if (!DescriptorUtils.isTopLevelDeclaration(descriptor)) {
                KtDeclaration ktDeclarationFindPsi = SourceLocationUtilsKt.findPsi(descriptor);
                if (ktDeclarationFindPsi == null) {
                    ktDeclarationFindPsi = declaration;
                }
                context.getTrace().report(ErrorsWasm.WASI_EXTERNAL_NOT_TOP_LEVEL_FUNCTION.on(ktDeclarationFindPsi));
                return;
            }
            if (descriptor.getAnnotations().hasAnnotation(new FqName("kotlin.wasm.WasmImport"))) {
                return;
            }
            KtDeclaration ktDeclarationFindPsi2 = SourceLocationUtilsKt.findPsi(descriptor);
            if (ktDeclarationFindPsi2 == null) {
                ktDeclarationFindPsi2 = declaration;
            }
            context.getTrace().report(ErrorsWasm.WASI_EXTERNAL_FUNCTION_WITHOUT_IMPORT.on(ktDeclarationFindPsi2));
        }
    }
}
