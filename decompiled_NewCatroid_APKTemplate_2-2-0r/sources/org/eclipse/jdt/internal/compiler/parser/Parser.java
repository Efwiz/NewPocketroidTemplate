package org.eclipse.jdt.internal.compiler.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.physics.bullet.collision.CollisionConstants;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ReadManager;
import org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference;
import org.eclipse.jdt.internal.compiler.ast.AssertStatement;
import org.eclipse.jdt.internal.compiler.ast.Assignment;
import org.eclipse.jdt.internal.compiler.ast.BinaryExpression;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.BreakStatement;
import org.eclipse.jdt.internal.compiler.ast.CaseStatement;
import org.eclipse.jdt.internal.compiler.ast.CastExpression;
import org.eclipse.jdt.internal.compiler.ast.CharLiteral;
import org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess;
import org.eclipse.jdt.internal.compiler.ast.CombinedBinaryExpression;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.CompoundAssignment;
import org.eclipse.jdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ContinueStatement;
import org.eclipse.jdt.internal.compiler.ast.DoStatement;
import org.eclipse.jdt.internal.compiler.ast.DoubleLiteral;
import org.eclipse.jdt.internal.compiler.ast.EmptyStatement;
import org.eclipse.jdt.internal.compiler.ast.EqualExpression;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldReference;
import org.eclipse.jdt.internal.compiler.ast.FloatLiteral;
import org.eclipse.jdt.internal.compiler.ast.ForStatement;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.eclipse.jdt.internal.compiler.ast.IfStatement;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression;
import org.eclipse.jdt.internal.compiler.ast.IntLiteral;
import org.eclipse.jdt.internal.compiler.ast.IntersectionCastTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Javadoc;
import org.eclipse.jdt.internal.compiler.ast.LabeledStatement;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LongLiteral;
import org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NameReference;
import org.eclipse.jdt.internal.compiler.ast.NormalAnnotation;
import org.eclipse.jdt.internal.compiler.ast.NullLiteral;
import org.eclipse.jdt.internal.compiler.ast.OR_OR_Expression;
import org.eclipse.jdt.internal.compiler.ast.OperatorIds;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.PostfixExpression;
import org.eclipse.jdt.internal.compiler.ast.PrefixExpression;
import org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedSuperReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Receiver;
import org.eclipse.jdt.internal.compiler.ast.Reference;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;
import org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.SingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.StringLiteral;
import org.eclipse.jdt.internal.compiler.ast.SuperReference;
import org.eclipse.jdt.internal.compiler.ast.SwitchStatement;
import org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;
import org.eclipse.jdt.internal.compiler.ast.TryStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.UnaryExpression;
import org.eclipse.jdt.internal.compiler.ast.UnionTypeReference;
import org.eclipse.jdt.internal.compiler.ast.WhileStatement;
import org.eclipse.jdt.internal.compiler.ast.Wildcard;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.parser.diagnose.DiagnoseParser;
import org.eclipse.jdt.internal.compiler.parser.diagnose.RangeUtil;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class Parser implements TerminalTokens, ParserBasicInformation, ConflictedParser, OperatorIds, TypeIds {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind = null;
    protected static final int AstStackIncrement = 100;
    public static final int BracketKinds = 3;
    public static final int CurlyBracket = 2;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_AUTOMATON = false;
    private static final String EOF_TOKEN = "$eof";
    private static final String ERROR_TOKEN = "$error";
    protected static final int ExpressionStackIncrement = 100;
    private static final String FILEPREFIX = "parser";
    protected static final int GenericsStackIncrement = 10;
    protected static final int HALT = 0;
    private static final String INVALID_CHARACTER = "Invalid Character";
    private static final String READABLE_NAMES_FILE = "readableNames";
    protected static final int RESTART = 1;
    protected static final int RESUME = 2;
    public static final int RoundBracket = 0;
    protected static final int SUPER_CALL = 2;
    public static final int SquareBracket = 1;
    protected static final int StackIncrement = 255;
    protected static final int THIS_CALL = 3;
    protected static final int TypeAnnotationStackIncrement = 100;
    private static final String UNEXPECTED_EOF = "Unexpected End Of File";
    protected boolean annotationRecoveryActivated;
    protected int astLengthPtr;
    protected int[] astLengthStack;
    protected int astPtr;
    protected ASTNode[] astStack;
    protected boolean checkExternalizeStrings;
    protected int colonColonStart;
    public CompilationUnitDeclaration compilationUnit;
    protected RecoveredElement currentElement;
    public int currentToken;
    protected boolean diet;
    protected int dietInt;
    protected int dimensions;
    protected int endPosition;
    protected int endStatementPosition;
    private boolean expectTypeAnnotation;
    protected int expressionLengthPtr;
    protected int[] expressionLengthStack;
    protected int expressionPtr;
    protected Expression[] expressionStack;
    public int firstToken;
    protected int forStartPosition;
    protected int genericsIdentifiersLengthPtr;
    protected int[] genericsIdentifiersLengthStack;
    protected int genericsLengthPtr;
    protected int[] genericsLengthStack;
    protected int genericsPtr;
    protected ASTNode[] genericsStack;
    private boolean haltOnSyntaxError;
    protected boolean hasError;
    protected boolean hasReportedError;
    protected int identifierLengthPtr;
    protected int[] identifierLengthStack;
    protected long[] identifierPositionStack;
    protected int identifierPtr;
    protected char[][] identifierStack;
    protected boolean ignoreNextClosingBrace;
    protected boolean ignoreNextOpeningBrace;
    protected int intPtr;
    protected int[] intStack;
    public Javadoc javadoc;
    public JavadocParser javadocParser;
    protected int lParenPos;
    public int lastAct;
    protected int lastCheckPoint;
    protected int lastErrorEndPosition;
    protected int lastErrorEndPositionBeforeRecovery;
    protected int lastIgnoredToken;
    protected int lastJavadocEnd;
    protected int lastPosistion;
    protected int listLength;
    protected int listTypeParameterLength;
    public boolean methodRecoveryActivated;
    protected int modifiers;
    protected int modifiersSourceStart;
    protected int[] nestedMethod;
    protected int nestedType;
    protected int nextIgnoredToken;
    protected int nextTypeStart;
    ASTNode[] noAstNodes;
    Expression[] noExpressions;
    protected boolean optimizeStringLiterals;
    protected CompilerOptions options;
    protected boolean parsingJava8Plus;
    protected TypeDeclaration pendingRecoveredType;
    protected ProblemReporter problemReporter;
    private boolean processingLambdaParameterList;
    protected int rBraceEnd;
    protected int rBraceStart;
    protected int rBraceSuccessorStart;
    protected int rBracketPosition;
    protected int rParenPos;
    public ReadManager readManager;
    protected int realBlockPtr;
    protected int[] realBlockStack;
    protected boolean recordStringLiterals;
    protected int recoveredStaticInitializerStart;
    protected int recoveredTypePtr;
    protected TypeDeclaration[] recoveredTypes;
    public RecoveryScanner recoveryScanner;
    public ReferenceContext referenceContext;
    private boolean reparsingLambdaExpression;
    public boolean reportOnlyOneSyntaxError;
    public boolean reportSyntaxErrorIsRequired;
    protected boolean restartRecovery;
    public Scanner scanner;
    protected int[] stack;
    private int[] stateStackLengthStack;
    protected int stateStackTop;
    protected boolean statementRecoveryActivated;
    protected int synchronizedBlockSourceStart;
    private boolean tolerateDefaultClassMethods;
    protected int typeAnnotationLengthPtr;
    protected int[] typeAnnotationLengthStack;
    protected int typeAnnotationPtr;
    protected Annotation[] typeAnnotationStack;
    protected int unstackedAct;
    protected int valueLambdaNestDepth;
    protected int[] variablesCounter;
    public static final char[] FALL_THROUGH_TAG = "$FALL-THROUGH$".toCharArray();
    public static final char[] CASES_OMITTED_TAG = "$CASES-OMITTED$".toCharArray();
    public static char[] asb = null;
    public static char[] asr = null;
    public static char[] base_action = null;
    public static short[] check_table = null;
    public static char[] in_symb = null;
    public static char[] lhs = null;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public static String[] f629name = null;
    public static char[] nasb = null;
    public static char[] nasr = null;
    public static char[] non_terminal_index = null;
    public static String[] readableName = null;
    public static byte[] rhs = null;
    public static int[] reverse_index = null;
    public static char[] recovery_templates_index = null;
    public static char[] recovery_templates = null;
    public static char[] statements_recovery_filter = null;
    public static long[] rules_compliance = null;
    public static byte[] scope_la = null;
    public static char[] scope_lhs = null;
    public static char[] scope_prefix = null;
    public static char[] scope_rhs = null;
    public static char[] scope_state = null;
    public static char[] scope_state_set = null;
    public static char[] scope_suffix = null;
    public static char[] term_action = null;
    public static byte[] term_check = null;
    public static char[] terminal_index = null;
    public static boolean VERBOSE_RECOVERY = false;

    private enum LocalTypeKind {
        LOCAL,
        METHOD_REFERENCE,
        LAMBDA;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static LocalTypeKind[] valuesCustom() {
            LocalTypeKind[] localTypeKindArrValuesCustom = values();
            int length = localTypeKindArrValuesCustom.length;
            LocalTypeKind[] localTypeKindArr = new LocalTypeKind[length];
            System.arraycopy(localTypeKindArrValuesCustom, 0, localTypeKindArr, 0, length);
            return localTypeKindArr;
        }
    }

    protected void consumeAdditionalBound1() {
    }

    protected void consumeArrayCreationHeader() {
    }

    protected void consumeBlockStatement() {
    }

    protected void consumeCompilationUnit() {
    }

    protected void consumeEnterCompilationUnit() {
    }

    protected void consumeEnterInstanceCreationArgumentList() {
    }

    protected void consumeEnumBodyNoConstants() {
    }

    protected void consumeEnumDeclarations() {
    }

    protected void consumeGenericType() {
    }

    protected void consumeGenericTypeArrayType() {
    }

    protected void consumeGenericTypeNameArrayType() {
    }

    protected void consumeInsideCastExpression() {
    }

    protected void consumeInsideCastExpressionWithQualifiedGenerics() {
    }

    protected void consumeInvocationExpression() {
    }

    protected void consumeLabel() {
    }

    protected void consumeOnlyTypeArgumentsForCastExpression() {
    }

    protected void consumePrimitiveArrayType() {
    }

    protected void consumeResourceSpecification() {
    }

    protected void consumeSingleResource() {
    }

    protected void consumeTypeParameter1() {
    }

    protected void consumeWildcard1WithBounds() {
    }

    protected void consumeWildcard2WithBounds() {
    }

    protected void consumeWildcard3WithBounds() {
    }

    protected void consumeWildcardWithBounds() {
    }

    protected boolean isAssistParser() {
        return false;
    }

    protected boolean isIndirectlyInsideLambdaExpression() {
        return false;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[LocalTypeKind.valuesCustom().length];
        try {
            iArr2[LocalTypeKind.LAMBDA.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[LocalTypeKind.LOCAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[LocalTypeKind.METHOD_REFERENCE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind = iArr2;
        return iArr2;
    }

    static {
        try {
            initTables();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    public static int asi(int i) {
        return asb[original_state(i)];
    }

    public static final short base_check(int i) {
        return check_table[i - 801];
    }

    private static final void buildFile(String str, List list) throws Throwable {
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(str));
                try {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        bufferedWriter2.write(String.valueOf(it.next()));
                    }
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (IOException unused) {
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    System.out.println(String.valueOf(str) + " creation complete");
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            }
        } catch (IOException unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
        System.out.println(String.valueOf(str) + " creation complete");
    }

    private static void buildFileForCompliance(String str, int i, String[] strArr) throws Throwable {
        long j;
        byte[] bArr = new byte[i * 8];
        for (int i2 = 0; i2 < strArr.length; i2 += 3) {
            if ("2".equals(strArr[i2])) {
                int i3 = Integer.parseInt(strArr[i2 + 1]);
                String strTrim = strArr[i2 + 2].trim();
                if (CompilerOptions.VERSION_1_4.equals(strTrim)) {
                    j = ClassFileConstants.JDK1_4;
                } else if (CompilerOptions.VERSION_1_5.equals(strTrim)) {
                    j = ClassFileConstants.JDK1_5;
                } else if (CompilerOptions.VERSION_1_6.equals(strTrim)) {
                    j = ClassFileConstants.JDK1_6;
                } else if (CompilerOptions.VERSION_1_7.equals(strTrim)) {
                    j = ClassFileConstants.JDK1_7;
                } else if (CompilerOptions.VERSION_1_8.equals(strTrim)) {
                    j = ClassFileConstants.JDK1_8;
                } else {
                    j = "recovery".equals(strTrim) ? Long.MAX_VALUE : 0L;
                }
                int i4 = i3 * 8;
                bArr[i4] = (byte) (j >>> 56);
                bArr[i4 + 1] = (byte) (j >>> 48);
                bArr[i4 + 2] = (byte) (j >>> 40);
                bArr[i4 + 3] = (byte) (j >>> 32);
                bArr[i4 + 4] = (byte) (j >>> 24);
                bArr[i4 + 5] = (byte) (j >>> 16);
                bArr[i4 + 6] = (byte) (j >>> 8);
                bArr[i4 + 7] = (byte) j;
            }
        }
        buildFileForTable(str, bArr);
    }

    private static final String[] buildFileForName(String str, String str2) throws Throwable {
        String[] strArr = new String[str2.length()];
        strArr[0] = null;
        StringBuffer stringBuffer = new StringBuffer();
        int iIndexOf = str2.indexOf(34, str2.indexOf("name[]"));
        String strSubstring = str2.substring(iIndexOf, str2.indexOf("};", iIndexOf));
        StringBuffer stringBuffer2 = new StringBuffer();
        boolean z = false;
        int i = 1;
        int i2 = -1;
        for (int i3 = 0; i3 < strSubstring.length(); i3++) {
            char cCharAt = strSubstring.charAt(i3);
            if (cCharAt == '\"') {
                if (i2 == -1) {
                    i2 = i3 + 1;
                } else {
                    if (z) {
                        stringBuffer.append('\n');
                        strArr[i] = stringBuffer2.toString();
                        stringBuffer2 = new StringBuffer();
                        i++;
                    }
                    String strSubstring2 = strSubstring.substring(i2, i3);
                    if (strSubstring2.equals(ERROR_TOKEN)) {
                        strSubstring2 = INVALID_CHARACTER;
                    } else if (strSubstring2.equals(EOF_TOKEN)) {
                        strSubstring2 = UNEXPECTED_EOF;
                    }
                    stringBuffer.append(strSubstring2);
                    stringBuffer2.append(strSubstring2);
                    z = true;
                    i2 = -1;
                }
            }
            if (i2 == -1 && cCharAt == '+') {
                z = false;
            }
        }
        if (stringBuffer2.length() > 0) {
            strArr[i] = stringBuffer2.toString();
            i++;
        }
        buildFileForTable(str, stringBuffer.toString().toCharArray());
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        return strArr2;
    }

    private static void buildFileForReadableName(String str, char[] cArr, char[] cArr2, String[] strArr, String[] strArr2) throws Throwable {
        ArrayList arrayList = new ArrayList();
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < strArr2.length; i += 3) {
            if (SchemaSymbols.ATTVAL_TRUE_1.equals(strArr2[i])) {
                char c = cArr2[cArr[Integer.parseInt(strArr2[i + 1])]];
                StringBuffer stringBuffer = new StringBuffer();
                if (!zArr[c]) {
                    zArr[c] = true;
                    stringBuffer.append(strArr[c]);
                    stringBuffer.append('=');
                    stringBuffer.append(strArr2[i + 2].trim());
                    stringBuffer.append('\n');
                    arrayList.add(String.valueOf(stringBuffer));
                }
            }
        }
        int i2 = 1;
        while (!INVALID_CHARACTER.equals(strArr[i2])) {
            i2++;
        }
        for (int i3 = i2 + 1; i3 < length; i3++) {
            if (!zArr[i3]) {
                System.out.println(String.valueOf(strArr[i3]) + " has no readable name");
            }
        }
        Collections.sort(arrayList);
        buildFile(str, arrayList);
    }

    private static final void buildFileForTable(String str, byte[] bArr) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.close();
                } catch (IOException unused) {
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    System.out.println(String.valueOf(str) + " creation complete");
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            }
        } catch (IOException unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
        System.out.println(String.valueOf(str) + " creation complete");
    }

    private static final void buildFileForTable(String str, char[] cArr) throws Throwable {
        byte[] bArr = new byte[cArr.length * 2];
        for (int i = 0; i < cArr.length; i++) {
            int i2 = i * 2;
            char c = cArr[i];
            bArr[i2] = (byte) (c >>> '\b');
            bArr[i2 + 1] = (byte) (c & 255);
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.close();
                } catch (IOException unused) {
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    System.out.println(String.valueOf(str) + " creation complete");
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            }
        } catch (IOException unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
        System.out.println(String.valueOf(str) + " creation complete");
    }

    private static final byte[] buildFileOfByteFor(String str, String str2, String[] strArr) throws Throwable {
        int i;
        int i2 = 0;
        while (true) {
            i = i2 + 1;
            if (strArr[i2].equals(str2)) {
                break;
            }
            i2 = i;
        }
        byte[] bArr = new byte[strArr.length];
        int i3 = 0;
        while (true) {
            int i4 = i + 1;
            String str3 = strArr[i];
            if (!str3.equals("}")) {
                bArr[i3] = (byte) Integer.parseInt(str3);
                i = i4;
                i3++;
            } else {
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                buildFileForTable(str, bArr2);
                return bArr2;
            }
        }
    }

    private static final char[] buildFileOfIntFor(String str, String str2, String[] strArr) throws Throwable {
        int i;
        int i2 = 0;
        while (true) {
            i = i2 + 1;
            if (strArr[i2].equals(str2)) {
                break;
            }
            i2 = i;
        }
        char[] cArr = new char[strArr.length];
        int i3 = 0;
        while (true) {
            int i4 = i + 1;
            String str3 = strArr[i];
            if (!str3.equals("}")) {
                cArr[i3] = (char) Integer.parseInt(str3);
                i = i4;
                i3++;
            } else {
                char[] cArr2 = new char[i3];
                System.arraycopy(cArr, 0, cArr2, 0, i3);
                buildFileForTable(str, cArr2);
                return cArr2;
            }
        }
    }

    private static final void buildFileOfShortFor(String str, String str2, String[] strArr) throws Throwable {
        int i;
        int i2 = 0;
        while (true) {
            i = i2 + 1;
            if (strArr[i2].equals(str2)) {
                break;
            } else {
                i2 = i;
            }
        }
        char[] cArr = new char[strArr.length];
        int i3 = 0;
        while (true) {
            int i4 = i + 1;
            String str3 = strArr[i];
            if (!str3.equals("}")) {
                cArr[i3] = (char) (Integer.parseInt(str3) + 32768);
                i = i4;
                i3++;
            } else {
                char[] cArr2 = new char[i3];
                System.arraycopy(cArr, 0, cArr2, 0, i3);
                buildFileForTable(str, cArr2);
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void buildFilesForRecoveryTemplates(String str, String str2, char[] cArr, char[] cArr2, String[] strArr, char[] cArr3, String[] strArr2) throws Throwable {
        char[] cArr4;
        int[] iArrComputeReverseTable = computeReverseTable(cArr, cArr2, strArr);
        char[] cArr5 = new char[cArr2.length];
        char[] cArr6 = new char[cArr2.length];
        int i = 0;
        for (int i2 = 0; i2 < strArr2.length; i2 += 3) {
            if ("3".equals(strArr2[i2])) {
                int length = cArr6.length;
                int i3 = i + 1;
                if (length == i3) {
                    cArr4 = new char[length * 2];
                    System.arraycopy(cArr6, 0, cArr4, 0, length);
                } else {
                    cArr4 = cArr6;
                }
                cArr4[i] = 0;
                cArr5[cArr3[Integer.parseInt(strArr2[i2 + 1])]] = (char) i3;
                StringTokenizer stringTokenizer = new StringTokenizer(strArr2[i2 + 2].trim(), " ");
                int iCountTokens = stringTokenizer.countTokens();
                String[] strArr3 = new String[iCountTokens];
                int i4 = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    strArr3[i4] = stringTokenizer.nextToken();
                    i4++;
                }
                i = i3;
                for (int i5 = 0; i5 < iCountTokens; i5++) {
                    int symbol = getSymbol(strArr3[i5], strArr, iArrComputeReverseTable);
                    if (symbol > -1) {
                        int length2 = cArr4.length;
                        int i6 = i + 1;
                        if (length2 == i6) {
                            char[] cArr7 = new char[length2 * 2];
                            System.arraycopy(cArr4, 0, cArr7, 0, length2);
                            cArr4 = cArr7;
                        }
                        cArr4[i] = (char) symbol;
                        i = i6;
                    }
                }
                cArr6 = cArr4;
            }
        }
        int i7 = i + 1;
        cArr6[i] = 0;
        char[] cArr8 = new char[i7];
        System.arraycopy(cArr6, 0, cArr8, 0, i7);
        buildFileForTable(str, cArr5);
        buildFileForTable(str2, cArr8);
    }

    private static void buildFilesForStatementsRecoveryFilter(String str, char[] cArr, char[] cArr2, String[] strArr) throws Throwable {
        char[] cArr3 = new char[cArr.length];
        for (int i = 0; i < strArr.length; i += 3) {
            if ("4".equals(strArr[i])) {
                cArr3[cArr2[Integer.parseInt(strArr[i + 1])]] = 1;
            }
        }
        buildFileForTable(str, cArr3);
    }

    public static final void buildFilesFromLPG(String str, String str2) throws Throwable {
        char[] cArr = CharOperation.NO_CHAR;
        try {
            char[] fileCharContent = Util.getFileCharContent(new File(str), null);
            StringTokenizer stringTokenizer = new StringTokenizer(new String(fileCharContent), " \t\n\r[]={,;");
            String[] strArr = new String[stringTokenizer.countTokens()];
            int i = 0;
            int i2 = 0;
            while (stringTokenizer.hasMoreTokens()) {
                strArr[i2] = stringTokenizer.nextToken();
                i2++;
            }
            char[] cArrBuildFileOfIntFor = buildFileOfIntFor(FILEPREFIX + "1.rsc", "lhs", strArr);
            buildFileOfShortFor(FILEPREFIX + "2.rsc", "check_table", strArr);
            buildFileOfIntFor(FILEPREFIX + "3.rsc", "asb", strArr);
            buildFileOfIntFor(FILEPREFIX + "4.rsc", "asr", strArr);
            buildFileOfIntFor(FILEPREFIX + "5.rsc", "nasb", strArr);
            buildFileOfIntFor(FILEPREFIX + "6.rsc", "nasr", strArr);
            char[] cArrBuildFileOfIntFor2 = buildFileOfIntFor(FILEPREFIX + "7.rsc", "terminal_index", strArr);
            char[] cArrBuildFileOfIntFor3 = buildFileOfIntFor(FILEPREFIX + "8.rsc", "non_terminal_index", strArr);
            buildFileOfIntFor(FILEPREFIX + "9.rsc", "term_action", strArr);
            buildFileOfIntFor(FILEPREFIX + "10.rsc", "scope_prefix", strArr);
            buildFileOfIntFor(FILEPREFIX + "11.rsc", "scope_suffix", strArr);
            buildFileOfIntFor(FILEPREFIX + "12.rsc", "scope_lhs", strArr);
            buildFileOfIntFor(FILEPREFIX + "13.rsc", "scope_state_set", strArr);
            buildFileOfIntFor(FILEPREFIX + "14.rsc", "scope_rhs", strArr);
            buildFileOfIntFor(FILEPREFIX + "15.rsc", "scope_state", strArr);
            buildFileOfIntFor(FILEPREFIX + "16.rsc", "in_symb", strArr);
            byte[] bArrBuildFileOfByteFor = buildFileOfByteFor(FILEPREFIX + "17.rsc", "rhs", strArr);
            buildFileOfByteFor(FILEPREFIX + "18.rsc", "term_check", strArr);
            buildFileOfByteFor(FILEPREFIX + "19.rsc", "scope_la", strArr);
            String[] strArrBuildFileForName = buildFileForName(FILEPREFIX + "20.rsc", new String(fileCharContent));
            char[] cArr2 = CharOperation.NO_CHAR;
            try {
                StringTokenizer stringTokenizer2 = new StringTokenizer(new String(Util.getFileCharContent(new File(str2), null)), "\t\n\r#");
                String[] strArr2 = new String[stringTokenizer2.countTokens()];
                while (stringTokenizer2.hasMoreTokens()) {
                    strArr2[i] = stringTokenizer2.nextToken();
                    i++;
                }
                buildFileForCompliance(FILEPREFIX + "21.rsc", bArrBuildFileOfByteFor.length, strArr2);
                buildFileForReadableName("readableNames.props", cArrBuildFileOfIntFor, cArrBuildFileOfIntFor3, strArrBuildFileForName, strArr2);
                buildFilesForRecoveryTemplates(FILEPREFIX + "22.rsc", FILEPREFIX + "23.rsc", cArrBuildFileOfIntFor2, cArrBuildFileOfIntFor3, strArrBuildFileForName, cArrBuildFileOfIntFor, strArr2);
                buildFilesForStatementsRecoveryFilter(FILEPREFIX + "24.rsc", cArrBuildFileOfIntFor3, cArrBuildFileOfIntFor, strArr2);
                System.out.println(Messages.parser_moveFiles);
            } catch (IOException unused) {
                System.out.println(Messages.parser_incorrectPath);
            }
        } catch (IOException unused2) {
            System.out.println(Messages.parser_incorrectPath);
        }
    }

    protected static int[] computeReverseTable(char[] cArr, char[] cArr2, String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            int i2 = 0;
            while (true) {
                if (i2 < cArr.length) {
                    if (cArr[i2] == i) {
                        iArr[i] = i2;
                        break;
                    }
                    i2++;
                } else {
                    for (int i3 = 0; i3 < cArr2.length; i3++) {
                        if (cArr2[i3] == i) {
                            iArr[i] = -i3;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return iArr;
    }

    private static int getSymbol(String str, String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (str.equals(strArr[i])) {
                return iArr[i];
            }
        }
        return -1;
    }

    public static int in_symbol(int i) {
        return in_symb[original_state(i)];
    }

    public static final void initTables() throws Throwable {
        lhs = readTable(FILEPREFIX + "1.rsc");
        char[] table = readTable(FILEPREFIX + "2.rsc");
        check_table = new short[table.length];
        int length = table.length;
        while (true) {
            int i = length - 1;
            if (length > 0) {
                check_table[i] = (short) (table[i] - 32768);
                length = i;
            } else {
                asb = readTable(FILEPREFIX + "3.rsc");
                asr = readTable(FILEPREFIX + "4.rsc");
                nasb = readTable(FILEPREFIX + "5.rsc");
                nasr = readTable(FILEPREFIX + "6.rsc");
                terminal_index = readTable(FILEPREFIX + "7.rsc");
                non_terminal_index = readTable(FILEPREFIX + "8.rsc");
                term_action = readTable(FILEPREFIX + "9.rsc");
                scope_prefix = readTable(FILEPREFIX + "10.rsc");
                scope_suffix = readTable(FILEPREFIX + "11.rsc");
                scope_lhs = readTable(FILEPREFIX + "12.rsc");
                scope_state_set = readTable(FILEPREFIX + "13.rsc");
                scope_rhs = readTable(FILEPREFIX + "14.rsc");
                scope_state = readTable(FILEPREFIX + "15.rsc");
                in_symb = readTable(FILEPREFIX + "16.rsc");
                rhs = readByteTable(FILEPREFIX + "17.rsc");
                term_check = readByteTable(FILEPREFIX + "18.rsc");
                scope_la = readByteTable(FILEPREFIX + "19.rsc");
                f629name = readNameTable(FILEPREFIX + "20.rsc");
                rules_compliance = readLongTable(FILEPREFIX + "21.rsc");
                readableName = readReadableNameTable("readableNames.props");
                reverse_index = computeReverseTable(terminal_index, non_terminal_index, f629name);
                recovery_templates_index = readTable(FILEPREFIX + "22.rsc");
                recovery_templates = readTable(FILEPREFIX + "23.rsc");
                statements_recovery_filter = readTable(FILEPREFIX + "24.rsc");
                base_action = lhs;
                return;
            }
        }
    }

    public static int nasi(int i) {
        return nasb[original_state(i)];
    }

    public static int ntAction(int i, int i2) {
        return base_action[i + i2];
    }

    protected static int original_state(int i) {
        return -base_check(i);
    }

    protected static byte[] readByteTable(String str) throws Throwable {
        Throwable th;
        InputStream bufferedInputStream;
        InputStream resourceAsStream = Parser.class.getResourceAsStream(str);
        if (resourceAsStream == null) {
            throw new IOException(Messages.bind(Messages.parser_missingFile, str));
        }
        try {
            bufferedInputStream = new BufferedInputStream(resourceAsStream);
            try {
                byte[] inputStreamAsByteArray = Util.getInputStreamAsByteArray(bufferedInputStream, -1);
                try {
                    bufferedInputStream.close();
                } catch (IOException unused) {
                }
                return inputStreamAsByteArray;
            } catch (Throwable th2) {
                th = th2;
                try {
                    bufferedInputStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = resourceAsStream;
        }
    }

    protected static long[] readLongTable(String str) throws Throwable {
        InputStream resourceAsStream = Parser.class.getResourceAsStream(str);
        if (resourceAsStream == null) {
            throw new IOException(Messages.bind(Messages.parser_missingFile, str));
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
            try {
                byte[] inputStreamAsByteArray = Util.getInputStreamAsByteArray(bufferedInputStream, -1);
                try {
                    bufferedInputStream.close();
                } catch (IOException unused) {
                }
                int length = inputStreamAsByteArray.length;
                if (length % 8 != 0) {
                    throw new IOException(Messages.bind(Messages.parser_corruptedFile, str));
                }
                long[] jArr = new long[length / 8];
                int i = 0;
                int i2 = 0;
                while (true) {
                    int i3 = i + 1;
                    int i4 = i2 + 7;
                    long j = (((long) (inputStreamAsByteArray[i2] & 255)) << 56) + (((long) (inputStreamAsByteArray[i2 + 1] & 255)) << 48) + (((long) (inputStreamAsByteArray[i2 + 2] & 255)) << 40) + (((long) (inputStreamAsByteArray[i2 + 3] & 255)) << 32) + (((long) (inputStreamAsByteArray[i2 + 4] & 255)) << 24) + (((long) (inputStreamAsByteArray[i2 + 5] & 255)) << 16) + (((long) (inputStreamAsByteArray[i2 + 6] & 255)) << 8);
                    i2 += 8;
                    jArr[i] = j + ((long) (inputStreamAsByteArray[i4] & 255));
                    if (i2 == length) {
                        return jArr;
                    }
                    i = i3;
                }
            } catch (Throwable th) {
                th = th;
                resourceAsStream = bufferedInputStream;
                try {
                    resourceAsStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    protected static String[] readNameTable(String str) throws IOException {
        char[][] cArrSplitOn = CharOperation.splitOn('\n', readTable(str));
        String[] strArr = new String[cArrSplitOn.length + 1];
        int i = 0;
        strArr[0] = null;
        while (i < cArrSplitOn.length) {
            int i2 = i + 1;
            strArr[i2] = new String(cArrSplitOn[i]);
            i = i2;
        }
        return strArr;
    }

    protected static String[] readReadableNameTable(String str) {
        String[] strArr = new String[f629name.length];
        InputStream resourceAsStream = Parser.class.getResourceAsStream(str);
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            for (int i = 0; i < 119; i++) {
                strArr[i] = f629name[i];
            }
            int i2 = 118;
            while (true) {
                String[] strArr2 = f629name;
                if (i2 >= strArr2.length) {
                    return strArr;
                }
                String property = properties.getProperty(strArr2[i2]);
                if (property != null && property.length() > 0) {
                    strArr[i2] = property;
                } else {
                    strArr[i2] = f629name[i2];
                }
                i2++;
            }
        } catch (IOException unused) {
            return f629name;
        }
    }

    protected static char[] readTable(String str) throws Throwable {
        InputStream resourceAsStream = Parser.class.getResourceAsStream(str);
        if (resourceAsStream == null) {
            throw new IOException(Messages.bind(Messages.parser_missingFile, str));
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
            try {
                byte[] inputStreamAsByteArray = Util.getInputStreamAsByteArray(bufferedInputStream, -1);
                try {
                    bufferedInputStream.close();
                } catch (IOException unused) {
                }
                int length = inputStreamAsByteArray.length;
                if ((length & 1) != 0) {
                    throw new IOException(Messages.bind(Messages.parser_corruptedFile, str));
                }
                char[] cArr = new char[length / 2];
                int i = 0;
                int i2 = 0;
                while (true) {
                    int i3 = i + 1;
                    int i4 = i2 + 1;
                    int i5 = (inputStreamAsByteArray[i2] & 255) << 8;
                    i2 += 2;
                    cArr[i] = (char) (i5 + (inputStreamAsByteArray[i4] & 255));
                    if (i2 == length) {
                        return cArr;
                    }
                    i = i3;
                }
            } catch (Throwable th) {
                th = th;
                resourceAsStream = bufferedInputStream;
                try {
                    resourceAsStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int tAction(int i, int i2) {
        char[] cArr = term_action;
        byte[] bArr = term_check;
        char c = base_action[i];
        byte b = bArr[c + i2];
        int i3 = c;
        if (b == i2) {
            i3 = c + i2;
        }
        return cArr[i3];
    }

    public Parser() {
        this.astStack = new ASTNode[100];
        this.diet = false;
        this.dietInt = 0;
        this.expressionStack = new Expression[100];
        this.typeAnnotationStack = new Annotation[100];
        this.genericsIdentifiersLengthStack = new int[10];
        this.genericsLengthStack = new int[10];
        this.genericsStack = new ASTNode[10];
        this.lastErrorEndPositionBeforeRecovery = -1;
        this.colonColonStart = -1;
        this.forStartPosition = 0;
        this.noAstNodes = new ASTNode[100];
        this.noExpressions = new Expression[100];
        this.optimizeStringLiterals = true;
        this.reportOnlyOneSyntaxError = false;
        this.reportSyntaxErrorIsRequired = true;
        this.annotationRecoveryActivated = true;
        this.methodRecoveryActivated = false;
        this.statementRecoveryActivated = false;
        this.stack = new int[255];
        this.valueLambdaNestDepth = -1;
        this.stateStackLengthStack = new int[0];
        this.unstackedAct = 16382;
        this.haltOnSyntaxError = false;
        this.tolerateDefaultClassMethods = false;
        this.processingLambdaParameterList = false;
        this.expectTypeAnnotation = false;
        this.reparsingLambdaExpression = false;
    }

    public Parser(ProblemReporter problemReporter, boolean z) {
        this.astStack = new ASTNode[100];
        this.diet = false;
        this.dietInt = 0;
        this.expressionStack = new Expression[100];
        this.typeAnnotationStack = new Annotation[100];
        this.genericsIdentifiersLengthStack = new int[10];
        this.genericsLengthStack = new int[10];
        this.genericsStack = new ASTNode[10];
        this.lastErrorEndPositionBeforeRecovery = -1;
        this.colonColonStart = -1;
        this.forStartPosition = 0;
        this.noAstNodes = new ASTNode[100];
        this.noExpressions = new Expression[100];
        this.optimizeStringLiterals = true;
        this.reportOnlyOneSyntaxError = false;
        this.reportSyntaxErrorIsRequired = true;
        this.annotationRecoveryActivated = true;
        this.methodRecoveryActivated = false;
        this.statementRecoveryActivated = false;
        this.stack = new int[255];
        this.valueLambdaNestDepth = -1;
        this.stateStackLengthStack = new int[0];
        this.unstackedAct = 16382;
        this.haltOnSyntaxError = false;
        this.tolerateDefaultClassMethods = false;
        this.processingLambdaParameterList = false;
        this.expectTypeAnnotation = false;
        this.reparsingLambdaExpression = false;
        this.problemReporter = problemReporter;
        this.options = problemReporter.options;
        this.optimizeStringLiterals = z;
        initializeScanner();
        this.parsingJava8Plus = this.options.sourceLevel >= ClassFileConstants.JDK1_8;
        this.astLengthStack = new int[50];
        this.expressionLengthStack = new int[30];
        this.typeAnnotationLengthStack = new int[30];
        this.intStack = new int[50];
        this.identifierStack = new char[30][];
        this.identifierLengthStack = new int[30];
        this.nestedMethod = new int[30];
        this.realBlockStack = new int[30];
        this.identifierPositionStack = new long[30];
        this.variablesCounter = new int[30];
        this.javadocParser = createJavadocParser();
    }

    protected void annotationRecoveryCheckPoint(int i, int i2) {
        if (this.lastCheckPoint < i2) {
            this.lastCheckPoint = i2 + 1;
        }
    }

    public void arrayInitializer(int i) {
        ArrayInitializer arrayInitializer = new ArrayInitializer();
        if (i != 0) {
            int i2 = this.expressionPtr - i;
            this.expressionPtr = i2;
            Expression[] expressionArr = new Expression[i];
            arrayInitializer.expressions = expressionArr;
            System.arraycopy(this.expressionStack, i2 + 1, expressionArr, 0, i);
        }
        pushOnExpressionStack(arrayInitializer);
        arrayInitializer.sourceEnd = this.endStatementPosition;
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        arrayInitializer.sourceStart = iArr[i3];
    }

    protected void blockReal() {
        int[] iArr = this.realBlockStack;
        int i = this.realBlockPtr;
        iArr[i] = iArr[i] + 1;
    }

    public RecoveredElement buildInitialRecoveryState() {
        RecoveredElement recoveredInitializer;
        this.lastCheckPoint = 0;
        this.lastErrorEndPositionBeforeRecovery = this.scanner.currentPosition;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof CompilationUnitDeclaration) {
            RecoveredUnit recoveredUnit = new RecoveredUnit(this.compilationUnit, 0, this);
            this.compilationUnit.currentPackage = null;
            this.compilationUnit.imports = null;
            this.compilationUnit.types = null;
            this.currentToken = 0;
            this.listLength = 0;
            this.listTypeParameterLength = 0;
            this.endPosition = 0;
            this.endStatementPosition = 0;
            return recoveredUnit;
        }
        if (referenceContext instanceof AbstractMethodDeclaration) {
            recoveredInitializer = new RecoveredMethod((AbstractMethodDeclaration) this.referenceContext, null, 0, this);
            this.lastCheckPoint = ((AbstractMethodDeclaration) this.referenceContext).bodyStart;
            if (this.statementRecoveryActivated) {
                recoveredInitializer = recoveredInitializer.add(new Block(0), 0);
            }
        } else {
            if (!(referenceContext instanceof TypeDeclaration)) {
                recoveredInitializer = null;
                break;
            }
            FieldDeclaration[] fieldDeclarationArr = ((TypeDeclaration) referenceContext).fields;
            int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    recoveredInitializer = null;
                    break;
                }
                FieldDeclaration fieldDeclaration = fieldDeclarationArr[i];
                if (fieldDeclaration != null && fieldDeclaration.getKind() == 2 && ((Initializer) fieldDeclaration).block != null && fieldDeclaration.declarationSourceStart <= this.scanner.initialPosition && this.scanner.initialPosition <= fieldDeclaration.declarationSourceEnd && this.scanner.eofPosition <= fieldDeclaration.declarationSourceEnd + 1) {
                    recoveredInitializer = new RecoveredInitializer(fieldDeclaration, null, 1, this);
                    this.lastCheckPoint = fieldDeclaration.declarationSourceStart;
                    break;
                }
                i++;
            }
        }
        if (recoveredInitializer == null) {
            return recoveredInitializer;
        }
        for (int i2 = 0; i2 <= this.astPtr; i2++) {
            ASTNode aSTNode = this.astStack[i2];
            if (aSTNode instanceof AbstractMethodDeclaration) {
                AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) aSTNode;
                if (abstractMethodDeclaration.declarationSourceEnd == 0) {
                    recoveredInitializer = recoveredInitializer.add(abstractMethodDeclaration, 0);
                    this.lastCheckPoint = abstractMethodDeclaration.bodyStart;
                } else {
                    recoveredInitializer = recoveredInitializer.add(abstractMethodDeclaration, 0);
                    this.lastCheckPoint = abstractMethodDeclaration.declarationSourceEnd + 1;
                }
            } else if (aSTNode instanceof Initializer) {
                Initializer initializer = (Initializer) aSTNode;
                if (initializer.block != null) {
                    if (initializer.declarationSourceEnd == 0) {
                        recoveredInitializer = recoveredInitializer.add((FieldDeclaration) initializer, 1);
                        this.lastCheckPoint = initializer.sourceStart;
                    } else {
                        recoveredInitializer = recoveredInitializer.add((FieldDeclaration) initializer, 0);
                        this.lastCheckPoint = initializer.declarationSourceEnd + 1;
                    }
                }
            } else if (aSTNode instanceof FieldDeclaration) {
                FieldDeclaration fieldDeclaration2 = (FieldDeclaration) aSTNode;
                if (fieldDeclaration2.declarationSourceEnd == 0) {
                    recoveredInitializer = recoveredInitializer.add(fieldDeclaration2, 0);
                    if (fieldDeclaration2.initialization == null) {
                        this.lastCheckPoint = fieldDeclaration2.sourceEnd + 1;
                    } else {
                        this.lastCheckPoint = fieldDeclaration2.initialization.sourceEnd + 1;
                    }
                } else {
                    recoveredInitializer = recoveredInitializer.add(fieldDeclaration2, 0);
                    this.lastCheckPoint = fieldDeclaration2.declarationSourceEnd + 1;
                }
            } else if (aSTNode instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) aSTNode;
                if ((typeDeclaration.modifiers & 16384) == 0) {
                    if (typeDeclaration.declarationSourceEnd == 0) {
                        recoveredInitializer = recoveredInitializer.add(typeDeclaration, 0);
                        this.lastCheckPoint = typeDeclaration.bodyStart;
                    } else {
                        recoveredInitializer = recoveredInitializer.add(typeDeclaration, 0);
                        this.lastCheckPoint = typeDeclaration.declarationSourceEnd + 1;
                    }
                }
            } else {
                if (aSTNode instanceof ImportReference) {
                    ImportReference importReference = (ImportReference) aSTNode;
                    recoveredInitializer = recoveredInitializer.add(importReference, 0);
                    this.lastCheckPoint = importReference.declarationSourceEnd + 1;
                }
                if (this.statementRecoveryActivated) {
                    if (aSTNode instanceof Block) {
                        Block block = (Block) aSTNode;
                        recoveredInitializer = recoveredInitializer.add(block, 0);
                        this.lastCheckPoint = block.sourceEnd + 1;
                    } else if (aSTNode instanceof LocalDeclaration) {
                        LocalDeclaration localDeclaration = (LocalDeclaration) aSTNode;
                        recoveredInitializer = recoveredInitializer.add(localDeclaration, 0);
                        this.lastCheckPoint = localDeclaration.sourceEnd + 1;
                    } else if (aSTNode instanceof Expression) {
                        if ((aSTNode instanceof Assignment) || (aSTNode instanceof PrefixExpression) || (aSTNode instanceof PostfixExpression) || (aSTNode instanceof MessageSend) || (aSTNode instanceof AllocationExpression)) {
                            Expression expression = (Expression) aSTNode;
                            recoveredInitializer = recoveredInitializer.add(expression, 0);
                            if (expression.statementEnd != -1) {
                                this.lastCheckPoint = expression.statementEnd + 1;
                            } else {
                                this.lastCheckPoint = expression.sourceEnd + 1;
                            }
                        }
                    } else if (aSTNode instanceof Statement) {
                        Statement statement = (Statement) aSTNode;
                        recoveredInitializer = recoveredInitializer.add(statement, 0);
                        this.lastCheckPoint = statement.sourceEnd + 1;
                    }
                }
            }
        }
        if (!this.statementRecoveryActivated || this.pendingRecoveredType == null || this.scanner.startPosition - 1 > this.pendingRecoveredType.declarationSourceEnd) {
            return recoveredInitializer;
        }
        RecoveredElement recoveredElementAdd = recoveredInitializer.add(this.pendingRecoveredType, 0);
        this.lastCheckPoint = this.pendingRecoveredType.declarationSourceEnd + 1;
        this.pendingRecoveredType = null;
        return recoveredElementAdd;
    }

    protected void checkAndSetModifiers(int i) {
        int i2 = this.modifiers;
        if ((i2 & i) != 0) {
            this.modifiers = i2 | 4194304;
        }
        this.modifiers |= i;
        if (this.modifiersSourceStart < 0) {
            this.modifiersSourceStart = this.scanner.startPosition;
        }
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            recoveredElement.addModifier(i, this.modifiersSourceStart);
        }
    }

    public void checkComment() {
        if ((!this.diet || this.dietInt != 0) && this.scanner.commentPtr >= 0) {
            flushCommentsDefinedPriorTo(this.endStatementPosition);
        }
        int i = this.scanner.commentPtr;
        if (this.modifiersSourceStart >= 0) {
            while (i >= 0) {
                int i2 = this.scanner.commentStarts[i];
                if (i2 < 0) {
                    i2 = -i2;
                }
                if (i2 <= this.modifiersSourceStart) {
                    break;
                } else {
                    i--;
                }
            }
        }
        if (i >= 0) {
            int i3 = this.scanner.commentStarts[0];
            if (i3 < 0) {
                i3 = -i3;
            }
            int i4 = this.forStartPosition;
            if (i4 != 0 || i4 < i3) {
                this.modifiersSourceStart = i3;
            }
            while (i >= 0 && this.scanner.commentStops[i] < 0) {
                i--;
            }
            if (i < 0 || this.javadocParser == null) {
                return;
            }
            int i5 = this.scanner.commentStops[i] - 1;
            if (this.javadocParser.shouldReportProblems) {
                this.javadocParser.reportProblems = this.currentElement == null || i5 > this.lastJavadocEnd;
            } else {
                this.javadocParser.reportProblems = false;
            }
            if (this.javadocParser.checkDeprecation(i)) {
                checkAndSetModifiers(1048576);
            }
            this.javadoc = this.javadocParser.docComment;
            if (this.currentElement == null) {
                this.lastJavadocEnd = i5;
            }
        }
    }

    protected void checkNonNLSAfterBodyEnd(int i) {
        if (this.scanner.currentPosition - 1 <= i) {
            Scanner scanner = this.scanner;
            if (i < Integer.MAX_VALUE) {
                i++;
            }
            scanner.eofPosition = i;
            do {
                try {
                } catch (InvalidInputException unused) {
                    return;
                }
            } while (this.scanner.getNextToken() != 60);
        }
    }

    protected void classInstanceCreation(boolean z) {
        AllocationExpression allocationExpression;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i3 = this.astPtr;
            if (aSTNodeArr[i3] == null) {
                this.astPtr = i3 - 1;
                if (z) {
                    allocationExpression = new QualifiedAllocationExpression();
                } else {
                    allocationExpression = new AllocationExpression();
                }
                allocationExpression.sourceEnd = this.endPosition;
                int[] iArr2 = this.expressionLengthStack;
                int i4 = this.expressionLengthPtr;
                this.expressionLengthPtr = i4 - 1;
                int i5 = iArr2[i4];
                if (i5 != 0) {
                    int i6 = this.expressionPtr - i5;
                    this.expressionPtr = i6;
                    Expression[] expressionArr = new Expression[i5];
                    allocationExpression.arguments = expressionArr;
                    System.arraycopy(this.expressionStack, i6 + 1, expressionArr, 0, i5);
                }
                allocationExpression.type = getTypeReference(0);
                checkForDiamond(allocationExpression.type);
                int[] iArr3 = this.intStack;
                int i7 = this.intPtr;
                this.intPtr = i7 - 1;
                allocationExpression.sourceStart = iArr3[i7];
                pushOnExpressionStack(allocationExpression);
                return;
            }
        }
        dispatchDeclarationInto(i2);
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        typeDeclaration.declarationSourceEnd = this.endStatementPosition;
        typeDeclaration.bodyEnd = this.endStatementPosition;
        if (typeDeclaration.allocation != null) {
            typeDeclaration.allocation.sourceEnd = this.endStatementPosition;
            checkForDiamond(typeDeclaration.allocation.type);
        }
        if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
            typeDeclaration.bits |= 8;
        }
        this.astPtr--;
        this.astLengthPtr--;
    }

    protected void checkForDiamond(TypeReference typeReference) {
        if (typeReference instanceof ParameterizedSingleTypeReference) {
            ParameterizedSingleTypeReference parameterizedSingleTypeReference = (ParameterizedSingleTypeReference) typeReference;
            if (parameterizedSingleTypeReference.typeArguments == TypeReference.NO_TYPE_ARGUMENTS) {
                if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
                    problemReporter().diamondNotBelow17(typeReference);
                }
                if (this.options.sourceLevel > ClassFileConstants.JDK1_4) {
                    parameterizedSingleTypeReference.bits |= 524288;
                    return;
                }
                return;
            }
            return;
        }
        if (typeReference instanceof ParameterizedQualifiedTypeReference) {
            ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) typeReference;
            if (parameterizedQualifiedTypeReference.typeArguments[parameterizedQualifiedTypeReference.typeArguments.length - 1] == TypeReference.NO_TYPE_ARGUMENTS) {
                if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
                    problemReporter().diamondNotBelow17(typeReference, parameterizedQualifiedTypeReference.typeArguments.length - 1);
                }
                if (this.options.sourceLevel > ClassFileConstants.JDK1_4) {
                    parameterizedQualifiedTypeReference.bits |= 524288;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00b7  */
    protected ParameterizedQualifiedTypeReference computeQualifiedGenericsFromRightSide(TypeReference typeReference, int i, Annotation[][] annotationArr) {
        int i2;
        Annotation[][] annotationArr2;
        int i3 = this.identifierLengthStack[this.identifierLengthPtr];
        boolean z = typeReference instanceof ParameterizedSingleTypeReference;
        if (z || (typeReference instanceof SingleTypeReference)) {
            i2 = i3 + 1;
        } else if ((typeReference instanceof ParameterizedQualifiedTypeReference) || (typeReference instanceof QualifiedTypeReference)) {
            int length = ((QualifiedTypeReference) typeReference).tokens.length;
            i2 = length + i3;
        } else {
            i2 = i3;
        }
        TypeReference[][] typeReferenceArr = new TypeReference[i2][];
        char[][] cArr = new char[i2][];
        long[] jArr = new long[i2];
        if (z) {
            ParameterizedSingleTypeReference parameterizedSingleTypeReference = (ParameterizedSingleTypeReference) typeReference;
            cArr[i3] = parameterizedSingleTypeReference.token;
            jArr[i3] = (((long) parameterizedSingleTypeReference.sourceStart) << 32) + ((long) parameterizedSingleTypeReference.sourceEnd);
            typeReferenceArr[i3] = parameterizedSingleTypeReference.typeArguments;
            if (parameterizedSingleTypeReference.annotations != null) {
                annotationArr2 = new Annotation[i2][];
                annotationArr2[i3] = parameterizedSingleTypeReference.annotations[0];
            } else {
                annotationArr2 = null;
            }
        } else if (typeReference instanceof SingleTypeReference) {
            SingleTypeReference singleTypeReference = (SingleTypeReference) typeReference;
            cArr[i3] = singleTypeReference.token;
            jArr[i3] = (((long) singleTypeReference.sourceStart) << 32) + ((long) singleTypeReference.sourceEnd);
            if (singleTypeReference.annotations != null) {
                annotationArr2 = new Annotation[i2][];
                annotationArr2[i3] = singleTypeReference.annotations[0];
            } else {
                annotationArr2 = null;
            }
        } else if (typeReference instanceof ParameterizedQualifiedTypeReference) {
            ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) typeReference;
            TypeReference[][] typeReferenceArr2 = parameterizedQualifiedTypeReference.typeArguments;
            System.arraycopy(typeReferenceArr2, 0, typeReferenceArr, i3, typeReferenceArr2.length);
            char[][] cArr2 = parameterizedQualifiedTypeReference.tokens;
            System.arraycopy(cArr2, 0, cArr, i3, cArr2.length);
            long[] jArr2 = parameterizedQualifiedTypeReference.sourcePositions;
            System.arraycopy(jArr2, 0, jArr, i3, jArr2.length);
            Annotation[][] annotationArr3 = parameterizedQualifiedTypeReference.annotations;
            if (annotationArr3 != null) {
                annotationArr2 = new Annotation[i2][];
                System.arraycopy(annotationArr3, 0, annotationArr2, i3, annotationArr3.length);
            } else {
                annotationArr2 = null;
            }
        } else if (typeReference instanceof QualifiedTypeReference) {
            QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) typeReference;
            char[][] cArr3 = qualifiedTypeReference.tokens;
            System.arraycopy(cArr3, 0, cArr, i3, cArr3.length);
            long[] jArr3 = qualifiedTypeReference.sourcePositions;
            System.arraycopy(jArr3, 0, jArr, i3, jArr3.length);
            Annotation[][] annotationArr4 = qualifiedTypeReference.annotations;
            if (annotationArr4 != null) {
                annotationArr2 = new Annotation[i2][];
                System.arraycopy(annotationArr4, 0, annotationArr2, i3, annotationArr4.length);
            } else {
                annotationArr2 = null;
            }
        } else {
            annotationArr2 = null;
        }
        int[] iArr = this.genericsLengthStack;
        int i4 = this.genericsLengthPtr;
        this.genericsLengthPtr = i4 - 1;
        int i5 = iArr[i4];
        TypeReference[] typeReferenceArr3 = new TypeReference[i5];
        int i6 = this.genericsPtr - i5;
        this.genericsPtr = i6;
        System.arraycopy(this.genericsStack, i6 + 1, typeReferenceArr3, 0, i5);
        if (i3 == 1) {
            char[][] cArr4 = this.identifierStack;
            int i7 = this.identifierPtr;
            cArr[0] = cArr4[i7];
            long[] jArr4 = this.identifierPositionStack;
            this.identifierPtr = i7 - 1;
            jArr[0] = jArr4[i7];
            typeReferenceArr[0] = typeReferenceArr3;
        } else {
            int i8 = this.identifierPtr - i3;
            this.identifierPtr = i8;
            System.arraycopy(this.identifierStack, i8 + 1, cArr, 0, i3);
            System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr, 0, i3);
            typeReferenceArr[i3 - 1] = typeReferenceArr3;
        }
        this.identifierLengthPtr--;
        ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference2 = new ParameterizedQualifiedTypeReference(cArr, typeReferenceArr, i, annotationArr, jArr);
        while (i3 > 0) {
            int[] iArr2 = this.typeAnnotationLengthStack;
            int i9 = this.typeAnnotationLengthPtr;
            this.typeAnnotationLengthPtr = i9 - 1;
            int i10 = iArr2[i9];
            if (i10 != 0) {
                if (annotationArr2 == null) {
                    annotationArr2 = new Annotation[i2][];
                }
                Annotation[] annotationArr5 = this.typeAnnotationStack;
                int i11 = this.typeAnnotationPtr - i10;
                this.typeAnnotationPtr = i11;
                Annotation[] annotationArr6 = new Annotation[i10];
                annotationArr2[i3 - 1] = annotationArr6;
                System.arraycopy(annotationArr5, i11 + 1, annotationArr6, 0, i10);
                if (i3 == 1) {
                    parameterizedQualifiedTypeReference2.sourceStart = annotationArr2[0][0].sourceStart;
                }
            }
            i3--;
        }
        parameterizedQualifiedTypeReference2.annotations = annotationArr2;
        if (annotationArr2 != null) {
            parameterizedQualifiedTypeReference2.bits |= 1048576;
        }
        return parameterizedQualifiedTypeReference2;
    }

    protected void concatExpressionLists() {
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr - 1;
        this.expressionLengthPtr = i;
        iArr[i] = iArr[i] + 1;
    }

    protected void concatGenericsLists() {
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        int i2 = i - 1;
        int i3 = iArr[i2];
        this.genericsLengthPtr = i - 1;
        iArr[i2] = i3 + iArr[i];
    }

    protected void concatNodeLists() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        int i2 = i - 1;
        int i3 = iArr[i2];
        this.astLengthPtr = i - 1;
        iArr[i2] = i3 + iArr[i];
    }

    protected void consumeAdditionalBound() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnGenericsStack(getTypeReference(iArr[i]));
    }

    protected void consumeAdditionalBoundList() {
        concatGenericsLists();
    }

    protected void consumeAdditionalBoundList1() {
        concatGenericsLists();
    }

    protected void consumeAllocationHeader() {
        if (this.currentElement == null) {
            return;
        }
        if (this.currentToken == 49) {
            TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
            typeDeclaration.f619name = CharOperation.NO_CHAR;
            typeDeclaration.bits |= 768;
            int[] iArr = this.intStack;
            int i = this.intPtr;
            this.intPtr = i - 1;
            typeDeclaration.sourceStart = iArr[i];
            typeDeclaration.declarationSourceStart = typeDeclaration.sourceStart;
            typeDeclaration.sourceEnd = this.rParenPos;
            QualifiedAllocationExpression qualifiedAllocationExpression = new QualifiedAllocationExpression(typeDeclaration);
            qualifiedAllocationExpression.type = getTypeReference(0);
            qualifiedAllocationExpression.sourceStart = typeDeclaration.sourceStart;
            qualifiedAllocationExpression.sourceEnd = typeDeclaration.sourceEnd;
            int i2 = this.scanner.currentPosition;
            typeDeclaration.bodyStart = i2;
            this.lastCheckPoint = i2;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
            if (isIndirectlyInsideLambdaExpression()) {
                this.ignoreNextOpeningBrace = true;
                return;
            } else {
                this.currentToken = 0;
                return;
            }
        }
        this.lastCheckPoint = this.scanner.startPosition;
        this.restartRecovery = true;
    }

    protected void consumeAnnotationAsModifier() {
        int i = this.expressionStack[this.expressionPtr].sourceStart;
        if (this.modifiersSourceStart < 0) {
            this.modifiersSourceStart = i;
        }
    }

    protected void consumeAnnotationName() {
        if (this.currentElement != null && !this.expectTypeAnnotation) {
            int i = this.intStack[this.intPtr];
            annotationRecoveryCheckPoint(i, (int) (this.identifierPositionStack[this.identifierPtr] & 4294967295L));
            if (this.annotationRecoveryActivated) {
                this.currentElement = this.currentElement.addAnnotationName(this.identifierPtr, this.identifierLengthPtr, i, 0);
            }
        }
        this.recordStringLiterals = false;
        this.expectTypeAnnotation = false;
    }

    protected void consumeAnnotationTypeDeclaration() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            dispatchDeclarationInto(i2);
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        typeDeclaration.checkConstructors(this);
        if (this.scanner.containsAssertKeyword) {
            typeDeclaration.bits |= 1;
        }
        typeDeclaration.addClinit();
        typeDeclaration.bodyEnd = this.endStatementPosition;
        if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
            typeDeclaration.bits |= 8;
        }
        typeDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeAnnotationTypeDeclarationHeader() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            typeDeclaration.bodyStart = this.scanner.currentPosition;
        }
        if (this.currentElement != null) {
            this.restartRecovery = true;
        }
        this.scanner.commentPtr = -1;
    }

    protected void consumeAnnotationTypeDeclarationHeaderName() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        if (iArr[i] != 0) {
            typeDeclaration.bits |= 256;
            markEnclosingMemberWithLocalType();
            blockReal();
        } else if (i != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        typeDeclaration.f619name = cArr[i2];
        this.identifierLengthPtr--;
        int i3 = this.intPtr;
        int[] iArr2 = this.intStack;
        this.intPtr = i3 - 3;
        typeDeclaration.modifiersSourceStart = iArr2[i3 - 2];
        int[] iArr3 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        typeDeclaration.modifiers = iArr3[i4] | 8704;
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
            this.intPtr--;
        } else {
            int[] iArr4 = this.intStack;
            int i5 = this.intPtr;
            this.intPtr = i5 - 1;
            typeDeclaration.declarationSourceStart = iArr4[i5];
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr5 = this.expressionLengthStack;
        int i6 = this.expressionLengthPtr;
        this.expressionLengthPtr = i6 - 1;
        int i7 = iArr5[i6];
        if (i7 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i8 = this.expressionPtr - i7;
            this.expressionPtr = i8;
            Annotation[] annotationArr = new Annotation[i7];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i8 + 1, annotationArr, 0, i7);
        }
        typeDeclaration.bodyStart = typeDeclaration.sourceEnd + 1;
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        pushOnAstStack(typeDeclaration);
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfAnnotationDeclarations(typeDeclaration);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeAnnotationTypeDeclarationHeaderNameWithTypeParameters() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        TypeParameter[] typeParameterArr = new TypeParameter[i2];
        typeDeclaration.typeParameters = typeParameterArr;
        System.arraycopy(this.genericsStack, i3 + 1, typeParameterArr, 0, i2);
        problemReporter().invalidUsageOfTypeParametersForAnnotationDeclaration(typeDeclaration);
        typeDeclaration.bodyStart = typeDeclaration.typeParameters[i2 - 1].declarationSourceEnd + 1;
        this.listTypeParameterLength = 0;
        int[] iArr2 = this.nestedMethod;
        int i4 = this.nestedType;
        if (iArr2[i4] != 0) {
            typeDeclaration.bits |= 256;
            markEnclosingMemberWithLocalType();
            blockReal();
        } else if (i4 != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i5 = this.identifierPtr;
        this.identifierPtr = i5 - 1;
        typeDeclaration.f619name = cArr[i5];
        this.identifierLengthPtr--;
        int i6 = this.intPtr;
        int[] iArr3 = this.intStack;
        this.intPtr = i6 - 3;
        typeDeclaration.modifiersSourceStart = iArr3[i6 - 2];
        int[] iArr4 = this.intStack;
        int i7 = this.intPtr;
        this.intPtr = i7 - 1;
        typeDeclaration.modifiers = iArr4[i7] | 8704;
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
            this.intPtr--;
        } else {
            int[] iArr5 = this.intStack;
            int i8 = this.intPtr;
            this.intPtr = i8 - 1;
            typeDeclaration.declarationSourceStart = iArr5[i8];
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr6 = this.expressionLengthStack;
        int i9 = this.expressionLengthPtr;
        this.expressionLengthPtr = i9 - 1;
        int i10 = iArr6[i9];
        if (i10 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i11 = this.expressionPtr - i10;
            this.expressionPtr = i11;
            Annotation[] annotationArr = new Annotation[i10];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i11 + 1, annotationArr, 0, i10);
        }
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        pushOnAstStack(typeDeclaration);
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfAnnotationDeclarations(typeDeclaration);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeAnnotationTypeMemberDeclaration() {
        AnnotationMethodDeclaration annotationMethodDeclaration = (AnnotationMethodDeclaration) this.astStack[this.astPtr];
        annotationMethodDeclaration.modifiers |= 16777216;
        int iFlushCommentsDefinedPriorTo = flushCommentsDefinedPriorTo(this.endStatementPosition);
        annotationMethodDeclaration.bodyStart = this.endStatementPosition;
        annotationMethodDeclaration.bodyEnd = iFlushCommentsDefinedPriorTo;
        annotationMethodDeclaration.declarationSourceEnd = iFlushCommentsDefinedPriorTo;
    }

    protected void consumeAnnotationTypeMemberDeclarations() {
        concatNodeLists();
    }

    protected void consumeAnnotationTypeMemberDeclarationsopt() {
        this.nestedType--;
    }

    protected void consumeArgumentList() {
        concatExpressionLists();
    }

    protected void consumeArguments() {
        pushOnIntStack(this.rParenPos);
    }

    protected void consumeArrayAccess(boolean z) {
        ArrayReference arrayReference;
        if (z) {
            Expression[] expressionArr = this.expressionStack;
            int i = this.expressionPtr;
            arrayReference = new ArrayReference(getUnspecifiedReferenceOptimized(), this.expressionStack[this.expressionPtr]);
            expressionArr[i] = arrayReference;
        } else {
            int i2 = this.expressionPtr - 1;
            this.expressionPtr = i2;
            this.expressionLengthPtr--;
            Expression[] expressionArr2 = this.expressionStack;
            Expression[] expressionArr3 = this.expressionStack;
            int i3 = this.expressionPtr;
            arrayReference = new ArrayReference(expressionArr3[i3], expressionArr3[i3 + 1]);
            expressionArr2[i2] = arrayReference;
        }
        arrayReference.sourceEnd = this.endStatementPosition;
    }

    protected void consumeArrayCreationExpressionWithInitializer() {
        ArrayAllocationExpression arrayAllocationExpression = new ArrayAllocationExpression();
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        arrayAllocationExpression.initializer = (ArrayInitializer) expressionArr[i];
        int[] iArr = this.expressionLengthStack;
        int i2 = this.expressionLengthPtr;
        this.expressionLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.expressionPtr - i3;
        this.expressionPtr = i4;
        Expression[] expressionArr2 = new Expression[i3];
        arrayAllocationExpression.dimensions = expressionArr2;
        System.arraycopy(this.expressionStack, i4 + 1, expressionArr2, 0, i3);
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(i3);
        arrayAllocationExpression.annotationsOnDimensions = annotationsOnDimensions;
        arrayAllocationExpression.type = getTypeReference(0);
        arrayAllocationExpression.type.bits |= 1073741824;
        if (annotationsOnDimensions != null) {
            arrayAllocationExpression.bits |= 1048576;
            TypeReference typeReference = arrayAllocationExpression.type;
            typeReference.bits = 1048576 | typeReference.bits;
        }
        int[] iArr2 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        arrayAllocationExpression.sourceStart = iArr2[i5];
        if (arrayAllocationExpression.initializer == null) {
            arrayAllocationExpression.sourceEnd = this.endStatementPosition;
        } else {
            arrayAllocationExpression.sourceEnd = arrayAllocationExpression.initializer.sourceEnd;
        }
        pushOnExpressionStack(arrayAllocationExpression);
    }

    protected void consumeArrayCreationExpressionWithoutInitializer() {
        ArrayAllocationExpression arrayAllocationExpression = new ArrayAllocationExpression();
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.expressionPtr - i2;
        this.expressionPtr = i3;
        Expression[] expressionArr = new Expression[i2];
        arrayAllocationExpression.dimensions = expressionArr;
        System.arraycopy(this.expressionStack, i3 + 1, expressionArr, 0, i2);
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(i2);
        arrayAllocationExpression.annotationsOnDimensions = annotationsOnDimensions;
        arrayAllocationExpression.type = getTypeReference(0);
        arrayAllocationExpression.type.bits |= 1073741824;
        if (annotationsOnDimensions != null) {
            arrayAllocationExpression.bits |= 1048576;
            TypeReference typeReference = arrayAllocationExpression.type;
            typeReference.bits = 1048576 | typeReference.bits;
        }
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        arrayAllocationExpression.sourceStart = iArr2[i4];
        if (arrayAllocationExpression.initializer == null) {
            arrayAllocationExpression.sourceEnd = this.endStatementPosition;
        } else {
            arrayAllocationExpression.sourceEnd = arrayAllocationExpression.initializer.sourceEnd;
        }
        pushOnExpressionStack(arrayAllocationExpression);
    }

    protected void consumeArrayInitializer() {
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        arrayInitializer(iArr[i]);
    }

    protected void consumeArrayTypeWithTypeArgumentsName() {
        int[] iArr = this.genericsIdentifiersLengthStack;
        int i = this.genericsIdentifiersLengthPtr;
        iArr[i] = iArr[i] + this.identifierLengthStack[this.identifierLengthPtr];
        pushOnGenericsLengthStack(0);
    }

    protected void consumeAssertStatement() {
        this.expressionLengthPtr -= 2;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        int i2 = i - 1;
        this.expressionPtr = i2;
        Expression expression = expressionArr[i];
        this.expressionPtr = i - 2;
        Expression expression2 = expressionArr[i2];
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        pushOnAstStack(new AssertStatement(expression, expression2, iArr[i3]));
    }

    protected void consumeAssignment() {
        Expression assignment;
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.expressionPtr;
        int i4 = i3 - 1;
        this.expressionPtr = i4;
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        Expression expression = expressionArr[i3];
        if (i2 != 30) {
            assignment = new CompoundAssignment(this.expressionStack[this.expressionPtr], expression, i2, expression.sourceEnd);
        } else {
            assignment = new Assignment(this.expressionStack[this.expressionPtr], expression, expression.sourceEnd);
        }
        expressionArr[i4] = assignment;
        TypeDeclaration typeDeclaration = this.pendingRecoveredType;
        if (typeDeclaration != null) {
            if (typeDeclaration.allocation != null && this.scanner.startPosition - 1 <= this.pendingRecoveredType.declarationSourceEnd) {
                this.expressionStack[this.expressionPtr] = this.pendingRecoveredType.allocation;
                this.pendingRecoveredType = null;
            } else {
                this.pendingRecoveredType = null;
            }
        }
    }

    protected void consumeAssignmentOperator(int i) {
        pushOnIntStack(i);
    }

    protected void consumeBinaryExpression(int i) {
        int i2 = this.expressionPtr;
        int i3 = i2 - 1;
        this.expressionPtr = i3;
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        Expression expression = expressionArr[i3];
        Expression expression2 = expressionArr[i2];
        if (i == 0) {
            expressionArr[i3] = new AND_AND_Expression(expression, expression2, i);
            return;
        }
        if (i == 1) {
            expressionArr[i3] = new OR_OR_Expression(expression, expression2, i);
            return;
        }
        if (i != 4) {
            if (i == 14) {
                if (this.optimizeStringLiterals) {
                    if (expression instanceof StringLiteral) {
                        if (((expression.bits & ASTNode.ParenthesizedMASK) >> 21) == 0) {
                            if (expression2 instanceof CharLiteral) {
                                this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendWith((CharLiteral) expression2);
                                return;
                            } else if (expression2 instanceof StringLiteral) {
                                this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendWith((StringLiteral) expression2);
                                return;
                            } else {
                                this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                                return;
                            }
                        }
                        this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                        return;
                    }
                    if (expression instanceof CombinedBinaryExpression) {
                        CombinedBinaryExpression combinedBinaryExpression = (CombinedBinaryExpression) expression;
                        if (combinedBinaryExpression.arity < combinedBinaryExpression.arityMax) {
                            combinedBinaryExpression.left = new BinaryExpression(combinedBinaryExpression);
                            combinedBinaryExpression.arity++;
                        } else {
                            combinedBinaryExpression.left = new CombinedBinaryExpression(combinedBinaryExpression);
                            combinedBinaryExpression.arity = 0;
                            combinedBinaryExpression.tuneArityMax();
                        }
                        combinedBinaryExpression.right = expression2;
                        combinedBinaryExpression.sourceEnd = expression2.sourceEnd;
                        this.expressionStack[this.expressionPtr] = combinedBinaryExpression;
                        return;
                    }
                    if ((expression instanceof BinaryExpression) && ((expression.bits & ASTNode.OperatorMASK) >> 6) == 14) {
                        this.expressionStack[this.expressionPtr] = new CombinedBinaryExpression(expression, expression2, 14, 1);
                        return;
                    } else {
                        this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                        return;
                    }
                }
                if (expression instanceof StringLiteral) {
                    if ((expression2 instanceof StringLiteral) && ((expression.bits & ASTNode.ParenthesizedMASK) >> 21) == 0) {
                        this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendsWith((StringLiteral) expression2);
                        return;
                    } else {
                        this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                        return;
                    }
                }
                if (expression instanceof CombinedBinaryExpression) {
                    CombinedBinaryExpression combinedBinaryExpression2 = (CombinedBinaryExpression) expression;
                    if (combinedBinaryExpression2.arity < combinedBinaryExpression2.arityMax) {
                        combinedBinaryExpression2.left = new BinaryExpression(combinedBinaryExpression2);
                        combinedBinaryExpression2.bits &= -534773761;
                        combinedBinaryExpression2.arity++;
                    } else {
                        combinedBinaryExpression2.left = new CombinedBinaryExpression(combinedBinaryExpression2);
                        combinedBinaryExpression2.bits &= -534773761;
                        combinedBinaryExpression2.arity = 0;
                        combinedBinaryExpression2.tuneArityMax();
                    }
                    combinedBinaryExpression2.right = expression2;
                    combinedBinaryExpression2.sourceEnd = expression2.sourceEnd;
                    this.expressionStack[this.expressionPtr] = combinedBinaryExpression2;
                    return;
                }
                if ((expression instanceof BinaryExpression) && ((expression.bits & ASTNode.OperatorMASK) >> 6) == 14) {
                    this.expressionStack[this.expressionPtr] = new CombinedBinaryExpression(expression, expression2, 14, 1);
                    return;
                } else {
                    this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                    return;
                }
            }
            if (i != 15) {
                expressionArr[i3] = new BinaryExpression(expression, expression2, i);
                return;
            }
        }
        this.intPtr--;
        expressionArr[i3] = new BinaryExpression(expression, expression2, i);
    }

    protected void consumeBinaryExpressionWithName(int i) {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
        int i2 = this.expressionPtr;
        int i3 = i2 - 1;
        this.expressionPtr = i3;
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        Expression expression = expressionArr[i2];
        Expression expression2 = expressionArr[i3];
        if (i == 0) {
            expressionArr[i3] = new AND_AND_Expression(expression, expression2, i);
            return;
        }
        if (i == 1) {
            expressionArr[i3] = new OR_OR_Expression(expression, expression2, i);
            return;
        }
        if (i != 4) {
            if (i == 14) {
                if (this.optimizeStringLiterals) {
                    if ((expression instanceof StringLiteral) && ((expression.bits & ASTNode.ParenthesizedMASK) >> 21) == 0) {
                        if (expression2 instanceof CharLiteral) {
                            this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendWith((CharLiteral) expression2);
                            return;
                        } else if (expression2 instanceof StringLiteral) {
                            this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendWith((StringLiteral) expression2);
                            return;
                        } else {
                            this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                            return;
                        }
                    }
                    this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, 14);
                    return;
                }
                if (expression instanceof StringLiteral) {
                    if ((expression2 instanceof StringLiteral) && ((expression.bits & ASTNode.ParenthesizedMASK) >> 21) == 0) {
                        this.expressionStack[this.expressionPtr] = ((StringLiteral) expression).extendsWith((StringLiteral) expression2);
                        return;
                    } else {
                        this.expressionStack[this.expressionPtr] = new BinaryExpression(expression, expression2, i);
                        return;
                    }
                }
                expressionArr[i3] = new BinaryExpression(expression, expression2, i);
                return;
            }
            if (i != 15) {
                expressionArr[i3] = new BinaryExpression(expression, expression2, i);
                return;
            }
        }
        this.intPtr--;
        expressionArr[i3] = new BinaryExpression(expression, expression2, i);
    }

    protected void consumeBlock() {
        Block block;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 0) {
            block = new Block(0);
            int[] iArr2 = this.intStack;
            int i3 = this.intPtr;
            this.intPtr = i3 - 1;
            block.sourceStart = iArr2[i3];
            block.sourceEnd = this.endStatementPosition;
            if (!containsComment(block.sourceStart, block.sourceEnd)) {
                block.bits |= 8;
            }
            this.realBlockPtr--;
        } else {
            int[] iArr3 = this.realBlockStack;
            int i4 = this.realBlockPtr;
            this.realBlockPtr = i4 - 1;
            Block block2 = new Block(iArr3[i4]);
            int i5 = this.astPtr - i2;
            this.astPtr = i5;
            Statement[] statementArr = new Statement[i2];
            block2.statements = statementArr;
            System.arraycopy(this.astStack, i5 + 1, statementArr, 0, i2);
            int[] iArr4 = this.intStack;
            int i6 = this.intPtr;
            this.intPtr = i6 - 1;
            block2.sourceStart = iArr4[i6];
            block2.sourceEnd = this.endStatementPosition;
            block = block2;
        }
        pushOnAstStack(block);
    }

    protected void consumeBlockStatements() {
        concatNodeLists();
    }

    protected void consumeCaseLabel() {
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        Expression expression = expressionArr[i];
        int i2 = expression.sourceEnd;
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        CaseStatement caseStatement = new CaseStatement(expression, i2, iArr[i3]);
        if (hasLeadingTagComment(FALL_THROUGH_TAG, caseStatement.sourceStart)) {
            caseStatement.bits |= 536870912;
        }
        pushOnAstStack(caseStatement);
    }

    protected void consumeCastExpressionLL1() {
        int i = this.expressionPtr - 1;
        this.expressionPtr = i;
        Expression[] expressionArr = this.expressionStack;
        Expression[] expressionArr2 = this.expressionStack;
        int i2 = this.expressionPtr;
        Expression expression = expressionArr2[i2 + 1];
        CastExpression castExpression = new CastExpression(expression, (TypeReference) expressionArr2[i2]);
        expressionArr[i] = castExpression;
        this.expressionLengthPtr--;
        updateSourcePosition(castExpression);
        castExpression.sourceEnd = expression.sourceEnd;
    }

    public IntersectionCastTypeReference createIntersectionCastTypeReference(TypeReference[] typeReferenceArr) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_8) {
            problemReporter().intersectionCastNotBelow18(typeReferenceArr);
        }
        return new IntersectionCastTypeReference(typeReferenceArr);
    }

    protected void consumeCastExpressionLL1WithBounds() {
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        int i2 = i - 1;
        this.expressionPtr = i2;
        Expression expression = expressionArr[i];
        int i3 = this.expressionLengthPtr - 1;
        this.expressionLengthPtr = i3;
        int i4 = this.expressionLengthStack[i3];
        TypeReference[] typeReferenceArr = new TypeReference[i4];
        int i5 = i2 - (i4 - 1);
        this.expressionPtr = i5;
        System.arraycopy(expressionArr, i5, typeReferenceArr, 0, i4);
        Expression[] expressionArr2 = this.expressionStack;
        int i6 = this.expressionPtr;
        CastExpression castExpression = new CastExpression(expression, createIntersectionCastTypeReference(typeReferenceArr));
        expressionArr2[i6] = castExpression;
        this.expressionLengthStack[this.expressionLengthPtr] = 1;
        updateSourcePosition(castExpression);
        castExpression.sourceEnd = expression.sourceEnd;
    }

    protected void consumeCastExpressionWithGenericsArray() {
        TypeReference[] typeReferenceArr;
        TypeReference typeReference;
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 > 0) {
            typeReferenceArr = new TypeReference[i2 + 1];
            int i3 = this.genericsPtr - i2;
            this.genericsPtr = i3;
            System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 1, i2);
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        int i5 = i4 - 1;
        this.intPtr = i5;
        int i6 = iArr2[i4];
        this.intPtr = i4 - 2;
        int i7 = iArr2[i5];
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        if (i2 > 0) {
            typeReferenceArr[0] = getTypeReference(i7);
            typeReference = createIntersectionCastTypeReference(typeReferenceArr);
        } else {
            typeReference = getTypeReference(i7);
        }
        Expression[] expressionArr = this.expressionStack;
        int i8 = this.expressionPtr;
        Expression expression = this.expressionStack[this.expressionPtr];
        CastExpression castExpression = new CastExpression(expression, typeReference);
        expressionArr[i8] = castExpression;
        this.intPtr--;
        typeReference.sourceEnd = i6 - 1;
        int[] iArr3 = this.intStack;
        int i9 = this.intPtr;
        this.intPtr = i9 - 1;
        int i10 = iArr3[i9];
        castExpression.sourceStart = i10;
        typeReference.sourceStart = i10 + 1;
        castExpression.sourceEnd = expression.sourceEnd;
    }

    protected void consumeCastExpressionWithNameArray() {
        TypeReference[] typeReferenceArr;
        TypeReference typeReference;
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        int[] iArr2 = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr2[i3];
        if (i4 > 0) {
            typeReferenceArr = new TypeReference[i4 + 1];
            int i5 = this.genericsPtr - i4;
            this.genericsPtr = i5;
            System.arraycopy(this.genericsStack, i5 + 1, typeReferenceArr, 1, i4);
        } else {
            typeReferenceArr = null;
        }
        pushOnGenericsLengthStack(0);
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        if (i4 > 0) {
            int[] iArr3 = this.intStack;
            int i6 = this.intPtr;
            this.intPtr = i6 - 1;
            typeReferenceArr[0] = getTypeReference(iArr3[i6]);
            typeReference = createIntersectionCastTypeReference(typeReferenceArr);
        } else {
            int[] iArr4 = this.intStack;
            int i7 = this.intPtr;
            this.intPtr = i7 - 1;
            typeReference = getTypeReference(iArr4[i7]);
        }
        Expression[] expressionArr = this.expressionStack;
        int i8 = this.expressionPtr;
        Expression expression = this.expressionStack[this.expressionPtr];
        CastExpression castExpression = new CastExpression(expression, typeReference);
        expressionArr[i8] = castExpression;
        typeReference.sourceEnd = i2 - 1;
        int[] iArr5 = this.intStack;
        int i9 = this.intPtr;
        this.intPtr = i9 - 1;
        int i10 = iArr5[i9];
        castExpression.sourceStart = i10;
        typeReference.sourceStart = i10 + 1;
        castExpression.sourceEnd = expression.sourceEnd;
    }

    protected void consumeCastExpressionWithPrimitiveType() {
        TypeReference[] typeReferenceArr;
        TypeReference typeReference;
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 > 0) {
            typeReferenceArr = new TypeReference[i2 + 1];
            int i3 = this.genericsPtr - i2;
            this.genericsPtr = i3;
            System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 1, i2);
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        int i5 = i4 - 1;
        this.intPtr = i5;
        int i6 = iArr2[i4];
        if (i2 > 0) {
            this.intPtr = i4 - 2;
            typeReferenceArr[0] = getTypeReference(iArr2[i5]);
            typeReference = createIntersectionCastTypeReference(typeReferenceArr);
        } else {
            this.intPtr = i4 - 2;
            typeReference = getTypeReference(iArr2[i5]);
        }
        Expression[] expressionArr = this.expressionStack;
        int i7 = this.expressionPtr;
        Expression expression = this.expressionStack[this.expressionPtr];
        CastExpression castExpression = new CastExpression(expression, typeReference);
        expressionArr[i7] = castExpression;
        typeReference.sourceEnd = i6 - 1;
        int[] iArr3 = this.intStack;
        int i8 = this.intPtr;
        this.intPtr = i8 - 1;
        int i9 = iArr3[i8];
        castExpression.sourceStart = i9;
        typeReference.sourceStart = i9 + 1;
        castExpression.sourceEnd = expression.sourceEnd;
    }

    protected void consumeCastExpressionWithQualifiedGenericsArray() {
        TypeReference[] typeReferenceArr;
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 > 0) {
            typeReferenceArr = new TypeReference[i2 + 1];
            int i3 = this.genericsPtr - i2;
            this.genericsPtr = i3;
            System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 1, i2);
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        int i5 = i4 - 1;
        this.intPtr = i5;
        int i6 = iArr2[i4];
        this.intPtr = i4 - 2;
        int i7 = iArr2[i5];
        TypeReference typeReferenceComputeQualifiedGenericsFromRightSide = computeQualifiedGenericsFromRightSide(getTypeReference(0), i7, i7 != 0 ? getAnnotationsOnDimensions(i7) : null);
        if (i2 > 0) {
            typeReferenceArr[0] = typeReferenceComputeQualifiedGenericsFromRightSide;
            typeReferenceComputeQualifiedGenericsFromRightSide = createIntersectionCastTypeReference(typeReferenceArr);
        }
        this.intPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i8 = this.expressionPtr;
        Expression expression = this.expressionStack[this.expressionPtr];
        CastExpression castExpression = new CastExpression(expression, typeReferenceComputeQualifiedGenericsFromRightSide);
        expressionArr[i8] = castExpression;
        typeReferenceComputeQualifiedGenericsFromRightSide.sourceEnd = i6 - 1;
        int[] iArr3 = this.intStack;
        int i9 = this.intPtr;
        this.intPtr = i9 - 1;
        int i10 = iArr3[i9];
        castExpression.sourceStart = i10;
        typeReferenceComputeQualifiedGenericsFromRightSide.sourceStart = i10 + 1;
        castExpression.sourceEnd = expression.sourceEnd;
    }

    protected void consumeCatches() {
        optimizedConcatNodeLists();
    }

    protected void consumeCatchFormalParameter() {
        TypeReference typeReference;
        this.identifierLengthPtr--;
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        char[] cArr2 = cArr[i];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        int i3 = iArr[i2];
        ASTNode[] aSTNodeArr = this.astStack;
        int i4 = this.astPtr;
        this.astPtr = i4 - 1;
        TypeReference typeReference2 = (TypeReference) aSTNodeArr[i4];
        if (i3 > 0) {
            TypeReference typeReferenceAugmentTypeWithAdditionalDimensions = augmentTypeWithAdditionalDimensions(typeReference2, i3, null, false);
            typeReferenceAugmentTypeWithAdditionalDimensions.sourceEnd = this.endPosition;
            if (typeReferenceAugmentTypeWithAdditionalDimensions instanceof UnionTypeReference) {
                problemReporter().illegalArrayOfUnionType(cArr2, typeReferenceAugmentTypeWithAdditionalDimensions);
            }
            typeReference = typeReferenceAugmentTypeWithAdditionalDimensions;
        } else {
            typeReference = typeReference2;
        }
        this.astLengthPtr--;
        int[] iArr2 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        int i6 = iArr2[i5];
        this.intPtr = i5 - 2;
        Argument argument = new Argument(cArr2, j, typeReference, (-1048577) & this.intStack[this.intPtr + 1]);
        argument.bits &= -5;
        argument.declarationSourceStart = i6;
        int[] iArr3 = this.expressionLengthStack;
        int i7 = this.expressionLengthPtr;
        this.expressionLengthPtr = i7 - 1;
        int i8 = iArr3[i7];
        if (i8 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i9 = this.expressionPtr - i8;
            this.expressionPtr = i9;
            Annotation[] annotationArr = new Annotation[i8];
            argument.annotations = annotationArr;
            System.arraycopy(expressionArr, i9 + 1, annotationArr, 0, i8);
        }
        pushOnAstStack(argument);
        this.listLength++;
    }

    protected void consumeCatchHeader() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null) {
            return;
        }
        if (!(recoveredElement instanceof RecoveredBlock)) {
            if (!(recoveredElement instanceof RecoveredMethod)) {
                return;
            }
            RecoveredMethod recoveredMethod = (RecoveredMethod) recoveredElement;
            if (recoveredMethod.methodBody != null || recoveredMethod.bracketBalance <= 0) {
                return;
            }
        }
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        this.astPtr = i - 1;
        Argument argument = (Argument) aSTNodeArr[i];
        LocalDeclaration localDeclaration = new LocalDeclaration(argument.f617name, argument.sourceStart, argument.sourceEnd);
        localDeclaration.type = argument.type;
        localDeclaration.declarationSourceStart = argument.declarationSourceStart;
        localDeclaration.declarationSourceEnd = argument.declarationSourceEnd;
        this.currentElement = this.currentElement.add(localDeclaration, 0);
        this.lastCheckPoint = this.scanner.startPosition;
        this.restartRecovery = true;
        this.lastIgnoredToken = -1;
    }

    protected void consumeCatchType() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 1) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i3 = this.astPtr - i2;
            this.astPtr = i3;
            TypeReference[] typeReferenceArr = new TypeReference[i2];
            System.arraycopy(aSTNodeArr, i3 + 1, typeReferenceArr, 0, i2);
            UnionTypeReference unionTypeReference = new UnionTypeReference(typeReferenceArr);
            pushOnAstStack(unionTypeReference);
            if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
                problemReporter().multiCatchNotBelow17(unionTypeReference);
                return;
            }
            return;
        }
        pushOnAstLengthStack(1);
    }

    protected void consumeClassBodyDeclaration() {
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] - 1;
        ASTNode[] aSTNodeArr = this.astStack;
        int i2 = this.astPtr;
        this.astPtr = i2 - 1;
        Block block = (Block) aSTNodeArr[i2];
        this.astLengthPtr--;
        if (this.diet) {
            block.bits &= -9;
        }
        Initializer initializer = (Initializer) this.astStack[this.astPtr];
        int i3 = block.sourceStart;
        initializer.sourceStart = i3;
        initializer.declarationSourceStart = i3;
        initializer.block = block;
        int i4 = this.intPtr;
        int[] iArr2 = this.intStack;
        this.intPtr = i4 - 2;
        initializer.bodyStart = iArr2[i4 - 1];
        this.realBlockPtr--;
        int[] iArr3 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        int i6 = iArr3[i5];
        if (i6 != -1) {
            initializer.declarationSourceStart = i6;
            initializer.javadoc = this.javadoc;
            this.javadoc = null;
        }
        initializer.bodyEnd = this.endPosition;
        initializer.sourceEnd = this.endStatementPosition;
        initializer.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeClassBodyDeclarations() {
        concatNodeLists();
    }

    protected void consumeClassBodyDeclarationsopt() {
        this.nestedType--;
    }

    protected void consumeClassBodyopt() {
        pushOnAstStack(null);
        this.endPosition = this.rParenPos;
    }

    protected void consumeClassDeclaration() {
        int iKind;
        boolean z;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            dispatchDeclarationInto(i2);
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (!typeDeclaration.checkConstructors(this) && ((iKind = TypeDeclaration.kind(typeDeclaration.modifiers)) == 1 || iKind == 3)) {
            if (!this.diet) {
                z = false;
                break;
            }
            int i3 = this.nestedType;
            while (true) {
                if (i3 <= 0) {
                    z = false;
                    break;
                } else {
                    if (this.variablesCounter[i3] > 0) {
                        z = true;
                        break;
                    }
                    i3--;
                }
            }
            typeDeclaration.createDefaultConstructor((this.diet && this.dietInt == 0 && !z) ? false : true, true);
        }
        if (this.scanner.containsAssertKeyword) {
            typeDeclaration.bits |= 1;
        }
        typeDeclaration.addClinit();
        typeDeclaration.bodyEnd = this.endStatementPosition;
        if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
            typeDeclaration.bits |= 8;
        }
        typeDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeClassHeader() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            typeDeclaration.bodyStart = this.scanner.currentPosition;
        }
        if (this.currentElement != null) {
            this.restartRecovery = true;
        }
        this.scanner.commentPtr = -1;
    }

    protected void consumeClassHeaderExtends() {
        TypeReference typeReference = getTypeReference(0);
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        typeDeclaration.bits |= typeReference.bits & 1048576;
        typeDeclaration.superclass = typeReference;
        typeReference.bits |= 16;
        typeDeclaration.bodyStart = typeDeclaration.superclass.sourceEnd + 1;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
        }
    }

    protected void consumeClassHeaderImplements() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.astPtr - i2;
        this.astPtr = i3;
        ASTNode[] aSTNodeArr = this.astStack;
        TypeDeclaration typeDeclaration = (TypeDeclaration) aSTNodeArr[i3];
        TypeReference[] typeReferenceArr = new TypeReference[i2];
        typeDeclaration.superInterfaces = typeReferenceArr;
        System.arraycopy(aSTNodeArr, i3 + 1, typeReferenceArr, 0, i2);
        for (TypeReference typeReference : typeDeclaration.superInterfaces) {
            typeDeclaration.bits |= typeReference.bits & 1048576;
            typeReference.bits |= 16;
        }
        typeDeclaration.bodyStart = typeDeclaration.superInterfaces[i2 - 1].sourceEnd + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
        }
    }

    protected void consumeClassHeaderName1() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        if (iArr[i] != 0) {
            typeDeclaration.bits |= 256;
            markEnclosingMemberWithLocalType();
            blockReal();
        } else if (i != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        typeDeclaration.f619name = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        typeDeclaration.declarationSourceStart = iArr2[i3];
        int i4 = this.intPtr;
        int[] iArr3 = this.intStack;
        this.intPtr = i4 - 2;
        typeDeclaration.modifiersSourceStart = iArr3[i4 - 1];
        int[] iArr4 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        typeDeclaration.modifiers = iArr4[i5];
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr5 = this.expressionLengthStack;
        int i6 = this.expressionLengthPtr;
        this.expressionLengthPtr = i6 - 1;
        int i7 = iArr5[i6];
        if (i7 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i8 = this.expressionPtr - i7;
            this.expressionPtr = i8;
            Annotation[] annotationArr = new Annotation[i7];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i8 + 1, annotationArr, 0, i7);
        }
        typeDeclaration.bodyStart = typeDeclaration.sourceEnd + 1;
        pushOnAstStack(typeDeclaration);
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
    }

    protected void consumeClassInstanceCreationExpression() {
        classInstanceCreation(false);
        consumeInvocationExpression();
    }

    protected void consumeClassInstanceCreationExpressionName() {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
    }

    protected void consumeClassInstanceCreationExpressionQualified() {
        classInstanceCreation(true);
        QualifiedAllocationExpression qualifiedAllocationExpression = (QualifiedAllocationExpression) this.expressionStack[this.expressionPtr];
        if (qualifiedAllocationExpression.anonymousType == null) {
            this.expressionLengthPtr--;
            int i = this.expressionPtr - 1;
            this.expressionPtr = i;
            qualifiedAllocationExpression.enclosingInstance = this.expressionStack[i];
            this.expressionStack[this.expressionPtr] = qualifiedAllocationExpression;
        }
        qualifiedAllocationExpression.sourceStart = qualifiedAllocationExpression.enclosingInstance.sourceStart;
        consumeInvocationExpression();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0073  */
    /* JADX WARN: Code duplicated, block: B:17:0x00a6  */
    protected void consumeClassInstanceCreationExpressionQualifiedWithTypeArguments() {
        TypeDeclaration typeDeclaration;
        QualifiedAllocationExpression qualifiedAllocationExpression;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i3 = this.astPtr;
            if (aSTNodeArr[i3] == null) {
                this.astPtr = i3 - 1;
                QualifiedAllocationExpression qualifiedAllocationExpression2 = new QualifiedAllocationExpression();
                qualifiedAllocationExpression2.sourceEnd = this.endPosition;
                int[] iArr2 = this.expressionLengthStack;
                int i4 = this.expressionLengthPtr;
                this.expressionLengthPtr = i4 - 1;
                int i5 = iArr2[i4];
                if (i5 != 0) {
                    int i6 = this.expressionPtr - i5;
                    this.expressionPtr = i6;
                    Expression[] expressionArr = new Expression[i5];
                    qualifiedAllocationExpression2.arguments = expressionArr;
                    System.arraycopy(this.expressionStack, i6 + 1, expressionArr, 0, i5);
                }
                qualifiedAllocationExpression2.type = getTypeReference(0);
                checkForDiamond(qualifiedAllocationExpression2.type);
                int[] iArr3 = this.genericsLengthStack;
                int i7 = this.genericsLengthPtr;
                this.genericsLengthPtr = i7 - 1;
                int i8 = iArr3[i7];
                int i9 = this.genericsPtr - i8;
                this.genericsPtr = i9;
                TypeReference[] typeReferenceArr = new TypeReference[i8];
                qualifiedAllocationExpression2.typeArguments = typeReferenceArr;
                System.arraycopy(this.genericsStack, i9 + 1, typeReferenceArr, 0, i8);
                int i10 = this.intPtr;
                int[] iArr4 = this.intStack;
                this.intPtr = i10 - 2;
                qualifiedAllocationExpression2.sourceStart = iArr4[i10 - 1];
                pushOnExpressionStack(qualifiedAllocationExpression2);
            } else {
                dispatchDeclarationInto(i2);
                typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
                typeDeclaration.declarationSourceEnd = this.endStatementPosition;
                typeDeclaration.bodyEnd = this.endStatementPosition;
                if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
                    typeDeclaration.bits |= 8;
                }
                this.astPtr--;
                this.astLengthPtr--;
                qualifiedAllocationExpression = typeDeclaration.allocation;
                if (qualifiedAllocationExpression != null) {
                    qualifiedAllocationExpression.sourceEnd = this.endStatementPosition;
                    int[] iArr5 = this.genericsLengthStack;
                    int i11 = this.genericsLengthPtr;
                    this.genericsLengthPtr = i11 - 1;
                    int i12 = iArr5[i11];
                    int i13 = this.genericsPtr - i12;
                    this.genericsPtr = i13;
                    TypeReference[] typeReferenceArr2 = new TypeReference[i12];
                    qualifiedAllocationExpression.typeArguments = typeReferenceArr2;
                    System.arraycopy(this.genericsStack, i13 + 1, typeReferenceArr2, 0, i12);
                    int[] iArr6 = this.intStack;
                    int i14 = this.intPtr;
                    this.intPtr = i14 - 1;
                    qualifiedAllocationExpression.sourceStart = iArr6[i14];
                    checkForDiamond(qualifiedAllocationExpression.type);
                }
            }
        } else {
            dispatchDeclarationInto(i2);
            typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
            typeDeclaration.declarationSourceEnd = this.endStatementPosition;
            typeDeclaration.bodyEnd = this.endStatementPosition;
            if (i2 == 0) {
                typeDeclaration.bits |= 8;
            }
            this.astPtr--;
            this.astLengthPtr--;
            qualifiedAllocationExpression = typeDeclaration.allocation;
            if (qualifiedAllocationExpression != null) {
                qualifiedAllocationExpression.sourceEnd = this.endStatementPosition;
                int[] iArr7 = this.genericsLengthStack;
                int i15 = this.genericsLengthPtr;
                this.genericsLengthPtr = i15 - 1;
                int i16 = iArr7[i15];
                int i17 = this.genericsPtr - i16;
                this.genericsPtr = i17;
                TypeReference[] typeReferenceArr3 = new TypeReference[i16];
                qualifiedAllocationExpression.typeArguments = typeReferenceArr3;
                System.arraycopy(this.genericsStack, i17 + 1, typeReferenceArr3, 0, i16);
                int[] iArr8 = this.intStack;
                int i18 = this.intPtr;
                this.intPtr = i18 - 1;
                qualifiedAllocationExpression.sourceStart = iArr8[i18];
                checkForDiamond(qualifiedAllocationExpression.type);
            }
        }
        QualifiedAllocationExpression qualifiedAllocationExpression3 = (QualifiedAllocationExpression) this.expressionStack[this.expressionPtr];
        if (qualifiedAllocationExpression3.anonymousType == null) {
            this.expressionLengthPtr--;
            int i19 = this.expressionPtr - 1;
            this.expressionPtr = i19;
            qualifiedAllocationExpression3.enclosingInstance = this.expressionStack[i19];
            this.expressionStack[this.expressionPtr] = qualifiedAllocationExpression3;
        }
        qualifiedAllocationExpression3.sourceStart = qualifiedAllocationExpression3.enclosingInstance.sourceStart;
        consumeInvocationExpression();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0073  */
    /* JADX WARN: Code duplicated, block: B:17:0x00a6  */
    protected void consumeClassInstanceCreationExpressionWithTypeArguments() {
        TypeDeclaration typeDeclaration;
        QualifiedAllocationExpression qualifiedAllocationExpression;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i3 = this.astPtr;
            if (aSTNodeArr[i3] == null) {
                this.astPtr = i3 - 1;
                AllocationExpression allocationExpression = new AllocationExpression();
                allocationExpression.sourceEnd = this.endPosition;
                int[] iArr2 = this.expressionLengthStack;
                int i4 = this.expressionLengthPtr;
                this.expressionLengthPtr = i4 - 1;
                int i5 = iArr2[i4];
                if (i5 != 0) {
                    int i6 = this.expressionPtr - i5;
                    this.expressionPtr = i6;
                    Expression[] expressionArr = new Expression[i5];
                    allocationExpression.arguments = expressionArr;
                    System.arraycopy(this.expressionStack, i6 + 1, expressionArr, 0, i5);
                }
                allocationExpression.type = getTypeReference(0);
                checkForDiamond(allocationExpression.type);
                int[] iArr3 = this.genericsLengthStack;
                int i7 = this.genericsLengthPtr;
                this.genericsLengthPtr = i7 - 1;
                int i8 = iArr3[i7];
                int i9 = this.genericsPtr - i8;
                this.genericsPtr = i9;
                TypeReference[] typeReferenceArr = new TypeReference[i8];
                allocationExpression.typeArguments = typeReferenceArr;
                System.arraycopy(this.genericsStack, i9 + 1, typeReferenceArr, 0, i8);
                int i10 = this.intPtr;
                int[] iArr4 = this.intStack;
                this.intPtr = i10 - 2;
                allocationExpression.sourceStart = iArr4[i10 - 1];
                pushOnExpressionStack(allocationExpression);
            } else {
                dispatchDeclarationInto(i2);
                typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
                typeDeclaration.declarationSourceEnd = this.endStatementPosition;
                typeDeclaration.bodyEnd = this.endStatementPosition;
                if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
                    typeDeclaration.bits |= 8;
                }
                this.astPtr--;
                this.astLengthPtr--;
                qualifiedAllocationExpression = typeDeclaration.allocation;
                if (qualifiedAllocationExpression != null) {
                    qualifiedAllocationExpression.sourceEnd = this.endStatementPosition;
                    int[] iArr5 = this.genericsLengthStack;
                    int i11 = this.genericsLengthPtr;
                    this.genericsLengthPtr = i11 - 1;
                    int i12 = iArr5[i11];
                    int i13 = this.genericsPtr - i12;
                    this.genericsPtr = i13;
                    TypeReference[] typeReferenceArr2 = new TypeReference[i12];
                    qualifiedAllocationExpression.typeArguments = typeReferenceArr2;
                    System.arraycopy(this.genericsStack, i13 + 1, typeReferenceArr2, 0, i12);
                    int[] iArr6 = this.intStack;
                    int i14 = this.intPtr;
                    this.intPtr = i14 - 1;
                    qualifiedAllocationExpression.sourceStart = iArr6[i14];
                    checkForDiamond(qualifiedAllocationExpression.type);
                }
            }
        } else {
            dispatchDeclarationInto(i2);
            typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
            typeDeclaration.declarationSourceEnd = this.endStatementPosition;
            typeDeclaration.bodyEnd = this.endStatementPosition;
            if (i2 == 0) {
                typeDeclaration.bits |= 8;
            }
            this.astPtr--;
            this.astLengthPtr--;
            qualifiedAllocationExpression = typeDeclaration.allocation;
            if (qualifiedAllocationExpression != null) {
                qualifiedAllocationExpression.sourceEnd = this.endStatementPosition;
                int[] iArr7 = this.genericsLengthStack;
                int i15 = this.genericsLengthPtr;
                this.genericsLengthPtr = i15 - 1;
                int i16 = iArr7[i15];
                int i17 = this.genericsPtr - i16;
                this.genericsPtr = i17;
                TypeReference[] typeReferenceArr3 = new TypeReference[i16];
                qualifiedAllocationExpression.typeArguments = typeReferenceArr3;
                System.arraycopy(this.genericsStack, i17 + 1, typeReferenceArr3, 0, i16);
                int[] iArr8 = this.intStack;
                int i18 = this.intPtr;
                this.intPtr = i18 - 1;
                qualifiedAllocationExpression.sourceStart = iArr8[i18];
                checkForDiamond(qualifiedAllocationExpression.type);
            }
        }
        consumeInvocationExpression();
    }

    protected void consumeClassOrInterface() {
        int[] iArr = this.genericsIdentifiersLengthStack;
        int i = this.genericsIdentifiersLengthPtr;
        iArr[i] = iArr[i] + this.identifierLengthStack[this.identifierLengthPtr];
        pushOnGenericsLengthStack(0);
    }

    protected void consumeClassOrInterfaceName() {
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnGenericsLengthStack(0);
    }

    protected void consumeClassTypeElt() {
        pushOnAstStack(getTypeReference(0));
        this.listLength++;
    }

    protected void consumeClassTypeList() {
        optimizedConcatNodeLists();
    }

    protected void consumeConditionalExpression(int i) {
        this.intPtr -= 2;
        int i2 = this.expressionPtr - 2;
        this.expressionPtr = i2;
        this.expressionLengthPtr -= 2;
        Expression[] expressionArr = this.expressionStack;
        Expression[] expressionArr2 = this.expressionStack;
        int i3 = this.expressionPtr;
        expressionArr[i2] = new ConditionalExpression(expressionArr2[i3], expressionArr2[i3 + 1], expressionArr2[i3 + 2]);
    }

    protected void consumeConditionalExpressionWithName(int i) {
        this.intPtr -= 2;
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
        int i2 = this.expressionPtr - 2;
        this.expressionPtr = i2;
        this.expressionLengthPtr -= 2;
        Expression[] expressionArr = this.expressionStack;
        Expression[] expressionArr2 = this.expressionStack;
        int i3 = this.expressionPtr;
        expressionArr[i2] = new ConditionalExpression(expressionArr2[i3 + 2], expressionArr2[i3], expressionArr2[i3 + 1]);
    }

    protected void consumeConstructorBlockStatements() {
        concatNodeLists();
    }

    protected void consumeConstructorBody() {
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] - 1;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0077  */
    protected void consumeConstructorDeclaration() {
        Statement[] statementArr;
        this.intPtr -= 2;
        boolean z = true;
        this.realBlockPtr--;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        ExplicitConstructorCall explicitConstructorCallImplicitSuperConstructorCall = null;
        if (i2 != 0) {
            this.astPtr -= i2;
            if (this.options.ignoreMethodBodies) {
                statementArr = null;
            } else {
                ASTNode[] aSTNodeArr = this.astStack;
                int i3 = this.astPtr;
                if (aSTNodeArr[i3 + 1] instanceof ExplicitConstructorCall) {
                    int i4 = i2 - 1;
                    Statement[] statementArr2 = new Statement[i4];
                    System.arraycopy(aSTNodeArr, i3 + 2, statementArr2, 0, i4);
                    explicitConstructorCallImplicitSuperConstructorCall = (ExplicitConstructorCall) this.astStack[this.astPtr + 1];
                    statementArr = statementArr2;
                } else {
                    statementArr = new Statement[i2];
                    System.arraycopy(aSTNodeArr, i3 + 1, statementArr, 0, i2);
                    explicitConstructorCallImplicitSuperConstructorCall = SuperReference.implicitSuperConstructorCall();
                }
            }
        } else {
            if (!this.diet) {
                z = false;
                break;
            }
            int i5 = this.nestedType;
            while (true) {
                if (i5 <= 0) {
                    z = false;
                    break;
                } else if (this.variablesCounter[i5] > 0) {
                    break;
                } else {
                    i5--;
                }
            }
            if (this.options.ignoreMethodBodies || (this.diet && !z)) {
                statementArr = null;
            } else {
                statementArr = null;
                explicitConstructorCallImplicitSuperConstructorCall = SuperReference.implicitSuperConstructorCall();
            }
        }
        ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) this.astStack[this.astPtr];
        constructorDeclaration.constructorCall = explicitConstructorCallImplicitSuperConstructorCall;
        constructorDeclaration.statements = statementArr;
        if (explicitConstructorCallImplicitSuperConstructorCall != null && constructorDeclaration.constructorCall.sourceEnd == 0) {
            constructorDeclaration.constructorCall.sourceEnd = constructorDeclaration.sourceEnd;
            constructorDeclaration.constructorCall.sourceStart = constructorDeclaration.sourceStart;
        }
        if ((!this.diet || this.dietInt != 0) && statementArr == null && ((explicitConstructorCallImplicitSuperConstructorCall == null || explicitConstructorCallImplicitSuperConstructorCall.isImplicitSuper()) && !containsComment(constructorDeclaration.bodyStart, this.endPosition))) {
            constructorDeclaration.bits |= 8;
        }
        constructorDeclaration.bodyEnd = this.endPosition;
        constructorDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeConstructorHeader() {
        AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            abstractMethodDeclaration.bodyStart = this.scanner.currentPosition;
        }
        if (this.currentElement != null) {
            if (this.currentToken == 28) {
                abstractMethodDeclaration.modifiers |= 16777216;
                abstractMethodDeclaration.declarationSourceEnd = this.scanner.currentPosition - 1;
                abstractMethodDeclaration.bodyEnd = this.scanner.currentPosition - 1;
                if (this.currentElement.parseTree() == abstractMethodDeclaration && this.currentElement.parent != null) {
                    this.currentElement = this.currentElement.parent;
                }
            }
            this.restartRecovery = true;
        }
    }

    protected void consumeConstructorHeaderName() {
        if (this.currentElement != null && this.lastIgnoredToken == 36) {
            this.lastCheckPoint = this.scanner.startPosition;
            this.restartRecovery = true;
            return;
        }
        ConstructorDeclaration constructorDeclaration = new ConstructorDeclaration(this.compilationUnit.compilationResult);
        constructorDeclaration.selector = this.identifierStack[this.identifierPtr];
        long[] jArr = this.identifierPositionStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        constructorDeclaration.declarationSourceStart = iArr[i2];
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        constructorDeclaration.modifiers = iArr2[i3];
        int[] iArr3 = this.expressionLengthStack;
        int i4 = this.expressionLengthPtr;
        this.expressionLengthPtr = i4 - 1;
        int i5 = iArr3[i4];
        if (i5 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i6 = this.expressionPtr - i5;
            this.expressionPtr = i6;
            Annotation[] annotationArr = new Annotation[i5];
            constructorDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i6 + 1, annotationArr, 0, i5);
        }
        constructorDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        constructorDeclaration.sourceStart = (int) (j >>> 32);
        pushOnAstStack(constructorDeclaration);
        constructorDeclaration.sourceEnd = this.lParenPos;
        constructorDeclaration.bodyStart = this.lParenPos + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = constructorDeclaration.bodyStart;
            if ((!(this.currentElement instanceof RecoveredType) || this.lastIgnoredToken == 3) && constructorDeclaration.modifiers == 0) {
                return;
            }
            this.currentElement = this.currentElement.add(constructorDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeConstructorHeaderNameWithTypeParameters() {
        if (this.currentElement != null && this.lastIgnoredToken == 36) {
            this.lastCheckPoint = this.scanner.startPosition;
            this.restartRecovery = true;
            return;
        }
        ConstructorDeclaration constructorDeclaration = new ConstructorDeclaration(this.compilationUnit.compilationResult);
        constructorDeclaration.selector = this.identifierStack[this.identifierPtr];
        long[] jArr = this.identifierPositionStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i2 = this.genericsLengthPtr;
        this.genericsLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.genericsPtr - i3;
        this.genericsPtr = i4;
        TypeParameter[] typeParameterArr = new TypeParameter[i3];
        constructorDeclaration.typeParameters = typeParameterArr;
        System.arraycopy(this.genericsStack, i4 + 1, typeParameterArr, 0, i3);
        int[] iArr2 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        constructorDeclaration.declarationSourceStart = iArr2[i5];
        int[] iArr3 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        constructorDeclaration.modifiers = iArr3[i6];
        int[] iArr4 = this.expressionLengthStack;
        int i7 = this.expressionLengthPtr;
        this.expressionLengthPtr = i7 - 1;
        int i8 = iArr4[i7];
        if (i8 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i9 = this.expressionPtr - i8;
            this.expressionPtr = i9;
            Annotation[] annotationArr = new Annotation[i8];
            constructorDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i9 + 1, annotationArr, 0, i8);
        }
        constructorDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        constructorDeclaration.sourceStart = (int) (j >>> 32);
        pushOnAstStack(constructorDeclaration);
        constructorDeclaration.sourceEnd = this.lParenPos;
        constructorDeclaration.bodyStart = this.lParenPos + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = constructorDeclaration.bodyStart;
            if ((!(this.currentElement instanceof RecoveredType) || this.lastIgnoredToken == 3) && constructorDeclaration.modifiers == 0) {
                return;
            }
            this.currentElement = this.currentElement.add(constructorDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeCreateInitializer() {
        pushOnAstStack(new Initializer(null, 0));
    }

    protected void consumeDefaultLabel() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        int i2 = i - 1;
        this.intPtr = i2;
        int i3 = iArr[i];
        this.intPtr = i - 2;
        CaseStatement caseStatement = new CaseStatement(null, i3, iArr[i2]);
        if (hasLeadingTagComment(FALL_THROUGH_TAG, caseStatement.sourceStart)) {
            caseStatement.bits |= 536870912;
        }
        if (hasLeadingTagComment(CASES_OMITTED_TAG, caseStatement.sourceStart)) {
            caseStatement.bits |= 1073741824;
        }
        pushOnAstStack(caseStatement);
    }

    protected void consumeDefaultModifiers() {
        checkComment();
        pushOnIntStack(this.modifiers);
        int i = this.modifiersSourceStart;
        if (i < 0) {
            i = this.scanner.startPosition;
        }
        pushOnIntStack(i);
        resetModifiers();
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeDiet() {
        checkComment();
        pushOnIntStack(this.modifiersSourceStart);
        resetModifiers();
        jumpOverMethodBody();
    }

    protected void consumeDims() {
        pushOnIntStack(this.dimensions);
        this.dimensions = 0;
    }

    protected void consumeDimWithOrWithOutExpr() {
        pushOnExpressionStack(null);
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || this.currentToken != 49) {
            return;
        }
        this.ignoreNextOpeningBrace = true;
        recoveredElement.bracketBalance++;
    }

    protected void consumeDimWithOrWithOutExprs() {
        concatExpressionLists();
    }

    protected void consumeUnionType() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnAstStack(getTypeReference(iArr[i]));
        optimizedConcatNodeLists();
    }

    protected void consumeUnionTypeAsClassType() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnAstStack(getTypeReference(iArr[i]));
    }

    protected void consumeEmptyAnnotationTypeMemberDeclarationsopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyArgumentListopt() {
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeEmptyArguments() {
        pushOnIntStack(((FieldDeclaration) this.astStack[this.astPtr]).sourceEnd);
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeEmptyArrayInitializer() {
        arrayInitializer(0);
    }

    protected void consumeEmptyArrayInitializeropt() {
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeEmptyBlockStatementsopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyCatchesopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyClassBodyDeclarationsopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyDimsopt() {
        pushOnIntStack(0);
    }

    protected void consumeEmptyEnumDeclarations() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyExpression() {
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeEmptyForInitopt() {
        pushOnAstLengthStack(0);
        this.forStartPosition = 0;
    }

    protected void consumeEmptyForUpdateopt() {
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumeEmptyInterfaceMemberDeclarationsopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyInternalCompilationUnit() {
        if (this.compilationUnit.isPackageInfo()) {
            this.compilationUnit.types = new TypeDeclaration[1];
            this.compilationUnit.createPackageInfoType();
        }
    }

    protected void consumeEmptyMemberValueArrayInitializer() {
        arrayInitializer(0);
    }

    protected void consumeEmptyMemberValuePairsopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyMethodHeaderDefaultValue() {
        if (((AbstractMethodDeclaration) this.astStack[this.astPtr]).isAnnotationMethod()) {
            pushOnExpressionStackLengthStack(0);
        }
        this.recordStringLiterals = true;
    }

    protected void consumeEmptyStatement() {
        char c;
        int hexadecimalValue;
        int hexadecimalValue2;
        int hexadecimalValue3;
        int hexadecimalValue4;
        char[] cArr = this.scanner.source;
        int i = this.endStatementPosition;
        if (cArr[i] == ';') {
            int i2 = this.endStatementPosition;
            pushOnAstStack(new EmptyStatement(i2, i2));
            return;
        }
        if (cArr.length > 5) {
            int i3 = i - 4;
            while (true) {
                c = cArr[i3];
                if (c != 'u') {
                    break;
                } else {
                    i3--;
                }
            }
            if (c == '\\' && (hexadecimalValue = ScannerHelper.getHexadecimalValue(cArr[this.endStatementPosition - 3])) <= 15 && hexadecimalValue >= 0 && (hexadecimalValue2 = ScannerHelper.getHexadecimalValue(cArr[this.endStatementPosition - 2])) <= 15 && hexadecimalValue2 >= 0 && (hexadecimalValue3 = ScannerHelper.getHexadecimalValue(cArr[this.endStatementPosition - 1])) <= 15 && hexadecimalValue3 >= 0 && (hexadecimalValue4 = ScannerHelper.getHexadecimalValue(cArr[this.endStatementPosition])) <= 15 && hexadecimalValue4 >= 0 && ((char) ((((((hexadecimalValue * 16) + hexadecimalValue2) * 16) + hexadecimalValue3) * 16) + hexadecimalValue4)) == ';') {
                pushOnAstStack(new EmptyStatement(i3, this.endStatementPosition));
                return;
            }
        }
        pushOnAstStack(new EmptyStatement(this.endPosition + 1, this.endStatementPosition));
    }

    protected void consumeEmptySwitchBlock() {
        pushOnAstLengthStack(0);
    }

    protected void consumeEmptyTypeDeclaration() {
        pushOnAstLengthStack(0);
        if (!this.statementRecoveryActivated) {
            problemReporter().superfluousSemicolon(this.endPosition + 1, this.endStatementPosition);
        }
        flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeEnhancedForStatement() {
        this.astLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        int i2 = i - 1;
        this.astPtr = i2;
        Statement statement = (Statement) aSTNodeArr[i];
        ForeachStatement foreachStatement = (ForeachStatement) aSTNodeArr[i2];
        foreachStatement.action = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        foreachStatement.sourceEnd = this.endStatementPosition;
    }

    protected void consumeEnhancedForStatementHeader() {
        ForeachStatement foreachStatement = (ForeachStatement) this.astStack[this.astPtr];
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        Expression expression = expressionArr[i];
        foreachStatement.collection = expression;
        foreachStatement.elementVariable.declarationSourceEnd = expression.sourceEnd;
        foreachStatement.elementVariable.declarationEnd = expression.sourceEnd;
        foreachStatement.sourceEnd = this.rParenPos;
        if (this.statementRecoveryActivated || this.options.sourceLevel >= ClassFileConstants.JDK1_5 || this.lastErrorEndPositionBeforeRecovery >= this.scanner.currentPosition) {
            return;
        }
        problemReporter().invalidUsageOfForeachStatements(foreachStatement.elementVariable, expression);
    }

    protected void consumeEnhancedForStatementHeaderInit(boolean z) {
        int i;
        int i2;
        char[][] cArr = this.identifierStack;
        int i3 = this.identifierPtr;
        char[] cArr2 = cArr[i3];
        long j = this.identifierPositionStack[i3];
        LocalDeclaration localDeclarationCreateLocalDeclaration = createLocalDeclaration(cArr2, (int) (j >>> 32), (int) j);
        localDeclarationCreateLocalDeclaration.declarationSourceEnd = localDeclarationCreateLocalDeclaration.declarationEnd;
        localDeclarationCreateLocalDeclaration.bits |= 16;
        int[] iArr = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        int i5 = iArr[i4];
        Annotation[][] annotationsOnDimensions = i5 == 0 ? null : getAnnotationsOnDimensions(i5);
        this.identifierPtr--;
        this.identifierLengthPtr--;
        if (z) {
            int[] iArr2 = this.intStack;
            int i6 = this.intPtr;
            int i7 = i6 - 1;
            this.intPtr = i7;
            i2 = iArr2[i6];
            this.intPtr = i6 - 2;
            i = iArr2[i7];
        } else {
            this.intPtr -= 2;
            i = 0;
            i2 = 0;
        }
        int[] iArr3 = this.intStack;
        int i8 = this.intPtr;
        this.intPtr = i8 - 1;
        TypeReference typeReference = getTypeReference(iArr3[i8]);
        int[] iArr4 = this.expressionLengthStack;
        int i9 = this.expressionLengthPtr;
        this.expressionLengthPtr = i9 - 1;
        int i10 = iArr4[i9];
        if (i10 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i11 = this.expressionPtr - i10;
            this.expressionPtr = i11;
            Annotation[] annotationArr = new Annotation[i10];
            localDeclarationCreateLocalDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i11 + 1, annotationArr, 0, i10);
            localDeclarationCreateLocalDeclaration.bits |= 1048576;
        }
        if (i5 != 0) {
            typeReference = augmentTypeWithAdditionalDimensions(typeReference, i5, annotationsOnDimensions, false);
        }
        if (z) {
            localDeclarationCreateLocalDeclaration.declarationSourceStart = i2;
            localDeclarationCreateLocalDeclaration.modifiers = i;
        } else {
            localDeclarationCreateLocalDeclaration.declarationSourceStart = typeReference.sourceStart;
        }
        localDeclarationCreateLocalDeclaration.type = typeReference;
        localDeclarationCreateLocalDeclaration.bits |= typeReference.bits & 1048576;
        int[] iArr5 = this.intStack;
        int i12 = this.intPtr;
        this.intPtr = i12 - 1;
        ForeachStatement foreachStatement = new ForeachStatement(localDeclarationCreateLocalDeclaration, iArr5[i12]);
        pushOnAstStack(foreachStatement);
        foreachStatement.sourceEnd = localDeclarationCreateLocalDeclaration.declarationSourceEnd;
        this.forStartPosition = 0;
    }

    protected void consumeEnterAnonymousClassBody(boolean z) {
        TypeReference typeReference = getTypeReference(0);
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        typeDeclaration.f619name = CharOperation.NO_CHAR;
        typeDeclaration.bits |= 768;
        typeDeclaration.bits |= typeReference.bits & 1048576;
        QualifiedAllocationExpression qualifiedAllocationExpression = new QualifiedAllocationExpression(typeDeclaration);
        markEnclosingMemberWithLocalType();
        pushOnAstStack(typeDeclaration);
        qualifiedAllocationExpression.sourceEnd = this.rParenPos;
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            int i3 = this.expressionPtr - i2;
            this.expressionPtr = i3;
            Expression[] expressionArr = new Expression[i2];
            qualifiedAllocationExpression.arguments = expressionArr;
            System.arraycopy(this.expressionStack, i3 + 1, expressionArr, 0, i2);
        }
        if (z) {
            this.expressionLengthPtr--;
            Expression[] expressionArr2 = this.expressionStack;
            int i4 = this.expressionPtr;
            this.expressionPtr = i4 - 1;
            qualifiedAllocationExpression.enclosingInstance = expressionArr2[i4];
        }
        qualifiedAllocationExpression.type = typeReference;
        typeDeclaration.sourceEnd = qualifiedAllocationExpression.sourceEnd;
        int i5 = qualifiedAllocationExpression.type.sourceStart;
        typeDeclaration.declarationSourceStart = i5;
        typeDeclaration.sourceStart = i5;
        int[] iArr2 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        qualifiedAllocationExpression.sourceStart = iArr2[i6];
        pushOnExpressionStack(qualifiedAllocationExpression);
        typeDeclaration.bodyStart = this.scanner.currentPosition;
        this.listLength = 0;
        this.scanner.commentPtr = -1;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            RecoveredElement recoveredElementAdd = this.currentElement.add(typeDeclaration, 0);
            this.currentElement = recoveredElementAdd;
            if (!(recoveredElementAdd instanceof RecoveredAnnotation)) {
                if (isIndirectlyInsideLambdaExpression()) {
                    this.ignoreNextOpeningBrace = true;
                } else {
                    this.currentToken = 0;
                }
            } else {
                this.ignoreNextOpeningBrace = true;
                recoveredElementAdd.bracketBalance++;
            }
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeEnterMemberValue() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || !(recoveredElement instanceof RecoveredAnnotation)) {
            return;
        }
        ((RecoveredAnnotation) recoveredElement).hasPendingMemberValueName = true;
    }

    protected void consumeEnterMemberValueArrayInitializer() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            this.ignoreNextOpeningBrace = true;
            recoveredElement.bracketBalance++;
        }
    }

    protected void consumeEnterVariable() {
        AbstractVariableDeclaration abstractVariableDeclarationCreateFieldDeclaration;
        TypeReference typeReference;
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        char[] cArr2 = cArr[i];
        long j = this.identifierPositionStack[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        int i3 = iArr[i2];
        Annotation[][] annotationsOnDimensions = i3 == 0 ? null : getAnnotationsOnDimensions(i3);
        boolean z = this.nestedMethod[this.nestedType] != 0;
        if (z) {
            abstractVariableDeclarationCreateFieldDeclaration = createLocalDeclaration(cArr2, (int) (j >>> 32), (int) j);
        } else {
            abstractVariableDeclarationCreateFieldDeclaration = createFieldDeclaration(cArr2, (int) (j >>> 32), (int) j);
        }
        this.identifierPtr--;
        this.identifierLengthPtr--;
        int i4 = this.variablesCounter[this.nestedType];
        if (i4 == 0) {
            if (z) {
                int[] iArr2 = this.intStack;
                int i5 = this.intPtr;
                this.intPtr = i5 - 1;
                abstractVariableDeclarationCreateFieldDeclaration.declarationSourceStart = iArr2[i5];
                int[] iArr3 = this.intStack;
                int i6 = this.intPtr;
                this.intPtr = i6 - 1;
                abstractVariableDeclarationCreateFieldDeclaration.modifiers = iArr3[i6];
                int[] iArr4 = this.expressionLengthStack;
                int i7 = this.expressionLengthPtr;
                this.expressionLengthPtr = i7 - 1;
                int i8 = iArr4[i7];
                if (i8 != 0) {
                    Expression[] expressionArr = this.expressionStack;
                    int i9 = this.expressionPtr - i8;
                    this.expressionPtr = i9;
                    Annotation[] annotationArr = new Annotation[i8];
                    abstractVariableDeclarationCreateFieldDeclaration.annotations = annotationArr;
                    System.arraycopy(expressionArr, i9 + 1, annotationArr, 0, i8);
                }
                int[] iArr5 = this.intStack;
                int i10 = this.intPtr;
                this.intPtr = i10 - 1;
                typeReference = getTypeReference(iArr5[i10]);
                if (abstractVariableDeclarationCreateFieldDeclaration.declarationSourceStart == -1) {
                    abstractVariableDeclarationCreateFieldDeclaration.declarationSourceStart = typeReference.sourceStart;
                }
                pushOnAstStack(typeReference);
            } else {
                int[] iArr6 = this.intStack;
                int i11 = this.intPtr;
                this.intPtr = i11 - 1;
                typeReference = getTypeReference(iArr6[i11]);
                pushOnAstStack(typeReference);
                int[] iArr7 = this.intStack;
                int i12 = this.intPtr;
                this.intPtr = i12 - 1;
                abstractVariableDeclarationCreateFieldDeclaration.declarationSourceStart = iArr7[i12];
                int[] iArr8 = this.intStack;
                int i13 = this.intPtr;
                this.intPtr = i13 - 1;
                abstractVariableDeclarationCreateFieldDeclaration.modifiers = iArr8[i13];
                int[] iArr9 = this.expressionLengthStack;
                int i14 = this.expressionLengthPtr;
                this.expressionLengthPtr = i14 - 1;
                int i15 = iArr9[i14];
                if (i15 != 0) {
                    Expression[] expressionArr2 = this.expressionStack;
                    int i16 = this.expressionPtr - i15;
                    this.expressionPtr = i16;
                    Annotation[] annotationArr2 = new Annotation[i15];
                    abstractVariableDeclarationCreateFieldDeclaration.annotations = annotationArr2;
                    System.arraycopy(expressionArr2, i16 + 1, annotationArr2, 0, i15);
                }
                ((FieldDeclaration) abstractVariableDeclarationCreateFieldDeclaration).javadoc = this.javadoc;
            }
            this.javadoc = null;
        } else {
            ASTNode[] aSTNodeArr = this.astStack;
            int i17 = this.astPtr;
            typeReference = (TypeReference) aSTNodeArr[i17 - i4];
            AbstractVariableDeclaration abstractVariableDeclaration = (AbstractVariableDeclaration) aSTNodeArr[i17];
            abstractVariableDeclarationCreateFieldDeclaration.declarationSourceStart = abstractVariableDeclaration.declarationSourceStart;
            abstractVariableDeclarationCreateFieldDeclaration.modifiers = abstractVariableDeclaration.modifiers;
            Annotation[] annotationArr3 = abstractVariableDeclaration.annotations;
            if (annotationArr3 != null) {
                int length = annotationArr3.length;
                Annotation[] annotationArr4 = new Annotation[length];
                abstractVariableDeclarationCreateFieldDeclaration.annotations = annotationArr4;
                System.arraycopy(annotationArr3, 0, annotationArr4, 0, length);
            }
        }
        abstractVariableDeclarationCreateFieldDeclaration.type = i3 == 0 ? typeReference : augmentTypeWithAdditionalDimensions(typeReference, i3, annotationsOnDimensions, false);
        abstractVariableDeclarationCreateFieldDeclaration.bits |= typeReference.bits & 1048576;
        int[] iArr10 = this.variablesCounter;
        int i18 = this.nestedType;
        iArr10[i18] = iArr10[i18] + 1;
        pushOnAstStack(abstractVariableDeclarationCreateFieldDeclaration);
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            if (!(recoveredElement instanceof RecoveredType) && (this.currentToken == 3 || Util.getLineNumber(abstractVariableDeclarationCreateFieldDeclaration.type.sourceStart, this.scanner.lineEnds, 0, this.scanner.linePtr) != Util.getLineNumber((int) (j >>> 32), this.scanner.lineEnds, 0, this.scanner.linePtr))) {
                this.lastCheckPoint = (int) (j >>> 32);
                this.restartRecovery = true;
                return;
            }
            if (z) {
                LocalDeclaration localDeclaration = (LocalDeclaration) this.astStack[this.astPtr];
                this.lastCheckPoint = localDeclaration.sourceEnd + 1;
                this.currentElement = this.currentElement.add(localDeclaration, 0);
            } else {
                FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[this.astPtr];
                this.lastCheckPoint = fieldDeclaration.sourceEnd + 1;
                this.currentElement = this.currentElement.add(fieldDeclaration, 0);
            }
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeEnumBodyWithConstants() {
        concatNodeLists();
    }

    protected void consumeEnumConstantHeader() {
        RecoveredType recoveredTypeCurrentRecoveryType;
        FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[this.astPtr];
        boolean z = this.currentToken == 49;
        if (z) {
            TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
            typeDeclaration.f619name = CharOperation.NO_CHAR;
            typeDeclaration.bits |= 768;
            int i = this.scanner.startPosition;
            typeDeclaration.declarationSourceStart = i;
            typeDeclaration.sourceStart = i;
            typeDeclaration.sourceEnd = i;
            typeDeclaration.modifiers = 0;
            typeDeclaration.bodyStart = this.scanner.currentPosition;
            markEnclosingMemberWithLocalType();
            consumeNestedType();
            int[] iArr = this.variablesCounter;
            int i2 = this.nestedType;
            iArr[i2] = iArr[i2] + 1;
            pushOnAstStack(typeDeclaration);
            QualifiedAllocationExpression qualifiedAllocationExpression = new QualifiedAllocationExpression(typeDeclaration);
            qualifiedAllocationExpression.enumConstant = fieldDeclaration;
            int[] iArr2 = this.expressionLengthStack;
            int i3 = this.expressionLengthPtr;
            this.expressionLengthPtr = i3 - 1;
            int i4 = iArr2[i3];
            if (i4 != 0) {
                int i5 = this.expressionPtr - i4;
                this.expressionPtr = i5;
                Expression[] expressionArr = new Expression[i4];
                qualifiedAllocationExpression.arguments = expressionArr;
                System.arraycopy(this.expressionStack, i5 + 1, expressionArr, 0, i4);
            }
            fieldDeclaration.initialization = qualifiedAllocationExpression;
        } else {
            AllocationExpression allocationExpression = new AllocationExpression();
            allocationExpression.enumConstant = fieldDeclaration;
            int[] iArr3 = this.expressionLengthStack;
            int i6 = this.expressionLengthPtr;
            this.expressionLengthPtr = i6 - 1;
            int i7 = iArr3[i6];
            if (i7 != 0) {
                int i8 = this.expressionPtr - i7;
                this.expressionPtr = i8;
                Expression[] expressionArr2 = new Expression[i7];
                allocationExpression.arguments = expressionArr2;
                System.arraycopy(this.expressionStack, i8 + 1, expressionArr2, 0, i7);
            }
            fieldDeclaration.initialization = allocationExpression;
        }
        fieldDeclaration.initialization.sourceStart = fieldDeclaration.declarationSourceStart;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            if (z) {
                TypeDeclaration typeDeclaration2 = (TypeDeclaration) this.astStack[this.astPtr];
                this.currentElement = recoveredElement.add(typeDeclaration2, 0);
                this.lastCheckPoint = typeDeclaration2.bodyStart;
                this.lastIgnoredToken = -1;
                if (isIndirectlyInsideLambdaExpression()) {
                    this.ignoreNextOpeningBrace = true;
                    return;
                } else {
                    this.currentToken = 0;
                    return;
                }
            }
            if (this.currentToken == 28 && (recoveredTypeCurrentRecoveryType = currentRecoveryType()) != null) {
                recoveredTypeCurrentRecoveryType.insideEnumConstantPart = false;
            }
            this.lastCheckPoint = this.scanner.startPosition;
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeEnumConstantHeaderName() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null && ((!(recoveredElement instanceof RecoveredType) && (!(recoveredElement instanceof RecoveredField) || ((RecoveredField) recoveredElement).fieldDeclaration.type != null)) || this.lastIgnoredToken == 3)) {
            this.lastCheckPoint = this.scanner.startPosition;
            this.restartRecovery = true;
            return;
        }
        long[] jArr = this.identifierPositionStack;
        int i = this.identifierPtr;
        long j = jArr[i];
        FieldDeclaration fieldDeclarationCreateFieldDeclaration = createFieldDeclaration(this.identifierStack[i], (int) (j >>> 32), (int) j);
        this.identifierPtr--;
        this.identifierLengthPtr--;
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        fieldDeclarationCreateFieldDeclaration.modifiersSourceStart = iArr[i2];
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        fieldDeclarationCreateFieldDeclaration.modifiers = iArr2[i3];
        fieldDeclarationCreateFieldDeclaration.declarationSourceStart = fieldDeclarationCreateFieldDeclaration.modifiersSourceStart;
        int[] iArr3 = this.expressionLengthStack;
        int i4 = this.expressionLengthPtr;
        this.expressionLengthPtr = i4 - 1;
        int i5 = iArr3[i4];
        if (i5 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i6 = this.expressionPtr - i5;
            this.expressionPtr = i6;
            Annotation[] annotationArr = new Annotation[i5];
            fieldDeclarationCreateFieldDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i6 + 1, annotationArr, 0, i5);
            fieldDeclarationCreateFieldDeclaration.bits |= 1048576;
        }
        pushOnAstStack(fieldDeclarationCreateFieldDeclaration);
        if (this.currentElement != null) {
            this.lastCheckPoint = fieldDeclarationCreateFieldDeclaration.sourceEnd + 1;
            this.currentElement = this.currentElement.add(fieldDeclarationCreateFieldDeclaration, 0);
        }
        fieldDeclarationCreateFieldDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
    }

    protected void consumeEnumConstantNoClassBody() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[this.astPtr];
        fieldDeclaration.declarationEnd = i2;
        fieldDeclaration.declarationSourceEnd = i2;
        Expression expression = fieldDeclaration.initialization;
        if (expression != null) {
            expression.sourceEnd = i2;
        }
    }

    protected void consumeEnumConstants() {
        concatNodeLists();
    }

    protected void consumeEnumConstantWithClassBody() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        dispatchDeclarationInto(iArr[i]);
        ASTNode[] aSTNodeArr = this.astStack;
        int i2 = this.astPtr;
        this.astPtr = i2 - 1;
        TypeDeclaration typeDeclaration = (TypeDeclaration) aSTNodeArr[i2];
        this.astLengthPtr--;
        typeDeclaration.bodyEnd = this.endPosition;
        typeDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[this.astPtr];
        fieldDeclaration.declarationEnd = this.endStatementPosition;
        int i3 = typeDeclaration.declarationSourceEnd;
        fieldDeclaration.declarationSourceEnd = i3;
        this.intPtr--;
        int[] iArr2 = this.variablesCounter;
        int i4 = this.nestedType;
        iArr2[i4] = 0;
        this.nestedType = i4 - 1;
        Expression expression = fieldDeclaration.initialization;
        if (expression != null) {
            expression.sourceEnd = i3;
        }
    }

    protected void consumeEnumDeclaration() {
        boolean z;
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            dispatchDeclarationIntoEnumDeclaration(i2);
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (!typeDeclaration.checkConstructors(this)) {
            if (!this.diet) {
                z = false;
                break;
            }
            int i3 = this.nestedType;
            while (true) {
                if (i3 <= 0) {
                    z = false;
                    break;
                } else {
                    if (this.variablesCounter[i3] > 0) {
                        z = true;
                        break;
                    }
                    i3--;
                }
            }
            typeDeclaration.createDefaultConstructor(!this.diet || z, true);
        }
        if (this.scanner.containsAssertKeyword) {
            typeDeclaration.bits |= 1;
        }
        typeDeclaration.addClinit();
        typeDeclaration.bodyEnd = this.endStatementPosition;
        if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
            typeDeclaration.bits |= 8;
        }
        typeDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeEnumHeader() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            typeDeclaration.bodyStart = this.scanner.currentPosition;
        }
        if (this.currentElement != null) {
            this.restartRecovery = true;
        }
        this.scanner.commentPtr = -1;
    }

    protected void consumeEnumHeaderName() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        if (iArr[i] != 0) {
            blockReal();
        } else if (i != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        typeDeclaration.f619name = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        typeDeclaration.declarationSourceStart = iArr2[i3];
        int i4 = this.intPtr;
        int[] iArr3 = this.intStack;
        this.intPtr = i4 - 2;
        typeDeclaration.modifiersSourceStart = iArr3[i4 - 1];
        int[] iArr4 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        typeDeclaration.modifiers = iArr4[i5] | 16384;
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr5 = this.expressionLengthStack;
        int i6 = this.expressionLengthPtr;
        this.expressionLengthPtr = i6 - 1;
        int i7 = iArr5[i6];
        if (i7 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i8 = this.expressionPtr - i7;
            this.expressionPtr = i8;
            Annotation[] annotationArr = new Annotation[i7];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i8 + 1, annotationArr, 0, i7);
        }
        typeDeclaration.bodyStart = typeDeclaration.sourceEnd + 1;
        pushOnAstStack(typeDeclaration);
        this.listLength = 0;
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfEnumDeclarations(typeDeclaration);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
    }

    protected void consumeEnumHeaderNameWithTypeParameters() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        TypeParameter[] typeParameterArr = new TypeParameter[i2];
        typeDeclaration.typeParameters = typeParameterArr;
        System.arraycopy(this.genericsStack, i3 + 1, typeParameterArr, 0, i2);
        problemReporter().invalidUsageOfTypeParametersForEnumDeclaration(typeDeclaration);
        typeDeclaration.bodyStart = typeDeclaration.typeParameters[i2 - 1].declarationSourceEnd + 1;
        this.listTypeParameterLength = 0;
        int[] iArr2 = this.nestedMethod;
        int i4 = this.nestedType;
        if (iArr2[i4] != 0) {
            blockReal();
        } else if (i4 != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i5 = this.identifierPtr;
        this.identifierPtr = i5 - 1;
        typeDeclaration.f619name = cArr[i5];
        this.identifierLengthPtr--;
        int[] iArr3 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        typeDeclaration.declarationSourceStart = iArr3[i6];
        int i7 = this.intPtr;
        int[] iArr4 = this.intStack;
        this.intPtr = i7 - 2;
        typeDeclaration.modifiersSourceStart = iArr4[i7 - 1];
        int[] iArr5 = this.intStack;
        int i8 = this.intPtr;
        this.intPtr = i8 - 1;
        typeDeclaration.modifiers = iArr5[i8] | 16384;
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr6 = this.expressionLengthStack;
        int i9 = this.expressionLengthPtr;
        this.expressionLengthPtr = i9 - 1;
        int i10 = iArr6[i9];
        if (i10 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i11 = this.expressionPtr - i10;
            this.expressionPtr = i11;
            Annotation[] annotationArr = new Annotation[i10];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i11 + 1, annotationArr, 0, i10);
        }
        typeDeclaration.bodyStart = typeDeclaration.sourceEnd + 1;
        pushOnAstStack(typeDeclaration);
        this.listLength = 0;
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfEnumDeclarations(typeDeclaration);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
    }

    protected void consumeEqualityExpression(int i) {
        int i2 = this.expressionPtr - 1;
        this.expressionPtr = i2;
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        Expression[] expressionArr2 = this.expressionStack;
        int i3 = this.expressionPtr;
        expressionArr[i2] = new EqualExpression(expressionArr2[i3], expressionArr2[i3 + 1], i);
    }

    protected void consumeEqualityExpressionWithName(int i) {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
        int i2 = this.expressionPtr - 1;
        this.expressionPtr = i2;
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        Expression[] expressionArr2 = this.expressionStack;
        int i3 = this.expressionPtr;
        expressionArr[i2] = new EqualExpression(expressionArr2[i3 + 1], expressionArr2[i3], i);
    }

    protected void consumeExitMemberValue() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || !(recoveredElement instanceof RecoveredAnnotation)) {
            return;
        }
        RecoveredAnnotation recoveredAnnotation = (RecoveredAnnotation) recoveredElement;
        recoveredAnnotation.hasPendingMemberValueName = false;
        recoveredAnnotation.memberValuPairEqualEnd = -1;
    }

    protected void consumeExitTryBlock() {
        if (this.currentElement != null) {
            this.restartRecovery = true;
        }
    }

    protected void consumeExitVariableWithInitialization() {
        this.expressionLengthPtr--;
        AbstractVariableDeclaration abstractVariableDeclaration = (AbstractVariableDeclaration) this.astStack[this.astPtr];
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        abstractVariableDeclaration.initialization = expressionArr[i];
        abstractVariableDeclaration.declarationSourceEnd = abstractVariableDeclaration.initialization.sourceEnd;
        abstractVariableDeclaration.declarationEnd = abstractVariableDeclaration.initialization.sourceEnd;
        recoveryExitFromVariable();
    }

    protected void consumeExitVariableWithoutInitialization() {
        AbstractVariableDeclaration abstractVariableDeclaration = (AbstractVariableDeclaration) this.astStack[this.astPtr];
        abstractVariableDeclaration.declarationSourceEnd = abstractVariableDeclaration.declarationEnd;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null && (recoveredElement instanceof RecoveredField) && this.endStatementPosition > abstractVariableDeclaration.sourceEnd) {
            this.currentElement.updateSourceEndIfNecessary(this.endStatementPosition);
        }
        recoveryExitFromVariable();
    }

    protected void consumeExplicitConstructorInvocation(int i, int i2) {
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        int i4 = iArr[i3];
        ExplicitConstructorCall explicitConstructorCall = new ExplicitConstructorCall(i2);
        int[] iArr2 = this.expressionLengthStack;
        int i5 = this.expressionLengthPtr;
        this.expressionLengthPtr = i5 - 1;
        int i6 = iArr2[i5];
        if (i6 != 0) {
            int i7 = this.expressionPtr - i6;
            this.expressionPtr = i7;
            Expression[] expressionArr = new Expression[i6];
            explicitConstructorCall.arguments = expressionArr;
            System.arraycopy(this.expressionStack, i7 + 1, expressionArr, 0, i6);
        }
        if (i == 0) {
            explicitConstructorCall.sourceStart = i4;
        } else if (i == 1) {
            this.expressionLengthPtr--;
            Expression[] expressionArr2 = this.expressionStack;
            int i8 = this.expressionPtr;
            this.expressionPtr = i8 - 1;
            Expression expression = expressionArr2[i8];
            explicitConstructorCall.qualification = expression;
            explicitConstructorCall.sourceStart = expression.sourceStart;
        } else if (i == 2) {
            NameReference unspecifiedReferenceOptimized = getUnspecifiedReferenceOptimized();
            explicitConstructorCall.qualification = unspecifiedReferenceOptimized;
            explicitConstructorCall.sourceStart = unspecifiedReferenceOptimized.sourceStart;
        }
        pushOnAstStack(explicitConstructorCall);
        explicitConstructorCall.sourceEnd = this.endStatementPosition;
    }

    protected void consumeExplicitConstructorInvocationWithTypeArguments(int i, int i2) {
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        int i4 = iArr[i3];
        ExplicitConstructorCall explicitConstructorCall = new ExplicitConstructorCall(i2);
        int[] iArr2 = this.expressionLengthStack;
        int i5 = this.expressionLengthPtr;
        this.expressionLengthPtr = i5 - 1;
        int i6 = iArr2[i5];
        if (i6 != 0) {
            int i7 = this.expressionPtr - i6;
            this.expressionPtr = i7;
            Expression[] expressionArr = new Expression[i6];
            explicitConstructorCall.arguments = expressionArr;
            System.arraycopy(this.expressionStack, i7 + 1, expressionArr, 0, i6);
        }
        int[] iArr3 = this.genericsLengthStack;
        int i8 = this.genericsLengthPtr;
        this.genericsLengthPtr = i8 - 1;
        int i9 = iArr3[i8];
        int i10 = this.genericsPtr - i9;
        this.genericsPtr = i10;
        TypeReference[] typeReferenceArr = new TypeReference[i9];
        explicitConstructorCall.typeArguments = typeReferenceArr;
        System.arraycopy(this.genericsStack, i10 + 1, typeReferenceArr, 0, i9);
        int[] iArr4 = this.intStack;
        int i11 = this.intPtr;
        this.intPtr = i11 - 1;
        explicitConstructorCall.typeArgumentsSourceStart = iArr4[i11];
        if (i == 0) {
            explicitConstructorCall.sourceStart = i4;
        } else if (i == 1) {
            this.expressionLengthPtr--;
            Expression[] expressionArr2 = this.expressionStack;
            int i12 = this.expressionPtr;
            this.expressionPtr = i12 - 1;
            Expression expression = expressionArr2[i12];
            explicitConstructorCall.qualification = expression;
            explicitConstructorCall.sourceStart = expression.sourceStart;
        } else if (i == 2) {
            NameReference unspecifiedReferenceOptimized = getUnspecifiedReferenceOptimized();
            explicitConstructorCall.qualification = unspecifiedReferenceOptimized;
            explicitConstructorCall.sourceStart = unspecifiedReferenceOptimized.sourceStart;
        }
        pushOnAstStack(explicitConstructorCall);
        explicitConstructorCall.sourceEnd = this.endStatementPosition;
    }

    protected void consumeExpressionStatement() {
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        Expression expression = expressionArr[i];
        expression.statementEnd = this.endStatementPosition;
        expression.bits |= 16;
        pushOnAstStack(expression);
    }

    protected void consumeFieldAccess(boolean z) {
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        char[] cArr2 = cArr[i];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i - 1;
        FieldReference fieldReference = new FieldReference(cArr2, jArr[i]);
        this.identifierLengthPtr--;
        if (z) {
            int[] iArr = this.intStack;
            int i2 = this.intPtr;
            this.intPtr = i2 - 1;
            fieldReference.sourceStart = iArr[i2];
            fieldReference.receiver = new SuperReference(fieldReference.sourceStart, this.endPosition);
            pushOnExpressionStack(fieldReference);
            return;
        }
        fieldReference.receiver = this.expressionStack[this.expressionPtr];
        fieldReference.sourceStart = fieldReference.receiver.sourceStart;
        this.expressionStack[this.expressionPtr] = fieldReference;
    }

    protected void consumeFieldDeclaration() {
        int i = this.astLengthStack[this.astLengthPtr];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[this.astPtr - i2];
            fieldDeclaration.declarationSourceEnd = this.endStatementPosition;
            fieldDeclaration.declarationEnd = this.endStatementPosition;
        }
        updateSourceDeclarationParts(i);
        int iFlushCommentsDefinedPriorTo = flushCommentsDefinedPriorTo(this.endStatementPosition);
        if (iFlushCommentsDefinedPriorTo != this.endStatementPosition) {
            for (int i3 = 0; i3 < i; i3++) {
                ((FieldDeclaration) this.astStack[this.astPtr - i3]).declarationSourceEnd = iFlushCommentsDefinedPriorTo;
            }
        }
        int i4 = this.astPtr - this.variablesCounter[this.nestedType];
        ASTNode[] aSTNodeArr = this.astStack;
        System.arraycopy(aSTNodeArr, i4 + 1, aSTNodeArr, i4, i);
        this.astPtr--;
        int[] iArr = this.astLengthStack;
        int i5 = this.astLengthPtr - 1;
        this.astLengthPtr = i5;
        iArr[i5] = i;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            this.lastCheckPoint = iFlushCommentsDefinedPriorTo + 1;
            if (recoveredElement.parent != null) {
                RecoveredElement recoveredElement2 = this.currentElement;
                if ((recoveredElement2 instanceof RecoveredField) && !(recoveredElement2 instanceof RecoveredInitializer)) {
                    this.currentElement = recoveredElement2.parent;
                }
            }
            this.restartRecovery = true;
        }
        this.variablesCounter[this.nestedType] = 0;
    }

    protected void consumeForceNoDiet() {
        this.dietInt++;
    }

    protected void consumeForInit() {
        pushOnAstLengthStack(-1);
        this.forStartPosition = 0;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0085  */
    /* JADX WARN: Code duplicated, block: B:24:0x0087 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x0089  */
    /* JADX WARN: Code duplicated, block: B:28:0x0093  */
    /* JADX WARN: Code duplicated, block: B:31:0x009e  */
    /* JADX WARN: Code duplicated, block: B:32:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:40:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:41:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:44:0x0105  */
    /* JADX WARN: Code duplicated, block: B:46:0x011f  */
    /* JADX WARN: Code duplicated, block: B:49:0x012e  */
    /* JADX WARN: Code duplicated, block: B:61:? A[RETURN, SYNTHETIC] */
    protected void consumeFormalParameter(boolean z) {
        NameReference nameReference;
        int i;
        Annotation[] annotationArr;
        TypeReference typeReference;
        int i2;
        Argument argument;
        int i3;
        RecoveredType recoveredTypeCurrentRecoveryType;
        int[] iArr = this.intStack;
        int i4 = this.intPtr;
        int i5 = i4 - 1;
        this.intPtr = i5;
        boolean z2 = iArr[i4] == 0;
        if (z2) {
            Expression[] expressionArr = this.expressionStack;
            int i6 = this.expressionPtr;
            this.expressionPtr = i6 - 1;
            NameReference nameReference2 = (NameReference) expressionArr[i6];
            this.expressionLengthPtr--;
            nameReference = nameReference2;
        } else {
            nameReference = null;
        }
        this.identifierLengthPtr--;
        char[][] cArr = this.identifierStack;
        int i7 = this.identifierPtr;
        char[] cArr2 = cArr[i7];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i7 - 1;
        long j = jArr[i7];
        this.intPtr = i4 - 2;
        int i8 = iArr[i5];
        Annotation[][] annotationsOnDimensions = i8 == 0 ? null : getAnnotationsOnDimensions(i8);
        if (z) {
            int[] iArr2 = this.intStack;
            int i9 = this.intPtr;
            this.intPtr = i9 - 1;
            i = iArr2[i9];
            int[] iArr3 = this.typeAnnotationLengthStack;
            int i10 = this.typeAnnotationLengthPtr;
            this.typeAnnotationLengthPtr = i10 - 1;
            int i11 = iArr3[i10];
            if (i11 != 0) {
                Annotation[] annotationArr2 = this.typeAnnotationStack;
                int i12 = this.typeAnnotationPtr - i11;
                this.typeAnnotationPtr = i12;
                annotationArr = new Annotation[i11];
                System.arraycopy(annotationArr2, i12 + 1, annotationArr, 0, i11);
            }
            int[] iArr4 = this.intStack;
            int i13 = this.intPtr;
            this.intPtr = i13 - 1;
            typeReference = getTypeReference(iArr4[i13]);
            if (!z || i8 != 0) {
                if (z) {
                    typeReference = augmentTypeWithAdditionalDimensions(typeReference, 1, annotationArr != null ? new Annotation[][]{annotationArr} : null, true);
                }
                if (i8 != 0) {
                    typeReference = augmentTypeWithAdditionalDimensions(typeReference, i8, annotationsOnDimensions, false);
                }
                if (typeReference.isParameterizedTypeReference()) {
                    i2 = this.endStatementPosition;
                } else {
                    i2 = this.endPosition;
                }
                typeReference.sourceEnd = i2;
            }
            if (z) {
                if (i8 == 0) {
                    typeReference.sourceEnd = i;
                }
                typeReference.bits |= 16384;
            }
            int[] iArr5 = this.intStack;
            int i14 = this.intPtr;
            this.intPtr = i14 - 1;
            int i15 = iArr5[i14];
            if (z2) {
                int[] iArr6 = this.intStack;
                int i16 = this.intPtr;
                this.intPtr = i16 - 1;
                argument = new Receiver(cArr2, j, typeReference, nameReference, iArr6[i16] & (-1048577));
            } else {
                int[] iArr7 = this.intStack;
                int i17 = this.intPtr;
                this.intPtr = i17 - 1;
                argument = new Argument(cArr2, j, typeReference, iArr7[i17] & (-1048577));
            }
            argument.declarationSourceStart = i15;
            argument.bits |= typeReference.bits & 1048576;
            int[] iArr8 = this.expressionLengthStack;
            int i18 = this.expressionLengthPtr;
            this.expressionLengthPtr = i18 - 1;
            i3 = iArr8[i18];
            if (i3 != 0) {
                Expression[] expressionArr2 = this.expressionStack;
                int i19 = this.expressionPtr - i3;
                this.expressionPtr = i19;
                Annotation[] annotationArr3 = new Annotation[i3];
                argument.annotations = annotationArr3;
                System.arraycopy(expressionArr2, i19 + 1, annotationArr3, 0, i3);
                argument.bits |= 1048576;
                recoveredTypeCurrentRecoveryType = currentRecoveryType();
                if (recoveredTypeCurrentRecoveryType != null) {
                    recoveredTypeCurrentRecoveryType.annotationsConsumed(argument.annotations);
                }
            }
            pushOnAstStack(argument);
            this.listLength++;
            if (z) {
                if (this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
                    problemReporter().invalidUsageOfVarargs(argument);
                    return;
                } else {
                    if (!this.statementRecoveryActivated || i8 <= 0) {
                    }
                    problemReporter().illegalExtendedDimensions(argument);
                    return;
                }
            }
        }
        i = 0;
        annotationArr = null;
        int[] iArr9 = this.intStack;
        int i110 = this.intPtr;
        this.intPtr = i110 - 1;
        typeReference = getTypeReference(iArr9[i110]);
        if (!z) {
            if (z) {
                typeReference = augmentTypeWithAdditionalDimensions(typeReference, 1, annotationArr != null ? new Annotation[][]{annotationArr} : null, true);
            }
            if (i8 != 0) {
                typeReference = augmentTypeWithAdditionalDimensions(typeReference, i8, annotationsOnDimensions, false);
            }
            if (typeReference.isParameterizedTypeReference()) {
                i2 = this.endStatementPosition;
            } else {
                i2 = this.endPosition;
            }
            typeReference.sourceEnd = i2;
        } else {
            if (z) {
                typeReference = augmentTypeWithAdditionalDimensions(typeReference, 1, annotationArr != null ? new Annotation[][]{annotationArr} : null, true);
            }
            if (i8 != 0) {
                typeReference = augmentTypeWithAdditionalDimensions(typeReference, i8, annotationsOnDimensions, false);
            }
            if (typeReference.isParameterizedTypeReference()) {
                i2 = this.endStatementPosition;
            } else {
                i2 = this.endPosition;
            }
            typeReference.sourceEnd = i2;
        }
        if (z) {
            if (i8 == 0) {
                typeReference.sourceEnd = i;
            }
            typeReference.bits |= 16384;
        }
        int[] iArr10 = this.intStack;
        int i111 = this.intPtr;
        this.intPtr = i111 - 1;
        int i112 = iArr10[i111];
        if (z2) {
            int[] iArr11 = this.intStack;
            int i113 = this.intPtr;
            this.intPtr = i113 - 1;
            argument = new Receiver(cArr2, j, typeReference, nameReference, iArr11[i113] & (-1048577));
        } else {
            int[] iArr12 = this.intStack;
            int i114 = this.intPtr;
            this.intPtr = i114 - 1;
            argument = new Argument(cArr2, j, typeReference, iArr12[i114] & (-1048577));
        }
        argument.declarationSourceStart = i112;
        argument.bits |= typeReference.bits & 1048576;
        int[] iArr13 = this.expressionLengthStack;
        int i115 = this.expressionLengthPtr;
        this.expressionLengthPtr = i115 - 1;
        i3 = iArr13[i115];
        if (i3 != 0) {
            Expression[] expressionArr3 = this.expressionStack;
            int i116 = this.expressionPtr - i3;
            this.expressionPtr = i116;
            Annotation[] annotationArr4 = new Annotation[i3];
            argument.annotations = annotationArr4;
            System.arraycopy(expressionArr3, i116 + 1, annotationArr4, 0, i3);
            argument.bits |= 1048576;
            recoveredTypeCurrentRecoveryType = currentRecoveryType();
            if (recoveredTypeCurrentRecoveryType != null) {
                recoveredTypeCurrentRecoveryType.annotationsConsumed(argument.annotations);
            }
        }
        pushOnAstStack(argument);
        this.listLength++;
        if (z) {
            if (this.statementRecoveryActivated) {
            }
            if (this.statementRecoveryActivated) {
            }
        }
    }

    protected Annotation[][] getAnnotationsOnDimensions(int i) {
        Annotation[][] annotationArr = null;
        if (i > 0) {
            for (int i2 = 0; i2 < i; i2++) {
                int[] iArr = this.typeAnnotationLengthStack;
                int i3 = this.typeAnnotationLengthPtr;
                this.typeAnnotationLengthPtr = i3 - 1;
                int i4 = iArr[i3];
                if (i4 != 0) {
                    Annotation[] annotationArr2 = this.typeAnnotationStack;
                    int i5 = this.typeAnnotationPtr - i4;
                    this.typeAnnotationPtr = i5;
                    Annotation[] annotationArr3 = new Annotation[i4];
                    System.arraycopy(annotationArr2, i5 + 1, annotationArr3, 0, i4);
                    if (annotationArr == null) {
                        annotationArr = new Annotation[i][];
                    }
                    annotationArr[(i - i2) - 1] = annotationArr3;
                }
            }
        }
        return annotationArr;
    }

    protected void consumeFormalParameterList() {
        optimizedConcatNodeLists();
    }

    protected void consumeFormalParameterListopt() {
        pushOnAstLengthStack(0);
    }

    protected void consumeGenericTypeWithDiamond() {
        pushOnGenericsLengthStack(-1);
        concatGenericsLists();
        this.intPtr--;
    }

    protected void consumeImportDeclaration() {
        ImportReference importReference = (ImportReference) this.astStack[this.astPtr];
        importReference.declarationEnd = this.endStatementPosition;
        importReference.declarationSourceEnd = flushCommentsDefinedPriorTo(importReference.declarationSourceEnd);
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.currentElement = this.currentElement.add(importReference, 0);
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeImportDeclarations() {
        optimizedConcatNodeLists();
    }

    protected void consumeInsideCastExpressionLL1() {
        pushOnGenericsLengthStack(0);
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnExpressionStack(getTypeReference(0));
    }

    protected void consumeInsideCastExpressionLL1WithBounds() {
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        TypeReference[] typeReferenceArr = new TypeReference[i2 + 1];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 1, i2);
        pushOnGenericsLengthStack(0);
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        typeReferenceArr[0] = getTypeReference(0);
        for (int i4 = 0; i4 <= i2; i4++) {
            pushOnExpressionStack(typeReferenceArr[i4]);
            if (i4 > 0) {
                int[] iArr2 = this.expressionLengthStack;
                int i5 = this.expressionLengthPtr - 1;
                this.expressionLengthPtr = i5;
                iArr2[i5] = iArr2[i5] + 1;
            }
        }
    }

    protected void consumeInstanceOfExpression() {
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        Expression expression = this.expressionStack[this.expressionPtr];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        InstanceOfExpression instanceOfExpression = new InstanceOfExpression(expression, getTypeReference(iArr[i2]));
        expressionArr[i] = instanceOfExpression;
        if (instanceOfExpression.sourceEnd == 0) {
            instanceOfExpression.sourceEnd = this.scanner.startPosition - 1;
        }
    }

    protected void consumeInstanceOfExpressionWithName() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        TypeReference typeReference = getTypeReference(iArr[i]);
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        InstanceOfExpression instanceOfExpression = new InstanceOfExpression(this.expressionStack[this.expressionPtr], typeReference);
        expressionArr[i2] = instanceOfExpression;
        if (instanceOfExpression.sourceEnd == 0) {
            instanceOfExpression.sourceEnd = this.scanner.startPosition - 1;
        }
    }

    protected void consumeInterfaceDeclaration() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            dispatchDeclarationInto(i2);
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        typeDeclaration.checkConstructors(this);
        FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
        int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            FieldDeclaration fieldDeclaration = fieldDeclarationArr[i3];
            if (fieldDeclaration instanceof Initializer) {
                problemReporter().interfaceCannotHaveInitializers(typeDeclaration.f619name, fieldDeclaration);
            }
        }
        if (this.scanner.containsAssertKeyword) {
            typeDeclaration.bits |= 1;
        }
        typeDeclaration.addClinit();
        typeDeclaration.bodyEnd = this.endStatementPosition;
        if (i2 == 0 && !containsComment(typeDeclaration.bodyStart, typeDeclaration.bodyEnd)) {
            typeDeclaration.bits |= 8;
        }
        typeDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
    }

    protected void consumeInterfaceHeader() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            typeDeclaration.bodyStart = this.scanner.currentPosition;
        }
        if (this.currentElement != null) {
            this.restartRecovery = true;
        }
        this.scanner.commentPtr = -1;
    }

    protected void consumeInterfaceHeaderExtends() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.astPtr - i2;
        this.astPtr = i3;
        ASTNode[] aSTNodeArr = this.astStack;
        TypeDeclaration typeDeclaration = (TypeDeclaration) aSTNodeArr[i3];
        TypeReference[] typeReferenceArr = new TypeReference[i2];
        typeDeclaration.superInterfaces = typeReferenceArr;
        System.arraycopy(aSTNodeArr, i3 + 1, typeReferenceArr, 0, i2);
        for (TypeReference typeReference : typeDeclaration.superInterfaces) {
            typeDeclaration.bits |= typeReference.bits & 1048576;
            typeReference.bits |= 16;
        }
        typeDeclaration.bodyStart = typeDeclaration.superInterfaces[i2 - 1].sourceEnd + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
        }
    }

    protected void consumeInterfaceHeaderName1() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationUnit.compilationResult);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        if (iArr[i] != 0) {
            typeDeclaration.bits |= 256;
            markEnclosingMemberWithLocalType();
            blockReal();
        } else if (i != 0) {
            typeDeclaration.bits |= 1024;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        typeDeclaration.sourceEnd = (int) j;
        typeDeclaration.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        typeDeclaration.f619name = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        typeDeclaration.declarationSourceStart = iArr2[i3];
        int i4 = this.intPtr;
        int[] iArr3 = this.intStack;
        this.intPtr = i4 - 2;
        typeDeclaration.modifiersSourceStart = iArr3[i4 - 1];
        int[] iArr4 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        typeDeclaration.modifiers = iArr4[i5] | 512;
        if (typeDeclaration.modifiersSourceStart >= 0) {
            typeDeclaration.declarationSourceStart = typeDeclaration.modifiersSourceStart;
        }
        if ((typeDeclaration.bits & 1024) == 0 && (typeDeclaration.bits & 256) == 0 && this.compilationUnit != null && !CharOperation.equals(typeDeclaration.f619name, this.compilationUnit.getMainTypeName())) {
            typeDeclaration.bits |= 4096;
        }
        int[] iArr5 = this.expressionLengthStack;
        int i6 = this.expressionLengthPtr;
        this.expressionLengthPtr = i6 - 1;
        int i7 = iArr5[i6];
        if (i7 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i8 = this.expressionPtr - i7;
            this.expressionPtr = i8;
            Annotation[] annotationArr = new Annotation[i7];
            typeDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i8 + 1, annotationArr, 0, i7);
        }
        typeDeclaration.bodyStart = typeDeclaration.sourceEnd + 1;
        pushOnAstStack(typeDeclaration);
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = typeDeclaration.bodyStart;
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
            this.lastIgnoredToken = -1;
        }
        typeDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
    }

    protected void consumeInterfaceMemberDeclarations() {
        concatNodeLists();
    }

    protected void consumeInterfaceMemberDeclarationsopt() {
        this.nestedType--;
    }

    protected void consumeInterfaceType() {
        pushOnAstStack(getTypeReference(0));
        this.listLength++;
    }

    protected void consumeInterfaceTypeList() {
        optimizedConcatNodeLists();
    }

    protected void consumeInternalCompilationUnit() {
        if (this.compilationUnit.isPackageInfo()) {
            this.compilationUnit.types = new TypeDeclaration[1];
            this.compilationUnit.createPackageInfoType();
        }
    }

    protected void consumeInternalCompilationUnitWithTypes() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            if (this.compilationUnit.isPackageInfo()) {
                this.compilationUnit.types = new TypeDeclaration[i2 + 1];
                int i3 = this.astPtr - i2;
                this.astPtr = i3;
                System.arraycopy(this.astStack, i3 + 1, this.compilationUnit.types, 1, i2);
                this.compilationUnit.createPackageInfoType();
                return;
            }
            this.compilationUnit.types = new TypeDeclaration[i2];
            int i4 = this.astPtr - i2;
            this.astPtr = i4;
            System.arraycopy(this.astStack, i4 + 1, this.compilationUnit.types, 0, i2);
        }
    }

    protected void consumeInvalidAnnotationTypeDeclaration() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (!this.statementRecoveryActivated) {
            problemReporter().illegalLocalTypeDeclaration(typeDeclaration);
        }
        this.astPtr--;
        pushOnAstLengthStack(-1);
        concatNodeLists();
    }

    protected void consumeInvalidConstructorDeclaration() {
        ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) this.astStack[this.astPtr];
        constructorDeclaration.bodyEnd = this.endPosition;
        constructorDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        constructorDeclaration.modifiers |= 16777216;
    }

    protected void consumeInvalidConstructorDeclaration(boolean z) {
        if (z) {
            this.intPtr--;
        }
        if (z) {
            this.realBlockPtr--;
        }
        if (z) {
            int[] iArr = this.astLengthStack;
            int i = this.astLengthPtr;
            this.astLengthPtr = i - 1;
            int i2 = iArr[i];
            if (i2 != 0) {
                this.astPtr -= i2;
            }
        }
        ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) this.astStack[this.astPtr];
        constructorDeclaration.bodyEnd = this.endStatementPosition;
        constructorDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        if (z) {
            return;
        }
        constructorDeclaration.modifiers |= 16777216;
    }

    protected void consumeInvalidEnumDeclaration() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (!this.statementRecoveryActivated) {
            problemReporter().illegalLocalTypeDeclaration(typeDeclaration);
        }
        this.astPtr--;
        pushOnAstLengthStack(-1);
        concatNodeLists();
    }

    protected void consumeInvalidInterfaceDeclaration() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (!this.statementRecoveryActivated) {
            problemReporter().illegalLocalTypeDeclaration(typeDeclaration);
        }
        this.astPtr--;
        pushOnAstLengthStack(-1);
        concatNodeLists();
    }

    protected void consumeInterfaceMethodDeclaration(boolean z) {
        int i;
        boolean z2 = true;
        Statement[] statementArr = null;
        if (z) {
            i = 0;
        } else {
            this.intPtr -= 2;
            int[] iArr = this.realBlockStack;
            int i2 = this.realBlockPtr;
            this.realBlockPtr = i2 - 1;
            i = iArr[i2];
            int[] iArr2 = this.astLengthStack;
            int i3 = this.astLengthPtr;
            this.astLengthPtr = i3 - 1;
            int i4 = iArr2[i3];
            if (i4 != 0) {
                if (this.options.ignoreMethodBodies) {
                    this.astPtr -= i4;
                } else {
                    ASTNode[] aSTNodeArr = this.astStack;
                    int i5 = this.astPtr - i4;
                    this.astPtr = i5;
                    Statement[] statementArr2 = new Statement[i4];
                    System.arraycopy(aSTNodeArr, i5 + 1, statementArr2, 0, i4);
                    statementArr = statementArr2;
                }
            }
        }
        MethodDeclaration methodDeclaration = (MethodDeclaration) this.astStack[this.astPtr];
        methodDeclaration.statements = statementArr;
        methodDeclaration.explicitDeclarations = i;
        methodDeclaration.bodyEnd = this.endPosition;
        methodDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        boolean z3 = (methodDeclaration.modifiers & 65536) != 0;
        boolean z4 = (methodDeclaration.modifiers & 8) != 0;
        if (!z3 && !z4) {
            z2 = false;
        }
        if (!this.parsingJava8Plus) {
            if (z3) {
                problemReporter().defaultMethodsNotBelow18(methodDeclaration);
            }
            if (z4) {
                problemReporter().staticInterfaceMethodsNotBelow18(methodDeclaration);
            }
        } else if (z2 && z) {
            methodDeclaration.modifiers |= 16777216;
        }
        if (z2 || this.statementRecoveryActivated || z) {
            return;
        }
        problemReporter().abstractMethodNeedingNoBody(methodDeclaration);
    }

    protected void consumeLeftParen() {
        pushOnIntStack(this.lParenPos);
    }

    protected void consumeLocalVariableDeclaration() {
        int i = this.astLengthStack[this.astLengthPtr];
        int i2 = this.astPtr - this.variablesCounter[this.nestedType];
        ASTNode[] aSTNodeArr = this.astStack;
        System.arraycopy(aSTNodeArr, i2 + 1, aSTNodeArr, i2, i);
        this.astPtr--;
        int[] iArr = this.astLengthStack;
        int i3 = this.astLengthPtr - 1;
        this.astLengthPtr = i3;
        iArr[i3] = i;
        this.variablesCounter[this.nestedType] = 0;
        this.forStartPosition = 0;
    }

    protected void consumeLocalVariableDeclarationStatement() {
        Expression singleNameReference;
        int i = this.astLengthStack[this.astLengthPtr];
        if (i == 1) {
            LocalDeclaration localDeclaration = (LocalDeclaration) this.astStack[this.astPtr];
            if (localDeclaration.isRecoveredFromLoneIdentifier()) {
                if (localDeclaration.type instanceof QualifiedTypeReference) {
                    QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) localDeclaration.type;
                    singleNameReference = new QualifiedNameReference(qualifiedTypeReference.tokens, qualifiedTypeReference.sourcePositions, 0, 0);
                } else {
                    singleNameReference = new SingleNameReference(localDeclaration.type.getLastToken(), 0L);
                }
                singleNameReference.sourceStart = localDeclaration.type.sourceStart;
                singleNameReference.sourceEnd = localDeclaration.type.sourceEnd;
                SingleNameReference singleNameReference2 = new SingleNameReference(localDeclaration.f617name, 0L);
                singleNameReference2.sourceStart = localDeclaration.sourceStart;
                singleNameReference2.sourceEnd = localDeclaration.sourceEnd;
                Assignment assignment = new Assignment(singleNameReference, singleNameReference2, 0);
                int i2 = this.endStatementPosition;
                if (i2 == localDeclaration.sourceEnd) {
                    i2++;
                }
                assignment.sourceEnd = i2;
                assignment.statementEnd = i2;
                this.astStack[this.astPtr] = assignment;
                RecoveryScanner recoveryScanner = this.recoveryScanner;
                if (recoveryScanner != null) {
                    RecoveryScannerData data = recoveryScanner.getData();
                    int i3 = data.insertedTokensPtr;
                    while (i3 > 0 && data.insertedTokensPosition[i3] == data.insertedTokensPosition[i3 - 1]) {
                        i3--;
                    }
                    if (i3 >= 0) {
                        this.recoveryScanner.insertTokenAhead(70, i3);
                    }
                }
                if (this.currentElement != null) {
                    this.lastCheckPoint = assignment.sourceEnd + 1;
                    this.currentElement = this.currentElement.add(assignment, 0);
                    return;
                }
                return;
            }
        }
        int[] iArr = this.realBlockStack;
        int i4 = this.realBlockPtr;
        iArr[i4] = iArr[i4] + 1;
        for (int i5 = i - 1; i5 >= 0; i5--) {
            LocalDeclaration localDeclaration2 = (LocalDeclaration) this.astStack[this.astPtr - i5];
            localDeclaration2.declarationSourceEnd = this.endStatementPosition;
            localDeclaration2.declarationEnd = this.endStatementPosition;
        }
    }

    protected void consumeMarkerAnnotation(boolean z) {
        int i = this.identifierPtr;
        TypeReference annotationType = getAnnotationType();
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        MarkerAnnotation markerAnnotation = new MarkerAnnotation(annotationType, iArr[i2]);
        markerAnnotation.declarationSourceEnd = markerAnnotation.sourceEnd;
        if (z) {
            pushOnTypeAnnotationStack(markerAnnotation);
        } else {
            pushOnExpressionStack(markerAnnotation);
        }
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfAnnotation(markerAnnotation);
        }
        this.recordStringLiterals = true;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || !(recoveredElement instanceof RecoveredAnnotation)) {
            return;
        }
        this.currentElement = ((RecoveredAnnotation) recoveredElement).addAnnotation(markerAnnotation, i);
    }

    protected void consumeMemberValueArrayInitializer() {
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        arrayInitializer(iArr[i]);
    }

    protected void consumeMemberValueAsName() {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
    }

    protected void consumeMemberValuePair() {
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        char[] cArr2 = cArr[i];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        this.identifierLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        this.expressionLengthPtr--;
        pushOnAstStack(new MemberValuePair(cArr2, (int) (j >>> 32), (int) j, expression));
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || !(recoveredElement instanceof RecoveredAnnotation)) {
            return;
        }
        ((RecoveredAnnotation) recoveredElement).setKind(1);
    }

    protected void consumeMemberValuePairs() {
        concatNodeLists();
    }

    protected void consumeMemberValues() {
        concatExpressionLists();
    }

    protected void consumeMethodBody() {
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] - 1;
    }

    protected void consumeMethodDeclaration(boolean z, boolean z2) {
        if (z) {
            this.intPtr -= 2;
        }
        int i = 0;
        Statement[] statementArr = null;
        if (z) {
            int[] iArr = this.realBlockStack;
            int i2 = this.realBlockPtr;
            this.realBlockPtr = i2 - 1;
            int i3 = iArr[i2];
            int[] iArr2 = this.astLengthStack;
            int i4 = this.astLengthPtr;
            this.astLengthPtr = i4 - 1;
            int i5 = iArr2[i4];
            if (i5 == 0) {
                i = i3;
            } else if (this.options.ignoreMethodBodies) {
                this.astPtr -= i5;
                i = i3;
            } else {
                ASTNode[] aSTNodeArr = this.astStack;
                int i6 = this.astPtr - i5;
                this.astPtr = i6;
                Statement[] statementArr2 = new Statement[i5];
                System.arraycopy(aSTNodeArr, i6 + 1, statementArr2, 0, i5);
                i = i3;
                statementArr = statementArr2;
            }
        }
        MethodDeclaration methodDeclaration = (MethodDeclaration) this.astStack[this.astPtr];
        methodDeclaration.statements = statementArr;
        methodDeclaration.explicitDeclarations = i;
        if (!z) {
            methodDeclaration.modifiers |= 16777216;
        } else if ((!this.diet || this.dietInt != 0) && statementArr == null && !containsComment(methodDeclaration.bodyStart, this.endPosition)) {
            methodDeclaration.bits |= 8;
        }
        methodDeclaration.bodyEnd = this.endPosition;
        methodDeclaration.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        if (!z2 || this.tolerateDefaultClassMethods) {
            return;
        }
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_8) {
            problemReporter().defaultModifierIllegallySpecified(methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
        } else {
            problemReporter().illegalModifierForMethod(methodDeclaration);
        }
    }

    protected void consumeMethodHeader() {
        AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) this.astStack[this.astPtr];
        if (this.currentToken == 49) {
            abstractMethodDeclaration.bodyStart = this.scanner.currentPosition;
        }
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            int i = this.currentToken;
            if (i == 28) {
                abstractMethodDeclaration.modifiers |= 16777216;
                abstractMethodDeclaration.declarationSourceEnd = this.scanner.currentPosition - 1;
                abstractMethodDeclaration.bodyEnd = this.scanner.currentPosition - 1;
                if (this.currentElement.parseTree() == abstractMethodDeclaration && this.currentElement.parent != null) {
                    this.currentElement = this.currentElement.parent;
                }
            } else if (i == 49 && (recoveredElement instanceof RecoveredMethod) && ((RecoveredMethod) recoveredElement).methodDeclaration != abstractMethodDeclaration) {
                this.ignoreNextOpeningBrace = true;
                this.currentElement.bracketBalance++;
            }
            this.restartRecovery = true;
        }
    }

    protected void consumeMethodHeaderDefaultValue() {
        MethodDeclaration methodDeclaration = (MethodDeclaration) this.astStack[this.astPtr];
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        if (iArr[i] == 1) {
            this.intPtr -= 2;
            if (methodDeclaration.isAnnotationMethod()) {
                ((AnnotationMethodDeclaration) methodDeclaration).defaultValue = this.expressionStack[this.expressionPtr];
                methodDeclaration.modifiers |= 131072;
            }
            this.expressionPtr--;
            this.recordStringLiterals = true;
        }
        if (this.currentElement == null || !methodDeclaration.isAnnotationMethod()) {
            return;
        }
        this.currentElement.updateSourceEndIfNecessary(((AnnotationMethodDeclaration) methodDeclaration).defaultValue.sourceEnd);
    }

    protected void consumeMethodHeaderExtendedDims() {
        MethodDeclaration methodDeclaration = (MethodDeclaration) this.astStack[this.astPtr];
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        if (methodDeclaration.isAnnotationMethod()) {
            ((AnnotationMethodDeclaration) methodDeclaration).extendedDimensions = i2;
        }
        if (i2 != 0) {
            methodDeclaration.sourceEnd = this.endPosition;
            methodDeclaration.returnType = augmentTypeWithAdditionalDimensions(methodDeclaration.returnType, i2, getAnnotationsOnDimensions(i2), false);
            methodDeclaration.bits |= methodDeclaration.returnType.bits & 1048576;
            if (this.currentToken == 49) {
                methodDeclaration.bodyStart = this.endPosition + 1;
            }
            if (this.currentElement != null) {
                this.lastCheckPoint = methodDeclaration.bodyStart;
            }
        }
    }

    protected void consumeMethodHeaderName(boolean z) {
        MethodDeclaration methodDeclaration;
        if (z) {
            methodDeclaration = new AnnotationMethodDeclaration(this.compilationUnit.compilationResult);
            this.recordStringLiterals = false;
        } else {
            methodDeclaration = new MethodDeclaration(this.compilationUnit.compilationResult);
        }
        methodDeclaration.selector = this.identifierStack[this.identifierPtr];
        long[] jArr = this.identifierPositionStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        methodDeclaration.returnType = getTypeReference(iArr[i2]);
        methodDeclaration.bits |= methodDeclaration.returnType.bits & 1048576;
        int[] iArr2 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        methodDeclaration.declarationSourceStart = iArr2[i3];
        int[] iArr3 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        methodDeclaration.modifiers = iArr3[i4];
        int[] iArr4 = this.expressionLengthStack;
        int i5 = this.expressionLengthPtr;
        this.expressionLengthPtr = i5 - 1;
        int i6 = iArr4[i5];
        if (i6 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i7 = this.expressionPtr - i6;
            this.expressionPtr = i7;
            Annotation[] annotationArr = new Annotation[i6];
            methodDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i7 + 1, annotationArr, 0, i6);
        }
        methodDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        methodDeclaration.sourceStart = (int) (j >>> 32);
        pushOnAstStack(methodDeclaration);
        methodDeclaration.sourceEnd = this.lParenPos;
        methodDeclaration.bodyStart = this.lParenPos + 1;
        this.listLength = 0;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            if ((recoveredElement instanceof RecoveredType) || Util.getLineNumber(methodDeclaration.returnType.sourceStart, this.scanner.lineEnds, 0, this.scanner.linePtr) == Util.getLineNumber(methodDeclaration.sourceStart, this.scanner.lineEnds, 0, this.scanner.linePtr)) {
                this.lastCheckPoint = methodDeclaration.bodyStart;
                this.currentElement = this.currentElement.add(methodDeclaration, 0);
                this.lastIgnoredToken = -1;
            } else {
                this.lastCheckPoint = methodDeclaration.sourceStart;
                this.restartRecovery = true;
            }
        }
    }

    protected void consumeMethodHeaderNameWithTypeParameters(boolean z) {
        MethodDeclaration methodDeclaration;
        if (z) {
            methodDeclaration = new AnnotationMethodDeclaration(this.compilationUnit.compilationResult);
            this.recordStringLiterals = false;
        } else {
            methodDeclaration = new MethodDeclaration(this.compilationUnit.compilationResult);
        }
        methodDeclaration.selector = this.identifierStack[this.identifierPtr];
        long[] jArr = this.identifierPositionStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        TypeReference typeReference = getTypeReference(iArr[i2]);
        if (z) {
            rejectIllegalLeadingTypeAnnotations(typeReference);
        }
        methodDeclaration.returnType = typeReference;
        methodDeclaration.bits |= typeReference.bits & 1048576;
        int[] iArr2 = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr2[i3];
        int i5 = this.genericsPtr - i4;
        this.genericsPtr = i5;
        TypeParameter[] typeParameterArr = new TypeParameter[i4];
        methodDeclaration.typeParameters = typeParameterArr;
        System.arraycopy(this.genericsStack, i5 + 1, typeParameterArr, 0, i4);
        int[] iArr3 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        methodDeclaration.declarationSourceStart = iArr3[i6];
        int[] iArr4 = this.intStack;
        int i7 = this.intPtr;
        this.intPtr = i7 - 1;
        methodDeclaration.modifiers = iArr4[i7];
        int[] iArr5 = this.expressionLengthStack;
        int i8 = this.expressionLengthPtr;
        this.expressionLengthPtr = i8 - 1;
        int i9 = iArr5[i8];
        if (i9 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i10 = this.expressionPtr - i9;
            this.expressionPtr = i10;
            Annotation[] annotationArr = new Annotation[i9];
            methodDeclaration.annotations = annotationArr;
            System.arraycopy(expressionArr, i10 + 1, annotationArr, 0, i9);
        }
        methodDeclaration.javadoc = this.javadoc;
        this.javadoc = null;
        methodDeclaration.sourceStart = (int) (j >>> 32);
        pushOnAstStack(methodDeclaration);
        methodDeclaration.sourceEnd = this.lParenPos;
        methodDeclaration.bodyStart = this.lParenPos + 1;
        this.listLength = 0;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            boolean z2 = recoveredElement instanceof RecoveredType;
            if (z2 || Util.getLineNumber(methodDeclaration.returnType.sourceStart, this.scanner.lineEnds, 0, this.scanner.linePtr) == Util.getLineNumber(methodDeclaration.sourceStart, this.scanner.lineEnds, 0, this.scanner.linePtr)) {
                if (z2) {
                    ((RecoveredType) this.currentElement).pendingTypeParameters = null;
                }
                this.lastCheckPoint = methodDeclaration.bodyStart;
                this.currentElement = this.currentElement.add(methodDeclaration, 0);
                this.lastIgnoredToken = -1;
                return;
            }
            this.lastCheckPoint = methodDeclaration.sourceStart;
            this.restartRecovery = true;
        }
    }

    protected void consumeMethodHeaderRightParen() {
        int i;
        int[] iArr = this.astLengthStack;
        int i2 = this.astLengthPtr;
        this.astLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.astPtr - i3;
        this.astPtr = i4;
        AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) this.astStack[i4];
        abstractMethodDeclaration.sourceEnd = this.rParenPos;
        if (i3 != 0) {
            Argument argument = (Argument) this.astStack[this.astPtr + 1];
            if (argument.isReceiver()) {
                abstractMethodDeclaration.receiver = (Receiver) argument;
                if (i3 > 1) {
                    ASTNode[] aSTNodeArr = this.astStack;
                    int i5 = this.astPtr + 2;
                    int i6 = i3 - 1;
                    Argument[] argumentArr = new Argument[i6];
                    abstractMethodDeclaration.arguments = argumentArr;
                    System.arraycopy(aSTNodeArr, i5, argumentArr, 0, i6);
                }
                Annotation[] annotationArr = argument.annotations;
                if (annotationArr != null && annotationArr.length > 0) {
                    TypeReference typeReference = argument.type;
                    if (typeReference.annotations == null) {
                        typeReference.bits |= 1048576;
                        typeReference.annotations = new Annotation[typeReference.getAnnotatableLevels()][];
                        abstractMethodDeclaration.bits |= 1048576;
                    }
                    typeReference.annotations[0] = annotationArr;
                    int i7 = annotationArr[0].sourceStart;
                    if (typeReference.sourceStart > i7) {
                        typeReference.sourceStart = i7;
                    }
                    argument.annotations = null;
                }
                abstractMethodDeclaration.bits = (argument.type.bits & 1048576) | abstractMethodDeclaration.bits;
            } else {
                ASTNode[] aSTNodeArr2 = this.astStack;
                int i8 = this.astPtr + 1;
                Argument[] argumentArr2 = new Argument[i3];
                abstractMethodDeclaration.arguments = argumentArr2;
                System.arraycopy(aSTNodeArr2, i8, argumentArr2, 0, i3);
                int length = abstractMethodDeclaration.arguments.length;
                for (int i9 = 0; i9 < length; i9++) {
                    if ((abstractMethodDeclaration.arguments[i9].bits & 1048576) != 0) {
                        abstractMethodDeclaration.bits |= 1048576;
                        break;
                    }
                }
            }
        }
        abstractMethodDeclaration.bodyStart = this.rParenPos + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = abstractMethodDeclaration.bodyStart;
            if (this.currentElement.parseTree() != abstractMethodDeclaration && abstractMethodDeclaration.isConstructor()) {
                if (i3 != 0 || (i = this.currentToken) == 49 || i == 112) {
                    this.currentElement = this.currentElement.add(abstractMethodDeclaration, 0);
                    this.lastIgnoredToken = -1;
                }
            }
        }
    }

    protected void consumeMethodHeaderThrowsClause() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.astPtr - i2;
        this.astPtr = i3;
        ASTNode[] aSTNodeArr = this.astStack;
        AbstractMethodDeclaration abstractMethodDeclaration = (AbstractMethodDeclaration) aSTNodeArr[i3];
        TypeReference[] typeReferenceArr = new TypeReference[i2];
        abstractMethodDeclaration.thrownExceptions = typeReferenceArr;
        System.arraycopy(aSTNodeArr, i3 + 1, typeReferenceArr, 0, i2);
        int i4 = i2 - 1;
        abstractMethodDeclaration.sourceEnd = abstractMethodDeclaration.thrownExceptions[i4].sourceEnd;
        abstractMethodDeclaration.bodyStart = abstractMethodDeclaration.thrownExceptions[i4].sourceEnd + 1;
        this.listLength = 0;
        if (this.currentElement != null) {
            this.lastCheckPoint = abstractMethodDeclaration.bodyStart;
        }
    }

    protected void consumeMethodInvocationName() {
        MessageSend messageSendNewMessageSend = newMessageSend();
        messageSendNewMessageSend.sourceEnd = this.rParenPos;
        long j = this.identifierPositionStack[this.identifierPtr];
        messageSendNewMessageSend.nameSourcePosition = j;
        messageSendNewMessageSend.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        messageSendNewMessageSend.selector = cArr[i];
        int[] iArr = this.identifierLengthStack;
        int i2 = this.identifierLengthPtr;
        int i3 = iArr[i2];
        if (i3 == 1) {
            messageSendNewMessageSend.receiver = ThisReference.implicitThis();
            this.identifierLengthPtr--;
        } else {
            iArr[i2] = i3 - 1;
            messageSendNewMessageSend.receiver = getUnspecifiedReference();
            messageSendNewMessageSend.sourceStart = messageSendNewMessageSend.receiver.sourceStart;
        }
        int[] iArr2 = this.typeAnnotationLengthStack;
        int i4 = this.typeAnnotationLengthPtr;
        this.typeAnnotationLengthPtr = i4 - 1;
        int i5 = iArr2[i4];
        if (i5 != 0) {
            Annotation[] annotationArr = this.typeAnnotationStack;
            int i6 = this.typeAnnotationPtr - i5;
            this.typeAnnotationPtr = i6;
            Annotation[] annotationArr2 = new Annotation[i5];
            System.arraycopy(annotationArr, i6 + 1, annotationArr2, 0, i5);
            problemReporter().misplacedTypeAnnotations(annotationArr2[0], annotationArr2[i5 - 1]);
        }
        pushOnExpressionStack(messageSendNewMessageSend);
        consumeInvocationExpression();
    }

    protected void consumeMethodInvocationNameWithTypeArguments() {
        MessageSend messageSendNewMessageSendWithTypeArguments = newMessageSendWithTypeArguments();
        messageSendNewMessageSendWithTypeArguments.sourceEnd = this.rParenPos;
        long j = this.identifierPositionStack[this.identifierPtr];
        messageSendNewMessageSendWithTypeArguments.nameSourcePosition = j;
        messageSendNewMessageSendWithTypeArguments.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        messageSendNewMessageSendWithTypeArguments.selector = cArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i2 = this.genericsLengthPtr;
        this.genericsLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.genericsPtr - i3;
        this.genericsPtr = i4;
        TypeReference[] typeReferenceArr = new TypeReference[i3];
        messageSendNewMessageSendWithTypeArguments.typeArguments = typeReferenceArr;
        System.arraycopy(this.genericsStack, i4 + 1, typeReferenceArr, 0, i3);
        this.intPtr--;
        messageSendNewMessageSendWithTypeArguments.receiver = getUnspecifiedReference();
        messageSendNewMessageSendWithTypeArguments.sourceStart = messageSendNewMessageSendWithTypeArguments.receiver.sourceStart;
        pushOnExpressionStack(messageSendNewMessageSendWithTypeArguments);
        consumeInvocationExpression();
    }

    protected void consumeMethodInvocationPrimary() {
        MessageSend messageSendNewMessageSend = newMessageSend();
        long j = this.identifierPositionStack[this.identifierPtr];
        messageSendNewMessageSend.nameSourcePosition = j;
        messageSendNewMessageSend.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        messageSendNewMessageSend.selector = cArr[i];
        this.identifierLengthPtr--;
        messageSendNewMessageSend.receiver = this.expressionStack[this.expressionPtr];
        messageSendNewMessageSend.sourceStart = messageSendNewMessageSend.receiver.sourceStart;
        messageSendNewMessageSend.sourceEnd = this.rParenPos;
        this.expressionStack[this.expressionPtr] = messageSendNewMessageSend;
        consumeInvocationExpression();
    }

    protected void consumeMethodInvocationPrimaryWithTypeArguments() {
        MessageSend messageSendNewMessageSendWithTypeArguments = newMessageSendWithTypeArguments();
        long j = this.identifierPositionStack[this.identifierPtr];
        messageSendNewMessageSendWithTypeArguments.nameSourcePosition = j;
        messageSendNewMessageSendWithTypeArguments.sourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        messageSendNewMessageSendWithTypeArguments.selector = cArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i2 = this.genericsLengthPtr;
        this.genericsLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.genericsPtr - i3;
        this.genericsPtr = i4;
        TypeReference[] typeReferenceArr = new TypeReference[i3];
        messageSendNewMessageSendWithTypeArguments.typeArguments = typeReferenceArr;
        System.arraycopy(this.genericsStack, i4 + 1, typeReferenceArr, 0, i3);
        this.intPtr--;
        messageSendNewMessageSendWithTypeArguments.receiver = this.expressionStack[this.expressionPtr];
        messageSendNewMessageSendWithTypeArguments.sourceStart = messageSendNewMessageSendWithTypeArguments.receiver.sourceStart;
        messageSendNewMessageSendWithTypeArguments.sourceEnd = this.rParenPos;
        this.expressionStack[this.expressionPtr] = messageSendNewMessageSendWithTypeArguments;
        consumeInvocationExpression();
    }

    protected void consumeMethodInvocationSuper() {
        MessageSend messageSendNewMessageSend = newMessageSend();
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        messageSendNewMessageSend.sourceStart = iArr[i];
        messageSendNewMessageSend.sourceEnd = this.rParenPos;
        messageSendNewMessageSend.nameSourcePosition = this.identifierPositionStack[this.identifierPtr];
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        messageSendNewMessageSend.selector = cArr[i2];
        this.identifierLengthPtr--;
        messageSendNewMessageSend.receiver = new SuperReference(messageSendNewMessageSend.sourceStart, this.endPosition);
        pushOnExpressionStack(messageSendNewMessageSend);
        consumeInvocationExpression();
    }

    protected void consumeMethodInvocationSuperWithTypeArguments() {
        MessageSend messageSendNewMessageSendWithTypeArguments = newMessageSendWithTypeArguments();
        this.intPtr--;
        messageSendNewMessageSendWithTypeArguments.sourceEnd = this.rParenPos;
        messageSendNewMessageSendWithTypeArguments.nameSourcePosition = this.identifierPositionStack[this.identifierPtr];
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        messageSendNewMessageSendWithTypeArguments.selector = cArr[i];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i2 = this.genericsLengthPtr;
        this.genericsLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        int i4 = this.genericsPtr - i3;
        this.genericsPtr = i4;
        TypeReference[] typeReferenceArr = new TypeReference[i3];
        messageSendNewMessageSendWithTypeArguments.typeArguments = typeReferenceArr;
        System.arraycopy(this.genericsStack, i4 + 1, typeReferenceArr, 0, i3);
        int[] iArr2 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        messageSendNewMessageSendWithTypeArguments.sourceStart = iArr2[i5];
        messageSendNewMessageSendWithTypeArguments.receiver = new SuperReference(messageSendNewMessageSendWithTypeArguments.sourceStart, this.endPosition);
        pushOnExpressionStack(messageSendNewMessageSendWithTypeArguments);
        consumeInvocationExpression();
    }

    protected void consumeModifiers() {
        int i = this.modifiersSourceStart;
        checkComment();
        pushOnIntStack(this.modifiers);
        if (this.modifiersSourceStart >= i) {
            this.modifiersSourceStart = i;
        }
        pushOnIntStack(this.modifiersSourceStart);
        resetModifiers();
    }

    protected void consumeModifiers2() {
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        int i2 = i - 1;
        int i3 = iArr[i2];
        this.expressionLengthPtr = i - 1;
        iArr[i2] = i3 + iArr[i];
    }

    protected void consumeMultipleResources() {
        concatNodeLists();
    }

    protected void consumeTypeAnnotation() {
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_8 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfTypeAnnotations(this.typeAnnotationStack[this.typeAnnotationPtr]);
        }
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        this.dimensions = iArr[i];
    }

    protected void consumeOneMoreTypeAnnotation() {
        int[] iArr = this.typeAnnotationLengthStack;
        int i = this.typeAnnotationLengthPtr - 1;
        this.typeAnnotationLengthPtr = i;
        iArr[i] = iArr[i] + 1;
    }

    protected void consumeNameArrayType() {
        pushOnGenericsLengthStack(0);
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
    }

    protected void consumeNestedMethod() {
        jumpOverMethodBody();
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        pushOnIntStack(this.scanner.currentPosition);
        consumeOpenBlock();
    }

    protected void consumeNestedType() {
        int[] iArr = this.nestedMethod;
        int length = iArr.length;
        int i = this.nestedType + 1;
        this.nestedType = i;
        if (i >= length) {
            int i2 = length + 30;
            int[] iArr2 = new int[i2];
            this.nestedMethod = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
            int[] iArr3 = this.variablesCounter;
            int[] iArr4 = new int[i2];
            this.variablesCounter = iArr4;
            System.arraycopy(iArr3, 0, iArr4, 0, length);
        }
        int[] iArr5 = this.nestedMethod;
        int i3 = this.nestedType;
        iArr5[i3] = 0;
        this.variablesCounter[i3] = 0;
    }

    protected void consumeNormalAnnotation(boolean z) {
        int i = this.identifierPtr;
        TypeReference annotationType = getAnnotationType();
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        NormalAnnotation normalAnnotation = new NormalAnnotation(annotationType, iArr[i2]);
        int[] iArr2 = this.astLengthStack;
        int i3 = this.astLengthPtr;
        this.astLengthPtr = i3 - 1;
        int i4 = iArr2[i3];
        if (i4 != 0) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i5 = this.astPtr - i4;
            this.astPtr = i5;
            MemberValuePair[] memberValuePairArr = new MemberValuePair[i4];
            normalAnnotation.memberValuePairs = memberValuePairArr;
            System.arraycopy(aSTNodeArr, i5 + 1, memberValuePairArr, 0, i4);
        }
        normalAnnotation.declarationSourceEnd = this.rParenPos;
        if (z) {
            pushOnTypeAnnotationStack(normalAnnotation);
        } else {
            pushOnExpressionStack(normalAnnotation);
        }
        if (this.currentElement != null) {
            annotationRecoveryCheckPoint(normalAnnotation.sourceStart, normalAnnotation.declarationSourceEnd);
            RecoveredElement recoveredElement = this.currentElement;
            if (recoveredElement instanceof RecoveredAnnotation) {
                this.currentElement = ((RecoveredAnnotation) recoveredElement).addAnnotation(normalAnnotation, i);
            }
        }
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfAnnotation(normalAnnotation);
        }
        this.recordStringLiterals = true;
    }

    protected void consumeOneDimLoop(boolean z) {
        this.dimensions++;
        if (z) {
            return;
        }
        pushOnTypeAnnotationLengthStack(0);
    }

    protected void consumeOnlySynchronized() {
        pushOnIntStack(this.synchronizedBlockSourceStart);
        resetModifiers();
        this.expressionLengthPtr--;
    }

    protected void consumeOnlyTypeArguments() {
        if (this.statementRecoveryActivated || this.options.sourceLevel >= ClassFileConstants.JDK1_5 || this.lastErrorEndPositionBeforeRecovery >= this.scanner.currentPosition) {
            return;
        }
        int i = this.genericsLengthStack[this.genericsLengthPtr];
        ProblemReporter problemReporter = problemReporter();
        ASTNode[] aSTNodeArr = this.genericsStack;
        int i2 = this.genericsPtr;
        problemReporter.invalidUsageOfTypeArguments((TypeReference) aSTNodeArr[(i2 - i) + 1], (TypeReference) aSTNodeArr[i2]);
    }

    protected void consumeOpenBlock() {
        pushOnIntStack(this.scanner.startPosition);
        int[] iArr = this.realBlockStack;
        int length = iArr.length;
        int i = this.realBlockPtr + 1;
        this.realBlockPtr = i;
        if (i >= length) {
            int[] iArr2 = new int[length + 255];
            this.realBlockStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.realBlockStack[this.realBlockPtr] = 0;
    }

    protected void consumePackageComment() {
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5) {
            checkComment();
            resetModifiers();
        }
    }

    protected void consumePackageDeclaration() {
        ImportReference importReference = this.compilationUnit.currentPackage;
        this.compilationUnit.javadoc = this.javadoc;
        this.javadoc = null;
        importReference.declarationEnd = this.endStatementPosition;
        importReference.declarationSourceEnd = flushCommentsDefinedPriorTo(importReference.declarationSourceEnd);
    }

    protected void consumePackageDeclarationName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        char[][] cArr = new char[i2][];
        int i3 = this.identifierPtr - i2;
        this.identifierPtr = i3;
        long[] jArr = new long[i2];
        char[][] cArr2 = this.identifierStack;
        int i4 = i3 + 1;
        this.identifierPtr = i4;
        System.arraycopy(cArr2, i4, cArr, 0, i2);
        long[] jArr2 = this.identifierPositionStack;
        int i5 = this.identifierPtr;
        this.identifierPtr = i5 - 1;
        System.arraycopy(jArr2, i5, jArr, 0, i2);
        ImportReference importReference = new ImportReference(cArr, jArr, false, 0);
        this.compilationUnit.currentPackage = importReference;
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        int[] iArr2 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        importReference.declarationSourceStart = iArr2[i6];
        Javadoc javadoc = this.javadoc;
        if (javadoc != null) {
            importReference.declarationSourceStart = javadoc.sourceStart;
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.restartRecovery = true;
        }
    }

    protected void consumePackageDeclarationNameWithModifiers() {
        int i;
        int[] iArr = this.identifierLengthStack;
        int i2 = this.identifierLengthPtr;
        this.identifierLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        char[][] cArr = new char[i3][];
        int i4 = this.identifierPtr - i3;
        this.identifierPtr = i4;
        long[] jArr = new long[i3];
        char[][] cArr2 = this.identifierStack;
        int i5 = i4 + 1;
        this.identifierPtr = i5;
        System.arraycopy(cArr2, i5, cArr, 0, i3);
        long[] jArr2 = this.identifierPositionStack;
        int i6 = this.identifierPtr;
        this.identifierPtr = i6 - 1;
        System.arraycopy(jArr2, i6, jArr, 0, i3);
        int[] iArr2 = this.intStack;
        int i7 = this.intPtr;
        int i8 = i7 - 1;
        this.intPtr = i8;
        int i9 = iArr2[i7];
        this.intPtr = i7 - 2;
        int i10 = iArr2[i8];
        ImportReference importReference = new ImportReference(cArr, jArr, false, i10);
        this.compilationUnit.currentPackage = importReference;
        int[] iArr3 = this.expressionLengthStack;
        int i11 = this.expressionLengthPtr;
        this.expressionLengthPtr = i11 - 1;
        int i12 = iArr3[i11];
        if (i12 != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i13 = this.expressionPtr - i12;
            this.expressionPtr = i13;
            Annotation[] annotationArr = new Annotation[i12];
            importReference.annotations = annotationArr;
            System.arraycopy(expressionArr, i13 + 1, annotationArr, 0, i12);
            importReference.declarationSourceStart = i9;
            int[] iArr4 = this.intStack;
            int i14 = this.intPtr;
            this.intPtr = i14 - 1;
            i = iArr4[i14] - 2;
        } else {
            int[] iArr5 = this.intStack;
            int i15 = this.intPtr;
            this.intPtr = i15 - 1;
            importReference.declarationSourceStart = iArr5[i15];
            i = importReference.declarationSourceStart - 2;
            Javadoc javadoc = this.javadoc;
            if (javadoc != null) {
                importReference.declarationSourceStart = javadoc.sourceStart;
            }
        }
        if (i10 != 0) {
            problemReporter().illegalModifiers(i9, i);
        }
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.restartRecovery = true;
        }
    }

    protected void consumePostfixExpression() {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
    }

    protected void consumePrimaryNoNewArray() {
        Expression expression = this.expressionStack[this.expressionPtr];
        updateSourcePosition(expression);
        int i = (expression.bits & ASTNode.ParenthesizedMASK) >> 21;
        expression.bits &= -534773761;
        expression.bits = ((i + 1) << 21) | expression.bits;
    }

    protected void consumePrimaryNoNewArrayArrayType() {
        this.intPtr--;
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnGenericsLengthStack(0);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        int i2 = i - 1;
        this.intPtr = i2;
        int i3 = iArr[i];
        this.intPtr = i - 2;
        ClassLiteralAccess classLiteralAccess = new ClassLiteralAccess(i3, getTypeReference(iArr[i2]));
        pushOnExpressionStack(classLiteralAccess);
        rejectIllegalTypeAnnotations(classLiteralAccess.type);
    }

    protected void consumePrimaryNoNewArrayName() {
        this.intPtr--;
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnGenericsLengthStack(0);
        TypeReference typeReference = getTypeReference(0);
        rejectIllegalTypeAnnotations(typeReference);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnExpressionStack(new ClassLiteralAccess(iArr[i], typeReference));
    }

    protected void rejectIllegalLeadingTypeAnnotations(TypeReference typeReference) {
        Annotation[][] annotationArr = typeReference.annotations;
        if (annotationArr == null || annotationArr[0] == null) {
            return;
        }
        ProblemReporter problemReporter = problemReporter();
        Annotation[] annotationArr2 = annotationArr[0];
        problemReporter.misplacedTypeAnnotations(annotationArr2[0], annotationArr2[annotationArr2.length - 1]);
        annotationArr[0] = null;
    }

    private void rejectIllegalTypeAnnotations(TypeReference typeReference) {
        rejectIllegalTypeAnnotations(typeReference, false);
    }

    private void rejectIllegalTypeAnnotations(TypeReference typeReference, boolean z) {
        Annotation[][] annotationArr = typeReference.annotations;
        int length = annotationArr == null ? 0 : annotationArr.length;
        for (int i = 0; i < length; i++) {
            Annotation[] annotationArr2 = annotationArr[i];
            if (annotationArr2 != null) {
                problemReporter().misplacedTypeAnnotations(annotationArr2[0], annotationArr2[annotationArr2.length - 1]);
            }
        }
        Annotation[][] annotationsOnDimensions = typeReference.getAnnotationsOnDimensions(true);
        int length2 = annotationsOnDimensions == null ? 0 : annotationsOnDimensions.length;
        boolean z2 = false;
        for (int i2 = 0; i2 < length2; i2++) {
            Annotation[] annotationArr3 = annotationsOnDimensions[i2];
            if (annotationArr3 != null) {
                if (z) {
                    problemReporter().toleratedMisplacedTypeAnnotations(annotationArr3[0], annotationArr3[annotationArr3.length - 1]);
                    z2 = true;
                } else {
                    problemReporter().misplacedTypeAnnotations(annotationArr3[0], annotationArr3[annotationArr3.length - 1]);
                }
            }
        }
        if (z2) {
            return;
        }
        typeReference.annotations = null;
        typeReference.setAnnotationsOnDimensions(null);
        typeReference.bits &= -1048577;
    }

    protected void consumeQualifiedSuperReceiver() {
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnGenericsLengthStack(0);
        TypeReference typeReference = getTypeReference(0);
        rejectIllegalTypeAnnotations(typeReference);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnExpressionStack(new QualifiedSuperReference(typeReference, iArr[i], this.endPosition));
    }

    protected void consumePrimaryNoNewArrayNameThis() {
        pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
        pushOnGenericsLengthStack(0);
        TypeReference typeReference = getTypeReference(0);
        rejectIllegalTypeAnnotations(typeReference);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnExpressionStack(new QualifiedThisReference(typeReference, iArr[i], this.endPosition));
    }

    protected void consumePrimaryNoNewArrayPrimitiveArrayType() {
        this.intPtr--;
        int[] iArr = this.intStack;
        int i = this.intPtr;
        int i2 = i - 1;
        this.intPtr = i2;
        int i3 = iArr[i];
        this.intPtr = i - 2;
        ClassLiteralAccess classLiteralAccess = new ClassLiteralAccess(i3, getTypeReference(iArr[i2]));
        pushOnExpressionStack(classLiteralAccess);
        rejectIllegalTypeAnnotations(classLiteralAccess.type, true);
    }

    protected void consumePrimaryNoNewArrayPrimitiveType() {
        this.intPtr--;
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        ClassLiteralAccess classLiteralAccess = new ClassLiteralAccess(iArr[i], getTypeReference(0));
        pushOnExpressionStack(classLiteralAccess);
        rejectIllegalTypeAnnotations(classLiteralAccess.type);
    }

    protected void consumePrimaryNoNewArrayThis() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnExpressionStack(new ThisReference(iArr[i], this.endPosition));
    }

    protected void consumePrimaryNoNewArrayWithName() {
        pushOnExpressionStack(getUnspecifiedReferenceOptimized());
        Expression expression = this.expressionStack[this.expressionPtr];
        updateSourcePosition(expression);
        int i = (expression.bits & ASTNode.ParenthesizedMASK) >> 21;
        expression.bits &= -534773761;
        expression.bits = ((i + 1) << 21) | expression.bits;
    }

    protected void consumePrimitiveType() {
        pushOnIntStack(0);
    }

    protected void consumePushLeftBrace() {
        pushOnIntStack(this.endPosition);
    }

    protected void consumePushModifiers() {
        pushOnIntStack(this.modifiers);
        pushOnIntStack(this.modifiersSourceStart);
        resetModifiers();
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumePushCombineModifiers() {
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        int i2 = iArr[i - 1];
        int i3 = 65536 | i2;
        int i4 = i - 4;
        this.intPtr = i4;
        if ((iArr[i - 5] & i3) != 0) {
            i3 = 4259840 | i2;
        }
        int i5 = i - 5;
        iArr[i5] = iArr[i5] | i3;
        int[] iArr2 = this.expressionLengthStack;
        int i6 = this.expressionLengthPtr;
        int i7 = i6 - 1;
        int i8 = iArr2[i7];
        this.expressionLengthPtr = i6 - 1;
        iArr2[i7] = i8 + iArr2[i6];
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            recoveredElement.addModifier(i3, iArr[i4]);
        }
    }

    protected void consumePushModifiersForHeader() {
        checkComment();
        pushOnIntStack(this.modifiers);
        pushOnIntStack(this.modifiersSourceStart);
        resetModifiers();
        pushOnExpressionStackLengthStack(0);
    }

    protected void consumePushPosition() {
        pushOnIntStack(this.endPosition);
    }

    protected void consumePushRealModifiers() {
        checkComment();
        pushOnIntStack(this.modifiers);
        pushOnIntStack(this.modifiersSourceStart);
        resetModifiers();
    }

    protected void consumeQualifiedName(boolean z) {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr - 1;
        this.identifierLengthPtr = i;
        iArr[i] = iArr[i] + 1;
        if (z) {
            return;
        }
        pushOnTypeAnnotationLengthStack(0);
    }

    protected void consumeUnannotatableQualifiedName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr - 1;
        this.identifierLengthPtr = i;
        iArr[i] = iArr[i] + 1;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    protected void consumeRecoveryMethodHeaderName() {
        RecoveredElement recoveredElement = this.currentElement;
        boolean z = true;
        boolean z2 = false;
        if (recoveredElement instanceof RecoveredType) {
            if ((((RecoveredType) recoveredElement).typeDeclaration.modifiers & 8192) == 0) {
                z = false;
            }
            z2 = z;
        } else {
            RecoveredType recoveredTypeEnclosingType = recoveredElement.enclosingType();
            if (recoveredTypeEnclosingType != null) {
                if ((recoveredTypeEnclosingType.typeDeclaration.modifiers & 8192) == 0) {
                    z = false;
                }
                z2 = z;
            }
        }
        consumeMethodHeaderName(z2);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    protected void consumeRecoveryMethodHeaderNameWithTypeParameters() {
        RecoveredElement recoveredElement = this.currentElement;
        boolean z = true;
        boolean z2 = false;
        if (recoveredElement instanceof RecoveredType) {
            if ((((RecoveredType) recoveredElement).typeDeclaration.modifiers & 8192) == 0) {
                z = false;
            }
            z2 = z;
        } else {
            RecoveredType recoveredTypeEnclosingType = recoveredElement.enclosingType();
            if (recoveredTypeEnclosingType != null) {
                if ((recoveredTypeEnclosingType.typeDeclaration.modifiers & 8192) == 0) {
                    z = false;
                }
                z2 = z;
            }
        }
        consumeMethodHeaderNameWithTypeParameters(z2);
    }

    protected void consumeReduceImports() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        this.astLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            int i3 = this.astPtr - i2;
            this.astPtr = i3;
            ImportReference[] importReferenceArr = new ImportReference[i2];
            this.compilationUnit.imports = importReferenceArr;
            System.arraycopy(this.astStack, i3 + 1, importReferenceArr, 0, i2);
        }
    }

    protected void consumeReferenceType() {
        pushOnIntStack(0);
    }

    protected void consumeReferenceType1() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnGenericsStack(getTypeReference(iArr[i]));
    }

    protected void consumeReferenceType2() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnGenericsStack(getTypeReference(iArr[i]));
    }

    protected void consumeReferenceType3() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnGenericsStack(getTypeReference(iArr[i]));
    }

    protected void consumeResourceAsLocalVariableDeclaration() {
        consumeLocalVariableDeclaration();
    }

    protected void consumeResourceOptionalTrailingSemiColon(boolean z) {
        LocalDeclaration localDeclaration = (LocalDeclaration) this.astStack[this.astPtr];
        if (z) {
            localDeclaration.declarationSourceEnd = this.endStatementPosition;
        }
    }

    protected void consumeRestoreDiet() {
        this.dietInt--;
    }

    protected void consumeRightParen() {
        pushOnIntStack(this.rParenPos);
    }

    protected void consumeNonTypeUseName() {
        for (int i = this.identifierLengthStack[this.identifierLengthPtr]; i > 0; i--) {
            int i2 = this.typeAnnotationLengthPtr;
            if (i2 < 0) {
                return;
            }
            int[] iArr = this.typeAnnotationLengthStack;
            this.typeAnnotationLengthPtr = i2 - 1;
            int i3 = iArr[i2];
            if (i3 != 0) {
                Annotation[] annotationArr = this.typeAnnotationStack;
                int i4 = this.typeAnnotationPtr - i3;
                this.typeAnnotationPtr = i4;
                Annotation[] annotationArr2 = new Annotation[i3];
                System.arraycopy(annotationArr, i4 + 1, annotationArr2, 0, i3);
                problemReporter().misplacedTypeAnnotations(annotationArr2[0], annotationArr2[i3 - 1]);
            }
        }
    }

    protected void consumeZeroTypeAnnotations() {
        pushOnTypeAnnotationLengthStack(0);
    }

    protected void consumeRule(int i) {
        switch (i) {
            case 35:
                consumePrimitiveType();
                break;
            case 49:
                consumeReferenceType();
                break;
            case 53:
                consumeClassOrInterfaceName();
                break;
            case 54:
                consumeClassOrInterface();
                break;
            case 55:
                consumeGenericType();
                break;
            case 56:
                consumeGenericTypeWithDiamond();
                break;
            case 57:
                consumeArrayTypeWithTypeArgumentsName();
                break;
            case 58:
                consumePrimitiveArrayType();
                break;
            case 59:
                consumeNameArrayType();
                break;
            case 60:
                consumeGenericTypeNameArrayType();
                break;
            case 61:
                consumeGenericTypeArrayType();
                break;
            case 63:
                consumeZeroTypeAnnotations();
                break;
            case 68:
                consumeUnannotatableQualifiedName();
                break;
            case 69:
                consumeQualifiedName(false);
                break;
            case 70:
                consumeQualifiedName(true);
                break;
            case 71:
                consumeZeroTypeAnnotations();
                break;
            case 75:
                consumeOneMoreTypeAnnotation();
                break;
            case 76:
                consumeTypeAnnotation();
                break;
            case 77:
                consumeTypeAnnotation();
                break;
            case 78:
                consumeTypeAnnotation();
                break;
            case 79:
                consumeAnnotationName();
                break;
            case 80:
                consumeNormalAnnotation(true);
                break;
            case 81:
                consumeMarkerAnnotation(true);
                break;
            case 82:
                consumeSingleMemberAnnotation(true);
                break;
            case 83:
                consumeNonTypeUseName();
                break;
            case 84:
                consumeZeroTypeAnnotations();
                break;
            case 85:
                consumeExplicitThisParameter(false);
                break;
            case 86:
                consumeExplicitThisParameter(true);
                break;
            case 87:
                consumeVariableDeclaratorIdParameter();
                break;
            case 88:
                consumeCompilationUnit();
                break;
            case 89:
                consumeInternalCompilationUnit();
                break;
            case 90:
                consumeInternalCompilationUnit();
                break;
            case 91:
                consumeInternalCompilationUnitWithTypes();
                break;
            case 92:
                consumeInternalCompilationUnitWithTypes();
                break;
            case 93:
                consumeInternalCompilationUnit();
                break;
            case 94:
                consumeInternalCompilationUnitWithTypes();
                break;
            case 95:
                consumeInternalCompilationUnitWithTypes();
                break;
            case 96:
                consumeEmptyInternalCompilationUnit();
                break;
            case 97:
                consumeReduceImports();
                break;
            case 98:
                consumeEnterCompilationUnit();
                break;
            case 114:
                consumeCatchHeader();
                break;
            case 116:
                consumeImportDeclarations();
                break;
            case 118:
                consumeTypeDeclarations();
                break;
            case 119:
                consumePackageDeclaration();
                break;
            case 120:
                consumePackageDeclarationNameWithModifiers();
                break;
            case 121:
                consumePackageDeclarationName();
                break;
            case 122:
                consumePackageComment();
                break;
            case 127:
                consumeImportDeclaration();
                break;
            case 128:
                consumeSingleTypeImportDeclarationName();
                break;
            case 129:
                consumeImportDeclaration();
                break;
            case 130:
                consumeTypeImportOnDemandDeclarationName();
                break;
            case 133:
                consumeEmptyTypeDeclaration();
                break;
            case 137:
                consumeModifiers2();
                break;
            case 149:
                consumeAnnotationAsModifier();
                break;
            case 150:
                consumeClassDeclaration();
                break;
            case 151:
                consumeClassHeader();
                break;
            case 152:
                consumeTypeHeaderNameWithTypeParameters();
                break;
            case 154:
                consumeClassHeaderName1();
                break;
            case 155:
                consumeClassHeaderExtends();
                break;
            case 156:
                consumeClassHeaderImplements();
                break;
            case 158:
                consumeInterfaceTypeList();
                break;
            case 159:
                consumeInterfaceType();
                break;
            case 162:
                consumeClassBodyDeclarations();
                break;
            case 166:
                consumeClassBodyDeclaration();
                break;
            case 167:
                consumeDiet();
                break;
            case 168:
                consumeClassBodyDeclaration();
                break;
            case 169:
                consumeCreateInitializer();
                break;
            case 176:
                consumeEmptyTypeDeclaration();
                break;
            case 179:
                consumeFieldDeclaration();
                break;
            case 181:
                consumeVariableDeclarators();
                break;
            case 184:
                consumeEnterVariable();
                break;
            case 185:
                consumeExitVariableWithInitialization();
                break;
            case 186:
                consumeExitVariableWithoutInitialization();
                break;
            case 187:
                consumeForceNoDiet();
                break;
            case 188:
                consumeRestoreDiet();
                break;
            case 193:
                consumeMethodDeclaration(true, false);
                break;
            case 194:
                consumeMethodDeclaration(true, true);
                break;
            case 195:
                consumeMethodDeclaration(false, false);
                break;
            case 196:
                consumeMethodHeader();
                break;
            case 197:
                consumeMethodHeader();
                break;
            case 198:
                consumeMethodHeaderNameWithTypeParameters(false);
                break;
            case 199:
                consumeMethodHeaderName(false);
                break;
            case 200:
                consumeMethodHeaderNameWithTypeParameters(false);
                break;
            case 201:
                consumeMethodHeaderName(false);
                break;
            case 202:
                consumePushCombineModifiers();
                break;
            case 203:
                consumeMethodHeaderRightParen();
                break;
            case 204:
                consumeMethodHeaderExtendedDims();
                break;
            case 205:
                consumeMethodHeaderThrowsClause();
                break;
            case 206:
                consumeConstructorHeader();
                break;
            case 207:
                consumeConstructorHeaderNameWithTypeParameters();
                break;
            case 208:
                consumeConstructorHeaderName();
                break;
            case 210:
                consumeFormalParameterList();
                break;
            case 211:
                consumeFormalParameter(false);
                break;
            case 212:
                consumeFormalParameter(true);
                break;
            case 213:
                consumeFormalParameter(true);
                break;
            case 214:
                consumeCatchFormalParameter();
                break;
            case 215:
                consumeCatchType();
                break;
            case 216:
                consumeUnionTypeAsClassType();
                break;
            case 217:
                consumeUnionType();
                break;
            case 219:
                consumeClassTypeList();
                break;
            case 220:
                consumeClassTypeElt();
                break;
            case 221:
                consumeMethodBody();
                break;
            case 222:
                consumeNestedMethod();
                break;
            case 223:
                consumeStaticInitializer();
                break;
            case 224:
                consumeStaticOnly();
                break;
            case 225:
                consumeConstructorDeclaration();
                break;
            case 226:
                consumeInvalidConstructorDeclaration();
                break;
            case XKeySym.XK_atilde /* 227 */:
                consumeExplicitConstructorInvocation(0, 3);
                break;
            case XKeySym.XK_adiaeresis /* 228 */:
                consumeExplicitConstructorInvocationWithTypeArguments(0, 3);
                break;
            case XKeySym.XK_aring /* 229 */:
                consumeExplicitConstructorInvocation(0, 2);
                break;
            case XKeySym.XK_ae /* 230 */:
                consumeExplicitConstructorInvocationWithTypeArguments(0, 2);
                break;
            case XKeySym.XK_ccedilla /* 231 */:
                consumeExplicitConstructorInvocation(1, 2);
                break;
            case XKeySym.XK_egrave /* 232 */:
                consumeExplicitConstructorInvocationWithTypeArguments(1, 2);
                break;
            case XKeySym.XK_eacute /* 233 */:
                consumeExplicitConstructorInvocation(2, 2);
                break;
            case XKeySym.XK_ecircumflex /* 234 */:
                consumeExplicitConstructorInvocationWithTypeArguments(2, 2);
                break;
            case XKeySym.XK_ediaeresis /* 235 */:
                consumeExplicitConstructorInvocation(1, 3);
                break;
            case XKeySym.XK_igrave /* 236 */:
                consumeExplicitConstructorInvocationWithTypeArguments(1, 3);
                break;
            case XKeySym.XK_iacute /* 237 */:
                consumeExplicitConstructorInvocation(2, 3);
                break;
            case XKeySym.XK_icircumflex /* 238 */:
                consumeExplicitConstructorInvocationWithTypeArguments(2, 3);
                break;
            case XKeySym.XK_idiaeresis /* 239 */:
                consumeInterfaceDeclaration();
                break;
            case 240:
                consumeInterfaceHeader();
                break;
            case XKeySym.XK_ntilde /* 241 */:
                consumeTypeHeaderNameWithTypeParameters();
                break;
            case 243:
                consumeInterfaceHeaderName1();
                break;
            case 244:
                consumeInterfaceHeaderExtends();
                break;
            case 247:
                consumeInterfaceMemberDeclarations();
                break;
            case 248:
                consumeEmptyTypeDeclaration();
                break;
            case 250:
                consumeInterfaceMethodDeclaration(false);
                break;
            case 251:
                consumeInterfaceMethodDeclaration(false);
                break;
            case 252:
                consumeInterfaceMethodDeclaration(true);
                break;
            case 253:
                consumeInvalidConstructorDeclaration(true);
                break;
            case 254:
                consumeInvalidConstructorDeclaration(false);
                break;
            case 265:
                consumePushLeftBrace();
                break;
            case 266:
                consumeEmptyArrayInitializer();
                break;
            case 267:
                consumeArrayInitializer();
                break;
            case 268:
                consumeArrayInitializer();
                break;
            case 270:
                consumeVariableInitializers();
                break;
            case 271:
                consumeBlock();
                break;
            case 272:
                consumeOpenBlock();
                break;
            case 273:
                consumeBlockStatement();
                break;
            case 274:
                consumeBlockStatements();
                break;
            case 281:
                consumeInvalidInterfaceDeclaration();
                break;
            case 282:
                consumeInvalidAnnotationTypeDeclaration();
                break;
            case 283:
                consumeInvalidEnumDeclaration();
                break;
            case 284:
                consumeLocalVariableDeclarationStatement();
                break;
            case 285:
                consumeLocalVariableDeclaration();
                break;
            case 286:
                consumeLocalVariableDeclaration();
                break;
            case 287:
                consumePushModifiers();
                break;
            case 288:
                consumePushModifiersForHeader();
                break;
            case 289:
                consumePushRealModifiers();
                break;
            case 316:
                consumeEmptyStatement();
                break;
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
                consumeStatementLabel();
                break;
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
                consumeStatementLabel();
                break;
            case 319:
                consumeLabel();
                break;
            case 320:
                consumeExpressionStatement();
                break;
            case 329:
                consumeStatementIfNoElse();
                break;
            case 330:
                consumeStatementIfWithElse();
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
                consumeStatementIfWithElse();
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
                consumeStatementSwitch();
                break;
            case 333:
                consumeEmptySwitchBlock();
                break;
            case 336:
                consumeSwitchBlock();
                break;
            case 338:
                consumeSwitchBlockStatements();
                break;
            case 339:
                consumeSwitchBlockStatement();
                break;
            case 341:
                consumeSwitchLabels();
                break;
            case 342:
                consumeCaseLabel();
                break;
            case 343:
                consumeDefaultLabel();
                break;
            case 344:
                consumeStatementWhile();
                break;
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                consumeStatementWhile();
                break;
            case 346:
                consumeStatementDo();
                break;
            case 347:
                consumeStatementFor();
                break;
            case 348:
                consumeStatementFor();
                break;
            case 349:
                consumeForInit();
                break;
            case 353:
                consumeStatementExpressionList();
                break;
            case 354:
                consumeSimpleAssertStatement();
                break;
            case 355:
                consumeAssertStatement();
                break;
            case 356:
                consumeStatementBreak();
                break;
            case 357:
                consumeStatementBreakWithLabel();
                break;
            case 358:
                consumeStatementContinue();
                break;
            case 359:
                consumeStatementContinueWithLabel();
                break;
            case 360:
                consumeStatementReturn();
                break;
            case 361:
                consumeStatementThrow();
                break;
            case 362:
                consumeStatementSynchronized();
                break;
            case 363:
                consumeOnlySynchronized();
                break;
            case 364:
                consumeStatementTry(false, false);
                break;
            case 365:
                consumeStatementTry(true, false);
                break;
            case 366:
                consumeStatementTry(false, true);
                break;
            case 367:
                consumeStatementTry(true, true);
                break;
            case 368:
                consumeResourceSpecification();
                break;
            case 369:
                consumeResourceOptionalTrailingSemiColon(false);
                break;
            case 370:
                consumeResourceOptionalTrailingSemiColon(true);
                break;
            case 371:
                consumeSingleResource();
                break;
            case 372:
                consumeMultipleResources();
                break;
            case 373:
                consumeResourceOptionalTrailingSemiColon(true);
                break;
            case 374:
                consumeResourceAsLocalVariableDeclaration();
                break;
            case 375:
                consumeResourceAsLocalVariableDeclaration();
                break;
            case 377:
                consumeExitTryBlock();
                break;
            case 379:
                consumeCatches();
                break;
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
                consumeStatementCatch();
                break;
            case 382:
                consumeLeftParen();
                break;
            case 383:
                consumeRightParen();
                break;
            case 388:
                consumePrimaryNoNewArrayThis();
                break;
            case 389:
                consumePrimaryNoNewArray();
                break;
            case 390:
                consumePrimaryNoNewArrayWithName();
                break;
            case 393:
                consumePrimaryNoNewArrayNameThis();
                break;
            case 394:
                consumeQualifiedSuperReceiver();
                break;
            case 395:
                consumePrimaryNoNewArrayName();
                break;
            case 396:
                consumePrimaryNoNewArrayArrayType();
                break;
            case 397:
                consumePrimaryNoNewArrayPrimitiveArrayType();
                break;
            case 398:
                consumePrimaryNoNewArrayPrimitiveType();
                break;
            case 404:
                consumeReferenceExpressionTypeArgumentsAndTrunk(false);
                break;
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
                consumeReferenceExpressionTypeArgumentsAndTrunk(true);
                break;
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
                consumeReferenceExpressionTypeForm(true);
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
                consumeReferenceExpressionTypeForm(false);
                break;
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
                consumeReferenceExpressionGenericTypeForm();
                break;
            case HttpStatus.SC_CONFLICT /* 409 */:
                consumeReferenceExpressionPrimaryForm();
                break;
            case HttpStatus.SC_GONE /* 410 */:
                consumeReferenceExpressionPrimaryForm();
                break;
            case 411:
                consumeReferenceExpressionSuperForm();
                break;
            case 412:
                consumeEmptyTypeArguments();
                break;
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
                consumeIdentifierOrNew(false);
                break;
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /* 415 */:
                consumeIdentifierOrNew(true);
                break;
            case 416:
                consumeLambdaExpression();
                break;
            case 417:
                consumeNestedLambda();
                break;
            case XKeySym.XK_breve /* 418 */:
                consumeTypeElidedLambdaParameter(false);
                break;
            case 424:
                consumeFormalParameterList();
                break;
            case 425:
                consumeTypeElidedLambdaParameter(true);
                break;
            case XKeySym.XK_Zacute /* 428 */:
                consumeElidedLeftBraceAndReturn();
                break;
            case Constants.ERROR_TOO_MANY_REQUESTS /* 429 */:
                consumeAllocationHeader();
                break;
            case XKeySym.XK_Zcaron /* 430 */:
                consumeClassInstanceCreationExpressionWithTypeArguments();
                break;
            case 431:
                consumeClassInstanceCreationExpression();
                break;
            case 432:
                consumeClassInstanceCreationExpressionQualifiedWithTypeArguments();
                break;
            case XKeySym.XK_aogonek /* 433 */:
                consumeClassInstanceCreationExpressionQualified();
                break;
            case XKeySym.XK_ogonek /* 434 */:
                consumeClassInstanceCreationExpressionQualified();
                break;
            case XKeySym.XK_lstroke /* 435 */:
                consumeClassInstanceCreationExpressionQualifiedWithTypeArguments();
                break;
            case 436:
                consumeEnterInstanceCreationArgumentList();
                break;
            case XKeySym.XK_lcaron /* 437 */:
                consumeClassInstanceCreationExpressionName();
                break;
            case XKeySym.XK_sacute /* 438 */:
                consumeClassBodyopt();
                break;
            case 440:
                consumeEnterAnonymousClassBody(false);
                break;
            case XKeySym.XK_scaron /* 441 */:
                consumeClassBodyopt();
                break;
            case 443:
                consumeEnterAnonymousClassBody(true);
                break;
            case XKeySym.XK_doubleacute /* 445 */:
                consumeArgumentList();
                break;
            case XKeySym.XK_zcaron /* 446 */:
                consumeArrayCreationHeader();
                break;
            case XKeySym.XK_zabovedot /* 447 */:
                consumeArrayCreationHeader();
                break;
            case XKeySym.XK_Racute /* 448 */:
                consumeArrayCreationExpressionWithoutInitializer();
                break;
            case 449:
                consumeArrayCreationExpressionWithInitializer();
                break;
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
                consumeArrayCreationExpressionWithoutInitializer();
                break;
            case XKeySym.XK_Abreve /* 451 */:
                consumeArrayCreationExpressionWithInitializer();
                break;
            case XKeySym.XK_Lacute /* 453 */:
                consumeDimWithOrWithOutExprs();
                break;
            case 455:
                consumeDimWithOrWithOutExpr();
                break;
            case XKeySym.XK_Ccaron /* 456 */:
                consumeDims();
                break;
            case 459:
                consumeOneDimLoop(false);
                break;
            case XKeySym.XK_Ecaron /* 460 */:
                consumeOneDimLoop(true);
                break;
            case 461:
                consumeFieldAccess(false);
                break;
            case 462:
                consumeFieldAccess(true);
                break;
            case XKeySym.XK_Dcaron /* 463 */:
                consumeFieldAccess(false);
                break;
            case 464:
                consumeMethodInvocationName();
                break;
            case XKeySym.XK_Nacute /* 465 */:
                consumeMethodInvocationNameWithTypeArguments();
                break;
            case XKeySym.XK_Ncaron /* 466 */:
                consumeMethodInvocationPrimaryWithTypeArguments();
                break;
            case 467:
                consumeMethodInvocationPrimary();
                break;
            case 468:
                consumeMethodInvocationPrimary();
                break;
            case XKeySym.XK_Odoubleacute /* 469 */:
                consumeMethodInvocationPrimaryWithTypeArguments();
                break;
            case 470:
                consumeMethodInvocationSuperWithTypeArguments();
                break;
            case 471:
                consumeMethodInvocationSuper();
                break;
            case XKeySym.XK_Rcaron /* 472 */:
                consumeArrayAccess(true);
                break;
            case XKeySym.XK_Uring /* 473 */:
                consumeArrayAccess(false);
                break;
            case 474:
                consumeArrayAccess(false);
                break;
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
                consumePostfixExpression();
                break;
            case 479:
                consumeUnaryExpression(14, true);
                break;
            case 480:
                consumeUnaryExpression(13, true);
                break;
            case 481:
                consumePushPosition();
                break;
            case 484:
                consumeUnaryExpression(14);
                break;
            case XKeySym.XK_lacute /* 485 */:
                consumeUnaryExpression(13);
                break;
            case 487:
                consumeUnaryExpression(14, false);
                break;
            case 488:
                consumeUnaryExpression(13, false);
                break;
            case XKeySym.XK_eogonek /* 490 */:
                consumeUnaryExpression(12);
                break;
            case 491:
                consumeUnaryExpression(11);
                break;
            case UnixStat.DEFAULT_DIR_PERM /* 493 */:
                consumeCastExpressionWithPrimitiveType();
                break;
            case 494:
                consumeCastExpressionWithGenericsArray();
                break;
            case XKeySym.XK_dcaron /* 495 */:
                consumeCastExpressionWithQualifiedGenericsArray();
                break;
            case XKeySym.XK_dstroke /* 496 */:
                consumeCastExpressionLL1();
                break;
            case XKeySym.XK_nacute /* 497 */:
                consumeCastExpressionLL1WithBounds();
                break;
            case XKeySym.XK_ncaron /* 498 */:
                consumeCastExpressionWithNameArray();
                break;
            case 499:
                consumeZeroAdditionalBounds();
                break;
            case 503:
                consumeOnlyTypeArgumentsForCastExpression();
                break;
            case 504:
                consumeInsideCastExpression();
                break;
            case 505:
                consumeInsideCastExpressionLL1();
                break;
            case 506:
                consumeInsideCastExpressionLL1WithBounds();
                break;
            case 507:
                consumeInsideCastExpressionWithQualifiedGenerics();
                break;
            case 509:
                consumeBinaryExpression(15);
                break;
            case 510:
                consumeBinaryExpression(9);
                break;
            case 511:
                consumeBinaryExpression(16);
                break;
            case 513:
                consumeBinaryExpression(14);
                break;
            case 514:
                consumeBinaryExpression(13);
                break;
            case 516:
                consumeBinaryExpression(10);
                break;
            case GL20.GL_NOTEQUAL /* 517 */:
                consumeBinaryExpression(17);
                break;
            case 518:
                consumeBinaryExpression(19);
                break;
            case 520:
                consumeBinaryExpression(4);
                break;
            case 521:
                consumeBinaryExpression(6);
                break;
            case 522:
                consumeBinaryExpression(5);
                break;
            case 523:
                consumeBinaryExpression(7);
                break;
            case 525:
                consumeInstanceOfExpression();
                break;
            case 527:
                consumeEqualityExpression(18);
                break;
            case 528:
                consumeEqualityExpression(29);
                break;
            case 530:
                consumeBinaryExpression(2);
                break;
            case 532:
                consumeBinaryExpression(8);
                break;
            case 534:
                consumeBinaryExpression(3);
                break;
            case 536:
                consumeBinaryExpression(0);
                break;
            case 538:
                consumeBinaryExpression(1);
                break;
            case 540:
                consumeConditionalExpression(23);
                break;
            case 543:
                consumeAssignment();
                break;
            case 545:
                ignoreExpressionAssignment();
                break;
            case 546:
                consumeAssignmentOperator(30);
                break;
            case 547:
                consumeAssignmentOperator(15);
                break;
            case 548:
                consumeAssignmentOperator(9);
                break;
            case 549:
                consumeAssignmentOperator(16);
                break;
            case 550:
                consumeAssignmentOperator(14);
                break;
            case 551:
                consumeAssignmentOperator(13);
                break;
            case 552:
                consumeAssignmentOperator(10);
                break;
            case 553:
                consumeAssignmentOperator(17);
                break;
            case 554:
                consumeAssignmentOperator(19);
                break;
            case 555:
                consumeAssignmentOperator(2);
                break;
            case 556:
                consumeAssignmentOperator(8);
                break;
            case 557:
                consumeAssignmentOperator(3);
                break;
            case 558:
                consumeExpression();
                break;
            case 561:
                consumeEmptyExpression();
                break;
            case 566:
                consumeEmptyClassBodyDeclarationsopt();
                break;
            case 567:
                consumeClassBodyDeclarationsopt();
                break;
            case 568:
                consumeDefaultModifiers();
                break;
            case 569:
                consumeModifiers();
                break;
            case 570:
                consumeEmptyBlockStatementsopt();
                break;
            case 572:
                consumeEmptyDimsopt();
                break;
            case 574:
                consumeEmptyArgumentListopt();
                break;
            case 578:
                consumeFormalParameterListopt();
                break;
            case 582:
                consumeEmptyInterfaceMemberDeclarationsopt();
                break;
            case 583:
                consumeInterfaceMemberDeclarationsopt();
                break;
            case 584:
                consumeNestedType();
                break;
            case 585:
                consumeEmptyForInitopt();
                break;
            case 587:
                consumeEmptyForUpdateopt();
                break;
            case 591:
                consumeEmptyCatchesopt();
                break;
            case 593:
                consumeEnumDeclaration();
                break;
            case 594:
                consumeEnumHeader();
                break;
            case 595:
                consumeEnumHeaderName();
                break;
            case 596:
                consumeEnumHeaderNameWithTypeParameters();
                break;
            case 597:
                consumeEnumBodyNoConstants();
                break;
            case 598:
                consumeEnumBodyNoConstants();
                break;
            case 599:
                consumeEnumBodyWithConstants();
                break;
            case 600:
                consumeEnumBodyWithConstants();
                break;
            case TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE /* 602 */:
                consumeEnumConstants();
                break;
            case TypedValues.MotionType.TYPE_EASING /* 603 */:
                consumeEnumConstantHeaderName();
                break;
            case TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR /* 604 */:
                consumeEnumConstantHeader();
                break;
            case TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO /* 605 */:
                consumeEnumConstantWithClassBody();
                break;
            case TypedValues.MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO /* 606 */:
                consumeEnumConstantNoClassBody();
                break;
            case TypedValues.MotionType.TYPE_PATHMOTION_ARC /* 607 */:
                consumeArguments();
                break;
            case TypedValues.MotionType.TYPE_DRAW_PATH /* 608 */:
                consumeEmptyArguments();
                break;
            case TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS /* 610 */:
                consumeEnumDeclarations();
                break;
            case TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_TYPE /* 611 */:
                consumeEmptyEnumDeclarations();
                break;
            case 613:
                consumeEnhancedForStatement();
                break;
            case 614:
                consumeEnhancedForStatement();
                break;
            case 615:
                consumeEnhancedForStatementHeaderInit(false);
                break;
            case 616:
                consumeEnhancedForStatementHeaderInit(true);
                break;
            case 617:
                consumeEnhancedForStatementHeader();
                break;
            case 618:
                consumeImportDeclaration();
                break;
            case 619:
                consumeSingleStaticImportDeclarationName();
                break;
            case 620:
                consumeImportDeclaration();
                break;
            case 621:
                consumeStaticImportOnDemandDeclarationName();
                break;
            case 622:
                consumeTypeArguments();
                break;
            case 623:
                consumeOnlyTypeArguments();
                break;
            case 625:
                consumeTypeArgumentList1();
                break;
            case 627:
                consumeTypeArgumentList();
                break;
            case 628:
                consumeTypeArgument();
                break;
            case 632:
                consumeReferenceType1();
                break;
            case 633:
                consumeTypeArgumentReferenceType1();
                break;
            case 635:
                consumeTypeArgumentList2();
                break;
            case 638:
                consumeReferenceType2();
                break;
            case 639:
                consumeTypeArgumentReferenceType2();
                break;
            case 641:
                consumeTypeArgumentList3();
                break;
            case 644:
                consumeReferenceType3();
                break;
            case 645:
                consumeWildcard();
                break;
            case 646:
                consumeWildcardWithBounds();
                break;
            case 647:
                consumeWildcardBoundsExtends();
                break;
            case 648:
                consumeWildcardBoundsSuper();
                break;
            case 649:
                consumeWildcard1();
                break;
            case 650:
                consumeWildcard1WithBounds();
                break;
            case 651:
                consumeWildcardBounds1Extends();
                break;
            case 652:
                consumeWildcardBounds1Super();
                break;
            case 653:
                consumeWildcard2();
                break;
            case 654:
                consumeWildcard2WithBounds();
                break;
            case 655:
                consumeWildcardBounds2Extends();
                break;
            case 656:
                consumeWildcardBounds2Super();
                break;
            case 657:
                consumeWildcard3();
                break;
            case 658:
                consumeWildcard3WithBounds();
                break;
            case 659:
                consumeWildcardBounds3Extends();
                break;
            case 660:
                consumeWildcardBounds3Super();
                break;
            case 661:
                consumeTypeParameterHeader();
                break;
            case 662:
                consumeTypeParameters();
                break;
            case 664:
                consumeTypeParameterList();
                break;
            case 666:
                consumeTypeParameterWithExtends();
                break;
            case 667:
                consumeTypeParameterWithExtendsAndBounds();
                break;
            case 669:
                consumeAdditionalBoundList();
                break;
            case 670:
                consumeAdditionalBound();
                break;
            case 672:
                consumeTypeParameterList1();
                break;
            case XKeySym.XK_Hstroke /* 673 */:
                consumeTypeParameter1();
                break;
            case 674:
                consumeTypeParameter1WithExtends();
                break;
            case 675:
                consumeTypeParameter1WithExtendsAndBounds();
                break;
            case 677:
                consumeAdditionalBoundList1();
                break;
            case XKeySym.XK_Hcircumflex /* 678 */:
                consumeAdditionalBound1();
                break;
            case XKeySym.XK_Jcircumflex /* 684 */:
                consumeUnaryExpression(14);
                break;
            case 685:
                consumeUnaryExpression(13);
                break;
            case 688:
                consumeUnaryExpression(12);
                break;
            case XKeySym.XK_hstroke /* 689 */:
                consumeUnaryExpression(11);
                break;
            case 692:
                consumeBinaryExpression(15);
                break;
            case 693:
                consumeBinaryExpressionWithName(15);
                break;
            case XKeySym.XK_hcircumflex /* 694 */:
                consumeBinaryExpression(9);
                break;
            case 695:
                consumeBinaryExpressionWithName(9);
                break;
            case 696:
                consumeBinaryExpression(16);
                break;
            case XKeySym.XK_idotless /* 697 */:
                consumeBinaryExpressionWithName(16);
                break;
            case XKeySym.XK_gbreve /* 699 */:
                consumeBinaryExpression(14);
                break;
            case 700:
                consumeBinaryExpressionWithName(14);
                break;
            case 701:
                consumeBinaryExpression(13);
                break;
            case TypedValues.TransitionType.TYPE_TO /* 702 */:
                consumeBinaryExpressionWithName(13);
                break;
            case TypedValues.TransitionType.TYPE_AUTO_TRANSITION /* 704 */:
                consumeBinaryExpression(10);
                break;
            case TypedValues.TransitionType.TYPE_INTERPOLATOR /* 705 */:
                consumeBinaryExpressionWithName(10);
                break;
            case TypedValues.TransitionType.TYPE_STAGGERED /* 706 */:
                consumeBinaryExpression(17);
                break;
            case TypedValues.TransitionType.TYPE_TRANSITION_FLAGS /* 707 */:
                consumeBinaryExpressionWithName(17);
                break;
            case 708:
                consumeBinaryExpression(19);
                break;
            case XKeySym.XK_Cabovedot /* 709 */:
                consumeBinaryExpressionWithName(19);
                break;
            case 711:
                consumeBinaryExpression(4);
                break;
            case 712:
                consumeBinaryExpressionWithName(4);
                break;
            case 713:
                consumeBinaryExpression(6);
                break;
            case 714:
                consumeBinaryExpressionWithName(6);
                break;
            case 715:
                consumeBinaryExpression(5);
                break;
            case 716:
                consumeBinaryExpressionWithName(5);
                break;
            case 717:
                consumeBinaryExpression(7);
                break;
            case 718:
                consumeBinaryExpressionWithName(7);
                break;
            case 720:
                consumeInstanceOfExpressionWithName();
                break;
            case 721:
                consumeInstanceOfExpression();
                break;
            case 723:
                consumeEqualityExpression(18);
                break;
            case 724:
                consumeEqualityExpressionWithName(18);
                break;
            case XKeySym.XK_Gabovedot /* 725 */:
                consumeEqualityExpression(29);
                break;
            case 726:
                consumeEqualityExpressionWithName(29);
                break;
            case XKeySym.XK_Gcircumflex /* 728 */:
                consumeBinaryExpression(2);
                break;
            case 729:
                consumeBinaryExpressionWithName(2);
                break;
            case 731:
                consumeBinaryExpression(8);
                break;
            case 732:
                consumeBinaryExpressionWithName(8);
                break;
            case XKeySym.XK_Scircumflex /* 734 */:
                consumeBinaryExpression(3);
                break;
            case 735:
                consumeBinaryExpressionWithName(3);
                break;
            case 737:
                consumeBinaryExpression(0);
                break;
            case 738:
                consumeBinaryExpressionWithName(0);
                break;
            case 740:
                consumeBinaryExpression(1);
                break;
            case XKeySym.XK_cabovedot /* 741 */:
                consumeBinaryExpressionWithName(1);
                break;
            case 743:
                consumeConditionalExpression(23);
                break;
            case 744:
                consumeConditionalExpressionWithName(23);
                break;
            case 748:
                consumeAnnotationTypeDeclarationHeaderName();
                break;
            case 749:
                consumeAnnotationTypeDeclarationHeaderNameWithTypeParameters();
                break;
            case 750:
                consumeAnnotationTypeDeclarationHeaderNameWithTypeParameters();
                break;
            case 751:
                consumeAnnotationTypeDeclarationHeaderName();
                break;
            case 752:
                consumeAnnotationTypeDeclarationHeader();
                break;
            case 753:
                consumeAnnotationTypeDeclaration();
                break;
            case 755:
                consumeEmptyAnnotationTypeMemberDeclarationsopt();
                break;
            case 756:
                consumeAnnotationTypeMemberDeclarationsopt();
                break;
            case 758:
                consumeAnnotationTypeMemberDeclarations();
                break;
            case 759:
                consumeMethodHeaderNameWithTypeParameters(true);
                break;
            case XKeySym.XK_gcircumflex /* 760 */:
                consumeMethodHeaderName(true);
                break;
            case ConstantPool.STRING_INITIAL_SIZE /* 761 */:
                consumeEmptyMethodHeaderDefaultValue();
                break;
            case 762:
                consumeMethodHeaderDefaultValue();
                break;
            case 763:
                consumeMethodHeader();
                break;
            case 764:
                consumeAnnotationTypeMemberDeclaration();
                break;
            case GL20.GL_DST_ALPHA /* 772 */:
                consumeAnnotationName();
                break;
            case GL20.GL_ONE_MINUS_DST_ALPHA /* 773 */:
                consumeNormalAnnotation(false);
                break;
            case GL20.GL_DST_COLOR /* 774 */:
                consumeEmptyMemberValuePairsopt();
                break;
            case 777:
                consumeMemberValuePairs();
                break;
            case ConstantPool.UTF8_INITIAL_SIZE /* 778 */:
                consumeMemberValuePair();
                break;
            case 779:
                consumeEnterMemberValue();
                break;
            case 780:
                consumeExitMemberValue();
                break;
            case 782:
                consumeMemberValueAsName();
                break;
            case 785:
                consumeMemberValueArrayInitializer();
                break;
            case 786:
                consumeMemberValueArrayInitializer();
                break;
            case 787:
                consumeEmptyMemberValueArrayInitializer();
                break;
            case 788:
                consumeEmptyMemberValueArrayInitializer();
                break;
            case 789:
                consumeEnterMemberValueArrayInitializer();
                break;
            case 791:
                consumeMemberValues();
                break;
            case 792:
                consumeMarkerAnnotation(false);
                break;
            case 793:
                consumeSingleMemberAnnotationMemberValue();
                break;
            case 794:
                consumeSingleMemberAnnotation(false);
                break;
            case 795:
                consumeRecoveryMethodHeaderNameWithTypeParameters();
                break;
            case 796:
                consumeRecoveryMethodHeaderName();
                break;
            case 797:
                consumeRecoveryMethodHeaderNameWithTypeParameters();
                break;
            case 798:
                consumeRecoveryMethodHeaderName();
                break;
            case 799:
                consumeMethodHeader();
                break;
            case ParserBasicInformation.NUM_RULES /* 800 */:
                consumeMethodHeader();
                break;
        }
    }

    protected void consumeVariableDeclaratorIdParameter() {
        pushOnIntStack(1);
    }

    protected void consumeExplicitThisParameter(boolean z) {
        pushOnExpressionStack(z ? getUnspecifiedReference(false) : null);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        pushIdentifier(ConstantPool.This, (((long) i2) << 32) + ((long) (i2 + 3)));
        pushOnIntStack(0);
        pushOnIntStack(0);
    }

    protected void consumeNestedLambda() {
        consumeNestedType();
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        pushOnAstStack(new LambdaExpression(this.compilationUnit.compilationResult, isAssistParser()));
        this.processingLambdaParameterList = true;
    }

    protected void consumeLambdaHeader() {
        Argument[] argumentArr;
        int i = this.scanner.currentPosition;
        int i2 = i - 1;
        int[] iArr = this.astLengthStack;
        int i3 = this.astLengthPtr;
        this.astLengthPtr = i3 - 1;
        int i4 = iArr[i3];
        int i5 = this.astPtr - i4;
        this.astPtr = i5;
        if (i4 != 0) {
            argumentArr = new Argument[i4];
            System.arraycopy(this.astStack, i5 + 1, argumentArr, 0, i4);
        } else {
            argumentArr = null;
        }
        for (int i6 = 0; i6 < i4; i6++) {
            Argument argument = argumentArr[i6];
            if (argument.isReceiver()) {
                problemReporter().illegalThis(argument);
            }
            if (argument.f617name.length == 1 && argument.f617name[0] == '_') {
                problemReporter().illegalUseOfUnderscoreAsAnIdentifier(argument.sourceStart, argument.sourceEnd, true);
            }
        }
        LambdaExpression lambdaExpression = (LambdaExpression) this.astStack[this.astPtr];
        lambdaExpression.setArguments(argumentArr);
        lambdaExpression.setArrowPosition(i2);
        int[] iArr2 = this.intStack;
        int i7 = this.intPtr;
        this.intPtr = i7 - 1;
        lambdaExpression.sourceEnd = iArr2[i7];
        int[] iArr3 = this.intStack;
        int i8 = this.intPtr;
        this.intPtr = i8 - 1;
        lambdaExpression.sourceStart = iArr3[i8];
        lambdaExpression.hasParentheses = this.scanner.getSource()[lambdaExpression.sourceStart] == '(';
        this.listLength -= argumentArr == null ? 0 : argumentArr.length;
        this.processingLambdaParameterList = false;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            this.lastCheckPoint = i;
            recoveredElement.lambdaNestLevel++;
        }
    }

    protected void consumeLambdaExpression() {
        this.nestedType--;
        this.astLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        this.astPtr = i - 1;
        Statement statement = (Statement) aSTNodeArr[i];
        if ((statement instanceof Block) && this.options.ignoreMethodBodies) {
            Statement block = new Block(0);
            block.sourceStart = statement.sourceStart;
            block.sourceEnd = statement.sourceEnd;
            statement = block;
        }
        ASTNode[] aSTNodeArr2 = this.astStack;
        int i2 = this.astPtr;
        this.astPtr = i2 - 1;
        LambdaExpression lambdaExpression = (LambdaExpression) aSTNodeArr2[i2];
        this.astLengthPtr--;
        lambdaExpression.setBody(statement);
        lambdaExpression.sourceEnd = statement.sourceEnd;
        if (statement instanceof Expression) {
            ((Expression) statement).statementEnd = statement.sourceEnd;
        }
        if (!this.parsingJava8Plus) {
            problemReporter().lambdaExpressionsNotBelow18(lambdaExpression);
        }
        pushOnExpressionStack(lambdaExpression);
        if (this.currentElement != null) {
            this.lastCheckPoint = statement.sourceEnd + 1;
            this.currentElement.lambdaNestLevel--;
        }
        this.referenceContext.compilationResult().hasFunctionalTypes = true;
        markEnclosingMemberWithLocalOrFunctionalType(LocalTypeKind.LAMBDA);
        if (lambdaExpression.compilationResult.getCompilationUnit() == null) {
            int i3 = (lambdaExpression.sourceEnd - lambdaExpression.sourceStart) + 1;
            char[] source = this.scanner.getSource();
            int i4 = lambdaExpression.sourceStart;
            char[] cArr = new char[i3];
            lambdaExpression.text = cArr;
            System.arraycopy(source, i4, cArr, 0, i3);
        }
    }

    protected Argument typeElidedArgument() {
        this.identifierLengthPtr--;
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        char[] cArr2 = cArr[i];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i - 1;
        long j = jArr[i];
        Argument argument = new Argument(cArr2, j, null, 0, true);
        argument.declarationSourceStart = (int) (j >>> 32);
        return argument;
    }

    protected void consumeTypeElidedLambdaParameter(boolean z) {
        int i;
        int i2;
        int i3;
        if (z) {
            int[] iArr = this.intStack;
            int i4 = this.intPtr;
            int i5 = i4 - 1;
            this.intPtr = i5;
            i = iArr[i4];
            this.intPtr = i4 - 2;
            i2 = iArr[i5];
            int[] iArr2 = this.expressionLengthStack;
            int i6 = this.expressionLengthPtr;
            this.expressionLengthPtr = i6 - 1;
            i3 = iArr2[i6];
            this.expressionPtr -= i3;
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        Argument argumentTypeElidedArgument = typeElidedArgument();
        if (i2 != 0 || i3 != 0) {
            problemReporter().illegalModifiersForElidedType(argumentTypeElidedArgument);
            argumentTypeElidedArgument.declarationSourceStart = i;
        }
        if (!z) {
            pushOnIntStack(argumentTypeElidedArgument.declarationSourceStart);
            pushOnIntStack(argumentTypeElidedArgument.declarationSourceEnd);
        }
        pushOnAstStack(argumentTypeElidedArgument);
        this.listLength++;
    }

    protected void consumeElidedLeftBraceAndReturn() {
        int[] iArr = this.stateStackLengthStack;
        int length = iArr.length;
        int i = this.valueLambdaNestDepth + 1;
        this.valueLambdaNestDepth = i;
        if (i >= length) {
            int[] iArr2 = new int[length + 4];
            this.stateStackLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.stateStackLengthStack[this.valueLambdaNestDepth] = this.stateStackTop;
    }

    protected void consumeExpression() {
        int i = this.valueLambdaNestDepth;
        if (i < 0 || this.stateStackLengthStack[i] != this.stateStackTop - 1) {
            return;
        }
        this.valueLambdaNestDepth = i - 1;
        this.scanner.ungetToken(this.currentToken);
        this.currentToken = 66;
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        this.expressionLengthPtr--;
        pushOnAstStack(expression);
    }

    protected void consumeIdentifierOrNew(boolean z) {
        if (z) {
            int[] iArr = this.intStack;
            int i = this.intPtr;
            this.intPtr = i - 1;
            int i2 = iArr[i];
            pushIdentifier(ConstantPool.Init, (((long) i2) << 32) + ((long) (i2 + 2)));
        }
    }

    protected void consumeEmptyTypeArguments() {
        pushOnGenericsLengthStack(0);
    }

    public ReferenceExpression newReferenceExpression() {
        return new ReferenceExpression();
    }

    protected void consumeReferenceExpressionTypeForm(boolean z) {
        TypeReference[] typeReferenceArr;
        ReferenceExpression referenceExpressionNewReferenceExpression = newReferenceExpression();
        long j = this.identifierPositionStack[this.identifierPtr];
        int i = (int) j;
        referenceExpressionNewReferenceExpression.nameSourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        char[] cArr2 = cArr[i2];
        boolean z2 = true;
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr[i3];
        if (i4 > 0) {
            int i5 = this.genericsPtr - i4;
            this.genericsPtr = i5;
            TypeReference[] typeReferenceArr2 = new TypeReference[i4];
            System.arraycopy(this.genericsStack, i5 + 1, typeReferenceArr2, 0, i4);
            this.intPtr--;
            typeReferenceArr = typeReferenceArr2;
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        int i7 = iArr2[i6];
        int i8 = this.identifierLengthStack[this.identifierLengthPtr];
        int i9 = 0;
        while (true) {
            if (i8 > 0) {
                int i10 = this.typeAnnotationLengthPtr;
                if (i10 >= 0) {
                    if (this.typeAnnotationLengthStack[i10 - i9] != 0) {
                        break;
                    }
                    i8--;
                    i9++;
                }
            }
            z2 = false;
            break;
        }
        if (i7 > 0 || z2) {
            if (!z) {
                pushOnGenericsLengthStack(0);
                pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
            }
            referenceExpressionNewReferenceExpression.initialize(this.compilationUnit.compilationResult, getTypeReference(i7), typeReferenceArr, cArr2, i);
        } else {
            referenceExpressionNewReferenceExpression.initialize(this.compilationUnit.compilationResult, getUnspecifiedReference(), typeReferenceArr, cArr2, i);
        }
        consumeReferenceExpression(referenceExpressionNewReferenceExpression);
    }

    protected void consumeReferenceExpressionPrimaryForm() {
        TypeReference[] typeReferenceArr;
        ReferenceExpression referenceExpressionNewReferenceExpression = newReferenceExpression();
        long j = this.identifierPositionStack[this.identifierPtr];
        int i = (int) j;
        referenceExpressionNewReferenceExpression.nameSourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        char[] cArr2 = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr[i3];
        if (i4 > 0) {
            int i5 = this.genericsPtr - i4;
            this.genericsPtr = i5;
            typeReferenceArr = new TypeReference[i4];
            System.arraycopy(this.genericsStack, i5 + 1, typeReferenceArr, 0, i4);
            this.intPtr--;
        } else {
            typeReferenceArr = null;
        }
        Expression[] expressionArr = this.expressionStack;
        int i6 = this.expressionPtr;
        this.expressionPtr = i6 - 1;
        Expression expression = expressionArr[i6];
        this.expressionLengthPtr--;
        referenceExpressionNewReferenceExpression.initialize(this.compilationUnit.compilationResult, expression, typeReferenceArr, cArr2, i);
        consumeReferenceExpression(referenceExpressionNewReferenceExpression);
    }

    protected void consumeReferenceExpressionSuperForm() {
        TypeReference[] typeReferenceArr;
        ReferenceExpression referenceExpressionNewReferenceExpression = newReferenceExpression();
        long j = this.identifierPositionStack[this.identifierPtr];
        int i = (int) j;
        referenceExpressionNewReferenceExpression.nameSourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        char[] cArr2 = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr[i3];
        if (i4 > 0) {
            int i5 = this.genericsPtr - i4;
            this.genericsPtr = i5;
            typeReferenceArr = new TypeReference[i4];
            System.arraycopy(this.genericsStack, i5 + 1, typeReferenceArr, 0, i4);
            this.intPtr--;
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        referenceExpressionNewReferenceExpression.initialize(this.compilationUnit.compilationResult, new SuperReference(iArr2[i6], this.endPosition), typeReferenceArr, cArr2, i);
        consumeReferenceExpression(referenceExpressionNewReferenceExpression);
    }

    protected void consumeReferenceExpression(ReferenceExpression referenceExpression) {
        pushOnExpressionStack(referenceExpression);
        if (!this.parsingJava8Plus) {
            problemReporter().referenceExpressionsNotBelow18(referenceExpression);
        }
        if (referenceExpression.compilationResult.getCompilationUnit() == null) {
            int i = (referenceExpression.sourceEnd - referenceExpression.sourceStart) + 1;
            char[] source = this.scanner.getSource();
            int i2 = referenceExpression.sourceStart;
            char[] cArr = new char[i];
            referenceExpression.text = cArr;
            System.arraycopy(source, i2, cArr, 0, i);
        }
        this.referenceContext.compilationResult().hasFunctionalTypes = true;
        markEnclosingMemberWithLocalOrFunctionalType(LocalTypeKind.METHOD_REFERENCE);
    }

    protected void consumeReferenceExpressionTypeArgumentsAndTrunk(boolean z) {
        pushOnIntStack(z ? 1 : 0);
        pushOnIntStack(this.scanner.startPosition - 1);
    }

    protected void consumeReferenceExpressionGenericTypeForm() {
        TypeReference[] typeReferenceArr;
        TypeReference typeReference;
        ReferenceExpression referenceExpressionNewReferenceExpression = newReferenceExpression();
        long j = this.identifierPositionStack[this.identifierPtr];
        int i = (int) j;
        referenceExpressionNewReferenceExpression.nameSourceStart = (int) (j >>> 32);
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        this.identifierPtr = i2 - 1;
        char[] cArr2 = cArr[i2];
        this.identifierLengthPtr--;
        int[] iArr = this.genericsLengthStack;
        int i3 = this.genericsLengthPtr;
        this.genericsLengthPtr = i3 - 1;
        int i4 = iArr[i3];
        if (i4 > 0) {
            int i5 = this.genericsPtr - i4;
            this.genericsPtr = i5;
            typeReferenceArr = new TypeReference[i4];
            System.arraycopy(this.genericsStack, i5 + 1, typeReferenceArr, 0, i4);
            this.intPtr--;
        } else {
            typeReferenceArr = null;
        }
        int[] iArr2 = this.intStack;
        int i6 = this.intPtr;
        int i7 = i6 - 1;
        this.intPtr = i7;
        int i8 = iArr2[i6];
        int i9 = i6 - 2;
        this.intPtr = i9;
        boolean z = iArr2[i7] != 0;
        this.intPtr = i6 - 3;
        int i10 = iArr2[i9];
        if (z) {
            typeReference = computeQualifiedGenericsFromRightSide(getTypeReference(0), i10, i10 != 0 ? getAnnotationsOnDimensions(i10) : null);
        } else {
            pushOnGenericsIdentifiersLengthStack(this.identifierLengthStack[this.identifierLengthPtr]);
            typeReference = getTypeReference(i10);
        }
        TypeReference typeReference2 = typeReference;
        this.intPtr--;
        typeReference2.sourceEnd = i8;
        referenceExpressionNewReferenceExpression.initialize(this.compilationUnit.compilationResult, typeReference2, typeReferenceArr, cArr2, i);
        consumeReferenceExpression(referenceExpressionNewReferenceExpression);
    }

    protected void consumeSimpleAssertStatement() {
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        Expression expression = expressionArr[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        pushOnAstStack(new AssertStatement(expression, iArr[i2]));
    }

    protected void consumeSingleMemberAnnotation(boolean z) {
        int i = this.identifierPtr;
        TypeReference annotationType = getAnnotationType();
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        SingleMemberAnnotation singleMemberAnnotation = new SingleMemberAnnotation(annotationType, iArr[i2]);
        Expression[] expressionArr = this.expressionStack;
        int i3 = this.expressionPtr;
        this.expressionPtr = i3 - 1;
        singleMemberAnnotation.memberValue = expressionArr[i3];
        this.expressionLengthPtr--;
        singleMemberAnnotation.declarationSourceEnd = this.rParenPos;
        if (z) {
            pushOnTypeAnnotationStack(singleMemberAnnotation);
        } else {
            pushOnExpressionStack(singleMemberAnnotation);
        }
        if (this.currentElement != null) {
            annotationRecoveryCheckPoint(singleMemberAnnotation.sourceStart, singleMemberAnnotation.declarationSourceEnd);
            RecoveredElement recoveredElement = this.currentElement;
            if (recoveredElement instanceof RecoveredAnnotation) {
                this.currentElement = ((RecoveredAnnotation) recoveredElement).addAnnotation(singleMemberAnnotation, i);
            }
        }
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            problemReporter().invalidUsageOfAnnotation(singleMemberAnnotation);
        }
        this.recordStringLiterals = true;
    }

    protected void consumeSingleMemberAnnotationMemberValue() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || !(recoveredElement instanceof RecoveredAnnotation)) {
            return;
        }
        ((RecoveredAnnotation) recoveredElement).setKind(2);
    }

    protected void consumeSingleStaticImportDeclarationName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        char[][] cArr = new char[i2][];
        int i3 = this.identifierPtr - i2;
        this.identifierPtr = i3;
        long[] jArr = new long[i2];
        System.arraycopy(this.identifierStack, i3 + 1, cArr, 0, i2);
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr, 0, i2);
        ImportReference importReference = new ImportReference(cArr, jArr, false, 8);
        pushOnAstStack(importReference);
        this.modifiers = 0;
        this.modifiersSourceStart = -1;
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        importReference.declarationSourceStart = iArr2[i4];
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            importReference.modifiers = 0;
            problemReporter().invalidUsageOfStaticImports(importReference);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.currentElement = this.currentElement.add(importReference, 0);
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeSingleTypeImportDeclarationName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        char[][] cArr = new char[i2][];
        int i3 = this.identifierPtr - i2;
        this.identifierPtr = i3;
        long[] jArr = new long[i2];
        System.arraycopy(this.identifierStack, i3 + 1, cArr, 0, i2);
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr, 0, i2);
        ImportReference importReference = new ImportReference(cArr, jArr, false, 0);
        pushOnAstStack(importReference);
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        importReference.declarationSourceStart = iArr2[i4];
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.currentElement = this.currentElement.add(importReference, 0);
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeStatementBreak() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnAstStack(new BreakStatement(null, iArr[i], this.endStatementPosition));
        TypeDeclaration typeDeclaration = this.pendingRecoveredType;
        if (typeDeclaration != null) {
            if (typeDeclaration.allocation == null && this.endPosition <= this.pendingRecoveredType.declarationSourceEnd) {
                this.astStack[this.astPtr] = this.pendingRecoveredType;
                this.pendingRecoveredType = null;
            } else {
                this.pendingRecoveredType = null;
            }
        }
    }

    protected void consumeStatementBreakWithLabel() {
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        char[] cArr2 = cArr[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        pushOnAstStack(new BreakStatement(cArr2, iArr[i2], this.endStatementPosition));
        this.identifierLengthPtr--;
    }

    protected void consumeStatementCatch() {
        this.astLengthPtr--;
        this.listLength = 0;
    }

    protected void consumeStatementContinue() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnAstStack(new ContinueStatement(null, iArr[i], this.endStatementPosition));
    }

    protected void consumeStatementContinueWithLabel() {
        char[][] cArr = this.identifierStack;
        int i = this.identifierPtr;
        this.identifierPtr = i - 1;
        char[] cArr2 = cArr[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        pushOnAstStack(new ContinueStatement(cArr2, iArr[i2], this.endStatementPosition));
        this.identifierLengthPtr--;
    }

    protected void consumeStatementDo() {
        this.intPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        Statement statement = (Statement) aSTNodeArr[i];
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        aSTNodeArr[i] = new DoStatement(expression, statement, iArr[i3], this.endStatementPosition);
    }

    protected void consumeStatementExpressionList() {
        concatExpressionLists();
    }

    protected void consumeStatementFor() {
        Statement[] statementArr;
        Expression expression;
        boolean z;
        this.astLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        this.astPtr = i - 1;
        Statement statement = (Statement) aSTNodeArr[i];
        int[] iArr = this.expressionLengthStack;
        int i2 = this.expressionLengthPtr;
        this.expressionLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        Statement[] statementArr2 = null;
        if (i3 == 0) {
            statementArr = null;
        } else {
            int i4 = this.expressionPtr - i3;
            this.expressionPtr = i4;
            Statement[] statementArr3 = new Statement[i3];
            System.arraycopy(this.expressionStack, i4 + 1, statementArr3, 0, i3);
            statementArr = statementArr3;
        }
        int[] iArr2 = this.expressionLengthStack;
        int i5 = this.expressionLengthPtr;
        int i6 = i5 - 1;
        this.expressionLengthPtr = i6;
        if (iArr2[i5] != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i7 = this.expressionPtr;
            this.expressionPtr = i7 - 1;
            expression = expressionArr[i7];
        } else {
            expression = null;
        }
        int[] iArr3 = this.astLengthStack;
        int i8 = this.astLengthPtr;
        this.astLengthPtr = i8 - 1;
        int i9 = iArr3[i8];
        if (i9 == 0) {
            z = false;
        } else if (i9 == -1) {
            this.expressionLengthPtr = i5 - 2;
            int i10 = iArr2[i6];
            int i11 = this.expressionPtr - i10;
            this.expressionPtr = i11;
            Statement[] statementArr4 = new Statement[i10];
            System.arraycopy(this.expressionStack, i11 + 1, statementArr4, 0, i10);
            statementArr2 = statementArr4;
            z = false;
        } else {
            int i12 = this.astPtr - i9;
            this.astPtr = i12;
            Statement[] statementArr5 = new Statement[i9];
            System.arraycopy(this.astStack, i12 + 1, statementArr5, 0, i9);
            z = true;
            statementArr2 = statementArr5;
        }
        int[] iArr4 = this.intStack;
        int i13 = this.intPtr;
        this.intPtr = i13 - 1;
        pushOnAstStack(new ForStatement(statementArr2, expression, statementArr, statement, z, iArr4[i13], this.endStatementPosition));
    }

    protected void consumeStatementIfNoElse() {
        this.expressionLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        Statement statement = (Statement) aSTNodeArr[i];
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        aSTNodeArr[i] = new IfStatement(expression, statement, iArr[i3], this.endStatementPosition);
    }

    protected void consumeStatementIfWithElse() {
        this.expressionLengthPtr--;
        this.astLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr - 1;
        this.astPtr = i;
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        ASTNode[] aSTNodeArr2 = this.astStack;
        int i3 = this.astPtr;
        Statement statement = (Statement) aSTNodeArr2[i3];
        Statement statement2 = (Statement) aSTNodeArr2[i3 + 1];
        int[] iArr = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        aSTNodeArr[i] = new IfStatement(expression, statement, statement2, iArr[i4], this.endStatementPosition);
    }

    protected void consumeStatementLabel() {
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        Statement statement = (Statement) aSTNodeArr[i];
        char[][] cArr = this.identifierStack;
        int i2 = this.identifierPtr;
        char[] cArr2 = cArr[i2];
        long[] jArr = this.identifierPositionStack;
        this.identifierPtr = i2 - 1;
        aSTNodeArr[i] = new LabeledStatement(cArr2, statement, jArr[i2], this.endStatementPosition);
        this.identifierLengthPtr--;
    }

    protected void consumeStatementReturn() {
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        if (iArr[i] != 0) {
            Expression[] expressionArr = this.expressionStack;
            int i2 = this.expressionPtr;
            this.expressionPtr = i2 - 1;
            Expression expression = expressionArr[i2];
            int[] iArr2 = this.intStack;
            int i3 = this.intPtr;
            this.intPtr = i3 - 1;
            pushOnAstStack(new ReturnStatement(expression, iArr2[i3], this.endStatementPosition));
            return;
        }
        int[] iArr3 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        pushOnAstStack(new ReturnStatement(null, iArr3[i4], this.endStatementPosition));
    }

    protected void consumeStatementSwitch() {
        SwitchStatement switchStatement = new SwitchStatement();
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        switchStatement.expression = expressionArr[i];
        int[] iArr = this.astLengthStack;
        int i2 = this.astLengthPtr;
        this.astLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        if (i3 != 0) {
            int i4 = this.astPtr - i3;
            this.astPtr = i4;
            Statement[] statementArr = new Statement[i3];
            switchStatement.statements = statementArr;
            System.arraycopy(this.astStack, i4 + 1, statementArr, 0, i3);
        }
        int[] iArr2 = this.realBlockStack;
        int i5 = this.realBlockPtr;
        this.realBlockPtr = i5 - 1;
        switchStatement.explicitDeclarations = iArr2[i5];
        pushOnAstStack(switchStatement);
        int[] iArr3 = this.intStack;
        int i6 = this.intPtr;
        this.intPtr = i6 - 1;
        switchStatement.blockStart = iArr3[i6];
        int[] iArr4 = this.intStack;
        int i7 = this.intPtr;
        this.intPtr = i7 - 1;
        switchStatement.sourceStart = iArr4[i7];
        switchStatement.sourceEnd = this.endStatementPosition;
        if (i3 != 0 || containsComment(switchStatement.blockStart, switchStatement.sourceEnd)) {
            return;
        }
        switchStatement.bits |= 8;
    }

    protected void consumeStatementSynchronized() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr;
        if (iArr[i] == 0) {
            iArr[i] = 1;
            this.expressionLengthPtr--;
            ASTNode[] aSTNodeArr = this.astStack;
            int i2 = this.astPtr + 1;
            this.astPtr = i2;
            Expression[] expressionArr = this.expressionStack;
            int i3 = this.expressionPtr;
            this.expressionPtr = i3 - 1;
            Expression expression = expressionArr[i3];
            int[] iArr2 = this.intStack;
            int i4 = this.intPtr;
            this.intPtr = i4 - 1;
            aSTNodeArr[i2] = new SynchronizedStatement(expression, null, iArr2[i4], this.endStatementPosition);
        } else {
            this.expressionLengthPtr--;
            ASTNode[] aSTNodeArr2 = this.astStack;
            int i5 = this.astPtr;
            Expression[] expressionArr2 = this.expressionStack;
            int i6 = this.expressionPtr;
            this.expressionPtr = i6 - 1;
            Expression expression2 = expressionArr2[i6];
            Block block = (Block) this.astStack[this.astPtr];
            int[] iArr3 = this.intStack;
            int i7 = this.intPtr;
            this.intPtr = i7 - 1;
            aSTNodeArr2[i5] = new SynchronizedStatement(expression2, block, iArr3[i7], this.endStatementPosition);
        }
        this.modifiers = 0;
        this.modifiersSourceStart = -1;
    }

    protected void consumeStatementThrow() {
        this.expressionLengthPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        Expression expression = expressionArr[i];
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        pushOnAstStack(new ThrowStatement(expression, iArr[i2], this.endStatementPosition));
    }

    protected void consumeStatementTry(boolean z, boolean z2) {
        TryStatement tryStatement = new TryStatement();
        if (z) {
            this.astLengthPtr--;
            ASTNode[] aSTNodeArr = this.astStack;
            int i = this.astPtr;
            this.astPtr = i - 1;
            tryStatement.finallyBlock = (Block) aSTNodeArr[i];
        }
        int[] iArr = this.astLengthStack;
        int i2 = this.astLengthPtr;
        this.astLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        if (i3 != 0) {
            if (i3 == 1) {
                ASTNode[] aSTNodeArr2 = this.astStack;
                int i4 = this.astPtr;
                this.astPtr = i4 - 1;
                tryStatement.catchBlocks = new Block[]{(Block) aSTNodeArr2[i4]};
                ASTNode[] aSTNodeArr3 = this.astStack;
                int i5 = this.astPtr;
                this.astPtr = i5 - 1;
                tryStatement.catchArguments = new Argument[]{(Argument) aSTNodeArr3[i5]};
            } else {
                Block[] blockArr = new Block[i3];
                tryStatement.catchBlocks = blockArr;
                Argument[] argumentArr = new Argument[i3];
                tryStatement.catchArguments = argumentArr;
                while (true) {
                    int i6 = i3 - 1;
                    if (i3 <= 0) {
                        break;
                    }
                    ASTNode[] aSTNodeArr4 = this.astStack;
                    int i7 = this.astPtr;
                    int i8 = i7 - 1;
                    this.astPtr = i8;
                    blockArr[i6] = (Block) aSTNodeArr4[i7];
                    this.astPtr = i7 - 2;
                    argumentArr[i6] = (Argument) aSTNodeArr4[i8];
                    i3 = i6;
                }
            }
        }
        this.astLengthPtr--;
        ASTNode[] aSTNodeArr5 = this.astStack;
        int i9 = this.astPtr;
        this.astPtr = i9 - 1;
        tryStatement.tryBlock = (Block) aSTNodeArr5[i9];
        if (z2) {
            int[] iArr2 = this.astLengthStack;
            int i10 = this.astLengthPtr;
            this.astLengthPtr = i10 - 1;
            int i11 = iArr2[i10];
            LocalDeclaration[] localDeclarationArr = new LocalDeclaration[i11];
            ASTNode[] aSTNodeArr6 = this.astStack;
            int i12 = this.astPtr - i11;
            this.astPtr = i12;
            System.arraycopy(aSTNodeArr6, i12 + 1, localDeclarationArr, 0, i11);
            tryStatement.resources = localDeclarationArr;
            if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
                problemReporter().autoManagedResourcesNotBelow17(localDeclarationArr);
            }
        }
        tryStatement.sourceEnd = this.endStatementPosition;
        int[] iArr3 = this.intStack;
        int i13 = this.intPtr;
        this.intPtr = i13 - 1;
        tryStatement.sourceStart = iArr3[i13];
        pushOnAstStack(tryStatement);
    }

    protected void consumeStatementWhile() {
        this.expressionLengthPtr--;
        ASTNode[] aSTNodeArr = this.astStack;
        int i = this.astPtr;
        Statement statement = (Statement) aSTNodeArr[i];
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        this.expressionPtr = i2 - 1;
        Expression expression = expressionArr[i2];
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        aSTNodeArr[i] = new WhileStatement(expression, statement, iArr[i3], this.endStatementPosition);
    }

    protected void consumeStaticImportOnDemandDeclarationName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        char[][] cArr = new char[i2][];
        int i3 = this.identifierPtr - i2;
        this.identifierPtr = i3;
        long[] jArr = new long[i2];
        System.arraycopy(this.identifierStack, i3 + 1, cArr, 0, i2);
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr, 0, i2);
        ImportReference importReference = new ImportReference(cArr, jArr, true, 8);
        pushOnAstStack(importReference);
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        importReference.trailingStarPosition = iArr2[i4];
        this.modifiers = 0;
        this.modifiersSourceStart = -1;
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        int[] iArr3 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        importReference.declarationSourceStart = iArr3[i5];
        if (!this.statementRecoveryActivated && this.options.sourceLevel < ClassFileConstants.JDK1_5 && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
            importReference.modifiers = 0;
            problemReporter().invalidUsageOfStaticImports(importReference);
        }
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.currentElement = this.currentElement.add(importReference, 0);
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeStaticInitializer() {
        Block block = (Block) this.astStack[this.astPtr];
        if (this.diet) {
            block.bits &= -9;
        }
        Initializer initializer = new Initializer(block, 8);
        this.astStack[this.astPtr] = initializer;
        initializer.sourceEnd = this.endStatementPosition;
        initializer.declarationSourceEnd = flushCommentsDefinedPriorTo(this.endStatementPosition);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] - 1;
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        initializer.declarationSourceStart = iArr2[i2];
        int[] iArr3 = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        initializer.bodyStart = iArr3[i3];
        initializer.bodyEnd = this.endPosition;
        initializer.javadoc = this.javadoc;
        this.javadoc = null;
        if (this.currentElement != null) {
            this.lastCheckPoint = initializer.declarationSourceEnd;
            this.currentElement = this.currentElement.add((FieldDeclaration) initializer, 0);
            this.lastIgnoredToken = -1;
        }
    }

    protected void consumeStaticOnly() {
        int i = this.modifiersSourceStart;
        checkComment();
        if (this.modifiersSourceStart >= i) {
            this.modifiersSourceStart = i;
        }
        pushOnIntStack(this.scanner.currentPosition);
        int i2 = this.modifiersSourceStart;
        if (i2 < 0) {
            i2 = this.scanner.startPosition;
        }
        pushOnIntStack(i2);
        jumpOverMethodBody();
        int[] iArr = this.nestedMethod;
        int i3 = this.nestedType;
        iArr[i3] = iArr[i3] + 1;
        resetModifiers();
        this.expressionLengthPtr--;
        if (this.currentElement != null) {
            this.recoveredStaticInitializerStart = this.intStack[this.intPtr];
        }
    }

    protected void consumeSwitchBlock() {
        concatNodeLists();
    }

    protected void consumeSwitchBlockStatement() {
        concatNodeLists();
    }

    protected void consumeSwitchBlockStatements() {
        concatNodeLists();
    }

    protected void consumeSwitchLabels() {
        optimizedConcatNodeLists();
    }

    protected void consumeToken(int i) {
        StringLiteral stringLiteralCreateStringLiteral;
        if (i != 1 && i != 2) {
            if (i != 4 && i != 5) {
                if (i == 6) {
                    pushOnIntStack(this.scanner.currentPosition - 1);
                    return;
                }
                if (i == 7) {
                    this.colonColonStart = this.scanner.currentPosition - 2;
                    return;
                }
                if (i == 11) {
                    pushOnIntStack(this.scanner.startPosition);
                    return;
                }
                if (i == 22) {
                    pushIdentifier();
                    if (this.scanner.useAssertAsAnIndentifier && this.lastErrorEndPositionBeforeRecovery < this.scanner.currentPosition) {
                        long j = this.identifierPositionStack[this.identifierPtr];
                        if (!this.statementRecoveryActivated) {
                            problemReporter().useAssertAsAnIdentifier((int) (j >>> 32), (int) j);
                        }
                    }
                    if (!this.scanner.useEnumAsAnIndentifier || this.lastErrorEndPositionBeforeRecovery >= this.scanner.currentPosition) {
                        return;
                    }
                    long j2 = this.identifierPositionStack[this.identifierPtr];
                    if (this.statementRecoveryActivated) {
                        return;
                    }
                    problemReporter().useEnumAsAnIdentifier((int) (j2 >>> 32), (int) j2);
                    return;
                }
                if (i != 32) {
                    if (i != 95) {
                        if (i == 110) {
                            consumeLambdaHeader();
                            return;
                        }
                        if (i == 113) {
                            pushOnIntStack(this.scanner.currentPosition - 1);
                            return;
                        }
                        if (i == 24) {
                            this.lParenPos = this.scanner.startPosition;
                            return;
                        }
                        if (i == 25) {
                            this.rParenPos = this.scanner.currentPosition - 1;
                            return;
                        }
                        switch (i) {
                            case 27:
                                this.expectTypeAnnotation = true;
                                pushOnIntStack(this.dimensions);
                                this.dimensions = 0;
                                pushOnIntStack(this.scanner.startPosition);
                                break;
                            case 28:
                                break;
                            case 29:
                                pushOnIntStack(this.scanner.startPosition);
                                pushOnIntStack(this.scanner.currentPosition - 1);
                                break;
                            default:
                                switch (i) {
                                    case 34:
                                    case 35:
                                        this.endPosition = this.scanner.currentPosition - 1;
                                        pushOnIntStack(this.scanner.startPosition);
                                        break;
                                    case 36:
                                        resetModifiers();
                                        pushOnIntStack(this.scanner.startPosition);
                                        break;
                                    case 37:
                                        pushOnIntStack(this.scanner.startPosition);
                                        break;
                                    case 38:
                                        pushOnExpressionStack(new FalseLiteral(this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 39:
                                        pushOnExpressionStack(new NullLiteral(this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 40:
                                        checkAndSetModifiers(8);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 41:
                                        this.synchronizedBlockSourceStart = this.scanner.startPosition;
                                        checkAndSetModifiers(32);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 42:
                                        pushOnExpressionStack(new TrueLiteral(this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 43:
                                        pushOnExpressionStack(IntLiteral.buildIntLiteral(this.scanner.getCurrentTokenSource(), this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 44:
                                        pushOnExpressionStack(LongLiteral.buildLongLiteral(this.scanner.getCurrentTokenSource(), this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 45:
                                        pushOnExpressionStack(new FloatLiteral(this.scanner.getCurrentTokenSource(), this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 46:
                                        pushOnExpressionStack(new DoubleLiteral(this.scanner.getCurrentTokenSource(), this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 47:
                                        pushOnExpressionStack(new CharLiteral(this.scanner.getCurrentTokenSource(), this.scanner.startPosition, this.scanner.currentPosition - 1));
                                        break;
                                    case 48:
                                        if (this.recordStringLiterals && !this.reparsingLambdaExpression && this.checkExternalizeStrings && this.lastPosistion < this.scanner.currentPosition && !this.statementRecoveryActivated) {
                                            stringLiteralCreateStringLiteral = createStringLiteral(this.scanner.getCurrentTokenSourceString(), this.scanner.startPosition, this.scanner.currentPosition - 1, Util.getLineNumber(this.scanner.startPosition, this.scanner.lineEnds, 0, this.scanner.linePtr));
                                            this.compilationUnit.recordStringLiteral(stringLiteralCreateStringLiteral, this.currentElement != null);
                                        } else {
                                            stringLiteralCreateStringLiteral = createStringLiteral(this.scanner.getCurrentTokenSourceString(), this.scanner.startPosition, this.scanner.currentPosition - 1, 0);
                                        }
                                        pushOnExpressionStack(stringLiteralCreateStringLiteral);
                                        break;
                                    case 49:
                                        this.endStatementPosition = this.scanner.currentPosition - 1;
                                        break;
                                    case 50:
                                        flushCommentsDefinedPriorTo(this.scanner.currentPosition);
                                        break;
                                    case 51:
                                        checkAndSetModifiers(1024);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 52:
                                        checkAndSetModifiers(16);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 53:
                                        checkAndSetModifiers(256);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 54:
                                        checkAndSetModifiers(2);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 55:
                                        checkAndSetModifiers(4);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 56:
                                        checkAndSetModifiers(1);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 57:
                                        checkAndSetModifiers(2048);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 58:
                                        checkAndSetModifiers(128);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    case 59:
                                        checkAndSetModifiers(64);
                                        pushOnExpressionStackLengthStack(0);
                                        break;
                                    default:
                                        switch (i) {
                                            case 62:
                                            case 63:
                                                break;
                                            case 64:
                                                this.rBracketPosition = this.scanner.startPosition;
                                                this.endPosition = this.scanner.startPosition;
                                                this.endStatementPosition = this.scanner.currentPosition - 1;
                                                break;
                                            default:
                                                switch (i) {
                                                    case 67:
                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                        pushOnIntStack(this.scanner.startPosition);
                                                        break;
                                                    case 68:
                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                        pushOnIntStack(this.scanner.startPosition);
                                                        break;
                                                    case 69:
                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                        pushOnIntStack(this.scanner.startPosition);
                                                        break;
                                                    case 70:
                                                        RecoveredElement recoveredElement = this.currentElement;
                                                        if (recoveredElement != null && (recoveredElement instanceof RecoveredAnnotation)) {
                                                            RecoveredAnnotation recoveredAnnotation = (RecoveredAnnotation) recoveredElement;
                                                            if (recoveredAnnotation.memberValuPairEqualEnd == -1) {
                                                                recoveredAnnotation.memberValuPairEqualEnd = this.scanner.currentPosition - 1;
                                                            }
                                                            break;
                                                        }
                                                        break;
                                                    case 71:
                                                    case 72:
                                                    case 73:
                                                    case 74:
                                                    case 76:
                                                    case 78:
                                                    case 79:
                                                    case 80:
                                                    case 81:
                                                    case 82:
                                                        break;
                                                    case 75:
                                                        pushOnIntStack(this.scanner.startPosition);
                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                        break;
                                                    case 77:
                                                        this.forStartPosition = this.scanner.startPosition;
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 97:
                                                                pushIdentifier(-5);
                                                                pushOnIntStack(this.scanner.currentPosition - 1);
                                                                pushOnIntStack(this.scanner.startPosition);
                                                                break;
                                                            case 98:
                                                                pushIdentifier(-3);
                                                                pushOnIntStack(this.scanner.currentPosition - 1);
                                                                pushOnIntStack(this.scanner.startPosition);
                                                                break;
                                                            case 99:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 101:
                                                                        pushIdentifier(-2);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 102:
                                                                        pushIdentifier(-8);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 103:
                                                                        pushIdentifier(-9);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 105:
                                                                        pushIdentifier(-10);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 106:
                                                                        pushIdentifier(-7);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 107:
                                                                        pushIdentifier(-4);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                    case 108:
                                                                        pushIdentifier(-6);
                                                                        pushOnIntStack(this.scanner.currentPosition - 1);
                                                                        pushOnIntStack(this.scanner.startPosition);
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                    pushOnIntStack(this.scanner.startPosition);
                    return;
                }
                this.endStatementPosition = this.scanner.currentPosition - 1;
                this.endPosition = this.scanner.startPosition - 1;
                return;
            }
            this.endPosition = this.scanner.startPosition;
            return;
        }
        this.endPosition = this.scanner.startPosition;
        this.endStatementPosition = this.scanner.currentPosition - 1;
    }

    protected void consumeTypeArgument() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        pushOnGenericsStack(getTypeReference(iArr[i]));
    }

    protected void consumeTypeArgumentList() {
        concatGenericsLists();
    }

    protected void consumeTypeArgumentList1() {
        concatGenericsLists();
    }

    protected void consumeTypeArgumentList2() {
        concatGenericsLists();
    }

    protected void consumeTypeArgumentList3() {
        concatGenericsLists();
    }

    protected void consumeTypeArgumentReferenceType1() {
        concatGenericsLists();
        pushOnGenericsStack(getTypeReference(0));
        this.intPtr--;
    }

    protected void consumeTypeArgumentReferenceType2() {
        concatGenericsLists();
        pushOnGenericsStack(getTypeReference(0));
        this.intPtr--;
    }

    protected void consumeTypeArguments() {
        concatGenericsLists();
        this.intPtr--;
        if (this.statementRecoveryActivated || this.options.sourceLevel >= ClassFileConstants.JDK1_5 || this.lastErrorEndPositionBeforeRecovery >= this.scanner.currentPosition) {
            return;
        }
        int i = this.genericsLengthStack[this.genericsLengthPtr];
        ProblemReporter problemReporter = problemReporter();
        ASTNode[] aSTNodeArr = this.genericsStack;
        int i2 = this.genericsPtr;
        problemReporter.invalidUsageOfTypeArguments((TypeReference) aSTNodeArr[(i2 - i) + 1], (TypeReference) aSTNodeArr[i2]);
    }

    protected void consumeTypeDeclarations() {
        concatNodeLists();
    }

    protected void consumeTypeHeaderNameWithTypeParameters() {
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        TypeParameter[] typeParameterArr = new TypeParameter[i2];
        typeDeclaration.typeParameters = typeParameterArr;
        System.arraycopy(this.genericsStack, i3 + 1, typeParameterArr, 0, i2);
        typeDeclaration.bodyStart = typeDeclaration.typeParameters[i2 - 1].declarationSourceEnd + 1;
        this.listTypeParameterLength = 0;
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null) {
            if (recoveredElement instanceof RecoveredType) {
                ((RecoveredType) recoveredElement).pendingTypeParameters = null;
                this.lastCheckPoint = typeDeclaration.bodyStart;
            } else {
                this.lastCheckPoint = typeDeclaration.bodyStart;
                this.currentElement = this.currentElement.add(typeDeclaration, 0);
                this.lastIgnoredToken = -1;
            }
        }
    }

    protected void consumeTypeImportOnDemandDeclarationName() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        char[][] cArr = new char[i2][];
        int i3 = this.identifierPtr - i2;
        this.identifierPtr = i3;
        long[] jArr = new long[i2];
        System.arraycopy(this.identifierStack, i3 + 1, cArr, 0, i2);
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr, 0, i2);
        ImportReference importReference = new ImportReference(cArr, jArr, true, 0);
        pushOnAstStack(importReference);
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        importReference.trailingStarPosition = iArr2[i4];
        if (this.currentToken == 28) {
            importReference.declarationSourceEnd = this.scanner.currentPosition - 1;
        } else {
            importReference.declarationSourceEnd = importReference.sourceEnd;
        }
        importReference.declarationEnd = importReference.declarationSourceEnd;
        int[] iArr3 = this.intStack;
        int i5 = this.intPtr;
        this.intPtr = i5 - 1;
        importReference.declarationSourceStart = iArr3[i5];
        if (this.currentElement != null) {
            this.lastCheckPoint = importReference.declarationSourceEnd + 1;
            this.currentElement = this.currentElement.add(importReference, 0);
            this.lastIgnoredToken = -1;
            this.restartRecovery = true;
        }
    }

    protected void consumeTypeParameter1WithExtends() {
        ASTNode[] aSTNodeArr = this.genericsStack;
        int i = this.genericsPtr;
        int i2 = i - 1;
        this.genericsPtr = i2;
        TypeReference typeReference = (TypeReference) aSTNodeArr[i];
        this.genericsLengthPtr--;
        TypeParameter typeParameter = (TypeParameter) aSTNodeArr[i2];
        typeParameter.declarationSourceEnd = typeReference.sourceEnd;
        typeParameter.type = typeReference;
        typeReference.bits |= 16;
        typeParameter.bits = (typeReference.bits & 1048576) | typeParameter.bits;
        this.genericsStack[this.genericsPtr] = typeParameter;
    }

    protected void consumeTypeParameter1WithExtendsAndBounds() {
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        TypeReference[] typeReferenceArr = new TypeReference[i2];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 0, i2);
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        TypeReference typeReference = getTypeReference(iArr2[i4]);
        TypeParameter typeParameter = (TypeParameter) this.genericsStack[this.genericsPtr];
        typeParameter.declarationSourceEnd = typeReferenceArr[i2 - 1].sourceEnd;
        typeParameter.type = typeReference;
        typeParameter.bits |= typeReference.bits & 1048576;
        typeReference.bits |= 16;
        typeParameter.bounds = typeReferenceArr;
        for (int i5 = 0; i5 < i2; i5++) {
            TypeReference typeReference2 = typeReferenceArr[i5];
            typeReference2.bits |= 16;
            typeParameter.bits = (typeReference2.bits & 1048576) | typeParameter.bits;
        }
    }

    protected void consumeTypeParameterHeader() {
        TypeParameter typeParameter = new TypeParameter();
        int[] iArr = this.typeAnnotationLengthStack;
        int i = this.typeAnnotationLengthPtr;
        this.typeAnnotationLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            Annotation[] annotationArr = this.typeAnnotationStack;
            int i3 = this.typeAnnotationPtr - i2;
            this.typeAnnotationPtr = i3;
            Annotation[] annotationArr2 = new Annotation[i2];
            typeParameter.annotations = annotationArr2;
            System.arraycopy(annotationArr, i3 + 1, annotationArr2, 0, i2);
            typeParameter.bits |= 1048576;
        }
        long j = this.identifierPositionStack[this.identifierPtr];
        int i4 = (int) j;
        typeParameter.declarationSourceEnd = i4;
        typeParameter.sourceEnd = i4;
        int i5 = (int) (j >>> 32);
        typeParameter.declarationSourceStart = i5;
        typeParameter.sourceStart = i5;
        char[][] cArr = this.identifierStack;
        int i6 = this.identifierPtr;
        this.identifierPtr = i6 - 1;
        typeParameter.f617name = cArr[i6];
        this.identifierLengthPtr--;
        pushOnGenericsStack(typeParameter);
        this.listTypeParameterLength++;
    }

    protected void consumeTypeParameterList() {
        concatGenericsLists();
    }

    protected void consumeTypeParameterList1() {
        concatGenericsLists();
    }

    protected void consumeTypeParameters() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        int i2 = iArr[i];
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement != null && (recoveredElement instanceof RecoveredType)) {
            int i3 = this.genericsLengthStack[this.genericsLengthPtr];
            TypeParameter[] typeParameterArr = new TypeParameter[i3];
            System.arraycopy(this.genericsStack, (this.genericsPtr - i3) + 1, typeParameterArr, 0, i3);
            ((RecoveredType) recoveredElement).add(typeParameterArr, i2);
        }
        if (this.statementRecoveryActivated || this.options.sourceLevel >= ClassFileConstants.JDK1_5 || this.lastErrorEndPositionBeforeRecovery >= this.scanner.currentPosition) {
            return;
        }
        int i4 = this.genericsLengthStack[this.genericsLengthPtr];
        ProblemReporter problemReporter = problemReporter();
        ASTNode[] aSTNodeArr = this.genericsStack;
        int i5 = this.genericsPtr;
        problemReporter.invalidUsageOfTypeParameters((TypeParameter) aSTNodeArr[(i5 - i4) + 1], (TypeParameter) aSTNodeArr[i5]);
    }

    protected void consumeTypeParameterWithExtends() {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        TypeReference typeReference = getTypeReference(iArr[i]);
        TypeParameter typeParameter = (TypeParameter) this.genericsStack[this.genericsPtr];
        typeParameter.declarationSourceEnd = typeReference.sourceEnd;
        typeParameter.type = typeReference;
        typeParameter.bits |= typeReference.bits & 1048576;
        typeReference.bits |= 16;
    }

    protected void consumeTypeParameterWithExtendsAndBounds() {
        int[] iArr = this.genericsLengthStack;
        int i = this.genericsLengthPtr;
        this.genericsLengthPtr = i - 1;
        int i2 = iArr[i];
        TypeReference[] typeReferenceArr = new TypeReference[i2];
        int i3 = this.genericsPtr - i2;
        this.genericsPtr = i3;
        System.arraycopy(this.genericsStack, i3 + 1, typeReferenceArr, 0, i2);
        int[] iArr2 = this.intStack;
        int i4 = this.intPtr;
        this.intPtr = i4 - 1;
        TypeReference typeReference = getTypeReference(iArr2[i4]);
        TypeParameter typeParameter = (TypeParameter) this.genericsStack[this.genericsPtr];
        typeParameter.type = typeReference;
        typeParameter.bits |= typeReference.bits & 1048576;
        typeReference.bits |= 16;
        typeParameter.bounds = typeReferenceArr;
        typeParameter.declarationSourceEnd = typeReferenceArr[i2 - 1].sourceEnd;
        for (int i5 = 0; i5 < i2; i5++) {
            TypeReference typeReference2 = typeReferenceArr[i5];
            typeReference2.bits |= 16;
            typeParameter.bits = (typeReference2.bits & 1048576) | typeParameter.bits;
        }
    }

    protected void consumeZeroAdditionalBounds() {
        if (this.currentToken == 25) {
            pushOnGenericsLengthStack(0);
        }
    }

    protected void consumeUnaryExpression(int i) {
        Expression unaryExpression;
        LongLiteral longLiteral;
        Expression expression = this.expressionStack[this.expressionPtr];
        if (i == 13) {
            if (expression instanceof IntLiteral) {
                IntLiteral intLiteral = (IntLiteral) expression;
                unaryExpression = intLiteral.convertToMinValue();
                if (unaryExpression == intLiteral) {
                    unaryExpression = new UnaryExpression(expression, i);
                }
            } else if (!(expression instanceof LongLiteral) || (unaryExpression = (longLiteral = (LongLiteral) expression).convertToMinValue()) == longLiteral) {
                unaryExpression = new UnaryExpression(expression, i);
            }
        } else {
            unaryExpression = new UnaryExpression(expression, i);
        }
        int[] iArr = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        unaryExpression.sourceStart = iArr[i2];
        unaryExpression.sourceEnd = expression.sourceEnd;
        this.expressionStack[this.expressionPtr] = unaryExpression;
    }

    protected void consumeUnaryExpression(int i, boolean z) {
        Expression[] expressionArr = this.expressionStack;
        int i2 = this.expressionPtr;
        Expression expression = expressionArr[i2];
        if (!(expression instanceof Reference)) {
            if (!z) {
                this.intPtr--;
            }
            if (this.statementRecoveryActivated) {
                return;
            }
            problemReporter().invalidUnaryExpression(expression);
            return;
        }
        if (z) {
            expressionArr[i2] = new PostfixExpression(expression, IntLiteral.One, i, this.endStatementPosition);
            return;
        }
        IntLiteral intLiteral = IntLiteral.One;
        int[] iArr = this.intStack;
        int i3 = this.intPtr;
        this.intPtr = i3 - 1;
        expressionArr[i2] = new PrefixExpression(expression, intLiteral, i, iArr[i3]);
    }

    protected void consumeVariableDeclarators() {
        optimizedConcatNodeLists();
    }

    protected void consumeVariableInitializers() {
        concatExpressionLists();
    }

    protected void consumeWildcard() {
        Wildcard wildcard = new Wildcard(0);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.sourceEnd = iArr[i];
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        wildcard.sourceStart = iArr2[i2];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    protected void consumeWildcard1() {
        Wildcard wildcard = new Wildcard(0);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.sourceEnd = iArr[i];
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        wildcard.sourceStart = iArr2[i2];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    protected void consumeWildcard2() {
        Wildcard wildcard = new Wildcard(0);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.sourceEnd = iArr[i];
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        wildcard.sourceStart = iArr2[i2];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    protected void consumeWildcard3() {
        Wildcard wildcard = new Wildcard(0);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.sourceEnd = iArr[i];
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        wildcard.sourceStart = iArr2[i2];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    protected void consumeWildcardBounds1Extends() {
        Wildcard wildcard = new Wildcard(1);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBounds1Super() {
        Wildcard wildcard = new Wildcard(2);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        this.intPtr--;
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBounds2Extends() {
        Wildcard wildcard = new Wildcard(1);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBounds2Super() {
        Wildcard wildcard = new Wildcard(2);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        this.intPtr--;
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBounds3Extends() {
        Wildcard wildcard = new Wildcard(1);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBounds3Super() {
        Wildcard wildcard = new Wildcard(2);
        wildcard.bound = (TypeReference) this.genericsStack[this.genericsPtr];
        this.intPtr--;
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i = this.intPtr;
        int[] iArr = this.intStack;
        this.intPtr = i - 2;
        wildcard.sourceStart = iArr[i - 1];
        annotateTypeReference(wildcard);
        this.genericsStack[this.genericsPtr] = wildcard;
    }

    protected void consumeWildcardBoundsExtends() {
        Wildcard wildcard = new Wildcard(1);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.bound = getTypeReference(iArr[i]);
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i2 = this.intPtr;
        int[] iArr2 = this.intStack;
        this.intPtr = i2 - 2;
        wildcard.sourceStart = iArr2[i2 - 1];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    protected void consumeWildcardBoundsSuper() {
        Wildcard wildcard = new Wildcard(2);
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        wildcard.bound = getTypeReference(iArr[i]);
        this.intPtr--;
        wildcard.sourceEnd = wildcard.bound.sourceEnd;
        int i2 = this.intPtr;
        int[] iArr2 = this.intStack;
        this.intPtr = i2 - 2;
        wildcard.sourceStart = iArr2[i2 - 1];
        annotateTypeReference(wildcard);
        pushOnGenericsStack(wildcard);
    }

    public boolean containsComment(int i, int i2) {
        for (int i3 = this.scanner.commentPtr; i3 >= 0; i3--) {
            int i4 = this.scanner.commentStarts[i3];
            if (i4 < 0) {
                i4 = -i4;
            }
            if (i4 >= i && i4 <= i2) {
                return true;
            }
        }
        return false;
    }

    public MethodDeclaration convertToMethodDeclaration(ConstructorDeclaration constructorDeclaration, CompilationResult compilationResult) {
        MethodDeclaration methodDeclaration = new MethodDeclaration(compilationResult);
        methodDeclaration.typeParameters = constructorDeclaration.typeParameters;
        methodDeclaration.sourceStart = constructorDeclaration.sourceStart;
        methodDeclaration.sourceEnd = constructorDeclaration.sourceEnd;
        methodDeclaration.bodyStart = constructorDeclaration.bodyStart;
        methodDeclaration.bodyEnd = constructorDeclaration.bodyEnd;
        methodDeclaration.declarationSourceEnd = constructorDeclaration.declarationSourceEnd;
        methodDeclaration.declarationSourceStart = constructorDeclaration.declarationSourceStart;
        methodDeclaration.selector = constructorDeclaration.selector;
        methodDeclaration.statements = constructorDeclaration.statements;
        methodDeclaration.modifiers = constructorDeclaration.modifiers;
        methodDeclaration.annotations = constructorDeclaration.annotations;
        methodDeclaration.arguments = constructorDeclaration.arguments;
        methodDeclaration.thrownExceptions = constructorDeclaration.thrownExceptions;
        methodDeclaration.explicitDeclarations = constructorDeclaration.explicitDeclarations;
        methodDeclaration.returnType = null;
        methodDeclaration.javadoc = constructorDeclaration.javadoc;
        methodDeclaration.bits = constructorDeclaration.bits;
        return methodDeclaration;
    }

    protected TypeReference augmentTypeWithAdditionalDimensions(TypeReference typeReference, int i, Annotation[][] annotationArr, boolean z) {
        return typeReference.augmentTypeWithAdditionalDimensions(i, annotationArr, z);
    }

    protected FieldDeclaration createFieldDeclaration(char[] cArr, int i, int i2) {
        return new FieldDeclaration(cArr, i, i2);
    }

    protected JavadocParser createJavadocParser() {
        return new JavadocParser(this);
    }

    protected LocalDeclaration createLocalDeclaration(char[] cArr, int i, int i2) {
        return new LocalDeclaration(cArr, i, i2);
    }

    protected StringLiteral createStringLiteral(char[] cArr, int i, int i2, int i3) {
        return new StringLiteral(cArr, i, i2, i3);
    }

    protected RecoveredType currentRecoveryType() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null) {
            return null;
        }
        if (recoveredElement instanceof RecoveredType) {
            return (RecoveredType) recoveredElement;
        }
        return recoveredElement.enclosingType();
    }

    public CompilationUnitDeclaration dietParse(ICompilationUnit iCompilationUnit, CompilationResult compilationResult) {
        boolean z = this.diet;
        int i = this.dietInt;
        try {
            this.dietInt = 0;
            this.diet = true;
            return parse(iCompilationUnit, compilationResult);
        } finally {
            this.diet = z;
            this.dietInt = i;
        }
    }

    protected void dispatchDeclarationInto(int i) {
        if (i == 0) {
            return;
        }
        int[] iArr = new int[i + 1];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        for (int i5 = i - 1; i5 >= 0; i5--) {
            ASTNode[] aSTNodeArr = this.astStack;
            int i6 = this.astPtr;
            this.astPtr = i6 - 1;
            ASTNode aSTNode = aSTNodeArr[i6];
            if (aSTNode instanceof AbstractMethodDeclaration) {
                iArr[i5] = 2;
                i3++;
                if (((AbstractMethodDeclaration) aSTNode).isAbstract()) {
                    z = true;
                }
            } else if (aSTNode instanceof TypeDeclaration) {
                iArr[i5] = 3;
                i4++;
            } else {
                iArr[i5] = 1;
                i2++;
            }
        }
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr];
        if (i2 != 0) {
            typeDeclaration.fields = new FieldDeclaration[i2];
        }
        if (i3 != 0) {
            typeDeclaration.methods = new AbstractMethodDeclaration[i3];
            if (z) {
                typeDeclaration.bits |= 2048;
            }
        }
        if (i4 != 0) {
            typeDeclaration.memberTypes = new TypeDeclaration[i4];
        }
        int i7 = iArr[0];
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        for (int i12 = 0; i12 <= i; i12++) {
            if (i7 != iArr[i12]) {
                if (i7 == 1) {
                    int i13 = i12 - i9;
                    i8 += i13;
                    System.arraycopy(this.astStack, this.astPtr + i9 + 1, typeDeclaration.fields, i8 - i13, i13);
                } else if (i7 == 2) {
                    int i14 = i12 - i9;
                    i10 += i14;
                    System.arraycopy(this.astStack, this.astPtr + i9 + 1, typeDeclaration.methods, i10 - i14, i14);
                } else if (i7 == 3) {
                    int i15 = i12 - i9;
                    i11 += i15;
                    System.arraycopy(this.astStack, this.astPtr + i9 + 1, typeDeclaration.memberTypes, i11 - i15, i15);
                }
                i7 = iArr[i12];
                i9 = i12;
            }
        }
        if (typeDeclaration.memberTypes != null) {
            for (int length = typeDeclaration.memberTypes.length - 1; length >= 0; length--) {
                typeDeclaration.memberTypes[length].enclosingType = typeDeclaration;
            }
        }
    }

    protected void dispatchDeclarationIntoEnumDeclaration(int i) {
        int i2;
        if (i == 0) {
            return;
        }
        int[] iArr = new int[i + 1];
        TypeDeclaration typeDeclaration = (TypeDeclaration) this.astStack[this.astPtr - i];
        int i3 = i - 1;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        boolean z = false;
        int i8 = 0;
        while (true) {
            i2 = 3;
            if (i3 < 0) {
                break;
            }
            ASTNode[] aSTNodeArr = this.astStack;
            int i9 = this.astPtr;
            this.astPtr = i9 - 1;
            ASTNode aSTNode = aSTNodeArr[i9];
            if (aSTNode instanceof AbstractMethodDeclaration) {
                iArr[i3] = 2;
                i6++;
                if (((AbstractMethodDeclaration) aSTNode).isAbstract()) {
                    z = true;
                }
            } else if (aSTNode instanceof TypeDeclaration) {
                iArr[i3] = 3;
                i7++;
            } else if (aSTNode instanceof FieldDeclaration) {
                iArr[i3] = 1;
                i5++;
                if (((FieldDeclaration) aSTNode).getKind() == 3) {
                    i8++;
                }
            }
            i3--;
        }
        if (i5 != 0) {
            typeDeclaration.fields = new FieldDeclaration[i5];
        }
        if (i6 != 0) {
            typeDeclaration.methods = new AbstractMethodDeclaration[i6];
            if (z) {
                typeDeclaration.bits |= 2048;
            }
        }
        if (i7 != 0) {
            typeDeclaration.memberTypes = new TypeDeclaration[i7];
        }
        int i10 = iArr[0];
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i4 <= i) {
            if (i10 != iArr[i4]) {
                if (i10 == 1) {
                    int i15 = i4 - i12;
                    i11 += i15;
                    System.arraycopy(this.astStack, this.astPtr + i12 + 1, typeDeclaration.fields, i11 - i15, i15);
                } else if (i10 == 2) {
                    int i16 = i4 - i12;
                    i13 += i16;
                    System.arraycopy(this.astStack, this.astPtr + i12 + 1, typeDeclaration.methods, i13 - i16, i16);
                } else if (i10 == i2) {
                    int i17 = i4 - i12;
                    i14 += i17;
                    System.arraycopy(this.astStack, this.astPtr + i12 + 1, typeDeclaration.memberTypes, i14 - i17, i17);
                }
                i10 = iArr[i4];
                i12 = i4;
            }
            i4++;
            i2 = 3;
        }
        if (typeDeclaration.memberTypes != null) {
            for (int length = typeDeclaration.memberTypes.length - 1; length >= 0; length--) {
                typeDeclaration.memberTypes[length].enclosingType = typeDeclaration;
            }
        }
        typeDeclaration.enumConstantsCounter = i8;
    }

    protected CompilationUnitDeclaration endParse(int i) {
        this.lastAct = i;
        if (this.statementRecoveryActivated) {
            RecoveredElement recoveredElementBuildInitialRecoveryState = buildInitialRecoveryState();
            if (recoveredElementBuildInitialRecoveryState != null) {
                recoveredElementBuildInitialRecoveryState.topElement().updateParseTree();
            }
            if (this.hasError) {
                resetStacks();
            }
        } else if (this.currentElement != null) {
            if (VERBOSE_RECOVERY) {
                System.out.print(Messages.parser_syntaxRecovery);
                System.out.println("--------------------------");
                System.out.println(this.compilationUnit);
                System.out.println("----------------------------------");
            }
            this.currentElement.topElement().updateParseTree();
        } else if (this.diet & VERBOSE_RECOVERY) {
            System.out.print(Messages.parser_regularParse);
            System.out.println("--------------------------");
            System.out.println(this.compilationUnit);
            System.out.println("----------------------------------");
        }
        persistLineSeparatorPositions();
        int i2 = 0;
        while (true) {
            if (i2 < this.scanner.foundTaskCount) {
                if (!this.statementRecoveryActivated) {
                    problemReporter().task(new String(this.scanner.foundTaskTags[i2]), new String(this.scanner.foundTaskMessages[i2]), this.scanner.foundTaskPriorities[i2] != null ? new String(this.scanner.foundTaskPriorities[i2]) : null, this.scanner.foundTaskPositions[i2][0], this.scanner.foundTaskPositions[i2][1]);
                }
                i2++;
            } else {
                this.javadoc = null;
                return this.compilationUnit;
            }
        }
    }

    public int flushCommentsDefinedPriorTo(int i) {
        int i2 = this.scanner.commentPtr;
        if (i2 < 0) {
            return i;
        }
        int i3 = 0;
        while (i2 >= 0) {
            int i4 = this.scanner.commentStops[i2];
            if (i4 < 0) {
                i4 = -i4;
            }
            if (i4 <= i) {
                break;
            }
            i2--;
            i3++;
        }
        if (i3 > 0) {
            int i5 = i2 + 1;
            int i6 = -this.scanner.commentStops[i5];
            if (i6 > 0) {
                int i7 = i6 - 1;
                if (Util.getLineNumber(i, this.scanner.lineEnds, 0, this.scanner.linePtr) == Util.getLineNumber(i7, this.scanner.lineEnds, 0, this.scanner.linePtr)) {
                    i3--;
                    i = i7;
                    i2 = i5;
                }
            }
        }
        if (i2 < 0) {
            return i;
        }
        if (i3 != 0) {
            if (i3 == 1) {
                int i8 = i2 + 1;
                this.scanner.commentStarts[0] = this.scanner.commentStarts[i8];
                this.scanner.commentStops[0] = this.scanner.commentStops[i8];
                this.scanner.commentTagStarts[0] = this.scanner.commentTagStarts[i8];
            } else if (i3 == 2) {
                int i9 = i2 + 1;
                this.scanner.commentStarts[0] = this.scanner.commentStarts[i9];
                this.scanner.commentStops[0] = this.scanner.commentStops[i9];
                this.scanner.commentTagStarts[0] = this.scanner.commentTagStarts[i9];
                int i10 = i2 + 2;
                this.scanner.commentStarts[1] = this.scanner.commentStarts[i10];
                this.scanner.commentStops[1] = this.scanner.commentStops[i10];
                this.scanner.commentTagStarts[1] = this.scanner.commentTagStarts[i10];
            } else {
                int i11 = i2 + 1;
                System.arraycopy(this.scanner.commentStarts, i11, this.scanner.commentStarts, 0, i3);
                System.arraycopy(this.scanner.commentStops, i11, this.scanner.commentStops, 0, i3);
                System.arraycopy(this.scanner.commentTagStarts, i11, this.scanner.commentTagStarts, 0, i3);
            }
        }
        this.scanner.commentPtr = i3 - 1;
        return i;
    }

    protected TypeReference getAnnotationType() {
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            char[][] cArr = this.identifierStack;
            int i3 = this.identifierPtr;
            char[] cArr2 = cArr[i3];
            long[] jArr = this.identifierPositionStack;
            this.identifierPtr = i3 - 1;
            return new SingleTypeReference(cArr2, jArr[i3]);
        }
        char[][] cArr3 = new char[i2][];
        int i4 = this.identifierPtr - i2;
        this.identifierPtr = i4;
        long[] jArr2 = new long[i2];
        System.arraycopy(this.identifierStack, i4 + 1, cArr3, 0, i2);
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr2, 0, i2);
        return new QualifiedTypeReference(cArr3, jArr2);
    }

    public int getFirstToken() {
        return this.firstToken;
    }

    public int[] getJavaDocPositions() {
        int i;
        int i2 = this.scanner.commentPtr;
        int i3 = 0;
        for (int i4 = 0; i4 <= i2; i4++) {
            if (this.scanner.commentStarts[i4] >= 0 && this.scanner.commentStops[i4] > 0) {
                i3++;
            }
        }
        if (i3 == 0) {
            return null;
        }
        int[] iArr = new int[i3 * 2];
        int i5 = 0;
        for (int i6 = 0; i6 <= i2; i6++) {
            int i7 = this.scanner.commentStarts[i6];
            if (i7 >= 0 && (i = this.scanner.commentStops[i6]) > 0) {
                int i8 = i5 + 1;
                iArr[i5] = i7;
                i5 += 2;
                iArr[i8] = i - 1;
            }
        }
        return iArr;
    }

    public void getMethodBodies(CompilationUnitDeclaration compilationUnitDeclaration) {
        char[] contents;
        if (compilationUnitDeclaration == null) {
            return;
        }
        if (compilationUnitDeclaration.ignoreMethodBodies) {
            compilationUnitDeclaration.ignoreFurtherInvestigation = true;
            return;
        }
        if ((compilationUnitDeclaration.bits & 16) != 0) {
            return;
        }
        int[] iArr = this.scanner.lineEnds;
        int i = this.scanner.linePtr;
        CompilationResult compilationResult = compilationUnitDeclaration.compilationResult;
        ReadManager readManager = this.readManager;
        if (readManager != null) {
            contents = readManager.getContents(compilationResult.compilationUnit);
        } else {
            contents = compilationResult.compilationUnit.getContents();
        }
        this.scanner.setSource(contents, compilationResult);
        JavadocParser javadocParser = this.javadocParser;
        if (javadocParser != null && javadocParser.checkDocComment) {
            this.javadocParser.scanner.setSource(contents);
        }
        if (compilationUnitDeclaration.types != null) {
            int length = compilationUnitDeclaration.types.length;
            for (int i2 = 0; i2 < length; i2++) {
                compilationUnitDeclaration.types[i2].parseMethods(this, compilationUnitDeclaration);
            }
        }
        compilationUnitDeclaration.bits |= 16;
        this.scanner.lineEnds = iArr;
        this.scanner.linePtr = i;
    }

    protected char getNextCharacter(char[] cArr, int[] iArr) {
        char c;
        int i;
        int i2 = iArr[0];
        iArr[0] = i2 + 1;
        char c2 = cArr[i2];
        if (c2 != '\\') {
            return c2;
        }
        iArr[0] = i2 + 2;
        while (true) {
            int i3 = iArr[0];
            c = cArr[i3];
            i = i3 + 1;
            if (c != 'u') {
                break;
            }
            iArr[0] = i;
        }
        iArr[0] = i;
        int hexadecimalValue = ScannerHelper.getHexadecimalValue(c);
        if (hexadecimalValue > 15 || hexadecimalValue < 0) {
            return c2;
        }
        int i4 = iArr[0];
        iArr[0] = i4 + 1;
        int hexadecimalValue2 = ScannerHelper.getHexadecimalValue(cArr[i4]);
        if (hexadecimalValue2 > 15 || hexadecimalValue2 < 0) {
            return c2;
        }
        int i5 = iArr[0];
        iArr[0] = i5 + 1;
        int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(cArr[i5]);
        if (hexadecimalValue3 > 15 || hexadecimalValue3 < 0) {
            return c2;
        }
        int i6 = iArr[0];
        iArr[0] = i6 + 1;
        int hexadecimalValue4 = ScannerHelper.getHexadecimalValue(cArr[i6]);
        return (hexadecimalValue4 > 15 || hexadecimalValue4 < 0) ? c2 : (char) ((((((hexadecimalValue * 16) + hexadecimalValue2) * 16) + hexadecimalValue3) * 16) + hexadecimalValue4);
    }

    protected Expression getTypeReference(Expression expression) {
        expression.bits &= -8;
        expression.bits |= 4;
        return expression;
    }

    protected void annotateTypeReference(Wildcard wildcard) {
        int[] iArr = this.typeAnnotationLengthStack;
        int i = this.typeAnnotationLengthPtr;
        this.typeAnnotationLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            if (wildcard.annotations == null) {
                wildcard.annotations = new Annotation[wildcard.getAnnotatableLevels()][];
            }
            Annotation[] annotationArr = this.typeAnnotationStack;
            int i3 = this.typeAnnotationPtr - i2;
            this.typeAnnotationPtr = i3;
            Annotation[] annotationArr2 = new Annotation[i2];
            wildcard.annotations[0] = annotationArr2;
            System.arraycopy(annotationArr, i3 + 1, annotationArr2, 0, i2);
            if (wildcard.sourceStart > wildcard.annotations[0][0].sourceStart) {
                wildcard.sourceStart = wildcard.annotations[0][0].sourceStart;
            }
            wildcard.bits |= 1048576;
        }
        if (wildcard.bound != null) {
            wildcard.bits |= 1048576 & wildcard.bound.bits;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00d4  */
    protected TypeReference getTypeReference(int i) {
        TypeReference typeReferenceForGenericType;
        int[] iArr = this.identifierLengthStack;
        int i2 = this.identifierLengthPtr;
        this.identifierLengthPtr = i2 - 1;
        int i3 = iArr[i2];
        if (i3 < 0) {
            typeReferenceForGenericType = TypeReference.baseTypeReference(-i3, i, i > 0 ? getAnnotationsOnDimensions(i) : null);
            int[] iArr2 = this.intStack;
            int i4 = this.intPtr;
            this.intPtr = i4 - 1;
            typeReferenceForGenericType.sourceStart = iArr2[i4];
            if (i == 0) {
                int[] iArr3 = this.intStack;
                int i5 = this.intPtr;
                this.intPtr = i5 - 1;
                typeReferenceForGenericType.sourceEnd = iArr3[i5];
            } else {
                this.intPtr--;
                typeReferenceForGenericType.sourceEnd = this.rBracketPosition;
            }
        } else {
            int[] iArr4 = this.genericsIdentifiersLengthStack;
            int i6 = this.genericsIdentifiersLengthPtr;
            this.genericsIdentifiersLengthPtr = i6 - 1;
            int i7 = iArr4[i6];
            if (i3 == i7) {
                int[] iArr5 = this.genericsLengthStack;
                int i8 = this.genericsLengthPtr;
                if (iArr5[i8] != 0) {
                    typeReferenceForGenericType = getTypeReferenceForGenericType(i, i3, i7);
                } else if (i3 == 1) {
                    this.genericsLengthPtr = i8 - 1;
                    if (i == 0) {
                        char[][] cArr = this.identifierStack;
                        int i9 = this.identifierPtr;
                        char[] cArr2 = cArr[i9];
                        long[] jArr = this.identifierPositionStack;
                        this.identifierPtr = i9 - 1;
                        typeReferenceForGenericType = new SingleTypeReference(cArr2, jArr[i9]);
                    } else {
                        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(i);
                        char[][] cArr3 = this.identifierStack;
                        int i10 = this.identifierPtr;
                        char[] cArr4 = cArr3[i10];
                        long[] jArr2 = this.identifierPositionStack;
                        this.identifierPtr = i10 - 1;
                        ArrayTypeReference arrayTypeReference = new ArrayTypeReference(cArr4, i, annotationsOnDimensions, jArr2[i10]);
                        arrayTypeReference.sourceEnd = this.endPosition;
                        if (annotationsOnDimensions != null) {
                            arrayTypeReference.bits |= 1048576;
                        }
                        typeReferenceForGenericType = arrayTypeReference;
                    }
                } else {
                    this.genericsLengthPtr = i8 - 1;
                    char[][] cArr5 = new char[i3][];
                    int i11 = this.identifierPtr - i3;
                    this.identifierPtr = i11;
                    long[] jArr3 = new long[i3];
                    System.arraycopy(this.identifierStack, i11 + 1, cArr5, 0, i3);
                    System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr3, 0, i3);
                    if (i == 0) {
                        typeReferenceForGenericType = new QualifiedTypeReference(cArr5, jArr3);
                    } else {
                        Annotation[][] annotationsOnDimensions2 = getAnnotationsOnDimensions(i);
                        ArrayQualifiedTypeReference arrayQualifiedTypeReference = new ArrayQualifiedTypeReference(cArr5, i, annotationsOnDimensions2, jArr3);
                        arrayQualifiedTypeReference.sourceEnd = this.endPosition;
                        if (annotationsOnDimensions2 != null) {
                            arrayQualifiedTypeReference.bits |= 1048576;
                        }
                        typeReferenceForGenericType = arrayQualifiedTypeReference;
                    }
                }
            } else {
                typeReferenceForGenericType = getTypeReferenceForGenericType(i, i3, i7);
            }
        }
        int annotatableLevels = typeReferenceForGenericType.getAnnotatableLevels();
        for (int i12 = annotatableLevels - 1; i12 >= 0; i12--) {
            int[] iArr6 = this.typeAnnotationLengthStack;
            int i13 = this.typeAnnotationLengthPtr;
            this.typeAnnotationLengthPtr = i13 - 1;
            int i14 = iArr6[i13];
            if (i14 != 0) {
                if (typeReferenceForGenericType.annotations == null) {
                    typeReferenceForGenericType.annotations = new Annotation[annotatableLevels][];
                }
                Annotation[] annotationArr = this.typeAnnotationStack;
                int i15 = this.typeAnnotationPtr - i14;
                this.typeAnnotationPtr = i15;
                Annotation[] annotationArr2 = new Annotation[i14];
                typeReferenceForGenericType.annotations[i12] = annotationArr2;
                System.arraycopy(annotationArr, i15 + 1, annotationArr2, 0, i14);
                if (i12 == 0) {
                    typeReferenceForGenericType.sourceStart = typeReferenceForGenericType.annotations[0][0].sourceStart;
                }
                typeReferenceForGenericType.bits |= 1048576;
            }
        }
        return typeReferenceForGenericType;
    }

    protected TypeReference getTypeReferenceForGenericType(int i, int i2, int i3) {
        TypeReference[] typeReferenceArr;
        Annotation[][] annotationsOnDimensions = i == 0 ? null : getAnnotationsOnDimensions(i);
        if (i2 == 1 && i3 == 1) {
            int[] iArr = this.genericsLengthStack;
            int i4 = this.genericsLengthPtr;
            this.genericsLengthPtr = i4 - 1;
            int i5 = iArr[i4];
            if (i5 < 0) {
                typeReferenceArr = TypeReference.NO_TYPE_ARGUMENTS;
            } else {
                TypeReference[] typeReferenceArr2 = new TypeReference[i5];
                int i6 = this.genericsPtr - i5;
                this.genericsPtr = i6;
                System.arraycopy(this.genericsStack, i6 + 1, typeReferenceArr2, 0, i5);
                typeReferenceArr = typeReferenceArr2;
            }
            char[][] cArr = this.identifierStack;
            int i7 = this.identifierPtr;
            char[] cArr2 = cArr[i7];
            long[] jArr = this.identifierPositionStack;
            this.identifierPtr = i7 - 1;
            ParameterizedSingleTypeReference parameterizedSingleTypeReference = new ParameterizedSingleTypeReference(cArr2, typeReferenceArr, i, annotationsOnDimensions, jArr[i7]);
            if (i != 0) {
                parameterizedSingleTypeReference.sourceEnd = this.endStatementPosition;
            }
            return parameterizedSingleTypeReference;
        }
        TypeReference[][] typeReferenceArr3 = new TypeReference[i3][];
        char[][] cArr3 = new char[i3][];
        long[] jArr2 = new long[i3];
        while (i3 > 0) {
            int[] iArr2 = this.genericsLengthStack;
            int i8 = this.genericsLengthPtr;
            this.genericsLengthPtr = i8 - 1;
            int i9 = iArr2[i8];
            if (i9 > 0) {
                int i10 = this.genericsPtr - i9;
                this.genericsPtr = i10;
                TypeReference[] typeReferenceArr4 = new TypeReference[i9];
                typeReferenceArr3[i3 - 1] = typeReferenceArr4;
                System.arraycopy(this.genericsStack, i10 + 1, typeReferenceArr4, 0, i9);
            } else if (i9 < 0) {
                typeReferenceArr3[i3 - 1] = TypeReference.NO_TYPE_ARGUMENTS;
            }
            if (i2 == 1) {
                int i11 = i3 - 1;
                char[][] cArr4 = this.identifierStack;
                int i12 = this.identifierPtr;
                cArr3[i11] = cArr4[i12];
                long[] jArr3 = this.identifierPositionStack;
                this.identifierPtr = i12 - 1;
                jArr2[i11] = jArr3[i12];
            } else {
                int i13 = this.identifierPtr - i2;
                this.identifierPtr = i13;
                int i14 = i3 - i2;
                System.arraycopy(this.identifierStack, i13 + 1, cArr3, i14, i2);
                System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr2, i14, i2);
            }
            i3 -= i2;
            if (i3 > 0) {
                int[] iArr3 = this.identifierLengthStack;
                int i15 = this.identifierLengthPtr;
                this.identifierLengthPtr = i15 - 1;
                i2 = iArr3[i15];
            }
        }
        ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = new ParameterizedQualifiedTypeReference(cArr3, typeReferenceArr3, i, annotationsOnDimensions, jArr2);
        if (i != 0) {
            parameterizedQualifiedTypeReference.sourceEnd = this.endStatementPosition;
        }
        return parameterizedQualifiedTypeReference;
    }

    protected NameReference getUnspecifiedReference() {
        return getUnspecifiedReference(true);
    }

    protected NameReference getUnspecifiedReference(boolean z) {
        if (z) {
            consumeNonTypeUseName();
        }
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            char[][] cArr = this.identifierStack;
            int i3 = this.identifierPtr;
            char[] cArr2 = cArr[i3];
            long[] jArr = this.identifierPositionStack;
            this.identifierPtr = i3 - 1;
            return new SingleNameReference(cArr2, jArr[i3]);
        }
        char[][] cArr3 = new char[i2][];
        int i4 = this.identifierPtr - i2;
        this.identifierPtr = i4;
        System.arraycopy(this.identifierStack, i4 + 1, cArr3, 0, i2);
        long[] jArr2 = new long[i2];
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr2, 0, i2);
        long[] jArr3 = this.identifierPositionStack;
        int i5 = this.identifierPtr;
        return new QualifiedNameReference(cArr3, jArr2, (int) (jArr3[i5 + 1] >> 32), (int) jArr3[i5 + i2]);
    }

    protected NameReference getUnspecifiedReferenceOptimized() {
        consumeNonTypeUseName();
        int[] iArr = this.identifierLengthStack;
        int i = this.identifierLengthPtr;
        this.identifierLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 == 1) {
            char[][] cArr = this.identifierStack;
            int i3 = this.identifierPtr;
            char[] cArr2 = cArr[i3];
            long[] jArr = this.identifierPositionStack;
            this.identifierPtr = i3 - 1;
            SingleNameReference singleNameReference = new SingleNameReference(cArr2, jArr[i3]);
            singleNameReference.bits &= -8;
            singleNameReference.bits |= 3;
            return singleNameReference;
        }
        char[][] cArr3 = new char[i2][];
        int i4 = this.identifierPtr - i2;
        this.identifierPtr = i4;
        System.arraycopy(this.identifierStack, i4 + 1, cArr3, 0, i2);
        long[] jArr2 = new long[i2];
        System.arraycopy(this.identifierPositionStack, this.identifierPtr + 1, jArr2, 0, i2);
        long[] jArr3 = this.identifierPositionStack;
        int i5 = this.identifierPtr;
        QualifiedNameReference qualifiedNameReference = new QualifiedNameReference(cArr3, jArr2, (int) (jArr3[i5 + 1] >> 32), (int) jArr3[i5 + i2]);
        qualifiedNameReference.bits &= -8;
        qualifiedNameReference.bits |= 3;
        return qualifiedNameReference;
    }

    public void goForBlockStatementsopt() {
        this.firstToken = 63;
        this.scanner.recordLineSeparator = false;
    }

    public void goForBlockStatementsOrCatchHeader() {
        this.firstToken = 6;
        this.scanner.recordLineSeparator = false;
    }

    public void goForClassBodyDeclarations() {
        this.firstToken = 21;
        this.scanner.recordLineSeparator = true;
    }

    public void goForCompilationUnit() {
        this.firstToken = 1;
        this.scanner.foundTaskCount = 0;
        this.scanner.recordLineSeparator = true;
    }

    public void goForExpression(boolean z) {
        this.firstToken = 8;
        this.scanner.recordLineSeparator = z;
    }

    public void goForFieldDeclaration() {
        this.firstToken = 30;
        this.scanner.recordLineSeparator = true;
    }

    public void goForGenericMethodDeclaration() {
        this.firstToken = 9;
        this.scanner.recordLineSeparator = true;
    }

    public void goForHeaders() {
        RecoveredType recoveredTypeCurrentRecoveryType = currentRecoveryType();
        if (recoveredTypeCurrentRecoveryType != null && recoveredTypeCurrentRecoveryType.insideEnumConstantPart) {
            this.firstToken = 62;
        } else {
            this.firstToken = 16;
        }
        this.scanner.recordLineSeparator = true;
    }

    public void goForImportDeclaration() {
        this.firstToken = 31;
        this.scanner.recordLineSeparator = true;
    }

    public void goForInitializer() {
        this.firstToken = 14;
        this.scanner.recordLineSeparator = false;
    }

    public void goForMemberValue() {
        this.firstToken = 31;
        this.scanner.recordLineSeparator = true;
    }

    public void goForMethodBody() {
        this.firstToken = 2;
        this.scanner.recordLineSeparator = false;
    }

    public void goForPackageDeclaration() {
        this.firstToken = 29;
        this.scanner.recordLineSeparator = true;
    }

    public void goForTypeDeclaration() {
        this.firstToken = 4;
        this.scanner.recordLineSeparator = true;
    }

    public boolean hasLeadingTagComment(char[] cArr, int i) {
        int i2;
        int i3 = this.scanner.commentPtr;
        if (i3 < 0 || (i2 = this.astLengthPtr) < 0 || this.astLengthStack[i2] <= 1) {
            return false;
        }
        int i4 = this.astStack[this.astPtr].sourceEnd;
        while (i3 >= 0) {
            int i5 = this.scanner.commentStarts[i3];
            if (i5 < 0) {
                i5 = -i5;
            }
            if (i5 < i4) {
                return false;
            }
            if (i5 <= i) {
                char[] cArr2 = this.scanner.source;
                int i6 = i5 + 2;
                while (i6 < i) {
                    char c = cArr2[i6];
                    if (c >= 128 || (ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 256) == 0) {
                        break;
                    }
                    i6++;
                }
                int length = cArr.length;
                int i7 = 0;
                while (i7 < length) {
                    if (i6 < i && cArr2[i6] == cArr[i7]) {
                        i7++;
                        i6++;
                    } else if (i7 == 0) {
                        return false;
                    }
                }
                return true;
            }
            i3--;
        }
        return false;
    }

    protected void ignoreNextClosingBrace() {
        this.ignoreNextClosingBrace = true;
    }

    protected void ignoreExpressionAssignment() {
        this.intPtr--;
        Expression[] expressionArr = this.expressionStack;
        int i = this.expressionPtr;
        this.expressionPtr = i - 1;
        ArrayInitializer arrayInitializer = (ArrayInitializer) expressionArr[i];
        this.expressionLengthPtr--;
        if (this.statementRecoveryActivated) {
            return;
        }
        problemReporter().arrayConstantsOnlyInArrayInitializers(arrayInitializer.sourceStart, arrayInitializer.sourceEnd);
    }

    public void initialize() {
        initialize(false);
    }

    public void initialize(boolean z) {
        this.javadoc = null;
        this.astPtr = -1;
        this.astLengthPtr = -1;
        this.expressionPtr = -1;
        this.expressionLengthPtr = -1;
        this.typeAnnotationLengthPtr = -1;
        this.typeAnnotationPtr = -1;
        this.identifierPtr = -1;
        this.identifierLengthPtr = -1;
        this.intPtr = -1;
        int[] iArr = this.nestedMethod;
        this.nestedType = 0;
        iArr[0] = 0;
        this.variablesCounter[0] = 0;
        this.dimensions = 0;
        this.realBlockPtr = -1;
        this.compilationUnit = null;
        this.referenceContext = null;
        this.endStatementPosition = 0;
        this.valueLambdaNestDepth = -1;
        ASTNode[] aSTNodeArr = this.astStack;
        int length = aSTNodeArr.length;
        if (this.noAstNodes.length < length) {
            this.noAstNodes = new ASTNode[length];
        }
        System.arraycopy(this.noAstNodes, 0, aSTNodeArr, 0, length);
        Expression[] expressionArr = this.expressionStack;
        int length2 = expressionArr.length;
        if (this.noExpressions.length < length2) {
            this.noExpressions = new Expression[length2];
        }
        System.arraycopy(this.noExpressions, 0, expressionArr, 0, length2);
        this.scanner.commentPtr = -1;
        this.scanner.foundTaskCount = 0;
        this.scanner.eofPosition = Integer.MAX_VALUE;
        this.recordStringLiterals = true;
        boolean z2 = this.options.getSeverity(256) != 256;
        this.checkExternalizeStrings = z2;
        this.scanner.checkNonExternalizedStringLiterals = z && z2;
        this.scanner.checkUninternedIdentityComparison = z && this.options.complainOnUninternedIdentityComparison;
        this.scanner.lastPosition = -1;
        resetModifiers();
        this.lastCheckPoint = -1;
        this.currentElement = null;
        this.restartRecovery = false;
        this.hasReportedError = false;
        this.recoveredStaticInitializerStart = 0;
        this.lastIgnoredToken = -1;
        this.lastErrorEndPosition = -1;
        this.lastErrorEndPositionBeforeRecovery = -1;
        this.lastJavadocEnd = -1;
        this.listLength = 0;
        this.listTypeParameterLength = 0;
        this.lastPosistion = -1;
        this.rBraceStart = 0;
        this.rBraceEnd = 0;
        this.rBraceSuccessorStart = 0;
        this.rBracketPosition = 0;
        this.genericsIdentifiersLengthPtr = -1;
        this.genericsLengthPtr = -1;
        this.genericsPtr = -1;
    }

    public void initializeScanner() {
        this.scanner = new Scanner(false, false, false, this.options.sourceLevel, this.options.complianceLevel, this.options.taskTags, this.options.taskPriorities, this.options.isTaskCaseSensitive);
    }

    public void jumpOverMethodBody() {
        if (this.diet && this.dietInt == 0) {
            this.scanner.diet = true;
        }
    }

    private void jumpOverType() {
        int i;
        int i2;
        if (this.recoveredTypes == null || (i = this.nextTypeStart) <= -1 || i >= this.scanner.currentPosition) {
            return;
        }
        TypeDeclaration typeDeclaration = this.recoveredTypes[this.recoveredTypePtr];
        boolean z = typeDeclaration.allocation != null;
        this.scanner.startPosition = typeDeclaration.declarationSourceEnd + 1;
        this.scanner.currentPosition = typeDeclaration.declarationSourceEnd + 1;
        this.scanner.diet = false;
        if (!z) {
            ((RecoveryScanner) this.scanner).setPendingTokens(new int[]{28, 73});
        } else {
            ((RecoveryScanner) this.scanner).setPendingTokens(new int[]{22, 70, 22});
        }
        this.pendingRecoveredType = typeDeclaration;
        try {
            this.currentToken = this.scanner.getNextToken();
        } catch (InvalidInputException unused) {
        }
        int i3 = this.recoveredTypePtr + 1;
        this.recoveredTypePtr = i3;
        TypeDeclaration[] typeDeclarationArr = this.recoveredTypes;
        if (i3 < typeDeclarationArr.length) {
            TypeDeclaration typeDeclaration2 = typeDeclarationArr[i3];
            if (typeDeclaration2.allocation == null) {
                i2 = typeDeclaration2.declarationSourceStart;
            } else {
                i2 = typeDeclaration2.allocation.sourceStart;
            }
            this.nextTypeStart = i2;
            return;
        }
        this.nextTypeStart = Integer.MAX_VALUE;
    }

    protected void markEnclosingMemberWithLocalType() {
        if (this.currentElement != null) {
            return;
        }
        markEnclosingMemberWithLocalOrFunctionalType(LocalTypeKind.LOCAL);
    }

    protected void markEnclosingMemberWithLocalOrFunctionalType(LocalTypeKind localTypeKind) {
        for (int i = this.astPtr; i >= 0; i--) {
            ASTNode aSTNode = this.astStack[i];
            if ((aSTNode instanceof AbstractMethodDeclaration) || (aSTNode instanceof FieldDeclaration) || ((aSTNode instanceof TypeDeclaration) && ((TypeDeclaration) aSTNode).declarationSourceEnd == 0)) {
                int i2 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind()[localTypeKind.ordinal()];
                if (i2 != 1) {
                    if (i2 == 2) {
                        aSTNode.bits |= 2097152;
                        return;
                    } else if (i2 != 3) {
                        return;
                    } else {
                        aSTNode.bits |= 2097152;
                    }
                }
                aSTNode.bits |= 2;
                return;
            }
        }
        Object obj = this.referenceContext;
        if ((obj instanceof AbstractMethodDeclaration) || (obj instanceof TypeDeclaration)) {
            ASTNode aSTNode2 = (ASTNode) obj;
            int i3 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$parser$Parser$LocalTypeKind()[localTypeKind.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    aSTNode2.bits |= 2097152;
                    return;
                } else if (i3 != 3) {
                    return;
                } else {
                    aSTNode2.bits |= 2097152;
                }
            }
            aSTNode2.bits |= 2;
        }
    }

    protected boolean moveRecoveryCheckpoint() {
        int i;
        int i2 = this.lastCheckPoint;
        this.scanner.startPosition = i2;
        this.scanner.currentPosition = i2;
        this.scanner.diet = false;
        if (this.restartRecovery) {
            this.lastIgnoredToken = -1;
            this.scanner.insideRecovery = true;
            return true;
        }
        this.lastIgnoredToken = this.nextIgnoredToken;
        this.nextIgnoredToken = -1;
        do {
            try {
                try {
                    int[] iArr = this.scanner.lookBack;
                    this.scanner.lookBack[1] = 0;
                    iArr[0] = 0;
                    this.nextIgnoredToken = this.scanner.getNextToken();
                    if (this.scanner.currentPosition == this.scanner.startPosition) {
                        this.scanner.currentPosition++;
                        this.nextIgnoredToken = -1;
                    }
                    int[] iArr2 = this.scanner.lookBack;
                    this.scanner.lookBack[1] = 0;
                    iArr2[0] = 0;
                } catch (InvalidInputException unused) {
                    i2 = this.scanner.currentPosition;
                    int[] iArr3 = this.scanner.lookBack;
                    this.scanner.lookBack[1] = 0;
                    iArr3[0] = 0;
                }
                i = this.nextIgnoredToken;
            } catch (Throwable th) {
                int[] iArr4 = this.scanner.lookBack;
                this.scanner.lookBack[1] = 0;
                iArr4[0] = 0;
                throw th;
            }
        } while (i < 0);
        if (i == 60 && this.currentToken == 60) {
            return false;
        }
        this.lastCheckPoint = this.scanner.currentPosition;
        this.scanner.startPosition = i2;
        this.scanner.currentPosition = i2;
        this.scanner.commentPtr = -1;
        this.scanner.foundTaskCount = 0;
        return true;
    }

    protected MessageSend newMessageSend() {
        MessageSend messageSend = new MessageSend();
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            int i3 = this.expressionPtr - i2;
            this.expressionPtr = i3;
            Expression[] expressionArr = new Expression[i2];
            messageSend.arguments = expressionArr;
            System.arraycopy(this.expressionStack, i3 + 1, expressionArr, 0, i2);
        }
        return messageSend;
    }

    protected MessageSend newMessageSendWithTypeArguments() {
        MessageSend messageSend = new MessageSend();
        int[] iArr = this.expressionLengthStack;
        int i = this.expressionLengthPtr;
        this.expressionLengthPtr = i - 1;
        int i2 = iArr[i];
        if (i2 != 0) {
            int i3 = this.expressionPtr - i2;
            this.expressionPtr = i3;
            Expression[] expressionArr = new Expression[i2];
            messageSend.arguments = expressionArr;
            System.arraycopy(this.expressionStack, i3 + 1, expressionArr, 0, i2);
        }
        return messageSend;
    }

    protected void optimizedConcatNodeLists() {
        int[] iArr = this.astLengthStack;
        int i = this.astLengthPtr - 1;
        this.astLengthPtr = i;
        iArr[i] = iArr[i] + 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.ConflictedParser
    public boolean atConflictScenario(int i) {
        int i2 = this.unstackedAct;
        if (i2 == 16382) {
            return false;
        }
        if (i != 37) {
            i = i == 24 ? 50 : 83;
        }
        return automatonWillShift(i, i2);
    }

    protected void parse() {
        int iTAction;
        int i;
        int i2;
        boolean z = this.diet;
        int firstToken = getFirstToken();
        this.hasError = false;
        this.hasReportedError = false;
        this.unstackedAct = 16382;
        this.stateStackTop = -1;
        this.currentToken = getFirstToken();
        try {
            this.scanner.setActiveParser(this);
            loop0: while (true) {
                iTAction = 1580;
                while (true) {
                    int[] iArr = this.stack;
                    int length = iArr.length;
                    int i3 = this.stateStackTop + 1;
                    this.stateStackTop = i3;
                    if (i3 >= length) {
                        int[] iArr2 = new int[length + 255];
                        this.stack = iArr2;
                        System.arraycopy(iArr, 0, iArr2, 0, length);
                    }
                    this.stack[this.stateStackTop] = iTAction;
                    iTAction = tAction(iTAction, this.currentToken);
                    this.unstackedAct = iTAction;
                    if (iTAction == 16382 || this.restartRecovery) {
                        i = this.scanner.currentPosition - 1;
                        if (!this.hasReportedError) {
                            this.hasError = true;
                        }
                        i2 = this.currentToken;
                        int iResumeOnSyntaxError = resumeOnSyntaxError();
                        if (iResumeOnSyntaxError == 0) {
                            iTAction = 16382;
                            break loop0;
                        } else if (iResumeOnSyntaxError != 1) {
                            if (iResumeOnSyntaxError == 2 && iTAction == 16382) {
                                int[] iArr3 = this.stack;
                                int i4 = this.stateStackTop;
                                this.stateStackTop = i4 - 1;
                                iTAction = iArr3[i4];
                            }
                        }
                    }
                    if (iTAction <= 800) {
                        this.stateStackTop--;
                    } else {
                        if (iTAction <= 16382) {
                            if (iTAction >= 16381) {
                                break loop0;
                            }
                            consumeToken(this.currentToken);
                            if (this.currentElement != null) {
                                boolean z2 = this.recordStringLiterals;
                                this.recordStringLiterals = false;
                                recoveryTokenCheck();
                                this.recordStringLiterals = z2;
                            }
                            try {
                                this.currentToken = this.scanner.getNextToken();
                            } catch (InvalidInputException e) {
                                if (!this.hasReportedError) {
                                    problemReporter().scannerError(this, e.getMessage());
                                    this.hasReportedError = true;
                                }
                                this.lastCheckPoint = this.scanner.currentPosition;
                                this.currentToken = 0;
                                this.restartRecovery = true;
                            }
                            if (this.statementRecoveryActivated) {
                                jumpOverType();
                            }
                        } else {
                            consumeToken(this.currentToken);
                            if (this.currentElement != null) {
                                boolean z3 = this.recordStringLiterals;
                                this.recordStringLiterals = false;
                                recoveryTokenCheck();
                                this.recordStringLiterals = z3;
                            }
                            try {
                                this.currentToken = this.scanner.getNextToken();
                            } catch (InvalidInputException e2) {
                                if (!this.hasReportedError) {
                                    problemReporter().scannerError(this, e2.getMessage());
                                    this.hasReportedError = true;
                                }
                                this.lastCheckPoint = this.scanner.currentPosition;
                                this.currentToken = 0;
                                this.restartRecovery = true;
                            }
                            if (this.statementRecoveryActivated) {
                                jumpOverType();
                            }
                            iTAction -= 16382;
                            this.unstackedAct = iTAction;
                        }
                        this.unstackedAct = 16382;
                        this.scanner.setActiveParser(null);
                        throw th;
                    }
                    do {
                        int i5 = this.stateStackTop - (rhs[iTAction] - 1);
                        this.stateStackTop = i5;
                        this.unstackedAct = ntAction(this.stack[i5], lhs[iTAction]);
                        consumeRule(iTAction);
                        iTAction = this.unstackedAct;
                    } while (iTAction <= 800);
                }
                if (iTAction == 16382 && i2 != 0) {
                    this.lastErrorEndPosition = i;
                }
                this.stateStackTop = -1;
                this.currentToken = getFirstToken();
            }
            this.unstackedAct = 16382;
            this.scanner.setActiveParser(null);
            endParse(iTAction);
            NLSTag[] nLSTags = this.scanner.getNLSTags();
            if (nLSTags != null) {
                this.compilationUnit.nlsTags = nLSTags;
            }
            this.scanner.checkNonExternalizedStringLiterals = false;
            if (this.scanner.checkUninternedIdentityComparison) {
                this.compilationUnit.validIdentityComparisonLines = this.scanner.getIdentityComparisonLines();
                this.scanner.checkUninternedIdentityComparison = false;
            }
            if (this.reportSyntaxErrorIsRequired && this.hasError && !this.statementRecoveryActivated) {
                if (!this.options.performStatementsRecovery) {
                    reportSyntaxErrors(z, firstToken);
                } else {
                    RecoveryScannerData recoveryScannerData = this.referenceContext.compilationResult().recoveryScannerData;
                    RecoveryScanner recoveryScanner = this.recoveryScanner;
                    if (recoveryScanner == null) {
                        this.recoveryScanner = new RecoveryScanner(this.scanner, recoveryScannerData);
                    } else {
                        recoveryScanner.setData(recoveryScannerData);
                    }
                    this.recoveryScanner.setSource(this.scanner.source);
                    this.recoveryScanner.lineEnds = this.scanner.lineEnds;
                    this.recoveryScanner.linePtr = this.scanner.linePtr;
                    reportSyntaxErrors(z, firstToken);
                    if (recoveryScannerData == null) {
                        this.referenceContext.compilationResult().recoveryScannerData = this.recoveryScanner.getData();
                    }
                    if (this.methodRecoveryActivated && this.options.performStatementsRecovery) {
                        this.methodRecoveryActivated = false;
                        recoverStatements();
                        this.methodRecoveryActivated = true;
                        this.lastAct = 16382;
                    }
                }
            }
            this.problemReporter.referenceContext = null;
        } catch (Throwable th) {
            this.unstackedAct = 16382;
            this.scanner.setActiveParser(null);
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:38:0x00e0  */
    public void parse(ConstructorDeclaration constructorDeclaration, CompilationUnitDeclaration compilationUnitDeclaration, boolean z) {
        boolean z2 = this.methodRecoveryActivated;
        if (this.options.performMethodsFullRecovery) {
            this.methodRecoveryActivated = true;
            this.ignoreNextOpeningBrace = true;
        }
        initialize();
        goForBlockStatementsopt();
        if (z) {
            this.scanner.recordLineSeparator = true;
        }
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        pushOnRealBlockStack(0);
        this.referenceContext = constructorDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.resetTo(constructorDeclaration.bodyStart, constructorDeclaration.bodyEnd);
        try {
            try {
                parse();
                int[] iArr2 = this.nestedMethod;
                int i2 = this.nestedType;
                iArr2[i2] = iArr2[i2] - 1;
                if (this.options.performStatementsRecovery) {
                    this.methodRecoveryActivated = z2;
                }
            } catch (AbortCompilation unused) {
                this.lastAct = 16382;
                int[] iArr3 = this.nestedMethod;
                int i3 = this.nestedType;
                iArr3[i3] = iArr3[i3] - 1;
                if (this.options.performStatementsRecovery) {
                }
            }
            checkNonNLSAfterBodyEnd(constructorDeclaration.declarationSourceEnd);
            if (this.lastAct == 16382) {
                constructorDeclaration.bits |= 524288;
                initialize();
                return;
            }
            int[] iArr4 = this.realBlockStack;
            int i4 = this.realBlockPtr;
            this.realBlockPtr = i4 - 1;
            constructorDeclaration.explicitDeclarations = iArr4[i4];
            int i5 = this.astLengthPtr;
            if (i5 > -1) {
                int[] iArr5 = this.astLengthStack;
                this.astLengthPtr = i5 - 1;
                int i6 = iArr5[i5];
                if (i6 != 0) {
                    this.astPtr -= i6;
                    if (!this.options.ignoreMethodBodies) {
                        ASTNode[] aSTNodeArr = this.astStack;
                        int i7 = this.astPtr;
                        if (aSTNodeArr[i7 + 1] instanceof ExplicitConstructorCall) {
                            int i8 = i6 - 1;
                            Statement[] statementArr = new Statement[i8];
                            constructorDeclaration.statements = statementArr;
                            System.arraycopy(aSTNodeArr, i7 + 2, statementArr, 0, i8);
                            constructorDeclaration.constructorCall = (ExplicitConstructorCall) this.astStack[this.astPtr + 1];
                        } else {
                            Statement[] statementArr2 = new Statement[i6];
                            constructorDeclaration.statements = statementArr2;
                            System.arraycopy(aSTNodeArr, i7 + 1, statementArr2, 0, i6);
                            constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
                        }
                    }
                } else {
                    if (!this.options.ignoreMethodBodies) {
                        constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
                    }
                    if (!containsComment(constructorDeclaration.bodyStart, constructorDeclaration.bodyEnd)) {
                        constructorDeclaration.bits |= 8;
                    }
                }
            } else {
                if (!this.options.ignoreMethodBodies) {
                    constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
                }
                if (!containsComment(constructorDeclaration.bodyStart, constructorDeclaration.bodyEnd)) {
                    constructorDeclaration.bits |= 8;
                }
            }
            ExplicitConstructorCall explicitConstructorCall = constructorDeclaration.constructorCall;
            if (explicitConstructorCall == null || explicitConstructorCall.sourceEnd != 0) {
                return;
            }
            explicitConstructorCall.sourceEnd = constructorDeclaration.sourceEnd;
            explicitConstructorCall.sourceStart = constructorDeclaration.sourceStart;
        } catch (Throwable th) {
            int[] iArr6 = this.nestedMethod;
            int i9 = this.nestedType;
            iArr6[i9] = iArr6[i9] - 1;
            if (this.options.performStatementsRecovery) {
                this.methodRecoveryActivated = z2;
            }
            throw th;
        }
    }

    public void parse(FieldDeclaration fieldDeclaration, TypeDeclaration typeDeclaration, CompilationUnitDeclaration compilationUnitDeclaration, char[] cArr) {
        initialize();
        goForExpression(true);
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        this.referenceContext = typeDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.setSource(cArr);
        this.scanner.resetTo(0, cArr.length - 1);
        try {
            parse();
        } catch (AbortCompilation unused) {
            this.lastAct = 16382;
        } finally {
            int[] iArr2 = this.nestedMethod;
            int i2 = this.nestedType;
            iArr2[i2] = iArr2[i2] - 1;
        }
        if (this.lastAct == 16382) {
            fieldDeclaration.bits |= 524288;
            return;
        }
        fieldDeclaration.initialization = this.expressionStack[this.expressionPtr];
        if ((typeDeclaration.bits & 2) != 0) {
            fieldDeclaration.bits |= 2;
        }
    }

    public CompilationUnitDeclaration parse(ICompilationUnit iCompilationUnit, CompilationResult compilationResult) {
        return parse(iCompilationUnit, compilationResult, -1, -1);
    }

    public CompilationUnitDeclaration parse(ICompilationUnit iCompilationUnit, CompilationResult compilationResult, int i, int i2) {
        char[] contents;
        try {
            initialize(true);
            goForCompilationUnit();
            CompilationUnitDeclaration compilationUnitDeclaration = new CompilationUnitDeclaration(this.problemReporter, compilationResult, 0);
            this.compilationUnit = compilationUnitDeclaration;
            this.referenceContext = compilationUnitDeclaration;
            try {
                ReadManager readManager = this.readManager;
                contents = readManager != null ? readManager.getContents(iCompilationUnit) : iCompilationUnit.getContents();
            } catch (AbortCompilationUnit e) {
                problemReporter().cannotReadSource(this.compilationUnit, e, this.options.verbose);
                contents = CharOperation.NO_CHAR;
            }
            this.scanner.setSource(contents);
            this.compilationUnit.sourceEnd = this.scanner.source.length - 1;
            if (i2 != -1) {
                this.scanner.resetTo(i, i2);
            }
            JavadocParser javadocParser = this.javadocParser;
            if (javadocParser != null && javadocParser.checkDocComment) {
                this.javadocParser.scanner.setSource(contents);
                if (i2 != -1) {
                    this.javadocParser.scanner.resetTo(i, i2);
                }
            }
            parse();
            CompilationUnitDeclaration compilationUnitDeclaration2 = this.compilationUnit;
            this.compilationUnit = null;
            if (!this.diet) {
                compilationUnitDeclaration2.bits |= 16;
            }
            return compilationUnitDeclaration2;
        } catch (Throwable th) {
            CompilationUnitDeclaration compilationUnitDeclaration3 = this.compilationUnit;
            this.compilationUnit = null;
            if (!this.diet) {
                compilationUnitDeclaration3.bits |= 16;
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0097  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a5  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    public void parse(Initializer initializer, TypeDeclaration typeDeclaration, CompilationUnitDeclaration compilationUnitDeclaration) {
        boolean z = this.methodRecoveryActivated;
        if (this.options.performMethodsFullRecovery) {
            this.methodRecoveryActivated = true;
        }
        initialize();
        goForBlockStatementsopt();
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        pushOnRealBlockStack(0);
        this.referenceContext = typeDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.resetTo(initializer.bodyStart, initializer.bodyEnd);
        try {
            try {
                parse();
                int[] iArr2 = this.nestedMethod;
                int i2 = this.nestedType;
                iArr2[i2] = iArr2[i2] - 1;
                if (this.options.performStatementsRecovery) {
                    this.methodRecoveryActivated = z;
                }
            } catch (AbortCompilation unused) {
                this.lastAct = 16382;
                int[] iArr3 = this.nestedMethod;
                int i3 = this.nestedType;
                iArr3[i3] = iArr3[i3] - 1;
                if (this.options.performStatementsRecovery) {
                }
            }
            checkNonNLSAfterBodyEnd(initializer.declarationSourceEnd);
            z = this.lastAct;
            if (z == 16382) {
                initializer.bits |= 524288;
                return;
            }
            Block block = initializer.block;
            int[] iArr4 = this.realBlockStack;
            int i4 = this.realBlockPtr;
            this.realBlockPtr = i4 - 1;
            block.explicitDeclarations = iArr4[i4];
            int i5 = this.astLengthPtr;
            if (i5 > -1) {
                int[] iArr5 = this.astLengthStack;
                this.astLengthPtr = i5 - 1;
                int i6 = iArr5[i5];
                if (i6 > 0) {
                    ASTNode[] aSTNodeArr = this.astStack;
                    int i7 = this.astPtr - i6;
                    this.astPtr = i7;
                    Statement[] statementArr = new Statement[i6];
                    initializer.block.statements = statementArr;
                    System.arraycopy(aSTNodeArr, i7 + 1, statementArr, 0, i6);
                } else if (!containsComment(initializer.block.sourceStart, initializer.block.sourceEnd)) {
                    initializer.block.bits |= 8;
                }
            } else if (!containsComment(initializer.block.sourceStart, initializer.block.sourceEnd)) {
                initializer.block.bits |= 8;
            }
            if ((typeDeclaration.bits & 2) != 0) {
                initializer.bits |= 2;
            }
        } catch (Throwable th) {
            int[] iArr6 = this.nestedMethod;
            int i8 = this.nestedType;
            iArr6[i8] = iArr6[i8] - 1;
            if (this.options.performStatementsRecovery) {
                this.methodRecoveryActivated = z;
            }
            throw th;
        }
    }

    public void parse(MethodDeclaration methodDeclaration, CompilationUnitDeclaration compilationUnitDeclaration) {
        if (methodDeclaration.isAbstract() || methodDeclaration.isNative() || (methodDeclaration.modifiers & 16777216) != 0) {
            return;
        }
        boolean z = this.methodRecoveryActivated;
        if (this.options.performMethodsFullRecovery) {
            this.ignoreNextOpeningBrace = true;
            this.methodRecoveryActivated = true;
            this.rParenPos = methodDeclaration.sourceEnd;
        }
        initialize();
        goForBlockStatementsopt();
        int[] iArr = this.nestedMethod;
        int i = this.nestedType;
        iArr[i] = iArr[i] + 1;
        pushOnRealBlockStack(0);
        this.referenceContext = methodDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.resetTo(methodDeclaration.bodyStart, methodDeclaration.bodyEnd);
        try {
            try {
                parse();
                int[] iArr2 = this.nestedMethod;
                int i2 = this.nestedType;
                iArr2[i2] = iArr2[i2] - 1;
                if (this.options.performStatementsRecovery) {
                    this.methodRecoveryActivated = z;
                }
            } catch (AbortCompilation unused) {
                this.lastAct = 16382;
                int[] iArr3 = this.nestedMethod;
                int i3 = this.nestedType;
                iArr3[i3] = iArr3[i3] - 1;
                if (this.options.performStatementsRecovery) {
                }
            }
            checkNonNLSAfterBodyEnd(methodDeclaration.declarationSourceEnd);
            if (this.lastAct == 16382) {
                methodDeclaration.bits |= 524288;
                return;
            }
            int[] iArr4 = this.realBlockStack;
            int i4 = this.realBlockPtr;
            this.realBlockPtr = i4 - 1;
            methodDeclaration.explicitDeclarations = iArr4[i4];
            int i5 = this.astLengthPtr;
            if (i5 > -1) {
                int[] iArr5 = this.astLengthStack;
                this.astLengthPtr = i5 - 1;
                int i6 = iArr5[i5];
                if (i6 != 0) {
                    if (this.options.ignoreMethodBodies) {
                        this.astPtr -= i6;
                        return;
                    }
                    ASTNode[] aSTNodeArr = this.astStack;
                    int i7 = this.astPtr - i6;
                    this.astPtr = i7;
                    Statement[] statementArr = new Statement[i6];
                    methodDeclaration.statements = statementArr;
                    System.arraycopy(aSTNodeArr, i7 + 1, statementArr, 0, i6);
                    return;
                }
            }
            if (containsComment(methodDeclaration.bodyStart, methodDeclaration.bodyEnd)) {
                return;
            }
            methodDeclaration.bits |= 8;
        } catch (Throwable th) {
            int[] iArr6 = this.nestedMethod;
            int i8 = this.nestedType;
            iArr6[i8] = iArr6[i8] - 1;
            if (this.options.performStatementsRecovery) {
                this.methodRecoveryActivated = z;
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00ba  */
    public ASTNode[] parseClassBodyDeclarations(char[] cArr, int i, int i2, CompilationUnitDeclaration compilationUnitDeclaration) {
        ASTNode[] aSTNodeArr;
        boolean z = this.diet;
        int i3 = this.dietInt;
        boolean z2 = this.tolerateDefaultClassMethods;
        initialize();
        goForClassBodyDeclarations();
        this.scanner.setSource(cArr);
        int i4 = (i2 + i) - 1;
        this.scanner.resetTo(i, i4);
        JavadocParser javadocParser = this.javadocParser;
        if (javadocParser != null && javadocParser.checkDocComment) {
            this.javadocParser.scanner.setSource(cArr);
            this.javadocParser.scanner.resetTo(i, i4);
        }
        this.nestedType = 1;
        TypeDeclaration typeDeclaration = new TypeDeclaration(compilationUnitDeclaration.compilationResult);
        typeDeclaration.f619name = Util.EMPTY_STRING.toCharArray();
        typeDeclaration.fields = new FieldDeclaration[0];
        this.compilationUnit = compilationUnitDeclaration;
        compilationUnitDeclaration.types = new TypeDeclaration[1];
        compilationUnitDeclaration.types[0] = typeDeclaration;
        this.referenceContext = compilationUnitDeclaration;
        try {
            try {
                this.diet = true;
                this.dietInt = 0;
                this.tolerateDefaultClassMethods = this.parsingJava8Plus;
                parse();
            } catch (AbortCompilation unused) {
                this.lastAct = 16382;
            }
            this.diet = z;
            this.dietInt = i3;
            this.tolerateDefaultClassMethods = z2;
            if (this.lastAct == 16382) {
                if (!this.options.performMethodsFullRecovery && !this.options.performStatementsRecovery) {
                    return null;
                }
                final ArrayList arrayList = new ArrayList();
                ASTVisitor aSTVisitor = new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.parser.Parser.1
                    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                    public boolean visit(MethodDeclaration methodDeclaration, ClassScope classScope) {
                        if (methodDeclaration.isDefaultConstructor()) {
                            return false;
                        }
                        arrayList.add(methodDeclaration);
                        return false;
                    }

                    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                    public boolean visit(FieldDeclaration fieldDeclaration, MethodScope methodScope) {
                        arrayList.add(fieldDeclaration);
                        return false;
                    }

                    @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                    public boolean visit(TypeDeclaration typeDeclaration2, ClassScope classScope) {
                        arrayList.add(typeDeclaration2);
                        return false;
                    }
                };
                compilationUnitDeclaration.ignoreFurtherInvestigation = false;
                compilationUnitDeclaration.traverse(aSTVisitor, compilationUnitDeclaration.scope);
                compilationUnitDeclaration.ignoreFurtherInvestigation = true;
                aSTNodeArr = (ASTNode[]) arrayList.toArray(new ASTNode[arrayList.size()]);
            } else {
                int i5 = this.astLengthPtr;
                if (i5 > -1) {
                    int[] iArr = this.astLengthStack;
                    this.astLengthPtr = i5 - 1;
                    int i6 = iArr[i5];
                    if (i6 != 0) {
                        ASTNode[] aSTNodeArr2 = new ASTNode[i6];
                        int i7 = this.astPtr - i6;
                        this.astPtr = i7;
                        System.arraycopy(this.astStack, i7 + 1, aSTNodeArr2, 0, i6);
                        aSTNodeArr = aSTNodeArr2;
                    } else {
                        aSTNodeArr = new ASTNode[0];
                    }
                } else {
                    aSTNodeArr = new ASTNode[0];
                }
            }
            boolean z3 = false;
            TypeDeclaration typeDeclaration2 = null;
            for (ASTNode aSTNode : aSTNodeArr) {
                if (aSTNode instanceof TypeDeclaration) {
                    ((TypeDeclaration) aSTNode).parseMethods(this, compilationUnitDeclaration);
                } else if (aSTNode instanceof AbstractMethodDeclaration) {
                    ((AbstractMethodDeclaration) aSTNode).parseStatements(this, compilationUnitDeclaration);
                } else if (aSTNode instanceof FieldDeclaration) {
                    FieldDeclaration fieldDeclaration = (FieldDeclaration) aSTNode;
                    if (fieldDeclaration.getKind() == 2) {
                        if (typeDeclaration2 == null) {
                            typeDeclaration2 = typeDeclaration;
                        }
                        if (typeDeclaration2.fields == null) {
                            typeDeclaration2.fields = new FieldDeclaration[1];
                            typeDeclaration2.fields[0] = fieldDeclaration;
                        } else {
                            int length = typeDeclaration2.fields.length;
                            FieldDeclaration[] fieldDeclarationArr = new FieldDeclaration[length + 1];
                            System.arraycopy(typeDeclaration2.fields, 0, fieldDeclarationArr, 0, length);
                            fieldDeclarationArr[length] = fieldDeclaration;
                            typeDeclaration2.fields = fieldDeclarationArr;
                        }
                        z3 = true;
                    }
                }
                if ((524288 & aSTNode.bits) != 0 && !this.options.performMethodsFullRecovery && !this.options.performStatementsRecovery) {
                    return null;
                }
            }
            if (z3) {
                for (FieldDeclaration fieldDeclaration2 : typeDeclaration2.fields) {
                    Initializer initializer = (Initializer) fieldDeclaration2;
                    initializer.parseStatements(this, typeDeclaration2, compilationUnitDeclaration);
                    if ((initializer.bits & 524288) != 0 && !this.options.performMethodsFullRecovery && !this.options.performStatementsRecovery) {
                        return null;
                    }
                }
            }
            return aSTNodeArr;
        } catch (Throwable th) {
            this.diet = z;
            this.dietInt = i3;
            this.tolerateDefaultClassMethods = z2;
            throw th;
        }
    }

    public Expression parseLambdaExpression(char[] cArr, int i, int i2, CompilationUnitDeclaration compilationUnitDeclaration, boolean z) {
        this.haltOnSyntaxError = true;
        this.reparsingLambdaExpression = true;
        return parseExpression(cArr, i, i2, compilationUnitDeclaration, z);
    }

    public Expression parseExpression(char[] cArr, int i, int i2, CompilationUnitDeclaration compilationUnitDeclaration, boolean z) {
        initialize();
        goForExpression(z);
        int[] iArr = this.nestedMethod;
        int i3 = this.nestedType;
        iArr[i3] = iArr[i3] + 1;
        this.referenceContext = compilationUnitDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.setSource(cArr);
        this.scanner.resetTo(i, (i2 + i) - 1);
        try {
            parse();
        } catch (AbortCompilation unused) {
            this.lastAct = 16382;
        } finally {
            int[] iArr2 = this.nestedMethod;
            int i4 = this.nestedType;
            iArr2[i4] = iArr2[i4] - 1;
        }
        if (this.lastAct == 16382) {
            return null;
        }
        return this.expressionStack[this.expressionPtr];
    }

    public Expression parseMemberValue(char[] cArr, int i, int i2, CompilationUnitDeclaration compilationUnitDeclaration) {
        initialize();
        goForMemberValue();
        int[] iArr = this.nestedMethod;
        int i3 = this.nestedType;
        iArr[i3] = iArr[i3] + 1;
        this.referenceContext = compilationUnitDeclaration;
        this.compilationUnit = compilationUnitDeclaration;
        this.scanner.setSource(cArr);
        this.scanner.resetTo(i, (i2 + i) - 1);
        try {
            parse();
        } catch (AbortCompilation unused) {
            this.lastAct = 16382;
        } finally {
            int[] iArr2 = this.nestedMethod;
            int i4 = this.nestedType;
            iArr2[i4] = iArr2[i4] - 1;
        }
        if (this.lastAct == 16382) {
            return null;
        }
        return this.expressionStack[this.expressionPtr];
    }

    public void parseStatements(ReferenceContext referenceContext, int i, int i2, TypeDeclaration[] typeDeclarationArr, CompilationUnitDeclaration compilationUnitDeclaration) {
        int i3;
        boolean z = this.statementRecoveryActivated;
        this.statementRecoveryActivated = true;
        initialize();
        goForBlockStatementsopt();
        int[] iArr = this.nestedMethod;
        int i4 = this.nestedType;
        iArr[i4] = iArr[i4] + 1;
        pushOnRealBlockStack(0);
        pushOnAstLengthStack(0);
        this.referenceContext = referenceContext;
        this.compilationUnit = compilationUnitDeclaration;
        this.pendingRecoveredType = null;
        if (typeDeclarationArr != null && typeDeclarationArr.length > 0) {
            this.recoveredTypes = typeDeclarationArr;
            this.recoveredTypePtr = 0;
            if (typeDeclarationArr[0].allocation == null) {
                i3 = this.recoveredTypes[0].declarationSourceStart;
            } else {
                i3 = this.recoveredTypes[0].allocation.sourceStart;
            }
            this.nextTypeStart = i3;
        } else {
            this.recoveredTypes = null;
            this.recoveredTypePtr = -1;
            this.nextTypeStart = -1;
        }
        this.scanner.resetTo(i, i2);
        this.lastCheckPoint = this.scanner.initialPosition;
        this.stateStackTop = -1;
        try {
            parse();
            int[] iArr2 = this.nestedMethod;
            int i5 = this.nestedType;
            iArr2[i5] = iArr2[i5] - 1;
        } catch (AbortCompilation unused) {
            this.lastAct = 16382;
            int[] iArr3 = this.nestedMethod;
            int i6 = this.nestedType;
            iArr3[i6] = iArr3[i6] - 1;
        } finally {
            int[] iArr4 = this.nestedMethod;
            int i7 = this.nestedType;
            iArr4[i7] = iArr4[i7] - 1;
            this.recoveredTypes = null;
            this.statementRecoveryActivated = z;
        }
        checkNonNLSAfterBodyEnd(i2);
    }

    public void persistLineSeparatorPositions() {
        if (this.scanner.recordLineSeparator) {
            this.compilationUnit.compilationResult.lineSeparatorPositions = this.scanner.getLineEnds();
        }
    }

    protected void prepareForBlockStatements() {
        int[] iArr = this.nestedMethod;
        this.nestedType = 0;
        iArr[0] = 1;
        this.variablesCounter[0] = 0;
        int[] iArr2 = this.realBlockStack;
        this.realBlockPtr = 1;
        iArr2[1] = 0;
    }

    public ProblemReporter problemReporter() {
        if (this.scanner.recordLineSeparator) {
            this.compilationUnit.compilationResult.lineSeparatorPositions = this.scanner.getLineEnds();
        }
        this.problemReporter.referenceContext = this.referenceContext;
        return this.problemReporter;
    }

    protected void pushIdentifier(char[] cArr, long j) {
        char[][] cArr2 = this.identifierStack;
        int length = cArr2.length;
        int i = this.identifierPtr + 1;
        this.identifierPtr = i;
        if (i >= length) {
            int i2 = length + 20;
            char[][] cArr3 = new char[i2][];
            this.identifierStack = cArr3;
            System.arraycopy(cArr2, 0, cArr3, 0, length);
            long[] jArr = this.identifierPositionStack;
            long[] jArr2 = new long[i2];
            this.identifierPositionStack = jArr2;
            System.arraycopy(jArr, 0, jArr2, 0, length);
        }
        char[][] cArr4 = this.identifierStack;
        int i3 = this.identifierPtr;
        cArr4[i3] = cArr;
        this.identifierPositionStack[i3] = j;
        int[] iArr = this.identifierLengthStack;
        int length2 = iArr.length;
        int i4 = this.identifierLengthPtr + 1;
        this.identifierLengthPtr = i4;
        if (i4 >= length2) {
            int[] iArr2 = new int[length2 + 10];
            this.identifierLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length2);
        }
        this.identifierLengthStack[this.identifierLengthPtr] = 1;
        if (this.parsingJava8Plus && cArr.length == 1 && cArr[0] == '_' && !this.processingLambdaParameterList) {
            problemReporter().illegalUseOfUnderscoreAsAnIdentifier((int) (j >>> 32), (int) j, false);
        }
    }

    protected void pushIdentifier() {
        pushIdentifier(this.scanner.getCurrentIdentifierSource(), (((long) this.scanner.startPosition) << 32) + ((long) (this.scanner.currentPosition - 1)));
    }

    protected void pushIdentifier(int i) {
        int[] iArr = this.identifierLengthStack;
        int length = iArr.length;
        int i2 = this.identifierLengthPtr + 1;
        this.identifierLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 10];
            this.identifierLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.identifierLengthStack[this.identifierLengthPtr] = i;
    }

    protected void pushOnAstLengthStack(int i) {
        int[] iArr = this.astLengthStack;
        int length = iArr.length;
        int i2 = this.astLengthPtr + 1;
        this.astLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 255];
            this.astLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.astLengthStack[this.astLengthPtr] = i;
    }

    protected void pushOnAstStack(ASTNode aSTNode) {
        ASTNode[] aSTNodeArr = this.astStack;
        int length = aSTNodeArr.length;
        int i = this.astPtr + 1;
        this.astPtr = i;
        if (i >= length) {
            ASTNode[] aSTNodeArr2 = new ASTNode[length + 100];
            this.astStack = aSTNodeArr2;
            System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, length);
            this.astPtr = length;
        }
        this.astStack[this.astPtr] = aSTNode;
        int[] iArr = this.astLengthStack;
        int length2 = iArr.length;
        int i2 = this.astLengthPtr + 1;
        this.astLengthPtr = i2;
        if (i2 >= length2) {
            int[] iArr2 = new int[length2 + 100];
            this.astLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length2);
        }
        this.astLengthStack[this.astLengthPtr] = 1;
    }

    protected void pushOnTypeAnnotationStack(Annotation annotation) {
        Annotation[] annotationArr = this.typeAnnotationStack;
        int length = annotationArr.length;
        int i = this.typeAnnotationPtr + 1;
        this.typeAnnotationPtr = i;
        if (i >= length) {
            Annotation[] annotationArr2 = new Annotation[length + 100];
            this.typeAnnotationStack = annotationArr2;
            System.arraycopy(annotationArr, 0, annotationArr2, 0, length);
        }
        this.typeAnnotationStack[this.typeAnnotationPtr] = annotation;
        int[] iArr = this.typeAnnotationLengthStack;
        int length2 = iArr.length;
        int i2 = this.typeAnnotationLengthPtr + 1;
        this.typeAnnotationLengthPtr = i2;
        if (i2 >= length2) {
            int[] iArr2 = new int[length2 + 100];
            this.typeAnnotationLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length2);
        }
        this.typeAnnotationLengthStack[this.typeAnnotationLengthPtr] = 1;
    }

    protected void pushOnTypeAnnotationLengthStack(int i) {
        int[] iArr = this.typeAnnotationLengthStack;
        int length = iArr.length;
        int i2 = this.typeAnnotationLengthPtr + 1;
        this.typeAnnotationLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 100];
            this.typeAnnotationLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.typeAnnotationLengthStack[this.typeAnnotationLengthPtr] = i;
    }

    protected void pushOnExpressionStack(Expression expression) {
        Expression[] expressionArr = this.expressionStack;
        int length = expressionArr.length;
        int i = this.expressionPtr + 1;
        this.expressionPtr = i;
        if (i >= length) {
            Expression[] expressionArr2 = new Expression[length + 100];
            this.expressionStack = expressionArr2;
            System.arraycopy(expressionArr, 0, expressionArr2, 0, length);
        }
        this.expressionStack[this.expressionPtr] = expression;
        int[] iArr = this.expressionLengthStack;
        int length2 = iArr.length;
        int i2 = this.expressionLengthPtr + 1;
        this.expressionLengthPtr = i2;
        if (i2 >= length2) {
            int[] iArr2 = new int[length2 + 100];
            this.expressionLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length2);
        }
        this.expressionLengthStack[this.expressionLengthPtr] = 1;
    }

    protected void pushOnExpressionStackLengthStack(int i) {
        int[] iArr = this.expressionLengthStack;
        int length = iArr.length;
        int i2 = this.expressionLengthPtr + 1;
        this.expressionLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 255];
            this.expressionLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.expressionLengthStack[this.expressionLengthPtr] = i;
    }

    protected void pushOnGenericsIdentifiersLengthStack(int i) {
        int[] iArr = this.genericsIdentifiersLengthStack;
        int length = iArr.length;
        int i2 = this.genericsIdentifiersLengthPtr + 1;
        this.genericsIdentifiersLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 10];
            this.genericsIdentifiersLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.genericsIdentifiersLengthStack[this.genericsIdentifiersLengthPtr] = i;
    }

    protected void pushOnGenericsLengthStack(int i) {
        int[] iArr = this.genericsLengthStack;
        int length = iArr.length;
        int i2 = this.genericsLengthPtr + 1;
        this.genericsLengthPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 10];
            this.genericsLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.genericsLengthStack[this.genericsLengthPtr] = i;
    }

    protected void pushOnGenericsStack(ASTNode aSTNode) {
        ASTNode[] aSTNodeArr = this.genericsStack;
        int length = aSTNodeArr.length;
        int i = this.genericsPtr + 1;
        this.genericsPtr = i;
        if (i >= length) {
            ASTNode[] aSTNodeArr2 = new ASTNode[length + 10];
            this.genericsStack = aSTNodeArr2;
            System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, length);
        }
        this.genericsStack[this.genericsPtr] = aSTNode;
        int[] iArr = this.genericsLengthStack;
        int length2 = iArr.length;
        int i2 = this.genericsLengthPtr + 1;
        this.genericsLengthPtr = i2;
        if (i2 >= length2) {
            int[] iArr2 = new int[length2 + 10];
            this.genericsLengthStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length2);
        }
        this.genericsLengthStack[this.genericsLengthPtr] = 1;
    }

    protected void pushOnIntStack(int i) {
        int[] iArr = this.intStack;
        int length = iArr.length;
        int i2 = this.intPtr + 1;
        this.intPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 255];
            this.intStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.intStack[this.intPtr] = i;
    }

    protected void pushOnRealBlockStack(int i) {
        int[] iArr = this.realBlockStack;
        int length = iArr.length;
        int i2 = this.realBlockPtr + 1;
        this.realBlockPtr = i2;
        if (i2 >= length) {
            int[] iArr2 = new int[length + 255];
            this.realBlockStack = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        this.realBlockStack[this.realBlockPtr] = i;
    }

    /* JADX INFO: renamed from: org.eclipse.jdt.internal.compiler.parser.Parser$1MethodVisitor, reason: invalid class name */
    class C1MethodVisitor extends ASTVisitor {
        TypeDeclaration enclosingType;
        public ASTVisitor typeVisitor;
        TypeDeclaration[] types = new TypeDeclaration[0];
        int typePtr = -1;

        C1MethodVisitor() {
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(ConstructorDeclaration constructorDeclaration, ClassScope classScope) {
            endVisitMethod(constructorDeclaration, classScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(Initializer initializer, MethodScope methodScope) {
            TypeDeclaration[] typeDeclarationArr;
            int i;
            if (initializer.block == null) {
                return;
            }
            int i2 = this.typePtr;
            if (i2 > -1) {
                i = i2 + 1;
                typeDeclarationArr = new TypeDeclaration[i];
                System.arraycopy(this.types, 0, typeDeclarationArr, 0, i);
            } else {
                typeDeclarationArr = null;
                i = 0;
            }
            ReferenceContext referenceContext = Parser.this.referenceContext;
            Parser.this.recoveryScanner.resetTo(initializer.bodyStart, initializer.bodyEnd);
            Scanner scanner = Parser.this.scanner;
            Parser parser = Parser.this;
            parser.scanner = parser.recoveryScanner;
            Parser.this.parseStatements(this.enclosingType, initializer.bodyStart, initializer.bodyEnd, typeDeclarationArr, Parser.this.compilationUnit);
            Parser.this.scanner = scanner;
            Parser.this.referenceContext = referenceContext;
            for (int i3 = 0; i3 < i; i3++) {
                typeDeclarationArr[i3].traverse(this.typeVisitor, methodScope);
            }
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(MethodDeclaration methodDeclaration, ClassScope classScope) {
            endVisitMethod(methodDeclaration, classScope);
        }

        private void endVisitMethod(AbstractMethodDeclaration abstractMethodDeclaration, ClassScope classScope) {
            TypeDeclaration[] typeDeclarationArr;
            int i;
            int i2 = this.typePtr;
            if (i2 > -1) {
                i = i2 + 1;
                typeDeclarationArr = new TypeDeclaration[i];
                System.arraycopy(this.types, 0, typeDeclarationArr, 0, i);
            } else {
                typeDeclarationArr = null;
                i = 0;
            }
            ReferenceContext referenceContext = Parser.this.referenceContext;
            Parser.this.recoveryScanner.resetTo(abstractMethodDeclaration.bodyStart, abstractMethodDeclaration.bodyEnd);
            Scanner scanner = Parser.this.scanner;
            Parser parser = Parser.this;
            parser.scanner = parser.recoveryScanner;
            Parser.this.parseStatements(abstractMethodDeclaration, abstractMethodDeclaration.bodyStart, abstractMethodDeclaration.bodyEnd, typeDeclarationArr, Parser.this.compilationUnit);
            Parser.this.scanner = scanner;
            Parser.this.referenceContext = referenceContext;
            for (int i3 = 0; i3 < i; i3++) {
                typeDeclarationArr[i3].traverse(this.typeVisitor, classScope);
            }
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope classScope) {
            this.typePtr = -1;
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Initializer initializer, MethodScope methodScope) {
            this.typePtr = -1;
            return initializer.block != null;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(MethodDeclaration methodDeclaration, ClassScope classScope) {
            this.typePtr = -1;
            return true;
        }

        private boolean visit(TypeDeclaration typeDeclaration) {
            TypeDeclaration[] typeDeclarationArr = this.types;
            int length = typeDeclarationArr.length;
            int i = this.typePtr + 1;
            this.typePtr = i;
            if (length <= i) {
                TypeDeclaration[] typeDeclarationArr2 = new TypeDeclaration[(i * 2) + 1];
                this.types = typeDeclarationArr2;
                System.arraycopy(typeDeclarationArr, 0, typeDeclarationArr2, 0, i);
            }
            this.types[this.typePtr] = typeDeclaration;
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
            return visit(typeDeclaration);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
            return visit(typeDeclaration);
        }
    }

    /* JADX INFO: renamed from: org.eclipse.jdt.internal.compiler.parser.Parser$1TypeVisitor, reason: invalid class name */
    class C1TypeVisitor extends ASTVisitor {
        public C1MethodVisitor methodVisitor;
        TypeDeclaration[] types = new TypeDeclaration[0];
        int typePtr = -1;

        C1TypeVisitor() {
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
            endVisitType();
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(TypeDeclaration typeDeclaration, ClassScope classScope) {
            endVisitType();
        }

        private void endVisitType() {
            this.typePtr--;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope classScope) {
            if (constructorDeclaration.isDefaultConstructor()) {
                return false;
            }
            constructorDeclaration.traverse(this.methodVisitor, classScope);
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Initializer initializer, MethodScope methodScope) {
            if (initializer.block == null) {
                return false;
            }
            this.methodVisitor.enclosingType = this.types[this.typePtr];
            initializer.traverse((ASTVisitor) this.methodVisitor, methodScope);
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(MethodDeclaration methodDeclaration, ClassScope classScope) {
            methodDeclaration.traverse(this.methodVisitor, classScope);
            return false;
        }

        private boolean visit(TypeDeclaration typeDeclaration) {
            TypeDeclaration[] typeDeclarationArr = this.types;
            int length = typeDeclarationArr.length;
            int i = this.typePtr + 1;
            this.typePtr = i;
            if (length <= i) {
                TypeDeclaration[] typeDeclarationArr2 = new TypeDeclaration[(i * 2) + 1];
                this.types = typeDeclarationArr2;
                System.arraycopy(typeDeclarationArr, 0, typeDeclarationArr2, 0, i);
            }
            this.types[this.typePtr] = typeDeclaration;
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
            return visit(typeDeclaration);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
            return visit(typeDeclaration);
        }
    }

    protected void recoverStatements() {
        C1MethodVisitor c1MethodVisitor = new C1MethodVisitor();
        C1TypeVisitor c1TypeVisitor = new C1TypeVisitor();
        c1MethodVisitor.typeVisitor = c1TypeVisitor;
        c1TypeVisitor.methodVisitor = c1MethodVisitor;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof AbstractMethodDeclaration) {
            ((AbstractMethodDeclaration) referenceContext).traverse(c1MethodVisitor, (ClassScope) null);
            return;
        }
        if (referenceContext instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) referenceContext;
            int length = typeDeclaration.fields.length;
            for (int i = 0; i < length; i++) {
                FieldDeclaration fieldDeclaration = typeDeclaration.fields[i];
                if (fieldDeclaration.getKind() == 2) {
                    Initializer initializer = (Initializer) fieldDeclaration;
                    if (initializer.block != null) {
                        c1MethodVisitor.enclosingType = typeDeclaration;
                        initializer.traverse((ASTVisitor) c1MethodVisitor, (MethodScope) null);
                    }
                }
            }
        }
    }

    public void recoveryExitFromVariable() {
        RecoveredElement recoveredElement = this.currentElement;
        if (recoveredElement == null || recoveredElement.parent == null) {
            return;
        }
        RecoveredElement recoveredElement2 = this.currentElement;
        if (recoveredElement2 instanceof RecoveredLocalVariable) {
            this.currentElement.updateSourceEndIfNecessary(((RecoveredLocalVariable) recoveredElement2).localDeclaration.sourceEnd);
            this.currentElement = this.currentElement.parent;
        } else {
            if (!(recoveredElement2 instanceof RecoveredField) || (recoveredElement2 instanceof RecoveredInitializer) || recoveredElement2.bracketBalance > 0) {
                return;
            }
            this.currentElement.updateSourceEndIfNecessary(((RecoveredField) this.currentElement).fieldDeclaration.sourceEnd);
            this.currentElement = this.currentElement.parent;
        }
    }

    public void recoveryTokenCheck() {
        int i = this.currentToken;
        if (i == 28) {
            this.endStatementPosition = this.scanner.currentPosition - 1;
            this.endPosition = this.scanner.startPosition - 1;
            RecoveredType recoveredTypeCurrentRecoveryType = currentRecoveryType();
            if (recoveredTypeCurrentRecoveryType != null) {
                recoveredTypeCurrentRecoveryType.insideEnumConstantPart = false;
            }
        } else {
            if (i != 32) {
                if (i != 48) {
                    if (i == 49) {
                        RecoveredElement recoveredElementUpdateOnOpeningBrace = !this.ignoreNextOpeningBrace ? this.currentElement.updateOnOpeningBrace(this.scanner.startPosition - 1, this.scanner.currentPosition - 1) : null;
                        this.lastCheckPoint = this.scanner.currentPosition;
                        if (recoveredElementUpdateOnOpeningBrace != null) {
                            this.restartRecovery = true;
                            this.currentElement = recoveredElementUpdateOnOpeningBrace;
                        }
                    }
                } else if (this.recordStringLiterals && this.checkExternalizeStrings && this.lastPosistion < this.scanner.currentPosition && !this.statementRecoveryActivated) {
                    this.compilationUnit.recordStringLiteral(createStringLiteral(this.scanner.getCurrentTokenSourceString(), this.scanner.startPosition, this.scanner.currentPosition - 1, Util.getLineNumber(this.scanner.startPosition, this.scanner.lineEnds, 0, this.scanner.linePtr)), this.currentElement != null);
                }
            } else if (this.ignoreNextClosingBrace) {
                this.ignoreNextClosingBrace = false;
            } else {
                this.rBraceStart = this.scanner.startPosition - 1;
                int i2 = this.scanner.currentPosition - 1;
                this.rBraceEnd = i2;
                this.endPosition = flushCommentsDefinedPriorTo(i2);
                RecoveredElement recoveredElementUpdateOnClosingBrace = this.currentElement.updateOnClosingBrace(this.scanner.startPosition, this.rBraceEnd);
                this.lastCheckPoint = this.scanner.currentPosition;
                if (recoveredElementUpdateOnClosingBrace != this.currentElement) {
                    this.currentElement = recoveredElementUpdateOnClosingBrace;
                }
            }
            this.ignoreNextOpeningBrace = false;
        }
        if (this.rBraceEnd > this.rBraceSuccessorStart && this.scanner.currentPosition != this.scanner.startPosition) {
            this.rBraceSuccessorStart = this.scanner.startPosition;
        }
        this.ignoreNextOpeningBrace = false;
    }

    protected void reportSyntaxErrors(boolean z, int i) {
        ReferenceContext referenceContext = this.referenceContext;
        if (!(referenceContext instanceof MethodDeclaration) || (((MethodDeclaration) referenceContext).bits & 32) == 0) {
            this.compilationUnit.compilationResult.lineSeparatorPositions = this.scanner.getLineEnds();
            this.scanner.recordLineSeparator = false;
            int i2 = this.scanner.initialPosition;
            int i3 = this.scanner.eofPosition == Integer.MAX_VALUE ? this.scanner.eofPosition : this.scanner.eofPosition - 1;
            if (z) {
                TypeDeclaration[] typeDeclarationArr = this.compilationUnit.types;
                int[][] iArrComputeDietRange = RangeUtil.computeDietRange(typeDeclarationArr);
                new DiagnoseParser(this, i, i2, i3, iArrComputeDietRange[0], iArrComputeDietRange[1], iArrComputeDietRange[2], this.options).diagnoseParse(false);
                reportSyntaxErrorsForSkippedMethod(typeDeclarationArr);
                this.scanner.resetTo(i2, i3);
                return;
            }
            new DiagnoseParser(this, i, i2, i3, this.options).diagnoseParse(this.options.performStatementsRecovery);
        }
    }

    private void reportSyntaxErrorsForSkippedMethod(TypeDeclaration[] typeDeclarationArr) {
        if (typeDeclarationArr != null) {
            for (int i = 0; i < typeDeclarationArr.length; i++) {
                TypeDeclaration[] typeDeclarationArr2 = typeDeclarationArr[i].memberTypes;
                if (typeDeclarationArr2 != null) {
                    reportSyntaxErrorsForSkippedMethod(typeDeclarationArr2);
                }
                AbstractMethodDeclaration[] abstractMethodDeclarationArr = typeDeclarationArr[i].methods;
                if (abstractMethodDeclarationArr != null) {
                    for (AbstractMethodDeclaration abstractMethodDeclaration : abstractMethodDeclarationArr) {
                        if ((abstractMethodDeclaration.bits & 32) != 0) {
                            if (abstractMethodDeclaration.isAnnotationMethod()) {
                                new DiagnoseParser(this, 29, abstractMethodDeclaration.declarationSourceStart, abstractMethodDeclaration.declarationSourceEnd, this.options).diagnoseParse(this.options.performStatementsRecovery);
                            } else {
                                new DiagnoseParser(this, 9, abstractMethodDeclaration.declarationSourceStart, abstractMethodDeclaration.declarationSourceEnd, this.options).diagnoseParse(this.options.performStatementsRecovery);
                            }
                        }
                    }
                }
                FieldDeclaration[] fieldDeclarationArr = typeDeclarationArr[i].fields;
                if (fieldDeclarationArr != null) {
                    for (FieldDeclaration fieldDeclaration : fieldDeclarationArr) {
                        if (fieldDeclaration instanceof Initializer) {
                            Initializer initializer = (Initializer) fieldDeclaration;
                            if ((initializer.bits & 32) != 0) {
                                new DiagnoseParser(this, 14, initializer.declarationSourceStart, initializer.declarationSourceEnd, this.options).diagnoseParse(this.options.performStatementsRecovery);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void resetModifiers() {
        this.modifiers = 0;
        this.modifiersSourceStart = -1;
        this.scanner.commentPtr = -1;
    }

    protected void resetStacks() {
        this.astPtr = -1;
        this.astLengthPtr = -1;
        this.expressionPtr = -1;
        this.expressionLengthPtr = -1;
        this.typeAnnotationLengthPtr = -1;
        this.typeAnnotationPtr = -1;
        this.identifierPtr = -1;
        this.identifierLengthPtr = -1;
        this.intPtr = -1;
        int[] iArr = this.nestedMethod;
        this.nestedType = 0;
        iArr[0] = 0;
        this.variablesCounter[0] = 0;
        this.dimensions = 0;
        int[] iArr2 = this.realBlockStack;
        this.realBlockPtr = 0;
        iArr2[0] = 0;
        this.recoveredStaticInitializerStart = 0;
        this.listLength = 0;
        this.listTypeParameterLength = 0;
        this.genericsIdentifiersLengthPtr = -1;
        this.genericsLengthPtr = -1;
        this.genericsPtr = -1;
        this.valueLambdaNestDepth = -1;
    }

    protected int resumeAfterRecovery() {
        if (!this.methodRecoveryActivated && !this.statementRecoveryActivated) {
            resetStacks();
            resetModifiers();
            if (!moveRecoveryCheckpoint() || !(this.referenceContext instanceof CompilationUnitDeclaration)) {
                return 0;
            }
            goForHeaders();
            this.diet = true;
            this.dietInt = 0;
            return 1;
        }
        if (this.statementRecoveryActivated) {
            return 0;
        }
        resetStacks();
        resetModifiers();
        if (!moveRecoveryCheckpoint()) {
            return 0;
        }
        goForHeaders();
        return 1;
    }

    protected int resumeOnSyntaxError() {
        if (this.haltOnSyntaxError) {
            return 0;
        }
        if (this.currentElement == null) {
            this.javadoc = null;
            if (this.statementRecoveryActivated) {
                return 0;
            }
            this.currentElement = buildInitialRecoveryState();
        }
        if (this.currentElement == null) {
            return 0;
        }
        if (this.restartRecovery) {
            this.restartRecovery = false;
        }
        updateRecoveryState();
        if (getFirstToken() == 21 && (this.referenceContext instanceof CompilationUnitDeclaration)) {
            TypeDeclaration typeDeclaration = new TypeDeclaration(this.referenceContext.compilationResult());
            typeDeclaration.f619name = Util.EMPTY_STRING.toCharArray();
            this.currentElement = this.currentElement.add(typeDeclaration, 0);
        }
        if (this.lastPosistion < this.scanner.currentPosition) {
            this.lastPosistion = this.scanner.currentPosition;
            Scanner scanner = this.scanner;
            scanner.lastPosition = scanner.currentPosition;
        }
        return resumeAfterRecovery();
    }

    public void setMethodsFullRecovery(boolean z) {
        this.options.performMethodsFullRecovery = z;
    }

    public void setStatementsRecovery(boolean z) {
        if (z) {
            this.options.performMethodsFullRecovery = true;
        }
        this.options.performStatementsRecovery = z;
    }

    public String toString() {
        String str = String.valueOf("lastCheckpoint : int = " + String.valueOf(this.lastCheckPoint) + "\n") + "identifierStack : char[" + (this.identifierPtr + 1) + "][] = {";
        for (int i = 0; i <= this.identifierPtr; i++) {
            str = String.valueOf(str) + "\"" + String.valueOf(this.identifierStack[i]) + "\",";
        }
        String str2 = String.valueOf(String.valueOf(str) + "}\n") + "identifierLengthStack : int[" + (this.identifierLengthPtr + 1) + "] = {";
        for (int i2 = 0; i2 <= this.identifierLengthPtr; i2++) {
            str2 = String.valueOf(str2) + this.identifierLengthStack[i2] + ",";
        }
        String str3 = String.valueOf(String.valueOf(str2) + "}\n") + "astLengthStack : int[" + (this.astLengthPtr + 1) + "] = {";
        for (int i3 = 0; i3 <= this.astLengthPtr; i3++) {
            str3 = String.valueOf(str3) + this.astLengthStack[i3] + ",";
        }
        String str4 = String.valueOf(String.valueOf(String.valueOf(str3) + "}\n") + "astPtr : int = " + String.valueOf(this.astPtr) + "\n") + "intStack : int[" + (this.intPtr + 1) + "] = {";
        for (int i4 = 0; i4 <= this.intPtr; i4++) {
            str4 = String.valueOf(str4) + this.intStack[i4] + ",";
        }
        String str5 = String.valueOf(String.valueOf(str4) + "}\n") + "expressionLengthStack : int[" + (this.expressionLengthPtr + 1) + "] = {";
        for (int i5 = 0; i5 <= this.expressionLengthPtr; i5++) {
            str5 = String.valueOf(str5) + this.expressionLengthStack[i5] + ",";
        }
        String str6 = String.valueOf(String.valueOf(String.valueOf(str5) + "}\n") + "expressionPtr : int = " + String.valueOf(this.expressionPtr) + "\n") + "genericsIdentifiersLengthStack : int[" + (this.genericsIdentifiersLengthPtr + 1) + "] = {";
        for (int i6 = 0; i6 <= this.genericsIdentifiersLengthPtr; i6++) {
            str6 = String.valueOf(str6) + this.genericsIdentifiersLengthStack[i6] + ",";
        }
        String str7 = String.valueOf(String.valueOf(str6) + "}\n") + "genericsLengthStack : int[" + (this.genericsLengthPtr + 1) + "] = {";
        for (int i7 = 0; i7 <= this.genericsLengthPtr; i7++) {
            str7 = String.valueOf(str7) + this.genericsLengthStack[i7] + ",";
        }
        return String.valueOf(String.valueOf(String.valueOf(str7) + "}\n") + "genericsPtr : int = " + String.valueOf(this.genericsPtr) + "\n") + "\n\n\n----------------Scanner--------------\n" + this.scanner.toString();
    }

    protected void updateRecoveryState() {
        this.currentElement.updateFromParserState();
        recoveryTokenCheck();
    }

    protected void updateSourceDeclarationParts(int i) {
        int i2 = this.astStack[(this.astPtr - i) + 1].sourceStart - 1;
        for (int i3 = 0; i3 < i - 1; i3++) {
            FieldDeclaration fieldDeclaration = (FieldDeclaration) this.astStack[(this.astPtr - i3) - 1];
            fieldDeclaration.endPart1Position = i2;
            fieldDeclaration.endPart2Position = this.astStack[this.astPtr - i3].sourceStart - 1;
        }
        FieldDeclaration fieldDeclaration2 = (FieldDeclaration) this.astStack[this.astPtr];
        fieldDeclaration2.endPart1Position = i2;
        fieldDeclaration2.endPart2Position = fieldDeclaration2.declarationSourceEnd;
    }

    protected void updateSourcePosition(Expression expression) {
        int[] iArr = this.intStack;
        int i = this.intPtr;
        this.intPtr = i - 1;
        expression.sourceEnd = iArr[i];
        int[] iArr2 = this.intStack;
        int i2 = this.intPtr;
        this.intPtr = i2 - 1;
        expression.sourceStart = iArr2[i2];
    }

    public void copyState(Parser parser) {
        this.stateStackTop = parser.stateStackTop;
        this.unstackedAct = parser.unstackedAct;
        this.identifierPtr = parser.identifierPtr;
        this.identifierLengthPtr = parser.identifierLengthPtr;
        this.astPtr = parser.astPtr;
        this.astLengthPtr = parser.astLengthPtr;
        this.expressionPtr = parser.expressionPtr;
        this.expressionLengthPtr = parser.expressionLengthPtr;
        this.genericsPtr = parser.genericsPtr;
        this.genericsLengthPtr = parser.genericsLengthPtr;
        this.genericsIdentifiersLengthPtr = parser.genericsIdentifiersLengthPtr;
        this.typeAnnotationPtr = parser.typeAnnotationPtr;
        this.typeAnnotationLengthPtr = parser.typeAnnotationLengthPtr;
        this.intPtr = parser.intPtr;
        this.nestedType = parser.nestedType;
        this.realBlockPtr = parser.realBlockPtr;
        this.valueLambdaNestDepth = parser.valueLambdaNestDepth;
        int[] iArr = parser.stack;
        int length = iArr.length;
        int[] iArr2 = new int[length];
        this.stack = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, length);
        char[][] cArr = parser.identifierStack;
        int length2 = cArr.length;
        char[][] cArr2 = new char[length2][];
        this.identifierStack = cArr2;
        System.arraycopy(cArr, 0, cArr2, 0, length2);
        int[] iArr3 = parser.identifierLengthStack;
        int length3 = iArr3.length;
        int[] iArr4 = new int[length3];
        this.identifierLengthStack = iArr4;
        System.arraycopy(iArr3, 0, iArr4, 0, length3);
        long[] jArr = parser.identifierPositionStack;
        int length4 = jArr.length;
        long[] jArr2 = new long[length4];
        this.identifierPositionStack = jArr2;
        System.arraycopy(jArr, 0, jArr2, 0, length4);
        ASTNode[] aSTNodeArr = parser.astStack;
        int length5 = aSTNodeArr.length;
        ASTNode[] aSTNodeArr2 = new ASTNode[length5];
        this.astStack = aSTNodeArr2;
        System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, length5);
        int[] iArr5 = parser.astLengthStack;
        int length6 = iArr5.length;
        int[] iArr6 = new int[length6];
        this.astLengthStack = iArr6;
        System.arraycopy(iArr5, 0, iArr6, 0, length6);
        Expression[] expressionArr = parser.expressionStack;
        int length7 = expressionArr.length;
        Expression[] expressionArr2 = new Expression[length7];
        this.expressionStack = expressionArr2;
        System.arraycopy(expressionArr, 0, expressionArr2, 0, length7);
        int[] iArr7 = parser.expressionLengthStack;
        int length8 = iArr7.length;
        int[] iArr8 = new int[length8];
        this.expressionLengthStack = iArr8;
        System.arraycopy(iArr7, 0, iArr8, 0, length8);
        ASTNode[] aSTNodeArr3 = parser.genericsStack;
        int length9 = aSTNodeArr3.length;
        ASTNode[] aSTNodeArr4 = new ASTNode[length9];
        this.genericsStack = aSTNodeArr4;
        System.arraycopy(aSTNodeArr3, 0, aSTNodeArr4, 0, length9);
        int[] iArr9 = parser.genericsLengthStack;
        int length10 = iArr9.length;
        int[] iArr10 = new int[length10];
        this.genericsLengthStack = iArr10;
        System.arraycopy(iArr9, 0, iArr10, 0, length10);
        int[] iArr11 = parser.genericsIdentifiersLengthStack;
        int length11 = iArr11.length;
        int[] iArr12 = new int[length11];
        this.genericsIdentifiersLengthStack = iArr12;
        System.arraycopy(iArr11, 0, iArr12, 0, length11);
        Annotation[] annotationArr = parser.typeAnnotationStack;
        int length12 = annotationArr.length;
        Annotation[] annotationArr2 = new Annotation[length12];
        this.typeAnnotationStack = annotationArr2;
        System.arraycopy(annotationArr, 0, annotationArr2, 0, length12);
        int[] iArr13 = parser.typeAnnotationLengthStack;
        int length13 = iArr13.length;
        int[] iArr14 = new int[length13];
        this.typeAnnotationLengthStack = iArr14;
        System.arraycopy(iArr13, 0, iArr14, 0, length13);
        int[] iArr15 = parser.intStack;
        int length14 = iArr15.length;
        int[] iArr16 = new int[length14];
        this.intStack = iArr16;
        System.arraycopy(iArr15, 0, iArr16, 0, length14);
        int[] iArr17 = parser.nestedMethod;
        int length15 = iArr17.length;
        int[] iArr18 = new int[length15];
        this.nestedMethod = iArr18;
        System.arraycopy(iArr17, 0, iArr18, 0, length15);
        int[] iArr19 = parser.realBlockStack;
        int length16 = iArr19.length;
        int[] iArr20 = new int[length16];
        this.realBlockStack = iArr20;
        System.arraycopy(iArr19, 0, iArr20, 0, length16);
        int[] iArr21 = parser.stateStackLengthStack;
        int length17 = iArr21.length;
        int[] iArr22 = new int[length17];
        this.stateStackLengthStack = iArr22;
        System.arraycopy(iArr21, 0, iArr22, 0, length17);
        int[] iArr23 = parser.variablesCounter;
        int length18 = iArr23.length;
        int[] iArr24 = new int[length18];
        this.variablesCounter = iArr24;
        System.arraycopy(iArr23, 0, iArr24, 0, length18);
        int[] iArr25 = parser.stack;
        int length19 = iArr25.length;
        int[] iArr26 = new int[length19];
        this.stack = iArr26;
        System.arraycopy(iArr25, 0, iArr26, 0, length19);
        int[] iArr27 = parser.stack;
        int length20 = iArr27.length;
        int[] iArr28 = new int[length20];
        this.stack = iArr28;
        System.arraycopy(iArr27, 0, iArr28, 0, length20);
        int[] iArr29 = parser.stack;
        int length21 = iArr29.length;
        int[] iArr30 = new int[length21];
        this.stack = iArr30;
        System.arraycopy(iArr29, 0, iArr30, 0, length21);
        this.listLength = parser.listLength;
        this.listTypeParameterLength = parser.listTypeParameterLength;
        this.dimensions = parser.dimensions;
        this.recoveredStaticInitializerStart = parser.recoveredStaticInitializerStart;
    }

    public int automatonState() {
        return this.stack[this.stateStackTop];
    }

    public boolean automatonWillShift(int i, int i2) {
        int i3;
        int iTAction;
        int i4 = this.stateStackTop;
        int i5 = this.stack[i4];
        if (i2 <= 800) {
            i2 += 16382;
            i4--;
            i3 = i5;
        } else {
            i3 = i5;
        }
        while (true) {
            if (i2 > 16382) {
                i2 -= 16382;
                do {
                    i4 -= rhs[i2] - 1;
                    if (i4 < i4) {
                        i3 = this.stack[i4];
                        i4 = i4;
                    }
                    i2 = ntAction(i3, lhs[i2]);
                } while (i2 <= 800);
            }
            i3 = i2;
            i4++;
            iTAction = tAction(i3, i);
            if (iTAction > 800) {
                break;
            }
            i2 = iTAction + 16382;
        }
        return iTAction != 16382;
    }
}
