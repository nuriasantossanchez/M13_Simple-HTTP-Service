package springBootSimpleHTTPService.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Clase de la capa de Configuration de Spring
 * Implementa la interface WebMvcConfigurer que proporciona la configuración principal de Spring MVC.
 *
 * Anotaciones:
 *
 * @ComponentScan
 * Utilizada junto a la anotacion @Configuration.
 * Configura directivas de análisis de componentes
 * Proporciona soporte en paralelo con el elemento <context:component-scan> de Spring XML.
 * Especifica basePackages(valor de alias), para definir paquetes específicos para escanear.
 * Si no se definen paquetes específicos, se realizará un escaneo desde el paquete de la
 * clase que declara esta anotación
 *
 * @Configuration
 * Indica que una clase declara uno o más métodos @Bean y puede ser procesada por el contenedor Spring
 * para generar definiciones de beans y solicitudes de servicio para esos beans en tiempo de ejecución
 *
 * @EnableWebMvc
 * Utilizada junto a la anotacion @Configuration.
 * Habilita la configuración predeterminada de Spring MVC y registra los componentes de infraestructura
 * MVC de Spring esperados por el DispatcherServlet.
 * A su vez, importa DelegatingWebMvcConfiguration, que proporciona la configuración predeterminada
 * de Spring MVC.
 *
 * En este caso, debido a la configuracion personalizada del CORS requerida, no queremos la configuracion
 * predeterminada de Spring MVC, si no una propia, proporcionada por la implementacion de la interface
 * WebMvcConfigurer, en la que sobreescribiremos los metodos necesarios.
 * La anotacion queda comentada, no hace efecto
 *
 */
@ComponentScan(basePackages = {"springBootSimpleHTTPService"})
@Configuration
//@EnableWebMvc
public class WebMVCConfiguration implements WebMvcConfigurer {

    /**
     * Crea un controlador de recursos proporcionando los patrones de ruta de URL para lo cual se
     * debe invocar al controlador para que sirva recursos estáticos (por ejemplo, "/**").
     * Usa metodos adicionales del ResourceHandlerRegistration para agregar una o más
     * ubicaciones desde las que entregar contenido estático (por ejemplo, {"/", "classpath:/resources/"})
     * o para especificar un período de caché para los recursos servidos.
     *
     * @param registry, instancia de tipo ResourceHandlerRegistry, almacena registros de controladores de
     *                  recursos para servir recursos estáticos como imágenes, archivos css y otros a través
     *                  de Spring MVC, incluida la configuración de encabezados de caché optimizados para una
     *                  carga eficiente en un navegador web.
     *                  Los recursos se pueden servir desde ubicaciones en la raíz de la aplicación web,
     *                  desde la classpath y otros.
     */
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("OK : addResourceHandlers ...");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * Configura el procesamiento de solicitudes de origen cruzado global.
     * El mapeo de las configuraciones CORS se aplican a anotaciones de controladores,
     * puntos finales funcionales y recursos estáticos.
     * Las anotaciones @CrossOrigin pueden declarar una configuración más detallada.
     * En tales casos, la configuración CORS global declarada aquí se combina con
     * la configuración CORS local definida en un metodo de controlador
     *
     * @param registry, objeto de tipo CorsRegistry, habilita el manejo de solicitudes de origen cruzado
     *                  para el patron de ruta especificado, en general, gestiona el registro de asignaciones
     *                  globales del CorsConfiguration, basadas en patrones de URL.
     *                  CorsConfiguration es un contenedor para la configuración de CORS junto con
     *                  metodos para verificar el origen real, los metodos HTTP y los encabezados de
     *                  una solicitud determinada
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("OK : addCorsMappings ...");
        registry.addMapping("/**").allowedOrigins("http://localhost:8181");
    }

}
