package org.eclipse.jdt.internal.compiler.parser;

import java.util.List;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.Javadoc;
import org.eclipse.jdt.internal.compiler.ast.JavadocAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.JavadocArgumentExpression;
import org.eclipse.jdt.internal.compiler.ast.JavadocArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocArraySingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocFieldReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocImplicitTypeReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocMessageSend;
import org.eclipse.jdt.internal.compiler.ast.JavadocQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocReturnStatement;
import org.eclipse.jdt.internal.compiler.ast.JavadocSingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.JavadocSingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocParser extends AbstractCommentParser {
    public Javadoc docComment;
    private int invalidParamReferencesPtr;
    private ASTNode[] invalidParamReferencesStack;
    private long invalidValuePositions;
    public boolean shouldReportProblems;
    private int tagWaitingForDescription;
    private long validValuePositions;

    protected void parseInheritDocTag() {
    }

    public JavadocParser(Parser parser) {
        super(parser);
        this.invalidParamReferencesPtr = -1;
        this.shouldReportProblems = true;
        this.kind = 513;
        if (parser == null || parser.options == null) {
            return;
        }
        this.setJavadocPositions = parser.options.processAnnotations;
    }

    public boolean checkDeprecation(int i) {
        int lineStart;
        int lineEnd;
        int i2;
        this.javadocStart = this.sourceParser.scanner.commentStarts[i];
        this.javadocEnd = this.sourceParser.scanner.commentStops[i] - 1;
        this.firstTagPosition = this.sourceParser.scanner.commentTagStarts[i];
        this.validValuePositions = -1L;
        this.invalidValuePositions = -1L;
        this.tagWaitingForDescription = 0;
        if (this.checkDocComment) {
            this.docComment = new Javadoc(this.javadocStart, this.javadocEnd);
        } else if (this.setJavadocPositions) {
            Javadoc javadoc = new Javadoc(this.javadocStart, this.javadocEnd);
            this.docComment = javadoc;
            javadoc.bits &= -65537;
        } else {
            this.docComment = null;
        }
        if (this.firstTagPosition == 0 && ((i2 = this.kind & 255) == 1 || i2 == 16)) {
            return false;
        }
        try {
            this.source = this.sourceParser.scanner.source;
            this.scanner.setSource(this.source);
            if (this.checkDocComment) {
                this.scanner.lineEnds = this.sourceParser.scanner.lineEnds;
                this.scanner.linePtr = this.sourceParser.scanner.linePtr;
                this.lineEnds = this.scanner.lineEnds;
                commentParse();
                this.source = null;
                this.scanner.setSource((char[]) null);
                return this.deprecated;
            }
            Scanner scanner = this.sourceParser.scanner;
            int lineNumber = Util.getLineNumber(this.javadocStart, scanner.lineEnds, 0, scanner.linePtr);
            int lineNumber2 = Util.getLineNumber(this.javadocEnd, scanner.lineEnds, 0, scanner.linePtr);
            this.index = this.javadocStart + 3;
            this.deprecated = false;
            for (int i3 = lineNumber; i3 <= lineNumber2; i3++) {
                if (i3 == lineNumber) {
                    lineStart = this.javadocStart + 3;
                } else {
                    lineStart = this.sourceParser.scanner.getLineStart(i3);
                }
                this.index = lineStart;
                if (i3 == lineNumber2) {
                    lineEnd = this.javadocEnd - 2;
                } else {
                    lineEnd = this.sourceParser.scanner.getLineEnd(i3);
                }
                this.lineEnd = lineEnd;
                while (this.index < this.lineEnd) {
                    char c = readChar();
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ' && c != '*') {
                        if (c == '@') {
                            parseSimpleTag();
                            if (this.tagValue != 1) {
                                break;
                            }
                            boolean z = this.abort;
                            break;
                        }
                        break;
                    }
                }
            }
            boolean z2 = this.deprecated;
            this.source = null;
            this.scanner.setSource((char[]) null);
            return z2;
        } catch (Throwable th) {
            this.source = null;
            this.scanner.setSource((char[]) null);
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected Object createArgumentReference(char[] cArr, int i, boolean z, Object obj, long[] jArr, long j) throws InvalidInputException {
        try {
            TypeReference javadocArrayQualifiedTypeReference = (TypeReference) obj;
            if (i > 0) {
                long j2 = (((long) javadocArrayQualifiedTypeReference.sourceStart) << 32) + ((long) javadocArrayQualifiedTypeReference.sourceEnd);
                if (obj instanceof JavadocSingleTypeReference) {
                    javadocArrayQualifiedTypeReference = new JavadocArraySingleTypeReference(((JavadocSingleTypeReference) obj).token, i, j2);
                } else {
                    javadocArrayQualifiedTypeReference = new JavadocArrayQualifiedTypeReference((JavadocQualifiedTypeReference) obj, i);
                }
            }
            int i2 = javadocArrayQualifiedTypeReference.sourceEnd;
            if (i > 0) {
                i2 = (int) jArr[i - 1];
                if (z) {
                    javadocArrayQualifiedTypeReference.bits |= 16384;
                }
            }
            if (j >= 0) {
                i2 = (int) j;
            }
            return new JavadocArgumentExpression(cArr, javadocArrayQualifiedTypeReference.sourceStart, i2, javadocArrayQualifiedTypeReference);
        } catch (ClassCastException unused) {
            throw new InvalidInputException();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected Object createFieldReference(Object obj) throws InvalidInputException {
        try {
            TypeReference javadocImplicitTypeReference = (TypeReference) obj;
            if (javadocImplicitTypeReference == null) {
                javadocImplicitTypeReference = new JavadocImplicitTypeReference(this.sourceParser.compilationUnit.getMainTypeName(), this.memberStart);
            }
            JavadocFieldReference javadocFieldReference = new JavadocFieldReference(this.identifierStack[0], this.identifierPositionStack[0]);
            javadocFieldReference.receiver = javadocImplicitTypeReference;
            javadocFieldReference.tagSourceStart = this.tagSourceStart;
            javadocFieldReference.tagSourceEnd = this.tagSourceEnd;
            javadocFieldReference.tagValue = this.tagValue;
            return javadocFieldReference;
        } catch (ClassCastException unused) {
            throw new InvalidInputException();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected Object createMethodReference(Object obj, List list) throws InvalidInputException {
        boolean zEquals;
        try {
            TypeReference javadocImplicitTypeReference = (TypeReference) obj;
            int i = this.identifierLengthStack[0];
            if (javadocImplicitTypeReference == null) {
                char[] mainTypeName = this.sourceParser.compilationUnit.getMainTypeName();
                TypeDeclaration parsedTypeDeclaration = getParsedTypeDeclaration();
                if (parsedTypeDeclaration != null) {
                    mainTypeName = parsedTypeDeclaration.f619name;
                }
                zEquals = CharOperation.equals(this.identifierStack[i - 1], mainTypeName);
                javadocImplicitTypeReference = new JavadocImplicitTypeReference(mainTypeName, this.memberStart);
            } else if (javadocImplicitTypeReference instanceof JavadocSingleTypeReference) {
                zEquals = CharOperation.equals(this.identifierStack[i - 1], ((JavadocSingleTypeReference) javadocImplicitTypeReference).token);
            } else if (javadocImplicitTypeReference instanceof JavadocQualifiedTypeReference) {
                char[][] cArr = ((JavadocQualifiedTypeReference) javadocImplicitTypeReference).tokens;
                int i2 = i - 1;
                boolean zEquals2 = CharOperation.equals(this.identifierStack[i2], cArr[cArr.length - 1]);
                if (zEquals2) {
                    boolean zEquals3 = true;
                    for (int i3 = 0; i3 < i2 && zEquals3; i3++) {
                        zEquals3 = CharOperation.equals(this.identifierStack[i3], cArr[i3]);
                    }
                    if (!zEquals3) {
                        if (!this.reportProblems) {
                            return null;
                        }
                        this.sourceParser.problemReporter().javadocInvalidMemberTypeQualification((int) (this.identifierPositionStack[0] >>> 32), (int) this.identifierPositionStack[i2], -1);
                        return null;
                    }
                }
                zEquals = zEquals2;
            } else {
                throw new InvalidInputException();
            }
            if (list == null) {
                if (zEquals) {
                    JavadocAllocationExpression javadocAllocationExpression = new JavadocAllocationExpression(this.identifierPositionStack[i - 1]);
                    javadocAllocationExpression.type = javadocImplicitTypeReference;
                    javadocAllocationExpression.tagValue = this.tagValue;
                    javadocAllocationExpression.sourceEnd = this.scanner.getCurrentTokenEndPosition();
                    if (i == 1) {
                        javadocAllocationExpression.qualification = new char[][]{this.identifierStack[0]};
                    } else {
                        char[][] cArr2 = this.identifierStack;
                        char[][] cArr3 = new char[i][];
                        javadocAllocationExpression.qualification = cArr3;
                        System.arraycopy(cArr2, 0, cArr3, 0, i);
                        javadocAllocationExpression.sourceStart = (int) (this.identifierPositionStack[0] >>> 32);
                    }
                    javadocAllocationExpression.memberStart = this.memberStart;
                    return javadocAllocationExpression;
                }
                int i4 = i - 1;
                JavadocMessageSend javadocMessageSend = new JavadocMessageSend(this.identifierStack[i4], this.identifierPositionStack[i4]);
                javadocMessageSend.receiver = javadocImplicitTypeReference;
                javadocMessageSend.tagValue = this.tagValue;
                javadocMessageSend.sourceEnd = this.scanner.getCurrentTokenEndPosition();
                return javadocMessageSend;
            }
            JavadocArgumentExpression[] javadocArgumentExpressionArr = new JavadocArgumentExpression[list.size()];
            list.toArray(javadocArgumentExpressionArr);
            if (zEquals) {
                JavadocAllocationExpression javadocAllocationExpression2 = new JavadocAllocationExpression(this.identifierPositionStack[i - 1]);
                javadocAllocationExpression2.arguments = javadocArgumentExpressionArr;
                javadocAllocationExpression2.type = javadocImplicitTypeReference;
                javadocAllocationExpression2.tagValue = this.tagValue;
                javadocAllocationExpression2.sourceEnd = this.scanner.getCurrentTokenEndPosition();
                if (i == 1) {
                    javadocAllocationExpression2.qualification = new char[][]{this.identifierStack[0]};
                } else {
                    char[][] cArr4 = this.identifierStack;
                    char[][] cArr5 = new char[i][];
                    javadocAllocationExpression2.qualification = cArr5;
                    System.arraycopy(cArr4, 0, cArr5, 0, i);
                    javadocAllocationExpression2.sourceStart = (int) (this.identifierPositionStack[0] >>> 32);
                }
                javadocAllocationExpression2.memberStart = this.memberStart;
                return javadocAllocationExpression2;
            }
            int i5 = i - 1;
            JavadocMessageSend javadocMessageSend2 = new JavadocMessageSend(this.identifierStack[i5], this.identifierPositionStack[i5], javadocArgumentExpressionArr);
            javadocMessageSend2.receiver = javadocImplicitTypeReference;
            javadocMessageSend2.tagValue = this.tagValue;
            javadocMessageSend2.sourceEnd = this.scanner.getCurrentTokenEndPosition();
            return javadocMessageSend2;
        } catch (ClassCastException unused) {
            throw new InvalidInputException();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected Object createReturnStatement() {
        return new JavadocReturnStatement(this.scanner.getCurrentTokenStartPosition(), this.scanner.getCurrentTokenEndPosition());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected void createTag() {
        this.tagValue = 100;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected Object createTypeReference(int i) {
        int i2 = this.identifierLengthStack[this.identifierLengthPtr];
        if (i2 == 1) {
            return new JavadocSingleTypeReference(this.identifierStack[this.identifierPtr], this.identifierPositionStack[this.identifierPtr], this.tagSourceStart, this.tagSourceEnd);
        }
        if (i2 <= 1) {
            return null;
        }
        char[][] cArr = new char[i2][];
        System.arraycopy(this.identifierStack, (this.identifierPtr - i2) + 1, cArr, 0, i2);
        long[] jArr = new long[i2];
        System.arraycopy(this.identifierPositionStack, (this.identifierPtr - i2) + 1, jArr, 0, i2);
        return new JavadocQualifiedTypeReference(cArr, jArr, this.tagSourceStart, this.tagSourceEnd);
    }

    protected TypeDeclaration getParsedTypeDeclaration() {
        for (int i = this.sourceParser.astPtr; i >= 0; i--) {
            ASTNode aSTNode = this.sourceParser.astStack[i];
            if (aSTNode instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) aSTNode;
                if (typeDeclaration.bodyEnd == 0) {
                    return typeDeclaration;
                }
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean parseThrows() {
        boolean z = super.parseThrows();
        this.tagWaitingForDescription = (z && this.reportProblems) ? 4 : 0;
        return z;
    }

    protected boolean parseReturn() {
        if (this.returnStatement == null) {
            this.returnStatement = createReturnStatement();
            return true;
        }
        if (!this.reportProblems) {
            return false;
        }
        this.sourceParser.problemReporter().javadocDuplicatedReturnTag(this.scanner.getCurrentTokenStartPosition(), this.scanner.getCurrentTokenEndPosition());
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x007d  */
    protected void parseSimpleTag() {
        char[] cArr = this.source;
        int i = this.index;
        this.index = i + 1;
        char c = cArr[i];
        if (c == '\\' && this.source[this.index] == 'u') {
            int i2 = this.index;
            this.index++;
            while (this.source[this.index] == 'u') {
                this.index++;
            }
            char[] cArr2 = this.source;
            int i3 = this.index;
            this.index = i3 + 1;
            int hexadecimalValue = ScannerHelper.getHexadecimalValue(cArr2[i3]);
            if (hexadecimalValue <= 15 && hexadecimalValue >= 0) {
                char[] cArr3 = this.source;
                int i4 = this.index;
                this.index = i4 + 1;
                int hexadecimalValue2 = ScannerHelper.getHexadecimalValue(cArr3[i4]);
                if (hexadecimalValue2 <= 15 && hexadecimalValue2 >= 0) {
                    char[] cArr4 = this.source;
                    int i5 = this.index;
                    this.index = i5 + 1;
                    int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(cArr4[i5]);
                    if (hexadecimalValue3 <= 15 && hexadecimalValue3 >= 0) {
                        char[] cArr5 = this.source;
                        int i6 = this.index;
                        this.index = i6 + 1;
                        int hexadecimalValue4 = ScannerHelper.getHexadecimalValue(cArr5[i6]);
                        if (hexadecimalValue4 > 15 || hexadecimalValue4 < 0) {
                            this.index = i2;
                        } else {
                            c = (char) ((((((hexadecimalValue * 16) + hexadecimalValue2) * 16) + hexadecimalValue3) * 16) + hexadecimalValue4);
                        }
                    } else {
                        this.index = i2;
                    }
                } else {
                    this.index = i2;
                }
            } else {
                this.index = i2;
            }
        }
        if (c == 'd' && readChar() == 'e' && readChar() == 'p' && readChar() == 'r' && readChar() == 'e' && readChar() == 'c' && readChar() == 'a' && readChar() == 't' && readChar() == 'e' && readChar() == 'd') {
            char c2 = readChar();
            if (ScannerHelper.isWhitespace(c2) || c2 == '*') {
                this.abort = true;
                this.deprecated = true;
                this.tagValue = 1;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:228:0x0378  */
    /* JADX WARN: Code duplicated, block: B:230:0x037c  */
    /* JADX WARN: Code duplicated, block: B:241:0x03a1  */
    /* JADX WARN: Code duplicated, block: B:258:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean parseTag(int i) throws InvalidInputException {
        int i2;
        boolean reference;
        int i3 = this.tagWaitingForDescription;
        if (i3 != 0) {
            if (i3 == 2 || i3 == 4) {
                if (!this.inlineTagStarted) {
                    this.sourceParser.problemReporter().javadocMissingTagDescriptionAfterReference((int) (this.identifierPositionStack[0] >>> 32), (int) this.identifierPositionStack[this.identifierPtr], this.sourceParser.modifiers);
                }
            } else if (!this.inlineTagStarted) {
                this.sourceParser.problemReporter().javadocMissingTagDescription(TAG_NAMES[this.tagWaitingForDescription], this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
            }
        }
        this.tagWaitingForDescription = 0;
        this.tagSourceStart = this.index;
        this.tagSourceEnd = i;
        this.scanner.startPosition = this.index;
        int i4 = this.index;
        char c = readChar();
        if (c == ' ' || c == '#' || c == '*' || c == '}') {
            if (this.reportProblems) {
                this.sourceParser.problemReporter().javadocInvalidTag(i, i4);
            }
            if (this.textStart == -1) {
                this.textStart = i4;
            }
            this.scanner.currentCharacter = c;
            return false;
        }
        if (ScannerHelper.isWhitespace(c)) {
            if (this.reportProblems) {
                this.sourceParser.problemReporter().javadocInvalidTag(i, i4);
            }
            if (this.textStart == -1) {
                this.textStart = i4;
            }
            this.scanner.currentCharacter = c;
            return false;
        }
        char[] cArr = new char[32];
        boolean z = true;
        int i5 = 32;
        int i6 = 0;
        char c2 = c;
        while (true) {
            if (i6 == i5) {
                int i7 = i5 + 32;
                char[] cArr2 = new char[i7];
                System.arraycopy(cArr, 0, cArr2, 0, i5);
                cArr = cArr2;
                i5 = i7;
            }
            i6++;
            cArr[i6] = c2;
            i2 = this.index;
            c2 = readChar();
            if (c2 == ' ') {
                break;
            }
            if (c2 != '#') {
                if (c2 == '*' || c2 == '}' || ScannerHelper.isWhitespace(c2)) {
                    break;
                }
            } else {
                z = false;
            }
        }
        this.tagSourceEnd = i2 - 1;
        this.scanner.currentCharacter = c2;
        this.scanner.currentPosition = i2;
        this.index = this.tagSourceEnd + 1;
        if (!z) {
            if (this.reportProblems) {
                this.sourceParser.problemReporter().javadocInvalidTag(this.tagSourceStart, this.tagSourceEnd);
            }
            if (this.textStart == -1) {
                this.textStart = this.index;
            }
            this.scanner.currentCharacter = c2;
            return false;
        }
        this.tagValue = 100;
        if (c == 'a') {
            if (i6 == TAG_AUTHOR_LENGTH && CharOperation.equals(TAG_AUTHOR, cArr, 0, i6)) {
                this.tagValue = 12;
                this.tagWaitingForDescription = this.tagValue;
            }
        } else {
            if (c != 'i') {
                if (c != 'l') {
                    if (c != 'p') {
                        if (c != 'v') {
                            switch (c) {
                                case 'c':
                                    if (i6 == TAG_CATEGORY_LENGTH && CharOperation.equals(TAG_CATEGORY, cArr, 0, i6)) {
                                        this.tagValue = 11;
                                        if (!this.inlineTagStarted) {
                                            reference = parseIdentifierTag(false);
                                        }
                                    } else if (i6 == TAG_CODE_LENGTH && this.inlineTagStarted && CharOperation.equals(TAG_CODE, cArr, 0, i6)) {
                                        this.tagValue = 18;
                                        this.tagWaitingForDescription = this.tagValue;
                                    }
                                    break;
                                case 'd':
                                    if (i6 == TAG_DEPRECATED_LENGTH && CharOperation.equals(TAG_DEPRECATED, cArr, 0, i6)) {
                                        this.deprecated = true;
                                        this.tagValue = 1;
                                        this.tagWaitingForDescription = this.tagValue;
                                    } else if (i6 == TAG_DOC_ROOT_LENGTH && CharOperation.equals(TAG_DOC_ROOT, cArr, 0, i6)) {
                                        this.tagValue = 20;
                                    }
                                    reference = true;
                                    break;
                                case 'e':
                                    if (i6 == TAG_EXCEPTION_LENGTH && CharOperation.equals(TAG_EXCEPTION, cArr, 0, i6)) {
                                        this.tagValue = 5;
                                        if (!this.inlineTagStarted) {
                                            reference = parseThrows();
                                        }
                                        break;
                                    }
                                    break;
                                default:
                                    switch (c) {
                                        case 'r':
                                            if (i6 == TAG_RETURN_LENGTH && CharOperation.equals(TAG_RETURN, cArr, 0, i6)) {
                                                this.tagValue = 3;
                                                if (!this.inlineTagStarted) {
                                                    reference = parseReturn();
                                                }
                                                break;
                                            }
                                            break;
                                        case 's':
                                            if (i6 == TAG_SEE_LENGTH && CharOperation.equals(TAG_SEE, cArr, 0, i6)) {
                                                this.tagValue = 6;
                                                if (!this.inlineTagStarted) {
                                                    reference = parseReference();
                                                }
                                            } else if (i6 == TAG_SERIAL_LENGTH && CharOperation.equals(TAG_SERIAL, cArr, 0, i6)) {
                                                this.tagValue = 13;
                                                this.tagWaitingForDescription = this.tagValue;
                                            } else if (i6 == TAG_SERIAL_DATA_LENGTH && CharOperation.equals(TAG_SERIAL_DATA, cArr, 0, i6)) {
                                                this.tagValue = 14;
                                                this.tagWaitingForDescription = this.tagValue;
                                            } else if (i6 == TAG_SERIAL_FIELD_LENGTH && CharOperation.equals(TAG_SERIAL_FIELD, cArr, 0, i6)) {
                                                this.tagValue = 15;
                                                this.tagWaitingForDescription = this.tagValue;
                                            } else if (i6 == TAG_SINCE_LENGTH && CharOperation.equals(TAG_SINCE, cArr, 0, i6)) {
                                                this.tagValue = 16;
                                                this.tagWaitingForDescription = this.tagValue;
                                            }
                                            break;
                                        case 't':
                                            if (i6 == TAG_THROWS_LENGTH && CharOperation.equals(TAG_THROWS, cArr, 0, i6)) {
                                                this.tagValue = 4;
                                                if (!this.inlineTagStarted) {
                                                    reference = parseThrows();
                                                }
                                                break;
                                            }
                                            break;
                                        default:
                                            createTag();
                                            break;
                                    }
                                    break;
                            }
                        } else if (i6 == TAG_VALUE_LENGTH && CharOperation.equals(TAG_VALUE, cArr, 0, i6)) {
                            this.tagValue = 10;
                            if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                                if (this.inlineTagStarted) {
                                    reference = parseReference();
                                }
                            } else if (this.validValuePositions == -1) {
                                if (this.invalidValuePositions != -1 && this.reportProblems) {
                                    ProblemReporter problemReporter = this.sourceParser.problemReporter();
                                    long j = this.invalidValuePositions;
                                    problemReporter.javadocUnexpectedTag((int) (j >>> 32), (int) j);
                                }
                                this.invalidValuePositions = (((long) this.tagSourceStart) << 32) + ((long) this.tagSourceEnd);
                            } else if (this.reportProblems) {
                                this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
                            }
                        } else if (i6 == TAG_VERSION_LENGTH && CharOperation.equals(TAG_VERSION, cArr, 0, i6)) {
                            this.tagValue = 17;
                            this.tagWaitingForDescription = this.tagValue;
                        } else {
                            createTag();
                        }
                    } else if (i6 == TAG_PARAM_LENGTH && CharOperation.equals(TAG_PARAM, cArr, 0, i6)) {
                        this.tagValue = 2;
                        if (!this.inlineTagStarted) {
                            reference = parseParam();
                        }
                    }
                } else if (i6 == TAG_LINK_LENGTH && CharOperation.equals(TAG_LINK, cArr, 0, i6)) {
                    this.tagValue = 7;
                    if (this.inlineTagStarted || (this.kind & 8) != 0) {
                        reference = parseReference();
                    }
                } else if (i6 == TAG_LINKPLAIN_LENGTH && CharOperation.equals(TAG_LINKPLAIN, cArr, 0, i6)) {
                    this.tagValue = 8;
                    if (this.inlineTagStarted) {
                        reference = parseReference();
                    }
                } else if (i6 == TAG_LITERAL_LENGTH && this.inlineTagStarted && CharOperation.equals(TAG_LITERAL, cArr, 0, i6)) {
                    this.tagValue = 19;
                    this.tagWaitingForDescription = this.tagValue;
                }
            } else if (i6 == TAG_INHERITDOC_LENGTH && CharOperation.equals(TAG_INHERITDOC, cArr, 0, i6)) {
                int i8 = this.lastBlockTagValue;
                if (i8 == 0 || i8 == 2 || i8 == 3 || i8 == 4 || i8 == 5) {
                    if (this.reportProblems) {
                        recordInheritedPosition((((long) this.tagSourceStart) << 32) + ((long) this.tagSourceEnd));
                    }
                    if (this.inlineTagStarted) {
                        parseInheritDocTag();
                    }
                    reference = true;
                } else {
                    if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
                    }
                    reference = false;
                }
                this.tagValue = 9;
            }
            this.textStart = this.index;
            if (this.tagValue != 100) {
                if (!this.inlineTagStarted) {
                    this.lastBlockTagValue = this.tagValue;
                }
                if ((this.inlineTagStarted && JAVADOC_TAG_TYPE[this.tagValue] == 2) || (!this.inlineTagStarted && JAVADOC_TAG_TYPE[this.tagValue] == 1)) {
                    this.tagValue = 100;
                    this.tagWaitingForDescription = 0;
                    if (!this.reportProblems) {
                        return false;
                    }
                    this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
                    return false;
                }
            }
            return reference;
        }
        reference = false;
        this.textStart = this.index;
        if (this.tagValue != 100) {
            if (!this.inlineTagStarted) {
                this.lastBlockTagValue = this.tagValue;
            }
            if (this.inlineTagStarted) {
                this.tagValue = 100;
                this.tagWaitingForDescription = 0;
                if (!this.reportProblems) {
                    return false;
                }
                this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
                return false;
            }
            this.tagValue = 100;
            this.tagWaitingForDescription = 0;
            if (!this.reportProblems) {
                return false;
            }
            this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
            return false;
        }
        return reference;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean parseParam() throws InvalidInputException {
        boolean param = super.parseParam();
        this.tagWaitingForDescription = (param && this.reportProblems) ? 2 : 0;
        return param;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean pushParamName(boolean z) {
        ASTNode javadocSingleNameReference;
        if (z) {
            javadocSingleNameReference = new JavadocSingleTypeReference(this.identifierStack[1], this.identifierPositionStack[1], this.tagSourceStart, this.tagSourceEnd);
        } else {
            javadocSingleNameReference = new JavadocSingleNameReference(this.identifierStack[0], this.identifierPositionStack[0], this.tagSourceStart, this.tagSourceEnd);
        }
        if (this.astLengthPtr == -1) {
            pushOnAstStack(javadocSingleNameReference, true);
        } else {
            if (!z) {
                for (int i = 1; i <= this.astLengthPtr; i += 3) {
                    if (this.astLengthStack[i] != 0) {
                        if (this.reportProblems) {
                            this.sourceParser.problemReporter().javadocUnexpectedTag(this.tagSourceStart, this.tagSourceEnd);
                        }
                        int i2 = this.invalidParamReferencesPtr;
                        if (i2 == -1) {
                            this.invalidParamReferencesStack = new JavadocSingleNameReference[10];
                        }
                        ASTNode[] aSTNodeArr = this.invalidParamReferencesStack;
                        int length = aSTNodeArr.length;
                        int i3 = i2 + 1;
                        this.invalidParamReferencesPtr = i3;
                        if (i3 >= length) {
                            JavadocSingleNameReference[] javadocSingleNameReferenceArr = new JavadocSingleNameReference[length + 10];
                            this.invalidParamReferencesStack = javadocSingleNameReferenceArr;
                            System.arraycopy(aSTNodeArr, 0, javadocSingleNameReferenceArr, 0, length);
                        }
                        this.invalidParamReferencesStack[this.invalidParamReferencesPtr] = javadocSingleNameReference;
                        return false;
                    }
                }
            }
            int i4 = this.astLengthPtr % 3;
            if (i4 == 0) {
                pushOnAstStack(javadocSingleNameReference, false);
            } else {
                if (i4 != 2) {
                    return false;
                }
                pushOnAstStack(javadocSingleNameReference, true);
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean pushSeeRef(Object obj) {
        if (this.astLengthPtr == -1) {
            pushOnAstStack(null, true);
            pushOnAstStack(null, true);
            pushOnAstStack(obj, true);
        } else {
            int i = this.astLengthPtr % 3;
            if (i == 0) {
                pushOnAstStack(null, true);
                pushOnAstStack(obj, true);
            } else if (i == 1) {
                pushOnAstStack(obj, true);
            } else {
                if (i != 2) {
                    return false;
                }
                pushOnAstStack(obj, false);
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected void pushText(int i, int i2) {
        this.tagWaitingForDescription = 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected boolean pushThrowName(Object obj) {
        if (this.astLengthPtr == -1) {
            pushOnAstStack(null, true);
            pushOnAstStack(obj, true);
        } else {
            int i = this.astLengthPtr % 3;
            if (i == 0) {
                pushOnAstStack(obj, true);
            } else if (i == 1) {
                pushOnAstStack(obj, false);
            } else {
                if (i != 2) {
                    return false;
                }
                pushOnAstStack(null, true);
                pushOnAstStack(obj, true);
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected void refreshInlineTagPosition(int i) {
        if (this.tagWaitingForDescription != 0) {
            this.sourceParser.problemReporter().javadocMissingTagDescription(TAG_NAMES[this.tagWaitingForDescription], this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
            this.tagWaitingForDescription = 0;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected void refreshReturnStatement() {
        ((JavadocReturnStatement) this.returnStatement).bits &= -262145;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("check javadoc: ");
        stringBuffer.append(this.checkDocComment).append("\njavadoc: ");
        stringBuffer.append(this.docComment).append("\n");
        stringBuffer.append(super.toString());
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser
    protected void updateDocComment() {
        int i = this.tagWaitingForDescription;
        if (i != 0) {
            if (i == 2 || i == 4) {
                if (!this.inlineTagStarted) {
                    this.sourceParser.problemReporter().javadocMissingTagDescriptionAfterReference((int) (this.identifierPositionStack[0] >>> 32), (int) this.identifierPositionStack[this.identifierPtr], this.sourceParser.modifiers);
                }
            } else if (!this.inlineTagStarted) {
                this.sourceParser.problemReporter().javadocMissingTagDescription(TAG_NAMES[this.tagWaitingForDescription], this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
            }
        }
        this.tagWaitingForDescription = 0;
        if (this.inheritedPositions != null && this.inheritedPositionsPtr != this.inheritedPositions.length) {
            long[] jArr = this.inheritedPositions;
            long[] jArr2 = new long[this.inheritedPositionsPtr];
            this.inheritedPositions = jArr2;
            System.arraycopy(jArr, 0, jArr2, 0, this.inheritedPositionsPtr);
        }
        this.docComment.inheritedPositions = this.inheritedPositions;
        Javadoc javadoc = this.docComment;
        long j = this.validValuePositions;
        if (j == -1) {
            j = this.invalidValuePositions;
        }
        javadoc.valuePositions = j;
        if (this.returnStatement != null) {
            this.docComment.returnStatement = (JavadocReturnStatement) this.returnStatement;
        }
        int i2 = this.invalidParamReferencesPtr;
        if (i2 >= 0) {
            this.docComment.invalidParameters = new JavadocSingleNameReference[i2 + 1];
            System.arraycopy(this.invalidParamReferencesStack, 0, this.docComment.invalidParameters, 0, this.invalidParamReferencesPtr + 1);
        }
        if (this.astLengthPtr == -1) {
            return;
        }
        int[] iArr = new int[3];
        for (int i3 = 0; i3 <= this.astLengthPtr; i3++) {
            int i4 = i3 % 3;
            iArr[i4] = iArr[i4] + this.astLengthStack[i3];
        }
        this.docComment.seeReferences = new Expression[iArr[2]];
        this.docComment.exceptionReferences = new TypeReference[iArr[1]];
        int i5 = iArr[0];
        this.docComment.paramReferences = new JavadocSingleNameReference[i5];
        int i6 = iArr[0];
        this.docComment.paramTypeParameters = new JavadocSingleTypeReference[i6];
        while (this.astLengthPtr >= 0) {
            int i7 = this.astLengthPtr % 3;
            if (i7 == 0) {
                int[] iArr2 = this.astLengthStack;
                int i8 = this.astLengthPtr;
                this.astLengthPtr = i8 - 1;
                int i9 = iArr2[i8];
                for (int i10 = 0; i10 < i9; i10++) {
                    Object[] objArr = this.astStack;
                    int i11 = this.astPtr;
                    this.astPtr = i11 - 1;
                    Expression expression = (Expression) objArr[i11];
                    if (expression instanceof JavadocSingleNameReference) {
                        i5--;
                        this.docComment.paramReferences[i5] = (JavadocSingleNameReference) expression;
                    } else if (expression instanceof JavadocSingleTypeReference) {
                        i6--;
                        this.docComment.paramTypeParameters[i6] = (JavadocSingleTypeReference) expression;
                    }
                }
            } else if (i7 == 1) {
                int[] iArr3 = this.astLengthStack;
                int i12 = this.astLengthPtr;
                this.astLengthPtr = i12 - 1;
                int i13 = iArr3[i12];
                for (int i14 = 0; i14 < i13; i14++) {
                    TypeReference[] typeReferenceArr = this.docComment.exceptionReferences;
                    int i15 = iArr[i7] - 1;
                    iArr[i7] = i15;
                    Object[] objArr2 = this.astStack;
                    int i16 = this.astPtr;
                    this.astPtr = i16 - 1;
                    typeReferenceArr[i15] = (TypeReference) objArr2[i16];
                }
            } else if (i7 == 2) {
                int[] iArr4 = this.astLengthStack;
                int i17 = this.astLengthPtr;
                this.astLengthPtr = i17 - 1;
                int i18 = iArr4[i17];
                for (int i19 = 0; i19 < i18; i19++) {
                    Expression[] expressionArr = this.docComment.seeReferences;
                    int i20 = iArr[i7] - 1;
                    iArr[i7] = i20;
                    Object[] objArr3 = this.astStack;
                    int i21 = this.astPtr;
                    this.astPtr = i21 - 1;
                    expressionArr[i20] = (Expression) objArr3[i21];
                }
            }
        }
        if (i5 == 0) {
            this.docComment.paramTypeParameters = null;
            return;
        }
        if (i6 == 0) {
            this.docComment.paramReferences = null;
            return;
        }
        int i22 = iArr[0];
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.docComment.paramReferences;
        int i23 = i22 - i5;
        JavadocSingleNameReference[] javadocSingleNameReferenceArr2 = new JavadocSingleNameReference[i23];
        this.docComment.paramReferences = javadocSingleNameReferenceArr2;
        System.arraycopy(javadocSingleNameReferenceArr, i5, javadocSingleNameReferenceArr2, 0, i23);
        JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.docComment.paramTypeParameters;
        int i24 = i22 - i6;
        JavadocSingleTypeReference[] javadocSingleTypeReferenceArr2 = new JavadocSingleTypeReference[i24];
        this.docComment.paramTypeParameters = javadocSingleTypeReferenceArr2;
        System.arraycopy(javadocSingleTypeReferenceArr, i6, javadocSingleTypeReferenceArr2, 0, i24);
    }
}
