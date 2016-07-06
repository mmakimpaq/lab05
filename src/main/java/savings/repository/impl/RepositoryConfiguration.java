package savings.repository.impl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

// TODO #1 mark this class as configuration component
// TODO #2 configure component scanning to configure all repository layer classes
@Configuration
@ComponentScan(basePackages = {"savings.repository.impl"})
public class RepositoryConfiguration {

}
