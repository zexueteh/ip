# TARS Chatbot Application

TARS is a chatbot named after the character in Interstellar. It is based on the Duke project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/TARS.java` file, right-click it, and choose `Run TARS.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
	  _____  _    ____  ____
	 |_   _|/ \  |  _ \/ ___|
	   | | / _ \ | |_) \___ \
	   | |/ ___ \|  _ < ___) |
	   |_/_/   \_\_| \_\____/
	Hello, I'm TARS, your friendly chatbot assistant.
	How can I help you today?
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running the JAR Release

#### 1. Download the latest JAR release:
- Navigate to the Releases page of the TARS project.
- Download the latest TARS.jar file.

#### 2. Ensure Java is installed:
- Open a terminal or command prompt and run:
```
java -version
```
- Ensure the output indicates that Java 17 is installed.

#### 3. Run the JAR file:
- Open a terminal or command prompt.
- Navigate to the folder where you downloaded TARS.jar.
- Run the chatbot with the following command:
```
java -jar TARS.jar
```
- Verify the chatbot is running:
- You should see the chatbot's welcome message:
```
Hello, I'm TARS, your friendly chatbot assistant.
How can I help you today?
```
Now you are ready to use TARS via the command line! 🚀
