# Larper project

This is a greenfield Java project named _Larper_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/larper/Larper.java` file, right-click it, and choose `Run Larper.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
    _                              
   | |       __ _   _ __   _ __     ___   _ __
   | |      / _` | | '__| | '_ \   / _ \ | '__|
   | |___  | (_| | | |    | |_) | |  __/ | |
   |_____|  \__,_| |_|    | .__/   \___| |_|
                          |_|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Acknowledgements

- This project was built from the [SE-EDU Duke project template](https://github.com/se-edu/duke).
- The JavaFX GUI structure was adapted from the
  [SE-EDU JavaFX tutorial](https://se-education.org/guides/tutorials/javaFxPart1.html).
- The user guide follows the structure recommended in the CS2103/T Week 6 iP instructions and uses the
  [AddressBook Level 3 User Guide](https://se-education.org/addressbook-level3/UserGuide.html) as a formatting
  benchmark.
- Profile images are project assets stored in `src/main/resources/images`. If any of them were downloaded from an
  external source, add the exact source here before final submission.
