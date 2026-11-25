package org.xmlunit.diff;

import java.util.Map;
import javax.xml.transform.Source;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public interface DifferenceEngine {
    void addComparisonListener(ComparisonListener comparisonListener);

    void addDifferenceListener(ComparisonListener comparisonListener);

    void addMatchListener(ComparisonListener comparisonListener);

    void compare(Source source, Source source2);

    void setAttributeFilter(Predicate<Attr> predicate);

    void setComparisonController(ComparisonController comparisonController);

    void setDifferenceEvaluator(DifferenceEvaluator differenceEvaluator);

    void setNamespaceContext(Map<String, String> map);

    void setNodeFilter(Predicate<Node> predicate);

    void setNodeMatcher(NodeMatcher nodeMatcher);
}
