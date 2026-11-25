package com.gaurav.avnc.vnc;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: UserCredential.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\u0006\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0010\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/gaurav/avnc/vnc/UserCredential;", "", "username", "", AmazonS3.Keys.PASSWORD, "(Ljava/lang/String;Ljava/lang/String;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class UserCredential {
    public final String password;
    public final String username;

    /* JADX WARN: Multi-variable type inference failed */
    public UserCredential() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ UserCredential copy$default(UserCredential userCredential, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = userCredential.username;
        }
        if ((i & 2) != 0) {
            str2 = userCredential.password;
        }
        return userCredential.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getUsername() {
        return this.username;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPassword() {
        return this.password;
    }

    public final UserCredential copy(String username, String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        return new UserCredential(username, password);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserCredential)) {
            return false;
        }
        UserCredential userCredential = (UserCredential) other;
        return Intrinsics.areEqual(this.username, userCredential.username) && Intrinsics.areEqual(this.password, userCredential.password);
    }

    public int hashCode() {
        return (this.username.hashCode() * 31) + this.password.hashCode();
    }

    public String toString() {
        return "UserCredential(username=" + this.username + ", password=" + this.password + Constant.AFTER_QUTO;
    }

    public UserCredential(String username, String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        this.username = username;
        this.password = password;
    }

    public /* synthetic */ UserCredential(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2);
    }
}
