plugins {
	java
	application
//	checkstyle
	jacoco
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.ben-manes.versions") version "0.50.0"

	id("io.freefair.lombok") version "8.4"
//	id("com.github.johnrengelman.shadow") version "8.1.1"
	id ("com.adarshr.test-logger") version "4.0.0"
	id("io.sentry.jvm.gradle") version "4.4.0"
}

sentry {
	// Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
	// This enables source context, allowing you to see your source
	// code as part of your stack traces in Sentry.
	includeSourceContext.set(true)



	org.set("daniilvasutin")
	projectName.set("java-spring-boot")

	authToken.set(System.getenv("SENTRY_AUTH_TOKEN"))
}

tasks.sentryBundleSourcesJava {
	enabled = System.getenv("SENTRY_AUTH_TOKEN") != null
}

//java {
//	toolchain {
//		languageVersion.set(JavaLanguageVersion.of(21))
//	}
//}
////work for tasks
//tasks.withType<JavaExec>().configureEach {
//	dependsOn(tasks.compileJava)
//	javaLauncher.set(javaToolchains.launcherFor { languageVersion.set(JavaLanguageVersion.of(21)) })
//}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

application {
	mainClass.set("hexlet.code.AppApplication")
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")

	implementation("io.sentry:sentry-spring-boot-starter-jakarta:7.6.0")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql:42.6.0")

//	test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	implementation("net.datafaker:datafaker:2.0.1")
	implementation("org.instancio:instancio-junit:3.3.0")

	//mapstruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")

	//secur
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	testImplementation("org.springframework.security:spring-security-test")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")


//	compileOnly("com.fasterxml.jackson.core:jackson-databind:2.8.5")
//	runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.8.5")


}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required.set(true)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

jacoco {
	toolVersion = "0.8.9"
}
