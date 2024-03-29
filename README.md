# Eclipse Weblogic Plugin 

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://raw.githubusercontent.com/rajendarreddyj/eclipse-weblogic-plugin/master/LICENSE)
[![Build Status](https://travis-ci.org/rajendarreddyj/eclipse-weblogic-plugin.svg?branch=master)](https://travis-ci.org/rajendarreddyj/eclipse-weblogic-plugin)
[![](https://img.shields.io/github/issues-raw/rajendarreddyj/eclipse-weblogic-plugin.svg)](https://github.com/rajendarreddyj/eclipse-weblogic-plugin/issues)
[![](https://tokei.rs/b1/github/rajendarreddyj/eclipse-weblogic-plugin?category=code)](https://github.com/rajendarreddyj/eclipse-weblogic-plugin)

Eclipse Weblogic Plugin is a plug-in for the Eclipse platform.

##Description
It allows you to start and stop specified weblogic instance. It is also used to debug and hot swap code.

##How to build Eclipse Weblogic Plugin ?
###With Eclipse:
- Launch _Eclipse_,
- Import _"Existing Maven Projects"_ by selecting the parent project folder,
- Right Click on eclipse-weblogic-plugin project and Run _"mvn clean install"_.

##How to Change Version of  Eclipse Weblogic Plugin ?
- Go to eclipse-weblogic-plugin directory and Run _"mvn tycho-versions:set-version -DnewVersion=1.0.1"_.

##How to install Eclipse Weblogic Plugin ?
1. Build or download from releases & unzip _"Eclipse-Weblogic-Plugin-Site-vx.y.z.zip"_,
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

## Development Notes

This mojo will set New version for parent pom.

`mvn versions:set -DnewVersion=1.1.0`
