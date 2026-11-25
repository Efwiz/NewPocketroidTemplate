package org.eclipse.jdt.internal.compiler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.RecoveryScannerData;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class CompilationResult {
    private static final int[] EMPTY_LINE_ENDS = Util.EMPTY_INT_ARRAY;
    private static final Comparator PROBLEM_COMPARATOR = new Comparator() { // from class: org.eclipse.jdt.internal.compiler.CompilationResult.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((CategorizedProblem) obj).getSourceStart() - ((CategorizedProblem) obj2).getSourceStart();
        }
    };
    public ICompilationUnit compilationUnit;
    public char[] fileName;
    private Set firstErrors;
    private boolean hasMandatoryErrors;
    public int[] lineSeparatorPositions;
    private int maxProblemPerUnit;
    private int numberOfErrors;
    public char[][] packageName;
    public int problemCount;
    public CategorizedProblem[] problems;
    private Map<CategorizedProblem, ReferenceContext> problemsMap;
    public char[][][] qualifiedReferences;
    public RecoveryScannerData recoveryScannerData;
    public char[][] rootReferences;
    public char[][] simpleNameReferences;
    public int taskCount;
    public CategorizedProblem[] tasks;
    public int totalUnitsKnown;
    public int unitIndex;
    public boolean hasAnnotations = false;
    public boolean hasFunctionalTypes = false;
    public Map compiledTypes = new Hashtable(11);
    public boolean hasBeenAccepted = false;
    public boolean hasInconsistentToplevelHierarchies = false;
    public boolean hasSyntaxError = false;
    public boolean checkSecondaryTypes = false;

    public CompilationResult(char[] cArr, int i, int i2, int i3) {
        this.fileName = cArr;
        this.unitIndex = i;
        this.totalUnitsKnown = i2;
        this.maxProblemPerUnit = i3;
    }

    public CompilationResult(ICompilationUnit iCompilationUnit, int i, int i2, int i3) {
        this.fileName = iCompilationUnit.getFileName();
        this.compilationUnit = iCompilationUnit;
        this.unitIndex = i;
        this.totalUnitsKnown = i2;
        this.maxProblemPerUnit = i3;
    }

    private int computePriority(CategorizedProblem categorizedProblem) {
        int sourceLineNumber = 10000 - categorizedProblem.getSourceLineNumber();
        if (sourceLineNumber < 0) {
            sourceLineNumber = 0;
        }
        if (categorizedProblem.isError()) {
            sourceLineNumber += BZip2Constants.BASEBLOCKSIZE;
        }
        Map<CategorizedProblem, ReferenceContext> map = this.problemsMap;
        ReferenceContext referenceContext = map == null ? null : map.get(categorizedProblem);
        if (referenceContext == null) {
            return sourceLineNumber + 40000;
        }
        if (!(referenceContext instanceof AbstractMethodDeclaration)) {
            sourceLineNumber += 40000;
        } else if (((AbstractMethodDeclaration) referenceContext).isStatic()) {
            sourceLineNumber += 10000;
        }
        return this.firstErrors.contains(categorizedProblem) ? sourceLineNumber + 20000 : sourceLineNumber;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0045  */
    public CategorizedProblem[] getAllProblems() {
        CategorizedProblem[] problems = getProblems();
        int i = 0;
        int length = problems != null ? problems.length : 0;
        CategorizedProblem[] tasks = getTasks();
        int length2 = tasks != null ? tasks.length : 0;
        if (length2 == 0) {
            return problems;
        }
        if (length == 0) {
            return tasks;
        }
        int i2 = length + length2;
        CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[i2];
        int i3 = 0;
        int i4 = 0;
        while (i + i3 < i2) {
            CategorizedProblem categorizedProblem = null;
            CategorizedProblem categorizedProblem2 = i < length2 ? tasks[i] : null;
            CategorizedProblem categorizedProblem3 = i3 < length ? problems[i3] : null;
            if (categorizedProblem3 != null) {
                if (categorizedProblem2 == null || categorizedProblem3.getSourceStart() < categorizedProblem2.getSourceStart()) {
                    i3++;
                    categorizedProblem = categorizedProblem3;
                } else {
                    i++;
                    categorizedProblem = categorizedProblem2;
                }
            } else if (categorizedProblem2 != null) {
                i++;
                categorizedProblem = categorizedProblem2;
            }
            categorizedProblemArr[i4] = categorizedProblem;
            i4++;
        }
        return categorizedProblemArr;
    }

    public ClassFile[] getClassFiles() {
        ClassFile[] classFileArr = new ClassFile[this.compiledTypes.size()];
        this.compiledTypes.values().toArray(classFileArr);
        return classFileArr;
    }

    public ICompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    public CategorizedProblem[] getErrors() {
        int i;
        CategorizedProblem[] problems = getProblems();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.problemCount;
            if (i2 >= i) {
                break;
            }
            if (problems[i2].isError()) {
                i3++;
            }
            i2++;
        }
        if (i3 == i) {
            return problems;
        }
        CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[i3];
        int i4 = 0;
        for (int i5 = 0; i5 < this.problemCount; i5++) {
            if (problems[i5].isError()) {
                categorizedProblemArr[i4] = problems[i5];
                i4++;
            }
        }
        return categorizedProblemArr;
    }

    public char[] getFileName() {
        return this.fileName;
    }

    public int[] getLineSeparatorPositions() {
        int[] iArr = this.lineSeparatorPositions;
        return iArr == null ? EMPTY_LINE_ENDS : iArr;
    }

    public CategorizedProblem[] getProblems() {
        int i;
        CategorizedProblem[] categorizedProblemArr = this.problems;
        if (categorizedProblemArr != null) {
            int i2 = this.problemCount;
            if (i2 != categorizedProblemArr.length) {
                CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[i2];
                this.problems = categorizedProblemArr2;
                System.arraycopy(categorizedProblemArr, 0, categorizedProblemArr2, 0, i2);
            }
            int i3 = this.maxProblemPerUnit;
            if (i3 > 0 && (i = this.problemCount) > i3) {
                quickPrioritize(this.problems, 0, i - 1);
                int i4 = this.maxProblemPerUnit;
                this.problemCount = i4;
                CategorizedProblem[] categorizedProblemArr3 = this.problems;
                CategorizedProblem[] categorizedProblemArr4 = new CategorizedProblem[i4];
                this.problems = categorizedProblemArr4;
                System.arraycopy(categorizedProblemArr3, 0, categorizedProblemArr4, 0, i4);
            }
            CategorizedProblem[] categorizedProblemArr5 = this.problems;
            Arrays.sort(categorizedProblemArr5, 0, categorizedProblemArr5.length, PROBLEM_COMPARATOR);
        }
        return this.problems;
    }

    public CategorizedProblem[] getCUProblems() {
        int i;
        int i2;
        int i3;
        if (this.problems != null) {
            CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[this.problemCount];
            int i4 = 0;
            int i5 = 0;
            while (true) {
                i = this.problemCount;
                if (i4 >= i) {
                    break;
                }
                CategorizedProblem categorizedProblem = this.problems[i4];
                if (categorizedProblem.getID() != 536871825) {
                    i3 = i5 + 1;
                    categorizedProblemArr[i5] = categorizedProblem;
                } else {
                    ICompilationUnit iCompilationUnit = this.compilationUnit;
                    if (iCompilationUnit != null && CharOperation.equals(iCompilationUnit.getMainTypeName(), TypeConstants.PACKAGE_INFO_NAME)) {
                        i3 = i5 + 1;
                        categorizedProblemArr[i5] = categorizedProblem;
                    }
                    i4++;
                }
                i5 = i3;
                i4++;
            }
            if (i5 < i) {
                CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[i5];
                System.arraycopy(categorizedProblemArr, 0, categorizedProblemArr2, 0, i5);
                this.problemCount = i5;
                categorizedProblemArr = categorizedProblemArr2;
            }
            this.problems = categorizedProblemArr;
            int i6 = this.maxProblemPerUnit;
            if (i6 > 0 && (i2 = this.problemCount) > i6) {
                quickPrioritize(categorizedProblemArr, 0, i2 - 1);
                int i7 = this.maxProblemPerUnit;
                this.problemCount = i7;
                CategorizedProblem[] categorizedProblemArr3 = this.problems;
                CategorizedProblem[] categorizedProblemArr4 = new CategorizedProblem[i7];
                this.problems = categorizedProblemArr4;
                System.arraycopy(categorizedProblemArr3, 0, categorizedProblemArr4, 0, i7);
            }
            CategorizedProblem[] categorizedProblemArr5 = this.problems;
            Arrays.sort(categorizedProblemArr5, 0, categorizedProblemArr5.length, PROBLEM_COMPARATOR);
        }
        return this.problems;
    }

    public CategorizedProblem[] getTasks() {
        CategorizedProblem[] categorizedProblemArr = this.tasks;
        if (categorizedProblemArr != null) {
            int i = this.taskCount;
            if (i != categorizedProblemArr.length) {
                CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[i];
                this.tasks = categorizedProblemArr2;
                System.arraycopy(categorizedProblemArr, 0, categorizedProblemArr2, 0, i);
            }
            CategorizedProblem[] categorizedProblemArr3 = this.tasks;
            Arrays.sort(categorizedProblemArr3, 0, categorizedProblemArr3.length, PROBLEM_COMPARATOR);
        }
        return this.tasks;
    }

    public boolean hasErrors() {
        return this.numberOfErrors != 0;
    }

    public boolean hasMandatoryErrors() {
        return this.hasMandatoryErrors;
    }

    public boolean hasProblems() {
        return this.problemCount != 0;
    }

    public boolean hasTasks() {
        return this.taskCount != 0;
    }

    public boolean hasWarnings() {
        if (this.problems != null) {
            for (int i = 0; i < this.problemCount; i++) {
                if (this.problems[i].isWarning()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void quickPrioritize(CategorizedProblem[] categorizedProblemArr, int i, int i2) {
        if (i >= i2) {
            return;
        }
        int iComputePriority = computePriority(categorizedProblemArr[((i2 - i) / 2) + i]);
        int i3 = i;
        int i4 = i2;
        while (true) {
            if (computePriority(categorizedProblemArr[i4]) >= iComputePriority) {
                while (iComputePriority < computePriority(categorizedProblemArr[i3])) {
                    i3++;
                }
                if (i3 <= i4) {
                    CategorizedProblem categorizedProblem = categorizedProblemArr[i3];
                    categorizedProblemArr[i3] = categorizedProblemArr[i4];
                    categorizedProblemArr[i4] = categorizedProblem;
                    i3++;
                    i4--;
                }
                if (i3 > i4) {
                    break;
                }
            } else {
                i4--;
            }
        }
        if (i < i4) {
            quickPrioritize(categorizedProblemArr, i, i4);
        }
        if (i3 < i2) {
            quickPrioritize(categorizedProblemArr, i3, i2);
        }
    }

    public void recordPackageName(char[][] cArr) {
        this.packageName = cArr;
    }

    public void record(CategorizedProblem categorizedProblem, ReferenceContext referenceContext) {
        record(categorizedProblem, referenceContext, true);
    }

    public void record(CategorizedProblem categorizedProblem, ReferenceContext referenceContext, boolean z) {
        if (categorizedProblem.getID() == 536871362) {
            recordTask(categorizedProblem);
            return;
        }
        int i = this.problemCount;
        if (i == 0) {
            this.problems = new CategorizedProblem[5];
        } else {
            CategorizedProblem[] categorizedProblemArr = this.problems;
            if (i == categorizedProblemArr.length) {
                CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[i * 2];
                this.problems = categorizedProblemArr2;
                System.arraycopy(categorizedProblemArr, 0, categorizedProblemArr2, 0, i);
            }
        }
        CategorizedProblem[] categorizedProblemArr3 = this.problems;
        int i2 = this.problemCount;
        this.problemCount = i2 + 1;
        categorizedProblemArr3[i2] = categorizedProblem;
        if (referenceContext != null) {
            if (this.problemsMap == null) {
                this.problemsMap = new HashMap(5);
            }
            if (this.firstErrors == null) {
                this.firstErrors = new HashSet(5);
            }
            if (categorizedProblem.isError() && !referenceContext.hasErrors()) {
                this.firstErrors.add(categorizedProblem);
            }
            this.problemsMap.put(categorizedProblem, referenceContext);
        }
        if (categorizedProblem.isError()) {
            this.numberOfErrors++;
            if (z) {
                this.hasMandatoryErrors = true;
            }
            if ((categorizedProblem.getID() & 1073741824) != 0) {
                this.hasSyntaxError = true;
            }
        }
    }

    ReferenceContext getContext(CategorizedProblem categorizedProblem) {
        if (categorizedProblem != null) {
            return this.problemsMap.get(categorizedProblem);
        }
        return null;
    }

    public void record(char[] cArr, ClassFile classFile) {
        SourceTypeBinding sourceTypeBinding = classFile.referenceBinding;
        if (!sourceTypeBinding.isLocalType() && sourceTypeBinding.isHierarchyInconsistent()) {
            this.hasInconsistentToplevelHierarchies = true;
        }
        this.compiledTypes.put(cArr, classFile);
    }

    private void recordTask(CategorizedProblem categorizedProblem) {
        int i = this.taskCount;
        if (i == 0) {
            this.tasks = new CategorizedProblem[5];
        } else {
            CategorizedProblem[] categorizedProblemArr = this.tasks;
            if (i == categorizedProblemArr.length) {
                CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[i * 2];
                this.tasks = categorizedProblemArr2;
                System.arraycopy(categorizedProblemArr, 0, categorizedProblemArr2, 0, i);
            }
        }
        CategorizedProblem[] categorizedProblemArr3 = this.tasks;
        int i2 = this.taskCount;
        this.taskCount = i2 + 1;
        categorizedProblemArr3[i2] = categorizedProblem;
    }

    public void removeProblem(CategorizedProblem categorizedProblem) {
        Map<CategorizedProblem, ReferenceContext> map = this.problemsMap;
        if (map != null) {
            map.remove(categorizedProblem);
        }
        Set set = this.firstErrors;
        if (set != null) {
            set.remove(categorizedProblem);
        }
        if (categorizedProblem.isError()) {
            this.numberOfErrors--;
        }
        this.problemCount--;
    }

    public CompilationResult tagAsAccepted() {
        this.hasBeenAccepted = true;
        this.problemsMap = null;
        this.firstErrors = null;
        return this;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.fileName != null) {
            stringBuffer.append("Filename : ").append(this.fileName).append('\n');
        }
        if (this.compiledTypes != null) {
            stringBuffer.append("COMPILED type(s)\t\n");
            Iterator it = this.compiledTypes.keySet().iterator();
            while (it.hasNext()) {
                stringBuffer.append("\t - ").append((char[]) it.next()).append('\n');
            }
        } else {
            stringBuffer.append("No COMPILED type\n");
        }
        if (this.problems != null) {
            stringBuffer.append(this.problemCount).append(" PROBLEM(s) detected \n");
            for (int i = 0; i < this.problemCount; i++) {
                stringBuffer.append("\t - ").append(this.problems[i]).append('\n');
            }
        } else {
            stringBuffer.append("No PROBLEM\n");
        }
        return stringBuffer.toString();
    }
}
