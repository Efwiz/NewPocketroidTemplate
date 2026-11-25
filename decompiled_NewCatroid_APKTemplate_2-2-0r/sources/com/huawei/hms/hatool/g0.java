package com.huawei.hms.hatool;

import com.beust.jcommander.Parameters;
import java.util.Calendar;
import java.util.UUID;

/* JADX INFO: loaded from: classes10.dex */
public class g0 {
    public long a = 1800000;
    public volatile boolean b = false;
    public a c = null;

    public class a {
        public String a = UUID.randomUUID().toString().replace(Parameters.DEFAULT_OPTION_PREFIXES, "");
        public boolean b;
        public long c;

        public a(long j) {
            this.a += "_" + j;
            this.c = j;
            this.b = true;
            g0.this.b = false;
        }

        public void a(long j) {
            if (g0.this.b) {
                g0.this.b = false;
                b(j);
            } else if (b(this.c, j) || a(this.c, j)) {
                b(j);
            } else {
                this.c = j;
                this.b = false;
            }
        }

        public final boolean a(long j, long j2) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(j2);
            return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
        }

        public final void b(long j) {
            z.c("hmsSdk", "getNewSession() session is flush!");
            String string = UUID.randomUUID().toString();
            this.a = string;
            this.a = string.replace(Parameters.DEFAULT_OPTION_PREFIXES, "");
            this.a += "_" + j;
            this.c = j;
            this.b = true;
        }

        public final boolean b(long j, long j2) {
            return j2 - j >= g0.this.a;
        }
    }

    public String a() {
        a aVar = this.c;
        if (aVar != null) {
            return aVar.a;
        }
        z.f("hmsSdk", "getSessionName(): session not prepared. onEvent() must be called first.");
        return "";
    }

    public void a(long j) {
        a aVar = this.c;
        if (aVar != null) {
            aVar.a(j);
        } else {
            z.c("hmsSdk", "Session is first flush");
            this.c = new a(j);
        }
    }

    public boolean b() {
        a aVar = this.c;
        if (aVar != null) {
            return aVar.b;
        }
        z.f("hmsSdk", "isFirstEvent(): session not prepared. onEvent() must be called first.");
        return false;
    }
}
