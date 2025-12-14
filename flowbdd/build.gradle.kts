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
    `maven-publish`
    `java-library`
    signing
}

group = "io.techthinking"
version = "0.1.1-SNAPSHOT"
description = "Generate the report and feature file / documentation"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":flowbdd-wordify"))
    api(project(":flowbdd-test-utils"))
    // api(project(":flowbdd-diagrams")) //TODO

    implementation(platform(libs.jackson.bom))
    implementation(libs.jackson.databind)
    implementation(libs.jackson.annotations)

    implementation(libs.thymeleaf)

    testImplementation(libs.mockito.all)
    testImplementation(libs.jimfs)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "flowbdd"
            from(components["java"])
            //artifact(tasks["jar"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("flowbdd")
                description.set("Generate the report and feature file / documentation")
                url.set("https://github.com/techthinking-io/flowbdd")

                licenses {
                    license {
                        name.set("GNU General Public License")
                        url.set("https://www.gnu.org/licenses/")
                    }
                }
                developers {
                    developer {
                        id.set("jrbayliss")
                        name.set("James Bayliss")
                        email.set("mejrbayliss@gmail.com")
                    }
                }
                scm {
                    connection.set("https://github.com/techthinking-io/flowbdd.git")
                    url.set("https://github.com/techthinking-io/flowbdd")
                }
            }
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            name = "OSSRH"
            credentials {
                username = System.getenv("SONATYPE_TOKEN_USERNAME")
                password = System.getenv("SONATYPE_TOKEN_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PRIVATE_PASSWORD")
    )
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
