package org.jetbrains.kotlin.ir.backend.js;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.ir.backend.js.dce.DceDumpNameCache;
import org.jetbrains.kotlin.ir.backend.js.dce.DceKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrIcClassModel;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.inline.clean.ClassPostProcessor;
import org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor;

/* JADX INFO: compiled from: optimizations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a$\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"optimizeFragmentByJsAst", "", "fragment", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "optimizeProgramByIr", "modules", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "removeUnusedAssociatedObjects", "", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OptimizationsKt {
    public static final void optimizeProgramByIr(Iterable<? extends IrModuleFragment> modules, JsIrBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(context, "context");
        DceKt.eliminateDeadDeclarations(modules, context, z, new DceDumpNameCache());
        CompilerPhaseKt.invokeToplevel(JsLoweringPhasesKt.getJsOptimizationPhases(), new PhaseConfig(JsLoweringPhasesKt.getJsOptimizationPhases(), null, null, null, null, null, null, null, null, null, false, false, false, 8190, null), context, modules);
    }

    public static final void optimizeFragmentByJsAst(JsIrProgramFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        RecursiveJsVisitor recursiveJsVisitor = new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.ir.backend.js.OptimizationsKt$optimizeFragmentByJsAst$optimizer$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitFunction(x);
                new FunctionPostProcessor(x).apply();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitClass(JsClass x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitClass(x);
                new ClassPostProcessor(x).apply();
            }
        };
        List<JsStatement> statements = fragment.getDeclarations().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            ((JsStatement) it.next()).accept(recursiveJsVisitor);
        }
        for (JsIrIcClassModel jsIrIcClassModel : fragment.getClasses().values()) {
            List<JsStatement> statements2 = jsIrIcClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            Iterator<T> it2 = statements2.iterator();
            while (it2.hasNext()) {
                ((JsStatement) it2.next()).accept(recursiveJsVisitor);
            }
            List<JsStatement> statements3 = jsIrIcClassModel.getPreDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            Iterator<T> it3 = statements3.iterator();
            while (it3.hasNext()) {
                ((JsStatement) it3.next()).accept(recursiveJsVisitor);
            }
        }
    }
}
