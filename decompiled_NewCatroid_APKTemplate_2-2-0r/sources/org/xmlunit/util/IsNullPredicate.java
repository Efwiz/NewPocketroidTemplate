package org.xmlunit.util;

/* JADX INFO: loaded from: classes3.dex */
public class IsNullPredicate implements Predicate<Object> {
    @Override // org.xmlunit.util.Predicate
    public boolean test(Object obj) {
        return obj == null;
    }
}
