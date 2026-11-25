package org.xmlunit.diff;

import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public class Comparison {
    private final Detail control;
    private final Detail test;
    private final ComparisonType type;

    public static class Detail {
        private final String parentXPath;
        private final Node target;
        private final Object value;
        private final String xpath;

        private Detail(Node node, String str, Object obj, String str2) {
            this.target = node;
            this.xpath = str;
            this.value = obj;
            this.parentXPath = str2;
        }

        public Node getTarget() {
            return this.target;
        }

        public String getXPath() {
            return this.xpath;
        }

        public Object getValue() {
            return this.value;
        }

        public String getParentXPath() {
            return this.parentXPath;
        }
    }

    public Comparison(ComparisonType comparisonType, Node node, String str, Object obj, String str2, Node node2, String str3, Object obj2, String str4) {
        this.type = comparisonType;
        this.control = new Detail(node, str, obj, str2);
        this.test = new Detail(node2, str3, obj2, str4);
    }

    public ComparisonType getType() {
        return this.type;
    }

    public Detail getControlDetails() {
        return this.control;
    }

    public Detail getTestDetails() {
        return this.test;
    }

    public String toString(ComparisonFormatter comparisonFormatter) {
        return comparisonFormatter.getDescription(this);
    }

    public String toString() {
        return toString(new DefaultComparisonFormatter());
    }
}
