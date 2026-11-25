package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.ActionState;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: Lower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\t2\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b\u001al\u0010\u000f\u001a6\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0012\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00020\u00010\u0010j\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0012\u0012\u0004\u0012\u0002H\u0013`\u0014\"\u0004\b\u0000\u0010\u0013**\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00020\u00010\u0010j\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u0013`\u0014¨\u0006\u0016"}, d2 = {"lower", "", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "runOnFilePostfix", "Lorg/jetbrains/kotlin/backend/common/BodyAndScriptBodyLoweringPass;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "withLocalDeclarations", "", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/DeclarationContainerLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/ScriptLoweringPass;", "toMultiModuleAction", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "", "C", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "Lorg/jetbrains/kotlin/ir/IrElement;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LowerKt {
    public static final void lower(FileLoweringPass fileLoweringPass, IrModuleFragment moduleFragment) {
        Intrinsics.checkNotNullParameter(fileLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        for (IrFile irFile : moduleFragment.getFiles()) {
            try {
                fileLoweringPass.lower(irFile);
            } catch (CompilationException e) {
                e.setFile(irFile);
                throw e;
            } catch (KotlinExceptionWithAttachments e2) {
                throw e2;
            } catch (Throwable th) {
                throw CompilationExceptionKt.wrapWithCompilationException(th, "Internal error in file lowering", irFile, null);
            }
        }
    }

    public static final void runOnFilePostfix(ClassLoweringPass classLoweringPass, IrFile irFile) {
        Intrinsics.checkNotNullParameter(classLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrVisitorsKt.acceptVoid(irFile, new ClassLoweringVisitor(classLoweringPass));
    }

    public static final void runOnFilePostfix(ScriptLoweringPass scriptLoweringPass, IrFile irFile) {
        Intrinsics.checkNotNullParameter(scriptLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrVisitorsKt.acceptVoid(irFile, new ScriptLoweringVisitor(scriptLoweringPass));
    }

    public static final void runOnFilePostfix(DeclarationContainerLoweringPass declarationContainerLoweringPass, IrFile irFile) {
        Intrinsics.checkNotNullParameter(declarationContainerLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrVisitorsKt.acceptVoid(irFile, new DeclarationContainerLoweringVisitor(declarationContainerLoweringPass));
        declarationContainerLoweringPass.lower((IrDeclarationContainer) irFile);
    }

    public static /* synthetic */ void runOnFilePostfix$default(BodyLoweringPass bodyLoweringPass, IrFile irFile, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        runOnFilePostfix(bodyLoweringPass, irFile, z);
    }

    public static final void runOnFilePostfix(BodyLoweringPass bodyLoweringPass, IrFile irFile, boolean z) {
        Intrinsics.checkNotNullParameter(bodyLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        BodyLoweringVisitor bodyLoweringVisitor = new BodyLoweringVisitor(bodyLoweringPass, z);
        for (IrDeclaration irDeclaration : new ArrayList(irFile.getDeclarations())) {
            try {
                irDeclaration.accept(bodyLoweringVisitor, null);
            } catch (CompilationException e) {
                e.setFile(irFile);
                throw e;
            } catch (Throwable th) {
                throw CompilationExceptionKt.wrapWithCompilationException(th, "Internal error in body lowering", irFile, irDeclaration);
            }
        }
    }

    public static final void runOnFilePostfix(BodyAndScriptBodyLoweringPass bodyAndScriptBodyLoweringPass, IrFile irFile) {
        Intrinsics.checkNotNullParameter(bodyAndScriptBodyLoweringPass, "<this>");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        ScriptBodyLoweringVisitor scriptBodyLoweringVisitor = new ScriptBodyLoweringVisitor(bodyAndScriptBodyLoweringPass);
        Iterator it = new ArrayList(irFile.getDeclarations()).iterator();
        while (it.hasNext()) {
            ((IrDeclaration) it.next()).accept(scriptBodyLoweringVisitor, null);
        }
    }

    public static final <C> Function3<ActionState, Iterable<? extends IrModuleFragment>, C, Unit> toMultiModuleAction(final Function3<? super ActionState, ? super IrElement, ? super C, Unit> function3) {
        Intrinsics.checkNotNullParameter(function3, "<this>");
        return new Function3<ActionState, Iterable<? extends IrModuleFragment>, C, Unit>() { // from class: org.jetbrains.kotlin.backend.common.LowerKt.toMultiModuleAction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Iterable<? extends IrModuleFragment> iterable, Object obj) {
                invoke2(actionState, iterable, obj);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState state, Iterable<? extends IrModuleFragment> modules, C c) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(modules, "modules");
                Function3<ActionState, IrElement, C, Unit> function4 = function3;
                Iterator<? extends IrModuleFragment> it = modules.iterator();
                while (it.hasNext()) {
                    function4.invoke(state, it.next(), c);
                }
            }
        };
    }
}
