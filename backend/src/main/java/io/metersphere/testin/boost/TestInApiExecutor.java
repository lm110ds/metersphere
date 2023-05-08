package io.metersphere.testin.boost;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.LogUtil;
import io.metersphere.commons.utils.SessionUtils;
import io.metersphere.dto.UserDTO;
import io.metersphere.service.UserService;
import io.metersphere.testin.bo.*;
import io.metersphere.testin.dao.TestCaseScriptInformationDao;
import io.metersphere.testin.dto.BaseDto;
import io.metersphere.testin.dto.faceMsFront.EmailDto;
import io.metersphere.testin.dto.faceMsFront.MsProjectTestinProjectTeamWithEmailDto;
import io.metersphere.testin.dto.faceMsFront.TestCaseScriptInformationWithEmailDto;
import io.metersphere.testin.dto.faceMsFront.ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto;
import io.metersphere.testin.dto.faceTestInFront.*;
import io.metersphere.testin.entity.MsProjectTestinProjectTeam;
import io.metersphere.testin.entity.TestCaseScriptInformation;
import io.metersphere.testin.entity.TestPlanTestinTask;
import io.metersphere.testin.service.TestCaseScriptInformationService;
import io.metersphere.testin.service.TestPlanTestinTaskService;
import io.metersphere.testin.util.GsonUtil;
import io.metersphere.testin.util.HttpClientUtils;
import io.metersphere.testin.util.JackJsonUtils;
import io.metersphere.track.dto.TestPlanCaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestInApiExecutor {

    @Resource
    @Lazy
    private TestCaseScriptInformationService testCaseScriptInformationService;
    @Resource
    @Lazy
    private TestPlanTestinTaskService testPlanTestinTaskService;

    @Resource
    private UserService userService;

    private <T> T doPostResponse(String url, BaseDto baseBo, Class<T> classOfT) {

        String tokenUrl = url;
        String result = HttpClientUtils.doJsonPost(tokenUrl, GsonUtil.gson.toJson(baseBo));
        return genResponse(result, classOfT);
    }

    private <T> T genResponse(String result, Class<T> classOfT) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return GsonUtil.gson.fromJson(result, classOfT);
    }

    public String getEmailFromMs(){
        String userId = SessionUtils.getUserId();
        UserDTO admin = userService.getUserInfo(userId);
        if (admin==null||StringUtils.isBlank(admin.getEmail())) {
            return "hanlei32@faw.com.cn";
        }
        return admin.getEmail();
    }
    public static final String requestUrl = "xxx";
    public static final String apikey = "xxx";
//    public static final String apikey = "xxx";
//    public static final Integer OnCallProjectId = xxx;
    public static final Integer OnCallProjectId = 0;
    public List<MsProjectTestinProjectTeam> msProjectTestInProjectTeamsFromQueryTestIn(Integer goPage, Integer pageSize, MsProjectTestinProjectTeamWithEmailDto msProjectTestinProjectTeamWithEmailDto) {

        List<MsProjectTestinProjectTeam> result=new ArrayList<>();
        QueryTheListOfProjectGroupsUnderTheEnterpriseDto queryTheListOfProjectGroupsUnderTheEnterpriseDto=QueryTheListOfProjectGroupsUnderTheEnterpriseDto.
                builder()
                .apikey(apikey)
                .mkey("Usermanager")
                .op("Project.getProjectList")
                .data(
                        QueryTheListOfProjectGroupsUnderTheEnterpriseDto.RequestTestInData
                        .builder()
                                .onlineUserInfo(
                                        QueryTheListOfProjectGroupsUnderTheEnterpriseDto.OnlineUserInfo.builder()
                                        .email(StringUtils.isNotBlank(msProjectTestinProjectTeamWithEmailDto.getEmail()) ?msProjectTestinProjectTeamWithEmailDto.getEmail():getEmailFromMs())
                                        .projectid(OnCallProjectId)
                                        .build()
                                )
                                .page(1)
                                .status(1)
                                .pageSize(999)
                        .build()
                )
                .action("user")
                .timestamp(System.currentTimeMillis())
                .build();
        QueryTheListOfProjectGroupsUnderTheEnterpriseBo response = doPostResponse(requestUrl, queryTheListOfProjectGroupsUnderTheEnterpriseDto, QueryTheListOfProjectGroupsUnderTheEnterpriseBo.class);
        if (response.isSuccess()) {
            QueryTheListOfProjectGroupsUnderTheEnterpriseBo.RequestTestInResultData requestTestInResultData = response.getData();
            Integer totalPage =requestTestInResultData.getTotalPage();
            List<QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup> list = requestTestInResultData.getList();

            if (CollectionUtils.isNotEmpty(list)) {
                for (QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup testInProjectGroup : list) {
                    MsProjectTestinProjectTeam msProjectTestinProjectTeam =MsProjectTestinProjectTeam.builder()
                            .eid(testInProjectGroup.getEid())
                            .createTime(testInProjectGroup.getCreateTime())
                            .name(testInProjectGroup.getName())
                            .testInProjectId(testInProjectGroup.getProjectid())
                            .status(testInProjectGroup.getStatus())
                            .thirdPartyProjectid(testInProjectGroup.getThirdPartyProjectid())
                            .extend(testInProjectGroup.getExtend())
                            .productNo(testInProjectGroup.getProductNo())
                            .descr(testInProjectGroup.getDescr())
                            .build();
                    result.add(msProjectTestinProjectTeam);
                }
            }
            for (int i = requestTestInResultData.getPage()+1; i <= totalPage; i++) {
                QueryTheListOfProjectGroupsUnderTheEnterpriseDto queryTheListOfProjectGroupsUnderTheEnterpriseDtoLoopI=QueryTheListOfProjectGroupsUnderTheEnterpriseDto.
                        builder()
                        .apikey(apikey)
                        .mkey("Usermanager")
                        .op("Project.getProjectList")
                        .data(
                                QueryTheListOfProjectGroupsUnderTheEnterpriseDto.RequestTestInData
                                        .builder()
                                        .onlineUserInfo(
                                                QueryTheListOfProjectGroupsUnderTheEnterpriseDto.OnlineUserInfo.builder()
                                                        .email(StringUtils.isNotBlank(msProjectTestinProjectTeamWithEmailDto.getEmail()) ?msProjectTestinProjectTeamWithEmailDto.getEmail():getEmailFromMs())
                                                        .projectid(OnCallProjectId)
                                                        .build()
                                        )
                                        .page(i)
                                        .status(1)
                                        .pageSize(999)
                                        .build()
                        )
                        .action("user")
                        .timestamp(System.currentTimeMillis())
                        .build();
                QueryTheListOfProjectGroupsUnderTheEnterpriseBo responseLoopI = doPostResponse(requestUrl, queryTheListOfProjectGroupsUnderTheEnterpriseDtoLoopI, QueryTheListOfProjectGroupsUnderTheEnterpriseBo.class);
                if (responseLoopI.isSuccess()) {
                    QueryTheListOfProjectGroupsUnderTheEnterpriseBo.RequestTestInResultData requestTestInResultDataLoopI = responseLoopI.getData();
//                    Integer totalPage =requestTestInResultData.getTotalPage();
                    List<QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup> listLoopI = requestTestInResultDataLoopI.getList();

                    if (CollectionUtils.isNotEmpty(listLoopI)) {
                        for (QueryTheListOfProjectGroupsUnderTheEnterpriseBo.TestInProjectGroup testInProjectGroup : listLoopI) {
                            MsProjectTestinProjectTeam msProjectTestinProjectTeam =MsProjectTestinProjectTeam.builder()
                                    .eid(testInProjectGroup.getEid())
                                    .createTime(testInProjectGroup.getCreateTime())
                                    .name(testInProjectGroup.getName())
                                    .testInProjectId(testInProjectGroup.getProjectid())
                                    .status(testInProjectGroup.getStatus())
                                    .thirdPartyProjectid(testInProjectGroup.getThirdPartyProjectid())
                                    .extend(testInProjectGroup.getExtend())
                                    .productNo(testInProjectGroup.getProductNo())
                                    .descr(testInProjectGroup.getDescr())
                                    .build();
                            result.add(msProjectTestinProjectTeam);
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<TestCaseScriptInformation> queryTestCaseScriptInformationFromQueryTestIn(int goPage, int pageSize, Integer testInProjectId, TestCaseScriptInformationWithEmailDto testCaseScriptInformation) {
        List<TestCaseScriptInformation> result=new ArrayList<>();
        QueryTheListOfTestingScriptsDto queryTheListOfTestingScriptsDto=QueryTheListOfTestingScriptsDto.
                builder()
                .apikey(apikey)
                .mkey("script")
                .op("Script.listScriptFile")
                .data(
                        QueryTheListOfTestingScriptsDto.QueryTestinScriptListParameterData
                                .builder()
                                .onlineUserInfo(
                                    QueryTheListOfTestingScriptsDto.OnlineUserInfo.builder()
                                            .email(StringUtils.isNotBlank(testCaseScriptInformation.getEmail()) ?testCaseScriptInformation.getEmail():getEmailFromMs())
                                    .build()
                                )
                                .scriptDesc(StringUtils.isNotEmpty(testCaseScriptInformation.getScriptDesc())?testCaseScriptInformation.getScriptDesc():null)
                                .scriptType(1)
                                .scriptNo(null!=testCaseScriptInformation.getScriptNo()?testCaseScriptInformation.getScriptNo():null)
                                .projectId(testInProjectId)
                                .startPageNo(1)
                                .pageSize(999)
                                .build()
                )
                .action("script")
                .timestamp(System.currentTimeMillis())
                .build();
//        String requestUrl = "openapi.pro.testin.cn";
        QueryTestInScriptListParameterDataBo response = doPostResponse(requestUrl, queryTheListOfTestingScriptsDto, QueryTestInScriptListParameterDataBo.class);
        if (response.isSuccess()) {
            QueryTestInScriptListParameterDataBo.GetScriptRequestTestInData getScriptRequestTestInData = response.getData();
            Integer totalPage =getScriptRequestTestInData.getTotalPage();
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
                                    .appName(scriptInformationResultFromRequest.getAppinfo()!=null?scriptInformationResultFromRequest.getAppinfo().getAppName():null)
                                    .scriptName(scriptInformationResultFromRequest.getScriptName())
                                    .channelId(scriptInformationResultFromRequest.getChannelId())
                                    .scriptCreateTime(scriptInformationResultFromRequest.getScriptCreateTime())
                                    .scriptUpdateTime(scriptInformationResultFromRequest.getScriptUpdateTime())
                                    .build()
                    );
                }
                for (int i = getScriptRequestTestInData.getPage()+1; totalPage!=null&&i <= totalPage; i++) {
                    QueryTheListOfTestingScriptsDto queryTheListOfTestingScriptsDtoLoopI=QueryTheListOfTestingScriptsDto.
                            builder()
                            .apikey(apikey)
                            .mkey("script")
                            .op("Script.listScriptFile")
                            .data(
                                    QueryTheListOfTestingScriptsDto.QueryTestinScriptListParameterData
                                            .builder()
                                            .onlineUserInfo(
                                                    QueryTheListOfTestingScriptsDto.OnlineUserInfo.builder()
                                                            .email(StringUtils.isNotBlank(testCaseScriptInformation.getEmail()) ?testCaseScriptInformation.getEmail():getEmailFromMs())
                                                            .build()
                                            )
                                            .scriptDesc(StringUtils.isNotEmpty(testCaseScriptInformation.getScriptDesc())?testCaseScriptInformation.getScriptDesc():null)
                                            .scriptType(1)
                                            .scriptNo(null!=testCaseScriptInformation.getScriptNo()?testCaseScriptInformation.getScriptNo():null)
                                            .projectId(testInProjectId)
                                            .startPageNo(i)
                                            .pageSize(999)
                                            .build()
                            )
                            .action("script")
                            .timestamp(System.currentTimeMillis())
                            .build();
                    QueryTestInScriptListParameterDataBo responseLoopI = doPostResponse(requestUrl, queryTheListOfTestingScriptsDtoLoopI, QueryTestInScriptListParameterDataBo.class);
                    if (responseLoopI.isSuccess()) {
                        QueryTestInScriptListParameterDataBo.GetScriptRequestTestInData getScriptRequestTestInDataLoopI = responseLoopI.getData();
                        List<QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest> listLoopI = getScriptRequestTestInDataLoopI.getList();
                        if (CollectionUtils.isNotEmpty(listLoopI)) {
                            for (QueryTestInScriptListParameterDataBo.ScriptInformationResultFromRequest scriptInformationResultFromRequest : listLoopI) {
                                result.add(
                                        TestCaseScriptInformation.builder()
                                                .scriptId(scriptInformationResultFromRequest.getScriptid())
                                                .scriptNo(scriptInformationResultFromRequest.getScriptNo())
                                                .scriptCreateUser(scriptInformationResultFromRequest.getScriptCreateUser())

                                                .testInProjectId(scriptInformationResultFromRequest.getProjectId())

                                                .scriptUpdateUserid(scriptInformationResultFromRequest.getScriptUpdateUserid())

                                                .scriptUpdateDesc(scriptInformationResultFromRequest.getScriptUpdateDesc())
                                                .appName(scriptInformationResultFromRequest.getAppinfo()!=null?scriptInformationResultFromRequest.getAppinfo().getAppName():null)
                                                .scriptName(scriptInformationResultFromRequest.getScriptName())
                                                .channelId(scriptInformationResultFromRequest.getChannelId())
                                                .scriptCreateTime(scriptInformationResultFromRequest.getScriptCreateTime())
                                                .scriptUpdateTime(scriptInformationResultFromRequest.getScriptUpdateTime())
                                                .build()
                                );
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public String ObtainTheUserTokenForTheTestInSystem(Integer testInProjectId, String email) {
        String token = null;
        QueryObtainUserTokenRequestTestinDto queryObtainUserTokenRequestTestinDto = QueryObtainUserTokenRequestTestinDto.builder()
                .apikey(apikey)
                .mkey("UserManager")
                .op("User.getToken")
                .data(
                        QueryObtainUserTokenRequestTestinDto.RequestTestInData
                                .builder()
                                .onlineUserInfo(
                                        QueryObtainUserTokenRequestTestinDto.OnlineUserInfo.builder()
                                                .email(StringUtils.isNotBlank(email) ?email:getEmailFromMs())
                                                .projectid(testInProjectId)
                                                .build()
                                )
                                .build()
                )
                .action("user")
                .timestamp(System.currentTimeMillis())
                .build();
        QueryObtainUserTokenRequestTestinResultBo response = doPostResponse(requestUrl, queryObtainUserTokenRequestTestinDto, QueryObtainUserTokenRequestTestinResultBo.class);
        if (response.isSuccess()) {
            QueryObtainUserTokenRequestTestinResultBo.RequestTestInResultData requestTestInResultData = response.getData();
            if (requestTestInResultData != null) {
                token=requestTestInResultData.getResult();
            }
        }else {
            token=ObtainTheUserTokenForTheTestInSystem(testInProjectId,email);
        }
        return token;
    }

    public String InitiateDataRequestForTestingTaskTestin(String testPlanId, String callbackUrl, List<TestPlanCaseDTO> testPlanCaseDTOList, EmailDto emailDto, String token, MsProjectTestinProjectTeam msProjectTestinProjectTeam) {
        String reqId= "";
        List<String> caseIdListCollect = testPlanCaseDTOList.parallelStream().map(TestPlanCaseDTO::getCaseId).collect(Collectors.toList());
        List<QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Scripts> scriptsList=new ArrayList<>();
        for (String caseId : caseIdListCollect) {
            TestCaseScriptInformation testCaseScriptInformation = this.testCaseScriptInformationService.queryTestCaseScriptInformationByTestCaseId(caseId);
            Integer scriptNo = testCaseScriptInformation.getScriptNo();
            scriptsList.add(QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Scripts.builder()
                    .scriptNo(scriptNo)
                    .standard(
                            QueryTheInitializationDataRequestForTheTestingTaskTestinDto.Standard.builder()
                                    .coverInstall(testCaseScriptInformation.getCoverInstall())
                                    .cleanData(testCaseScriptInformation.getCleanData())
                                    .keepApp(testCaseScriptInformation.getKeepApp())
                                    .build()
                    )
                    .build());
        }

        QueryTheInitializationDataRequestForTheTestingTaskTestinDto queryTheInitializationDataRequestForTheTestingTaskTestinDto=QueryTheInitializationDataRequestForTheTestingTaskTestinDto.builder()
                .apikey(apikey)
                .mkey("realtest")
                .op("Task.initData")
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
                                        .email(StringUtils.isNotBlank(emailDto.getEmail()) ?emailDto.getEmail():getEmailFromMs())
                                        .build())
                                .build()
                )
                .action("app")
                .timestamp(System.currentTimeMillis())
                .build();
        QueryTheInitializationDataRequestForTheTestingTaskTestinBo response = doPostResponse(requestUrl, queryTheInitializationDataRequestForTheTestingTaskTestinDto, QueryTheInitializationDataRequestForTheTestingTaskTestinBo.class);
        if (response.isSuccess()) {
            QueryTheInitializationDataRequestForTheTestingTaskTestinBo.QueryTheInitializationDataRequestForTheTestingTaskTestinResultData requestTestInResultData = response.getData();
            if (requestTestInResultData != null) {
                return requestTestInResultData.getResult();
            }
        }
        return reqId;
    }

    public List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask> queryToObtainTheExecutionDetailsOfTheTestingReport(Integer goPage, Integer pageSize, ToObtainTheExecutionDetailsOfTheTestingReportGenerateDto toObtainTheExecutionDetailsOfTheTestingReportGenerateDto) {
        String token = ObtainTheUserTokenForTheTestInSystem(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getProjectid(), toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getEmail());
        if (StringUtils.isEmpty(token)){
            MSException.throwException("获取token异常,请稍后重试");
            return null;
        }
        TestPlanTestinTask testPlanTestinTask = this.testPlanTestinTaskService.queryById(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getTestPlanId());
        if (Objects.isNull(testPlanTestinTask)){
            MSException.throwException("提测任务为空,请先去启动testin任务提测");
            return null;
        }
        String summaryinfo = testPlanTestinTask.getSummaryinfo();
        LogUtil.info(" summaryinfo : {}",JSON.toJSONString(summaryinfo));
        JSONArray objects = JSONArray.parseArray(summaryinfo);
        List<CallBackTaskTestingOrCompletionMessageRequestDto.SummaryInfo> summaryInfoList = objects.toJavaList(CallBackTaskTestingOrCompletionMessageRequestDto.SummaryInfo.class);

        List<Integer> resultCategoryListCollect = summaryInfoList.parallelStream().map(CallBackTaskTestingOrCompletionMessageRequestDto.SummaryInfo::getResultCategory).collect(Collectors.toList());
        QueryToObtainTheExecutionDetailsOfTheTestingReportDto queryToObtainTheExecutionDetailsOfTheExecutionDetail=QueryToObtainTheExecutionDetailsOfTheTestingReportDto
                .builder()
                .apikey(apikey)
                .mkey("realtest")
                .op("Report.list")
                .sid(token)
                .data(
                        QueryToObtainTheExecutionDetailsOfTheTestingReportDto.RequestTestInData
                                .builder()
                                .projectid(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getProjectid())
                                .taskid(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getTaskid())
                                .resultCategorys(resultCategoryListCollect)
                                .page(1)
                                .pageSize(999)
                                .onlineUserInfo(QueryToObtainTheExecutionDetailsOfTheTestingReportDto.OnlineUserInfo.builder()
                                        .email(StringUtils.isNotBlank(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getEmail()) ?toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getEmail():getEmailFromMs())
                                        .build())
                                .build()
                )
                .action("analysis")
                .timestamp(System.currentTimeMillis())
                .build();
        QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo response = doPostResponse(requestUrl, queryToObtainTheExecutionDetailsOfTheExecutionDetail, QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.class);
        LogUtil.info(" query Report.list response : {}",JSON.toJSONString(response));
        if (response.isSuccess()) {
            QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.RequestTestInResultData requestTestInResultData = response.getData();
            if (requestTestInResultData != null) {
                Integer totalPage =requestTestInResultData.getTotalPage();
                for (int i = requestTestInResultData.getPage()+1; i <= totalPage; i++) {
                    QueryToObtainTheExecutionDetailsOfTheTestingReportDto queryToObtainTheExecutionDetailsOfTheExecutionDetailLoopI=QueryToObtainTheExecutionDetailsOfTheTestingReportDto
                            .builder()
                            .apikey(apikey)
                            .mkey("realtest")
                            .op("Report.list")
                            .sid(token)
                            .data(
                                    QueryToObtainTheExecutionDetailsOfTheTestingReportDto.RequestTestInData
                                            .builder()
                                            .projectid(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getProjectid())
                                            .taskid(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getTaskid())
                                            .resultCategorys(resultCategoryListCollect)
                                            .page(i)
                                            .pageSize(999)
                                            .onlineUserInfo(QueryToObtainTheExecutionDetailsOfTheTestingReportDto.OnlineUserInfo.builder()
                                                    .email(StringUtils.isNotBlank(toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getEmail()) ?toObtainTheExecutionDetailsOfTheTestingReportGenerateDto.getEmail():getEmailFromMs())
                                                    .build())
                                            .build()
                            )
                            .action("analysis")
                            .timestamp(System.currentTimeMillis())
                            .build();
                    QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo responseLoopI = doPostResponse(requestUrl, queryToObtainTheExecutionDetailsOfTheExecutionDetailLoopI, QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.class);
                    if (responseLoopI.isSuccess()) {
                        QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.RequestTestInResultData requestTestInResultDataLoopI = responseLoopI.getData();
                        List<QueryToObtainTheExecutionDetailsOfTheTestingReportGenerateBo.TestInProjectGroupTask> listLoopI = requestTestInResultDataLoopI.getList();

                        if (CollectionUtils.isNotEmpty(listLoopI)) {
                            requestTestInResultData.getList().addAll(listLoopI);
                        }
                    }
                }
                return requestTestInResultData.getList();
            }
        }
        return null;
    }
}
