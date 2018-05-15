package org.wlyyy.itrs.vo;

import org.activiti.engine.repository.Deployment;

import java.text.SimpleDateFormat;
import java.util.function.Function;

public class DeploymentListItemVo {

    public static DeploymentListItemVo buildFromDomain(
            Deployment source,
            Function<String, String> getKeyById          // 根据部署id得到最新版的key
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String id = source.getId();
        final String name = source.getName();
        final String key = getKeyById.apply(id);
        final String deploymentTime = formatter.format(source.getDeploymentTime());

        return new DeploymentListItemVo(id, name, key, deploymentTime);
    }

    public DeploymentListItemVo() {
    }

    public DeploymentListItemVo(String id, String name, String key, String deploymentTime) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.deploymentTime = deploymentTime;
    }

    private String id;
    private String name;
    private String key;
    private String deploymentTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(String deploymentTime) {
        this.deploymentTime = deploymentTime;
    }
}
