<?xml version="1.0" encoding="UTF-8"?>

<!--
  ~ Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~  you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  -->

<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>${artifactId}-server</artifactId>
    <packaging>war</packaging>

    <#if serverImplementation == "SPRING_BOOT">
        <properties>
            <spring-boot.version>2.2.2.RELEASE</spring-boot.version>
        </properties>
    </#if>

    <#if serverImplementation == "SPRING_BOOT">
        <!-- Spring Boot -->
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>${springBootVersion}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
    </#if>


    <dependencies>
        <#if serverImplementation == "SPRING_BOOT">
            <!-- SPRING BOOT -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>

        </#if>
        <dependency>
            <groupId>${groupId}</groupId>
            <artifactId>${artifactId}-shared</artifactId>
            <version>${projectVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.google.gwt</groupId>
            <artifactId>gwt-servlet</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <#if serverImplementation == "GWT_MAVEN_PLUGIN">
            <pluginManagement>
                <plugins>
                        <plugin>
                            <groupId>org.eclipse.jetty</groupId>
                            <artifactId>jetty-maven-plugin</artifactId>
                            <configuration>
                                <scanIntervalSeconds>1</scanIntervalSeconds>
                                <webApp>
                                    <extraClasspath>${basedir}/../${artifactId}-shared/target/classes/</extraClasspath>
                                </webApp>
                                <contextXml>${basedir}/src/main/jettyconf/context.xml</contextXml>
                            </configuration>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.tomcat.maven</groupId>
                            <artifactId>tomcat7-maven-plugin</artifactId>
                            <configuration>
                                <addWarDependenciesInClassloader>false</addWarDependenciesInClassloader>
                                <path>/</path>
                                <uriEncoding>UTF-8</uriEncoding>
                            </configuration>
                        </plugin>
                </plugins>
            </pluginManagement>
        </#if>

        <#if serverImplementation == "SPRING_BOOT">
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${springBootVersion}</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>repackage</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </#if>
    </build>
    <profiles>
        <profile>
            <id>env-prod</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <dependencies>
                <dependency>
                    <groupId>${groupId}</groupId>
                    <artifactId>${artifactId}-client</artifactId>
                    <version>${projectVersion}</version>
                    <type>war</type>
                    <scope>runtime</scope>
                </dependency>
            </dependencies>
        </profile>
        <profile>
            <id>env-dev</id>
            <activation>
                <property>
                    <name>env</name>
                    <value>dev</value>
                </property>
            </activation>
            <build>
                <pluginManagement>
                    <plugins>
                        <#if serverImplementation == "GWT_MAVEN_PLUGIN">
                            <plugin>
                                <groupId>org.eclipse.jetty</groupId>
                                <artifactId>jetty-maven-plugin</artifactId>
                                <configuration>
                                    <webApp>
                                        <resourceBases>
                                            <resourceBase>${basedir}/src/main/webapp</resourceBase>
                                            <resourceBase>${basedir}/../target/gwt/launcherDir/</resourceBase>
                                        </resourceBases>
                                    </webApp>
                                </configuration>
                            </plugin>
                            <plugin>
                                <groupId>org.apache.tomcat.maven</groupId>
                                <artifactId>tomcat7-maven-plugin</artifactId>
                                <configuration>
                                    <contextFile>${basedir}/src/main/tomcatconf/context.xml</contextFile>
                                </configuration>
                            </plugin>
                        <#elseif serverImplementation == "SPRING_BOOT">
                            <plugin>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-maven-plugin</artifactId>
                                <version>${springBootVersion}</version>
                            </plugin>
                        </#if>
                    </plugins>
                </pluginManagement>
            </build>
        </profile>
    </profiles>
</project>
