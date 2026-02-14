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
plugins {
    `java-library`
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "io.techthinking.flowbdd.examples"
version = "0.1.0-SNAPSHOT"
description = "Dev Team Simulator Demo Runner"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":examples:devteam", "testArtifacts"))
    implementation(project(":examples:devteam"))
    implementation(project(":flowbdd-server"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    systemProperties(System.getProperties().filterKeys { it is String && (it).startsWith("flowbdd.") } as Map<String, *>)
}
