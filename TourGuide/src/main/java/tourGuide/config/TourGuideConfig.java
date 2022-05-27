package tourGuide.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@Configurable
public class TourGuideConfig {

    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return new FeignFormatterRegistrar() {
            @Override
            public void registerFormatters(FormatterRegistry formatterRegistry) {
                DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
                registrar.setUseIsoFormat(true);
                registrar.registerFormatters(formatterRegistry);
            }
        };
    }
}
