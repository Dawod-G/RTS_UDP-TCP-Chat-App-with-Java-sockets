package ensea.rts.tcp.chat;

public class ChatProtocolParser {

    /**
     * Enum representing the types of commands in the chat protocol.
     */
    public enum CommandType {
        NICKNAME, // #nickname
        MENTION,  // @user
        MESSAGE   // classical message
    }

    public static class ParsedCommand {
        public CommandType type;
        public String value;
        public String message;

        /**
         * Constructs a ParsedCommand with the specified type, value, and message.
         *
         * @param type the type of the command
         * @param value the value associated with the command
         * @param message the message associated with the command
         */
        public ParsedCommand(CommandType type, String value, String message) {
            this.type = type;
            this.value = value;
            this.message = message;
        }

        /**
         * Returns a string representation of the parsed command.
         *
         * @return a string representation of the parsed command
         */
        @Override
        public String toString() {
            return "Type: " + type + ", value: '" + value + "'" + ", message: '" + message + "'";
        }
    }

    /**
     * Parses a line of text according to the chat protocol.
     *
     * @param line the line of text to parse
     * @return a ParsedCommand representing the parsed command
     */
    public ParsedCommand parseLine(String line) {
        if (line.startsWith("#nickname")) {
            return new ParsedCommand(CommandType.NICKNAME, line.substring(10).trim(), "");
        } else if (line.startsWith("@")) {
            String[] parts = line.split("\\s+", 2);
            String mentionUser = parts[0].substring(1);
            String message = parts.length > 1 ? parts[1] : "";
            return new ParsedCommand(CommandType.MENTION, mentionUser, message);
        } else {
            return new ParsedCommand(CommandType.MESSAGE, "", line.trim());
        }
    }
}