This is sample project with directory structure:
 
The 'config' directory contains testng.xml file, and is a place holder for configuration files.
The 'lib' directory contains required jar files, and is a place holder for other library jars/zips.
The 'resources' directory contains all required resources including properties files and data files, and is a place holder for other resources.
The 'scripts' directory contains ant script to build and run automation. It also contains bat file for windows environment.
The 'src' directory contains all java files and is a place holder for other java files.
The 'bin' directory contains compiled classes, and is a volatile directory that is deleted by the target clean in build.xml file.
The 'test-results' directory contains result files.
The 'scenarios' directory is the default place holder for all the scenario files. 


To change/modify dependencies check ivy.xml
To run the project, from command prompt go to project home and run ant. Open dashboard.htm to view results.

Note: This sample project uses chrome driver and it requires chrome driver binary.
You need to download and set webdriver.chrome.driver property in application.properties file with driver binary path.

Please refer https://qmetry.github.io/qaf/ 
Thanks,
QAS Team.