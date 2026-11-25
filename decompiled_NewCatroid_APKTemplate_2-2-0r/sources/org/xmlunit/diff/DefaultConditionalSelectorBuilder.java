package org.xmlunit.diff;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.xmlunit.util.Predicate;

/* JADX INFO: loaded from: classes3.dex */
class DefaultConditionalSelectorBuilder implements ElementSelectors.ConditionalSelectorBuilder, ElementSelectors.ConditionalSelectorBuilderThen {
    private final Map<Predicate<? super Element>, ElementSelector> conditionalSelectors = new LinkedHashMap();
    private ElementSelector defaultSelector;
    private Predicate<? super Element> pendingCondition;

    DefaultConditionalSelectorBuilder() {
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilderThen
    public ElementSelectors.ConditionalSelectorBuilder thenUse(ElementSelector elementSelector) {
        Predicate<? super Element> predicate = this.pendingCondition;
        if (predicate == null) {
            throw new IllegalStateException("missing condition");
        }
        this.conditionalSelectors.put(predicate, elementSelector);
        this.pendingCondition = null;
        return this;
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilder
    public ElementSelectors.ConditionalSelectorBuilderThen when(Predicate<? super Element> predicate) {
        if (this.pendingCondition != null) {
            throw new IllegalStateException("unbalanced conditions");
        }
        this.pendingCondition = predicate;
        return this;
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilder
    public ElementSelectors.ConditionalSelectorBuilder elseUse(ElementSelector elementSelector) {
        if (this.defaultSelector != null) {
            throw new IllegalStateException("can't have more than one default selector");
        }
        this.defaultSelector = elementSelector;
        return this;
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilder
    public ElementSelectors.ConditionalSelectorBuilderThen whenElementIsNamed(String str) {
        return when(ElementSelectors.elementNamePredicate(str));
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilder
    public ElementSelectors.ConditionalSelectorBuilderThen whenElementIsNamed(QName qName) {
        return when(ElementSelectors.elementNamePredicate(qName));
    }

    @Override // org.xmlunit.diff.ElementSelectors.ConditionalSelectorBuilder
    public ElementSelector build() {
        if (this.pendingCondition != null) {
            throw new IllegalStateException("unbalanced conditions");
        }
        return new ConditionalSelector(this.conditionalSelectors, this.defaultSelector);
    }

    private static class ConditionalSelector implements ElementSelector {
        private final Map<Predicate<? super Element>, ElementSelector> conditionalSelectors;
        private final ElementSelector defaultSelector;

        private ConditionalSelector(Map<Predicate<? super Element>, ElementSelector> map, ElementSelector elementSelector) {
            this.conditionalSelectors = new LinkedHashMap(map);
            this.defaultSelector = elementSelector;
        }

        @Override // org.xmlunit.diff.ElementSelector
        public boolean canBeCompared(Element element, Element element2) {
            for (Map.Entry<Predicate<? super Element>, ElementSelector> entry : this.conditionalSelectors.entrySet()) {
                if (entry.getKey().test(element)) {
                    return entry.getValue().canBeCompared(element, element2);
                }
            }
            ElementSelector elementSelector = this.defaultSelector;
            if (elementSelector != null) {
                return elementSelector.canBeCompared(element, element2);
            }
            return false;
        }
    }
}
