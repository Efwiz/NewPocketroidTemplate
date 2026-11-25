package org.eclipse.jgit.transport;

import java.util.Collection;

/* JADX INFO: loaded from: classes13.dex */
public interface PostReceiveHook {
    public static final PostReceiveHook NULL = new PostReceiveHook() { // from class: org.eclipse.jgit.transport.PostReceiveHook$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.PostReceiveHook
        public final void onPostReceive(ReceivePack receivePack, Collection collection) {
            PostReceiveHook.lambda$0(receivePack, collection);
        }
    };

    static /* synthetic */ void lambda$0(ReceivePack receivePack, Collection collection) {
    }

    void onPostReceive(ReceivePack receivePack, Collection<ReceiveCommand> collection);
}
