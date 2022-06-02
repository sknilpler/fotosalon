package ru.project.fotosalon.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("photos", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        //if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        //if (dirName.startsWith(".."+ File.separator)) dirName = dirName.replace(".."+File.separator, "");

       registry.addResourceHandler("/photos/**").addResourceLocations("file:"+ uploadPath + File.separator);
        System.out.println("####################################################");
        System.out.println(uploadPath + File.separator);
        //registry.addResourceHandler(File.separator + dirName + File.separator+"**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}
