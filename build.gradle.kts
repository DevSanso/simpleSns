

plugins {
	id("java")
	id("org.springframework.boot") version "2.7.3-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
}

group = "com.github.devsanso"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.session:spring-session-core:2.7.0")
	compileOnly("org.projectlombok:lombok:1.18.24")
	annotationProcessor("org.projectlombok:lombok:1.18.24")
	testImplementation("com.h2database:h2")
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.json:json:20220320")

}



tasks.withType<Test> {
	useJUnitPlatform()
}
