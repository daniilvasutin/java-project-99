run-dist:
	./build/install/app/bin/app

start:
	./gradlew bootRun --args='--spring.profiles.active=production'

build:
	./gradlew build

clean:
	./gradlew clean

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=production'

build-run: build run

.PHONY: build