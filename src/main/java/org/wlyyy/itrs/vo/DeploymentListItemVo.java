package org.wlyyy.itrs.vo;

import org.activiti.engine.repository.Deployment;

import java.text.SimpleDateFormat;

public class DeploymentListItemVo {

    public static DeploymentListItemVo buildFromDomain(
            Deployment source
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String id = source.getId();
        final String name = source.getName();
        final String deploymentTime = formatter.format(source.getDeploymentTime());

        return new DeploymentListItemVo(id, name, deploymentTime);
    }

    public DeploymentListItemVo() {
    }

    public DeploymentListItemVo(String id, String name, String deploymentTime) {
        this.id = id;
        this.name = name;
        this.deploymentTime = deploymentTime;
    }

    private String id;
    private String name;
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

    public String getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(String deploymentTime) {
        this.deploymentTime = deploymentTime;
    }
}
