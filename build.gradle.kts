plugins {
    id("java")
    id("maven-publish")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

group = "org.tgm.asuender"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TGM-HIT/asuender/sew9-2324-worttrainer-asuender")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        publications {
            register<MavenPublication>("gpr") {
                groupId = "org.tgm.asuender"
                artifactId =  "worttrainer-asuender"
                version = "1.0"

                from(components["java"])
            }
        }
    }
}


tasks.test {
    useJUnitPlatform()
}