package org.catrobat.catroid.scratchconverter.protocol.command;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/command/AuthenticateCommand.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AuthenticateCommand extends Command {
    public AuthenticateCommand(final long clientID) {
        super(Command.Type.AUTHENTICATE);
        addArgument(Command.ArgumentType.CLIENT_ID, Long.valueOf(clientID));
    }
}
