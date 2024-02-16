plugins {
    java
    kotlin("jvm") version "1.9.22"
}

val group = "me.honkling"
val version = "0.1.0"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
    maven(url = "https://jitpack.io/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("com.github.stephengold:Libbulletjme:20.0.0")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("cc.ekblad:4koma:1.2.0")
}

tasks.withType(ProcessResources::class.java) {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

kotlin {
    jvmToolchain(17)
}