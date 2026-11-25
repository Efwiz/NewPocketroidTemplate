package org.eclipse.jdt.internal.compiler.parser;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredElement {
    public int bracketBalance;
    public boolean foundOpeningBrace;
    public int lambdaNestLevel;
    public RecoveredElement parent;
    protected Parser recoveringParser;

    public void addModifier(int i, int i2) {
    }

    public ASTNode parseTree() {
        return null;
    }

    public void resetPendingModifiers() {
    }

    public int sourceEnd() {
        return 0;
    }

    public void updateFromParserState() {
    }

    public void updateParseTree() {
    }

    public void updateSourceEndIfNecessary(int i, int i2) {
    }

    public RecoveredElement(RecoveredElement recoveredElement, int i) {
        this(recoveredElement, i, null);
    }

    public RecoveredElement(RecoveredElement recoveredElement, int i, Parser parser) {
        this.parent = recoveredElement;
        this.bracketBalance = i;
        this.recoveringParser = parser;
    }

    public RecoveredElement addAnnotationName(int i, int i2, int i3, int i4) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(i3 - 1));
        return this.parent.addAnnotationName(i, i2, i3, i4);
    }

    public RecoveredElement add(AbstractMethodDeclaration abstractMethodDeclaration, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(abstractMethodDeclaration.declarationSourceStart - 1));
        return this.parent.add(abstractMethodDeclaration, i);
    }

    public RecoveredElement add(Block block, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(block.sourceStart - 1));
        return this.parent.add(block, i);
    }

    public RecoveredElement add(FieldDeclaration fieldDeclaration, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(fieldDeclaration.declarationSourceStart - 1));
        return this.parent.add(fieldDeclaration, i);
    }

    public RecoveredElement add(ImportReference importReference, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(importReference.declarationSourceStart - 1));
        return this.parent.add(importReference, i);
    }

    public RecoveredElement add(LocalDeclaration localDeclaration, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(localDeclaration.declarationSourceStart - 1));
        return this.parent.add(localDeclaration, i);
    }

    public RecoveredElement add(Statement statement, int i) {
        TypeDeclaration typeDeclaration;
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        if ((this instanceof RecoveredType) && (typeDeclaration = ((RecoveredType) this).typeDeclaration) != null && (typeDeclaration.bits & 512) != 0 && statement.sourceStart > typeDeclaration.sourceStart && statement.sourceEnd < typeDeclaration.sourceEnd) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(statement.sourceStart - 1));
        return this.parent.add(statement, i);
    }

    public RecoveredElement add(TypeDeclaration typeDeclaration, int i) {
        resetPendingModifiers();
        if (this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(previousAvailableLineEnd(typeDeclaration.declarationSourceStart - 1));
        return this.parent.add(typeDeclaration, i);
    }

    protected void addBlockStatement(RecoveredBlock recoveredBlock) {
        Block block = recoveredBlock.blockDeclaration;
        if (block.statements != null) {
            for (Statement statement : block.statements) {
                recoveredBlock.add(statement, 0);
            }
        }
    }

    public int depth() {
        int i = 0;
        RecoveredElement recoveredElement = this;
        while (true) {
            recoveredElement = recoveredElement.parent;
            if (recoveredElement == null) {
                return i;
            }
            i++;
        }
    }

    public RecoveredInitializer enclosingInitializer() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredInitializer) {
                return (RecoveredInitializer) recoveredElement;
            }
        }
        return null;
    }

    public RecoveredMethod enclosingMethod() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredMethod) {
                return (RecoveredMethod) recoveredElement;
            }
        }
        return null;
    }

    public RecoveredType enclosingType() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredType) {
                return (RecoveredType) recoveredElement;
            }
        }
        return null;
    }

    public Parser parser() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            Parser parser = recoveredElement.recoveringParser;
            if (parser != null) {
                return parser;
            }
        }
        return null;
    }

    public void preserveEnclosingBlocks() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredBlock) {
                ((RecoveredBlock) recoveredElement).preserveContent = true;
            }
            if (recoveredElement instanceof RecoveredType) {
                ((RecoveredType) recoveredElement).preserveContent = true;
            }
        }
    }

    public int previousAvailableLineEnd(int i) {
        int lineNumber;
        Parser rVar = parser();
        if (rVar == null) {
            return i;
        }
        Scanner scanner = rVar.scanner;
        if (scanner.lineEnds == null || (lineNumber = Util.getLineNumber(i, scanner.lineEnds, 0, scanner.linePtr)) < 2) {
            return i;
        }
        int i2 = scanner.lineEnds[lineNumber - 2];
        char[] cArr = scanner.source;
        for (int i3 = i2 + 1; i3 < i; i3++) {
            char c = cArr[i3];
            if (c != ' ' && c != '\t') {
                return i;
            }
        }
        return i2;
    }

    protected String tabString(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        while (i > 0) {
            stringBuffer.append("  ");
            i--;
        }
        return stringBuffer.toString();
    }

    public RecoveredElement topElement() {
        RecoveredElement recoveredElement = this;
        while (true) {
            RecoveredElement recoveredElement2 = recoveredElement.parent;
            if (recoveredElement2 == null) {
                return recoveredElement;
            }
            recoveredElement = recoveredElement2;
        }
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int i) {
        return super.toString();
    }

    public RecoveredType type() {
        for (RecoveredElement recoveredElement = this; recoveredElement != null; recoveredElement = recoveredElement.parent) {
            if (recoveredElement instanceof RecoveredType) {
                return (RecoveredType) recoveredElement;
            }
        }
        return null;
    }

    public void updateBodyStart(int i) {
        this.foundOpeningBrace = true;
    }

    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        int i3 = this.bracketBalance - 1;
        this.bracketBalance = i3;
        if (i3 > 0 || this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(i, i2);
        return this.parent;
    }

    public RecoveredElement updateOnOpeningBrace(int i, int i2) {
        int i3 = this.bracketBalance;
        this.bracketBalance = i3 + 1;
        if (i3 != 0) {
            return null;
        }
        updateBodyStart(i2 + 1);
        return this;
    }

    public void updateSourceEndIfNecessary(int i) {
        updateSourceEndIfNecessary(i + 1, i);
    }
}
