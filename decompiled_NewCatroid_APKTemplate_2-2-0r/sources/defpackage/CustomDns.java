package defpackage;

import android.util.Log;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Dns;
import org.catrobat.catroid.content.GeminiManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:CustomDns.class
  classes5.dex
 */
/* JADX INFO: compiled from: CustomDns.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"LCustomDns;", "Lokhttp3/Dns;", "()V", "lookup", "", "Ljava/net/InetAddress;", "hostname", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomDns implements Dns {
    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Intrinsics.checkNotNullParameter(hostname, "hostname");
        try {
            String dns_server = GeminiManager.INSTANCE.getDns_server();
            if (dns_server != null && dns_server.length() != 0) {
                Log.d("CustomDns", "Resolving hostname using DNS server: " + GeminiManager.INSTANCE.getDns_server());
                InetAddress[] allByName = InetAddress.getAllByName(GeminiManager.INSTANCE.getDns_server());
                Intrinsics.checkNotNull(allByName);
                return ArraysKt.toList(allByName);
            }
            return Dns.SYSTEM.lookup(hostname);
        } catch (UnknownHostException e) {
            Log.e("CustomDns", "Error resolving DNS: " + e.getMessage());
            throw e;
        }
    }
}
