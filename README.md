## eclipse-weblogic-plugin
Simple eclipse weblogic plugin rewritten for new Eclipse Versions(3.x or greater).

##How to build JD-Eclipse ?
###With Gradle:
```
> ./gradlew installSiteDist
```
generate _"build/install/jd-eclipse-site"_
###With Eclipse:
- Download dependencies
```
> ./gradlew downloadDependencies
```
- Launch _Eclipse_,
- Import the  _"Existing Projects into Workspace"_ by selecting the parent project folder,
- Export _"Deployable features"_,
- Copy _"site.xml"_ to the destination directory.

##How to install JD-Eclipse ?
1. Build or download & unzip _"eclipse-weblogic-site-x.y.z.zip"_,
2. Launch _Eclipse_,
3. Click on _"Help > Install New Software..."_,
4. Click on button _"Add..."_ to add an new repository,
5. Enter _"Eclipse Weblogic Plugin Update Site"_ and select the local site directory,
6. Check _"Java Decompiler Eclipse Plug-in"_,
7. Next, next, next... and restart.
