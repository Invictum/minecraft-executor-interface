[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e7c5c1f4629c4d849181de71fe54ed0d)](https://www.codacy.com/app/zim182/minecraft-executor-interface?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Invictum/minecraft-executor-interface&amp;utm_campaign=Badge_Grade)
[![BukkitDev](https://img.shields.io/badge/BukkitDev-v1.0.0-orange.svg)](https://dev.bukkit.org/projects/mei)

Minecraft Executor Interface (MEI)
=======================================

MEI is a Minecraft plugin provides ability to send commands for execution from external sources.

For configuration details refer to [project documentation](https://dev.bukkit.org/projects/mei) page on dev.bukkit.org

Contribution
------------
Apache Maven and JDK8 should be installed to contribute. To build plugin from sources, just emmit command in terminal with sources
```
mvn clean package -Prelease
```
This will build sources, run tests and produce plugin JAR file into `target/minecraft-executor-interface-1.0.0-SNAPSHOT-jar-with-dependencies.jar`

Sometimes it is necessary to rebuld plugin for specific Minecraft server version. To achieve it edit `dependency` section in `pom.xml` file
```
<dependency>
    <groupId>org.spigotmc</groupId>
    <artifactId>spigot-api</artifactId>
    <version>1.10.2-R0.1-SNAPSHOT</version>
    <scope>provided</scope>
    <type>jar</type>
</dependency>
```
Set suited version for `spigot-api`, save file and rebuild plugin as usual.

MEI is free software and available under the GPL license.
