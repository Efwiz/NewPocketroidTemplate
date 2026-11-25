package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
final class FetchV0Request extends FetchRequest {
    FetchV0Request(Set<ObjectId> set, int i, Set<ObjectId> set2, FilterSpec filterSpec, Set<String> set3, @Nullable String str) {
        super(set, i, set2, filterSpec, set3, 0, Collections.emptyList(), str);
    }

    static final class Builder {
        String agent;
        int depth;
        final Set<ObjectId> wantIds = new HashSet();
        final Set<ObjectId> clientShallowCommits = new HashSet();
        FilterSpec filterSpec = FilterSpec.NO_FILTER;
        final Set<String> clientCaps = new HashSet();

        Builder() {
        }

        Builder addWantId(ObjectId objectId) {
            this.wantIds.add(objectId);
            return this;
        }

        Builder setDepth(int i) {
            this.depth = i;
            return this;
        }

        Builder addClientShallowCommit(ObjectId objectId) {
            this.clientShallowCommits.add(objectId);
            return this;
        }

        Builder addClientCapabilities(Collection<String> collection) {
            this.clientCaps.addAll(collection);
            return this;
        }

        Builder setAgent(String str) {
            this.agent = str;
            return this;
        }

        Builder setFilterSpec(FilterSpec filterSpec) {
            this.filterSpec = (FilterSpec) Objects.requireNonNull(filterSpec);
            return this;
        }

        FetchV0Request build() {
            return new FetchV0Request(this.wantIds, this.depth, this.clientShallowCommits, this.filterSpec, this.clientCaps, this.agent);
        }
    }
}
