package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.arsc.container.SpecTypePair;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import kotlin.text.Typography;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassTypeReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightPackageReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightPackageReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiElementFactoryImpl extends PsiJavaParserFacadeImpl implements Disposable, PsiElementFactory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final JavaParserUtil.ParserWrapper CATCH_SECTION = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFactoryImpl$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getStatementParser().parseCatchBlock(psiBuilder);
        }
    };
    private final ConcurrentMap<LanguageLevel, PsiClass> myArrayClasses;
    private final ConcurrentMap<GlobalSearchScope, PsiClassType> myCachedObjectType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 40:
            case 42:
            case 46:
            case 48:
            case 50:
            case 53:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 73:
            case 76:
            case 79:
            case 81:
            case 89:
            case 91:
            case 94:
            case 97:
            case 103:
            case 105:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 127:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 40:
            case 42:
            case 46:
            case 48:
            case 50:
            case 53:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 73:
            case 76:
            case 79:
            case 81:
            case 89:
            case 91:
            case 94:
            case 97:
            case 103:
            case 105:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 127:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "languageLevel";
                break;
            case 2:
            case 5:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 40:
            case 42:
            case 46:
            case 48:
            case 50:
            case 53:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 73:
            case 76:
            case 79:
            case 81:
            case 89:
            case 91:
            case 94:
            case 97:
            case 103:
            case 105:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 127:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementFactoryImpl";
                break;
            case 3:
                objArr[0] = "componentType";
                break;
            case 6:
            case 8:
                objArr[0] = "resolve";
                break;
            case 7:
            case 9:
                objArr[0] = "substitutor";
                break;
            case 10:
            case 12:
            case 14:
            case 16:
            case 22:
            case 25:
            case 28:
            case 30:
            case 33:
            case 35:
            case 38:
            case 41:
            case 72:
            case 92:
            case 95:
            case 98:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
                objArr[0] = "name";
                break;
            case 18:
                objArr[0] = "psiType";
                break;
            case 20:
            case 26:
            case 39:
            case 55:
            case 93:
            case 96:
            case 99:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 23:
                objArr[0] = "superTypes";
                break;
            case 43:
            case 45:
            case 47:
            case 66:
            case 74:
            case 83:
            case 88:
                objArr[0] = "aClass";
                break;
            case 44:
                objArr[0] = "classReference";
                break;
            case 49:
                objArr[0] = "owner";
                break;
            case 51:
                objArr[0] = "baseSubstitutor";
                break;
            case 52:
                objArr[0] = "typeParameters";
                break;
            case 54:
            case 59:
            case 85:
            case 86:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 117:
            case 119:
            case 121:
            case 123:
                objArr[0] = "text";
                break;
            case 57:
                objArr[0] = "map";
                break;
            case 60:
            case 62:
            case 67:
            case 69:
                objArr[0] = "qName";
                break;
            case 63:
            case 68:
            case 70:
                objArr[0] = "resolveScope";
                break;
            case 71:
            case 84:
                objArr[0] = "aPackage";
                break;
            case 75:
                objArr[0] = "memberName";
                break;
            case 77:
                objArr[0] = "names";
                break;
            case 78:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 80:
                objArr[0] = OverlayablePolicy.NAME_references;
                break;
            case 82:
            case 90:
                objArr[0] = "packageName";
                break;
            case 87:
                objArr[0] = "keyword";
                break;
            case 100:
                objArr[0] = "replacement";
                break;
            case 101:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 102:
                objArr[0] = "parameterName";
                break;
            case 104:
            case 106:
                objArr[0] = "annotationText";
                break;
            case 125:
                objArr[0] = "exceptionType";
                break;
            case 126:
                objArr[0] = "exceptionName";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getArrayClass";
                break;
            case 5:
                objArr[1] = "getArrayClassType";
                break;
            case 11:
                objArr[1] = "createClass";
                break;
            case 13:
                objArr[1] = "createInterface";
                break;
            case 15:
                objArr[1] = "createEnum";
                break;
            case 17:
                objArr[1] = "createAnnotationType";
                break;
            case 19:
                objArr[1] = "createTypeElement";
                break;
            case 21:
                objArr[1] = "createTypeParameterList";
                break;
            case 24:
                objArr[1] = "createTypeParameter";
                break;
            case 27:
                objArr[1] = "createField";
                break;
            case 29:
            case 31:
                objArr[1] = "createMethod";
                break;
            case 32:
            case 34:
            case 36:
                objArr[1] = "createConstructor";
                break;
            case 37:
                objArr[1] = "createClassInitializer";
                break;
            case 40:
                objArr[1] = "createParameter";
                break;
            case 42:
                objArr[1] = "createCodeBlock";
                break;
            case 46:
            case 48:
                objArr[1] = "createType";
                break;
            case 50:
            case 53:
                objArr[1] = "createRawSubstitutor";
                break;
            case 56:
                objArr[1] = "createDummyHolder";
                break;
            case 58:
                objArr[1] = "createSubstitutor";
                break;
            case 61:
            case 64:
            case 65:
                objArr[1] = "createTypeByFQClassName";
                break;
            case 73:
                objArr[1] = "createPackageStatement";
                break;
            case 76:
                objArr[1] = "createImportStaticStatement";
                break;
            case 79:
                objArr[1] = "createParameterList";
                break;
            case 81:
                objArr[1] = "createReferenceList";
                break;
            case 89:
                objArr[1] = "createImportStatement";
                break;
            case 91:
                objArr[1] = "createImportStatementOnDemand";
                break;
            case 94:
            case 97:
                objArr[1] = "createVariableDeclarationStatement";
                break;
            case 103:
                objArr[1] = "createParamTag";
                break;
            case 105:
                objArr[1] = "createAnnotationFromText";
                break;
            case 108:
                objArr[1] = "createCodeBlockFromText";
                break;
            case 110:
                objArr[1] = "createEnumConstantFromText";
                break;
            case 112:
                objArr[1] = "createExpressionFromText";
                break;
            case 114:
                objArr[1] = "createFieldFromText";
                break;
            case 116:
                objArr[1] = "createParameterFromText";
                break;
            case 118:
                objArr[1] = "createStatementFromText";
                break;
            case 120:
                objArr[1] = "createTypeFromText";
                break;
            case 122:
                objArr[1] = "createTypeParameterFromText";
                break;
            case 124:
                objArr[1] = "createMethodFromText";
                break;
            case 127:
                objArr[1] = "createCatchSection";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementFactoryImpl";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getArrayClass";
                break;
            case 2:
            case 5:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 40:
            case 42:
            case 46:
            case 48:
            case 50:
            case 53:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 73:
            case 76:
            case 79:
            case 81:
            case 89:
            case 91:
            case 94:
            case 97:
            case 103:
            case 105:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 127:
                break;
            case 3:
            case 4:
                objArr[2] = "getArrayClassType";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 43:
            case 44:
            case 45:
            case 47:
                objArr[2] = "createType";
                break;
            case 10:
                objArr[2] = "createClass";
                break;
            case 12:
                objArr[2] = "createInterface";
                break;
            case 14:
                objArr[2] = "createEnum";
                break;
            case 16:
                objArr[2] = "createAnnotationType";
                break;
            case 18:
                objArr[2] = "createTypeElement";
                break;
            case 20:
                objArr[2] = "createReferenceElementByType";
                break;
            case 22:
            case 23:
                objArr[2] = "createTypeParameter";
                break;
            case 25:
            case 26:
                objArr[2] = "createField";
                break;
            case 28:
            case 30:
                objArr[2] = "createMethod";
                break;
            case 33:
            case 35:
                objArr[2] = "createConstructor";
                break;
            case 38:
            case 39:
            case 41:
                objArr[2] = "createParameter";
                break;
            case 49:
            case 51:
            case 52:
                objArr[2] = "createRawSubstitutor";
                break;
            case 54:
            case 55:
                objArr[2] = "createDummyHolder";
                break;
            case 57:
                objArr[2] = "createSubstitutor";
                break;
            case 59:
                objArr[2] = "createPrimitiveType";
                break;
            case 60:
            case 62:
            case 63:
                objArr[2] = "createTypeByFQClassName";
                break;
            case 66:
                objArr[2] = "createClassReferenceElement";
                break;
            case 67:
            case 68:
                objArr[2] = "createReferenceElementByFQClassName";
                break;
            case 69:
            case 70:
                objArr[2] = "createFQClassNameReferenceElement";
                break;
            case 71:
            case 82:
                objArr[2] = "createPackageReferenceElement";
                break;
            case 72:
                objArr[2] = "createPackageStatement";
                break;
            case 74:
            case 75:
                objArr[2] = "createImportStaticStatement";
                break;
            case 77:
            case 78:
                objArr[2] = "createParameterList";
                break;
            case 80:
                objArr[2] = "createReferenceList";
                break;
            case 83:
            case 84:
                objArr[2] = "createReferenceExpression";
                break;
            case 85:
                objArr[2] = "createIdentifier";
                break;
            case 86:
            case 87:
                objArr[2] = "createKeyword";
                break;
            case 88:
                objArr[2] = "createImportStatement";
                break;
            case 90:
                objArr[2] = "createImportStatementOnDemand";
                break;
            case 92:
            case 93:
            case 95:
            case 96:
                objArr[2] = "createVariableDeclarationStatement";
                break;
            case 98:
            case 99:
                objArr[2] = "createResourceVariable";
                break;
            case 100:
            case 101:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 102:
                objArr[2] = "createParamTag";
                break;
            case 104:
            case 106:
                objArr[2] = "createAnnotationFromText";
                break;
            case 107:
                objArr[2] = "createCodeBlockFromText";
                break;
            case 109:
                objArr[2] = "createEnumConstantFromText";
                break;
            case 111:
                objArr[2] = "createExpressionFromText";
                break;
            case 113:
                objArr[2] = "createFieldFromText";
                break;
            case 115:
                objArr[2] = "createParameterFromText";
                break;
            case 117:
                objArr[2] = "createStatementFromText";
                break;
            case 119:
                objArr[2] = "createTypeFromText";
                break;
            case 121:
                objArr[2] = "createTypeParameterFromText";
                break;
            case 123:
                objArr[2] = "createMethodFromText";
                break;
            case 125:
            case 126:
                objArr[2] = "createCatchSection";
                break;
            case 128:
                objArr[2] = "isValidClassName";
                break;
            case 129:
                objArr[2] = "isValidMethodName";
                break;
            case 130:
                objArr[2] = "isValidParameterName";
                break;
            case 131:
                objArr[2] = "isValidFieldName";
                break;
            case 132:
                objArr[2] = "isValidLocalVariableName";
                break;
            case 133:
                objArr[2] = "isIdentifier";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 40:
            case 42:
            case 46:
            case 48:
            case 50:
            case 53:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 73:
            case 76:
            case 79:
            case 81:
            case 89:
            case 91:
            case 94:
            case 97:
            case 103:
            case 105:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 127:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiElementFactoryImpl(Project project) {
        super(project);
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myArrayClasses = new ConcurrentHashMap();
        this.myCachedObjectType = ContainerUtil.createConcurrentSoftMap();
        project.getMessageBus().connect(this).subscribe(PsiManagerImpl.ANY_PSI_CHANGE_TOPIC, new AnyPsiChangeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFactoryImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener
            public void beforePsiChanged(boolean z) {
                if (z) {
                    PsiElementFactoryImpl.this.myCachedObjectType.clear();
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiClass getArrayClass(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        PsiClass psiClassComputeIfAbsent = this.myArrayClasses.computeIfAbsent(languageLevel, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFactoryImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.createArrayClass((LanguageLevel) obj);
            }
        });
        if (psiClassComputeIfAbsent == null) {
            $$$reportNull$$$0(2);
        }
        return psiClassComputeIfAbsent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiClass createArrayClass(LanguageLevel languageLevel) {
        String str;
        if (languageLevel.isAtLeast(LanguageLevel.JDK_1_5)) {
            str = "public static class __Array__<T> {\n public final int length;\n public T[] clone() {}\n}";
        } else {
            str = "public static class __Array__{\n public final int length;\n public Object clone() {}\n}";
        }
        PsiClass psiClass = ((PsiExtensibleClass) createClassFromText(str, null)).getOwnInnerClasses().get(0);
        ensureNonWritable(psiClass);
        PsiFile containingFile = psiClass.getContainingFile();
        containingFile.clearCaches();
        PsiUtil.FILE_LANGUAGE_LEVEL_KEY.set(containingFile, languageLevel);
        return psiClass;
    }

    private static void ensureNonWritable(PsiClass psiClass) {
        try {
            psiClass.getContainingFile().getViewProvider().getVirtualFile().setWritable(false);
        } catch (IOException unused) {
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiClassType getArrayClassType(PsiType psiType, LanguageLevel languageLevel) {
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(4);
        }
        PsiClass arrayClass = getArrayClass(languageLevel);
        PsiTypeParameter[] typeParameters = arrayClass.getTypeParameters();
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        if (typeParameters.length == 1) {
            psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[0], psiType);
        }
        PsiClassType psiClassTypeCreateType = createType(arrayClass, psiSubstitutorPut);
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        return new PsiImmediateClassType(psiClass, psiSubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
        return new PsiImmediateClassType(psiClass, psiSubstitutor, languageLevel);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClass createClass(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        PsiClass psiClassCreateClassInner = createClassInner("class", str);
        if (psiClassCreateClassInner == null) {
            $$$reportNull$$$0(11);
        }
        return psiClassCreateClassInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClass createInterface(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        PsiClass psiClassCreateClassInner = createClassInner(PsiKeyword.INTERFACE, str);
        if (psiClassCreateClassInner == null) {
            $$$reportNull$$$0(13);
        }
        return psiClassCreateClassInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClass createEnum(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        PsiClass psiClassCreateClassInner = createClassInner(PsiKeyword.ENUM, str);
        if (psiClassCreateClassInner == null) {
            $$$reportNull$$$0(15);
        }
        return psiClassCreateClassInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClass createAnnotationType(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        PsiClass psiClassCreateClassInner = createClassInner("@interface", str);
        if (psiClassCreateClassInner == null) {
            $$$reportNull$$$0(17);
        }
        return psiClassCreateClassInner;
    }

    private PsiClass createClassInner(String str, String str2) {
        PsiUtil.checkIsIdentifier(this.myManager, str2);
        PsiClass[] classes = createDummyJavaFile("public " + str + " " + str2 + " { }").getClasses();
        if (classes.length != 1) {
            throw new IncorrectOperationException("Incorrect " + str + " name \"" + str2 + "\".");
        }
        return classes[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiTypeElement createTypeElement(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(18);
        }
        LightTypeElement lightTypeElement = new LightTypeElement(this.myManager, psiType);
        CodeEditUtil.setNodeGenerated(lightTypeElement.getNode(), true);
        return lightTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiJavaCodeReferenceElement createReferenceElementByType(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(20);
        }
        if (psiClassType instanceof PsiClassReferenceType) {
            return ((PsiClassReferenceType) psiClassType).getReference();
        }
        return new LightClassTypeReference(this.myManager, psiClassType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiTypeParameterList createTypeParameterList() {
        PsiTypeParameterList typeParameterList = createMethodFromText("void foo()", null).mo6093getTypeParameterList();
        if (typeParameterList == null) {
            $$$reportNull$$$0(21);
        }
        return typeParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiTypeParameter createTypeParameter(String str, PsiClassType[] psiClassTypeArr) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(23);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("public <").append(str);
        if (psiClassTypeArr.length > 1 || (psiClassTypeArr.length == 1 && !psiClassTypeArr[0].equalsToText(CommonClassNames.JAVA_LANG_OBJECT))) {
            sb.append(" extends ");
            for (PsiClassType psiClassType : psiClassTypeArr) {
                if (!psiClassType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                    sb.append(psiClassType.getCanonicalText(true)).append(Typography.amp);
                }
            }
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("> void foo(){}");
        try {
            PsiTypeParameter psiTypeParameter = createMethodFromText(sb.toString(), null).getTypeParameters()[0];
            if (psiTypeParameter == null) {
                $$$reportNull$$$0(24);
            }
            return psiTypeParameter;
        } catch (RuntimeException e) {
            throw new IncorrectOperationException("type parameter text: " + ((Object) sb), e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiField createField(String str, PsiType psiType) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (psiType == null) {
            $$$reportNull$$$0(26);
        }
        PsiUtil.checkIsIdentifier(this.myManager, str);
        if (PsiType.NULL.equals(psiType)) {
            throw new IncorrectOperationException("Cannot create field with type \"null\".");
        }
        String str2 = "class _Dummy_ { private " + GenericsUtil.getVariableTypeByExpressionType(psiType).getCanonicalText(true) + " " + str + "; }";
        PsiClass[] classes = createDummyJavaFile(str2).getClasses();
        if (classes.length < 1) {
            throw new IncorrectOperationException("Class was not created " + str2);
        }
        PsiField[] fields = classes[0].getFields();
        if (fields.length < 1) {
            throw new IncorrectOperationException("Field was not created " + str2);
        }
        PsiField psiField = (PsiField) CodeStyleManager.getInstance(this.myManager.getProject()).reformat((PsiField) JavaCodeStyleManager.getInstance(this.myManager.getProject()).shortenClassReferences(fields[0]));
        if (psiField == null) {
            $$$reportNull$$$0(27);
        }
        return psiField;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiMethod createMethod(String str, PsiType psiType) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        PsiUtil.checkIsIdentifier(this.myManager, str);
        if (PsiType.NULL.equals(psiType)) {
            throw new IncorrectOperationException("Cannot create method with type \"null\".");
        }
        String canonicalText = GenericsUtil.getVariableTypeByExpressionType(psiType).getCanonicalText(true);
        PsiClass[] classes = createDummyJavaFile("class _Dummy_ { public " + canonicalText + " " + str + "() {\n} }").getClasses();
        if (classes.length < 1) {
            throw new IncorrectOperationException("Class was not created. Method name: " + str + "; return type: " + canonicalText);
        }
        PsiMethod[] methods = classes[0].getMethods();
        if (methods.length < 1) {
            throw new IncorrectOperationException("Method was not created. Method name: " + str + "; return type: " + canonicalText);
        }
        PsiMethod psiMethod = (PsiMethod) CodeStyleManager.getInstance(this.myManager.getProject()).reformat((PsiMethod) JavaCodeStyleManager.getInstance(this.myManager.getProject()).shortenClassReferences(methods[0]));
        if (psiMethod == null) {
            $$$reportNull$$$0(29);
        }
        return psiMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiMethod createMethod(String str, PsiType psiType, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        PsiMethod psiMethodCreateMethodFromText = createMethodFromText("public " + GenericsUtil.getVariableTypeByExpressionType(psiType).getCanonicalText(true) + " " + str + "() {}", psiElement);
        if (psiMethodCreateMethodFromText == null) {
            $$$reportNull$$$0(31);
        }
        return psiMethodCreateMethodFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiMethod createConstructor() {
        PsiMethod psiMethodCreateConstructor = createConstructor("_Dummy_");
        if (psiMethodCreateConstructor == null) {
            $$$reportNull$$$0(32);
        }
        return psiMethodCreateConstructor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiMethod createConstructor(String str) {
        if (str == null) {
            $$$reportNull$$$0(33);
        }
        PsiMethod psiMethod = (PsiMethod) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(createDummyJavaFile(ExternalAnnotationProvider.CLASS_PREFIX + str + " { public " + str + "() {} }").getClasses()[0].getMethods()[0]);
        if (psiMethod == null) {
            $$$reportNull$$$0(34);
        }
        return psiMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiMethod createConstructor(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        PsiMethod psiMethodCreateMethodFromText = createMethodFromText(str + "() {}", psiElement);
        if (psiMethodCreateMethodFromText == null) {
            $$$reportNull$$$0(36);
        }
        return psiMethodCreateMethodFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassInitializer createClassInitializer() throws IncorrectOperationException {
        PsiClassInitializer psiClassInitializer = (PsiClassInitializer) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(createDummyJavaFile("class _Dummy_ { {} }").getClasses()[0].getInitializers()[0]);
        if (psiClassInitializer == null) {
            $$$reportNull$$$0(37);
        }
        return psiClassInitializer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiParameter createParameter(String str, PsiType psiType) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(38);
        }
        if (psiType == null) {
            $$$reportNull$$$0(39);
        }
        PsiUtil.checkIsIdentifier(this.myManager, str);
        if (PsiType.NULL.equals(psiType)) {
            throw new IncorrectOperationException("Cannot create parameter with type \"null\".");
        }
        PsiParameter psiParameterCreateParameterFromText = createParameterFromText(psiType.getCanonicalText(true) + " " + str, null);
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(this.myManager.getProject());
        PsiUtil.setModifierProperty(psiParameterCreateParameterFromText, "final", JavaCodeStyleSettingsFacade.getInstance(this.myManager.getProject()).isGenerateFinalParameters());
        GeneratedMarkerVisitor.markGenerated(psiParameterCreateParameterFromText);
        PsiParameter psiParameter = (PsiParameter) codeStyleManager.reformat((PsiParameter) JavaCodeStyleManager.getInstance(this.myManager.getProject()).shortenClassReferences(psiParameterCreateParameterFromText));
        if (psiParameter == null) {
            $$$reportNull$$$0(40);
        }
        return psiParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiParameter createParameter(String str, PsiType psiType, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(41);
        }
        return createMethodFromText("void f(" + psiType.getCanonicalText(true) + " " + str + ") {}", psiElement).getParameterList().getParameters()[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiCodeBlock createCodeBlock() {
        PsiCodeBlock psiCodeBlock = (PsiCodeBlock) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(createCodeBlockFromText("{}", null));
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(42);
        }
        return psiCodeBlock;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createType(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(43);
        }
        return new PsiImmediateClassType(psiClass, psiClass instanceof PsiTypeParameter ? PsiSubstitutor.EMPTY : createRawSubstitutor(psiClass));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiClassType createType(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(44);
        }
        return new PsiClassReferenceType(psiJavaCodeReferenceElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createType(PsiClass psiClass, PsiType psiType) {
        if (psiClass == null) {
            $$$reportNull$$$0(45);
        }
        PsiClassType psiClassTypeCreateType = createType(psiClass, PsiSubstitutor.EMPTY.put(psiClass.getTypeParameters()[0], psiType));
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(46);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createType(PsiClass psiClass, PsiType... psiTypeArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(47);
        }
        PsiClassType psiClassTypeCreateType = createType(psiClass, PsiSubstitutor.EMPTY.putAll(psiClass, psiTypeArr));
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(48);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiSubstitutor createRawSubstitutor(PsiTypeParameterListOwner psiTypeParameterListOwner) {
        if (psiTypeParameterListOwner == null) {
            $$$reportNull$$$0(49);
        }
        HashMap map = null;
        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiTypeParameterListOwner)) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(psiTypeParameter, null);
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = PsiSubstitutor.createSubstitutor(map);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(50);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiSubstitutor createRawSubstitutor(PsiSubstitutor psiSubstitutor, PsiTypeParameter[] psiTypeParameterArr) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(51);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(52);
        }
        HashMap map = null;
        for (PsiTypeParameter psiTypeParameter : psiTypeParameterArr) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(psiTypeParameter, null);
        }
        PsiSubstitutor psiSubstitutorPutAll = PsiSubstitutor.createSubstitutor(map).putAll(psiSubstitutor);
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(53);
        }
        return psiSubstitutorPutAll;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiElement createDummyHolder(String str, IElementType iElementType, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(54);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(55);
        }
        FileElement treeElement = DummyHolderFactory.createHolder(this.myManager, psiElement).getTreeElement();
        Language language = iElementType.getLanguage();
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        Project project = this.myManager.getProject();
        ASTNode aSTNode = parserDefinitionForLanguage.createParser(project).parse(iElementType, PsiBuilderFactory.getInstance().createBuilder(project, treeElement, parserDefinitionForLanguage.createLexer(project), language, str));
        treeElement.rawAddChildren((TreeElement) aSTNode);
        PsiElement psi = aSTNode.getPsi();
        if (psi == null) {
            $$$reportNull$$$0(56);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiSubstitutor createSubstitutor(Map<PsiTypeParameter, PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(57);
        }
        PsiSubstitutor psiSubstitutorCreateSubstitutor = PsiSubstitutor.createSubstitutor(map);
        if (psiSubstitutorCreateSubstitutor == null) {
            $$$reportNull$$$0(58);
        }
        return psiSubstitutorCreateSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiPrimitiveType createPrimitiveType(String str) {
        if (str == null) {
            $$$reportNull$$$0(59);
        }
        return PsiJavaParserFacadeImpl.getPrimitiveType(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createTypeByFQClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(60);
        }
        PsiClassType psiClassTypeCreateTypeByFQClassName = createTypeByFQClassName(str, GlobalSearchScope.allScope(this.myManager.getProject()));
        if (psiClassTypeCreateTypeByFQClassName == null) {
            $$$reportNull$$$0(61);
        }
        return psiClassTypeCreateTypeByFQClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiClassType createTypeByFQClassName(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(62);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(63);
        }
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(str)) {
            PsiClassType psiClassType = this.myCachedObjectType.get(globalSearchScope);
            if (psiClassType != null) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(64);
                }
                return psiClassType;
            }
            PsiClass psiClassFindClass = JavaPsiFacade.getInstance(this.myManager.getProject()).findClass(CommonClassNames.JAVA_LANG_OBJECT, globalSearchScope);
            if (psiClassFindClass != null) {
                PsiClassType psiClassType2 = (PsiClassType) ConcurrencyUtil.cacheOrGet(this.myCachedObjectType, globalSearchScope, new PsiImmediateClassType(psiClassFindClass, PsiSubstitutor.EMPTY));
                if (psiClassType2 == null) {
                    $$$reportNull$$$0(65);
                }
                return psiClassType2;
            }
        }
        return new PsiClassReferenceType(createReferenceElementByFQClassName(str, globalSearchScope), null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiJavaCodeReferenceElement createClassReferenceElement(PsiClass psiClass) {
        String name2;
        if (psiClass == null) {
            $$$reportNull$$$0(66);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            name2 = ((PsiAnonymousClass) psiClass).getBaseClassType().getPresentableText();
        } else {
            name2 = psiClass.get$name();
        }
        if (name2 == null) {
            throw new IncorrectOperationException("Invalid class: " + psiClass);
        }
        return new LightClassReference(this.myManager, name2, psiClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiJavaCodeReferenceElement createReferenceElementByFQClassName(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(67);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(68);
        }
        return new LightClassReference(this.myManager, PsiNameHelper.getShortClassName(str), str, globalSearchScope);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiJavaCodeReferenceElement createFQClassNameReferenceElement(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(69);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(70);
        }
        return new LightClassReference(this.myManager, str, str, globalSearchScope);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiJavaCodeReferenceElement createPackageReferenceElement(PsiPackage psiPackage) throws IncorrectOperationException {
        if (psiPackage == null) {
            $$$reportNull$$$0(71);
        }
        if (psiPackage.getQualifiedName().isEmpty()) {
            throw new IncorrectOperationException("Cannot create reference to default package.");
        }
        return new LightPackageReference(this.myManager, psiPackage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiPackageStatement createPackageStatement(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(72);
        }
        PsiPackageStatement packageStatement = createDummyJavaFile("package " + str + ";").getPackageStatement();
        if (packageStatement == null) {
            throw new IncorrectOperationException("Incorrect package name: " + str);
        }
        if (packageStatement == null) {
            $$$reportNull$$$0(73);
        }
        return packageStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiImportStaticStatement createImportStaticStatement(PsiClass psiClass, String str) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(74);
        }
        if (str == null) {
            $$$reportNull$$$0(75);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            throw new IncorrectOperationException("Cannot create import statement for anonymous class.");
        }
        if (psiClass.getLightParent() instanceof PsiDeclarationStatement) {
            throw new IncorrectOperationException("Cannot create import statement for local class.");
        }
        PsiImportStaticStatement psiImportStaticStatement = (PsiImportStaticStatement) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(extractImport(createDummyJavaFile("import static " + psiClass.getQualifiedName() + "." + str + ";"), true));
        if (psiImportStaticStatement == null) {
            $$$reportNull$$$0(76);
        }
        return psiImportStaticStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiParameterList createParameterList(String[] strArr, PsiType[] psiTypeArr) throws IncorrectOperationException {
        if (strArr == null) {
            $$$reportNull$$$0(77);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(78);
        }
        StringBuilder sb = new StringBuilder("void method(");
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(psiTypeArr[i].getCanonicalText(true)).append(StringUtil.SPACE).append(strArr[i]);
        }
        sb.append(");");
        PsiParameterList parameterList = createMethodFromText(sb.toString(), null).getParameterList();
        if (parameterList == null) {
            $$$reportNull$$$0(79);
        }
        return parameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiReferenceList createReferenceList(PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr) throws IncorrectOperationException {
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(80);
        }
        StringBuilder sb = new StringBuilder("void method()");
        if (psiJavaCodeReferenceElementArr.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < psiJavaCodeReferenceElementArr.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(psiJavaCodeReferenceElementArr[i].getCanonicalText());
            }
        }
        sb.append(';');
        PsiReferenceList throwsList = createMethodFromText(sb.toString(), null).getThrowsList();
        if (throwsList == null) {
            $$$reportNull$$$0(81);
        }
        return throwsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiJavaCodeReferenceElement createPackageReferenceElement(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(82);
        }
        if (str.isEmpty()) {
            throw new IncorrectOperationException("Cannot create reference to default package.");
        }
        return new LightPackageReference(this.myManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiReferenceExpression createReferenceExpression(PsiClass psiClass) throws IncorrectOperationException {
        String name2;
        if (psiClass == null) {
            $$$reportNull$$$0(83);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            name2 = ((PsiAnonymousClass) psiClass).getBaseClassType().getPresentableText();
        } else {
            name2 = psiClass.get$name();
        }
        return new LightClassReferenceExpression(this.myManager, name2, psiClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiReferenceExpression createReferenceExpression(PsiPackage psiPackage) throws IncorrectOperationException {
        if (psiPackage == null) {
            $$$reportNull$$$0(84);
        }
        if (psiPackage.getQualifiedName().isEmpty()) {
            throw new IncorrectOperationException("Cannot create reference to default package.");
        }
        return new LightPackageReferenceExpression(this.myManager, psiPackage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiIdentifier createIdentifier(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(85);
        }
        PsiUtil.checkIsIdentifier(this.myManager, str);
        return new LightIdentifier(this.myManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiKeyword createKeyword(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(86);
        }
        if (!PsiNameHelper.getInstance(this.myManager.getProject()).isKeyword(str)) {
            throw new IncorrectOperationException("\"" + str + "\" is not a keyword.");
        }
        return new LightKeyword(this.myManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiKeyword createKeyword(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(87);
        }
        LanguageLevel languageLevel = PsiUtil.getLanguageLevel(psiElement);
        if (!JavaLexer.isKeyword(str, languageLevel) && !JavaLexer.isSoftKeyword(str, languageLevel)) {
            throw new IncorrectOperationException("\"" + str + "\" is not a keyword.");
        }
        return new LightKeyword(this.myManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiImportStatement createImportStatement(PsiClass psiClass) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(88);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            throw new IncorrectOperationException("Cannot create import statement for anonymous class.");
        }
        if (psiClass.getLightParent() instanceof PsiDeclarationStatement) {
            throw new IncorrectOperationException("Cannot create import statement for local class.");
        }
        PsiImportStatement psiImportStatement = (PsiImportStatement) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(extractImport(createDummyJavaFile("import " + psiClass.getQualifiedName() + ";"), false));
        if (psiImportStatement == null) {
            $$$reportNull$$$0(89);
        }
        return psiImportStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiImportStatement createImportStatementOnDemand(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(90);
        }
        if (str.isEmpty()) {
            throw new IncorrectOperationException("Cannot create import statement for default package.");
        }
        if (!PsiNameHelper.getInstance(this.myManager.getProject()).isQualifiedName(str)) {
            throw new IncorrectOperationException("Incorrect package name: \"" + str + "\".");
        }
        PsiImportStatement psiImportStatement = (PsiImportStatement) CodeStyleManager.getInstance(this.myManager.getProject()).reformat(extractImport(createDummyJavaFile("import " + str + ".*;"), false));
        if (psiImportStatement == null) {
            $$$reportNull$$$0(91);
        }
        return psiImportStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiDeclarationStatement createVariableDeclarationStatement(String str, PsiType psiType, PsiExpression psiExpression) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(92);
        }
        if (psiType == null) {
            $$$reportNull$$$0(93);
        }
        PsiDeclarationStatement psiDeclarationStatementCreateVariableDeclarationStatement = createVariableDeclarationStatement(str, psiType, psiExpression, null);
        if (psiDeclarationStatementCreateVariableDeclarationStatement == null) {
            $$$reportNull$$$0(94);
        }
        return psiDeclarationStatementCreateVariableDeclarationStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiDeclarationStatement createVariableDeclarationStatement(String str, PsiType psiType, PsiExpression psiExpression, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(95);
        }
        if (psiType == null) {
            $$$reportNull$$$0(96);
        }
        if (!isIdentifier(str)) {
            throw new IncorrectOperationException("\"" + str + "\" is not an identifier.");
        }
        if (PsiType.NULL.equals(psiType)) {
            throw new IncorrectOperationException("Cannot create variable with type \"null\".");
        }
        String str2 = "X " + str + (psiExpression != null ? " = x" : "") + ";";
        PsiDeclarationStatement psiDeclarationStatement = (PsiDeclarationStatement) createStatementFromText(str2, psiElement);
        boolean z = false;
        PsiVariable psiVariable = (PsiVariable) psiDeclarationStatement.getDeclaredElements()[0];
        replace(psiVariable.getTypeElement(), createTypeElement(GenericsUtil.getVariableTypeByExpressionType(psiType)), str2);
        if (psiElement != null && JavaFileCodeStyleFacade.forContext(psiElement.getContainingFile()).isGenerateFinalLocals()) {
            z = true;
        }
        PsiUtil.setModifierProperty(psiVariable, "final", z);
        if (psiExpression != null) {
            replace(psiVariable.getInitializer(), psiExpression, str2);
        }
        GeneratedMarkerVisitor.markGenerated(psiDeclarationStatement);
        if (psiDeclarationStatement == null) {
            $$$reportNull$$$0(97);
        }
        return psiDeclarationStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiResourceVariable createResourceVariable(String str, PsiType psiType, PsiExpression psiExpression, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(98);
        }
        if (psiType == null) {
            $$$reportNull$$$0(99);
        }
        PsiResourceVariable psiResourceVariable = (PsiResourceVariable) ((PsiTryStatement) createStatementFromText("try (X x = null){}", psiElement)).getResourceList().iterator().next();
        psiResourceVariable.getTypeElement().replace(createTypeElement(psiType));
        psiResourceVariable.mo7392getNameIdentifier().replace(createIdentifier(str));
        if (psiExpression != null) {
            psiResourceVariable.mo6096setInitializer(psiExpression);
        }
        return psiResourceVariable;
    }

    private static void replace(PsiElement psiElement, PsiElement psiElement2, String str) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(100);
        }
        if (str == null) {
            $$$reportNull$$$0(101);
        }
        psiElement.replace(psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiDocTag createParamTag(String str, String str2) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(102);
        }
        StringBuilder sb = new StringBuilder(" * @param ");
        sb.append(str);
        sb.append(" ");
        String[] strArrSplit = str2.split("\\n");
        for (int i = 0; i < strArrSplit.length; i++) {
            if (i > 0) {
                sb.append("\n * ");
            }
            sb.append(strArrSplit[i]);
        }
        PsiDocTag psiDocTagCreateDocTagFromText = createDocTagFromText(sb.toString());
        if (psiDocTagCreateDocTagFromText == null) {
            $$$reportNull$$$0(103);
        }
        return psiDocTagCreateDocTagFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiAnnotation createAnnotationFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(104);
        }
        PsiAnnotation psiAnnotationCreateAnnotationFromText = super.createAnnotationFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiAnnotationCreateAnnotationFromText);
        if (psiAnnotationCreateAnnotationFromText == null) {
            $$$reportNull$$$0(105);
        }
        return psiAnnotationCreateAnnotationFromText;
    }

    public PsiAnnotation createAnnotationFromText(String str, PsiElement psiElement, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(106);
        }
        PsiAnnotation psiAnnotationCreateAnnotationFromText = super.createAnnotationFromText(str, psiElement);
        if (z) {
            GeneratedMarkerVisitor.markGenerated(psiAnnotationCreateAnnotationFromText);
        }
        return psiAnnotationCreateAnnotationFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiCodeBlock createCodeBlockFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(107);
        }
        PsiCodeBlock psiCodeBlockCreateCodeBlockFromText = super.createCodeBlockFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiCodeBlockCreateCodeBlockFromText);
        if (psiCodeBlockCreateCodeBlockFromText == null) {
            $$$reportNull$$$0(108);
        }
        return psiCodeBlockCreateCodeBlockFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiEnumConstant createEnumConstantFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(109);
        }
        PsiEnumConstant psiEnumConstantCreateEnumConstantFromText = super.createEnumConstantFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiEnumConstantCreateEnumConstantFromText);
        if (psiEnumConstantCreateEnumConstantFromText == null) {
            $$$reportNull$$$0(110);
        }
        return psiEnumConstantCreateEnumConstantFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory, org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public PsiExpression createExpressionFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(111);
        }
        PsiExpression psiExpressionCreateExpressionFromText = super.createExpressionFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiExpressionCreateExpressionFromText);
        if (psiExpressionCreateExpressionFromText == null) {
            $$$reportNull$$$0(112);
        }
        return psiExpressionCreateExpressionFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiField createFieldFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(113);
        }
        PsiField psiFieldCreateFieldFromText = super.createFieldFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiFieldCreateFieldFromText);
        if (psiFieldCreateFieldFromText == null) {
            $$$reportNull$$$0(114);
        }
        return psiFieldCreateFieldFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiParameter createParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(115);
        }
        PsiParameter psiParameterCreateParameterFromText = super.createParameterFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiParameterCreateParameterFromText);
        if (psiParameterCreateParameterFromText == null) {
            $$$reportNull$$$0(116);
        }
        return psiParameterCreateParameterFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiStatement createStatementFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(117);
        }
        PsiStatement psiStatementCreateStatementFromText = super.createStatementFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiStatementCreateStatementFromText);
        if (psiStatementCreateStatementFromText == null) {
            $$$reportNull$$$0(118);
        }
        return psiStatementCreateStatementFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiType createTypeFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(119);
        }
        PsiType psiTypeCreateTypeInner = createTypeInner(str, psiElement, true);
        if (psiTypeCreateTypeInner == null) {
            $$$reportNull$$$0(120);
        }
        return psiTypeCreateTypeInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiTypeParameter createTypeParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(121);
        }
        PsiTypeParameter psiTypeParameterCreateTypeParameterFromText = super.createTypeParameterFromText(str, psiElement);
        GeneratedMarkerVisitor.markGenerated(psiTypeParameterCreateTypeParameterFromText);
        if (psiTypeParameterCreateTypeParameterFromText == null) {
            $$$reportNull$$$0(122);
        }
        return psiTypeParameterCreateTypeParameterFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade
    public PsiMethod createMethodFromText(String str, PsiElement psiElement, LanguageLevel languageLevel) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(123);
        }
        PsiMethod psiMethodCreateMethodFromText = super.createMethodFromText(str, psiElement, languageLevel);
        GeneratedMarkerVisitor.markGenerated(psiMethodCreateMethodFromText);
        if (psiMethodCreateMethodFromText == null) {
            $$$reportNull$$$0(124);
        }
        return psiMethodCreateMethodFromText;
    }

    private static PsiImportStatementBase extractImport(PsiJavaFile psiJavaFile, boolean z) {
        PsiImportList importList = psiJavaFile.getImportList();
        return (z ? importList.getImportStaticStatements() : importList.getImportStatements())[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory
    public PsiCatchSection createCatchSection(PsiType psiType, String str, PsiElement psiElement) throws IncorrectOperationException {
        if (psiType == null) {
            $$$reportNull$$$0(125);
        }
        if (str == null) {
            $$$reportNull$$$0(126);
        }
        if (!(psiType instanceof PsiClassType) && !(psiType instanceof PsiDisjunctionType)) {
            throw new IncorrectOperationException("Unexpected type:" + psiType);
        }
        String str2 = "catch (" + psiType.getCanonicalText(true) + " " + str + ") {}";
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(DummyHolderFactory.createHolder(this.myManager, new JavaDummyElement(str2, CATCH_SECTION, level(psiElement)), psiElement).getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiCatchSection)) {
            throw new IncorrectOperationException("Incorrect catch section '" + str2 + "'. Parsed element: " + psiElementTreeElementToPsi);
        }
        Project project = this.myManager.getProject();
        JavaPsiImplementationHelper.getInstance(project).setupCatchBlock(str, psiType, psiElement, (PsiCatchSection) psiElementTreeElementToPsi);
        PsiCatchSection psiCatchSection = (PsiCatchSection) CodeStyleManager.getInstance(project).reformat(psiElementTreeElementToPsi);
        GeneratedMarkerVisitor.markGenerated(psiCatchSection);
        if (psiCatchSection == null) {
            $$$reportNull$$$0(127);
        }
        return psiCatchSection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public boolean isValidClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(128);
        }
        return isIdentifier(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public boolean isValidMethodName(String str) {
        if (str == null) {
            $$$reportNull$$$0(129);
        }
        return isIdentifier(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public boolean isValidParameterName(String str) {
        if (str == null) {
            $$$reportNull$$$0(130);
        }
        return isIdentifier(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public boolean isValidFieldName(String str) {
        if (str == null) {
            $$$reportNull$$$0(131);
        }
        return isIdentifier(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    public boolean isValidLocalVariableName(String str) {
        if (str == null) {
            $$$reportNull$$$0(132);
        }
        return isIdentifier(str);
    }

    private boolean isIdentifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(133);
        }
        return PsiNameHelper.getInstance(this.myManager.getProject()).isIdentifier(str);
    }
}
