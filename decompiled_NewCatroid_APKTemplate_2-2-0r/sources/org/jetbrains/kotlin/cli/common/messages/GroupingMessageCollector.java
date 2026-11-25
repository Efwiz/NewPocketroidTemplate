package org.jetbrains.kotlin.cli.common.messages;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;

/* JADX INFO: loaded from: classes3.dex */
public class GroupingMessageCollector implements MessageCollector {
    private final MessageCollector delegate;
    private final Multimap<CompilerMessageSourceLocation, Message> groupedMessages;
    private final boolean treatWarningsAsErrors;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "severity";
        } else if (i != 2) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        }
        objArr[1] = "org/jetbrains/kotlin/cli/common/messages/GroupingMessageCollector";
        if (i == 1 || i == 2) {
            objArr[2] = "report";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public GroupingMessageCollector(MessageCollector messageCollector, boolean z) {
        if (messageCollector == null) {
            $$$reportNull$$$0(0);
        }
        this.groupedMessages = LinkedHashMultimap.create();
        this.delegate = messageCollector;
        this.treatWarningsAsErrors = z;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.groupedMessages.clear();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageSourceLocation compilerMessageSourceLocation) {
        if (compilerMessageSeverity == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (compilerMessageSeverity == CompilerMessageSeverity.OUTPUT || CompilerMessageSeverity.VERBOSE.contains(compilerMessageSeverity)) {
            this.delegate.report(compilerMessageSeverity, str, compilerMessageSourceLocation);
        } else {
            this.groupedMessages.put(compilerMessageSourceLocation, new Message(compilerMessageSeverity, str, compilerMessageSourceLocation));
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public boolean hasErrors() {
        return hasExplicitErrors() || (this.treatWarningsAsErrors && hasWarnings());
    }

    private boolean hasExplicitErrors() {
        return this.groupedMessages.entries().stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((GroupingMessageCollector.Message) ((Map.Entry) obj).getValue()).severity.isError();
            }
        });
    }

    private boolean hasWarnings() {
        return this.groupedMessages.entries().stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((GroupingMessageCollector.Message) ((Map.Entry) obj).getValue()).severity.isWarning();
            }
        });
    }

    public void flush() {
        boolean zHasExplicitErrors = hasExplicitErrors();
        if (this.treatWarningsAsErrors && !zHasExplicitErrors && hasWarnings()) {
            report(CompilerMessageSeverity.ERROR, "warnings found and -Werror specified", null);
        }
        Iterator it = CollectionsKt.sortedWith(this.groupedMessages.keySet(), Comparator.nullsFirst(CompilerMessageLocationComparator.INSTANCE)).iterator();
        while (it.hasNext()) {
            for (Message message : this.groupedMessages.get((CompilerMessageSourceLocation) it.next())) {
                if (!zHasExplicitErrors || message.severity.isError() || message.severity == CompilerMessageSeverity.STRONG_WARNING) {
                    this.delegate.report(message.severity, message.message, message.location);
                }
            }
        }
        this.groupedMessages.clear();
    }

    private static class CompilerMessageLocationComparator implements Comparator<CompilerMessageSourceLocation> {
        public static final CompilerMessageLocationComparator INSTANCE = new CompilerMessageLocationComparator();

        private CompilerMessageLocationComparator() {
        }

        @Override // java.util.Comparator
        public int compare(CompilerMessageSourceLocation compilerMessageSourceLocation, CompilerMessageSourceLocation compilerMessageSourceLocation2) {
            if (compilerMessageSourceLocation.getColumn() == -1 && compilerMessageSourceLocation2.getColumn() != -1) {
                return -1;
            }
            if (compilerMessageSourceLocation.getColumn() != -1 && compilerMessageSourceLocation2.getColumn() == -1) {
                return 1;
            }
            if (compilerMessageSourceLocation.getLine() == -1 && compilerMessageSourceLocation2.getLine() != -1) {
                return -1;
            }
            if (compilerMessageSourceLocation.getLine() == -1 || compilerMessageSourceLocation2.getLine() != -1) {
                return compilerMessageSourceLocation.getPath().compareTo(compilerMessageSourceLocation2.getPath());
            }
            return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Message {
        private final CompilerMessageSourceLocation location;
        private final String message;
        private final CompilerMessageSeverity severity;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "severity";
            } else {
                objArr[0] = CrashHianalyticsData.MESSAGE;
            }
            objArr[1] = "org/jetbrains/kotlin/cli/common/messages/GroupingMessageCollector$Message";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Message(CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageSourceLocation compilerMessageSourceLocation) {
            if (compilerMessageSeverity == null) {
                $$$reportNull$$$0(0);
            }
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            this.severity = compilerMessageSeverity;
            this.message = str;
            this.location = compilerMessageSourceLocation;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Message message = (Message) obj;
            return Objects.equals(this.location, message.location) && this.message.equals(message.message) && this.severity == message.severity;
        }

        public int hashCode() {
            int iHashCode = ((this.severity.hashCode() * 31) + this.message.hashCode()) * 31;
            CompilerMessageSourceLocation compilerMessageSourceLocation = this.location;
            return iHashCode + (compilerMessageSourceLocation != null ? compilerMessageSourceLocation.hashCode() : 0);
        }

        public String toString() {
            return "[" + this.severity + "] " + this.message + (this.location != null ? " (at " + this.location + Constant.AFTER_QUTO : " (no location)");
        }
    }
}
