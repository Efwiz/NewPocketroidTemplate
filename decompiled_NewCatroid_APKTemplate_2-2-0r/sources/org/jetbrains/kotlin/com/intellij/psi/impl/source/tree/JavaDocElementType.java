package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavadocParser;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocParamRef;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocTagImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiInlineDocTagImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IJavaDocElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaDocElementType {
    public static final TokenSet ALL_JAVADOC_ELEMENTS;
    public static final ILazyParseableElementType DOC_COMMENT;
    public static final IElementType DOC_INLINE_TAG;
    public static final IElementType DOC_METHOD_OR_FIELD_REF;
    public static final IElementType DOC_PARAMETER_REF;
    public static final ILazyParseableElementType DOC_REFERENCE_HOLDER;
    public static final IElementType DOC_TAG;
    public static final IElementType DOC_TAG_VALUE_ELEMENT;
    public static final ILazyParseableElementType DOC_TYPE_HOLDER;

    public static final class JavaDocCompositeElementType extends IJavaDocElementType implements ICompositeElementType {
        private final Supplier<? extends ASTNode> myConstructor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "nodeClass";
            } else if (i != 2) {
                objArr[0] = "debugName";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaDocElementType$JavaDocCompositeElementType";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaDocElementType$JavaDocCompositeElementType";
            } else {
                objArr[1] = "createCompositeNode";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        /* synthetic */ JavaDocCompositeElementType(String str, Supplier supplier, AnonymousClass1 anonymousClass1) {
            this(str, supplier);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private JavaDocCompositeElementType(String str, Supplier<? extends ASTNode> supplier) {
            super(str);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (supplier == null) {
                $$$reportNull$$$0(1);
            }
            this.myConstructor = supplier;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            ASTNode aSTNode = this.myConstructor.get();
            if (aSTNode == null) {
                $$$reportNull$$$0(2);
            }
            return aSTNode;
        }
    }

    public static class JavaDocLazyElementType extends ILazyParseableElementType {
        /* synthetic */ JavaDocLazyElementType(String str, AnonymousClass1 anonymousClass1) {
            this(str);
        }

        private JavaDocLazyElementType(String str) {
            super(str, JavaLanguage.INSTANCE);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
        public ASTNode createNode(CharSequence charSequence) {
            return new LazyParseablePsiElement(this, charSequence);
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        JavaDocCompositeElementType javaDocCompositeElementType = new JavaDocCompositeElementType("DOC_TAG", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaDocElementType.lambda$static$0();
            }
        }, anonymousClass1);
        DOC_TAG = javaDocCompositeElementType;
        JavaDocCompositeElementType javaDocCompositeElementType2 = new JavaDocCompositeElementType("DOC_INLINE_TAG", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaDocElementType.lambda$static$1();
            }
        }, anonymousClass1);
        DOC_INLINE_TAG = javaDocCompositeElementType2;
        JavaDocCompositeElementType javaDocCompositeElementType3 = new JavaDocCompositeElementType("DOC_METHOD_OR_FIELD_REF", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaDocElementType.lambda$static$2();
            }
        }, anonymousClass1);
        DOC_METHOD_OR_FIELD_REF = javaDocCompositeElementType3;
        JavaDocCompositeElementType javaDocCompositeElementType4 = new JavaDocCompositeElementType("DOC_PARAMETER_REF", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaDocElementType.lambda$static$3();
            }
        }, anonymousClass1);
        DOC_PARAMETER_REF = javaDocCompositeElementType4;
        IJavaDocElementType iJavaDocElementType = new IJavaDocElementType("DOC_TAG_VALUE_ELEMENT");
        DOC_TAG_VALUE_ELEMENT = iJavaDocElementType;
        AnonymousClass1 anonymousClass2 = new AnonymousClass1("DOC_REFERENCE_HOLDER");
        DOC_REFERENCE_HOLDER = anonymousClass2;
        AnonymousClass2 anonymousClass3 = new AnonymousClass2("DOC_TYPE_HOLDER");
        DOC_TYPE_HOLDER = anonymousClass3;
        AnonymousClass3 anonymousClass4 = new AnonymousClass3("DOC_COMMENT", JavaLanguage.INSTANCE);
        DOC_COMMENT = anonymousClass4;
        ALL_JAVADOC_ELEMENTS = TokenSet.create(javaDocCompositeElementType, javaDocCompositeElementType2, javaDocCompositeElementType3, javaDocCompositeElementType4, iJavaDocElementType, anonymousClass2, anonymousClass3, anonymousClass4);
    }

    static /* synthetic */ ASTNode lambda$static$0() {
        return new PsiDocTagImpl();
    }

    static /* synthetic */ ASTNode lambda$static$1() {
        return new PsiInlineDocTagImpl();
    }

    static /* synthetic */ ASTNode lambda$static$2() {
        return new PsiDocMethodOrFieldRef();
    }

    static /* synthetic */ ASTNode lambda$static$3() {
        return new PsiDocParamRef();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$1, reason: invalid class name */
    class AnonymousClass1 extends JavaDocLazyElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaDocElementType$1", "parseContents"));
        }

        AnonymousClass1(String str) {
            super(str, null);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavadocParser.parseJavadocReference(psiBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser, false, LanguageLevel.JDK_1_3);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$2, reason: invalid class name */
    class AnonymousClass2 extends JavaDocLazyElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaDocElementType$2", "parseContents"));
        }

        AnonymousClass2(String str) {
            super(str, null);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$2$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavadocParser.parseJavadocType(psiBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser, false, LanguageLevel.JDK_1_3);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$3, reason: invalid class name */
    class AnonymousClass3 extends IReparseableElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "buffer";
            } else if (i == 2) {
                objArr[0] = "fileLanguage";
            } else if (i != 3) {
                objArr[0] = "chameleon";
            } else {
                objArr[0] = "project";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaDocElementType$3";
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "isParsable";
            } else {
                objArr[2] = "parseContents";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        AnonymousClass3(String str, Language language) {
            super(str, language);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType$3$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavadocParser.parseDocCommentText(psiBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
        public ASTNode createNode(CharSequence charSequence) {
            return new PsiDocCommentImpl(charSequence);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementType
        public boolean isParsable(CharSequence charSequence, Language language, Project project) {
            if (charSequence == null) {
                $$$reportNull$$$0(1);
            }
            if (language == null) {
                $$$reportNull$$$0(2);
            }
            if (project == null) {
                $$$reportNull$$$0(3);
            }
            if (StringUtil.startsWith(charSequence, "/**") && StringUtil.endsWith(charSequence, "*/")) {
                Lexer lexerCreateLexer = JavaParserDefinition.createLexer(LanguageLevelProjectExtension.getInstance(project).getLanguageLevel());
                lexerCreateLexer.start(charSequence);
                if (lexerCreateLexer.getTokenType() == JavaDocElementType.DOC_COMMENT) {
                    lexerCreateLexer.advance();
                    return lexerCreateLexer.getTokenType() == null;
                }
            }
            return false;
        }
    }
}
