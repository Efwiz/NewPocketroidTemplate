package com.google.firebase.crashlytics.internal.model;

/* JADX INFO: loaded from: classes10.dex */
final class AutoValue_CrashlyticsReport_Session_Event_Application_Execution_Signal extends CrashlyticsReport.Session.Event.Application.Execution.Signal {
    private final long address;
    private final String code;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f169name;

    private AutoValue_CrashlyticsReport_Session_Event_Application_Execution_Signal(String str, String str2, long j) {
        this.f169name = str;
        this.code = str2;
        this.address = j;
    }

    @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal
    public String getName() {
        return this.f169name;
    }

    @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal
    public String getCode() {
        return this.code;
    }

    @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal
    public long getAddress() {
        return this.address;
    }

    public String toString() {
        return "Signal{name=" + this.f169name + ", code=" + this.code + ", address=" + this.address + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CrashlyticsReport.Session.Event.Application.Execution.Signal)) {
            return false;
        }
        CrashlyticsReport.Session.Event.Application.Execution.Signal signal = (CrashlyticsReport.Session.Event.Application.Execution.Signal) obj;
        return this.f169name.equals(signal.getName()) && this.code.equals(signal.getCode()) && this.address == signal.getAddress();
    }

    public int hashCode() {
        int iHashCode = (((this.f169name.hashCode() ^ 1000003) * 1000003) ^ this.code.hashCode()) * 1000003;
        long j = this.address;
        return iHashCode ^ ((int) (j ^ (j >>> 32)));
    }

    static final class Builder extends CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder {
        private long address;
        private String code;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private String f170name;
        private byte set$0;

        Builder() {
        }

        @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder
        public CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder setName(String str) {
            if (str == null) {
                throw new NullPointerException("Null name");
            }
            this.f170name = str;
            return this;
        }

        @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder
        public CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder setCode(String str) {
            if (str == null) {
                throw new NullPointerException("Null code");
            }
            this.code = str;
            return this;
        }

        @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder
        public CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder setAddress(long j) {
            this.address = j;
            this.set$0 = (byte) (this.set$0 | 1);
            return this;
        }

        @Override // com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Signal.Builder
        public CrashlyticsReport.Session.Event.Application.Execution.Signal build() {
            if (this.set$0 != 1 || this.f170name == null || this.code == null) {
                StringBuilder sb = new StringBuilder();
                if (this.f170name == null) {
                    sb.append(" name");
                }
                if (this.code == null) {
                    sb.append(" code");
                }
                if ((1 & this.set$0) == 0) {
                    sb.append(" address");
                }
                throw new IllegalStateException("Missing required properties:" + ((Object) sb));
            }
            return new AutoValue_CrashlyticsReport_Session_Event_Application_Execution_Signal(this.f170name, this.code, this.address);
        }
    }
}
