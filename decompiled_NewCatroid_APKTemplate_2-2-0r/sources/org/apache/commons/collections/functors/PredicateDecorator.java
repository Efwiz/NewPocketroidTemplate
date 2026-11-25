package org.apache.commons.collections.functors;

import org.apache.commons.collections.Predicate;

/* JADX INFO: loaded from: classes12.dex */
public interface PredicateDecorator extends Predicate {
    Predicate[] getPredicates();
}
