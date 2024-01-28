/* WeSmooth! 2024 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wesmooth"})
public class BlueprintService {
  public static void main(String[] args) {
    SpringApplication.run(BlueprintService.class, args);
  }
}
