

plugins {
    kotlin("jvm") version "1.6.21"
    id("java-gradle-plugin")
}

group = "com.github.devsanso.sns"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("com.github.devsanso.sns.plugin") {
            //id("front-plugin")
            //implementationClass("com.github.devsanso.sns.plugin.FrontPlugin")
            id = "com.github.devsanso.sns.plugin"
            implementationClass = "com.github.devsanso.sns.plugin.FrontPlugin"
            version = "1.0.0"
        }
    }
}


