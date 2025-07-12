/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2025  James Bayliss
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

public final class MessageBuilder extends ExpressionBuilder<Message> {

    private String from;
    private String to;
    private String text;
    private String type = "->>";

    private MessageBuilder() {
    }

    public static MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    public MessageBuilder from(String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder to(String to) {
        this.to = to;
        return this;
    }

    public MessageBuilder text(String text) {
        this.text = text;
        return this;
    }

    public MessageBuilder type(String type) {
        this.type = type;
        return this;
    }

    @Override
    public Message build() {
        return new Message(from, to, text, type);
    }

//    public Message buildV2(Map<String, Integer> participantsOrder) {
//        //TODO workout the direct from/to
//        int fromOrder = participantsOrder.get(from);
//        int toOrder = participantsOrder.get(to);
//        if (fromOrder > toOrder) {
//
//        }
//
//        return new Message(from, to, text, type);
//    }
}