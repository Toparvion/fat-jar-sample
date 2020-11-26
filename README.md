# Spring Boot “fat” JAR Sample :tumbler_glass:

This is a simple Spring Boot command-line application to demonstrate some features of executable JAR packaging. It supplements [the conference talk](https://toparvion.pro/talk/2020/joker/).

The application uses Spring Boot [2.4](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.4.0-M4-Release-Notes) and Gradle 6.6.1. 



## The contents

### `build.gradle`

The [build script](build.gradle) provides 2 ways for packaging the application into a Docker image:

* Spring Boot Gradle Plugin
* Google Jib Gradle Plugin

For both cases the script contains examples of how to customize the image layers in case the default ones don’t satisfy you. The script also declares some JAR Manifest attributes in order to show how they are propagated to the runtime in different execution modes (packaged jar, exploded jar, IDE, etc.).

:point_up: All the Docker manipulating tasks assume that you have **local Docker daemon** running and accessible via network.



### `src/main/java/pro/toparvion/sample/fatjar/` directory

##### `FatjarApplication.java` 

The main class of the application that triggers on the very start and then:

1. Prints the `java.class.path` system property value.
2. Prints the actual class path of the current thread’s context ClassLoader (only if it is of `URLClassLoader` type).
3. Prints the `java.protocol.handler.pkgs` system property value (useful for detection of running within “fat” JAR).

##### `generated/AnotherGeneratedStub.java`

This is just a sample of pseudo-generated stuff of the application (to show how it can be placed into a separate Docker image layer).



### `docker/` directory

The directory contains the following Dockerfile samples:

| File                 | Description                                                  |
| -------------------- | ------------------------------------------------------------ |
| `Dockerfile-asis`    | Exports the “fat” JAR into a Docker image “as is”, i.e. without any layering nor exploding |
| `Dockerfile-extract` | Simply extracts the “fat” JAR into a Docker image but doesn’t separate it into layers |
| `Dockerfile-layers`  | Extracts the “fat” JAR by layers into separate directories and puts each of them into corresponding layer |



### `cmd/` directory

The directory contains the following Windows command line script samples:

| File               | Description                                                  | WorkDir         |
| ------------------ | ------------------------------------------------------------ | --------------- |
| `fat-jar.cmd`      | Assembles the “fat” JAR with Gradle Wrapper and runs it      | repository root |
| `jar-launcher.cmd` | Explodes the previously packaged JAR and runs it with `JarLauncher` class | `build/libs`    |
| `main-class.cmd`   | Explodes the previously packaged JAR and runs it with the application’s main class (`FatjarApplication`) | `build/libs`    |
| `layers-list.cmd`  | Lists the layers of a “fat” JAR with built-in `layertools` mode | repository root |

:information_source: While initially created for Windows, all the scripts are simple enough to be easily ported on \*nix OSes.



### `cmd/image/` directory

The directory contains CMD script for building Docker images with Spring Boot Gradle Plugin and Google Jib Gradle Plugin.

| File      | Description                                                  | WorkDir   |
| --------- | ------------------------------------------------------------ | --------- |
| `oci.cmd` | Builds a Docker image using Spring Boot’s Cloud Native Buildpacks support | repo root |
| `jib.cmd` | Builds a Docker image with Google Jib Gradle Plugin. The plugin itself doesn’t depend on Docker Daemon but it still must be active to upload the final image into it. | repo root |



### `.run/` directory

The directory contains IntelliJ IDEA run configurations to simplify running the above scripts (and others). You may use in conjunction with `idea`  Gradle plugin facilities (also included into the build script).



## Contributions

Please feel free to submit Pull Requests to add examples of new use cases or edit the existing ones.



## License

The project uses [GPL-3.0 License](LICENSE) license.