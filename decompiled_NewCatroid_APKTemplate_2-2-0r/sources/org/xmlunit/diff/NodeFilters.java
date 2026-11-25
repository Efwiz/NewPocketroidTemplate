package org.xmlunit.diff;

import org.w3c.dom.Node;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public final class NodeFilters {
    public static final Predicate<Node> Default = new Predicate<Node>() { // from class: org.xmlunit.diff.NodeFilters.1
        @Override // org.xmlunit.util.Predicate
        public boolean test(Node node) {
            return node.getNodeType() != 10;
        }
    };
    public static final Predicate<Node> AcceptAll = new Predicate<Node>() { // from class: org.xmlunit.diff.NodeFilters.2
        @Override // org.xmlunit.util.Predicate
        public boolean test(Node node) {
            return true;
        }
    };

    public static Predicate<Node> satifiesAll(final Predicate<Node>... predicateArr) {
        return new Predicate<Node>() { // from class: org.xmlunit.diff.NodeFilters.3
            @Override // org.xmlunit.util.Predicate
            public boolean test(Node node) {
                for (Predicate predicate : predicateArr) {
                    if (!predicate.test(node)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static Predicate<Node> satifiesAny(final Predicate<Node>... predicateArr) {
        return new Predicate<Node>() { // from class: org.xmlunit.diff.NodeFilters.4
            @Override // org.xmlunit.util.Predicate
            public boolean test(Node node) {
                for (Predicate predicate : predicateArr) {
                    if (predicate.test(node)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private NodeFilters() {
    }
}
