plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    application
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("io.javalin:javalin:3.12.0")

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")
    implementation("org.apache.kafka:kafka-clients:2.7.0")
    implementation("io.confluent:kafka-avro-serializer:6.0.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClass.set("no.nav.permitteringsvarsel.notifikasjon.AppKt")
}
