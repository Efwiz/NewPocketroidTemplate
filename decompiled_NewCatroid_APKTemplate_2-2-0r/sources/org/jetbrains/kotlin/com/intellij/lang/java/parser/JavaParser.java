package org.jetbrains.kotlin.com.intellij.lang.java.parser;

/* JADX INFO: loaded from: classes3.dex */
public class JavaParser {
    public static final JavaParser INSTANCE = new JavaParser();
    private final FileParser myFileParser = new FileParser(this);
    private final ModuleParser myModuleParser = new ModuleParser(this);
    private final DeclarationParser myDeclarationParser = new DeclarationParser(this);
    private final StatementParser myStatementParser = new StatementParser(this);
    private final ExpressionParser myExpressionParser = new ExpressionParser(this);
    private final ReferenceParser myReferenceParser = new ReferenceParser(this);
    private final PatternParser myPatternParser = new PatternParser(this);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/JavaParser";
        switch (i) {
            case 1:
                objArr[1] = "getModuleParser";
                break;
            case 2:
                objArr[1] = "getDeclarationParser";
                break;
            case 3:
                objArr[1] = "getStatementParser";
                break;
            case 4:
                objArr[1] = "getExpressionParser";
                break;
            case 5:
                objArr[1] = "getReferenceParser";
                break;
            case 6:
                objArr[1] = "getPatternParser";
                break;
            default:
                objArr[1] = "getFileParser";
                break;
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public FileParser getFileParser() {
        FileParser fileParser = this.myFileParser;
        if (fileParser == null) {
            $$$reportNull$$$0(0);
        }
        return fileParser;
    }

    public ModuleParser getModuleParser() {
        ModuleParser moduleParser = this.myModuleParser;
        if (moduleParser == null) {
            $$$reportNull$$$0(1);
        }
        return moduleParser;
    }

    public DeclarationParser getDeclarationParser() {
        DeclarationParser declarationParser = this.myDeclarationParser;
        if (declarationParser == null) {
            $$$reportNull$$$0(2);
        }
        return declarationParser;
    }

    public StatementParser getStatementParser() {
        StatementParser statementParser = this.myStatementParser;
        if (statementParser == null) {
            $$$reportNull$$$0(3);
        }
        return statementParser;
    }

    public ExpressionParser getExpressionParser() {
        ExpressionParser expressionParser = this.myExpressionParser;
        if (expressionParser == null) {
            $$$reportNull$$$0(4);
        }
        return expressionParser;
    }

    public ReferenceParser getReferenceParser() {
        ReferenceParser referenceParser = this.myReferenceParser;
        if (referenceParser == null) {
            $$$reportNull$$$0(5);
        }
        return referenceParser;
    }

    public PatternParser getPatternParser() {
        PatternParser patternParser = this.myPatternParser;
        if (patternParser == null) {
            $$$reportNull$$$0(6);
        }
        return patternParser;
    }
}
