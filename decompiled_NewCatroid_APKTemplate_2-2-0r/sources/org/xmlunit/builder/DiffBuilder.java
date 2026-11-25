package org.xmlunit.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonController;
import org.xmlunit.diff.ComparisonControllers;
import org.xmlunit.diff.ComparisonFormatter;
import org.xmlunit.diff.ComparisonListener;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DOMDifferenceEngine;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.NodeMatcher;
import org.xmlunit.input.CommentLessSource;
import org.xmlunit.input.ElementContentWhitespaceStrippedSource;
import org.xmlunit.input.WhitespaceNormalizedSource;
import org.xmlunit.input.WhitespaceStrippedSource;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public class DiffBuilder implements DifferenceEngineConfigurer<DiffBuilder> {
    private Predicate<Attr> attributeFilter;
    private final Source controlSource;
    private DocumentBuilderFactory documentBuilderFactory;
    private ComparisonFormatter formatter;
    private boolean ignoreComments;
    private boolean ignoreECW;
    private boolean ignoreWhitespace;
    private Map<String, String> namespaceContext;
    private Predicate<Node> nodeFilter;
    private NodeMatcher nodeMatcher;
    private boolean normalizeWhitespace;
    private Source testSource;
    private static final ComparisonResult[] CHECK_FOR_SIMILAR = {ComparisonResult.DIFFERENT};
    private static final ComparisonResult[] CHECK_FOR_IDENTICAL = {ComparisonResult.SIMILAR, ComparisonResult.DIFFERENT};
    private ComparisonController comparisonController = ComparisonControllers.Default;
    private DifferenceEvaluator differenceEvaluator = DifferenceEvaluators.Default;
    private List<ComparisonListener> comparisonListeners = new ArrayList();
    private List<ComparisonListener> differenceListeners = new ArrayList();
    private ComparisonResult[] comparisonResultsToCheck = CHECK_FOR_IDENTICAL;
    private String ignoreCommentVersion = null;

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public /* bridge */ /* synthetic */ DifferenceEngineConfigurer withAttributeFilter(Predicate predicate) {
        return withAttributeFilter((Predicate<Attr>) predicate);
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public /* bridge */ /* synthetic */ DifferenceEngineConfigurer withNamespaceContext(Map map) {
        return withNamespaceContext((Map<String, String>) map);
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public /* bridge */ /* synthetic */ DifferenceEngineConfigurer withNodeFilter(Predicate predicate) {
        return withNodeFilter((Predicate<Node>) predicate);
    }

    private DiffBuilder(Source source) {
        this.controlSource = source;
    }

    public static DiffBuilder compare(Object obj) {
        return new DiffBuilder(getSource(obj));
    }

    public DiffBuilder withTest(Object obj) {
        this.testSource = getSource(obj);
        return this;
    }

    private static Source getSource(Object obj) {
        return Input.from(obj).build();
    }

    public DiffBuilder ignoreWhitespace() {
        this.ignoreWhitespace = true;
        return this;
    }

    public DiffBuilder normalizeWhitespace() {
        this.normalizeWhitespace = true;
        return this;
    }

    public DiffBuilder ignoreElementContentWhitespace() {
        this.ignoreECW = true;
        return this;
    }

    public DiffBuilder ignoreComments() {
        return ignoreCommentsUsingXSLTVersion(null);
    }

    public DiffBuilder ignoreCommentsUsingXSLTVersion(String str) {
        this.ignoreComments = true;
        this.ignoreCommentVersion = str;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withNodeMatcher(NodeMatcher nodeMatcher) {
        this.nodeMatcher = nodeMatcher;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withDifferenceEvaluator(DifferenceEvaluator differenceEvaluator) {
        this.differenceEvaluator = differenceEvaluator;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withComparisonController(ComparisonController comparisonController) {
        this.comparisonController = comparisonController;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withComparisonListeners(ComparisonListener... comparisonListenerArr) {
        this.comparisonListeners.addAll(Arrays.asList(comparisonListenerArr));
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withDifferenceListeners(ComparisonListener... comparisonListenerArr) {
        this.differenceListeners.addAll(Arrays.asList(comparisonListenerArr));
        return this;
    }

    public DiffBuilder checkForSimilar() {
        this.comparisonResultsToCheck = CHECK_FOR_SIMILAR;
        return this;
    }

    public DiffBuilder checkForIdentical() {
        this.comparisonResultsToCheck = CHECK_FOR_IDENTICAL;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withNamespaceContext(Map<String, String> map) {
        this.namespaceContext = map;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withAttributeFilter(Predicate<Attr> predicate) {
        this.attributeFilter = predicate;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withNodeFilter(Predicate<Node> predicate) {
        this.nodeFilter = predicate;
        return this;
    }

    @Override // org.xmlunit.builder.DifferenceEngineConfigurer
    public DiffBuilder withComparisonFormatter(ComparisonFormatter comparisonFormatter) {
        this.formatter = comparisonFormatter;
        return this;
    }

    public DiffBuilder withDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
        this.documentBuilderFactory = documentBuilderFactory;
        return this;
    }

    public Diff build() {
        DOMDifferenceEngine dOMDifferenceEngine = this.documentBuilderFactory != null ? new DOMDifferenceEngine(this.documentBuilderFactory) : new DOMDifferenceEngine();
        CollectResultsListener collectResultsListener = new CollectResultsListener(this.comparisonResultsToCheck);
        dOMDifferenceEngine.addDifferenceListener(collectResultsListener);
        NodeMatcher nodeMatcher = this.nodeMatcher;
        if (nodeMatcher != null) {
            dOMDifferenceEngine.setNodeMatcher(nodeMatcher);
        }
        dOMDifferenceEngine.setDifferenceEvaluator(this.differenceEvaluator);
        dOMDifferenceEngine.setComparisonController(this.comparisonController);
        Iterator<ComparisonListener> it = this.comparisonListeners.iterator();
        while (it.hasNext()) {
            dOMDifferenceEngine.addComparisonListener(it.next());
        }
        Iterator<ComparisonListener> it2 = this.differenceListeners.iterator();
        while (it2.hasNext()) {
            dOMDifferenceEngine.addDifferenceListener(it2.next());
        }
        Map<String, String> map = this.namespaceContext;
        if (map != null) {
            dOMDifferenceEngine.setNamespaceContext(map);
        }
        Predicate<Attr> predicate = this.attributeFilter;
        if (predicate != null) {
            dOMDifferenceEngine.setAttributeFilter(predicate);
        }
        Predicate<Node> predicate2 = this.nodeFilter;
        if (predicate2 != null) {
            dOMDifferenceEngine.setNodeFilter(predicate2);
        }
        dOMDifferenceEngine.compare(wrap(this.controlSource), wrap(this.testSource));
        if (this.formatter == null) {
            return new Diff(this.controlSource, this.testSource, collectResultsListener.getDifferences());
        }
        return new Diff(this.controlSource, this.testSource, this.formatter, collectResultsListener.getDifferences());
    }

    private Source wrap(Source source) {
        ElementContentWhitespaceStrippedSource elementContentWhitespaceStrippedSource;
        CommentLessSource commentLessSource;
        WhitespaceNormalizedSource whitespaceNormalizedSource;
        WhitespaceStrippedSource whitespaceStrippedSource;
        if (this.ignoreWhitespace) {
            if (this.documentBuilderFactory != null) {
                whitespaceStrippedSource = new WhitespaceStrippedSource(source, this.documentBuilderFactory);
            } else {
                whitespaceStrippedSource = new WhitespaceStrippedSource(source);
            }
            source = whitespaceStrippedSource;
        }
        if (this.normalizeWhitespace) {
            if (this.documentBuilderFactory != null) {
                whitespaceNormalizedSource = new WhitespaceNormalizedSource(source, this.documentBuilderFactory);
            } else {
                whitespaceNormalizedSource = new WhitespaceNormalizedSource(source);
            }
            source = whitespaceNormalizedSource;
        }
        if (this.ignoreComments) {
            if (this.ignoreCommentVersion == null) {
                commentLessSource = new CommentLessSource(source);
            } else {
                commentLessSource = new CommentLessSource(source, this.ignoreCommentVersion);
            }
            source = commentLessSource;
        }
        if (!this.ignoreECW) {
            return source;
        }
        if (this.documentBuilderFactory != null) {
            elementContentWhitespaceStrippedSource = new ElementContentWhitespaceStrippedSource(source, this.documentBuilderFactory);
        } else {
            elementContentWhitespaceStrippedSource = new ElementContentWhitespaceStrippedSource(source);
        }
        return elementContentWhitespaceStrippedSource;
    }

    private static final class CollectResultsListener implements ComparisonListener {
        private final EnumSet<ComparisonResult> comparisonResultsToCheck;
        private final List<Difference> results = new ArrayList();

        public CollectResultsListener(ComparisonResult... comparisonResultArr) {
            this.comparisonResultsToCheck = EnumSet.copyOf((Collection) Arrays.asList(comparisonResultArr));
        }

        @Override // org.xmlunit.diff.ComparisonListener
        public void comparisonPerformed(Comparison comparison, ComparisonResult comparisonResult) {
            if (this.comparisonResultsToCheck.contains(comparisonResult)) {
                this.results.add(new Difference(comparison, comparisonResult));
            }
        }

        public List<Difference> getDifferences() {
            return Collections.unmodifiableList(this.results);
        }
    }
}
