/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2021  James Bayliss
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

plugins {
    id("flowbdd.java-lib")
}

group = "com.flowbdd.ft"
version = "0.1.1-SNAPSHOT"
description = "Functional Tests"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":flowbdd"))
    implementation(project(":flowbdd-test-utils"))

    implementation(platform(libs.jackson.bom))
    implementation(libs.jackson.databind)
    implementation(libs.jackson.annotations)
    implementation(libs.jimfs)
    implementation("org.awaitility:awaitility:4.3.0")

    testImplementation(libs.mockito.core)
}

tasks.test {
    // Unless this is added, tests in undertest are run.
    // When AbstractResultsForTestSuite loads the index file  when launching ClassUnderTest.
    // the index file contains all the tests. I can only assume gradle runs the tests in parallel.
    exclude("**/ClassUnderTest.class")
    exclude("**/undertest")

    // This doesn't fix the issue above for IDEs. I haven't found a proper solution, because although this will work for Gradle
    // an IDE could run in to issues with tests run in parallel. Another option is use env variables or static flags/booleans
    // using @EnabledIf("isEnabled") or @EnabledIf("isClassUnderTestEnabled") on the class under test.
    // maxParallelForks = 1 // Will not do parallel execution
    // systemProperties["junit.jupiter.execution.parallel.enabled"] = false
}