## Prometheus starter
This is a spring boot auto-configuration that helps to configure prometheus fast.
An embed jetty server is used to expose a local port for prometheus server to pull metrics.

### Usage
1. Add maven dependency
```
<dependency>
    <groupId>com.kchen</groupId>
    <artifactId>prometheus-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
2. Add the following in your __application.yml__：
```
prometheus:
  host: localhost
  port: 9010
  contextPath: /
  path: /metrics
```
3. Add ```metrics``` in your code
```
@RestController
@RequestMapping("/")
public class HomeController {

    private static final Counter counters = Counter.build()
            .name("http_requests_total")
            .labelNames("app", "uri", "method")
            .help("counter the request")
            .register();

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        counters.labels("demo", request.getRequestURI(), request.getMethod()).inc();
        return "home";
    }
}

```