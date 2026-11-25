package org.xmlunit.diff;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public final class DifferenceEvaluators {
    private static final Short CDATA = 4;
    private static final Short TEXT = 3;
    public static final DifferenceEvaluator Accept = new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.1
        @Override // org.xmlunit.diff.DifferenceEvaluator
        public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
            return comparisonResult;
        }
    };
    public static final DifferenceEvaluator Default = new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.2
        @Override // org.xmlunit.diff.DifferenceEvaluator
        public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
            if (comparisonResult != ComparisonResult.DIFFERENT) {
                return comparisonResult;
            }
            switch (AnonymousClass8.$SwitchMap$org$xmlunit$diff$ComparisonType[comparison.getType().ordinal()]) {
                case 1:
                    Short sh = (Short) comparison.getControlDetails().getValue();
                    Short sh2 = (Short) comparison.getTestDetails().getValue();
                    return ((sh.equals(DifferenceEvaluators.TEXT) && sh2.equals(DifferenceEvaluators.CDATA)) || (sh.equals(DifferenceEvaluators.CDATA) && sh2.equals(DifferenceEvaluators.TEXT))) ? ComparisonResult.SIMILAR : comparisonResult;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return ComparisonResult.SIMILAR;
                default:
                    return comparisonResult;
            }
        }
    };

    /* JADX INFO: renamed from: org.xmlunit.diff.DifferenceEvaluators$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$org$xmlunit$diff$ComparisonType;

        static {
            int[] iArr = new int[ComparisonType.values().length];
            $SwitchMap$org$xmlunit$diff$ComparisonType = iArr;
            try {
                iArr[ComparisonType.NODE_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.HAS_DOCTYPE_DECLARATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.DOCTYPE_SYSTEM_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.SCHEMA_LOCATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.NO_NAMESPACE_SCHEMA_LOCATION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.NAMESPACE_PREFIX.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.ATTR_VALUE_EXPLICITLY_SPECIFIED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.CHILD_NODELIST_SEQUENCE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$xmlunit$diff$ComparisonType[ComparisonType.XML_ENCODING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private DifferenceEvaluators() {
    }

    public static DifferenceEvaluator first(final DifferenceEvaluator... differenceEvaluatorArr) {
        return new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.3
            @Override // org.xmlunit.diff.DifferenceEvaluator
            public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
                for (DifferenceEvaluator differenceEvaluator : differenceEvaluatorArr) {
                    ComparisonResult comparisonResultEvaluate = differenceEvaluator.evaluate(comparison, comparisonResult);
                    if (comparisonResultEvaluate != comparisonResult) {
                        return comparisonResultEvaluate;
                    }
                }
                return comparisonResult;
            }
        };
    }

    public static DifferenceEvaluator chain(final DifferenceEvaluator... differenceEvaluatorArr) {
        return new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.4
            @Override // org.xmlunit.diff.DifferenceEvaluator
            public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
                for (DifferenceEvaluator differenceEvaluator : differenceEvaluatorArr) {
                    comparisonResult = differenceEvaluator.evaluate(comparison, comparisonResult);
                }
                return comparisonResult;
            }
        };
    }

    public static DifferenceEvaluator downgradeDifferencesToEqual(ComparisonType... comparisonTypeArr) {
        return recordDifferencesAs(ComparisonResult.EQUAL, comparisonTypeArr);
    }

    public static DifferenceEvaluator downgradeDifferencesToSimilar(ComparisonType... comparisonTypeArr) {
        return recordDifferencesAs(ComparisonResult.SIMILAR, comparisonTypeArr);
    }

    public static DifferenceEvaluator upgradeDifferencesToDifferent(ComparisonType... comparisonTypeArr) {
        return recordDifferencesAs(ComparisonResult.DIFFERENT, comparisonTypeArr);
    }

    public static DifferenceEvaluator ignorePrologDifferences() {
        return new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.5
            @Override // org.xmlunit.diff.DifferenceEvaluator
            public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
                return (DifferenceEvaluators.belongsToProlog(comparison, true) || DifferenceEvaluators.isSequenceOfRootElement(comparison)) ? ComparisonResult.EQUAL : comparisonResult;
            }
        };
    }

    public static DifferenceEvaluator ignorePrologDifferencesExceptDoctype() {
        return new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.6
            @Override // org.xmlunit.diff.DifferenceEvaluator
            public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
                return (DifferenceEvaluators.belongsToProlog(comparison, false) || DifferenceEvaluators.isSequenceOfRootElement(comparison)) ? ComparisonResult.EQUAL : comparisonResult;
            }
        };
    }

    private static DifferenceEvaluator recordDifferencesAs(final ComparisonResult comparisonResult, ComparisonType... comparisonTypeArr) {
        final EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) Arrays.asList(comparisonTypeArr));
        return new DifferenceEvaluator() { // from class: org.xmlunit.diff.DifferenceEvaluators.7
            @Override // org.xmlunit.diff.DifferenceEvaluator
            public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult2) {
                return (comparisonResult2 == ComparisonResult.EQUAL || !enumSetCopyOf.contains(comparison.getType())) ? comparisonResult2 : comparisonResult;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean belongsToProlog(Comparison comparison, boolean z) {
        if (comparison.getType().isDoctypeComparison()) {
            return z;
        }
        return belongsToProlog(comparison.getControlDetails().getTarget(), z) || belongsToProlog(comparison.getTestDetails().getTarget(), z);
    }

    private static boolean belongsToProlog(Node node, boolean z) {
        if (node == null || (node instanceof Element)) {
            return false;
        }
        if (!z && (node instanceof DocumentType)) {
            return false;
        }
        if (node instanceof Document) {
            return true;
        }
        return belongsToProlog(node.getParentNode(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSequenceOfRootElement(Comparison comparison) {
        return comparison.getType() == ComparisonType.CHILD_NODELIST_SEQUENCE && (comparison.getControlDetails().getTarget() instanceof Element) && (comparison.getControlDetails().getTarget().getParentNode() instanceof Document);
    }
}
