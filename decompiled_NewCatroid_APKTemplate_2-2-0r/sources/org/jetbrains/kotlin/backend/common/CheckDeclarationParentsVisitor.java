package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.util.DeclarationParentsVisitor;

/* JADX INFO: compiled from: IrValidator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014R!\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CheckDeclarationParentsVisitor;", "Lorg/jetbrains/kotlin/ir/util/DeclarationParentsVisitor;", "()V", "errors", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/common/CheckDeclarationParentsVisitor$Error;", "Lkotlin/collections/ArrayList;", "getErrors", "()Ljava/util/ArrayList;", "handleParent", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Error", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckDeclarationParentsVisitor extends DeclarationParentsVisitor {
    private final ArrayList<Error> errors = new ArrayList<>();

    /* JADX INFO: compiled from: IrValidator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CheckDeclarationParentsVisitor$Error;", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "expectedParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "actualParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getActualParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getExpectedParent", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error {
        private final IrDeclarationParent actualParent;
        private final IrDeclaration declaration;
        private final IrDeclarationParent expectedParent;

        public Error(IrDeclaration declaration, IrDeclarationParent expectedParent, IrDeclarationParent irDeclarationParent) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(expectedParent, "expectedParent");
            this.declaration = declaration;
            this.expectedParent = expectedParent;
            this.actualParent = irDeclarationParent;
        }

        public final IrDeclarationParent getActualParent() {
            return this.actualParent;
        }

        public final IrDeclaration getDeclaration() {
            return this.declaration;
        }

        public final IrDeclarationParent getExpectedParent() {
            return this.expectedParent;
        }
    }

    public final ArrayList<Error> getErrors() {
        return this.errors;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeclarationParentsVisitor
    protected void handleParent(IrDeclaration declaration, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(parent, "parent");
        try {
            IrDeclarationParent parent2 = declaration.getParent();
            if (Intrinsics.areEqual(parent2, parent)) {
                return;
            }
            this.errors.add(new Error(declaration, parent, parent2));
        } catch (Exception unused) {
            this.errors.add(new Error(declaration, parent, null));
        }
    }
}
