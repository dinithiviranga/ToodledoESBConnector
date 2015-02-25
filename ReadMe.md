Product: Integration tests for WSO2 ESB ToodleDo connector

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above
 - A ToodleDo account


Tested Platform:

 - Ubuntu 14.04 64bit
 - WSO2 ESB 4.8.1

STEPS:

1. Make sure the ESB 4.8.1 zip file with latest patches available at {basedir}/repository folder. If you want to use another location edit the pom.xml as follows.

          <carbon.zip>
            ${basedir}/../test/wso2esb-${esb.version}.zip
          </carbon.zip>

2. Copy ToodleDo connector zip file (ToodleDo.zip) to the location "Integration_Test/products/esb/4.8.1/modules/integration/connectors/repository/"

3. Make sure the Toodledo test suite is enabled (as given below) and all other test suites are commented in the following file - "{basedir}/src/test/resources/testng.xml"
    <test name="ToodleDo-Connector-Test" preserve-order="true" verbose="2">
           <packages>
               <package name="com.wso2.esb.connector.integration.test.toodledo"/>
           </packages>
    </test>

4. This section describes how to obtain an access token from the toodledo API.

	i) 	Login to your toodledo developer account, then navigate to url: https://api.toodledo.com/3/account/authorize.php?response_type=code&client_id=yourClientID&state=newstate&scope=basic%20tasks%20notes%20outlines%20lists%20share%20write.

	ii) Get the code from return url https://localhost/?code=e64783a2ea984146bb91efa5fbc8c40907ba1cd9&state=qwerty

	iii)By giving the code you can get access token and refresh token.

	iv)	Access token will expire after two hours and we can get a new access token by giving the refresh token. Each authenticated user is allowed to make 100 API calls per access token.

	v)	Go to {basedir}/src/test/resources/artifacts/ESB/connector.config/toodledo.properties and add following lines :
        			clientId - client ID of the Toodledo app
        			clientSecret - client secret of the Toodledo app

	vi)	getAccessToken.xml configuration will obtain the access token using these property values and getRefreshToken.xml will obtain the new access_token.



5. From the base directory run the following command.
     $ mvn clean install

Notes:
More about api : http://api.toodledo.com/3/index.php