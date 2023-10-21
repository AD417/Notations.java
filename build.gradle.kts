plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "io.github.ad417"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.ad417:BreakInfinity:0.2.0")
    implementation("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}

signing {
    // require(true)
    configure<SigningExtension> {
        useInMemoryPgpKeys(
                project.findProperty("SIGNING_KEY") as String,
                project.findProperty("SIGNING_PASSWORD") as String
        )
        sign(publishing.publications)
    }
}

publishing {

    configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()
    }

    configure<PublishingExtension> {
        publications {
            val main by creating(MavenPublication::class) {
                from(components["java"])

                pom {
                    name.set("BreakNotations")
                    description.set(
                            "A Java port of AntimatterDimensions-Notations - all the notations used in the web game \"Antimatter Dimensions\", for use in other applications. "
                    )
                    url.set("https://github.com/AD417/Notations.java")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("AD417")
                            name.set("Alexander Day")
                            email.set("ard8302@rit.edu")
                        }
                    }
                    scm {
                        connection.set("scm:git:git@github.com:AD417/Notations.java.git")
                        url.set("https://github.com/AD417/Notations.java")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "OSSRH"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.findProperty("OSSRH_USER") as String
                    password = project.findProperty("OSSRH_PASSWORD") as String
                }
            }
        }
    }
}