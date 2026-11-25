package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;

/* JADX INFO: loaded from: classes13.dex */
public interface RepositoryResolver<C> {
    public static final RepositoryResolver<?> NONE = new RepositoryResolver() { // from class: org.eclipse.jgit.transport.resolver.RepositoryResolver$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.resolver.RepositoryResolver
        public final Repository open(Object obj, String str) {
            return RepositoryResolver.lambda$0(obj, str);
        }
    };

    Repository open(C c, String str) throws ServiceNotEnabledException, ServiceMayNotContinueException, RepositoryNotFoundException, ServiceNotAuthorizedException;

    static /* synthetic */ Repository lambda$0(Object obj, String str) throws ServiceNotEnabledException, ServiceMayNotContinueException, RepositoryNotFoundException, ServiceNotAuthorizedException {
        throw new RepositoryNotFoundException(str);
    }
}
