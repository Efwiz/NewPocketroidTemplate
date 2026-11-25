package org.eclipse.jgit.revwalk;

import java.util.Locale;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: loaded from: classes13.dex */
public final class FooterKey {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f659name;
    final byte[] raw;
    public static final FooterKey SIGNED_OFF_BY = new FooterKey("Signed-off-by");
    public static final FooterKey ACKED_BY = new FooterKey("Acked-by");
    public static final FooterKey CC = new FooterKey("CC");

    public FooterKey(String str) {
        this.f659name = str;
        this.raw = Constants.encode(str.toLowerCase(Locale.ROOT));
    }

    public String getName() {
        return this.f659name;
    }

    public String toString() {
        return "FooterKey[" + this.f659name + "]";
    }
}
