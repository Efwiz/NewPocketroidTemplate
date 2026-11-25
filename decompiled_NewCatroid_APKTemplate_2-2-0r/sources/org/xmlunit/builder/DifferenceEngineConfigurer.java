package org.xmlunit.builder;

import java.util.Map;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.builder.DifferenceEngineConfigurer;
import org.xmlunit.diff.ComparisonController;
import org.xmlunit.diff.ComparisonFormatter;
import org.xmlunit.diff.ComparisonListener;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.NodeMatcher;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public interface DifferenceEngineConfigurer<D extends DifferenceEngineConfigurer<D>> {
    D withAttributeFilter(Predicate<Attr> predicate);

    D withComparisonController(ComparisonController comparisonController);

    D withComparisonFormatter(ComparisonFormatter comparisonFormatter);

    D withComparisonListeners(ComparisonListener... comparisonListenerArr);

    D withDifferenceEvaluator(DifferenceEvaluator differenceEvaluator);

    D withDifferenceListeners(ComparisonListener... comparisonListenerArr);

    D withNamespaceContext(Map<String, String> map);

    D withNodeFilter(Predicate<Node> predicate);

    D withNodeMatcher(NodeMatcher nodeMatcher);
}
