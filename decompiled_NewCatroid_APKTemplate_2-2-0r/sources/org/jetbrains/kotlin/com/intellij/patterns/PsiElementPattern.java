package org.jetbrains.kotlin.com.intellij.patterns;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiElementPattern<T extends PsiElement, Self extends PsiElementPattern<T, Self>> extends TreeElementPattern<PsiElement, T, Self> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 38:
            case 41:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 5:
            case 7:
            case 9:
            case 11:
            case 16:
            case 19:
            case 21:
            case 23:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 37:
            case 39:
            case 40:
            case 42:
            case 44:
            case 48:
            case 50:
            case 52:
            case 59:
            case 60:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 38:
            case 41:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
                i2 = 2;
                break;
            case 5:
            case 7:
            case 9:
            case 11:
            case 16:
            case 19:
            case 21:
            case 23:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 37:
            case 39:
            case 40:
            case 42:
            case 44:
            case 48:
            case 50:
            case 52:
            case 59:
            case 60:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 38:
            case 41:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/PsiElementPattern";
                break;
            case 5:
            case 9:
                objArr[0] = "withText";
                break;
            case 7:
            case 11:
            case 16:
            case 37:
            case 40:
            case 42:
            case 60:
                objArr[0] = "pattern";
                break;
            case 19:
            case 21:
                objArr[0] = "filePattern";
                break;
            case 23:
                objArr[0] = "o";
                break;
            case 26:
            case 28:
            case 48:
            case 50:
                objArr[0] = "text";
                break;
            case 30:
            case 34:
                objArr[0] = "name";
                break;
            case 32:
                objArr[0] = "names";
                break;
            case 36:
            case 39:
            case 59:
                objArr[0] = SchemaSymbols.ATTVAL_SKIP;
                break;
            case 44:
                objArr[0] = "lengthPattern";
                break;
            case 52:
                objArr[0] = "language";
                break;
            default:
                objArr[0] = "condition";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 25:
                objArr[1] = "withElementType";
                break;
            case 5:
            case 7:
            case 9:
            case 11:
            case 16:
            case 19:
            case 21:
            case 23:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 37:
            case 39:
            case 40:
            case 42:
            case 44:
            case 48:
            case 50:
            case 52:
            case 59:
            case 60:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/PsiElementPattern";
                break;
            case 6:
            case 8:
                objArr[1] = "afterLeaf";
                break;
            case 10:
            case 12:
                objArr[1] = "beforeLeaf";
                break;
            case 13:
                objArr[1] = "whitespace";
                break;
            case 14:
                objArr[1] = "whitespaceCommentOrError";
                break;
            case 15:
                objArr[1] = "whitespaceCommentEmptyOrError";
                break;
            case 17:
                objArr[1] = "withFirstNonWhitespaceChild";
                break;
            case 18:
                objArr[1] = "withReference";
                break;
            case 20:
                objArr[1] = "inFile";
                break;
            case 22:
                objArr[1] = "inVirtualFile";
                break;
            case 24:
                objArr[1] = "equalTo";
                break;
            case 27:
            case 49:
                objArr[1] = "withText";
                break;
            case 29:
            case 51:
                objArr[1] = "withoutText";
                break;
            case 31:
            case 33:
            case 35:
                objArr[1] = "withName";
                break;
            case 38:
                objArr[1] = "afterLeafSkipping";
                break;
            case 41:
                objArr[1] = "beforeLeafSkipping";
                break;
            case 43:
                objArr[1] = "atStartOf";
                break;
            case 45:
                objArr[1] = "withTextLength";
                break;
            case 46:
                objArr[1] = "notEmpty";
                break;
            case 47:
                objArr[1] = "withTextLengthLongerThan";
                break;
            case 53:
                objArr[1] = "withLanguage";
                break;
            case 54:
                objArr[1] = "withMetaData";
                break;
            case 55:
                objArr[1] = "referencing";
                break;
            case 56:
                objArr[1] = "compiled";
                break;
            case 57:
                objArr[1] = "withTreeParent";
                break;
            case 58:
                objArr[1] = "insideStarting";
                break;
            case 61:
                objArr[1] = "withLastChildSkipping";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getChildren";
                break;
            case 2:
                objArr[2] = "getParent";
                break;
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 38:
            case 41:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
                break;
            case 5:
            case 7:
                objArr[2] = "afterLeaf";
                break;
            case 9:
            case 11:
                objArr[2] = "beforeLeaf";
                break;
            case 16:
                objArr[2] = "withFirstNonWhitespaceChild";
                break;
            case 19:
                objArr[2] = "inFile";
                break;
            case 21:
                objArr[2] = "inVirtualFile";
                break;
            case 23:
                objArr[2] = "equalTo";
                break;
            case 26:
            case 48:
                objArr[2] = "withText";
                break;
            case 28:
            case 50:
                objArr[2] = "withoutText";
                break;
            case 30:
            case 32:
            case 34:
                objArr[2] = "withName";
                break;
            case 36:
            case 37:
                objArr[2] = "afterLeafSkipping";
                break;
            case 39:
            case 40:
                objArr[2] = "beforeLeafSkipping";
                break;
            case 42:
                objArr[2] = "atStartOf";
                break;
            case 44:
                objArr[2] = "withTextLength";
                break;
            case 52:
                objArr[2] = "withLanguage";
                break;
            case 59:
            case 60:
                objArr[2] = "withLastChildSkipping";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 38:
            case 41:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
                throw new IllegalStateException(str2);
            case 5:
            case 7:
            case 9:
            case 11:
            case 16:
            case 19:
            case 21:
            case 23:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 37:
            case 39:
            case 40:
            case 42:
            case 44:
            case 48:
            case 50:
            case 52:
            case 59:
            case 60:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected PsiElementPattern(Class<T> cls) {
        super(cls);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.patterns.TreeElementPattern
    public PsiElement getParent(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if ((psiElement instanceof PsiFile) && InjectedLanguageManager.getInstance(psiElement.getProject()).isInjectedFragment((PsiFile) psiElement)) {
            return psiElement.getParent();
        }
        return psiElement.getContext();
    }

    public Self withElementType(IElementType iElementType) {
        Self self = (Self) withElementType(PlatformPatterns.elementType().equalTo(iElementType));
        if (self == null) {
            $$$reportNull$$$0(3);
        }
        return self;
    }

    public Self afterLeaf(ElementPattern<? extends PsiElement> elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(7);
        }
        Self self = (Self) afterLeafSkipping(PlatformPatterns.psiElement().whitespaceCommentEmptyOrError(), elementPattern);
        if (self == null) {
            $$$reportNull$$$0(8);
        }
        return self;
    }

    public Self whitespace() {
        Self self = (Self) withElementType(TokenType.WHITE_SPACE);
        if (self == null) {
            $$$reportNull$$$0(13);
        }
        return self;
    }

    public Self whitespaceCommentEmptyOrError() {
        Self selfAndOr = andOr(PlatformPatterns.psiElement().whitespace(), PlatformPatterns.psiElement(PsiComment.class), PlatformPatterns.psiElement(PsiErrorElement.class), PlatformPatterns.psiElement().withText(""));
        if (selfAndOr == null) {
            $$$reportNull$$$0(15);
        }
        return selfAndOr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern
    public Self equalTo(final T t) {
        if (t == null) {
            $$$reportNull$$$0(23);
        }
        Self self = (Self) with(new PatternCondition<T>("equalTo") { // from class: org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/PsiElementPattern$4", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t2, ProcessingContext processingContext) {
                if (t2 == null) {
                    $$$reportNull$$$0(0);
                }
                return t2.getManager().areElementsEquivalent(t2, t);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(24);
        }
        return self;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Self withElementType(final ElementPattern<IElementType> elementPattern) {
        Self self = (Self) with(new PatternCondition<T>("withElementType") { // from class: org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/PsiElementPattern$5", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                ASTNode node = t.getNode();
                return node != null && elementPattern.accepts(node.getElementType());
            }
        });
        if (self == null) {
            $$$reportNull$$$0(25);
        }
        return self;
    }

    public Self withText(String str) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        Self self = (Self) withText(StandardPatterns.string().equalTo(str));
        if (self == null) {
            $$$reportNull$$$0(27);
        }
        return self;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Self afterLeafSkipping(final ElementPattern elementPattern, final ElementPattern elementPattern2) {
        if (elementPattern == null) {
            $$$reportNull$$$0(36);
        }
        if (elementPattern2 == null) {
            $$$reportNull$$$0(37);
        }
        Self self = (Self) with(new PatternCondition<T>("afterLeafSkipping") { // from class: org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern.6
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/PsiElementPattern$6", "accepts"));
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                while (true) {
                    t = (T) PsiTreeUtil.prevLeaf(t);
                    if (t == null || t.getTextLength() != 0) {
                        if (!elementPattern.accepts(t, processingContext)) {
                            return elementPattern2.accepts(t, processingContext);
                        }
                    }
                }
            }
        });
        if (self == null) {
            $$$reportNull$$$0(38);
        }
        return self;
    }

    public Self withText(ElementPattern elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(48);
        }
        Self selfWith = with(_withText(elementPattern));
        if (selfWith == null) {
            $$$reportNull$$$0(49);
        }
        return selfWith;
    }

    private PatternCondition<T> _withText(ElementPattern elementPattern) {
        return new PatternConditionPlus<T, String>("_withText", elementPattern) { // from class: org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern.11
            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternConditionPlus
            public boolean processValues(T t, ProcessingContext processingContext, PairProcessor<? super String, ? super ProcessingContext> pairProcessor) {
                return pairProcessor.process(t.getText(), processingContext);
            }
        };
    }

    public static class Capture<T extends PsiElement> extends PsiElementPattern<T, Capture<T>> {
        @Override // org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern, org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern
        public /* bridge */ /* synthetic */ ObjectPattern equalTo(Object obj) {
            return super.equalTo((PsiElement) obj);
        }

        @Override // org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern, org.jetbrains.kotlin.com.intellij.patterns.TreeElementPattern
        protected /* bridge */ /* synthetic */ PsiElement getParent(PsiElement psiElement) {
            return super.getParent(psiElement);
        }

        protected Capture(Class<T> cls) {
            super(cls);
        }
    }
}
