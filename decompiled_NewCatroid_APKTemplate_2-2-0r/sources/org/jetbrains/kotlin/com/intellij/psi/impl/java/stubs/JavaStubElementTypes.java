package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnnotationMethodElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnonymousClassElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ClassElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.EnumConstantElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.EnumConstantInitializerElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.FieldElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStatementElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStaticStatementElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ReferenceListElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.TypeParameterExtendsBoundsListElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaStubElementTypes {
    public static final JavaModifierListElementType MODIFIER_LIST = new JavaModifierListElementType();
    public static final JavaAnnotationElementType ANNOTATION = new JavaAnnotationElementType();
    public static final JavaAnnotationParameterListType ANNOTATION_PARAMETER_LIST = new JavaAnnotationParameterListType();
    public static final JavaNameValuePairType NAME_VALUE_PAIR = new JavaNameValuePairType();
    public static final JavaLiteralExpressionElementType LITERAL_EXPRESSION = new JavaLiteralExpressionElementType();
    public static final LambdaExpressionElementType LAMBDA_EXPRESSION = new LambdaExpressionElementType();
    public static final MethodReferenceElementType METHOD_REF_EXPRESSION = new MethodReferenceElementType();
    public static final JavaParameterListElementType PARAMETER_LIST = new JavaParameterListElementType();
    public static final JavaParameterElementType PARAMETER = new JavaParameterElementType();
    public static final JavaTypeParameterElementType TYPE_PARAMETER = new JavaTypeParameterElementType();
    public static final JavaTypeParameterListElementType TYPE_PARAMETER_LIST = new JavaTypeParameterListElementType();
    public static final JavaClassInitializerElementType CLASS_INITIALIZER = new JavaClassInitializerElementType();
    public static final JavaImportListElementType IMPORT_LIST = new JavaImportListElementType();
    public static final JavaModuleElementType MODULE = new JavaModuleElementType();
    public static final JavaRequiresStatementElementType REQUIRES_STATEMENT = new JavaRequiresStatementElementType();
    public static final JavaUsesStatementElementType USES_STATEMENT = new JavaUsesStatementElementType();
    public static final JavaProvidesStatementElementType PROVIDES_STATEMENT = new JavaProvidesStatementElementType();
    public static final JavaRecordComponentElementType RECORD_COMPONENT = new JavaRecordComponentElementType();
    public static final JavaRecordHeaderElementType RECORD_HEADER = new JavaRecordHeaderElementType();
    public static final JavaPackageAccessibilityStatementElementType EXPORTS_STATEMENT = new JavaPackageAccessibilityStatementElementType("EXPORTS_STATEMENT");
    public static final JavaPackageAccessibilityStatementElementType OPENS_STATEMENT = new JavaPackageAccessibilityStatementElementType("OPENS_STATEMENT");
    public static final JavaClassElementType CLASS = new JavaClassElementType("CLASS") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.1
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ClassElement(this);
        }
    };
    public static final JavaClassElementType ANONYMOUS_CLASS = new JavaClassElementType("ANONYMOUS_CLASS") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.2
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new AnonymousClassElement();
        }
    };
    public static final JavaClassElementType ENUM_CONSTANT_INITIALIZER = new JavaClassElementType("ENUM_CONSTANT_INITIALIZER") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.3
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new EnumConstantInitializerElement();
        }
    };
    public static final JavaMethodElementType METHOD = new JavaMethodElementType("METHOD") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.4
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new MethodElement();
        }
    };
    public static final JavaMethodElementType ANNOTATION_METHOD = new JavaMethodElementType("ANNOTATION_METHOD") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.5
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new AnnotationMethodElement();
        }
    };
    public static final JavaFieldStubElementType FIELD = new JavaFieldStubElementType("FIELD") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.6
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new FieldElement();
        }
    };
    public static final JavaFieldStubElementType ENUM_CONSTANT = new JavaFieldStubElementType("ENUM_CONSTANT") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.7
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new EnumConstantElement();
        }
    };
    public static final JavaClassReferenceListElementType EXTENDS_LIST = new JavaClassReferenceListElementType("EXTENDS_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.8
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ReferenceListElement(this, JavaTokenType.EXTENDS_KEYWORD, PsiKeyword.EXTENDS);
        }
    };
    public static final JavaClassReferenceListElementType PERMITS_LIST = new JavaClassReferenceListElementType("PERMITS_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.9
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ReferenceListElement(this, JavaTokenType.PERMITS_KEYWORD, PsiKeyword.PERMITS);
        }
    };
    public static final JavaClassReferenceListElementType IMPLEMENTS_LIST = new JavaClassReferenceListElementType("IMPLEMENTS_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.10
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ReferenceListElement(this, JavaTokenType.IMPLEMENTS_KEYWORD, PsiKeyword.IMPLEMENTS);
        }
    };
    public static final JavaClassReferenceListElementType THROWS_LIST = new JavaClassReferenceListElementType("THROWS_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.11
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ReferenceListElement(this, JavaTokenType.THROWS_KEYWORD, PsiKeyword.THROWS);
        }
    };
    public static final JavaClassReferenceListElementType EXTENDS_BOUND_LIST = new JavaClassReferenceListElementType("EXTENDS_BOUND_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.12
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new TypeParameterExtendsBoundsListElement();
        }
    };
    public static final JavaClassReferenceListElementType PROVIDES_WITH_LIST = new JavaClassReferenceListElementType("PROVIDES_WITH_LIST") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.13
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ReferenceListElement(this, JavaTokenType.WITH_KEYWORD, "with");
        }
    };
    public static final JavaImportStatementElementType IMPORT_STATEMENT = new JavaImportStatementElementType("IMPORT_STATEMENT") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.14
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ImportStatementElement();
        }
    };
    public static final JavaImportStatementElementType IMPORT_STATIC_STATEMENT = new JavaImportStatementElementType("IMPORT_STATIC_STATEMENT") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes.15
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new ImportStaticStatementElement();
        }
    };

    @Deprecated
    public static final IStubFileElementType<?> JAVA_FILE = JavaParserDefinition.JAVA_FILE;
}
