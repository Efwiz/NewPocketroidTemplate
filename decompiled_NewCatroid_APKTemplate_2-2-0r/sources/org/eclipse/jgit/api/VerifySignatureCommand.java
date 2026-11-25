package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.ServiceUnavailableException;
import org.eclipse.jgit.api.errors.WrongObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.GpgConfig;
import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.lib.GpgSignatureVerifierFactory;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class VerifySignatureCommand extends GitCommand<Map<String, VerificationResult>> {
    private GpgConfig config;
    private VerifyMode mode;
    private final Set<String> namesToCheck;
    private boolean ownVerifier;
    private GpgSignatureVerifier verifier;

    public enum VerifyMode {
        ANY,
        COMMITS,
        TAGS;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static VerifyMode[] valuesCustom() {
            VerifyMode[] verifyModeArrValuesCustom = values();
            int length = verifyModeArrValuesCustom.length;
            VerifyMode[] verifyModeArr = new VerifyMode[length];
            System.arraycopy(verifyModeArrValuesCustom, 0, verifyModeArr, 0, length);
            return verifyModeArr;
        }
    }

    public VerifySignatureCommand(Repository repository) {
        super(repository);
        this.namesToCheck = new HashSet();
        this.mode = VerifyMode.ANY;
    }

    public VerifySignatureCommand addName(String str) {
        checkCallable();
        this.namesToCheck.add(str);
        return this;
    }

    public VerifySignatureCommand addNames(String... strArr) {
        checkCallable();
        this.namesToCheck.addAll(Arrays.asList(strArr));
        return this;
    }

    public VerifySignatureCommand addNames(Collection<String> collection) {
        checkCallable();
        this.namesToCheck.addAll(collection);
        return this;
    }

    public VerifySignatureCommand setMode(VerifyMode verifyMode) {
        checkCallable();
        this.mode = verifyMode;
        return this;
    }

    public VerifySignatureCommand setVerifier(GpgSignatureVerifier gpgSignatureVerifier) {
        checkCallable();
        this.verifier = gpgSignatureVerifier;
        return this;
    }

    public VerifySignatureCommand setGpgConfig(GpgConfig gpgConfig) {
        checkCallable();
        this.config = gpgConfig;
        return this;
    }

    public GpgSignatureVerifier getVerifier() {
        return this.verifier;
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public Map<String, VerificationResult> call() throws WrongObjectTypeException, ServiceUnavailableException {
        checkCallable();
        setCallable(false);
        HashMap map = new HashMap();
        if (this.verifier == null) {
            GpgSignatureVerifierFactory gpgSignatureVerifierFactory = GpgSignatureVerifierFactory.getDefault();
            if (gpgSignatureVerifierFactory == null) {
                throw new ServiceUnavailableException(JGitText.get().signatureVerificationUnavailable);
            }
            this.verifier = gpgSignatureVerifierFactory.getVerifier();
            this.ownVerifier = true;
        }
        if (this.config == null) {
            this.config = new GpgConfig(this.repo.getConfig());
        }
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this.repo);
            try {
                for (String str : this.namesToCheck) {
                    ObjectId objectIdResolve = this.repo.resolve(str);
                    if (objectIdResolve != null && !ObjectId.zeroId().equals((AnyObjectId) objectIdResolve)) {
                        try {
                            VerificationResult verificationResultVerifyOne = verifyOne(revWalk.parseAny(objectIdResolve));
                            if (verificationResultVerifyOne != null) {
                                map.put(str, verificationResultVerifyOne);
                            } else {
                                continue;
                            }
                        } catch (MissingObjectException unused) {
                            continue;
                        }
                    }
                }
                revWalk.close();
                if (this.ownVerifier) {
                    this.verifier.clear();
                }
                return map;
            } catch (Throwable th2) {
                revWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            try {
                if (0 == 0) {
                    throw th3;
                }
                if (null != th3) {
                    try {
                        th.addSuppressed(th3);
                    } catch (IOException e) {
                        throw new JGitInternalException(JGitText.get().signatureVerificationError, e);
                    }
                }
                throw null;
            } catch (Throwable th4) {
                if (this.ownVerifier) {
                    this.verifier.clear();
                }
                throw th4;
            }
        }
    }

    private VerificationResult verifyOne(RevObject revObject) throws WrongObjectTypeException, IOException {
        int type = revObject.getType();
        if (VerifyMode.TAGS.equals(this.mode) && type != 4) {
            throw new WrongObjectTypeException(revObject, 4);
        }
        if (VerifyMode.COMMITS.equals(this.mode) && type != 1) {
            throw new WrongObjectTypeException(revObject, 1);
        }
        if (type != 1 && type != 4) {
            return null;
        }
        try {
            GpgSignatureVerifier.SignatureVerification signatureVerificationVerifySignature = this.verifier.verifySignature(revObject, this.config);
            if (signatureVerificationVerifySignature == null) {
                return null;
            }
            return new Result(revObject, signatureVerificationVerifySignature, null);
        } catch (JGitInternalException e) {
            return new Result(revObject, null, e);
        }
    }

    private static class Result implements VerificationResult {
        private final RevObject object;
        private final Throwable throwable;
        private final GpgSignatureVerifier.SignatureVerification verification;

        public Result(RevObject revObject, GpgSignatureVerifier.SignatureVerification signatureVerification, Throwable th) {
            this.object = revObject;
            this.verification = signatureVerification;
            this.throwable = th;
        }

        @Override // org.eclipse.jgit.api.VerificationResult
        public Throwable getException() {
            return this.throwable;
        }

        @Override // org.eclipse.jgit.api.VerificationResult
        public GpgSignatureVerifier.SignatureVerification getVerification() {
            return this.verification;
        }

        @Override // org.eclipse.jgit.api.VerificationResult
        public RevObject getObject() {
            return this.object;
        }
    }
}
