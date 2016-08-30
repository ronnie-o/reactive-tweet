# reactive-tweet

## sbt init
```bash
$ mkdir reactive-tweet
$ cd reactive-tweet
$ touch build.sbt
$ mkdir {project, app, conf}
$ cd project
$ touch build.properties
$ touch plugins.sbt
$ cd ../conf
$ touch application.conf
$ sbt
```

#### build.sbt
```sbt
name := "reactive-tweet"

scalaVersion := "2.11.8"
```

#### project/build.properties
```properties
sbt.version=0.13.12
```

## play init

#### build.sbt
```sbt
val `reactive-tweet` =
  (project in file("."))
    .enablePlugins(PlayScala)
```

#### project/plugins.sbt
```sbt
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.8")
```

## 의존성 import 에러가 발생하면..
```bash
rm -rf ~/.ivy2/cache/commons-*
rm -rf ~/.ivy2/cache/junit*
rm -rf ~/.ivy2/cache/org.hamcrest*
rm -rf ~/.ivy2/cache/oauth.signpost*
rm -rf ~/.ivy2/cache/net.sf.ehcache*
rm -rf ~/.ivy2/cache/org.javassist*
```
