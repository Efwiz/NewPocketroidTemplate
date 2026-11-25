package org.eclipse.jgit.transport;

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.transport.parser.FirstWant;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
final class ProtocolV0Parser {
    private final TransferConfig transferConfig;

    ProtocolV0Parser(TransferConfig transferConfig) {
        this.transferConfig = transferConfig;
    }

    FetchV0Request recvWants(PacketLineIn packetLineIn) throws IOException {
        FetchV0Request.Builder builder = new FetchV0Request.Builder();
        boolean z = true;
        boolean z2 = false;
        while (true) {
            try {
                String string = packetLineIn.readString();
                if (PacketLineIn.isEnd(string)) {
                    break;
                }
                if (string.startsWith("deepen ")) {
                    int i = Integer.parseInt(string.substring(7));
                    if (i <= 0) {
                        throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidDepth, Integer.valueOf(i)));
                    }
                    builder.setDepth(i);
                } else if (string.startsWith("shallow ")) {
                    builder.addClientShallowCommit(ObjectId.fromString(string.substring(8)));
                } else if (this.transferConfig.isAllowFilter() && string.startsWith("filter ")) {
                    String strSubstring = string.substring("filter".length() + 1);
                    if (z2) {
                        throw new PackProtocolException(JGitText.get().tooManyFilters);
                    }
                    builder.setFilterSpec(FilterSpec.fromFilterLine(strSubstring));
                    z2 = true;
                } else {
                    if (!string.startsWith("want ") || string.length() < 45) {
                        throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "want", string));
                    }
                    if (z && string.length() > 45) {
                        FirstWant firstWantFromLine = FirstWant.fromLine(string);
                        builder.addClientCapabilities(firstWantFromLine.getCapabilities());
                        builder.setAgent(firstWantFromLine.getAgent());
                        string = firstWantFromLine.getLine();
                    }
                    builder.addWantId(ObjectId.fromString(string.substring(5)));
                    z = false;
                }
            } catch (EOFException e) {
                if (!z) {
                    throw e;
                }
            }
        }
        return builder.build();
    }
}
