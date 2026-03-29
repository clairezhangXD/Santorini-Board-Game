# Description of Executable
## Instructions to create executable of Santorini (Sprint 3.jar) In Intellij IDEA IDE on MacOS:

1. Mark "src" directory as Sources root, and “images” directory as Resources root
2. In the toolbar, select File -> Project Structure... -> Artifacts
3. Click "+" and select Add Jar -> from modules with dependencies...
4. Select "Santorini" as Main Class and click "OK"
5. Input the output directory you want, e.g. for me: "/Users/chickenbum/Documents/FIT3077/Sprint 3/out/artifacts/Sprint_3_jar" and click “OK”
7. In the toolbar, select Build -> Build Artifacts... -> Sprint 3:jar -> Build
   Done!

## Running the executable:

Run the executable by simply double-clicking it in Finder. 

Alternatively, in terminal, cd into the output directory

e.g. for me: “/Users/chickenbum/Documents/FIT3077/Sprint 3/out/artifacts/Sprint_3_jar” 

and enter the following command:

java -jar Sprint\ 3.jar

## Requirements:
Ensure you have the following installed:

JDK version: Eclipse Temurin JDK 24 (aarch64)

Architecture: ARM64 (Apple Silicon)

JRE Version (for running): Java Runtime Environment 24 