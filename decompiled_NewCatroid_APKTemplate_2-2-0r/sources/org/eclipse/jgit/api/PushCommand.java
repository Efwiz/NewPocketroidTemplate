package org.eclipse.jgit.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TooLargeObjectInPackException;
import org.eclipse.jgit.errors.TooLargePackException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefLeaseSpec;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.Transport;

/* JADX INFO: loaded from: classes13.dex */
public class PushCommand extends TransportCommand<PushCommand, Iterable<PushResult>> {
    private boolean atomic;
    private boolean dryRun;
    private boolean force;
    private ProgressMonitor monitor;
    private OutputStream out;
    private List<String> pushOptions;
    private String receivePack;
    private final Map<String, RefLeaseSpec> refLeaseSpecs;
    private final List<RefSpec> refSpecs;
    private String remote;
    private boolean thin;

    protected PushCommand(Repository repository) {
        super(repository);
        this.remote = "origin";
        this.monitor = NullProgressMonitor.INSTANCE;
        this.receivePack = RemoteConfig.DEFAULT_RECEIVE_PACK;
        this.thin = false;
        this.refSpecs = new ArrayList(3);
        this.refLeaseSpecs = new HashMap();
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public Iterable<PushResult> call() throws GitAPIException {
        Ref refExactRef;
        checkCallable();
        ArrayList arrayList = new ArrayList(3);
        try {
            if (this.refSpecs.isEmpty()) {
                this.refSpecs.addAll(new RemoteConfig(this.repo.getConfig(), getRemote()).getPushRefSpecs());
            }
            if (this.refSpecs.isEmpty() && (refExactRef = this.repo.exactRef("HEAD")) != null && refExactRef.isSymbolic()) {
                this.refSpecs.add(new RefSpec(refExactRef.getLeaf().getName()));
            }
            if (this.force) {
                for (int i = 0; i < this.refSpecs.size(); i++) {
                    List<RefSpec> list = this.refSpecs;
                    list.set(i, list.get(i).setForceUpdate(true));
                }
            }
            for (Transport transport : Transport.openAll(this.repo, this.remote, Transport.Operation.PUSH)) {
                transport.setPushThin(this.thin);
                transport.setPushAtomic(this.atomic);
                String str = this.receivePack;
                if (str != null) {
                    transport.setOptionReceivePack(str);
                }
                transport.setDryRun(this.dryRun);
                transport.setPushOptions(this.pushOptions);
                configure(transport);
                try {
                    try {
                        try {
                            try {
                                arrayList.add(transport.push(this.monitor, transport.findRemoteRefUpdatesFor(this.refSpecs, this.refLeaseSpecs), this.out));
                                transport.close();
                            } catch (TooLargePackException e) {
                                throw new org.eclipse.jgit.api.errors.TooLargePackException(e.getMessage(), e);
                            }
                        } catch (TransportException e2) {
                            throw new org.eclipse.jgit.api.errors.TransportException(e2.getMessage(), e2);
                        }
                    } catch (TooLargeObjectInPackException e3) {
                        throw new org.eclipse.jgit.api.errors.TooLargeObjectInPackException(e3.getMessage(), e3);
                    }
                } catch (Throwable th) {
                    transport.close();
                    throw th;
                }
            }
            return arrayList;
        } catch (URISyntaxException e4) {
            throw new InvalidRemoteException(MessageFormat.format(JGitText.get().invalidRemote, this.remote), e4);
        } catch (NotSupportedException e5) {
            throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfPushCommand, e5);
        } catch (TransportException e6) {
            throw new org.eclipse.jgit.api.errors.TransportException(e6.getMessage(), e6);
        } catch (IOException e7) {
            throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfPushCommand, e7);
        }
    }

    public PushCommand setRemote(String str) {
        checkCallable();
        this.remote = str;
        return this;
    }

    public String getRemote() {
        return this.remote;
    }

    public PushCommand setReceivePack(String str) {
        checkCallable();
        this.receivePack = str;
        return this;
    }

    public String getReceivePack() {
        return this.receivePack;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public ProgressMonitor getProgressMonitor() {
        return this.monitor;
    }

    public PushCommand setProgressMonitor(ProgressMonitor progressMonitor) {
        checkCallable();
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.monitor = progressMonitor;
        return this;
    }

    public List<RefLeaseSpec> getRefLeaseSpecs() {
        return new ArrayList(this.refLeaseSpecs.values());
    }

    public PushCommand setRefLeaseSpecs(RefLeaseSpec... refLeaseSpecArr) {
        return setRefLeaseSpecs(Arrays.asList(refLeaseSpecArr));
    }

    public PushCommand setRefLeaseSpecs(List<RefLeaseSpec> list) {
        checkCallable();
        this.refLeaseSpecs.clear();
        for (RefLeaseSpec refLeaseSpec : list) {
            this.refLeaseSpecs.put(refLeaseSpec.getRef(), refLeaseSpec);
        }
        return this;
    }

    public List<RefSpec> getRefSpecs() {
        return this.refSpecs;
    }

    public PushCommand setRefSpecs(RefSpec... refSpecArr) {
        checkCallable();
        this.refSpecs.clear();
        Collections.addAll(this.refSpecs, refSpecArr);
        return this;
    }

    public PushCommand setRefSpecs(List<RefSpec> list) {
        checkCallable();
        this.refSpecs.clear();
        this.refSpecs.addAll(list);
        return this;
    }

    public PushCommand setPushAll() {
        this.refSpecs.add(Transport.REFSPEC_PUSH_ALL);
        return this;
    }

    public PushCommand setPushTags() {
        this.refSpecs.add(Transport.REFSPEC_TAGS);
        return this;
    }

    public PushCommand add(Ref ref) {
        this.refSpecs.add(new RefSpec(ref.getLeaf().getName()));
        return this;
    }

    public PushCommand add(String str) {
        if (str.indexOf(58) >= 0) {
            this.refSpecs.add(new RefSpec(str));
        } else {
            try {
                Ref refFindRef = this.repo.findRef(str);
                if (refFindRef != null) {
                    add(refFindRef);
                }
            } catch (IOException e) {
                throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfPushCommand, e);
            }
        }
        return this;
    }

    public boolean isDryRun() {
        return this.dryRun;
    }

    public PushCommand setDryRun(boolean z) {
        checkCallable();
        this.dryRun = z;
        return this;
    }

    public boolean isThin() {
        return this.thin;
    }

    public PushCommand setThin(boolean z) {
        checkCallable();
        this.thin = z;
        return this;
    }

    public boolean isAtomic() {
        return this.atomic;
    }

    public PushCommand setAtomic(boolean z) {
        checkCallable();
        this.atomic = z;
        return this;
    }

    public boolean isForce() {
        return this.force;
    }

    public PushCommand setForce(boolean z) {
        checkCallable();
        this.force = z;
        return this;
    }

    public PushCommand setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
        return this;
    }

    public List<String> getPushOptions() {
        return this.pushOptions;
    }

    public PushCommand setPushOptions(List<String> list) {
        this.pushOptions = list;
        return this;
    }
}
