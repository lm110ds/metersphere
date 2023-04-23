package io.metersphere.testin.boost;

import io.metersphere.testin.bo.*;
import io.metersphere.testin.dao.TestCaseScriptInformationDao;
import io.metersphere.testin.dto.BaseDto;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.dto.faceMsFront.TestCaseScriptInformationWithEmailDto;
import io.metersphere.testin.dto.faceTestInFront.QueryObtainUserTokenRequestTestinDto;
import io.metersphere.testin.dto.faceTestInFront.QueryTheInitializationDataRequestForTheTestingTaskTestinDto;
import io.metersphere.testin.dto.faceTestInFront.QueryTheListOfProjectGroupsUnderTheEnterpriseDto;
import io.metersphere.testin.dto.faceTestInFront.QueryTheListOfTestingScriptsDto;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.util.GsonUtil;
import io.metersphere.testin.util.HttpClientUtils;
import io.metersphere.testin.util.JackJsonUtils;
import io.metersphere.track.dto.TestPlanCaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class TestInApiExecutor {

    private static final long GET_TOKEN_EXPIRED_TIME = 6L;
    private static final long GET_TOKEN_WAIT_TIME = 3L;

    public static final String ACCESS_TOKEN_PARAM = "?access_token=";
    public static final String TYPE = "&type=";
    public static final String TICKET_TYPE = "type";
    public static final String AGENT_CONFIG = "agent_config";
    public static final String EXTERNAL_USER_ID = "external_userid";
    public static final String CURSOR = "cursor";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_ID = "userid";
    public static final String CODE = "code";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String ID = "id";
    public static final String FETCH_CHILD = "fetch_child";
    public static final String CORP_ID = "corpid";
    public static final String CORP_SECRET_ID = "corpsecret";
    public static final String JOB_ID = "jobid";
    public static final String CHAT_ID = "chatid";
    public static final String OPEN_KF_ID = "open_kfid";
    @Resource
    private TestCaseScriptInformationDao testCaseScriptInformationDao;
/*
    public OfficialApiResponse genAddContactAct(ExternalContactBO externalContactBO, String corpId) {
        String secretId = genExternalSecretId(corpId);
        String requestUrl = apolloConfig.getAddExternalContactUrl();
        OfficialApiResponse response = doPostResponse(CONFIG_WK_API_QRCODE, corpId, secretId, requestUrl, externalContactBO, OfficialApiResponse.class);
        if (TOKEN_EXPIRE_ERROR.equals(Objects.requireNonNull(response).getErrorCode())) {
            redisService.delCorpSecretToken(corpId, secretId);
            genAddContactAct(externalContactBO, corpId);
        }
        if (!response.isSuccess()) {
            logLevelWarnOrErr(response.getErrorCode(), response.getErrorMsg(), WK_OFFICIAL_API_GEN_GET_CONTACT_WAY_URL);
        }
        return response;
    }


    private DepartmentInfoResult queryDepartmentInfos(String corpId, String departmentId, String secretId, String url) {
        Map<String, String> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put(ACCESS_TOKEN, genAccessToken(corpId, secretId));
        if (StringUtils.isNotEmpty(departmentId)) {
            paramMap.put(ID, departmentId);
        }
        DepartmentInfoResult officialApiResponse = doGetResponse(QUERY_DEPARTMENT_INFO, url, paramMap, DepartmentInfoResult.class);
        if (TOKEN_EXPIRE_ERROR.equals(Objects.requireNonNull(officialApiResponse).getErrorCode())) {
            redisService.delCorpSecretToken(corpId, secretId);
            queryDepartmentInfos(corpId, departmentId, secretId, url);
        }
        if (!officialApiResponse.isSuccess()) {
            logLevelWarnOrErr(officialApiResponse.getErrorCode(), officialApiResponse.getErrorMsg(), WK_QUERY_DEPARTMENT_INFOS_FAIL);
        }
        return officialApiResponse;
    }
*/
    /*public OfficialApiResponse genAddContactAct(ExternalContactBO externalContactBO, String corpId) {
        String secretId = genExternalSecretId(corpId);
        String requestUrl = apolloConfig.getAddExternalContactUrl();
        OfficialApiResponse response = doPostResponse(requestUrl, externalContactBO, OfficialApiResponse.class);
        if (TOKEN_EXPIRE_ERROR.equals(Objects.requireNonNull(response).getErrorCode())) {
            redisService.delCorpSecretToken(corpId, secretId);
            genAddContactAct(externalContactBO, corpId);
        }
        if (!response.isSuccess()) {
            logLevelWarnOrErr(response.getErrorCode(), response.getErrorMsg(), WK_OFFICIAL_API_GEN_GET_CONTACT_WAY_URL);
        }
        return response;
    }*/
    private <T> T doPostResponse(String logComment, String corpId, String secretId, String url, BaseDto baseBo, Class<T> classOfT) {
//        long begin = System.currentTimeMillis();
//        String tokenUrl = url + ACCESS_TOKEN_PARAM + genAccessToken(corpId, secretId);
        String tokenUrl = url;
        String result = HttpClientUtils.doJsonPost(tokenUrl, GsonUtil.gson.toJson(baseBo));
//        log4PostResult(logComment, baseBo, result, System.currentTimeMillis() - begin);
        return genResponse(result, classOfT);
    }
    private <T> T doPostResponse(String url, BaseDto baseBo, Class<T> classOfT) {
//        long begin = System.currentTimeMillis();
//        String tokenUrl = url + ACCESS_TOKEN_PARAM + genAccessToken(corpId, secretId);
        String tokenUrl = url;
        String result = HttpClientUtils.doJsonPost(tokenUrl, GsonUtil.gson.toJson(baseBo));
//        log4PostResult(logComment, baseBo, result, System.currentTimeMillis() - begin);
        return genResponse(result, classOfT);
    }

    private <T> T genResponse(String result, Class<T> classOfT) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return GsonUtil.gson.fromJson(result, classOfT);
    }

    private <T> T doPostUpLoadFileResponse(String logComment, String corpId, String secretId, String url, String filename, MultipartFile file, Class<T> classOfT) throws IOException {
        long begin = System.currentTimeMillis();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().addBinaryBody(filename, file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM, file.getName());
        String result = HttpClientUtils.doPost(url, builder.build(), null, "utf-8");
//        log4PostResult(logComment, url, ImmutableMap.of(corpId, corpId, secretId, secretId, Objects.requireNonNull(file.getOriginalFilename()), file.getOriginalFilename()), result, System.currentTimeMillis() - begin);
        return genResponse(result, classOfT);
    }

    private <T> T doGetResponse(String logComment, String url, Map<String, String> paramMap, Class<T> classOfT) {
        long begin = System.currentTimeMillis();
        String result = HttpClientUtils.doGet(url, paramMap);
//        log4GetResult(logComment, url, paramMap, result, System.currentTimeMillis() - begin);
        return genResponse(result, classOfT);
    }

    public List<MsProjectTestinProjectTeam> msProjectTestInProjectTeamsFromQueryTestIn(Integer goPage, Integer pageSize, MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {
        List<MsProjectTestinProjectTeam> result=new ArrayList<>();
        QueryTheListOfProjectGroupsUnderTheEnterpriseDto queryTheListOfProjectGroupsUnderTheEnterpriseDto=QueryTheListOfProjectGroupsUnderTheEnterpriseDto.
                builder()
                .apikey("cae1bfe07371a3da0bc09c3cd9c00b14")
                .mkey("usermanager")
                .op("Project.getProjectList")
                .data(
                        QueryTheListOfProjectGroupsUnderTheEnterpriseDto.RequestTestInData
                        .builder()
                                .onlineUserInfo(
                                        QueryTheListOfProjectGroupsUnderTheEnterpriseDto.OnlineUserInfo.builder()
                                        .email(msProjectTestinProjectTeamWithEmailDto.getEmail())
                                        .build()
                                )
                                .page(goPage)
                                .status(1)
                                .pageSize(pageSize)
                        .build()
                )
                .action("user")
                .timestamp(System.currentTimeMillis())
                .build();
        String requestUrl = "openapi.pro.testin.cn";
        QueryTheListOfProjectGroupsUnderTheEnterpriseBo response = doPostResponse(requestUrl, queryTheListOfProjectGroupsUnderTheEnterpriseDto, QueryTheListOfProjectGroupsUnderTheEnterpriseBo.class);
        if (response.isSuccess()) {
            QueryTheListOfProjectGroupsUnderTheEnterpriseBo.RequestTestInResultData requestTestInResultData = response.getData();
            List<QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup> list = requestTestInResultData.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup testInProjectGroup : list) {
                    //testInProjectGroup  反序列化将一个对象转化为另一个对象
                    result.add(JackJsonUtils.obj2pojo(testInProjectGroup, MsProjectTestinProjectTeam.class));
                }
            }
        }
        return result;
    }

    public List<TestCaseScriptInformation> queryTestCaseScriptInformationFromQueryTestIn(int goPage, int pageSize, Integer testInProjectId, TestCaseScriptInformationWithEmailDto testCaseScriptInformation) {
//        List<TestCaseScriptInformation> result=new ArrayList<>();
        QueryTheListOfTestingScriptsDto queryTheListOfTestingScriptsDto=QueryTheListOfTestingScriptsDto.
                builder()
                .apikey("cae1bfe07371a3da0bc09c3cd9c00b14")
                .mkey("script")
                .op("Script.listScriptFile")
                .data(
                        QueryTheListOfTestingScriptsDto.QueryTestinScriptListParameterData
                                .builder()
                                .onlineUserInfo(
                                    QueryTheListOfTestingScriptsDto.OnlineUserInfo.builder()
                                            .email(testCaseScriptInformation.getEmail())
                                    .build()
                                )
                                .scriptDesc(StringUtils.isNotEmpty(testCaseScriptInformation.getScriptCreateDesc())?testCaseScriptInformation.getScriptCreateDesc():null)
                                .projectId(testInProjectId)           //"用例关联的项目-项目关联的项目组"
                                .startPageNo(goPage)
                                .pageSize(pageSize)
                                .build()
                )
                .action("script")
                .build();
        String requestUrl = "openapi.pro.testin.cn";
        return GetQueryTestCaseScriptInformationFromQueryTestInUrl(requestUrl, queryTheListOfTestingScriptsDto, QueryTestInScriptListParameterDataBo.class);
        //范例改到表格上
        /*QueryTestInScriptListParameterDataBo response = doPostResponse(requestUrl, queryTheListOfTestingScriptsDto, QueryTestInScriptListParameterDataBo.class);
        if (response.isSuccess()) {
            QueryTestInScriptListParameterDataBo.GetScriptRequestTestInData getScriptRequestTestInData = response.getData();
            List<QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest> list = getScriptRequestTestInData.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest scriptInformationResultFromRequest : list) {
                    result.add(
                            TestCaseScriptInformation.builder()
                            .scriptId(scriptInformationResultFromRequest.getScriptid())
                            .scriptNo(scriptInformationResultFromRequest.getScriptNo())
                            .scriptCreateUser(scriptInformationResultFromRequest.getScriptCreateUser())

                            .testInProjectId(scriptInformationResultFromRequest.getProjectId())

                            .scriptUpdateUserid(scriptInformationResultFromRequest.getScriptUpdateUserid())

                            .scriptUpdateDesc(scriptInformationResultFromRequest.getScriptUpdateDesc())

                            .channelId(scriptInformationResultFromRequest.getChannelId())
                            .appinfo(scriptInformationResultFromRequest.getAppinfo())
                            .scriptCreateTime(scriptInformationResultFromRequest.getScriptCreateTime())
                            .scriptUpdateTime(scriptInformationResultFromRequest.getScriptUpdateTime())
                            .build()
                    );
                    //testInProjectGroup  反序列化将一个对象转化为另一个对象
                    //result.add(JackJsonUtils.obj2pojo(testInProjectGroup, TestCaseScriptInformation.class));
                }
            }
        }
        return result;*/
    }

    private List<TestCaseScriptInformation> GetQueryTestCaseScriptInformationFromQueryTestInUrl(String requestUrl, QueryTheListOfTestingScriptsDto queryTheListOfTestingScriptsDto, Class<QueryTestInScriptListParameterDataBo> queryTestInScriptListParameterDataBoClass) {
        List<TestCaseScriptInformation> result=new ArrayList<>();
        QueryTestInScriptListParameterDataBo response = doPostResponse(requestUrl, queryTheListOfTestingScriptsDto, queryTestInScriptListParameterDataBoClass);
        if (response.isSuccess()) {
            QueryTestInScriptListParameterDataBo.GetScriptRequestTestInData getScriptRequestTestInData = response.getData();
            List<QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest> list = getScriptRequestTestInData.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest scriptInformationResultFromRequest : list) {
                    result.add(
                            TestCaseScriptInformation.builder()
                                    .scriptId(scriptInformationResultFromRequest.getScriptid())
                                    .scriptNo(scriptInformationResultFromRequest.getScriptNo())
                                    .scriptCreateUser(scriptInformationResultFromRequest.getScriptCreateUser())

                                    .testInProjectId(scriptInformationResultFromRequest.getProjectId())

                                    .scriptUpdateUserid(scriptInformationResultFromRequest.getScriptUpdateUserid())

                                    .scriptUpdateDesc(scriptInformationResultFromRequest.getScriptUpdateDesc())

                                    .channelId(scriptInformationResultFromRequest.getChannelId())
                                    .appinfo(scriptInformationResultFromRequest.getAppinfo())
                                    .scriptCreateTime(scriptInformationResultFromRequest.getScriptCreateTime())
                                    .scriptUpdateTime(scriptInformationResultFromRequest.getScriptUpdateTime())
                                    .build()
                    );
                }
            }
        }
        return result;
    }
    public List<TestCaseScriptInformation> GetExcelQueryTestCaseScriptInformationFromQueryTestInUrl(String requestUrl, QueryTheListOfTestingScriptsDto queryTheListOfTestingScriptsDto, Class<QueryTestInScriptListParameterDataFromExcelBo> queryTestInScriptListParameterDataFromExcelBoClass) {
        List<TestCaseScriptInformation> result=new ArrayList<>();
        QueryTestInScriptListParameterDataFromExcelBo response = doPostResponse(requestUrl, queryTheListOfTestingScriptsDto, QueryTestInScriptListParameterDataFromExcelBo.class);
        if (response.isSuccess()) {
            QueryTestInScriptListParameterDataFromExcelBo.GetScriptRequestTestInData getScriptRequestTestInData = response.getData();
            List<QueryTestInScriptListParameterDataFromExcelBo.ScriptInformationResultFromRequest> list = getScriptRequestTestInData.getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (QueryTestInScriptListParameterDataFromExcelBo.ScriptInformationResultFromRequest scriptInformationResultFromRequest : list) {
                    result.add(
                            TestCaseScriptInformation.builder()
                                    .scriptId(scriptInformationResultFromRequest.getScriptid())
                                    .scriptNo(scriptInformationResultFromRequest.getScriptNo())
                                    .scriptCreateUser(scriptInformationResultFromRequest.getScriptCreateUser())

                                    .testInProjectId(scriptInformationResultFromRequest.getProjectId())

                                    .scriptUpdateUserid(scriptInformationResultFromRequest.getScriptUpdateUserid())

                                    .scriptUpdateDesc(scriptInformationResultFromRequest.getScriptUpdateDesc())

                                    .channelId(scriptInformationResultFromRequest.getChannelId())
                                    .appinfo(scriptInformationResultFromRequest.getAppinfo())
                                    .scriptCreateTime(scriptInformationResultFromRequest.getScriptCreateTime())
                                    .scriptUpdateTime(scriptInformationResultFromRequest.getScriptUpdateTime())
                                    .build()
                    );
                }
            }
        }
        return result;
    }

    public String ObtainTheUserTokenForTheTestInSystem(Integer testInProjectId, EmailDto emailDto) {
        QueryObtainUserTokenRequestTestinDto queryObtainUserTokenRequestTestinDto = QueryObtainUserTokenRequestTestinDto.builder()
                .apikey("cae1bfe07371a3da0bc09c3cd9c00b14")
                .mkey("UserManager")
                .op("User.getToken")
                .data(
                        QueryObtainUserTokenRequestTestinDto.RequestTestInData
                                .builder()
                                .onlineUserInfo(
                                        QueryObtainUserTokenRequestTestinDto.OnlineUserInfo.builder()
                                                .email(emailDto.getEmail())
                                                .projectid(testInProjectId)
                                                .build()
                                )
                                .build()
                )
                .action("user")
                .timestamp(System.currentTimeMillis())
                .build();
        String requestUrl = "openapi.pro.testin.cn";
        QueryObtainUserTokenRequestTestinResultBo response = doPostResponse(requestUrl, queryObtainUserTokenRequestTestinDto, QueryObtainUserTokenRequestTestinResultBo.class);
        if (response.isSuccess()) {
            QueryObtainUserTokenRequestTestinResultBo.RequestTestInResultData requestTestInResultData = response.getData();
            if (requestTestInResultData != null) {
               return requestTestInResultData.getResult();
            }
        }
        return null;
    }

    public String InitiateDataRequestForTestingTaskTestin(String testPlanId, String callbackUrl, List<TestPlanCaseDTO> testPlanCaseDTOList, EmailDto emailDto, String token, MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        String reqId= "";
        List<String> caseIdListCollect = testPlanCaseDTOList.parallelStream().map(TestPlanCaseDTO::getCaseId).collect(Collectors.toList());
        //List<Integer> scriptNoListCollect = new ArrayList<>();
        List<QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Scripts> scriptsList=new ArrayList<>();
        for (String caseId : caseIdListCollect) {
            TestCaseScriptInformation testCaseScriptInformation = testCaseScriptInformationDao.queryTestCaseScriptInformationByTestCaseId(caseId);
            Integer scriptNo = testCaseScriptInformation.getScriptNo();
            //scriptNoListCollect.add(scriptNo);
            //Standard 各个参数怎么设置
            scriptsList.add(QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Scripts.builder()
                    .scriptNo(scriptNo)
                    .standard(
                            QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Standard.builder()
                                    .coverInstall(0)
                                    .cleanData(0)
                                    .keepApp(0)
                                    .build()
                    )
                    .build());
        }

        QueryTheInitializationDataRequestForTheTestingTaskTestinDto queryTheInitializationDataRequestForTheTestingTaskTestinDto=QueryTheInitializationDataRequestForTheTestingTaskTestinDto.builder()
                .apikey("cae1bfe07371a3da0bc09c3cd9c00b14")
                .mkey("UserManager")
                .op("User.getToken")
                .sid(token)
                .data(
                        QueryTheInitializationDataRequestForTheTestingTaskTestinDto.QueryTheInitializationDataRequestForTheTestingTaskTestinRequestData
                                .builder()
                                .additionalInfo(testPlanId)
                                .callbackUrl(callbackUrl)
                                .bizCode(4001)
                                .extendedChannel("yiqiMS")
                                .scripts(scriptsList)
                                .onlineUserInfo(QueryTheInitializationDataRequestForTheTestingTaskTestinDto.OnlineUserInfo.builder()
                                        .email(emailDto.getEmail())
                                        .build())
                                .build()
                )
                .action("user")
                .timestamp(System.currentTimeMillis())
                .build();
        String requestUrl = "openapi.pro.testin.cn";
        QueryTheInitializationDataRequestForTheTestingTaskTestinBo response = doPostResponse(requestUrl, queryTheInitializationDataRequestForTheTestingTaskTestinDto, QueryTheInitializationDataRequestForTheTestingTaskTestinBo.class);
        if (response.isSuccess()) {
            QueryTheInitializationDataRequestForTheTestingTaskTestinBo.QueryTheInitializationDataRequestForTheTestingTaskTestinResultData requestTestInResultData = response.getData();
            if (requestTestInResultData != null) {
                return requestTestInResultData.getResult();
            }
        }
        return reqId;
    }
}
