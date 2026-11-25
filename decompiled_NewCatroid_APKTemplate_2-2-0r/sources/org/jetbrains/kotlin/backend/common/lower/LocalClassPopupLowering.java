package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LocalClassPopupLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001\u001aB.\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001f\b\u0002\u0010\u0004\u001a\u0019\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001a\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR(\u0010\u0004\u001a\u0019\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalClassPopupLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "recordExtractedLocalClasses", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lkotlin/jvm/functions/Function2;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "getRecordExtractedLocalClasses", "()Lkotlin/jvm/functions/Function2;", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "shouldPopUp", "", "klass", "currentScope", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "ExtractedLocalClass", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LocalClassPopupLowering implements BodyLoweringPass {
    private final BackendContext context;
    private final Function2<BackendContext, IrClass, Unit> recordExtractedLocalClasses;

    /* JADX WARN: Multi-variable type inference failed */
    public LocalClassPopupLowering(BackendContext context, Function2<? super BackendContext, ? super IrClass, Unit> recordExtractedLocalClasses) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(recordExtractedLocalClasses, "recordExtractedLocalClasses");
        this.context = context;
        this.recordExtractedLocalClasses = recordExtractedLocalClasses;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    public /* synthetic */ LocalClassPopupLowering(BackendContext backendContext, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(backendContext, (i & 2) != 0 ? new Function2<BackendContext, IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BackendContext backendContext2, IrClass it) {
                Intrinsics.checkNotNullParameter(backendContext2, "$this$null");
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(BackendContext backendContext2, IrClass irClass) {
                invoke2(backendContext2, irClass);
                return Unit.INSTANCE;
            }
        } : anonymousClass1);
    }

    public final Function2<BackendContext, IrClass, Unit> getRecordExtractedLocalClasses() {
        return this.recordExtractedLocalClasses;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile, true);
    }

    /* JADX INFO: compiled from: LocalClassPopupLowering.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalClassPopupLowering$ExtractedLocalClass;", "", "local", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "newContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "extractedUnder", "Lorg/jetbrains/kotlin/ir/IrStatement;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/IrStatement;)V", "getExtractedUnder", "()Lorg/jetbrains/kotlin/ir/IrStatement;", "getLocal", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getNewContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ExtractedLocalClass {
        private final IrStatement extractedUnder;
        private final IrClass local;
        private final IrDeclarationParent newContainer;

        public static /* synthetic */ ExtractedLocalClass copy$default(ExtractedLocalClass extractedLocalClass, IrClass irClass, IrDeclarationParent irDeclarationParent, IrStatement irStatement, int i, Object obj) {
            if ((i & 1) != 0) {
                irClass = extractedLocalClass.local;
            }
            if ((i & 2) != 0) {
                irDeclarationParent = extractedLocalClass.newContainer;
            }
            if ((i & 4) != 0) {
                irStatement = extractedLocalClass.extractedUnder;
            }
            return extractedLocalClass.copy(irClass, irDeclarationParent, irStatement);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrClass getLocal() {
            return this.local;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrDeclarationParent getNewContainer() {
            return this.newContainer;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final IrStatement getExtractedUnder() {
            return this.extractedUnder;
        }

        public final ExtractedLocalClass copy(IrClass local, IrDeclarationParent newContainer, IrStatement extractedUnder) {
            Intrinsics.checkNotNullParameter(local, "local");
            Intrinsics.checkNotNullParameter(newContainer, "newContainer");
            return new ExtractedLocalClass(local, newContainer, extractedUnder);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExtractedLocalClass)) {
                return false;
            }
            ExtractedLocalClass extractedLocalClass = (ExtractedLocalClass) other;
            return Intrinsics.areEqual(this.local, extractedLocalClass.local) && Intrinsics.areEqual(this.newContainer, extractedLocalClass.newContainer) && Intrinsics.areEqual(this.extractedUnder, extractedLocalClass.extractedUnder);
        }

        public int hashCode() {
            int iHashCode = ((this.local.hashCode() * 31) + this.newContainer.hashCode()) * 31;
            IrStatement irStatement = this.extractedUnder;
            return iHashCode + (irStatement == null ? 0 : irStatement.hashCode());
        }

        public String toString() {
            return "ExtractedLocalClass(local=" + this.local + ", newContainer=" + this.newContainer + ", extractedUnder=" + this.extractedUnder + ')';
        }

        public ExtractedLocalClass(IrClass local, IrDeclarationParent newContainer, IrStatement irStatement) {
            Intrinsics.checkNotNullParameter(local, "local");
            Intrinsics.checkNotNullParameter(newContainer, "newContainer");
            this.local = local;
            this.newContainer = newContainer;
            this.extractedUnder = irStatement;
        }

        public final IrStatement getExtractedUnder() {
            return this.extractedUnder;
        }

        public final IrClass getLocal() {
            return this.local;
        }

        public final IrDeclarationParent getNewContainer() {
            return this.newContainer;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, final IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        final ArrayList<ExtractedLocalClass> arrayList = new ArrayList();
        irBody.transform((IrElementTransformer<? super Object>) new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering.lower.1
            @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
            public IrStatement visitClassNew(IrClass declaration) {
                ScopeWithIr scopeWithIrCreateScope;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (getAllScopes().size() > 1) {
                    scopeWithIrCreateScope = getAllScopes().get(CollectionsKt.getLastIndex(getAllScopes()) - 1);
                } else {
                    IrDeclaration irDeclaration = container;
                    Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
                    scopeWithIrCreateScope = createScope(irDeclaration);
                }
                if (!this.shouldPopUp(declaration, scopeWithIrCreateScope)) {
                    return declaration;
                }
                IrStatement irStatement = declaration;
                IrDeclarationParent parent = declaration.getParent();
                while ((parent instanceof IrDeclaration) && !(parent instanceof IrClass) && !(parent instanceof IrScript)) {
                    irStatement = (IrStatement) parent;
                    parent = ((IrDeclaration) parent).getParent();
                }
                if (parent instanceof IrStatementContainer) {
                    if (Intrinsics.areEqual(irStatement, declaration)) {
                        IrStatementContainer irStatementContainer = (IrStatementContainer) parent;
                        Integer numValueOf = Integer.valueOf(irStatementContainer.getStatements().indexOf(irStatement) + 1);
                        int iIntValue = numValueOf.intValue();
                        if (iIntValue <= 0 || iIntValue >= irStatementContainer.getStatements().size()) {
                            numValueOf = null;
                        }
                        if (numValueOf != null) {
                            irStatement = irStatementContainer.getStatements().get(numValueOf.intValue());
                        } else {
                            irStatement = null;
                        }
                    }
                    arrayList.add(new ExtractedLocalClass(declaration, parent, irStatement));
                } else {
                    if (!(parent instanceof IrDeclarationContainer)) {
                        throw new IllegalStateException(("Inexpected container type " + parent).toString());
                    }
                    arrayList.add(new ExtractedLocalClass(declaration, parent, irStatement));
                }
                return new IrCompositeImpl(declaration.getStartOffset(), declaration.getEndOffset(), this.getContext().getIrBuiltIns().getUnitType(), null, 8, null);
            }
        }, (Object) null);
        for (ExtractedLocalClass extractedLocalClass : arrayList) {
            IrClass local = extractedLocalClass.getLocal();
            IrDeclarationParent newContainer = extractedLocalClass.getNewContainer();
            IrStatement extractedUnder = extractedLocalClass.getExtractedUnder();
            if (newContainer instanceof IrStatementContainer) {
                int iIndexOf = extractedUnder != null ? ((IrStatementContainer) newContainer).getStatements().indexOf(extractedUnder) : -1;
                if (iIndexOf >= 0) {
                    ((IrStatementContainer) newContainer).getStatements().add(iIndexOf, local);
                } else {
                    ((IrStatementContainer) newContainer).getStatements().add(local);
                }
                IrUtilsKt.setDeclarationsParent(local, newContainer);
            } else if (newContainer instanceof IrDeclarationContainer) {
                IrUtilsKt.addChild((IrDeclarationContainer) newContainer, local);
            } else {
                throw new IllegalStateException(("Inexpected container type " + newContainer).toString());
            }
            this.recordExtractedLocalClasses.invoke(this.context, local);
        }
    }

    protected boolean shouldPopUp(IrClass klass, ScopeWithIr currentScope) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return LocalDeclarationsLoweringKt.isLocalNotInner(klass);
    }
}
