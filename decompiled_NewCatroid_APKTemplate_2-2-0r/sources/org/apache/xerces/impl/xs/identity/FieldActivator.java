package org.apache.xerces.impl.xs.identity;

/* JADX INFO: loaded from: classes13.dex */
public interface FieldActivator {
    XPathMatcher activateField(Field field, int i);

    void endValueScopeFor(IdentityConstraint identityConstraint, int i);

    void startValueScopeFor(IdentityConstraint identityConstraint, int i);
}
