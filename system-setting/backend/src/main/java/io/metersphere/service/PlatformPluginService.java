package io.metersphere.service;

import io.metersphere.api.Platform;
import io.metersphere.api.PluginMetaInfo;
import io.metersphere.base.domain.PluginWithBLOBs;
import io.metersphere.base.domain.ServiceIntegration;
import io.metersphere.base.mapper.PluginMapper;
import io.metersphere.commons.constants.PluginScenario;
import io.metersphere.commons.utils.BeanUtils;
import io.metersphere.commons.utils.JSON;
import io.metersphere.commons.utils.LogUtil;
import io.metersphere.commons.utils.SessionUtils;
import io.metersphere.domain.GetOptionRequest;
import io.metersphere.domain.PlatformRequest;
import io.metersphere.domain.SelectOption;
import io.metersphere.dto.PlatformProjectOptionRequest;
import io.metersphere.loader.PlatformPluginManager;
import io.metersphere.request.IntegrationRequest;
import io.metersphere.utils.PluginManagerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PlatformPluginService {

    @Resource
    private BasePluginService basePluginService;
    @Resource
    private PluginMapper pluginMapper;
    @Resource
    private BaseIntegrationService baseIntegrationService;

    private PlatformPluginManager pluginManager;

    public PluginWithBLOBs addPlatformPlugin(MultipartFile file) {
        if (pluginManager != null) {
            pluginManager = new PlatformPluginManager();
        }
        String id = UUID.randomUUID().toString();

        PluginManagerUtil.loadPlugin(id, pluginManager, file);
        PluginMetaInfo pluginMetaInfo = pluginManager.getImplInstance(id, PluginMetaInfo.class);

        Map map = JSON.parseMap(pluginMetaInfo.getFrontendMetaData());
        map.put("id", id);
        map.put("key", pluginMetaInfo.getKey());

        PluginWithBLOBs plugin = new PluginWithBLOBs();
        plugin.setId(id);
        plugin.setName(file.getOriginalFilename());
        plugin.setPluginId(pluginMetaInfo.getKey() + "-" + pluginMetaInfo.getVersion());
        plugin.setScriptId(pluginMetaInfo.getKey());
        plugin.setSourcePath("");
//      plugin.setFormOption(item.getFormOption());
        plugin.setFormScript(JSON.toJSONString(map));
        plugin.setClazzName("");
        plugin.setSourceName(file.getOriginalFilename());
        plugin.setJmeterClazz("");
        plugin.setExecEntry("");
        plugin.setCreateUserId(SessionUtils.getUserId());
        plugin.setXpack(pluginMetaInfo.isXpack());
        plugin.setScenario(PluginScenario.platform.name());
        return plugin;
    }

    /**
     * 查询所有平台插件并加载
     */
    public void loadPlatFormPlugins() {
        pluginManager = new PlatformPluginManager();
        List<PluginWithBLOBs> plugins = basePluginService.getPlugins(PluginScenario.platform.name());
        PluginManagerUtil.loadPlugins(pluginManager, plugins);
    }

    public void getPluginResource(String pluginId, String name, HttpServletResponse response) {
        InputStream inputStream = pluginManager.getClassLoader(pluginId)
                .getResourceAsStream(name);
        getImage(inputStream, response);
    }

    public Object getIntegrationInfo() {
        List<PluginWithBLOBs> plugins = basePluginService.getPlugins(PluginScenario.platform.name());
        List<Map> configs = new ArrayList<>();
        plugins.forEach(item -> configs.add(getFrontendMetaDataConfig(item, "serviceIntegration")));
        return configs;
    }

    public Map getProjectInfo(String key) {
        List<PluginWithBLOBs> plugins = basePluginService.getPlugins(PluginScenario.platform.name());
        for (PluginWithBLOBs plugin : plugins) {
            if (StringUtils.equals(plugin.getScriptId(), key)) {
                return getFrontendMetaDataConfig(plugin, "projectConfig");
            }
        }
        return null;
    }

    public List<SelectOption> getProjectOption(PlatformProjectOptionRequest request) {
        IntegrationRequest integrationRequest = new IntegrationRequest();
        BeanUtils.copyBean(integrationRequest, request);
        ServiceIntegration serviceIntegration = baseIntegrationService.get(integrationRequest);

        PlatformRequest platformRequest = new PlatformRequest();
        platformRequest.setIntegrationConfig(serviceIntegration.getConfiguration());

        Platform platform = pluginManager.getPlatformByKey(request.getPlatform(), platformRequest);
        GetOptionRequest getOptionRequest = new GetOptionRequest();
        getOptionRequest.setOptionMethod(request.getOptionMethod());
        getOptionRequest.setProjectConfig(request.getProjectConfig());
        try {
            return platform.getProjectOptions(getOptionRequest);
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public Map getFrontendMetaDataConfig(PluginWithBLOBs plugin, String configName) {
        Map metaData = JSON.parseMap(plugin.getFormScript());
        Map serviceIntegration = (Map) metaData.get(configName);
        serviceIntegration.put("id", metaData.get("id"));
        serviceIntegration.put("key", metaData.get("key"));
        return serviceIntegration;
    }

    public void getImage(InputStream in, HttpServletResponse response) {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            out.write(in.readAllBytes());
            out.flush();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
    }

    public void delete(String id) {
        pluginMapper.deleteByPrimaryKey(id);
        try {
            pluginManager.getClassLoader(id).getStorageStrategy().delete();
            pluginManager.deletePlugin(id);
        } catch (IOException e) {
            LogUtil.error(e);
        }
    }

    public Platform getPlatFormInstance(String pluginId, Map IntegrationConfig) {
        PlatformRequest request = new PlatformRequest();
        request.setIntegrationConfig(JSON.toJSONString(IntegrationConfig));
        return pluginManager.getPlatform(pluginId, request);
    }

    public Platform getPlatFormInstance(String pluginId, String integrationConfig) {
        PlatformRequest request = new PlatformRequest();
        request.setIntegrationConfig(integrationConfig);
        return pluginManager.getPlatform(pluginId, request);
    }

    public void validateIntegration(String pluginId, Map integrationConfig) {
        Platform platform = getPlatFormInstance(pluginId, integrationConfig);
        platform.validateIntegrationConfig();
    }

    public void validateProjectConfig(String pluginId, Map projectConfig) {
        PluginMetaInfo pluginMetaInfo = pluginManager.getPluginMetaInfo(pluginId);
        IntegrationRequest integrationRequest = new IntegrationRequest();
        integrationRequest.setPlatform(pluginMetaInfo.getKey());
        integrationRequest.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        ServiceIntegration serviceIntegration = baseIntegrationService.get(integrationRequest);
        Platform platform = getPlatFormInstance(pluginId, serviceIntegration.getConfiguration());
        platform.validateProjectConfig(JSON.toJSONString(projectConfig));
    }

    public List<SelectOption> getPlatformOptions() {
        List<SelectOption> options = pluginManager.getPluginMetaInfoList()
                .stream()
                .map(pluginMetaInfo -> new SelectOption(pluginMetaInfo.getLabel(), pluginMetaInfo.getKey()))
                .collect(Collectors.toList());
        List<ServiceIntegration> integrations = baseIntegrationService.getAll(SessionUtils.getCurrentWorkspaceId());
        // 过滤掉服务集成中没有的选项
        return options.stream()
                .filter(option ->
                    integrations.stream()
                            .filter(integration -> StringUtils.equals(integration.getPlatform(), option.getValue()))
                            .collect(Collectors.toList()).size() > 0
                ).collect(Collectors.toList());
    }

    public List<String> getThirdPartTemplateSupportPlatform() {
        List<PluginMetaInfo> pluginMetaInfoList = pluginManager.getPluginMetaInfoList();
        return pluginMetaInfoList.stream()
                .filter(PluginMetaInfo::isThirdPartTemplateSupport)
                .map(PluginMetaInfo::getKey)
                .collect(Collectors.toList());
    }
}
