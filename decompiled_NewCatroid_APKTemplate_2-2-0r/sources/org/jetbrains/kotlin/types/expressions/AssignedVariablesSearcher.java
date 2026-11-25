package org.jetbrains.kotlin.types.expressions;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.SetMultimap;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;

/* JADX INFO: compiled from: AssignedVariablesSearcher.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher;", "Lorg/jetbrains/kotlin/psi/KtTreeVisitorVoid;", "()V", "assignedNames", "Lorg/jetbrains/kotlin/com/google/common/collect/SetMultimap;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher$Writer;", "currentDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "hasWriters", "", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "visitBinaryExpression", "", "binaryExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "visitDeclaration", "declaration", "visitLambdaExpression", "lambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "writers", "", "Writer", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AssignedVariablesSearcher extends KtTreeVisitorVoid {
    private final SetMultimap<Name, Writer> assignedNames;
    private KtDeclaration currentDeclaration;

    public AssignedVariablesSearcher() {
        LinkedHashMultimap linkedHashMultimapCreate = LinkedHashMultimap.create();
        Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate, "create(...)");
        this.assignedNames = linkedHashMultimapCreate;
    }

    /* JADX INFO: compiled from: AssignedVariablesSearcher.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher$Writer;", "", "assignment", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "(Lorg/jetbrains/kotlin/psi/KtBinaryExpression;Lorg/jetbrains/kotlin/psi/KtDeclaration;)V", "getAssignment", "()Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Writer {
        private final KtBinaryExpression assignment;
        private final KtDeclaration declaration;

        public static /* synthetic */ Writer copy$default(Writer writer, KtBinaryExpression ktBinaryExpression, KtDeclaration ktDeclaration, int i, Object obj) {
            if ((i & 1) != 0) {
                ktBinaryExpression = writer.assignment;
            }
            if ((i & 2) != 0) {
                ktDeclaration = writer.declaration;
            }
            return writer.copy(ktBinaryExpression, ktDeclaration);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KtBinaryExpression getAssignment() {
            return this.assignment;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtDeclaration getDeclaration() {
            return this.declaration;
        }

        public final Writer copy(KtBinaryExpression assignment, KtDeclaration declaration) {
            Intrinsics.checkNotNullParameter(assignment, "assignment");
            return new Writer(assignment, declaration);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Writer)) {
                return false;
            }
            Writer writer = (Writer) other;
            return Intrinsics.areEqual(this.assignment, writer.assignment) && Intrinsics.areEqual(this.declaration, writer.declaration);
        }

        public int hashCode() {
            int iHashCode = this.assignment.hashCode() * 31;
            KtDeclaration ktDeclaration = this.declaration;
            return iHashCode + (ktDeclaration == null ? 0 : ktDeclaration.hashCode());
        }

        public String toString() {
            return "Writer(assignment=" + this.assignment + ", declaration=" + this.declaration + ')';
        }

        public Writer(KtBinaryExpression assignment, KtDeclaration ktDeclaration) {
            Intrinsics.checkNotNullParameter(assignment, "assignment");
            this.assignment = assignment;
            this.declaration = ktDeclaration;
        }

        public final KtBinaryExpression getAssignment() {
            return this.assignment;
        }

        public final KtDeclaration getDeclaration() {
            return this.declaration;
        }
    }

    public Set<Writer> writers(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Set<Writer> set = this.assignedNames.get(variableDescriptor.getShortName());
        Intrinsics.checkNotNullExpressionValue(set, "get(...)");
        return set;
    }

    public final boolean hasWriters(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        return !writers(variableDescriptor).isEmpty();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitDeclaration(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        KtDeclaration ktDeclaration = this.currentDeclaration;
        if ((declaration instanceof KtDeclarationWithBody) || (declaration instanceof KtClassOrObject) || (declaration instanceof KtAnonymousInitializer)) {
            this.currentDeclaration = declaration;
        }
        super.visitDeclaration(declaration);
        this.currentDeclaration = ktDeclaration;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitLambdaExpression(KtLambdaExpression lambdaExpression) {
        Intrinsics.checkNotNullParameter(lambdaExpression, "lambdaExpression");
        KtDeclaration ktDeclaration = this.currentDeclaration;
        this.currentDeclaration = lambdaExpression.getFunctionLiteral();
        super.visitLambdaExpression(lambdaExpression);
        this.currentDeclaration = ktDeclaration;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitBinaryExpression(KtBinaryExpression binaryExpression) {
        Intrinsics.checkNotNullParameter(binaryExpression, "binaryExpression");
        if (binaryExpression.getOperationToken() == KtTokens.EQ) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(binaryExpression.getLeft());
            if (ktExpressionDeparenthesize instanceof KtNameReferenceExpression) {
                this.assignedNames.put(((KtNameReferenceExpression) ktExpressionDeparenthesize).getReferencedNameAsName(), new Writer(binaryExpression, this.currentDeclaration));
            }
        }
        super.visitBinaryExpression(binaryExpression);
    }
}
