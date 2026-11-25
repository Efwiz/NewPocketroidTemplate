package antlr;

import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class ASTFactory {
    static /* synthetic */ Class class$antlr$CommonAST;
    static /* synthetic */ Class class$antlr$Token;
    protected String theASTNodeType = null;
    protected Class theASTNodeTypeClass = null;
    protected Hashtable tokenTypeToASTClassMap = null;

    public ASTFactory() {
    }

    public ASTFactory(Hashtable hashtable) {
        setTokenTypeToASTClassMap(hashtable);
    }

    public void setTokenTypeASTNodeType(int i, String str) throws IllegalArgumentException {
        if (this.tokenTypeToASTClassMap == null) {
            this.tokenTypeToASTClassMap = new Hashtable();
        }
        if (str == null) {
            this.tokenTypeToASTClassMap.remove(new Integer(i));
            return;
        }
        try {
            this.tokenTypeToASTClassMap.put(new Integer(i), Utils.loadClass(str));
        } catch (Exception unused) {
            throw new IllegalArgumentException(new StringBuffer("Invalid class, ").append(str).toString());
        }
    }

    public Class getASTNodeType(int i) {
        Class cls;
        Hashtable hashtable = this.tokenTypeToASTClassMap;
        if (hashtable != null && (cls = (Class) hashtable.get(new Integer(i))) != null) {
            return cls;
        }
        Class cls2 = this.theASTNodeTypeClass;
        if (cls2 != null) {
            return cls2;
        }
        Class cls3 = class$antlr$CommonAST;
        if (cls3 != null) {
            return cls3;
        }
        Class clsClass$ = class$("antlr.CommonAST");
        class$antlr$CommonAST = clsClass$;
        return clsClass$;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void addASTChild(ASTPair aSTPair, AST ast) {
        if (ast != null) {
            if (aSTPair.root == null) {
                aSTPair.root = ast;
            } else if (aSTPair.child == null) {
                aSTPair.root.setFirstChild(ast);
            } else {
                aSTPair.child.setNextSibling(ast);
            }
            aSTPair.child = ast;
            aSTPair.advanceChildToEnd();
        }
    }

    public AST create() {
        return create(0);
    }

    public AST create(int i) {
        AST astCreate = create(getASTNodeType(i));
        if (astCreate != null) {
            astCreate.initialize(i, "");
        }
        return astCreate;
    }

    public AST create(int i, String str) {
        AST astCreate = create(i);
        if (astCreate != null) {
            astCreate.initialize(i, str);
        }
        return astCreate;
    }

    public AST create(int i, String str, String str2) {
        AST astCreate = create(str2);
        if (astCreate != null) {
            astCreate.initialize(i, str);
        }
        return astCreate;
    }

    public AST create(AST ast) {
        if (ast == null) {
            return null;
        }
        AST astCreate = create(ast.getType());
        if (astCreate != null) {
            astCreate.initialize(ast);
        }
        return astCreate;
    }

    public AST create(Token token) {
        AST astCreate = create(token.getType());
        if (astCreate != null) {
            astCreate.initialize(token);
        }
        return astCreate;
    }

    public AST create(Token token, String str) {
        return createUsingCtor(token, str);
    }

    public AST create(String str) {
        try {
            return create(Utils.loadClass(str));
        } catch (Exception unused) {
            throw new IllegalArgumentException(new StringBuffer("Invalid class, ").append(str).toString());
        }
    }

    protected AST createUsingCtor(Token token, String str) {
        try {
            Class clsLoadClass = Utils.loadClass(str);
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$antlr$Token;
            if (clsClass$ == null) {
                clsClass$ = class$("antlr.Token");
                class$antlr$Token = clsClass$;
            }
            clsArr[0] = clsClass$;
            try {
                return (AST) clsLoadClass.getConstructor(clsArr).newInstance(token);
            } catch (NoSuchMethodException unused) {
                AST astCreate = create(clsLoadClass);
                if (astCreate == null) {
                    return astCreate;
                }
                astCreate.initialize(token);
                return astCreate;
            }
        } catch (Exception unused2) {
            throw new IllegalArgumentException(new StringBuffer("Invalid class or can't make instance, ").append(str).toString());
        }
    }

    protected AST create(Class cls) {
        try {
            return (AST) cls.newInstance();
        } catch (Exception unused) {
            error(new StringBuffer("Can't create AST Node ").append(cls.getName()).toString());
            return null;
        }
    }

    public AST dup(AST ast) {
        if (ast == null) {
            return null;
        }
        AST astCreate = create(ast.getClass());
        astCreate.initialize(ast);
        return astCreate;
    }

    public AST dupList(AST ast) {
        AST astDupTree = dupTree(ast);
        AST nextSibling = astDupTree;
        while (ast != null) {
            ast = ast.getNextSibling();
            nextSibling.setNextSibling(dupTree(ast));
            nextSibling = nextSibling.getNextSibling();
        }
        return astDupTree;
    }

    public AST dupTree(AST ast) {
        AST astDup = dup(ast);
        if (ast != null) {
            astDup.setFirstChild(dupList(ast.getFirstChild()));
        }
        return astDup;
    }

    public AST make(AST[] astArr) {
        AST ast = null;
        if (astArr == null || astArr.length == 0) {
            return null;
        }
        AST ast2 = astArr[0];
        if (ast2 != null) {
            ast2.setFirstChild(null);
        }
        for (int i = 1; i < astArr.length; i++) {
            AST nextSibling = astArr[i];
            if (nextSibling != null) {
                if (ast2 == null) {
                    ast2 = nextSibling;
                } else if (ast == null) {
                    ast2.setFirstChild(nextSibling);
                    nextSibling = ast2.getFirstChild();
                } else {
                    ast.setNextSibling(nextSibling);
                    nextSibling = ast.getNextSibling();
                }
                while (nextSibling.getNextSibling() != null) {
                    nextSibling = nextSibling.getNextSibling();
                }
                ast = nextSibling;
            }
        }
        return ast2;
    }

    public AST make(ASTArray aSTArray) {
        return make(aSTArray.array);
    }

    public void makeASTRoot(ASTPair aSTPair, AST ast) {
        if (ast != null) {
            ast.addChild(aSTPair.root);
            aSTPair.child = aSTPair.root;
            aSTPair.advanceChildToEnd();
            aSTPair.root = ast;
        }
    }

    public void setASTNodeClass(Class cls) {
        if (cls != null) {
            this.theASTNodeTypeClass = cls;
            this.theASTNodeType = cls.getName();
        }
    }

    public void setASTNodeClass(String str) {
        this.theASTNodeType = str;
        try {
            this.theASTNodeTypeClass = Utils.loadClass(str);
        } catch (Exception unused) {
            error(new StringBuffer("Can't find/access AST Node type").append(str).toString());
        }
    }

    public void setASTNodeType(String str) {
        setASTNodeClass(str);
    }

    public Hashtable getTokenTypeToASTClassMap() {
        return this.tokenTypeToASTClassMap;
    }

    public void setTokenTypeToASTClassMap(Hashtable hashtable) {
        this.tokenTypeToASTClassMap = hashtable;
    }

    public void error(String str) {
        System.err.println(str);
    }
}
