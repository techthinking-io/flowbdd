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

import io.techthinking.flowbdd.report.mermaid.Expression;

import java.util.Objects;

public class Message implements Expression {

    private final String from;
    private final String to;
    private final String text;
    private final String type;

    public Message(String from, String to, String text) {
        this(from, to, text, "->>");
    }

    public Message(String from, String to, String text, String type) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.type = type;
    }

    @Override
    public String generate() {
        return from + type + to + ": " + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(from, message1.from) && Objects.equals(to, message1.to) && Objects.equals(text, message1.text) && Objects.equals(type, message1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, text, type);
    }

    @Override
    public String toString() {
        return "Message{" +
            "from='" + from + '\'' +
            ", to='" + to + '\'' +
            ", message='" + text + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}