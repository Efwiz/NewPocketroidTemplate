package org.jetbrains.kotlin.com.intellij.util.diff;

import java.util.BitSet;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.LineTokenizer;
import org.jetbrains.kotlin.com.intellij.util.containers.Enumerator;

/* JADX INFO: loaded from: classes3.dex */
public final class Diff {
    private static final Logger LOG = Logger.getInstance((Class<?>) Diff.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 22) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 22) ? 2 : 3];
        switch (i) {
            case 1:
            case 20:
                objArr[0] = "after";
                break;
            case 2:
                objArr[0] = "s";
                break;
            case 3:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/diff/Diff";
                break;
            case 4:
                objArr[0] = "objects1";
                break;
            case 5:
                objArr[0] = "objects2";
                break;
            case 6:
                objArr[0] = "array1";
                break;
            case 7:
                objArr[0] = "array2";
                break;
            case 8:
                objArr[0] = "builder";
                break;
            case 9:
                objArr[0] = "ints1";
                break;
            case 10:
                objArr[0] = "ints2";
                break;
            case 11:
            case 13:
            case 15:
            case 17:
                objArr[0] = "o1";
                break;
            case 12:
            case 14:
            case 16:
            case 18:
                objArr[0] = "o2";
                break;
            case 19:
            default:
                objArr[0] = "before";
                break;
            case 21:
                objArr[0] = "lines";
                break;
            case 23:
                objArr[0] = "lines1";
                break;
            case 24:
                objArr[0] = "lines2";
                break;
        }
        if (i == 3) {
            objArr[1] = "splitLines";
        } else if (i != 22) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/diff/Diff";
        } else {
            objArr[1] = "trim";
        }
        if (i == 2) {
            objArr[2] = "splitLines";
        } else if (i != 3) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                    objArr[2] = "doBuildChanges";
                    break;
                case 11:
                case 12:
                case 15:
                case 16:
                    objArr[2] = "getStartShift";
                    break;
                case 13:
                case 14:
                case 17:
                case 18:
                    objArr[2] = "getEndCut";
                    break;
                case 19:
                case 20:
                    objArr[2] = "translateLine";
                    break;
                case 21:
                    objArr[2] = "trim";
                    break;
                case 22:
                    break;
                case 23:
                case 24:
                    objArr[2] = "linesDiff";
                    break;
                default:
                    objArr[2] = "buildChanges";
                    break;
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 22) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String[] splitLines(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        String[] strArr = charSequence.length() == 0 ? new String[]{""} : LineTokenizer.tokenize(charSequence, false, false);
        if (strArr == null) {
            $$$reportNull$$$0(3);
        }
        return strArr;
    }

    public static <T> Change buildChanges(T[] tArr, T[] tArr2) throws FilesTooBigForDiffException {
        if (tArr == null) {
            $$$reportNull$$$0(4);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(5);
        }
        int length = tArr.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
                break;
            }
            T t = tArr[i];
            Logger logger = LOG;
            if (t == null) {
                z = false;
            }
            logger.assertTrue(z);
            i++;
        }
        for (T t2 : tArr2) {
            LOG.assertTrue(t2 != null);
        }
        int startShift = getStartShift(tArr, tArr2);
        int endCut = getEndCut(tArr, tArr2, startShift);
        Ref<Change> refDoBuildChangesFast = doBuildChangesFast(tArr.length, tArr2.length, startShift, endCut);
        if (refDoBuildChangesFast != null) {
            return refDoBuildChangesFast.get();
        }
        Enumerator enumerator = new Enumerator(((tArr.length + tArr2.length) - (startShift * 2)) - (endCut * 2));
        return doBuildChanges(enumerator.enumerate(tArr, startShift, endCut), enumerator.enumerate(tArr2, startShift, endCut), new ChangeBuilder(startShift));
    }

    private static Ref<Change> doBuildChangesFast(int i, int i2, int i3, int i4) {
        int i5 = (i - i3) - i4;
        int i6 = (i2 - i3) - i4;
        if (i5 == 0 || i6 == 0) {
            return new Ref<>((i5 == 0 && i6 == 0) ? null : new Change(i3, i3, i5, i6, null));
        }
        return null;
    }

    private static Change doBuildChanges(int[] iArr, int[] iArr2, ChangeBuilder changeBuilder) throws FilesTooBigForDiffException {
        BitSet[] changes;
        if (changeBuilder == null) {
            $$$reportNull$$$0(8);
        }
        if (iArr == null) {
            $$$reportNull$$$0(9);
        }
        if (iArr2 == null) {
            $$$reportNull$$$0(10);
        }
        Reindexer reindexer = new Reindexer();
        int[][] iArrDiscardUnique = reindexer.discardUnique(iArr, iArr2);
        if (iArrDiscardUnique[0].length == 0 && iArrDiscardUnique[1].length == 0) {
            changeBuilder.addChange(iArr.length, iArr2.length);
            return changeBuilder.getFirstChange();
        }
        if (Registry.is("diff.patience.alg", false)) {
            PatienceIntLCS patienceIntLCS = new PatienceIntLCS(iArrDiscardUnique[0], iArrDiscardUnique[1]);
            patienceIntLCS.execute();
            changes = patienceIntLCS.getChanges();
        } else {
            try {
                MyersLCS myersLCS = new MyersLCS(iArrDiscardUnique[0], iArrDiscardUnique[1]);
                myersLCS.executeWithThreshold();
                changes = myersLCS.getChanges();
            } catch (FilesTooBigForDiffException unused) {
                PatienceIntLCS patienceIntLCS2 = new PatienceIntLCS(iArrDiscardUnique[0], iArrDiscardUnique[1]);
                patienceIntLCS2.execute(true);
                changes = patienceIntLCS2.getChanges();
                LOG.info("Successful fallback to patience diff");
            }
        }
        reindexer.reindex(changes, changeBuilder);
        return changeBuilder.getFirstChange();
    }

    private static <T> int getStartShift(T[] tArr, T[] tArr2) {
        if (tArr == null) {
            $$$reportNull$$$0(11);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(12);
        }
        int iMin = Math.min(tArr.length, tArr2.length);
        int i = 0;
        for (int i2 = 0; i2 < iMin && tArr[i2].equals(tArr2[i2]); i2++) {
            i++;
        }
        return i;
    }

    private static <T> int getEndCut(T[] tArr, T[] tArr2, int i) {
        if (tArr == null) {
            $$$reportNull$$$0(13);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(14);
        }
        int iMin = Math.min(tArr.length, tArr2.length) - i;
        int i2 = 0;
        for (int i3 = 0; i3 < iMin && tArr[(tArr.length - i3) - 1].equals(tArr2[(tArr2.length - i3) - 1]); i3++) {
            i2++;
        }
        return i2;
    }

    public static int translateLine(Change change, int i) {
        return translateLine(change, i, false);
    }

    public static int translateLine(Change change, int i, boolean z) {
        int i2 = i;
        while (change != null && i >= change.line0) {
            if (i < change.line0 + change.deleted) {
                if (z) {
                    return change.line1;
                }
                return -1;
            }
            i2 += change.inserted - change.deleted;
            change = change.link;
        }
        return i2;
    }

    public static class Change {
        public final int deleted;
        public final int inserted;
        public final int line0;
        public final int line1;
        public Change link;

        public Change(int i, int i2, int i3, int i4, Change change) {
            this.line0 = i;
            this.line1 = i2;
            this.inserted = i4;
            this.deleted = i3;
            this.link = change;
        }

        public String toString() {
            return "change[inserted=" + this.inserted + ", deleted=" + this.deleted + ", line0=" + this.line0 + ", line1=" + this.line1 + "]";
        }
    }

    public static class ChangeBuilder implements LCSBuilder {
        private Change myFirstChange;
        private int myIndex1 = 0;
        private int myIndex2 = 0;
        private Change myLastChange;

        public ChangeBuilder(int i) {
            skip(i, i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.LCSBuilder
        public void addChange(int i, int i2) {
            Change change = new Change(this.myIndex1, this.myIndex2, i, i2, null);
            Change change2 = this.myLastChange;
            if (change2 != null) {
                change2.link = change;
            } else {
                this.myFirstChange = change;
            }
            this.myLastChange = change;
            skip(i, i2);
        }

        private void skip(int i, int i2) {
            this.myIndex1 += i;
            this.myIndex2 += i2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.LCSBuilder
        public void addEqual(int i) {
            skip(i, i);
        }

        public Change getFirstChange() {
            return this.myFirstChange;
        }
    }
}
