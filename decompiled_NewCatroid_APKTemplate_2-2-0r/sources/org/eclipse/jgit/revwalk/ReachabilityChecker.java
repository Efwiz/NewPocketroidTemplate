package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes13.dex */
public interface ReachabilityChecker {
    Optional<RevCommit> areAllReachable(Collection<RevCommit> collection, Stream<RevCommit> stream) throws IOException;

    @Deprecated
    default Optional<RevCommit> areAllReachable(Collection<RevCommit> collection, Collection<RevCommit> collection2) throws IOException {
        return areAllReachable(collection, collection2.stream());
    }
}
