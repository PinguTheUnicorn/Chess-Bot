build:
	javac *.java

run: build
	java Main

clean: build
	rm *.class
