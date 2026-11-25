package org.eclipse.jgit.lib;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.time.ProposedTimestamp;

/* JADX INFO: loaded from: classes13.dex */
public class PersonIdent implements Serializable {
    private static final long serialVersionUID = 1;
    private final String emailAddress;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f655name;
    private final int tzOffset;
    private final long when;

    public static TimeZone getTimeZone(int i) {
        StringBuilder sb = new StringBuilder(8);
        sb.append(TimeZones.GMT_ID);
        appendTimezone(sb, i);
        return TimeZone.getTimeZone(sb.toString());
    }

    public static void appendTimezone(StringBuilder sb, int i) {
        char c;
        if (i < 0) {
            i = -i;
            c = '-';
        } else {
            c = '+';
        }
        int i2 = i / 60;
        int i3 = i % 60;
        sb.append(c);
        if (i2 < 10) {
            sb.append(ExternalAnnotationProvider.NULLABLE);
        }
        sb.append(i2);
        if (i3 < 10) {
            sb.append(ExternalAnnotationProvider.NULLABLE);
        }
        sb.append(i3);
    }

    public static void appendSanitized(StringBuilder sb, String str) {
        int i = 0;
        while (i < str.length() && str.charAt(i) <= ' ') {
            i++;
        }
        int length = str.length();
        while (length > i && str.charAt(length - 1) <= ' ') {
            length--;
        }
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\n' && cCharAt != '<' && cCharAt != '>') {
                sb.append(cCharAt);
            }
            i++;
        }
    }

    public PersonIdent(Repository repository) {
        this((UserConfig) repository.getConfig().get(UserConfig.KEY));
    }

    public PersonIdent(PersonIdent personIdent) {
        this(personIdent.getName(), personIdent.getEmailAddress());
    }

    public PersonIdent(String str, String str2) {
        this(str, str2, SystemReader.getInstance().getCurrentTime());
    }

    public PersonIdent(String str, String str2, ProposedTimestamp proposedTimestamp) {
        this(str, str2, proposedTimestamp.millis());
    }

    public PersonIdent(PersonIdent personIdent, Date date, TimeZone timeZone) {
        this(personIdent.getName(), personIdent.getEmailAddress(), date, timeZone);
    }

    public PersonIdent(PersonIdent personIdent, Date date) {
        this(personIdent.getName(), personIdent.getEmailAddress(), date.getTime(), personIdent.tzOffset);
    }

    public PersonIdent(String str, String str2, Date date, TimeZone timeZone) {
        this(str, str2, date.getTime(), timeZone.getOffset(date.getTime()) / 60000);
    }

    public PersonIdent(PersonIdent personIdent, long j, int i) {
        this(personIdent.getName(), personIdent.getEmailAddress(), j, i);
    }

    private PersonIdent(String str, String str2, long j) {
        this(str, str2, j, SystemReader.getInstance().getTimezone(j));
    }

    private PersonIdent(UserConfig userConfig) {
        this(userConfig.getCommitterName(), userConfig.getCommitterEmail());
    }

    public PersonIdent(String str, String str2, long j, int i) {
        if (str == null) {
            throw new IllegalArgumentException(JGitText.get().personIdentNameNonNull);
        }
        if (str2 == null) {
            throw new IllegalArgumentException(JGitText.get().personIdentEmailNonNull);
        }
        this.f655name = str;
        this.emailAddress = str2;
        this.when = j;
        this.tzOffset = i;
    }

    public String getName() {
        return this.f655name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Date getWhen() {
        return new Date(this.when);
    }

    public TimeZone getTimeZone() {
        return getTimeZone(this.tzOffset);
    }

    public int getTimeZoneOffset() {
        return this.tzOffset;
    }

    public int hashCode() {
        return (getEmailAddress().hashCode() * 31) + ((int) (this.when / 1000));
    }

    public boolean equals(Object obj) {
        if (obj instanceof PersonIdent) {
            PersonIdent personIdent = (PersonIdent) obj;
            if (getName().equals(personIdent.getName()) && getEmailAddress().equals(personIdent.getEmailAddress()) && this.when / 1000 == personIdent.when / 1000) {
                return true;
            }
        }
        return false;
    }

    public String toExternalString() {
        StringBuilder sb = new StringBuilder();
        appendSanitized(sb, getName());
        sb.append(" <");
        appendSanitized(sb, getEmailAddress());
        sb.append("> ");
        sb.append(this.when / 1000);
        sb.append(StringUtil.SPACE);
        appendTimezone(sb, this.tzOffset);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PersonIdent[");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        simpleDateFormat.setTimeZone(getTimeZone());
        sb.append(getName());
        sb.append(", ");
        sb.append(getEmailAddress());
        sb.append(", ");
        sb.append(simpleDateFormat.format(Long.valueOf(this.when)));
        sb.append("]");
        return sb.toString();
    }
}
