package pro.toparvion.sample.fatjar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

@SpringBootApplication
public class FatjarApplication implements ApplicationRunner {
  private static final Logger log = LoggerFactory.getLogger(FatjarApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(FatjarApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    // Print the 'java.class.path' JVM option
    String[] desiredClassPath = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    String separatedLines = join("\n", desiredClassPath);
    log.info("'java.class.path' ({} entries):\n{}", desiredClassPath.length, separatedLines);

    // Print actual class path of the current thread's class loader if it's an URLClassLoader
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    if (contextClassLoader instanceof URLClassLoader) {
      URLClassLoader urlClassLoader = (URLClassLoader) contextClassLoader;
      String actualClassPath = Arrays.stream(urlClassLoader.getURLs())
              .map(URL::toString)
              .collect(joining("\n"));
      log.info("Actual class path: \n{}", actualClassPath);
    }

    // Print any custom URL handlers registered within the app 
    String handlerPackages = System.getProperty("java.protocol.handler.pkgs", "(n/a)");
    log.info("'java.protocol.handler.pkgs': {}", handlerPackages);
  }

}
