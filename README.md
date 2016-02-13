## Eclipse Weblogic Plugin
Eclipse Weblogic Plugin is a plug-in for the Eclipse platform.

##Description
It allows you to start and stop specified weblogic instance. It is also used to debug and hot swap code.

##How to build Eclipse Weblogic Plugin ?
###With Eclipse:
- Launch _Eclipse_,
- Import _"Existing Projects into Workspace"_ by selecting the parent project folder,
- Right Click on _"site.xml"_ in com.rajendarreddyj.eclipse.plugins.weblogic.site project and Select _"Plug-in Tools"_ and click _"Build Site"_.

##How to install Eclipse Weblogic Plugin ?
1. Build or download from releases & unzip _"eclipse-weblogic-plugin-site-x.y.z.zip"_,
2. Launch _Eclipse_,
3. In Eclipse, Click on _"Help > Install New Software..."_,
3. Click on button _"Add..."_ to add a new repository,
4. Enter Name and select the local site directory as below and click OK.
    * Name    : Eclipse Weblogic Plugin Update Site
    * Location: Eclipse Site Directory (either extracted one or locally built site directory)
5. You should see _"Eclipse Weblogic Plugin"_. Select the checkbox next to it and click Next,
6. You'll need to accept the license and confirm you want to install a plugin that is not digitally signed. Go ahead and install it anyway.
7. Restart eclipse.

##How to install Eclipse Weblogic Plugin from [update site](https://rajendarreddyj.github.io/eclipse-weblogic-plugin/update-site/)?
1. Launch _Eclipse_,
2. In Eclipse, Click on _"Help > Install New Software..."_,
3. Click on button _"Add..."_ to add a new repository,
4. Enter Name and URL as below and click OK.
    * Name    : Eclipse Weblogic Plugin Update Site
    * Location: https://rajendarreddyj.github.io/eclipse-weblogic-plugin/update-site/
5. You should see _"Eclipse Weblogic Plugin"_. Select the checkbox next to it and click Next,
6. You'll need to accept the license and confirm you want to install a plugin that is not digitally signed. Go ahead and install it anyway.
7. Restart eclipse.

##How to uninstall Eclipse Weblogic Plugin ?
1. Click on _"Help > About Eclipse > Installation Details"_,
2. Select _"Eclipse Weblogic Plugin"_,
3. Click on _"Uninstall..."_.
