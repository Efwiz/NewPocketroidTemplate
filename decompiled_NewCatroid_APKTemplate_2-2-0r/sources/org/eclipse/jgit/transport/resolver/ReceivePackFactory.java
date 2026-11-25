package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

/* JADX INFO: loaded from: classes13.dex */
public interface ReceivePackFactory<C> {
    public static final ReceivePackFactory<?> DISABLED = new ReceivePackFactory() { // from class: org.eclipse.jgit.transport.resolver.ReceivePackFactory$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.resolver.ReceivePackFactory
        public final ReceivePack create(Object obj, Repository repository) {
            return ReceivePackFactory.lambda$0(obj, repository);
        }
    };

    ReceivePack create(C c, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException;

    static /* synthetic */ ReceivePack lambda$0(Object obj, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException {
        throw new ServiceNotEnabledException();
    }
}
