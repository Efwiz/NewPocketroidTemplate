package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.project.structure.KtCompilerPluginsProvider;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;
import org.jetbrains.kotlin.analysis.project.structure.KtSourceModule;
import org.jetbrains.kotlin.analysis.project.structure.ProjectStructureProvider;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtImportAlias;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: KtSimpleNameReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eJ$\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u0013\u001a\u00020\u0014H\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H&J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u000bH\u0016J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\u000bH&R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "expression", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "bindToElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "shorteningMode", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference$ShorteningMode;", "bindToFqName", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "targetElement", "canRename", "", "getImportAlias", "Lorg/jetbrains/kotlin/psi/KtImportAlias;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "isAssignmentResolved", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "binaryExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "isReferenceTo", "candidateTarget", "isReferenceToViaExtension", "ShorteningMode", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtSimpleNameReference extends KtSimpleReference<KtSimpleNameExpression> {

    /* JADX INFO: compiled from: KtSimpleNameReference.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference$ShorteningMode;", "", "(Ljava/lang/String;I)V", "NO_SHORTENING", "DELAYED_SHORTENING", "FORCED_SHORTENING", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ShorteningMode {
        NO_SHORTENING,
        DELAYED_SHORTENING,
        FORCED_SHORTENING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ShorteningMode> getEntries() {
            return $ENTRIES;
        }
    }

    public abstract KtImportAlias getImportAlias();

    public abstract boolean isReferenceToViaExtension(PsiElement element);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSimpleNameReference(KtSimpleNameExpression expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement candidateTarget) {
        Intrinsics.checkNotNullParameter(candidateTarget, "candidateTarget");
        if (!canBeReferenceTo(candidateTarget)) {
            return false;
        }
        if (isReferenceToViaExtension(candidateTarget)) {
            return true;
        }
        return super.isReferenceTo(candidateTarget);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        PsiElement referencedNameElement = ((KtSimpleNameExpression) getElement()).getReferencedNameElement();
        PsiElement element = getElement();
        Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
        TextRange textRangeShiftRight = referencedNameElement.getTextRange().shiftRight(-PsiUtilsKt.getStartOffset(element));
        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
        return textRangeShiftRight;
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        PsiElement expression = getExpression();
        AnonymousClass1 anonymousClass1 = new Function1<KtWhenConditionInRange, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.KtSimpleNameReference.canRename.1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtWhenConditionInRange getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getOperationReference();
            }
        };
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(expression, KtWhenConditionInRange.class, true);
        if ((parentOfType != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType, expression, anonymousClass1) : null) != null) {
            return false;
        }
        IElementType referencedNameElementType = ((KtSimpleNameExpression) getExpression()).getReferencedNameElementType();
        return (Intrinsics.areEqual(referencedNameElementType, KtTokens.PLUSPLUS) || Intrinsics.areEqual(referencedNameElementType, KtTokens.MINUSMINUS)) ? false : true;
    }

    public static /* synthetic */ PsiElement bindToElement$default(KtSimpleNameReference ktSimpleNameReference, PsiElement psiElement, ShorteningMode shorteningMode, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bindToElement");
        }
        if ((i & 2) != 0) {
            shorteningMode = ShorteningMode.DELAYED_SHORTENING;
        }
        return ktSimpleNameReference.bindToElement(psiElement, shorteningMode);
    }

    public final PsiElement bindToElement(PsiElement element, ShorteningMode shorteningMode) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(shorteningMode, "shorteningMode");
        return getKtReferenceMutateService().bindToElement(this, element, shorteningMode);
    }

    public static /* synthetic */ PsiElement bindToFqName$default(KtSimpleNameReference ktSimpleNameReference, FqName fqName, ShorteningMode shorteningMode, PsiElement psiElement, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bindToFqName");
        }
        if ((i & 2) != 0) {
            shorteningMode = ShorteningMode.DELAYED_SHORTENING;
        }
        if ((i & 4) != 0) {
            psiElement = null;
        }
        return ktSimpleNameReference.bindToFqName(fqName, shorteningMode, psiElement);
    }

    public final PsiElement bindToFqName(FqName fqName, ShorteningMode shorteningMode, PsiElement targetElement) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(shorteningMode, "shorteningMode");
        return getKtReferenceMutateService().bindToFqName(this, fqName, shorteningMode, targetElement);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0061  */
    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        KtOperationReferenceExpression ktOperationReferenceExpression;
        KtSingleValueToken operationSignTokenType;
        boolean z;
        PsiElement element = getElement();
        Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) element;
        if ((ktSimpleNameExpression instanceof KtOperationReferenceExpression) && (operationSignTokenType = (ktOperationReferenceExpression = (KtOperationReferenceExpression) ktSimpleNameExpression).getOperationSignTokenType()) != null) {
            Name nameForOperationSymbol = OperatorConventions.getNameForOperationSymbol(operationSignTokenType, ktOperationReferenceExpression.getLightParent() instanceof KtUnaryExpression, ktOperationReferenceExpression.getLightParent() instanceof KtBinaryExpression);
            if (nameForOperationSymbol == null) {
                PsiElement parent = ((KtSimpleNameExpression) getExpression()).getLightParent();
                Name name2 = null;
                KtBinaryExpression ktBinaryExpression = parent instanceof KtBinaryExpression ? (KtBinaryExpression) parent : null;
                if (ktBinaryExpression != null) {
                    if (Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ)) {
                        Project project = ktOperationReferenceExpression.getProject();
                        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                        if (isAssignmentResolved(project, ktBinaryExpression)) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        name2 = OperatorConventions.ASSIGN_METHOD;
                    }
                }
                nameForOperationSymbol = name2;
                if (nameForOperationSymbol == null) {
                    return CollectionsKt.emptyList();
                }
            }
            KtSingleValueToken ktSingleValueToken = OperatorConventions.ASSIGNMENT_OPERATION_COUNTERPARTS.get(operationSignTokenType);
            if (ktSingleValueToken != null) {
                Name nameForOperationSymbol2 = OperatorConventions.getNameForOperationSymbol(ktSingleValueToken, false, true);
                Intrinsics.checkNotNull(nameForOperationSymbol2);
                return CollectionsKt.listOf((Object[]) new Name[]{nameForOperationSymbol, nameForOperationSymbol2});
            }
            return CollectionsKt.listOf(nameForOperationSymbol);
        }
        return CollectionsKt.listOf(ktSimpleNameExpression.getReferencedNameAsName());
    }

    private final boolean isAssignmentResolved(Project project, KtBinaryExpression binaryExpression) {
        PsiReference reference;
        KtModule module = ProjectStructureProvider.INSTANCE.getModule(project, binaryExpression, null);
        if (!(module instanceof KtSourceModule) || (reference = binaryExpression.getOperationReference().getReference()) == null) {
            return false;
        }
        KtCompilerPluginsProvider ktCompilerPluginsProvider = (KtCompilerPluginsProvider) project.getService(KtCompilerPluginsProvider.class);
        if (ktCompilerPluginsProvider == null) {
            throw new IllegalStateException("KtAssignResolutionPresenceService is not available as a service".toString());
        }
        if (!ktCompilerPluginsProvider.isPluginOfTypeRegistered((KtSourceModule) module, KtCompilerPluginsProvider.CompilerPluginType.ASSIGNMENT)) {
            return false;
        }
        PsiElement psiElementResolve = reference.resolve();
        KtNamedFunction ktNamedFunction = psiElementResolve instanceof KtNamedFunction ? (KtNamedFunction) psiElementResolve : null;
        return Intrinsics.areEqual(ktNamedFunction != null ? ktNamedFunction.getNameAsName() : null, OperatorConventions.ASSIGN_METHOD);
    }
}
