package org.eclipse.jgit.transport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes13.dex */
final class ProtocolV2Parser {
    private final TransferConfig transferConfig;

    ProtocolV2Parser(TransferConfig transferConfig) {
        this.transferConfig = transferConfig;
    }

    private static String consumeCapabilities(PacketLineIn packetLineIn, Consumer<String> consumer, Consumer<String> consumer2) throws IOException {
        String string = packetLineIn.readString();
        while (!PacketLineIn.isDelimiter(string) && !PacketLineIn.isEnd(string)) {
            if (string.startsWith("server-option=")) {
                consumer.accept(string.substring("server-option=".length()));
            } else if (string.startsWith("agent=")) {
                consumer2.accept(string.substring("agent=".length()));
            }
            string = packetLineIn.readString();
        }
        return string;
    }

    FetchV2Request parseFetchRequest(PacketLineIn packetLineIn) throws IOException {
        final FetchV2Request.Builder builder = FetchV2Request.builder();
        builder.addClientCapability("side-band-64k");
        String strConsumeCapabilities = consumeCapabilities(packetLineIn, new Consumer() { // from class: org.eclipse.jgit.transport.ProtocolV2Parser$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.addServerOption((String) obj);
            }
        }, new Consumer() { // from class: org.eclipse.jgit.transport.ProtocolV2Parser$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.setAgent((String) obj);
            }
        });
        if (PacketLineIn.isEnd(strConsumeCapabilities)) {
            return builder.build();
        }
        if (!PacketLineIn.isDelimiter(strConsumeCapabilities)) {
            throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, strConsumeCapabilities));
        }
        boolean z = false;
        for (String str : packetLineIn.readStrings()) {
            if (str.startsWith("want ")) {
                builder.addWantId(ObjectId.fromString(str.substring(5)));
            } else if (this.transferConfig.isAllowRefInWant() && str.startsWith("want-ref ")) {
                builder.addWantedRef(str.substring(GitProtocolConstants.OPTION_WANT_REF.length() + 1));
            } else if (str.startsWith("have ")) {
                builder.addPeerHas(ObjectId.fromString(str.substring(5)));
            } else if (str.equals(ES6Iterator.DONE_PROPERTY)) {
                builder.setDoneReceived();
            } else if (str.equals(GitProtocolConstants.OPTION_WAIT_FOR_DONE)) {
                builder.setWaitForDone();
            } else if (str.equals("thin-pack")) {
                builder.addClientCapability("thin-pack");
            } else if (str.equals("no-progress")) {
                builder.addClientCapability("no-progress");
            } else if (str.equals("include-tag")) {
                builder.addClientCapability("include-tag");
            } else if (str.equals("ofs-delta")) {
                builder.addClientCapability("ofs-delta");
            } else if (str.startsWith("shallow ")) {
                builder.addClientShallowCommit(ObjectId.fromString(str.substring(8)));
            } else if (str.startsWith("deepen ")) {
                int i = Integer.parseInt(str.substring(7));
                if (i <= 0) {
                    throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidDepth, Integer.valueOf(i)));
                }
                if (builder.getDeepenSince() != 0) {
                    throw new PackProtocolException(JGitText.get().deepenSinceWithDeepen);
                }
                if (builder.hasDeepenNotRefs()) {
                    throw new PackProtocolException(JGitText.get().deepenNotWithDeepen);
                }
                builder.setDepth(i);
            } else if (str.startsWith("deepen-not ")) {
                builder.addDeepenNotRef(str.substring(11));
                if (builder.getDepth() != 0) {
                    throw new PackProtocolException(JGitText.get().deepenNotWithDeepen);
                }
            } else if (str.equals(GitProtocolConstants.OPTION_DEEPEN_RELATIVE)) {
                builder.addClientCapability(GitProtocolConstants.OPTION_DEEPEN_RELATIVE);
            } else if (str.startsWith("deepen-since ")) {
                int i2 = Integer.parseInt(str.substring(13));
                if (i2 <= 0) {
                    throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidTimestamp, str));
                }
                if (builder.getDepth() != 0) {
                    throw new PackProtocolException(JGitText.get().deepenSinceWithDeepen);
                }
                builder.setDeepenSince(i2);
            } else if (this.transferConfig.isAllowFilter() && str.startsWith("filter ")) {
                if (z) {
                    throw new PackProtocolException(JGitText.get().tooManyFilters);
                }
                builder.setFilterSpec(FilterSpec.fromFilterLine(str.substring("filter".length() + 1)));
                z = true;
            } else if (this.transferConfig.isAllowSidebandAll() && str.equals(GitProtocolConstants.OPTION_SIDEBAND_ALL)) {
                builder.setSidebandAll(true);
            } else if (str.startsWith("packfile-uris ")) {
                String[] strArrSplit = str.substring(14).split(",");
                for (String str2 : strArrSplit) {
                    builder.addPackfileUriProtocol(str2);
                }
            } else {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, str));
            }
        }
        return builder.build();
    }

    LsRefsV2Request parseLsRefsRequest(PacketLineIn packetLineIn) throws IOException {
        final LsRefsV2Request.Builder builder = LsRefsV2Request.builder();
        ArrayList arrayList = new ArrayList();
        String strConsumeCapabilities = consumeCapabilities(packetLineIn, new Consumer() { // from class: org.eclipse.jgit.transport.ProtocolV2Parser$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.addServerOption((String) obj);
            }
        }, new Consumer() { // from class: org.eclipse.jgit.transport.ProtocolV2Parser$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.setAgent((String) obj);
            }
        });
        if (PacketLineIn.isEnd(strConsumeCapabilities)) {
            return builder.build();
        }
        if (!PacketLineIn.isDelimiter(strConsumeCapabilities)) {
            throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, strConsumeCapabilities));
        }
        for (String str : packetLineIn.readStrings()) {
            if (str.equals("peel")) {
                builder.setPeel(true);
            } else if (str.equals("symrefs")) {
                builder.setSymrefs(true);
            } else if (str.startsWith("ref-prefix ")) {
                arrayList.add(str.substring("ref-prefix ".length()));
            } else {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, str));
            }
        }
        return builder.setRefPrefixes(arrayList).build();
    }

    ObjectInfoRequest parseObjectInfoRequest(PacketLineIn packetLineIn) throws IOException {
        ObjectInfoRequest.Builder builder = ObjectInfoRequest.builder();
        ArrayList arrayList = new ArrayList();
        String string = packetLineIn.readString();
        if (PacketLineIn.isEnd(string)) {
            return builder.build();
        }
        if (!string.equals("size")) {
            throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, string));
        }
        for (String str : packetLineIn.readStrings()) {
            if (!str.startsWith("oid ")) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().unexpectedPacketLine, str));
            }
            String strSubstring = str.substring("oid ".length());
            try {
                arrayList.add(ObjectId.fromString(strSubstring));
            } catch (InvalidObjectIdException e) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidObject, strSubstring), e);
            }
        }
        return builder.setObjectIDs(arrayList).build();
    }
}
