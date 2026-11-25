package org.eclipse.jgit.util;

import io.netty.util.internal.StringUtil;
import java.text.MessageFormat;
import java.util.Locale;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.lib.PersonIdent;

/* JADX INFO: loaded from: classes14.dex */
public final class SignatureUtils {
    private SignatureUtils() {
    }

    public static String toString(GpgSignatureVerifier.SignatureVerification signatureVerification, PersonIdent personIdent, GitDateFormatter gitDateFormatter) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(JGitText.get().verifySignatureMade, gitDateFormatter.formatDate(new PersonIdent(personIdent, signatureVerification.getCreationDate()))));
        sb.append('\n');
        sb.append(MessageFormat.format(JGitText.get().verifySignatureKey, signatureVerification.getKeyFingerprint().toUpperCase(Locale.ROOT)));
        sb.append('\n');
        if (!StringUtils.isEmptyOrNull(signatureVerification.getSigner())) {
            sb.append(MessageFormat.format(JGitText.get().verifySignatureIssuer, signatureVerification.getSigner()));
            sb.append('\n');
        }
        if (signatureVerification.getVerified()) {
            if (signatureVerification.isExpired()) {
                str = JGitText.get().verifySignatureExpired;
            } else {
                str = JGitText.get().verifySignatureGood;
            }
        } else {
            str = JGitText.get().verifySignatureBad;
        }
        sb.append(MessageFormat.format(str, signatureVerification.getKeyUser()));
        if (!GpgSignatureVerifier.TrustLevel.UNKNOWN.equals(signatureVerification.getTrustLevel())) {
            sb.append(String.valueOf(StringUtil.SPACE) + MessageFormat.format(JGitText.get().verifySignatureTrust, signatureVerification.getTrustLevel().name().toLowerCase(Locale.ROOT)));
        }
        sb.append('\n');
        String message = signatureVerification.getMessage();
        if (!StringUtils.isEmptyOrNull(message)) {
            sb.append(message);
            sb.append('\n');
        }
        return sb.toString();
    }
}
