package org.apache.xerces.util;

/* JADX INFO: loaded from: classes13.dex */
public final class SecurityManager {
    private static final int DEFAULT_ENTITY_EXPANSION_LIMIT = 100000;
    private static final int DEFAULT_MAX_OCCUR_NODE_LIMIT = 3000;
    private int entityExpansionLimit = 100000;
    private int maxOccurLimit = 3000;

    public int getEntityExpansionLimit() {
        return this.entityExpansionLimit;
    }

    public int getMaxOccurNodeLimit() {
        return this.maxOccurLimit;
    }

    public void setEntityExpansionLimit(int i) {
        this.entityExpansionLimit = i;
    }

    public void setMaxOccurNodeLimit(int i) {
        this.maxOccurLimit = i;
    }
}
