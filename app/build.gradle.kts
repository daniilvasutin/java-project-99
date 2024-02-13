plugins {
	java
	application
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
//	kotlin("jvm") version "21"
//	id("org.jetbrains.kotlin.jvm") version "21.0.1"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}
//tasks.withType<JavaExec>().configureEach {
//	dependsOn(tasks.compileJava)
//	javaLauncher.set(javaToolchains.launcherFor { languageVersion.set(JavaLanguageVersion.of(21)) })
//}
//javaToolchains { JavaLanguageVersion.of(21) }

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

//java {
//	sourceCompatibility = JavaVersion.VERSION_21
//}
//
//tasks.withType<JavaCompile> {
//	options.release = 21
//}

application {
	mainClass.set("hexlet.code.app.AppApplication")
}



repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
