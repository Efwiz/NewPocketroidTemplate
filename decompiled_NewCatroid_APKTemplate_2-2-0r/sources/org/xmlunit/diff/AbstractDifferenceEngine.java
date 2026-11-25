package org.xmlunit.diff;

import java.util.Collections;
import java.util.Map;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractDifferenceEngine implements DifferenceEngine {
    private static final String LISTENER_MUST_NOT_BE_NULL = "listener must not be null";
    private static final String NOT_BE_NULL = " not be null";
    private final ComparisonListenerSupport listeners = new ComparisonListenerSupport();
    private NodeMatcher nodeMatcher = new DefaultNodeMatcher();
    private DifferenceEvaluator diffEvaluator = DifferenceEvaluators.Default;
    private ComparisonController comparisonController = ComparisonControllers.Default;
    private Map<String, String> prefix2uri = Collections.emptyMap();
    private Predicate<Attr> attributeFilter = new Predicate<Attr>() { // from class: org.xmlunit.diff.AbstractDifferenceEngine.1
        @Override // org.xmlunit.util.Predicate
        public boolean test(Attr attr) {
            return true;
        }
    };
    private Predicate<Node> nodeFilter = NodeFilters.Default;

    protected interface DeferredComparison {
        ComparisonState apply();
    }

    protected AbstractDifferenceEngine() {
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void addComparisonListener(ComparisonListener comparisonListener) {
        if (comparisonListener == null) {
            throw new IllegalArgumentException(LISTENER_MUST_NOT_BE_NULL);
        }
        this.listeners.addComparisonListener(comparisonListener);
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void addMatchListener(ComparisonListener comparisonListener) {
        if (comparisonListener == null) {
            throw new IllegalArgumentException(LISTENER_MUST_NOT_BE_NULL);
        }
        this.listeners.addMatchListener(comparisonListener);
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void addDifferenceListener(ComparisonListener comparisonListener) {
        if (comparisonListener == null) {
            throw new IllegalArgumentException(LISTENER_MUST_NOT_BE_NULL);
        }
        this.listeners.addDifferenceListener(comparisonListener);
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setNodeMatcher(NodeMatcher nodeMatcher) {
        if (nodeMatcher == null) {
            throw new IllegalArgumentException("node matcher must not be null");
        }
        this.nodeMatcher = nodeMatcher;
    }

    protected NodeMatcher getNodeMatcher() {
        return this.nodeMatcher;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setDifferenceEvaluator(DifferenceEvaluator differenceEvaluator) {
        if (differenceEvaluator == null) {
            throw new IllegalArgumentException("difference evaluator must not be null");
        }
        this.diffEvaluator = differenceEvaluator;
    }

    protected DifferenceEvaluator getDifferenceEvaluator() {
        return this.diffEvaluator;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setComparisonController(ComparisonController comparisonController) {
        if (comparisonController == null) {
            throw new IllegalArgumentException("comparison controller must not be null");
        }
        this.comparisonController = comparisonController;
    }

    protected ComparisonController getComparisonController() {
        return this.comparisonController;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setNamespaceContext(Map<String, String> map) {
        this.prefix2uri = Collections.unmodifiableMap(map);
    }

    protected Map<String, String> getNamespaceContext() {
        return this.prefix2uri;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setAttributeFilter(Predicate<Attr> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("attribute filter must not be null");
        }
        this.attributeFilter = predicate;
    }

    protected Predicate<Attr> getAttributeFilter() {
        return this.attributeFilter;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void setNodeFilter(Predicate<Node> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("node filter must not be null");
        }
        this.nodeFilter = predicate;
    }

    protected Predicate<Node> getNodeFilter() {
        return this.nodeFilter;
    }

    protected final ComparisonState compare(Comparison comparison) {
        boolean zEquals;
        Object value = comparison.getControlDetails().getValue();
        Object value2 = comparison.getTestDetails().getValue();
        if (value == null) {
            zEquals = value2 == null;
        } else {
            zEquals = value.equals(value2);
        }
        ComparisonResult comparisonResultEvaluate = getDifferenceEvaluator().evaluate(comparison, zEquals ? ComparisonResult.EQUAL : ComparisonResult.DIFFERENT);
        this.listeners.fireComparisonPerformed(comparison, comparisonResultEvaluate);
        if (comparisonResultEvaluate != ComparisonResult.EQUAL && getComparisonController().stopDiffing(new Difference(comparison, comparisonResultEvaluate))) {
            return new FinishedComparisonState(comparisonResultEvaluate);
        }
        return new OngoingComparisonState(comparisonResultEvaluate);
    }

    protected static String getXPath(XPathContext xPathContext) {
        if (xPathContext == null) {
            return null;
        }
        return xPathContext.getXPath();
    }

    protected static String getParentXPath(XPathContext xPathContext) {
        if (xPathContext == null) {
            return null;
        }
        return xPathContext.getParentXPath();
    }

    protected abstract class ComparisonState {
        private final boolean finished;
        private final ComparisonResult result;

        protected ComparisonState(boolean z, ComparisonResult comparisonResult) {
            this.finished = z;
            this.result = comparisonResult;
        }

        protected ComparisonState andThen(DeferredComparison deferredComparison) {
            return this.finished ? this : deferredComparison.apply();
        }

        protected ComparisonState andIfTrueThen(boolean z, DeferredComparison deferredComparison) {
            return z ? andThen(deferredComparison) : this;
        }

        protected ComparisonState andThen(final Comparison comparison) {
            return andThen(new DeferredComparison() { // from class: org.xmlunit.diff.AbstractDifferenceEngine.ComparisonState.1
                @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
                public ComparisonState apply() {
                    return AbstractDifferenceEngine.this.compare(comparison);
                }
            });
        }

        protected ComparisonState andIfTrueThen(boolean z, final Comparison comparison) {
            return andIfTrueThen(z, new DeferredComparison() { // from class: org.xmlunit.diff.AbstractDifferenceEngine.ComparisonState.2
                @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
                public ComparisonState apply() {
                    return AbstractDifferenceEngine.this.compare(comparison);
                }
            });
        }

        public String toString() {
            return getClass().getName() + ": current result is " + this.result;
        }

        public boolean equals(Object obj) {
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            ComparisonState comparisonState = (ComparisonState) obj;
            return this.finished == comparisonState.finished && this.result == comparisonState.result;
        }

        public int hashCode() {
            return (this.finished ? 7 : 1) * this.result.hashCode();
        }
    }

    protected final class FinishedComparisonState extends ComparisonState {
        protected FinishedComparisonState(ComparisonResult comparisonResult) {
            super(true, comparisonResult);
        }
    }

    protected final class OngoingComparisonState extends ComparisonState {
        protected OngoingComparisonState(ComparisonResult comparisonResult) {
            super(false, comparisonResult);
        }

        protected OngoingComparisonState(AbstractDifferenceEngine abstractDifferenceEngine) {
            this(ComparisonResult.EQUAL);
        }
    }
}
