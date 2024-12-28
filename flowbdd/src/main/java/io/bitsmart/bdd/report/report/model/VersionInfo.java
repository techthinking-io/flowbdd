/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2022  James Bayliss
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

package io.bitsmart.bdd.report.report.model;

import java.time.ZonedDateTime;

public class VersionInfo {
    private final ZonedDateTime zonedDateTime;
    private final String dateTimeAsString;
    private final String hostname;

    public VersionInfo(ZonedDateTime zonedDateTime, String dateTimeAsString, String hostname) {
        this.zonedDateTime = zonedDateTime;
        this.dateTimeAsString = dateTimeAsString;
        this.hostname = hostname;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public String getDateTimeAsString() {
        return dateTimeAsString;
    }

    public String getHostname() {
        return hostname;
    }
}
