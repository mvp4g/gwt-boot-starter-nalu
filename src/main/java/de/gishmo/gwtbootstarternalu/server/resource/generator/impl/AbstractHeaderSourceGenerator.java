/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.gwtbootstarternalu.server.resource.generator.impl;

import com.github.mvp4g.nalu.client.component.AbstractComponent;
import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.IsComponent;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.GeneratorException;
import de.gishmo.gwtbootstarternalu.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstarternalu.server.resource.generator.GeneratorUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractHeaderSourceGenerator
  extends AbstractSourceGenerator {

  private String controllerPackage;

  public void generate()
    throws GeneratorException {
    this.controllerPackage = this.clientPackageJavaConform + ".ui.header";

    this.generateIComponentClass();
    this.generateComponentClass();
    this.generateControllerClass();
  }

  private void generateIComponentClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("IHeaderComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsComponent.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                                   "IHeaderComponent.Controller"),
                                                                                     super.getClassNameWidget()));

    typeSpec.addType(TypeSpec.interfaceBuilder("Controller")
                             .addSuperinterface(ClassName.get(IsComponent.Controller.class))
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .build());

    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>IHeaderComponent" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateComponentClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("HeaderComponent")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponent.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                            "IHeaderComponent.Controller"),
                                                                              this.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                         "IHeaderComponent"));
    typeSpec.addField(getContainerFieldSpec());
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // render method
    createRenderMethod(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>HeaderComponent" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateControllerClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("HeaderController")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Controller.class)
                                                                     .addMember("route",
                                                                                "$S",
                                                                                "/")
                                                                     .addMember("selector",
                                                                                "$S",
                                                                                "header")
                                                                     .addMember("componentInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                              "IHeaderComponent"))
                                                                     .addMember("component",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                              "HeaderComponent"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) + GeneratorConstants.CONTEXT),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                            "IHeaderComponent"),
                                                                              super.getClassNameWidget()))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                         "IHeaderComponent.Controller"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build());
    JavaFile javaFile = JavaFile.builder(this.controllerPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>HeaderController" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract FieldSpec getContainerFieldSpec();

  protected abstract void createRenderMethod(TypeSpec.Builder typeSpec);
}
