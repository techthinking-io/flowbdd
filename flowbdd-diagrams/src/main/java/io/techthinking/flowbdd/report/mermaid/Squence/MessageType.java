/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.techthinking.flowbdd.report.mermaid.Squence;

/**
 * Enum representing the semantic types of messages in a sequence diagram.
 * This provides a more usable abstraction over the raw syntax.
 */
public enum MessageType {
    // Line types
    LINE("->", false, "Normal line", "NORMAL"),
    LINE_DOTTED("-->", true, "Dotted line", "NORMAL"),

    // Synchronous messages (with arrowhead)
    ARROW_SYNC("->>", false, "Synchronous message", "SYNC"),
    ARROW_SYNC_DOTTED("-->>", true, "Dotted synchronous message", "SYNC"),

    // Asynchronous messages (with open arrow)
    ARROW_ASYNC("-)", false, "Asynchronous message", "ASYNC"),
    ARROW_ASYNC_DOTTED("--)", true, "Dotted asynchronous message", "ASYNC"),

    // Not arrived messages (with cross)
    NOT_ARRIVED("-x", false, "Not arrived message", "NOT_ARRIVED"),
    NOT_ARRIVED_DOTTED("--x", true, "Dotted not arrived message", "NOT_ARRIVED"),

    // Bidirectional messages
    BIDIRECTIONAL("<<->>", false, "Bidirectional message", "BIDIRECTIONAL"),
    BIDIRECTIONAL_DOTTED("<<-->>", true, "Dotted bidirectional message", "BIDIRECTIONAL");

    private final String rawText;
    private final boolean dotted;
    private final String description;
    private final String lineType;

    MessageType(String rawText, boolean dotted, String description, String lineType) {
        this.rawText = rawText;
        this.dotted = dotted;
        this.description = description;
        this.lineType = lineType;
    }

    /**
     * Get the raw syntax string for this message type.
     *
     * @return the raw syntax string
     */
    public String rawText() {
        return rawText;
    }

//    /**
//     * Get a MessageType from a raw syntax string value.
//     *
//     * @param value the string value of the raw message type
//     * @return the corresponding MessageType
//     */
//    public static MessageType fromRawValue(String value) {
//        for (MessageType type : values()) {
//            if (type.rawText.equals(value)) {
//                return type;
//            }
//        }
//        return ARROW_SYNC; // Default to SYNC if not found
//    }
}
