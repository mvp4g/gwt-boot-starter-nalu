package com.github.nalukit.bootstarternalu.server.generator.impl.springboot;

import com.github.nalukit.bootstarternalu.server.generator.impl.common.WebXmlSourceGenerator;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ApplicationPropertiesSourceGenerator {

    private static final String APPLICATION_PROPERTIES = "application.properties";
    private File resourceDirectory;

    public ApplicationPropertiesSourceGenerator(Builder builder) {
        this.resourceDirectory = builder.resourceDirecory;
    }

    public void generate() throws GeneratorException {
        Configuration freeMarkerConfiguration = new Configuration();

        freeMarkerConfiguration.setClassForTemplateLoading(WebXmlSourceGenerator.class,
                "/templates/properties");
        freeMarkerConfiguration.setDefaultEncoding("UTF-8");
        Template template;

        try {
            template = freeMarkerConfiguration.getTemplate("ApplicationProperties.ftl");
        } catch (IOException e) {
            throw new GeneratorException("Unable to get >>ApplicationProperties.ftl<< -> exception: " + e.getMessage());
        }

        if (!this.resourceDirectory.exists()) {
            this.resourceDirectory.mkdirs();
        }

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("projectBuildDirectory", "${project.build.directory}");

        try (StringWriter out = new StringWriter()) {
            template.process(templateData, out);
            Files.write(Paths.get(this.resourceDirectory.getPath() + File.separator + APPLICATION_PROPERTIES),
                    out.toString()
                            .getBytes());
            out.flush();
        } catch (IOException | TemplateException e) {
            throw new GeneratorException("Unable to write generated file: >>" + this.resourceDirectory.getPath() + File.separator + "application.properties" + "<< -> exception: " + e.getMessage());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected File resourceDirecory;

        public Builder setResourceDirecory(File resourceDirecory) {
            this.resourceDirecory = resourceDirecory;
            return this;
        }


        public ApplicationPropertiesSourceGenerator build() {
            return new ApplicationPropertiesSourceGenerator(this);
        }
    }
}
