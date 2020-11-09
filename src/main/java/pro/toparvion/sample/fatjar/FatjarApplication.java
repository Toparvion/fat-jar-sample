package pro.toparvion.sample.fatjar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.String.join;

@SpringBootApplication
public class FatjarApplication implements ApplicationRunner {
  private static final Logger log = LoggerFactory.getLogger(FatjarApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(FatjarApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    String[] classPath = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    String separatedLines = join("\n", classPath);
    log.info("'java.class.path' JVM property value ({} entries):\n{}", classPath.length, separatedLines);
    String handlerPackages = System.getProperty("java.protocol.handler.pkgs", "(n/a)");
    log.info("'java.protocol.handler.pkgs' JVM property value: {}", handlerPackages);
    /*
    //Example using JSR 233 Scripting for Java 6
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine rbEngine = mgr.getEngineByExtension("rb");
    try {
      rbEngine.eval("puts 'Hello World from JRuby!'");
    } catch (ScriptException ex) {
      log.error("Ну дела", ex);
    }
    */
  }

}
