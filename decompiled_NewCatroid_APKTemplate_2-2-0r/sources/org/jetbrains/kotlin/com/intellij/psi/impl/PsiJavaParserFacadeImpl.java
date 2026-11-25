package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.DeclarationParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaParserFacadeImpl implements PsiJavaParserFacade {
    private static final Map<String, PsiPrimitiveType> PRIMITIVE_TYPES;
    protected final PsiManager myManager;
    private static final String DUMMY_FILE_NAME = "_Dummy_." + JavaFileType.INSTANCE.getDefaultExtension();
    private static final JavaParserUtil.ParserWrapper ANNOTATION = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseAnnotation(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper PARAMETER = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda9
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseParameter(psiBuilder, true, false, false);
        }
    };
    private static final JavaParserUtil.ParserWrapper RESOURCE = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda10
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseResource(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper TYPE = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda11
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getReferenceParser().parseType(psiBuilder, 151);
        }
    };
    public static final JavaParserUtil.ParserWrapper REFERENCE = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda12
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getReferenceParser().parseJavaCodeReference(psiBuilder, false, true, false, false);
        }
    };
    private static final JavaParserUtil.ParserWrapper DIAMOND_REF = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda13
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getReferenceParser().parseJavaCodeReference(psiBuilder, false, true, false, true);
        }
    };
    private static final JavaParserUtil.ParserWrapper STATIC_IMPORT_REF = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getReferenceParser().parseImportCodeReference(psiBuilder, true);
        }
    };
    private static final JavaParserUtil.ParserWrapper TYPE_PARAMETER = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda2
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getReferenceParser().parseTypeParameter(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper DECLARATION = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda3
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parse(psiBuilder, DeclarationParser.Context.CLASS);
        }
    };
    private static final JavaParserUtil.ParserWrapper CODE_BLOCK = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda4
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getStatementParser().parseCodeBlockDeep(psiBuilder, true);
        }
    };
    private static final JavaParserUtil.ParserWrapper STATEMENT = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda5
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getStatementParser().parseStatement(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper EXPRESSION = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda6
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getExpressionParser().parse(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper ENUM_CONSTANT = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda7
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseEnumConstant(psiBuilder);
        }
    };
    private static final JavaParserUtil.ParserWrapper MODULE = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl$$ExternalSyntheticLambda8
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getModuleParser().parse(psiBuilder);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 12:
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 42:
            case 44:
            case 46:
            case 48:
            case 50:
                objArr[0] = "text";
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiJavaParserFacadeImpl";
                break;
            case 9:
                objArr[0] = "body";
                break;
            case 11:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "createAnnotationFromText";
                break;
            case 4:
                objArr[1] = "createDocTagFromText";
                break;
            case 6:
            case 8:
                objArr[1] = "createDocCommentFromText";
                break;
            case 10:
                objArr[1] = "createClassFromText";
                break;
            case 13:
                objArr[1] = "createRecordHeaderFromText";
                break;
            case 15:
                objArr[1] = "createRecordFromText";
                break;
            case 17:
                objArr[1] = "createFieldFromText";
                break;
            case 19:
            case 21:
                objArr[1] = "createMethodFromText";
                break;
            case 23:
                objArr[1] = "createParameterFromText";
                break;
            case 25:
                objArr[1] = "createResourceFromText";
                break;
            case 27:
                objArr[1] = "createTypeFromText";
                break;
            case 29:
                objArr[1] = "createTypeElementFromText";
                break;
            case 31:
                objArr[1] = "createReferenceFromText";
                break;
            case 33:
                objArr[1] = "createCodeBlockFromText";
                break;
            case 35:
                objArr[1] = "createStatementFromText";
                break;
            case 37:
                objArr[1] = "createExpressionFromText";
                break;
            case 39:
                objArr[1] = "createTypeParameterFromText";
                break;
            case 41:
                objArr[1] = "createCommentFromText";
                break;
            case 43:
                objArr[1] = "createEnumConstantFromText";
                break;
            case 45:
                objArr[1] = "createPrimitiveTypeFromText";
                break;
            case 47:
                objArr[1] = "createModuleFromText";
                break;
            case 49:
                objArr[1] = "createModuleStatementFromText";
                break;
            case 51:
                objArr[1] = "createModuleReferenceFromText";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiJavaParserFacadeImpl";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "createAnnotationFromText";
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
                break;
            case 3:
                objArr[2] = "createDocTagFromText";
                break;
            case 5:
            case 7:
                objArr[2] = "createDocCommentFromText";
                break;
            case 9:
                objArr[2] = "createClassFromText";
                break;
            case 11:
                objArr[2] = "createRecord";
                break;
            case 12:
                objArr[2] = "createRecordHeaderFromText";
                break;
            case 14:
                objArr[2] = "createRecordFromText";
                break;
            case 16:
                objArr[2] = "createFieldFromText";
                break;
            case 18:
            case 20:
                objArr[2] = "createMethodFromText";
                break;
            case 22:
                objArr[2] = "createParameterFromText";
                break;
            case 24:
                objArr[2] = "createResourceFromText";
                break;
            case 26:
                objArr[2] = "createTypeFromText";
                break;
            case 28:
                objArr[2] = "createTypeElementFromText";
                break;
            case 30:
                objArr[2] = "createReferenceFromText";
                break;
            case 32:
                objArr[2] = "createCodeBlockFromText";
                break;
            case 34:
                objArr[2] = "createStatementFromText";
                break;
            case 36:
                objArr[2] = "createExpressionFromText";
                break;
            case 38:
                objArr[2] = "createTypeParameterFromText";
                break;
            case 40:
                objArr[2] = "createCommentFromText";
                break;
            case 42:
                objArr[2] = "createEnumConstantFromText";
                break;
            case 44:
                objArr[2] = "createPrimitiveTypeFromText";
                break;
            case 46:
                objArr[2] = "createModuleFromText";
                break;
            case 48:
                objArr[2] = "createModuleStatementFromText";
                break;
            case 50:
                objArr[2] = "createModuleReferenceFromText";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    static {
        HashMap map = new HashMap();
        PRIMITIVE_TYPES = map;
        map.put(PsiType.BYTE.getCanonicalText(), PsiType.BYTE);
        map.put(PsiType.CHAR.getCanonicalText(), PsiType.CHAR);
        map.put(PsiType.DOUBLE.getCanonicalText(), PsiType.DOUBLE);
        map.put(PsiType.FLOAT.getCanonicalText(), PsiType.FLOAT);
        map.put(PsiType.INT.getCanonicalText(), PsiType.INT);
        map.put(PsiType.LONG.getCanonicalText(), PsiType.LONG);
        map.put(PsiType.SHORT.getCanonicalText(), PsiType.SHORT);
        map.put(PsiType.BOOLEAN.getCanonicalText(), PsiType.BOOLEAN);
        map.put(PsiType.VOID.getCanonicalText(), PsiType.VOID);
        map.put(PsiType.NULL.getCanonicalText(), PsiType.NULL);
    }

    public PsiJavaParserFacadeImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = PsiManager.getInstance(project);
    }

    protected PsiJavaFile createDummyJavaFile(String str) {
        return (PsiJavaFile) PsiFileFactory.getInstance(this.myManager.getProject()).createFileFromText(DUMMY_FILE_NAME, JavaFileType.INSTANCE, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiAnnotation createAnnotationFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, ANNOTATION, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiAnnotation)) {
            throw newException("Incorrect annotation '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiAnnotation psiAnnotation = (PsiAnnotation) psiElementTreeElementToPsi;
        if (psiAnnotation == null) {
            $$$reportNull$$$0(2);
        }
        return psiAnnotation;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiDocTag createDocTagFromText(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PsiDocTag psiDocTag = createDocCommentFromText(StringUtil.join("/**\n", str, "\n */")).getTags()[0];
        if (psiDocTag == null) {
            $$$reportNull$$$0(4);
        }
        return psiDocTag;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiDocComment createDocCommentFromText(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        PsiDocComment psiDocCommentCreateDocCommentFromText = createDocCommentFromText(str, null);
        if (psiDocCommentCreateDocCommentFromText == null) {
            $$$reportNull$$$0(6);
        }
        return psiDocCommentCreateDocCommentFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiDocComment createDocCommentFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        PsiDocComment docComment = createMethodFromText(str.trim() + "void m();", psiElement).mo6091getDocComment();
        if (docComment == null) {
            throw new IncorrectOperationException("Incorrect comment '" + str + FormatHelper.QUOTE);
        }
        if (docComment == null) {
            $$$reportNull$$$0(8);
        }
        return docComment;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiClass createClassFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        PsiClass[] classes = createDummyJavaFile(StringUtil.join("class _Dummy_ {\n", str, "\n}")).getClasses();
        if (classes.length != 1) {
            throw new IncorrectOperationException("Incorrect class '" + str + FormatHelper.QUOTE);
        }
        PsiClass psiClass = classes[0];
        if (psiClass == null) {
            $$$reportNull$$$0(10);
        }
        return psiClass;
    }

    public PsiClass createRecord(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return createRecordFromText("public record " + str + "() { }");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiRecordHeader createRecordHeaderFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        PsiRecordHeader recordHeader = createRecordFromText("public record Record(" + str + ") { }").getRecordHeader();
        if (recordHeader == null) {
            throw new IncorrectOperationException("Incorrect record component '" + str + FormatHelper.QUOTE);
        }
        if (recordHeader == null) {
            $$$reportNull$$$0(13);
        }
        return recordHeader;
    }

    private PsiClass createRecordFromText(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, DECLARATION, LanguageLevel.JDK_16), (PsiElement) null);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiClass)) {
            throw newException("Incorrect class '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiClass psiClass = (PsiClass) psiElementTreeElementToPsi;
        if (psiClass == null) {
            $$$reportNull$$$0(15);
        }
        return psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiField createFieldFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, DECLARATION, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiField)) {
            throw newException("Incorrect field '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiField psiField = (PsiField) psiElementTreeElementToPsi;
        if (psiField == null) {
            $$$reportNull$$$0(17);
        }
        return psiField;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiMethod createMethodFromText(String str, PsiElement psiElement, LanguageLevel languageLevel) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, DECLARATION, languageLevel), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiMethod)) {
            throw newException("Incorrect method '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiMethod psiMethod = (PsiMethod) psiElementTreeElementToPsi;
        if (psiMethod == null) {
            $$$reportNull$$$0(19);
        }
        return psiMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiMethod createMethodFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        PsiMethod psiMethodCreateMethodFromText = createMethodFromText(str, psiElement, LanguageLevelProjectExtension.getInstance(this.myManager.getProject()).getLanguageLevel());
        if (psiMethodCreateMethodFromText == null) {
            $$$reportNull$$$0(21);
        }
        return psiMethodCreateMethodFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiParameter createParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, PARAMETER, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiParameter)) {
            throw newException("Incorrect parameter '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiParameter psiParameter = (PsiParameter) psiElementTreeElementToPsi;
        if (psiParameter == null) {
            $$$reportNull$$$0(23);
        }
        return psiParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiResourceVariable createResourceFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, RESOURCE, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiResourceVariable)) {
            throw newException("Incorrect resource '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiResourceVariable psiResourceVariable = (PsiResourceVariable) psiElementTreeElementToPsi;
        if (psiResourceVariable == null) {
            $$$reportNull$$$0(25);
        }
        return psiResourceVariable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiType createTypeFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        PsiType psiTypeCreateTypeInner = createTypeInner(str, psiElement, false);
        if (psiTypeCreateTypeInner == null) {
            $$$reportNull$$$0(27);
        }
        return psiTypeCreateTypeInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiTypeElement createTypeElementFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        LanguageLevel languageLevelLevel = level(psiElement);
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, TYPE, languageLevelLevel), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiTypeElement)) {
            throw newException("Incorrect type '" + str + "' (" + languageLevelLevel + Constant.AFTER_QUTO, dummyHolderCreateHolder);
        }
        PsiTypeElement psiTypeElement = (PsiTypeElement) psiElementTreeElementToPsi;
        if (psiTypeElement == null) {
            $$$reportNull$$$0(29);
        }
        return psiTypeElement;
    }

    PsiType createTypeInner(String str, PsiElement psiElement, boolean z) throws IncorrectOperationException {
        PsiPrimitiveType psiPrimitiveType = PRIMITIVE_TYPES.get(str);
        if (psiPrimitiveType != null) {
            return psiPrimitiveType;
        }
        PsiTypeElement psiTypeElementCreateTypeElementFromText = createTypeElementFromText(str, psiElement);
        if (z) {
            GeneratedMarkerVisitor.markGenerated(psiTypeElementCreateTypeElementFromText);
        }
        return psiTypeElementCreateTypeElementFromText.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiJavaCodeReferenceElement createReferenceFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        JavaParserUtil.ParserWrapper parserWrapper;
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        boolean z = (psiElement instanceof PsiImportStaticStatement) && !((PsiImportStaticStatement) psiElement).isOnDemand();
        boolean z2 = (psiElement instanceof PsiNewExpression) && PsiUtil.getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_7);
        if (z) {
            parserWrapper = STATIC_IMPORT_REF;
        } else {
            parserWrapper = z2 ? DIAMOND_REF : REFERENCE;
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, parserWrapper, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiJavaCodeReferenceElement)) {
            throw newException("Incorrect reference '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        if (psiElement instanceof PsiIdentifier) {
            psiElement = psiElement.getLightParent();
        }
        if ((psiElementTreeElementToPsi instanceof PsiJavaCodeReferenceElementImpl) && (psiElement instanceof PsiJavaCodeReferenceElementImpl)) {
            ((PsiJavaCodeReferenceElementImpl) psiElementTreeElementToPsi).setKindWhenDummy(((PsiJavaCodeReferenceElementImpl) psiElement).getKindEnum(psiElement.getContainingFile()));
        }
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) psiElementTreeElementToPsi;
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(31);
        }
        return psiJavaCodeReferenceElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiCodeBlock createCodeBlockFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, CODE_BLOCK, level(psiElement), true), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiCodeBlock)) {
            throw newException("Incorrect code block '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiCodeBlock psiCodeBlock = (PsiCodeBlock) psiElementTreeElementToPsi;
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(33);
        }
        return psiCodeBlock;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiStatement createStatementFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, STATEMENT, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiStatement)) {
            throw newException("Incorrect statement '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiStatement psiStatement = (PsiStatement) psiElementTreeElementToPsi;
        if (psiStatement == null) {
            $$$reportNull$$$0(35);
        }
        return psiStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade, org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiExpression createExpressionFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, EXPRESSION, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiExpression)) {
            throw newException("Incorrect expression '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiExpression psiExpression = (PsiExpression) psiElementTreeElementToPsi;
        if (psiExpression == null) {
            $$$reportNull$$$0(37);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiTypeParameter createTypeParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(38);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, TYPE_PARAMETER, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiTypeParameter)) {
            throw newException("Incorrect type parameter '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiTypeParameter psiTypeParameter = (PsiTypeParameter) psiElementTreeElementToPsi;
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(39);
        }
        return psiTypeParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiComment createCommentFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(40);
        }
        for (PsiElement psiElement2 : createDummyJavaFile(str).getChildren()) {
            if (psiElement2 instanceof PsiComment) {
                if (!psiElement2.getText().equals(str)) {
                    break;
                }
                PsiComment psiComment = (PsiComment) psiElement2;
                DummyHolderFactory.createHolder(this.myManager, (TreeElement) SourceTreeToPsiMap.psiElementToTree(psiComment), psiElement);
                if (psiComment == null) {
                    $$$reportNull$$$0(41);
                }
                return psiComment;
            }
        }
        throw new IncorrectOperationException("Incorrect comment '" + str + FormatHelper.QUOTE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiEnumConstant createEnumConstantFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, ENUM_CONSTANT, level(psiElement)), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiEnumConstant)) {
            throw newException("Incorrect enum constant '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiEnumConstant psiEnumConstant = (PsiEnumConstant) psiElementTreeElementToPsi;
        if (psiEnumConstant == null) {
            $$$reportNull$$$0(43);
        }
        return psiEnumConstant;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiType createPrimitiveTypeFromText(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(44);
        }
        PsiPrimitiveType primitiveType = getPrimitiveType(str);
        if (primitiveType == null) {
            throw new IncorrectOperationException("Incorrect primitive type '" + str + FormatHelper.QUOTE);
        }
        if (primitiveType == null) {
            $$$reportNull$$$0(45);
        }
        return primitiveType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiJavaModule createModuleFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(46);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str, MODULE, LanguageLevel.JDK_1_9), psiElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(dummyHolderCreateHolder.getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiJavaModule)) {
            throw newException("Incorrect module declaration '" + str + FormatHelper.QUOTE, dummyHolderCreateHolder);
        }
        PsiJavaModule psiJavaModule = (PsiJavaModule) psiElementTreeElementToPsi;
        if (psiJavaModule == null) {
            $$$reportNull$$$0(47);
        }
        return psiJavaModule;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiStatement createModuleStatementFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(48);
        }
        PsiStatement psiStatement = (PsiStatement) PsiTreeUtil.getChildOfType(createModuleFromText("module M { " + str + "; }", psiElement), PsiStatement.class);
        if (psiStatement == null) {
            throw new IncorrectOperationException("Incorrect module statement '" + str + FormatHelper.QUOTE);
        }
        if (psiStatement == null) {
            $$$reportNull$$$0(49);
        }
        return psiStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiJavaModuleReferenceElement createModuleReferenceFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(50);
        }
        PsiJavaModuleReferenceElement psiJavaModuleReferenceElementMo7392getNameIdentifier = createModuleFromText("module " + str + " {}", psiElement).mo7392getNameIdentifier();
        if (psiJavaModuleReferenceElementMo7392getNameIdentifier == null) {
            $$$reportNull$$$0(51);
        }
        return psiJavaModuleReferenceElementMo7392getNameIdentifier;
    }

    public static PsiPrimitiveType getPrimitiveType(String str) {
        return PRIMITIVE_TYPES.get(str);
    }

    protected static LanguageLevel level(PsiElement psiElement) {
        return (psiElement == null || !psiElement.isValid()) ? LanguageLevel.HIGHEST : PsiUtil.getLanguageLevel(psiElement);
    }

    private static IncorrectOperationException newException(String str, DummyHolder dummyHolder) {
        Throwable parserError;
        FileElement treeElement = dummyHolder.getTreeElement();
        if ((treeElement instanceof JavaDummyElement) && (parserError = ((JavaDummyElement) treeElement).getParserError()) != null) {
            return new IncorrectOperationException(str, parserError);
        }
        return new IncorrectOperationException(str);
    }
}
