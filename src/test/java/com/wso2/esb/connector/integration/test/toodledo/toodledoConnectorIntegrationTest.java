package com.wso2.esb.connector.integration.test.toodledo;


/** Created by dinithi on 9/8/14.
 *
 *  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


    import org.apache.axis2.context.ConfigurationContext;
    import org.testng.Assert;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;
    import org.wso2.carbon.automation.api.clients.proxy.admin.ProxyServiceAdminClient;
    import org.wso2.carbon.automation.api.clients.utils.AuthenticateStub;
    import org.wso2.carbon.automation.utils.axis2client.ConfigurationContextProvider;
    import com.wso2.esb.connector.integration.test.common.ConnectorIntegrationUtil;
    import org.wso2.carbon.esb.ESBIntegrationTest;
    import org.wso2.carbon.mediation.library.stub.MediationLibraryAdminServiceStub;
    import org.wso2.carbon.mediation.library.stub.upload.MediationLibraryUploaderStub;

    import javax.activation.DataHandler;
    import java.net.URL;
    import java.util.Properties;

    public class toodledoConnectorIntegrationTest extends ESBIntegrationTest {

    private static final String CONNECTOR_NAME = "ToodleDo";

    private ProxyServiceAdminClient proxyAdmin;

    private String pathToProxiesDirectory = null;

    private String pathToRequestsDirectory = null;

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        super.init();

        ConfigurationContextProvider configurationContextProvider = ConfigurationContextProvider.getInstance();
        ConfigurationContext cc = configurationContextProvider.getConfigurationContext();
        MediationLibraryUploaderStub mediationLibUploadStub = new MediationLibraryUploaderStub(cc, esbServer.getBackEndUrl() + "MediationLibraryUploader");
        AuthenticateStub.authenticateStub("admin", "admin", mediationLibUploadStub);

        MediationLibraryAdminServiceStub adminServiceStub = new MediationLibraryAdminServiceStub(cc, esbServer.getBackEndUrl() + "MediationLibraryAdminService");

        AuthenticateStub.authenticateStub("admin", "admin", adminServiceStub);

        String repoLocation;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            repoLocation = System.getProperty("connector_repo").replace("/", "\\");
        } else {
            repoLocation = System.getProperty("connector_repo").replace("/", "/");
        }
        proxyAdmin = new ProxyServiceAdminClient(esbServer.getBackEndUrl(), esbServer.getSessionCookie());

        String toodleDoConnectorFileName = CONNECTOR_NAME + ".zip";
        ConnectorIntegrationUtil.uploadConnector(repoLocation, mediationLibUploadStub, toodleDoConnectorFileName);
        log.info("Sleeping for " + 60000 / 1000 + " seconds while waiting for synapse import");
        Thread.sleep(60000);

         adminServiceStub.updateStatus("{org.wso2.esb.connector}" + CONNECTOR_NAME, CONNECTOR_NAME,"org.wso2.esb.connector", "enabled");

        Properties toodleDoConnectorProperties = ConnectorIntegrationUtil.getConnectorConfigProperties(CONNECTOR_NAME);

        pathToProxiesDirectory = repoLocation + toodleDoConnectorProperties.getProperty("proxyDirectoryRelativePath");
        pathToRequestsDirectory = repoLocation + toodleDoConnectorProperties.getProperty("requestDirectoryRelativePath");

    }

    @Override
    protected void cleanup() {
        axis2Client.destroy();
    }

/**
 * Mandatory parameter test case for addingFolders method.
 * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingFolders} integration test with mandatory parameters.")
    public void testAddingFolders() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingFolders.txt";
        String methodName = "addingFolders";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Test case with optional parameters for addingFoldersOptional method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingFoldersOptional} integration test with mandatory parameters.")
    public void testAddingFoldersWithOptionalParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingFoldersOptional.txt";
        String methodName = "addingFoldersOptional";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editingFolders method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingFolders} integration test with mandatory parameters.")
    public void testEditingFoldersWithMandatoryParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingFolders.txt";
        String methodName = "editingFolders";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editingFoldersOptional method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingFoldersOptional} integration test with mandatory parameters.")
    public void testEditingFoldersWithOptionalParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingFoldersOptional.txt";
        String methodName = "editingFoldersOptional";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for deletingFolders method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {deletingFolders} integration test with mandatory parameters.")
    public void testDeletingFolders() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingFolders.txt";
        String methodName = "deletingFolders";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     //     * Mandatory parameter test case for retrievingFolders method.
     //     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingFolders} integration test with mandatory parameters.")
    public void testRetrievingFolders() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingFolders.txt";
        String methodName = "retrievingFolders";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for addingGoals method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingGoals} integration test with mandatory parameters.")
    public void testAddingGoals() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingGoals.txt";
        String methodName = "addingGoals";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Test case with optional parameters for addingGoalsOptional method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingGoalsOptional} integration test with mandatory parameters.")
    public void testAddingGoalsWithOptionalParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingGoalsOptional.txt";
        String methodName = "addingGoalsOptional";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editingGoals method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingGoals} integration test with mandatory parameters.")
    public void testEditingGoalsWithMandatoryParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingGoals.txt";
        String methodName = "editingGoals";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

 /*   /**
     * Test case with optional parameters for editingGoalsOptional method.
     * */
/*
    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingGoalsOptional} integration test with optional parameters.")
    public void testEditingGoalsWithOptionalParams() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingGoalsOptional.txt";
        String methodName = "editingGoalsOptional";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }*/

    /**
     * Mandatory parameter test case for deletingFolders method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {deletingFolders} integration test with mandatory parameters.")
    public void testDeletingGoals() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingFolders.txt";
        String methodName = "deletingFolders";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     //     * Mandatory parameter test case for retrievingGoals method.
     //     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingGoals} integration test with mandatory parameters.")
    public void testRetrievingGoals() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingGoals.txt";
        String methodName = "retrievingGoals";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


   /**
     * Mandatory parameter test case for addingTasks method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingTasks} integration test with mandatory parameters.")
    public void testAddingTasks() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingTasks.txt";
        String methodName = "addingTasks";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editingTasks method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingTasks} integration test with mandatory parameters.")
    public void testEditingTasks() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingTasks.txt";
        String methodName = "editingTasks";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for deletingTasks method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {deletingTasks} integration test with mandatory parameters.")
    public void testDeletingTasks() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingTasks.txt";
        String methodName = "deletingTasks";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     //     * Mandatory parameter test case for retrievingTasks method.
     //     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingTasks} integration test with mandatory parameters.")
    public void testRetrievingTasks() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingTasks.txt";
        String methodName = "retrievingTasks";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for addingOutlines method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {addingOutlines} integration test with mandatory parameters.")
    public void testAddingOutlines() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingOutlines.txt";
        String methodName = "addingOutlines";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editingOutlines method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {editingOutlines} integration test with mandatory parameters.")
    public void testEditingOutlines() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingOutlines.txt";
        String methodName = "editingOutlines";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for deletingOutlines method.
     * */

    @Test(groups = { "wso2.esb" }, description = "ToodleDo {deletingOutlines} integration test with mandatory parameters.")
    public void testDeletingOutlines() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingOutlines.txt";
        String methodName = "deletingOutlines";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrievingOutlines method.
    * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingOutlines} integration test with mandatory parameters.")
    public void testRetrievingOutlines() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingOutlines.txt";
        String methodName = "retrievingOutlines";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for addContext method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addContext} integration test with mandatory parameters.")
    public void testAddContext() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addContext.txt";
        String methodName = "addContext";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editContext method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editContext} integration test with mandatory parameters.")
    public void testEditContext() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editContext.txt";
        String methodName = "editContext";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
    /**
     * Mandatory parameter test case for deleteContext method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {deleteContext} integration test with mandatory parameters.")
    public void testDeleteContext() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deleteContext.txt";
        String methodName = "deleteContext";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrieveContext method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrieveContext} integration test with mandatory parameters.")
    public void testRetrieveContext() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrieveContext.txt";
        String methodName = "retrieveContext";
       final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for addLocation method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addLocation} integration test with mandatory parameters.")
    public void testAddLocation() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addLocation.txt";
        String methodName = "addLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for editLocation method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editLocation} integration test with mandatory parameters.")
    public void testEditLocation() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editContext.txt";
        String methodName = "editLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
    /**
     * Mandatory parameter test case for deleteLocation method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {deleteLocation} integration test with mandatory parameters.")
    public void testDeleteLocation() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deleteLocation.txt";
        String methodName = "deleteLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrieveLocation method.
     * */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrieveLocation} integration test with mandatory parameters.")
    public void testRetrieveLocation() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrieveLocation.txt";
        String methodName = "retrieveLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for addingNotes method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addingNotes} integration test with mandatory parameters.")
    public void testAddNotes() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingNotes.txt";
        String methodName = "addingNotes";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for editingNotes method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editingNotes} integration test with mandatory parameters.")
    public void testEditingNotes() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editNotes.txt";
        String methodName = "editingNotes";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
    /**
     * Mandatory parameter test case for deletingNotes method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {deletingNotes} integration test with mandatory parameters.")
    public void testDeletingNotes() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingNotes.txt";
        String methodName = "deletingNotes";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrievingNotes method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingNotes} integration test with mandatory parameters.")
    public void testRetrievingNotes() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingNotes.txt";
        String methodName = "retrievingNotes";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for addingLists method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addingLists} integration test with mandatory parameters.")
    public void testAddLists() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingLists.txt";
        String methodName = "addingLists";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for editingLists method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editingLists} integration test with mandatory parameters.")
    public void testEditingLists() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingLists.txt";
        String methodName = "editingLists";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
    /**
     * Mandatory parameter test case for deletingLists method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {deletingLists} integration test with mandatory parameters.")
    public void testDeletingLists() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingLists.txt";
        String methodName = "deletingLists";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrievingLists method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingLists} integration test with mandatory parameters.")
    public void testRetrievingLists() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingLists.txt";
        String methodName = "retrievingList";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for addingRows method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addingRows} integration test with mandatory parameters.")
    public void testAddRows() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addingRows.txt";
        String methodName = "addingRows";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Mandatory parameter test case for editingRows method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editingRows} integration test with mandatory parameters.")
    public void testEditingRows() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editingRows.txt";
        String methodName = "editingRows";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
    /**
     * Mandatory parameter test case for deletingRows method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {deletingRows} integration test with mandatory parameters.")
    public void testDeletingRows() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deletingRows.txt";
        String methodName = "deletingRows";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrievingRows method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingLists} integration test with mandatory parameters.")
    public void testRetrievingRows() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingRows.txt";
        String methodName = "retrievingRows";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional parameter test case for addContext method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addContext} integration test with mandatory parameters.")
    public void testAddContextOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addContextOptional.txt";
        String methodName = "addContext";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional parameter test case for editContext method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editContext} integration test with mandatory parameters.")
    public void testEditContextOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editContextOptional.txt";
        String methodName = "editContext";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }






    /**
     * Optional parameter test case for addLocation method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {addLocationOptional} integration test with mandatory parameters.")
    public void testAddLocationOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "addLocationOptional.txt";
        String methodName = "addLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional parameter test case for editLocation method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {editLocationOptional} integration test with mandatory parameters.")
    public void testEditLocationOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "editLocationOptional.txt";
        String methodName = "editLocation";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }



    /**
     * Optional parameter test case for retrievingNotes method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingNotesOptional} integration test with mandatory parameters.")
    public void testRetrievingNotesOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingNotesOptional.txt";
        String methodName = "retrievingNotes";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Mandatory parameter test case for retrievingLists method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingListsOptional} integration test with mandatory parameters.")
    public void testRetrievingListsOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingListsOptional.txt";
        String methodName = "retrievingList";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Optional parameter test case for retrievingRows method.
     */

    @Test(groups = {"wso2.esb"}, description = "ToodleDo {retrievingListsOptional} integration test with mandatory parameters.")
    public void testRetrievingRowsOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "retrievingRowsOptional.txt";
        String methodName = "retrievingRows";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        //String modifiedJsonString = String.format(jsonString,"");
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),jsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
}
