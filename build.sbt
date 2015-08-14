/*
 * Copyright 2015 Damian McDonald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

name := "action-figure-magic"

version       := "1.0.0"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  Resolver.url("ivy releases", url("http://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns),
  Resolver.url("scalasbt releases", url("http://scalasbt.artifactoryonline.com/scalasbt/repo"))(Resolver.ivyStylePatterns),
  Resolver.url("scalaz releases", url("http://dl.bintray.com/scalaz/releases"))(Resolver.defaultPatterns),
  Resolver.url("sonatype releases", url("http://oss.sonatype.org/content/repositories/releases"))(Resolver.defaultPatterns),
  Resolver.url("typesafe releases", url("http://dl.bintray.com/typesafe/maven-releases"))(Resolver.defaultPatterns),
  Resolver.url("sbt releases", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"))(Resolver.defaultPatterns)
)

libraryDependencies ++= {
  val akkaV  = "2.3.9"
  val sprayV = "1.3.2"
  Seq(
      /* Spray */
    "io.spray"                    %%    "spray-json"            % "1.3.1"               withSources() withJavadoc,
    "io.spray"                    %%    "spray-can"             % sprayV                withSources() withJavadoc,
    "io.spray"                    %%    "spray-routing"         % sprayV                withSources() withJavadoc,
    "io.spray"                    %%    "spray-client"          % sprayV                withSources() withJavadoc,

    /* Akka */
    "com.typesafe.akka"           %%    "akka-actor"            % akkaV                 withSources() withJavadoc,
    "com.typesafe.akka"           %%    "akka-slf4j"            % akkaV                 withSources() withJavadoc,

    /* Supporting libs */
    "ch.qos.logback"              %     "logback-classic"       % "1.1.2",
    "org.scalaz.stream"           %%    "scalaz-stream"         % "0.7a",
    "net.liftweb"                 %     "lift-json_2.11"        % "2.6.2",
    "org.reflections"             %     "reflections"           % "0.9.9-RC1",
    "org.apache.commons"          %     "commons-lang3"         % "3.4",
    "commons-io"                  %     "commons-io"            % "2.4",

    /* Testing */
    "com.typesafe.akka"           %%  "akka-testkit"            % akkaV    % "test"     withSources() withJavadoc,
    "io.spray"                    %%  "spray-testkit"           % sprayV   % "test"     withSources() withJavadoc,
    "org.specs2"                  %%  "specs2"                  % "2.4.17" % "test"      // until spray-testkit gets compiled against specs 3.3
  )
}

Revolver.settings