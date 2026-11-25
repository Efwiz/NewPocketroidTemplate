package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

/* JADX INFO: loaded from: classes13.dex */
public class RemoteAddCommand extends GitCommand<RemoteConfig> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f635name;
    private URIish uri;

    protected RemoteAddCommand(Repository repository) {
        super(repository);
    }

    public RemoteAddCommand setName(String str) {
        this.f635name = str;
        return this;
    }

    public RemoteAddCommand setUri(URIish uRIish) {
        this.uri = uRIish;
        return this;
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public RemoteConfig call() throws GitAPIException {
        checkCallable();
        try {
            StoredConfig config = this.repo.getConfig();
            RemoteConfig remoteConfig = new RemoteConfig(config, this.f635name);
            remoteConfig.addFetchRefSpec(new RefSpec().setForceUpdate(true).setSourceDestination("refs/heads/*", Constants.R_REMOTES + this.f635name + RefSpec.WILDCARD_SUFFIX));
            remoteConfig.addURI(this.uri);
            remoteConfig.update(config);
            config.save();
            return remoteConfig;
        } catch (IOException | URISyntaxException e) {
            throw new JGitInternalException(e.getMessage(), e);
        }
    }
}
