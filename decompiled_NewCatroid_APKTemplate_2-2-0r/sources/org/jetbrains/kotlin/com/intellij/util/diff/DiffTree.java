package org.jetbrains.kotlin.com.intellij.util.diff;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class DiffTree<OT, NT> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final DiffTreeChangeBuilder<?, ?> EMPTY_CONSUMER = new DiffTreeChangeBuilder<Object, Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.diff.DiffTree.1
        /* JADX WARN: Code duplicated, block: B:11:0x0021  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "newChild";
            } else if (i == 2) {
                objArr[0] = "oldParent";
            } else if (i == 3) {
                objArr[0] = "oldNode";
            } else if (i == 4) {
                objArr[0] = "oldParent";
            } else if (i != 5) {
                objArr[0] = "oldChild";
            } else {
                objArr[0] = "newNode";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/diff/DiffTree$1";
            if (i == 2 || i == 3) {
                objArr[2] = "nodeDeleted";
            } else if (i == 4 || i == 5) {
                objArr[2] = "nodeInserted";
            } else {
                objArr[2] = "nodeReplaced";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeDeleted(Object obj, Object obj2) {
            if (obj == null) {
                $$$reportNull$$$0(2);
            }
            if (obj2 == null) {
                $$$reportNull$$$0(3);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeInserted(Object obj, Object obj2, int i) {
            if (obj == null) {
                $$$reportNull$$$0(4);
            }
            if (obj2 == null) {
                $$$reportNull$$$0(5);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
        public void nodeReplaced(Object obj, Object obj2) {
            if (obj == null) {
                $$$reportNull$$$0(0);
            }
            if (obj2 == null) {
                $$$reportNull$$$0(1);
            }
        }
    };
    private final ShallowNodeComparator<? super OT, ? super NT> myComparator;
    private final List<Ref<NT[]>> myNewChildrenLists;
    private final CharSequence myNewText;
    private final FlyweightCapableTreeStructure<NT> myNewTree;
    private final int myNewTreeStart;
    private final List<Ref<OT[]>> myOldChildrenLists;
    private final CharSequence myOldText;
    private final FlyweightCapableTreeStructure<OT> myOldTree;
    private final int myOldTreeStart;

    private enum CompareResult {
        EQUAL,
        DRILL_DOWN_NEEDED,
        TYPE_ONLY,
        NOT_EQUAL
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 9) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 9) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
                objArr[0] = "newTree";
                break;
            case 2:
            case 6:
                objArr[0] = "comparator";
                break;
            case 3:
            case 8:
                objArr[0] = "oldText";
                break;
            case 4:
            default:
                objArr[0] = "oldTree";
                break;
            case 7:
            case 12:
                objArr[0] = "consumer";
                break;
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/diff/DiffTree";
                break;
            case 10:
                objArr[0] = "oldNode";
                break;
            case 11:
                objArr[0] = "newNode";
                break;
        }
        if (i != 9) {
            switch (i) {
                case 13:
                    objArr[1] = JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
                    break;
                case 14:
                case 15:
                case 16:
                case 17:
                    objArr[1] = "looksEqual";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/diff/DiffTree";
                    break;
            }
        } else {
            objArr[1] = "emptyConsumer";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "diff";
                break;
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                break;
            case 10:
            case 11:
            case 12:
                objArr[2] = JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 9) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private DiffTree(FlyweightCapableTreeStructure<OT> flyweightCapableTreeStructure, FlyweightCapableTreeStructure<NT> flyweightCapableTreeStructure2, ShallowNodeComparator<? super OT, ? super NT> shallowNodeComparator, CharSequence charSequence) {
        if (flyweightCapableTreeStructure == null) {
            $$$reportNull$$$0(0);
        }
        if (flyweightCapableTreeStructure2 == null) {
            $$$reportNull$$$0(1);
        }
        if (shallowNodeComparator == null) {
            $$$reportNull$$$0(2);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        this.myOldChildrenLists = new ArrayList();
        this.myNewChildrenLists = new ArrayList();
        this.myOldTree = flyweightCapableTreeStructure;
        this.myNewTree = flyweightCapableTreeStructure2;
        this.myComparator = shallowNodeComparator;
        this.myOldText = charSequence;
        this.myOldTreeStart = flyweightCapableTreeStructure.getStartOffset(flyweightCapableTreeStructure.getRoot());
        this.myNewText = flyweightCapableTreeStructure2.toString(flyweightCapableTreeStructure2.getRoot());
        this.myNewTreeStart = flyweightCapableTreeStructure2.getStartOffset(flyweightCapableTreeStructure2.getRoot());
    }

    public static <OT, NT> void diff(FlyweightCapableTreeStructure<OT> flyweightCapableTreeStructure, FlyweightCapableTreeStructure<NT> flyweightCapableTreeStructure2, ShallowNodeComparator<? super OT, ? super NT> shallowNodeComparator, DiffTreeChangeBuilder<? super OT, ? super NT> diffTreeChangeBuilder, CharSequence charSequence) {
        if (flyweightCapableTreeStructure == null) {
            $$$reportNull$$$0(4);
        }
        if (flyweightCapableTreeStructure2 == null) {
            $$$reportNull$$$0(5);
        }
        if (shallowNodeComparator == null) {
            $$$reportNull$$$0(6);
        }
        if (diffTreeChangeBuilder == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        new DiffTree(flyweightCapableTreeStructure, flyweightCapableTreeStructure2, shallowNodeComparator, charSequence).build(flyweightCapableTreeStructure.getRoot(), flyweightCapableTreeStructure2.getRoot(), 0, diffTreeChangeBuilder);
    }

    private static <OT, NT> DiffTreeChangeBuilder<OT, NT> emptyConsumer() {
        DiffTreeChangeBuilder<OT, NT> diffTreeChangeBuilder = (DiffTreeChangeBuilder<OT, NT>) EMPTY_CONSUMER;
        if (diffTreeChangeBuilder == null) {
            $$$reportNull$$$0(9);
        }
        return diffTreeChangeBuilder;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private CompareResult build(OT ot, NT nt, int i, DiffTreeChangeBuilder<? super OT, ? super NT> diffTreeChangeBuilder) {
        NT[] ntArr;
        CompareResult compareResult;
        if (ot == null) {
            $$$reportNull$$$0(10);
        }
        if (nt == null) {
            $$$reportNull$$$0(11);
        }
        if (diffTreeChangeBuilder == null) {
            $$$reportNull$$$0(12);
        }
        if (i == this.myNewChildrenLists.size()) {
            this.myNewChildrenLists.add(new Ref<>());
            this.myOldChildrenLists.add(new Ref<>());
        }
        Ref<OT[]> ref = this.myOldChildrenLists.get(i);
        int children = this.myOldTree.getChildren(ot, ref);
        OT[] otArr = ref.get();
        Ref<NT[]> ref2 = this.myNewChildrenLists.get(i);
        int children2 = this.myNewTree.getChildren(nt, ref2);
        NT[] ntArr2 = ref2.get();
        if (Math.abs(children - children2) > 20) {
            diffTreeChangeBuilder.nodeReplaced(ot, nt);
            compareResult = CompareResult.NOT_EQUAL;
        } else if (children == 0 && children2 == 0) {
            if (!this.myComparator.typesEqual(ot, nt) || !this.myComparator.hashCodesEqual(ot, nt)) {
                diffTreeChangeBuilder.nodeReplaced(ot, nt);
                compareResult = CompareResult.NOT_EQUAL;
            } else {
                compareResult = CompareResult.EQUAL;
            }
        } else {
            int iMin = Math.min(children, children2);
            int iMatch = match(otArr, children - 1, ntArr2, children2 - 1, i, -1, iMin);
            int i2 = iMatch;
            int iMatch2 = match(otArr, 0, ntArr2, 0, i, 1, (iMin - iMatch) - ((children != children2 || iMatch >= iMin) ? 0 : 1));
            if (children == children2 && i2 + iMatch2 == children) {
                compareResult = CompareResult.EQUAL;
            } else if (diffTreeChangeBuilder == emptyConsumer()) {
                compareResult = CompareResult.NOT_EQUAL;
            } else {
                int i3 = iMatch2;
                int i4 = i3;
                while (true) {
                    int i5 = children - i2;
                    if (i4 >= i5 && i3 >= children2 - i2) {
                        break;
                    }
                    int i6 = children2 - i2;
                    int i7 = i3;
                    ThreeElementMatchResult threeElementMatchResultMatchNext3Children = matchNext3Children(otArr, ntArr2, i4, i3, i5, i6);
                    if (threeElementMatchResultMatchNext3Children.hasStartMatch()) {
                        if (threeElementMatchResultMatchNext3Children == ThreeElementMatchResult.DRILL_DOWN_START_MATCH) {
                            build(otArr[i4], ntArr2[i7], i + 1, diffTreeChangeBuilder);
                        } else if (threeElementMatchResultMatchNext3Children == ThreeElementMatchResult.REPLACE_START) {
                            diffTreeChangeBuilder.nodeReplaced((Object) otArr[i4], ntArr2[i7]);
                        }
                        i4++;
                        i3 = i7 + 1;
                    } else {
                        if (threeElementMatchResultMatchNext3Children != ThreeElementMatchResult.NO_MATCH) {
                            for (int iSkipOldCount = threeElementMatchResultMatchNext3Children.skipOldCount() - 1; iSkipOldCount >= 0; iSkipOldCount--) {
                                diffTreeChangeBuilder.nodeDeleted(ot, (Object) otArr[i4]);
                                i4++;
                            }
                            i3 = i7;
                            for (int iSkipNewCount = threeElementMatchResultMatchNext3Children.skipNewCount() - 1; iSkipNewCount >= 0; iSkipNewCount--) {
                                diffTreeChangeBuilder.nodeInserted(ot, ntArr2[i3], i3);
                                i3++;
                            }
                        } else {
                            int i8 = i4;
                            ntArr = ntArr2;
                            int iMatchLastChildren = matchLastChildren(i, diffTreeChangeBuilder, i5, otArr, i4, i6, ntArr2, i7);
                            if (iMatchLastChildren > 0) {
                                i2 += iMatchLastChildren;
                                i4 = i8;
                                i3 = i7;
                            } else {
                                diffTreeChangeBuilder.nodeReplaced((Object) otArr[i8], (Object) ntArr[i7]);
                                i4 = i8 + 1;
                                i3 = i7 + 1;
                            }
                        }
                        ntArr2 = ntArr;
                    }
                    ntArr = ntArr2;
                    ntArr2 = ntArr;
                }
                compareResult = CompareResult.NOT_EQUAL;
            }
        }
        this.myOldTree.disposeChildren(otArr, children);
        this.myNewTree.disposeChildren(ntArr2, children2);
        if (compareResult == null) {
            $$$reportNull$$$0(13);
        }
        return compareResult;
    }

    private ThreeElementMatchResult matchNext3Children(OT[] otArr, NT[] ntArr, int i, int i2, int i3, int i4) {
        if (i >= i3) {
            return ThreeElementMatchResult.SKIP_NEW_1;
        }
        if (i2 >= i4) {
            return ThreeElementMatchResult.SKIP_OLD_1;
        }
        OT ot = otArr[i];
        NT nt = ntArr[i2];
        CompareResult compareResultLooksEqual = looksEqual(ot, nt);
        if (compareResultLooksEqual == CompareResult.EQUAL) {
            return ThreeElementMatchResult.FULL_START_MATCH;
        }
        if (compareResultLooksEqual == CompareResult.DRILL_DOWN_NEEDED) {
            return ThreeElementMatchResult.DRILL_DOWN_START_MATCH;
        }
        OT ot2 = i < i3 + (-1) ? otArr[i + 1] : null;
        CompareResult compareResultLooksEqual2 = looksEqual(ot, i2 < i4 + (-1) ? ntArr[i2 + 1] : null);
        if (compareResultLooksEqual2 == CompareResult.EQUAL || compareResultLooksEqual2 == CompareResult.DRILL_DOWN_NEEDED) {
            return ThreeElementMatchResult.SKIP_NEW_1;
        }
        CompareResult compareResultLooksEqual3 = looksEqual(ot2, nt);
        if (compareResultLooksEqual3 == CompareResult.EQUAL || compareResultLooksEqual3 == CompareResult.DRILL_DOWN_NEEDED) {
            return ThreeElementMatchResult.SKIP_OLD_1;
        }
        if (compareResultLooksEqual == CompareResult.TYPE_ONLY) {
            return ThreeElementMatchResult.REPLACE_START;
        }
        if (compareResultLooksEqual2 == CompareResult.TYPE_ONLY) {
            return ThreeElementMatchResult.SKIP_NEW_1;
        }
        if (compareResultLooksEqual3 == CompareResult.TYPE_ONLY) {
            return ThreeElementMatchResult.SKIP_OLD_1;
        }
        OT ot3 = i < i3 + (-2) ? otArr[i + 2] : null;
        if (looksEqual(ot, i2 < i4 + (-2) ? ntArr[i2 + 2] : null) != CompareResult.NOT_EQUAL) {
            return ThreeElementMatchResult.SKIP_NEW_2;
        }
        return looksEqual(ot3, nt) != CompareResult.NOT_EQUAL ? ThreeElementMatchResult.SKIP_OLD_2 : ThreeElementMatchResult.NO_MATCH;
    }

    private enum ThreeElementMatchResult {
        FULL_START_MATCH,
        DRILL_DOWN_START_MATCH,
        REPLACE_START,
        SKIP_NEW_1,
        SKIP_NEW_2,
        SKIP_OLD_1,
        SKIP_OLD_2,
        NO_MATCH;

        int skipNewCount() {
            if (this == SKIP_NEW_1) {
                return 1;
            }
            return this == SKIP_NEW_2 ? 2 : 0;
        }

        int skipOldCount() {
            if (this == SKIP_OLD_1) {
                return 1;
            }
            return this == SKIP_OLD_2 ? 2 : 0;
        }

        boolean hasStartMatch() {
            return this == FULL_START_MATCH || this == DRILL_DOWN_START_MATCH || this == REPLACE_START;
        }
    }

    private int matchLastChildren(int i, DiffTreeChangeBuilder<? super OT, ? super NT> diffTreeChangeBuilder, int i2, OT[] otArr, int i3, int i4, NT[] ntArr, int i5) {
        int i6;
        OT ot;
        NT nt;
        CompareResult compareResultLooksEqual;
        int i7 = 0;
        while (true) {
            int i8 = i2 - i7;
            if (i3 >= i8 || i5 >= (i6 = i4 - i7) || (compareResultLooksEqual = looksEqual((ot = otArr[i8 - 1]), (nt = ntArr[i6 - 1]))) == CompareResult.NOT_EQUAL) {
                break;
            }
            if (compareResultLooksEqual == CompareResult.DRILL_DOWN_NEEDED) {
                build(ot, nt, i + 1, diffTreeChangeBuilder);
            } else if (compareResultLooksEqual == CompareResult.TYPE_ONLY) {
                diffTreeChangeBuilder.nodeReplaced(ot, nt);
            }
            i7++;
        }
        return i7;
    }

    private int match(OT[] otArr, int i, NT[] ntArr, int i2, int i3, int i4, int i5) {
        int i6 = 0;
        while (i6 != i5 * i4) {
            OT ot = otArr[i + i6];
            NT nt = ntArr[i2 + i6];
            CompareResult compareResultLooksEqual = looksEqual(ot, nt);
            if (compareResultLooksEqual == CompareResult.DRILL_DOWN_NEEDED) {
                compareResultLooksEqual = textMatch(ot, nt) ? build(ot, nt, i3 + 1, emptyConsumer()) : CompareResult.NOT_EQUAL;
            }
            if (compareResultLooksEqual != CompareResult.EQUAL) {
                break;
            }
            i6 += i4;
        }
        return i6 * i4;
    }

    private boolean textMatch(OT ot, NT nt) {
        return CharArrayUtil.regionMatches(this.myOldText, this.myOldTree.getStartOffset(ot) - this.myOldTreeStart, this.myOldTree.getEndOffset(ot) - this.myOldTreeStart, this.myNewText, this.myNewTree.getStartOffset(nt) - this.myNewTreeStart, this.myNewTree.getEndOffset(nt) - this.myNewTreeStart);
    }

    private CompareResult looksEqual(OT ot, NT nt) {
        if (ot == null || nt == null || !this.myComparator.typesEqual(ot, nt)) {
            CompareResult compareResult = CompareResult.NOT_EQUAL;
            if (compareResult == null) {
                $$$reportNull$$$0(14);
            }
            return compareResult;
        }
        ThreeState threeStateDeepEqual = this.myComparator.deepEqual(ot, nt);
        if (threeStateDeepEqual == ThreeState.YES) {
            CompareResult compareResult2 = CompareResult.EQUAL;
            if (compareResult2 == null) {
                $$$reportNull$$$0(15);
            }
            return compareResult2;
        }
        if (threeStateDeepEqual == ThreeState.UNSURE) {
            CompareResult compareResult3 = CompareResult.DRILL_DOWN_NEEDED;
            if (compareResult3 == null) {
                $$$reportNull$$$0(16);
            }
            return compareResult3;
        }
        CompareResult compareResult4 = CompareResult.TYPE_ONLY;
        if (compareResult4 == null) {
            $$$reportNull$$$0(17);
        }
        return compareResult4;
    }
}
