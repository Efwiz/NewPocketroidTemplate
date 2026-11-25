package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;

/* JADX INFO: loaded from: classes13.dex */
public interface UploadPackFactory<C> {
    public static final UploadPackFactory<?> DISABLED = new UploadPackFactory() { // from class: org.eclipse.jgit.transport.resolver.UploadPackFactory$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.resolver.UploadPackFactory
        public final UploadPack create(Object obj, Repository repository) {
            return UploadPackFactory.lambda$0(obj, repository);
        }
    };

    UploadPack create(C c, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException;

    static /* synthetic */ UploadPack lambda$0(Object obj, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException {
        throw new ServiceNotEnabledException();
    }
}
