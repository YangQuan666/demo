plugins {
    id("java")
}

group = "rpc.common"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.92.Final")
    implementation("io.protostuff:protostuff-core:1.8.0")
    implementation("io.protostuff:protostuff-runtime:1.8.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}