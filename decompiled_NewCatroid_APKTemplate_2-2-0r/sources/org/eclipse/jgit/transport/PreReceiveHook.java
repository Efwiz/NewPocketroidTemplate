package org.eclipse.jgit.transport;

import java.util.Collection;

/* JADX INFO: loaded from: classes13.dex */
public interface PreReceiveHook {
    public static final PreReceiveHook NULL = new PreReceiveHook() { // from class: org.eclipse.jgit.transport.PreReceiveHook$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.PreReceiveHook
        public final void onPreReceive(ReceivePack receivePack, Collection collection) {
            PreReceiveHook.lambda$0(receivePack, collection);
        }
    };

    static /* synthetic */ void lambda$0(ReceivePack receivePack, Collection collection) {
    }

    void onPreReceive(ReceivePack receivePack, Collection<ReceiveCommand> collection);
}
